///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 本软件为武汉水象科技有限公司开发研制。
// * 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.drainage.entity.common.PushOrderRequest;
//import com.waterelephant.entity.*;
//import com.waterelephant.service.*;
//import com.waterelephant.sxyDrainage.service.AsyncTodHdService;
//import com.waterelephant.sxyDrainage.service.HdPushOrderService;
//import com.waterelephant.sxyDrainage.utils.DrainageUtils;
//import com.waterelephant.sxyDrainage.utils.haodai.HaoDaiConstant;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.RedisUtils;
//import com.waterelephant.utils.SystemConstant;
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// *
// *
// * Module:
// *
// * HdPushOrderServiceImpl.java
// *
// * @author 王飞
// * @since JDK 1.8
// * @version 1.0
// * @description: <描述>
// */
//@Service
//public class HdPushOrderServiceImpl implements HdPushOrderService {
//
//    private Logger logger = Logger.getLogger(HdPushOrderServiceImpl.class);
//
//    private final static String PRODUCT_ID = HaoDaiConstant.get("product_id");
//    private static final String CHANNELID = HaoDaiConstant.get("channel_id");
//    private static final String hdUserKey = "tripartite:hdUserKey:";
//
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//    @Autowired
//    private BwOrderRongService bwOrderRongService;
//    @Autowired
//    private BwOperateBasicService bwOperateBasicService;
//    @Autowired
//    private BwOperateVoiceService bwOperateVoiceService;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//    @Autowired
//    private IBwMerchantOrderService bwMerchantOrderServiceImpl;
//    @Autowired
//    AsyncTodHdService asyncTodHdService;
//
//    @Autowired
//    private BwTripartiteMarkService bwTripartiteMarkService;
//
//    /**
//     * 好贷网基础数据异步保存
//     */
//    @Transactional(rollbackFor = RuntimeException.class)
//    @Async("taskExecutor")
//    @Override
//    public void saveHdOrderInfo(PushOrderRequest pushOrderRequest) {
//
//        long sessionId = System.currentTimeMillis();
//        logger.info("START>>>savePushOrder>>>" + sessionId);
//        try {
//            if (null == pushOrderRequest) {
//                logger.info(sessionId + "END>>>saveHdOrderInfo>>> pushOrderRequest 为空");
//                return;
//            }
//            String userName = pushOrderRequest.getUserName();
//            String idCard = pushOrderRequest.getIdCard();
//            String phone = pushOrderRequest.getPhone();
//            Integer channelId = pushOrderRequest.getChannelId();
//            String thirdOrderNo = pushOrderRequest.getThirdOrderNo();
//
//            // 新增或更新借款人
//            BwBorrower borrower = thirdCommonService.addOrUpdateBorrower(sessionId, userName, idCard, phone, channelId);
//            long borrowerId = borrower.getId();
//            logger.info(sessionId + ">>> 好贷新增/更新借款人ID：" + borrowerId);
//
//            long count = bwOrderService.findProOrder(borrowerId + "");
//            if (count > 0) {
//                logger.info(sessionId + "END>>>saveHdOrderInfo>>> 好贷存在进行中的订单");
//                return;
//            }
//
//            BwOrder bwOrder = new BwOrder();
//            bwOrder.setBorrowerId(borrowerId);
//            bwOrder.setStatusId(1L);
//            bwOrder.setProductType(2);
//            bwOrder.setChannel(Integer.valueOf(channelId));
//            // 先查询草稿状态的订单
//            List<BwOrder> boList = bwOrderService.findBwOrderListByAttr(bwOrder);
//            if (boList != null && boList.size() > 0) {
//                bwOrder = boList.get(boList.size() - 1);
//                bwOrder.setStatusId(1L);
//                bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwOrder.setProductId(Integer.valueOf(PRODUCT_ID));
//                bwOrder.setRepayType(2);
//                bwOrder.setExpectMoney(pushOrderRequest.getExpectMoney());
//                bwOrder.setExpectNumber(pushOrderRequest.getExpectNumber());
//
//                bwOrderService.updateBwOrder(bwOrder);
//            } else {
//                bwOrder = new BwOrder();
//                bwOrder.setOrderNo(DrainageUtils.generateOrderNo());
//                bwOrder.setBorrowerId(borrower.getId());
//                bwOrder.setStatusId(1L);
//                bwOrder.setCreateTime(Calendar.getInstance().getTime());
//                bwOrder.setUpdateTime(Calendar.getInstance().getTime());
//                bwOrder.setChannel(channelId);
//                bwOrder.setAvoidFineDate(Integer.parseInt(SystemConstant.DEFAULT_AVOID_FINE_DATE));
//                bwOrder.setApplyPayStatus(0);
//                bwOrder.setProductId(Integer.valueOf(PRODUCT_ID));
//                bwOrder.setProductType(2);
//                bwOrder.setRepayType(2);
//                bwOrder.setExpectMoney(pushOrderRequest.getExpectMoney());
//                bwOrder.setExpectNumber(pushOrderRequest.getExpectNumber());
//
//                bwOrderService.addBwOrder(bwOrder);
//            }
//            long orderId = bwOrder.getId();
//            logger.info(sessionId + ">>> 判断好贷是否有草稿状态的订单：ID = " + orderId);
//
//            // 征信数据 2018-06-26添加
//            try {
//                String key = "phone_apply";
//                Map<String, Object> params = new HashMap<>();
//                params.put("mobile", phone);
//                params.put("order_id", orderId);
//                params.put("borrower_id", borrowerId);
//                String value = JSON.toJSONString(params);
//                RedisUtils.rpush(key, value);
//            } catch (Exception e) {
//                logger.info(sessionId + ">>> 征信Redis存储异常");
//            }
//
//			// 保存运营商 TODO
//            asyncTodHdService.save(JSON.toJSONString(pushOrderRequest.getOperaterData()), orderId);
//
//            // 判断是否有融360订单
//            BwOrderRong bwOrderRong = new BwOrderRong();
//            bwOrderRong.setOrderId(orderId);
//            bwOrderRong.setChannelId(Long.valueOf(CHANNELID));
//            bwOrderRong = bwOrderRongService.findBwOrderRongByAttr(bwOrderRong);
//            if (bwOrderRong == null) {
//                bwOrderRong = new BwOrderRong();
//                bwOrderRong.setOrderId(orderId);
//                bwOrderRong.setThirdOrderNo(thirdOrderNo);
//                bwOrderRong.setChannelId(Long.valueOf(channelId));
//                bwOrderRong.setCreateTime(Calendar.getInstance().getTime());
//                bwOrderRongService.save(bwOrderRong);
//            } else {
//                if (null == bwOrderRong.getChannelId()) {
//                    bwOrderRong.setChannelId(Long.valueOf(channelId));
//                }
//                bwOrderRong.setThirdOrderNo(thirdOrderNo);
//                bwOrderRongService.update(bwOrderRong);
//            }
//            logger.info(sessionId + ">>> 判断好贷是否有融360订单");
//
//            // 判断是否有商户订单信息
//            BwMerchantOrder bwMerchantOrder = new BwMerchantOrder();
//            bwMerchantOrder.setOrderId(orderId);
//            bwMerchantOrder = bwMerchantOrderServiceImpl.selectByAtt(bwMerchantOrder);
//            if (bwMerchantOrder == null) {
//                bwMerchantOrder = new BwMerchantOrder();
//                bwMerchantOrder.setBorrowerId(borrowerId);
//                bwMerchantOrder.setCreateTime(Calendar.getInstance().getTime());
//                bwMerchantOrder.setExt3("0");
//                bwMerchantOrder.setMerchantId(0L);
//                bwMerchantOrder.setOrderId(orderId);
//                bwMerchantOrder.setUpdateTime(Calendar.getInstance().getTime());
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
//            BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//            BwPersonInfo bwPersonInfo_ = pushOrderRequest.getBwPersonInfo();
//            if (null == bwPersonInfo_) {
//                bwPersonInfo_ = new BwPersonInfo();
//            }
//            if (bwPersonInfo == null) {
//                bwPersonInfo_.setCreateTime(new Date());
//                bwPersonInfo_.setOrderId(orderId);
//                bwPersonInfo_.setUpdateTime(new Date());
//                bwPersonInfoService.add(bwPersonInfo_);
//            } else {
//                bwPersonInfo.setAddress(bwPersonInfo_.getAddress());
//                bwPersonInfo.setUpdateTime(new Date());
//                bwPersonInfo.setWechat(bwPersonInfo_.getWechat());
//                bwPersonInfoService.update(bwPersonInfo);
//            }
//            logger.info(sessionId + ">>> 处理好贷亲属联系人信息");
//
//            // 运营商
//            BwOperateBasic bwOperateBasic_ = pushOrderRequest.getBwOperateBasic();
//            BwOperateBasic bwOperateBasic = bwOperateBasicService.getOperateBasicById(borrowerId);
//            if (bwOperateBasic == null) {
//                if (null == bwOperateBasic_) {
//                    bwOperateBasic_ = new BwOperateBasic();
//                }
//                bwOperateBasic_.setBorrowerId(borrowerId);
//                bwOperateBasic_.setCreateTime(new Date());
//                bwOperateBasic_.setUpdateTime(new Date());
//                bwOperateBasicService.save(bwOperateBasic_);
//            } else if (null != bwOperateBasic_ && null != bwOperateBasic) {
//                bwOperateBasic.setBorrowerId(borrowerId);
//                bwOperateBasic.setUpdateTime(new Date());
//                bwOperateBasic.setUserSource(bwOperateBasic_.getUserSource());
//                bwOperateBasic.setIdCard(bwOperateBasic_.getIdCard());
//                bwOperateBasic.setAddr(bwOperateBasic_.getAddr());
//                bwOperateBasic.setPhone(bwOperateBasic_.getPhone());
//                bwOperateBasic.setPhoneRemain(bwOperateBasic_.getPhoneRemain());
//                bwOperateBasic.setRealName(bwOperateBasic_.getRealName());
//                bwOperateBasic.setUpdateTime(new Date());
//                bwOperateBasic.setRegTime(bwOperateBasic_.getRegTime());
//                bwOperateBasicService.update(bwOperateBasic);
//            }
//            logger.info(sessionId + ">>> 处理运营商信息");
//
//            // 通话记录
//            List<BwOperateVoice> bwOperateVoice_ = pushOrderRequest.getBwOperateVoiceList();
//            Date callDate = bwOperateVoiceService.getCallTimeByborrowerIdEs(borrowerId);
//            if (CollectionUtils.isNotEmpty(bwOperateVoice_)) {
//                SimpleDateFormat sdf_hms = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//                for (BwOperateVoice bwOperateVoice : bwOperateVoice_) {
//                    try {
//                        Date jsonCallData = sdf_hms.parse(bwOperateVoice.getCall_time());
//                        if (callDate == null || jsonCallData.after(callDate)) { // 通话记录采取最新追加的方式
//                            bwOperateVoice.setUpdateTime(new Date());
//                            bwOperateVoice.setBorrower_id(borrowerId);
//                            bwOperateVoiceService.save(bwOperateVoice);
//                        }
//                    } catch (Exception e) {
//                        logger.error(sessionId + ">>> 插入单条通话记录异常，忽略该条记录" + e.getMessage());
//                    }
//                }
//            }
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, orderId, 1, channelId);// 插入运营商认证记录
//            logger.info(sessionId + ">>> 处理通话记录信息 ");
//
//            BwTripartiteMark markByIndexKey = bwTripartiteMarkService.getBwTripartiteMarkByIndexKey(thirdOrderNo);
//            if (markByIndexKey == null) {
//                markByIndexKey = new BwTripartiteMark();
//                markByIndexKey.setIndexKey(thirdOrderNo);
//                markByIndexKey.setChannelId(Integer.valueOf(channelId));
//                markByIndexKey.setSaveOk(0);
//                markByIndexKey.setCreateTime(new Date());
//                bwTripartiteMarkService.save(markByIndexKey);
//            } else {
//                markByIndexKey.setSaveOk(0);
//                markByIndexKey.setUpdateTime(new Date());
//                bwTripartiteMarkService.updateByAttr(markByIndexKey);
//            }
//            // RedisUtils.set("tripartite:hdUserKey:" + thirdOrderNo, "");
//            logger.info(sessionId + "END>>>saveHdOrderInfo>>> 成功");
//            return;
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(sessionId + "END>>>saveHdOrderInfo>>>" + e.getMessage());
//            return;
//        }
//    }
//}
