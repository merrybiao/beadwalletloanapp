//package com.waterelephant.sxyDrainage.entity;
//
///**
// * 响应公共类
// *
// * @author
// * @since 2018-07-23
// */
//public class DrainageRsp {
//
//    private String code;
//    private String message;
//    private Object object;
//
//    public DrainageRsp() {
//    }
//
//    public void setCode(DrainageEnum drainageEnum, Object... args) {
//        this.code = drainageEnum.getCode();
//        this.message = String.format(drainageEnum.getMsg(), args);
//    }
//
//    public DrainageRsp(DrainageEnum drainageEnum, Object... args) {
//        this.code = drainageEnum.getCode();
//        this.message = String.format(drainageEnum.getMsg(), args);
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public Object getObject() {
//        return object;
//    }
//
//    public void setObject(Object object) {
//        this.object = object;
//    }
//
//    public static void main(String[] args) {
//        DrainageRsp d = new DrainageRsp();
//        d.setCode(DrainageEnum.CODE_LOANAMOUNT_GT, 100, 1000);
//        System.out.println(d.getMessage());
//
//    }
//}
