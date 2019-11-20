//package com.waterelephant.sxyDrainage.entity.wacai;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/29
// * @since JDK 1.8
// */
//public class WaCaiResponse {
//
//    /** 成功 */
//    public static final String CODE_SUCCESS = "0";
//    /** 失败 */
//    public static final String CODE_FAIL = "1";
//    /** 进件同步返回 成功 */
//    public static final String RECEIVE_SUCCESS = "11000";
//    /** 进件同步返回 失败 */
//    public static final String RECEIVE_FAIL = "11100";
//
//    public static final String SIGN_SUCCESS = "15000";
//
//    public static final String SIGN_FAIL = "14100";
//
//
//    /** 错误码 */
//    private String code;
//    /** 信息 */
//    private String message;
//    /** 其它数据 */
//    private Object data;
//
//
//    public WaCaiResponse() {}
//
//    public WaCaiResponse(String code, String message) {
//        this.code = code;
//        this.message = message;
//    }
//    public WaCaiResponse(String code, String message,Object data ) {
//        this.code = code;
//        this.message = message;
//        this.data=data;
//    }
//
//
//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
//
//    public WaCaiResponse(Object data) {
//        this.data = data;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//
//
//}
