//package com.waterelephant.sxyDrainage.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.geinihua.ApprovalConfirm;
//import com.waterelephant.sxyDrainage.entity.geinihua.BasicInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.BindingCard;
//import com.waterelephant.sxyDrainage.entity.geinihua.Contract;
//import com.waterelephant.sxyDrainage.entity.geinihua.OrderInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.PublicInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.ResponseInfo;
//import com.waterelephant.sxyDrainage.entity.geinihua.Supplement;
//import com.waterelephant.sxyDrainage.entity.geinihua.UserInfo;
//import com.waterelephant.sxyDrainage.exception.GeinihuaException;
//import com.waterelephant.sxyDrainage.service.GeinihuaService;
//import com.waterelephant.sxyDrainage.utils.geinihua.Coder;
//import com.waterelephant.sxyDrainage.utils.geinihua.GZipUtil;
//import com.waterelephant.sxyDrainage.utils.geinihua.GnhRsaUtil;
//import com.waterelephant.sxyDrainage.utils.geinihua.Utils;
//
//import org.apache.commons.lang3.CharEncoding;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.MediaType;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @author xanthuim
// */
//@RequestMapping(value = "/geinihua",
//        method = RequestMethod.POST,
//        consumes = {MediaType.APPLICATION_JSON_VALUE + ";charset=" + CharEncoding.UTF_8})
//@RestController
//public class GeinihuaController {
//
//    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
//
//    private static final String MAPPING_ADMITTANCE = "/admittance.do";
//    private static final String MAPPING_BASIC = "/basic.do";
//    private static final String MAPPING_SUPPLEMENT = "/supplement.do";
//    private static final String MAPPING_BINDING_CARDS = "/cards.do";
//    private static final String MAPPING_BANKS = "/banks.do";
//    private static final String MAPPING_BIND_CARD = "/bindCard.do";
//    private static final String MAPPING_VERIFY_BINDING = "/bindCardCode.do";
//    private static final String MAPPING_PULL_RESULT = "/approvalResult.do";
//    private static final String MAPPING_CONFIRM = "/approvalConfirm.do";
//    private static final String MAPPING_CONTRACT = "/contract.do";
//    private static final String MAPPING_CACULATE_ORDER = "/caculateOrder.do";
//    private static final String MAPPING_ORDER_STATUS = "/orderStatus.do";
//    private static final String MAPPING_POSTIVE_REPAY = "/postiveRepay.do";
//    private static final String MAPPING_REPLAN_REPAY = "/replanRepay.do";
//    private static final String MAPPING_DETAIL_REPAY = "/detailRepay.do";
//
//    @Value("${GNH_PUBLIC_KEY}")
//    private String publicKey;
//
//    @Autowired
//    private GeinihuaService geinihuaService;
//
//    @ExceptionHandler(value = {Exception.class})
//    public ResponseInfo<?> exception(Exception e) {
//        if (e instanceof GeinihuaException) {
//            GeinihuaException geinihuaException = (GeinihuaException) e;
//            return geinihuaException.getResponseInfo();
//        }
//        LOGGER.error("【给你花系统异常】", e);
//        //todo 发送邮件
//        return ResponseInfo.error("给你花系统异常，请联系我们");
//    }
//
//    /**
//     * 用来做请求签名校验。
//     *
//     * @param publicInfo
//     * @return
//     * @throws Exception
//     */
//    @ModelAttribute
//    public Object validated(@Validated @RequestBody PublicInfo<Object> publicInfo,
//                            BindingResult bindingResult, HttpServletRequest request) {
//        parametersError(bindingResult);
//
//        //获取请求的地址
//        int lastIndexOf = request.getRequestURI().lastIndexOf("/");
//        String mappingName = request.getRequestURI().substring(lastIndexOf);
//        //由于基础信息和补充信息加密了，因此无需做JSON转换
//        if (MAPPING_BASIC.equals(mappingName) || MAPPING_SUPPLEMENT.equals(mappingName)) {
//            verifySignature(publicInfo);
//            return parseObject(publicInfo.getBizData().toString(), mappingName);
//        } else {
//            //其他接口需要转换成JSON
//            String bizData = JSON.toJSONString(publicInfo.getBizData());
//            publicInfo.setBizData(bizData);
//            verifySignature(publicInfo);
//            return parseObject(bizData, mappingName);
//        }
//    }
//
//    /**
//     * 解析对象
//     *
//     * @param json
//     * @param mappingName
//     * @return
//     */
//    private Object parseObject(String json, String mappingName) {
//        //准入
//        if (MAPPING_ADMITTANCE.equalsIgnoreCase(mappingName)) {
//            return JSON.parseObject(json, UserInfo.class);
//        }
//        //基本信息
//        else if (MAPPING_BASIC.equals(mappingName)) {
//            //解压、解码
//            return JSON.parseObject(GZipUtil.decompress(Coder.decryptBASE64(json)),
//                    BasicInfo.class);
//        }
//        //附加信息
//        else if (MAPPING_SUPPLEMENT.equals(mappingName)) {
//            //解压、解码
//            return JSON.parseObject(GZipUtil.decompress(Coder.decryptBASE64(json)),
//                    Supplement.class);
//        }
//        //绑卡
//        else if (MAPPING_BINDING_CARDS.equals(mappingName)) {
//            return JSON.parseObject(json, OrderInfo.class);
//        }
//        //支持的银行卡
//        else if (MAPPING_BANKS.equals(mappingName)) {
//            return JSON.parseObject(json, OrderInfo.class);
//        }
//        //绑卡
//        else if (MAPPING_BIND_CARD.equals(mappingName)) {
//            return JSON.parseObject(json, BindingCard.class);
//        }
//        //验证码绑卡
//        else if (MAPPING_VERIFY_BINDING.equals(mappingName)) {
//            return JSON.parseObject(json, BindingCard.class);
//        }
//        //审批拉取结果
//        else if (MAPPING_PULL_RESULT.equals(mappingName)) {
//            return JSON.parseObject(json, OrderInfo.class);
//        }
//        //审批确认
//        else if (MAPPING_CONFIRM.equals(mappingName)) {
//            return JSON.parseObject(json, OrderInfo.class);
//        }
//        //合同
//        else if (MAPPING_CONTRACT.equals(mappingName)) {
//            return JSON.parseObject(json, Contract.class);
//        }
//        //试算订单
//        else if (MAPPING_CACULATE_ORDER.equals(mappingName)) {
//            return JSON.parseObject(json, ApprovalConfirm.class);
//        }
//        //订单状态
//        else if (MAPPING_ORDER_STATUS.equals(mappingName)) {
//            return JSON.parseObject(json, OrderInfo.class);
//        }
//        //主动还款
//        else if (MAPPING_POSTIVE_REPAY.equals(mappingName)) {
//            return JSON.parseObject(json, OrderInfo.class);
//        }
//        //还款计划
//        else if (MAPPING_REPLAN_REPAY.equals(mappingName)) {
//            return JSON.parseObject(json, OrderInfo.class);
//        } else if (MAPPING_DETAIL_REPAY.equals(mappingName)) {
//            return JSON.parseObject(json, OrderInfo.class);
//        } else {
//            throw new GeinihuaException(ResponseInfo.error("访问的地址不存在"));
//        }
//    }
//
//    /**
//     * 校验签名
//     *
//     * @param publicInfo
//     */
//    private void verifySignature(PublicInfo<?> publicInfo) {
//        Map<String, Object> paraMap = new HashMap<>(16);
//        paraMap.put("appId", publicInfo.getAppId());
//        paraMap.put("encryptType", publicInfo.getEncryptType());
//        paraMap.put("signType", publicInfo.getSignType());
//        paraMap.put("isCompress", publicInfo.getIsCompress());
//        paraMap.put("version", publicInfo.getVersion());
//        paraMap.put("isEncrypt", publicInfo.getIsEncrypt());
//        paraMap.put("timestamp", publicInfo.getTimestamp());
//        paraMap.put("bizData", publicInfo.getBizData());
//        //参数排序
//        String signatureData = Utils.formatUrlMap(paraMap);
//        LOGGER.info("排序的数据：{}", signatureData);
//        LOGGER.info("签名的数据：{}", publicInfo.getSign());
//
//        boolean verify = GnhRsaUtil.verify(signatureData.getBytes(), publicKey, publicInfo.getSign());
//        if (!verify) {
//            throw new GeinihuaException(ResponseInfo.signatureFailure());
//        }
//    }
//
//    /**
//     * 参数错误
//     *
//     * @param bindingResult 参数绑定，可以检测出参数是否异常
//     */
//    private void parametersError(BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            List<String> errors = new ArrayList<>();
//            bindingResult.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
//            String invalidParameters = String.join("；", errors);
//            throw new GeinihuaException(ResponseInfo.parametersError(invalidParameters));
//        }
//    }
//
//    /**
//     * 准入接口
//     *
//     * @param userInfo
//     * @return
//     */
//    @RequestMapping(value = "/admittance.do")
//    public ResponseInfo<?> admittance(@Validated UserInfo userInfo, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.checkUser(userInfo);
//    }
//
//    /**
//     * 基本信息
//     *
//     * @param basicInfo
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/basic.do")
//    public ResponseInfo<?> basic(@Validated BasicInfo basicInfo, BindingResult bindingResult) throws Exception {
//        parametersError(bindingResult);
//        return geinihuaService.basicInfo(basicInfo);
//    }
//
//    /**
//     * 补充信息
//     *
//     * @param supplement
//     * @return
//     */
//    @RequestMapping(value = "/supplement.do")
//    public ResponseInfo<?> supplement(@Validated Supplement supplement, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.supplementInfo(supplement);
//    }
//
//    /**
//     * 获取用户绑卡信息
//     *
//     * @param orderInfo
//     * @return
//     */
//    @RequestMapping(value = "/cards.do")
//    public ResponseInfo<?> bindingCards(@Validated OrderInfo orderInfo, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.bindingCards(orderInfo.getOrderNo());
//    }
//
//    /**
//     * 获取商户支持的银行
//     *
//     * @param orderInfo
//     * @return
//     */
//    @RequestMapping(value = "/banks.do")
//    public ResponseInfo<?> banks(OrderInfo orderInfo) {
//        return geinihuaService.banks();
//    }
//
//    /**
//     * 绑卡。
//     * <p>
//     * 如果需要用户填写短信验证码，则合作机构给用户发送短信，并在此接口的返回参数中标注，
//     * 为短信发送或短信验证码验证不匹配等信息，如果不需要用户填写短信验证码，则合作机构通过此接口直接对用户进行绑卡操作。
//     *
//     * @param bindingCard
//     * @return
//     */
//    @RequestMapping(value = "/bindCard.do")
//    public ResponseInfo<?> bindCard(@Validated BindingCard bindingCard, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.bindCard(bindingCard);
//    }
//
//    /**
//     * 银行卡验证码绑定确认
//     * <p>
//     * 用户点击银行卡绑定后，返回需要输入的验证码，用户输入验证码后，掉用此接口进行银行卡的绑定。
//     *
//     * @param bindingCard
//     * @return
//     */
//    @RequestMapping(value = "/bindCardCode.do")
//    public ResponseInfo<?> confirmBinding(@Validated BindingCard bindingCard, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.verifyBinding(bindingCard);
//    }
//
//    /**
//     * 审批结论拉取
//     *
//     * @param orderInfo
//     * @return
//     */
//    @RequestMapping(value = "/approvalResult.do")
//    public ResponseInfo<?> approvalResult(@Validated OrderInfo orderInfo, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.approvalResult(orderInfo);
//    }
//
//    /**
//     * 审批确认
//     *
//     * @param orderInfo
//     * @return
//     */
//    @RequestMapping(value = "/approvalConfirm.do")
//    public ResponseInfo<?> approvalConfirm(@Validated OrderInfo orderInfo, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.approvalConfirm(orderInfo);
//    }
//
//    /**
//     * 合同拉取接口，告知机构要获取的订单号和当前流程涉及的合同类型。
//     *
//     * @param contract
//     * @return
//     */
//    @RequestMapping(value = "/contract.do")
//    public ResponseInfo<?> contract(@Validated Contract contract, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.contract(contract);
//    }
//
//    /**
//     * 订单试算
//     *
//     * @param approvalConfirm
//     * @return
//     */
//    @RequestMapping(value = "/caculateOrder.do")
//    public ResponseInfo<?> caculateOrder(@Validated ApprovalConfirm approvalConfirm, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.caculateOrder(approvalConfirm);
//    }
//
//    /**
//     * 订单状态查询
//     *
//     * @param orderInfo
//     * @return
//     */
//    @RequestMapping(value = "/orderStatus.do")
//    public ResponseInfo<?> orderStatus(@Validated OrderInfo orderInfo, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.orderStatus(orderInfo);
//    }
//
//    /**
//     * 主动还款
//     *
//     * @param orderInfo
//     * @return
//     */
//    @RequestMapping(value = "/postiveRepay.do")
//    public ResponseInfo<?> postiveRepay(@Validated OrderInfo orderInfo, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.postiveRepay(orderInfo);
//    }
//
//    /**
//     * 还款计划拉取
//     *
//     * @param orderInfo
//     * @return
//     */
//    @RequestMapping(value = "/replanRepay.do")
//    public ResponseInfo<?> replanRepay(@Validated OrderInfo orderInfo, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.replanRepay(orderInfo);
//    }
//
//    /**
//     * 还款详情
//     *
//     * @param orderInfo
//     * @return
//     */
//    @RequestMapping(value = "/detailRepay.do")
//    public ResponseInfo<?> detailRepay(@Validated OrderInfo orderInfo, BindingResult bindingResult) {
//        parametersError(bindingResult);
//        return geinihuaService.detailRepay(orderInfo);
//    }
//}
