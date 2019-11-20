package com.waterelephant.gxb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
/**
 * 公信宝-滴滴推送数据-用户信息
 * @author dinglinhao
 * @date 2018年6月29日11:55:09
 *
 */
public class TaxiDataDto implements Serializable {

	private static final long serialVersionUID = 2519002834610695912L;
	
	

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 是否本人账号，1是0否
     */
    private int status;

    /**
     * 用户电话
     */
    private String phone;
    /**
     * 用户身份证(未实名的用户无此字段)
     */
    private String idCard;
    /**
     * 用户所在企业名(未开通企业滴滴的用户无此字段)
     */
    private String company;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户性别 0未知 1男2女
     */
    private int gender;

    /**
     * 用户级别
     */
    private int level;

    /**
     * 用户级别描述 2白银 3黄金 4白金 5钻石 6黑金
     */
    private String levelName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 芝麻分
     */
    private Integer sesameScore;
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastUpdateDate;
    /**
     * 六个月内的出行记录
     */
    private List<TaxiOrderDto> orderList;
    
    private BigDecimal	balance;//账户余额
    private BigDecimal specialCardBalance;//充值卡余额
    private BigDecimal financeBalance;//理财余额
    private BigDecimal debit;//借钱额度
    private Integer coin;//滴滴的滴币余额
    private TaxiDriverInfoDto driverInfo;//车主认证的信息（部分有车主认证的用户有此信息）
    private List<TaxiCommonAddressDto> commonAddressList;//常用地址记录(没有的话为空list)
    private List<TaxiInvoiceDto> invoiceList;//发票记录(没有的话为空list)


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSesameScore() {
        return sesameScore;
    }

    public void setSesameScore(Integer sesameScore) {
        this.sesameScore = sesameScore;
    }

    public List<TaxiOrderDto> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<TaxiOrderDto> orderList) {
        this.orderList = orderList;
    }

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getSpecialCardBalance() {
		return specialCardBalance;
	}

	public void setSpecialCardBalance(BigDecimal specialCardBalance) {
		this.specialCardBalance = specialCardBalance;
	}

	public BigDecimal getFinanceBalance() {
		return financeBalance;
	}

	public void setFinanceBalance(BigDecimal financeBalance) {
		this.financeBalance = financeBalance;
	}

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public Integer getCoin() {
		return coin;
	}

	public void setCoin(Integer coin) {
		this.coin = coin;
	}

	public TaxiDriverInfoDto getDriverInfo() {
		return driverInfo;
	}

	public void setDriverInfo(TaxiDriverInfoDto driverInfo) {
		this.driverInfo = driverInfo;
	}

	public List<TaxiCommonAddressDto> getCommonAddressList() {
		return commonAddressList;
	}

	public void setCommonAddressList(List<TaxiCommonAddressDto> commonAddressList) {
		this.commonAddressList = commonAddressList;
	}

	public List<TaxiInvoiceDto> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<TaxiInvoiceDto> invoiceList) {
		this.invoiceList = invoiceList;
	}
	
}