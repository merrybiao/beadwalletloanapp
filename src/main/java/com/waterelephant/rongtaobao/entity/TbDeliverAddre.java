package com.waterelephant.rongtaobao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
/**
 * 实体类
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-8 9:36:18
 */
@Table(name="bw_tb_deliver_addre")
@JsonIgnoreProperties(ignoreUnknown=true)
public class TbDeliverAddre implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //
    private Long borrowerId; // 借款人ID
    private Long orderId; // 订单ID
    @JSONField(name="receiver_name")
    private String receiverName; // 收货人姓名
    @JSONField(name="area")
    private String area; // 所在地区
    @JSONField(name="address")
    private String address; // 详细地址
    @JSONField(name="zip_code")
    private String zipCode; // 邮编
    @JSONField(name="phone")
    private String phone; // 电话/手机
    @JSONField(name="is_default_address")
    private String isDefaultAddress; // 是否默认地址
    private Date createDate; //
    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }

    public void setBorrowerId(Long borrowerId){
        this.borrowerId=borrowerId;
    }
    public Long getBorrowerId(){
        return this.borrowerId;
    }

    public void setOrderId(Long orderId){
        this.orderId=orderId;
    }
    public Long getOrderId(){
        return this.orderId;
    }

    public void setReceiverName(String receiverName){
        this.receiverName=receiverName;
    }
    public String getReceiverName(){
        return this.receiverName;
    }

    public void setArea(String area){
        this.area=area;
    }
    public String getArea(){
        return this.area;
    }

    public void setAddress(String address){
        this.address=address;
    }
    public String getAddress(){
        return this.address;
    }

    public void setZipCode(String zipCode){
        this.zipCode=zipCode;
    }
    public String getZipCode(){
        return this.zipCode;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }
    public String getPhone(){
        return this.phone;
    }

    public void setIsDefaultAddress(String isDefaultAddress){
        this.isDefaultAddress=isDefaultAddress;
    }
    public String getIsDefaultAddress(){
        return this.isDefaultAddress;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("TbDeliverAddre[");
        sb.append("id=");
        sb.append(id);
        sb.append(",borrowerId=");
        sb.append(borrowerId);
        sb.append(",orderId=");
        sb.append(orderId);
        sb.append(",receiverName=");
        sb.append(receiverName);
        sb.append(",area=");
        sb.append(area);
        sb.append(",address=");
        sb.append(address);
        sb.append(",zipCode=");
        sb.append(zipCode);
        sb.append(",phone=");
        sb.append(phone);
        sb.append(",isDefaultAddress=");
        sb.append(isDefaultAddress);
        sb.append("]");
        return sb.toString();
    }
}
