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
// * <p>Title: SmsVo</p>  
// * <p>Description: sms 短信详单</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Table(name = "bw_xygj_sms")
//public class BwXygjSms implements Serializable{
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -5075697591970581709L;
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//	private Long order_id;   // 我方订单号
//	private String smstime;   // 发送/接收短信时间
//	private String smsphone;  // 对方号码
//	private String smstype;   // 发送? 接收
//	private String month;
//	private String homearea;  // 地区名/区号
//	private String smsfee;    // 短信费用
//	private String businesstype; // 短信/彩信
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
//	public String getSmstime() {
//		return smstime;
//	}
//	public void setSmstime(String smstime) {
//		this.smstime = smstime;
//	}
//	public String getSmsphone() {
//		return smsphone;
//	}
//	public void setSmsphone(String smsphone) {
//		this.smsphone = smsphone;
//	}
//	public String getSmstype() {
//		return smstype;
//	}
//	public void setSmstype(String smstype) {
//		this.smstype = smstype;
//	}
//	public String getMonth() {
//		return month;
//	}
//	public void setMonth(String month) {
//		this.month = month;
//	}
//	public String getHomearea() {
//		return homearea;
//	}
//	public void setHomearea(String homearea) {
//		this.homearea = homearea;
//	}
//	public String getSmsfee() {
//		return smsfee;
//	}
//	public void setSmsfee(String smsfee) {
//		this.smsfee = smsfee;
//	}
//	public String getBusinesstype() {
//		return businesstype;
//	}
//	public void setBusinesstype(String businesstype) {
//		this.businesstype = businesstype;
//	}
//	
//}
