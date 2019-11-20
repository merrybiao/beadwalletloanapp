package com.waterelephant.drainage.util.fqgj;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.drainage.entity.fqgj.FqgjReq;
import com.waterelephant.drainage.util.fqgj.jkzj.Base64Utils;
import com.waterelephant.drainage.util.fqgj.jkzj.KeyReader;
import com.waterelephant.utils.SystemConstant;
import org.apache.commons.lang.StringUtils;

import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/6/20 11:03
 */
public class FqgjUtil {
    // 订单状态映射
    private static Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>();
    // 审核状态映射
    private static Map<String, String> APPROVE_STATUS_MAP = new HashMap<String, String>();
    // 账单状态映射
    private static Map<String, String> BILL_STATUS_MAP = new HashMap<String, String>();
    // 连连状态码
    private static Map<String, String> LIANLIAN_CODE_MAP = new HashMap<String, String>();

    static{
        ORDER_STATUS_MAP.put("9", "170");
        ORDER_STATUS_MAP.put("13", "180");
        ORDER_STATUS_MAP.put("6", "200");
        ORDER_STATUS_MAP.put("7", "110");
        ORDER_STATUS_MAP.put("8", "110");
        ORDER_STATUS_MAP.put("4", "100");
        ORDER_STATUS_MAP.put("11", "210");
        ORDER_STATUS_MAP.put("12", "210");
        ORDER_STATUS_MAP.put("14", "210");
        ORDER_STATUS_MAP.put("5", "210");

        APPROVE_STATUS_MAP.put("7", "40");
        APPROVE_STATUS_MAP.put("8", "40");
        APPROVE_STATUS_MAP.put("4", "10");
        APPROVE_STATUS_MAP.put("11", "10");
        APPROVE_STATUS_MAP.put("12", "10");
        APPROVE_STATUS_MAP.put("14", "10");
        APPROVE_STATUS_MAP.put("9", "10");
        APPROVE_STATUS_MAP.put("5", "10");
        APPROVE_STATUS_MAP.put("6", "10");
        APPROVE_STATUS_MAP.put("13", "10");

        BILL_STATUS_MAP.put("9", "1");
        BILL_STATUS_MAP.put("6", "2");
        BILL_STATUS_MAP.put("13", "3");

        LIANLIAN_CODE_MAP.put("0000", "交易成功");

        LIANLIAN_CODE_MAP.put("1000", "支付授权令牌失效或错误");
        LIANLIAN_CODE_MAP.put("1001", "商户请求签名错误");
        LIANLIAN_CODE_MAP.put("1002", "支付服务超时，请重新支付");
        LIANLIAN_CODE_MAP.put("1003", "正在支付中,请稍后");
        LIANLIAN_CODE_MAP.put("1004", "商户请求参数校验错误");
        LIANLIAN_CODE_MAP.put("1005", "支付处理失败");
        LIANLIAN_CODE_MAP.put("1006", "用户中途取消支付操作");
        LIANLIAN_CODE_MAP.put("1007", "网络链接繁忙");
        LIANLIAN_CODE_MAP.put("1008", "银行卡号无效");
        LIANLIAN_CODE_MAP.put("1009", "暂停商户支付服务，请联系连连银通客服");
        LIANLIAN_CODE_MAP.put("1014", "日累计金额或笔数超限");
        LIANLIAN_CODE_MAP.put("1016", "余额不足");
        LIANLIAN_CODE_MAP.put("1019", "单笔金额超限");
        LIANLIAN_CODE_MAP.put("1100", "无效卡号");
        LIANLIAN_CODE_MAP.put("1101", "卡已挂失");
        LIANLIAN_CODE_MAP.put("1102", "无此发卡方");
        LIANLIAN_CODE_MAP.put("1103", "您的卡已过期或者您输入的有效期不正确");
        LIANLIAN_CODE_MAP.put("1104", "卡密码错误  ");
        LIANLIAN_CODE_MAP.put("1105", "卡号在黑名单中");
        LIANLIAN_CODE_MAP.put("1106", "允许的输入 PIN 次数超限");
        LIANLIAN_CODE_MAP.put("1107", "交易成功");
        LIANLIAN_CODE_MAP.put("1108", "您输入的证件号、姓名或手机号有误");
        LIANLIAN_CODE_MAP.put("1109", "卡号和证件号不符");
        LIANLIAN_CODE_MAP.put("1110", "卡状态异常");
        LIANLIAN_CODE_MAP.put("1111", "交易异常，支付失败");

        LIANLIAN_CODE_MAP.put("1112", "证件号有误");
        LIANLIAN_CODE_MAP.put("1113", "持卡人姓名有误");
        LIANLIAN_CODE_MAP.put("1114", "手机号有误");
        LIANLIAN_CODE_MAP.put("1115", "该卡未预留手机号");
        LIANLIAN_CODE_MAP.put("1200", "用户选择的银行暂不支持，请重新选择其他银行进行支付/签约");
        LIANLIAN_CODE_MAP.put("1900", "短信码校验错误");
        LIANLIAN_CODE_MAP.put("1901", "短信码已失效");
        LIANLIAN_CODE_MAP.put("2004", "签约处理中");
        LIANLIAN_CODE_MAP.put("2005", "原交易已在进行处理，请勿重复提交");
        LIANLIAN_CODE_MAP.put("2006", "交易已过期");
        LIANLIAN_CODE_MAP.put("2007", "交易已支付成功");
        LIANLIAN_CODE_MAP.put("2008", "交易处理中");
        LIANLIAN_CODE_MAP.put("3001", "非法商户");
        LIANLIAN_CODE_MAP.put("3002", "商户无此业务权限");
        LIANLIAN_CODE_MAP.put("3003", "用户签约失败");
        LIANLIAN_CODE_MAP.put("3004", "用户解约失败");
        LIANLIAN_CODE_MAP.put("3005", "暂时不支持该银行卡支付 ");
        LIANLIAN_CODE_MAP.put("3006", "无效的银行卡信息");
        LIANLIAN_CODE_MAP.put("3007", "用户信息查询失败");
        LIANLIAN_CODE_MAP.put("4000", "解约失败，请联系发卡行");
        LIANLIAN_CODE_MAP.put("5001", "卡 bin 校验失败");
        LIANLIAN_CODE_MAP.put("5002", "原始交易不存在");
        LIANLIAN_CODE_MAP.put("5003", "退款金额错误");
        LIANLIAN_CODE_MAP.put("5004", "商户状态异常，无法退款");
        LIANLIAN_CODE_MAP.put("5005", "退款失败，请重试");

        LIANLIAN_CODE_MAP.put("5006", "商户账户余额不足");
        LIANLIAN_CODE_MAP.put("5007", "累计退款金额大于原交易金额");
        LIANLIAN_CODE_MAP.put("5008", "原交易未成功");
        LIANLIAN_CODE_MAP.put("5501", "大额行号查询失败");
        LIANLIAN_CODE_MAP.put("5502", "信用卡不支持提现");
        LIANLIAN_CODE_MAP.put("6001", "卡余额不足");
        LIANLIAN_CODE_MAP.put("6002", "该卡号未成功进行首次验证");
        LIANLIAN_CODE_MAP.put("8000", "用户信息不存在");
        LIANLIAN_CODE_MAP.put("8001", "用户状态异常");
        LIANLIAN_CODE_MAP.put("8888", "交易申请成功,需要再次进行验证");
        LIANLIAN_CODE_MAP.put("8901", "没有签约记录 ");
        LIANLIAN_CODE_MAP.put("8911", "没有风控记录");
        LIANLIAN_CODE_MAP.put("9901", "请求报文非法");
        LIANLIAN_CODE_MAP.put("9902", "请求参数错误");
        LIANLIAN_CODE_MAP.put("9903", "请求参数错误");
        LIANLIAN_CODE_MAP.put("9904", "支付参数和原创建支付单参数不一致");
        LIANLIAN_CODE_MAP.put("9091", "创建支付失败");
        LIANLIAN_CODE_MAP.put("9092", "业务信息非法");
        LIANLIAN_CODE_MAP.put("9093", "无对应的支付单信息");
        LIANLIAN_CODE_MAP.put("9094", "请求银行扣款失败");
        LIANLIAN_CODE_MAP.put("9700", "短信验证码错误");
        LIANLIAN_CODE_MAP.put("9701", "短信验证码和手机不匹配");
        LIANLIAN_CODE_MAP.put("9702", "验证码错误次数超过最大次数,请重新获取进行验证");
        LIANLIAN_CODE_MAP.put("9703", "短信验证码失效,请重新获取");
        LIANLIAN_CODE_MAP.put("9704", "短信发送异常,请稍后重试");
        LIANLIAN_CODE_MAP.put("9902", "接口调用异常");

        LIANLIAN_CODE_MAP.put("9910", "风险等级过高");
        LIANLIAN_CODE_MAP.put("9911", "金额超过指定额度");
        LIANLIAN_CODE_MAP.put("9912", "该卡不支持");
        LIANLIAN_CODE_MAP.put("9913", "该卡已签约成功");
        LIANLIAN_CODE_MAP.put("9970", "银行系统日切处理中");
        LIANLIAN_CODE_MAP.put("9999", "系统错误");
        LIANLIAN_CODE_MAP.put("9990", "银行交易出错");
        LIANLIAN_CODE_MAP.put("9907", "银行编码与卡不一致");
        LIANLIAN_CODE_MAP.put("9000", "银行维护中，请稍后再试");
    }

