//package com.waterelephant.sxyDrainage.entity.wacai;
//
//import com.waterelephant.sxyDrainage.entity.wacai.wcorder.WaCaiExtData;
//import com.waterelephant.sxyDrainage.entity.wacai.wcorder.WaCaiRealName;
//import com.waterelephant.sxyDrainage.entity.wacai.wcorder.WaCaiUserInfo;
//
//import java.util.List;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/28
// * @since JDK 1.8
// */
//public class WaCaiCommonReq {
//
//    /** OFA 用户标识 */
//    private String openId;
//    /** 申请单编号 */
//    private String orderId;
//
//    /** 用户手机号 */
//    private String mobile;
//    /** 身份证号码 */
//    private String idCard;
//    /** 用户姓名 */
//    private String name;
//
//    /** 实名信息 */
//    private WaCaiRealName realName;
//    /** 用户信息 */
//    private WaCaiUserInfo userInfo;
//    /** 期望金额 */
//    private String expectMoney;
//    /** 期望期数 */
//    private String expectNumber;
//    /**借款用途*/
//    private WaCaiExtData extData;
//
//    /** 绑卡 */
//    private WaCaBankCard bankCard;
//    /** 短信验证码 */
//    private String smsCode;
//
//    /** 授信结果拉取接口:产品标识 */
//    private String productId;
//
//    /** 申请金额 ，单位 分 */
//    private Long loanAmount;
//    /** 试算接口:申请期数 */
//    private List<Integer> period;
//
//    /** 合同类型 1 所有合同的集成页面 2 申请数据提交页面 3 借款页面 4 绑卡页面 5 还款页面 */
//    private Integer contractPage;
//
//    /** 借款申请接口:申请期数 */
//    private Integer periodMonth;
//
//
//    public String getOpenId() {
//        return openId;
//    }
//
//    public void setOpenId(String openId) {
//        this.openId = openId;
//    }
//
//    public String getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(String orderId) {
//        this.orderId = orderId;
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
//    public String getIdCard() {
//        return idCard;
//    }
//
//    public void setIdCard(String idCard) {
//        this.idCard = idCard;
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
//    public WaCaiRealName getRealName() {
//        return realName;
//    }
//
//    public void setRealName(WaCaiRealName realName) {
//        this.realName = realName;
//    }
//
//    public WaCaiUserInfo getUserInfo() {
//        return userInfo;
//    }
//
//    public void setUserInfo(WaCaiUserInfo userInfo) {
//        this.userInfo = userInfo;
//    }
//
//    public String getExpectMoney() {
//        return expectMoney;
//    }
//
//    public void setExpectMoney(String expectMoney) {
//        this.expectMoney = expectMoney;
//    }
//
//    public String getExpectNumber() {
//        return expectNumber;
//    }
//
//    public void setExpectNumber(String expectNumber) {
//        this.expectNumber = expectNumber;
//    }
//
//    public WaCaiExtData getExtData() {
//        return extData;
//    }
//
//    public void setExtData(WaCaiExtData extData) {
//        this.extData = extData;
//    }
//
//    public WaCaBankCard getBankCard() {
//        return bankCard;
//    }
//
//    public void setBankCard(WaCaBankCard bankCard) {
//        this.bankCard = bankCard;
//    }
//
//    public String getSmsCode() {
//        return smsCode;
//    }
//
//    public void setSmsCode(String smsCode) {
//        this.smsCode = smsCode;
//    }
//
//    public String getProductId() {
//        return productId;
//    }
//
//    public void setProductId(String productId) {
//        this.productId = productId;
//    }
//
//    public Long getLoanAmount() {
//        return loanAmount;
//    }
//
//    public void setLoanAmount(Long loanAmount) {
//        this.loanAmount = loanAmount;
//    }
//
//    public List<Integer> getPeriod() {
//        return period;
//    }
//
//    public void setPeriod(List<Integer> period) {
//        this.period = period;
//    }
//
//    public Integer getContractPage() {
//        return contractPage;
//    }
//
//    public void setContractPage(Integer contractPage) {
//        this.contractPage = contractPage;
//    }
//
//    public Integer getPeriodMonth() {
//        return periodMonth;
//    }
//
//    public void setPeriodMonth(Integer periodMonth) {
//        this.periodMonth = periodMonth;
//    }
//}
