package com.waterelephant.drainage.jsonentity.fqgj;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/29 11:40
 */
public class FqgjApproveConfirmData {
    private String order_no;
    private String loan_amount;
    private String loan_term;

    public String getOrder_no() {
        return order_no;
    }
    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
    public String getLoan_amount() {
        return loan_amount;
    }
    public void setLoan_amount(String loan_amount) {
        this.loan_amount = loan_amount;
    }
    public String getLoan_term() {
        return loan_term;
    }
    public void setLoan_term(String loan_term) {
        this.loan_term = loan_term;
    }
}
