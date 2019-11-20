//package com.waterelephant.sxyDrainage.entity.xinYongGuanJia;
//
//import java.io.Serializable;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
///**
// * 
// * <p>Title: BillVo</p>  
// * <p>Description: bill 月账单</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Table(name = "bw_xygj_bill")
//public class BwXygjBill implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -4841281223089060185L;
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//	private Long order_id;   // 我方订单号
//	private String month;
//	private String totalfee;  // 当月账单总额
//	private String basefee;   // 规定套餐费
//	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
//	public Long getOrder_id() {
//		return order_id;
//	}
//	public void setOrder_id(Long order_id) {
//		this.order_id = order_id;
//	}
//	public String getMonth() {
//		return month;
//	}
//	public void setMonth(String month) {
//		this.month = month;
//	}
//	public String getTotalfee() {
//		return totalfee;
//	}
//	public void setTotalfee(String totalfee) {
//		this.totalfee = totalfee;
//	}
//	public String getBasefee() {
//		return basefee;
//	}
//	public void setBasefee(String basefee) {
//		this.basefee = basefee;
//	}
//	
//}
