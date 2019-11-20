// package com.waterelephant.entity;
//
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
// import java.io.Serializable;
//
/// **
// * (code:jb001)
// */
// @Table(name = "bw_jb_smses")
// public class BwJbSmses implements Serializable {
//
// /** */
// private static final long serialVersionUID = 1L;
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;
// /**
// * //对方号码
// */
// private String peerNumber;
// /**
// * //详情唯一标识
// */
// private String detailsId;
// /**
// * //更新时间
// */
// private java.util.Date updateTime;
// /**
// * //业务名称
// */
// private String serivceName;
// /**
// * //创建时间
// */
// private java.util.Date createTime;
// /**
// * //通话费(单位:分)
// */
// private String fee;
// /**
// * //SEND-发送;RECEIVE-收取
// */
// private String sendTpye;
// /**
// * //SMS-短信;MMS-彩信
// */
// private String msgType;
// /**
// * //通话地
// */
// private String location;
// /**
// * //收/发短信时间
// */
// private String time;
// /**
// * //订单id
// */
// private Long orderId;
//
// public Long getId() {
// return this.id;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public String getPeerNumber() {
// return this.peerNumber;
// }
//
// public void setPeerNumber(String peerNumber) {
// this.peerNumber = peerNumber;
// }
//
// public String getDetailsId() {
// return this.detailsId;
// }
//
// public void setDetailsId(String detailsId) {
// this.detailsId = detailsId;
// }
//
// public java.util.Date getUpdateTime() {
// return this.updateTime;
// }
//
// public void setUpdateTime(java.util.Date updateTime) {
// this.updateTime = updateTime;
// }
//
// public String getSerivceName() {
// return this.serivceName;
// }
//
// public void setSerivceName(String serivceName) {
// this.serivceName = serivceName;
// }
//
// public java.util.Date getCreateTime() {
// return this.createTime;
// }
//
// public void setCreateTime(java.util.Date createTime) {
// this.createTime = createTime;
// }
//
// public String getFee() {
// return this.fee;
// }
//
// public void setFee(String fee) {
// this.fee = fee;
// }
//
// public String getSendTpye() {
// return this.sendTpye;
// }
//
// public void setSendTpye(String sendTpye) {
// this.sendTpye = sendTpye;
// }
//
// public String getMsgType() {
// return this.msgType;
// }
//
// public void setMsgType(String msgType) {
// this.msgType = msgType;
// }
//
// public String getLocation() {
// return this.location;
// }
//
// public void setLocation(String location) {
// this.location = location;
// }
//
// public String getTime() {
// return this.time;
// }
//
// public void setTime(String time) {
// this.time = time;
// }
//
// public Long getOrderId() {
// return this.orderId;
// }
//
// public void setOrderId(Long orderId) {
// this.orderId = orderId;
// }
//
// }
