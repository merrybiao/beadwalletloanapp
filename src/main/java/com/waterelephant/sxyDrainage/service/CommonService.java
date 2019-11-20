//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.drainage.entity.common.PushOrderRequest;
//import com.waterelephant.entity.BwBorrower;
//import com.waterelephant.entity.BwOrder;
//import com.waterelephant.entity.BwOrderRong;
//import com.waterelephant.sxyDrainage.entity.DrainageBindCardVO;
//import com.waterelephant.sxyDrainage.entity.DrainageRsp;
//
//import java.util.Collection;
//
//import tk.mybatis.mapper.common.Mapper;
//
///**
// * 水象云 - 公共方法
// * <p>
// * <p>
// * Module:
// * <p>
// * CommonService.java
// *
// * @author liuDaodao
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//public interface CommonService {
//    /**
//     * 公共检验用户方法
//     *
//     * @param name
//     * @param phone
//     * @param idCard
//     * @return
//     */
//    public DrainageRsp checkUser(Long sessionId, String name, String phone, String idCard);
//
//    /**
//     * 公共工单进件方法
//     *
//     * @param orderRequest
//     * @return
//     */
//    public DrainageRsp saveOrder(Long sessionId, PushOrderRequest orderRequest);
//
//    /**
//     * 公共方法 - 订单查询方法(订单，还款 计划，逾期记录)
//     *
//     * @param orderNo
//     * @return
//     */
//    public DrainageRsp queryOrder(Long sessionId, String orderNo);
//
//    /**
//     * 公共方法 - 试算
//     *
//     * @param loanAmount
//     * @param loanTrem
//     * @return
//     */
//    public DrainageRsp calculate(Long sessionId, int loanAmount, int loanTerm);
//
//    /**
//     * 公共方法 - 绑卡接口
//     *
//     * @param sessionId
//     * @param idCardNo     身份证号
//     * @param bankCardNo   绑定卡号
//     * @param name         持卡人姓名
//     * @param regPhone     银行卡绑定手机号
//     * @param thirdOrderNo 三方订单号
//     * @return
//     */
//    public DrainageRsp saveBindCard(Long sessionId, String idCardNo, String bankCardNo, String name, String regPhone,
//                                    String thirdOrderNo);
//
//    /**
//     * 公共方法 - 签约
//     *
//     * @param orderId
//     * @return
//     */
//    public DrainageRsp updateSignContract(Long sessionId, String thirdOrderNo);
//
//    /**
//     * 公共方法 - 获取合同接口
//     *
//     * @param orderId
//     * @return
//     */
//    public DrainageRsp getContactUrl(Long sessionId, String thirdOrderNo);
//
//    /**
//     * 主动还款
//     */
//    public DrainageRsp updateRepayment(Long sessionId, String thirdOrderNo);
//
//    /**
//     * 预绑卡
//     *
//     * @param sessionId
//     * @param drainageBindCardVO
//     * @return
//     */
//    DrainageRsp saveBindCard_NewReady(Long sessionId, DrainageBindCardVO drainageBindCardVO);
//
//    /**
//     * 确认绑卡
//     *
//     * @param sessionId
//     * @param drainageBindCardVO
//     * @return
//     */
//    DrainageRsp saveBindCard_NewSure(Long sessionId, DrainageBindCardVO drainageBindCardVO);
//
//    /**
//     * @param sessionId
//     * @param thirdOrderNo
//     * @return
//     */
//    DrainageRsp updateRepayment_New(Long sessionId, String thirdOrderNo);
//
//    /**
//     * @param sessionId
//     * @param name
//     * @param phone
//     * @param idCard
//     * @param channelId
//     * @return
//     */
//    DrainageRsp checkUserNew(Long sessionId, String name, String phone, String idCard, String channelId);
//
//    /**
//     * 提交机审
//     *
//     * @param bwBorrower
//     * @param bwOrder
//     * @param channelId
//     * @param thirdOrderNo
//     */
//    void sumbitAI(BwBorrower bwBorrower, BwOrder bwOrder, Integer channelId, String thirdOrderNo);
//
//    /**
//     * 批处理
//     *
//     * @param collection
//     * @param mapper
//     */
//    void batch(Collection<?> collection, Mapper mapper);
//}
