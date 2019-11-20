package com.waterelephant.entity;

/**
 *
 * @ClassName: HaoDaiResponse
 * @Description: TODO(好贷网响应数据)
 * @author SongYaJun
 * @date 2016年11月18日 下午2:14:32
 *
 */
public class HaoDaiResponse {

    /**
     * 接口版本号
     */
    private String version = "";
    /**
     * 错误码（当 code 为 1000 时表示请求成功，并在 message 中返回相应的数 据，若 code 非 1000 则表 示失败，message 会有相应 的错误提示,错误码请看下 表
     */
    private int code;
    /**
     * 错误或正确返回的信息
     */
    private String message = "";

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
