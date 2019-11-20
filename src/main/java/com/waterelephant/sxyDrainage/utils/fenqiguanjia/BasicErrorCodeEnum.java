//package com.waterelephant.sxyDrainage.utils.fenqiguanjia;
//
//
//import org.apache.http.HttpStatus;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//public enum BasicErrorCodeEnum {
//
//    OPEN_SUCCESS(200, "SUCCESS"),
//    INTERNAL_SERVER_ERROR(90000, "服务器内部错误"),
//    PARAM_VALID_ERROR(90001, "参数验证失败"),
//    PARAM_RESOLVE_ERROR(90002, "参数解析异常"),
//    SIGN_RESOLVE_ERROR(90003, "签名校验未通过"),
//    UNKNOW_ERROR(90012, "操作失败,请稍后再试!"),
//    NO_ACCESS_RIGHT(90024, "没有访问权限");
//
//
//    private Integer code;
//
//    private String msg;
//
//    BasicErrorCodeEnum(Integer code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }
//
//    public Map<String, Object> map() {
//        Map<String, Object> result = new HashMap<String, Object>();
//        result.put("code", code);
//        result.put("msg", msg);
//        return result;
//    }
//
//    public Map<String, Object> map(String message) {
//        Map<String, Object> result = new HashMap<String, Object>();
//        result.put("code", code);
//        if (message != null) {
//            result.put("msg", message + msg);
//        }
//        return result;
//    }
//
//    public String toString() {
//        return code + ":" + msg;
//    }
//
//    public Integer getCode() {
//        return code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//}
