///******************************************************************************
// * Copyright (C) 2016 Wuhan Medical union Co.Ltd All Rights Reserved. 
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
// * 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service.impl;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwPersonInfo;
//import com.waterelephant.service.IBwBorrowerService;
//import com.waterelephant.service.IBwOrderService;
//import com.waterelephant.service.IBwPersonInfoService;
//import com.waterelephant.sxyDrainage.service.CloudService;
//
///**
// * Module:(code:saas)
// * <p>
// * CloudServiceImpl.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//@Service
//public class CloudServiceImpl implements CloudService {
//    private Logger logger = Logger.getLogger(CloudServiceImpl.class);
//    @Autowired
//    private IBwOrderService bwOrderService;
//
//    @Autowired
//    private IBwBorrowerService bwBorrowerService;
//    @Autowired
//    private IBwPersonInfoService bwPersonInfoService;
//
//    /**
//     * @see com.waterelephant.sxyDrainage.service.CloudService#findBwBorrower
//     */
//    @Override
//    public List<BwBorrower> findBwBorrower(String startTime, String endTime, int pageSize, int pageNum) {
//        try {
//            // 通过更新时间查询的
//            // List<Long> borrowerIds = bwOrderService.findByUpdateTime(startTime, endTime, pageSize, pageNum);
//
//            // 通过创建时间查询的
//            List<Long> borrowerIds2 = bwOrderService.findByCreateTime(startTime, endTime, pageSize, pageNum);
//
//            List<BwBorrower> bwBorrowers = new ArrayList<>();
//            // for (Long borrowerId : borrowerIds) {
//            // BwBorrower bwBorrower = new BwBorrower();
//            // bwBorrower.setId(borrowerId);
//            // bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//            // if (bwBorrower != null) {
//            // bwBorrowers.add(bwBorrower);
//            // }
//            // }
//            for (Long borrowerId : borrowerIds2) {
//                BwBorrower bwBorrower = new BwBorrower();
//                bwBorrower.setId(borrowerId);
//                bwBorrower = bwBorrowerService.findBwBorrowerByAttr(bwBorrower);
//                if (bwBorrower != null) {
//                    bwBorrowers.add(bwBorrower);
//                }
//            }
//            return bwBorrowers;
//        } catch (Exception e) {
//            logger.error("获取数据异常", e);
//            return null;
//        }
//    }
//
//    /**
//     * @see com.waterelephant.sxyDrainage.service.CloudService#findBwBorrowerList
//     */
//    @Override
//    public List<BwBorrower> findBwBorrowerList(Long merchantId) {
//        try {
//            List<BwBorrower> bwBorrowers = bwOrderService.findByMerchantId(merchantId);
//            return bwBorrowers;
//        } catch (Exception e) {
//            logger.error("查询用户异常", e);
//            return null;
//        }
//    }
//
//    /**
//     * @see com.waterelephant.sxyDrainage.service.CloudService#findBorrowerInfoList(java.lang.String, java.lang.String,
//     * int, int, int)
//     */
//    @Override
//    public List<Map<String, String>> findBorrowerInfoList(String startTime, String endTime, int pageSize, int pageNum,
//                                                          int orderStatus) {
//        try {
//            List<BwOrder> bwOrders;
//            if (orderStatus == 1) {
//                bwOrders = bwOrderService.findBwOrderByParams(startTime, endTime, pageSize, pageNum, orderStatus);
//            } else {
//                bwOrders = bwOrderService.findBwOrderByParams2(startTime, endTime, pageSize, pageNum, orderStatus);
//            }
//
//            List<Map<String, String>> borrowerInfos = new ArrayList<>();
//
//            for (BwOrder bwOrder : bwOrders) {
//                Long orderId = bwOrder.getId();
//                Long borrowerId = bwOrder.getBorrowerId();
//
//                // 获取所在地
//                BwPersonInfo bwPersonInfo = bwPersonInfoService.findBwPersonInfoByOrderId(orderId);
//                String cityName = null;
//                if (bwPersonInfo != null) {
//                    cityName = bwPersonInfo.getCityName();
//                    if (cityName != null) {
//                        if (cityName.length() >= 3) {
//                            cityName = cityName.substring(cityName.length() - 3, cityName.length() - 1);
//                        }
//                    }
//                }
//
//                Map<String, Object> borrowerMap = bwBorrowerService.findBorrowerAndPositionXY(borrowerId);
//                String name = (String) borrowerMap.get("name");
//                String phone = (String) borrowerMap.get("phone");
//                String idCard = (String) borrowerMap.get("id_card");
//                BigDecimal x = (BigDecimal) borrowerMap.get("position_x");
//                BigDecimal y = (BigDecimal) borrowerMap.get("position_y");
//
//                Map<String, String> map = new HashMap<>();
//                map.put("name", name);
//                map.put("phone", phone);
//                map.put("idCard", idCard);
//                map.put("longitude", x == null ? null : x.toString());
//                map.put("latitude", y == null ? null : y.toString());
//                map.put("location", cityName);
//
//                borrowerInfos.add(map);
//            }
//            return borrowerInfos;
//        } catch (Exception e) {
//            logger.error("findBorrowerInfoList方法获取数据异常", e);
//            return null;
//        }
//    }
//
//}
