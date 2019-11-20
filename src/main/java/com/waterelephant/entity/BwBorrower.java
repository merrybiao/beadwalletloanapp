package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @ClassName: BwBorrower
 * @Description: TODO(借款人实体类)
 * @author duxiaoyong
 * @date 2016年9月2日 上午10:24:30
 *
 */
@Table(name = "bw_borrower")
public class BwBorrower implements Serializable {

    private static final long serialVersionUID = 8987699287832151875L;

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 富友账号
     */
    private String fuiouAcct;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 邀请码
     */
    private String inviteCode;
    /**
     * 邀请人
     */
    private Long borrowerId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 手机序列
     */
    private String mobileSeq;

    /**
     * 删除标志 0:已删除 1:未删除
     */
    private Integer flag;
    /**
     * 认证状态 1：工作认证 2：工作认证 3：社保认证 4：已认证
     */
    private Integer authStep;
    /**
     * 借款人状态 0：禁用 1：启用
     */
    private Integer state;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后更新时间
     */
    private Date updateTime;
    /**
     * 注册地址
     */
    private String registerAddr;

    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别 1、男 0、女
     */
    private Integer sex;

    /**
     * 注册渠道
     */
    private Integer channel;
    /**
     * 用户app类型(1-水象分期，2-77钱包，3-乐分期)
     */
    private Integer appId;

    /**
     * 借款人头像url
     */
    // private String headImgUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuiouAcct() {
        return fuiouAcct;
    }

    public void setFuiouAcct(String fuiouAcct) {
        this.fuiouAcct = fuiouAcct;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long borrowerId) {
        this.borrowerId = borrowerId;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getAuthStep() {
        return authStep;
    }

    public void setAuthStep(Integer authStep) {
        this.authStep = authStep;
    }

    public String getMobileSeq() {
        return mobileSeq;
    }

    public void setMobileSeq(String mobileSeq) {
        this.mobileSeq = mobileSeq;
    }

    public String getRegisterAddr() {
        return registerAddr;
    }

    public void setRegisterAddr(String registerAddr) {
        this.registerAddr = registerAddr;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BwBorrower[");
        builder.append(" [id] = ");
        builder.append(id);
        builder.append(" 富有账号[fuiouAcct] = ");
        builder.append(fuiouAcct);
        builder.append(" 手机号[phone] = ");
        builder.append(phone);
        builder.append(" 密码[password] = ");
        builder.append(password);
        builder.append(" 邀请码[inviteCode] = ");
        builder.append(inviteCode);
        builder.append(" 邀请人[borrowerId] = ");
        builder.append(borrowerId);
        builder.append(" 真实姓名[name] = ");
        builder.append(name);
        builder.append(" 身份证[idCard] = ");
        builder.append(idCard);
        builder.append(" 借款人状态 0:禁用 1:启用[state] = ");
        builder.append(state);
        builder.append(" 认证步骤 1:个人信息认证 2:工作信息认证 3:图片上传认证 4:已认证 5:运营商认证[authStep] = ");
        builder.append(authStep);
        builder.append(" 删除标志 0:已删除 1:未删除[flag] = ");
        builder.append(flag);
        builder.append(" [createTime] = ");
        builder.append(createTime);
        builder.append(" [updateTime] = ");
        builder.append(updateTime);
        builder.append(" [mobileSeq] = ");
        builder.append(mobileSeq);
        builder.append(" 注册地址[registerAddr] = ");
        builder.append(registerAddr);
        builder.append(" 年龄[age] = ");
        builder.append(age);
        builder.append(" 性别：1：男。0：女[sex] = ");
        builder.append(sex);
        builder.append(" 注册渠道:1.ios 2.app 3.微信 4.融360[channel] = ");
        builder.append(channel);
        builder.append("]");
        return builder.toString();
    }

}
