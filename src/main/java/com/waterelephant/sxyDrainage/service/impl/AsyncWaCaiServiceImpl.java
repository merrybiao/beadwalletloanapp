//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.beadwallet.service.utils.HttpClientHelper;
//import com.beadwallet.utils.JsonUtils;
//import com.waterelephant.dto.SystemAuditDto;
//import com.waterelephant.entity.*;
//import com.waterelephant.mapper.BwOperateVoiceMapper;
//import com.waterelephant.mapper.BwThirdOperateVoiceMapper;
//import com.waterelephant.service.*;
//import com.waterelephant.service.impl.BwContactListService;
//import com.waterelephant.service.impl.BwIdentityCardServiceImpl;
//import com.waterelephant.sxyDrainage.entity.wacai.WaCaiApproval;
//import com.waterelephant.sxyDrainage.entity.wacai.WaCaiApprovalAmount;
//import com.waterelephant.sxyDrainage.entity.wacai.WaCaiCommonReq;
//import com.waterelephant.sxyDrainage.entity.wacai.WaCaiTerm;
//import com.waterelephant.sxyDrainage.entity.wacai.table.BwWcBill;
//import com.waterelephant.sxyDrainage.entity.wacai.table.BwWcDevice;
//import com.waterelephant.sxyDrainage.entity.wacai.wcorder.*;
//import com.waterelephant.sxyDrainage.mapper.BwWcBillMapper;
//import com.waterelephant.sxyDrainage.mapper.BwWcDeviceMapper;
//import com.waterelephant.sxyDrainage.service.AsyncWaCaiService;
//import com.waterelephant.sxyDrainage.utils.shandiandai.SddConstant;
//import com.waterelephant.sxyDrainage.utils.wacaiutils.WaCaiConstant;
//import com.waterelephant.sxyDrainage.utils.wacaiutils.WaCaiUtil;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.*;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.ibatis.session.ExecutorType;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.log4j.Logger;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * 异步进件
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/7/3
// * @since JDK 1.8
// */
//@Service
//public class AsyncWaCaiServiceImpl implements AsyncWaCaiService {
//
//    private Logger logger = Logger.getLogger(AsyncWaCaiServiceImpl.class);
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
//    private BwIdentityCardServiceImpl bwIdentityCardService;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//    @Autowired
//    private IBwMerchantOrderService bwMerchantOrderService;
//    @Autowired
//    private BwProductDictionaryService bwProductDictionaryService;
//
//    @Autowired
//    private SqlSessionTemplate sqlSessionTemplate;
//    @Autowired
//    private BwThirdOperateBasicService bwThirdOperateBasicService;
//
//
//
//    /**
//     * 异步进件
//     *
//     * @param sessionId 时间戳
//     * @param bwOrder 订单
//     * @param borrower 联系人
//     * @param waCaiCommonReq 请求数据
//     */
//    @Async("asyncTaskExecutor")
//    @Override
//    public void saveOrder(Long sessionId, BwOrder bwOrder, BwBorrower borrower, WaCaiCommonReq waCaiCommonReq) {
//
//        Integer channelId = Integer.valueOf(WaCaiConstant.CHANNEL_SX);
//
//        String openId = waCaiCommonReq.getOpenId();
//        String thirdOrderNo = waCaiCommonReq.getOrderId();
//
//        String userName = waCaiCommonReq.getRealName().getName();
//        String idCard = waCaiCommonReq.getRealName().getIdCard();
//        String mobile = waCaiCommonReq.getRealName().getMobile();
//
//        WaCaiUserInfo userInfo = waCaiCommonReq.getUserInfo();
//
//        try {
//            Long orderId = bwOrder.getId();
//            Long borrowerId = borrower.getId();
//
//            // 征信redis
//            try {
//                String key = "phone_apply";
//                Map<String, Object> params = new HashMap<>();
//                params.put("mobile", mobile);
//                params.put("order_id", orderId);
//                params.put("borrower_id", borrowerId);
//                String value = JSON.toJSONString(params);
//                RedisUtils.rpush(key, value);
//            } catch (Exception e) {
//                logger.info(sessionId + " 挖财>>> 征信Redis存储异常：ID = " + orderId);
//            }
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
//                logger.info(sessionId + " 挖财>>> 新增/更新三方订单");
//            }
//
//            // 判断是否有商户订单信息
//            BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//            bwMerchantOrder.setOrderId(orderId);
//            bwMerchantOrder = bwMerchantOrderService.selectByAtt(bwMerchantOrder);
//            if (bwMerchantOrder == null) {
//                bwMerchantOrder = new BwMerchantOrder();
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setCreateTime(new Date());
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrder.setUpdateTime(new Date());
//                bwMerchantOrderService.insertByAtt(bwMerchantOrder);
//            } else {
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwMerchantOrderService.updateByAtt(bwMerchantOrder);
//            }
//
//
//            // 判断是否有工作信息
//            BwWorkInfo bwWorkInfo = new BwWorkInfo();
//            bwWorkInfo.setOrderId(orderId);
//            bwWorkInfo = bwWorkInfoService.findBwWorkInfoByAttr(bwWorkInfo);
//            if (null == bwWorkInfo) {
//                bwWorkInfo = new BwWorkInfo();
//                bwWorkInfo.setOrderId(orderId);
//                // 默认可以打电话时间
//                bwWorkInfo.setCallTime("10:00 - 12:00");
//                bwWorkInfo.setUpdateTime(new Date());
//                bwWorkInfo.setCreateTime(new Date());
//                bwWorkInfoService.save(bwWorkInfo, borrowerId);
//            } else {
//                // 默认可以打电话时间
//                bwWorkInfo.setCallTime("10:00 - 12:00");
//                bwWorkInfo.setUpdateTime(Calendar.getInstance().getTime());
//                bwWorkInfo.setWorkYears(userInfo.getWorkYears());
//                bwWorkInfo.setComName(userInfo.getCompanyName());
//                bwWorkInfo.setIndustry(userInfo.getIndustry());
//                bwWorkInfoService.update(bwWorkInfo);
//            }
//            // 插入个人认证记录
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 2, channelId);
//            logger.info(sessionId + " 挖财>>> 新增/更新工作信息");
//
//
//            // 设置批量处理
//            SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
//
//            // 身份证OCR信息
//            boolean orcFlag = saveOcr(openId, sessionId, borrowerId, orderId);
//            // if (!orcFlag) {
//            // pushOrderResult(sessionId, WaCaiApproval.ORDER_LACK, "缺少身份证OCR信息", thirdOrderNo, openId);
//            // return;
//            // }
//
//            // 活体人脸比对数据
//            boolean livingFlag = saveLivingCompare(openId, sessionId, borrowerId, orderId);
//            // if (!livingFlag) {
//            // pushOrderResult(sessionId, WaCaiApproval.ORDER_LACK, "缺少活体人脸比对数据", thirdOrderNo, openId);
//            // return;
//            // }
//
//            // 运营商
//            boolean operatorFlag = saveOperator(openId, sessionId, borrowerId, orderId, mobile, sqlSession);
//            // if (!operatorFlag) {
//            // pushOrderResult(sessionId, WaCaiApproval.ORDER_LACK, "缺少运营商数据", thirdOrderNo, openId);
//            // return;
//            // }
//            // 定位信息
//            // 通讯录信息
//            boolean contactFlag = saveContact(openId, sessionId, borrowerId, orderId, mobile);
//            // if (!contactFlag) {
//            // pushOrderResult(sessionId, WaCaiApproval.ORDER_LACK, "缺少通讯录数据", thirdOrderNo, openId);
//            // return;
//            // }
//
//            // 设备信息
//            boolean deviceFlag = saveDevices(openId, sessionId, borrowerId, orderId, mobile, sqlSession);
//            // if (!deviceFlag) {
//            // pushOrderResult(sessionId, WaCaiApproval.ORDER_LACK, "缺少设备信息数据", thirdOrderNo, openId);
//            // return;
//            // }
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
//            // 更改状态 进入审核，修改订单状态 - 2
//            bwOrder.setStatusId(2L);
//            bwOrder.setUpdateTime(new Date());
//            bwOrder.setSubmitTime(new Date());
//            bwOrderService.updateBwOrder(bwOrder);
//            logger.info(sessionId + " 挖财>>> 修改工单状态为" + 2);
//
//            // 进件成功
//            pushOrderResult(sessionId, WaCaiApproval.ORDER_SUCCESS, "进件成功", thirdOrderNo, openId);
//
//            // 放入redis
//            SystemAuditDto systemAuditDto = new SystemAuditDto();
//            systemAuditDto.setIncludeAddressBook(0);
//            systemAuditDto.setOrderId(orderId);
//            systemAuditDto.setBorrowerId(borrowerId);
//            systemAuditDto.setName(userName);
//            systemAuditDto.setPhone(mobile);
//            systemAuditDto.setIdCard(idCard);
//            systemAuditDto.setChannel(channelId);
//            systemAuditDto.setThirdOrderId(thirdOrderNo);
//            systemAuditDto.setCreateTime(Calendar.getInstance().getTime());
//            RedisUtils.hset(SystemConstant.AUDIT_KEY, orderId + "", JsonUtils.toJson(systemAuditDto));
//            logger.info(sessionId + " 挖财>>> 修改订单状态，并放入redis");
//
//
//            // 更改订单进行表时间
//            BwOrderProcessRecord bwOrderProcessRecord = new BwOrderProcessRecord();
//            bwOrderProcessRecord.setSubmitTime(new Date());
//            bwOrderProcessRecord.setOrderId(bwOrder.getId());
//            bwOrderProcessRecordService.saveOrUpdateByOrderId(bwOrderProcessRecord);
//            logger.info(sessionId + " 挖财>>> 更改订单进行表时间");
//
//            logger.info(sessionId + " 挖财>>>AsyncSddTaskImpl saveOrder()进件成功！");
//
//        } catch (Exception e) {
//            try {
//                logger.error("挖财>>>AsyncSddTaskImpl saveOrder()进件异常！", e);
//                pushOrderResult(sessionId, WaCaiApproval.ORDER_DIE, "授信数据接收异常", thirdOrderNo, openId);
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 查询身份证OCR信息
//     *
//     * @param openId 挖财用户标识
//     * @param sessionId 时间戳标记
//     * @param borrowerId 用户ID
//     * @param orderId 我方订单号
//     * @return 是否保存成功
//     */
//    private boolean saveOcr(String openId, Long sessionId, Long borrowerId, Long orderId) {
//        // 请求数据
//        Map<String, String> headers = new HashMap<>(1);
//        headers.put("Authorization", "HmacSHA256 Credential=" + WaCaiConstant.APP_ID + ", Signature=" + WaCaiUtil.getSign("GET", null));
//        WaCaiOcr waCaiOcr = null;
//        for (int i = 0; i < 3; i++) {
//            try {
//                String orcJson = WaCaiUtil.sendGet(WaCaiConstant.WACAI_URL + "/finance-open/api/v1/facePlus/idCardOcrInfo/" + openId, headers);
//                waCaiOcr = JSON.parseObject(orcJson, WaCaiOcr.class);
//                if (waCaiOcr != null && waCaiOcr.isOcrFlag() && waCaiOcr.getIdCard() != null && !CommUtils.isNull(waCaiOcr.getPictures())) {
//                    break;
//                }
//            } catch (Exception e) {
//                logger.error(" 挖财>>> 第[" + (i + 1) + "]次获取身份证OCR信息异常", e);
//            }
//        }
//        // 判断数据是否拉取成功
//        if (waCaiOcr == null || waCaiOcr.getIdCard() == null || CommUtils.isNull(waCaiOcr.getPictures())) {
//            return false;
//        }
//
//        // 保存数据
//        BwIdentityCard2 bwIdentityCard = new BwIdentityCard2();
//        bwIdentityCard.setBorrowerId(borrowerId);
//        bwIdentityCard = bwIdentityCardService.findBwIdentityCardByAttr(bwIdentityCard);
//        if (bwIdentityCard == null) {
//            bwIdentityCard = new BwIdentityCard2();
//
//            bwIdentityCard.setBorrowerId(borrowerId);
//            bwIdentityCard.setCreateTime(new Date());
//            bwIdentityCard.setUpdateTime(new Date());
//            bwIdentityCard.setAddress(waCaiOcr.getIdCard().getAddress());
//            bwIdentityCard.setBirthday(waCaiOcr.getIdCard().getBirthday());
//            bwIdentityCard.setGender(waCaiOcr.getIdCard().getGender());
//            bwIdentityCard.setIdCardNumber(waCaiOcr.getIdCard().getIdNumber());
//            bwIdentityCard.setIssuedBy(waCaiOcr.getIdCard().getAgency());
//            bwIdentityCard.setName(waCaiOcr.getIdCard().getName());
//            bwIdentityCard.setRace(waCaiOcr.getIdCard().getNation());
//            bwIdentityCard.setValidDate(waCaiOcr.getIdCard().getValidDateEnd());
//            bwIdentityCardService.saveBwIdentityCard(bwIdentityCard);
//        } else {
//            bwIdentityCard.setUpdateTime(new Date());
//            bwIdentityCard.setAddress(waCaiOcr.getIdCard().getAddress());
//            bwIdentityCard.setBirthday(waCaiOcr.getIdCard().getBirthday());
//            bwIdentityCard.setGender(waCaiOcr.getIdCard().getGender());
//            bwIdentityCard.setIdCardNumber(waCaiOcr.getIdCard().getIdNumber());
//            bwIdentityCard.setIssuedBy(waCaiOcr.getIdCard().getAgency());
//            bwIdentityCard.setName(waCaiOcr.getIdCard().getName());
//            bwIdentityCard.setRace(waCaiOcr.getIdCard().getNation());
//            bwIdentityCard.setValidDate(waCaiOcr.getIdCard().getValidDateEnd());
//            bwIdentityCardService.updateBwIdentityCard(bwIdentityCard);
//        }
//        // 插入身份认证记录
//        thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, Integer.valueOf(WaCaiConstant.CHANNEL_SX));
//        logger.info(sessionId + " 挖财>>> 处理身份证信息");
//
//
//        // 认证图片
//        String frontFile = null;
//        String backFile = null;
//        for (WaCaiPicture waCaiPicture : waCaiOcr.getPictures()) {
//            // 身份证正面照片
//            if ("ID_CARD_FRONT".equals(waCaiPicture.getType())) {
//                frontFile = waCaiPicture.getLocation();
//            }
//            // 身份证正面照片
//            if ("ID_CARD_BACK".equals(waCaiPicture.getType())) {
//                backFile = waCaiPicture.getLocation();
//            }
//        }
//        // 身份证正面照
//        if (StringUtils.isNotBlank(frontFile)) {
//            String frontImage = UploadToCssUtils.urlUpload(frontFile, orderId + "_01");
//            logger.info(sessionId + " 挖财>>> 身份证正面 " + frontImage);
//            // 保存身份证正面照Url
//            thirdCommonService.addOrUpdateBwAdjunct(sessionId, 1, frontImage, null, orderId, borrowerId, 0);
//        }
//        // 身份证反面照
//        if (StringUtils.isNotBlank(backFile)) {
//            String backImage = UploadToCssUtils.urlUpload(backFile, orderId + "_02");
//            logger.info(sessionId + " 挖财>>> 身份证反面 " + backImage);
//            // 保存身份证反面照Url
//            thirdCommonService.addOrUpdateBwAdjunct(sessionId, 2, backImage, null, orderId, borrowerId, 0);
//        }
//        return true;
//    }
//
//    /**
//     * 活体数据人脸比对
//     *
//     * @param openId 挖财用户标识
//     * @param sessionId 时间戳标记
//     * @param borrowerId 用户ID
//     * @param orderId 我方订单号
//     * @return 是否保存成功
//     */
//    private boolean saveLivingCompare(String openId, Long sessionId, Long borrowerId, Long orderId) {
//        // 请求数据
//        Map<String, String> headers = new HashMap<>(1);
//        headers.put("Authorization", "HmacSHA256 Credential=" + WaCaiConstant.APP_ID + ", Signature=" + WaCaiUtil.getSign("GET", null));
//        WaCaiLivingCompare waCaiLivingCompare = null;
//        for (int i = 0; i < 3; i++) {
//            try {
//                String livingCompareJson = WaCaiUtil.sendGet(WaCaiConstant.WACAI_URL + "/finance-open/api/v1/facePlus/livingCompare/" + openId, headers);
//                waCaiLivingCompare = JSON.parseObject(livingCompareJson, WaCaiLivingCompare.class);
//
//                if (waCaiLivingCompare != null && waCaiLivingCompare.isLivingCompareFlag() && !CommUtils.isNull(waCaiLivingCompare.getPictures())) {
//                    break;
//                }
//            } catch (Exception e) {
//                logger.error(sessionId + " 挖财>>> 第[" + (i + 1) + "]次获取活体数据人脸比对异常", e);
//            }
//        }
//
//        // 判断数据是否拉取成功
//        if (waCaiLivingCompare == null || CommUtils.isNull(waCaiLivingCompare.getPictures())) {
//            return false;
//        }
//
//        // 获取数据
//        String natureFile = null;
//        for (WaCaiPicture waCaiPicture : waCaiLivingCompare.getPictures()) {
//            if ("IMAGE_ENV".equals(waCaiPicture.getType())) {
//                natureFile = waCaiPicture.getLocation();
//            }
//        }
//
//        // 活体照片
//        if (StringUtils.isNotBlank(natureFile)) {
//            String handerImage = UploadToCssUtils.urlUpload(natureFile, orderId + "_03");
//            logger.info(sessionId + " 挖财>>> 手持照/人脸 " + handerImage);
//            // 保存活体照片Url
//            thirdCommonService.addOrUpdateBwAdjunct(sessionId, 3, handerImage, null, orderId, borrowerId, 0);
//        }
//        // 认证 照片
//        thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 3, Integer.valueOf(WaCaiConstant.CHANNEL_SX));
//        logger.info(sessionId + " 挖财>>> 处理认证图片 ");
//
//        return false;
//    }
//
//    /**
//     * 运营商信息
//     *
//     * @param openId 挖财用户标识
//     * @param sessionId 时间戳标记
//     * @param borrowerId 用户ID
//     * @param orderId 我方订单号
//     * @return 是否保存成功
//     */
//    private boolean saveOperator(String openId, Long sessionId, Long borrowerId, Long orderId, String mobile, SqlSession sqlSession) throws Exception {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        // 请求数据
//        Map<String, String> bodyMap = new HashMap<>(1);
//        bodyMap.put("mobile", mobile);
//        String bodyJson = JSON.toJSONString(bodyMap);
//        Map<String, String> headers = new HashMap<>(1);
//        headers.put("Authorization", "HmacSHA256 Credential=" + WaCaiConstant.APP_ID + ", Signature=" + WaCaiUtil.getSign("POST", bodyJson));
//        WaCaiOperator waCaiOperator = null;
//        for (int i = 0; i < 3; i++) {
//            try {
//                String livingCompareJson = WaCaiUtil.sendPost(WaCaiConstant.WACAI_URL + "/finance-open/api/v1/operators/" + openId + "/query", headers, bodyJson);
//                waCaiOperator = JSON.parseObject(livingCompareJson, WaCaiOperator.class);
//
//                if (waCaiOperator != null && waCaiOperator.getBaseInfo() != null && !CommUtils.isNull(waCaiOperator.getFlows()) && !CommUtils.isNull(waCaiOperator.getBills())) {
//                    break;
//                }
//            } catch (Exception e) {
//                logger.error(sessionId + " 挖财>>> 第[" + (i + 1) + "]次获取运营商信息异常", e);
//            }
//        }
//
//        // 判断数据是否拉取成功
//        if (waCaiOperator == null || CommUtils.isNull(waCaiOperator.getBills()) || CommUtils.isNull(waCaiOperator.getFlows())) {
//            return false;
//        }
//
//        // 数据
//        String idcard = waCaiOperator.getBaseInfo().getIdcard();
//        String addr = waCaiOperator.getBaseInfo().getProvince() + waCaiOperator.getBaseInfo().getCity() + waCaiOperator.getBaseInfo().getAddress();
//        String name = waCaiOperator.getBaseInfo().getName();
//        String phoneRemain = waCaiOperator.getBaseInfo().getBalance() + "";
//        String phone = waCaiOperator.getBaseInfo().getPhone();
//        Date registerTime = waCaiOperator.getBaseInfo().getRegisterTime();
//        String isidentify = waCaiOperator.getBaseInfo().getIsidentify();
//        String status = waCaiOperator.getBaseInfo().getStatus();
//        String provider = waCaiOperator.getBaseInfo().getProvider();
//
//        // 运营商
//        BwOperateBasic bwOperateBasicNew = new BwOperateBasic();
//        bwOperateBasicNew.setIdCard(idcard);
//        bwOperateBasicNew.setUserSource(provider);
//        bwOperateBasicNew.setAddr(addr);
//        bwOperateBasicNew.setRealName(name);
//        bwOperateBasicNew.setPhoneRemain(phoneRemain);
//        bwOperateBasicNew.setPhone(phone);
//        bwOperateBasicNew.setRegTime(registerTime);
//        bwOperateBasicNew.setAuthentication("是".equals(isidentify) ? "1" : ("否".equals(isidentify) ? "2" : "0"));
//        bwOperateBasicNew.setPhoneStatus("正常".equals(status) ? "1" : ("停机".equals(status) ? "2" : "0"));
//
//        BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//        if (bwOperateBasic == null) {
//            bwOperateBasicNew.setBorrowerId(borrowerId);
//            bwOperateBasicNew.setCreateTime(new Date());
//            bwOperateBasicNew.setUpdateTime(new Date());
//            bwOperateBasicService.save(bwOperateBasicNew);
//        } else {
//            bwOperateBasicNew.setUpdateTime(new Date());
//            bwOperateBasicService.update(bwOperateBasicNew);
//        }
//        logger.info(sessionId + " 挖财>>> 处理运营商信息");
//
//
//        // 风控--运营商
//        BwThirdOperateBasic bwThirdOperateBasicNew = new BwThirdOperateBasic();
//        bwThirdOperateBasicNew.setChannel(Integer.valueOf(WaCaiConstant.CHANNEL_SX));
//        bwThirdOperateBasicNew.setUserSource(provider);
//        bwThirdOperateBasicNew.setIdCard(idcard);
//        bwThirdOperateBasicNew.setAddr(addr);
//        bwThirdOperateBasicNew.setRealName(name);
//        bwThirdOperateBasicNew.setPhoneRemain(phoneRemain);
//        bwThirdOperateBasicNew.setPhone(phone);
//        bwThirdOperateBasicNew.setRegTime(registerTime);
//        bwThirdOperateBasicNew.setAuthentication("是".equals(isidentify) ? "1" : ("否".equals(isidentify) ? "2" : "0"));
//        bwThirdOperateBasicNew.setPhoneStatus("正常".equals(status) ? "1" : ("停机".equals(status) ? "2" : "0"));
//
//        BwThirdOperateBasic bwThirdOperateBasic = new BwThirdOperateBasic();
//        bwThirdOperateBasic.setOrderId(orderId);
//        bwThirdOperateBasic = bwThirdOperateBasicService.findByAttr(bwThirdOperateBasic);
//        if (bwThirdOperateBasic == null) {
//            bwThirdOperateBasicNew.setOrderId(orderId);
//            bwThirdOperateBasicNew.setCreateTime(new Date());
//            bwThirdOperateBasicNew.setUpdateTime(new Date());
//            bwThirdOperateBasicService.save(bwThirdOperateBasicNew);
//        } else {
//            bwThirdOperateBasicNew.setUpdateTime(new Date());
//            bwThirdOperateBasicService.update(bwThirdOperateBasicNew);
//        }
//
//
//        // 通话记录
//        // 创建 批处理mapper
//        BwOperateVoiceMapper operateVoiceMapper = sqlSession.getMapper(BwOperateVoiceMapper.class);
//        BwThirdOperateVoiceMapper thirdOperateVoiceMapper = sqlSession.getMapper(BwThirdOperateVoiceMapper.class);
//
//        Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//
//        for (WaCaiFlow waCaiFlow : waCaiOperator.getFlows()) {
//            // String phoneVoice = waCaiFlow.getPhone();
//            // String chargeMode = waCaiFlow.getChargeMode();
//            // Integer actualExpenses = waCaiFlow.getActualExpenses();
//            // Date updatedTime = waCaiFlow.getUpdatedTime();
//            String opposite = waCaiFlow.getOpposite();
//            Date callBegin = waCaiFlow.getCallBegin();
//            Integer callUsed = waCaiFlow.getCallUsed();
//            Integer callType = "主叫".equals(waCaiFlow.getStartMode()) ? Integer.valueOf(1) : ("被叫".equals(waCaiFlow.getStartMode()) ? 2 : null);
//            String tradeAddr = waCaiFlow.getNation() + waCaiFlow.getProvince() + waCaiFlow.getCity() + waCaiFlow.getPlace();
//
//            // 通话记录
//            BwOperateVoice bwOperateVoice = new BwOperateVoice();
//            bwOperateVoice.setBorrower_id(borrowerId);
//            bwOperateVoice.setTrade_type(1);
//            bwOperateVoice.setTrade_time(callUsed + "");
//            bwOperateVoice.setCall_time(sdf.format(callBegin));
//            bwOperateVoice.setTrade_addr(tradeAddr);
//            bwOperateVoice.setReceive_phone(opposite);
//            bwOperateVoice.setCall_type(callType);
//
//            // 风控--通话记录
//            BwThirdOperateVoice bwThirdOperateVoice = new BwThirdOperateVoice();
//            bwThirdOperateVoice.setOrderId(orderId);
//            bwThirdOperateVoice.setChannel(Integer.valueOf(WaCaiConstant.CHANNEL_SX));
//            bwThirdOperateVoice.setTradeType(1);
//            bwThirdOperateVoice.setTradeTime(callUsed + "");
//            bwThirdOperateVoice.setCallTime(sdf.format(callBegin));
//            bwThirdOperateVoice.setTradeAddr(tradeAddr);
//            bwThirdOperateVoice.setReceivePhone(opposite);
//            bwThirdOperateVoice.setCallType(callType);
//            // 追加插入
//            if (callDate == null || callBegin.after(callDate)) {
//                operateVoiceMapper.insert(bwOperateVoice);
//                thirdOperateVoiceMapper.insert(bwThirdOperateVoice);
//            }
//        }
//        // 提交 清理缓存
//        sqlSession.commit();
//        sqlSession.clearCache();
//
//        // 风控--账单
//
//        // 创建 批处理mapper todo
//        BwWcBillMapper wcBillMapper = sqlSession.getMapper(BwWcBillMapper.class);
//
//        // 先删除
//        BwWcBill bwWcBillDel = new BwWcBill();
//        bwWcBillDel.setOrderId(orderId);
//        wcBillMapper.delete(bwWcBillDel);
//
//        // 保存
//        for (WaCaiBill waCaiBill : waCaiOperator.getBills()) {
//            // 批量插入
//            BwWcBill bwWcBill = new BwWcBill();
//            bwWcBill.setOrderId(orderId);
//            bwWcBill.setPhone(waCaiBill.getPhone());
//            bwWcBill.setChargeBegin(waCaiBill.getChargeBegin());
//            bwWcBill.setChargeEnd(waCaiBill.getChargeEnd());
//            bwWcBill.setPlanAmount(waCaiBill.getPlanAmount());
//            bwWcBill.setFixedExpenses(waCaiBill.getFixedExpenses());
//            bwWcBill.setVoiceExpenses(waCaiBill.getVoiceExpenses());
//            bwWcBill.setNetExpenses(waCaiBill.getNetExpenses());
//            bwWcBill.setMmsExpenses(waCaiBill.getMmsExpenses());
//            bwWcBill.setAddedExpenses(waCaiBill.getAddedExpenses());
//            bwWcBill.setCollectionExpenses(waCaiBill.getCollectionExpenses());
//            bwWcBill.setOtherExpenses(waCaiBill.getOtherExpenses());
//            bwWcBill.setTotalAmount(waCaiBill.getTotalAmount());
//            bwWcBill.setPayAmount(waCaiBill.getPayAmount());
//            bwWcBill.setUpdatedTime(waCaiBill.getUpdatedTime());
//            bwWcBill.setCreateTime(new Date());
//            wcBillMapper.insert(bwWcBill);
//        }
//        // 提交 清理缓存
//        sqlSession.commit();
//        sqlSession.clearCache();
//
//        logger.info(sessionId + " 挖财>>> 运营商存储 ");
//        return true;
//
//    }
//
//    /**
//     * 设备信息
//     *
//     * @param openId 挖财用户标识
//     * @param sessionId 时间戳标记
//     * @param borrowerId 用户ID
//     * @param orderId 我方订单号
//     * @return 是否保存成功
//     */
//    private boolean saveDevices(String openId, Long sessionId, Long borrowerId, Long orderId, String phone, SqlSession sqlSession) {
//
//        // 请求数据
//        Map<String, String> headers = new HashMap<>(1);
//        headers.put("Authorization", "HmacSHA256 Credential=" + WaCaiConstant.APP_ID + ", Signature=" + WaCaiUtil.getSign("GET", null));
//        WaCaiDevice waCaiDevice = null;
//        for (int i = 0; i < 3; i++) {
//            try {
//                String orcJson = WaCaiUtil.sendGet(WaCaiConstant.WACAI_URL + "/finance-open/api/v1/devices/" + openId, headers);
//                waCaiDevice = JSON.parseObject(orcJson, WaCaiDevice.class);
//                if (waCaiDevice != null) {
//                    break;
//                }
//            } catch (Exception e) {
//                logger.error("挖财>>> 第[" + (i + 1) + "]次获设备信息异常", e);
//            }
//        }
//
//        // 判断数据是否拉取成功
//        if (waCaiDevice == null) {
//            return false;
//        }
//
//        // 创建 批处理mapper
//        BwWcDeviceMapper bwWcDeviceMapper = sqlSession.getMapper(BwWcDeviceMapper.class);
//
//        // 先删除
//        BwWcDevice bwWcDeviceDel = new BwWcDevice();
//        bwWcDeviceDel.setOrderId(orderId);
//        bwWcDeviceMapper.delete(bwWcDeviceDel);
//
//        // 插入
//        BwWcDevice bwWcDevice = new BwWcDevice();
//        bwWcDevice.setOrderId(orderId);
//        bwWcDevice.setDeviceId(waCaiDevice.getDeviceId());
//        bwWcDevice.setIdentifier1(waCaiDevice.getIdentifier1());
//        bwWcDevice.setIdentifier2(waCaiDevice.getIdentifier2());
//        bwWcDevice.setSeriaNo(waCaiDevice.getSeriaNo());
//        bwWcDevice.setUuid(waCaiDevice.getUuid());
//        bwWcDevice.setDeviceType(waCaiDevice.getDeviceType());
//        bwWcDevice.setPlatform(waCaiDevice.getPlatform());
//        bwWcDevice.setSimulator(waCaiDevice.getSimulator());
//        bwWcDevice.setRoot(waCaiDevice.getRoot());
//        bwWcDevice.setMemorySize(waCaiDevice.getMemorySize());
//        bwWcDevice.setStorageSize(waCaiDevice.getStorageSize());
//        bwWcDevice.setAvaStorageSize(waCaiDevice.getAvaStorageSize());
//        bwWcDevice.setSystemVersion(waCaiDevice.getSystemVersion());
//        bwWcDevice.setAppName(waCaiDevice.getAppName());
//        bwWcDevice.setAppVersion(waCaiDevice.getAppVersion());
//        bwWcDevice.setUpdatedTime(waCaiDevice.getUpdatedTime());
//        bwWcDevice.setCreateTime(new Date());
//        bwWcDeviceMapper.insert(bwWcDevice);
//
//        // 提交 清理缓存
//        sqlSession.commit();
//        sqlSession.clearCache();
//
//        logger.info(sessionId + " 挖财>>> 设备信息 ");
//        return true;
//    }
//
//    /**
//     * 通讯录信息
//     *
//     * @param openId 挖财用户标识
//     * @param sessionId 时间戳标记
//     * @param borrowerId 用户ID
//     * @param orderId 我方订单号
//     * @return 是否保存成功
//     */
//    private boolean saveContact(String openId, Long sessionId, Long borrowerId, Long orderId, String phone) {
//        // 请求数据
//        Map<String, String> headers = new HashMap<>(1);
//        headers.put("Authorization", "HmacSHA256 Credential=" + WaCaiConstant.APP_ID + ", Signature=" + WaCaiUtil.getSign("GET", null));
//        List<WaCaiContact> waCaiContacts = null;
//        for (int i = 0; i < 3; i++) {
//            try {
//                String json = WaCaiUtil.sendGet(WaCaiConstant.WACAI_URL + "/finance-open/api/v1/contacts/" + openId, headers);
//                waCaiContacts = JSON.parseArray(json, WaCaiContact.class);
//                if (!CommUtils.isNull(waCaiContacts)) {
//                    break;
//                }
//            } catch (Exception e) {
//                logger.error("挖财>>> 第[" + (i + 1) + "]次获取通讯录信息异常", e);
//            }
//        }
//
//        // 判断数据是否拉取成功
//        if (waCaiContacts == null || CommUtils.isNull(waCaiContacts)) {
//            return false;
//        }
//
//        // 保存数据
//        List<BwContactList> listConS = new ArrayList<>();
//        for (WaCaiContact waCaiContact : waCaiContacts) {
//            if (StringUtils.isBlank(waCaiContact.getName())) {
//                continue;
//            }
//            if (CommUtils.isNull(waCaiContact.getMobile())) {
//                continue;
//            }
//            BwContactList bwContactList = new BwContactList();
//            bwContactList.setBorrowerId(borrowerId);
//            bwContactList.setPhone(waCaiContact.getMobile().get(0).replaceAll(" ", "").replaceAll("-", ""));
//            bwContactList.setName(waCaiContact.getName());
//            listConS.add(bwContactList);
//        }
//        bwContactListService.addOrUpdateBwContactLists(listConS, borrowerId);
//
//        logger.info(sessionId + " 挖财>>> 通讯录 ");
//        return true;
//    }
//
//    /**
//     * 推送进件结果
//     *
//     * @param sessionId 时间戳
//     * @param orderCode 推送结果码
//     * @param massage 推送信息
//     * @param thirdOrderNo 三方工单号
//     * @param openId 挖财用户标识
//     * @throws IOException 异常
//     */
//    private void pushOrderResult(Long sessionId, Integer orderCode, String massage, String thirdOrderNo, String openId) throws IOException {
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        // 封装数据
//        BwProductDictionary bwProductDictionary = bwProductDictionaryService.findBwProductDictionaryById(Integer.valueOf(WaCaiConstant.PRODUCT_ID));
//        Double interestRate = bwProductDictionary.getInterestRate();
//        int interestRateDay = DoubleUtil.mul(DoubleUtil.div(interestRate, 7.0), 10000.0).intValue();
//        WaCaiApproval waCaiApproval = new WaCaiApproval();
//        // 响应码
//        waCaiApproval.setApprovalResults(orderCode);
//        waCaiApproval.setApprovalMessage(massage);
//
//        waCaiApproval.setOrderId(thirdOrderNo);
//        waCaiApproval.setOpenId(openId);
//        waCaiApproval.setApprovalTime(sdf.format(new Date()));
//        waCaiApproval.setApprovalAmount(new WaCaiApprovalAmount());
//        waCaiApproval.setAppliedAmount(0L);
//        waCaiApproval.setRemainAmount(0L);
//        waCaiApproval.setTerm(new WaCaiTerm());
//        waCaiApproval.setRate(interestRateDay);
//        waCaiApproval.setRateType(1);
//
//        // 准备数据
//        String json = JSON.toJSONString(waCaiApproval);
//        Map<String, String> headers = new HashMap<>(1);
//        headers.put("Authorization", "HmacSHA256 Credential=" + WaCaiConstant.APP_ID + ", Signature=" + WaCaiUtil.getSign("POST", json));
//
//        // 推送数据
//        logger.info(sessionId + " 挖财>>>进件结果推送，数据：" + json);
//        // String responseResult = WaCaiUtil.sendPost("", headers, json);
//        // logger.info(sessionId + " 挖财>>>进件结果推送，返回：" + responseResult);
//    }
//}
