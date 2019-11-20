package com.waterelephant.third.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

@SuppressWarnings("unused")
public class HttpClientHelper {

	private static CloseableHttpClient client = null;

	@SuppressWarnings("deprecation")
	public static String doHttp(String urlStr, String charSet, Object parameters, String timeOut) throws Exception {
		String responseString = "";
		PostMethod xmlpost;
		int statusCode = 0;
		HttpClient httpclient = new HttpClient();
		httpclient.setConnectionTimeout(new Integer(timeOut).intValue());
		xmlpost = new PostMethod(urlStr);
		httpclient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charSet);
		try {
			// 组合请求参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Method[] ms = parameters.getClass().getMethods();
			for (int i = 0; i < ms.length; i++) {
				Method m = ms[i];
				String name = m.getName();
				if (name.startsWith("get")) {
					String param = name.substring(3, name.length());
					param = param.substring(0, 1).toLowerCase() + param.substring(1, param.length());
					if (param.equals("class")) {
						continue;
					}
					Object value = "";
					try {
						value = m.invoke(parameters, null);
						NameValuePair nvp = new NameValuePair(param, value.toString());
						list.add(nvp);
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					}
				}
			}
			NameValuePair[] nvps = new NameValuePair[list.size()];
			xmlpost.setRequestBody(list.toArray(nvps));
			statusCode = httpclient.executeMethod(xmlpost);
			responseString = xmlpost.getResponseBodyAsString();
			if (statusCode < HttpURLConnection.HTTP_OK || statusCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
				throw new Exception("请求接口失败，失败码[ " + statusCode + " ]");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return responseString;
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 * 
	 * @param url
	 * @param charset
	 * @param params
	 * @return
	 */
	public static String post(String url, String charset, Map<String, String> params) {
		String responseResult = null;
		// http://blog.csdn.net/wangpeng047/article/details/19624529
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建httppost
		HttpPost httppost = new HttpPost(url);

		// 设置超时时间
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(180000).setSocketTimeout(60000).build();
		// 创建参数队列
		List<org.apache.http.NameValuePair> formparams = new ArrayList<org.apache.http.NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, charset);
			httppost.setEntity(uefEntity);
			httppost.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseResult = EntityUtils.toString(entity, charset);
				} else {
					responseResult = null;
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseResult;
	}

	/**
	 * 发送 post请求请求service并返回json数据
	 * 
	 * @param url
	 * @param charset
	 * @param params
	 * @return
	 * 
	 * @author dengyan
	 */
	public static String post2(String url, String charset, Map<String, String> params) {
		String responseResult = null;
		// http://blog.csdn.net/wangpeng047/article/details/19624529
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建httppost
		HttpPost httppost = new HttpPost(url);
		// 创建参数队列
		List<org.apache.http.NameValuePair> formparams = new ArrayList<org.apache.http.NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, charset);
			httppost.setEntity(uefEntity);
			httppost.setHeader("Accept", "application/json");
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseResult = EntityUtils.toString(entity, charset);
				} else {
					responseResult = null;
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseResult;
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static String post(String url, String charset, String jsonStrData) {
		String responseResult = null;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建httppost
		HttpPost httppost = new HttpPost(url);

		try {
			HttpEntity entity_request = new StringEntity(jsonStrData, charset);
			httppost.setEntity(entity_request);
			httppost.setHeader("Content-type", "application/json");
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseResult = EntityUtils.toString(entity, charset);
					// System.out.println("响应结果:" + responseResult);
				} else {
					// System.out.println("无响应结果");
					responseResult = null;
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseResult;
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static String post(String url, String charset, Map<String, String> params, Map<String, String> headers) {
		String responseResult = null;
		// http://blog.csdn.net/wangpeng047/article/details/19624529
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();

		// 创建httppost
		HttpPost httppost = new HttpPost(url);

		// 设置请求头
		Set<String> keySet = headers.keySet();
		for (String key : keySet) {
			String value = headers.get(key);
			httppost.setHeader(key, value);
		}

		// 创建参数队列
		List<org.apache.http.NameValuePair> formparams = new ArrayList<org.apache.http.NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, charset);
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseResult = EntityUtils.toString(entity, charset);
				} else {
					responseResult = null;
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseResult;
	}

	/**
	 * get请求
	 * 
	 * @param url
	 * @param charset
	 * @param params
	 * @param headers
	 * @return
	 */
	public static String get(String url, String charset, Map<String, String> params, Map<String, String> headers) {
		String responseResult = null;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 设置get请求的参数
			StringBuffer getParams = new StringBuffer();
			if (params != null) {
				int getParamCount = 1;
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (getParamCount == 1) {
						getParams.append("?");
						getParams.append(entry.getKey() + "=" + entry.getValue());
					} else {
						getParams.append("&");
						getParams.append(entry.getKey() + "=" + entry.getValue());
					}
					getParamCount++;
				}
			}

			// 创建HttpGet
			url = url + getParams.toString();
			HttpGet httpGet = new HttpGet(url);

			// 设置请求头
			if (headers != null) {
				Set<String> keySet = headers.keySet();
				for (String key : keySet) {
					String value = headers.get(key);
					httpGet.setHeader(key, value);
				}
			}
			CloseableHttpResponse response = httpclient.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseResult = EntityUtils.toString(entity, charset);
				} else {
					responseResult = null;
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseResult;
	}

	/**
	 * 发送 post请求访问本地应用并根据传递参数不同返回不同结果
	 */
	public static String postFile(String url, String charset, Map<String, String> params) {
		String responseResult = null;
		// http://blog.csdn.net/wangpeng047/article/details/19624529
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(url);

		// 设置请求头
		httppost.setHeader("Content-Type", "multipart/form-data;name=\"file\"; boundary=image" + "111111111111");
		// httppost.setHeader("Content-Disposition", " form-data; name=\"file\"");
		// MultipartEntity entity = new MultipartEntity();
		// entity.addPart("param3", new FileBody(new File("C:\\1.txt")));

		// MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		// builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		// builder.addPart("upfile", fileBody);
		// builder.addPart("text1", stringBody1);
		// builder.addPart("text2", stringBody2);
		// HttpEntity entity = builder.build();
		//
		// httppost.setEntity(entity);

		// FileEntity fileEntity = new FileEntity(new File("C:\\20160815123459.png"));
		// httppost.setEntity(fileEntity);

		// 创建参数队列
		List<org.apache.http.NameValuePair> formparams = new ArrayList<org.apache.http.NameValuePair>();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, charset);
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseResult = EntityUtils.toString(entity, charset);
				} else {
					responseResult = null;
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseResult;
	}

}
