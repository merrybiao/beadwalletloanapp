///******************************************************************************
// * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved.
// * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、 团体不得使用、复制、修改或发布本软件.
// *****************************************************************************/
//package com.waterelephant.sxyDrainage.controller.jdq;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.TypeReference;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqBindCardReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCalculateReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCardInfoReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqCheckUserData;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqContractReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqOrderInfoRequest;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqPullOrderStatusReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqRepaymentReq;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqRequest;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqResponse;
//import com.waterelephant.sxyDrainage.entity.jdq.JdqWithdrawReq;
//import com.waterelephant.sxyDrainage.exception.JdqException;
//import com.waterelephant.sxyDrainage.service.JdqService;
//import com.waterelephant.sxyDrainage.utils.jdq.BankcardList;
//import com.waterelephant.sxyDrainage.utils.jdq.JdqConstant;
//import com.waterelephant.sxyDrainage.utils.jdq.RSAUtil;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
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
//import java.lang.reflect.Type;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author Xanthuim
// * @since 2018-07-24
// */
//@RestController
//@RequestMapping(value = "/sxyDrainage/jdq", method = RequestMethod.POST,
//        consumes = {MediaType.APPLICATION_JSON_VALUE})
//public class JdqController {
//
//    private static Logger logger = LoggerFactory.getLogger(JdqController.class);
//
//    private static final String sxPrivateKey = JdqConstant.get("sxPrivateKey");
//
//    /**
//     * fastjson泛型转换
//     */
//    private static final Type TYPE = new TypeReference<Map<String, String>>() {
//    }.getType();
//
//    @Autowired
//    private JdqService jdqService;
//
//    /**
//     * 拦截该类相关地所有异常
//     *
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(value = {Exception.class})
//    public JdqResponse exception(Exception e) {
//        JdqResponse jdqResponse = new JdqResponse();
//        if (e instanceof JdqException) {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL);
//            jdqResponse.setDesc(e.getLocalizedMessage());
//        } else {
//            jdqResponse.setCode(JdqResponse.CODE_FAIL);
//            jdqResponse.setDesc("借点钱异常，请反馈");
//            logger.error("借点钱异常", e);
//        }
//        return jdqResponse;
//    }
//
//    /**
//     * 拦截所有请求。在该方法中做数据校验、解密等操作，而不用没个请求方法都去校验。
//     *
//     * @param jdqRequest
//     * @param br
//     * @return
//     */
//    @ModelAttribute
//    public JdqResponse validated(@Validated @RequestBody JdqRequest jdqRequest, BindingResult br) {
//        JdqResponse jdqResponse = new JdqResponse();
//        //参数有误
//        if (br.hasErrors()) {
//            List<String> errors = new ArrayList<>();
//            br.getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
//            String invalidParameters = String.join("；", errors);
//            throw new JdqException(invalidParameters);
//        }
//        jdqResponse.setData(decodeData(jdqRequest.getData()));
//        return jdqResponse;
//    }
//
//    /**
//     * 对data进行解密
//     *
//     * @param data
//     */
//    private String decodeData(String data) {
//        // 水象私钥解密，获取数据
//        data = RSAUtil.buildRSADecryptByPrivateKey(data, sxPrivateKey);
//        if (StringUtils.isBlank(data)) {
//            throw new RuntimeException("解密后的数据为空，请检查！");
//        }
//        return data;
//    }
//
//    /**
//     * 3.1 检查用户接口
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/user-quota.do")
//    public JdqResponse checkUser(JdqResponse jdqResponse) throws ParseException {
//        return jdqService.checkUser(JSONObject.parseObject(jdqResponse.getData().toString(), JdqCheckUserData.class));
//    }
//
//    /**
//     * 3.2 进件接口
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/into-order.do")
//    public JdqResponse saveOrder(JdqResponse jdqResponse) {
//        jdqResponse = jdqService.saveOrder(JSON.parseObject(jdqResponse.getData().toString(), JdqOrderInfoRequest.class));
//        return jdqResponse;
//    }
//
//    /**
//     * 3.5 订单试算接口
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/calculate.do")
//    public JdqResponse orderCalculate(JdqResponse jdqResponse) {
//        return jdqService.calculateOrder(JSONObject.parseObject(jdqResponse.getData().toString(), JdqCalculateReq.class));
//    }
//
//    /**
//     * 3.5 确认提现接口
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/withdraw.do")
//    public JdqResponse orderWithdraw(JdqResponse jdqResponse) {
//        return jdqService.updateWithdraw(JSONObject.parseObject(jdqResponse.getData().toString(), JdqWithdrawReq.class));
//    }
//
//    /**
//     * 3.7 主动还款接口
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/repayment.do")
//    public JdqResponse orderRepayment(JdqResponse jdqResponse) {
//        return jdqService.updateActiveRepayment(JSONObject.parseObject(jdqResponse.getData().toString(), JdqRepaymentReq.class));
//    }
//
//    /**
//     * 3.10 预绑卡
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/pre-bindcard.do")
//    public JdqResponse bindCardPre(JdqResponse jdqResponse) {
//        return commonBindCard(true, jdqResponse.getData().toString());
//    }
//
//    /**
//     * 绑卡
//     *
//     * @param prefix
//     * @param data
//     * @return
//     */
//    @Deprecated
//    private JdqResponse commonBindCard(boolean prefix, String data) {
//        JdqBindCardReq jdqBindCardReq = JSONObject.parseObject(data, JdqBindCardReq.class);
//        return prefix ? jdqService.saveBindCardPre(jdqBindCardReq) : jdqService.saveBindCardWithCode(jdqBindCardReq);
//    }
//
//    /**
//     * 确认绑卡，附带手机验证码
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/bind-card.do")
//    public JdqResponse bindCardWithCode(JdqResponse jdqResponse) {
//        return commonBindCard(false, jdqResponse.getData().toString());
//    }
//
//    /**
//     * 3.13 查询绑卡信息接口
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/card-info.do")
//    public JdqResponse orderCardInfo(JdqResponse jdqResponse) {
//        return jdqService.queryCardInfo(JSONObject.parseObject(jdqResponse.getData().toString(), JdqCardInfoReq.class));
//    }
//
//    /**
//     * 3.14 合同接口
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/loan_contract.do")
//    public JdqResponse orderContract(JdqResponse jdqResponse) {
//        return jdqService.loanContract(JSONObject.parseObject(jdqResponse.getData().toString(), JdqContractReq.class));
//    }
//
//    /**
//     * 3.14 订单状态查询接口
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/order-info.do")
//    public JdqResponse queryOrderStatus(JdqResponse jdqResponse) throws InterruptedException {
//        synchronized (this) {
//            return jdqService.orderStatus(JSONObject.parseObject(jdqResponse.getData().toString(), JdqPullOrderStatusReq.class));
//        }
//    }
//
//    /**
//     * 支持的银行列表
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/bank-info.do")
//    public JdqResponse banks(JdqResponse jdqResponse) {
//        Map<String, String> cartType = JSONObject.parseObject(jdqResponse.getData().toString(), TYPE);
//        jdqResponse.setCode(0);
//        jdqResponse.setDesc("ok");
//        jdqResponse.setData(BankcardList.banks(cartType.get("card_type")));
//        return jdqResponse;
//    }
//
//
//    /**
//     * 新预绑卡
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/pre-bindcard-new.do")
//    public JdqResponse bindCardPreNew(JdqResponse jdqResponse) {
//        return commonBindCardNew(true, jdqResponse.getData().toString());
//    }
//
//    /**
//     * 新绑卡
//     *
//     * @param prefix
//     * @param data
//     * @return
//     */
//    private JdqResponse commonBindCardNew(boolean prefix, String data) {
//        JdqBindCardReq jdqBindCardReq = JSONObject.parseObject(data, JdqBindCardReq.class);
//        return prefix ? jdqService.saveBindCardPreNew(jdqBindCardReq) : jdqService.saveBindCardWithCodeNew(jdqBindCardReq);
//    }
//
//    /**
//     * 新确认绑卡，附带手机验证码
//     *
//     * @param jdqResponse
//     * @return
//     */
//    @RequestMapping("/bind-card-new.do")
//    public JdqResponse bindCardWithCodeNew(JdqResponse jdqResponse) {
//        return commonBindCardNew(false, jdqResponse.getData().toString());
//    }
//}
