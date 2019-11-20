//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import org.hibernate.validator.constraints.NotBlank;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
//import javax.persistence.Column;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
///**
// * base				用户基本信息	JSON	否	格式参考表2
// * call				通话详单		MAP	否	格式参考表3
// * gprs				月流量使用情况	MAP	否	格式参考表4
// * bill				账单详单		MAP	否	格式参考表5
// * sms					短信详单		MAP	否	格式参考表6
// * familiarityNumbers	亲情网		List	否	格式参考表7
// * recharges			充值信息		MAP	否	格式参考表8
// *
// * @author xanthuim
// */
//
//public class Operator {
//
//    @Valid
//    @Size(message = "call至少{min}条", min = 1)
//    @NotNull
//    private Map<String, List<Call>> call;
//
//    private Map<String, List<Recharge>> recharges;
//
//    private Map<String, List<Sms>> sms;
//
//    private Map<String, Bill> bill;
//
//    private List<FamiliarityNumbers> familiarityNumbers;
//
//    private Map<String, Gprs> gprs;
//
//    @Valid
//    @NotNull
//    private Base base;
//
//    @NotNull
//    public Map<String, List<Call>> getCall() {
//        return call;
//    }
//
//    public void setCall(@NotNull Map<String, List<Call>> call) {
//        this.call = call;
//    }
//
//    public Map<String, List<Recharge>> getRecharges() {
//        return recharges;
//    }
//
//    public void setRecharges(Map<String, List<Recharge>> recharges) {
//        this.recharges = recharges;
//    }
//
//    public Map<String, List<Sms>> getSms() {
//        return sms;
//    }
//
//    public void setSms(Map<String, List<Sms>> sms) {
//        this.sms = sms;
//    }
//
//    public Map<String, Bill> getBill() {
//        return bill;
//    }
//
//    public void setBill(Map<String, Bill> bill) {
//        this.bill = bill;
//    }
//
//    public List<FamiliarityNumbers> getFamiliarityNumbers() {
//        return familiarityNumbers;
//    }
//
//    public void setFamiliarityNumbers(List<FamiliarityNumbers> familiarityNumbers) {
//        this.familiarityNumbers = familiarityNumbers;
//    }
//
//    public Map<String, Gprs> getGprs() {
//        return gprs;
//    }
//
//    public void setGprs(Map<String, Gprs> gprs) {
//        this.gprs = gprs;
//    }
//
//    @NotNull
//    public Base getBase() {
//        return base;
//    }
//
//    public void setBase(@NotNull Base base) {
//        this.base = base;
//    }
//
//    @Table(name = "bw_gnh_gprs")
//    public static class Gprs {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//        private Long id;
//
//        private Date gmtCreate;
//
//        private Date gmtModified;
//
//        private Long orderId;
//
//        private String total_flow;
//
//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public Date getGmtCreate() {
//            return gmtCreate;
//        }
//
//        public void setGmtCreate(Date gmtCreate) {
//            this.gmtCreate = gmtCreate;
//        }
//
//        public Date getGmtModified() {
//            return gmtModified;
//        }
//
//        public void setGmtModified(Date gmtModified) {
//            this.gmtModified = gmtModified;
//        }
//
//        public Long getOrderId() {
//            return orderId;
//        }
//
//        public void setOrderId(Long orderId) {
//            this.orderId = orderId;
//        }
//
//        public String getTotal_flow() {
//            return total_flow;
//        }
//
//        public void setTotal_flow(String total_flow) {
//            this.total_flow = total_flow;
//        }
//    }
//
//    @Table(name = "bw_gnh_bill")
//    public static class Bill {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//        private Long id;
//
//        private Date gmtCreate;
//
//        private Date gmtModified;
//
//        private Long orderId;
//
//        /**
//         * 当月帐单总额 string
//         */
//        private String total_fee;
//        /**
//         * 规定套餐费 string
//         */
//        private String base_fee;
//        /**
//         * 流量费用 string
//         */
//        private String gprs_fee;
//        /**
//         * 通话费用 string
//         */
//        private String call_fee;
//        /**
//         * 短信费用 string
//         */
//        private String sms_fee;
//
//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public Date getGmtCreate() {
//            return gmtCreate;
//        }
//
//        public void setGmtCreate(Date gmtCreate) {
//            this.gmtCreate = gmtCreate;
//        }
//
//        public Date getGmtModified() {
//            return gmtModified;
//        }
//
//        public void setGmtModified(Date gmtModified) {
//            this.gmtModified = gmtModified;
//        }
//
//        public Long getOrderId() {
//            return orderId;
//        }
//
//        public void setOrderId(Long orderId) {
//            this.orderId = orderId;
//        }
//
//        public String getTotal_fee() {
//            return total_fee;
//        }
//
//        public void setTotal_fee(String total_fee) {
//            this.total_fee = total_fee;
//        }
//
//        public String getBase_fee() {
//            return base_fee;
//        }
//
//        public void setBase_fee(String base_fee) {
//            this.base_fee = base_fee;
//        }
//
//        public String getGprs_fee() {
//            return gprs_fee;
//        }
//
//        public void setGprs_fee(String gprs_fee) {
//            this.gprs_fee = gprs_fee;
//        }
//
//        public String getCall_fee() {
//            return call_fee;
//        }
//
//        public void setCall_fee(String call_fee) {
//            this.call_fee = call_fee;
//        }
//
//        public String getSms_fee() {
//            return sms_fee;
//        }
//
//        public void setSms_fee(String sms_fee) {
//            this.sms_fee = sms_fee;
//        }
//    }
//
//    public static class Call {
//        /**
//         * 通话时间 date 否
//         */
//        @NotNull(message = "calltime不能为空")
//        private Date calltime;
//        /**
//         * 对方号码 string 否
//         */
//        @NotBlank(message = "callphone不能为空")
//        @Column(name = "call_phone")
//        private String callphone;
//        /**
//         * 呼叫类型 string 否	语音电话（固定）
//         */
//        @NotBlank(message = "thtypename不能为空")
//        @Column(name = "thtype_name")
//        private String thtypename;
//        /**
//         * 通话时长 string 否 单位秒
//         */
//        @NotBlank(message = "calllong不能为空")
//        @Column(name = "call_long")
//        private String calllong;
//        /**
//         * 主叫被叫 string 否 主叫、被叫
//         */
//        @NotBlank(message = "calltype不能为空")
//        @Column(name = "call_type")
//        private String calltype;
//        /**
//         * 本地长途 sring 否 包括但不限于：地域类型、本地、长途、国内通话、国际通话
//         */
//        @NotBlank(message = "landtype不能为空")
//        @Column(name = "land_type")
//        private String landtype;
//        /**
//         * 通话时本号所在地 string 否
//         */
//        @Column(name = "home_area")
//        private String homearea;
//        /**
//         * 对方号码所在地 string 否
//         */
//        @Column(name = "other_area")
//        private String otherarea;
//
//        @NotNull
//        public Date getCalltime() {
//            return calltime;
//        }
//
//        public void setCalltime(@NotNull Date calltime) {
//            this.calltime = calltime;
//        }
//
//        public String getCallphone() {
//            return callphone;
//        }
//
//        public void setCallphone(String callphone) {
//            this.callphone = callphone;
//        }
//
//        public String getThtypename() {
//            return thtypename;
//        }
//
//        public void setThtypename(String thtypename) {
//            this.thtypename = thtypename;
//        }
//
//        public String getCalllong() {
//            return calllong;
//        }
//
//        public void setCalllong(String calllong) {
//            this.calllong = calllong;
//        }
//
//        public String getCalltype() {
//            return calltype;
//        }
//
//        public void setCalltype(String calltype) {
//            this.calltype = calltype;
//        }
//
//        public String getLandtype() {
//            return landtype;
//        }
//
//        public void setLandtype(String landtype) {
//            this.landtype = landtype;
//        }
//
//        public String getHomearea() {
//            return homearea;
//        }
//
//        public void setHomearea(String homearea) {
//            this.homearea = homearea;
//        }
//
//        public String getOtherarea() {
//            return otherarea;
//        }
//
//        public void setOtherarea(String otherarea) {
//            this.otherarea = otherarea;
//        }
//    }
//
//    public static class Base {
//        /**
//         * 运营商登记姓名
//         */
//        @NotBlank(message = "truename不能为空")
//        private String truename;
//        /**
//         * 身份证地址
//         */
//        private String address;
//        /**
//         * 认证时间
//         */
//        @NotNull(message = "create_time不能为空")
//        private Date create_time;
//        /**
//         * 手机号归属城市
//         */
//        private String city;
//        /**
//         * 是否实名
//         */
//        private String certify;
//        /**
//         * 认证渠道
//         */
//        private String channel;
//        /**
//         * 运营商登记身份证
//         */
//        private String id_card;
//        /**
//         * 手机号
//         */
//        @NotBlank(message = "mobile不能为空")
//        private String mobile;
//        /**
//         * 套餐名称
//         */
//        private String telPackage;
//        /**
//         * 开通时间
//         */
//        private Date open_time;
//        /**
//         * 运营商类型
//         */
//        @NotBlank(message = "type不能为空")
//        private String type;
//        /**
//         * 当前金额
//         */
//        private String balance;
//        /**
//         * 手机号归属省份
//         */
//        @NotBlank(message = "province不能为空")
//        private String province;
//
//        public String getTruename() {
//            return truename;
//        }
//
//        public void setTruename(String truename) {
//            this.truename = truename;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        @NotNull
//        public Date getCreate_time() {
//            return create_time;
//        }
//
//        public void setCreate_time(@NotNull Date create_time) {
//            this.create_time = create_time;
//        }
//
//        public String getCity() {
//            return city;
//        }
//
//        public void setCity(String city) {
//            this.city = city;
//        }
//
//        public String getCertify() {
//            return certify;
//        }
//
//        public void setCertify(String certify) {
//            this.certify = certify;
//        }
//
//        public String getChannel() {
//            return channel;
//        }
//
//        public void setChannel(String channel) {
//            this.channel = channel;
//        }
//
//        public String getId_card() {
//            return id_card;
//        }
//
//        public void setId_card(String id_card) {
//            this.id_card = id_card;
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
//        public String getTelPackage() {
//            return telPackage;
//        }
//
//        public void setTelPackage(String telPackage) {
//            this.telPackage = telPackage;
//        }
//
//        public Date getOpen_time() {
//            return open_time;
//        }
//
//        public void setOpen_time(Date open_time) {
//            this.open_time = open_time;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public String getBalance() {
//            return balance;
//        }
//
//        public void setBalance(String balance) {
//            this.balance = balance;
//        }
//
//        public String getProvince() {
//            return province;
//        }
//
//        public void setProvince(String province) {
//            this.province = province;
//        }
//    }
//
//    @Table(name = "bw_gnh_sms")
//    public static class Sms {
//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//        private Long id;
//
//        private Date gmtCreate;
//
//        private Date gmtModified;
//
//        private Long orderId;
//        /**
//         * 发送/接收短信时间 date
//         */
//        @Column(name = "sms_time")
//        private Date smstime;
//        /**
//         * 对方号码 string
//         */
//        @Column(name = "sms_phone")
//        private String smsphone;
//        /**
//         * 地区名 / 区号 string
//         */
//        @Column(name = "home_area")
//        private String homearea;
//        /**
//         * 短信费用 string
//         */
//        @Column(name = "sms_fee")
//        private String smsfee;
//        /**
//         * 发送 / 接收 string
//         */
//        @Column(name = "sms_type")
//        private String smstype;
//        /**
//         * 短信／彩信 string
//         */
//        @Column(name = "business_type")
//        private String businesstype;
//
//        public Long getId() {
//            return id;
//        }
//
//        public void setId(Long id) {
//            this.id = id;
//        }
//
//        public Date getGmtCreate() {
//            return gmtCreate;
//        }
//
//        public void setGmtCreate(Date gmtCreate) {
//            this.gmtCreate = gmtCreate;
//        }
//
//        public Date getGmtModified() {
//            return gmtModified;
//        }
//
//        public void setGmtModified(Date gmtModified) {
//            this.gmtModified = gmtModified;
//        }
//
//        public Long getOrderId() {
//            return orderId;
//        }
//
//        public void setOrderId(Long orderId) {
//            this.orderId = orderId;
//        }
//
//        public Date getSmstime() {
//            return smstime;
//        }
//
//        public void setSmstime(Date smstime) {
//            this.smstime = smstime;
//        }
//
//        public String getSmsphone() {
//            return smsphone;
//        }
//
//        public void setSmsphone(String smsphone) {
//            this.smsphone = smsphone;
//        }
//
//        public String getHomearea() {
//            return homearea;
//        }
//
//        public void setHomearea(String homearea) {
//            this.homearea = homearea;
//        }
//
//        public String getSmsfee() {
//            return smsfee;
//        }
//
//        public void setSmsfee(String smsfee) {
//            this.smsfee = smsfee;
//        }
//
//        public String getSmstype() {
//            return smstype;
//        }
//
//        public void setSmstype(String smstype) {
//            this.smstype = smstype;
//        }
//
//        public String getBusinesstype() {
//            return businesstype;
//        }
//
//        public void setBusinesstype(String businesstype) {
//            this.businesstype = businesstype;
//        }
//    }
//
//    public static class Recharge {
//        /**
//         * 充值时间 date
//         */
//        private Date rechargeTime;
//        /**
//         * 充值金额 double 是 单位：分
//         */
//        private Double rechargeAmount;
//        /**
//         * 充值方式 string 是
//         */
//        private String rechargeType;
//
//        public Date getRechargeTime() {
//            return rechargeTime;
//        }
//
//        public void setRechargeTime(Date rechargeTime) {
//            this.rechargeTime = rechargeTime;
//        }
//
//        public Double getRechargeAmount() {
//            return rechargeAmount;
//        }
//
//        public void setRechargeAmount(Double rechargeAmount) {
//            this.rechargeAmount = rechargeAmount;
//        }
//
//        public String getRechargeType() {
//            return rechargeType;
//        }
//
//        public void setRechargeType(String rechargeType) {
//            this.rechargeType = rechargeType;
//        }
//    }
//
//    public static class FamiliarityNumbers {
//        private Date memeberExpireDate;
//        private String memberShortNum;
//        private String memberType;
//        private String memberNum;
//        private Date memeberAddDate;
//
//        public Date getMemeberExpireDate() {
//            return memeberExpireDate;
//        }
//
//        public void setMemeberExpireDate(Date memeberExpireDate) {
//            this.memeberExpireDate = memeberExpireDate;
//        }
//
//        public String getMemberShortNum() {
//            return memberShortNum;
//        }
//
//        public void setMemberShortNum(String memberShortNum) {
//            this.memberShortNum = memberShortNum;
//        }
//
//        public String getMemberType() {
//            return memberType;
//        }
//
//        public void setMemberType(String memberType) {
//            this.memberType = memberType;
//        }
//
//        public String getMemberNum() {
//            return memberNum;
//        }
//
//        public void setMemberNum(String memberNum) {
//            this.memberNum = memberNum;
//        }
//
//        public Date getMemeberAddDate() {
//            return memeberAddDate;
//        }
//
//        public void setMemeberAddDate(Date memeberAddDate) {
//            this.memeberAddDate = memeberAddDate;
//        }
//    }
//}
