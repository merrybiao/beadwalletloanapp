//package com.waterelephant.sxyDrainage.entity.fqgj;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * @author wangfei
// * @version 1.0
// * @date 2018/5/29
// * @Description <TODO>
// * @since JDK 1.8
// */
//
//@Table(name = "bw_fqgj_operate_basic")
//public class FqgjOperateBasic  implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;// 主键ID
//    private Long orderId;// 订单Id
//    private String addr;//注册号码所填地址
//    private String userSource;//号码类型（移动，联通，电信）
//    private String idCard;//身份证号
//    private String realName;//用户姓名
//    private String phoneRemain;//当前账户余额
//    private String phone;//电话号码
//    private String regTime;//入网时间
//    private String score;//用户积分
//    private String contactPhone;//联系人号码
//    private String starLevel;//用户星级
//    private String authentication;//用户实名状态
//    private String phoneStatus;//客户状态
//    private String packageName;//套餐名称
//    private Date createTime;//创建时间
//    private Date updateTime;//更新时间
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getAddr() {
//        return addr;
//    }
//
//    public void setAddr(String addr) {
//        this.addr = addr;
//    }
//
//    public Long getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(Long orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getUserSource() {
//        return userSource;
//    }
//
//    public void setUserSource(String userSource) {
//        this.userSource = userSource;
//    }
//
//    public String getIdCard() {
//        return idCard;
//    }
//
//    public void setIdCard(String idCard) {
//        this.idCard = idCard;
//    }
//
//    public String getRealName() {
//        return realName;
//    }
//
//    public void setRealName(String realName) {
//        this.realName = realName;
//    }
//
//    public String getPhoneRemain() {
//        return phoneRemain;
//    }
//
//    public void setPhoneRemain(String phoneRemain) {
//        this.phoneRemain = phoneRemain;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getRegTime() {
//        return regTime;
//    }
//
//    public void setRegTime(String regTime) {
//        this.regTime = regTime;
//    }
//
//    public String getScore() {
//        return score;
//    }
//
//    public void setScore(String score) {
//        this.score = score;
//    }
//
//    public String getContactPhone() {
//        return contactPhone;
//    }
//
//    public void setContactPhone(String contactPhone) {
//        this.contactPhone = contactPhone;
//    }
//
//    public String getStarLevel() {
//        return starLevel;
//    }
//
//    public void setStarLevel(String starLevel) {
//        this.starLevel = starLevel;
//    }
//
//    public String getAuthentication() {
//        return authentication;
//    }
//
//    public void setAuthentication(String authentication) {
//        this.authentication = authentication;
//    }
//
//    public String getPhoneStatus() {
//        return phoneStatus;
//    }
//
//    public void setPhoneStatus(String phoneStatus) {
//        this.phoneStatus = phoneStatus;
//    }
//
//    public String getPackageName() {
//        return packageName;
//    }
//
//    public void setPackageName(String packageName) {
//        this.packageName = packageName;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }
//}
