package com.waterelephant.gxboss.constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;

/**
 * 阿里云oss流式下载文件
 *  *  *  * @author Administrator
 *
 */
public class OSSUtil {
	
	private static Logger logger = LoggerFactory.getLogger(OSSUtil.class);
	
	public static JSONObject downLoadOssFile(String url)  {
		BufferedReader reader = null;
		OSSClient ossClient = null;
		JSONObject respjson = null;
		try {
			ossClient = new OSSClient(AliyunOss.endpoint, AliyunOss.accessKeyId, AliyunOss.accessKeySecret);
			OSSObject ossObject = ossClient.getObject(new GetObjectRequest(AliyunOss.bucket,url));
			reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));				
		    String respStr = IOUtils.toString(reader);
		    respjson = JSONObject.parseObject(respStr);
			} catch (IOException e) {
				logger.error("从OSS下载文件{}失败，异常信息为：{}",url,e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					reader.close();
					ossClient.shutdown();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return respjson;	
	}
	
	public static void main(String[] args) {
		OSSUtil.downLoadOssFile("");
	}
}
