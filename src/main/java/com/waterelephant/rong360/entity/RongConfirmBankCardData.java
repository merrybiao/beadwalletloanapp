package com.waterelephant.rong360.entity;
@Deprecated
public class RongConfirmBankCardData {
	private String order_no;
	private String bank_card;
	private String index;
	
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getBank_card() {
		return bank_card;
	}
	public void setBank_card(String bank_card) {
		this.bank_card = bank_card;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RongConfirmBankCardData [");
		if (order_no != null) {
			builder.append("order_no=");
			builder.append(order_no);
			builder.append(", ");
		}
		if (bank_card != null) {
			builder.append("bank_card=");
			builder.append(bank_card);
			builder.append(", ");
		}
		if (index != null) {
			builder.append("index=");
			builder.append(index);
		}
		builder.append("]");
		return builder.toString();
	}
}