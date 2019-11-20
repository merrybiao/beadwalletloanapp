package com.waterelephant.mohe.common;

/**
 * 魔方授权类型
 * @author dinglinhao
 *
 */
public enum MofangAuthType {
	
	MOBILE_OPERATOR("YYS"),//运营商授权
	;
	
	private String authType;
	
	private MofangAuthType(String authType){
		this.authType = authType;
	}

	public String getAuthType() {
		return authType;
	}
	
	public static MofangAuthType get(String authType) {
		MofangAuthType[] array = MofangAuthType.values();
		for(MofangAuthType type : array) {
			if(type.authType.equals(authType)) return type;
		}
		return null;
	}

}
