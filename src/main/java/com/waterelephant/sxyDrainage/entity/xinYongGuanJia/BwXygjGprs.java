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
// * <p>Title: XygjOperatorBase</p>  
// * <p>Description: base 用户基本信息</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Table(name = "bw_xygj_gprs")
//public class BwXygjGprs implements Serializable{
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -9144455407527968375L;
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//	private Long order_id;   // 我方订单号
//	private String totalflow;  // 本月使用的流量总量
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
//	public String getTotalflow() {
//		return totalflow;
//	}
//	public void setTotalflow(String totalflow) {
//		this.totalflow = totalflow;
//	}
//	
//	
//}
