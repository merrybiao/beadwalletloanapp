package com.waterelephant.linkface.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 商汤人脸识别（code0088）
 * 
 * 
 * Module: 
 * 
 * LinkfaceConstant.java 
 * @author liuDaodao
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */
public class LinkfaceConstant {
	public static Map<String, String> SOURCENAME = new HashMap<>();
	public static Map<String, String> SOURCECODE = new HashMap<>();
	
	public static String VERIFYSOURCE_CURRENT = ""; // 当前认证来源
	public static String VERIFYSOURCE_MANUAL = ""; // 人工审核
	public static String VERIFYSOURCE_FACEPLUS = ""; // face++
	public static String VERIFYSOURCE_LINKFACE = ""; // linkface(商汤)
	public static String FILEURL = ""; // 本地文件临时存储路径

	static {
		ResourceBundle config_linkface = ResourceBundle.getBundle("linkface");
		if (config_linkface == null) {
			throw new IllegalArgumentException("[linkface.properties] is not found!");
		}
		LinkfaceConstant.VERIFYSOURCE_CURRENT = config_linkface.getString("linkface.verifySource_current");
		LinkfaceConstant.VERIFYSOURCE_MANUAL = config_linkface.getString("linkface.verifySource_manual");
		LinkfaceConstant.VERIFYSOURCE_FACEPLUS = config_linkface.getString("linkface.verifySource_faceplus");
		LinkfaceConstant.VERIFYSOURCE_LINKFACE = config_linkface.getString("linkface.verifySource_linkface");
		LinkfaceConstant.FILEURL = config_linkface.getString("linkface.fileUrl");
		
		SOURCENAME.put(LinkfaceConstant.VERIFYSOURCE_MANUAL, "人工审核");
		SOURCENAME.put(LinkfaceConstant.VERIFYSOURCE_FACEPLUS, "face++认证");
		SOURCENAME.put(LinkfaceConstant.VERIFYSOURCE_LINKFACE, "商汤认证");
		
		SOURCECODE.put(LinkfaceConstant.VERIFYSOURCE_MANUAL, "990");
		SOURCECODE.put(LinkfaceConstant.VERIFYSOURCE_FACEPLUS, "991");
		SOURCECODE.put(LinkfaceConstant.VERIFYSOURCE_LINKFACE, "992");
	}
	
	/**
	 * 当前认证来源名称
	 * 
	 * @return
	 */
	public static String getCurrentVerifySourceName(){
		return SOURCENAME.get(LinkfaceConstant.VERIFYSOURCE_CURRENT);
	}
	
	public static String getCurrentVerifyCode(){
		return SOURCECODE.get(LinkfaceConstant.VERIFYSOURCE_CURRENT);
	}
}
