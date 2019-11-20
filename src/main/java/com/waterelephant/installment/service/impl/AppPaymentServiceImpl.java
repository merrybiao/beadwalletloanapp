/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.installment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.entity.baofu.WithHodingResult;
import com.beadwallet.entity.baofu.WithLessRequest;
import com.beadwallet.entity.lianlian.*;
import com.beadwallet.servcie.BaoFuService;
import com.beadwallet.servcie.LianLianPayService;
import com.beadwallet.service.capital.service.CapitalKoudaiService;
import com.beadwallet.service.serve.BeadWalletHaoDaiService;
import com.beadwallet.service.sms.dto.MessageDto;
import com.beadwallet.utils.HttpRequest;
import com.beadwallet.utils.RSAUtil;
import com.google.gson.Gson;
import com.waterelephant.channel.service.ProductService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.constants.BaofuConstant;
import com.waterelephant.constants.ParameterConstant;
import com.waterelephant.constants.RedisKeyConstant;
import com.waterelephant.dto.LoanInfo;
import com.waterelephant.dto.RepaymentBatch;
import com.waterelephant.dto.RepaymentDto;
import com.waterelephant.entity.*;
import com.waterelephant.installment.service.AppPaymentService;
import com.waterelephant.service.*;
import com.waterelephant.sms.service.SendMessageCommonService;
import com.waterelephant.utils.*;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Module:
 * <p>
 * AppPaymentServiceImpl.java
 *
 * @author 毛恩奇
 * @version 1.0
 * @description: <描述>
 * @since JDK 1.8
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AppPaymentServiceImpl implements AppPaymentService {
    private Logger logger = Logger.getLogger(AppPaymentServiceImpl.class);
    @Autowired
    protected SqlMapper sqlMapper;
    @Autowired
    protected IBwRepaymentPlanService bwRepaymentPlanService;
    @Autowired
    protected BwOrderRepaymentBatchDetailService bwOrderRepaymentBatchDetailService;
    @Autowired
    protected BwPaymentDetailService bwPaymentDetailService;
    @Autowired
    protected ProductService productService;
    @Autowired
    protected IBwOrderXuDaiService bwOrderXudaiService;
    @Autowired
    protected ExtraConfigService extraConfigService;
    @Autowired
    protected IBwBorrowerService bwBorrowerService;
    @Autowired
    protected IBwOrderService bwOrderService;
    @Autowired
    protected IBwBankCardService bwBankCardService;
    @Autowired
    protected BwOrderRongService bwOrderRongService;
    @Autowired
    protected BwOrderStatusRecordService bwOrderStatusRecordService;
    @Autowired
    protected BwPlatformRecordService bwPlatformRecordService;
    @Autowired
    protected BwCapitalWithholdService bwCapitalWithholdService;
    @Autowired
    protected BwOverdueRecordService bwOverdueRecordService;
    @Autowired
    protected SendMessageCommonService sendMessageCommonService;
    // 是否测试
    public boolean testBool = SystemConstant.WITHHOLD_TEST_BOOL;

    /**
     * @see com.waterelephant.installment.service.AppPaymentService#updateAndPay(java.util.Map)
     */
    @Override
    public AppResponseResult updateAndPay(Map<String, String> paramMap) {
        AppResponseResult result = new AppResponseResult();
        // int isUseCoupon = Integer.parseInt(paramMap.get("isUseCoupon"));// 是否使用优惠券
        int payType = Integer.parseInt(paramMap.get("payType"));// 1.还款 2.展期
        double batchMoney = Double.parseDouble(paramMap.get("batchMoney"));// 还款金额
        // 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
        Integer terminalType = NumberUtil.parseInteger(paramMap.get("terminalType"), null);
        logger.info("============进入AppPayment支付==========请求参数：" + paramMap);
        BwOrder bwOrder = JSON.parseObject(paramMap.get("bwOrderJson"), BwOrder.class);
        BwBankCard bwBankCard = JSON.parseObject(paramMap.get("bwBankCardJson"), BwBankCard.class);
        BwBorrower bwBorrower = bwBorrowerService.findBwBorrowerById(bwOrder.getBorrowerId());
        Integer productType = bwOrder.getProductType();
        Long orderId = bwOrder.getId();

        List<BwRepaymentPlan> planList = null;
        if (productType == 1) {// 单期
            planList = new ArrayList<BwRepaymentPlan>();
            planList.add(bwRepaymentPlanService.getLastRepaymentPlanByOrderId(orderId));
        } else if (productType == 2) {// 多期，获取所有还款计划
            planList = bwRepaymentPlanService.getRepaymentPlanList(orderId, null);
        }
        if (planList == null || planList.isEmpty()) {
            result.setCode("108");
            result.setMsg("还款计划不存在");
            return result;
        }
        RepaymentBatch repaymentBatch = bwOrderRepaymentBatchDetailService.getRepaymentBatch(orderId);
        // 分批已还款金额
        Double alreadyTotalBatchMoney = repaymentBatch.getAlreadyTotalBatchMoney();

        // 已续贷期数
        Integer hasXudaiTerm = bwOrderXudaiService.queryXudaiTerm(orderId);
        int xudaiTimes = hasXudaiTerm;
        if (payType == 2) {// 续贷
            xudaiTimes = xudaiTimes + 1;
        }
        paramMap.put("xudaiTimes", xudaiTimes + "");
        // 5月13号以后续贷次数
        Integer hasAfterXudaiTerm = bwRepaymentPlanService.getXuDaiCountAfterDate(orderId);
        double payAmount = 0.0;// 实际支付金额
        BwProductDictionary product = productService.queryByOrderId(orderId);

        // 计算还款或续贷总金额
        Map<Long, LoanInfo> loanInfoMap = new HashMap<Long, LoanInfo>();
        List<BwRepaymentPlan> notRepayPlanList = new ArrayList<BwRepaymentPlan>();// 未还款
        Double totalRepayMoney = bwOrder.getBorrowAmount();// 总共还款金额，不算利息
        for (BwRepaymentPlan plan : planList) {
            LoanInfo loanInfo = new LoanInfo();
            loanInfo.setAvoidFineDate(bwOrder.getAvoidFineDate());
            loanInfo.setAmt(totalRepayMoney.toString());
            Double realityRepayMoney = plan.getRealityRepayMoney();
            Long repayId = plan.getId();
            Integer repayStatus = plan.getRepayStatus();
            if (payType == 1) {// 多期还款才有多个还款计划
                productService.calcRepaymentCost(realityRepayMoney, orderId, repayId, product, loanInfo);
                double realOverdueAmount = NumberUtil.parseDouble(loanInfo.getRealOverdueAmount(), 0.0);
                payAmount = DoubleUtil.add(DoubleUtil.add(payAmount, realityRepayMoney), realOverdueAmount);// 总共还款金额
            } else {// 展期，只获取最后一条还款计划
                Double xudaiMoney = productService.calcXudaiCost(totalRepayMoney, orderId, repayId, product,
                        hasAfterXudaiTerm + 1, loanInfo);
                payAmount = DoubleUtil.add(payAmount, xudaiMoney);
            }
            loanInfoMap.put(repayId, loanInfo);
            if (repayStatus == 1 || repayStatus == 3) {// 未还款
                notRepayPlanList.add(plan);
            }
        }

        double totalAmount = payAmount;// 总共应还金额
        double totalLeftAmount = DoubleUtil.sub(totalAmount, alreadyTotalBatchMoney);// 总剩余还款金额

        // 最少分期还款金额
        String minBatchRepaymentAmountStr = extraConfigService
                .findCountExtraConfigByCode(ParameterConstant.MIN_BATCH_REPAYMENT_AMOUNT);
        double minBatchRepaymentAmount = NumberUtils.toDouble(minBatchRepaymentAmountStr, 0.0);
        if (batchMoney > totalLeftAmount) {
            result.setCode("121");
            result.setMsg("还款金额不能大于剩余还款金额");
            return result;
        }
        if (minBatchRepaymentAmount >= totalLeftAmount && batchMoney < totalLeftAmount) {// 剩余还款金额小于最小金额，且还款金额小于剩余还款金额
            result.setCode("122");
            result.setMsg("需一次还完剩下的欠款！");
            return result;
        }
        if (minBatchRepaymentAmount < totalLeftAmount && batchMoney < minBatchRepaymentAmount) {// 还款金额小于最小还款金额
            result.setCode("122");
            result.setMsg("每次还款金额不能低于" + minBatchRepaymentAmountStr + "元");
            return result;
        }

        int repaymentChannel = Integer.parseInt(paramMap.get("repaymentChannel"));
        paramMap.put("signStatus", bwBankCard.getSignStatus() + "");

        // 该金额可以还到最后一个还款计划ID
        Long lastPayRepayId = notRepayPlanList.get(notRepayPlanList.size() - 1).getId();
        if (productType == 1) {// TODO 单期还款或续贷，暂时老接口
            result.setCode("222");
            result.setMsg("");
            logger.info("分期还款：单期接口，用老接口，orderId：" + orderId);
            return result;
        } else if (productType == 2 && payType == 1) {// 分期还款，没有优惠券
            // 开始记录
            double leftBatchMoney = batchMoney;
            lastPayRepayId = recordPayInfoRecursive(bwOrder, notRepayPlanList, loanInfoMap, paramMap,
                    repaymentBatch, leftBatchMoney, 0, lastPayRepayId);
            BwOrderRepaymentBatchDetail batchDetail = getRepaymentBatchDetail(bwOrder, paramMap, notRepayPlanList,
                    loanInfoMap, repaymentBatch, totalAmount);
            String batchDetailJson = JSON.toJSONString(batchDetail);
            logger.info("分期还款：AppPaymentServiceImpl工单Id：" + orderId + "，保存BwOrderRepaymentBatchDetail到redis:"
                    + batchDetailJson);
            RedisUtils.hset(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, String.valueOf(orderId), batchDetailJson);
            if (!RedisUtils.hexists(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, String.valueOf(orderId))) {
                result.setCode("223");
                result.setMsg("支付失败");
                logger.info(
                        "分期还款：保存BwOrderRepaymentBatchDetail到redis失败，orderId：" + orderId + "，" + batchDetailJson);
                return result;
            }
        }

        payAmount = batchMoney;// 实际支付金额
        logger.info("=============orderId：" + orderId + "实际交易金额：" + payAmount + "=================");

        if (repaymentChannel == 2) {// 续贷并且连连签约用连连支付
            result = lianlianPay(bwOrder, bwBorrower, bwBankCard, terminalType.toString(), payType, lastPayRepayId,
                    payAmount);
        } else {// 还款或签约宝付用宝付
            result = baofooPay(bwOrder, bwBorrower, bwBankCard, payType, lastPayRepayId, payAmount);
        }
        String payCode = result.getCode();
        if (!"000".equals(payCode)) {// 调用支付失败
            result.setCode("110");
            result.setMsg("支付失败");
            // 删除redis中支付明细和分批记录
            for (BwRepaymentPlan plan : notRepayPlanList) {
                RedisUtils.hdel(SystemConstant.NOTIFY_BAOFU, orderId.toString());
                RedisUtils.hdel(RedisKeyConstant.PAYMENT_DETAIL, plan.getId().toString());
                RedisUtils.hdel(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, orderId.toString());
            }
            return result;
        }


        return result;
    }

    private BwOrderRepaymentBatchDetail getRepaymentBatchDetail(BwOrder bwOrder, Map<String, String> paramMap,
                                                                List<BwRepaymentPlan> notRepayPlanList, Map<Long, LoanInfo> loanInfoMap, RepaymentBatch repaymentBatch,
                                                                Double totalAmount) {
        Long orderId = bwOrder.getId();
        BwRepaymentPlan earlyPlan = notRepayPlanList.get(0);
        double totalOverdueAmount = 0.0;// 总逾期金额
        for (BwRepaymentPlan notPlan : notRepayPlanList) {
            LoanInfo loanInfo = loanInfoMap.get(notPlan.getId());
            totalOverdueAmount = DoubleUtil.add(totalOverdueAmount,
                    NumberUtils.toDouble(loanInfo.getOverdueAmount(), 0.0));
        }
        LoanInfo earlyLoanInfo = loanInfoMap.get(earlyPlan.getId());
        double batchMoney = Double.parseDouble(paramMap.get("batchMoney"));// 还款金额
        // 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
        Integer terminalType = NumberUtil.parseInteger(paramMap.get("terminalType"), null);
        // 总剩余金额
        double totalLeftAmount = DoubleUtil.sub(totalAmount, repaymentBatch.getAlreadyTotalBatchMoney());
        int repaymentChannel = Integer.parseInt(paramMap.get("repaymentChannel"));
        Date nowDate = new Date();
        BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = new BwOrderRepaymentBatchDetail();
        bwOrderRepaymentBatchDetail.setAmount(batchMoney);
        bwOrderRepaymentBatchDetail.setResidualAmount(DoubleUtil.sub(totalLeftAmount, batchMoney));
        // 开始保存
        bwOrderRepaymentBatchDetail.setOrderId(orderId);
        bwOrderRepaymentBatchDetail.setNumber(repaymentBatch.getCurrentNumber());
        bwOrderRepaymentBatchDetail.setRepaymentChannel(repaymentChannel);
        bwOrderRepaymentBatchDetail.setOverdueDay(earlyLoanInfo.getOverdueDay());
        bwOrderRepaymentBatchDetail.setOverdueAmount(totalOverdueAmount);
        bwOrderRepaymentBatchDetail.setTotalAmount(totalAmount);
        bwOrderRepaymentBatchDetail.setCreateTime(nowDate);
        bwOrderRepaymentBatchDetail.setUpdateTime(nowDate);
        if (batchMoney < totalLeftAmount) {
            bwOrderRepaymentBatchDetail.setLastRepayment(false);
        } else {
            bwOrderRepaymentBatchDetail.setLastRepayment(true);
        }
        if (terminalType != null) {
            bwOrderRepaymentBatchDetail.setTerminalType(terminalType);
        }
        return bwOrderRepaymentBatchDetail;
    }

    /**
     * 递归，根据参数还款金额，逐层还款
     *
     * @param bwOrder
     * @param notRepayPlanList
     * @param loanInfoMap
     * @param paramMap
     * @param repaymentBatch
     * @param leftBatchMoney
     * @param planIndex
     * @param lastPayRepayId   最后一个还款计划ID
     * @return
     */
    private Long recordPayInfoRecursive(BwOrder bwOrder, List<BwRepaymentPlan> notRepayPlanList,
                                        Map<Long, LoanInfo> loanInfoMap, Map<String, String> paramMap, RepaymentBatch repaymentBatch,
                                        double leftBatchMoney, int planIndex, Long lastPayRepayId) {
        int payType = Integer.parseInt(paramMap.get("payType"));// 1.还款 2.展期
        BwBankCard bwBankCard = JSON.parseObject(paramMap.get("bwBankCardJson"), BwBankCard.class);
        // 终端类型：0系统后台 1Android 2ios 3WAP 4外部渠道
        Integer terminalType = NumberUtil.parseInteger(paramMap.get("terminalType"), null);
        Integer xudaiTimes = NumberUtil.parseInteger(paramMap.get("xudaiTimes"), 0);
        BwRepaymentPlan currentPlan = notRepayPlanList.get(planIndex);
        Long repayId = currentPlan.getId();
        Long orderId = bwOrder.getId();
        Long borrowerId = bwOrder.getBorrowerId();
        int repaymentChannel = Integer.parseInt(paramMap.get("repaymentChannel"));
        LoanInfo loanInfo = loanInfoMap.get(repayId);
        double totalAmount = NumberUtils.toDouble(loanInfo.getAmt(), 0.0);// 计算后的总金额
        Double alreadyRepayMoney = currentPlan.getAlreadyRepayMoney();// 已还金额
        if (alreadyRepayMoney == null) {
            alreadyRepayMoney = 0.0;
        }
        double leftAmount = DoubleUtil.sub(totalAmount, alreadyRepayMoney);// 该还款计划剩余还款金额
        boolean isLastRepayment = false;// 改还款计划是否结束
        if (leftBatchMoney < leftAmount) {// 未还清，递归结束

        } else if (leftBatchMoney == leftAmount) {// 刚好还清，递归结束
            isLastRepayment = true;
        } else {// 还清并有多余，继续下一个还款计划
            isLastRepayment = true;
        }

        if (leftBatchMoney < leftAmount) {// 未还清，递归结束

        } else if (leftBatchMoney == leftAmount) {// 刚好还清，递归结束
            isLastRepayment = true;
        } else {// 还清并有多余，继续下一个还款计划
            isLastRepayment = true;
        }

        if (isLastRepayment) {// 记录支付明细
            // 扣款明细存redis，回调时取并存入BwPaymentDetail，先保存再掉支付接口，防止还没保存回调取不到值，支付失败删除
            BwPaymentDetail bwPaymentDetail = new BwPaymentDetail(null, orderId, repayId, payType,
                    NumberUtils.toDouble(loanInfo.getAmt(), 0.0), 0.0, currentPlan.getRealityRepayMoney(),
                    NumberUtils.toDouble(loanInfo.getOverdueAmount(), 0.0), NumberUtils.toDouble("couponAmount", 0.0),
                    new Date(), null);
            bwPaymentDetail.setNoOverdueAmount(NumberUtils.toDouble(loanInfo.getNoOverdueAmount(), 0.0));
            bwPaymentDetail.setRealOverdueAmount(NumberUtils.toDouble(loanInfo.getRealOverdueAmount(), 0.0));
            bwPaymentDetail.setBorrowerId(borrowerId);
            bwPaymentDetail.setPayChannel(repaymentChannel);
            bwPaymentDetail.setOverdueDay(loanInfo.getOverdueDay());
            bwPaymentDetail.setXudaiTimes(xudaiTimes);
            if (bwBankCard != null) {
                bwPaymentDetail.setCardNo(bwBankCard.getCardNo());
                bwPaymentDetail.setBankCode(bwBankCard.getBankCode());
            }
            if (terminalType != null) {
                bwPaymentDetail.setTerminalType(terminalType);
            }
            String bwPaymentDetailJson = JSON.toJSONString(bwPaymentDetail);
            logger.info(
                    "分期还款：AppPaymentServiceImpl工单Id：" + orderId + "，保存BwPaymentDetail到redis:" + bwPaymentDetailJson);
            RedisUtils.hset(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString(), bwPaymentDetailJson);
            if (!RedisUtils.hexists(RedisKeyConstant.PAYMENT_DETAIL, repayId.toString())) {
                logger.error("分期还款：保存bwPaymentDetail到redis失败，orderId：" + orderId + "，repayId：" + repayId
                        + "，bwPaymentDetail：" + bwPaymentDetail);
                throw new RuntimeException("保存bwPaymentDetail到redis失败");
            }
        }
        planIndex++;
        leftBatchMoney = DoubleUtil.sub(leftBatchMoney, leftAmount);// 还款后剩余分批金额
        if (leftBatchMoney > 0.0) {
            lastPayRepayId = recordPayInfoRecursive(bwOrder, notRepayPlanList, loanInfoMap, paramMap, repaymentBatch,
                    leftBatchMoney, planIndex, currentPlan.getId());
        } else {
            lastPayRepayId = currentPlan.getId();
        }
        return lastPayRepayId;
    }

    /**
     * 连连扣款接口
     *
     * @param bwOrder
     * @param bwBorrower
     * @param bwBankCard
     * @param appRequest
     * @param payType
     * @param repayId
     * @param payAmount
     * @return
     */
    private AppResponseResult lianlianPay(BwOrder bwOrder, BwBorrower bwBorrower, BwBankCard bwBankCard,
                                          String appRequest, Integer payType, Long repayId, Double payAmount) {
        logger.info("===============开始调用连连支付================orderId：" + bwOrder.getId());
        List<BwRepaymentPlan> repaymentPlanList = bwRepaymentPlanService.getRepaymentPlanList(bwOrder.getId(),
                Arrays.asList(1, 3));
        List<RepaymentPlan> planList = new ArrayList<RepaymentPlan>();
        for (BwRepaymentPlan repaymentPlan : repaymentPlanList) {
            RepaymentPlan repay = new RepaymentPlan();
            repay.setAmount(payAmount.toString());
            repay.setDate(CommUtils.convertDateToString(repaymentPlan.getRepayTime(), SystemConstant.YMD));
            planList.add(repay);
        }
        AppResponseResult result = new AppResponseResult();
        Long borrowerId = bwOrder.getBorrowerId();
        // 连连支付签约查询接口
        logger.info("=====================连连签约查询接口开始--参数：borrowerId--" + borrowerId + " ,cardNo--"
                + bwBankCard.getCardNo());
        CardQueryResult cardResult = LianLianPayService.cardBindQuery(borrowerId.toString(), bwBankCard.getCardNo());
        logger.info("================连连签约查询接口返回值：" + new Gson().toJson(cardResult));
        if (CommUtils.isNull(cardResult.getAgreement_list())) {
            result.setCode("113");
            result.setMsg("该用户未签约连连支付");
            logger.info("==========================连连卡扣=============该用户未签约连连支付");
            return result;
        }

        String no_agree = cardResult.getAgreement_list().get(0).getNo_agree();
        logger.info("==========================连连卡扣============用户id:" + borrowerId + "的连连支付协议号:" + no_agree);
        SignalLess signalLess = new SignalLess();
        signalLess.setAcct_name(bwBorrower.getName());
        signalLess.setApp_request(appRequest);
        signalLess.setCard_no(bwBankCard.getCardNo());
        signalLess.setId_no(bwBorrower.getIdCard());
        signalLess.setNo_agree(no_agree);
        signalLess.setUser_id(borrowerId.toString());
        PlanResult planResult = LianLianPayService.sigalAccreditPay(signalLess, bwOrder.getOrderNo(), planList);
        logger.info("=======================连连支付单独授权接口返回值：" + JSON.toJSONString(planResult));

        RepayRequest repayRequest = new RepayRequest();
        repayRequest.setNo_order(GenerateSerialNumber.getSerialNumber().substring(8) + repayId);
        logger.info("orderId：" + bwOrder.getId() + "，传给连连的交易编号：" + repayRequest.getNo_order());
        repayRequest.setUser_id(bwBorrower.getId().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        repayRequest.setDt_order(dateFormat.format(new Date()));
        repayRequest.setName_goods("易秒贷");
        // TODO 测试金额，上线前改回来
        if (testBool) {
            repayRequest.setMoney_order("0.01");// 测试金额0.01
        } else {
            repayRequest.setMoney_order(String.valueOf(payAmount));
        }
        repayRequest.setRepayment_no(bwOrder.getOrderNo());
        repayRequest.setUser_info_bind_phone(bwBorrower.getPhone());
        repayRequest.setUser_info_dt_register(dateFormat.format(bwBorrower.getCreateTime()));
        repayRequest.setUser_info_full_name(bwBorrower.getName());
        repayRequest.setUser_info_id_no(bwBorrower.getIdCard());
        if (payType == 1) {// 还款
            repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + "/app/payment/lianlianRepaymentNotify.do");// 回调地址
        } else {
            repayRequest.setNotify_url(SystemConstant.NOTIRY_URL + "/app/payment/lianlianXuDaiNotify.do");// 回调地址
        }
        repayRequest.setSchedule_repayment_date(planList.get(0).getDate());
        repayRequest.setNo_agree(no_agree);
        RepaymentResult repaymentResult = LianLianPayService.bankRepay(repayRequest);
        if (!"0000".equals(planResult.getRet_code())) {
            result.setCode("122");
            result.setMsg(planResult.getRet_msg());
            return result;
        }
        logger.info("================================连连卡扣接口返回值：" + JSON.toJSONString(repaymentResult));
        if (!"0000".equals(repaymentResult.getRet_code())) {
            result.setCode("115");
            result.setMsg(repaymentResult.getRet_msg());
        } else {
            result.setCode("000");
            result.setMsg("还款成功");
        }
        return result;
    }

    /**
     * 宝付扣款接口
     *
     * @param bwOrder
     * @param bwborrower
     * @param bwBankCard
     * @param payType
     * @param planId
     * @param payAmount
     * @return
     */
    private AppResponseResult baofooPay(BwOrder bwOrder, BwBorrower bwborrower, BwBankCard bwBankCard, Integer payType,
                                        Long planId, Double payAmount) {
        Long orderId = bwOrder.getId();
        logger.info("===============开始调用宝付支付================orderId：" + orderId);
        String mchnt_txn_ssn = "";
        if (payType == 1) {// 还款
            mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber().substring(8) + "A" + planId;
        } else {
            mchnt_txn_ssn = GenerateSerialNumber.getSerialNumber().substring(8) + "B" + planId;
        }
        logger.info("=================orderId：" + orderId + "，传给宝付的交易编号：" + mchnt_txn_ssn);
        String bankCode = bwBankCard.getBankCode();
        WithLessRequest withLess = new WithLessRequest();
        withLess.setAcc_no(bwBankCard.getCardNo());// 卡号
        withLess.setId_card(bwborrower.getIdCard());// 身份证号
        withLess.setId_holder(bwborrower.getName());// 持卡人姓名
        withLess.setMobile(bwborrower.getPhone());// 银行预留手机号
        String bank_code = BaofuConstant.convertFuiouBankCodeToBaofu(bankCode);
        logger.info("银行编码：" + bankCode + "，对应宝付银行编码：" + bank_code);
        withLess.setPay_code(bank_code);// 银行编码
        // withLess.setTxn_amt("1");// 测试交易金额
        withLess.setTxn_amt(payAmount.toString());// 交易金额
        withLess.setRepayId(mchnt_txn_ssn);// 还款计划id
        withLess.setOrderNo(bwOrder.getOrderNo());
        WithHodingResult res = null;
        if (testBool) {// TODO 测试
            res = new WithHodingResult();
            res.setResp_code("0000");
            res.setResp_msg("测试通过");
        } else {
            res = BaoFuService.withHold(withLess);
        }
        logger.info("=================宝付代扣返回值=============，orderId：" + orderId + "，：" + JSON.toJSONString(res));
        String respCode = res.getResp_code();
        AppResponseResult result = new AppResponseResult();
        if (testBool) {
            respCode = "0000";
        }
        if ("0000".equals(respCode)) {
            result.setCode("000");
            result.setMsg("扣款成功");
            logger.info("宝付扣款成功：[" + respCode + "]" + res.getResp_msg());
        } else if (!CommUtils.isNull(respCode) && SystemConstant.baofuCode.contains(respCode)) {// 处理中
            result.setCode("000");
            logger.info("宝付处理中，返回消息：[" + respCode + "]" + res.getResp_msg());
        } else {
            logger.info("宝付扣款失败，返回消息：[" + respCode + "]" + res.getResp_msg());
            result.setCode("122");
            result.setMsg(res.getResp_msg());
        }
        if (testBool) {
            // 模拟请求回调
            if ("000".equals(result.getCode())) {
                requestBaofooNotifyTest(orderId, mchnt_txn_ssn, payAmount);
            }
        }
        return result;
    }

    /**
     * @throws Exception
     * @see com.waterelephant.installment.service.AppPaymentService#updateForLianlianPaymentNotify(com.beadwallet.entity.lianlian.NotifyResult)
     */
    @Override
    public NotifyNotice updateForLianlianPaymentNotify(NotifyResult notifyResult) throws Exception {
        // 验签
        NotifyNotice notice = checkSign(notifyResult);
        if (!"0000".equals(notice.getRet_code())) {
            return notice;
        }
        if (CommUtils.isNull(notifyResult.getNo_order())) {// 订单号
            logger.info("==================工单id为空========================");
            notice.setRet_code("101");
            notice.setRet_msg("工单id为空");
            return notice;
        }
        Long lastPayRepayId = NumberUtils.toLong(notifyResult.getNo_order().substring(20), 0L);
        BwRepaymentPlan plan = bwRepaymentPlanService.getBwRepaymentPlanByPlanId(lastPayRepayId);
        Long orderId = plan.getOrderId();
        BwOrder bwOrder = bwOrderService.findBwOrderById(orderId.toString());
        if (bwOrder.getStatusId() == 6) {// 已经还款
            logger.info(
                    "=======================该工单:" + orderId + " 已经处理，状态======================" + bwOrder.getStatusId());
            notice.setRet_code("0000");
            notice.setRet_msg("交易成功");
            return notice;
        }

        // 查询还款人信息
        BwBorrower borrower = bwBorrowerService.findBwBorrowerByOrderId(Long.valueOf(orderId));
        if (CommUtils.isNull(borrower)) {
            logger.info("==================借款人信息为空========================");
            notice.setRet_code("102");
            notice.setRet_msg("借款人为空");
            return notice;
        }
        // 查询银行卡信息
        BwBankCard card = bwBankCardService.findBwBankCardByBoorwerId(borrower.getId());
        if (CommUtils.isNull(card)) {
            logger.info("==================银行卡信息为空========================");
            notice.setRet_code("103");
            notice.setRet_msg("银行卡信息为空");
            return notice;
        }

        // 扣款金额
        double tradeAmount = Double.valueOf(notifyResult.getMoney_order());
        // 所有还款计划
        List<BwRepaymentPlan> planList = bwRepaymentPlanService.getRepaymentPlanList(orderId, null);
        // 产品类型，1.单期 2.多期
        Integer productType = bwOrder.getProductType();
        // 交易失败
        if (!"SUCCESS".equals(notifyResult.getResult_pay())) {
            logger.info("====================交易失败======================");
            notice.setRet_code("102");
            notice.setRet_msg("交易失败");

            // 删除redis支付详情
            for (BwRepaymentPlan tempPlan : planList) {
                RedisUtils.hdel(RedisKeyConstant.PAYMENT_DETAIL, tempPlan.getId().toString());
            }
            RedisUtils.hdel(RedisKeyConstant.BATCH_REPAYMENT_DETAIL, orderId.toString());

            // 往BwOrderStatusRecord表插入记录用于弹窗
            bwOrderStatusRecordService.insertRecord(bwOrder, ActivityConstant.BWORDERSTATUSRECORD_MSG.MSG_ERROR,
                    ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_REPAYFAIL);
            return notice;
        }

        String bwOrderRepaymentBatchDetailStr = RedisUtils.hget(RedisKeyConstant.BATCH_REPAYMENT_DETAIL,
                orderId.toString());
        BwOrderRepaymentBatchDetail bwOrderRepaymentBatchDetail = null;
        if (StringUtils.isNotEmpty(bwOrderRepaymentBatchDetailStr)) {
            bwOrderRepaymentBatchDetail = JSON.parseObject(bwOrderRepaymentBatchDetailStr,
                    BwOrderRepaymentBatchDetail.class);
        }

        Integer terminalType = null;
        if (bwOrderRepaymentBatchDetail != null) {
            terminalType = bwOrderRepaymentBatchDetail.getTerminalType();
            // 记录弹窗(分批)
            bwOrderStatusRecordService.insertRecord(bwOrder,
                    "您于" + DateUtil.getCurrentDateString(DateUtil.YMD) + "已经成功还款"
                            + Double.valueOf(notifyResult.getMoney_order()) + "元" + ",剩余还款金额"
                            + bwOrderRepaymentBatchDetail.getResidualAmount() + "元",
                    ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_BATCHREPAYSUCCESS);
        } else {
            // 记录弹窗(不分批)
            bwOrderStatusRecordService.insertRecord(bwOrder,
                    "您于" + DateUtil.getCurrentDateString(DateUtil.YMD) + "已经成功还款"
                            + Double.valueOf(notifyResult.getMoney_order()) + "元",
                    ActivityConstant.BWORDERSTATUSRECORD_DIALOGSTYLE.DIALOGSTYLE_REPAYSUCCESS);
        }

        // 已还清的还款计划支付明细
        List<BwPaymentDetail> paymentDetailList = new ArrayList<BwPaymentDetail>();
        // 记录分批还款明细，全额一次还款或最后一批还款则记录支付金额明细
        for (BwRepaymentPlan tempPlan : planList) {
            BwPaymentDetail bwPaymentDetail = bwOrderRepaymentBatchDetailService
                    .saveBatchDetailAndRepaymentDetailByRedis(orderId, tempPlan.getId(), false);
            if (bwPaymentDetail != null) {
                paymentDetailList.add(bwPaymentDetail);
            }
        }

        // 记录流水
        BwPlatformRecord bwPlatformRecord = new BwPlatformRecord();
        bwPlatformRecord.setTradeNo(notifyResult.getOid_paybill());
        bwPlatformRecord.setTradeAmount(Double.valueOf(notifyResult.getMoney_order()));// 交易金额
        bwPlatformRecord.setTradeType(1);// 1划拨2转账
        bwPlatformRecord.setOutAccount(card.getCardNo());
        bwPlatformRecord.setOutName(borrower.getName());
        bwPlatformRecord.setInAccount("上海水象金融信息服务有限公司-连连支付");
        bwPlatformRecord.setInName("上海水象金融信息服务有限公司-连连支付");
        bwPlatformRecord.setOrderId(bwOrder.getId());
        bwPlatformRecord.setTradeTime(new Date());
        bwPlatformRecord.setTradeRemark("连连还款扣款");
        bwPlatformRecord.setTradeChannel(3);// 连连支付
        bwPlatformRecord.setTerminalType(terminalType);// 终端类型
        bwPlatformRecordService.saveOrUpdateByTradeNo(bwPlatformRecord);

        // 已还款金额
        Double alreadyRepayMoney = bwOrderRepaymentBatchDetailService.getBatchDetailTotal(orderId);
        if (alreadyRepayMoney == 0.0 && productType == 1) {// 单期全额还款没有分批记录，只有支付明细
            BwPaymentDetail bwPaymentDetail = bwPaymentDetailService.queryByRedisOrDB(lastPayRepayId);
            Double realOverdueAmount = bwPaymentDetail.getRealOverdueAmount();
            Double borrowAmount = bwPaymentDetail.getBorrowAmount();
            if (realOverdueAmount == null) {
                realOverdueAmount = 0.0;
            }
            if (borrowAmount == null) {
                borrowAmount = 0.0;
            }
            alreadyRepayMoney = DoubleUtil.add(realOverdueAmount, borrowAmount);
        }

        // 换完后修改工单状态
        Boolean isLastRepayment = true;// 是否最后一次还款并结清
        if (bwOrderRepaymentBatchDetail != null) {// 更新工单状态
            isLastRepayment = bwOrderRepaymentBatchDetail.getLastRepayment();
        }
        // 有支付明细则对应还款计划已还完，更新还款计划状态；最后一批更新工单状态
        updateRepaymentPlanStatus(paymentDetailList, orderId, lastPayRepayId, alreadyRepayMoney);
        if (isLastRepayment) {
            sqlMapper.update("update bw_order set status_id = 6,update_time = now() where id=" + orderId);// 修改订单状态
        } else {// 工单处于还款或逾期中，还款计划有一个在逾期中则修改工单未逾期中状态，否则还款中状态
            String nowTime = CommUtils.convertDateToString(new Date(), "yyyy-MM-dd") + " 00:00:00";
            String sql = "SELECT count(*) FROM bw_repayment_plan " + "WHERE order_id=" + orderId
                    + " and repay_status in (1,3) " + "AND repay_time < '" + nowTime + "'";
            // 未还款并逾期中还款计划
            Integer notRepayPlanCount = sqlMapper.selectOne(sql, Integer.class);
            int updateStatusId = 0;
            if (notRepayPlanCount > 0) {
                updateStatusId = 13;
            } else {
                updateStatusId = 9;
            }
            sqlMapper.update("update bw_order set status_id=" + updateStatusId + " where id=" + orderId);
        }
        // 删除redis
        for (BwRepaymentPlan tempPlan : planList) {
            bwOrderRepaymentBatchDetailService.deleteBatchDetailAndRepaymentDetailRedis(orderId, tempPlan.getId());
        }

        // MsgReqData msgReqData = new MsgReqData();
        // msgReqData.setPhone(borrower.getPhone());
        // String msg = "【水象借点花】尊敬的" + borrower.getName() + ",您于" + DateUtil.getDateString(new Date(),
        // DateUtil.yyyy_MM_dd)
        // + "成功还款" + DoubleUtil.toTwoDecimal(tradeAmount) + "元。点滴信用，弥足珍贵，水象祝您生活愉快！";
        // msgReqData.setMsg(msg);
        // msgReqData.setType(SystemConstant.MESSAGE_CIRCUMSTANCE);
        // Response<Object> rsp = BeadWalletSendMsgService.sendMsg(msgReqData);
        // if (rsp.getRequestCode().equals("200")) {
        // logger.info("还款短信发送成功,工单Id:" + bwOrder.getId());
        // } else {
        // logger.info("还款短信发送失败,工单Id:" + bwOrder.getId());
        // }
        String phone = borrower.getPhone();
        String message = "尊敬的" + borrower.getName() + ",您于" + DateUtil.getDateString(new Date(), DateUtil.yyyy_MM_dd)
                + "成功还款" + DoubleUtil.toTwoDecimal(tradeAmount) + "元。点滴信用，弥足珍贵，水象祝您生活愉快！";
        // boolean bo = sendMessageCommonService.commonSendMessage(phone, message);
        // if (bo) {
        // logger.info("还款短信发送成功,工单Id:" + bwOrder.getId());
        // } else {
        // logger.info("还款短信发送失败,工单Id:" + bwOrder.getId());
        // }
        MessageDto messageDto = new MessageDto();
        messageDto.setBusinessScenario("1");
        messageDto.setPhone(phone);
        messageDto.setMsg(message);
        messageDto.setType("1");
        RedisUtils.rpush("system:sendMessage", JSON.toJSONString(messageDto));
        logger.info("====================交易成功======================");

        try {
            String channel = bwOrder.getChannel() + "";
            if ("12".equals(channel) || "81".equals(channel)) {
                // 好贷
                String thirdOrderId = bwOrderRongService.findThirdOrderNoByOrderId(String.valueOf(bwOrder.getId()));
                if (!StringUtils.isEmpty(thirdOrderId)) {
                    BeadWalletHaoDaiService.sendOrderStatus(thirdOrderId, "4");
                }
            }
            // channelService.sendOrderStatus(CommUtils.toString(order.getChannel()),
            // orderId, "6");
        } catch (Exception e) {
            logger.info("====================渠道同步工单状态，回调失败======================");
        }
        notice.setRet_code("0000");
        notice.setRet_msg("交易成功");
        RedisUtils.del(SystemConstant.NOTIFY_LIANLIAN_PRE + orderId);
        logger.info("删除连连redis成功============");
        return notice;
    }

    /**
     * 有支付明细则对应还款计划已还完，清零罚息并更新还款计划状态<br />
     *
     * @param paymentDetailList
     * @param orderId
     * @param lastPayRepayId
     * @param alreadyRepayMoney 总共已还金额
     * @throws ParseException
     */
    private void updateRepaymentPlanStatus(List<BwPaymentDetail> paymentDetailList, Long orderId, Long lastPayRepayId,
                                           Double alreadyRepayMoney) throws ParseException {
        boolean updatePlanBool = true;// 已还清为false
        if (paymentDetailList != null && !paymentDetailList.isEmpty()) {
            for (BwPaymentDetail tempDetail : paymentDetailList) {
                Double tradeAmount = tempDetail.getTradeAmount();
                Long repayId = tempDetail.getRepayId();
                if (repayId.equals(lastPayRepayId)) {// 已还清则有支付明细，不走下面的更新已还金额
                    updatePlanBool = false;
                }
                // 还款已扣除逾期罚息，清理逾期记录金额，续贷会在定时任务XuDaiJob执行
                // bwOverdueRecordService.updateBwOverdueRecordMoney(orderId, repayId);
                // 更新还款计划状态
                BwRepaymentPlan tempPlan = bwRepaymentPlanService.getBwRepaymentPlanByPlanId(repayId);
                tempPlan.getRepayTime();
                tempPlan.setUpdateTime(new Date());
                tempPlan.setAlreadyRepayMoney(tradeAmount);// 不算优惠券
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date repayTime = dateFormat.parse(dateFormat.format(tempPlan.getRepayTime()));
                Date now = dateFormat.parse(dateFormat.format(new Date()));
                if (now.after(repayTime)) {
                    tempPlan.setRepayType(2);// 逾期还款
                } else if (now.before(repayTime)) {
                    tempPlan.setRepayType(3);// 提前还款
                } else {
                    tempPlan.setRepayType(1);// 正常还款
                }
                tempPlan.setRepayStatus(2);// 已还款
                bwRepaymentPlanService.update(tempPlan);
            }
        }
        if (updatePlanBool) {// 最后一个批量还款没还清，支付明细则通过此方法更新已还金额
            Double totalDetailAmount = sqlMapper.selectOne(
                    "select sum(trade_amount) from bw_payment_detail where order_id=" + orderId, Double.class);
            if (totalDetailAmount == null) {
                totalDetailAmount = 0.0;
            }
            double leftBatchMoney = DoubleUtil.sub(alreadyRepayMoney, totalDetailAmount);
            logger.info("最后一个分批还款计划，lastPayRepayId=" + lastPayRepayId + "，已还款金额：" + leftBatchMoney);
            // 修改还款计划状态、已还款金额
            BwRepaymentPlan updatePlan = new BwRepaymentPlan();
            updatePlan.setId(lastPayRepayId);
            updatePlan.setAlreadyRepayMoney(leftBatchMoney);
            updatePlan.setUpdateTime(new Date());
            bwRepaymentPlanService.update(updatePlan);
        }
    }

    private NotifyNotice checkSign(NotifyResult notifyResult) {
        NotifyNotice notice = new NotifyNotice();
        if (CommUtils.isNull(notifyResult)) {
            logger.info("==================异步通知为空========================");
            notice.setRet_code("101");
            notice.setRet_msg("异步通知为空");
            return notice;
        } else {
            logger.info("==================异步通知验签开始========================");
            Map<String, String> map = new HashMap<>();
            map.put("oid_partner", notifyResult.getOid_partner());
            map.put("sign_type", notifyResult.getSign_type());
            map.put("dt_order", notifyResult.getDt_order());
            map.put("no_order", notifyResult.getNo_order());
            map.put("oid_paybill", notifyResult.getOid_paybill());
            map.put("money_order", notifyResult.getMoney_order());
            map.put("result_pay", notifyResult.getResult_pay());
            map.put("settle_date", notifyResult.getSettle_date());
            map.put("info_order", notifyResult.getInfo_order());
            map.put("pay_type", notifyResult.getPay_type());
            map.put("bank_code", notifyResult.getBank_code());
            map.put("no_agree", notifyResult.getNo_agree());
            map.put("id_type", notifyResult.getId_type());
            map.put("id_no", notifyResult.getId_no());
            map.put("acct_name", notifyResult.getAcct_name());
            map.put("card_no", notifyResult.getCard_no());

            if (!testBool) {
                // TODO 连连验签测试不执行
                String osign = RSAUtil.sortParams(map);
                if (!RSAUtil.checksign(SystemConstant.YT_PUB_KEY, osign, notifyResult.getSign())) {// 未通过验签
                    logger.info("==================异步通知未通过========================");
                    notice.setRet_code("101");
                    notice.setRet_msg("验签未通过");
                    return notice;
                }
            }
            logger.info("==================异步通知通过========================");
        }
        notice.setRet_code("0000");
        notice.setRet_msg("SUCCESS");
        return notice;
    }

    @Override
    public void requestBaofooNotifyTest(Long orderId, String transId, double payAmount) {
        logger.info("=================orderId：" + orderId + "开始请求宝付回调=================");
        try {
            String requestUrl = "http://106.14.238.126:8902/beadwalletafterloan/baofoo/notifyTest.do";
            logger.info("=================宝付模拟回调请求链接：" + requestUrl + "?transId=" + transId + "&amount=" + payAmount
                    + "，orderId：" + orderId);
            String str = HttpRequest.doPost(requestUrl, "transId=" + transId + "&amount=" + payAmount);
            logger.info("=================orderId：" + orderId + "宝付回调返回：" + str + "=================");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public AppResponseResult updateAndKouDaiWithhold(RepaymentDto repaymentDto) {
        Long orderId = repaymentDto.getOrderId();
        AppResponseResult result = new AppResponseResult();
        Double payMoney = repaymentDto.getAmount();
        repaymentDto.setPayChannel(7);
        Boolean useCoupon = repaymentDto.getUseCoupon();
        Integer payType = repaymentDto.getType();
        if (RedisUtils.hexists(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString())) {
            result.setCode("106");
            result.setMsg("此工单还款正在处理中..");
            return result;
        }
        if (payType == 1) {// 还款
            result = productService.calcRepaymentCost(orderId, useCoupon, payMoney);
            Map<String, Object> resultMap = (Map<String, Object>) result.getResult();
            if (resultMap != null) {
                Double totalUseCouponsAmount = NumberUtil.parseDouble(resultMap.get("totalUseCouponsAmount") + "",
                        0.0);
                Double totalAmount = NumberUtil.parseDouble(resultMap.get("totalAmount") + "", 0.0);
                if (useCoupon && payMoney >= totalAmount) {// 使用优惠券且全额还款
                    payMoney = totalUseCouponsAmount;
                    useCoupon = true;
                } else {
                    useCoupon = false;
                }
            }
            logger.info("【AppPaymentService.kouDaiWithhold】还款orderId:" + orderId + ",money=" + payMoney + ",useCoupon="
                    + useCoupon + ",resultMap=" + resultMap);
        } else if (payType == 2) {// 展期
            result = productService.calcZhanQiCost(orderId);
            LoanInfo loanInfo = (LoanInfo) result.getResult();
            if (loanInfo != null) {
                payMoney = Double.parseDouble(loanInfo.getAmt());
            }
            logger.info("【AppPaymentService.kouDaiWithhold】展期orderId:" + orderId + ",loanInfo="
                    + JSON.toJSONString(loanInfo));
        }
        if (!"000".equals(result.getCode())) {
            return result;
        }
        // 处理中保存
        RedisUtils.hset(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString(), CommUtils.convertDateToString(new Date(), SystemConstant.YMD_HMS));
        Map<String, Object> capitalKoudaiMap = bwOrderService.findCapitalKoudaiD(orderId);
        if (capitalKoudaiMap != null) {
            BwCapitalWithhold bwCapitalWithhold = new BwCapitalWithhold();
            bwCapitalWithhold.setCapitalId(2);
            bwCapitalWithhold.setCardNo(CommUtils.toString(capitalKoudaiMap.get("card_no")));
            bwCapitalWithhold.setOrderId(Long.parseLong(CommUtils.toString(capitalKoudaiMap.get("id"))));
            bwCapitalWithhold.setOrderNo(CommUtils.toString(capitalKoudaiMap.get("order_no")));
            bwCapitalWithhold.setCreateTime(new Date());
            bwCapitalWithhold.setPushStatus(0);
            bwCapitalWithhold.setMoney(new BigDecimal(payMoney));
            bwCapitalWithhold.setPayType(payType);
            bwCapitalWithhold.setTerminalType(repaymentDto.getTerminalType());
            bwCapitalWithholdService.save(bwCapitalWithhold);
            capitalKoudaiMap.put("push_id", capitalKoudaiMap.get("order_no") + "K" + bwCapitalWithhold.getId());
            capitalKoudaiMap.put("money", payMoney);
            logger.info("【AppPaymentService.kouDaiWithhold】orderId:" + orderId + "capitalKoudaiMap:" + JSON.toJSONString(capitalKoudaiMap));
            if (testBool) {
                bwCapitalWithhold.setMoney(new BigDecimal(0.01));
                capitalKoudaiMap.put("money", "0.01");
            }
            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(NumberUtil.parseLong(capitalKoudaiMap.get("borrower_id") + "", 0L));
            if (bwBankCard != null && StringUtils.isNotEmpty(bwBankCard.getPhone())) {
                capitalKoudaiMap.put("phone", bwBankCard.getPhone());
            }

            String resultJsonStr = CapitalKoudaiService.withholdPush(JSON.toJSONString(capitalKoudaiMap));
            logger.info("【AppPaymentService.updateAndKouDaiWithhold】orderId:" + orderId + "口袋代扣请求返回:" + resultJsonStr);
            if (StringUtils.isNotEmpty(resultJsonStr)) {
                JSONObject resultJson = JSON.parseObject(resultJsonStr);
                if ("0".equals(resultJson.getString("code"))) {// 成功
                    RedisUtils.hset("pay_info", orderId.toString(), JSON.toJSONString(repaymentDto));
                    result.setCode("000");
                    result.setMsg("SUCCESS");
                } else {
                    RedisUtils.hdel(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString());
                    result.setCode("003");
                    result.setMsg("FAIL");
                }
            } else {// 调用接口超时或失败
                RedisUtils.hdel(RedisKeyConstant.KOUDAI_PROCESS, orderId.toString());
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(repaymentDto));
                jsonObject.put("exceptionMsg", "接口调用异常");
                RedisUtils.hset("pay_info", orderId.toString(), jsonObject.toJSONString());
                result.setCode("001");
                result.setMsg("FAIL");

            }
        }
        return result;
    }
}