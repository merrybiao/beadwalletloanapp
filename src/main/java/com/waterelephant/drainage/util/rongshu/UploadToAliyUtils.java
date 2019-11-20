package com.waterelephant.drainage.util.rongshu;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.waterelephant.utils.CdnUploadTools;
import com.waterelephant.utils.CommUtils;

public class UploadToAliyUtils {
	
	private  static org.apache.log4j.Logger logger=org.apache.log4j.Logger.getLogger(UploadToAliyUtils.class);
	
	public static String base64Upload(String imgStr) {
		logger.info("start upload image to aliyun");
		String uploadPath = "";
		InputStream inputStream = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			int random = (int) (Math.random() * 1000000);
			uploadPath = "upload/backend/" + sdf.format(new Date()) + "/" + sdf2.format(new Date()) + random + ".jpg";
			// 从服务器返回一个输入流
			inputStream = Base64Image.generateImage(imgStr);
			
			if (CommUtils.isNull(inputStream)) {
				logger.info("inputStream is null");
			}
			else {
				// 上传至oss
				CdnUploadTools.uploadPic(inputStream, uploadPath);
			}
		}catch (Exception e) {
			logger.error("上传图片异常:", e);
		}finally {	
			if (!CommUtils.isNull(inputStream)) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("关闭字节流异常:", e);
				}
			}
		}

		return uploadPath;
	}
}