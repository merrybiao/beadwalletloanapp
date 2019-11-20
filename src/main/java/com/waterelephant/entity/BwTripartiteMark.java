// package com.waterelephant.entity;
//
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
// import java.io.Serializable;
// import java.util.Date;
//
/// **
// * @author wangfei
// * @version 1.0
// * @date 2018/5/31
// * @Description <TODO>
// * @since JDK 1.8
// */
// @Table(name = "bw_tripartite_mark")
// public class BwTripartiteMark implements Serializable {
//
// private static final long serialVersionUID = 1L;
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;// 主键id
//
// private String indexKey;// 关键字
//
// private Integer channelId;// 渠道号
//
// private Integer saveOk = 1;// 进件是否成功，默认失败
//
// private Integer ifDel = 1;// 是否删除 默认不可删除
//
// private String addr;// 存储网页地址类
//
// private Long number;// 存储数字类
//
// private Date createTime;// 创建时间
//
// private Date updateTime;// 修改时间
//
// private String remark1;// 备用字段1
//
// private String remark2;// 备用字段2
//
//
// public Long getId() {
// return id;
// }
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public String getIndexKey() {
// return indexKey;
// }
//
// public void setIndexKey(String indexKey) {
// this.indexKey = indexKey;
// }
//
// public Integer getChannelId() {
// return channelId;
// }
//
// public void setChannelId(Integer channelId) {
// this.channelId = channelId;
// }
//
// public Integer getSaveOk() {
// return saveOk;
// }
//
// public void setSaveOk(Integer saveOk) {
// this.saveOk = saveOk;
// }
//
// public Integer getIfDel() {
// return ifDel;
// }
//
// public void setIfDel(Integer ifDel) {
// this.ifDel = ifDel;
// }
//
// public String getAddr() {
// return addr;
// }
//
// public void setAddr(String addr) {
// this.addr = addr;
// }
//
// public Long getNumber() {
// return number;
// }
//
// public void setNumber(Long number) {
// this.number = number;
// }
//
// public String getRemark1() {
// return remark1;
// }
//
// public void setRemark1(String remark1) {
// this.remark1 = remark1;
// }
//
// public String getRemark2() {
// return remark2;
// }
//
// public void setRemark2(String remark2) {
// this.remark2 = remark2;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
//
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getUpdateTime() {
// return updateTime;
// }
//
// public void setUpdateTime(Date updateTime) {
// this.updateTime = updateTime;
// }
// }
