package com.waterelephant.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;

public final class AliyunOSSUtil {
	
	private static final String endpoint = "https://oss-cn-shanghai.aliyuncs.com";
	
	private static final String accessKeyId = "LTAI9aq8YCa7XDmB";
	
	private static final String secretAccessKey = "dKjVZlPw5ANWsErjcyRDHx9vLushIx";
	
	private static final String bucketName = "waterelephant";
	
	private static OSS client;
	
	private static OSS getClient() {
		if(null == client)
			client= new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey);
		return client;
	}
	
	public static String getObject(String filePath) {
		
		String content = null;
		InputStream inputStream = null;
		try {
			String key = "";
			if(filePath.startsWith(bucketName)) {
				key = filePath.substring(bucketName.length()+1);
			}else {
				key = filePath;
			}
			OSSObject object = getClient().getObject(bucketName, key);
			inputStream = object.getObjectContent();
			content = IOUtils.toString(inputStream, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != inputStream) inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
	
	public static String putString(String filePath,String content) {
		InputStream inputStream = null;
		try {
			String key = "";
			if(filePath.startsWith(bucketName)) {
				key = filePath.substring(bucketName.length()+1);
			}else {
				key = filePath;
			}
			inputStream = IOUtils.toInputStream(content, "utf-8");
			PutObjectResult result = getClient().putObject(bucketName, key, inputStream);
			return result.getETag();
		} catch (OSSException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(null != inputStream) inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
