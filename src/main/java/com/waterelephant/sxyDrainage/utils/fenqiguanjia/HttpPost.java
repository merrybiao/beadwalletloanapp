//package com.waterelephant.sxyDrainage.utils.fenqiguanjia;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.alibaba.fastjson.JSONObject;
//
//import okhttp3.Interceptor;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class HttpPost {
//
//	private static final Logger LOGGER = LoggerFactory.getLogger(HttpPost.class);
//
//	public static final MediaType JSON_FORMAT = MediaType.parse("application/json; charset=utf-8");
//
//	private OkHttpClient client;
//
//	public HttpPost() {
//		buildHttpClient();
//	}
//
//	private void buildHttpClient() {
//		this.client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//			@Override
//			public Response intercept(Chain chain) throws IOException {
//				long t1 = System.nanoTime();
//				Request request = chain.request();
//				Response response = chain.proceed(request);
//				long t2 = System.nanoTime();
//				LOGGER.info(String.format("Received response for %s in %.1fms%n%s", request.url(), (t2 - t1) / 1e6d,
//						response.headers()));
//				return response;
//			}
//		}).connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
//				.build();
//	}
//
//	public CallerResponse synchronousPost(String url, String privateKey, RequestParams requestParams) {
//		CallerResponse callerResponse = new CallerResponse();
//		Request request = createRequest(url, privateKey, requestParams);
//		try {
//			Response response = client.newCall(request).execute();
//			LOGGER.error("third response:{}", response);
//			if (response.isSuccessful()) {
//				String responseBodyString = response.body().string();
//
//				LOGGER.info("third responseBodyString:{}", responseBodyString);
//
//				JSONObject jsonObject = JSONObject.parseObject(responseBodyString);
//				callerResponse.putAll(jsonObject);
//			} else {
//				callerResponse.setError(BasicErrorCodeEnum.PARAM_RESOLVE_ERROR.getCode());
//				callerResponse.setMsg(BasicErrorCodeEnum.PARAM_RESOLVE_ERROR.getMsg());
//				callerResponse.put("error msg", response.toString());
//				LOGGER.error("http调用返回数据解析异常{}", response);
//				response.close();
//			}
//		} catch (IOException e) {
//			callerResponse.setError(BasicErrorCodeEnum.UNKNOW_ERROR.getCode());
//			callerResponse.setMsg(BasicErrorCodeEnum.UNKNOW_ERROR.getMsg());
//			callerResponse.put("exception msg", e.toString());
//
//			LOGGER.error("http调用异常{}", e);
//			return callerResponse;
//		}
//		return callerResponse;
//	}
//
//	private Request createRequest(String url, String privateKey, RequestParams requestParams) {
//		Map<String, String> sortedParams = requestParams.getParams();
//
//		int size = sortedParams.size();
//		int count = 0;
//		StringBuffer buffer = new StringBuffer();
//		for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
//			count++;
//			String key = entry.getKey();
//			String value = entry.getValue();
//			if (null == value) {
//				continue;
//			}
//			buffer.append(key).append("=").append(value).append("&");
//		}
//		int length = buffer.length();
//		buffer.delete(length - 1, length);
//
//		LOGGER.info("签名str:{}", buffer.toString());
//
//		String sign = KeyReader.generateSHA1withRSASigature(buffer.toString(), privateKey);
//		sortedParams.put("sign", sign);
//		String requestBody = JSONObject.toJSONString(sortedParams);
//		LOGGER.info("发送请求url:{},body:{}", url, requestBody);
//		RequestBody body = RequestBody.create(JSON_FORMAT, requestBody);
//		return new Request.Builder().url(url).post(body).build();
//	}
//}
