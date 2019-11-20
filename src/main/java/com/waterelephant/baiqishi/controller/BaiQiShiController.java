package com.waterelephant.baiqishi.controller;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.baiqishi.service.BaiQiShiService;
import com.waterelephant.baiqishi.util.BaiQiShiUtil;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.zhengxin91.entity.ZxCeshi;
import com.waterelephant.zhengxin91.service.ZxCeshiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/5/23 11:15
 */
@Controller
public class BaiQiShiController {

    private Logger logger = Logger.getLogger(BaiQiShiController.class);

    @Autowired
    private BaiQiShiService baiQiShiService;
    @Autowired
    private ZxCeshiService zxCeshiService;


    @ResponseBody
    @RequestMapping("baiqishi/decision.do")
    public AppResponseResult decision(HttpServletRequest request, HttpServletResponse response) {
        AppResponseResult result = new AppResponseResult();
        String eventType = request.getParameter("eventType"); // 事件类型
        // 验证参数
        if (CommUtils.isNull(eventType)) {
            result.setCode("1201");
            result.setMsg("事件类型为空");
            return result;
        }
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("eventType", eventType);
        reqMap = getReqMap(request, reqMap);
        try {
            result = baiQiShiService.decision(reqMap);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("1202");
            result.setMsg("Service服务器访问异常");
        }
        return result;
    }


