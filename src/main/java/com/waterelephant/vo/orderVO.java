package com.waterelephant.vo;

/**
 * 传递借款记录详情是实体类
 * 
 * @Title: BorrowRcordVO.java
 * @Description:
 * @author wangkun
 * @date 2017年4月20日 下午2:02:53
 * @version V1.0
 */
public class orderVO {
	/**
	 * 借款金额
	 * 
	 */
	private String borrowAmount;
	/**
	 * 工单id
	 * 
	 */
	private Long orderId;
	/**
	 * 工单状态id
	 * 
	 */
	private Long statusId;
	/**
	 * 工单申请时间
	 * 
	 */
	private String createTime;
	/**
	 * 分期期数
	 */
	private Integer borrowNumber;

	/**
	 * @return 获取 borrowAmount属性值
	 */
	public String getBorrowAmount() {
		return borrowAmount;
	}

	/**
	 * @param borrowAmount 设置 borrowAmount 属性值为参数值 borrowAmount
	 */
	public void setBorrowAmount(String borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	/**
	 * @return 获取 orderId属性值
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId 设置 orderId 属性值为参数值 orderId
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return 获取 statusId属性值
	 */
	public Long getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId 设置 statusId 属性值为参数值 statusId
	 */
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return 获取 createTime属性值
	 */
	public String getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime 设置 createTime 属性值为参数值 createTime
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return 获取 borrowNumber属性值
	 */
	public Integer getBorrowNumber() {
		return borrowNumber;
	}

	/**
	 * @param borrowNumber 设置 borrowNumber 属性值为参数值 borrowNumber
	 */
	public void setBorrowNumber(Integer borrowNumber) {
		this.borrowNumber = borrowNumber;
	}

	/**
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "orderVO [borrowAmount=" + borrowAmount + ", orderId=" + orderId + ", statusId=" + statusId
				+ ", createTime=" + createTime + ", borrowNumber=" + borrowNumber + "]";
	}

}
