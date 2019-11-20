//package com.waterelephant.sxyDrainage.service;
//
//import com.waterelephant.sxyDrainage.entity.geinihua.ApprovalConfirm;
//import com.waterelephant.sxyDrainage.entity.geinihua.BasicInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.BindingCard;
//import com.waterelephant.sxyDrainage.entity.geinihua.Contract;
//import com.waterelephant.sxyDrainage.entity.geinihua.OrderInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.ResponseInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.Supplement;
//import com.waterelephant.sxyDrainage.entity.geinihua.UserInfo;
//
///**
// * 给你花
// *
// * @author xanthuim
// */
//public interface GeinihuaService {
//
//    /**
//     * 3种细分情况说明： code=10004的时候表示用户不可以申请，即为不可申请用户，此时需要返回不可申请的原因。异常代码说明：
//     * R001：已经在对方有进行中的贷款 R002：在对方有不良贷款记录 R003：30天内被机构审批拒绝过
//     * R004：自定义情况，（此时message需要机构放提供实际原因）
//     * <p>
//     * code=10001的时候表示用户可以申请，用户为复贷用户。 code=200的时候表示用户为新用户可以申请。
//     *
//     * @param userInfo
//     * @return
//     */
//    ResponseInfo<?> checkUser(UserInfo userInfo);
//
//    /**
//     * 基本信息
//     *
//     * @param basicInfo
//     * @return
//     * @throws Exception
//     */
//    ResponseInfo<?> basicInfo(BasicInfo basicInfo) throws Exception;
//
//    /**
//     * 附加信息
//     *
//     * @param supplement
//     * @return
//     */
//    ResponseInfo<?> supplementInfo(Supplement supplement);
//
//    /**
//     * 绑卡信息
//     *
//     * @param orderNo
//     * @return
//     */
//    ResponseInfo<?> bindingCards(String orderNo);
//
//    /**
//     * 支持的银行卡
//     *
//     * @return
//     */
//    ResponseInfo<?> banks();
//
//    /**
//     * 绑卡
//     *
//     * @param bindingCard
//     * @return
//     */
//    ResponseInfo<?> bindCard(BindingCard bindingCard);
//
//    /**
//     * 确认绑卡
//     *
//     * @param bindingCard
//     * @return
//     */
//    ResponseInfo<?> verifyBinding(BindingCard bindingCard);
//
//    /**
//     * 审批结论拉取
//     *
//     * @param orderInfo
//     * @return
//     */
//    ResponseInfo<?> approvalResult(OrderInfo orderInfo);
//
//    /**
//     * 审批确认
//     *
//     * @param orderInfo
//     * @return
//     */
//    ResponseInfo<?> approvalConfirm(OrderInfo orderInfo);
//
//    /**
//     * 合同
//     *
//     * @param contract
//     * @return
//     */
//    ResponseInfo<?> contract(Contract contract);
//
//    /**
//     * 订单试算
//     *
//     * @param approvalConfirm
//     * @return
//     */
//    ResponseInfo<?> caculateOrder(ApprovalConfirm approvalConfirm);
//
//    /**
//     * 订单状态
//     *
//     * @param orderInfo
//     * @return
//     */
//    ResponseInfo<?> orderStatus(OrderInfo orderInfo);
//
//    /**
//     * 主动还款
//     *
//     * @param orderInfo
//     * @return
//     */
//    ResponseInfo<?> postiveRepay(OrderInfo orderInfo);
//
//    /**
//     * 计划还款
//     *
//     * @param orderInfo
//     * @return
//     */
//    ResponseInfo<?> replanRepay(OrderInfo orderInfo);
//
//    /**
//     * 还款详情
//     *
//     * @param orderInfo
//     * @return
//     */
//    ResponseInfo<?> detailRepay(OrderInfo orderInfo);
//}
