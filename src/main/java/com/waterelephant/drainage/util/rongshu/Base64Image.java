package com.waterelephant.drainage.util.rongshu;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
 
@SuppressWarnings("restriction")
public class Base64Image {
	
  private static Logger logger = Logger.getLogger(Base64Image.class);
  
  public static InputStream generateImage(String imgStr) {
	    if (imgStr == null){
	    	return null;
	    }
	    
	    imgStr = imgStr.replaceAll(" ", "\\+");
	    InputStream inputStream = null;
	    BASE64Decoder decoder = new BASE64Decoder();
	    try {
	      // Base64解码
	      byte[] bytes = decoder.decodeBuffer(imgStr);
	      for (int i = 0; i < bytes.length; ++i) {
	        if (bytes[i] < 0) {
	          bytes[i] += 256;
	        }
	      }
	      inputStream = new ByteArrayInputStream(bytes);
	    } catch (Exception e) {
	    	logger.info("base64转输入流失败:", e);
	    }
	    
	    return inputStream;
	  }
}