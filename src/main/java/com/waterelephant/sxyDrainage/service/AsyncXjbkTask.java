//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.UserAdditional;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.UserInfo;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.UserVerify;
//
///**
// * (code:xjbk001)
// *
// * @Author: ZhangChong
// * @Description: 异步处理运营商等数据
// * @Date: Created in 14:24 2018/6/25
// * @Modified By:
// */
//public interface AsyncXjbkTask {
//    /**
//     * 异步处理
//     *
//     * @param sessionId      时间戳
//     * @param userVerify     认证信息详情
//     * @param userInfo       用户信息详情
//     * @param bwOrder        订单信息
//     * @param bwBorrower     用户信息
//     */
//    void saveOperatorData(Long sessionId, UserVerify userVerify, UserInfo userInfo,
//                          BwOrder bwOrder, BwBorrower bwBorrower, String thirdOrderNo);
//}
