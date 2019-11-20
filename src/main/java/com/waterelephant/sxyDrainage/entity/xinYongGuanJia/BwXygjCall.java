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
// * <p>Title: CallVo</p>  
// * <p>Description: call 通话详单</p>
// * @since JDK 1.8  
// * @author xiongfeng
// */
//@Table(name = "bw_xygj_call")
//public class BwXygjCall implements Serializable{
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 9212019004546403274L;
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//	private Long order_id;   // 我方订单号
//	private String callphone;  // 对方号码
//	private String month;      // 语音详情月份，格式 yyyy-MM
//	private String homearea;   // 通话时本手机号所在地
//	private String thtypename; // 呼叫类型
//	private int calllong;      // 通话时长（单位秒）
//	private String calltype;   // 主叫被叫
//	private String landtype;   // 本地长途
//	private String calltime;   // 通话时间  格式：yyyy-MM-dd HH:mm:ss
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
//	public String getCallphone() {
//		return callphone;
//	}
//	public void setCallphone(String callphone) {
//		this.callphone = callphone;
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
//	public String getThtypename() {
//		return thtypename;
//	}
//	public void setThtypename(String thtypename) {
//		this.thtypename = thtypename;
//	}
//	
//	public int getCalllong() {
//		return calllong;
//	}
//	public void setCalllong(int calllong) {
//		this.calllong = calllong;
//	}
//	public String getCalltype() {
//		return calltype;
//	}
//	public void setCalltype(String calltype) {
//		this.calltype = calltype;
//	}
//	public String getLandtype() {
//		return landtype;
//	}
//	public void setLandtype(String landtype) {
//		this.landtype = landtype;
//	}
//	public String getCalltime() {
//		return calltime;
//	}
//	public void setCalltime(String calltime) {
//		this.calltime = calltime;
//	}
//	
//}
