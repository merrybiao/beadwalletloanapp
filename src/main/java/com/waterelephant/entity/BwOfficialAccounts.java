package com.waterelephant.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @ClassName: BwOfficialAccounts
 * @Description: TODO(公众号与用户关联实体类)
 * @author buzhongshan
 * @date 2016年10月31日
 *
 */
@Table(name = "bw_official_accounts")
public class BwOfficialAccounts implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1208859806196495579L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主键
    private Long userId;
    private String openId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

}
