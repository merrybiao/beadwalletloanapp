//package com.waterelephant.sxyDrainage.utils.wacaiutils;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.wacai.WaCaiResponse;
//import com.waterelephant.utils.DoubleUtil;
//import okhttp3.*;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import javax.servlet.http.HttpServletRequest;
//import javax.xml.bind.DatatypeConverter;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/28
// * @since JDK 1.8
// */
//public class WaCaiUtil {
//    private static Logger logger = Logger.getLogger(WaCaiUtil.class);
//
//    /**
//     * 订单状态转换
//     */
//    private static Map<Long, Integer> ORDER_STATUS = new HashMap<>();
//    /**
//     * 订单状态转换
//     */
//    private static Map<Integer, Integer> PLAN_STATUS = new HashMap<>();
//
//    static {
//        // 我方，1:草稿，2:初审，3:终审，4:待签约，5:待放款，6:结束，7:拒绝，8:撤回，9:还款中，11:待生成合同，12:待债匹，13:逾期，14:债匹中，15:待商城用户确认
//        // 闪电贷， 13000 审核通过 ,13100 审核不通过 ,12000 资料提交成功（授信审核中） ,14000 签约成功 ,15100 放款成功 ,16000 (放款成功)待还款 ,16200 还款完成 ,16100 还款逾期
//        //ORDER_STATUS.put(1L, 12000);
//        ORDER_STATUS.put(2L, 12000);
//        ORDER_STATUS.put(3L, 12000);
//        ORDER_STATUS.put(4L, 13000);
//        ORDER_STATUS.put(6L, 16200);
//        ORDER_STATUS.put(7L, 13100);
//        ORDER_STATUS.put(8L, 13100);
//        ORDER_STATUS.put(9L, 16000);
//        ORDER_STATUS.put(11L, 14000);
//        ORDER_STATUS.put(12L, 15000);
//        ORDER_STATUS.put(13L, 16100);
//        ORDER_STATUS.put(14L, 15000);
//
//        // 我方还款状态：1 未还款 2 已还款 3 已垫付 4 展期 5取消'
//        // 还款状态 闪电贷 16000(放款成功)待还款 16002扣款失败 16100 还款逾期 16200 还款完成
//
//        PLAN_STATUS.put(1, 16000);
//        PLAN_STATUS.put(2, 16200);
//        PLAN_STATUS.put(3, 16100);
//    }
//
//    /**
//     * 订单状态转换
//     */
//    public static Integer statusConvert(Long statusId) {
//        if (statusId == null) {
//            return null;
//        }
//        return ORDER_STATUS.get(statusId);
//    }
//
//    /**
//     * 计划状态转换
//     */
//    public static Integer planConvert(Integer statusId) {
//        if (statusId == null) {
//            return null;
//        }
//        return PLAN_STATUS.get(statusId);
//    }
//
//    /**
//     * 数据过滤
//     *
//     * @param request 请求
//     * @param sessionId 时间戳
//     * @param interfaceMsg 接口信息
//     * @return 响应
//     */
//    public static WaCaiResponse filter(HttpServletRequest request, Long sessionId, String interfaceMsg) {
//
//        String body;
//        try {
//            body = IOUtils.toString(request.getInputStream(), "UTF-8");
//        } catch (IOException e) {
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "数据读取异常");
//        }
//        String sign = request.getHeader("Authorization");
//
//        logger.info("挖财>>>" + sessionId + "进入WaCaiController " + interfaceMsg + ",body:" + body/* (body.length() > 1000 ? "数据太多,不输出日志" : body) */ + ",sign" + sign);
//
//        if (StringUtils.isBlank(body)) {
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "请求参数RequestBody为空");
//        }
//
//        Map<String, String> parseSign = parseSign(sign);
//        if (parseSign == null) {
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "解析签名为空");
//        }
//
//        String signNew = parseSign.get("sign");
//        String appId = parseSign.get("credential");
//        if (StringUtils.isBlank(signNew)) {
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "签名sign为空");
//        }
//        if (!WaCaiConstant.APP_ID.equals(appId)) {
//            return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "AppID不匹配");
//        }
//        // if (!checkSign(body, sign)) {
//        // return new WaCaiResponse(WaCaiResponse.CODE_FAIL, "验签错误");
//        // }
//        return new WaCaiResponse(null, body);
//
//    }
//
//    /**
//     * 获得签名
//     *
//     * @param sign 消息头 签名
//     * @return 返回
//     */
//    private static Map<String, String> parseSign(String sign) {
//        try {
//            Map<String, String> map = new HashMap<>(2);
//            String[] split = sign.split(" ");
//            String credential = split[1].substring(split[1].lastIndexOf("=") + 1, split[1].lastIndexOf(","));
//            String signNew = split[2].substring(split[2].lastIndexOf("=") + 1);
//            map.put("credential", credential);
//            map.put("sign", signNew);
//            return map;
//        } catch (Exception e) {
//            logger.error("挖财>>>解析签名异常", e);
//            return null;
//        }
//    }
//
//
//    /**
//     * 利息计算
//     */
//    public static Double calculateRepayMoney(Double amount, int term, Double interestRate) {
//        switch (term) {
//            case 1:
//                return DoubleUtil.round(amount / 4 * 4 * interestRate, 0);
//            case 2:
//                return DoubleUtil.round(amount / 4 * 3 * interestRate, 0);
//            case 3:
//                return DoubleUtil.round(amount / 4 * 2 * interestRate, 0);
//            case 4:
//                return DoubleUtil.round(amount / 4 * 1 * interestRate, 0);
//            default:
//                return 0.0D;
//        }
//    }
//
//    /**
//     * 验签
//     *
//     * @param body 请求数据
//     * @param sign 接收的签名
//     * @return 验签结果
//     */
//    public static boolean checkSign(String body, String sign) {
//        try {
//            String appId = WaCaiConstant.APP_ID;
//            String appSecretKey = WaCaiConstant.APP_SECRET_KEY;
//            String method = "POST";
//            String message = method + '\n' + body + '\n' + appId + '\n' + appSecretKey;
//
//            Mac hasher = Mac.getInstance("HmacSHA256");
//            hasher.init(new SecretKeySpec(appSecretKey.getBytes(), "HmacSHA256"));
//            byte[] hash = hasher.doFinal(message.getBytes());
//            // 获得十六进制形式的签名
//            String signature = DatatypeConverter.printHexBinary(hash).toLowerCase();
//            // 验签
//            return sign.equals(signature);
//        } catch (Exception e) {
//            logger.error("挖财 >>>验签异常", e);
//            return false;
//        }
//    }
//
//    /**
//     * 生成签
//     *
//     * @param method 方法类型 POST GET
//     * @return 签名
//     */
//    public static String getSign(String method, String body) {
//        try {
//
//            String message = "";
//            if ("GET".equalsIgnoreCase(method)) {
//                message = "GET" + '\n' + WaCaiConstant.APP_ID + '\n' + WaCaiConstant.APP_SECRET_KEY;
//            } else if ("POST".equalsIgnoreCase(method)) {
//                message = "POST" + '\n' + body + '\n' + WaCaiConstant.APP_ID + '\n' + WaCaiConstant.APP_SECRET_KEY;
//            } else {
//                return null;
//            }
//            Mac hasher = Mac.getInstance("HmacSHA256");
//            hasher.init(new SecretKeySpec(WaCaiConstant.APP_SECRET_KEY.getBytes(), "HmacSHA256"));
//            byte[] hash = hasher.doFinal(message.getBytes());
//            // 获得十六进制形式的签名 返回
//            return DatatypeConverter.printHexBinary(hash).toLowerCase();
//        } catch (Exception e) {
//            logger.error("挖财 >>>加签异常", e);
//            return null;
//        }
//    }
//
//
//    /**
//     * get请求
//     *
//     * @param url 地址
//     * @param headers 请求头
//     * @return 返回数据
//     * @throws IOException 异常
//     */
//    public static String sendGet(String url, Map<String, String> headers) throws IOException {
//        // 创建okHttpClient对象
//        OkHttpClient client = new OkHttpClient();
//        Request.Builder builder = new Request.Builder().url(url);
//        // 添加消息头
//        if (headers != null) {
//            Set<String> keySet = headers.keySet();
//            for (String key : keySet) {
//                String value = headers.get(key);
//                builder.header(key, value);
//            }
//        }
//        String json = null;
//        Request request = builder.build();
//        // 获取请求数据
//        Response response = client.newCall(request).execute();
//        if (response.isSuccessful()) {
//            json = response.body().string();
//        } else {
//            logger.error("get请求失败,url>>>" + url + ",参数>>>" + JSON.toJSONString(response));
//        }
//        return json;
//    }
//
//    /**
//     * post请求
//     *
//     * @param url 地址
//     * @param headers 请求头
//     * @param json 请求体json
//     * @return 返回数据
//     * @throws IOException 异常
//     */
//    public static String sendPost(String url, Map<String, String> headers, String json) throws IOException {
//        OkHttpClient client = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
//        Request.Builder builder = new Request.Builder().url(url);
//        // 添加消息头
//        if (headers != null) {
//            Set<String> keySet = headers.keySet();
//            for (String key : keySet) {
//                String value = headers.get(key);
//                builder.header(key, value);
//            }
//        }
//        String responseJson = null;
//        Request request = builder.post(requestBody).build();
//        // 获取请求数据
//        Response response = client.newCall(request).execute();
//        if (response.isSuccessful()) {
//            responseJson = response.body().string();
//        } else {
//            logger.error("POST请求失败,url>>>" + url + ",参数>>>" + JSON.toJSONString(response));
//        }
//        return responseJson;
//    }
//
//
//}
