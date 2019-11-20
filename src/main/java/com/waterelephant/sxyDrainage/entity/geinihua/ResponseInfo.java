//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//
///**
// * 响应信息
// *
// * @author xanthuim
// */
//
//@JsonInclude(value = Include.NON_NULL)
//public class ResponseInfo<T> {
//    /**
//     * 成功
//     */
//    private static final int SUCCESS = 200;
//    /**
//     * 复贷用户
//     */
//    private static final int SUCCESS_REPEAT = 201;
//    /**
//     * 参数错误
//     */
//    private static final int ERROR_PARAMETER = 400;
//    /**
//     * 服务器异常
//     */
//    private static final int ERROR_INTERAL = 500;
//
//    private Integer code = SUCCESS;
//    private String resonCode;
//    private String message;
//    private T bizData;
//
//    public Integer getCode() {
//        return code;
//    }
//
//    public void setCode(Integer code) {
//        this.code = code;
//    }
//
//    public String getResonCode() {
//        return resonCode;
//    }
//
//    public void setResonCode(String resonCode) {
//        this.resonCode = resonCode;
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
//    public T getBizData() {
//        return bizData;
//    }
//
//    public void setBizData(T bizData) {
//        this.bizData = bizData;
//    }
//
//    public ResponseInfo() {
//        super();
//    }
//
//    public ResponseInfo(Integer code, String message) {
//        this.code = code;
//        this.message = message;
//    }
//
//    public ResponseInfo(Integer code, String message, T bizData) {
//        this.code = code;
//        this.message = message;
//        this.bizData = bizData;
//    }
//
//    public ResponseInfo(String message, T bizData) {
//        this.message = message;
//        this.bizData = bizData;
//    }
//
//    /**
//     * 签名失败
//     *
//     * @return
//     */
//    public static ResponseInfo<String> signatureFailure() {
//        return new ResponseInfo<>(ERROR_PARAMETER, "签名失败", null);
//    }
//
//    public static ResponseInfo success(String message) {
//        return new ResponseInfo<>(SUCCESS, message, null);
//    }
//
//    public static <T> ResponseInfo<T> success(T bizData) {
//        return new ResponseInfo<>(SUCCESS, "success", bizData);
//    }
//
//    public static ResponseInfo<String> error(String error) {
//        return new ResponseInfo<>(ERROR_INTERAL, error, null);
//    }
//
//    /**
//     * 参数错误，比如为空，不合法等
//     *
//     * @return
//     */
//    public static ResponseInfo<?> parametersError(String error) {
//        return new ResponseInfo<>(ERROR_PARAMETER, error, null);
//    }
//}
