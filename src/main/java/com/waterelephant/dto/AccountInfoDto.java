package com.waterelephant.dto;

/**
 * 账户信息DTO 20161228
 * @author duxiaoyong
 *
 */
public class AccountInfoDto {
	
	private Long id;// 借款人ID
	private String phone;// 借款人手机号
	private String name;// 姓名
	private String inviteCode;// 邀请码
	private Integer inviteCount;// 邀请人数
	private Double borrowAmount;// 借款金额
	private String mark;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public Integer getInviteCount() {
		return inviteCount;
	}
	public void setInviteCount(Integer inviteCount) {
		this.inviteCount = inviteCount;
	}
	public Double getBorrowAmount() {
		return borrowAmount;
	}
	public void setBorrowAmount(Double borrowAmount) {
		this.borrowAmount = borrowAmount;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
}
