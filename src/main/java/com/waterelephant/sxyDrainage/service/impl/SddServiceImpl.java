//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.drainage.entity.common.PushOrderRequest;
//import com.waterelephant.entity.*;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.impl.BwAdjunctServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageEnum;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.shandiandai.*;
//import com.waterelephant.sxyDrainage.entity.shandiandai.pushorder.*;
//import com.waterelephant.sxyDrainage.service.AsyncSddTask;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.SddService;
//import com.waterelephant.sxyDrainage.utils.shandiandai.SddConstant;
//import com.waterelephant.sxyDrainage.utils.shandiandai.SddUtil;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DoubleUtil;
//import com.waterelephant.utils.SystemConstant;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/5
// * @since JDK 1.8
// */
//@Service
//public class SddServiceImpl implements SddService {
//
//    private Logger logger = Logger.getLogger(SddServiceImpl.class);
//
//    private final static String CHANNEL_SX = SddConstant.CHANNEL_SX;
//    private final static String PRODUCT_ID = SddConstant.PRODUCT_ID;
//    /** 期数 */
//    private final static Integer LOAN_MATURITY = 4;
//
//
//    @Autowired
//    private CommonService commonService;
//    @Autowired
//    private BwAdjunctServiceImpl bwAdjunctService;
//    @Autowired
//    private IBwRepaymentPlanService bwRepaymentPlanService;
//    @Autowired
//    private BwOverdueRecordService bwOverdueRecordService;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private BwProductDictionaryService bwProductDictionaryService;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//
//    @Autowired
//    private AsyncSddTask asyncSddTask;
//
//
//    /**
//     * 撞库
//     *
//     * @param sddFilterReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    @Override
//    public SddResponse checkUser(SddFilterReq sddFilterReq, Long sessionId) {
//        logger.info("=====闪电贷>>>" + sessionId + "开始SddServiceImpl checkUser()撞库");
//
//        // 获取参数 姓名、手机、身份证
//        String name = sddFilterReq.getName();
//        String phone = sddFilterReq.getPhone();
//        String idCard = sddFilterReq.getIdCard();
//
//        // 开始过滤
//        DrainageRsp drainageRsp = commonService.checkUser(sessionId, name, phone, idCard);
//
//        // 2001 命中黑名单规则 2002 存在进行中的订单 2003 命中被拒规则 2004 用户年龄超限 1002 参数为空 0000 成功
//
//        SddFilterResp sddFilterResp = new SddFilterResp();
//
//        if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//            // 可贷款
//            sddFilterResp.setType(1);
//        } else {
//            // 不可贷款
//            sddFilterResp.setType(2);
//            sddFilterResp.setDetails(drainageRsp.getMessage());
//        }
//
//        return new SddResponse(SddResponse.SUCCESS_CODE, "success", JSON.toJSONString(sddFilterResp));
//    }
//
//    /**
//     * 预绑卡
//     *
//     * @param sddBindCardReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    @Override
//    public SddResponse saveBankCard(SddBindCardReq sddBindCardReq, Long sessionId) {
//        logger.info("=====闪电贷>>>" + sessionId + "开始SddServiceImpl saveBankCard()预绑卡");
//
//        SddBindCardResp sddBindCardResp = new SddBindCardResp();
//
//        // 获取手机号
//        String phone = sddBindCardReq.getPhone();
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setIdCardNo(sddBindCardReq.getIdCard());
//        drainageBindCardVO.setBankCardNo(sddBindCardReq.getBankcard());
//        drainageBindCardVO.setName(sddBindCardReq.getName());
//        drainageBindCardVO.setRegPhone(sddBindCardReq.getRetainPhone());
//        drainageBindCardVO.setBankCode(SddUtil.getBankCode(sddBindCardReq.getAbbreviation()));
//        drainageBindCardVO.setBindType("1");
//        drainageBindCardVO.setPhone(phone);
//        drainageBindCardVO.setChannelId(Integer.valueOf(CHANNEL_SX));
//        drainageBindCardVO.setNotify(false);
//        logger.info("闪电贷>>>" + sessionId + "预绑卡前数据：" + JSON.toJSONString(drainageBindCardVO) + ">>>>>");
//
//        // 开始预绑卡
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//
//        if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//            logger.info(sessionId + "预绑卡成功，手机号[" + phone + "]");
//            sddBindCardResp.setStatus(1);
//        } else {
//            logger.info(sessionId + "预绑卡失败，手机号[" + phone + "]");
//
//            sddBindCardResp.setStatus(2);
//            sddBindCardResp.setDetails(drainageRsp.getMessage());
//        }
//        return new SddResponse(SddResponse.SUCCESS_CODE, "success", JSON.toJSONString(sddBindCardResp));
//    }
//
//    /**
//     * 验证码绑卡
//     *
//     * @param sddBindcardVerifReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    @Override
//    public SddResponse saveBankCardCode(SddBindcardVerifReq sddBindcardVerifReq, Long sessionId) {
//        logger.info("=====闪电贷>>>" + sessionId + "开始SddServiceImpl saveBankCardCode()验证码绑卡");
//
//        SddBindcardVerifResp sddBindcardVerifResp = new SddBindcardVerifResp();
//
//        // 获取手机号
//        String phone = sddBindcardVerifReq.getPhone();
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setIdCardNo(sddBindcardVerifReq.getIdCard());
//        drainageBindCardVO.setName(sddBindcardVerifReq.getName());
//        drainageBindCardVO.setBindType("1");
//        drainageBindCardVO.setPhone(phone);
//        drainageBindCardVO.setChannelId(Integer.valueOf(CHANNEL_SX));
//        drainageBindCardVO.setNotify(false);
//        drainageBindCardVO.setVerifyCode(sddBindcardVerifReq.getVerifCode());
//
//        // 开始验证码绑卡
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//
//        if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//            logger.info(sessionId + ">>>绑卡成功，手机号[" + phone + "]");
//            sddBindcardVerifResp.setStatus(1);
//        } else {
//            logger.info(sessionId + ">>>绑卡失败，手机号[" + phone + "]");
//            sddBindcardVerifResp.setStatus(2);
//            sddBindcardVerifResp.setDetails(drainageRsp.getMessage());
//        }
//        return new SddResponse(SddResponse.SUCCESS_CODE, "success", JSON.toJSONString(sddBindcardVerifResp));
//    }
//
//
//    /**
//     * 进件
//     *
//     * @param sddPushOrderReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    @Override
//    public SddResponse saveOrder(SddPushOrderReq sddPushOrderReq, Long sessionId) {
//        logger.info("=====闪电贷>>>" + sessionId + "开始SddServiceImpl saveOrder()进件,三方工单号[" + sddPushOrderReq.getLoanId() + "]");
//
//        // 基本信息
//        SddPersonalInfo personalInfo = sddPushOrderReq.getPersonalInfo();
//        if (CommUtils.isNull(personalInfo)) {
//            logger.info("闪电贷>>>" + sessionId + "用户基本信息为空>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "用户基本信息为空", "{}");
//        }
//        // 手机信息
//        SddmobileData mobileData = sddPushOrderReq.getMobileData();
//        if (CommUtils.isNull(mobileData)) {
//            logger.info("闪电贷>>>" + sessionId + "手机信息为空>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "手机信息为空", "{}");
//        }
//        // 认证信息
//        SddOcr ocr = sddPushOrderReq.getOcr();
//        if (CommUtils.isNull(ocr)) {
//            logger.info("闪电贷>>>" + sessionId + "认证信息为空>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "认证信息为空", "{}");
//        }
//        // 运营商报告
//        SddCarrierInfo carrierInfo = sddPushOrderReq.getCarrierInfo();
//        if (CommUtils.isNull(carrierInfo)) {
//            logger.info("闪电贷>>>" + sessionId + "运营商报告为空>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "运营商报告为空", "{}");
//        }
//
//
//        // 身份证json对象
//        SddIdCard idCard = personalInfo.getIdCard();
//        if (CommUtils.isNull(idCard)) {
//            logger.info("闪电贷>>>" + sessionId + "身份证信息为空>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "用户身份证信息为空", "{}");
//        }
//        // 三方工单
//        String loanId = sddPushOrderReq.getLoanId();
//        // 用户姓名
//        String name = personalInfo.getName();
//        // 用户手机号
//        String phone = personalInfo.getPhone();
//        // 身份证号
//        String no = idCard.getNo();
//        // 用户预期借款金额
//        String loanAmount = sddPushOrderReq.getLoanAmount();
//        // 预借期数
//        String loanMaturity = sddPushOrderReq.getLoanMaturity();
//
//        if (StringUtils.isBlank(loanId) || StringUtils.isBlank(name) || StringUtils.isBlank(phone) || StringUtils.isBlank(no) || StringUtils.isBlank(loanAmount) || StringUtils.isBlank(loanMaturity)) {
//            logger.info("闪电贷>>>" + sessionId + "必要数据为空,工单号[" + loanId + "]>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "必要数据为空", "{}");
//        }
//        // 根据身份证查看工单状态
//        boolean progressOrder;
//
//        // 调用此方法，方法自定义抛出的异常
//        try {
//            progressOrder = thirdCommonService.checkUserAccountProgressOrder(sessionId, no);
//        } catch (Exception e) {
//            progressOrder = true;
//            logger.error("thirdCommonService.checkUserAccountProgressOrder()抛出异常", e);
//        }
//
//        if (progressOrder) {
//            logger.info("闪电贷>>>" + sessionId + "存在进行中的工单,工单号[" + loanId + "]>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "存在进行中的工单", "{}");
//        }
//        // 渠道号
//        Integer channel = Integer.valueOf(CHANNEL_SX);
//
//        logger.info("闪电贷>>>" + sessionId + "开始处理进件数据,工单号[" + loanId + "]>>>>>");
//        PushOrderRequest pushOrderRequest = new PushOrderRequest();
//        pushOrderRequest.setThirdOrderNo(loanId);
//        pushOrderRequest.setUserName(name);
//        pushOrderRequest.setPhone(phone);
//        pushOrderRequest.setIdCard(no);
//        pushOrderRequest.setChannelId(channel);
//        // 芝麻认证
//        SddZhiMa zhima = sddPushOrderReq.getZhima();
//        if (!CommUtils.isNull(zhima) && StringUtils.isBlank(zhima.getScore())) {
//            Integer score = Integer.valueOf(zhima.getScore());
//            pushOrderRequest.setSesameScore(score);
//        }
//        // 用户预期借款金额
//        pushOrderRequest.setExpectMoney(Double.valueOf(loanAmount));
//        // 预借期数 设置固定值4 todo
//        pushOrderRequest.setExpectNumber(LOAN_MATURITY);
//        // 身份证正面url
//        String frontUrl = ocr.getFrontUrl();
//        // 身份证反面url
//        String backUrl = ocr.getBackUrl();
//        // 身份证手持地址url
//        String handerUrl = ocr.getHanderImage();
//        if (StringUtils.isBlank(frontUrl) && StringUtils.isBlank(backUrl) && StringUtils.isBlank(handerUrl)) {
//            logger.info("闪电贷>>>" + sessionId + "身份认证照片不存在,工单号[" + loanId + "]>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "身份认证照片不存在", "{}");
//        }
//        pushOrderRequest.setIdCardFrontImage(frontUrl);
//        pushOrderRequest.setIdCardBackImage(backUrl);
//        pushOrderRequest.setIdCardHanderImage(handerUrl);
//
//        // 工作信息
//        BwWorkInfo bwWorkInfo = new BwWorkInfo();
//        bwWorkInfo.setComName(personalInfo.getCompanyName());
//        bwWorkInfo.setIndustry(SddUtil.getJob(personalInfo.getJob()));
//        bwWorkInfo.setWorkYears(personalInfo.getWorkYear() + "");
//        pushOrderRequest.setBwWorkInfo(bwWorkInfo);
//
//        // 通讯录
//        List<SddContact> contacts = mobileData.getContacts();
//        if (CommUtils.isNull(contacts)) {
//            logger.info("闪电贷>>>" + sessionId + "通讯录不存在,工单号[" + loanId + "]>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "通讯录不存在", "{}");
//        }
//        List<BwContactList> bwContactList = getContact(contacts);
//        pushOrderRequest.setBwContactList(bwContactList);
//
//
//        // 运营商
//        SddBasic basic = carrierInfo.getBasic();
//        if (CommUtils.isNull(basic)) {
//            logger.info("闪电贷>>>" + sessionId + "运营商不存在,工单号[" + loanId + "]>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "运营商不存在", "{}");
//        }
//        BwOperateBasic operateBasic = getOperateBasic(basic);
//        pushOrderRequest.setBwOperateBasic(operateBasic);
//
//        // 运营商-通话记录
//        if (carrierInfo.getMessages() == null || CommUtils.isNull(carrierInfo.getMessages().getCalls())) {
//            logger.info("闪电贷>>>" + sessionId + "运营商通话记录不存在,工单号[" + loanId + "]>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "运营商通话记录不存在", "{}");
//        }
//        List<BwOperateVoice> operateVoices = getOperateVoice(carrierInfo.getMessages().getCalls());
//        pushOrderRequest.setBwOperateVoiceList(operateVoices);
//
//
//        // 身份证信息
//        BwIdentityCard2 bwIdentityCard2 = new BwIdentityCard2();
//        bwIdentityCard2.setAddress(ocr.getAddress());
//        bwIdentityCard2.setGender(idCard.getGender() == 2 ? "女" : "男");
//        bwIdentityCard2.setIdCardNumber(ocr.getIdCard());
//        bwIdentityCard2.setName(ocr.getName());
//        bwIdentityCard2.setIssuedBy(ocr.getIssuer());
//
//        String validDuration = ocr.getValidDuration();
//        if (StringUtils.isNotBlank(validDuration) && validDuration.length() == 17) {
//            validDuration = validDuration.substring(0, 4) + "." + validDuration.substring(4, 6) + "." + validDuration.substring(6, 13) + "." + validDuration.substring(13, 15) + "."
//                    + validDuration.substring(15);
//        } else {
//            logger.info("闪电贷>>>" + sessionId + "无法处理身份证有效期[" + validDuration + "],工单号[" + loanId + "]>>>>>");
//        }
//        bwIdentityCard2.setValidDate(validDuration);
//        bwIdentityCard2.setRace(ocr.getNation());
//        pushOrderRequest.setBwIdentityCard(bwIdentityCard2);
//
//        // 亲属联系人
//        BwPersonInfo bwPersonInfo = getPersonInfo(personalInfo);
//        pushOrderRequest.setBwPersonInfo(bwPersonInfo);
//
//        // 设置状态
//        pushOrderRequest.setOrderStatus(2);
//
//        // TODO 异步进件
//        asyncSddTask.saveOrder(sessionId, pushOrderRequest, carrierInfo, sddPushOrderReq.getCreditCard(), mobileData);
//
//        return new SddResponse(SddResponse.SUCCESS_CODE, "接收进件数据成功", "{}");
//    }
//
//    /**
//     * 借款合同
//     *
//     * @param sddCommonReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    @Override
//    public SddResponse getContract(SddCommonReq sddCommonReq, Long sessionId) {
//        logger.info("=====闪电贷>>>" + sessionId + "开始SddServiceImpl getContract()借款合同");
//
//        String loanId = sddCommonReq.getLoanId();
//
//        // 查询订单
//        BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(loanId, CHANNEL_SX);
//        if (bwOrder == null) {
//            logger.info("闪电贷>>>" + sessionId + "订单不存在>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "未查到订单记录", "{}");
//        }
//
//        // 服务合同
//        BwAdjunct bwAdjunct = new BwAdjunct();
//        bwAdjunct.setOrderId(bwOrder.getId());
//        bwAdjunct.setAdjunctType(29);
//        bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
//        if (CommUtils.isNull(bwAdjunct)) {
//            logger.info("闪电贷>>>" + sessionId + "合同不存在>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "未查到相关合同", "{}");
//        }
//        String contractUrl = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
//
//        // 封装合同响应
//        SddContractResp sddContractResp = new SddContractResp();
//        sddContractResp.setUrl(contractUrl);
//
//        return new SddResponse(SddResponse.SUCCESS_CODE, "success", JSON.toJSONString(sddContractResp));
//    }
//
//    /**
//     * 订单信息查询
//     *
//     * @param sddCommonReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    @Override
//    public SddResponse getOrderInfo(SddCommonReq sddCommonReq, Long sessionId) {
//        logger.info("=====闪电贷>>>" + sessionId + "开始SddServiceImpl getOrderInfo()订单信息查询");
//
//        String loanId = sddCommonReq.getLoanId();
//
//        // 查询订单
//        BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(loanId, CHANNEL_SX);
//        if (bwOrder == null) {
//            logger.info("闪电贷>>>" + sessionId + "订单不存在>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "未查到订单记录", "{}");
//        }
//
//        // 查询对应的产品
//        BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(bwOrder.getProductId());
//        if (bwProductDictionary == null) {
//            logger.info("闪电贷>>>" + sessionId + "产品记录不存在>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "未查到产品记录", "{}");
//        }
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        // 订单号
//        Long orderId = bwOrder.getId();
//        // 我方订单状态
//        Long statusId = bwOrder.getStatusId();
//
//        // 订单信息响应
//        SddRepayDetailResp sddRepayDetailResp = new SddRepayDetailResp();
//        // 状态（转换）
//        sddRepayDetailResp.setStatus(SddUtil.statusConvert(statusId));
//        // 日利率
//        sddRepayDetailResp.setDayRate((DoubleUtil.div(DoubleUtil.mul(bwProductDictionary.getInterestRate(), 100), 7.0, 5)) + "");
//
//
//
//        if (statusId == 9 || statusId == 6 || statusId == 13) {
//            // 查询还款计划
//            List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(orderId);
//            if (CommUtils.isNull(bwRepaymentPlans)) {
//                logger.info("闪电贷>>>" + sessionId + "还款计划不存在>>>>>");
//                return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "未查到还款计划", "{}");
//            }
//
//            // 当前第几期
//            sddRepayDetailResp.setCurrentPeriod(getPlanNumber(bwRepaymentPlans));
//
//            List<SddRefundPlan> refundPlan = new ArrayList<>(4);
//            // 遍历还款计划
//            for (BwRepaymentPlan plan : bwRepaymentPlans) {
//
//                SddRefundPlan sddRefundPlan = new SddRefundPlan();
//                // 期数
//                sddRefundPlan.setPeriodNo(plan.getNumber());
//                // 还款状态
//                Integer repayStatus = plan.getRepayStatus();
//                sddRefundPlan.setStatus(SddUtil.planConvert(repayStatus));
//
//                // 应还款额度
//                sddRefundPlan.setPlanAmount(plan.getRepayMoney() + "");
//                // 应还款时间
//                sddRefundPlan.setPlanTime(sdf.format(plan.getRepayTime()));
//                // 应还款利息费
//                sddRefundPlan.setPlanInterest(plan.getRepayAccrualMoney() + "");
//                // 当期本金
//                sddRefundPlan.setPrincipal(plan.getRepayCorpusMoney() + "");
//
//                // 已还款
//                if (repayStatus == 2) {
//                    // 已还款额度
//                    sddRefundPlan.setRefundAmount(plan.getAlreadyRepayMoney() + "");
//                    // 已还款时间
//                    sddRefundPlan.setRefundTime(sdf.format(plan.getUpdateTime()));
//                }
//
//                BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//                bwOverdueRecord.setRepayId(plan.getId());
//                bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//                if (bwOverdueRecord != null) {
//                    // 逾期费用
//                    sddRefundPlan.setOverdueFee(bwOverdueRecord.getOverdueAccrualMoney() + "");
//                    // 逾期天数
//                    sddRefundPlan.setOverdueDay(bwOverdueRecord.getOverdueDay() + "");
//                }
//
//                // 添加贷款时间
//                sddRepayDetailResp.setLoanTime(sdf.format(plan.getCreateTime()));
//                // 添加到还款计划中
//                refundPlan.add(sddRefundPlan);
//            }
//            // 添加还款计划
//            sddRepayDetailResp.setRefundPlan(refundPlan);
//        }
//
//        // 剩余还款额度 非必传
//        // sddRepayDetailResp.setRemainAmount("");
//        // 剩余还款期数 非必传
//        // sddRepayDetailResp.setRemainMaturity("");
//
//        if (statusId > 2 & statusId != 7 && statusId != 8) {
//            // 本金
//            Double borrowAmount = bwOrder.getBorrowAmount();
//            sddRepayDetailResp.setPrincipal(borrowAmount + "");
//            // 利息
//            Double interestRate = bwProductDictionary.getInterestRate();
//            Double totalInterest = SddUtil.calculateRepayMoney(borrowAmount, 1, interestRate) + SddUtil.calculateRepayMoney(borrowAmount, 2, interestRate)
//                    + SddUtil.calculateRepayMoney(borrowAmount, 3, interestRate) + SddUtil.calculateRepayMoney(borrowAmount, 4, interestRate);
//            sddRepayDetailResp.setInterest(totalInterest + "");
//        }
//
//        // 审批失败原因，或放款失败原因，或还款失败原因
//        if (statusId == 7 || statusId == 8) {
//            // 审核失败
//            sddRepayDetailResp.setReason("系统评分不足！");
//            sddRepayDetailResp.setPrincipal("");
//            sddRepayDetailResp.setInterest("");
//        }
//
//        return new SddResponse(SddResponse.SUCCESS_CODE, "success", JSON.toJSONString(sddRepayDetailResp));
//    }
//
//    /**
//     * 试算
//     *
//     * @param sddLoanCalculateReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    @Override
//    public SddResponse trial(SddLoanCalculateReq sddLoanCalculateReq, Long sessionId) {
//        logger.info("=====闪电贷>>>" + sessionId + "开始SddServiceImpl trial()试算");
//
//        BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(Integer.valueOf(PRODUCT_ID));
//        if (bwProductDictionary == null) {
//            logger.info("闪电贷>>>" + sessionId + "产品不存在>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "未查到贷款产品", "{}");
//        }
//        // 利息
//        Double borrowAmount = sddLoanCalculateReq.getLoanAmount() == null ? 0 : Double.valueOf(sddLoanCalculateReq.getLoanAmount());
//        Double interestRate = bwProductDictionary.getInterestRate();
//        Double totalInterest = SddUtil.calculateRepayMoney(borrowAmount, 1, interestRate) + SddUtil.calculateRepayMoney(borrowAmount, 2, interestRate)
//                + SddUtil.calculateRepayMoney(borrowAmount, 3, interestRate) + SddUtil.calculateRepayMoney(borrowAmount, 4, interestRate);
//
//        // 试算响应
//        SddLoanCalculateResp sddLoanCalculateResp = new SddLoanCalculateResp();
//        sddLoanCalculateResp.setInterest(totalInterest + "");
//
//        return new SddResponse(SddResponse.SUCCESS_CODE, "success", JSON.toJSONString(sddLoanCalculateResp));
//    }
//
//    /**
//     * 主动还款
//     *
//     * @param sddCommonReq 请求
//     * @param sessionId 时间戳标识
//     * @return SddResponse
//     */
//    @Override
//    public SddResponse updateRepayment(SddCommonReq sddCommonReq, Long sessionId) {
//        logger.info("=====闪电贷>>>" + sessionId + "开始SddServiceImpl repayment()主动还款");
//
//        // 获取参数
//        String loanId = sddCommonReq.getLoanId();
//        String periodNos = sddCommonReq.getPeriodNos();
//        Integer sddNumber = Integer.valueOf(periodNos);
//
//        // 校验数据
//        if (CommUtils.isNull(loanId) || CommUtils.isNull(periodNos)) {
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "订单号或期数为空", "{}");
//        }
//
//        // 查询订单
//        BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(loanId, CHANNEL_SX);
//        if (bwOrder == null) {
//            logger.info("闪电贷>>>" + sessionId + "订单不存在>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "未查到订单记录", "{}");
//        }
//        // 查询还款计划
//        List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//        if (CommUtils.isNull(bwRepaymentPlans)) {
//            logger.info("闪电贷>>>" + sessionId + "还款计划不存在>>>>>");
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "未查到还款计划", "{}");
//        }
//
//        // 防止重复还款 todo 备用
//        for (BwRepaymentPlan plan : bwRepaymentPlans) {
//            if (sddNumber.equals(plan.getNumber())) {
//                if (plan.getRepayStatus() == 2) {
//                    logger.info(sessionId + "请勿重复还款");
//                    return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "请勿重复还款", "{}");
//                }
//            }
//            if (sddNumber > plan.getNumber()) {
//                if (plan.getRepayStatus() != 2) {
//                    logger.info(sessionId + "请依次还款");
//                    return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "请依次还款", "{}");
//                }
//            }
//        }
//
//        // 提交还款
//        DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, loanId);
//        logger.info(loanId + ":返回闪电贷还款结果" + JSONObject.toJSONString(drainageRsp));
//        if (drainageRsp != null) {
//            if (DrainageEnum.CODE_EXCEPTION.getCode().equals(drainageRsp.getCode())) {
//                //系统异常
//                return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, drainageRsp.getMessage(), "{}");
//            } else {
//                //请求成功
//                return new SddResponse(SddResponse.SUCCESS_CODE, drainageRsp.getMessage(), "{}");
//            }
//        } else {
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "支付接口返回信息为空", "{}");
//        }
//    }
//
//    /**
//     * 处理通讯录数据
//     *
//     * @param sddContactList 接收的通讯录数据
//     * @return 返回数据
//     */
//    private List<BwContactList> getContact(List<SddContact> sddContactList) {
//        // 我方通讯录
//        List<BwContactList> bwContactList = new ArrayList<>();
//        // 遍历进件通讯录
//        for (SddContact sddContact : sddContactList) {
//            BwContactList contactList = new BwContactList();
//            String name = sddContact.getName();
//            String phone = sddContact.getPhone();
//            if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(phone)) {
//                contactList.setName(name);
//                contactList.setPhone(phone);
//                // 存储
//                bwContactList.add(contactList);
//            }
//        }
//        return bwContactList;
//    }
//
//    /**
//     * 处理通话记录
//     * @return 返回数据
//     */
//    private List<BwOperateVoice> getOperateVoice(List<SddCall> sddCalls) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        // 我方通话记录
//        List<BwOperateVoice> bwOperateVoiceList = new ArrayList<>();
//        // 遍历进件通话记录
//        for (SddCall call : sddCalls) {
//            BwOperateVoice bwOperateVoice = new BwOperateVoice();
//            bwOperateVoice.setTrade_type("本地".equals(call.getCallType()) ? 1 : 2);
//            bwOperateVoice.setTrade_time(call.getUseTime() + "");
//            bwOperateVoice.setCall_time(sdf.format(call.getStartTime()));
//            bwOperateVoice.setTrade_addr(call.getPlace());
//            if (call.getOtherCallPhone() == null || call.getOtherCallPhone().length() > 19) {
//                continue;
//            }
//            bwOperateVoice.setReceive_phone(call.getOtherCallPhone());
//            bwOperateVoice.setCall_type(getCallType(call.getInitType()));
//            bwOperateVoiceList.add(bwOperateVoice);
//        }
//        return bwOperateVoiceList;
//    }
//
//    /** 通话记录 主被叫转换 */
//    private Integer getCallType(String initType) {
//        if ("主叫".equals(initType)) {
//            return 1;
//        }
//        if ("被叫".equals(initType)) {
//            return 2;
//        }
//        return null;
//    }
//
//    /**
//     * 处理运营商
//     *
//     * @param basic 进件运营商
//     * @return 返回数据
//     */
//    private BwOperateBasic getOperateBasic(SddBasic basic) {
//        // 我方运营商
//        BwOperateBasic bwOperateBasic = new BwOperateBasic();
//
//        bwOperateBasic.setUserSource(basic.getCarrier());
//        bwOperateBasic.setIdCard(basic.getIdCard());
//        bwOperateBasic.setAddr(basic.getLocation());
//        bwOperateBasic.setRealName(basic.getName());
//        bwOperateBasic.setPhoneRemain(basic.getBalance());
//        bwOperateBasic.setPhone(basic.getPhone());
//        bwOperateBasic.setRegTime(basic.getRegTime());
//
//        return bwOperateBasic;
//    }
//
//    /**
//     * 处理联系人
//     *
//     * @param sddPersonalInfo 进件个人数据
//     * @return 返回数据
//     */
//    private BwPersonInfo getPersonInfo(SddPersonalInfo sddPersonalInfo) {
//        BwPersonInfo bwPersonInfo = new BwPersonInfo();
//
//        // 地址
//        SddLocation location = sddPersonalInfo.getLocation();
//        if (!CommUtils.isNull(location)) {
//            bwPersonInfo.setAddress(location.getProvince() + location.getCity() + location.getArea() + location.getAddress());
//        }
//        // 联系人1
//        bwPersonInfo.setRelationName(sddPersonalInfo.getFirstEmergencyName());
//        bwPersonInfo.setRelationPhone(sddPersonalInfo.getFirstEmergencyPhone());
//        // 联系人2
//        bwPersonInfo.setUnrelationName(sddPersonalInfo.getSecondEmergencyName());
//        bwPersonInfo.setUnrelationPhone(sddPersonalInfo.getSecondEmergencyPhone());
//        // 联系人3
//        bwPersonInfo.setColleagueName(sddPersonalInfo.getWorkmateName());
//        bwPersonInfo.setColleaguePhone(sddPersonalInfo.getWorkmatePhone());
//        // 联系人4
//        bwPersonInfo.setFriend1Name(sddPersonalInfo.getFriendFirstName());
//        bwPersonInfo.setFriend1Phone(sddPersonalInfo.getFriendFirstPhone());
//        // 联系人5
//        bwPersonInfo.setFriend2Name(sddPersonalInfo.getFriendSecondName());
//        bwPersonInfo.setFriend2Phone(sddPersonalInfo.getFriendSecondPhone());
//
//        // 婚姻状态
//        int marriage = sddPersonalInfo.getMarriage();
//        if (marriage == 1) {
//            bwPersonInfo.setMarryStatus(1);
//        } else if (marriage == 2) {
//            bwPersonInfo.setMarryStatus(2);
//        }
//
//        // QQ
//        bwPersonInfo.setQqchat(sddPersonalInfo.getQq());
//        // 微信号
//        bwPersonInfo.setWechat(sddPersonalInfo.getWechatNumber());
//        // email
//        bwPersonInfo.setEmail(sddPersonalInfo.getEmail());
//
//        return bwPersonInfo;
//    }
//
//    /** 获取当前期数 */
//    private Integer getPlanNumber(List<BwRepaymentPlan> bwRepaymentPlans) {
//        for (int i = bwRepaymentPlans.size() - 1; i >= 0; i--) {
//            BwRepaymentPlan plan = bwRepaymentPlans.get(i);
//            // 第一次遍历到 未还款 1，就是当前期数
//            if (plan.getRepayStatus() == 1) {
//                return plan.getNumber();
//            }
//        }
//        return null;
//    }
//}
