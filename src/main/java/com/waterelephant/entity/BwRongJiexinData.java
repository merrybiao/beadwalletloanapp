/**
 * @author heqiwen
 * @date 2017年1月23日
 */
package com.waterelephant.entity;

/**
 * @author Administrator
 *
 */
public class BwRongJiexinData {

    private String name;// 姓名
    private String idCard;// 身份证号
    private String phone;// 手机号
    private String black_result;// 黑名单命中结果
    private String risk_level;// 风险等级
    private String legal_executed;// 命中法院执行黑名单
    private String legal_dishonesty;// 命中法院失信黑名单
    private String legal_time;// 最近法院黑名单记录日期
    private String legal_type;// 最近法院黑名单记录类型
    private String overdue_black;// 命中逾期黑名单
    private String max_overdue_nper;// 最大逾期期数
    private String max_overdue_money;// 最大逾期金额
    private String multi_loan;// 命中多头借贷黑名单
    private String last_loan_time;// 上次借贷记录时间

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBlack_result() {
        return black_result;
    }

    public void setBlack_result(String black_result) {
        this.black_result = black_result;
    }

    public String getRisk_level() {
        return risk_level;
    }

    public void setRisk_level(String risk_level) {
        this.risk_level = risk_level;
    }

    public String getLegal_executed() {
        return legal_executed;
    }

    public void setLegal_executed(String legal_executed) {
        this.legal_executed = legal_executed;
    }

    public String getLegal_dishonesty() {
        return legal_dishonesty;
    }

    public void setLegal_dishonesty(String legal_dishonesty) {
        this.legal_dishonesty = legal_dishonesty;
    }

    public String getLegal_time() {
        return legal_time;
    }

    public void setLegal_time(String legal_time) {
        this.legal_time = legal_time;
    }

    public String getLegal_type() {
        return legal_type;
    }

    public void setLegal_type(String legal_type) {
        this.legal_type = legal_type;
    }

    public String getOverdue_black() {
        return overdue_black;
    }

    public void setOverdue_black(String overdue_black) {
        this.overdue_black = overdue_black;
    }

    public String getMax_overdue_nper() {
        return max_overdue_nper;
    }

    public void setMax_overdue_nper(String max_overdue_nper) {
        this.max_overdue_nper = max_overdue_nper;
    }

    public String getMax_overdue_money() {
        return max_overdue_money;
    }

    public void setMax_overdue_money(String max_overdue_money) {
        this.max_overdue_money = max_overdue_money;
    }

    public String getMulti_loan() {
        return multi_loan;
    }

    public void setMulti_loan(String multi_loan) {
        this.multi_loan = multi_loan;
    }

    public String getLast_loan_time() {
        return last_loan_time;
    }

    public void setLast_loan_time(String last_loan_time) {
        this.last_loan_time = last_loan_time;
    }



}
