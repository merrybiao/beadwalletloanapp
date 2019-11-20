//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.*;
//import com.waterelephant.service.*;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.dkdh360.*;
//import com.waterelephant.sxyDrainage.mapper.BwDkdh360DeviceInfoMapper;
//import com.waterelephant.sxyDrainage.mapper.BwDkdh360InstalledAppsMapper;
//import com.waterelephant.sxyDrainage.service.AsyncDkdh360Task;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.Dkdh360Service;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.SxyDrainageConstant;
//import com.waterelephant.sxyDrainage.utils.dkdh360util.Dkdh360Constant;
//import com.waterelephant.sxyDrainage.utils.dkdh360util.Dkdh360Utils;
//import com.waterelephant.sxyDrainage.utils.interfaceLog.SxyThirdInterfaceLogUtils;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.*;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.log4j.Logger;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/25 17:03
// * @Description: 360贷款导航实现类
// */
//@Service
//public class Dkdh360ServiceImpl implements Dkdh360Service {
//    private Logger logger = Logger.getLogger(Dkdh360ServiceImpl.class);
//    private String channelId = Dkdh360Constant.get("channel_id");
//    @Autowired
//    private CommonService commonService;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//    @Autowired
//    private BwOrderRongService bwOrderRongService;
//    @Autowired
//    private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private IBwWorkInfoService bwWorkInfoService;
//    @Autowired
//    private AsyncDkdh360Task asyncDkdh360Task;
//    @Autowired
//    private IBwBorrowerService iBwBorrowerService;
//    @Autowired
//    private IBwIdentityCardService bwIdentityCardService;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//    @Autowired
//    private BwOrderProcessRecordService bwOrderProcessRecordService;
//    @Autowired
//    private BwDkdh360DeviceInfoMapper bwDkdh360DeviceInfoMapper;
//    @Autowired
//    private BwDkdh360InstalledAppsMapper bwDkdh360InstalledAppsMapper;
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//    @Autowired
//    private IBwBankCardService bwBankCardService;
//    @Autowired
//    private BwProductDictionaryService bwProductDictionaryService;
//    @Autowired
//    private IBwAdjunctService bwAdjunctService;
//    @Autowired
//    private IBwRepaymentPlanService bwRepaymentPlanService;
//    @Autowired
//    private BwOverdueRecordService bwOverdueRecordService;
//
//    @Override
//    public Dkdh360Response checkUser(long sessionId, CheckInfo checkInfo) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.checkUser method：" + JSON.toJSONString(checkInfo));
//
//        String idCard = checkInfo.getId_card();
//        String userMobile = checkInfo.getUser_mobile();
//        String userName = checkInfo.getUser_name();
//
//        // 开始过滤
//        DrainageRsp drainageRsp = commonService.checkUser(sessionId, userName, userMobile, idCard);
//        String code = drainageRsp.getCode();
//        // 2001 命中黑名单规则; 2002 存在进行中的订单; 2003 命中被拒规则; 2004 用户年龄超限; 1002 参数为空; 0000 成功; 1000 系统异常
//
//        if ("1002".equals(code)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.checkUser method：" + drainageRsp.getMessage());
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, drainageRsp.getMessage());
//        }
//        if ("2001".equals(code) || "2004".equals(code)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.checkUser method：" + drainageRsp.getMessage());
//            return new Dkdh360Response(Dkdh360Response.CODE_NOTLOAN, drainageRsp.getMessage(), "C002");
//        }
//        if ("2002".equals(code)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.checkUser method：" + drainageRsp.getMessage());
//            return new Dkdh360Response(Dkdh360Response.CODE_NOTLOAN, drainageRsp.getMessage(), "C001");
//        }
//        if ("2003".equals(code)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.checkUser method：" + drainageRsp.getMessage());
//            return new Dkdh360Response(Dkdh360Response.CODE_NOTLOAN, drainageRsp.getMessage(), "C003");
//        }
//        if ("1000".equals(code)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.checkUser method：" + drainageRsp.getMessage());
//            return new Dkdh360Response(Dkdh360Response.CODE_FAILURE, drainageRsp.getMessage());
//        }
//        if (userMobile.startsWith("170")) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.checkUser method：" + userMobile + "为虚拟运营商号段！");
//            return new Dkdh360Response(Dkdh360Response.CODE_NOTLOAN, userMobile + "为虚拟运营商号段！");
//        }
//
//        Map<String, Integer> data = new HashMap<>(1);
//        data.put("is_reloan", 0);
//        logger.info(sessionId + "：结束Dkdh360ServiceImpl.checkUser method：可以申请");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "可以申请", data);
//    }
//
//    @Override
//    public Dkdh360Response savePushLoanBaseInfo(long sessionId, LoanBaseInfo loanBaseInfo) throws Exception {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.pushLoanBaseInfo method：" + JSON.toJSONString(loanBaseInfo));
//
//        OrderInfo orderInfo = loanBaseInfo.getOrderInfo();
//        BaseInfo applyDetail = loanBaseInfo.getApplyDetail();
//        AddInfo addInfo = loanBaseInfo.getAddInfo();
//        AddInfoMobile addInfoMobile = addInfo.getMobile();
//        if (CommUtils.isNull(orderInfo) || CommUtils.isNull(applyDetail) || CommUtils.isNull(addInfo) || CommUtils
//            .isNull(addInfoMobile)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushLoanBaseInfo method：参数不合法");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "参数不合法");
//        }
//
//        String orderNo = orderInfo.getOrder_no();
//        String userName = orderInfo.getUser_name();
//        String userMobile = orderInfo.getUser_mobile();
//        String userIdCard = applyDetail.getUser_id();
//        if (CommUtils.isNull(orderNo) || CommUtils.isNull(userName) || CommUtils.isNull(userMobile) || CommUtils
//            .isNull(userIdCard)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushLoanBaseInfo method：参数不合法");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "参数不合法");
//        }
//
//        // 判断用户是否有多个账号，及是否有进行中的订单
//        boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, userIdCard);
//        if (flag) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushLoanBaseInfo method：存在进行中的订单，请勿重复推送");
//            return new Dkdh360Response(Dkdh360Response.CODE_DUPLICATECALL, "存在进行中的订单，请勿重复推送");
//        }
//
//        // 新增或更新借款人
//        BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, userIdCard, userMobile,
//            NumberUtils.toInt(channelId));
//        long borrowerId = borrower.getId();
//        logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//
//        // 判断是否有草稿状态的订单
//        BwOrder bwOrder = new BwOrder();
//        bwOrder.setBorrowerId(borrowerId);
//        bwOrder.setStatusId(1L);
//        bwOrder.setProductType(2);
//        bwOrder.setChannel(Integer.valueOf(channelId));
//        List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
//        if (boList != null && boList.size() > 0) {
//            bwOrder = boList.get(0);
//
//            bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//            bwOrder.setApplyPayStatus(0);
//            bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwOrder.setProductId(7);
//            bwOrder.setRepayType(2);
//            bwOrder.setExpectMoney(Double.valueOf(orderInfo.getApplication_amount()));
//            bwOrder.setExpectNumber(4);
//
//            bwOrderService.updateBwOrder(bwOrder);
//        } else {
//            bwOrder = new BwOrder();
//            bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//            bwOrder.setBorrowerId(borrowerId);
//            bwOrder.setStatusId(1L);
//            bwOrder.setCreateTime(Calendar.getInstance().getTime());
//            bwOrder.setChannel(Integer.valueOf(channelId));
//            bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//            bwOrder.setApplyPayStatus(0);
//            bwOrder.setProductId(7);
//            bwOrder.setProductType(2);
//            bwOrder.setRepayType(2);
//            bwOrder.setExpectMoney(Double.valueOf(orderInfo.getApplication_amount()));
//            bwOrder.setExpectNumber(4);
//
//            bwOrderService.addBwOrder(bwOrder);
//        }
//        long orderId = bwOrder.getId();
//        logger.info(sessionId + ">>> 新增/更新订单ID = " + orderId);
//
//        //用于获取征信数据
//        try {
//            String key = "phone_apply";
//            Map<String, Object> params = new HashMap<>(16);
//            params.put("mobile", userMobile);
//            params.put("order_id", orderId);
//            params.put("borrower_id", borrowerId);
//            String value = JSON.toJSONString(params);
//            RedisUtils.rpush(key, value);
//
//            logger.info(sessionId + ">>> 获取征信数据：" + JSON.toJSONString(params));
//        } catch (Exception e) {
//            logger.error(sessionId + ">>> 获取征信数据异常：", e);
//        }
//
//        // 判断是否有三方订单
//        BwOrderRong bwOrderRong = new BwOrderRong();
//        bwOrderRong.setOrderId(orderId);
//        bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//        if (bwOrderRong == null) {
//            bwOrderRong = new BwOrderRong();
//            bwOrderRong.setOrderId(orderId);
//            bwOrderRong.setThirdOrderNo(orderNo);
//            bwOrderRong.setChannelId(Long.valueOf(channelId));
//            bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//            bwOrderRongService.save(bwOrderRong);
//        } else {
//            bwOrderRong.setChannelId(Long.valueOf(channelId));
//            bwOrderRong.setThirdOrderNo(orderNo);
//            bwOrderRongService.update(bwOrderRong);
//        }
//        logger.info(sessionId + ">>> 新增/更新三方订单");
//
//        // 判断是否有商户订单信息
//        BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//        bwMerchantOrder.setOrderId(orderId);
//        bwMerchantOrder = bwMerchantOrderServiceImpl.selectByAtt(bwMerchantOrder);
//        if (bwMerchantOrder == null) {
//            bwMerchantOrder = new BwMerchantOrder();
//            bwMerchantOrder.setBorrowerId(borrowerId);
//            bwMerchantOrder.setCreateTime(Calendar.getInstance().getTime());
//            bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwMerchantOrder.setExt3("0");
//            bwMerchantOrder.setMerchantId(0L);
//            bwMerchantOrder.setOrderId(orderId);
//            bwMerchantOrderServiceImpl.insertByAtt(bwMerchantOrder);
//        } else {
//            bwMerchantOrder.setBorrowerId(borrowerId);
//            bwMerchantOrder.setExt3("0");
//            bwMerchantOrder.setMerchantId(0L);
//            bwMerchantOrder.setOrderId(orderId);
//            bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwMerchantOrderServiceImpl.updateByAtt(bwMerchantOrder);
//        }
//        logger.info(sessionId + ">>> 新增/更新商户订单表");
//
//        // 工作信息
//        String income, workYears = null;
//        Integer isOpType = applyDetail.getIs_op_type();
//        if (isOpType == 1 || isOpType == 2) {
//            income = applyDetail.getOperating_year().toString();
//            workYears = applyDetail.getCorporate_flow();
//        } else if (isOpType == 4) {
//            workYears = applyDetail.getWork_period().toString();
//            income = applyDetail.getUser_income_by_card();
//        } else {
//            income = applyDetail.getMonthly_average_income();
//        }
//
//        BwWorkInfo bwWorkInfo = new BwWorkInfo();
//        bwWorkInfo.setOrderId(bwOrder.getId());
//        bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//        if (bwWorkInfo == null) {
//            bwWorkInfo = new BwWorkInfo();
//            bwWorkInfo.setCallTime("10:00 - 12:00");
//            bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
//            bwWorkInfo.setWorkYears(workYears);
//            bwWorkInfo.setIncome(income);
//            bwWorkInfo.setOrderId(bwOrder.getId());
//            bwWorkInfoService.save(bwWorkInfo, borrower.getId());
//        } else {
//            bwWorkInfo.setCallTime("10:00 - 12:00");
//            bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//            bwWorkInfo.setWorkYears(workYears);
//            bwWorkInfo.setIncome(income);
//            bwWorkInfoService.update(bwWorkInfo);
//        }
//        logger.info(sessionId + ">>> 新增/更新工作信息");
//        //异步处理运营商数据
//        asyncDkdh360Task.saveOperatorData(sessionId, addInfoMobile, orderId, borrowerId);
//
//        SxyThirdInterfaceLogUtils.setSxyLog(channelId, orderNo, 200 + "", "success", "三方订单号");
//
//        logger.info(sessionId + ":结束Dkdh360ServiceImpl.pushLoanBaseInfo method:success");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "success");
//    }
//
//    @Override
//    public Dkdh360Response savePushLoanAddInfo(long sessionId, LoanAddInfo loanAddInfo) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.pushLoanAddInfo method：" + JSON.toJSONString(loanAddInfo));
//
//        String orderNo = loanAddInfo.getOrder_no();
//        if (StringUtils.isBlank(orderNo)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushLoanAddInfo method：order_no为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "order_no为空");
//        }
//        logger.info(sessionId + ":thirdOrderNo=" + orderNo);
//
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//        if (bwOrderRong == null) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushLoanAddInfo method：第三方订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "第三方订单不存在");
//        }
//
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        // 如果当前订单不存在表示订单基本信息未推送
//        if (CommUtils.isNull(bwOrder)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushLoanAddInfo method：订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "订单不存在");
//        }
//
//        Long orderId = bwOrder.getId();
//        Long borrowerId = bwOrder.getBorrowerId();
//
//        // 查询是否有进行中的订单
//        long count = bwOrderService.findProOrder(borrowerId + "");
//        logger.info(sessionId + ">>> 进行中的订单校验：" + count);
//        if (count > 0) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushLoanAddInfo method：存在进行中的订单，请勿重复推送");
//            return new Dkdh360Response(Dkdh360Response.CODE_DUPLICATECALL, "存在进行中的订单，请勿重复推送");
//        }
//
//        BwBorrower bwBorrower = new BwBorrower();
//        bwBorrower.setId(borrowerId);
//        bwBorrower = iBwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//        if (CommUtils.isNull(bwBorrower)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushLoanAddInfo method：用户不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "用户不存在");
//        }
//
//        // 更新工作信息
//        BwWorkInfo bwWorkInfo = new BwWorkInfo();
//        bwWorkInfo.setOrderId(bwOrderRong.getOrderId());
//        bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//        bwWorkInfo.setComName(loanAddInfo.getCompany_name());
//        bwWorkInfo.setIndustry(Dkdh360Utils.getWorkType(loanAddInfo.getIndustry_type()));
//        bwWorkInfo.setUpdateTime(new Date());
//        bwWorkInfoService.update(bwWorkInfo);
//        logger.info(sessionId + ">>> 更新工作信息");
//        // 插入个人认证记录
//        thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, Integer.parseInt(channelId));
//
//        // 身份证信息
//        List<String> positive = loanAddInfo.getID_Positive();
//        String front = positive.get(positive.size() - 1);
//        List<String> negative = loanAddInfo.getID_Negative();
//        String back = negative.get(negative.size() - 1);
//        List<String> photoAssay = loanAddInfo.getPhoto_assay();
//        String hand = photoAssay.get(photoAssay.size() - 1);
//
//        // 身份证正面照
//        String frontImage = UploadToCssUtils.urlUpload(front, bwOrder.getId() + "_01");
//        logger.info(sessionId + ">>> 身份证正面 " + frontImage);
//        // 保存身份证正面照
//        thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, bwOrder.getId(), bwBorrower.getId(), 0);
//
//        // 身份证反面照
//        String backImage = UploadToCssUtils.urlUpload(back, bwOrder.getId() + "_02");
//        logger.info(sessionId + ">>> 身份证反面 " + backImage);
//        // 保存身份证反面照
//        thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, bwOrder.getId(), bwBorrower.getId(), 0);
//
//        // 手持照
//        String handerImage = UploadToCssUtils.urlUpload(hand, bwOrder.getId() + "_03");
//        logger.info(sessionId + ">>> 手持照/人脸 " + handerImage);
//        // 保存手持照
//        thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, bwOrder.getId(), bwBorrower.getId(),
//            0);
//        logger.info(sessionId + ">>> 处理认证图片 ");
//
//        // 保存身份证信息
//        BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//        bwIdentityCard.setBorrowerId(bwBorrower.getId());
//        bwIdentityCard = bwIdentityCardService.findBwIdentityCardByAttr(bwIdentityCard);
//        String ocrStartTime = loanAddInfo.getID_Effect_time_OCR();
//        String ocrEndTime = loanAddInfo.getID_Due_time_OCR();
//        if (ocrEndTime != null && ocrStartTime != null) {
//            ocrEndTime = ocrEndTime.replaceAll("-", ".");
//            ocrStartTime = ocrStartTime.replaceAll("-", ".");
//        }
//        if (bwIdentityCard == null) {
//            bwIdentityCard = new BwIdentityCard2();
//            bwIdentityCard.setBorrowerId(bwBorrower.getId());
//            bwIdentityCard.setCreateTime(new Date());
//            bwIdentityCard.setAddress(loanAddInfo.getID_Address_OCR());
//            bwIdentityCard.setGender(loanAddInfo.getID_Sex_OCR());
//            bwIdentityCard.setIdCardNumber(loanAddInfo.getID_Number_OCR());
//            bwIdentityCard.setIssuedBy(loanAddInfo.getID_Issue_Org_OCR());
//            bwIdentityCard.setName(loanAddInfo.getName_OCR());
//            bwIdentityCard.setRace(loanAddInfo.getID_Ethnic_OCR());
//            bwIdentityCard.setValidDate(ocrStartTime + "-" + ocrEndTime);
//            bwIdentityCardService.saveBwIdentityCard(bwIdentityCard);
//        } else {
//            bwIdentityCard.setAddress(loanAddInfo.getID_Address_OCR());
//            bwIdentityCard.setGender(loanAddInfo.getID_Sex_OCR());
//            bwIdentityCard.setIdCardNumber(loanAddInfo.getID_Number_OCR());
//            bwIdentityCard.setIssuedBy(loanAddInfo.getID_Issue_Org_OCR());
//            bwIdentityCard.setName(loanAddInfo.getName_OCR());
//            bwIdentityCard.setRace(loanAddInfo.getID_Ethnic_OCR());
//            bwIdentityCard.setValidDate(ocrStartTime + "-" + ocrEndTime);
//            bwIdentityCard.setUpdateTime(new Date());
//            bwIdentityCardService.updateBwIdentityCard(bwIdentityCard);
//        }
//        // 插入身份认证记录
//        thirdCommonService.addOrUpdateBwOrderAuth(sessionId, bwOrder.getId(), 3, Integer.parseInt(channelId));
//        logger.info(sessionId + ">>> 新增/更新身份证信息");
//
//        // 亲属联系人
//        BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//        if (bwPersonInfo == null) {
//            bwPersonInfo = new BwPersonInfo();
//            bwPersonInfo.setOrderId(orderId);
//            bwPersonInfo.setAddress(loanAddInfo.getAddr_detail());
//            bwPersonInfo.setEmail(loanAddInfo.getUser_email());
//            bwPersonInfo.setQqchat(loanAddInfo.getUser_qq());
//            bwPersonInfo.setWechat(loanAddInfo.getUser_wechat());
//            bwPersonInfo.setCreateTime(new Date());
//            bwPersonInfo.setRelationName(loanAddInfo.getContact1A_name());
//            bwPersonInfo.setRelationPhone(loanAddInfo.getContact1A_number());
//            bwPersonInfo.setUnrelationName(loanAddInfo.getEmergency_contact_personA_name());
//            bwPersonInfo.setUnrelationPhone(loanAddInfo.getEmergency_contact_personA_phone());
//            bwPersonInfo.setMarryStatus(loanAddInfo.getUser_marriage() == 1 ? 0 : 1);
//            bwPersonInfo.setHouseStatus((loanAddInfo.getFamily_live_type() == 1 || loanAddInfo.getFamily_live_type()
//                == 2) ? 1 : 0);
//            bwPersonInfo.setCarStatus(loanAddInfo.getAsset_auto_type() == 1 ? 0 : 1);
//            bwPersonInfoService.add(bwPersonInfo);
//        } else {
//            bwPersonInfo.setAddress(loanAddInfo.getAddr_detail());
//            bwPersonInfo.setEmail(loanAddInfo.getUser_email());
//            bwPersonInfo.setQqchat(loanAddInfo.getUser_qq());
//            bwPersonInfo.setWechat(loanAddInfo.getUser_wechat());
//            bwPersonInfo.setUpdateTime(new Date());
//            bwPersonInfo.setRelationName(loanAddInfo.getContact1A_name());
//            bwPersonInfo.setRelationPhone(loanAddInfo.getContact1A_number());
//            bwPersonInfo.setUnrelationName(loanAddInfo.getEmergency_contact_personA_name());
//            bwPersonInfo.setUnrelationPhone(loanAddInfo.getEmergency_contact_personA_phone());
//            bwPersonInfo.setMarryStatus(loanAddInfo.getUser_marriage() == 1 ? 0 : 1);
//            bwPersonInfo.setHouseStatus((loanAddInfo.getFamily_live_type() == 1 || loanAddInfo.getFamily_live_type()
//                == 2) ? 1 : 0);
//            bwPersonInfo.setCarStatus(loanAddInfo.getAsset_auto_type() == 1 ? 0 : 1);
//            bwPersonInfoService.update(bwPersonInfo);
//        }
//        logger.info(sessionId + ">>> 新增/更新个人信息");
//
//        // 设备信息
//        ExtraInfoContacts extraInfoContacts = loanAddInfo.getContacts();
//        String deviceInfoAll = loanAddInfo.getDevice_info_all();
//        DeviceInfo deviceInfo = new DeviceInfo();
//        if (StringUtils.isNotBlank(deviceInfoAll)) {
//            deviceInfo = JSON.parseObject(deviceInfoAll, DeviceInfo.class);
//        }
//
//        BwDkdh360DeviceInfo bwDkdh360DeviceInfo = bwDkdh360DeviceInfoMapper.findByOrderId(orderId);
//        if (CommUtils.isNull(bwDkdh360DeviceInfo)) {
//            bwDkdh360DeviceInfo = new BwDkdh360DeviceInfo();
//            bwDkdh360DeviceInfo.setOrderId(orderId);
//            bwDkdh360DeviceInfo.setDeviceNum(extraInfoContacts.getDevice_num());
//            bwDkdh360DeviceInfo.setPlatform(extraInfoContacts.getPlatform());
//
//            ExtraInfoContactsAppLocation appLocation = extraInfoContacts.getApp_location();
//            if (!CommUtils.isNull(appLocation)) {
//                bwDkdh360DeviceInfo.setLat(appLocation.getLat());
//                bwDkdh360DeviceInfo.setLon(appLocation.getLon());
//                bwDkdh360DeviceInfo.setAddress(appLocation.getAddress());
//            }
//
//            bwDkdh360DeviceInfo.setDeviceInfo(extraInfoContacts.getDevice_info());
//            bwDkdh360DeviceInfo.setTeleNum(deviceInfo.getTele_num());
//            bwDkdh360DeviceInfo.setTeleName(deviceInfo.getTele_name());
//            bwDkdh360DeviceInfo.setIsRoot(deviceInfo.getIs_root());
//            bwDkdh360DeviceInfo.setDns(deviceInfo.getDns());
//            bwDkdh360DeviceInfo.setMemSize(deviceInfo.getMem_size());
//            bwDkdh360DeviceInfo.setStorageSize(deviceInfo.getStorage_size());
//            bwDkdh360DeviceInfo.setAvaStorageSize(deviceInfo.getAva_storage_size());
//            bwDkdh360DeviceInfo.setPhoneBrand(deviceInfo.getPhone_brand());
//            bwDkdh360DeviceInfo.setDeviceModel(deviceInfo.getDevice_model());
//            bwDkdh360DeviceInfo.setImei(deviceInfo.getImei());
//            bwDkdh360DeviceInfo.setImsi(deviceInfo.getImsi());
//            bwDkdh360DeviceInfo.setSeriaNo(deviceInfo.getSeria_no());
//            bwDkdh360DeviceInfo.setAndroidId(deviceInfo.getAndroid_id());
//            bwDkdh360DeviceInfo.setUdid(deviceInfo.getUdid());
//            bwDkdh360DeviceInfo.setMac(deviceInfo.getMac());
//            bwDkdh360DeviceInfo.setAndroidVer(deviceInfo.getAndroid_ver());
//            bwDkdh360DeviceInfo.setIdfa(deviceInfo.getIdfa());
//            bwDkdh360DeviceInfo.setIdfv(deviceInfo.getIdfv());
//            bwDkdh360DeviceInfo.setIosPlat(deviceInfo.getIos_plat());
//            bwDkdh360DeviceInfo.setIosVer(deviceInfo.getIos_ver());
//            bwDkdh360DeviceInfo.setUuid(deviceInfo.getUuid());
//            bwDkdh360DeviceInfo.setIsSimulator(loanAddInfo.getIs_simulator());
//            bwDkdh360DeviceInfo.setCreateTime(new Date());
//            bwDkdh360DeviceInfoMapper.insert(bwDkdh360DeviceInfo);
//        }
//        logger.info(sessionId + ">>> 新增设备信息");
//
//        // app列表
//        List<InstalledApps> installedAppsList = extraInfoContacts.getInstalled_apps();
//
//        List<BwDkdh360InstalledApps> bwDkdh360InstalledAppsList = bwDkdh360InstalledAppsMapper.findListByOrderId
//            (orderId);
//        if (CollectionUtils.isEmpty(bwDkdh360InstalledAppsList)) {
//            try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,
//                false)) {
//                BwDkdh360InstalledAppsMapper bwDkdh360InstalledAppsMapper = sqlSession.getMapper
//                    (BwDkdh360InstalledAppsMapper.class);
//
//                if (CollectionUtils.isNotEmpty(installedAppsList)) {
//                    installedAppsList.forEach(installedApps -> {
//                        BwDkdh360InstalledApps bwDkdh360InstalledApps = new BwDkdh360InstalledApps();
//                        bwDkdh360InstalledApps.setOrderId(orderId);
//                        bwDkdh360InstalledApps.setName(installedApps.getName());
//                        bwDkdh360InstalledApps.setPackageName(installedApps.getPackageName());
//                        bwDkdh360InstalledApps.setVersionName(installedApps.getVersionName());
//                        bwDkdh360InstalledApps.setCreateTime(new Date());
//
//                        bwDkdh360InstalledAppsMapper.insert(bwDkdh360InstalledApps);
//                    });
//                }
//
//                sqlSession.commit();
//            }
//        }
//        logger.info(sessionId + ">>> 新增app列表信息");
//
//        bwOrder.setStatusId(2L);
//        bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//        bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//        bwOrderService.updateBwOrder(bwOrder);
//
//        // 放入redis
//        SystemAuditDto systemAuditDto = new SystemAuditDto();
//        systemAuditDto.setIncludeAddressBook(0);
//        systemAuditDto.setOrderId(bwOrder.getId());
//        systemAuditDto.setBorrowerId(bwBorrower.getId());
//        systemAuditDto.setName(bwBorrower.getName());
//        systemAuditDto.setPhone(bwBorrower.getPhone());
//        systemAuditDto.setIdCard(bwBorrower.getIdCard());
//        systemAuditDto.setChannel(Integer.valueOf(channelId));
//        systemAuditDto.setThirdOrderId(orderNo);
//        systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//        RedisUtils.hset(SystemConstant.AUDIT_KEY, bwOrder.getId() + "", JsonUtils.toJson(systemAuditDto));
//        logger.info(sessionId + ">>> 修改订单状态，并放入redis");
//
//        // 更改订单进行时间
//        BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//        bwOrderProcessRecord.setSubmitTime(new Date());
//        bwOrderProcessRecord.setOrderId(bwOrder.getId());
//        bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//        logger.info(sessionId + ">>> 更改订单进行时间");
//
//        SxyThirdInterfaceLogUtils.setSxyLog(channelId, orderNo, 200 + "", "success", "三方订单号");
//
//        logger.info(sessionId + ":结束Dkdh360ServiceImpl.pushLoanAddInfo method:success");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "success");
//    }
//
//    @Override
//    public Dkdh360Response getBankCardInfo(long sessionId, CommonQuery commonQuery) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.getBankCardInfo method：" + JSON.toJSONString(commonQuery));
//
//        String orderNo = commonQuery.getOrder_no();
//        if (StringUtils.isNotBlank(orderNo)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.getBankCardInfo method：order_no为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "order_no为空");
//        }
//
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//        if (bwOrderRong == null) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.getBankCardInfo method：第三方订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "第三方订单不存在");
//        }
//
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        if (CommUtils.isNull(bwOrder)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.getBankCardInfo method：订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "订单不存在");
//        }
//
//        Long borrowerId = bwOrder.getBorrowerId();
//
//        BwBorrower bwBorrower = new BwBorrower();
//        bwBorrower.setId(borrowerId);
//        bwBorrower = iBwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//        if (CommUtils.isNull(bwBorrower)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.getBankCardInfo method：用户不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "用户不存在");
//        }
//
//        List<Map<String, Object>> bankCard = new ArrayList<>();
//        // 获取银行卡信息
//        BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrowerId);
//        if (!CommUtils.isNull(bwBankCard) && bwBankCard.getSignStatus() == 4) {
//            Map<String, Object> map = new HashMap<>(16);
//            map.put("open_bank", Dkdh360Utils.getOpenBankByCode(bwBankCard.getBankCode()));
//            map.put("bank_card", bwBankCard.getCardNo());
//            map.put("user_name", bwBorrower.getName());
//            map.put("user_mobile", bwBankCard.getPhone());
//            map.put("use_for", "1");
//            map.put("card_type", 3);
//
//            bankCard.add(map);
//        }
//
//        Map<String, Object> data = new HashMap<>(1);
//        data.put("bank_card", bankCard);
//        logger.info(sessionId + "：结束Dkdh360ServiceImpl.getBankCardInfo method：获取银行卡列表成功");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "获取银行卡列表成功", data);
//    }
//
//    @Override
//    public Dkdh360Response updateBindCardReady(long sessionId, BankCardInfo bankCardInfo) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.updateBindCardReady method：" + JSON.toJSONString(bankCardInfo));
//
//        String orderNo = bankCardInfo.getOrder_no();
//        String bankCard = bankCardInfo.getBank_card();
//        String idNumber = bankCardInfo.getId_number();
//        String openBank = bankCardInfo.getOpen_bank();
//        String userMobile = bankCardInfo.getUser_mobile();
//        String userName = bankCardInfo.getUser_name();
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setThirdOrderNo(orderNo);
//        drainageBindCardVO.setName(userName);
//        drainageBindCardVO.setIdCardNo(idNumber);
//        drainageBindCardVO.setBankCardNo(bankCard);
//        drainageBindCardVO.setRegPhone(userMobile);
//        drainageBindCardVO.setBankCode(Dkdh360Utils.getCodeByOpenBank(openBank));
//        drainageBindCardVO.setChannelId(NumberUtils.toInt(channelId));
//        drainageBindCardVO.setNotify(false);
//
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//
//        SxyThirdInterfaceLogUtils.setSxyLog(channelId, orderNo, drainageRsp.getCode(), drainageRsp.getMessage(),
//            "三方订单号");
//
//        String code = "0000";
//        if (code.equals(drainageRsp.getCode())) {
//            Map<String, String> data = new HashMap<>(1);
//            data.put("need_confirm", "1");
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.updateBindCardReady method：预绑卡成功");
//            return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "预绑卡成功", data);
//        } else {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.updateBindCardReady method：" + drainageRsp.getMessage());
//            return new Dkdh360Response(Dkdh360Response.CODE_FAILURE, drainageRsp.getMessage());
//        }
//    }
//
//    @Override
//    public Dkdh360Response updateBindCardSure(long sessionId, BankCardInfo bankCardInfo) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.updateBindCardSure method：" + JSON.toJSONString(bankCardInfo));
//
//        String orderNo = bankCardInfo.getOrder_no();
//        String bankCard = bankCardInfo.getBank_card();
//        String idNumber = bankCardInfo.getId_number();
//        String openBank = bankCardInfo.getOpen_bank();
//        String userMobile = bankCardInfo.getUser_mobile();
//        String userName = bankCardInfo.getUser_name();
//        String verifyCode = bankCardInfo.getVerify_code();
//
//        DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//        drainageBindCardVO.setThirdOrderNo(orderNo);
//        drainageBindCardVO.setName(userName);
//        drainageBindCardVO.setIdCardNo(idNumber);
//        drainageBindCardVO.setBankCardNo(bankCard);
//        drainageBindCardVO.setRegPhone(userMobile);
//        drainageBindCardVO.setBankCode(Dkdh360Utils.getCodeByOpenBank(openBank));
//        drainageBindCardVO.setChannelId(NumberUtils.toInt(channelId));
//        drainageBindCardVO.setNotify(true);
//        drainageBindCardVO.setVerifyCode(verifyCode);
//
//        DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//
//        SxyThirdInterfaceLogUtils.setSxyLog(channelId, orderNo, drainageRsp.getCode(), drainageRsp.getMessage(),
//            "三方订单号");
//
//        String code = "0000";
//        if (code.equals(drainageRsp.getCode())) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.updateBindCardSure method：确认绑卡成功");
//            return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "确认绑卡成功");
//        } else {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.updateBindCardSure method：" + drainageRsp.getMessage());
//            return new Dkdh360Response(Dkdh360Response.CODE_FAILURE, drainageRsp.getMessage());
//        }
//    }
//
//    @Override
//    public Dkdh360Response queryApprovalStatus(long sessionId, CommonQuery commonQuery) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.queryApprovalStatus method：" + JSON.toJSONString(commonQuery));
//
//        String orderNo = commonQuery.getOrder_no();
//        if (StringUtils.isNotBlank(orderNo)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryApprovalStatus method：order_no为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "order_no为空");
//        }
//
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//        if (bwOrderRong == null) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryApprovalStatus method：第三方订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "第三方订单不存在");
//        }
//
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        if (CommUtils.isNull(bwOrder)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryApprovalStatus method：订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "订单不存在");
//        }
//
//        String approvalStatus = Dkdh360Utils.getApprovalStatus(String.valueOf(bwOrder.getStatusId()));
//        Map<String, Object> map = new HashMap<>(16);
//        Map<String, Object> data = new HashMap<>(1);
//        if ("10".equals(approvalStatus)) {
//            map.put("order_no", orderNo);
//            map.put("conclusion", approvalStatus);
//            map.put("approval_time", bwOrder.getUpdateTime().getTime());
//            map.put("term_unit", 3);
//            map.put("amount_type", 0);
//            map.put("term_type", 0);
//            map.put("approval_amount", bwOrder.getBorrowAmount());
//            map.put("approval_term", 4);
//
//            data.put("data_accept", map);
//        } else if ("40".equals(approvalStatus)) {
//            map.put("order_no", orderNo);
//            map.put("conclusion", approvalStatus);
//            map.put("remark", "信用评分过低#拒绝客户");
//            map.put("refuse_time", bwOrder.getUpdateTime().getTime());
//
//            data.put("data_reject", map);
//        }
//
//        logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryApprovalStatus method：success");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "success", data);
//    }
//
//    @Override
//    public Dkdh360Response trial(long sessionId, CommonQuery commonQuery) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.trial method：" + JSON.toJSONString(commonQuery));
//
//        String orderNo = commonQuery.getOrder_no();
//        String amount = commonQuery.getAmount();
//        if (StringUtils.isNotBlank(orderNo) || StringUtils.isNotBlank(amount)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.trial method：参数为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "参数为空");
//        }
//
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//        if (bwOrderRong == null) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.trial method：第三方订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "第三方订单不存在");
//        }
//
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        if (CommUtils.isNull(bwOrder)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.trial method：订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "订单不存在");
//        }
//
//        if (!amount.equals(String.valueOf(bwOrder.getBorrowAmount()))) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.trial method：金额有误");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "金额有误");
//        }
//
//        // 查询水象云产品
//        BwProductDictionary bwProductDictionary = bwProductDictionaryService
//            .findBwProductDictionaryById(Integer.valueOf(SxyDrainageConstant.productId));
//
//        // 分期利息率
//        Double interestRate = bwProductDictionary.getInterestRate();
//
//        // 每期应还本金
//        Double eachAmount = Double.valueOf(amount) / 4;
//        // 每期应还利息
//        Double periodOne = DrainageUtils.calculateRepayMoney(Double.valueOf(amount), 1, interestRate);
//        Double periodTwo = DrainageUtils.calculateRepayMoney(Double.valueOf(amount), 2, interestRate);
//        Double periodThree = DrainageUtils.calculateRepayMoney(Double.valueOf(amount), 3, interestRate);
//        Double periodFour = DrainageUtils.calculateRepayMoney(Double.valueOf(amount), 4, interestRate);
//
//        Double interestFee = periodOne + periodTwo + periodThree + periodFour;
//        Double amounts = Double.valueOf(amount) + interestFee;
//
//        Double[] periodAmount = {eachAmount + periodOne, eachAmount + periodTwo, eachAmount + periodThree, eachAmount
//            + periodFour};
//
//        Map<String, Object> map = new HashMap<>(16);
//        // 用户卡中收到款的金额，单位元
//        map.put("receive_amount", String.valueOf(bwOrder.getBorrowAmount()));
//        // 放款时预扣除手续费，单位元
//        map.put("service_fee", "0");
//        // 用户每期应还金额，单位元
//        map.put("period_amount", periodAmount);
//        // 用户的总还款额，单位元
//        map.put("pay_amount", String.valueOf(amounts));
//        // 总还款额费用描述
//        map.put("fee_desc", "应还本金：" + String.valueOf(amounts) + "元，应还利息" + String.valueOf(interestFee));
//
//        logger.info(sessionId + "：结束Dkdh360ServiceImpl.trial method：success");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "success", map);
//    }
//
//    @Override
//    public Dkdh360Response pushConfirm(long sessionId, ConfirmInfo confirmInfo) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.pushConfirm method：" + JSON.toJSONString(confirmInfo));
//
//        String orderNo = confirmInfo.getOrder_no();
//        String confirmReturnUrl = confirmInfo.getConfirm_return_url();
//        if (StringUtils.isNotBlank(orderNo) || StringUtils.isNotBlank(confirmReturnUrl)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushConfirm method：参数为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "参数为空");
//        }
//
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//        if (bwOrderRong == null) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushConfirm method：第三方订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "第三方订单不存在");
//        }
//
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        if (CommUtils.isNull(bwOrder)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushConfirm method：订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "订单不存在");
//        }
//
//        Long borrowerId = bwOrder.getBorrowerId();
//
//        BwBorrower bwBorrower = new BwBorrower();
//        bwBorrower.setId(borrowerId);
//        bwBorrower = iBwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//        if (CommUtils.isNull(bwBorrower)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushConfirm method：用户不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "用户不存在");
//        }
//
//        String md5 = "phone=" + bwBorrower.getPhone() + "&order_no=" + orderNo;
//        String md5Value = MD5Util.getMd5Value(md5);
//
//        String confirmUrl = Dkdh360Constant.get("url") + "?phone=" + bwBorrower.getPhone() + "&order_no=" + orderNo +
//            "&platform=4&params=" + md5Value + "&returnUrl=" + confirmReturnUrl;
//        logger.info(sessionId + ":签约地址：" + confirmUrl);
//
//        Map<String, Object> data = new HashMap<>(1);
//        data.put("confirm_url", confirmUrl);
//
//        logger.info(sessionId + "：结束Dkdh360ServiceImpl.pushConfirm method：success");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "success", data);
//    }
//
//    @Override
//    public Dkdh360Response queryContractUrl(long sessionId, CommonQuery commonQuery) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.queryContractUrl method：" + JSON.toJSONString(commonQuery));
//
//        String orderNo = commonQuery.getOrder_no();
//        String conUrl = null;
//        if (StringUtils.isNotBlank(orderNo)) {
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//            if (!CommUtils.isNull(bwOrderRong)) {
//                Long orderId = bwOrderRong.getOrderId();
//                List<BwAdjunct> bwAdjunctList = bwAdjunctService.findBwAdjunctByOrderId(orderId);
//                if (!CommUtils.isNull(bwAdjunctList)) {
//                    for (BwAdjunct bwAdjunct : bwAdjunctList) {
//                        if (bwAdjunct.getAdjunctType() == 29) {
//                            String adjunctPath = bwAdjunct.getAdjunctPath();
//                            conUrl = SystemConstant.PDF_URL + adjunctPath;
//                        }
//                    }
//                }
//            }
//        }
//        if (StringUtils.isBlank(conUrl)) {
//            conUrl = "https://www.sxfq.com/weixinApp3.0/html/Agreement/manageAssociation.html";
//        }
//
//        Map<String, Object> data = new HashMap<>(1);
//        data.put("contract_url", conUrl);
//
//        logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryContractUrl method：success");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "success", data);
//    }
//
//    @Override
//    public Dkdh360Response queryRepaymentPlan(long sessionId, CommonQuery commonQuery) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.queryRepaymentPlan method：" + JSON.toJSONString(commonQuery));
//
//        String orderNo = commonQuery.getOrder_no();
//        if (StringUtils.isNotBlank(orderNo)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepaymentPlan method：order_no为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "order_no为空");
//        }
//
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//        if (bwOrderRong == null) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepaymentPlan method：第三方订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "第三方订单不存在");
//        }
//
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        if (CommUtils.isNull(bwOrder)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepaymentPlan method：订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "订单不存在");
//        }
//
//        // 获取银行卡信息
//        BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(bwOrder.getBorrowerId());
//        if (CommUtils.isNull(bwBankCard)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepaymentPlan method：银行卡信息为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "银行卡信息为空");
//        }
//
//        // 获取还款计划
//        List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//        if (CommUtils.isNull(bwRepaymentPlans)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepaymentPlan method：还款计划为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "还款计划为空");
//        }
//
//        TreeMap<String, Object> result = new TreeMap<>();
//        result.put("order_no", orderNo);
//        //银行编码
//        result.put("open_bank", "");
//        //银行卡号
//        result.put("bank_card", bwBankCard.getCardNo());
//
//        List<Map<String, Object>> repaymentPlanList = new ArrayList<>();
//        //还款计划
//        for (BwRepaymentPlan plan : bwRepaymentPlans) {
//            Map<String, Object> repaymentPlan = new HashMap<>(20);
//            //还款计划编号
//            repaymentPlan.put("period_no", plan.getNumber() + "");
//            //账单状态：
//            repaymentPlan.put("bill_status", plan.getRepayStatus());
//            //还款日
//            repaymentPlan.put("due_time", plan.getRepayTime().getTime() / 1000);
//            //当期最早可以还款的时间
//            repaymentPlan.put("can_repay_time", plan.getCreateTime().getTime() / 1000);
//            //还款方式
//            repaymentPlan.put("pay_type", 5);
//
//            BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//            bwOverdueRecord.setRepayId(plan.getId());
//            bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//            //默认逾期金额
//            Double overdueFee = 0.0;
//            //默认逾期天数
//            Integer overdueDay = 0;
//            if (bwOverdueRecord != null) {
//                Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D :
//                    bwOverdueRecord.getOverdueAccrualMoney();
//                Double advance = bwOverdueRecord.getAdvance();
//                overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//                overdueDay = bwOverdueRecord.getOverdueDay();
//            }
//
//            //当前逾期费用，单位元
//            repaymentPlan.put("overdue_fee", overdueFee);
//            //账单已经逾期的天数
//            repaymentPlan.put("overdue_day", overdueDay);
//            //当前所需的还款金额，单位元
//            repaymentPlan.put("amount", DoubleUtil.sub(DoubleUtil.add(plan.getRealityRepayMoney(), overdueFee), plan
//                .getAlreadyRepayMoney()));
//            //已还款金额，单位元
//            repaymentPlan.put("paid_amount", plan.getAlreadyRepayMoney());
//            //还款成功的时间
//            if (plan.getUpdateTime() != null) {
//                repaymentPlan.put("success_time", plan.getUpdateTime().getTime() / 1000);
//            }
//            //当期还款金额描述
//            if (plan.getRepayStatus() == 2) {
//                repaymentPlan.put("remark", "含本金" + plan.getRepayCorpusMoney() + "元，利息" + plan
//                    .getRepayAccrualMoney() + "元，逾期" + overdueFee + "元");
//            }
//            repaymentPlanList.add(repaymentPlan);
//
//        }
//        result.put("repayment_plan", repaymentPlanList);
//
//        logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepaymentPlan method：success");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "success", result);
//    }
//
//    @Override
//    public Dkdh360Response queryRepayInfo(long sessionId, RepayInfo repayInfo) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.queryRepayInfo method：" + JSON.toJSONString(repayInfo));
//
//        String orderNo = repayInfo.getOrder_no();
//        String periodNos = repayInfo.getPeriod_nos();
//        if (StringUtils.isNotBlank(orderNo) || StringUtils.isNotBlank(periodNos)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepayInfo method：参数为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "参数为空");
//        }
//
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//        if (bwOrderRong == null) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepayInfo method：第三方订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "第三方订单不存在");
//        }
//
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        if (CommUtils.isNull(bwOrder)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepayInfo method：订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "订单不存在");
//        }
//
//        // 获取还款计划
//        List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder.getId());
//        if (CommUtils.isNull(bwRepaymentPlans)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepayInfo method：还款计划为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "还款计划为空");
//        }
//
//        String[] periodNo = periodNos.split(",");
//        List<String> stringList = Arrays.asList(periodNo);
//
//        List<Map<String, String>> repayStat = new ArrayList<>();
//        stringList.forEach(s -> {
//            BwRepaymentPlan bwRepaymentPlan = bwRepaymentPlanService.getPlan(bwOrder.getId(), Integer.parseInt(s));
//            Double realityRepayMoney = bwRepaymentPlan.getRealityRepayMoney();
//
//            BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//            bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
//            bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//            //默认逾期金额
//            Double overdueFee = 0.0;
//            if (!CommUtils.isNull(bwOverdueRecord)) {
//                Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D :
//                    bwOverdueRecord.getOverdueAccrualMoney();
//                Double advance = bwOverdueRecord.getAdvance();
//                overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//            }
//
//            Map<String, String> map = new HashMap<>(3);
//            map.put("period_nos", s);
//            map.put("amount", String.valueOf(DoubleUtil.add(realityRepayMoney, overdueFee)));
//            map.put("overdue_amount", String.valueOf(overdueFee));
//
//            repayStat.add(map);
//        });
//
//        logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryRepayInfo method：success");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "success", repayStat);
//    }
//
//    @Override
//    public Dkdh360Response updatePushRepayInfo(long sessionId, RepayInfo repayInfo) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.updatePushRepayInfo method：" + JSON.toJSONString(repayInfo));
//
//        String orderNo = repayInfo.getOrder_no();
//        DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, orderNo);
//        String code = "000";
//        if (drainageRsp != null) {
//
//            SxyThirdInterfaceLogUtils.setSxyLog(channelId, orderNo, drainageRsp.getCode(), drainageRsp.getMessage(),
//                "三方订单号");
//
//            if (code.equals(drainageRsp.getCode())) {
//                logger.info(sessionId + "：结束Dkdh360ServiceImpl.updatePushRepayInfo method：success");
//                return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "success");
//
//            } else {
//                logger.info(sessionId + "：结束Dkdh360ServiceImpl.updatePushRepayInfo method：" + drainageRsp.getMessage());
//                return new Dkdh360Response(Dkdh360Response.CODE_FAILURE, drainageRsp.getMessage());
//            }
//        } else {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.updatePushRepayInfo method：申请失败");
//            return new Dkdh360Response(Dkdh360Response.CODE_FAILURE, "申请失败");
//        }
//    }
//
//    @Override
//    public Dkdh360Response queryOrderStatus(long sessionId, CommonQuery commonQuery) {
//        logger.info(sessionId + "：开始Dkdh360ServiceImpl.queryOrderStatus method：" + JSON.toJSONString(commonQuery));
//
//        String orderNo = commonQuery.getOrder_no();
//        if (StringUtils.isNotBlank(orderNo)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryOrderStatus method：参数为空");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "参数为空");
//        }
//
//        BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
//        if (bwOrderRong == null) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryOrderStatus method：第三方订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "第三方订单不存在");
//        }
//
//        BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//        if (CommUtils.isNull(bwOrder)) {
//            logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryOrderStatus method：订单不存在");
//            return new Dkdh360Response(Dkdh360Response.CODE_PARAMETER, "订单不存在");
//        }
//
//        Long statusId = bwOrder.getStatusId();
//
//        Map<String, Object> map = new HashMap<>(16);
//        map.put("order_no", orderNo);
//        map.put("order_status", Dkdh360Utils.getOrderStatus(bwOrder.getStatusId().toString()));
//        map.put("update_time", bwOrder.getUpdateTime().getTime() / 1000);
//        if (statusId == 4 || statusId == 11 || statusId == 12 || statusId == 14) {
//            Double borrowAmount = bwOrder.getBorrowAmount();
//            map.put("loan_amount", borrowAmount + "");
//            map.put("loan_term", 28);
//            map.put("receive_amount", borrowAmount + "");
//            map.put("service_fee", "0");
//
//            // 查询水象云产品
//            BwProductDictionary bwProductDictionary = bwProductDictionaryService
//                .findBwProductDictionaryById(Integer.valueOf(SxyDrainageConstant.productId));
//
//            // 分期利息率
//            Double interestRate = bwProductDictionary.getInterestRate();
//
//            // 每期应还本金
//            Double eachAmount = borrowAmount / 4;
//            // 每期应还利息
//            Double periodOne = DrainageUtils.calculateRepayMoney(borrowAmount, 1, interestRate);
//            Double periodTwo = DrainageUtils.calculateRepayMoney(borrowAmount, 2, interestRate);
//            Double periodThree = DrainageUtils.calculateRepayMoney(borrowAmount, 3, interestRate);
//            Double periodFour = DrainageUtils.calculateRepayMoney(borrowAmount, 4, interestRate);
//
//            Double interestFee = periodOne + periodTwo + periodThree + periodFour;
//            Double amounts = borrowAmount + interestFee;
//
//            Double[] periodAmount = {eachAmount + periodOne, eachAmount + periodTwo, eachAmount + periodThree,
//                eachAmount
//                    + periodFour};
//
//            map.put("pay_amount", amounts + "");
//            map.put("period_amount", String.valueOf(periodAmount));
//        }
//
//        logger.info(sessionId + "：结束Dkdh360ServiceImpl.queryOrderStatus method：success");
//        return new Dkdh360Response(Dkdh360Response.CODE_SUCCESS, "success", map);
//    }
//
//}
