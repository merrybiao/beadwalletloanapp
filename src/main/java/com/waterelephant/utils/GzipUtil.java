package com.waterelephant.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 * Gzip 压缩、解压工具类
 * @author dinglinhao
 * @date 2018年8月22日17:30:47
 */
public class GzipUtil {
	
	public static String gzip(String text) {
		String gzip = null;
		try {
			byte[] gzipBytes = gzip(text.getBytes(Charset.defaultCharset()));
			gzip = byteToHexString(gzipBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gzip;
	}
	
	public static String unGzip(String gzip) {
		byte[] bytes = hexToByteArray(gzip);
		String text = null;
		try {
			byte[] unGzipBytes = unGzip(bytes);
			text = new String(unGzipBytes, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	private static byte[] gzip(byte[] content) throws IOException{
		byte[] result = null;
		ByteArrayOutputStream out = null;
		GZIPOutputStream gzipOut = null;
		ByteArrayInputStream byteIn = null;
		try {
			out =new ByteArrayOutputStream();
			gzipOut=new GZIPOutputStream(out);
			byteIn=new ByteArrayInputStream(content);

			byte[] buffer=new byte[1024];
			int n;
			while((n=byteIn.read(buffer))!=-1){
				gzipOut.write(buffer, 0, n);
			}
			if(null != gzipOut) {
				gzipOut.flush();
				gzipOut.close();
			}
			result =  out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != byteIn)byteIn.close();
			if(null != out)out.close();
		}
		return result;
	}
	
	private static byte[] unGzip(byte[] content) throws IOException{
		
		byte[] result = null;
		ByteArrayOutputStream out = null;
		GZIPInputStream gzipIn = null;
		try {
			out = new ByteArrayOutputStream();
			gzipIn = new GZIPInputStream(new ByteArrayInputStream(content));
			byte[] buffer=new byte[1024];
			int n;
			while((n = gzipIn.read(buffer))!=-1){
				out.write(buffer, 0, n);
			}
			out.flush();
			result = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != gzipIn)gzipIn.close();
			if(null != out)out.close();
		}
		return result;
	}
	

	/** 
	 * 字节数组转16进制 
	 * @param bytes 需要转换的byte数组 
	 * @return  转换后的Hex字符串 
	 */  
	private static String byteToHexString(byte[] bytes) {  
	    StringBuffer sb = new StringBuffer();  
	    for(int i = 0; i < bytes.length; i++) {  
	        String hex = Integer.toHexString(bytes[i] & 0xFF);  
	        if(hex.length() < 2){  
	            sb.append(0);  
	        }  
	        sb.append(hex);  
	    }  
	    return sb.toString();  
	} 
	
	/** 
	 * Hex字符串转byte 
	 * @param inHex 待转换的Hex字符串 
	 * @return  转换后的byte 
	 */  
	private static byte hexToByte(String inHex){  
	   return (byte)Integer.parseInt(inHex,16);  
	} 

	/** 
	 * hex字符串转byte数组 
	 * @param inHex 待转换的Hex字符串 
	 * @return  转换后的byte数组结果 
	 */  
	private static byte[] hexToByteArray(String inHex){  
	    int hexlen = inHex.length();  
	    byte[] result;  
	    if (hexlen % 2 == 1){  
	        //奇数  
	        hexlen++;  
	        result = new byte[(hexlen/2)];  
	        inHex="0"+inHex;  
	    }else {  
	        //偶数  
	        result = new byte[(hexlen/2)];  
	    }  
	    int j=0;  
	    for (int i = 0; i < hexlen; i+=2){  
	        result[j]=hexToByte(inHex.substring(i,i+2));  
	        j++;  
	    }  
	    return result;   
	} 
}
