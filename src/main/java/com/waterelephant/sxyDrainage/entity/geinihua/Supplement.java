//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import org.hibernate.validator.constraints.NotBlank;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//
///**
// * 补充信息
// *
// * @author xanthuim
// */
//
//public class Supplement {
//    /**
//     * 订单基本信息 包含了订单号等
//     */
//    @Valid
//    private OrderInfo orderInfo;
//    /**
//     * 模板信息（包含关联人信息（紧急联系人、直系亲属）、身份验证信息（身份证照片、活体验证）、公司信息等。） 根据具体商户定制的信息
//     */
//    @Valid
//    private TemplateInfo templateInfo;
//    /**
//     * 设备信息
//     */
//    private DeviceInfo deviceInfo;
//
//    public OrderInfo getOrderInfo() {
//        return orderInfo;
//    }
//
//    public void setOrderInfo(OrderInfo orderInfo) {
//        this.orderInfo = orderInfo;
//    }
//
//    public TemplateInfo getTemplateInfo() {
//        return templateInfo;
//    }
//
//    public void setTemplateInfo(TemplateInfo templateInfo) {
//        this.templateInfo = templateInfo;
//    }
//
//    public DeviceInfo getDeviceInfo() {
//        return deviceInfo;
//    }
//
//    public void setDeviceInfo(DeviceInfo deviceInfo) {
//        this.deviceInfo = deviceInfo;
//    }
//
//    public static class OrderInfo {
//        /**
//         * 订单编号 string 否 该订单号为北斗生成，机构方需要保存
//         */
//        @NotBlank(message = "orderNo不能为空")
//        private String orderNo;
//        /**
//         * 申请金额 string 否 申请金额为元
//         */
//        @NotNull(message = "loanAmount不能为空")
//        private Double loanAmount;
//        /**
//         * 申请期限 int 否
//         */
//        @NotNull(message = "loanTerm不能为空")
//        private Integer loanTerm;
//        /**
//         * 期限单位 int 否 期限单位：1天2月，默认为1天
//         */
//        @NotNull(message = "loanUnit不能为空")
//        private Integer loanUnit;
//
//        public String getOrderNo() {
//            return orderNo;
//        }
//
//        public void setOrderNo(String orderNo) {
//            this.orderNo = orderNo;
//        }
//
//        @NotNull
//        public Double getLoanAmount() {
//            return loanAmount;
//        }
//
//        public void setLoanAmount(@NotNull Double loanAmount) {
//            this.loanAmount = loanAmount;
//        }
//
//        @NotNull
//        public Integer getLoanTerm() {
//            return loanTerm;
//        }
//
//        public void setLoanTerm(@NotNull Integer loanTerm) {
//            this.loanTerm = loanTerm;
//        }
//
//        @NotNull
//        public Integer getLoanUnit() {
//            return loanUnit;
//        }
//
//        public void setLoanUnit(@NotNull Integer loanUnit) {
//            this.loanUnit = loanUnit;
//        }
//    }
//
//}
