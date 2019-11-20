//package com.waterelephant.sxyDrainage.entity.jieba;
//
//
//import java.util.List;
//
///**
// * (code:jb001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 18:14 2018/6/13
// * @Modified By:
// */
//public class Mobile {
//    /**
//     * 号码类型（China_MOBILE(移动)，CHINA_UNICOM(联通)，China _TELECOM(电信)）
//     */
//    private String carrier;
//    /**
//     * 身份证号（联通，移动部分省份，电信暂不支持）
//     */
//    private String idcard;
//    /**
//     * 本机号码归属省份
//     */
//    private String province;
//    /**
//     * 本机号码归属城市
//     */
//    private String city;
//    /**
//     * 用户姓名（为该号码所注册的实名用户姓名）
//     */
//    private String name;
//    /**
//     * 当前账户余额（分）
//     */
//    private Integer available_balance;
//    /**
//     * 电话号码
//     */
//    private String mobile;
//    /**
//     * 入网时间（手机号码在运营商的实名认证时间）格式 “yyyy-MM-dd”
//     */
//    private String open_time;
//    /**
//     * 用户星级
//     */
//    private String level;
//    /**
//     * 套餐名称
//     */
//    private String package_name;
//    /**
//     * 本机号码状态
//     */
//    private Integer state;
//    /**
//     * 数据获取时间，格式:yyyy-MM-dd HH:mm:ss
//     */
//    private String last_modify_time;
//    /**
//     * 状态描述
//     */
//    private String message;
//    /**
//     * 通话记录
//     */
//    private List<Calls> calls;
//    /**
//     * 短信信息
//     */
//    private List<Smses> smses;
//
//    /**
//     * 月账单记录
//     */
//    private List<Bills> bills;
//    /**
//     * 流量值记录
//     */
//    private List<Nets> nets;
//    /**
//     * 充值记录
//     */
//    private List<Recharges> recharges;
//    /**
//     * 通讯录
//     */
//    private Families families;
//
//
//    public String getCarrier() {
//        return carrier;
//    }
//
//    public void setCarrier(String carrier) {
//        this.carrier = carrier;
//    }
//
//    public String getIdcard() {
//        return idcard;
//    }
//
//    public void setIdcard(String idcard) {
//        this.idcard = idcard;
//    }
//
//    public String getProvince() {
//        return province;
//    }
//
//    public void setProvince(String province) {
//        this.province = province;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getAvailable_balance() {
//        return available_balance;
//    }
//
//    public void setAvailable_balance(Integer available_balance) {
//        this.available_balance = available_balance;
//    }
//
//    public String getMobile() {
//        return mobile;
//    }
//
//    public void setMobile(String mobile) {
//        this.mobile = mobile;
//    }
//
//    public String getOpen_time() {
//        return open_time;
//    }
//
//    public void setOpen_time(String open_time) {
//        this.open_time = open_time;
//    }
//
//    public String getLevel() {
//        return level;
//    }
//
//    public void setLevel(String level) {
//        this.level = level;
//    }
//
//    public String getPackage_name() {
//        return package_name;
//    }
//
//    public void setPackage_name(String package_name) {
//        this.package_name = package_name;
//    }
//
//    public Integer getState() {
//        return state;
//    }
//
//    public void setState(Integer state) {
//        this.state = state;
//    }
//
//    public String getLast_modify_time() {
//        return last_modify_time;
//    }
//
//    public void setLast_modify_time(String last_modify_time) {
//        this.last_modify_time = last_modify_time;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public List<Calls> getCalls() {
//        return calls;
//    }
//
//    public void setCalls(List<Calls> calls) {
//        this.calls = calls;
//    }
//
//    public List<Smses> getSmses() {
//        return smses;
//    }
//
//    public void setSmses(List<Smses> smses) {
//        this.smses = smses;
//    }
//
//    public List<Bills> getBills() {
//        return bills;
//    }
//
//    public void setBills(List<Bills> bills) {
//        this.bills = bills;
//    }
//
//    public List<Nets> getNets() {
//        return nets;
//    }
//
//    public void setNets(List<Nets> nets) {
//        this.nets = nets;
//    }
//
//    public List<Recharges> getRecharges() {
//        return recharges;
//    }
//
//    public void setRecharges(List<Recharges> recharges) {
//        this.recharges = recharges;
//    }
//
//    public Families getFamilies() {
//        return families;
//    }
//
//    public void setFamilies(Families families) {
//        this.families = families;
//    }
//}
