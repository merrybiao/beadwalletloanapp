//package com.waterelephant.sxyDrainage.utils.wacaiutils;
//
///**
// * 挖财code 枚举
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/30
// * @since JDK 1.8
// */
//public enum WaCaiEnum {
//
//    /** HTTP 状态码 */
//    HTTP_200(200, "请求成功"),
//    //
//    HTTP_400(400, "服务端抛出异常"),
//    //
//    HTTP_401(401, "未授权"),
//    //
//    HTTP_403(403, "没有权限方案"),
//    //
//    HTTP_404(404, "资源未找到"),
//    //
//    HTTP_415(415, "无法处理请求格式"),
//    //
//    HTTP_422(422, "请求数据验证错误"),
//
//    /** 通用异常消息 */
//    CODE_SERVICE_ERROR(101000, "服务器错误"),
//    //
//    CODE_PARAM_ERROR(102000, "数据格式错误"),
//    //
//    CODE_PARAM_DOUBLE(103000, "数据重复错误"),
//
//    /** 贷前检查错误码 */
//    USER_DISCREPANCY(1000, "用户资质不符"),
//
//    /** 订单状态-授信资料提交 */
//    ORDER_INIT(0, "初始化"),
//    //
//    ORDER_DATA_SUBMIT_SUCCESS(12000, "资料提交成功"),
//    //
//    ORDER_DATA_SUBMIT_FAIL(12100, "资料提交失败"),
//
//    /** 订单状态-授信资料审核阶段 */
//    ORDER_EXAMINATION_SUCCESS(13000, "审核通过"),
//    //
//    ORDER_EXAMINATION_FAIL(13100, "审核不通过"),
//    //
//    ORDER_EXAMINATION_ADD(13200, "审核通过"),
//
//    /** 订单状态-签约阶段 */
//    ORDER_CONTRACT_SUCCESS(14000, "签约成功"),
//    //
//    ORDER_CONTRACT_FAIL(14100, "签约失败"),
//    //
//    ORDER_CONTRACT_OVERDUE(14101, "签约过期"),
//
//    /** 订单状态-放款阶段 */
//    ORDER_LOAN_ING(15000, "放款中"),
//    //
//    ORDER_LOAN_SUCCESS(15100, "放款成功"),
//    //
//    ORDER_LOAN_FAIL(15200, "放款失败"),
//
//    /** 订单状态-还款阶段 */
//    ORDER_REPAYMENT_ING(16000, "待还款"),
//    //
//    ORDER_REPAYMENT_CUT(16001, "扣款阶段"),
//    //
//    ORDER_REPAYMENT_CUT_FAIL(16002, "扣款失败"),
//    //
//    ORDER_REPAYMENT_CUT_SUCCESS(16003, "扣款成功"),
//    //
//    ORDER_REPAYMENT_OVERDUE(16100, "还款逾期"),
//    //
//    ORDER_REPAYMENT_SUCCESS(16200, "还款完成"),
//    //
//    ORDER_REPAYMENT_BEFORE(16201, "提前还款"),
//
//    /** 订单状态-订单中止 */
//    ORDER_OFF(17000, "订单取消")
//
//
//    ;
//
//    private Integer code;
//    private String message;
//
//    WaCaiEnum(Integer code, String message) {
//        this.code = code;
//        this.message = message;
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
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//}
