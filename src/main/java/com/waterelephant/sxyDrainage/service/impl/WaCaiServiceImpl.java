//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.entity.*;
//import com.waterelephant.service.*;
//import com.waterelephant.service.impl.BwAdjunctServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageEnum;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.wacai.*;
//import com.waterelephant.sxyDrainage.entity.wacai.wcorder.WaCaiContact;
//import com.waterelephant.sxyDrainage.entity.wacai.wcorder.WaCaiRealName;
//import com.waterelephant.sxyDrainage.entity.wacai.wcorder.WaCaiUserInfo;
//import com.waterelephant.sxyDrainage.service.AsyncWaCaiService;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.WaCaiService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.shandiandai.SddUtil;
//import com.waterelephant.sxyDrainage.utils.wacaiutils.WaCaiConstant;
//import com.waterelephant.sxyDrainage.utils.wacaiutils.WaCaiUtil;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.SystemConstant;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import tk.mybatis.mapper.entity.Example;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/30
// * @since JDK 1.8
// */
//@Service
//public class WaCaiServiceImpl implements WaCaiService {
//
//
//    private Logger logger = Logger.getLogger(WaCaiServiceImpl.class);
//
//
//    @Autowired
//    private BwBlacklistService bwBlacklistService;
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
//    @Autowired
//    private IBwBorrowerService borrowerService;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//    @Autowired
//    private BwRejectRecordService bwRejectRecordService;
//    @Autowired
//    private AsyncWaCaiService asyncWaCaiService;
//    @Autowired
//    private IBwBankCardService bwBankCardService;
//
//
//
//    /**
//     * 用户过滤
//     *
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    @Override
//    public WaCaiResponse checkUser(Long sessionId, WaCaiCommonReq waCaiCommonReq) {
//
//        // 获取手机号
//        String mobile = waCaiCommonReq.getMobile();
//        String idCard = waCaiCommonReq.getIdCard();
//        String name = waCaiCommonReq.getName();
//        // 手机号为空
//        if (StringUtils.isBlank(mobile)||StringUtils.isBlank(name)||StringUtils.isBlank(idCard)) {
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "手机号为空或姓名或身份证号码为空");
//        }
//        // 判断虚拟运营商号段
//        if (mobile.startsWith("170")) {
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "手机号为虚拟运营商号段");
//        }
//        // 2001 命中黑名单规则 2002 存在进行中的订单 2003 命中被拒规则 2004 用户年龄超限 1002 参数为空 0000 成功
//
//            DrainageRsp drainageRsp = commonService.checkUser(sessionId, name, mobile, idCard);
//            //判断黑名单
//            if(DrainageEnum.CODE_RULE_BLACKLIST.getCode().equals(drainageRsp.getCode())){
//                return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "用户为黑名单");
//            }
//            //判断进行中的工单
//            if(DrainageEnum.CODE_RULE_ISPROCESSING.getCode().equals(drainageRsp.getCode())){
//                return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "存在进行中的工单");
//            }
//            //判断被拒
//            if(DrainageEnum.CODE_RULE_ISREJECT.getCode().equals(drainageRsp.getCode())){
//                return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "命中被拒规则");
//            }
//            //判断用户年龄超限
//            if(DrainageEnum.CODE_RULE_AGE_UNMATCH.getCode().equals(drainageRsp.getCode())){
//                return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "用户年龄超限");
//            }
//            //判断是否系统异常
//            if(DrainageEnum.CODE_EXCEPTION.getCode().equals(drainageRsp.getCode())){
//                return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "系统异常");
//            }
//            return new WaCaiResponse(WaCaiResponse.CODE_SUCCESS, "用户符合申贷条件");
//    }
//
//    /**
//     * 预绑卡接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    @Override
//    public WaCaiResponse saveBankCard(Long sessionId, WaCaiCommonReq waCaiCommonReq) {
//
//        WaCaBankCard waCaBankCard = waCaiCommonReq.getBankCard();
//        if (CommUtils.isNull(waCaBankCard)) {
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "银行卡数据为空");
//        }
//
//        // 预留手机号
//        String mobile = waCaBankCard.getMobile();
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setIdCardNo(waCaBankCard.getIdCard());
//        drainageBindCardVO.setBankCardNo(waCaBankCard.getCardNo());
//        drainageBindCardVO.setName(waCaBankCard.getHolderName());
//        //预留手机号
//        drainageBindCardVO.setRegPhone(mobile);
//        //注册手机号
//        String phone = waCaiCommonReq.getMobile();
//        drainageBindCardVO.setPhone(phone);
//
//        // 银行卡判断
//        String bankCode = WaCaiConstant.getBankCode(waCaBankCard.getBankCode());
//        if (StringUtils.isBlank(bankCode)) {
//            logger.info(sessionId + " 挖财>>>银行卡编码转换为空[" + waCaBankCard.getBankCode() + "]，手机号[" + mobile + "]");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "不支持该银行绑卡");
//        }
//        drainageBindCardVO.setBankCode(bankCode);
//        drainageBindCardVO.setBindType("1");
//        drainageBindCardVO.setChannelId(Integer.valueOf(WaCaiConstant.CHANNEL_SX));
//        drainageBindCardVO.setNotify(false);
//
//        // 开始预绑卡
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//
//        if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//            logger.info(sessionId + " 挖财>>>预绑卡成功，手机号[" + mobile + "]");
//            return new WaCaiResponse(WaCaiResponse.CODE_SUCCESS, "预绑卡成功");
//        } else {
//            logger.info(sessionId + " 挖财>>>预绑卡失败，手机号[" + mobile + "]");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, drainageRsp.getMessage());
//        }
//    }
//
//    /**
//     * 验证码绑卡接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    @Override
//    public WaCaiResponse saveBankCardCode(Long sessionId, WaCaiCommonReq waCaiCommonReq) {
//
//        String mobile = waCaiCommonReq.getMobile();
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setPhone(mobile);
//        drainageBindCardVO.setBindType("1");
//        drainageBindCardVO.setChannelId(Integer.valueOf(WaCaiConstant.CHANNEL_SX));
//        drainageBindCardVO.setNotify(false);
//        drainageBindCardVO.setVerifyCode(waCaiCommonReq.getSmsCode());
//        WaCaBankCard waCaBankCard = new WaCaBankCard();
//
//        // 开始预绑卡
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//
//        if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//            BwBorrower bwBorrower = new BwBorrower();
//            bwBorrower.setPhone(mobile);
//            BwBorrower bwBorrowerByAttr = borrowerService.findBwBorrowerByAttr(bwBorrower);
//            if(bwBorrowerByAttr==null){
//                logger.info(mobile+"未查询到用户信息");
//                return new WaCaiResponse(WaCaiResponse.CODE_FAIL,"未查询到用户信息");
//            }
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwBorrowerByAttr.getId());
//
//            if(bwBankCard==null){
//                logger.info(mobile+"未查询到银行卡信息");
//                return  new WaCaiResponse(WaCaiResponse.CODE_FAIL,"未查询银行卡信息");
//            }
//            waCaBankCard.setBankCode(bwBankCard.getBankCode());
//            waCaBankCard.setBankName(bwBankCard.getBankName());
//            waCaBankCard.setCardNo(bwBankCard.getCardNo());
//            waCaBankCard.setHolderName(bwBorrowerByAttr.getName());
//            waCaBankCard.setMobile(bwBankCard.getPhone());
//            waCaBankCard.setIdCard(bwBorrowerByAttr.getIdCard());
//            logger.info(sessionId + " 挖财>>>验证码绑卡成功，手机号[" + mobile + "]");
//            return new WaCaiResponse(WaCaiResponse.CODE_SUCCESS, "绑卡成功",waCaBankCard);
//        } else {
//            logger.info(sessionId + " 挖财>>>验证码绑卡失败，手机号[" + mobile + "]");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, drainageRsp.getMessage());
//        }
//    }
//
//
//    /**
//     * 进件
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求
//     * @return 响应
//     */
//    @Override
//    public WaCaiResponse saveOrder(Long sessionId, WaCaiCommonReq waCaiCommonReq) throws Exception {
//
//
//        // 三方工单 期望金额 期望期数
//        String thirdOrderNo = waCaiCommonReq.getOrderId();
//        String expectMoney = waCaiCommonReq.getExpectMoney();
//        String expectNumber = waCaiCommonReq.getExpectNumber();
//
//        // 实名信息
//        WaCaiRealName realName = waCaiCommonReq.getRealName();
//        // 用户信息
//        WaCaiUserInfo userInfo = waCaiCommonReq.getUserInfo();
//        if (realName == null || userInfo == null) {
//            logger.info(sessionId + " 挖财>>>进件失败,实名信息或实名信息为空，三方订单号[" + thirdOrderNo + "]");
//            return new WaCaiResponse(WaCaiResponse.RECEIVE_FAIL, "信息为空");
//        }
//
//        // 紧急联系人
//        List<WaCaiOrderContact> contacts = userInfo.getContacts();
//        if (CommUtils.isNull(contacts) || contacts.size() < 5) {
//            logger.info(sessionId + " 挖财>>>进件联系人不足5个，三方订单号[" + thirdOrderNo + "]");
//            return new WaCaiResponse(WaCaiResponse.RECEIVE_FAIL, "联系人太少");
//        }
//
//        // 身份证 手机号 姓名
//        String idCard = realName.getIdCard();
//        String mobile = realName.getMobile();
//        String name = realName.getName();
//
//        if (StringUtils.isBlank(thirdOrderNo) || StringUtils.isBlank(mobile) || StringUtils.isBlank(name) || StringUtils.isBlank(expectMoney) || StringUtils.isBlank(expectNumber)) {
//            logger.info("闪电贷>>>" + sessionId + " 挖财>>>必要数据为空,工单号[" + thirdOrderNo + "]>>>>>");
//            return new WaCaiResponse(WaCaiResponse.RECEIVE_FAIL, "必要数据为空");
//        }
//
//        // 新增/更新联系人
//        BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, name, idCard, mobile, Integer.valueOf(WaCaiConstant.CHANNEL_SX));
//        if (borrower == null) {
//            logger.info(sessionId + " 挖财>>>新增或更新联系人失败，三方订单号[" + thirdOrderNo + "]");
//            return new WaCaiResponse(WaCaiResponse.RECEIVE_FAIL, "创建联系人失败");
//        }
//        Long borrowerId = borrower.getId();
//        logger.info(sessionId + " 挖财>>> 新增/更新借款人ID：" + borrowerId);
//
//
//        // 查询是否有进行中的订单
//        long count = bwOrderService.findProOrder(borrowerId + "");
//        if (count > 0) {
//            logger.info(sessionId + " 挖财>>>存在进行中的工单,工单号[" + thirdOrderNo + "]>>>>>");
//            return new WaCaiResponse(WaCaiResponse.RECEIVE_FAIL, "存在进行中的工单");
//        }
//
//        // 判断是否有草稿状态的订单
//        BwOrder bwOrder = new BwOrder();
//        bwOrder.setBorrowerId(borrowerId);
//        bwOrder.setStatusId(1L);
//        bwOrder.setProductType(2);
//        bwOrder.setChannel(Integer.valueOf(WaCaiConstant.CHANNEL_SX));
//        bwOrder.setProductId(Integer.valueOf(WaCaiConstant.PRODUCT_ID));
//        List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
//        // 新增/更新订单
//        if (boList != null && boList.size() > 0) {
//            bwOrder = boList.get(boList.size() - 1);
//            bwOrder.setStatusId(1L);
//            bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwOrder.setRepayType(2);
//            bwOrder.setExpectMoney(Double.valueOf(waCaiCommonReq.getExpectMoney()));
//            bwOrder.setExpectNumber(Integer.valueOf(waCaiCommonReq.getExpectNumber()));
//            // 更新订单
//            bwOrderService.updateBwOrder(bwOrder);
//        } else {
//            bwOrder = new BwOrder();
//            bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//            bwOrder.setBorrowerId(borrower.getId());
//            bwOrder.setStatusId(1L);
//            bwOrder.setCreateTime(new Date());
//            bwOrder.setUpdateTime(new Date());
//            bwOrder.setChannel(Integer.valueOf(WaCaiConstant.CHANNEL_SX));
//            bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//            bwOrder.setApplyPayStatus(0);
//            bwOrder.setProductId(Integer.valueOf(WaCaiConstant.PRODUCT_ID));
//            bwOrder.setProductType(2);
//            bwOrder.setRepayType(2);
//            bwOrder.setExpectMoney(Double.valueOf(waCaiCommonReq.getExpectMoney()));
//            bwOrder.setExpectNumber(Integer.valueOf(waCaiCommonReq.getExpectNumber()));
//            // 保存订单
//            bwOrderService.addBwOrder(bwOrder);
//        }
//        long orderId = bwOrder.getId();
//        logger.info(sessionId + " 挖财>>> 新增/更新草稿状态的订单：ID = " + orderId);
//
//        // 5个联系人
//        BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//        BwPersonInfo bwPersonInfoNew = getUser5(contacts);
//        if (bwPersonInfo == null) {
//            bwPersonInfoNew.setOrderId(orderId);
//            bwPersonInfoNew.setQqchat(userInfo.getQq());
//            bwPersonInfoNew.setWechat(userInfo.getWechat());
//            bwPersonInfoNew.setCreateTime(new Date());
//            bwPersonInfoNew.setUpdateTime(new Date());
//            bwPersonInfoService.add(bwPersonInfoNew);
//        } else {
//            bwPersonInfo.setQqchat(userInfo.getQq());
//            bwPersonInfo.setWechat(userInfo.getWechat());
//            bwPersonInfo.setRelationName(bwPersonInfoNew.getRelationName());
//            bwPersonInfo.setRelationPhone(bwPersonInfoNew.getRelationPhone());
//            bwPersonInfo.setUnrelationName(bwPersonInfoNew.getUnrelationName());
//            bwPersonInfo.setUnrelationPhone(bwPersonInfoNew.getUnrelationPhone());
//            bwPersonInfo.setColleagueName(bwPersonInfoNew.getColleagueName());
//            bwPersonInfo.setColleaguePhone(bwPersonInfoNew.getColleaguePhone());
//            bwPersonInfo.setFriend1Name(bwPersonInfoNew.getFriend1Name());
//            bwPersonInfo.setFriend1Phone(bwPersonInfoNew.getFriend1Phone());
//            bwPersonInfo.setFriend2Name(bwPersonInfoNew.getFriend2Name());
//            bwPersonInfo.setFriend2Phone(bwPersonInfoNew.getFriend2Phone());
//            bwPersonInfoService.update(bwPersonInfo);
//        }
//        logger.info(sessionId + " 挖财>>> 新增/更新联系人：ID = " + orderId);
//
//        // TODO 异步进件
//        asyncWaCaiService.saveOrder(sessionId, bwOrder, borrower, waCaiCommonReq);
//
//        return new WaCaiResponse(WaCaiResponse.RECEIVE_SUCCESS, "数据接收成功");
//    }
//
//
//
//    /**
//     * 试算接口（暂时保留）
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    @Override
//    public WaCaiResponse trial(Long sessionId, WaCaiCommonReq waCaiCommonReq) {
//
//        // 查询水象云产品
//        BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(Integer.parseInt(WaCaiConstant.PRODUCT_ID));
//        if (bwProductDictionary == null) {
//            logger.info(sessionId + "水象云查询产品失败，产品号[" + WaCaiConstant.PRODUCT_ID + "]");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "未查到相关产品");
//        }
//
//        // 申请金额
//        Long loanAmount = waCaiCommonReq.getLoanAmount();
//        // 利息
//        Double interestRate = bwProductDictionary.getInterestRate();
//        // 获得还款计划
//        List<WaCaiRepayPlan> repayPlans = getRepayPlans(loanAmount, interestRate);
//        // 总利息
//        Double totalInterest = WaCaiUtil.calculateRepayMoney(loanAmount.doubleValue(), 1, interestRate) + SddUtil.calculateRepayMoney(loanAmount.doubleValue(), 2, interestRate)
//                + SddUtil.calculateRepayMoney(loanAmount.doubleValue(), 3, interestRate) + SddUtil.calculateRepayMoney(loanAmount.doubleValue(), 4, interestRate);
//
//        WaCaiTrialResp waCaiTrialResp = new WaCaiTrialResp();
//        waCaiTrialResp.setLoanAmount(loanAmount);
//        waCaiTrialResp.setActualAmount(loanAmount);
//        waCaiTrialResp.setServiceFee(0L);
//        waCaiTrialResp.setExtFee(0L);
//        waCaiTrialResp.setRepayAmount(loanAmount + totalInterest.longValue());
//        waCaiTrialResp.setRemark("本次试算构成：本金" + loanAmount / 100 + "元，服务费0元，仅供参考，实际金额以出账账单为准");
//        waCaiTrialResp.setRepayPlans(repayPlans);
//
//        return new WaCaiResponse(waCaiTrialResp);
//    }
//
//    /**
//     * 挖财签约接口
//     *
//     * @param sessionId 时间戳
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    @Override
//    public WaCaiResponse saveSign(Long sessionId, WaCaiCommonReq waCaiCommonReq) {
//        logger.info(sessionId + "-->>开始进入签约接口" + JSON.toJSONString(waCaiCommonReq));
//        // 申请金额，单位 分
//        Long loanAmount = waCaiCommonReq.getLoanAmount() == null ? 0 : waCaiCommonReq.getLoanAmount();
//        // 申请期数
//        Integer periodMonth = waCaiCommonReq.getPeriodMonth() == null ? 0 : waCaiCommonReq.getPeriodMonth();
//        String thirdOrderNo = waCaiCommonReq.getOrderId();
//        Map<String, Object> signMap = new HashMap<>();
//        if (thirdOrderNo == null) {
//            logger.info(sessionId + "-->>挖财签约接口传入三方订单为空");
//            signMap.put("orderStatus", WaCaiResponse.SIGN_FAIL);
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "三方订单为空", signMap);
//        }
//
//        BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(thirdOrderNo, WaCaiConstant.CHANNEL_SX);
//        if (bwOrder == null) {
//            logger.info(thirdOrderNo + "-->>--挖财签约接口未查询到我方订单");
//            signMap.put("orderStatus", WaCaiResponse.SIGN_FAIL);
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "订单为空", signMap);
//        }
//
//        long borrowAmount = bwOrder.getBorrowAmount().longValue() * 100;
//
//        if (periodMonth != 4 || loanAmount != borrowAmount) {
//            logger.info(thirdOrderNo + "-->>期数或金额校验失败,期数为：" + periodMonth + ",金额为：" + periodMonth);
//            signMap.put("orderStatus", WaCaiResponse.SIGN_FAIL);
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "期数或金额校验失败", signMap);
//        }
//        DrainageRsp drainageRsp = commonService.updateSignContract(sessionId, thirdOrderNo);
//        if (drainageRsp != null) {
//            if (DrainageEnum.CODE_SUCCESS.getCode().equals(drainageRsp.getCode())) {
//                signMap.put("orderStatus", WaCaiResponse.SIGN_SUCCESS);
//                return new WaCaiResponse(WaCaiResponse.CODE_SUCCESS, "签约成功", signMap);
//            } else {
//                signMap.put("orderStatus", WaCaiResponse.SIGN_FAIL);
//                return new WaCaiResponse(WaCaiResponse.CODE_FAIL, drainageRsp.getMessage(), signMap);
//            }
//        } else {
//            logger.info(thirdOrderNo + "-->>挖财签约接口返回参数为空");
//            signMap.put("orderStatus", WaCaiResponse.SIGN_FAIL);
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "方法返回参数为空", signMap);
//        }
//
//    }
//
//    /**
//     * 合同接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    @Override
//    public WaCaiResponse getContract(Long sessionId, WaCaiCommonReq waCaiCommonReq) {
//        logger.info("-->>开始进入挖财查询合同接口-->>" + JSON.toJSONString(waCaiCommonReq));
//        String thirdOrderNo = waCaiCommonReq.getOrderId();
//        Map<String, Object> contractMap = new HashMap<>();
//        if (thirdOrderNo == null) {
//            logger.info(sessionId + "-->>挖财合同接口传入三方订单为空");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "三方订单为空");
//        }
//        BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(thirdOrderNo, WaCaiConstant.CHANNEL_SX);
//        if (bwOrder == null) {
//            logger.info("-->>" + thirdOrderNo + "--挖财合同接口未查询到我方订单");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "订单为空");
//        }
//        Integer contractPage = waCaiCommonReq.getContractPage();
//        if (contractPage != 1) {
//            logger.info(thirdOrderNo + "-->>暂无此页面");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "暂无此页面");
//        }
//        BwAdjunct bwAdjunct = new BwAdjunct();
//        bwAdjunct.setOrderId(bwOrder.getId());
//        bwAdjunct.setAdjunctType(29);
//        bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
//        if (bwAdjunct == null) {
//            logger.info("-->>" + thirdOrderNo + "--未查询到合同地址");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "未查询到合同地址");
//        }
//        String contractUrl = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
//        contractMap.put("contractUrl", contractUrl);
//        return new WaCaiResponse(WaCaiResponse.CODE_SUCCESS, "获取合同成功", contractMap);
//    }
//
//    /**
//     * 授信结果拉取接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    @Override
//    public WaCaiResponse getApprovalResult(Long sessionId, WaCaiCommonReq waCaiCommonReq) {
//        logger.info("-->>开始进入挖财授信结果查询接口-->>" + JSON.toJSONString(waCaiCommonReq));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String thirdOrderNo = waCaiCommonReq.getOrderId();
//        if (thirdOrderNo == null) {
//            logger.info(sessionId + "-->>挖财订单状态查询接口传入三方订单为空");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "三方订单为空");
//        }
//        BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(thirdOrderNo, WaCaiConstant.CHANNEL_SX);
//        if (bwOrder == null) {
//            logger.info( thirdOrderNo + "-->>挖财订单状态查询接口未查询到我方订单");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "订单为空");
//        }
//        // 查询水象云产品
//        BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(Integer.parseInt(WaCaiConstant.PRODUCT_ID));
//        if (bwProductDictionary == null) {
//            logger.info(thirdOrderNo + "-->>水象云查询产品失败，产品号[" + WaCaiConstant.PRODUCT_ID + "]");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "订单为空");
//        }
//        Long statusId = bwOrder.getStatusId();
//        // 审批参数
//        WaCaiApproval waCaiApproval = new WaCaiApproval();
//        waCaiApproval.setOrderId(thirdOrderNo);
//        waCaiApproval.setApprovalResults(WaCaiUtil.statusConvert(statusId));
//
//        if (statusId == 4) {
//            // 审批通过
//            WaCaiApprovalAmount waCaiApprovalAmount = new WaCaiApprovalAmount();
//            // 水象支持的期数
//            WaCaiTerm waCaiTerm = new WaCaiTerm();
//            // 支持的期数 1 单期 2 多期
//            List<Integer> num = new ArrayList<>();
//            num.add(4);
//            // 期数对应的利率
//            List<Integer> rate = new ArrayList<>();
//            rate.add((int) (bwProductDictionary.getInterestRate() / 7 * 10000 * 365));
//            waCaiTerm.setType(2);
//            waCaiTerm.setUnit("week");
//            waCaiTerm.setNum(num);
//            waCaiTerm.setRate(rate);
//            // 固定利率，单位 万分之一
//            waCaiApproval.setRate((int) (bwProductDictionary.getInterestRate() / 7 * 10000 * 365));
//            // 利率类型: 1 日利率 2月利率 3 年利率
//            waCaiApproval.setRateType(1);
//            waCaiApproval.setTerm(waCaiTerm);
//            // 审核时间
//            waCaiApproval.setApprovalTime(sdf.format(bwOrder.getSubmitTime()));
//            // 审核结果附注
//            waCaiApproval.setApprovalMessage("审批通过");
//            // 授信额度
//            waCaiApprovalAmount.setMax(bwOrder.getBorrowAmount().longValue() * 100);
//            waCaiApprovalAmount.setMin(bwOrder.getBorrowAmount().longValue() * 100);
//            waCaiApproval.setApprovalAmount(waCaiApprovalAmount);
//            // 已使用的金额, 单位分
//            waCaiApproval.setAppliedAmount(bwOrder.getBorrowAmount().longValue() * 100);
//            // 剩余可申请的金额 , 单位分
//            waCaiApproval.setRemainAmount(0L);
//        } else if (statusId == 7 || statusId == 8) {
//            // 授信失败,审批不通过
//            waCaiApproval.setApprovalMessage("系统评分不足");
//            waCaiApproval.setApprovalTime(sdf.format(bwOrder.getSubmitTime()));
//        } else if (statusId == 2 || statusId == 3) {
//            waCaiApproval.setApprovalMessage("系统审核中");
//        }
//        return new WaCaiResponse(WaCaiResponse.CODE_SUCCESS, "授信接口查询成功", waCaiApproval);
//    }
//
//    /**
//     * 订单状态拉取接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    @Override
//    public WaCaiResponse getOrder(Long sessionId, WaCaiCommonReq waCaiCommonReq) {
//        logger.info("-->>开始进入挖财订单状态查询接口-->>" + JSON.toJSONString(waCaiCommonReq));
//        String thirdOrderNo = waCaiCommonReq.getOrderId();
//        if (thirdOrderNo == null) {
//            logger.info(sessionId + "-->>挖财订单状态查询接口传入三方订单为空");
//            return new WaCaiResponse(WaCaiOrderInfo.CODE_FAIL, "三方订单为空");
//        }
//        BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(thirdOrderNo, WaCaiConstant.CHANNEL_SX);
//        if (bwOrder == null) {
//            logger.info("-->>" + thirdOrderNo + "--挖财订单状态查询接口未查询到我方订单");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "订单为空");
//        }
//        Long orderId = bwOrder.getId();
//        Long statusId = bwOrder.getStatusId();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(orderId);
//        Double borrowAmount = bwOrder.getBorrowAmount();
//        WaCaiOrderInfo waCaiOrderInfo = new WaCaiOrderInfo();
//        waCaiOrderInfo.setOrderId(thirdOrderNo);
//        waCaiOrderInfo.setOrderStatus(WaCaiUtil.statusConvert(statusId));
//        waCaiOrderInfo.setUpdateTime(sdf.format(bwOrder.getUpdateTime()));
//        waCaiOrderInfo.setAmount(borrowAmount == null ? 0L : bwOrder.getBorrowAmount().longValue() * 100);
//        List<WaCaiRepaymentPlan> waCaiPlanList = new ArrayList<>(4);
//        // 遍历还款计划
//        for (BwRepaymentPlan plan : bwRepaymentPlans) {
//            WaCaiRepaymentPlan waCaiRepaymentPlan = new WaCaiRepaymentPlan();
//            // 当前期数
//            waCaiRepaymentPlan.setPeriod(plan.getNumber());
//            // 当前本金
//            waCaiRepaymentPlan.setAmount((plan.getRepayCorpusMoney().longValue() * 100));
//            // 还款的截止日期
//            waCaiRepaymentPlan.setDueTime(sdf.format(plan.getRepayTime()));
//            // 服务费
//            waCaiRepaymentPlan.setServiceFee(plan.getRepayAccrualMoney().longValue() * 100);
//            // 允许还款的起始日期
//            waCaiRepaymentPlan.setRepayTime(sdf.format(plan.getCreateTime()));
//            // 用户实际应还款的金额
//            waCaiRepaymentPlan.setRequiedRepayAmount(plan.getRealityRepayMoney().longValue() * 100);
//            // 当前期还款状态
//            waCaiRepaymentPlan.setRepayStatus(WaCaiUtil.planConvert(plan.getRepayStatus()));
//            if (plan.getRepayStatus() == 2) {
//                // 用户实际已还款的时间
//                waCaiRepaymentPlan.setPaidTime(sdf.format(plan.getUpdateTime()));
//                // //用户实际已还款金额
//                waCaiRepaymentPlan.setPaidAmount(plan.getAlreadyRepayMoney().longValue() * 100);
//            }
//            BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//            bwOverdueRecord.setRepayId(plan.getId());
//            bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//            if (bwOverdueRecord != null) {
//                logger.info("挖财订单号[" + orderId + "]查出逾期记录，当前期数[" + plan.getNumber() + "]");
//                // 逾期费用
//                waCaiRepaymentPlan.setOverdueFee(bwOverdueRecord.getOverdueAccrualMoney().longValue() * 100);
//            }
//            waCaiPlanList.add(waCaiRepaymentPlan);
//        }
//        waCaiOrderInfo.setRepaymentPlans(waCaiPlanList);
//        logger.info("-->>" + thirdOrderNo + "-->>挖财订单状态查询接口返回数据--" + JSON.toJSONString(waCaiOrderInfo));
//        return new WaCaiResponse(WaCaiResponse.CODE_SUCCESS, "拉取订单状态成功", waCaiOrderInfo);
//    }
//
//    /**
//     * 主动还款接口
//     *
//     * @param sessionId 时间戳标记
//     * @param waCaiCommonReq 请求参数
//     * @return 响应
//     */
//    @Override
//    public WaCaiResponse updateRepayment(Long sessionId, WaCaiCommonReq waCaiCommonReq) {
//        logger.info("-->>开始进入挖财主动还款接口--" + JSON.toJSONString(waCaiCommonReq));
//        String thirdOrderNo = waCaiCommonReq.getOrderId();
//        if (thirdOrderNo == null) {
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "传入三方订单为空");
//        }
//        // 查询订单
//        BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(thirdOrderNo, WaCaiConstant.CHANNEL_SX);
//        if (bwOrder == null) {
//            logger.info("-->>" + thirdOrderNo + "--挖财主动还款接口接口未查询到我方订单");
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "订单信息为空");
//        }
//        DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, thirdOrderNo);
//        logger.info(thirdOrderNo + "-->>" + "返回还款信息" + JSON.toJSONString(drainageRsp));
//        if (drainageRsp != null) {
//            if (drainageRsp.getCode().equals("000")) {
//                return new WaCaiResponse(WaCaiResponse.CODE_SUCCESS, "申请支付成功");
//            } else {
//                return new WaCaiResponse(WaCaiResponse.CODE_FAIL, drainageRsp.getMessage());
//            }
//        } else {
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "支付返回信息为空");
//        }
//    }
//
//
//    /**
//     *
//     * @param loanAmount 申请金额 分
//     * @param interestRate 产品表利息
//     * @return 还款计划4期
//     */
//    private List<WaCaiRepayPlan> getRepayPlans(Long loanAmount, Double interestRate) {
//        List<WaCaiRepayPlan> repayPlans = new ArrayList<>(4);
//        for (int i = 1; i <= 4; i++) {
//            WaCaiRepayPlan waCaiRepayPlan = new WaCaiRepayPlan();
//            waCaiRepayPlan.setPeriod(i);
//            waCaiRepayPlan.setServiceFee(0L);
//            waCaiRepayPlan.setLoanAmount(loanAmount / 4);
//            double repayAmount = loanAmount / 4 + WaCaiUtil.calculateRepayMoney(loanAmount.doubleValue(), i, interestRate);
//            waCaiRepayPlan.setRepayAmount((long) repayAmount);
//            repayPlans.add(waCaiRepayPlan);
//        }
//        return repayPlans;
//    }
//
//
//    /**
//     * 贷前检查接口-被拒记录
//     *
//     * @param borrowerId 联系人ID
//     * @return true不可贷 false可贷
//     */
//    public boolean isRejectRecord2(Long borrowerId) {
//        // 查询是否有被拒记录，如果有则判断被拒类型，如果是永久拒绝则返回400，如果是临时拒绝则判断是否到期
//        BwRejectRecord record = bwRejectRecordService.findBwRejectRecordByBid(borrowerId);
//        if (!CommUtils.isNull(record)) {
//            Date rejectDate = record.getCreateTime();
//            // 永久拒绝
//            if ("0".equals(String.valueOf(record.getRejectType()))) {
//                return true;
//            } else {
//                // 6判断临时拒绝是否到期，如果没到期则返回400，否则返回200+is_reloan=1
//                long day = (Calendar.getInstance().getTime().getTime() - rejectDate.getTime()) / (24 * 60 * 60 * 1000);
//                if (day < 7) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//
//    /**
//     * 处理联系人
//     *
//     * @param contacts 5个联系人集合
//     * @return BwPersonInfo
//     */
//    private BwPersonInfo getUser5(List<WaCaiOrderContact> contacts) {
//        BwPersonInfo bwPersonInfo = new BwPersonInfo();
//        for (WaCaiOrderContact contact : contacts) {
//            if ("紧急联系人1".equals(contact.getRelation())) {
//                bwPersonInfo.setRelationName(contact.getName());
//                bwPersonInfo.setRelationPhone(contact.getMobile());
//            }
//            if ("紧急联系人2".equals(contact.getRelation())) {
//                bwPersonInfo.setUnrelationName(contact.getName());
//                bwPersonInfo.setUnrelationPhone(contact.getMobile());
//            }
//            if ("同事".equals(contact.getRelation())) {
//                bwPersonInfo.setColleagueName(contact.getName());
//                bwPersonInfo.setColleaguePhone(contact.getMobile());
//            }
//            if ("朋友1".equals(contact.getRelation())) {
//                bwPersonInfo.setFriend1Name(contact.getName());
//                bwPersonInfo.setFriend1Phone(contact.getMobile());
//            }
//            if ("朋友2".equals(contact.getRelation())) {
//                bwPersonInfo.setFriend2Name(contact.getName());
//                bwPersonInfo.setFriend2Phone(contact.getMobile());
//            }
//        }
//        return bwPersonInfo;
//    }
//
//}
//
//
