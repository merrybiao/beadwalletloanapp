//package com.waterelephant.sxyDrainage.utils;
//
///**
// * @author xanthuim@gmail.com
// * @date 2018/6/1 11:27
// * <p>
// * 所有的订单状态，有些已经废弃
// * <p>
// * 1	草稿
// * 2	初审
// * 3	终审
// * 4	待签约
// * 5	待放款
// * 6	结束
// * 7	拒绝
// * 8	撤回
// * 9	还款中
// * 11	待生成合同
// * 12	待债匹
// * 13	逾期
// * 14	债匹中
// * 15	待商城用户确认
// * 16	进件临时状态(草稿)
// */
//public enum OrderStatusEnum {
//
//    DRAFT(1, "草稿"), APPROVAL_START(2, "初审"), APPROVAL_END(3, "终审"),
//    SIGN(4, "待签约"), LOAN(5, "待放款"), TERMINATE(6, "结束"),
//    REJECTED(7, "拒绝了"), REVOKED(8, "撤回"), REPAYMENT(9, "还款中"),
//    CONTRACT(11, "待生成合同"), BOND(12, "待债匹"), OVERDUE(13, "逾期"),
//    BOND_DISTRIBUTING(14, "债匹中");
//
//    private int status;
//    private String remark;
//
//    OrderStatusEnum(int status, String remark) {
//        this.status = status;
//        this.remark = remark;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public String getRemark() {
//        return remark;
//    }
//}
