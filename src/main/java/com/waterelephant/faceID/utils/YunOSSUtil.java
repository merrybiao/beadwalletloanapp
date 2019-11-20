package com.waterelephant.faceID.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * 向aliyun的OSS存储文件
 * @author dengyan
 *
 */
public class YunOSSUtil {

	private static Logger logger = LoggerFactory.getLogger(YunOSSUtil.class);
	
	/**
	 * 获取阿里云OSS客户端对象
	 * @return
	 */
	public static final OSSClient getOSSClient(){
		return new OSSClient(FaceIDConstant.YUNOSSURL, FaceIDConstant.ACCESS_KEY_ID, FaceIDConstant.ACCESS_KEY_SECRET);
	}
	
//	/**
//	 * 新建Bucket --Bucket权限：私有
//	 * @param client
//	 * @param bucketName
//	 * @return
//	 */
//	public static final boolean createBucket(OSSClient client, String bucketName){
//		Bucket bucket = client.createBucket(bucketName);
//		return bucketName.equals(bucket.getName());
//	}
//	
//	/**
//	 * 删除Bucket
//	 * @param client
//	 * @param bucketName
//	 */
//	public static final void deleteBucket(OSSClient client, String bucketName){
//		client.deleteBucket(bucketName);
//		logger.info("删除" + bucketName + "成功");
//	}
	
	/**
	 * 向阿里云的OSS存储中存储文件
	 * @param client
	 * @param file
	 * @param bucketName
	 * @param diskName
	 * @return
	 */
	public static boolean uploadObject2OSS(String imagePath, String bucketName, String diskName){
		String imageUrl = null;
		OSSClient client = getOSSClient();
		try{ 
			//将图片路径转化为文件
			File file = new File(imagePath);
			//将文件传给输入流
			InputStream ins = new FileInputStream(file);
			Long fileSize = file.length();
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
			String httpIndex = FaceIDConstant.YUNOSSURL.substring(0,7);
			String endIndex = FaceIDConstant.YUNOSSURL.substring(7);
			imageUrl = httpIndex + bucketName + "." + endIndex + diskName;
			logger.info("图片访问地址为：" + imageUrl);
		}catch (Exception e) {
			logger.error("上传云服务器异常：" + e.getMessage(),e);
		}
		
		return true;
	}
	
//	/**
//	 * 根据key获取OSS服务器的文件输入流
//	 * @param client
//	 * @param bucketName
//	 * @param diskName
//	 * @param key
//	 * @return
//	 */
//	public static final InputStream getOSS2InputStream(OSSClient client, String bucketName, String diskName, String key){
//		OSSObject ossObject = client.getObject(bucketName, diskName + key);
//		return ossObject.getObjectContent();
//	}
//	
//	/**
//	 * 根据key删除OSS服务器上文件
//	 * @param client
//	 * @param bucketName
//	 * @param diskName
//	 * @param key
//	 */
//	public static void deleteFile(OSSClient client, String bucketName, String diskName, String key){
//		client.deleteObject(bucketName, diskName + key);
//		logger.info("删除" + bucketName + "成功");
//	}
	
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
	
//	public static String getImageCompress(String imagePath){
//		int widthdist=0,
//				heightdist=0;
//		try {
//			File imageFile = new File(imagePath);
//			if (!imageFile.exists()) {
//				return imagePath;
//			}
//			
//			// 获取文件高度和宽度  
//            int[] results = getImgWidth(imageFile);  
//            if (results == null || results[0] == 0 || results[1] == 0) {  
//                return imagePath;  
//            } else {  
//                widthdist = (int) (results[0] * 0.5);  
//                heightdist = (int) (results[1] * 0.5);  
//            }
//            
//         // 开始读取文件并进行压缩  
//            Image src = javax.imageio.ImageIO.read(imageFile);  
//            BufferedImage tag = new BufferedImage(widthdist,  
//                    heightdist, BufferedImage.TYPE_INT_RGB);  
//  
//            tag.getGraphics().drawImage(  
//                    src.getScaledInstance(widthdist, heightdist,  
//                            Image.SCALE_SMOOTH), 0, 0, null);
//  
//            FileOutputStream out = new FileOutputStream(imagePath);  
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); 
//            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
//            jep.setQuality((float)1.0, true);
//            encoder.encode(tag, jep);  
//            out.close();  
//		}catch(Exception e) {
//			logger.error("压缩图片异常：",e);
//		}
//		
//		return imagePath;
//	}
	
//	/**
//	 * 获取图片
//	 * @param file
//	 * @return
//	 */
//	public static int[] getImgWidth(File file) {  
//        InputStream is = null;  
//        BufferedImage src = null;  
//        int result[] = { 0, 0 };  
//        try {  
//            is = new FileInputStream(file);  
//            src = javax.imageio.ImageIO.read(is);  
//            result[0] = src.getWidth(null); // 得到源图宽  
//            result[1] = src.getHeight(null); // 得到源图高  
//            is.close();  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//        return result;  
//    } 
	
	
	
//	/**
//	 * 测试工具类
//	 * @param args
//	 */
//	public static void main(String[] args){
//		String imagePath = "D:/video/968.jpg";
////		String bucketName = FaceIDConstant.BACKETNAME;
////		String diskName = "upload/backend/2017-04-20/201704200054220965_3756.jpg";
////		uploadObject2OSS(imagPath, bucketName, diskName);
//		
////		getImageCompress(imagePath);
//	}
	
}
