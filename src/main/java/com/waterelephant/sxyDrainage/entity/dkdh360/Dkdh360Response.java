//package com.waterelephant.sxyDrainage.entity.dkdh360;
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/24 19:55
// * @Description: 公共响应类
// */
//public class Dkdh360Response {
//    /**
//     * 接口调用成功
//     */
//    public static final Integer CODE_SUCCESS = 200;
//    /**
//     * 接口调用失败
//     */
//    public static final Integer CODE_FAILURE = 100;
//    /**
//     * 重复调用
//     */
//    public static final Integer CODE_DUPLICATECALL = 101;
//    /**
//     * 参数错误
//     */
//    public static final Integer CODE_PARAMETER = 103;
//    /**
//     * 不可申请
//     */
//    public static final Integer CODE_NOTLOAN = 400;
//    /**
//     * 响应码
//     */
//    private Integer code;
//    /**
//     * 响应信息
//     */
//    private String msg;
//    /**
//     * 响应数据
//     */
//    private Object data;
//    /**
//     * C001：已经在对方有进行中的贷款 , C002：在对方有不良贷款记录, C003：30 天内被机构审批拒绝过
//     */
//    private String reason;
//
//    public Dkdh360Response(Integer code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }
//
//    public Dkdh360Response(Integer code, String msg, String reason) {
//        this.code = code;
//        this.msg = msg;
//        this.reason = reason;
//    }
//
//    public Dkdh360Response(Integer code, String msg, Object data) {
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
//    public Object getData() {
//        return data;
//    }
//
//    public void setData(Object data) {
//        this.data = data;
//    }
//
//    public String getReason() {
//        return reason;
//    }
//
//    public void setReason(String reason) {
//        this.reason = reason;
//    }
//}
