///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.waterelephant.drainage.service.DrainageService;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.BwBankCard;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwContactList;
//import com.waterelephant.entity.BwIdentityCard2;
//import com.waterelephant.entity.BwMerchantOrder;
//import com.waterelephant.entity.BwOperateBasic;
//import com.waterelephant.entity.BwOperateVoice;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderProcessRecord;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.entity.BwOverdueRecord;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.entity.BwProductDictionary;
//import com.waterelephant.entity.BwRepaymentPlan;
//import com.waterelephant.entity.BwWorkInfo;
//import com.waterelephant.jiedianqian.entity.UserCheckResp;
//import com.waterelephant.service.BwOperateBasicService;
//import com.waterelephant.service.BwOperateVoiceService;
//import com.waterelephant.service.BwOrderAuthService;
//import com.waterelephant.service.BwOrderProcessRecordService;
//import com.waterelephant.service.BwOrderRongService;
//import com.waterelephant.service.BwOverdueRecordService;
//import com.waterelephant.service.BwProductDictionaryService;
//import com.waterelephant.service.IBwBankCardService;
//import com.waterelephant.service.IBwBorrowerService;
//import com.waterelephant.service.IBwMerchantOrderService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.service.IBwRepaymentPlanService;
//import com.waterelephant.service.IBwWorkInfoService;
//import com.waterelephant.service.impl.BwContactListService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.jdq.Address_book;
//import com.waterelephant.sxyDrainage.entity.jdq.AppData;
//import com.waterelephant.sxyDrainage.entity.jdq.Basic;
//import com.waterelephant.sxyDrainage.entity.jdq.Calls;
//import com.waterelephant.sxyDrainage.entity.jdq.DeviceInfo;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqBindCardReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCalculateReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCalculateResponse;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCardInfoReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCheckUserData;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCheckUserResp;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqContact;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqContractReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqOrderInfoRequest;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqPullOrderStatusReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqRepaymentReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqResponse;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqWithdrawReq;
//import com.waterelephant.sxyDrainage.entity.jdq.Loan_info;
//import com.waterelephant.sxyDrainage.entity.jdq.Operator;
//import com.waterelephant.sxyDrainage.entity.jdq.User_contact;
//import com.waterelephant.sxyDrainage.entity.jdq.User_info;
//import com.waterelephant.sxyDrainage.exception.JdqException;
//import com.waterelephant.sxyDrainage.service.AsyncJdqTask;
//import com.waterelephant.sxyDrainage.service.BwJdqAppDataService;
//import com.waterelephant.sxyDrainage.service.BwJdqDeviceInfoService;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.JdqService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.OrderStatusEnum;
//import com.waterelephant.sxyDrainage.utils.SxyDrainageConstant;
//import com.waterelephant.sxyDrainage.utils.haodai.HaoDaiUtils;
//import com.waterelephant.sxyDrainage.utils.jdq.BankcardList;
//import com.waterelephant.sxyDrainage.utils.jdq.Base64;
//import com.waterelephant.sxyDrainage.utils.jdq.GzipUtil;
//import com.waterelephant.sxyDrainage.utils.jdq.JdqConstant;
//import com.waterelephant.sxyDrainage.utils.jdq.JdqUtils;
//import com.waterelephant.sxyDrainage.utils.jdq.Md5Utils;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.DoubleUtil;
//import com.waterelephant.utils.JsonUtils;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//
//import org.apache.commons.lang3.StringUtils;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.text.DecimalFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//
///**
// * 借点钱
// *
// * @author xanthuim
// * @since 2018-07-24
// */
//@Service
//public class JdqServiceImpl implements JdqService {
//
//    private Logger logger = LoggerFactory.getLogger(JdqServiceImpl.class);
//
//    private final static String CHANNEL_ID = JdqConstant.get("channel_id");
//    private final static String CHANNEL_NO = JdqConstant.get("channel_no");
//    private final static String CONTRACT_KEY = JdqConstant.get("contract_key");
//
//    private final static String CONTRACT_URL = JdqConstant.get("contract_url");
//    // 审批期数
//    private final static String APPROVAL_PERIODS = "4";
//    // 审批每期天数
//    private final static String APPROVAL_PERIOD_DAYS = "7";
//    // 审批总天数
//    private final static String APPROVAL_DAYS = "28";
//
//    @Autowired
//    private DrainageService drainageService;
//
//    @Autowired
//    private IBwBorrowerService bwBorrowerService;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//
//    @Autowired
//    private IBwRepaymentPlanService bwRepaymentPlanService;
//    @Autowired
//    private BwOverdueRecordService bwOverdueRecordService;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private BwOrderRongService bwOrderRongService;
//    @Autowired
//    private BwOrderProcessRecordService bwOrderProcessRecordService;
//    @Autowired
//    private IBwWorkInfoService bwWorkInfoService;
//    @Autowired
//    private BwContactListService bwContactListService;
//    @Autowired
//    private BwOperateBasicService bwOperateBasicService;
//    @Autowired
//    private BwOperateVoiceService bwOperateVoiceService;
//    @Autowired
//    private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//    @Autowired
//    private IBwBankCardService bwBankCardService;
//    @Autowired
//    private BwProductDictionaryService bwProductDictionaryService;
//    @Autowired
//    private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//    @Autowired
//    private CommonService commonService;
//    @Autowired
//    private BwOrderAuthService bwOrderAuthService;
//
//    @Autowired
//    private AsyncJdqTask asyncJdqTask;
//
//    @Autowired
//    BwJdqAppDataService appDataService;
//    @Autowired
//    BwJdqDeviceInfoService deviceInfoService;
//
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//
//    /**
//     * 1.1 检测用户接口
//     */
//    @Override
//    public JdqResponse checkUser(JdqCheckUserData jdqCheckUserData) throws ParseException {
//        long sessionId = System.currentTimeMillis();
//        JdqResponse jdqResponse = new JdqResponse();
//
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqCheckUserData.getPhone());
//
//        JdqCheckUserResp jdqCheckUserResp = new JdqCheckUserResp();
//        String idNumber = jdqCheckUserData.getId_number().replace("*", "%");
//        String phone = jdqCheckUserData.getPhone().replace("*", "%");
//        String userName = jdqCheckUserData.getUser_name();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        if (StringUtils.isBlank(userName)) {
//            logger.info(sessionId + " 结束JdqServiceImpl.checkUser()方法，返回结果：姓名为空");
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("姓名为空");
//            return jdqResponse;
//        }
//        if (StringUtils.isBlank(idNumber)) {
//            logger.info(sessionId + " 结束JdqServiceImpl.checkUser()方法，返回结果：身份证号码为空");
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("身份证号码为空");
//            return jdqResponse;
//        }
//        if (StringUtils.isBlank(phone)) {
//            logger.info(sessionId + " 结束JdqServiceImpl.checkUser()方法，返回结果：手机号为空");
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("手机号为空");
//            return jdqResponse;
//        }
//        // 判断我方新老用户
//        boolean oldUserBoolean = drainageService.oldUserFilter2(userName, phone, idNumber);
//
//        // 判断借点钱新老用户
//        // 0-借点钱老用户 1-新用户 2-合作方老用户
//        Integer channeId = Integer.valueOf(CHANNEL_ID);
//        // TODO 新增方法
//        BwBorrower bwBorrower = bwBorrowerService.checkOldUserByChannel(phone, idNumber, userName, channeId);
//
//        if (oldUserBoolean) {
//            jdqCheckUserResp.setUser_type("2");
//            if (bwBorrower != null) {
//                jdqCheckUserResp.setUser_type("0");
//            }
//        } else {
//            jdqCheckUserResp.setUser_type("1");
//        }
//
//        // if_can_loan user_type can_loan_time amount reason
//        // 第一步：是否黑名单
//        boolean isBlackUser = drainageService.isBlackUser2(userName, phone, idNumber);
//        if (isBlackUser) {
//            logger.info(sessionId + " 结束JdqServiceImpl.checkUser()方法，返回结果：命中黑名单规则");
//            jdqCheckUserResp.setIf_can_loan("0");
//            jdqCheckUserResp.setReason("1");
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("命中黑名单规则");
//            jdqResponse.setData(jdqCheckUserResp);
//            return jdqResponse;
//        }
//        // 第二步：是否进行中的订单
//        boolean isProcessingOrder = drainageService.isPocessingOrder2(userName, phone, idNumber);
//        if (isProcessingOrder) {
//            jdqCheckUserResp.setReason("2");
//            jdqCheckUserResp.setIf_can_loan("0");
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("存在进行中的订单");
//            jdqResponse.setData(jdqCheckUserResp);
//
//            return jdqResponse;
//        }
//        // 第三步：是否有被拒记录
//        UserCheckResp isRejectRecord = drainageService.isRejectRecord2(userName, phone, idNumber);
//        if (isRejectRecord.getIf_can_loan().equals("0")) {
//            // 0-否；1-是
//            logger.info(sessionId + " 结束JdqServiceImpl.checkUser()方法，返回结果：命中被拒规则");
//            jdqCheckUserResp.setIf_can_loan("0");
//            jdqCheckUserResp.setReason("3");
//            if (isRejectRecord.getCan_loan_time() != null) {
//                jdqCheckUserResp.setCan_loan_time(sdf.format(sdf.parse(isRejectRecord.getCan_loan_time())));
//            }
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("命中被拒规则");
//            jdqResponse.setData(jdqCheckUserResp);
//            return jdqResponse;
//        }
//        // 第四步：判断是否年龄超限
//        Calendar cal = Calendar.getInstance();
//        String year = idNumber.substring(6, 10);
//        if (year.indexOf("%") == -1) {
//            int iCurrYear = cal.get(Calendar.YEAR);
//            int age = iCurrYear - Integer.valueOf(year);
//            if (age > 55 || age <= 21) {
//                logger.info(sessionId + " 结束JdqServiceImpl.checkUser()方法，返回结果：用户年龄超限");
//                jdqCheckUserResp.setIf_can_loan("0");
//                jdqCheckUserResp.setReason("3");
//                jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//                jdqResponse.setDesc("用户年龄超限");
//                jdqResponse.setData(jdqCheckUserResp);
//                return jdqResponse;
//            }
//        }
//        // 第四步：查询借款信息（规则已过，可以借款）
//
//        jdqCheckUserResp.setIf_can_loan("1");
//        jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//        jdqResponse.setDesc("该用户允许通过");
//        jdqResponse.setData(jdqCheckUserResp);
//
//        return jdqResponse;
//
//    }
//
//    /**
//     * 重复进件
//     *
//     * @param thirdOrder
//     * @return
//     */
//    private boolean existsOrder(String thirdOrder) {
//        BwOrderRong orderRong = bwOrderRongService.getByOrderNo(thirdOrder);
//        if (orderRong == null) {
//            return false;
//        }
//        BwOrder order = bwOrderService.findBwOrderById(orderRong.getOrderId().toString());
//        if (order == null) {
//            return false;
//        }
//        if (order.getSubmitTime() == null) {
//            return false;
//        }
//        return true;
//    }
//
//    /**
//     * APP信息
//     *
//     * @param map
//     * @param orderId
//     * @return
//     */
//    private void appData(Map<String, Object> map, long orderId) {
//        if (Objects.isNull(map)) {
//            logger.warn("APP_Data为空，是否未传？");
//            return;
//        }
//        for (Map.Entry<String, Object> enty : map.entrySet()) {
//            JSONObject jsonObject = (JSONObject) enty.getValue();
//            String name = jsonObject.getString("name");
//            String version = jsonObject.getString("version");
//
//            AppData data = new AppData();
//            data.setPackageName(enty.getKey());
//            data.setName(name);
//            data.setVersion(version);
//            data.setOrderId(orderId);
//            appDataService.saveOrUpdate(data);
//        }
//    }
//
//    @Override
//    public JdqResponse saveOrder(JdqOrderInfoRequest jdqOrderInfoRequest) {
//        //订单号为空
//        if (StringUtils.isBlank(jdqOrderInfoRequest.getJdq_order_id())) {
//            throw new JdqException("借点钱订单号为空，请检查！");
//        }
//
//        long sessionId = System.currentTimeMillis();
//        JdqResponse jdqResponse = new JdqResponse();
//
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqOrderInfoRequest.getJdq_order_id());
//
//        //重复进件
//        if (existsOrder(jdqOrderInfoRequest.getJdq_order_id())) {
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("已经进件，属于重复进件");
//            return jdqResponse;
//        }
//
//        // 解压运营商数据
//        String operatorString = GzipUtil.uncompress(Base64.decode(jdqOrderInfoRequest.getOperator()), "utf-8");
//        Operator operator = JSONObject.parseObject(operatorString, Operator.class);
//        if (operator == null) {
//            throw new JdqException("用户运营商数据为空，请检查！");
//        }
//
//        // 封装订单推送信息
//        String jdqOrderId = jdqOrderInfoRequest.getJdq_order_id();
//        Loan_info loanInfo = jdqOrderInfoRequest.getLoan_info();
//        User_info userInfo = jdqOrderInfoRequest.getUser_info();
//        List<Address_book> addressBooks = jdqOrderInfoRequest.getAddress_book();
//        User_contact userContact = jdqOrderInfoRequest.getUser_contact();
//        // 验证参数
//        String check = JdqUtils.checkSaveOrder(jdqOrderInfoRequest);
//        if (check != null) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL);
//            jdqResponse.setDesc(check);
//            return jdqResponse;
//        }
//        String phone = userInfo.getPhone();
//        String name = userInfo.getName();
//        String idCard = userInfo.getId_card();
//        Integer channelId = Integer.valueOf(CHANNEL_ID);
//
//        BwBorrower borrower;
//        long borrowerId;
//        boolean progressOrder;
//        try {
//            // 新增或更新借款人
//            logger.info(sessionId + ">>> 开始新增/更新借款人ID：" + phone + "，" + idCard);
//            borrower = thirdCommonService.addOrUpdateBorrower(sessionId, name, idCard, phone, channelId);
//            borrowerId = borrower.getId();
//            logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//
//
//            // 根据身份证号查询进行中的工单
//            progressOrder = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        if (progressOrder) {
//            throw new JdqException("该用户存在进行中的订单");
//        }
//
//        Integer productId = Integer.valueOf(SxyDrainageConstant.productId);
//        // 判断是否有草稿状态的订单
//        BwOrder bwOrder = new BwOrder();
//        bwOrder.setBorrowerId(borrowerId);
//        bwOrder.setStatusId(1L);
//        bwOrder.setProductType(2);
//        bwOrder.setChannel(channelId);
//        bwOrder.setProductId(productId);
//
//        // 先查询草稿状态的订单
//        List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
//        if (boList != null && boList.size() > 0) {
//            bwOrder = boList.get(boList.size() - 1);
//            bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwOrder.setProductType(2);
//            bwOrder.setExpectMoney(Double.valueOf(loanInfo.getAmount()));
//            bwOrder.setExpectNumber(4);
//            bwOrder.setRepayType(2);
//            bwOrderService.updateBwOrder(bwOrder);
//        } else {
//            bwOrder = new BwOrder();
//            bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//            bwOrder.setBorrowerId(borrower.getId());
//            bwOrder.setStatusId(1L);
//            bwOrder.setCreateTime(Calendar.getInstance().getTime());
//            bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwOrder.setChannel(channelId);
//            bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//            bwOrder.setApplyPayStatus(0);
//            bwOrder.setProductId(productId);
//            bwOrder.setRepayType(2);
//            bwOrder.setProductType(2);
//            bwOrder.setExpectMoney(Double.valueOf(loanInfo.getAmount()));
//            bwOrder.setExpectNumber(4);
//            bwOrderService.addBwOrder(bwOrder);
//        }
//        try {
//            //征信系统需要
//            String key = "phone_apply";
//            Map<String, Object> params = new HashMap<>();
//            params.put("mobile", borrower.getPhone());
//            params.put("order_id", bwOrder.getId());
//            params.put("borrower_id", borrower.getId());
//            String value = JSON.toJSONString(params);
//            RedisUtils.rpush(key, value);
//        } catch (Exception e) {
//            logger.error("征信redis异常", e);
//        }
//
//        long orderId = bwOrder.getId();
//        logger.info(sessionId + ">>> 判断是否有草稿状态的订单：ID = " + orderId);
//
//        // 判断是否有融360订单
//        BwOrderRong bwOrderRong = new BwOrderRong();
//        bwOrderRong.setOrderId(orderId);
//        bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//        if (bwOrderRong == null) {
//            bwOrderRong = new BwOrderRong();
//            bwOrderRong.setOrderId(orderId);
//            bwOrderRong.setThirdOrderNo(jdqOrderId);
//            bwOrderRong.setChannelId(Long.valueOf(channelId));
//            bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//            bwOrderRongService.save(bwOrderRong);
//        } else {
//            if (null == bwOrderRong.getChannelId()) {
//                bwOrderRong.setChannelId(Long.valueOf(channelId));
//            }
//            bwOrderRong.setThirdOrderNo(jdqOrderId);
//            bwOrderRongService.update(bwOrderRong);
//        }
//        logger.info(sessionId + ">>> 判断是否有融360订单");
//
//        // 判断是否有商户订单信息
//        BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//        bwMerchantOrder.setOrderId(orderId);
//        bwMerchantOrder = bwMerchantOrderServiceImpl.selectByAtt(bwMerchantOrder);
//        if (bwMerchantOrder == null) {
//            bwMerchantOrder = new BwMerchantOrder();
//            bwMerchantOrder.setBorrowerId(borrowerId);
//            bwMerchantOrder.setCreateTime(Calendar.getInstance().getTime());
//            bwMerchantOrder.setExt3("0");
//            bwMerchantOrder.setMerchantId(0L);
//            bwMerchantOrder.setOrderId(orderId);
//            bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwMerchantOrderServiceImpl.insertByAtt(bwMerchantOrder);
//        } else {
//            bwMerchantOrder.setBorrowerId(borrowerId);
//            bwMerchantOrder.setExt3("0");
//            bwMerchantOrder.setMerchantId(0L);
//            bwMerchantOrder.setOrderId(orderId);
//            bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwMerchantOrderServiceImpl.updateByAtt(bwMerchantOrder);
//        }
//        logger.info(sessionId + "开始更新借款人" + borrowerId + "工作信息");
//        saveOrUpdateWorkInfo(orderId, borrowerId, userInfo);
//        // 插入个人认证记录
//        thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId);
//        logger.info(sessionId + ">>> 判断是否有工作信息");
//
//        // 通讯录
//        if (addressBooks == null || addressBooks.size() < 3) {
//            throw new JdqException("通讯录、联系人为空或者数量小于3个，请检查！");
//        }
//
//        Set<JdqContact> listContact = new HashSet<>();
//        List<BwContactList> listConS = new ArrayList<>();
//        for (Address_book addressBook : addressBooks) {
//            if (CommUtils.isNull(addressBook.getName())) {
//                continue;
//            }
//            if (CommUtils.isNull(addressBook.getMobile())) {
//                continue;
//            }
//            BwContactList bwContactList = new BwContactList();
//            bwContactList.setBorrowerId(borrowerId);
//            bwContactList.setPhone(addressBook.getMobile());
//            bwContactList.setName(addressBook.getName());
//            listConS.add(bwContactList);
//
//            // 从通讯录中获取联系人，需要去重
//            JdqContact jdqContact = new JdqContact();
//            if (listContact.size() < 3 && JdqUtils.checkChinese(addressBook.getName())) {
//                if (addressBook.getMobile().equals(userContact.getMobile())
//                        || addressBook.getMobile().equals(userContact.getMobile_spare())) {
//                    logger.warn(sessionId + "通讯录与联系人相同：{}", addressBook);
//                    continue;
//                }
//                jdqContact.setName(addressBook.getName());
//                jdqContact.setPhone(handlePhone(addressBook.getMobile()));
//                listContact.add(jdqContact);
//            }
//        }
//        bwContactListService.batchAddContact(listConS, borrowerId);
//        logger.info(sessionId + ">>> 处理通讯录信息 ");
//
//        //如果通讯录信息不完整就有问题
//        if (listContact.size() == 0) {
//            logger.info(sessionId + ">>> 通讯录信息没有姓名或电话，请检查！");
//            throw new JdqException("通讯录信息没有姓名或电话，请检查！");
//        }
//
//
//        // 运营商
//        addOrUpdateOperate(sessionId, orderId, operator, borrowerId);
//        logger.info(sessionId + ">>> 处理运营商信息 ");
//
//        // 通话记录
//        logger.info(phone + "开始更新通话记录......");
//
//        addOperateVoice(operator.getCalls(), borrower.getId());
//
//        // 认证图片
//        String frontFile = userInfo.getId_positive();
//        String backFile = userInfo.getId_negative();
//        String natureFile = userInfo.getFace();
//        if (StringUtils.isBlank(frontFile) || StringUtils.isBlank(backFile) || StringUtils.isBlank(natureFile)) {
//            logger.info(sessionId + ">>> 身份证或活体照为空 ");
//            throw new JdqException("身份证或活体照为空，请检查！");
//        }
//
//        //异步处理运营商数据
//        asyncJdqTask.handleOperator(bwOrder, borrower, bwOrderRong, channelId, operator,
//                frontFile, backFile, natureFile);
//
//        // 保存身份证信息
//        BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//        bwIdentityCard.setBorrowerId(borrowerId);
//        bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
//        logger.info(sessionId + ">>> borrowerId =" + borrowerId);
//        if (bwIdentityCard == null) {
//            bwIdentityCard = new BwIdentityCard2();
//            bwIdentityCard.setBorrowerId(borrowerId);
//            bwIdentityCard.setCreateTime(new Date());
//            bwIdentityCard.setIdCardNumber(idCard);
//            bwIdentityCard.setName(name);
//            bwIdentityCard.setUpdateTime(new Date());
//            bwIdentityCard.setValidDate(userInfo.getId_start_date().replace("-", ".")
//                    + "-" + userInfo.getId_expiry_date().replace("-", "."));
//            bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
//        } else {
//            bwIdentityCard.setIdCardNumber(idCard);
//            bwIdentityCard.setName(name);
//            bwIdentityCard.setUpdateTime(new Date());
//            bwIdentityCard.setValidDate(userInfo.getId_start_date().replace("-", ".")
//                    + "-" + userInfo.getId_expiry_date().replace("-", "."));
//            bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//        }
//        // 插入身份认证记录
//        thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);
//        logger.info(sessionId + ">>> 处理身份证信息");
//
//        // 亲属联系人,个人信息
//        BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//        userContact = jdqOrderInfoRequest.getUser_contact();
//
//        if (bwPersonInfo == null) {
//            bwPersonInfo = new BwPersonInfo();
//            bwPersonInfo.setWechat(userInfo.getPhone());
//            bwPersonInfo.setQqchat(userInfo.getQq());
//            bwPersonInfo.setCreateTime(new Date());
//            bwPersonInfo.setUpdateTime(new Date());
//            bwPersonInfo.setOrderId(orderId);
//            bwPersonInfo.setCityName(userInfo.getCity());
//            bwPersonInfo.setAddress(userInfo.getLiving_address());
//            bwPersonInfo.setEmail(userInfo.getCommon_email());
//            bwPersonInfo.setRelationName(userContact.getName());
//            bwPersonInfo.setRelationPhone(userContact.getMobile());
//            bwPersonInfo.setUnrelationName(userContact.getName_spare());
//            bwPersonInfo.setUnrelationPhone(userContact.getMobile_spare());
//            //遍历从通讯录中的联系人
//            iteratorContact(listContact, bwPersonInfo);
//            logger.info(sessionId + "处理添加新增联系人信息字段-->>");
//
//            bwPersonInfoService.add(bwPersonInfo);
//
//        } else {
//            bwPersonInfo.setWechat(userInfo.getPhone());
//            bwPersonInfo.setQqchat(userInfo.getQq());
//            bwPersonInfo.setAddress(userInfo.getLiving_address());
//            bwPersonInfo.setEmail(userInfo.getCommon_email());
//            bwPersonInfo.setRelationName(userContact.getName());
//            bwPersonInfo.setRelationPhone(userContact.getMobile());
//            bwPersonInfo.setUnrelationName(userContact.getName_spare());
//            bwPersonInfo.setUnrelationPhone(userContact.getMobile_spare());
//            bwPersonInfo.setCityName(userInfo.getCity());
//            bwPersonInfo.setUpdateTime(new Date());
//            //遍历从通讯录中的联系人
//            iteratorContact(listContact, bwPersonInfo);
//            bwPersonInfoService.update(bwPersonInfo);
//        }
//
//        //设备信息
//        DeviceInfo deviceInfo = jdqOrderInfoRequest.getDeviceInfo();
//        deviceInfo.setOrderId(orderId);
//        deviceInfoService.saveOrUpdate(deviceInfo);
//
//        //APP信息，Android设备才会有
//        if (DeviceInfo.DeviceType.Android.getType().equals(deviceInfo.getDeviceType())) {
//            appData(jdqOrderInfoRequest.getAppData(), orderId);
//        }
//
//        sumbit(borrower, bwOrder, bwOrderRong);
//
//        Map<String, String> result = new HashMap<>(16);
//        result.put("order_id", String.valueOf(bwOrder.getOrderNo()));
//        jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//        jdqResponse.setDesc("订单【" + bwOrder.getOrderNo() + "】进件成功");
//        jdqResponse.setData(result);
//        return jdqResponse;
//    }
//
//    /**
//     * 提交机审
//     *
//     * @param bwBorrower
//     * @param bwOrder
//     * @param bwOrderRong
//     */
//    private void sumbit(BwBorrower bwBorrower, BwOrder bwOrder, BwOrderRong bwOrderRong) {
//        //运营商数据解析完毕后修改订单状态
//        SystemAuditDto systemAuditDto = new SystemAuditDto();
//        systemAuditDto.setIncludeAddressBook(0);
//        systemAuditDto.setOrderId(bwOrder.getId());
//        systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
//        systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//        systemAuditDto.setName(bwBorrower.getName());
//        systemAuditDto.setPhone(bwBorrower.getPhone());
//        systemAuditDto.setIdCard(bwBorrower.getIdCard());
//        systemAuditDto.setChannel(bwOrderRong.getChannelId().intValue());
//        systemAuditDto.setThirdOrderId(bwOrderRong.getThirdOrderNo());
//
//        Long reLong = RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
//                JsonUtils.toJson(systemAuditDto));
//        logger.info("修改订单状态，并放入" + reLong + "条redis");
//        bwOrder.setStatusId(2L);
//        bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//        bwOrderService.updateBwOrder(bwOrder);
//        logger.info("开始修改订单提交时间");
//        // 修改工单进程表
//        BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//        bwOrderProcessRecord.setOrderId(bwOrder.getId());
//        bwOrderProcessRecord.setSubmitTime(new Date());
//        bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//    }
//
//    /**
//     * 处理通讯录手机号
//     *
//     * @param mobile
//     * @return
//     */
//    private static String handlePhone(String mobile) {
//        //去掉+号、空格
//        mobile = mobile.replace("+", "").replace(" ", "");
//        //去掉86
//        if (mobile.startsWith("86") &&
//                mobile.length() == 13) {
//            mobile = mobile.substring(2).trim();
//        }
//        return mobile;
//    }
//
//    /**
//     * 遍历联系人
//     *
//     * @param listContact  通讯录
//     * @param bwPersonInfo 联系人
//     */
//    private void iteratorContact(Set<JdqContact> listContact, BwPersonInfo bwPersonInfo) {
//        logger.info("--------从通讯录获取联系人---------");
//        //同事 朋友1 朋友2
//        int index = 0;
//        for (JdqContact contact : listContact) {
//            if (contact.getPhone().length() > 30) {
//                logger.warn("电话超长，需要截取：{}", contact.getPhone());
//                contact.setPhone(contact.getPhone().substring(0, 30));
//            }
//            if (contact.getName().length() > 30) {
//                logger.warn("姓名超长，需要截取：{}", contact.getName());
//                contact.setName(contact.getName().substring(0, 30));
//            }
//            if (index == 0) {
//                bwPersonInfo.setColleagueName(contact.getName());
//                bwPersonInfo.setColleaguePhone(contact.getPhone());
//            }
//            if (index == 1) {
//                bwPersonInfo.setFriend1Name(contact.getName());
//                bwPersonInfo.setFriend1Phone(contact.getPhone());
//            }
//            if (index == 2) {
//                bwPersonInfo.setFriend2Name(contact.getName());
//                bwPersonInfo.setFriend2Phone(contact.getPhone());
//            }
//            index++;
//        }
//    }
//
//    private void saveOrUpdateWorkInfo(Long orderId, Long borrowerId, User_info userInfo) {
//        // 判断是否有工作信息
//        BwWorkInfo bwWorkInfo = new BwWorkInfo();
//        bwWorkInfo.setOrderId(orderId);
//        bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//        String companyWorkYear = userInfo.getCompany_work_year();
//        String companyName = userInfo.getCompany_name();
//        String income = userInfo.getIncome();
//        String workProfession = userInfo.getWork_profession();
//
//        if (null == bwWorkInfo) {
//            bwWorkInfo = new BwWorkInfo();
//            bwWorkInfo.setOrderId(orderId);
//            // 默认值
//            bwWorkInfo.setCallTime("10:00 - 12:00");
//            bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//            bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
//            bwWorkInfo.setWorkYears(CommUtils.isNull(companyWorkYear) ? "" : companyWorkYear);
//            bwWorkInfo.setComName(CommUtils.isNull(companyName) ? "" : companyName);
//            bwWorkInfo.setIncome(CommUtils.isNull(income) ? "" : income);
//
//            bwWorkInfo.setIndustry(CommUtils.isNull(workProfession) ? "" : workProfession);
//            bwWorkInfoService.save(bwWorkInfo, borrowerId);
//            logger.info("保存借款人" + borrowerId + "工作信息成功");
//        } else {
//            // 默认值
//            bwWorkInfo.setCallTime("10:00 - 12:00");
//            bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//            bwWorkInfo.setWorkYears(bwWorkInfo.getWorkYears());
//            bwWorkInfo.setComName(bwWorkInfo.getComName());
//            bwWorkInfo.setIncome(bwWorkInfo.getIncome());
//            bwWorkInfo.setIndustry(bwWorkInfo.getIndustry());
//            bwWorkInfoService.update(bwWorkInfo);
//            logger.info("更新借款人" + borrowerId + "工作信息成功");
//        }
//
//    }
//
//    private void addOrUpdateOperate(long sessionId, Long orderId, Operator operator, Long borrowerId) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        logger.info("开始查询运营商记录，borrowerId={}，basic：{}", borrowerId, JSON.toJSONString(operator.getBasic()));
//        BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//        Basic basic = operator.getBasic();
//        if (CommUtils.isNull(bwOperateBasic)) {
//            logger.info("运营商记录不存在，开始新增，borrowerId=" + borrowerId);
//            // 添加基本信息
//            bwOperateBasic = new BwOperateBasic();
//            Date date = new Date();
//            bwOperateBasic.setUpdateTime(date);
//            bwOperateBasic.setCreateTime(date);
//            String datasource = operator.getDatasource();
//            // 转换运营商公司信息
//            datasource = convertDataSource(datasource);
//            // 号码类型
//            bwOperateBasic.setUserSource(datasource.toUpperCase());
//            // chinamobile：移动；chinaunicom：联通；chinatelecom：电信
//            bwOperateBasic.setIdCard(CommUtils.isNull(basic.getIdcard()) ? "" : basic.getIdcard());
//            // 注册该号码所填写的地址
//            bwOperateBasic.setAddr("无");
//            // 本机号码
//            bwOperateBasic.setPhone(basic.getCell_phone());
//            String phoneRemain = "0";
//            DecimalFormat df = new DecimalFormat("#0.00");
//            // 当前账户余额
//            bwOperateBasic.setPhoneRemain(df.format((Double.parseDouble(phoneRemain) / 100)));
//            bwOperateBasic.setRealName(CommUtils.isNull(basic.getReal_name()) ? "" : basic.getReal_name());
//            bwOperateBasic.setBorrowerId(borrowerId);
//
//            if (!CommUtils.isNull(basic.getReg_time())) {
//                try {
//                    bwOperateBasic.setRegTime(basic.getReg_time());
//                } catch (Exception e) {
//                    logger.info("reg_time为" + basic.getReg_time() + "===用户注册时间格式错误，无法存储！");
//                }
//            }
//
//            bwOperateBasicService.save(bwOperateBasic);
//
//        } else {
//            logger.info("运营商记录已存在，开始修改，borrowerId=" + borrowerId);
//            // 修改基本信息
//            String datasource = operator.getDatasource();
//            // 转换运营商公司信息
//            datasource = convertDataSource(datasource);
//            Date date = new Date();
//            bwOperateBasic.setUpdateTime(date);
//            // '号码类型'
//            bwOperateBasic.setUserSource(datasource.toUpperCase());
//            bwOperateBasic.setIdCard(CommUtils.isNull(basic.getIdcard()) ? "" : basic.getIdcard());
//            bwOperateBasic.setAddr("无");
//            // 本机号码
//            bwOperateBasic.setPhone(basic.getCell_phone());
//            String phoneRemain = "0";
//            DecimalFormat df = new DecimalFormat("#0.00");
//            // 当前账户余额
//            bwOperateBasic.setPhoneRemain(df.format((Double.parseDouble(phoneRemain) / 100)));
//            bwOperateBasic.setRealName(CommUtils.isNull(basic.getReal_name()) ? "" : basic.getReal_name());
//            bwOperateBasic.setBorrowerId(borrowerId);
//
//            if (!CommUtils.isNull(basic.getReg_time())) {
//
//                try {
//                    bwOperateBasic.setRegTime(basic.getReg_time());
//                } catch (Exception e) {
//                    logger.info("reg_time为" + basic.getReg_time() + "===用户注册时间格式错误，无法存储！");
//                }
//            }
//            bwOperateBasicService.update(bwOperateBasic);
//        }
//        // 1：运营商
//        thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, Integer.valueOf(CHANNEL_ID));
//    }
//
//    private String convertDataSource(String datasource) {
//        if (datasource.startsWith("chinamobile")) {
//            datasource = datasource.substring(0, "chinamobile".length());
//        } else if (datasource.startsWith("chinaunicom")) {
//            datasource = datasource.substring(0, "chinaunicom".length());
//        } else if (datasource.startsWith("chinatelecom")) {
//            datasource = datasource.substring(0, "chinatelecom".length());
//        }
//        return datasource;
//    }
//
//    /**
//     * 3.5 订单试算
//     */
//    @Override
//    public JdqResponse calculateOrder(JdqCalculateReq jdqCalculateReq) {
//        long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "开始进入JdqServiceImpl.calculateOrder方法");
//        JdqResponse jdqResponse = new JdqResponse();
//
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqCalculateReq.getJdq_order_id());
//
//        BigDecimal amount = jdqCalculateReq.getAmount();
//        String jdqOrderId = jdqCalculateReq.getJdq_order_id();
//        logger.info("JdqServiceImpl.calculateOrder获取到的三方订单号为：" + jdqOrderId);
//        if (CommUtils.isNull(jdqOrderId) || CommUtils.isNull(amount)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("请求参数为空");
//            logger.info(sessionId + "：结束jdqResponse.calculateOrder 方法" + JSON.toJSONString(jdqResponse));
//            return jdqResponse;
//        }
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(jdqOrderId);
//        if (bwOrderRong == null) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("第三方订单不存在");
//            logger.info(sessionId + "：结束jdqResponse.calculateOrder 方法" + JSON.toJSONString(jdqResponse));
//            return jdqResponse;
//        }
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        // 如果当前订单不存在表示订单基本信息未推送
//        if (CommUtils.isNull(bwOrder)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("我方不存在该订单");
//            logger.info(sessionId + "：结束jdqResponse.calculateOrder method：" + JSON.toJSONString(jdqResponse));
//            return jdqResponse;
//        }
//        int intValue = bwOrder.getStatusId().intValue();
//        if (intValue < 4 || intValue == 7 || intValue == 8) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("未审批订单");
//            return jdqResponse;
//        }
//        // 判断下给定的金额是否符合我们的审批金额
//        if (bwOrder.getBorrowAmount() != amount.doubleValue()) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("借款金额与审批金额不一致");
//            logger.info(sessionId + "：结束jdqResponse.calculateOrder method：" + JSON.toJSONString(jdqResponse));
//            return jdqResponse;
//        }
//
//        BwProductDictionary bwProductDictionary = bwProductDictionaryService
//                .findBwProductDictionaryById(Integer.valueOf(SxyDrainageConstant.productId));
//        // 分期利息率
//        Double interestRate = bwProductDictionary.getInterestRate();
//
//        JdqCalculateResponse jdqCalculateResponse = new JdqCalculateResponse();
//        Double borrowAmount = bwOrder.getBorrowAmount();
//        BigDecimal minAmount = new BigDecimal(bwOrder.getBorrowAmount());
//        BigDecimal maxAmount = new BigDecimal(bwOrder.getBorrowAmount());
//        BigDecimal multiple = new BigDecimal(100.00);
//        BigDecimal cardAmount = new BigDecimal(bwOrder.getBorrowAmount());
//        String[] firstRepayAmount = {borrowAmount / 4
//                + DrainageUtils.calculateRepayMoney(Double.valueOf(borrowAmount), 1, interestRate) + ""};
//        String[] loanTerms = {"4"};
//        int loanTermUnit = 2;
//        int loanTermDays = 7;
//        jdqCalculateResponse.setMax_amount(maxAmount);
//        jdqCalculateResponse.setMin_amount(minAmount);
//        jdqCalculateResponse.setMultiple(multiple);
//        jdqCalculateResponse.setCard_amount(cardAmount);
//        jdqCalculateResponse.setLoan_term_days(loanTermDays);
//        jdqCalculateResponse.setLoan_term_unit(loanTermUnit);
//        jdqCalculateResponse.setFirst_repay_amount(firstRepayAmount);
//        jdqCalculateResponse.setLoan_terms(loanTerms);
//
//        jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//        jdqResponse.setDesc("试算接口请求成功");
//        jdqResponse.setData(jdqCalculateResponse);
//        logger.info(sessionId + "执行JdqServiceImpl.calculateOrder()方法返回数据： " + JSONObject.toJSONString(jdqResponse));
//        return jdqResponse;
//    }
//
//    @Deprecated
//    @Override
//    public JdqResponse saveBindCardPre(JdqBindCardReq jdqBindCardReq) {
//        JdqResponse jdqResponse = new JdqResponse();
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqBindCardReq.getPhone());
//
//        String bankCard = jdqBindCardReq.getCard_no();
//        String idenCard = jdqBindCardReq.getId_number();
//        String userMobile = jdqBindCardReq.getPhone();
//        String userName = jdqBindCardReq.getName();
//        String bankCode = jdqBindCardReq.getBank_code();
//        // 处理银行编码
//        String code = HaoDaiUtils.convertHdToBaoFuCode(bankCode);
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setBankCardNo(bankCard);
//        // 前置
//        drainageBindCardVO.setBindType("1");
//        drainageBindCardVO.setIdCardNo(idenCard);
//        drainageBindCardVO.setNotify(true);
//        drainageBindCardVO.setPhone(userMobile);
//        drainageBindCardVO.setName(userName);
//        drainageBindCardVO.setChannelId(Integer.valueOf(CHANNEL_ID));
//        drainageBindCardVO.setBankCode(code);
//        drainageBindCardVO.setRegPhone(userMobile);
//        logger.info("绑卡之前数据" + JSON.toJSONString(drainageBindCardVO));
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(System.currentTimeMillis(),
//                drainageBindCardVO);
//        if (drainageRsp != null) {
//            if ("0000".equals(drainageRsp.getCode())) {
//                jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//                jdqResponse.setDesc("预绑卡成功");
//            } else {
//                jdqResponse.setCode(JdqResponse.CODE_FAIL);
//                jdqResponse.setDesc(drainageRsp.getMessage());
//            }
//        } else {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("绑定银行卡返回参数为空");
//        }
//
//        return jdqResponse;
//    }
//
//    @Deprecated
//    @Override
//    public JdqResponse saveBindCardWithCode(JdqBindCardReq jdqBindCardReq) {
//        JdqResponse jdqResponse = new JdqResponse();
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqBindCardReq.getPhone());
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setBankCardNo(jdqBindCardReq.getCard_no());
//        drainageBindCardVO.setBindType("1");
//        drainageBindCardVO.setNotify(true);
//        drainageBindCardVO.setChannelId(Integer.valueOf(CHANNEL_ID));
//        drainageBindCardVO.setVerifyCode(jdqBindCardReq.getSms_code());
//        drainageBindCardVO.setPhone(jdqBindCardReq.getPhone());
//        logger.info("开始进入通用确认绑卡" + JSON.toJSONString(drainageBindCardVO));
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(System.currentTimeMillis(),
//                drainageBindCardVO);
//        if (drainageRsp != null) {
//            if ("0000".equals(drainageRsp.getCode())) {
//                jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//                jdqResponse.setDesc("绑卡成功");
//            } else {
//                jdqResponse.setCode(JdqResponse.CODE_FAIL);
//                jdqResponse.setDesc(drainageRsp.getMessage());
//            }
//        } else {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("绑定银行卡返回参数为空");
//        }
//
//        logger.info("结束借点钱绑定银行卡接口");
//        return jdqResponse;
//    }
//
//    @Override
//    public JdqResponse updateWithdraw(JdqWithdrawReq jdqWithdrawReq) {
//        long sessionId = System.currentTimeMillis();
//        JdqResponse jdqResponse = new JdqResponse();
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqWithdrawReq.getJdq_order_id());
//
//        String jdqOrderId = jdqWithdrawReq.getJdq_order_id();
//        BigDecimal loanAmount = jdqWithdrawReq.getLoan_amount();
//        String loanPeriods = jdqWithdrawReq.getLoan_periods();
//
//        if (CommUtils.isNull(jdqOrderId)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("请求参数为空");
//            return jdqResponse;
//        }
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(jdqOrderId);
//        if (bwOrderRong == null) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("第三方订单不存在");
//            return jdqResponse;
//        }
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        // 如果当前订单不存在表示订单基本信息未推送
//        if (CommUtils.isNull(bwOrder)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("我方不存在该订单");
//            return jdqResponse;
//        }
//
//        //防止重复提现
//        if (bwOrder.getStatusId() == OrderStatusEnum.REPAYMENT.getStatus() ||
//                bwOrder.getStatusId() == OrderStatusEnum.CONTRACT.getStatus()
//                || bwOrder.getStatusId() == OrderStatusEnum.BOND.getStatus()
//                || bwOrder.getStatusId() == OrderStatusEnum.BOND_DISTRIBUTING.getStatus()) {
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("已提现，属于重复提现");
//            return jdqResponse;
//        }
//
//        if (bwOrder.getStatusId() != OrderStatusEnum.SIGN.getStatus()) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("请检查用户订单状态");
//            return jdqResponse;
//        }
//        // 比较贷款金额
//        if (bwOrder.getBorrowAmount() != loanAmount.doubleValue()) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("贷款金额不一致");
//            return jdqResponse;
//        }
//        // 固定4期28天
//        if (!APPROVAL_PERIODS.equals(loanPeriods)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("期数不是4期");
//            return jdqResponse;
//        }
//        Long orderId = bwOrder.getId();
//        /*
//            从认证表中取是否签约（BwOrderAuth表），之前是从redis中取，但是因为业务有变动，直接从数据库取
//         */
//        if (bwOrderAuthService.findBwOrderAuth(orderId, 12) == null) {
//            jdqResponse.setCode(JdqResponse.CODE_UNCONFIRMED);
//            jdqResponse.setDesc("请点击水象分期借款信息确认页【马上拿钱】按钮");
//            return jdqResponse;
//        }
//        logger.info("成功取出H5页面key标识" + CONTRACT_KEY + orderId);
//
//        BwProductDictionary bwProductDictionary = bwProductDictionaryService
//                .findBwProductDictionaryById(bwOrder.getProductId());
//        Double contractMonthRate = bwProductDictionary.getpBorrowRateMonth();
//        // 该字段废弃，关联产品表的期限
//        bwOrder.setRepayTerm(1);
//        bwOrder.setBorrowRate(bwProductDictionary.getpInvestRateMonth());
//        bwOrder.setContractRate(bwProductDictionary.getpInvesstRateYear());
//        bwOrder.setContractMonthRate(contractMonthRate);
//        bwOrder.setStatusId(11L);
//
//        Date nowDate = new Date();
//        // 工单修改时间
//        bwOrder.setUpdateTime(nowDate);
//        bwOrderService.updateBwOrder(bwOrder);
//
//        logger.info("签约成功===" + bwOrder.getId());
//        HashMap<String, String> hm = new HashMap<>(16);
//        hm.put("channelId", CommUtils.toString(bwOrder.getChannel()));
//        hm.put("orderId", String.valueOf(bwOrder.getId()));
//        hm.put("orderStatus", "11");
//        hm.put("result", "签约成功");
//        String hmData = JSON.toJSONString(hm);
//        RedisUtils.rpush("tripartite:orderStatusNotify:" + bwOrder.getChannel(), hmData);
//
//        // 保存签约时间
//        BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//        bwOrderProcessRecord.setOrderId(bwOrder.getId());
//        bwOrderProcessRecord.setSignTime(nowDate);
//        bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//
//        // 清除语音验证码，// 清除湛江仲裁委点击标识
//        RedisUtils.hdel("arbitration:isFirst", orderId.toString());
//        // 待生成合同队列
//        RedisUtils.lpush("system:contract", orderId.toString());
//        RedisUtils.rpush("order:sign", String.valueOf(orderId));
//
//        jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//        jdqResponse.setDesc("请求成功");
//
//
//        logger.info(sessionId + "执行JdqServiceImpl.withdraw()方法返回数据： " + JSONObject.toJSONString(jdqResponse));
//        return jdqResponse;
//    }
//
//    @Override
//    public JdqResponse updateActiveRepayment(JdqRepaymentReq jdqRepaymentReq) {
//        long sessionId = System.currentTimeMillis();
//        JdqResponse jdqResponse = new JdqResponse();
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqRepaymentReq.getJdq_order_id());
//
//        String orderNo = jdqRepaymentReq.getJdq_order_id();
//
//        DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, orderNo);
//        if (drainageRsp != null) {
//            if (drainageRsp.getCode().equals("000")) {
//                jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//                jdqResponse.setDesc("申请支付成功");
//            }
//            //该工单正在支付中
//            else if ("106".equals(drainageRsp.getCode())) {
//                jdqResponse.setCode(JdqResponse.CODE_UNCONFIRMED);
//                jdqResponse.setDesc(drainageRsp.getMessage());
//            } else {
//                jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//                jdqResponse.setDesc(drainageRsp.getMessage());
//            }
//        } else {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("我方接口返回信息为空");
//        }
//        return jdqResponse;
//    }
//
//    /**
//     * 3.14 合同接口
//     */
//    @Override
//    public JdqResponse loanContract(JdqContractReq jdqContractReq) {
//        long sessionId = System.currentTimeMillis();
//        JdqResponse jdqResponse = new JdqResponse();
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqContractReq.getJdq_order_id());
//
//        String jdqOrderId = jdqContractReq.getJdq_order_id();
//
//        if (CommUtils.isNull(jdqOrderId)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("第三方订单号不存在");
//            return jdqResponse;
//        }
//
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(jdqOrderId);
//        if (bwOrderRong == null) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("第三方订单不存在");
//            logger.info(sessionId + "：结束jdqResponse.loanContract 方法" + JSON.toJSONString(jdqResponse));
//            return jdqResponse;
//        }
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        // 如果当前订单不存在表示订单基本信息未推送
//        if (CommUtils.isNull(bwOrder)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("我方不存在该订单");
//            logger.info(sessionId + "：结束jdqResponse.loanContract method：" + JSON.toJSONString(jdqResponse));
//            return jdqResponse;
//        }
//        BwBorrower bwBorrower = new BwBorrower();
//        bwBorrower.setId(bwOrder.getBorrowerId());
//        bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//        if (CommUtils.isNull(bwBorrower)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("根据手机号查询不到对应的借款人");
//            logger.info("根据对应的" + bwOrder.getBorrowerId() + "查询不到对应的借款人");
//            return jdqResponse;
//        }
//
//        String phone = bwBorrower.getPhone();
//
//        String md5Data = Md5Utils.getMD5("phone=" + phone + "&order_no=" + jdqOrderId);
//        String contractUrl = CONTRACT_URL + "phone=" + phone + "&order_no=" + jdqOrderId + "&platform=4&params="
//                + md5Data + "&channelSign=" + CHANNEL_NO;
//        logger.info("回调的地址：" + contractUrl);
//
//        List<Map<String, String>> listMap = new ArrayList<>();
//        Map<String, String> map = new HashMap<>(16);
//        map.put("contract_name", "水象分期借款信息确认页");
//        map.put("url", contractUrl);
//        listMap.add(map);
//
//        jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//        jdqResponse.setDesc("获取H5链接地址成功");
//        jdqResponse.setData(listMap);
//        return jdqResponse;
//    }
//
//    /**
//     * 3.14 查询银行卡信息接口
//     */
//    @Override
//    public JdqResponse queryCardInfo(JdqCardInfoReq jdqCardInfoReq) {
//        long sessionId = System.currentTimeMillis();
//        JdqResponse jdqResponse = new JdqResponse();
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqCardInfoReq.getPhone());
//
//        List<Map<String, Object>> banks = new ArrayList<>();
//        Map<String, Object> result = new HashMap<>(16);
//        Map<String, Object> cardInfo = new HashMap<>(16);
//
//        String idNumber = jdqCardInfoReq.getId_number();
//        String phone = jdqCardInfoReq.getPhone();
//        String status = jdqCardInfoReq.getStatus();
//
//        //进件前没传手机号的
//        if (CommUtils.isNull(phone)) {
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("未查到用户的相关信息");
//            return jdqResponse;
//        }
//
//        if (CommUtils.isNull(status) || CommUtils.isNull(idNumber)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL);
//            jdqResponse.setDesc("请求参数为空");
//            logger.info("请求参数为：" + JSON.toJSONString(jdqCardInfoReq));
//            return jdqResponse;
//        }
//        if ("1".equals(status)) {
//            result.put("change_card_flag", 0);
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("请求数据成功，处于进件前");
//            jdqResponse.setData(result);
//            logger.info("请求参数为：" + JSON.toJSONString(jdqResponse));
//            return jdqResponse;
//        }
//        BwBorrower bwBorrower = bwBorrowerService.oldUserFilter3(phone);
//        if (CommUtils.isNull(bwBorrower)) {
//            logger.info(phone + "对应的借款人不存在");
//            jdqResponse.setCode(JdqResponse.CODE_FAIL);
//            jdqResponse.setDesc("查询不到对应的借款人");
//            return jdqResponse;
//        }
//        BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwBorrower.getId());
//        if (CommUtils.isNull(bwBankCard)) {
//            logger.info(bwBorrower.getId() + "对应的银行卡不存在");
//            jdqResponse.setCode(JdqResponse.CODE_FAIL);
//            jdqResponse.setDesc("对应的银行卡不存在");
//            return jdqResponse;
//        }
//
//        if (status.equals("2")) {
//            result.put("change_card_flag", 1);
//            cardInfo.put("card_type", 1);
//            // 银行代码
//            cardInfo.put("bank_code", DrainageUtils.convertFuiouBankCodeToBaofu(bwBankCard.getBankCode()));
//            // 银行卡号
//            cardInfo.put("card_no", bwBankCard.getCardNo());
//            // 银行名称
//            cardInfo.put("bank_name", bwBankCard.getBankName());
//            // 手机号
//            cardInfo.put("phone", bwBankCard.getPhone());
//            // 身份证号码
//            cardInfo.put("id_number", bwBorrower.getIdCard());
//            // 姓名
//            cardInfo.put("name", bwBorrower.getName());
//            banks.add(cardInfo);
//            result.put("card_info", banks);
//
//        }
//        if (status.equals("3")) {
//            result.put("change_card_flag", 0);
//            cardInfo.put("card_type", 1);
//            cardInfo.put("bank_code", DrainageUtils.convertFuiouBankCodeToBaofu(bwBankCard.getBankCode()));
//            cardInfo.put("card_no", bwBankCard.getCardNo());
//            cardInfo.put("bank_name", bwBankCard.getBankName());
//            cardInfo.put("phone", bwBankCard.getPhone());
//            cardInfo.put("id_number", bwBorrower.getIdCard());
//            cardInfo.put("name", bwBorrower.getName());
//            banks.add(cardInfo);
//            result.put("card_info", banks);
//        }
//        jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//        jdqResponse.setDesc("绑卡信息");
//        jdqResponse.setData(result);
//
//        logger.info(sessionId + "执行JdqServiceImpl.queryCardInfo()方法返回数据： " + JSONObject.toJSONString(jdqResponse));
//
//        return jdqResponse;
//    }
//
//    /**
//     * 3.8 获取订单状态
//     */
//    @Override
//    public JdqResponse orderStatus(JdqPullOrderStatusReq jdqPullOrderStatusReq) {
//        long sessionId = System.currentTimeMillis();
//        JdqResponse jdqResponse = new JdqResponse();
//        //用来记录日志的
//        /*jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqPullOrderStatusReq.getJdq_order_id());*/
//
//        String jdqOrderId = jdqPullOrderStatusReq.getJdq_order_id();
//        if (CommUtils.isNull(jdqOrderId)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("第三方订单号不存在");
//            logger.info(sessionId + "：结束jdqResponse.orderStatus 方法" + JSON.toJSONString(jdqResponse));
//            return jdqResponse;
//        }
//
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(jdqOrderId);
//        if (bwOrderRong == null) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("第三方订单不存在");
//            logger.info(sessionId + "：结束jdqResponse.orderStatus 方法" + JSON.toJSONString(jdqResponse));
//            return jdqResponse;
//        }
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        // 如果当前订单不存在表示订单基本信息未推送
//        if (CommUtils.isNull(bwOrder)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("我方不存在该订单");
//            logger.info(sessionId + "：结束jdqResponse.orderStatus method：" + JSON.toJSONString(jdqResponse));
//            return jdqResponse;
//        }
//        BwBorrower bwBorrower = new BwBorrower();
//        bwBorrower.setId(bwOrder.getBorrowerId());
//        bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//        if (CommUtils.isNull(bwBorrower)) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("根据手机号查询不到对应的借款人");
//            logger.info("根据对应的" + bwOrder.getBorrowerId() + "查询不到对应的借款人");
//            return jdqResponse;
//        }
//        BwProductDictionary bwProductDictionary = bwProductDictionaryService
//                .findBwProductDictionaryById(bwOrder.getProductId());
//
//
//        logger.info(sessionId + "判断订单状态：{}", bwOrder.getStatusId());
//        if (OrderStatusEnum.DRAFT.getStatus() == bwOrder.getStatusId()
//                || OrderStatusEnum.APPROVAL_START.getStatus() == bwOrder.getStatusId()
//                || OrderStatusEnum.APPROVAL_END.getStatus() == bwOrder.getStatusId()) {
//            logger.info(sessionId + "判断订单状态---->：{}，借款人：{}", bwOrder.getStatusId(), bwBorrower.getId());
//            Map<String, Object> result = checking(bwOrder, bwOrderRong);
//            if (result == null) {
//                logger.info(bwOrder.getId() + "审核订单获取异常");
//                jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//                jdqResponse.setDesc("审核订单获取异常");
//                return jdqResponse;
//            }
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("审核订单获取成功");
//            jdqResponse.setData(result);
//            logger.info("获取订单【" + bwOrder.getId() + "】草稿状态成功");
//            return jdqResponse;
//        }
//
//        logger.info(sessionId + "风控审批(4,7,8)，推送订单状态");
//        // 风控审批(4,7,8)，推送订单状态
//        if (OrderStatusEnum.SIGN.getStatus() == bwOrder.getStatusId()
//                || OrderStatusEnum.REJECTED.getStatus() == bwOrder.getStatusId()
//                || OrderStatusEnum.REVOKED.getStatus() == bwOrder.getStatusId()) {
//            logger.info(sessionId + "判断订单状态---->：{}，借款人：{}", bwOrder.getStatusId(), bwBorrower.getId());
//            // 获取数据结果
//            Map<String, Object> result = riskStatus(bwOrder, bwOrderRong, bwProductDictionary);
//            if (result == null) {
//                logger.info(bwOrder.getId() + "审批结果获取异常");
//                jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//                jdqResponse.setDesc("审批结果获取异常");
//                return jdqResponse;
//            }
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("审批结果获取成功");
//            jdqResponse.setData(result);
//            logger.info("获取订单" + bwOrder.getId() + "审批状态成功");
//            return jdqResponse;
//
//        }
//
//
//        logger.info(sessionId + "放款推送(9)");
//        // 放款推送(9)
//        if (OrderStatusEnum.REPAYMENT.getStatus() == bwOrder.getStatusId()) {
//            logger.info(sessionId + "判断订单状态---->：{}，借款人：{}", bwOrder.getStatusId(), bwBorrower.getId());
//            // 查找银行卡，放款后必传
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
//            if (bwBankCard == null) {
//                logger.info("该借款人" + bwOrder.getBorrowerId() + "放款状态下查询不到对应的银行卡");
//                jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//                jdqResponse.setDesc("放款状态获取异常,找不到对应的银行卡");
//                return jdqResponse;
//            }
//
//            // 查询还款计划，放款后必传
//            List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService
//                    .listBwRepaymentPlanByOrderId(bwOrder.getId());
//            // 获取数据结果
//            Map<String, Object> result = loanStatus(bwOrder, bwOrderRong, bwBorrower, bwProductDictionary,
//                    bwBankCard, bwRepaymentPlans);
//            if (result == null) {
//                logger.info(bwOrder.getId() + "放款状态获取异常");
//                jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//                jdqResponse.setDesc("放款状态获取异常");
//                return jdqResponse;
//            }
//
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("获取放款状态成功");
//            jdqResponse.setData(result);
//            logger.info("获取订单" + bwOrder.getId() + "放款状态成功");
//            return jdqResponse;
//
//        }
//
//        logger.info(sessionId + "放款推送(13)");
//        // 放款推送(13)
//        if (OrderStatusEnum.OVERDUE.getStatus() == bwOrder.getStatusId()) {
//            logger.info(sessionId + "判断订单状态---->：{}，借款人：{}", bwOrder.getStatusId(), bwBorrower.getId());
//            // 查找银行卡，放款后必传
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
//            if (bwBankCard == null) {
//                logger.info("该借款人" + bwOrder.getBorrowerId() + "逾期状态下查询不到对应的银行卡");
//                jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//                jdqResponse.setDesc("逾期状态获取异常,找不到对应的银行卡");
//                return jdqResponse;
//            }
//
//            // 查询还款计划，放款后必传
//            List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService
//                    .listBwRepaymentPlanByOrderId(bwOrder.getId());
//            // 获取数据结果
//            Map<String, Object> result = overdueStatus(bwOrder, bwOrderRong, bwBorrower, bwProductDictionary,
//                    bwBankCard, bwRepaymentPlans);
//            if (result == null) {
//                logger.info(bwOrder.getId() + "逾期状态获取异常");
//                jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//                jdqResponse.setDesc("逾期状态获取异常");
//                return jdqResponse;
//            }
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("获取逾期状态成功");
//            jdqResponse.setData(result);
//            logger.info("获取订单" + bwOrder.getId() + "逾期状态成功");
//            return jdqResponse;
//        }
//
//        logger.info(sessionId + "结清推送(6)");
//        // 结清推送(6)
//        if (OrderStatusEnum.TERMINATE.getStatus() == bwOrder.getStatusId()) {
//            logger.info(sessionId + "判断订单状态---->：{}，借款人：{}", bwOrder.getStatusId(), bwBorrower.getId());
//            // 查找银行卡，结清后必传
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
//            if (bwBankCard == null) {
//                logger.info("该借款人" + bwOrder.getBorrowerId() + "结清状态下查询不到对应的银行卡");
//                jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//                jdqResponse.setDesc("结清状态获取异常,找不到对应的银行卡");
//                return jdqResponse;
//            }
//
//            // 查询还款计划，放款后必传
//            List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService
//                    .listBwRepaymentPlanByOrderId(bwOrder.getId());
//            // 获取数据结果
//            Map<String, Object> result = endStatus(bwOrder, bwOrderRong, bwBorrower, bwProductDictionary,
//                    bwBankCard, bwRepaymentPlans);
//            if (result == null) {
//                logger.info(bwOrder.getId() + "结清状态获取异常");
//                jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//                jdqResponse.setDesc("结清状态获取异常");
//                return jdqResponse;
//            }
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("获取结清状态成功");
//            jdqResponse.setData(result);
//            logger.info("获取订单" + bwOrder.getId() + "结清状态成功");
//            return jdqResponse;
//        }
//
//        logger.info(sessionId + "放款（11，12，14）");
//        // 放款（11，12，14）
//        if (bwOrder.getStatusId() == OrderStatusEnum.CONTRACT.getStatus()
//                || bwOrder.getStatusId() == OrderStatusEnum.BOND.getStatus()
//                || bwOrder.getStatusId() == OrderStatusEnum.BOND_DISTRIBUTING.getStatus()) {
//            logger.info(sessionId + "判断订单状态---->：{}，借款人：{}", bwOrder.getStatusId(), bwBorrower.getId());
//            orderResult(bwOrder, bwOrderRong, bwProductDictionary, jdqResponse);
//        }
//        return jdqResponse;
//    }
//
//    /**
//     * 订单结果
//     *
//     * @param bwOrder
//     * @param bwOrderRong
//     * @param bwProductDictionary
//     * @param jdqResponse
//     */
//    private void orderResult(BwOrder bwOrder, BwOrderRong bwOrderRong,
//                             BwProductDictionary bwProductDictionary,
//                             JdqResponse jdqResponse) {
//        // 获取数据结果
//        Map<String, Object> result = riskStatus(bwOrder, bwOrderRong, bwProductDictionary);
//        if (result == null) {
//            logger.info(bwOrder.getId() + "审批结果获取异常");
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("审批结果获取异常");
//        } else {
//            jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//            jdqResponse.setDesc("待放款");
//            jdqResponse.setData(result);
//        }
//    }
//
//    /**
//     * 风控审批(4,7,8)，待还款(11，12，14) 推送订单状态
//     */
//    private Map<String, Object> riskStatus(BwOrder bwOrder, BwOrderRong bwOrderRong,
//                                           BwProductDictionary bwProductDictionary) {
//        try {
//            // 输出参数
//            HashMap<String, Object> result = new HashMap<>(16);
//
//            // 新利率
//            Double interestRate = bwProductDictionary.getInterestRate();
//            Double interest_rate = DoubleUtil.mul(DoubleUtil.div(interestRate, 7.0), 365.0);
//
//            // 借点钱订单号
//            result.put("jdq_order_id", bwOrderRong.getThirdOrderNo());
//            // 机构方订单号
//            result.put("channel_order_id", bwOrder.getOrderNo());
//            // 订单状态(状态转换)
//            result.put("status", JdqUtils.convertOrderStatus(bwOrder.getStatusId()));
//
//            // 审批通过后必传
//            if (bwOrder.getStatusId() >= OrderStatusEnum.SIGN.getStatus()
//                    && bwOrder.getStatusId() != OrderStatusEnum.REJECTED.getStatus()
//                    && bwOrder.getStatusId() != OrderStatusEnum.REVOKED.getStatus()) {
//                // 审批金额
//                result.put("approval_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
//                // 审批期数
//                result.put("approval_periods", APPROVAL_PERIODS);
//                // 审批每期天数
//                result.put("approval_period_days", APPROVAL_PERIOD_DAYS);
//                // 审批总天数
//                result.put("approval_days", APPROVAL_DAYS);
//            }
//            // 年化贷款利息率
//            result.put("interest_rate", interest_rate + "");
//            return result;
//        } catch (Exception e) {
//            logger.error("===========借点钱订单推送反馈：riskStatus()获取还款计划信息失败>>>>>", e);
//            return null;
//        }
//
//    }
//
//    /**
//     * (1,2,3)审核中的状态
//     *
//     * @param bwOrder
//     * @param bwOrderRong
//     * @return
//     */
//    private Map<String, Object> checking(BwOrder bwOrder, BwOrderRong bwOrderRong) {
//
//        try {
//            HashMap<String, Object> result = new HashMap<>(16);
//            // 借点钱订单号
//            result.put("jdq_order_id", bwOrderRong.getThirdOrderNo());
//            // 机构方订单号
//            result.put("channel_order_id", bwOrder.getOrderNo());
//            result.put("status", JdqUtils.convertOrderStatus(bwOrder.getStatusId()));
//            return result;
//        } catch (Exception e) {
//            logger.error("===========借点钱订单推送反馈：checking()获取信息失败>>>>>", e);
//            return null;
//        }
//
//    }
//
//    /**
//     * 已放款(9)，推送订单状态
//     */
//    private Map<String, Object> loanStatus(BwOrder bwOrder, BwOrderRong bwOrderRong, BwBorrower bwBorrower,
//                                           BwProductDictionary bwProductDictionary, BwBankCard bwBankCard, List<BwRepaymentPlan> bwRepaymentPlans) {
//        try {
//            // 获得订单信息
//            HashMap<String, Object> result = findOrderInfo(bwOrder, bwOrderRong, bwProductDictionary);
//            if (result == null) {
//                logger.info(bwOrder.getId() + "放款状态下获取订单信息为空");
//                return null;
//            }
//
//            // 获得还款计划信息
//            List<Map<String, Object>> repayment_plan = findRepaymentPlan(bwRepaymentPlans);
//            if (repayment_plan == null) {
//                logger.info(bwOrder.getId() + "订单放款状态下还款计划为空");
//                return null;
//            }
//            result.put("repayment_plan", repayment_plan);
//
//            // 获取银行卡信息
//            List<Map<String, Object>> bank_card_info = findBankCard(bwBankCard, bwBorrower);
//            if (bank_card_info == null) {
//                logger.info(bwOrder.getId() + "订单放款状态下银行卡信息为空");
//                return null;
//            }
//            // 封装银行卡信息
//            result.put("bank_card_info", bank_card_info);
//
//            return result;
//        } catch (Exception e) {
//            logger.error("===========借点钱订单推送反馈：loanStatus()获取信息失败>>>>>", e);
//            return null;
//        }
//    }
//
//    /**
//     * 逾期(13)，推送订单状态
//     */
//    private Map<String, Object> overdueStatus(BwOrder bwOrder, BwOrderRong bwOrderRong, BwBorrower bwBorrower,
//                                              BwProductDictionary bwProductDictionary, BwBankCard bwBankCard, List<BwRepaymentPlan> bwRepaymentPlans) {
//        try {
//            // 获得订单信息
//            HashMap<String, Object> result = findOrderInfo(bwOrder, bwOrderRong, bwProductDictionary);
//            if (result == null) {
//                logger.info(bwOrder.getId() + "订单逾期状态下获取订单信息为空");
//                return null;
//            }
//            // 获得还款计划信息
//            List<Map<String, Object>> repaymentPlan = findRepaymentPlan(bwRepaymentPlans);
//            if (repaymentPlan == null) {
//                logger.info(bwOrder.getId() + "订单逾期状态下还款计划为空");
//                return null;
//            }
//            result.put("repayment_plan", repaymentPlan);
//
//            // 获取银行卡信息
//            List<Map<String, Object>> bank_card_info = findBankCard(bwBankCard, bwBorrower);
//            if (bank_card_info == null) {
//                logger.info(bwOrder.getId() + "订单逾期状态银行卡为空");
//                return null;
//            }
//
//            // 封装银行卡信息
//            result.put("bank_card_info", bank_card_info);
//
//            return result;
//        } catch (Exception e) {
//            logger.error("===========借点钱订单推送反馈：overdueStatus()获取信息失败>>>>>", e);
//            return null;
//        }
//    }
//
//    /**
//     * 还款结束(6)，推送订单状态
//     */
//    private Map<String, Object> endStatus(BwOrder bwOrder, BwOrderRong bwOrderRong, BwBorrower bwBorrower,
//                                          BwProductDictionary bwProductDictionary, BwBankCard bwBankCard, List<BwRepaymentPlan> bwRepaymentPlans) {
//        try {
//            // 获得订单信息
//            HashMap<String, Object> result = findOrderInfo(bwOrder, bwOrderRong, bwProductDictionary);
//            if (result == null) {
//                return null;
//            }
//
//            // 获得还款计划信息
//            List<Map<String, Object>> repaymentPlan = findRepaymentPlan(bwRepaymentPlans);
//            if (repaymentPlan == null) {
//                logger.info(bwOrder.getId() + "订单结清状态还款计划为空");
//                return null;
//            }
//            result.put("repayment_plan", repaymentPlan);
//
//            // 获取银行卡信息
//            List<Map<String, Object>> bankCardInfo = findBankCard(bwBankCard, bwBorrower);
//            if (bankCardInfo == null) {
//                logger.info(bwOrder.getId() + "订单结清状态银行卡为空");
//                return null;
//            }
//            // 封装银行卡信息
//            result.put("bank_card_info", bankCardInfo);
//            return result;
//        } catch (Exception e) {
//            logger.error("===========借点钱订单推送反馈：endStatus()获取信息失败>>>>>", e);
//            return null;
//        }
//    }
//
//    /**
//     * 获取还款计划信息（状态 9,13,6调用）
//     *
//     * @param bwRepaymentPlans 还款计划列表
//     * @return 还款计划列表
//     */
//    private List<Map<String, Object>> findRepaymentPlan(List<BwRepaymentPlan> bwRepaymentPlans) {
//
//        try {
//            // 还款计划列表
//            List<Map<String, Object>> repaymentPlans = new ArrayList<>();
//            // 创建时间返回格式
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//            for (BwRepaymentPlan plan : bwRepaymentPlans) {
//                // 还款计划装参
//                Map<String, Object> planMap = new HashMap<>(16);
//                // 计划还款日期
//                planMap.put("plan_repayment_time", simpleDateFormat.format(plan.getRepayTime()));
//                // 本期还款本金
//                planMap.put("amount", plan.getRepayCorpusMoney());
//                // 还款利息
//                planMap.put("period_fee", plan.getRepayAccrualMoney());
//                // 期数 当前期数
//                planMap.put("period", plan.getNumber());
//
//                // 判断还款状态
//                Integer repayStatus = plan.getRepayStatus();
//
//                // 1 未还款
//                if (repayStatus == 1) {
//                    // 没有逾期，未还款。是否逾期，0未逾期，1逾期
//                    planMap.put("overdue", "0");
//                    // 逾期罚款,数值
//                    planMap.put("overdue_fee", 0.00);
//                    // 逾期天数
//                    planMap.put("overdue_day", "0");
//                    // 还款计划状态， 1:待还款
//                    planMap.put("status", "1");
//                }
//                // 2 已还款
//                if (repayStatus == 2) {
//                    // 获取还款类型
//                    Integer repayType = plan.getRepayType();
//                    // 正常还款 // 3提前还款
//                    if (repayType == 1 || repayType == 3) {
//                        // 实际还款日期
//                        planMap.put("true_repayment_time", simpleDateFormat.format(plan.getUpdateTime()));
//                        // 是否逾期，0未逾期，1逾期
//                        planMap.put("overdue", "0");
//                        // 还款计划状态，2:正常结清
//                        planMap.put("status", "2");
//                        // 逾期罚款,数值
//                        planMap.put("overdue_fee", 0.00);
//                        // 逾期天数
//                        planMap.put("overdue_day", "0");
//                    }
//                    if (repayType == 2) {
//                        // 逾期还款 // 实际还款日期
//                        planMap.put("true_repayment_time", simpleDateFormat.format(plan.getUpdateTime()));
//                        // 是否逾期，0未逾期，1逾期
//                        planMap.put("overdue", "1");
//                        // 还款计划状态，3:逾期结清
//                        planMap.put("status", "3");
//                        // 查询逾期
//                        BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//                        bwOverdueRecord.setRepayId(plan.getId());
//                        bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//                        if (bwOverdueRecord == null) {
//                            logger.info("===========借点钱订单推送反馈：获取逾期信息失败>>>>>");
//                            return null;
//                        }
//                        // 逾期天数
//                        planMap.put("overdue_day", bwOverdueRecord.getOverdueDay());
//                        // 计算逾期费用，// 逾期利息
//                        Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D
//                                : bwOverdueRecord.getOverdueAccrualMoney();
//                        // 免罚息金额
//                        Double advance = bwOverdueRecord.getAdvance();
//                        Double overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//                        // 逾期费用
//                        planMap.put("overdue_fee", overdueFee);
//                    }
//                }
//
//                // 3 已垫付 逾期
//                if (repayStatus == 3) {
//                    // 查询逾期
//                    BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//                    bwOverdueRecord.setRepayId(plan.getId());
//                    bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//                    if (bwOverdueRecord == null) {
//                        logger.info("===========借点钱订单推送反馈：获取逾期信息失败>>>>>");
//                        return null;
//                    }
//                    // 逾期，未还款。是否逾期，0未逾期，1逾期
//                    planMap.put("overdue", "1");
//                    // 逾期天数
//                    planMap.put("overdue_day", bwOverdueRecord.getOverdueDay());
//                    // 计算逾期费用。// 逾期利息
//                    Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D
//                            : bwOverdueRecord.getOverdueAccrualMoney();
//                    // 免罚息金额
//                    Double advance = bwOverdueRecord.getAdvance();
//                    Double overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//                    // 逾期费用
//                    planMap.put("overdue_fee", overdueFee);
//                    // 还款计划状态， 4:逾期
//                    planMap.put("status", "4");
//                }
//                // 添加还款计划到list集合
//                repaymentPlans.add(planMap);
//            }
//            return repaymentPlans;
//        } catch (Exception e) {
//            logger.error("===========借点钱订单推送反馈：获取还款计划信息失败>>>>>", e);
//            return null;
//        }
//
//    }
//
//    /**
//     * 获得订单信息
//     *
//     * @param bwOrder             订单
//     * @param bwOrderRong         第三方订单
//     * @param bwProductDictionary 水象产品
//     */
//    private HashMap<String, Object> findOrderInfo(BwOrder bwOrder, BwOrderRong bwOrderRong,
//                                                  BwProductDictionary bwProductDictionary) {
//        // 输出参数
//        HashMap<String, Object> result = new HashMap<>(16);
//
//        // 新利率
//        Double interestRate = DoubleUtil.mul(DoubleUtil.div(bwProductDictionary.getInterestRate(), 7.0), 365.0);
//        // 借点钱订单号
//        result.put("jdq_order_id", bwOrderRong.getThirdOrderNo());
//        // 机构方订单号k
//        result.put("channel_order_id", bwOrder.getOrderNo());
//        // 订单状态(状态转换)9-7
//        result.put("status", JdqUtils.convertOrderStatus(bwOrder.getStatusId()));
//        // 审批是成功的
//        result.put("reason", "0");
//        // 审批失败原因（0=审批成功，1=材料缺失，2=资质不符，3=验卡失败）。审批金额
//        result.put("approval_amount", bwOrder.getBorrowAmount() == null ? "0" : bwOrder.getBorrowAmount() + "");
//        // 审批期数
//        result.put("approval_periods", APPROVAL_PERIODS);
//        // 审批每期天数
//        result.put("approval_period_days", APPROVAL_PERIOD_DAYS);
//        // 审批总天数
//        result.put("approval_days", APPROVAL_DAYS);
//        // 年化贷款利息率
//        result.put("interest_rate", interestRate + "");
//
//        return result;
//    }
//
//    /**
//     * 获取银行卡信息
//     *
//     * @param bwBankCard 银行卡
//     * @param bwBorrower 借款人
//     * @return 银行卡信息
//     */
//    private List<Map<String, Object>> findBankCard(BwBankCard bwBankCard, BwBorrower bwBorrower) {
//        // 封装银行卡信息
//        try {
//            List<Map<String, Object>> bankCardInfo = new ArrayList<>();
//            Map<String, Object> bankCard = new HashMap<>(16);
//            // 卡类型： 1：借记卡 2：信用卡
//            bankCard.put("card_type", 1);
//            // 银行代码
//            bankCard.put("bank_code", DrainageUtils.convertFuiouBankCodeToBaofu(bwBankCard.getBankCode()));
//            // 银行卡号
//            bankCard.put("card_no", bwBankCard.getCardNo());
//            // 银行行名
//            bankCard.put("bank_name", bwBankCard.getBankName());
//            // 预留手机号，和注册手机号一致?
//            bankCard.put("phone", bwBankCard.getPhone());
//            // 身份证号
//            bankCard.put("id_number", bwBorrower.getIdCard());
//            // 姓名
//            bankCard.put("name", bwBorrower.getName());
//            // 封装银行卡
//            bankCardInfo.add(bankCard);
//            return bankCardInfo;
//        } catch (Exception e) {
//            logger.error("===========借点钱订单推送反馈：获取银行卡信息失败>>>>>", e);
//            return null;
//        }
//    }
//
//    /**
//     * 处理通话记录信息
//     */
//    private void addOperateVoice(List<Calls> calls, Long borrowerId) {
//        logger.info("addOperateVoice开始处理通话记录");
//        if (CommUtils.isNull(calls)) {
//            throw new JdqException("通话记录为空，请检查！");
//        }
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        // 根据手机号查询最近一次抓取的通话记录时间
//        Date callDate;
//        try {
//            callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//        } catch (Exception e) {
//            logger.error("getCallTimeByborrowerIdEs：{}", e);
//            throw new RuntimeException(e);
//        }
//
//        List<BwOperateVoice> operateVoices = new ArrayList<>();
//        for (Calls call : calls) {
//            if (callDate == null || call.getStart_time().after(callDate)) {
//                BwOperateVoice bwOperateVoice = new BwOperateVoice();
//                bwOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
//                bwOperateVoice.setBorrower_id(borrowerId);
//
//                // 检验日期格式
//                String callTime = sdf.format(call.getStart_time());
//                // 通话时间
//                bwOperateVoice.setCall_time(callTime);
//                // 呼叫类型
//                bwOperateVoice.setCall_type("主叫".equals(call.getInit_type()) ? 1 : 2);
//                // 对方号码
//                if (!Objects.isNull(call.getOther_cell_phone()) &&
//                        call.getOther_cell_phone().length() > 20) {
//                    logger.warn("对方号码超长，跳过：{}", call.getOther_cell_phone());
//                    continue;
//                }
//                bwOperateVoice.setReceive_phone(call.getOther_cell_phone());
//                if (!Objects.isNull(call.getPlace()) &&
//                        call.getPlace().length() > 50) {
//                    logger.warn("通话地点超长，跳过：{}", call.getOther_cell_phone());
//                    continue;
//                }
//                // 通话地点
//                bwOperateVoice.setTrade_addr(CommUtils.isNull(call.getPlace()) ? "" : call.getPlace());
//                // 通话时长
//                bwOperateVoice.setTrade_time(String.valueOf(call.getUse_time()));
//                // 通信类型：1.本地通话,国内长途
//                bwOperateVoice.setTrade_type("本地通话".equals(call.getCall_type()) ? 1 : 2);
//                operateVoices.add(bwOperateVoice);
//            }
//        }
//        bwOperateVoiceService.batchOperateVoice(operateVoices);
//    }
//
//    @Override
//    public JdqResponse saveBindCardPreNew(JdqBindCardReq jdqBindCardReq) {
//        JdqResponse jdqResponse = new JdqResponse();
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqBindCardReq.getPhone());
//
//        String bankCard = jdqBindCardReq.getCard_no();
//        String idenCard = jdqBindCardReq.getId_number();
//        //预留手机号
//        String regPhone = jdqBindCardReq.getPhone();
//        //注册手机号
//        String mobile = jdqBindCardReq.getMobile();
//        String userName = jdqBindCardReq.getName();
//        String bankCode = jdqBindCardReq.getBank_code();
//        // 处理银行编码
//        String code = BankcardList.convertToBaoFuCode(bankCode);
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setBankCardNo(bankCard);
//        // 前置
//        drainageBindCardVO.setBindType("1");
//        drainageBindCardVO.setIdCardNo(idenCard);
//        drainageBindCardVO.setNotify(true);
//        drainageBindCardVO.setPhone(mobile);
//        drainageBindCardVO.setName(userName);
//        drainageBindCardVO.setChannelId(Integer.valueOf(CHANNEL_ID));
//        drainageBindCardVO.setBankCode(code);
//        drainageBindCardVO.setRegPhone(regPhone);
//        logger.info("绑卡之前数据" + JSON.toJSONString(drainageBindCardVO));
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(System.currentTimeMillis(),
//                drainageBindCardVO);
//        if (drainageRsp != null) {
//            if ("0000".equals(drainageRsp.getCode())) {
//                jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//                jdqResponse.setDesc("预绑卡成功");
//            } else {
//                jdqResponse.setCode(JdqResponse.CODE_FAIL);
//                jdqResponse.setDesc(drainageRsp.getMessage());
//            }
//        } else {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("绑定银行卡返回参数为空");
//        }
//
//        return jdqResponse;
//    }
//
//    @Override
//    public JdqResponse saveBindCardWithCodeNew(JdqBindCardReq jdqBindCardReq) {
//        JdqResponse jdqResponse = new JdqResponse();
//        //用来记录日志的
//        jdqResponse.setChannelId(CHANNEL_ID);
//        jdqResponse.setKey(jdqBindCardReq.getPhone());
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setBankCardNo(jdqBindCardReq.getCard_no());
//        drainageBindCardVO.setBindType("1");
//        drainageBindCardVO.setNotify(true);
//        drainageBindCardVO.setChannelId(Integer.valueOf(CHANNEL_ID));
//        drainageBindCardVO.setVerifyCode(jdqBindCardReq.getSms_code());
//        //预留手机号
//        drainageBindCardVO.setRegPhone(jdqBindCardReq.getPhone());
//        //注册手机号
//        drainageBindCardVO.setPhone(jdqBindCardReq.getMobile());
//        logger.info("开始进入通用确认绑卡" + JSON.toJSONString(drainageBindCardVO));
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(System.currentTimeMillis(),
//                drainageBindCardVO);
//        if (drainageRsp != null) {
//            if ("0000".equals(drainageRsp.getCode())) {
//                jdqResponse.setCode(JdqResponse.CODE_SUCCESS);
//                jdqResponse.setDesc("绑卡成功");
//            } else {
//                jdqResponse.setCode(JdqResponse.CODE_FAIL);
//                jdqResponse.setDesc(drainageRsp.getMessage());
//            }
//        } else {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL_Exception);
//            jdqResponse.setDesc("绑定银行卡返回参数为空");
//        }
//
//        logger.info("结束借点钱绑定银行卡接口");
//        return jdqResponse;
//    }
//}
