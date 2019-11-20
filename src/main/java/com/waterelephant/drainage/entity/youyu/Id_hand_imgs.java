/**
  * Copyright 2017 bejson.com 
  */
package com.waterelephant.drainage.entity.youyu;

/***
 * 
 * 
 * 
 * Module: 手持身份证照片
 * 
 * Id_hand_imgs.java 
 * @author Fan Shenghuan
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class Id_hand_imgs {

    private String id_hand_img;
    private String id_hand_img_sample;
    private String random_selfie;//手持身份证姿势
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

}