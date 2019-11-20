package com.waterelephant.yeepay.JsonEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/21 17:20
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class YeePayResult {
    private String errorcode;
    private String errormsg;
    private String status;//状态
    private String merchantno;//商户编号
    private String merchantbatchno;//商户批次号
    private String ybbatchno;//易宝批次号
//    private String free1;
//    private String free2;
//    private String free3;

    public String getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(String errorcode) {
        this.errorcode = errorcode;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchantno() {
        return merchantno;
    }

    public void setMerchantno(String merchantno) {
        this.merchantno = merchantno;
    }

    public String getMerchantbatchno() {
        return merchantbatchno;
    }

    public void setMerchantbatchno(String merchantbatchno) {
        this.merchantbatchno = merchantbatchno;
    }

    public String getYbbatchno() {
        return ybbatchno;
    }

    public void setYbbatchno(String ybbatchno) {
        this.ybbatchno = ybbatchno;
    }

//    public String getFree1() {
//        return free1;
//    }
//
//    public void setFree1(String free1) {
//        this.free1 = free1;
//    }
//
//    public String getFree2() {
//        return free2;
//    }
//
//    public void setFree2(String free2) {
//        this.free2 = free2;
//    }
//
//    public String getFree3() {
//        return free3;
//    }
//
//    public void setFree3(String free3) {
//        this.free3 = free3;
//    }
}
