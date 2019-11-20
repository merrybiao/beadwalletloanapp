// package com.waterelephant.entity;
//
// import java.io.Serializable;
// import java.util.Date;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
//
// @Table(name = "bw_kabao_operator_statistics")
// public class BwKabaoOperatorStatistics implements Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.IDENTITY)
// private Long id;//
// private Integer hasNoCallIn30d;//
// private Double nightPercent;//
// private Integer hasCallSpan;//
// private Integer totalOutCount;//
// private Integer doubleEndCallCount;//
// private Integer dayCount;//
// private Integer nightCount;//
// private Integer hasCallDays;//
// private Integer hasNoCallIn180d;//
// private Integer totalTime;//
// private Integer dawnCount;//
// private Double weekendPercent;//
// private Double weekdayPercent;//
// private Integer totalCount;//
// private Integer weekendCount;//
// private Integer totalOutTime;//
// private Integer threeMonthCount;//
// private Integer totalInTime;//
// private Integer totalInCount;//
// private Double dawnPercent;//
// private Integer oneMonthCount;//
// private Integer weekdayCount;//
// private Double dayPercent;//
// private Integer threeMonthTime;//
// private Integer oneMonthTime;//
// private Long orderId;//
// private Date createTime;//
// private Date updateTime;//
//
//
// public void setId(Long id) {
// this.id = id;
// }
//
// public Long getId() {
// return id;
// }
//
// public void setHasNoCallIn30d(Integer hasNoCallIn30d) {
// this.hasNoCallIn30d = hasNoCallIn30d;
// }
//
// public Integer getHasNoCallIn30d() {
// return hasNoCallIn30d;
// }
//
// public void setNightPercent(Double nightPercent) {
// this.nightPercent = nightPercent;
// }
//
// public Double getNightPercent() {
// return nightPercent;
// }
//
// public void setHasCallSpan(Integer hasCallSpan) {
// this.hasCallSpan = hasCallSpan;
// }
//
// public Integer getHasCallSpan() {
// return hasCallSpan;
// }
//
// public void setTotalOutCount(Integer totalOutCount) {
// this.totalOutCount = totalOutCount;
// }
//
// public Integer getTotalOutCount() {
// return totalOutCount;
// }
//
// public void setDoubleEndCallCount(Integer doubleEndCallCount) {
// this.doubleEndCallCount = doubleEndCallCount;
// }
//
// public Integer getDoubleEndCallCount() {
// return doubleEndCallCount;
// }
//
// public void setDayCount(Integer dayCount) {
// this.dayCount = dayCount;
// }
//
// public Integer getDayCount() {
// return dayCount;
// }
//
// public void setNightCount(Integer nightCount) {
// this.nightCount = nightCount;
// }
//
// public Integer getNightCount() {
// return nightCount;
// }
//
// public void setHasCallDays(Integer hasCallDays) {
// this.hasCallDays = hasCallDays;
// }
//
// public Integer getHasCallDays() {
// return hasCallDays;
// }
//
// public void setHasNoCallIn180d(Integer hasNoCallIn180d) {
// this.hasNoCallIn180d = hasNoCallIn180d;
// }
//
// public Integer getHasNoCallIn180d() {
// return hasNoCallIn180d;
// }
//
// public void setTotalTime(Integer totalTime) {
// this.totalTime = totalTime;
// }
//
// public Integer getTotalTime() {
// return totalTime;
// }
//
// public void setDawnCount(Integer dawnCount) {
// this.dawnCount = dawnCount;
// }
//
// public Integer getDawnCount() {
// return dawnCount;
// }
//
// public void setWeekendPercent(Double weekendPercent) {
// this.weekendPercent = weekendPercent;
// }
//
// public Double getWeekendPercent() {
// return weekendPercent;
// }
//
// public void setWeekdayPercent(Double weekdayPercent) {
// this.weekdayPercent = weekdayPercent;
// }
//
// public Double getWeekdayPercent() {
// return weekdayPercent;
// }
//
// public void setTotalCount(Integer totalCount) {
// this.totalCount = totalCount;
// }
//
// public Integer getTotalCount() {
// return totalCount;
// }
//
// public void setWeekendCount(Integer weekendCount) {
// this.weekendCount = weekendCount;
// }
//
// public Integer getWeekendCount() {
// return weekendCount;
// }
//
// public void setTotalOutTime(Integer totalOutTime) {
// this.totalOutTime = totalOutTime;
// }
//
// public Integer getTotalOutTime() {
// return totalOutTime;
// }
//
// public void setThreeMonthCount(Integer threeMonthCount) {
// this.threeMonthCount = threeMonthCount;
// }
//
// public Integer getThreeMonthCount() {
// return threeMonthCount;
// }
//
// public void setTotalInTime(Integer totalInTime) {
// this.totalInTime = totalInTime;
// }
//
// public Integer getTotalInTime() {
// return totalInTime;
// }
//
// public void setTotalInCount(Integer totalInCount) {
// this.totalInCount = totalInCount;
// }
//
// public Integer getTotalInCount() {
// return totalInCount;
// }
//
// public void setDawnPercent(Double dawnPercent) {
// this.dawnPercent = dawnPercent;
// }
//
// public Double getDawnPercent() {
// return dawnPercent;
// }
//
// public void setOneMonthCount(Integer oneMonthCount) {
// this.oneMonthCount = oneMonthCount;
// }
//
// public Integer getOneMonthCount() {
// return oneMonthCount;
// }
//
// public void setWeekdayCount(Integer weekdayCount) {
// this.weekdayCount = weekdayCount;
// }
//
// public Integer getWeekdayCount() {
// return weekdayCount;
// }
//
// public void setDayPercent(Double dayPercent) {
// this.dayPercent = dayPercent;
// }
//
// public Double getDayPercent() {
// return dayPercent;
// }
//
// public void setThreeMonthTime(Integer threeMonthTime) {
// this.threeMonthTime = threeMonthTime;
// }
//
// public Integer getThreeMonthTime() {
// return threeMonthTime;
// }
//
// public void setOneMonthTime(Integer oneMonthTime) {
// this.oneMonthTime = oneMonthTime;
// }
//
// public Integer getOneMonthTime() {
// return oneMonthTime;
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
// public void setCreateTime(Date createTime) {
// this.createTime = createTime;
// }
//
// public Date getCreateTime() {
// return createTime;
// }
//
// public void setUpdateTime(Date updateTime) {
// this.updateTime = updateTime;
// }
//
// public Date getUpdateTime() {
// return updateTime;
// }
// }
