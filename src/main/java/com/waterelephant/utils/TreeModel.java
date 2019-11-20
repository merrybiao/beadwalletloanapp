/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.waterelephant.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hui Wang
 */
public class TreeModel {

    /**
     * 节点ID
     */
    private String id;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 父节点ID
     */
    private String parentId;
    /**
     * 打开的样式
     */
    private String iconOpen;
    /**
     * 关闭的样式
     */
    private String iconClose;
    /**
     * 是否选中
     */
    private String checked;
    /**
     * 是否展开
     */
    private String open;
    /**
     * 是否是父节点
     */
    private String isParent;
    /**
     * 图片路径
     */
    private String imgPath;
    /**
     * url
     */
    private String url;
    /**
     * 打开url位置
     */
    private String target;
    /**
     * 子节点
     */
    private List<TreeModel> children = new ArrayList<TreeModel>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIconOpen() {
        return iconOpen;
    }

    public void setIconOpen(String iconOpen) {
        this.iconOpen = iconOpen;
    }

    public String getIconClose() {
        return iconClose;
    }

    public void setIconClose(String iconClose) {
        this.iconClose = iconClose;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<TreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<TreeModel> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
