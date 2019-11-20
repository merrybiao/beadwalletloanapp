package com.waterelephant.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;

public class HttpClientUtil {
	private Logger logger = Logger.getLogger(HttpClientUtil.class);
	private HttpClient client = null;

	// 构造单例
	private HttpClientUtil() {

		MultiThreadedHttpConnectionManager httpConnectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		// 默认连接超时时间
		params.setConnectionTimeout(60000);
		// 默认读取超时时间
		params.setSoTimeout(60000);
		// 默认单个host最大连接数
		params.setDefaultMaxConnectionsPerHost(200);// very important!!
		// 最大总连接数
		params.setMaxTotalConnections(500);// very important!!
		httpConnectionManager.setParams(params);

		client = new HttpClient(httpConnectionManager);

		client.getParams().setConnectionManagerTimeout(3000);
		// client.getParams().setIntParameter("http.socket.timeout", 10000);
		// client.getParams().setIntParameter("http.connection.timeout", 5000);
	}

	private static class ClientUtilInstance {
		private static final HttpClientUtil ClientUtil = new HttpClientUtil();
	}

	public static HttpClientUtil getInstance() {
		return ClientUtilInstance.ClientUtil;
	}

	/**
	 * 发送http GET请求，并返回http响应流和文件名
	 * 
	 * @param urlstr
	 *            完整的请求url字符串
	 * @return
	 */
	public Object[] doGetRequest(String urlstr) {
		Object[] res = new Object[2];

		HttpMethod httpmethod = new GetMethod(urlstr);
		try {
			int statusCode = client.executeMethod(httpmethod);
			InputStream _InputStream = null;
			String filename = null;
			if (statusCode == HttpStatus.SC_OK) {
				_InputStream = httpmethod.getResponseBodyAsStream();
				String disposition = httpmethod.getResponseHeader("Content-disposition").getValue();
				filename = disposition.substring(disposition.indexOf("filename") + 8 + 2, disposition.length() - 1);
			}
			res[0] = _InputStream;
			res[1] = filename;
		} catch (HttpException e) {
			logger.error("获取响应错误，原因：" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("获取响应错误，原因：" + e.getMessage());
			e.printStackTrace();
		} finally {
			//httpmethod.releaseConnection();
		}
		return res;
	}

}