    /**
     * 根据传参封装请求参数
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("baiqishi/ceshiF.do")
    public void ceshi(String begin, String end) {
        List<ZxCeshi> zxCeshiList = null;
        try {
            zxCeshiList = zxCeshiService.findByBorrowId(begin, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String ss = "{\"requestCode\":\"200\",\"requestMsg\":null,\"obj\":\"{\\\"finalDecision\\\":\\\"Reject\\\",\\\"finalScore\\\":\\\"0\\\",\\\"flowNo\\\":\\\"1706021631B1DB4B31D32B4D11A2F247B1F04FF628\\\",\\\"resultCode\\\":\\\"BQS000\\\",\\\"strategySet\\\":[{\\\"hitRules\\\":[{\\\"decision\\\":\\\"Accept\\\",\\\"memo\\\":\\\"V_PH_CN_MA_UL30D:手机号码与本人匹配，30天内有使用\\\",\\\"ruleId\\\":\\\"123881\\\",\\\"ruleName\\\":\\\"手机号与身份证匹配\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Review\\\",\\\"memo\\\":\\\"V_BC_CN_UK:银行卡号与本人关系未知\\\",\\\"ruleId\\\":\\\"123884\\\",\\\"ruleName\\\":\\\"银行卡号与身份证关系未知\\\",\\\"score\\\":0}],\\\"riskType\\\":\\\"creditRisk\\\",\\\"strategyDecision\\\":\\\"Review\\\",\\\"strategyId\\\":\\\"8655d5f9bb0c404c9e31e000b41f93be\\\",\\\"strategyMode\\\":\\\"WorstMode\\\",\\\"strategyName\\\":\\\"欺诈信息验证策略\\\",\\\"strategyScore\\\":0,\\\"tips\\\":\\\"欺诈信息验证策略\\\"},{\\\"hitRules\\\":[{\\\"decision\\\":\\\"Review\\\",\\\"memo\\\":\\\"R_PH_JJ_03:手机号近一月（不包含当天）在多个商户申请#R_PH_JJ_02:手机号近一周（不包含当天）在多个商户申请\\\",\\\"ruleId\\\":\\\"123906\\\",\\\"ruleName\\\":\\\"手机号匹配中风险欺诈关注清单\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Accept\\\",\\\"memo\\\":\\\"R_PH_JJ_03:手机号近一月（不包含当天）在多个商户申请#R_PH_JJ_02:手机号近一周（不包含当天）在多个商户申请\\\",\\\"ruleId\\\":\\\"123918\\\",\\\"ruleName\\\":\\\"手机号匹配中风险多头关注清单\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Review\\\",\\\"memo\\\":\\\"R_PH_006:手机号稳定性弱\\\",\\\"ruleId\\\":\\\"123907\\\",\\\"ruleName\\\":\\\"手机号匹配低风险欺诈关注清单\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Accept\\\",\\\"memo\\\":\\\"R_CN_JJ_02:身份证近一周（不包含当天）在多个商户申请#R_CN_JJ_03:身份证近一月（不包含当天）在多个商户申请\\\",\\\"ruleId\\\":\\\"123917\\\",\\\"ruleName\\\":\\\"身份证匹配中风险多头关注清单\\\",\\\"score\\\":0}],\\\"riskType\\\":\\\"creditRisk\\\",\\\"strategyDecision\\\":\\\"Review\\\",\\\"strategyId\\\":\\\"dac2fe7d91ed44faa48d2e6e21998b13\\\",\\\"strategyMode\\\":\\\"WorstMode\\\",\\\"strategyName\\\":\\\"欺诈关注清单策略\\\",\\\"strategyScore\\\":0,\\\"tips\\\":\\\"欺诈关注清单策略\\\"},{\\\"hitRules\\\":[{\\\"decision\\\":\\\"Review\\\",\\\"memo\\\":\\\"总数:31,持牌消费金融:2,消费金融:22,垂直金融:1,P2P理财:5,其它:1\\\",\\\"ruleId\\\":\\\"123870\\\",\\\"ruleName\\\":\\\"手机号三个月内多头申请过多\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Review\\\",\\\"memo\\\":\\\"总数:28,持牌消费金融:1,消费金融:20,垂直金融:1,P2P理财:5,其它:1\\\",\\\"ruleId\\\":\\\"123865\\\",\\\"ruleName\\\":\\\"身份证三个月内多头申请过多\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Review\\\",\\\"memo\\\":\\\"总数:18,持牌消费金融:2,消费金融:15,P2P理财:1\\\",\\\"ruleId\\\":\\\"123869\\\",\\\"ruleName\\\":\\\"手机号一个月内多头申请过多\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Review\\\",\\\"memo\\\":\\\"总数:15,持牌消费金融:1,消费金融:13,P2P理财:1\\\",\\\"ruleId\\\":\\\"123866\\\",\\\"ruleName\\\":\\\"身份证一个月内多头申请过多\\\",\\\"score\\\":0}],\\\"riskType\\\":\\\"creditRisk\\\",\\\"strategyDecision\\\":\\\"Review\\\",\\\"strategyId\\\":\\\"49afa065143f472d85a548a16fc9d824\\\",\\\"strategyMode\\\":\\\"WorstMode\\\",\\\"strategyName\\\":\\\"多头风险策略\\\",\\\"strategyScore\\\":0,\\\"tips\\\":\\\"多头风险\\\"},{\\\"hitRules\\\":[{\\\"decision\\\":\\\"Reject\\\",\\\"memo\\\":\\\"黑名单-信贷行业-信贷失联\\\",\\\"ruleId\\\":\\\"123834\\\",\\\"ruleName\\\":\\\"手机号比对信贷行业失联黑名单\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Review\\\",\\\"memo\\\":\\\"灰名单-信贷行业-曾经逾期(未知期限)\\\",\\\"ruleId\\\":\\\"123852\\\",\\\"ruleName\\\":\\\"身份证比对信贷行业曾经逾期未知期限灰名单\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Reject\\\",\\\"memo\\\":\\\"黑名单-信贷行业-信贷失联\\\",\\\"ruleId\\\":\\\"123849\\\",\\\"ruleName\\\":\\\"身份证比对信贷行业失联名单\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Reject\\\",\\\"memo\\\":\\\"黑名单-信贷行业-信用/消费贷黑名单\\\",\\\"ruleId\\\":\\\"123836\\\",\\\"ruleName\\\":\\\"手机号比对信贷行业黑名单\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Review\\\",\\\"memo\\\":\\\"灰名单-信贷行业-模型分值低\\\",\\\"ruleId\\\":\\\"123853\\\",\\\"ruleName\\\":\\\"身份证比对信贷行业模型分值低灰名单\\\",\\\"score\\\":0},{\\\"decision\\\":\\\"Reject\\\",\\\"memo\\\":\\\"黑名单-信贷行业-信用/消费贷黑名单\\\",\\\"ruleId\\\":\\\"123844\\\",\\\"ruleName\\\":\\\"身份证比对信贷行业信用消费黑名单\\\",\\\"score\\\":0}],\\\"riskType\\\":\\\"creditRisk\\\",\\\"strategyDecision\\\":\\\"Reject\\\",\\\"strategyId\\\":\\\"89340b73fe784d32b147d007c142992c\\\",\\\"strategyMode\\\":\\\"WorstMode\\\",\\\"strategyName\\\":\\\"失信风险策略\\\",\\\"strategyScore\\\":0,\\\"tips\\\":\\\"失信名单\\\"}]}\"}\n";
//        JSONObject jsonObject = JSONObject.parseObject(ss);
//        if("200".equals(jsonObject.getString("requestCode"))) {
//            jsonObject.getJSONObject("obj");
//        }
        for (ZxCeshi zxCeshi : zxCeshiList) {
            /** ~~~~~~~~~~~~~~~~~  白骑士反欺诈 开始 ~~~~~~~~~~~~~~~~~~~~ **/
            try {
//                Long borrowerId = bo.getBorrowerId();
//                BwBorrower borrower = bwBorrowerService.findBwBorrowerById(borrowerId);
//                String tokenKey = request.getParameter("tokenKey");
//                if(bwBorrower!= null) {
                baiQiShiService.saveBaiQiShi(zxCeshi.getBorrowId(), zxCeshi.getOrderId(), "verify", null, null);
//                }
            } catch (Exception e) {
                logger.error("白骑士异常：",e);
            }
            /** ~~~~~~~~~~~~~~~~~  白骑士反欺诈 结束 ~~~~~~~~~~~~~~~~~~~~ **/
        }
    }

    /**
     * 获取所有的信息
     *
     * @param request
     * @param reqMap
     * @return
     */
    private Map<String, String> getReqMap(HttpServletRequest request, Map<String, String> reqMap) {

        String tokenKey = request.getParameter("tokenKey");  // 当前会话标识，用于事件中关联设备信息
        String occurTime = request.getParameter("occurTime"); // 事件发生时间，格式yyyy-MM-dd HH:mm:ss
        String platform = request.getParameter("platform"); // 应用平台类型，h5/web/ios/android

        if (!CommUtils.isNull(tokenKey)) {
            reqMap.put("tokenKey", tokenKey);
        }
        if (!CommUtils.isNull(occurTime)) {
            reqMap.put("occurTime", occurTime);
        }
        if (!CommUtils.isNull(platform)) {
            reqMap.put("platform", platform);
        }

        // 用户信息
        String account = request.getParameter("account"); // 用户账号
        String name = request.getParameter("name"); //用户姓名
        String email = request.getParameter("email"); //用户邮箱
        String mobile = request.getParameter("mobile"); //用户手机号
        String certNo = request.getParameter("certNo"); //用户身份证号
        String address = request.getParameter("address"); //用户住址
        String addressCity = request.getParameter("addressCity"); //用户所在城市
        String contactsName = request.getParameter("contactsName"); //用户联系人姓名
        String contactsMobile = request.getParameter("contactsMobile"); //用户联系人电话
        String organization = request.getParameter("organization"); //用户工作单位名称
        String organizationAddress = request.getParameter("organizationAddress"); //用户工作单位地址
        String education = request.getParameter("education"); //学历
        String graduateSchool = request.getParameter("graduateSchool"); //毕业院校名称
        String graduateCity = request.getParameter("graduateCity"); //毕业院校城市
        String marriage = request.getParameter("marriage"); //是否已婚
        String residence = request.getParameter("residence"); //户籍所在地
        if (!CommUtils.isNull(account)) {
            reqMap.put("account", account);
        }
        if (!CommUtils.isNull(name)) {
            reqMap.put("name", name);
        }
        if (!CommUtils.isNull(email)) {
            reqMap.put("email", email);
        }
        if (!CommUtils.isNull(mobile)) {
            reqMap.put("mobile", mobile);
        }
        if (!CommUtils.isNull(certNo)) {
            reqMap.put("certNo", certNo);
        }
        if (!CommUtils.isNull(address)) {
            reqMap.put("address", address);
        }
        if (!CommUtils.isNull(addressCity)) {
            reqMap.put("addressCity", addressCity);
        }
        if (!CommUtils.isNull(contactsName)) {
            reqMap.put("contactsName", contactsName);
        }
        if (!CommUtils.isNull(contactsMobile)) {
            reqMap.put("contactsMobile", contactsMobile);
        }
        if (!CommUtils.isNull(organization)) {
            reqMap.put("organization", organization);
        }
        if (!CommUtils.isNull(organizationAddress)) {
            reqMap.put("organizationAddress", organizationAddress);
        }
        if (!CommUtils.isNull(education)) {
            reqMap.put("education", education);
        }
        if (!CommUtils.isNull(graduateSchool)) {
            reqMap.put("graduateSchool", graduateSchool);
        }
        if (!CommUtils.isNull(graduateCity)) {
            reqMap.put("graduateCity", graduateCity);
        }
        if (!CommUtils.isNull(marriage)) {
            reqMap.put("marriage", marriage);
        }
        if (!CommUtils.isNull(residence)) {
            reqMap.put("residence", residence);
        }

        // 收货人
        String deliverName = request.getParameter("deliverName"); //收货人
        String deliverMobileNo = request.getParameter("deliverMobileNo"); //收货人手机号
        String deliverAddressStreet = request.getParameter("deliverAddressStreet"); //收货人街道地址信息
        String deliverAddressCounty = request.getParameter("deliverAddressCounty"); //收货人县或区信息
        String deliverAddressCity = request.getParameter("deliverAddressCity"); //收货人城市信息
        String deliverAddressProvince = request.getParameter("deliverAddressProvince"); //收货人省份信息
        String deliverAddressCountry = request.getParameter("deliverAddressCountry"); //收货人国家信息

        if (!CommUtils.isNull(deliverName)) {
            reqMap.put("deliverName", deliverName);
        }
        if (!CommUtils.isNull(deliverMobileNo)) {
            reqMap.put("deliverMobileNo", deliverMobileNo);
        }
        if (!CommUtils.isNull(deliverAddressStreet)) {
            reqMap.put("deliverAddressStreet", deliverAddressStreet);
        }
        if (!CommUtils.isNull(deliverAddressCounty)) {
            reqMap.put("deliverAddressCounty", deliverAddressCounty);
        }
        if (!CommUtils.isNull(deliverAddressCity)) {
            reqMap.put("deliverAddressCity", deliverAddressCity);
        }
        if (!CommUtils.isNull(deliverAddressProvince)) {
            reqMap.put("deliverAddressProvince", deliverAddressProvince);
        }
        if (!CommUtils.isNull(deliverAddressCountry)) {
            reqMap.put("deliverAddressCountry", deliverAddressCountry);
        }

        // 订单信息
        String orderId = request.getParameter("orderId"); //订单号
        String payeeAccount = request.getParameter("payeeAccount"); //卖家或收款人账号
        String payeeName = request.getParameter("payeeName"); //卖家或收款人姓名
        String payeeEmail = request.getParameter("payeeEmail"); //卖家或收款人邮箱
        String payeeMobile = request.getParameter("payeeMobile"); //卖家或收款人手机
        String payeePhone = request.getParameter("payeePhone"); //卖家或收款人座机
        String payeeIdNumber = request.getParameter("payeeIdNumber"); //卖家或收款人身份证
        String payeeCardNumber = request.getParameter("payeeCardNumber"); //卖家或收款人银行卡号
        String amount = request.getParameter("amount"); //金额（通用）
        String items = request.getParameter("items"); //商品详情/清单
        String itemNum = request.getParameter("itemNum"); //商品数量
        String itemAmount = request.getParameter("itemAmount"); //商品金额
        String payMethod = request.getParameter("payMethod"); //支付方式
        String payAmount = request.getParameter("payAmount"); //支付金额
        String payAccount = request.getParameter("payAccount"); //支付卡号
        String merchantName = request.getParameter("merchantName"); //商户名称
        String bizLicense = request.getParameter("bizLicense"); //商户工商注册号
        String orgCode = request.getParameter("orgCode"); //组织机构代码


        if (!CommUtils.isNull(orderId)) {
            reqMap.put("orderId", orderId);
        }
        if (!CommUtils.isNull(payeeAccount)) {
            reqMap.put("payeeAccount", payeeAccount);
        }
        if (!CommUtils.isNull(payeeName)) {
            reqMap.put("payeeName", payeeName);
        }
        if (!CommUtils.isNull(payeeEmail)) {
            reqMap.put("payeeEmail", payeeEmail);
        }
        if (!CommUtils.isNull(payeeMobile)) {
            reqMap.put("payeeMobile", payeeMobile);
        }
        if (!CommUtils.isNull(payeePhone)) {
            reqMap.put("payeePhone", payeePhone);
        }
        if (!CommUtils.isNull(payeeIdNumber)) {
            reqMap.put("payeeIdNumber", payeeIdNumber);
        }
        if (!CommUtils.isNull(payeeCardNumber)) {
            reqMap.put("payeeCardNumber", payeeCardNumber);
        }
        if (!CommUtils.isNull(amount)) {
            reqMap.put("amount", amount);
        }
        if (!CommUtils.isNull(items)) {
            reqMap.put("items", items);
        }
        if (!CommUtils.isNull(itemNum)) {
            reqMap.put("itemNum", itemNum);
        }
        if (!CommUtils.isNull(itemAmount)) {
            reqMap.put("itemAmount", itemAmount);
        }
        if (!CommUtils.isNull(payMethod)) {
            reqMap.put("payMethod", payMethod);
        }
        if (!CommUtils.isNull(payAmount)) {
            reqMap.put("payAmount", payAmount);
        }
        if (!CommUtils.isNull(payAccount)) {
            reqMap.put("payAccount", payAccount);
        }
        if (!CommUtils.isNull(merchantName)) {
            reqMap.put("merchantName", merchantName);
        }
        if (!CommUtils.isNull(bizLicense)) {
            reqMap.put("bizLicense", bizLicense);
        }
        if (!CommUtils.isNull(orgCode)) {
            reqMap.put("orgCode", orgCode);
        }

        // 银行卡号信息
        String userBankBin = request.getParameter("userBankBin"); //BIN卡号
        String bankCardNo = request.getParameter("bankCardNo"); //银行卡卡号
        String bankCardType = request.getParameter("bankCardType"); //银行卡类型
        String bankCardCode = request.getParameter("bankCardCode"); //银行编码
        String bankCardName = request.getParameter("bankCardName"); //银行户名
        String bankCardMobile = request.getParameter("bankCardMobile"); //银行卡预留手机号
        String creditCardNo = request.getParameter("creditCardNo"); //信用卡卡号
        String creditCardName = request.getParameter("creditCardName"); //信用卡户名
        String creditCardMobile = request.getParameter("creditCardMobile"); //信用卡预留手机号

        if (!CommUtils.isNull(userBankBin)) {
            reqMap.put("userBankBin", userBankBin);
        }
        if (!CommUtils.isNull(bankCardNo)) {
            reqMap.put("bankCardNo", bankCardNo);
        }
        if (!CommUtils.isNull(bankCardType)) {
            reqMap.put("bankCardType", bankCardType);
        }
        if (!CommUtils.isNull(bankCardCode)) {
            reqMap.put("bankCardCode", bankCardCode);
        }
        if (!CommUtils.isNull(bankCardName)) {
            reqMap.put("bankCardName", bankCardName);
        }
        if (!CommUtils.isNull(bankCardMobile)) {
            reqMap.put("bankCardMobile", bankCardMobile);
        }
        if (!CommUtils.isNull(creditCardNo)) {
            reqMap.put("creditCardNo", creditCardNo);
        }
        if (!CommUtils.isNull(creditCardName)) {
            reqMap.put("creditCardName", creditCardName);
        }
        if (!CommUtils.isNull(creditCardMobile)) {
            reqMap.put("creditCardMobile", creditCardMobile);
        }

        // 设备信息
        String userAgentCust = request.getParameter("userAgentCust"); //浏览器UA
        String referCust = request.getParameter("referCust"); //Referer
        String ip = request.getParameter("ip"); //IP地址
        String mac = request.getParameter("mac"); //MAC地址
        String imei = request.getParameter("imei"); //IMEI
        String longitude = request.getParameter("longitude"); //经度
        String latitude = request.getParameter("latitude"); //纬度
        String bizResult = request.getParameter("bizResult"); //客户业务处理结果
        if (!CommUtils.isNull(userAgentCust)) {
            reqMap.put("userAgentCust", userAgentCust);
        }
        if (!CommUtils.isNull(referCust)) {
            reqMap.put("referCust", referCust);
        }
        if (!CommUtils.isNull(ip)) {
            reqMap.put("ip", ip);
        }
        if (!CommUtils.isNull(mac)) {
            reqMap.put("mac", mac);
        }
        if (!CommUtils.isNull(imei)) {
            reqMap.put("imei", imei);
        }
        if (!CommUtils.isNull(longitude)) {
            reqMap.put("longitude", longitude);
        }
        if (!CommUtils.isNull(latitude)) {
            reqMap.put("latitude", latitude);
        }
        if (!CommUtils.isNull(bizResult)) {
            reqMap.put("bizResult", bizResult);
        }


        return reqMap;
    }


    @ResponseBody
    @RequestMapping("baiqishi/ceshi.do")
    public AppResponseResult ceshi(HttpServletRequest request, HttpServletResponse response) {
        AppResponseResult result = new AppResponseResult();
        BaiQiShiUtil.putTokenKey("123", "eq123123");
        BaiQiShiUtil.putTokenKey("123312", "asdf");
        BaiQiShiUtil.putTokenKey("12312312", "123v");
        System.out.println(RedisUtils.get("BaiQiShi:tokenKey:" + "123"));
        System.out.println(RedisUtils.get("BaiQiShi:tokenKey:" + "123312"));
        System.out.println(RedisUtils.get("BaiQiShi:tokenKey:" + "12312312"));
        return result;
    }
}
