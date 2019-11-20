//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.drainage.entity.common.PushOrderRequest;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.*;
//import com.waterelephant.mapper.BwOperateVoiceMapper;
//import com.waterelephant.mapper.BwThirdOperateVoiceMapper;
//import com.waterelephant.service.*;
//import com.waterelephant.service.impl.BwContactListService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.sxyDrainage.entity.shandiandai.pushorder.*;
//import com.waterelephant.sxyDrainage.entity.shandiandai.sddoperator.*;
//import com.waterelephant.sxyDrainage.mapper.*;
//import com.waterelephant.sxyDrainage.service.AsyncSddTask;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.shandiandai.SddConstant;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.CommUtils;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//import com.waterelephant.utils.UploadToCssUtils;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.log4j.Logger;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * 异步保存进件数据
// *
// * @author 王亚楠
// * @version 1.0
// * @since JDK 1.8
// *
// */
//@Component
//public class AsyncSddTaskImpl implements AsyncSddTask {
//    private Logger logger = Logger.getLogger(AsyncSddTaskImpl.class);
//
//    /**
//     * 渠道号
//     */
//    private static final String CHANNEL_ID = SddConstant.CHANNEL_SX;
//    /**
//     * 产品号 7
//     */
//    private static final String PRODUCT_ID = SddConstant.PRODUCT_ID;
//
//
//    @Autowired
//    private ThirdCommonService thirdCommonService;
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
//    private BwZmxyGradeService bwZmxyGradeService;
//    @Autowired
//    private BwIdentityCardServiceImpl bwIdentityCardServiceImpl;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//    @Autowired
//    private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//    @Autowired
//    private BwThirdOperateBasicService bwThirdOperateBasicService;
//
//
//    /**
//     * 异步保存数据
//     *
//     * @param pushOrderRequest 进件数据
//     * @param carrierInfo 运营商
//     */
//    @Async("asyncTaskExecutor")
//    @Override
//    public void saveOrder(Long sessionId, PushOrderRequest pushOrderRequest, SddCarrierInfo carrierInfo, SddCreditCard sddCreditCard, SddmobileData sddmobileData) {
//
//
//        logger.info(sessionId + " 闪电贷>>>AsyncSddTaskImpl saveOrder()开始进件");
//        try {
//            String userName = pushOrderRequest.getUserName();
//            String idCard = pushOrderRequest.getIdCard();
//            String phone = pushOrderRequest.getPhone();
//            Integer channelId = pushOrderRequest.getChannelId();
//            String thirdOrderNo = pushOrderRequest.getThirdOrderNo();
//
//            // 新增或更新借款人
//            BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId);
//            long borrowerId = borrower.getId();
//            logger.info(sessionId + "闪电贷>>> 新增/更新借款人ID：" + borrowerId);
//
//
//            // 查询是否有进行中的订单
//            long count = bwOrderService.findProOrder(borrowerId + "");
//            if (count > 0) {
//                logger.info(sessionId + " 闪电贷>>>AsyncSddTaskImpl saveOrder()结束进件，返回结果：存在进行中的订单，请勿重复推送");
//                return;
//            }
//
//            // 设置批量处理
//            SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//
//
//            // 判断是否有草稿状态的订单
//            BwOrder bwOrder = new BwOrder();
//            bwOrder.setBorrowerId(borrowerId);
//            bwOrder.setStatusId(1L);
//            bwOrder.setProductType(2);
//            bwOrder.setChannel(channelId);
//            bwOrder.setProductId(Integer.valueOf(PRODUCT_ID));
//            List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
//
//            // 新增/更新订单
//            if (boList != null && boList.size() > 0) {
//                bwOrder = boList.get(boList.size() - 1);
//                bwOrder.setStatusId(1L);
//                bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwOrder.setRepayType(2);
//                bwOrder.setExpectMoney(pushOrderRequest.getExpectMoney());
//                bwOrder.setExpectNumber(pushOrderRequest.getExpectNumber());
//                // 更新订单
//                bwOrderService.updateBwOrder(bwOrder);
//            } else {
//                bwOrder = new BwOrder();
//                bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//                bwOrder.setBorrowerId(borrower.getId());
//                bwOrder.setStatusId(1L);
//                bwOrder.setCreateTime(new Date());
//                bwOrder.setUpdateTime(new Date());
//                bwOrder.setChannel(channelId);
//                bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//                bwOrder.setApplyPayStatus(0);
//                bwOrder.setProductId(Integer.valueOf(PRODUCT_ID));
//                bwOrder.setProductType(2);
//                bwOrder.setRepayType(2);
//                bwOrder.setExpectMoney(pushOrderRequest.getExpectMoney());
//                bwOrder.setExpectNumber(pushOrderRequest.getExpectNumber());
//                // 保存订单
//                bwOrderService.addBwOrder(bwOrder);
//            }
//            long orderId = bwOrder.getId();
//            logger.info(sessionId + "闪电贷>>> 新增/更新草稿状态的订单：ID = " + orderId);
//
//
//            // 征信redis
//            try {
//                String key = "phone_apply";
//                Map<String, Object> params = new HashMap<>();
//                params.put("mobile", phone);
//                params.put("order_id", orderId);
//                params.put("borrower_id", borrowerId);
//                String value = JSON.toJSONString(params);
//                RedisUtils.rpush(key, value);
//            } catch (Exception e) {
//                logger.info(sessionId + "闪电贷>>> 征信Redis存储异常：ID = " + orderId);
//            }
//
//
//            // 判断是否有三方订单
//            if (StringUtils.isNotBlank(thirdOrderNo)) {
//                BwOrderRong bwOrderRong = new BwOrderRong();
//                bwOrderRong.setOrderId(orderId);
//                bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//                if (bwOrderRong == null) {
//                    bwOrderRong = new BwOrderRong();
//                    bwOrderRong.setOrderId(orderId);
//                    bwOrderRong.setThirdOrderNo(thirdOrderNo);
//                    bwOrderRong.setChannelId(Long.valueOf(channelId));
//                    bwOrderRong.setCreateTime(new Date());
//                    bwOrderRongService.save(bwOrderRong);
//                } else {
//                    if (null == bwOrderRong.getChannelId()) {
//                        bwOrderRong.setChannelId(Long.valueOf(channelId));
//                    }
//                    bwOrderRong.setThirdOrderNo(thirdOrderNo);
//                    bwOrderRongService.update(bwOrderRong);
//                }
//                logger.info(sessionId + "闪电贷>>> 新增/更新三方订单");
//            }
//
//            // 判断是否有商户订单信息
//            BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//            bwMerchantOrder.setOrderId(orderId);
//            bwMerchantOrder = bwMerchantOrderServiceImpl.selectByAtt(bwMerchantOrder);
//            if (bwMerchantOrder == null) {
//                bwMerchantOrder = new BwMerchantOrder();
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setCreateTime(new Date());
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrder.setUpdateTime(new Date());
//                bwMerchantOrderServiceImpl.insertByAtt(bwMerchantOrder);
//            } else {
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwMerchantOrderServiceImpl.updateByAtt(bwMerchantOrder);
//            }
//
//            // 判断是否有工作信息
//            BwWorkInfo bwWorkInfo = new BwWorkInfo();
//            bwWorkInfo.setOrderId(orderId);
//            bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//            // 获得进件工作信息
//            BwWorkInfo bwWorkInfoNew = pushOrderRequest.getBwWorkInfo();
//            if (null == bwWorkInfo) {
//                if (null == bwWorkInfoNew) {
//                    bwWorkInfoNew = new BwWorkInfo();
//                }
//                bwWorkInfoNew.setOrderId(orderId);
//                // 默认可以打电话时间
//                bwWorkInfoNew.setCallTime("10:00 - 12:00");
//                bwWorkInfoNew.setUpdateTime(new Date());
//                bwWorkInfoNew.setCreateTime(new Date());
//
//                bwWorkInfoService.save(bwWorkInfoNew, borrowerId);
//                // 插入个人认证记录
//                thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId);
//                logger.info(sessionId + "闪电贷>>> 新增/更新工作信息");
//            } else if (null != bwWorkInfoNew) {
//                //// 默认可以打电话时间
//                bwWorkInfo.setCallTime("10:00 - 12:00");
//                bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//                bwWorkInfo.setWorkYears(bwWorkInfoNew.getWorkYears());
//                bwWorkInfo.setComName(bwWorkInfoNew.getComName());
//                bwWorkInfo.setIncome(bwWorkInfoNew.getIncome());
//                bwWorkInfo.setIndustry(bwWorkInfoNew.getIndustry());
//                bwWorkInfoService.update(bwWorkInfo);
//                // 插入个人认证记录
//                thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId);
//                logger.info(sessionId + "闪电贷>>> 新增/更新工作信息");
//            } else {
//                logger.info(sessionId + "闪电贷>>> 工作信息为空，处理失败");
//            }
//
//            // 通讯录
//            List<BwContactList> contactListNew = pushOrderRequest.getBwContactList();
//            List<BwContactList> listConS = new ArrayList<>();
//            if (contactListNew != null && contactListNew.size() > 0) {
//                for (BwContactList contact : contactListNew) {
//                    if (CommUtils.isNull(contact.getName())) {
//                        continue;
//                    }
//                    if (CommUtils.isNull(contact.getPhone())) {
//                        continue;
//                    }
//                    BwContactList bwContactList = new BwContactList();
//                    bwContactList.setBorrowerId(borrowerId);
//                    bwContactList.setPhone(contact.getPhone());
//                    bwContactList.setName(contact.getName());
//                    listConS.add(bwContactList);
//                }
//                bwContactListService.addOrUpdateBwContactLists(listConS, borrowerId);
//                logger.info(sessionId + "闪电贷>>> 处理通讯录信息 ");
//            } else {
//                logger.info(sessionId + "闪电贷>>> 通讯录为空，处理失败");
//            }
//
//
//            // 运营商
//            BwOperateBasic bwOperateBasicNew = pushOrderRequest.getBwOperateBasic();
//            BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//            if (bwOperateBasic == null) {
//                if (null == bwOperateBasicNew) {
//                    bwOperateBasicNew = new BwOperateBasic();
//                }
//                bwOperateBasicNew.setBorrowerId(borrowerId);
//                bwOperateBasicNew.setCreateTime(new Date());
//                bwOperateBasicNew.setUpdateTime(new Date());
//                bwOperateBasicService.save(bwOperateBasicNew);
//                logger.info(sessionId + "闪电贷>>> 处理运营商信息");
//            } else if (null != bwOperateBasicNew) {
//                bwOperateBasic.setBorrowerId(borrowerId);
//                bwOperateBasic.setUpdateTime(new Date());
//                bwOperateBasic.setUserSource(bwOperateBasicNew.getUserSource());
//                bwOperateBasic.setIdCard(bwOperateBasicNew.getIdCard());
//                bwOperateBasic.setAddr(bwOperateBasicNew.getAddr());
//                bwOperateBasic.setPhone(bwOperateBasicNew.getPhone());
//                bwOperateBasic.setPhoneRemain(bwOperateBasicNew.getPhoneRemain());
//                bwOperateBasic.setRealName(bwOperateBasicNew.getRealName());
//                bwOperateBasic.setRegTime(bwOperateBasicNew.getRegTime());
//                bwOperateBasicService.update(bwOperateBasic);
//                logger.info(sessionId + "闪电贷>>> 处理运营商信息");
//            } else {
//                logger.info(sessionId + "闪电贷>>> 运营商为空，处理失败");
//            }
//
//
//            // 通话记录
//            List<BwOperateVoice> bwOperateVoiceNew = pushOrderRequest.getBwOperateVoiceList();
//            Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//            if (CollectionUtils.isNotEmpty(bwOperateVoiceNew)) {
//                // 获得批处理 Mapper
//                BwOperateVoiceMapper bwOperateVoiceMapper = sqlSession.getMapper(BwOperateVoiceMapper.class);
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                for (BwOperateVoice bwOperateVoice : bwOperateVoiceNew) {
//                    try {
//                        Date jsonCallData = sdf.parse(bwOperateVoice.getCall_time());
//                        // 通话记录采取最新追加的方式
//                        if (callDate == null || jsonCallData.after(callDate)) {
//                            bwOperateVoice.setUpdateTime(new Date());
//                            bwOperateVoice.setBorrower_id(borrowerId);
//                            // bwOperateVoiceService.save(bwOperateVoice);
//                            bwOperateVoiceMapper.insert(bwOperateVoice);
//                        }
//                    } catch (Exception e) {
//                        logger.error(sessionId + "闪电贷>>> 插入单条通话记录异常，忽略该条记录" + e.getMessage());
//                    }
//                }
//                // 提交 清理缓存
//                sqlSession.commit();
//                sqlSession.clearCache();
//                // 插入运营商认证记录
//                thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, channelId);
//                logger.info(sessionId + "闪电贷>>> 处理通话记录信息 ");
//            } else {
//                logger.info(sessionId + "闪电贷>>> 通话记录为空，处理失败");
//            }
//
//
//            // 芝麻信用
//            Integer sesameScoreNew = pushOrderRequest.getSesameScore();
//            if (null != sesameScoreNew) {
//                BwZmxyGrade bwZmxyGrade = bwZmxyGradeService.findZmxyGradeByBorrowerId(borrowerId);
//                if (bwZmxyGrade == null) {
//                    bwZmxyGrade = new BwZmxyGrade();
//                    bwZmxyGrade.setBorrowerId(borrowerId);
//                    bwZmxyGrade.setZmScore(sesameScoreNew);
//                    bwZmxyGrade.setCreateTime(new Date());
//                    bwZmxyGrade.setUpdateTime(new Date());
//                    bwZmxyGradeService.saveBwZmxyGrade(bwZmxyGrade);
//                } else {
//                    bwZmxyGrade.setZmScore(sesameScoreNew);
//                    bwZmxyGrade.setUpdateTime(Calendar.getInstance().getTime());
//                    bwZmxyGradeService.updateBwZmxyGrade(bwZmxyGrade);
//                }
//                // 插入芝麻认证记录
//                thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 4, channelId);
//                logger.info(sessionId + "闪电贷>>> 处理芝麻信用信息 ");
//            } else {
//                logger.info(sessionId + "闪电贷>>> 芝麻信用为空，处理失败");
//            }
//
//
//            // 认证图片
//            String frontFile = pushOrderRequest.getIdCardFrontImage();
//            String backFile = pushOrderRequest.getIdCardBackImage();
//            String natureFile = pushOrderRequest.getIdCardHanderImage();
//            // 身份证正面照
//            if (StringUtils.isNotBlank(frontFile)) {
//                String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01");
//                logger.info(sessionId + "闪电贷>>> 身份证正面 " + frontImage);
//                // 保存身份证正面照Url
//                thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId, borrowerId, 0);
//            }
//            // 身份证反面照
//            if (StringUtils.isNotBlank(backFile)) {
//                String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02");
//                logger.info(sessionId + "闪电贷>>> 身份证反面 " + backImage);
//                // 保存身份证反面照Url
//                thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId, borrowerId, 0);
//            }
//            // 手持照
//            if (StringUtils.isNotBlank(natureFile)) {
//                String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03");
//                logger.info(sessionId + "闪电贷>>> 手持照/人脸 " + handerImage);
//                // 保存手持照Url
//                thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId, borrowerId, 0);
//            }
//            // 认证 照片
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);
//            logger.info(sessionId + "闪电贷>>> 处理认证图片 ");
//
//
//            // 保存身份证信息
//            BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//            bwIdentityCard.setBorrowerId(borrowerId);
//            bwIdentityCard = bwIdentityCardServiceImpl.findBwIdentityCardByAttr(bwIdentityCard);
//            BwIdentityCard2 bwIdentityCardNew = pushOrderRequest.getBwIdentityCard();
//            if (bwIdentityCard == null) {
//                if (null == bwIdentityCardNew) {
//                    bwIdentityCardNew = new BwIdentityCard2();
//                }
//                bwIdentityCardNew.setBorrowerId(borrowerId);
//                bwIdentityCardNew.setCreateTime(new Date());
//                bwIdentityCardNew.setUpdateTime(new Date());
//                bwIdentityCardServiceImpl.saveBwIdentityCard(bwIdentityCardNew);
//                // 插入身份认证记录
//                thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);
//                logger.info(sessionId + "闪电贷>>> 处理身份证信息");
//            } else if (null != bwIdentityCardNew) {
//                bwIdentityCard.setAddress(bwIdentityCardNew.getAddress());
//                bwIdentityCard.setBirthday(bwIdentityCardNew.getBirthday());
//                bwIdentityCard.setGender(bwIdentityCardNew.getGender());
//                bwIdentityCard.setIdCardNumber(bwIdentityCardNew.getIdCardNumber());
//                bwIdentityCard.setIssuedBy(bwIdentityCardNew.getIssuedBy());
//                bwIdentityCard.setName(bwIdentityCardNew.getName());
//                bwIdentityCard.setRace(bwIdentityCardNew.getRace());
//                bwIdentityCard.setUpdateTime(new Date());
//                bwIdentityCard.setValidDate(bwIdentityCardNew.getValidDate());
//                bwIdentityCardServiceImpl.updateBwIdentityCard(bwIdentityCard);
//                // 插入身份认证记录
//                thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, channelId);
//                logger.info(sessionId + "闪电贷>>> 处理身份证信息");
//            } else {
//                logger.info(sessionId + "闪电贷>>> 芝身份证信息为空，处理失败");
//            }
//
//            // 亲属联系人
//            BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//            BwPersonInfo bwPersonInfoNew = pushOrderRequest.getBwPersonInfo();
//            if (bwPersonInfo == null) {
//                if (null == bwPersonInfoNew) {
//                    bwPersonInfoNew = new BwPersonInfo();
//                }
//                bwPersonInfoNew.setCreateTime(new Date());
//                bwPersonInfoNew.setOrderId(orderId);
//                bwPersonInfoNew.setUpdateTime(new Date());
//                bwPersonInfoService.add(bwPersonInfoNew);
//                logger.info(sessionId + "闪电贷>>> 处理亲属联系人信息");
//            } else if (null != bwPersonInfoNew) {
//                bwPersonInfo.setCityName(bwPersonInfoNew.getCityName());
//                bwPersonInfo.setAddress(bwPersonInfoNew.getAddress());
//                bwPersonInfo.setRelationName(bwPersonInfoNew.getRelationName());
//                bwPersonInfo.setRelationPhone(bwPersonInfoNew.getRelationPhone());
//                bwPersonInfo.setUnrelationName(bwPersonInfoNew.getUnrelationName());
//                bwPersonInfo.setUnrelationPhone(bwPersonInfoNew.getUnrelationPhone());
//                bwPersonInfo.setMarryStatus(bwPersonInfoNew.getMarryStatus());
//                bwPersonInfo.setHouseStatus(bwPersonInfoNew.getHouseStatus());
//                bwPersonInfo.setCarStatus(bwPersonInfoNew.getCarStatus());
//                bwPersonInfo.setCreateTime(bwPersonInfoNew.getCreateTime());
//                bwPersonInfo.setEmail(bwPersonInfoNew.getEmail());
//                bwPersonInfo.setColleagueName(bwPersonInfoNew.getColleagueName());
//                bwPersonInfo.setColleaguePhone(bwPersonInfoNew.getColleaguePhone());
//                bwPersonInfo.setFriend1Name(bwPersonInfoNew.getFriend1Name());
//                bwPersonInfo.setFriend1Phone(bwPersonInfoNew.getFriend1Phone());
//                bwPersonInfo.setFriend2Name(bwPersonInfoNew.getFriend2Name());
//                bwPersonInfo.setFriend2Phone(bwPersonInfoNew.getFriend2Phone());
//                bwPersonInfo.setQqchat(bwPersonInfoNew.getQqchat());
//                bwPersonInfo.setWechat(bwPersonInfoNew.getWechat());
//                bwPersonInfo.setUpdateTime(new Date());
//                bwPersonInfoService.update(bwPersonInfo);
//                logger.info(sessionId + "闪电贷>>> 处理亲属联系人信息");
//            } else {
//                logger.info(sessionId + "闪电贷>>> 处理亲属联系人信息为空，处理失败");
//            }
//
//            // 处理风控数据 todo
//            try {
//                saveOperate(sqlSession, sddCreditCard, sddmobileData, carrierInfo, pushOrderRequest.getBwOperateBasic(), bwOrder.getId());
//                logger.info("闪电贷>>>" + sessionId + "异步保存风控数据成功");
//            } catch (Exception e) {
//                logger.error("闪电贷>>>" + sessionId + "异步保存风控数据异常", e);
//            }
//
//
//            // 调用白骑士校验 todo
//            try {
//                logger.info(sessionId + ":开始白骑士校验，orderId=" + orderId);
//                String url = SddConstant.BQS_URL;
//                Map<String, String> params = new HashMap<>(1);
//                params.put("orderId", String.valueOf(orderId));
//                String string = HttpClientHelper.post(url, "utf-8", params);
//                logger.info(sessionId + ":结束白骑士校验，orderId=" + orderId + ",返回结果：" + string);
//            } catch (Exception e) {
//                logger.error(sessionId + ":获取白骑士数据异常 ");
//            }
//
//
//
//            // 更改状态 进入审核
//            if (null != pushOrderRequest.getOrderStatus()) {
//                // 修改订单状态 - 2
//                bwOrder.setStatusId(Long.valueOf(pushOrderRequest.getOrderStatus()));
//                bwOrder.setUpdateTime(new Date());
//                bwOrder.setSubmitTime(new Date());
//                bwOrderService.updateBwOrder(bwOrder);
//                logger.info(sessionId + "闪电贷>>> 修改工单状态为" + pushOrderRequest.getOrderStatus());
//
//                // 放入redis
//                SystemAuditDto systemAuditDto = new SystemAuditDto();
//                systemAuditDto.setIncludeAddressBook(0);
//                systemAuditDto.setOrderId(orderId);
//                systemAuditDto.setBorrowerId(borrowerId);
//                systemAuditDto.setName(userName);
//                systemAuditDto.setPhone(phone);
//                systemAuditDto.setIdCard(idCard);
//                systemAuditDto.setChannel(channelId);
//                systemAuditDto.setThirdOrderId(thirdOrderNo);
//                systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//                RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
//                logger.info(sessionId + "闪电贷>>> 修改订单状态，并放入redis");
//            }
//
//            // 更改订单进行表时间
//            BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//            bwOrderProcessRecord.setSubmitTime(new Date());
//            bwOrderProcessRecord.setOrderId(bwOrder.getId());
//            bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//            logger.info(sessionId + "闪电贷>>> 更改订单进行表时间");
//
//            logger.info(sessionId + " 闪电贷>>>AsyncSddTaskImpl saveOrder()进件成功！");
//        } catch (Exception e) {
//            logger.error(sessionId + "闪电贷>>>AsyncSddTaskImpl saveOrder方法异常", e);
//        }
//    }
//
//
//    /**
//     * 保存风控数据
//     *
//     * @param carrierInfo 进件运营商数据
//     * @param orderId 订单号
//     */
//    private void saveOperate(SqlSession sqlSession, SddCreditCard sddCreditCard, SddmobileData sddMobileData, SddCarrierInfo carrierInfo, BwOperateBasic bwOperateBasic, Long orderId) {
//        logger.info(orderId + "闪电贷>>> 开始运营商存储");
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        // 运营商基础数据
//        try {
//            // 删除订单相关数据
//            bwThirdOperateBasicService.deleteAllByOrderId(orderId);
//            // 添加数据
//            BwThirdOperateBasic bwThirdOperateBasic = new BwThirdOperateBasic();
//            bwThirdOperateBasic.setOrderId(orderId);
//            bwThirdOperateBasic.setChannel(Integer.valueOf(CHANNEL_ID));
//            bwThirdOperateBasic.setUserSource(bwOperateBasic.getUserSource());
//            bwThirdOperateBasic.setIdCard(bwOperateBasic.getIdCard());
//            bwThirdOperateBasic.setAddr(bwOperateBasic.getAddr());
//            bwThirdOperateBasic.setRealName(bwOperateBasic.getRealName());
//            bwThirdOperateBasic.setPhoneRemain(bwOperateBasic.getPhoneRemain());
//            bwThirdOperateBasic.setPhone(bwOperateBasic.getPhone());
//            bwThirdOperateBasic.setRegTime(bwOperateBasic.getRegTime());
//            bwThirdOperateBasic.setSearchId(bwOperateBasic.getSearchId());
//            bwThirdOperateBasic.setScore(bwOperateBasic.getScore());
//            bwThirdOperateBasic.setContactPhone(bwOperateBasic.getContactPhone());
//            bwThirdOperateBasic.setStarLevel(bwOperateBasic.getStarLevel());
//            bwThirdOperateBasic.setAuthentication(bwOperateBasic.getAuthentication());
//            bwThirdOperateBasic.setPhoneStatus(bwOperateBasic.getPhoneStatus());
//            bwThirdOperateBasic.setPackageName(bwOperateBasic.getPackageName());
//            bwThirdOperateBasic.setCreateTime(new Date());
//            bwThirdOperateBasicService.save(bwThirdOperateBasic);
//            logger.info(orderId + "闪电贷>>> 运营商存储>>>基础数据");
//        } catch (NumberFormatException e) {
//            logger.error(orderId + "闪电贷>>> 运营商存储异常>>>基础数据", e);
//        }
//
//
//        // 信用卡认证
//        try {
//            if (sddCreditCard != null) {
//                BwSddCreditCardMapper creditCardMapper = sqlSession.getMapper(BwSddCreditCardMapper.class);
//
//                // 删除
//                BwSddCreditCard delBwSddCreditCard = new BwSddCreditCard();
//                delBwSddCreditCard.setOrderId(orderId);
//                creditCardMapper.delete(delBwSddCreditCard);
//
//                // 添加
//                BwSddCreditCard bwSddCreditCard = new BwSddCreditCard();
//                bwSddCreditCard.setOrderId(orderId);
//                bwSddCreditCard.setCardNo(sddCreditCard.getCardNo());
//                bwSddCreditCard.setName(sddCreditCard.getName());
//                bwSddCreditCard.setPhone(sddCreditCard.getPhone());
//                bwSddCreditCard.setIdCard(sddCreditCard.getIdCard());
//                bwSddCreditCard.setCreateTime(new Date());
//                creditCardMapper.insert(bwSddCreditCard);
//                // 提交 清理缓存
//                sqlSession.commit();
//                sqlSession.clearCache();
//                logger.info(orderId + "闪电贷>>> 风控数据存储>>>信用卡认证");
//            }
//        } catch (Exception e) {
//            logger.error(orderId + "闪电贷>>> 风控数据存储异常>>>信用卡认证", e);
//        }
//
//
//        // 手机-信息
//        try {
//            SddDeviceInfo sddDeviceInfo = sddMobileData.getDeviceInfo();
//            if (sddDeviceInfo != null) {
//                BwSddMobileDataMapepr mobileDataMapepr = sqlSession.getMapper(BwSddMobileDataMapepr.class);
//
//                // 删除
//                BwSddMobileData delBwSddMobileData = new BwSddMobileData();
//                delBwSddMobileData.setOrderId(orderId);
//                mobileDataMapepr.delete(delBwSddMobileData);
//
//                // 添加
//                BwSddMobileData bwSddMobileData = new BwSddMobileData();
//                bwSddMobileData.setOrderId(orderId);
//                bwSddMobileData.setMobileSystem(sddDeviceInfo.getMobileSystem());
//                bwSddMobileData.setMobileType(sddDeviceInfo.getMobileType());
//                bwSddMobileData.setImeiCode(sddDeviceInfo.getImeiCode());
//                bwSddMobileData.setWifiMac(sddDeviceInfo.getWifiMac());
//                bwSddMobileData.setLng(sddMobileData.getLng());
//                bwSddMobileData.setLat(sddMobileData.getLat());
//                bwSddMobileData.setCreateTime(new Date());
//                bwSddMobileData.setAppList(sddMobileData.getAppList());
//                mobileDataMapepr.insert(bwSddMobileData);
//                // 提交 清理缓存
//                sqlSession.commit();
//                sqlSession.clearCache();
//                logger.info(orderId + "闪电贷>>> 风控数据存储>>>手机-信息");
//            }
//        } catch (Exception e) {
//            logger.error(orderId + "闪电贷>>> 风控数据存储>>>手机-信息", e);
//        }
//
//
//        // 手机-通话记录
//        try {
//            List<SddCallHistorie> callHistories = sddMobileData.getCallHistories();
//            if (!CommUtils.isNull(callHistories)) {
//                BwSddCallHistoryMapper callHistoryMapper = sqlSession.getMapper(BwSddCallHistoryMapper.class);
//
//                // 删除
//                BwSddCallHistory delBwSddCallHistory = new BwSddCallHistory();
//                delBwSddCallHistory.setOrderId(orderId);
//                callHistoryMapper.delete(delBwSddCallHistory);
//
//                for (SddCallHistorie callHistory : callHistories) {
//                    BwSddCallHistory bwSddCallHistory = new BwSddCallHistory();
//                    bwSddCallHistory.setOrderId(orderId);
//                    bwSddCallHistory.setName(callHistory.getName());
//                    bwSddCallHistory.setPhone(callHistory.getPhone());
//                    bwSddCallHistory.setCallTime(sdf.format(callHistory.getCallTime()));
//                    bwSddCallHistory.setDuration(callHistory.getDuration());
//                    bwSddCallHistory.setCallType(callHistory.getCallType());
//                    bwSddCallHistory.setCreateTime(new Date());
//                    callHistoryMapper.insert(bwSddCallHistory);
//                }
//                // 提交 清理缓存
//                sqlSession.commit();
//                sqlSession.clearCache();
//                logger.info(orderId + "闪电贷>>> 风控数据存储>>>手机-通话记录");
//            }
//        } catch (Exception e) {
//            logger.error(orderId + "闪电贷>>> 风控数据存储异常>>>手机-通话记录", e);
//        }
//
//
//        // 手机-短信列表
//        try {
//            List<SddSmsList> sddSmsLists = sddMobileData.getSmsList();
//            if (!CommUtils.isNull(sddSmsLists)) {
//                BwSddSmsListMapper smsListMapper = sqlSession.getMapper(BwSddSmsListMapper.class);
//                // 删除
//                BwSddSmsList delBwSddSmsList = new BwSddSmsList();
//                delBwSddSmsList.setOrderId(orderId);
//                smsListMapper.delete(delBwSddSmsList);
//
//                for (SddSmsList sddSmsList : sddSmsLists) {
//                    BwSddSmsList bwSddSmsList = new BwSddSmsList();
//                    bwSddSmsList.setOrderId(orderId);
//                    bwSddSmsList.setAddress(sddSmsList.getAddress());
//                    bwSddSmsList.setDate(sdf.format(sddSmsList.getDate()));
//                    bwSddSmsList.setBody(sddSmsList.getBody());
//                    bwSddSmsList.setType(sddSmsList.getType());
//                    bwSddSmsList.setCreateTime(new Date());
//                    smsListMapper.insert(bwSddSmsList);
//                }
//                // 提交 清理缓存
//                sqlSession.commit();
//                sqlSession.clearCache();
//                logger.info(orderId + "闪电贷>>> 风控数据存储>>>手机-短信列表");
//            }
//        } catch (Exception e) {
//            logger.error(orderId + "闪电贷>>> 风控数据存储异常>>>手机-短信列表", e);
//        }
//
//
//        // 套餐信息
//        try {
//            List<SddBill> bills = carrierInfo.getBills();
//            if (!CommUtils.isNull(bills)) {
//                BwSddBillMapper billMapper = sqlSession.getMapper(BwSddBillMapper.class);
//                // 删除
//                BwSddBill delBwSddBill = new BwSddBill();
//                delBwSddBill.setOrderId(orderId);
//                billMapper.delete(delBwSddBill);
//
//                for (SddBill bill : bills) {
//                    BwSddBill bwSddBill = new BwSddBill();
//                    bwSddBill.setOrderId(orderId);
//                    bwSddBill.setPlanAmt(bill.getPlanAmt());
//                    bwSddBill.setBillCycle(bill.getBillCycle());
//                    bwSddBill.setTotalAmt(bill.getTotalAmt());
//                    bwSddBill.setPayAmt(bill.getPayAmt());
//                    bwSddBill.setCreateTime(new Date());
//                    billMapper.insert(bwSddBill);
//                }
//                // 提交 清理缓存
//                sqlSession.commit();
//                sqlSession.clearCache();
//                logger.info(orderId + "闪电贷>>> 运营商存储>>>套餐信息");
//            }
//        } catch (Exception e) {
//            logger.error(orderId + "闪电贷>>> 运营商存储异常>>>套餐信息", e);
//        }
//
//        // 获取消息短信
//        SddMessages messages = carrierInfo.getMessages();
//        if (messages == null) {
//            logger.error(orderId + "闪电贷>>> 运营商存储>>>massages为空");
//            return;
//        }
//
//        // 通话
//        try {
//            List<SddCall> calls = messages.getCalls();
//            if (!CommUtils.isNull(calls)) {
//                BwThirdOperateVoiceMapper tovMapper = sqlSession.getMapper(BwThirdOperateVoiceMapper.class);
//                // 删除
//                BwThirdOperateVoice delBwThirdOperateVoice = new BwThirdOperateVoice();
//                delBwThirdOperateVoice.setOrderId(orderId);
//                tovMapper.delete(delBwThirdOperateVoice);
//
//                for (SddCall call : calls) {
//                    BwThirdOperateVoice bwThirdOperateVoice = new BwThirdOperateVoice();
//                    bwThirdOperateVoice.setOrderId(orderId);
//                    bwThirdOperateVoice.setChannel(Integer.parseInt(CHANNEL_ID));
//                    bwThirdOperateVoice.setTradeType("本地".equals(call.getCallType()) ? 1 : 2);
//                    bwThirdOperateVoice.setTradeTime(call.getUseTime() + "");
//                    bwThirdOperateVoice.setCallTime(sdf.format(call.getStartTime()));
//                    bwThirdOperateVoice.setTradeAddr(call.getPlace());
//                    if (call.getOtherCallPhone() == null || call.getOtherCallPhone().length() > 19) {
//                        continue;
//                    }
//                    bwThirdOperateVoice.setReceivePhone(call.getOtherCallPhone());
//                    bwThirdOperateVoice.setCallType(getCallType(call.getInitType()));
//                    bwThirdOperateVoice.setCreateTime(new Date());
//                    tovMapper.insert(bwThirdOperateVoice);
//                }
//                // 提交 清理缓存
//                sqlSession.commit();
//                sqlSession.clearCache();
//                logger.info(orderId + "闪电贷>>> 运营商存储>>>通话");
//            }
//        } catch (Exception e) {
//            logger.error(orderId + "闪电贷>>> 运营商存储异常>>>通话", e);
//        }
//
//        // 流量
//        try {
//            List<SddNet> nets = messages.getNets();
//            if (!CommUtils.isNull(nets)) {
//                BwSddNetMapper netMapper = sqlSession.getMapper(BwSddNetMapper.class);
//                // 删除
//                BwSddNet delBwSddNet = new BwSddNet();
//                delBwSddNet.setOrderId(orderId);
//                netMapper.delete(delBwSddNet);
//
//                for (SddNet net : nets) {
//                    BwSddNet bwSddNet = new BwSddNet();
//                    bwSddNet.setOrderId(orderId);
//                    bwSddNet.setNetType(net.getNetType());
//                    bwSddNet.setSubflow(net.getSubflow());
//                    bwSddNet.setPlace(net.getPlace());
//                    bwSddNet.setUseTime(net.getUseTime());
//                    bwSddNet.setStartTime(net.getStartTime());
//                    bwSddNet.setCreateTime(new Date());
//                    netMapper.insert(bwSddNet);
//                }
//                // 提交 清理缓存
//                sqlSession.commit();
//                sqlSession.clearCache();
//                logger.info(orderId + "闪电贷>>> 运营商存储>>>流量");
//            }
//        } catch (Exception e) {
//            logger.error(orderId + "闪电贷>>> 运营商存储异常>>>流量", e);
//        }
//
//        // 短信
//        try {
//            List<SddSms> smses = messages.getSmses();
//            if (!CommUtils.isNull(smses)) {
//                BwSddSmsMapper smsMapper = sqlSession.getMapper(BwSddSmsMapper.class);
//                // 删除
//                BwSddSms delBwSddSms = new BwSddSms();
//                delBwSddSms.setOrderId(orderId);
//                smsMapper.delete(delBwSddSms);
//
//                for (SddSms sms : smses) {
//                    BwSddSms bwSddSms = new BwSddSms();
//                    bwSddSms.setOrderId(orderId);
//                    bwSddSms.setInitType(sms.getInitType());
//                    bwSddSms.setPlace(sms.getPlace());
//                    bwSddSms.setStartTime(sms.getStartTime());
//                    bwSddSms.setOtherCallPhone(sms.getOtherCallPhone());
//                    bwSddSms.setCellphone(sms.getCellphone());
//                    bwSddSms.setCreateTime(new Date());
//                    smsMapper.insert(bwSddSms);
//                }
//                // 提交 清理缓存
//                sqlSession.commit();
//                sqlSession.clearCache();
//                logger.info(orderId + "闪电贷>>> 运营商存储>>>短信");
//            }
//        } catch (Exception e) {
//            logger.error(orderId + "闪电贷>>> 运营商存储异常>>>短信", e);
//        }
//
//        logger.info(orderId + "闪电贷>>> 结束风控数据存储");
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
//}
