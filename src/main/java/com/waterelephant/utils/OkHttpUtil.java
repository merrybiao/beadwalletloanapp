package com.waterelephant.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttp替代HttpClient，推荐使用
 *
 * @author xanthuim
 * @since 2018-07-18
 */
public class OkHttpUtil {

    /**
     * 请求过程中出现的异常
     */
    public static class OkHttpException extends RuntimeException {

        public OkHttpException(Throwable cause) {
            super(cause);
        }

        public OkHttpException(String message) {
            super(message);
        }
    }

    public enum Type {
        /**
         * 所有类型
         */
        ALL("*/*"),
        /**
         * Key=Value
         */
        URL_ENCODED("application/x-www-form-urlencoded; charset=utf-8"),
        /**
         * JSON
         */
        JSON("application/json; charset=utf-8");

        /**
         * MediaType
         */
        private MediaType type;

        Type(String value) {
            this.type = MediaType.parse(value);
        }

        public MediaType getType() {
            return type;
        }
    }

    /**
     * 请求方式，常用四种方式，推荐遵循RESTful风格，使用合适的请求方式
     */
    public enum RequestMethod {
        /**
         * get请求
         */
        GET,
        /**
         * post请求
         */
        POST,
        /**
         * put请求
         */
        PUT,
        /**
         * delete请求
         */
        DELETE
    }

    /**
     * 超时时间，单位秒
     */
    private static final int OVER_TIME = 30;
    private static OkHttpClient.Builder builder;

    private OkHttpUtil() {
    }

    /**
     * 构建，调试模式
     *
     * @param debug
     * @return
     */
    public static OkHttpUtil builder(boolean debug) {
        new Builder(debug);
        return new OkHttpUtil();
    }

    /**
     * 构建模式
     */
    private static class Builder {

        public Builder(boolean debug) {
            builderOkHttp(debug);
        }

        /**
         * 构建OkHttp
         */
        private static void builderOkHttp(boolean debug) {
            //日志拦截器，用来监控OkHttp请求的信息。开发、测试环境下添加日志
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            builder = new OkHttpClient().newBuilder()
                    .writeTimeout(OVER_TIME, TimeUnit.SECONDS)
                    .readTimeout(OVER_TIME, TimeUnit.SECONDS)
                    .connectTimeout(OVER_TIME, TimeUnit.SECONDS);
            if (debug) {
                builder.addInterceptor(loggingInterceptor);
            }
        }
    }


    /**
     * 发送的参数是JSON，即ContentType为application/json
     *
     * @param url  地址
     * @param json 请求参数
     * @return 响应结果
     */
    public static String post(String url, String json) {
        return execute(RequestMethod.POST, url, Type.JSON, json);
    }

    /**
     * 发送的参数是Key=Value，即ContentType为application/x-www-form-urlencoded
     *
     * @param url    地址
     * @param params 请求参数
     * @return 响应结果
     */
    public static String post(String url, Map<String, Object> params) {
        return execute(RequestMethod.POST, url, Type.URL_ENCODED, params(params));
    }

    /**
     * GET请求
     *
     * @param url 地址
     * @return 响应结果
     */
    public static String get(String url) {
        return execute(RequestMethod.GET, url, null, null);
    }

    /**
     * 获取流数据
     *
     * @param url 地址
     * @return 流
     */
    public static InputStream getStream(String url) {
        return execute(url);
    }

    /**
     * 执行请求
     *
     * @param method 请求方式
     * @param url    地址
     * @param type   Content-Type {@link Type}
     * @param json   JSON串
     * @return 响应结果
     */
    private static String execute(RequestMethod method, String url, Type type, String json) {
        return string(doMethod(method, url, type, json));
    }

    private static Request doMethod(RequestMethod method, String url, Type type, String json) {
        Request.Builder builder = new Request.Builder().url(url);
        Request request;
        switch (method) {
            case GET:
                request = builder.build();
                break;
            case POST:
                request = builder.post(RequestBody.create(type.getType(), json)).build();
                break;
            case PUT:
                request = builder.put(RequestBody.create(type.getType(), json)).build();
                break;
            case DELETE:
                if (Objects.isNull(json) || json.trim().isEmpty()) {
                    request = builder.delete().build();
                } else {
                    request = builder.delete(RequestBody.create(type.getType(), json)).build();
                }
                break;
            default:
                throw new RuntimeException("还不支持该请求方式，敬请期待！");
        }
        return request;
    }

    /**
     * 执行请求，返回流数据
     *
     * @param url 请求地址
     * @return 流
     */
    private static InputStream execute(String url) {
        Request request = new Request.Builder().url(url).build();
        return stream(request);
    }

    /**
     * 同步执行
     * <p>
     * 如果需要监控日志，请使用{@link OkHttpUtil.Builder}
     *
     * @param request OkHttp请求对象
     * @return 响应结果
     */
    private static ResponseBody responseBody(Request request) {
        createBuilder();
        try {
            Response response = builder.build().newCall(request).execute();
            if (response.code() >= HttpURLConnection.HTTP_OK
                    && response.code() < HttpURLConnection.HTTP_BAD_REQUEST) {
                return response.body();
            } else if (response.code() >= HttpURLConnection.HTTP_BAD_REQUEST
                    && response.code() < HttpURLConnection.HTTP_INTERNAL_ERROR) {
                throw new RuntimeException("客户端错误：" + response.toString());
            } else if (response.code() >= HttpURLConnection.HTTP_INTERNAL_ERROR
                    && response.code() < HttpURLConnection.HTTP_VERSION) {
                throw new RuntimeException("服务端错误：" + response.toString());
            }
            return null;
        } catch (IOException e) {
            throw new OkHttpException(e);
        }
    }

    /**
     * 为null就创建，但是没有日志
     */
    private static void createBuilder() {
        if (builder == null) {
            Builder.builderOkHttp(false);
        }
    }

    /**
     * 返回字符串
     *
     * @param request OkHttp请求对象
     * @return 字符串
     */
    private static String string(Request request) {
        try {
            return responseBody(request).string();
        } catch (IOException e) {
            throw new OkHttpException("转换字符串错误：" + e);
        }
    }

    /**
     * 返回输入流
     *
     * @param request OkHttp请求对象
     * @return 流
     */
    private static InputStream stream(Request request) {
        return responseBody(request).byteStream();
    }

    /**
     * 异步执行
     * <p>
     * 如果需要监控日志，请使用{@link OkHttpUtil.Builder}
     *
     * @param request
     * @param responseCallback
     */
    private static void asyncResponseBody(Request request, Callback responseCallback) {
        createBuilder();
        builder.build().newCall(request).enqueue(responseCallback);
    }

    /**
     * 异步post请求
     *
     * @param url
     * @param json
     * @param responseCallback
     */
    public static void asyncPost(String url, String json, Callback responseCallback) {
        asyncResponseBody(doMethod(RequestMethod.POST, url, Type.JSON, json), responseCallback);
    }

    public static void asyncPost(String url, Map<String, Object> params, Callback responseCallback) {
        asyncResponseBody(doMethod(RequestMethod.POST, url, Type.URL_ENCODED, params(params)), responseCallback);
    }


    /**
     * 异步get请求
     *
     * @param url
     * @param responseCallback
     */
    public static void asyncGet(String url, Callback responseCallback) {
        asyncResponseBody(doMethod(RequestMethod.GET, url, null, null), responseCallback);
    }

    private static String params(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        for (final Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
