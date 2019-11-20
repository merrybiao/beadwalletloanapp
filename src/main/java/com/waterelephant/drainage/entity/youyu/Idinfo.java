/**
  * Copyright 2017 bejson.com 
  */
package com.waterelephant.drainage.entity.youyu;
import java.util.List;

/***
 * 
 * 
 * 
 * Module: 基本信息实体类
 * 
 * Idinfo.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Idinfo {

    private String user_id;//用户ID
    private String authority;//身份证签发机关
    private String user_name;//用户名
    private String valid_period; //身份证有效期
    private String id_positive_img;//证件照
    private String id_negative_img;//证件照
    private String id_live_img;//活体照片
    private String id_live_score;//活体识别分数
    private String id_live_head_img;//活体头像
    private String id_hand_img;//手持身份证图像
    private String id_hand_img_sample;//手持身份证图像
    private String random_selfie;//手持身份证姿势
    private List<Id_hand_imgs> id_hand_imgs;
    public void setUser_id(String user_id) {
         this.user_id = user_id;
     }
     public String getUser_id() {
         return user_id;
     }

    public void setAuthority(String authority) {
         this.authority = authority;
     }
     public String getAuthority() {
         return authority;
     }

    public void setUser_name(String user_name) {
         this.user_name = user_name;
     }
     public String getUser_name() {
         return user_name;
     }

    public void setValid_period(String valid_period) {
         this.valid_period = valid_period;
     }
     public String getValid_period() {
         return valid_period;
     }

    public void setId_positive_img(String id_positive_img) {
         this.id_positive_img = id_positive_img;
     }
     public String getId_positive_img() {
         return id_positive_img;
     }

    public void setId_negative_img(String id_negative_img) {
         this.id_negative_img = id_negative_img;
     }
     public String getId_negative_img() {
         return id_negative_img;
     }

    public void setId_live_img(String id_live_img) {
         this.id_live_img = id_live_img;
     }
     public String getId_live_img() {
         return id_live_img;
     }

    public void setId_live_score(String id_live_score) {
         this.id_live_score = id_live_score;
     }
     public String getId_live_score() {
         return id_live_score;
     }

    public void setId_live_head_img(String id_live_head_img) {
         this.id_live_head_img = id_live_head_img;
     }
     public String getId_live_head_img() {
         return id_live_head_img;
     }

    public void setId_hand_img(String id_hand_img) {
         this.id_hand_img = id_hand_img;
     }
     public String getId_hand_img() {
         return id_hand_img;
     }

    public void setId_hand_img_sample(String id_hand_img_sample) {
         this.id_hand_img_sample = id_hand_img_sample;
     }
     public String getId_hand_img_sample() {
         return id_hand_img_sample;
     }

    public void setRandom_selfie(String random_selfie) {
         this.random_selfie = random_selfie;
     }
     public String getRandom_selfie() {
         return random_selfie;
     }

    public void setId_hand_imgs(List<Id_hand_imgs> id_hand_imgs) {
         this.id_hand_imgs = id_hand_imgs;
     }
     public List<Id_hand_imgs> getId_hand_imgs() {
         return id_hand_imgs;
     }

}