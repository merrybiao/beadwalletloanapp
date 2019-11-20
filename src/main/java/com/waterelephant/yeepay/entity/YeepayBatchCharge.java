package com.waterelephant.yeepay.entity;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
/**
 * 实体类
 * @author GuoKun
 * @version 1.0
 * @create_date 2017-6-22 10:21:09
 */
@JsonIgnoreProperties(ignoreUnknown=true)
@Table(name="bw_yeepay_batch_charge")
public class YeepayBatchCharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private Long id; //
    
    private String merchantBatchNo; // 商户批次号
    
//    private Date requestTime; // 请求时间
    
    private String ybBatchNo; // 易宝批次号（易宝返回）
    
    private String status; // 审批状态（易宝返回）ACCEPT_FAIL：接收失败，出现在同步 请求响应结果中，数据库中⽆此状态 PROCESSING：处理中 注意：具体⽀付结果通过异步通知或由 查询接⼜给出； 异步：PAY_FINISHED：⼦单已全部到达⽀付 终态 TIME_OUT: 批次订单超时失败
    
    private String errorCode; // 错误码（易宝返回）
    
    private String errorMsg; // 错误信息（易宝返回）
    
    private String free1; // 自由项1
    
    private String free2; // 自由项2
    
    private String free3; // 自由项3

    public void setId(Long id){
        this.id=id;
    }
    public Long getId(){
        return this.id;
    }

    public void setMerchantBatchNo(String merchantBatchNo){
        this.merchantBatchNo=merchantBatchNo;
    }
    public String getMerchantBatchNo(){
        return this.merchantBatchNo;
    }

//    public void setRequestTime(Date requestTime){
//        this.requestTime=requestTime;
//    }
//    public Date getRequestTime(){
//        return this.requestTime;
//    }

    public void setYbBatchNo(String ybBatchNo){
        this.ybBatchNo=ybBatchNo;
    }
    public String getYbBatchNo(){
        return this.ybBatchNo;
    }

    public void setStatus(String status){
        this.status=status;
    }
    public String getStatus(){
        return this.status;
    }

    public void setErrorCode(String errorCode){
        this.errorCode=errorCode;
    }
    public String getErrorCode(){
        return this.errorCode;
    }

    public void setErrorMsg(String errorMsg){
        this.errorMsg=errorMsg;
    }
    public String getErrorMsg(){
        return this.errorMsg;
    }

    public void setFree1(String free1){
        this.free1=free1;
    }
    public String getFree1(){
        return this.free1;
    }

    public void setFree2(String free2){
        this.free2=free2;
    }
    public String getFree2(){
        return this.free2;
    }

    public void setFree3(String free3){
        this.free3=free3;
    }
    public String getFree3(){
        return this.free3;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("YeepayBatchCharge[");
        sb.append("id=");
        sb.append(id);
        sb.append(",merchantBatchNo=");
        sb.append(merchantBatchNo);
        sb.append(",requestTime=");
//        sb.append(requestTime);
        sb.append(",ybBatchNo=");
        sb.append(ybBatchNo);
        sb.append(",status=");
        sb.append(status);
        sb.append(",errorCode=");
        sb.append(errorCode);
        sb.append(",errorMsg=");
        sb.append(errorMsg);
        sb.append(",free1=");
        sb.append(free1);
        sb.append(",free2=");
        sb.append(free2);
        sb.append(",free3=");
        sb.append(free3);
        sb.append("]");
        return sb.toString();
    }
}
