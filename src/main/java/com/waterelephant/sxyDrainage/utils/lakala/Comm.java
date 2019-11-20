//package com.waterelephant.sxyDrainage.utils.lakala;
//
//import org.hibernate.validator.constraints.NotBlank;
//
///**
// * @ClassName Comm
// * @Description TODO(报文头实体类)
// * @author zhangxin
// * @Date 2017-11-22 下午3:24:48
// */
//public class Comm {
//
//	 // sendTime 报文发送时间 String（17） M 格式：YYYYMMDD HH:MM:SS
//    @NotBlank(message = "NotBlank")
//    private String sendTime;
//    // requestCode 交易码 String（20） M 接口标识(接口唯一标识，接口id，见具体接口)
//    @NotBlank(message = "NotBlank")
//    private String requestCode;
//    // version 版本号 String（6） M 接口升级使用(格式：1.0,2.0)
//    @NotBlank(message = "NotBlank")
//    private String version;
//    // needret 是否需要返回报文 String（6） M yes:需要 no:不需要 目前只有下载接口需要传no
//    private String needret;
//    // serialNo 请求流水号 String（32） M 每笔交易唯一标识（包银消费发起通知交易则为包银消费流水，格式一样）例如：fql_20151010131222000001020202等
//    @NotBlank(message = "NotBlank")
//    private String serialNo;
//    // channelNo 渠道编号 String（10） M 填第三方渠道代码包银消费提供
//    @NotBlank(message = "NotBlank")
//    private String channelNo;
//    // signtx 签名域 String C
//    @NotBlank(message = "NotBlank")
//    private String signtx;
//
//    // 单笔唯一流水号
//    private String sid;
//
//    // H5专用字段
//    // 是否加密
//    private String needEntry;
//    // 加密后的ticktId
//    private String tickIdMd;
//
//    public String getNeedEntry() {
//        return needEntry;
//    }
//
//    public void setNeedEntry(String needEntry) {
//        this.needEntry = needEntry;
//    }
//
//    public String getTickIdMd() {
//        return tickIdMd;
//    }
//
//    public void setTickIdMd(String tickIdMd) {
//        this.tickIdMd = tickIdMd;
//    }
//
//    public String getSendTime() {
//        return sendTime;
//    }
//
//    public void setSendTime(String sendTime) {
//        this.sendTime = sendTime;
//    }
//
//    public String getRequestCode() {
//        return requestCode;
//    }
//
//    public void setRequestCode(String requestCode) {
//        this.requestCode = requestCode;
//    }
//
//    public String getVersion() {
//        return version;
//    }
//
//    public void setVersion(String version) {
//        this.version = version;
//    }
//
//    public String getNeedret() {
//        return needret;
//    }
//
//    public void setNeedret(String needret) {
//        this.needret = needret;
//    }
//
//    public String getSerialNo() {
//        return serialNo;
//    }
//
//    public void setSerialNo(String serialNo) {
//        this.serialNo = serialNo;
//    }
//
//    public String getChannelNo() {
//        return channelNo;
//    }
//
//    public void setChannelNo(String channelNo) {
//        this.channelNo = channelNo;
//    }
//
//    public String getSigntx() {
//        return signtx;
//    }
//
//    public void setSigntx(String signtx) {
//        this.signtx = signtx;
//    }
//
//    public String getSid() {
//        return sid;
//    }
//
//    public void setSid(String sid) {
//        this.sid = sid;
//    } 
//}
