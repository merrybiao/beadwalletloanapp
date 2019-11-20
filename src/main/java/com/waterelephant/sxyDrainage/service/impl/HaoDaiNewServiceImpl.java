///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
// * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.drainage.entity.common.PushOrderRequest;
//import com.waterelephant.entity.BwAdjunct;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwBorrowerService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.impl.BwAdjunctServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.haodai.HaoDaiResponse;
//import com.waterelephant.sxyDrainage.entity.haodai.HdAddInfo;
//import com.waterelephant.sxyDrainage.entity.haodai.HdApplyDetail;
//import com.waterelephant.sxyDrainage.entity.haodai.HdApproveResp;
//import com.waterelephant.sxyDrainage.entity.haodai.HdBankCardInfo;
//import com.waterelephant.sxyDrainage.entity.haodai.HdBaseInfoReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdBindCardCheckReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdCheckUserReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdCommonReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdContractURLResp;
//import com.waterelephant.sxyDrainage.entity.haodai.HdOrderInfo;
//import com.waterelephant.sxyDrainage.entity.haodai.HdOrderStatusResp;
//import com.waterelephant.sxyDrainage.entity.haodai.HdPlan;
//import com.waterelephant.sxyDrainage.entity.haodai.HdRepaymentResp;
//import com.waterelephant.sxyDrainage.entity.haodai.HdSupplementInfoReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdSureLoanReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdTrialReq;
//import com.waterelephant.sxyDrainage.entity.haodai.HdTrialResp;
//import com.waterelephant.sxyDrainage.entity.haodai.HdUser;
//import com.waterelephant.sxyDrainage.entity.haodai.mobilesrc.HdCall;
//import com.waterelephant.sxyDrainage.entity.haodai.mobilesrc.HdCallItem;
//import com.waterelephant.sxyDrainage.entity.haodai.mobilesrc.HdMobileSrc;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.HaoDaiNewService;
//import com.waterelephant.sxyDrainage.service.HdPushOrderService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.haodai.HaoDaiConstant;
//import com.waterelephant.sxyDrainage.utils.haodai.HaoDaiUtils;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DoubleUtil;
//import com.waterelephant.utils.MD5Util;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//
///**
// *
// * Module:
// *
// * HaoDaiNewServiceImpl.java
// *
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Service
//public class HaoDaiNewServiceImpl implements HaoDaiNewService {
//
//    @Autowired
//    private CommonService commonService;
//    @Autowired
//    private IBwBorrowerService bwBorrowerService;
//    @Autowired
//    private BwAdjunctServiceImpl bwAdjunctService;
//    @Autowired
//    private IBwRepaymentPlanService bwRepaymentPlanService;
//    @Autowired
//    private BwOverdueRecordService bwOverdueRecordService;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private BwOrderRongService bwOrderRongService;
//    @Autowired
//    private IBwBankCardService bwBankCardService;
//    @Autowired
//    private BwProductDictionaryService bwProductDictionaryService;
//    @Autowired
//    private HdPushOrderService hdPushOrderService;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//
//    private Logger logger = Logger.getLogger(HaoDaiNewServiceImpl.class);
//
//    private final static String CHANNEL_ID = HaoDaiConstant.CHANNEL_ID;
//
//    private final static Integer LOAN_TERM = 28;
//
//    private final static String PUSHORDER = "tripartite:pushOrderInfo:" + CHANNEL_ID;
//
//    private final static String PUSHORDERADD = "tripartite:pushOrderAddInfo:" + CHANNEL_ID;
//
//    private final static String CONTRACT_URL = HaoDaiConstant.CONTRACT_URL;
//
//    private final static String SX_RETRUN_URL = HaoDaiConstant.SX_RETRUN_URL;
//
//    /**
//     * 用户检查接口
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#(com.waterelephant.sxyDrainage.entity.haodai.HdBindCardCheckReq)
//     */
//    @Override
//
//    public HaoDaiResponse checkUser(HdCheckUserReq hdCheckUserReq, long sessionId) {
//        logger.info("=====好贷网>>>" + sessionId + " 开始HaoDaiWangServiceImpl checkUser()" + JSON.toJSONString(hdCheckUserReq));
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//            // 获取参数,查找用户
//            String user_name = hdCheckUserReq.getUser_name();
//            String user_mobileTemp = hdCheckUserReq.getMobile();
//            String userIdTemp = hdCheckUserReq.getUser_id();
//            if (CommUtils.isNull(user_mobileTemp) || CommUtils.isNull(user_name)||CommUtils.isNull(userIdTemp)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("姓名或手机号为空");
//                logger.info("HaoDaiNewServiceImpl.checkUser()姓名或手机号为空,返回数据为：" + JSON.toJSONString(haoDaiResponse));
//                return haoDaiResponse;
//            }
//            String user_mobile = user_mobileTemp.replace("****", "%");
//            String idCard = userIdTemp.replace("*", "%");
//
//            BwBorrower bwBorrowerTemp=bwBorrowerService.oldUserFilter2(user_name,user_mobile,idCard);
//            // bwBorrowerService打版本
//            if (bwBorrowerTemp == null) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//                haoDaiResponse.setMsg("ok");
//                haoDaiResponse.setUserstatus(1);
//                logger.info("HaoDaiNewServiceImpl.checkUser()标记为新用户,返回数据为：" + JSON.toJSONString(haoDaiResponse));
//            } else {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//                haoDaiResponse.setMsg("ok");
//                haoDaiResponse.setUserstatus(1);
//            }
//
//            DrainageRsp drainageRsp = commonService.checkUser(sessionId, user_name, user_mobile, idCard);
//
//            // 1表示新用户可申请或老用户复贷时走全流程，2表示老用户复贷时走简捷流程，3表示老用户不可复待，4表示黑名单用户，5表示其他
//
//            // 2001 命中黑名单规则
//            // 2002 存在进行中的订单
//            // 2003 命中被拒规则
//            // 2004 用户年龄超限
//            // 1002 参数为空
//
//            if (drainageRsp.getCode().equals("2001")) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//                haoDaiResponse.setMsg(drainageRsp.getMessage());
//                haoDaiResponse.setUserstatus(4);
//            }
//            if (drainageRsp.getCode().equals("2002")) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//                haoDaiResponse.setMsg(drainageRsp.getMessage());
//                haoDaiResponse.setUserstatus(3);
//            }
//
//            if (drainageRsp.getCode().equals("2003")) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//                haoDaiResponse.setMsg(drainageRsp.getMessage());
//                haoDaiResponse.setUserstatus(5);
//            }
//            if (drainageRsp.getCode().equals("2004")) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//                haoDaiResponse.setMsg(drainageRsp.getMessage());
//                haoDaiResponse.setUserstatus(5);
//            }
//
//            logger.info("=====好贷网>>>结束可否申请&复贷判断接口方法HaoDaiWangServiceImpl checkUser()，数据为：" + JSON.toJSONString(haoDaiResponse));
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>结束可否申请&复贷判断接口方法HaoDaiWangServiceImpl checkUser()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("可否申请&复贷判断接口系统异常，请稍后重试");
//        }
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 用户进件
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#()
//     */
//    @Override
//    public HaoDaiResponse saveOrder(HdBaseInfoReq hdBaseInfoReq, long sessionId, Object operaterData) {
//        logger.info("用户进件接口输出数据"/* + JSON.toJSONString(hdBaseInfoReq) */);
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        String orderNo = null;
//        try {
//            HdAddInfo addInfo = hdBaseInfoReq.getAddInfo();
//            HdOrderInfo orderInfo = hdBaseInfoReq.getOrderInfo();
//            if (CommUtils.isNull(addInfo) || CommUtils.isNull(orderInfo)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("进件接口传入数据为空");
//                return haoDaiResponse;
//            }
//            orderNo = orderInfo.getOrder_no();
//            String userName = orderInfo.getUser_name();
//            String userMobile = orderInfo.getUser_mobile();
//            String userId = orderInfo.getUser_id();
//            String address = orderInfo.getAddress();
//            Double applyAmount = orderInfo.getApply_amount();
//            boolean progressOrder = thirdCommonService.checkUserAccountProgressOrder(sessionId, userId);// TODO新加方法
//            // 根据身份证号查询进行中的工单
//            if (progressOrder) {
//                logger.info(orderNo + "存在进行中的订单");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("存在进行中的订单");
//                return haoDaiResponse;
//            }
//
//            DrainageRsp drainageRsp = commonService.checkUser(sessionId, userName, userMobile, userId);
//            if ("2001".equals(drainageRsp.getCode())) {
//                logger.info(orderNo + "命中黑名单规则");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg(drainageRsp.getMessage());
//                return haoDaiResponse;
//
//            }
//            if ("2002".equals(drainageRsp.getCode())) {
//                logger.info(orderNo + "存在进行中的订单");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg(drainageRsp.getMessage());
//                return haoDaiResponse;
//            }
//
//            if ("2003".equals(drainageRsp.getCode())) {
//                logger.info(orderNo + "命中被拒规则");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg(drainageRsp.getMessage());
//                return haoDaiResponse;
//
//            }
//            if ("2004".equals(drainageRsp.getCode())) {
//                logger.info(orderNo + "用户年龄超限");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg(drainageRsp.getMessage());
//                return haoDaiResponse;
//            }
//
//            if (CommUtils.isNull(orderNo)) {
//                logger.info(sessionId + "三方工单为空");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("三方工单为空");
//                return haoDaiResponse;
//            }
//            logger.info(sessionId + "进入好贷进件接口,三方工单为:" + orderNo);
//
//            if (CommUtils.isNull(userName)) {
//                logger.info(sessionId + "用户姓名为空");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("用户姓名为空");
//                return haoDaiResponse;
//            }
//            if (CommUtils.isNull(userMobile)) {
//                logger.info(sessionId + "用户手机号为空");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("用户手机号为空");
//                return haoDaiResponse;
//            }
//            if (CommUtils.isNull(userId)) {
//                logger.info(sessionId + "用户身份证为空");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("用户身份证为空");
//                return haoDaiResponse;
//            }
//            logger.info(orderNo + "处理进件基本信息");
//            PushOrderRequest pushOrderRequest = new PushOrderRequest();
//            pushOrderRequest.setOperaterData(operaterData);// 存运营商 TODO
//            pushOrderRequest.setThirdOrderNo(orderNo);
//            pushOrderRequest.setChannelId(Integer.valueOf(CHANNEL_ID));
//            pushOrderRequest.setPhone(userMobile);
//            pushOrderRequest.setUserName(userName);
//            pushOrderRequest.setIdCard(userId);
//            pushOrderRequest.setExpectMoney(applyAmount);
//            pushOrderRequest.setExpectNumber(4);
//            logger.info(orderNo + "好贷进件接口处理个人信息");
//            BwPersonInfo bwPersonInfo = new BwPersonInfo();
//            bwPersonInfo.setAddress(address);
//            bwPersonInfo.setWechat(userMobile);
//            pushOrderRequest.setBwPersonInfo(bwPersonInfo);
//
//            // 运营商数据
//            logger.info(sessionId + "（获取运营商信息）");
//            HdMobileSrc mobileSrc = addInfo.getMobile_src();
//            BwOperateBasic operateBasic = getOperatorData(orderNo, mobileSrc);
//            pushOrderRequest.setBwOperateBasic(operateBasic);
//
//            // 通话记录
//            logger.info(sessionId + "（获取通话记录）");
//            List<BwOperateVoice> calls = getCalls(orderNo, mobileSrc);
//            pushOrderRequest.setBwOperateVoiceList(calls);
//
//            // 放入redis
//            // logger.info(orderNo + "处理基本信息并放入redis");
//            // RedisUtils.lpush(PUSHORDER, JSON.toJSONString(pushOrderRequest));
//            hdPushOrderService.saveHdOrderInfo(pushOrderRequest); // TODO 异步存数据
//
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//            haoDaiResponse.setMsg("进件接收数据成功");
//        } catch (Exception e) {
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("进件接口系统异常");
//            logger.error("进件接收数据异常", e);
//        }
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 用户补充信息
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#()
//     */
//    @Override
//    public HaoDaiResponse saveAddOrder(HdSupplementInfoReq hdSupplementInfoReq, long sessionId) {
//        logger.info(sessionId + "开始进入好贷补充信息接口"/* + JSON.toJSONString(hdSupplementInfoReq) */);
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        String orderNo = null;
//        try {
//            HdOrderInfo orderInfo = hdSupplementInfoReq.getOrderInfo();// 基本信息，订单号
//            Map<String, String> contacts = hdSupplementInfoReq.getContacts();// 通讯录
//            HdApplyDetail applyDetail = hdSupplementInfoReq.getApplyDetail();// 申请人补充信息说明
//            if (CommUtils.isNull(orderInfo) || CommUtils.isNull(applyDetail) || CommUtils.isNull(contacts)) {
//                logger.info(sessionId + "补充信息接口传入数据为空");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("补充信息接口传入数据为空");
//                return haoDaiResponse;
//            }
//
//            orderNo = orderInfo.getOrder_no();
//            if (CommUtils.isNull(orderNo)) {
//                logger.info(sessionId + "三方工单编号为空");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("三方工单编号为空");
//                return haoDaiResponse;
//            }
//            logger.info(sessionId + "进入好贷补充信息接口,三方工单为:" + orderNo);
//
//            PushOrderRequest pushOrderRequest = new PushOrderRequest();
//            pushOrderRequest.setThirdOrderNo(orderNo);
//            pushOrderRequest.setChannelId(Integer.valueOf(CHANNEL_ID));
//
//            logger.info(orderNo + "好贷处理通讯录");
//            List<BwContactList> bwContactList = getContactList(sessionId, contacts);
//            pushOrderRequest.setBwContactList(bwContactList);
//
//            logger.info(orderNo + "好贷处理个人信息");
//            BwPersonInfo bwPersonInfo = getPersonInfo(applyDetail, orderNo);
//            if (bwPersonInfo == null) {
//                logger.info(sessionId + "紧急联系人获取失败，电话重复或异常");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("联系人信息重复或其它异常");
//                return haoDaiResponse;
//            }
//            pushOrderRequest.setBwPersonInfo(bwPersonInfo);
//
//            logger.info(orderNo + "好贷处理身份证信息");
//            String idenOppositeSide = applyDetail.getIden_opposite_side();// 身份证背面地址
//            String idenCorrectSide = applyDetail.getIden_correct_side();// 身份证正面地址
//            String idenScene = applyDetail.getIden_scene();// 手持身份证照片
//            pushOrderRequest.setIdCardBackImage(idenOppositeSide);
//            pushOrderRequest.setIdCardFrontImage(idenCorrectSide);
//            pushOrderRequest.setIdCardHanderImage(idenScene);
//
//            logger.info(orderNo + "好贷处理工作信息");
//            String companyName = applyDetail.getCompany_name();
//            Integer number = applyDetail.getIndustry();
//            String workType = HaoDaiUtils.getWorkType(number);// 职业类型
//            BwWorkInfo bwWorkInfo = new BwWorkInfo();
//            bwWorkInfo.setComName(companyName);
//            bwWorkInfo.setIndustry(workType);
//            pushOrderRequest.setBwWorkInfo(bwWorkInfo);
//
//            // 借用OperaterData存其它所有信息 todo
//            pushOrderRequest.setOperaterData(hdSupplementInfoReq);
//
//            // 放入redis
//            pushOrderRequest.setOrderStatus(2);
//            logger.info(orderNo + "处理补充信息并放入redis");
//            String json = JSON.toJSONString(pushOrderRequest);
//            Long num = RedisUtils.lpush(PUSHORDERADD, json);
//            logger.info("tripartite:pushOrderAddInfo,存入数量" + num);
//
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//            haoDaiResponse.setMsg("补充信息接口接收数据成功");
//
//            logger.info(sessionId + "结束补充信息接口");
//
//        } catch (Exception e) {
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("补充信息接口系统异常");
//            logger.error("补充信息接口接收数据异常", e);
//        }
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 获取银行卡列表
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#(com.waterelephant.sxyDrainage.entity.haodai.HdCommonReq)
//     */
//    @Override
//    public HaoDaiResponse getBankCard(HdCommonReq hdCommonReq, long sessionId) {
//
//        logger.info("=====好贷网>>>" + sessionId + " 开始HaoDaiWangServiceImpl getBankCard() 获取银行卡列表接口,请求数据为：" + JSON.toJSONString(hdCommonReq));
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        String order_no = null;
//        try {
//            // 获得请求参数（订单编号）
//            order_no = hdCommonReq.getOrder_no();
//
//            // 查询订单
//            BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(order_no, CHANNEL_ID);// TODO 打版本
//            if (bwOrder == null) {
//                logger.info("好贷网>>>" + sessionId + "订单不存在>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到订单记录");
//                return haoDaiResponse;
//            }
//
//            List<HdBankCardInfo> bankCardList = new ArrayList<>();
//
//            // 查询银行卡
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
//            if (bwBankCard == null) {
//                logger.info("好贷网>>>" + sessionId + "银行卡不存在>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到银行卡记录");
//                haoDaiResponse.setData(bankCardList);
//                return haoDaiResponse;
//            }
//            // 银行卡状态
//            if (bwBankCard.getSignStatus() != 1 && bwBankCard.getSignStatus() != 4) {
//                logger.info("好贷网>>>" + sessionId + "用户银行卡状态未绑定>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到银行卡记录");
//                haoDaiResponse.setData(bankCardList);
//                return haoDaiResponse;
//            }
//
//            HdBankCardInfo hdBankCardInfo = new HdBankCardInfo();
//
//            hdBankCardInfo.setBank_code(HaoDaiUtils.convertHdBankCode(bwBankCard.getBankCode()));// 银行编码
//            hdBankCardInfo.setBank_card(HaoDaiUtils.getBankCardCover(bwBankCard.getCardNo()));// 银行卡号
//            // hdBankCardInfo.setUser_name(bwBankCard.getBankName());// 银行卡姓名
//            // hdBankCardInfo.setUser_mobile(bwBankCard.getPhone());// 银行卡预留手机号
//            // hdBankCardInfo.setBank_address("");// 银行卡开户地址
//            hdBankCardInfo.setCard_type("1");// 银行卡类型,1 储蓄卡
//            hdBankCardInfo.setCard_purpose("3");// 银行卡作用,3 收款+还款
//
//            bankCardList.add(hdBankCardInfo);
//
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//            haoDaiResponse.setMsg("ok");
//            haoDaiResponse.setData(bankCardList);
//
//            logger.info("=====好贷网>>>结束获取银行卡列表接口方法HaoDaiWangServiceImpl getBankCard()，数据为：" + JSON.toJSONString(haoDaiResponse));
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>结束获取银行卡列表接口方法HaoDaiWangServiceImpl getBankCard()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("获取银行卡列表接口系统异常，请稍后重试");
//        }
//
//        return haoDaiResponse;
//
//    }
//
//    /**
//     * 绑定银行卡
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#(com.waterelephant.sxyDrainage.entity.haodai.HdBindCardCheckReq)
//     */
//    @Override
//    public HaoDaiResponse saveBankCardInfo(HdBindCardCheckReq hdBindCardCheckReq, long sessionId) {
//        logger.info(sessionId + "开始进入好贷绑定银行卡接口" + JSON.toJSONString(hdBindCardCheckReq));
//        String orderNo = null;
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        try {
//            String bankCard = hdBindCardCheckReq.getBank_card();
//            String idenCard = hdBindCardCheckReq.getIden_card();
//            orderNo = hdBindCardCheckReq.getOrder_no();
//            String userMobile = hdBindCardCheckReq.getUser_mobile();
//            String userName = hdBindCardCheckReq.getUser_name();
//            String bankCode = hdBindCardCheckReq.getBank_code();
//            // 处理银行编码
//            String code = HaoDaiUtils.convertHdToBaoFuCode(bankCode);
//
//            DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//            drainageBindCardVO.setBankCardNo(bankCard);
//            drainageBindCardVO.setBindType("2");
//            drainageBindCardVO.setIdCardNo(idenCard);
//            drainageBindCardVO.setNotify(true);
//            drainageBindCardVO.setPhone(userMobile);
//            drainageBindCardVO.setName(userName);
//            drainageBindCardVO.setChannelId(Integer.valueOf(CHANNEL_ID));
//            drainageBindCardVO.setThirdOrderNo(orderNo);
//            drainageBindCardVO.setBankCode(code);
//            drainageBindCardVO.setRegPhone(userMobile);
//            logger.info("绑卡之前数据" + JSON.toJSONString(drainageBindCardVO));
//            DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//            if (drainageRsp != null) {
//                if ("0000".equals(drainageRsp.getCode())) {
//                    logger.info(orderNo + "预绑卡成功");
//                    haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//                    haoDaiResponse.setMsg("绑卡成功");
//                } else {
//                    logger.info(orderNo + "预绑卡失败");
//                    haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                    haoDaiResponse.setMsg(drainageRsp.getMessage());
//                }
//            } else {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//                haoDaiResponse.setMsg("绑定银行卡返回参数为空");
//                logger.info(orderNo + "绑定银行卡返回参数为空");
//            }
//
//        } catch (Exception e) {
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//            haoDaiResponse.setMsg("绑定银行卡接口系统异常");
//            logger.error("绑定银行卡接口接收数据异常", e);
//        }
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 推送用户验证银行卡(带验证码)
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#(com.waterelephant.sxyDrainage.entity.haodai.HdBindCardCheckReq)
//     */
//    @Override
//    public HaoDaiResponse saveBankCardWithCode(HdBindCardCheckReq hdBindCardCheckReq, long sessionId) {
//        logger.info(sessionId + "开始进入好贷验证银行卡接口" + JSON.toJSONString(hdBindCardCheckReq));
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        String orderNo = null;
//        try {
//            String bankCard = hdBindCardCheckReq.getBank_card();
//            orderNo = hdBindCardCheckReq.getOrder_no();
//            String verifyCode = hdBindCardCheckReq.getVerify_code();
//
//            DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//            drainageBindCardVO.setBankCardNo(bankCard);
//            drainageBindCardVO.setBindType("2");
//            drainageBindCardVO.setNotify(true);
//            drainageBindCardVO.setChannelId(Integer.valueOf(CHANNEL_ID));
//            drainageBindCardVO.setVerifyCode(verifyCode);
//            drainageBindCardVO.setThirdOrderNo(orderNo);
//
//            DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//            if (drainageRsp != null) {
//                if ("0000".equals(drainageRsp.getCode())) {
//                    logger.info(orderNo + ">>绑卡成功");
//                    haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//                    haoDaiResponse.setMsg("绑卡成功");
//                } else {
//                    logger.info(orderNo + ">>绑卡失败");
//                    haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                    haoDaiResponse.setMsg(drainageRsp.getMessage());
//                }
//            } else {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//                haoDaiResponse.setMsg("绑定银行卡返回参数为空");
//                logger.info(orderNo + "绑定银行卡返回参数为空");
//            }
//
//        } catch (Exception e) {
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//            haoDaiResponse.setMsg("绑定银行卡接口系统异常");
//            logger.error("绑定银行卡接口接收数据异常", e);
//        }
//        logger.info(sessionId + "结束好贷绑定银行卡接口");
//
//        return haoDaiResponse;
//
//    }
//
//    /**
//     * 推送确认金额及期限
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#(com.waterelephant.sxyDrainage.entity.haodai.HdSureLoanReq)
//     */
//    @Override
//    public HaoDaiResponse withDraw(HdSureLoanReq hdSureLoanReq, long sessionId) {
//
//        logger.info(sessionId + "开始进入好贷确认金额接口" + JSON.toJSONString(hdSureLoanReq));
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        try {
//            Map<String, String> map = new HashMap<>();
//            String orderNo = hdSureLoanReq.getOrderNo();
//            Double loanAmt = hdSureLoanReq.getLoanAmt();
//            String period = hdSureLoanReq.getPeriod();
//            String returnUrl = hdSureLoanReq.getReturnUrl();
//
//            if (CommUtils.isNull(orderNo)) {
//                logger.info(sessionId + "确认金额接口三方工单编号为空");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//                haoDaiResponse.setMsg("确认金额接口三方工单编号为空");
//                return haoDaiResponse;
//            }
//
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//            if (bwOrderRong == null) {
//                logger.info(orderNo + "HdPushOrderServiceImpl.withDraw()三方订单为空");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//                haoDaiResponse.setMsg("确认金额接口三方工单为空");
//                return haoDaiResponse;
//            }
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//            // 如果当前订单不存在表示订单基本信息未推送
//            if (CommUtils.isNull(bwOrder)) {
//                logger.info(orderNo + "HdPushOrderServiceImpl.withDraw()我方订单为空");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//                haoDaiResponse.setMsg("确认金额接口我方工单为空");
//                return haoDaiResponse;
//            }
//            Long orderId = bwOrder.getId();
//            BwBorrower bwBorrower = new BwBorrower();
//            bwBorrower.setId(bwOrder.getBorrowerId());
//            bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//            if (CommUtils.isNull(bwBorrower)) {
//                logger.info(orderNo + "HdPushOrderServiceImpl.withDraw()我方借款人为空");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//                haoDaiResponse.setMsg("确认金额接口我方借款人为空");
//                return haoDaiResponse;
//            }
//            Double amount = bwOrder.getBorrowAmount();
//            logger.info("我方金额:" + amount + "对方金额:" + loanAmt);
//            if (DoubleUtil.sub(amount, loanAmt) != 0) {
//                logger.info(orderNo + "HdPushOrderServiceImpl.withDraw()金额不一致");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//                haoDaiResponse.setMsg("金额不一致");
//                return haoDaiResponse;
//            }
//            if (!"1-28".equals(period)) {
//                logger.info(orderNo + "HdPushOrderServiceImpl.withDraw()期限不一致");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//                haoDaiResponse.setMsg("期限不一致");
//                return haoDaiResponse;
//            }
//            String phone = bwBorrower.getPhone();
//
//            String md5Data = MD5Util.getMd5Value("phone=" + phone + "&order_no=" + orderNo);
//            // http://106.14.99.159/loanapp-api-web/v4/app/order/a1/login.do?phone=13297035695&order_no=222222223794&platform=4&params=1533b8d036e6ed96564fc2d98612d5a9&returnUrl=回调地址
//            String contractUrl = CONTRACT_URL + "phone=" + phone + "&order_no=" + orderNo + "&platform=4&params=" + md5Data + "&returnUrl=" + SX_RETRUN_URL;// TODO线上地址需要配置
//            logger.info("回调的地址：" + contractUrl);
//            map.put("url", contractUrl);
//
//            // RedisUtils.set("tripartite:hdWithDraw:" + orderNo, returnUrl);
//            RedisUtils.setNxAndEx("tripartite:hdWithDraw:" + orderNo, returnUrl, 60 * 60 * 6);
//
//            // RedisUtils.rpush("tripartite:hdWithDraw:" + orderNo, returnUrl);
//
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//            haoDaiResponse.setMsg("确认金额接口接收数据成功");
//            haoDaiResponse.setData(map);
//
//        } catch (Exception e) {
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL_CONFIRM);
//            haoDaiResponse.setMsg("确认金额接口系统异常");
//            logger.error("确认金额接口接收数据异常", e);
//        }
//
//        return haoDaiResponse;
//    }
//
//    /**
//     * 借款合同查询接口
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#(com.waterelephant.sxyDrainage.entity.haodai.HdCommonReq)
//     */
//    @Override
//    public HaoDaiResponse getContract(HdCommonReq hdCommonReq, long sessionId) {
//        logger.info("=====好贷网>>>" + sessionId + " 开始HaoDaiWangServiceImpl getContract() 借款合同查询接口,请求数据为：" + JSON.toJSONString(hdCommonReq));
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        try {
//            // 获得请求参数（订单编号）
//            String order_no = hdCommonReq.getOrder_no();
//            // 查询订单
//            BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(order_no, CHANNEL_ID);
//            if (bwOrder == null) {
//                logger.info("好贷网>>>" + sessionId + "订单不存在>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到订单记录");
//                return haoDaiResponse;
//            }
//
//            // 合同列表
//            ArrayList<Map<String, String>> arrayList = new ArrayList<>(4);
//            // 用户授权协议
//            {
//                HashMap<String, String> contract = new HashMap<>();
//                String contractUrl = "http://www.sxfq.com/weixinApp3.0/html/Agreement/sqxy.html";
//                contract.put("contractName", "《用户授权协议》");
//                contract.put("url", contractUrl);
//                arrayList.add(contract);
//            }
//
//            // 江西银行网络交易资金账户服务三方协议
//            {
//                HashMap<String, String> contract = new HashMap<>();
//                String contractUrl = "http://www.sxfq.com/weixinApp3.0/html/Agreement/bankDeal.html";
//                contract.put("contractName", "《江西银行网络交易资金账户服务三方协议》");
//                contract.put("url", contractUrl);
//                arrayList.add(contract);
//            }
//
//            // 服务合同
//            BwAdjunct bwAdjunct = new BwAdjunct();
//            bwAdjunct.setOrderId(bwOrder.getId());
//            bwAdjunct.setAdjunctType(29);
//            bwAdjunct = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct);
//            if (!CommUtils.isNull(bwAdjunct)) {
//                HashMap<String, String> contract = new HashMap<>();
//                String contractUrl = SystemConstant.PDF_URL + bwAdjunct.getAdjunctPath();
//                contract.put("contractName", "《小微金融水象分期信息咨询及信用管理服务合同》");
//                contract.put("url", contractUrl);
//                arrayList.add(contract);
//            }
//
//            // 授权书
//            BwAdjunct bwAdjunct2 = new BwAdjunct();
//            bwAdjunct2.setOrderId(bwOrder.getId());
//            bwAdjunct2.setAdjunctType(30);
//            bwAdjunct2 = bwAdjunctService.findBwAdjunctByAttr(bwAdjunct2);
//            if (!CommUtils.isNull(bwAdjunct2)) {
//                HashMap<String, String> contract = new HashMap<>();
//                String contractUrl = SystemConstant.PDF_URL + bwAdjunct2.getAdjunctPath();
//                contract.put("contractName", "《征信及信息披露授权书》");
//                contract.put("url", contractUrl);
//                arrayList.add(contract);
//            }
//
//            // HdContractURLResp hdContractURLResp = new HdContractURLResp();
//            // hdContractURLResp.setContract_url(contractUrl);
//
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//            haoDaiResponse.setData(arrayList);
//            haoDaiResponse.setMsg("ok");
//
//            logger.info("=====好贷网>>>结束借款合同查询接口方法HaoDaiWangServiceImpl getContract()，数据为：" + JSON.toJSONString(haoDaiResponse));
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>结束借款合同查询接口方法HaoDaiWangServiceImpl getContract()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("借款合同查询接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    /**
//     * 试算接口
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#(com.waterelephant.sxyDrainage.entity.haodai.HdTrialReq)
//     */
//
//    @Override
//    public HaoDaiResponse trial(HdTrialReq hdTrialReq, long sessionId) {
//        logger.info("=====好贷网>>>" + sessionId + " 开始HaoDaiWangServiceImpl trial() 试算接口,请求数据为：" + JSON.toJSONString(hdTrialReq));
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//            // 获得请求参数（订单编号）
//            String order_no = hdTrialReq.getOrder_no();
//
//            // 查询订单
//            BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(order_no, CHANNEL_ID);
//            if (bwOrder == null) {
//                logger.info("好贷网>>>" + sessionId + "订单不存在>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到订单记录");
//                return haoDaiResponse;
//            }
//
//            int statusId = bwOrder.getStatusId().intValue();
//            if (statusId < 4 || statusId == 7 || statusId == 8) {
//                logger.info("好贷网>>>" + sessionId + "订单审核未通过>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未审核订单");
//                return haoDaiResponse;
//            }
//
//            // 第一步， 查询水象云产品
//            BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(Integer.valueOf(HaoDaiConstant.PRODUCT_ID));
//
//
//            Integer loan_term = hdTrialReq.getLoan_term();// 借款期限
//            if (loan_term != LOAN_TERM) {
//                logger.info(sessionId + "：本次借款期限：" + loan_term + "，约定期限：" + LOAN_TERM);
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("本次借款期限：" + loan_term + "，约定期限：" + LOAN_TERM);
//                return haoDaiResponse;
//            }
//
//            Double loanAmount = Double.valueOf(hdTrialReq.getLoan_amount());// 获得借款金额
//
//            // 第二步，获取放款金额限制
//            // Integer maxLoanAmount = bwProductDictionary.getMaxAmount();
//            // Integer minLoanAmount = bwProductDictionary.getMinAmount();
//            // 计算利息
//            Double interestRate = bwProductDictionary.getInterestRate();// 分期利息率
//            Double totalAmount = DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 1, interestRate) + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 2, interestRate) + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 3, interestRate) + DrainageUtils.calculateRepayMoney(Double.valueOf(loanAmount), 4, interestRate);
//
//            // if (loanAmount > maxLoanAmount) {
//            // logger.info(sessionId + "：本次借款金额：" + loanAmount + "，大于最大借款金额：" +
//            // maxLoanAmount);
//            // haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            // haoDaiResponse.setMsg("本次借款金额：" + loanAmount + "，大于最大借款金额：" + maxLoanAmount);
//            // return haoDaiResponse;
//            // } else if (loanAmount < minLoanAmount) {
//            // logger.info(sessionId + "：本次借款金额：" + loanAmount + "，小于最小借款金额：" +
//            // minLoanAmount);
//            // haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            // haoDaiResponse.setMsg("本次借款金额：" + loanAmount + "，小于最小借款金额：" + minLoanAmount);
//            // return haoDaiResponse;
//            // }
//
//            HdTrialResp hdTrialResp = new HdTrialResp();
//            hdTrialResp.setReceive_amount(bwOrder.getBorrowAmount() + "");// 实际收到金额
//            hdTrialResp.setLoan_interest(totalAmount + "");// 借款利息
//            hdTrialResp.setPay_amount(loanAmount + totalAmount + "");// 预期应还总额
//
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//            haoDaiResponse.setMsg("ok");
//            haoDaiResponse.setData(hdTrialResp);
//
//            logger.info("=====好贷网>>>结束试算接口方法HaoDaiWangServiceImpl trial()，数据为：" + JSON.toJSONString(haoDaiResponse));
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>结束试算接口方法HaoDaiWangServiceImpl trial()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("试算接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    /**
//     * 主动还款接口
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#(com.waterelephant.sxyDrainage.entity.haodai.HdCommonReq)
//     */
//    @Override
//    public HaoDaiResponse updateActiveRepayment(HdCommonReq hdCommonReq, long sessionId) {
//        logger.info(sessionId + "开始进入好贷主动还款接口" + JSON.toJSONString(hdCommonReq));
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        String orderNo = hdCommonReq.getOrder_no();
//        String periodNos = hdCommonReq.getPeriod_nos();
//        try {
//            if (CommUtils.isNull(orderNo) || CommUtils.isNull(periodNos)) {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("三方订单或期数为空");
//            }
//            int hdNumber = Integer.valueOf(periodNos);
//
//            BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(orderNo, CHANNEL_ID);
//            if (bwOrder == null) {
//                logger.info("好贷网>>>" + sessionId + "订单不存在>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到订单记录");
//                return haoDaiResponse;
//            }
//
//            // 查询还款计划
//            List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//            if (bwRepaymentPlans == null || bwRepaymentPlans.size() == 0) {
//                logger.info("好贷网>>>" + sessionId + "还款计划不存在，订单状态[" + bwOrder.getStatusId() + "]>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到还款计划记录");
//                return haoDaiResponse;
//            }
//
//            for (BwRepaymentPlan plan : bwRepaymentPlans) {
//                if (hdNumber == plan.getNumber()) {
//                    if (plan.getRepayStatus() == 2) {
//                        haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                        haoDaiResponse.setMsg("请勿重复还款");
//                        logger.info(sessionId + "请勿重复还款");
//                        return haoDaiResponse;
//                    }
//                }
//
//                if (hdNumber > plan.getNumber()) {
//                    if (plan.getRepayStatus() != 2) {
//                        haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                        haoDaiResponse.setMsg("请依次还款");
//                        logger.info(sessionId + "请依次还款");
//                        return haoDaiResponse;
//                    }
//                }
//
//            }
//
//            DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, orderNo);
//            logger.info(orderNo + ":返回好贷还款结果" + JSONObject.toJSONString(drainageRsp));
//            if (drainageRsp != null) {
//                if (drainageRsp.getCode().equals("000")) {
//                    haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//                    haoDaiResponse.setMsg("申请支付成功");
//                } else {
//                    haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                    haoDaiResponse.setMsg(drainageRsp.getMessage());
//                }
//            } else {
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("我方接口返回信息为空");
//            }
//
//        } catch (Exception e) {
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("主动还款接口系统异常");
//            logger.error("好贷主动还款接口接收数据异常", e);
//        }
//        logger.info(orderNo + "结束好贷主动还款接口" + JSON.toJSONString(haoDaiResponse));
//        return haoDaiResponse;
//    }
//
//    @Override
//    public HaoDaiResponse pullApprove(HdCommonReq hdCommonReq, long sessionId) {
//        logger.info("=====好贷网>>>" + sessionId + " 开始HaoDaiWangServiceImpl pullApprove()订单状态反馈拉取,请求数据为：" + JSON.toJSONString(hdCommonReq));
//
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//            // 获得请求参数（订单编号）
//            String order_no = hdCommonReq.getOrder_no();
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z08:00'");
//
//            // 查询订单
//            BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(order_no, CHANNEL_ID);
//            if (bwOrder == null) {
//                logger.info("好贷网>>>" + sessionId + "订单不存在>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到订单记录");
//                return haoDaiResponse;
//            }
//
//            // 封装审批结果
//            HdApproveResp hdApproveResp = new HdApproveResp();
//            hdApproveResp.setOrder_no(order_no);// 订单编号
//            hdApproveResp.setApprove_time(sdf.format(bwOrder.getUpdateTime()));// 审批时间
//
//            // 获得审核状态
//            int statusId = bwOrder.getStatusId().intValue();
//            // 正在审核
//            if (2 == statusId || 3 == statusId) {
//                logger.info("好贷网>>>" + sessionId + "订单正在审核状态>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("正在审核，请稍候");
//                return haoDaiResponse;
//            }
//
//            // 审核通过
//            if (statusId == 4) {
//                hdApproveResp.setApprove_conclusion(0);// 审批结论 10 审批通过，-10 为审批不通过
//                hdApproveResp.setApprove_amount_min(bwOrder.getCreditLimit().doubleValue());// 批复额度 最小值
//                hdApproveResp.setApprove_amount_max(bwOrder.getCreditLimit().doubleValue());// 批复额度 最大值
//                hdApproveResp.setApprove_amount_unit(0);// 批复额度 单位,如果额度为区间 值为步进值 如100，如果固定额度则为0
//                hdApproveResp.setApprove_term_options(4);// 批复天数
//                hdApproveResp.setApprove_term_unit(1);// 批复天数 单位 1天30月k
//
//            } else if (statusId == 7 || statusId == 8) { // 审核未通过 TODO 其它字段？
//                hdApproveResp.setApprove_conclusion(-10);// 审批结论 10 审批通过，-10 为审批不通过
//                hdApproveResp.setApprove_remark("系统评分不足");// 审批拒绝说明
//
//            } else {
//                logger.info("好贷网>>>" + sessionId + "订单非审核状态>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("非审核状态，请联系客服");
//                return haoDaiResponse;
//            }
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//            haoDaiResponse.setMsg("ok");
//            haoDaiResponse.setData(hdApproveResp);
//
//            logger.info("=====好贷网>>>结拉取审批结论接口方法HaoDaiWangServiceImpl pullApprove()，数据为：" + JSON.toJSONString(haoDaiResponse));
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>结束拉取审批结论接口方法HaoDaiWangServiceImpl pullApprove()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("拉取审批结论接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    /**
//     * 拉取订单状态接口
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#pullOrderStatus(com.waterelephant.sxyDrainage.entity.haodai.HdCommonReq,
//     *      long)
//     */
//    @Override
//    public HaoDaiResponse pullOrderStatus(HdCommonReq hdCommonReq, long sessionId) {
//        logger.info("=====好贷网>>>" + sessionId + " 开始HaoDaiWangServiceImpl pullOrderStatus()订单状态反馈拉取,请求数据为：" + JSON.toJSONString(hdCommonReq));
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//        try {
//            // 获得请求参数（订单编号）
//            String order_no = hdCommonReq.getOrder_no();
//
//            // 查询订单
//            BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(order_no, CHANNEL_ID);
//            if (bwOrder == null) {
//                logger.info("好贷网>>>" + sessionId + "订单不存在>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到订单记录");
//                return haoDaiResponse;
//            }
//
//            // 查询订单状态
//            if (6 != bwOrder.getStatusId()) {
//                logger.info("好贷网>>>" + sessionId + "用户订单未完成,状态为[" + bwOrder.getStatusId() + "]>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("用户订单未完成");
//                return haoDaiResponse;
//            }
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z08:00'");
//
//            HdOrderStatusResp hdOrderStatusResp = new HdOrderStatusResp();
//            hdOrderStatusResp.setOrder_no(order_no);// 订单编号
//            hdOrderStatusResp.setOrder_update_time(sdf.format(bwOrder.getUpdateTime()));// 变更时间
//            hdOrderStatusResp.setOrder_status(10);// 状态, 10还款成功订单完成
//
//            haoDaiResponse.setData(hdOrderStatusResp);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//            haoDaiResponse.setMsg("用户订单已完成");
//
//            logger.info("=====好贷网>>>结束订单状态反馈方法HaoDaiWangServiceImpl pullOrderStatus()，数据为：" + JSON.toJSONString(haoDaiResponse));
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>结束订单状态反馈方法HaoDaiWangServiceImpl pullOrderStatus()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("订单状态反馈拉取接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    /**
//     * 拉取还款计划接口
//     *
//     * @see com.waterelephant.sxyDrainage.service.HaoDaiNewService#pullRepaymentPlan(com.waterelephant.sxyDrainage.entity.haodai.HdCommonReq,
//     *      long)
//     */
//    @Override
//    public HaoDaiResponse pullRepaymentPlan(HdCommonReq hdCommonReq, long sessionId) {
//        logger.info("=====好贷网>>>" + sessionId + " 开始HaoDaiWangServiceImpl pullRepaymentPlan()订单状态反馈拉取,请求数据为：" + JSON.toJSONString(hdCommonReq));
//        HaoDaiResponse haoDaiResponse = new HaoDaiResponse();
//
//        try {
//            // 获得请求参数（订单编号）
//            String order_no = hdCommonReq.getOrder_no();
//
//            // 查询订单
//            BwOrder bwOrder = bwOrderService.findOrderByThirdOrderNoAndChannel(order_no, CHANNEL_ID);
//            if (bwOrder == null) {
//                logger.info("好贷网>>>" + sessionId + "订单不存在>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到订单记录");
//                return haoDaiResponse;
//            }
//            // if (9 != bwOrder.getStatusId() && 6 != bwOrder.getStatusId() && 13 !=
//            // bwOrder.getStatusId()) {
//            // logger.info("好贷网>>>" + sessionId + "订单状态[" + bwOrder.getStatusId() +
//            // "]不符合>>>>>");
//            // haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            // haoDaiResponse.setMsg("未生成还款计划记录");
//            // return haoDaiResponse;
//            // }
//
//            // 查询还款计划
//            List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//            if (bwRepaymentPlans == null || bwRepaymentPlans.size() == 0) {
//                logger.info("好贷网>>>" + sessionId + "还款计划不存在，订单状态[" + bwOrder.getStatusId() + "]>>>>>");
//                haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                haoDaiResponse.setMsg("未查到还款计划记录");
//                return haoDaiResponse;
//            }
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z08:00'");
//
//            HdRepaymentResp hdRepaymentResp = new HdRepaymentResp();
//
//            hdRepaymentResp.setOrder_no(order_no);// 订单编号
//            hdRepaymentResp.setLoan_apply_amount(bwOrder.getBorrowAmount());// 用户申请金额
//            hdRepaymentResp.setLoan_receive_amount(bwOrder.getBorrowAmount());// 用户到账金额
//            // hdRepaymentReq.setLoan_status(HaoDaiUtils.statusConvert(bwOrder.getStatusId()));//
//
//            // 用户已还金额
//            Double loan_paid_amount = 0.0;
//            // 用户应还金额(含逾期费)
//            Double loan_repay_amount = 0.0;
//
//            List<HdPlan> repayment_plan = new ArrayList<HdPlan>(4);
//
//            for (BwRepaymentPlan plan : bwRepaymentPlans) {
//                HdPlan hdPlan = new HdPlan();
//                hdPlan.setTerm_index(plan.getNumber());// 第几期
//                hdPlan.setDue_time(sdf.format(plan.getRepayTime()));// 到期时间
//
//                // 已还金额
//                Double paid_amount = plan.getAlreadyRepayMoney();
//                hdPlan.setPaid_amount(paid_amount);
//                loan_paid_amount = DoubleUtil.add(loan_paid_amount, paid_amount);// 累加总用户已还金额
//
//                // 应还金额，1.先计算还款金额
//                Double amount = plan.getRepayMoney();
//
//                Integer repayStatus = plan.getRepayStatus();
//                if (1 == repayStatus) {// 未还款
//                    hdPlan.setBill_status(10);// 账单状态,10未到期
//
//                } else if (2 == repayStatus) {// 已经还款
//
//                    hdPlan.setBill_status(11);// 账单状态,11已还款
//                    hdPlan.setFinish_time(plan.getRepayTime() == null ? null : sdf.format(plan.getRepayTime()));// 还款时间
//
//                    if (2 == plan.getRepayType()) {// 逾期还款
//                        // 查看逾期表记录
//                        BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//                        bwOverdueRecord.setRepayId(plan.getId());
//                        bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//                        if (bwOverdueRecord == null) {
//                            logger.info("好贷网>>>" + sessionId + "逾期记录为空>>>>>");
//                            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                            haoDaiResponse.setMsg("逾期记录查询异常");
//                            return haoDaiResponse;
//                        }
//                        amount = DoubleUtil.round(DoubleUtil.add(amount, bwOverdueRecord.getOverdueAccrualMoney()), 2);// 应还金额，2.累加逾期金额
//                    }
//                } else {// 逾期
//                    hdPlan.setBill_status(-10);// 账单状态,-10 逾期
//                    // 查看逾期表记录
//                    BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//                    bwOverdueRecord.setRepayId(plan.getId());
//                    bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//                    if (bwOverdueRecord == null) {
//                        logger.info("好贷网>>>" + sessionId + "逾期记录为空>>>>>");
//                        haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//                        haoDaiResponse.setMsg("逾期记录查询异常");
//                        return haoDaiResponse;
//                    }
//                    amount = DoubleUtil.round(DoubleUtil.add(amount, bwOverdueRecord.getOverdueAccrualMoney()), 2);// 应还金额，2.累加逾期金额
//                }
//
//                hdPlan.setAmount(amount);
//                loan_repay_amount = DoubleUtil.add(loan_repay_amount, amount);// 累加总用户应还金额(含逾期费)
//
//                repayment_plan.add(hdPlan);
//            }
//
//            hdRepaymentResp.setLoan_repay_amount(loan_repay_amount);// 用户应还金额(含逾期费)
//            hdRepaymentResp.setLoan_paid_amount(loan_paid_amount);// 用户已还金额
//            hdRepaymentResp.setLoan_update_time(sdf.format(new Date()));// 更新时间
//
//            hdRepaymentResp.setRepayment_plan(repayment_plan);
//
//            haoDaiResponse.setData(hdRepaymentResp);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_SUCCESS);
//            haoDaiResponse.setMsg("还款计划拉取成功");
//
//            logger.info("=====好贷网>>>结束还款计划反馈拉取方法HaoDaiWangServiceImpl pullRepaymentPlan()，数据为：" + JSON.toJSONString(haoDaiResponse));
//        } catch (Exception e) {
//            logger.error("=====好贷网>>>结束还款计划反馈拉取方法HaoDaiWangServiceImpl pullRepaymentPlan()，发生异常>>>", e);
//            haoDaiResponse.setCode(HaoDaiResponse.CODE_FAIL);
//            haoDaiResponse.setMsg("还款计划反馈拉取接口系统异常，请稍后重试");
//        }
//        return haoDaiResponse;
//    }
//
//    private List<BwContactList> getContactList(long sessionId, Map<String, String> hdContacts) {
//        List<BwContactList> list = new ArrayList<BwContactList>();
//        if (hdContacts != null) {
//            // key 电话 value 名称
//            for (String key : hdContacts.keySet()) {
//
//                if (CommUtils.isNull(key)) {
//                    continue;
//                }
//                if (CommUtils.isNull(hdContacts.get(key))) {
//                    continue;
//                }
//                BwContactList bwContactList = new BwContactList();
//                bwContactList.setCreateTime(new Date());
//                bwContactList.setUpdateTime(new Date());
//                bwContactList.setName(hdContacts.get(key));
//                bwContactList.setPhone(key);
//                list.add(bwContactList);
//            }
//        }
//        return list;
//    }
//
//    private BwPersonInfo getPersonInfo(HdApplyDetail applyDetail, String orderNo) {
//        BwPersonInfo bwPersonInfo = null;
//        try {
//            String email = applyDetail.getEmail();
//            String qq = applyDetail.getQq();
//            Integer marriage = applyDetail.getMarriage();
//            logger.info("婚姻类型：" + marriage);
//            int marryType = HaoDaiUtils.getMarryType(marriage);
//
//            String sosUser = applyDetail.getSos_user();
//            logger.info("sosUser为" + sosUser);
//            JSONObject parseObject = JSONObject.parseObject(sosUser);
//            // 第一联系人 user_1
//            // 第二联系人 user_2
//            // 同事 user_3
//            // 朋友1 user_4
//            // 朋友2 user_5
//
//            Set<String> phoneSet = new HashSet<>();
//
//            String user1 = parseObject.getString("user_1");
//            HdUser relation = JSONObject.parseObject(user1, HdUser.class);
//            String relationName = relation.getName();
//            String relationPhone = relation.getPhone();
//            phoneSet.add(relationPhone);
//
//            String user2 = parseObject.getString("user_2");
//            HdUser unrelation = JSONObject.parseObject(user2, HdUser.class);
//            String unrelationName = unrelation.getName();
//            String unrelationPhone = unrelation.getPhone();
//            if (!phoneSet.add(unrelationPhone)) {
//                logger.info(unrelationPhone + "电话重复");
//                return null;
//            }
//
//            String user3 = parseObject.getString("user_3");
//            HdUser colleague = JSONObject.parseObject(user3, HdUser.class);
//            String colleagueName = colleague.getName();
//            String colleaguePhone = colleague.getPhone();
//            if (!phoneSet.add(colleaguePhone)) {
//                logger.info(colleaguePhone + "电话重复");
//                return null;
//            }
//
//            String user4 = parseObject.getString("user_4");
//            HdUser friend1 = JSONObject.parseObject(user4, HdUser.class);
//            String friend1Name = friend1.getName();
//            String friend1Phone = friend1.getPhone();
//            if (!phoneSet.add(friend1Phone)) {
//                logger.info(friend1Phone + "电话重复");
//                return null;
//            }
//
//            String user5 = parseObject.getString("user_5");
//            HdUser friend2 = JSONObject.parseObject(user5, HdUser.class);
//            String friend2Name = friend2.getName();
//            String friend2Phone = friend2.getPhone();
//            if (!phoneSet.add(friend2Phone)) {
//                logger.info(friend2Phone + "电话重复");
//                return null;
//            }
//
//            bwPersonInfo = new BwPersonInfo();
//            bwPersonInfo.setEmail(email);
//            bwPersonInfo.setMarryStatus(marryType);
//            bwPersonInfo.setQqchat(qq);
//            bwPersonInfo.setColleagueName(colleagueName);
//            bwPersonInfo.setColleaguePhone(colleaguePhone);
//            bwPersonInfo.setFriend1Name(friend1Name);
//            bwPersonInfo.setFriend2Name(friend2Name);
//            bwPersonInfo.setFriend1Phone(friend1Phone);
//            bwPersonInfo.setFriend2Phone(friend2Phone);
//            bwPersonInfo.setRelationName(relationName);
//            bwPersonInfo.setRelationPhone(relationPhone);
//            bwPersonInfo.setUnrelationName(unrelationName);
//            bwPersonInfo.setUnrelationPhone(unrelationPhone);
//            logger.info("返回个人信息为：" + JSON.toJSONString(bwPersonInfo));
//        } catch (Exception e) {
//            logger.error(orderNo + "处理个人信息异常", e);
//        }
//        return bwPersonInfo;
//
//    }
//
//    private BwOperateBasic getOperatorData(String orderNo, HdMobileSrc hdMobileSrc) {
//        BwOperateBasic bwOperateBasic = new BwOperateBasic();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        try {
//            String realName = "", idCard_oper = "", phone_oper = "", regTime = "", dataSource = "";
//            if (null != hdMobileSrc) {
//                dataSource = hdMobileSrc.getCarrier();
//                logger.info("手机运营商数据为：" + dataSource);
//                if (StringUtils.isBlank(dataSource)) {
//                    dataSource = "网络号码";
//                }
//                idCard_oper = hdMobileSrc.getIdcard();
//                realName = hdMobileSrc.getName();
//                phone_oper = hdMobileSrc.getMobile();
//                Date openTime = hdMobileSrc.getOpen_time();
//
//                try {
//                    if (!CommUtils.isNull(openTime)) {
//                        regTime = sdf.format(openTime);
//                    }
//
//                } catch (Exception e) {
//                    logger.error(orderNo + "运营商时间解析异常", e);
//                }
//            }
//            bwOperateBasic.setUserSource(dataSource);
//            bwOperateBasic.setIdCard(idCard_oper);
//            bwOperateBasic.setPhone(phone_oper);
//            bwOperateBasic.setRealName(realName);
//            bwOperateBasic.setPhoneRemain(hdMobileSrc.getAvailable_balance() + "");
//            if (StringUtils.isNotBlank(regTime)) {
//                bwOperateBasic.setRegTime(sdf.parse(regTime));
//            }
//        } catch (Exception e) {
//            logger.warn(orderNo + "解析运营商信息" + e.getMessage());
//        }
//        return bwOperateBasic;
//    }
//
//    /**
//     * 解析通话记录
//     *
//     * @param
//     * @param
//     */
//    private List<BwOperateVoice> getCalls(String orderNo, HdMobileSrc hdMobileSrc) {
//        List<BwOperateVoice> list = new ArrayList<BwOperateVoice>();
//        if (null != hdMobileSrc) {
//            // List<CallsVo> callList = operatorVerify.getCalls();
//            List<HdCall> calls = hdMobileSrc.getCalls();
//            if (CollectionUtils.isNotEmpty(calls)) {
//                BwOperateVoice bwOperateVoice = null;
//                for (HdCall hdCall : calls) {
//                    try {
//                        List<HdCallItem> items = hdCall.getItems();
//                        if (CollectionUtils.isNotEmpty(items)) {
//                            for (HdCallItem hdCallItem : items) {
//                                if (hdCallItem == null) {
//                                    continue;
//                                }
//                                // logger.info(JSON.toJSONString(hdCallItem));
//                                bwOperateVoice = new BwOperateVoice();
//                                bwOperateVoice.setCall_time(hdCallItem.getTime());
//                                bwOperateVoice.setUpdateTime(new Date());
//                                bwOperateVoice.setCall_type("DIAL".equals(hdCallItem.getDial_type()) ? 1 : 2);
//                                if (hdCallItem.getPeer_number() != null && hdCallItem.getPeer_number().length() > 20) {
//                                    bwOperateVoice.setReceive_phone(hdCallItem.getPeer_number().substring(0, 20));
//                                } else {
//                                    bwOperateVoice.setReceive_phone(hdCallItem.getPeer_number());
//                                }
//                                bwOperateVoice.setTrade_time(hdCallItem.getDuration() + "");
//                                list.add(bwOperateVoice);
//                            }
//                        }
//
//                    } catch (Exception e) {
//                        logger.error(orderNo + "插入单条通话记录异常，忽略该条记录", e);
//                    }
//                }
//            }
//        }
//        return list;
//    }
//
//}
