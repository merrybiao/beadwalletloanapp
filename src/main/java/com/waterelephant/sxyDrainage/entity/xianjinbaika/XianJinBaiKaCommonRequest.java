//package com.waterelephant.sxyDrainage.entity.xianjinbaika;
//
///**
// * Module: (code:xjbk)
// * <p>
// * XianJinBaiKaCommonRequest.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//public class XianJinBaiKaCommonRequest {
//    // 用户过滤
//    /**
//     * // 用户姓名
//     */
//    private String user_name;
//    /**
//     * // 用户手机号
//     */
//    private String user_phone;
//    /**
//     * // 用户身份证号码
//     */
//    private String user_idcard;
//
//    // 推送用户基础信息
//    /**
//     * 用户订单信息
//     */
//    private OrderInfo order_info;
//    /**
//     * 用户基础信息
//     */
//    private UserInfo user_info;
//    /**
//     * // 用户认证信息
//     */
//    private UserVerify user_verify;
//
//    // 推送用户补充信息
//    /**
//     * // 用户补充信息
//     */
//    private UserAdditional user_additional;
//
//    // 订单绑卡接口
//    /**
//     * // 借款订单唯一编号
//     */
//    private String order_sn;
//    /**
//     * // 绑卡银行编码
//     */
//    private String bank_code;
//    /**
//     * // 银行卡号
//     */
//    private String card_number;
//    /**
//     * // 银行预留手机号
//     */
//    private String card_phone;
//    /**
//     * 绑卡验证码，当已发送验证码之后再次请求提交
//     */
//    private String verify_code;
//
//    // 拉取订单状态
//    /**
//     * 操作类型; 1: 拉取订单审批结果，2：拉取订单签约状态，3：拉取订单放款状态
//     */
//    private Integer act_type;
//
//    // 借款试算接口
//    /**
//     * // 审批金额 单位（分）
//     */
//    private Integer loan_amount;
//
//    // H5认证状态查询接口
//    /**
//     * // 认证类型 1 芝麻认证，2 运营商认证, 3 信用卡认证，4 公积金认证，5 社保认证，6 人行征信认证，7 信用卡账单认证 8 用款确认
//     */
//    private String auth_type;
//
//    public String getUser_name() {
//        return user_name;
//    }
//
//    public void setUser_name(String user_name) {
//        this.user_name = user_name;
//    }
//
//    public String getUser_phone() {
//        return user_phone;
//    }
//
//    public void setUser_phone(String user_phone) {
//        this.user_phone = user_phone;
//    }
//
//    public String getUser_idcard() {
//        return user_idcard;
//    }
//
//    public void setUser_idcard(String user_idcard) {
//        this.user_idcard = user_idcard;
//    }
//
//    public OrderInfo getOrder_info() {
//        return order_info;
//    }
//
//    public void setOrder_info(OrderInfo order_info) {
//        this.order_info = order_info;
//    }
//
//    public UserInfo getUser_info() {
//        return user_info;
//    }
//
//    public void setUser_info(UserInfo user_info) {
//        this.user_info = user_info;
//    }
//
//    public UserVerify getUser_verify() {
//        return user_verify;
//    }
//
//    public void setUser_verify(UserVerify user_verify) {
//        this.user_verify = user_verify;
//    }
//
//    public UserAdditional getUser_additional() {
//        return user_additional;
//    }
//
//    public void setUser_additional(UserAdditional user_additional) {
//        this.user_additional = user_additional;
//    }
//
//    public String getOrder_sn() {
//        return order_sn;
//    }
//
//    public void setOrder_sn(String order_sn) {
//        this.order_sn = order_sn;
//    }
//
//    public String getBank_code() {
//        return bank_code;
//    }
//
//    public void setBank_code(String bank_code) {
//        this.bank_code = bank_code;
//    }
//
//    public String getCard_number() {
//        return card_number;
//    }
//
//    public void setCard_number(String card_number) {
//        this.card_number = card_number;
//    }
//
//    public String getCard_phone() {
//        return card_phone;
//    }
//
//    public void setCard_phone(String card_phone) {
//        this.card_phone = card_phone;
//    }
//
//    public String getVerify_code() {
//        return verify_code;
//    }
//
//    public void setVerify_code(String verify_code) {
//        this.verify_code = verify_code;
//    }
//
//    public Integer getAct_type() {
//        return act_type;
//    }
//
//    public void setAct_type(Integer act_type) {
//        this.act_type = act_type;
//    }
//
//    public Integer getLoan_amount() {
//        return loan_amount;
//    }
//
//    public void setLoan_amount(Integer loan_amount) {
//        this.loan_amount = loan_amount;
//    }
//
//    public String getAuth_type() {
//        return auth_type;
//    }
//
//    public void setAuth_type(String auth_type) {
//        this.auth_type = auth_type;
//    }
//}
