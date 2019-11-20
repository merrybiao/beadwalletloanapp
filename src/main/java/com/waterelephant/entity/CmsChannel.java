package com.waterelephant.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * CMS栏目
 *
 * @author duxiaoyong
 */
@Table(name = "cms_channel")
public class CmsChannel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3624315122663488050L;
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 父栏目Id
     */
    private Long parentId;
    /**
     * 栏目名称
     */
    private String channelName;
    /**
     * 排序
     */
    private Integer priority;
    /**
     * 树节点左边值
     */
    private Long lft;
    /**
     * 树节点右边值
     */
    private Long rgt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Long getLft() {
        return lft;
    }

    public void setLft(Long lft) {
        this.lft = lft;
    }

    public Long getRgt() {
        return rgt;
    }

    public void setRgt(Long rgt) {
        this.rgt = rgt;
    }

}
