// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kaniu_behavior")
// public class BwKaniuBehavior implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Long orderId;//
// private String userknUserReggap;// 注册时长
// private String userknAvgperdayOpencardniuCnt;// 日均打开卡牛次数
// private String userknClickLoanproductFirsttime;// 点击【贷款】最早时间
// private String userknClickLoanproductLasttime;// 点击【贷款】最后时间
// private String userknFirstOpencardniuTime;// 首次打开卡牛时间
// private String userkn6mOpencardniuDays;// 最近半年内打开卡牛天数
// private String userkn30dOpencardniuDays;// 30天内打开卡牛天数
// private String userkn60dOpencardniuDays;// 60天内打开卡牛天数
// private String userknActionEnterapplyloanpageFirsttimegap;// 第一次打开贷款中心距离首次打开卡牛时长
// private String userknActionOpencardniuCntFirstapplyday;// 第一次贷款当天打开卡牛次数
// private String userknDaygapImportcardOpen;// 第一导卡距离第一次打开卡牛天数
// private String userknHourgapAenterapplyloanpageOpen;// 第一次进入贷款中心距离首次打开卡牛小时数
// private String userknHourgapImportcardOpen;// 第一导卡距离第一次打开卡牛小时数
// private String userknImportbillcntFirstday;// 注册第一天导入账单数量
// private String userknImportbillrateFirstday;// 注册第一天导入账单占比
// private String userknImportcardcntFirstday;// 注册第一天导入卡数量
// private String userknImportcardrateFirstday;// 注册第一天导入卡占比
// private String userknRegistercnt;// 注册次数
// private String userknUdiduseridcnt;// 对应账号数
// private String userknRcCreditCardNum;// userid绑定信用卡数量（邮箱、网银、其他）
// private String userknAlipaybillNum;// userid绑定支付宝账单数量
// private String userknRcRepositCardNum;// userid绑定储蓄卡数量（邮箱、网银、其他）
// private String userknNewfirstcreditDate;// 第一次绑定信用卡日期（邮箱、网银、其他）
// private String userknNewfirstdepositDate;// 第一次绑定储蓄卡日期（邮箱、网银、其他）
// private String userknFirstcardtype;// 第一次绑卡的方式
// private String devknDeviceAtime;// 激活日期，用户当前设备对应的激活日期
// private Date createTime;//
//
// public String getUserknUdiduseridcnt() {
// return userknUdiduseridcnt;
// }
//
// public void setUserknUdiduseridcnt(String userknUdiduseridcnt) {
// this.userknUdiduseridcnt = userknUdiduseridcnt;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public Long getId() {
// return id;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// public Long getOrderId() {
// return orderId;
// }
//
// public void setUserknUserReggap(String userknUserReggap) {
// this.userknUserReggap = userknUserReggap;
// }
//
// public String getUserknUserReggap() {
// return userknUserReggap;
// }
//
// public void setUserknAvgperdayOpencardniuCnt(String userknAvgperdayOpencardniuCnt) {
// this.userknAvgperdayOpencardniuCnt = userknAvgperdayOpencardniuCnt;
// }
//
// public String getUserknAvgperdayOpencardniuCnt() {
// return userknAvgperdayOpencardniuCnt;
// }
//
// public void setUserknClickLoanproductFirsttime(String userknClickLoanproductFirsttime) {
// this.userknClickLoanproductFirsttime = userknClickLoanproductFirsttime;
// }
//
// public String getUserknClickLoanproductFirsttime() {
// return userknClickLoanproductFirsttime;
// }
//
// public void setUserknClickLoanproductLasttime(String userknClickLoanproductLasttime) {
// this.userknClickLoanproductLasttime = userknClickLoanproductLasttime;
// }
//
// public String getUserknClickLoanproductLasttime() {
// return userknClickLoanproductLasttime;
// }
//
// public void setUserknFirstOpencardniuTime(String userknFirstOpencardniuTime) {
// this.userknFirstOpencardniuTime = userknFirstOpencardniuTime;
// }
//
// public String getUserknFirstOpencardniuTime() {
// return userknFirstOpencardniuTime;
// }
//
// public void setUserkn6mOpencardniuDays(String userkn6mOpencardniuDays) {
// this.userkn6mOpencardniuDays = userkn6mOpencardniuDays;
// }
//
// public String getUserkn6mOpencardniuDays() {
// return userkn6mOpencardniuDays;
// }
//
// public void setUserkn30dOpencardniuDays(String userkn30dOpencardniuDays) {
// this.userkn30dOpencardniuDays = userkn30dOpencardniuDays;
// }
//
// public String getUserkn30dOpencardniuDays() {
// return userkn30dOpencardniuDays;
// }
//
// public void setUserkn60dOpencardniuDays(String userkn60dOpencardniuDays) {
// this.userkn60dOpencardniuDays = userkn60dOpencardniuDays;
// }
//
// public String getUserkn60dOpencardniuDays() {
// return userkn60dOpencardniuDays;
// }
//
// public void setUserknActionEnterapplyloanpageFirsttimegap(String userknActionEnterapplyloanpageFirsttimegap) {
// this.userknActionEnterapplyloanpageFirsttimegap = userknActionEnterapplyloanpageFirsttimegap;
// }
//
// public String getUserknActionEnterapplyloanpageFirsttimegap() {
// return userknActionEnterapplyloanpageFirsttimegap;
// }
//
// public void setUserknActionOpencardniuCntFirstapplyday(String userknActionOpencardniuCntFirstapplyday) {
// this.userknActionOpencardniuCntFirstapplyday = userknActionOpencardniuCntFirstapplyday;
// }
//
// public String getUserknActionOpencardniuCntFirstapplyday() {
// return userknActionOpencardniuCntFirstapplyday;
// }
//
// public void setUserknDaygapImportcardOpen(String userknDaygapImportcardOpen) {
// this.userknDaygapImportcardOpen = userknDaygapImportcardOpen;
// }
//
// public String getUserknDaygapImportcardOpen() {
// return userknDaygapImportcardOpen;
// }
//
// public void setUserknHourgapAenterapplyloanpageOpen(String userknHourgapAenterapplyloanpageOpen) {
// this.userknHourgapAenterapplyloanpageOpen = userknHourgapAenterapplyloanpageOpen;
// }
//
// public String getUserknHourgapAenterapplyloanpageOpen() {
// return userknHourgapAenterapplyloanpageOpen;
// }
//
// public void setUserknHourgapImportcardOpen(String userknHourgapImportcardOpen) {
// this.userknHourgapImportcardOpen = userknHourgapImportcardOpen;
// }
//
// public String getUserknHourgapImportcardOpen() {
// return userknHourgapImportcardOpen;
// }
//
// public void setUserknImportbillcntFirstday(String userknImportbillcntFirstday) {
// this.userknImportbillcntFirstday = userknImportbillcntFirstday;
// }
//
// public String getUserknImportbillcntFirstday() {
// return userknImportbillcntFirstday;
// }
//
// public void setUserknImportbillrateFirstday(String userknImportbillrateFirstday) {
// this.userknImportbillrateFirstday = userknImportbillrateFirstday;
// }
//
// public String getUserknImportbillrateFirstday() {
// return userknImportbillrateFirstday;
// }
//
// public void setUserknImportcardcntFirstday(String userknImportcardcntFirstday) {
// this.userknImportcardcntFirstday = userknImportcardcntFirstday;
// }
//
// public String getUserknImportcardcntFirstday() {
// return userknImportcardcntFirstday;
// }
//
// public void setUserknImportcardrateFirstday(String userknImportcardrateFirstday) {
// this.userknImportcardrateFirstday = userknImportcardrateFirstday;
// }
//
// public String getUserknImportcardrateFirstday() {
// return userknImportcardrateFirstday;
// }
//
// public void setUserknRegistercnt(String userknRegistercnt) {
// this.userknRegistercnt = userknRegistercnt;
// }
//
// public String getUserknRegistercnt() {
// return userknRegistercnt;
// }
//
// public void setUserknRcCreditCardNum(String userknRcCreditCardNum) {
// this.userknRcCreditCardNum = userknRcCreditCardNum;
// }
//
// public String getUserknRcCreditCardNum() {
// return userknRcCreditCardNum;
// }
//
// public void setUserknAlipaybillNum(String userknAlipaybillNum) {
// this.userknAlipaybillNum = userknAlipaybillNum;
// }
//
// public String getUserknAlipaybillNum() {
// return userknAlipaybillNum;
// }
//
// public void setUserknRcRepositCardNum(String userknRcRepositCardNum) {
// this.userknRcRepositCardNum = userknRcRepositCardNum;
// }
//
// public String getUserknRcRepositCardNum() {
// return userknRcRepositCardNum;
// }
//
// public void setUserknNewfirstcreditDate(String userknNewfirstcreditDate) {
// this.userknNewfirstcreditDate = userknNewfirstcreditDate;
// }
//
// public String getUserknNewfirstcreditDate() {
// return userknNewfirstcreditDate;
// }
//
// public void setUserknNewfirstdepositDate(String userknNewfirstdepositDate) {
// this.userknNewfirstdepositDate = userknNewfirstdepositDate;
// }
//
// public String getUserknNewfirstdepositDate() {
// return userknNewfirstdepositDate;
// }
//
// public void setUserknFirstcardtype(String userknFirstcardtype) {
// this.userknFirstcardtype = userknFirstcardtype;
// }
//
// public String getUserknFirstcardtype() {
// return userknFirstcardtype;
// }
//
// public void setDevknDeviceAtime(String devknDeviceAtime) {
// this.devknDeviceAtime = devknDeviceAtime;
// }
//
// public String getDevknDeviceAtime() {
// return devknDeviceAtime;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
// }