    public static String convertOrderStatus(Long oriStatus){
        if (oriStatus == null){
            return null;
        }

        return ORDER_STATUS_MAP.get(String.valueOf(oriStatus));
    }

    public static String convertApproveStatus(Long oriStatus){
        if (oriStatus == null){
            return null;
        }

        return APPROVE_STATUS_MAP.get(String.valueOf(oriStatus));
    }

    public static String convertBillStatus(Long oriStatus){
        if (oriStatus == null){
            return null;
        }

        return BILL_STATUS_MAP.get(String.valueOf(oriStatus));
    }


    // 根据分期管家code转成平台自己的code
    public static String convertToBankCode(String openBank) {
        String bankCode = "";
        switch (openBank) {
            case "CCB":
                // 建设银行
                bankCode = "0105";
                break;
            case "CEB":
                // 光大银行
                bankCode = "0303";
                break;
            case "PSBC":
                // 邮政银行
                bankCode = "0403";
                break;
            case "HXB":
                // 华夏银行
                bankCode = "0304";
                break;
            case "ABC":
                // 农业银行
                bankCode = "0103";
                break;
            case "BOCOM":
                // 交通银行
                bankCode = "0301";
                break;
            case "COMM":
                // 交通银行
                bankCode = "0301";
                break;
            case "BOC":
                // 中国银行
                bankCode = "0104";
                break;
            case "CITIC":
                // 中信实业银行
                bankCode = "0302";
                break;
            case "SPDB":
                // 上海浦东发展银行
                bankCode = "0310";
                break;
            case "ICBC":
                // 中国工商银行
                bankCode = "0102";
                break;
            case "CIB":
                // 兴业银行
                bankCode = "0309";
                break;
            case "PINGAN":
                // 平安银行股份有限公司
                bankCode = "0307";
                break;
            case "CMB":
                // 招商银行
                bankCode = "0308";
                break;
            case "CMBC":
                // 中国民生银行
                bankCode = "0305";
                break;
            case "GDB":
                // 广东发展银行
                bankCode = "0306";
                break;
            default:
                bankCode = "0000";
                break;
        }
        return bankCode;
    }

