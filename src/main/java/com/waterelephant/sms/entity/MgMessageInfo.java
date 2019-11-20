package com.waterelephant.sms.entity;



/**
 * 存储发送信息的实体
 * @author dengyan
 *
 */
public class MgMessageInfo {

	private String phone; // 接收短信的手机
	private String msg; // 消息内容
	private int state; // 消息状态，初始均为0
	private String create_time; // 发送消息时间
	private String update_time; // 更新时间
	private String seqid; // 唯一识别id
	private String state_value; // 获取到的状态值
	private int chenal; // 发送短信的渠道：1、亿美 2、大汉三通 3、创蓝
	private int type; // 发送方式：1、文字 2、语音
	private Oid _id; // 主键
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getState_value() {
		return state_value;
	}
	public void setState_value(String state_value) {
		this.state_value = state_value;
	}
	public String getSeqid() {
		return seqid;
	}
	public void setSeqid(String seqid) {
		this.seqid = seqid;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public int getChenal() {
		return chenal;
	}
	public void setChenal(int chenal) {
		this.chenal = chenal;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Oid get_id() {
		return _id;
	}
	public void set_id(Oid _id) {
		this._id = _id;
	}
}
