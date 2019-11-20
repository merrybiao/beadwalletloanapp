package com.waterelephant.faceID.entity;

/**
 * FaceIDOrderAuthDto应用实体
 * @author dengyan
 *
 */
public class FaceIDOrderAuthDto {

	private Integer authType;
	private Integer authStatus;
	private Integer photoState;
	
	public FaceIDOrderAuthDto() {
	}

	public FaceIDOrderAuthDto(Integer authType, Integer authStatus, Integer photoState) {
		this.authType = authType;
		this.authStatus = authStatus;
		this.photoState = photoState;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public Integer getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(Integer authStatus) {
		this.authStatus = authStatus;
	}

	public Integer getPhotoState() {
		return photoState;
	}

	public void setPhotoState(Integer photoState) {
		this.photoState = photoState;
	}

	@Override
	public String toString() {
		return "FaceIDOrderAuthDto [authType=" + authType + ", authStatus="
				+ authStatus + ", photoState=" + photoState + "]";
	}

	

}
