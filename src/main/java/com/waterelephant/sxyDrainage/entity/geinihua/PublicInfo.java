//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import org.hibernate.validator.constraints.NotBlank;
//
//import javax.validation.constraints.NotNull;
//
///**
// * 公共信息
// *
// * @author xanthuim
// * @since 2018-07
// */
//
//public class PublicInfo<T> {
//    /**
//     * string Y API请求的签名
//     */
//    @NotBlank(message = "sign不能为空")
//    private String sign;
//    /**
//     * 机构ID
//     */
//    @NotBlank(message = "appId不能为空")
//    private String appId;
//    /**
//     * 加密类型
//     */
//    @NotBlank(message = "encryptType不能为空")
//    private String encryptType = "RSA";
//    /**
//     * 支持RSA
//     */
//    @NotBlank(message = "signType不能为空")
//    private String signType = "RSA";
//    /**
//     * JSON Y 请求的业务数据，此处数据格式为Json封装。
//     */
//    @NotNull(message = "bizData不能为空")
//    private T bizData;
//    /**
//     * API协议版本，默认值：1.0
//     */
//    @NotBlank(message = "version不能为空")
//    private String version;
//    /**
//     * 10位时间戳，精确到秒
//     */
//    @NotNull(message = "timestamp不能为空")
//    private Long timestamp;
//    /**
//     * N 是否需要压缩，默认 0 不压缩, 1 压缩
//     */
//    private Integer isCompress;
//    /**
//     * N 是否需要加密，默认 0 不加密, 1 加密
//     */
//    private Integer isEncrypt;
//    /**
//     * N 回调url
//     */
//    private String returnUrl;
//
//    public String getEncryptType() {
//        return encryptType;
//    }
//
//    public void setEncryptType(String encryptType) {
//        this.encryptType = encryptType;
//    }
//
//    public String getSign() {
//        return sign;
//    }
//
//    public void setSign(String sign) {
//        this.sign = sign;
//    }
//
//    public String getAppId() {
//        return appId;
//    }
//
//    public void setAppId(String appId) {
//        this.appId = appId;
//    }
//
//    public String getSignType() {
//        return signType;
//    }
//
//    public void setSignType(String signType) {
//        this.signType = signType;
//    }
//
//    @NotNull
//    public T getBizData() {
//        return bizData;
//    }
//
//    public void setBizData(@NotNull T bizData) {
//        this.bizData = bizData;
//    }
//
//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }
//
//    @NotNull
//    public Long getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(@NotNull Long timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public Integer getIsCompress() {
//        return isCompress;
//    }
//
//    public void setIsCompress(Integer isCompress) {
//        this.isCompress = isCompress;
//    }
//
//    public Integer getIsEncrypt() {
//        return isEncrypt;
//    }
//
//    public void setIsEncrypt(Integer isEncrypt) {
//        this.isEncrypt = isEncrypt;
//    }
//
//    public String getReturnUrl() {
//        return returnUrl;
//    }
//
//    public void setReturnUrl(String returnUrl) {
//        this.returnUrl = returnUrl;
//    }
//}
