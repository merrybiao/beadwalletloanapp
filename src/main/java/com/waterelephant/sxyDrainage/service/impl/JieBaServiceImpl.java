//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.*;
//import com.waterelephant.mapper.BwOperateVoiceMapper;
//import com.waterelephant.service.*;
//import com.waterelephant.service.impl.BwContactListService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageEnum;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//import com.waterelephant.sxyDrainage.entity.jieba.*;
//import com.waterelephant.sxyDrainage.service.AsyncJbTask;
//import com.waterelephant.sxyDrainage.service.CommonService;
//import com.waterelephant.sxyDrainage.service.JieBaService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.OrderStatusEnum;
//import com.waterelephant.sxyDrainage.utils.SxyDrainageConstant;
//import com.waterelephant.sxyDrainage.utils.interfaceLog.SxyThirdInterfaceLogUtils;
//import com.waterelephant.sxyDrainage.utils.jieba.JieBaConstant;
//import com.waterelephant.sxyDrainage.utils.jieba.JieBaUtil;
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
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * (code:jb001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 15:43 2018/6/13
// * @Modified By:
// */
//@Service
//public class JieBaServiceImpl implements JieBaService {
//    private Logger logger = Logger.getLogger(JieBaServiceImpl.class);
//    private String channelStr = JieBaConstant.channelId;
//    private String productId = SxyDrainageConstant.productId;
//
//    private CommonService commonService;
//    private ThirdCommonService thirdCommonService;
//    private IBwOrderService bwOrderService;
//    private BwOrderRongService bwOrderRongService;
//    private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//    private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
//    private IBwWorkInfoService bwWorkInfoService;
//    private BwContactListService bwContactListService;
//    private BwOperateBasicService bwOperateBasicService;
//    private BwOperateVoiceService bwOperateVoiceService;
//    private SqlSessionTemplate sqlSessionTemplate;
//    private IBwPersonInfoService bwPersonInfoService;
//    private AsyncJbTask asyncJbTask;
//    private BwJbContactsService bwJbContactsService;
//    private BwOrderProcessRecordService bwOrderProcessRecordService;
//    private IBwBorrowerService iBwBorrowerService;
//    private BwProductDictionaryService bwProductDictionaryService;
//    private IBwRepaymentPlanService bwRepaymentPlanService;
//    private BwOverdueRecordService bwOverdueRecordService;
//
//    @Autowired
//    public JieBaServiceImpl(CommonService commonService, ThirdCommonService thirdCommonService, IBwOrderService
//        bwOrderService,
//                            BwOrderRongService bwOrderRongService, IBwMerchantOrderService bwMerchantOrderServiceImpl,
//                            BwIdentityCardServiceImpl bwIdentityCardServiceImpl, IBwWorkInfoService bwWorkInfoService,
//                            BwContactListService bwContactListService, BwOperateBasicService bwOperateBasicService,
//                            BwOperateVoiceService bwOperateVoiceService, SqlSessionTemplate sqlSessionTemplate,
//                            IBwPersonInfoService bwPersonInfoService, AsyncJbTask asyncJbTask,
//                            BwJbContactsService bwJbContactsService, BwOrderProcessRecordService
//                                bwOrderProcessRecordService,
//                            IBwBorrowerService iBwBorrowerService, BwProductDictionaryService
//                                bwProductDictionaryService,
//                            IBwRepaymentPlanService bwRepaymentPlanService, BwOverdueRecordService
//                                bwOverdueRecordService) {
//        this.commonService = commonService;
//        this.thirdCommonService = thirdCommonService;
//        this.bwOrderService = bwOrderService;
//        this.bwOrderRongService = bwOrderRongService;
//        this.bwMerchantOrderServiceImpl = bwMerchantOrderServiceImpl;
//        this.bwIdentityCardServiceImpl = bwIdentityCardServiceImpl;
//        this.bwWorkInfoService = bwWorkInfoService;
//        this.bwContactListService = bwContactListService;
//        this.bwOperateBasicService = bwOperateBasicService;
//        this.bwOperateVoiceService = bwOperateVoiceService;
//        this.sqlSessionTemplate = sqlSessionTemplate;
//        this.bwPersonInfoService = bwPersonInfoService;
//        this.asyncJbTask = asyncJbTask;
//        this.bwJbContactsService = bwJbContactsService;
//        this.bwOrderProcessRecordService = bwOrderProcessRecordService;
//        this.iBwBorrowerService = iBwBorrowerService;
//        this.bwProductDictionaryService = bwProductDictionaryService;
//        this.bwRepaymentPlanService = bwRepaymentPlanService;
//        this.bwOverdueRecordService = bwOverdueRecordService;
//    }
//
//    @Override
//    public JieBaResponse checkUser(Long sessionId, CheckUser checkUser) {
//        logger.info(sessionId + "：开始JieBaServiceImpl checkUser method：" + JSON.toJSONString(checkUser));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            Map<String, Object> map = new HashMap<>(16);
//            String userName = checkUser.getUser_name();
//            String userPhone = checkUser.getUser_mobile();
//            String userIdCard = checkUser.getId_card();
//
//            DrainageRsp drainageRsp = commonService.checkUser(sessionId, userName, userPhone, userIdCard);
//            String code = drainageRsp.getCode();
//            // 验证数据是否为空
//            if (DrainageEnum.CODE_PARAMETER.getCode().equals(code)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage(drainageRsp.getMessage());
//                logger.info(sessionId + "：结束JieBaServiceImpl checkUser method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//            // 验证是否符合申请要求
//            if (DrainageEnum.CODE_RULE_BLACKLIST.getCode().equals(code)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage(drainageRsp.getMessage());
//                logger.info(sessionId + "：结束JieBaServiceImpl checkUser method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//            if (DrainageEnum.CODE_RULE_ISPROCESSING.getCode().equals(code)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage(drainageRsp.getMessage());
//                map.put("reason", "C001");
//                jieBaResponse.setData(map);
//                logger.info(sessionId + "：结束JieBaServiceImpl checkUser method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//            if (DrainageEnum.CODE_RULE_AGE_UNMATCH.getCode().equals(code)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage(drainageRsp.getMessage());
//                logger.info(sessionId + "：结束JieBaServiceImpl checkUser method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//            if (DrainageEnum.CODE_RULE_ISREJECT.getCode().equals(code)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage(drainageRsp.getMessage());
//                map.put("reason", "C003");
//                jieBaResponse.setData(map);
//                logger.info(sessionId + "：结束JieBaServiceImpl checkUser method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            jieBaResponse.setCode(200);
//            jieBaResponse.setMessage("请求成功");
//
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaServiceImpl checkUser method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaServiceImpl checkUser method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @Override
//    public JieBaResponse savePushOrderInfo(Long sessionId, PushOrderInfo pushOrderInfo) {
//        //logger.info(sessionId + "：开始JieBaServiceImpl pushOrderInfo method：" + JSON.toJSONString(pushOrderInfo));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        String thirdOrderNo = null;
//        try {
//            //验证参数
//            AddInfo addInfo = pushOrderInfo.getAddInfo();
//            ApplyDetail applyDetail = pushOrderInfo.getApplyDetail();
//            OrderInfo orderInfo = pushOrderInfo.getOrderInfo();
//            if (CommUtils.isNull(addInfo) || CommUtils.isNull(applyDetail) || CommUtils.isNull(orderInfo)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaServiceImpl pushOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            thirdOrderNo = orderInfo.getOrder_id();
//            logger.info(sessionId + "：借吧订单号：" + thirdOrderNo);
//
//            String idCard = applyDetail.getId_card();
//            String fullName = orderInfo.getFull_name();
//            String phone = orderInfo.getPhone();
//
//            //判断用户是否有多个账号，是否有进行中的订单
//            boolean flag = thirdCommonService.checkUserAccountProgressOrder(sessionId, idCard);
//            if (flag) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("存在进行中的订单，请勿重复推送");
//                logger.info(sessionId + "：结束JieBaServiceImpl pushOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            // 新增或更新借款人
//            BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, fullName, idCard, phone,
//                NumberUtils.toInt(channelStr));
//            long borrowerId = borrower.getId();
//            logger.info(sessionId + ">>> 新增/更新借款人ID：" + borrowerId);
//
//            // 判断是否有草稿状态的订单
//            BwOrder bwOrder = new BwOrder();
//            bwOrder.setBorrowerId(borrowerId);
//            bwOrder.setStatusId(1L);
//            bwOrder.setProductType(2);
//            bwOrder.setChannel(NumberUtils.toInt(channelStr));
//            bwOrder.setProductId(NumberUtils.toInt(productId));
//
//            // 先查询草稿状态的订单
//            List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
//            if (boList != null && boList.size() > 0) {
//                bwOrder = boList.get(0);
//                bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwOrder.setExpectMoney(orderInfo.getApply_amount());
//                bwOrder.setExpectNumber(4);
//                bwOrder.setRepayType(2);
//                bwOrderService.updateBwOrder(bwOrder);
//            } else {
//                bwOrder = new BwOrder();
//                bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//                bwOrder.setBorrowerId(borrower.getId());
//                bwOrder.setStatusId(1L);
//                bwOrder.setCreateTime(Calendar.getInstance().getTime());
//                bwOrder.setChannel(NumberUtils.toInt(channelStr));
//                bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//                bwOrder.setApplyPayStatus(0);
//                bwOrder.setProductId(NumberUtils.toInt(productId));
//                bwOrder.setRepayType(2);
//                bwOrder.setProductType(2);
//                bwOrder.setExpectMoney(orderInfo.getApply_amount());
//                bwOrder.setExpectNumber(4);
//                bwOrderService.addBwOrder(bwOrder);
//            }
//            long orderId = bwOrder.getId();
//            logger.info(sessionId + ">>> 新增/更新订单：ID = " + orderId);
//
//            //用于获取征信数据
//            try {
//                String key = "phone_apply";
//                Map<String, Object> params = new HashMap<>(16);
//                params.put("mobile", phone);
//                params.put("order_id", orderId);
//                params.put("borrower_id", borrowerId);
//                String value = JSON.toJSONString(params);
//                RedisUtils.rpush(key, value);
//
//                logger.info(sessionId + ">>> 获取征信数据：" + JSON.toJSONString(params));
//            } catch (Exception e) {
//                logger.info(sessionId + ">>> 获取征信数据异常：", e);
//            }
//
//            // 判断是否有三方订单
//            BwOrderRong bwOrderRong = new BwOrderRong();
//            bwOrderRong.setOrderId(orderId);
//            bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//            if (bwOrderRong == null) {
//                bwOrderRong = new BwOrderRong();
//                bwOrderRong.setOrderId(orderId);
//                bwOrderRong.setThirdOrderNo(thirdOrderNo);
//                bwOrderRong.setChannelId(Long.valueOf(channelStr));
//                bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//                bwOrderRongService.save(bwOrderRong);
//            } else {
//                bwOrderRong.setChannelId(Long.valueOf(channelStr));
//                bwOrderRong.setThirdOrderNo(thirdOrderNo);
//                bwOrderRongService.update(bwOrderRong);
//            }
//            logger.info(sessionId + ">>> 新增/更新融三方订单");
//
//            // 判断是否有商户订单信息
//            BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//            bwMerchantOrder.setOrderId(orderId);
//            bwMerchantOrder = bwMerchantOrderServiceImpl.selectByAtt(bwMerchantOrder);
//            if (bwMerchantOrder == null) {
//                bwMerchantOrder = new BwMerchantOrder();
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setCreateTime(Calendar.getInstance().getTime());
//                bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrderServiceImpl.insertByAtt(bwMerchantOrder);
//            } else {
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwMerchantOrderServiceImpl.updateByAtt(bwMerchantOrder);
//            }
//            logger.info(sessionId + ">>> 新增/更新商户订单");
//
//            // 保存身份证信息
//            BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//            bwIdentityCard.setBorrowerId(borrowerId);
//            bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
//            if (bwIdentityCard == null) {
//                bwIdentityCard = new BwIdentityCard2();
//                bwIdentityCard.setBorrowerId(borrowerId);
//                bwIdentityCard.setCreateTime(new Date());
//                bwIdentityCard.setIdCardNumber(idCard);
//                bwIdentityCard.setName(fullName);
//                bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCard);
//            } else {
//                bwIdentityCard.setIdCardNumber(idCard);
//                bwIdentityCard.setName(fullName);
//                bwIdentityCard.setUpdateTime(new Date());
//                bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//            }
//            logger.info(sessionId + ">>> 新增/更新身份证信息:id=" + bwIdentityCard.getId());
//
//            // 判断是否有工作信息
//            BwWorkInfo bwWorkInfo = new BwWorkInfo();
//            bwWorkInfo.setOrderId(orderId);
//            bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//            if (CommUtils.isNull(bwWorkInfo)) {
//                bwWorkInfo = new BwWorkInfo();
//                bwWorkInfo.setOrderId(orderId);
//                bwWorkInfo.setCallTime("10:00 - 12:00");
//                bwWorkInfo.setCreateTime(Calendar.getInstance().getTime());
//                bwWorkInfo.setIncome(applyDetail.getMonth_income().toString());
//                bwWorkInfo.setIndustry(JieBaUtil.getWorkType(applyDetail.getJob_identity()));
//                bwWorkInfoService.save(bwWorkInfo, borrowerId);
//            } else {
//                bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//                bwWorkInfo.setIndustry(JieBaUtil.getWorkType(applyDetail.getJob_identity()));
//                bwWorkInfo.setIncome(bwWorkInfo.getIncome());
//                bwWorkInfoService.update(bwWorkInfo);
//            }
//            logger.info(sessionId + ">>> 新增/更新工作信息");
//
//            // 亲属联系人,个人信息
//            BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//            if (bwPersonInfo == null) {
//                bwPersonInfo = new BwPersonInfo();
//                bwPersonInfo.setWechat(applyDetail.getWechat());
//                bwPersonInfo.setQqchat(applyDetail.getQq());
//                bwPersonInfo.setCreateTime(new Date());
//                bwPersonInfo.setOrderId(orderId);
//                bwPersonInfo.setCarStatus(applyDetail.getHas_car() == 0 ? 0 : 1);
//                bwPersonInfoService.add(bwPersonInfo);
//            } else {
//                bwPersonInfo.setWechat(applyDetail.getWechat());
//                bwPersonInfo.setQqchat(applyDetail.getQq());
//                bwPersonInfo.setUpdateTime(new Date());
//                bwPersonInfo.setCarStatus(applyDetail.getHas_car() == 0 ? 0 : 1);
//                bwPersonInfoService.update(bwPersonInfo);
//            }
//            logger.info(sessionId + ">>> 新增/更新个人信息");
//
//            Mobile mobile = addInfo.getMobile();
//            Families families = mobile.getFamilies();
//            String familyNum = families.getFamily_num();
//            List<FamiliesItems> familiesItems = families.getItems();
//
//            // 通讯录
//            int num = 5;
//            if (NumberUtils.toInt(familyNum) < num) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("通讯录、联系人为空或者数量小于5个，请检查！");
//                logger.info(sessionId + "：结束JieBaServiceImpl pushOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//                return jieBaResponse;
//            }
//
//            List<BwContactList> listConS = new ArrayList<>();
//            for (FamiliesItems items : familiesItems) {
//                if (CommUtils.isNull(items.getLong_member())) {
//                    continue;
//                }
//                if (CommUtils.isNull(items.getMember_type())) {
//                    continue;
//                }
//                BwContactList bwContactList = new BwContactList();
//                bwContactList.setBorrowerId(borrowerId);
//                bwContactList.setPhone(items.getLong_member());
//                bwContactList.setName(items.getMember_type());
//
//                listConS.add(bwContactList);
//            }
//            bwContactListService.addOrUpdateBwContactLists(listConS, borrowerId);
//            logger.info(sessionId + ">>> 新增/更新通讯录信息 ");
//
//            // 通话记录
//            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            // 根据手机号查询最近一次抓取的通话记录时间
//            Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//            long start = System.currentTimeMillis();
//            //使用批处理
//            List<Calls> calls = mobile.getCalls();
//            int size = 0;
//            try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,
//                false)) {
//                BwOperateVoiceMapper mapper = sqlSession.getMapper(BwOperateVoiceMapper.class);
//                if (CollectionUtils.isNotEmpty(calls)) {
//                    for (Calls call : calls) {
//                        List<CallsItems> items = call.getItems();
//                        size += call.getTotal_size();
//                        if (CollectionUtils.isNotEmpty(items)) {
//                            for (CallsItems callsItems : items) {
//                                try {
//                                    if (callDate == null || sdfs.parse(callsItems.getTime()).after(callDate)) {
//                                        BwOperateVoice bwOperateVoice = new BwOperateVoice();
//                                        bwOperateVoice.setUpdateTime(Calendar.getInstance().getTime());
//                                        bwOperateVoice.setBorrower_id(borrowerId);
//                                        // 通话时间
//                                        bwOperateVoice.setCall_time(callsItems.getTime());
//                                        // 呼叫类型
//                                        bwOperateVoice.setCall_type("DIAL".equals(callsItems.getDial_type()) ? 1 : 2);
//                                        String peerNumber = callsItems.getPeer_number();
//                                        if (peerNumber != null && peerNumber.length() > 20) {
//                                            peerNumber = peerNumber.substring(0, 19);
//                                        }
//                                        // 对方号码
//                                        bwOperateVoice.setReceive_phone(peerNumber);
//                                        // 通话地点
//                                        bwOperateVoice.setTrade_addr(callsItems.getLocation());
//                                        // 通话时长
//                                        bwOperateVoice.setTrade_time(callsItems.getDuration());
//                                        // 通信类型：1.本地市话,国内呼转
//                                        bwOperateVoice.setTrade_type("本地市话".equals(callsItems.getLocation_type()) ? 1
//                                            : 2);
//                                        mapper.insert(bwOperateVoice);
//                                    }
//                                } catch (Exception e) {
//                                    logger.error("保存通话记录异常,忽略此条通话记录...", e);
//                                }
//                            }
//                        }
//                    }
//                }
//                sqlSession.commit();
//            }
//            long end = System.currentTimeMillis();
//            logger.info(sessionId + ">>> 处理【" + size + "】条通话记录时间(ms)：" + (end - start));
//
//            // 运营商基础信息
//            BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//            if (CommUtils.isNull(bwOperateBasic)) {
//                bwOperateBasic = new BwOperateBasic();
//                bwOperateBasic.setBorrowerId(borrowerId);
//                bwOperateBasic.setCreateTime(new Date());
//                // 号码类型China_MOBILE(移动)，CHINA_UNICOM(联通)，China _TELECOM(电信)
//                bwOperateBasic.setUserSource(mobile.getCarrier());
//                bwOperateBasic.setIdCard(CommUtils.isNull(mobile.getIdcard()) ? null : mobile.getIdcard());
//                // 注册该号码所填写的地址
//                bwOperateBasic.setAddr((CommUtils.isNull(mobile.getProvince()) ? null : mobile.getProvince()) +
//                    (CommUtils.isNull(mobile.getCity()) ? null : mobile.getCity()));
//                // 本机号码
//                bwOperateBasic.setPhone(mobile.getMobile());
//                // 当前账户余额
//                bwOperateBasic.setPhoneRemain(CommUtils.isNull(mobile.getAvailable_balance()) ? null :
//                    String.valueOf(mobile.getAvailable_balance() / 100));
//                bwOperateBasic.setRealName(CommUtils.isNull(mobile.getName()) ? null : mobile.getName());
//
//                if (StringUtils.isNotBlank(mobile.getOpen_time())) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    bwOperateBasic.setRegTime(sdf.parse(mobile.getOpen_time()));
//                }
//                bwOperateBasic.setStarLevel(CommUtils.isNull(mobile.getLevel()) ? null : mobile.getLevel());
//                String packageName = mobile.getPackage_name();
//                if (packageName != null && packageName.length() > 55) {
//                    packageName = packageName.substring(0, 50);
//                }
//                bwOperateBasic.setPackageName(packageName);
//                bwOperateBasic.setPhoneStatus(mobile.getMessage());
//                bwOperateBasicService.save(bwOperateBasic);
//            } else {
//                bwOperateBasic.setBorrowerId(borrowerId);
//                bwOperateBasic.setUpdateTime(new Date());
//                bwOperateBasic.setBorrowerId(borrower.getId());
//                // 号码类型China_MOBILE(移动)，CHINA_UNICOM(联通)，China _TELECOM(电信)
//                bwOperateBasic.setUserSource(mobile.getCarrier());
//                bwOperateBasic.setIdCard(CommUtils.isNull(mobile.getIdcard()) ? null : mobile.getIdcard());
//                // 注册该号码所填写的地址
//                bwOperateBasic.setAddr(mobile.getProvince() + mobile.getCity());
//                // 本机号码
//                bwOperateBasic.setPhone(mobile.getMobile());
//                // 当前账户余额
//                String phoneRemain = "0";
//                DecimalFormat df = new DecimalFormat("#0.00");
//                bwOperateBasic.setPhoneRemain(df.format((Double.parseDouble(phoneRemain) / 100)));
//                bwOperateBasic.setRealName(CommUtils.isNull(mobile.getName()) ? null : mobile.getName());
//
//                if (StringUtils.isNotBlank(mobile.getOpen_time())) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    bwOperateBasic.setRegTime(sdf.parse(mobile.getOpen_time()));
//                }
//                bwOperateBasic.setStarLevel(CommUtils.isNull(mobile.getLevel()) ? null : mobile.getLevel());
//                String packageName = mobile.getPackage_name();
//                if (packageName != null && packageName.length() > 55) {
//                    packageName = packageName.substring(0, 50);
//                }
//                bwOperateBasic.setPackageName(packageName);
//                bwOperateBasic.setPhoneStatus(mobile.getMessage());
//                bwOperateBasicService.update(bwOperateBasic);
//            }
//            logger.info(sessionId + ">>> 处理运营商基础信息");
//
//            //异步处理运营商的其他数据
//            asyncJbTask.saveOperator(sessionId, addInfo, orderId);
//
//            // 插入运营商认证记录
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, Integer.parseInt(channelStr));
//
//            jieBaResponse.setCode(200);
//            jieBaResponse.setMessage("请求成功");
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaServiceImpl pushOrderInfo method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        } finally {
//            SxyThirdInterfaceLogUtils.setSxyLog(channelStr, thirdOrderNo, jieBaResponse.getCode() + "", jieBaResponse
//                .getMessage(), "三方订单号");
//        }
//        logger.info(sessionId + "：结束JieBaServiceImpl pushOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @Override
//    public JieBaResponse savePushAddOrderInfo(Long sessionId, PushAddOrderInfo pushAddOrderInfo) {
//        logger.info(sessionId + "：开始JieBaServiceImpl pushAddOrderInfo method：" + JSON.toJSONString(pushAddOrderInfo));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        String thirdOrderNo = null;
//        try {
//            thirdOrderNo = pushAddOrderInfo.getOrder_id();
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("第三方订单号不存在");
//                logger.info(sessionId + "：结束JieBaServiceImpl pushAddOrderInfo method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (bwOrderRong == null) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("第三方订单不存在");
//                logger.info(sessionId + "：结束JieBaServiceImpl pushAddOrderInfo method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//            // 如果当前订单不存在表示订单基本信息未推送
//            if (CommUtils.isNull(bwOrder)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("我方不存在该订单");
//                logger.info(sessionId + "：结束JieBaServiceImpl pushAddOrderInfo method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            Long orderId = bwOrder.getId();
//            Long borrowerId = bwOrder.getBorrowerId();
//
//            // 查询是否有进行中的订单
//            long count = bwOrderService.findProOrder(borrowerId + "");
//            logger.info(sessionId + ">>> 进行中的订单校验：" + count);
//            if (count > 0) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("存在进行中的订单，请勿重复推送");
//                logger.info(sessionId + "：结束JieBaServiceImpl pushAddOrderInfo method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            BwBorrower bwBorrower = new BwBorrower();
//            bwBorrower.setId(borrowerId);
//            bwBorrower = iBwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//            if (CommUtils.isNull(bwBorrower)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("我方不存在该用户");
//                logger.info(sessionId + "：结束JieBaServiceImpl pushAddOrderInfo method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            // 更新身份证信息
//            BwWorkInfo bwWorkInfo = new BwWorkInfo();
//            bwWorkInfo.setOrderId(orderId);
//            bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//            bwWorkInfo.setComName(pushAddOrderInfo.getCompany_name());
//            bwWorkInfo.setUpdateTime(new Date());
//            bwWorkInfoService.update(bwWorkInfo);
//            // 插入个人认证记录
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, Integer.parseInt(channelStr));
//            logger.info(sessionId + ">>> 更新工作信息");
//
//            // 认证图片
//            List<String> obverseImg = pushAddOrderInfo.getObverse_img();
//            List<String> reverseImg = pushAddOrderInfo.getReverse_img();
//            List<String> humanImg = pushAddOrderInfo.getHuman_img();
//
//            String frontFile = obverseImg.get(obverseImg.size() - 1);
//            String backFile = reverseImg.get(reverseImg.size() - 1);
//            String natureFile = humanImg.get(humanImg.size() - 1);
//
//            // 身份证正面照
//            String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01");
//            logger.info(sessionId + ">>> 身份证正面 " + frontImage);
//            // 保存身份证正面照
//            thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId, borrowerId, 0);
//
//
//            // 身份证反面照
//            String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02");
//            logger.info(sessionId + ">>> 身份证反面 " + backImage);
//            // 保存身份证反面照
//            thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId, borrowerId, 0);
//
//            // 活体照
//            String handlerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03");
//            logger.info(sessionId + ">>> 活体照/人脸 " + handlerImage);
//            // 保存活体照
//            thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handlerImage, null, orderId, borrowerId, 0);
//            logger.info(sessionId + ">>> 处理认证图片 ");
//
//            // 插入身份认证记录
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, Integer.parseInt(channelStr));
//
//            //处理个人信息
//            String emergencyContactAName = pushAddOrderInfo.getEmergency_contact_A_name();
//            String emergencyContactAPhone = pushAddOrderInfo.getEmergency_contact_A_phone();
//            String emergencyContactBName = pushAddOrderInfo.getEmergency_contact_B_name();
//            String emergencyContactBPhone = pushAddOrderInfo.getEmergency_contact_B_phone();
//
//            List<BwContactList> bwContactLists = bwContactListService.findBwContactListByBorrowerId(borrowerId);
//            if (CollectionUtils.isEmpty(bwContactLists)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("通讯录为空");
//                logger.info(sessionId + "：结束JieBaServiceImpl pushAddOrderInfo method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//            List<Map<String, String>> listContact = new ArrayList<>();
//            for (BwContactList bwContactList : bwContactLists) {
//                // 从通讯录中获取联系人，需要去重
//                Map<String, String> map = new HashMap<>(16);
//                if (listContact.size() < 3) {
//                    if (bwContactList.getPhone().equals(emergencyContactAPhone) || bwContactList.getPhone().equals
//                        (emergencyContactBPhone)) {
//                        continue;
//                    }
//                    map.put("name", bwContactList.getName());
//                    map.put("phone", bwContactList.getPhone());
//                    listContact.add(map);
//                }
//            }
//
//            // 亲属联系人,个人信息
//            BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//            bwPersonInfo.setAddress(pushAddOrderInfo.getDetail_address());
//            bwPersonInfo.setEmail(pushAddOrderInfo.getEmail());
//            bwPersonInfo.setRelationName(emergencyContactAName);
//            bwPersonInfo.setRelationPhone(emergencyContactAPhone);
//            bwPersonInfo.setUnrelationName(emergencyContactBName);
//            bwPersonInfo.setUnrelationPhone(emergencyContactBPhone);
//            bwPersonInfo.setColleagueName(listContact.get(0).get("name"));
//            bwPersonInfo.setColleaguePhone(listContact.get(0).get("phone"));
//            bwPersonInfo.setFriend1Name(listContact.get(1).get("name"));
//            bwPersonInfo.setFriend1Phone(listContact.get(1).get("phone"));
//            bwPersonInfo.setFriend2Name(listContact.get(2).get("name"));
//            bwPersonInfo.setFriend2Phone(listContact.get(2).get("phone"));
//            bwPersonInfo.setMarryStatus(pushAddOrderInfo.getUser_marriage() == 0 ? 0 : 1);
//            bwPersonInfo.setUpdateTime(new Date());
//            bwPersonInfoService.update(bwPersonInfo);
//            logger.info(sessionId + ">>> 更新个人信息");
//
//            //设配信息
//            Contacts contacts = pushAddOrderInfo.getContacts();
//            BwJbContacts bwJbContacts = new BwJbContacts();
//            bwJbContacts.setOrderId(orderId);
//            bwJbContacts = bwJbContactsService.findByAttr(bwJbContacts);
//            if (CommUtils.isNull(bwJbContacts)) {
//                bwJbContacts = new BwJbContacts();
//                bwJbContacts.setOrderId(orderId);
//                bwJbContacts.setAppLocation(contacts.getApp_location());
//                bwJbContacts.setCreateTime(new Date());
//                bwJbContacts.setDevNo(contacts.getDev_no());
//                bwJbContacts.setDevNum(contacts.getDev_num());
//                bwJbContacts.setDevVersion(contacts.getDev_version());
//                bwJbContacts.setPlatform(contacts.getPlatform());
//                bwJbContactsService.saveBwJbContactsByAttr(bwJbContacts);
//            } else {
//                bwJbContacts.setAppLocation(contacts.getApp_location());
//                bwJbContacts.setUpdateTime(new Date());
//                bwJbContacts.setDevNo(contacts.getDev_no());
//                bwJbContacts.setDevNum(contacts.getDev_num());
//                bwJbContacts.setDevVersion(contacts.getDev_version());
//                bwJbContacts.setPlatform(contacts.getPlatform());
//                bwJbContactsService.updateByAttr(bwJbContacts);
//            }
//            logger.info(sessionId + ">>> 新增/更新设配信息");
//
//            bwOrder.setStatusId(2L);
//            bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//            bwOrder.setSubmitTime(Calendar.getInstance().getTime());
//            bwOrderService.updateBwOrder(bwOrder);
//
//            // 放入redis
//            SystemAuditDto systemAuditDto = new SystemAuditDto();
//            systemAuditDto.setIncludeAddressBook(0);
//            systemAuditDto.setOrderId(orderId);
//            systemAuditDto.setBorrowerId(borrowerId);
//            systemAuditDto.setName(bwBorrower.getName());
//            systemAuditDto.setPhone(bwBorrower.getPhone());
//            systemAuditDto.setIdCard(bwBorrower.getIdCard());
//            systemAuditDto.setChannel(Integer.valueOf(channelStr));
//            systemAuditDto.setThirdOrderId(thirdOrderNo);
//            systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//            RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
//            logger.info(sessionId + ">>> 修改订单状态，并放入redis");
//
//            // 更改订单进行时间
//            BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//            bwOrderProcessRecord.setSubmitTime(new Date());
//            bwOrderProcessRecord.setOrderId(bwOrder.getId());
//            bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//            logger.info(sessionId + ">>> 更改订单进行时间");
//
//            jieBaResponse.setCode(200);
//            jieBaResponse.setMessage("请求成功");
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaServiceImpl pushAddOrderInfo method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        } finally {
//            SxyThirdInterfaceLogUtils.setSxyLog(channelStr, thirdOrderNo, jieBaResponse.getCode() + "", jieBaResponse
//                .getMessage(), "三方订单号");
//        }
//        logger.info(sessionId + "：结束JieBaServiceImpl pushAddOrderInfo method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @Override
//    public JieBaResponse updateBindCardReady(Long sessionId, BindCardInfo bindCardInfo) {
//        logger.info(sessionId + "：开始JieBaServiceImpl updateBindCardReady method：" + JSON.toJSONString(bindCardInfo));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        String thirdOrderNo = null;
//        try {
//            thirdOrderNo = bindCardInfo.getOrder_no();
//            String userName = bindCardInfo.getUser_name();
//            String userMobile = bindCardInfo.getUser_mobile();
//            String idNumber = bindCardInfo.getId_number();
//            String bankCard = bindCardInfo.getBank_card();
//            String bankCode = bindCardInfo.getBank_code();
//            String bankMobile = bindCardInfo.getBank_mobile();
//
//            DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//            drainageBindCardVO.setThirdOrderNo(thirdOrderNo);
//            drainageBindCardVO.setName(userName);
//            drainageBindCardVO.setIdCardNo(idNumber);
//            drainageBindCardVO.setBankCardNo(bankCard);
//            drainageBindCardVO.setRegPhone(bankMobile);
//            drainageBindCardVO.setPhone(userMobile);
//            drainageBindCardVO.setBankCode(JieBaUtil.convertBankCodeToFuYou(bankCode));
//            drainageBindCardVO.setChannelId(NumberUtils.toInt(channelStr));
//            drainageBindCardVO.setNotify(false);
//
//            DrainageRsp drainageRsp = commonService.saveBindCard_NewReady(sessionId, drainageBindCardVO);
//            String code = "0000";
//            if (code.equals(drainageRsp.getCode())) {
//                jieBaResponse.setCode(200);
//                jieBaResponse.setMessage("预绑卡成功");
//            } else {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage(drainageRsp.getMessage());
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaServiceImpl updateBindCardReady method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        } finally {
//            SxyThirdInterfaceLogUtils.setSxyLog(channelStr, thirdOrderNo, jieBaResponse.getCode() + "", jieBaResponse
//                .getMessage(), "三方订单号");
//        }
//        logger.info(sessionId + "：结束JieBaServiceImpl updateBindCardReady method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @Override
//    public JieBaResponse updateBindCardSure(Long sessionId, BindCardInfo bindCardInfo) {
//        logger.info(sessionId + "：开始JieBaServiceImpl updateBindCardSure method：" + JSON.toJSONString(bindCardInfo));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        String thirdOrderNo = null;
//        try {
//            thirdOrderNo = bindCardInfo.getOrder_no();
//            String userName = bindCardInfo.getUser_name();
//            String userMobile = bindCardInfo.getUser_mobile();
//            String idNumber = bindCardInfo.getId_number();
//            String bankCard = bindCardInfo.getBank_card();
//            String bankCode = bindCardInfo.getBank_code();
//            String bankMobile = bindCardInfo.getBank_mobile();
//            String verifyCode = bindCardInfo.getVerify_code();
//
//            DrainageBindCardVO drainageBindCardVO = new DrainageBindCardVO();
//            drainageBindCardVO.setThirdOrderNo(thirdOrderNo);
//            drainageBindCardVO.setName(userName);
//            drainageBindCardVO.setIdCardNo(idNumber);
//            drainageBindCardVO.setBankCardNo(bankCard);
//            drainageBindCardVO.setRegPhone(bankMobile);
//            drainageBindCardVO.setPhone(userMobile);
//            drainageBindCardVO.setBankCode(JieBaUtil.convertBankCodeToFuYou(bankCode));
//            drainageBindCardVO.setChannelId(NumberUtils.toInt(channelStr));
//            drainageBindCardVO.setNotify(false);
//            drainageBindCardVO.setVerifyCode(verifyCode);
//
//            DrainageRsp drainageRsp = commonService.saveBindCard_NewSure(sessionId, drainageBindCardVO);
//            String code = "0000";
//            if (code.equals(drainageRsp.getCode())) {
//                jieBaResponse.setCode(200);
//                jieBaResponse.setMessage("确认绑卡成功");
//            } else {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage(drainageRsp.getMessage());
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaServiceImpl updateBindCardSure method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        } finally {
//            SxyThirdInterfaceLogUtils.setSxyLog(channelStr, thirdOrderNo, jieBaResponse.getCode() + "", jieBaResponse
//                .getMessage(), "三方订单号");
//        }
//        logger.info(sessionId + "：结束JieBaServiceImpl updateBindCardSure method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @Override
//    public JieBaResponse pullApproveResult(Long sessionId, CommonPullInfo commonPullInfo) {
//        logger.info(sessionId + "：开始JieBaServiceImpl pullApproveResult method：" + JSON.toJSONString(commonPullInfo));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            String thirdOrderNo = commonPullInfo.getOrder_no();
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullApproveResult method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (bwOrderRong == null) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("第三方订单不存在");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullApproveResult method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//            if (CommUtils.isNull(bwOrder)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("我方不存在该订单");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullApproveResult method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            BwProductDictionary dictionary = bwProductDictionaryService.findBwProductDictionaryById(bwOrder
//                .getProductId());
//            if (CommUtils.isNull(dictionary)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("产品表不存在");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullApproveResult method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            Long statusId = bwOrder.getStatusId();
//            Map<String, Object> params = new HashMap<>(16);
//            //订单编号 反馈订单的订单编号
//            params.put("order_no", thirdOrderNo);
//            if (statusId == OrderStatusEnum.SIGN.getStatus() || statusId == OrderStatusEnum.BOND.getStatus() ||
//                statusId == OrderStatusEnum.BOND_DISTRIBUTING.getStatus() || statusId == OrderStatusEnum.CONTRACT
//                .getStatus() ||
//                statusId == OrderStatusEnum.OVERDUE.getStatus() || statusId == OrderStatusEnum.REPAYMENT.getStatus() ||
//                statusId == OrderStatusEnum.TERMINATE.getStatus()) {
//
//                //审批结果 10 = 审批通过
//                params.put("conclusion", 10);
//                //期限类型 1 = 按天计息2 = 按月计息
//                params.put("term_unit", 1);
//                //审批期数 天数或月数
//                params.put("approval_term", 28);
//                //审批金额 即审批本金，参与各种利息管理费手续费等各项费用计算的金额，而非用户拿到钱的金额；2. 保留小数点后2位, 单位元；
//                params.put("approval_amount", bwOrder.getBorrowAmount());
//                //放款时预扣除手续费 保留小数点后2位，单位元；
//                params.put("service_fee", 0.00);
//                //放款金额 1. 实际打款到用户银行卡的金额；2. 保留小数点后2位，单位元；
//                params.put("receive_amount", bwOrder.getBorrowAmount());
//                Double interestRate = dictionary.getInterestRate();
//                //月利率	float 1. 一般分期产品会涉及此字段，此字段用于计算利息（贵机构针对该费用项来讲也可能是其它叫法），若不收取则返回0；2. 小数格式，
//                params.put("month_interest_rate", DoubleUtil.div(DoubleUtil.mul(interestRate, 30), 7D, 2));
//                //审批通过时间 Timestamp 反馈订单审批通过的时间
//                params.put("approval_time", bwOrder.getUpdateTime().getTime());
//            } else if (statusId == OrderStatusEnum.REJECTED.getStatus() || statusId == OrderStatusEnum.REVOKED
//                .getStatus()) {
//                //审批结果 40-审批不通过
//                params.put("conclusion", 40);
//                //备注说明 备注（例如：信用评分过低#拒绝客户，如果没备注，信用评分过低#）
//                params.put("remark", "信用评分过低#拒绝客户");
//                //审批拒绝时间 Timestamp 反馈订单审批被拒的时间戳
//                params.put("refuse_time", bwOrder.getUpdateTime().getTime());
//            }
//
//            jieBaResponse.setCode(200);
//            jieBaResponse.setMessage("success");
//            jieBaResponse.setData(params);
//
//            if (statusId == OrderStatusEnum.APPROVAL_START.getStatus() || statusId == OrderStatusEnum.APPROVAL_END
//                .getStatus()) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage(null);
//                jieBaResponse.setData(null);
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaServiceImpl pullApproveResult method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaServiceImpl pullApproveResult method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @Override
//    public JieBaResponse pullRepaymentPlan(Long sessionId, CommonPullInfo commonPullInfo) {
//        logger.info(sessionId + "：开始JieBaServiceImpl pullRepaymentPlan method：" + JSON.toJSONString(commonPullInfo));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            String thirdOrderNo = commonPullInfo.getOrder_no();
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullRepaymentPlan method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (bwOrderRong == null) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("第三方订单不存在");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullRepaymentPlan method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//            if (CommUtils.isNull(bwOrder)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("我方不存在该订单");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullRepaymentPlan method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            BwProductDictionary dictionary = bwProductDictionaryService.findBwProductDictionaryById(bwOrder
//                .getProductId());
//            if (CommUtils.isNull(dictionary)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("产品表不存在");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullRepaymentPlan method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            // 获取还款计划
//            List<BwRepaymentPlan> bwRepaymentPlans = bwRepaymentPlanService.listBwRepaymentPlanByOrderId(bwOrder
//                .getId());
//            if (CommUtils.isNull(bwRepaymentPlans)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("还款计划不存在");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullRepaymentPlan method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Map<String, Object> params = new HashMap<>(16);
//            //订单编号 反馈订单的订单编号
//            params.put("order_no", thirdOrderNo);
//            //本金 借款本金 单位元 格式 0.00
//            params.put("total_capital", bwOrder.getBorrowAmount());
//            //手续费 手续费 单位元 格式 0.00
//            params.put("poundage_fee", 0.00);
//            //账单结清状态 1 未结清；2 已结清
//            params.put("bill_status", bwOrder.getStatusId() == 6 ? 2 : 1);
//
//            Double totalAmount = 0.0D;
//            Double totalInterest = 0.0D;
//            Double overdueFees = 0.0D;
//            List<Map<String, Object>> plans = new ArrayList<>();
//            for (BwRepaymentPlan bwRepaymentPlan : bwRepaymentPlans) {
//                Map<String, Object> plan = new HashMap<>(16);
//                //期数 具体的第几期
//                plan.put("duration", bwRepaymentPlan.getNumber());
//                //应还本金 本期应还本金 单位元 格式 0.00
//                plan.put("principal", bwRepaymentPlan.getRepayCorpusMoney());
//                //应还利息 本期应还利息 单位元 格式 0.00
//                plan.put("interest", bwRepaymentPlan.getRepayAccrualMoney());
//                //应还款日 时间戳格式 本期应还款日 格式为 unix10位时间戳
//                plan.put("repayment_time", bwRepaymentPlan.getRepayTime().getTime() / 1000);
//                //应还款日 日期格式 本期应还款日 格式为1970-01-01 00:00:00
//                plan.put("repayment_time_text", sdf.format(bwRepaymentPlan.getRepayTime()));
//
//                BwOverdueRecord bwOverdueRecord = new BwOverdueRecord();
//                bwOverdueRecord.setRepayId(bwRepaymentPlan.getId());
//                bwOverdueRecord = bwOverdueRecordService.findBwOverdueRecordByAttr(bwOverdueRecord);
//                Double overdueFee = 0.0D;
//                Integer overdueDay = 0;
//                if (bwOverdueRecord != null) {
//                    Double overdueAccrualMoney = bwOverdueRecord.getOverdueAccrualMoney() == null ? 0.0D
//                        : bwOverdueRecord.getOverdueAccrualMoney();
//                    Double advance = bwOverdueRecord.getAdvance();
//                    overdueFee = DoubleUtil.sub(overdueAccrualMoney, advance);
//                    overdueDay = bwOverdueRecord.getOverdueDay();
//                }
//
//                //应还款总金额 本期应还金额 单位元 格式 0.00
//                plan.put("repayamount", DoubleUtil.add(bwRepaymentPlan.getRealityRepayMoney(), overdueFee));
//                //已还还款金额
//                plan.put("actual_repayment_amount", bwRepaymentPlan.getAlreadyRepayMoney());
//
//                long repayTime = 0;
//                Date repayTimes = null;
//                int num = 2;
//                if (bwRepaymentPlan.getRepayStatus() == num) {
//                    repayTime = bwRepaymentPlan.getUpdateTime().getTime();
//                    repayTimes = bwRepaymentPlan.getUpdateTime();
//                }
//
//                //实际还款日 时间戳格式 本期实际还款日 格式为 unix10位时间戳 未还款返回0
//                plan.put("actual_repayment_time", repayTime / 1000);
//                //实际还款日 日期格式 本期实际还款日 格式为1970-01-01 00:00:00 未还款返回空
//                plan.put("actual_repayment_time_text", repayTimes == null ? null : sdf.format(repayTimes));
//                //逾期天数 本期逾期天数 没有逾期 返回0
//                plan.put("overdue_days", overdueDay);
//                //逾期费用 本期逾期费用 没有逾期 返回0 单位元 格式 0.00
//                plan.put("overdue_fee", overdueFee);
//                //还款状态 本期还款状态 1未还款；2已还款
//                plan.put("status", bwRepaymentPlan.getRepayStatus() == num ? 2 : 1);
//
//                plans.add(plan);
//
//                totalAmount += bwRepaymentPlan.getRealityRepayMoney();
//                totalInterest += bwRepaymentPlan.getRepayAccrualMoney();
//                overdueFees += overdueFee;
//            }
//
//            // 还款总金额 还款总金额 单位元 格式 0.00
//            params.put("total_amount", DoubleUtil.add(totalAmount, overdueFees));
//            // 总利息 总利息 单位元 格式 0.00
//            params.put("total_interest", totalInterest);
//            //账单每期信息集合 账单每期信息集合为二维数组
//            params.put("repayment_plan", plans);
//
//            jieBaResponse.setData(200);
//            jieBaResponse.setMessage("success");
//            jieBaResponse.setData(params);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaServiceImpl pullRepaymentPlan method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaServiceImpl pullRepaymentPlan method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @Override
//    public JieBaResponse pullOrderStatus(Long sessionId, CommonPullInfo commonPullInfo) {
//        logger.info(sessionId + "：开始JieBaServiceImpl pullOrderStatus method：" + JSON.toJSONString(commonPullInfo));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        try {
//            String thirdOrderNo = commonPullInfo.getOrder_no();
//            if (StringUtils.isBlank(thirdOrderNo)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("参数为空");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullOrderStatus method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (bwOrderRong == null) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("第三方订单不存在");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullOrderStatus method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(bwOrderRong.getOrderId()));
//            if (CommUtils.isNull(bwOrder)) {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("我方不存在该订单");
//                logger.info(sessionId + "：结束JieBaServiceImpl pullOrderStatus method：" + JSON.toJSONString
//                    (jieBaResponse));
//                return jieBaResponse;
//            }
//
//            Long statusId = bwOrder.getStatusId();
//
//            Map<String, Object> params = new HashMap<>(16);
//            //order_no	订单编号	String
//            params.put("order_no", thirdOrderNo);
//            //order_status	状态	int	订单状态见附录
//            params.put("order_status", JieBaConstant.statusMap.get(String.valueOf(statusId)));
//            //update_time	订单变更时间	timestamp	需反馈订单流经至对应状态的时间
//            params.put("update_time", bwOrder.getUpdateTime().getTime() / 1000);
//
//            jieBaResponse.setCode(200);
//            jieBaResponse.setMessage("success");
//            jieBaResponse.setData(params);
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaServiceImpl pullOrderStatus method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束JieBaServiceImpl pullOrderStatus method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//
//    @Override
//    public JieBaResponse updateApplyRepay(Long sessionId, RepayInfo repayInfo) {
//        logger.info(sessionId + "：开始JieBaServiceImpl updateApplyRepay method：" + JSON.toJSONString(repayInfo));
//        JieBaResponse jieBaResponse = new JieBaResponse();
//        String thirdOrderNo = null;
//        try {
//            thirdOrderNo = repayInfo.getOrder_no();
//            DrainageRsp drainageRsp = commonService.updateRepayment_New(sessionId, thirdOrderNo);
//            String code = "000";
//            if (drainageRsp != null) {
//                if (code.equals(drainageRsp.getCode())) {
//                    jieBaResponse.setCode(200);
//                    jieBaResponse.setMessage("success");
//                } else {
//                    jieBaResponse.setCode(400);
//                    jieBaResponse.setMessage(drainageRsp.getMessage());
//                }
//            } else {
//                jieBaResponse.setCode(400);
//                jieBaResponse.setMessage("还款失败");
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行JieBaServiceImpl updateApplyRepay method异常：", e);
//            jieBaResponse.setCode(400);
//            jieBaResponse.setMessage("请求失败");
//        } finally {
//            SxyThirdInterfaceLogUtils.setSxyLog(channelStr, thirdOrderNo, jieBaResponse.getCode() + "", jieBaResponse
//                .getMessage(), "三方订单号");
//        }
//        logger.info(sessionId + "：结束JieBaServiceImpl updateApplyRepay method：" + JSON.toJSONString(jieBaResponse));
//        return jieBaResponse;
//    }
//}
