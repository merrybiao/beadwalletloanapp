package com.waterelephant.bjsms.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.alibaba.fastjson.annotation.JSONField;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;

/**
 * @author 
 */
@Document(collection = "mg_dhst_message_report")
public class BwDhstreportSms implements Serializable {
	
    private static final long serialVersionUID = 101212151454114L;

    /**
     * 短信编号
     */
    private String msgid;

    /**
     * 下行手机号码
     */
    private String phone;

    /*
     * 短信发送结果：0——成功；1——接口处理失败；2——运营商网关失败；
     */
    private String status;

    /**
     * 当status为1时，以desc的错误码为准
     */
    private String desc;

    /**
     * 当status为2时，表示运营商网关返回的原始值；
     */
    private String wgcode;

    /**
     * 状态报告接收时间格式为yyyy-MM-ddHH:mm:ss。
     */
    @Field(value ="mg_time")
    @JSONField(name = "time")
    private Date createTime;

//    /**
//     * 长短信条数
//     */
    private String smsCount;
//
//    /**
//     * 长短信序号
//     */
    private String smsIndex;
 
    
    
	public String getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(String smsCount) {
		this.smsCount = smsCount;
	}

	public String getSmsIndex() {
		return smsIndex;
	}

	public void setSmsIndex(String smsIndex) {
		this.smsIndex = smsIndex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWgcode() {
		return wgcode;
	}

	public void setWgcode(String wgcode) {
		this.wgcode = wgcode;
	}

    public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = CommUtils.isNull(createTime) ? new Date() : DateUtil.stringToDate(createTime,DateUtil.yyyy_MM_dd_HHmmss);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}