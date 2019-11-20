//package com.waterelephant.sxyDrainage.entity.geinihua;
//
//import org.hibernate.validator.constraints.NotBlank;
//
///**
// * 合同
// *
// * @author xanthuim
// */
//
//public class Contract {
//    /**
//     * 订单编号 string 是 请求时可能无订单号参数
//     */
//    @NotBlank(message = "orderNo不能为空")
//    private String orderNo;
//    /**
//     * 用户ID int 否 北斗平台用户ID，需要将用户id添加到合同的最下方。
//     */
//    private String userId;
//    /**
//     * 合同所属的页面 int 否 2 补充信息页 3:审批结果页 4 绑卡页面 5:账单页面
//     */
//    private Integer contractPage;
//    private String contractUrl;
//    private String contractName;
//
//    public String getOrderNo() {
//        return orderNo;
//    }
//
//    public void setOrderNo(String orderNo) {
//        this.orderNo = orderNo;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public Integer getContractPage() {
//        return contractPage;
//    }
//
//    public void setContractPage(Integer contractPage) {
//        this.contractPage = contractPage;
//    }
//
//    public String getContractUrl() {
//        return contractUrl;
//    }
//
//    public void setContractUrl(String contractUrl) {
//        this.contractUrl = contractUrl;
//    }
//
//    public String getContractName() {
//        return contractName;
//    }
//
//    public void setContractName(String contractName) {
//        this.contractName = contractName;
//    }
//}
