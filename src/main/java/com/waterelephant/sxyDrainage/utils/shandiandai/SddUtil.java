//package com.waterelephant.sxyDrainage.utils.shandiandai;
//
//import com.waterelephant.sxyDrainage.entity.shandiandai.SddRequest;
//import com.waterelephant.sxyDrainage.entity.shandiandai.SddResponse;
//import com.waterelephant.utils.DoubleUtil;
//import org.apache.commons.lang.StringUtils;
//import org.apache.log4j.Logger;
//
//import java.nio.charset.Charset;
//import java.security.MessageDigest;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 闪电贷工具类
// *
// * @author 王亚楠
// * @version 1.0
// * @date 2018/6/4
// * @since JDK 1.8
// */
//public class SddUtil {
//
//    private static Logger logger = Logger.getLogger(SddUtil.class);
//
//    /** 密钥 */
//    private static final String SECRET_KEY = SddConstant.SECRET_KEY;
//    /** 闪电贷渠道 */
//    private static final String CHANNEL_SDD = SddConstant.CHANNEL_SDD;
//
//
//    /** 订单状态转换 */
//    private static Map<Long, Integer> ORDER_STATUS = new HashMap<>();
//    /** 订单状态转换 */
//    private static Map<Integer, Integer> PLAN_STATUS = new HashMap<>();
//    /** 银行卡编码转换 */
//    private static Map<String, String> BANK_CODE_STATUS = new HashMap<>();
//
//    static {
//        // 我方，1:草稿，2:初审，3:终审，4:待签约，5:待放款，6:结束，7:拒绝，8:撤回，9:还款中，11:待生成合同，12:待债匹，13:逾期，14:债匹中，15:待商城用户确认
//        // 闪电贷， 1. 审批中 2. 审批失败 3. 审批成功（放款中）4.已签约(放款中) 5. 款失败 6.款中(已到帐) 7.已结清 8.逾期 9.坏账
//        // ORDER_STATUS.put(1L, 0);
//        ORDER_STATUS.put(2L, 1);
//        ORDER_STATUS.put(3L, 1);
//        ORDER_STATUS.put(4L, 3);
//        // ORDER_STATUS.put(5L, 3);
//        ORDER_STATUS.put(6L, 7);
//        ORDER_STATUS.put(7L, 2);
//        ORDER_STATUS.put(8L, 2);
//        ORDER_STATUS.put(9L, 6);
//        ORDER_STATUS.put(11L, 4);
//        ORDER_STATUS.put(12L, 4);
//        ORDER_STATUS.put(13L, 8);
//        ORDER_STATUS.put(14L, 4);
//
//        // 我方还款状态：1 未还款 2 已还款 3 已垫付 4 展期 5取消'
//        // 还款状态 闪电贷 1：待还 2：处理中 3：成功 4: 逾期 5：失败
//        PLAN_STATUS.put(1, 1);
//        PLAN_STATUS.put(2, 3);
//        PLAN_STATUS.put(3, 4);
//
//        // 银行卡状态转换
//        BANK_CODE_STATUS.put("ICBC", "0102");
//        BANK_CODE_STATUS.put("ABC", "0103");
//        BANK_CODE_STATUS.put("BOC", "0104");
//        BANK_CODE_STATUS.put("CITIC", "0302");
//        BANK_CODE_STATUS.put("CEB", "0303");
//        BANK_CODE_STATUS.put("HXBANK", "0304");
//        BANK_CODE_STATUS.put("CMBC", "0305");
//        BANK_CODE_STATUS.put("CGB", "0306");
//        BANK_CODE_STATUS.put("SPABANK", "0307");
//        BANK_CODE_STATUS.put("CIB", "0309");
//        BANK_CODE_STATUS.put("SPDB", "0310");
//        BANK_CODE_STATUS.put("PSBC", "0403");
//        BANK_CODE_STATUS.put("CCB", "0105");
//
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
//     * 银行卡状态转换
//     */
//    public static String getBankCode(String code) {
//        if (code == null) {
//            return null;
//        }
//        return BANK_CODE_STATUS.get(code);
//    }
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
//     * 获取工作类型
//     */
//    public static String getJob(String num) {
//        if (num == null) {
//            return "其它";
//        }
//        int i = Integer.valueOf(num) - 1;
//        String[] arr = {"商业、服务从业人员", "专业技术人员", "办事人员、文员、行政等有关人员", "工厂、生产、运输设备操作人员", "农林牧渔水利生产人员", "前线销售人员", "国家机关、企事业单位管理人员", "军人", "在校学生"};
//        if (i >= 0 && i < arr.length) {
//            return arr[i];
//        } else {
//            return "其它";
//        }
//    }
//
//    /**
//     * 数据过滤
//     */
//    public static SddResponse checkFilter(SddRequest sddRequest ,String logMsg) {
//
//        if (sddRequest == null) {
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "数据为空", "");
//        }
//        String channelId = sddRequest.getChannelId();
//        String param = sddRequest.getParam();
//        String sign = sddRequest.getSign();
//        logger.info("闪电贷>>>" + logMsg + " 数据校验>>>channelId:" + channelId + ",param:" + (param.length() > 2000 ? "数据太多，不输出日志" : param) + ",sign:" + sign);
//
//        if (StringUtils.isBlank(channelId)) {
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "渠道号为空", "{}");
//        }
//        if (StringUtils.isBlank(param)) {
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "请求参数为空", "{}");
//        }
//        if (StringUtils.isBlank(sign)) {
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "签名为空", "{}");
//        }
//        if (!CHANNEL_SDD.equals(channelId)) {
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "渠道号不合法", "{}");
//        }
//        boolean flag = signCheck(param, SECRET_KEY, sign);
//        if (!flag) {
//            return new SddResponse(SddResponse.PARM_EXCEPTION_CODE, "签名错误", "{}");
//        }
//        return null;
//    }
//
//    /**
//     * MD5签名
//     *
//     * @param content 待签名数据
//     * @return 签名值
//     */
//    public static String sign(String content, String secretKey) {
//        try {
//            return md5(content + "&secretKey=" + secretKey);
//        } catch (Exception e) {
//            logger.error("MD5签名异常", e);
//        }
//        return null;
//    }
//
//    public static String md5(String str) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            byte[] input = str.getBytes(Charset.forName("utf-8"));
//            byte[] buff = md.digest(input);
//            return bytesToHex(buff).toLowerCase();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    /**
//     * 二进制到16进制
//     *
//     * @param bytes 参数
//     * @return 字符串
//     */
//    private static String bytesToHex(byte[] bytes) {
//        StringBuilder md5str = new StringBuilder();
//        int digital;
//        for (byte aByte : bytes) {
//            digital = aByte;
//
//            if (digital < 0) {
//                digital += 256;
//            }
//            if (digital < 16) {
//                md5str.append("0");
//            }
//            md5str.append(Integer.toHexString(digital));
//        }
//        return md5str.toString();
//    }
//
//    /**
//     * 验签操作
//     *
//     * @param content 待签名数据.
//     * @param secretKey .
//     * @param signStr 签名.
//     * @return .
//     */
//    private static boolean signCheck(String content, String secretKey, String signStr) {
//        if (signStr == null) {
//            return false;
//        }
//        try {
//            return signStr.equals(sign(content, secretKey));
//        } catch (Exception e) {
//            logger.error("MD5验证签名异常", e);
//        }
//        return false;
//    }
//
//
//    /**
//     * 生成密码和解码
//     *
//     * @param args 参数
//     */
//    public static void main(String[] args) {
//        // 密码
//        String secret = "sd6q45rmrq4rbzfpi";
//        // 加签
//        String sign = sign("{\"loanId\":\"201805101421101169wyn\"}", secret);
//        System.out.println(sign);
//        // 验签
//        boolean flag = signCheck("{\"uid\":\"000001\"}", secret, sign);
//        System.out.println("验签结果：" + flag);
//    }
//
//}
