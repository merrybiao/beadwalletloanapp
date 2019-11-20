///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.jdq.JdqBindCardReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCalculateReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCardInfoReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCheckUserData;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqContractReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqOrderInfoRequest;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqPullOrderStatusReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqRepaymentReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqResponse;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqWithdrawReq;
//
//import java.text.ParseException;
//
///**
// * Module:
// * <p>
// * JdqService.java
// *
// * @author 王飞
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//
//public interface JdqService {
//
//    /**
//     * 检测用户
//     *
//     * @param jdqCheckUserData
//     * @return
//     */
//    JdqResponse checkUser(JdqCheckUserData jdqCheckUserData) throws ParseException;
//
//    /**
//     * 用户进件保存数据
//     *
//     * @param jdqOrderInfoRequest
//     * @return
//     */
//    JdqResponse saveOrder(JdqOrderInfoRequest jdqOrderInfoRequest);
//
//    /**
//     * 订单试算数据
//     *
//     * @param jdqCalculateReq
//     * @return
//     */
//    JdqResponse calculateOrder(JdqCalculateReq jdqCalculateReq);
//
//    /**
//     * 3.6 确认提现
//     *
//     * @param jdqWithdrawReq
//     * @return
//     */
//    JdqResponse updateWithdraw(JdqWithdrawReq jdqWithdrawReq);
//
//    /**
//     * 3.7 主动还款接口
//     *
//     * @param jdqRepaymentReq 主动还款请求实体数据
//     * @return
//     */
//    JdqResponse updateActiveRepayment(JdqRepaymentReq jdqRepaymentReq);
//
//    /**
//     * 3.8 获取订单状态
//     *
//     * @param jdqPullOrderStatusReq 主动获取订单状态实体数据
//     * @return
//     */
//    JdqResponse orderStatus(JdqPullOrderStatusReq jdqPullOrderStatusReq);
//
//    /**
//     * 3.10 预绑卡接口（新绑卡）
//     *
//     * @param jdqBindCardReq
//     * @return
//     */
//    JdqResponse saveBindCardPre(JdqBindCardReq jdqBindCardReq);
//
//    /**
//     * 附带手机验证码的绑卡
//     *
//     * @param jdqBindCardReq
//     * @return
//     */
//    JdqResponse saveBindCardWithCode(JdqBindCardReq jdqBindCardReq);
//
//    /**
//     * 3.13 查询银行卡信息接口
//     *
//     * @param jdqCardInfoReq
//     * @return
//     */
//    JdqResponse queryCardInfo(JdqCardInfoReq jdqCardInfoReq);
//
//    /**
//     * 3.14合同接口
//     *
//     * @param jdqContractReq
//     * @return
//     */
//    JdqResponse loanContract(JdqContractReq jdqContractReq);
//
//    /**
//     * 新预绑卡，修复预留手机号与注册手机号不一致的情况
//     *
//     * @param jdqBindCardReq
//     * @return
//     */
//    JdqResponse saveBindCardPreNew(JdqBindCardReq jdqBindCardReq);
//
//    /**
//     * 确认绑卡，修复预留手机号与注册手机号不一致的情况
//     *
//     * @param jdqBindCardReq
//     * @return
//     */
//    JdqResponse saveBindCardWithCodeNew(JdqBindCardReq jdqBindCardReq);
//
//}
