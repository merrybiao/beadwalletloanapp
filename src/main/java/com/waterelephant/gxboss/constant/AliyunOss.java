package com.waterelephant.gxboss.constant;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliyunOss {
	
	private static Logger logger = LoggerFactory.getLogger(AliyunOss.class);
	
	private AliyunOss() {};
	
	public static String endpoint;
	
	public static String accessKeyId;
	
	public static String accessKeySecret;
	
	public static String bucket;
	
	public static String folder;
	
	
	static {
		
		ResourceBundle oss = ResourceBundle.getBundle("aliyunoss");
		
		try {
			
			endpoint = oss.getString("oss.endpoint");
			
			accessKeyId = oss.getString("oss.access.key.id");
			 
			accessKeySecret = oss.getString("oss.access.key.secret");
			 
			bucket = oss.getString("oss.bucket");
			 
			folder = oss.getString("oss.auth.folder");
		} catch (Exception e) {
			logger.error("~阿里云的aliyunoss配置文件出现问题，缺少key或值~");
		}
	}
}
