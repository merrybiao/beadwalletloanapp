package com.waterelephant.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 
 */
@Table(name = "bw_dhstreport_sms")
public class BwDhstreportSms implements Serializable {
	
    private static final long serialVersionUID = 101212151454114L;

	
    /**
     * 主键
     */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator="JDBC")
    private Long id;

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
    private String descration;

    /**
     * 当status为2时，表示运营商网关返回的原始值；
     */
    private String wgcode;

    /**
     * 状态报告接收时间格式为yyyy-MM-ddHH:mm:ss。
     */
    private Date time;

//    /**
//     * 长短信条数
//     */
    private String smsCount;
//
//    /**
//     * 长短信序号
//     */
    private String smsIndex;
    
    

    /**
     * 创建时间
     */
    private Date createTime;
    
    
    
    

//    public String getDesc() {
//		return desc;
//	}
//
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}

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

	/**
     * 修改时间
     */
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public String getDescration() {
		return descration;
	}

	public void setDescration(String desc) {
		this.descration = desc;
	}
    
    
}