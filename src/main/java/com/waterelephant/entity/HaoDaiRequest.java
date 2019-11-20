package com.waterelephant.entity;

/**
 *
 * @ClassName: HaoDaiRequest
 * @Description: TODO(好贷网请求数据)
 * @author SongYaJun
 * @date 2016年11月18日 下午2:47:20
 *
 */
public class HaoDaiRequest {

    /**
     * 机构合作号(由闪贷提供)
     */
    private int appid;
    /**
     * 当前时间戳(精确到秒)
     */
    private int time;
    /**
     * 认证签名
     */
    private String sign;
    /**
     * 版本号(1.1)
     */
    private float ver;

    public int getAppid() {
        return appid;
    }

    public void setAppid(int appid) {
        this.appid = appid;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public float getVer() {
        return ver;
    }

    public void setVer(float ver) {
        this.ver = ver;
    }

}