    /**
     * 参数非空判断
     * @author GuoKun
     * @param req
     * @return
     */
    public static String isolduser(FqgjReq req) {
        String result = null;
        if (req == null) {
            return "老用户过滤接口请求内容为空";
        }
        if (StringUtils.isBlank(req.getBiz_data())) {
            return "老用户过滤接口业务数据为空";
        }
        if (StringUtils.isBlank(req.getSign())) {
            return "老用户过滤接口签名为空";
        }
        return result;
    }

    /**
     * 验签判断
     * @author GuoKun
     * @param jsonObject
     * @return
     */
    public static boolean veriry(JSONObject jsonObject) {
        Boolean pass = false;
        try {
            String signNative = jsonObject.getString("sign");
            RSAPublicKey publicKey = KeyReader.loadPublicKey(SystemConstant.FQGJ_PUB_KEY);
            String encryptStr = getSignSourceStr(jsonObject);
            pass = KeyReader.doCheck(encryptStr, Base64Utils.decode(signNative), publicKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return pass;
    }

    public static String   getSignSourceStr (JSONObject jsonObject) throws Exception{
        TreeMap<String, Object> sortedParams =new TreeMap<>() ;
        Set<String> keySet= jsonObject.keySet();
        for(String key:keySet){
            if(!"sign".equals(key)){
                sortedParams.put(key,jsonObject.get(key));
            }
        }
        int size = sortedParams.size();
        int count = 0;
        StringBuffer buffer = new StringBuffer();
        for (Map.Entry<String, Object> entry : sortedParams.entrySet()) {
            count++;
            String key = entry.getKey();
            Object value = entry.getValue();
            if (null == value) {
                continue;
            }
            buffer.append(key).append("=").append(value).append("&");
        }
        int length = buffer.length();
        return buffer.delete(length - 1, length).toString();
    }

}
