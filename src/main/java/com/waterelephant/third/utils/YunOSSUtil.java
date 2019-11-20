package com.waterelephant.third.utils;

import java.io.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

public class YunOSSUtil {

	private static Logger logger = LoggerFactory.getLogger(YunOSSUtil.class);

	/**
	 * 获取阿里云OSS客户端对象
	 * @return
	 */
	public static final OSSClient getOSSClient(){
		return new OSSClient(ThirdInterfaceConstant.YUNOSSURL, ThirdInterfaceConstant.ACCESS_KEY_ID, ThirdInterfaceConstant.ACCESS_KEY_SECRET);
	}
	
	/**
	 * 向阿里云的OSS存储中存储文件
	 * @param client
	 * @param file
	 * @param bucketName
	 * @param diskName
	 * @return
	 */
	public static boolean uploadObject2OSS(String base64Image, String bucketName, String diskName){
		String imageUrl = null;
		OSSClient client = getOSSClient();
		logger.info("uploadObject2OSS开始存储图片到阿里云");
		try{ 
			// Base64转化为输入流
			byte[] bytes = Base64.decode(base64Image);
			ByteArrayInputStream ins = new ByteArrayInputStream(bytes);
			Long fileSize = (long)bytes.length;
			//创建上传Object的Metadata
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(ins.available());
			metadata.setCacheControl("no-cache");
			metadata.setHeader("Pragma", "no-cache");
			metadata.setContentEncoding("utf-8");
			metadata.setContentType(getContentType(diskName));
			metadata.setContentDisposition("fileName/fileSize=" + diskName + "/" + fileSize + "Byte");
			
			//上传文件
			PutObjectResult putResult = client.putObject(bucketName, diskName, ins, metadata);
			ins.close();
			//解析结果
			String resultStr = putResult.getETag();
			logger.info("上传阿里云OSS成功，解析结果为：" + resultStr + "  " + fileSize);
			String httpIndex = ThirdInterfaceConstant.YUNOSSURL.substring(0,7);
			String endIndex = ThirdInterfaceConstant.YUNOSSURL.substring(7);
			imageUrl = httpIndex + bucketName + "." + endIndex + diskName;
			logger.info("图片访问地址为：" + imageUrl);
		}catch (Exception e) {
			logger.error("上传云服务器异常：" + e.getMessage(),e);
		}
		
		return true;
	}
	
	/**
	 * 通过文件名判断并获取OSS服务文件上传时文件的contentType 
	 * @param fileName
	 * @return
	 */
	public static final String getContentType(String fileName){
		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
		if("bmp".equalsIgnoreCase(fileExtension)){
			return "image/bmp";
		}
    	if("gif".equalsIgnoreCase(fileExtension)){
    		return "image/gif";
    	}
    	if("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension)  || "png".equalsIgnoreCase(fileExtension) ){;
    		return "image/jpeg";
    	}
    	if("html".equalsIgnoreCase(fileExtension)){
    		return "text/html";
    	}
    	if("txt".equalsIgnoreCase(fileExtension)){
    		return "text/plain";
    	}
    	if("vsd".equalsIgnoreCase(fileExtension)){
    		return "application/vnd.visio";
    	}
    	if("ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)){
    		return "application/vnd.ms-powerpoint";
    	}
    	if("doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)){
    		return "application/msword";
    	}
    	if("xml".equalsIgnoreCase(fileExtension)){
    		return "text/xml";
    	}
    	if("mp4".equalsIgnoreCase(fileExtension)){
    		return "video/mp4";
    	}
    	
    	return "text/html";
	}
	
}
