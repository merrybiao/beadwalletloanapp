package com.waterelephant.drainage.entity.rongShu;
/**
 * @author xiaoXingWu
 * @time 2017年8月24日
 * @since JDK1.8
 * @description 通话详细信息
 */
public class CallDetailList {

	private  String  dialType;//业务类型
	private  String commType;//
	private  String  time;//通话时间
	private  String  location;//通话地点
	private  String  locationType;//地点类型
	private  String peerOperator;//对方运营
	private  String peerNumber;//对方号码
	private  String durationSec;//通话时长
	private  String fee;//总费用
	public String getDialType() {
		return dialType;
	}
	public void setDialType(String dialType) {
		this.dialType = dialType;
	}
	public String getCommType() {
		return commType;
	}
	public void setCommType(String commType) {
		this.commType = commType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getPeerOperator() {
		return peerOperator;
	}
	public void setPeerOperator(String peerOperator) {
		this.peerOperator = peerOperator;
	}
	public String getPeerNumber() {
		return peerNumber;
	}
	public void setPeerNumber(String peerNumber) {
		this.peerNumber = peerNumber;
	}
	public String getDurationSec() {
		return durationSec;
	}
	public void setDurationSec(String durationSec) {
		this.durationSec = durationSec;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	
	
	
}

