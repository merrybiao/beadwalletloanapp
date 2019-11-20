package com.waterelephant.drainage.baidu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.beadwallet.utils.CommUtils;
import com.waterelephant.drainage.baidu.entity.SignEntity;
import com.waterelephant.drainage.baidu.entity.UserEntity;

public class BaiduUtil {

	public static String checkSignEntity(SignEntity req) {
		String result = null;

		if (req == null) {
			return "签约推送数据内容为空";
		}

		if (CommUtils.isNull(req.getUser_info())) {
			return "签约推送用户信息为空";
		}

		if (StringUtils.isBlank(req.getSign())) {
			return "签约推送签名为空";
		}

		return result;
	}

	/**
	 * 如果接收参数有int类型的0请勿使用此方法验证
	 * 
	 * @param entity
	 * @return
	 */
	// 校验参数是否为空
	@SuppressWarnings("rawtypes")
	public static <T> String checkClazz(T entity) {
		// 遍历出所有属性
		try {
			// 获取实体类的所有属性并返回field数组
			Field[] field = entity.getClass().getDeclaredFields();
			for (int i = 0; i < field.length; i++) {
				String name = field[i].getName(); // 获取属性名称
				name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性名字首字母大写
				String type = field[i].getGenericType().toString();
				if (type.equals("class java.lang.String")) {
					Method m = entity.getClass().getMethod("get" + name); // 调用get方法获取属性值
					String value = (String) m.invoke(entity);
					if (StringUtils.isEmpty(value)) {
						return name + "不能为空";
					}
				}
				if (type.equals("int")) {
					Method m = entity.getClass().getMethod("get" + name); // 调用get方法获取属性值
					Integer value = (Integer) m.invoke(entity);
					if (value == 0) {
						return name + "不能为空";
					}
				}
			}
			// 判断是否存在父类
			if (entity.getClass().getGenericSuperclass() != null) {
				Class<?> parentClass = entity.getClass().getSuperclass();
				Field[] parentField = parentClass.getDeclaredFields();
				for (int i = 0; i < parentField.length; i++) {
					String name = parentField[i].getName(); // 获取属性名称
					name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性名字首字母大写
					String type = parentField[i].getGenericType().toString();
					if (type.equals("class java.lang.String")) {
						Method m = parentClass.getMethod("get" + name); // 调用get方法获取属性值
						String value = (String) m.invoke(entity);
						if (StringUtils.isEmpty(value)) {
							return name + "不能为空";
						}
					}
					if (type.equals("int")) {
						Method m = parentClass.getMethod("get" + name); // 调用get方法获取属性值
						Integer value = (Integer) m.invoke(entity);
						if (value == 0) {
							return name + "不能为空";
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		UserEntity user = new UserEntity();
		user.setUser_name("123");
		user.setEvent("123");
		user.setId_card("123");
		user.setMobile("123");
		user.setSign("123");
		String value = checkClazz(user);
		System.out.println(value);
	}

	public static String checkPhone(String phone) {
		if (phone == null) {
			return phone;
		}

		if (isChinaPhoneLegal(phone)) {
			return phone;
		}

		phone = phone.replaceAll("\\D", "");

		if (phone.length() < 2) {
			return phone;
		}

		if ("86".equals(phone.substring(0, 2))) {
			phone = phone.substring(2);
		}
		return phone;
	}

	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 模拟请求
	 * 
	 * @param url 资源地址
	 * @param map 参数列表
	 * @param encoding 编码
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String send(String url, Map<String, String> map, String encoding) throws ParseException, IOException {
		String body = "";

		// 创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		// 创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		// 装填参数
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (map != null) {
			for (Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		// 设置参数到请求对象中
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		System.out.println("请求地址：" + url);
		System.out.println("请求参数：" + nvps.toString());

		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		// 获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// 按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		// 释放链接
		response.close();
		return body;
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url 发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
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
}
