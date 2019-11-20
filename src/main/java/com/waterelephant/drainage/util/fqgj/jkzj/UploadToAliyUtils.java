package com.waterelephant.drainage.util.fqgj.jkzj;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.waterelephant.rong360.util.LogUtil;
import com.waterelephant.utils.CdnUploadTools;
import com.waterelephant.utils.CommUtils;

public class UploadToAliyUtils {
	private static LogUtil logger = new LogUtil(UploadToAliyUtils.class);

	/**
	 * 上传至oss
	 * 
	 * @param url
	 * @return
	 */
	public static String urlUploadNew(String url, String fileName) {
		logger.info("start upload zip to aliyun,url=" + url);
		String uploadPath = "";
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			uploadPath = "upload/backend/" + sdf.format(new Date()) + "/" + fileName;
			URL imageUrl = new URL(url);
			logger.info("load zip aliyun , uploadPath = " + uploadPath);
			httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
			// 设置网络连接超时时间
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(5000);
			// 设置应用程序要从网络连接读取数据
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("GET");
			int responseCode = httpURLConnection.getResponseCode();
			logger.info("connection responseCode:" + responseCode);
			if (responseCode == 200) {
				// 从服务器返回一个输入流
				inputStream = httpURLConnection.getInputStream();

				if (CommUtils.isNull(inputStream)) {
					logger.info("inputStream is null");
				} else {
					// 上传至oss
					CdnUploadTools.uploadPic(inputStream, uploadPath);
				}
			}
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			logger.error("上传图片异常:", e);
		} catch (IOException e) {
			logger.error("上传图片异常:", e);
		} finally {
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

	/**
	 * 上传至oss
	 * 
	 * @param url
	 * @return
	 */
	public static String urlUpload(String url, String uploadPath) {
		logger.info("start upload image to aliyun,url=" + url);
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL imageUrl = new URL(url);
			httpURLConnection = (HttpURLConnection) imageUrl.openConnection();
			// 设置网络连接超时时间
			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setReadTimeout(5000);
			// 设置应用程序要从网络连接读取数据
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("GET");
			int responseCode = httpURLConnection.getResponseCode();
			logger.info("connection responseCode:" + responseCode);
			if (responseCode == 200) {
				// 从服务器返回一个输入流
				inputStream = httpURLConnection.getInputStream();

				if (CommUtils.isNull(inputStream)) {
					logger.info("inputStream is null");
				} else {
					// 上传至oss
					CdnUploadTools.uploadPic(inputStream, uploadPath);
				}
			}
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			logger.error("上传图片异常:", e);
		} catch (IOException e) {
			logger.error("上传图片异常:", e);
		} finally {
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

	/**
	 * 从服务器获得一个输入流（这里指的是获取img输入流）
	 * 
	 * 图pain路径
	 * 
	 * @return
	 */
	public static InputStream getInputStream(String imgUrl) {
		InputStream inputStream = null;
		HttpURLConnection httpURLConnection = null;
		try {
			URL url = new URL(imgUrl);
			httpURLConnection = (HttpURLConnection) url.openConnection();
			// 设置网络连接超时时间
			httpURLConnection.setConnectTimeout(3000);
			// 设置应用程序要从网络连接读取数据
			httpURLConnection.setDoInput(true);
			httpURLConnection.setRequestMethod("GET");
			int responseCode = httpURLConnection.getResponseCode();
			if (responseCode == 200) {
				// 从服务器返回一个输入流
				inputStream = httpURLConnection.getInputStream();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
}
