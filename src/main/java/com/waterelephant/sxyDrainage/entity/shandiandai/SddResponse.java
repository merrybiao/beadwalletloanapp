//package com.waterelephant.sxyDrainage.entity.shandiandai;
//
///**
// * 闪电贷响应实体类
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/5/31
// * @since JDK 1.8
// */
//public class SddResponse {
//
//
//    /** 请求成功 */
//    public static final Integer SUCCESS_CODE = 200;
//    /** 签名错误 */
//    public static final Integer SIGN_ERROR_CODE = 5000;
//    /** 参数异常 */
//    public static final Integer PARM_EXCEPTION_CODE = 5001;
//    /** 服务器异常 */
//    public static final Integer SERVER_EXCEPTION_CODE = 5002;
//
//
//    /** 编码 */
//    private Integer code;
//    /** 描述 */
//    private String msg;
//    /** api接口返回，json格式 */
//    private String data;
//    /** 签名 */
//    private String sign;
//
//    public SddResponse() {}
//
//    public SddResponse(Integer code, String msg, String data) {
//        this.code = code;
//        this.msg = msg;
//        this.data = data;
//    }
//
//    public Integer getCode() {
//        return code;
//    }
//
//    public void setCode(Integer code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public String getData() {
//        return data;
//    }
//
//    public void setData(String data) {
//        this.data = data;
//    }
//
//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }
//}
