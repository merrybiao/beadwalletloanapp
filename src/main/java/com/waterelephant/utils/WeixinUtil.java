package com.waterelephant.utils;

import java.awt.Menu;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.waterelephant.entity.UserInfo;
import com.waterelephant.vo.AccessToken;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 公众平台通用接口工具类
 * 
 * @author HWang
 *
 */
public class WeixinUtil {
	private static Logger log = Logger.getLogger(WeixinUtil.class);
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 菜单创建（POST） 限100（次/天）
	public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 获取用户信息的接口地址（GET） 限200（次/天）
	public final static String ACCESS_TOKEN_USER_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// 获得jsapi_ticket
	public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	// 用户授权URL
	public final static String USER_OAUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
	// 获取用户详细信息URL（网页授权）
	public final static String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 获取用户详细信息URL（UnionID机制）
	public final static String UNION_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	// 下载媒体文件
	public final static String DOWNLAOD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}"
						+ jsonObject.getInt("errcode")
						+ jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
	
	/**
	 * 从Redis中获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessTokenFromRedis() {
		AccessToken accessToken = null;
		String token_json = RedisUtils.get("weixin:token");
		if (StringUtils.isNotBlank(token_json)) {
			accessToken = JsonUtils.fromJson(token_json, AccessToken.class);
		}
		return accessToken;
	}

	/**
	 * 获取access_token以及openid
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @param code
	 *            凭证
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret,
			String code) {
		AccessToken accessToken = null;
		String requestUrl = ACCESS_TOKEN_USER_URL.replace("APPID", appid)
				.replace("SECRET", appsecret).replace("CODE", code);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
				accessToken.setOpenid(jsonObject.getString("openid"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}"
						+ jsonObject.getInt("errcode")
						+ jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 获取jsapiTicket
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getJsapiTicket(String accessToken) {
		String ticket = null;
		String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN",
				accessToken);
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		if (null != jsonObject) {
			ticket = jsonObject.getString("ticket");
		}
		return ticket;
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}"
						+ jsonObject.getInt("errcode")
						+ jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	/**
	 * 获取用户授权URL
	 * 
	 * @param url
	 * @param scope
	 * @return
	 */
	public static String getUserOauthUrl(String url, String scope) {
		String userOauthUrl = USER_OAUTH_URL
				.replace("APPID", SystemConstant.APPID)
				.replace("REDIRECT_URI", url).replace("SCOPE", scope)
				.replace("STATE", "123");
		return userOauthUrl;
	}

	/**
	 * 获取用户详细信息(网页授权)
	 * 
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public static UserInfo getUserInfo(String accessToken, String openid) {
		UserInfo userInfo = new UserInfo();
		userInfo.setOpenid(openid);
		try {
			String requestUrl = USER_INFO_URL.replace("ACCESS_TOKEN",
					accessToken).replace("OPENID", openid);
			// 调用接口创建菜单
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			if (null != jsonObject) {
				String nickname = jsonObject.getString("nickname");
				String sex = jsonObject.getString("sex");
				String province = jsonObject.getString("province");
				String city = jsonObject.getString("city");
				String country = jsonObject.getString("country");
				String headimgurl = jsonObject.getString("headimgurl");
				userInfo.setCity(city);
				userInfo.setCountry(country);
				userInfo.setHeadimgurl(headimgurl);
				userInfo.setNickname(filterEmoji(nickname));
				userInfo.setProvince(province);
				userInfo.setSex(sex);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return userInfo;
	}

	/**
	 * 获取用户详细信息（UnionID机制）
	 * 
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	public static UserInfo getUnionUserInfo(String accessToken, String openid) {
		UserInfo userInfo = new UserInfo();
		userInfo.setOpenid(openid);
		try {
			String requestUrl = UNION_USER_INFO_URL.replace("ACCESS_TOKEN",
					accessToken).replace("OPENID", openid);
			// 调用接口创建菜单
			JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
			if (null != jsonObject) {
				String nickname = jsonObject.getString("nickname");
				String sex = jsonObject.getString("sex");
				String province = jsonObject.getString("province");
				String city = jsonObject.getString("city");
				String country = jsonObject.getString("country");
				String headimgurl = jsonObject.getString("headimgurl");
				userInfo.setCity(city);
				userInfo.setCountry(country);
				userInfo.setHeadimgurl(headimgurl);
				userInfo.setNickname(filterEmoji(nickname));
				userInfo.setProvince(province);
				userInfo.setSex(sex);
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return userInfo;
	}

	/**
	 * 过滤emoji表情
	 * 
	 * @param source
	 * @return
	 */
	private static String filterEmoji(String source) {
		if (source != null && !source.equals("")) {
			return source.replaceAll(
					"[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
		} else {
			return source;
		}
	}
	
	/**
	 * 下载多媒体文件
	 * @param accessToken
	 * @param mediaId
	 * @return
	 * @throws Exception 
	 */
	public static Object[] downloadMedia(String mediaId) {
		String requestUrl = DOWNLAOD_MEDIA_URL.replace("ACCESS_TOKEN", WeixinUtil.getAccessTokenFromRedis().getToken())
				.replace("MEDIA_ID", mediaId);
		return HttpClientUtil.getInstance().doGetRequest(requestUrl);
	}
	
}
