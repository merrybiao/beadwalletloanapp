//package com.waterelephant.sxyDrainage.entity.dkdh360;
//
//import javax.persistence.*;
//import java.util.Date;
//
///**
// * (code:dkdh001)
// *
// * @Author: zhangchong
// * @Date: 2018/7/27 15:00
// * @Description: APP列表
// */
//@Table(name = "bw_dkdh360_installed_apps")
//public class BwDkdh360InstalledApps {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
//    private Long id;
//    private Long orderId;
//    /**
//     * 应用名
//     */
//    private String name;
//    /**
//     * 应用包名
//     */
//    private String packageName;
//    /**
//     * 应用的版本号
//     */
//    private String versionName;
//    private Date createTime;
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getOrderId() {
//        return orderId;
//    }
//
//    public void setOrderId(Long orderId) {
//        this.orderId = orderId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPackageName() {
//        return packageName;
//    }
//
//    public void setPackageName(String packageName) {
//        this.packageName = packageName;
//    }
//
//    public String getVersionName() {
//        return versionName;
//    }
//
//    public void setVersionName(String versionName) {
//        this.versionName = versionName;
//    }
//
//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//}
