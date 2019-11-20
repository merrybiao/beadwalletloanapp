package com.waterelephant.third.jsonentity;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 身份证照片
 * Created by dengyan on 2017/7/20.
 */
public class IdCardPhotoes {

	@NotNull(message = "身份证正面照片为空")
    @JSONField(name = "ID_FacePhoto")
    private String facePhoto; // 身份证正面照片，Base64格式

	@NotNull(message = "身份证反面照片为空")
    @JSONField(name = "ID_BackPhoto")
    private String backPhoto; // 身份证反面照片，Base64格式

	@NotNull(message = "持证照为空")
    @JSONField(name = "photo_hand_ID")
    private String photoHandId; // 手持身份证照片，Base64格式

	@NotNull
    @JSONField(name = "photo_assay")
    private String photoAssay; // 活体照片， Base64格式

    public String getFacePhoto() {
        return facePhoto;
    }

    public void setFacePhoto(String facePhoto) {
        this.facePhoto = facePhoto;
    }

    public String getBackPhoto() {
        return backPhoto;
    }

    public void setBackPhoto(String backPhoto) {
        this.backPhoto = backPhoto;
    }

    public String getPhotoHandId() {
        return photoHandId;
    }

    public void setPhotoHandId(String photoHandId) {
        this.photoHandId = photoHandId;
    }

    public String getPhotoAssay() {
        return photoAssay;
    }

    public void setPhotoAssay(String photoAssay) {
        this.photoAssay = photoAssay;
    }
}
