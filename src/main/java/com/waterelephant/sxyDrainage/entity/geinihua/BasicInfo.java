///**
// *
// */
//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import org.hibernate.validator.constraints.Length;
//import org.hibernate.validator.constraints.NotBlank;
//
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//
///**
// * 基本信息
// *
// * @author xanthuim@gmail.com - 黄校
// * @date 2018年5月10日 下午7:37:41
// * @description
// */
//
//public class BasicInfo {
//    /**
//     * 申请人的基本信息
//     */
//    @Valid
//    @NotNull(message = "userDetail不能为空")
//    private UserDetail userDetail;
//    /**
//     * 订单基本信息
//     */
//    @Valid
//    @NotNull(message = "orderInfo不能为空")
//    private OrderInfo orderInfo;
//    /**
//     * 附加数据
//     */
//    @Valid
//    @NotNull(message = "addInfo不能为空")
//    private AddInfo addInfo;
//
//    public static class UserDetail {
//        /**
//         * 用户姓名 string 否
//         */
//        @NotBlank(message = "userName不能为空")
//        private String userName;
//        /**
//         * 用户身份证号 string 否
//         */
//        @Length(message = "idCard的最大长度是{max}位", max = 18)
//        @NotBlank(message = "idCard不能为空")
//        private String idCard;
//        /**
//         * 用户手机号 string 否
//         */
//        @Length(message = "mobile的最大长度是{max}位", max = 11)
//        @NotBlank(message = "mobile不能为空")
//        private String mobile;
//        /**
//         * 月均收入 double 否
//         */
//        private Double monthlyIncome;
//        /**
//         * 教育程度 int 否
//         */
//        private Integer education;
//        /**
//         * GPS string 是
//         */
//        private String gpsLoaction;
//        /**
//         * IP地址 string 是
//         */
//        private String ip;
//
//        public String getUserName() {
//            return userName;
//        }
//
//        public void setUserName(String userName) {
//            this.userName = userName;
//        }
//
//        public String getIdCard() {
//            return idCard;
//        }
//
//        public void setIdCard(String idCard) {
//            this.idCard = idCard;
//        }
//
//        public String getMobile() {
//            return mobile;
//        }
//
//        public void setMobile(String mobile) {
//            this.mobile = mobile;
//        }
//
//        public Double getMonthlyIncome() {
//            return monthlyIncome;
//        }
//
//        public void setMonthlyIncome(Double monthlyIncome) {
//            this.monthlyIncome = monthlyIncome;
//        }
//
//        public Integer getEducation() {
//            return education;
//        }
//
//        public void setEducation(Integer education) {
//            this.education = education;
//        }
//
//        public String getGpsLoaction() {
//            return gpsLoaction;
//        }
//
//        public void setGpsLoaction(String gpsLoaction) {
//            this.gpsLoaction = gpsLoaction;
//        }
//
//        public String getIp() {
//            return ip;
//        }
//
//        public void setIp(String ip) {
//            this.ip = ip;
//        }
//    }
//
//    /**
//     * 附加信息
//     *
//     * @author xanthuim
//     */
//    public static class AddInfo {
//
//        @Valid
//        @NotNull(message = "operator不能为空")
//        private Operator operator;
//
//        @NotNull
//        public Operator getOperator() {
//            return operator;
//        }
//
//        public void setOperator(@NotNull Operator operator) {
//            this.operator = operator;
//        }
//    }
//
//    @NotNull
//    public UserDetail getUserDetail() {
//        return userDetail;
//    }
//
//    public void setUserDetail(@NotNull UserDetail userDetail) {
//        this.userDetail = userDetail;
//    }
//
//    @NotNull
//    public OrderInfo getOrderInfo() {
//        return orderInfo;
//    }
//
//    public void setOrderInfo(@NotNull OrderInfo orderInfo) {
//        this.orderInfo = orderInfo;
//    }
//
//    @NotNull
//    public AddInfo getAddInfo() {
//        return addInfo;
//    }
//
//    public void setAddInfo(@NotNull AddInfo addInfo) {
//        this.addInfo = addInfo;
//    }
//}
