package com.waterelephant.drainage.entity.fqgj;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/20 11:15
 */
public class FqgjIsOldUserData {
    private String id_card;
    private String user_mobile;
    private String user_name;

    public String getId_card() {
        return id_card;
    }
    public void setId_card(String id_card) {
        this.id_card = id_card;
    }
    public String getUser_mobile() {
        return user_mobile;
    }
    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FqgjIsOldUserData [");
        if (id_card != null) {
            builder.append("id_card=");
            builder.append(id_card);
            builder.append(", ");
        }
        if (user_mobile != null) {
            builder.append("user_mobile=");
            builder.append(user_mobile);
            builder.append(", ");
        }
        if (user_name != null) {
            builder.append("user_name=");
            builder.append(user_name);
        }
        builder.append("]");
        return builder.toString();
    }
}
