package com.waterelephant.dto;

/**
 * Created by song on 2017/3/16.
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/3/16 9:58
 */
public class MoxieTaskDto {

    private String mobile;
    private String user_id;
    private String order_id;
    private String task_id;
    private int account;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "MoxieTaskDto{" +
                "mobile='" + mobile + '\'' +
                ", user_id='" + user_id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", task_id='" + task_id + '\'' +
                '}';
    }
}
