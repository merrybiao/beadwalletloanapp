//package com.waterelephant.sxyDrainage.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.TreeMap;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.waterelephant.entity.*;
//import com.waterelephant.service.*;
//import com.waterelephant.sxyDrainage.service.XjbkPullOrderInfoService;
//import com.waterelephant.utils.CommUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.commons.lang3.math.NumberUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.drainage.entity.xianJinCard.XianJinCardResponse;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaCommonRequest;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaRequest;
//import com.waterelephant.sxyDrainage.entity.xianjinbaika.XianJinBaiKaResponse;
//import com.waterelephant.sxyDrainage.service.XianJinBaiKaService;
//import com.waterelephant.sxyDrainage.utils.xianJinBaiKa.XianJinBaiKaConstant;
//import com.waterelephant.sxyDrainage.utils.xianJinBaiKa.XianJinBaiKaUtil;
//import com.waterelephant.third.service.ThirdCommonService;
//import com.waterelephant.utils.Base64;
//import com.waterelephant.utils.RedisUtils;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 现金白卡
// * <p>
// * Module: (code:xjbk)
// * <p>
// * XianJinBaiKaController.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <描述>
// * @since JDK 1.8
// */
//@Controller
//public class XianJinBaiKaController {
//    private Logger logger = LoggerFactory.getLogger(XianJinBaiKaController.class);
//    private String channelId = XianJinBaiKaConstant.CHANNELID;
//
//    @Autowired
//    private XianJinBaiKaService xianJinBaiKaService;
//    @Autowired
//    private IBwBorrowerService bwBorrowerService;
//    @Autowired
//    private IBwOrderService bwOrderService;
//    @Autowired
//    private ThirdCommonService thirdCommonService;
//    @Autowired
//    private BwOrderAuthService bwOrderAuthService;
//    @Autowired
//    private XjbkPullOrderInfoService xjbkPullOrderInfoService;
//    @Autowired
//    private IBwBankCardService bwBankCardService;
//    @Autowired
//    private BwOrderRongService bwOrderRongService;
//
//    @ResponseBody
//    @RequestMapping("/sxyDrainage/xjbk.do")
//    public XianJinBaiKaResponse xianjincard(XianJinBaiKaRequest xianJinBaiKaRequest) {
//        long sessionId = System.currentTimeMillis();
//        XianJinBaiKaResponse xianJinBaiKaResponse = new XianJinBaiKaResponse();
//        try {
//            logger.info(sessionId + "：开始XianJinBaiKaController xianjincard method");
//            // 获取业务参数
//            String args = xianJinBaiKaRequest.getArgs();
//            String call = xianJinBaiKaRequest.getCall();
//            String sign = xianJinBaiKaRequest.getSign();
//            XianJinBaiKaCommonRequest xianJinBaiKaCommonRequest = JSON.parseObject(args,
//                    XianJinBaiKaCommonRequest.class);
//
//            logger.info(sessionId + "：call=" + call + ",sign=" + sign);
//            if (StringUtils.isBlank(call) || StringUtils.isBlank(sign)) {
//                xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("入参不合法");
//                logger.info(sessionId + "：结束xianjincard：" + JSON.toJSONString(xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            // 验证签名
//            boolean flag = XianJinBaiKaUtil.checkSign(call, args, sign);
//            if (!flag) {
//                xianJinBaiKaResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("sign错误");
//                logger.info(sessionId + "：结束xianjincard：" + JSON.toJSONString(xianJinBaiKaResponse));
//                return xianJinBaiKaResponse;
//            }
//
//            // 处理业务
//            if ("User.isUserAccept".equals(call)) {
//                // 1.3.1. 用户过滤
//                xianJinBaiKaResponse = xianJinBaiKaService.checkUser(sessionId, xianJinBaiKaCommonRequest);
//
//            } else if ("Order.pushUserBaseInfo".equals(call)) {
//                // 1.3.7. 推送用户基础信息
//                xianJinBaiKaResponse = xjbkPullOrderInfoService.savePushUserBaseInfo(sessionId, xianJinBaiKaCommonRequest);
//
//            } else if ("Order.pushUserAdditionalInfo".equals(call)) {
//                // 1.3.8. 推送用户补充信息
//                xianJinBaiKaResponse = xjbkPullOrderInfoService.savePushUserAdditionalInfo(sessionId, xianJinBaiKaCommonRequest);
//
//            } else if ("BindCard.getValidBankList".equals(call)) {
//                // 1.3.4. 获取绑卡银行列表
//                xianJinBaiKaResponse = xianJinBaiKaService.getValidBankList(sessionId);
//
//            } else if ("BindCard.applyBindCard".equals(call)) {
//                // 1.3.5. 订单绑卡接口
//                xianJinBaiKaResponse = xianJinBaiKaService.updateApplyBindCardNew(sessionId, xianJinBaiKaCommonRequest);
//
//            } else if ("BindCard.getUserBindBankCardList".equals(call)) {
//                // 1.3.20. 获取用户已绑银行卡
//                xianJinBaiKaResponse = xianJinBaiKaService.getUserBindBankCardList(sessionId, xianJinBaiKaCommonRequest);
//
//            } else if ("Order.getContracts".equals(call)) {
//                // 1.3.2. 合同获取接口
//                xianJinBaiKaResponse = xianJinBaiKaService.getContracts(sessionId, xianJinBaiKaCommonRequest);
//
//            } else if ("Order.getRepayplan".equals(call)) {
//                // 1.3.14. 拉取还款计划
//                xianJinBaiKaResponse = xianJinBaiKaService.getRepayplan(sessionId, xianJinBaiKaCommonRequest);
//
//            } else if ("Order.getOrderStatus".equals(call)) {
//                // 1.3.15. 拉取订单状态
//                xianJinBaiKaResponse = xianJinBaiKaService.getOrderStatus(sessionId, xianJinBaiKaCommonRequest);
//
//            } else if ("Order.loanCalculate".equals(call)) {
//                // 1.3.3. 借款试算接口
//                xianJinBaiKaResponse = xianJinBaiKaService.loanCalculate(sessionId, xianJinBaiKaCommonRequest);
//
//            } else if ("Order.applyRepay".equals(call)) {
//                // 1.3.12. 订单还款接口
//                xianJinBaiKaResponse = xianJinBaiKaService.updateApplyRepayNew(sessionId, xianJinBaiKaCommonRequest);
//
//            } else if ("User.authStatus".equals(call)) {
//                // 1.3.22.1. H5认证状态查询接口
//                xianJinBaiKaResponse = xianJinBaiKaService.authStatus(sessionId, xianJinBaiKaCommonRequest);
//
//            } else {
//                xianJinBaiKaResponse.setStatus(XianJinCardResponse.CODE_FAILURE);
//                xianJinBaiKaResponse.setMessage("请求失败");
//            }
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行xianjincard method 异常：", e);
//            xianJinBaiKaResponse.setStatus(XianJinBaiKaResponse.CODE_FAILURE);
//            xianJinBaiKaResponse.setMessage("请求失败");
//        }
//        logger.info(sessionId + "：结束xianjincard：" + JSON.toJSONString(xianJinBaiKaResponse));
//        return xianJinBaiKaResponse;
//    }
//
//    /**
//     * 1.3.22.2. H5认证接口
//     *
//     * @param request  请求参数
//     * @param modelMap 模板
//     * @return String
//     */
//    @RequestMapping("/sxyDrainage/xjbk/orderAuth.do")
//    public String orderAuth(HttpServletRequest request, ModelMap modelMap) {
//        long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始controller orderAuth method");
//
//        // 获取参数
//        String thirdOrderNo = request.getParameter("order_sn");
//        String userName = request.getParameter("user_name");
//        String userPhone = request.getParameter("user_phone");
//        String userIdcard = request.getParameter("user_idcard");
//        String returnUrl = request.getParameter("return_url");
//        String sign = request.getParameter("sign");
//        logger.info(sessionId + ":order_sn=" + thirdOrderNo + ",user_phone=" + userPhone + ",return_url=" + returnUrl);
//        String url = null;
//        try {
//            url = new String(Base64.decode(returnUrl), "utf-8");
//            url = XianJinBaiKaUtil.getUrl(url);
//            logger.info(sessionId + "：returnUrl==" + url);
//
//            // 验证参数
//            if (StringUtils.isBlank(thirdOrderNo) || StringUtils.isBlank(userName) || StringUtils.isBlank(userPhone) ||
//                    StringUtils.isBlank(userIdcard) || StringUtils.isBlank(returnUrl) || StringUtils.isBlank(sign)) {
//                logger.info(sessionId + "：结束orderAuth method：参数不合法");
//                // 通知现金白卡
//                HashMap<String, String> hm = new HashMap<>(16);
//                hm.put("channelId", channelId);
//                hm.put("userPhone", userPhone);
//                hm.put("result", "参数不合法");
//                String hmData = JSON.toJSONString(hm);
//                RedisUtils.rpush("tripartite:h5auth:" + channelId, hmData);
//
//                return "redirect:" + url;
//            }
//
//            // 验证签名
//            Map<String, String> treeMap = new TreeMap<>();
//            treeMap.put("order_sn", thirdOrderNo);
//            treeMap.put("user_name", userName);
//            treeMap.put("user_phone", userPhone);
//            treeMap.put("user_idcard", userIdcard);
//            treeMap.put("return_url", returnUrl);
//            treeMap.put("sign", sign);
//            boolean flag = XianJinBaiKaUtil.checkH5Sign(treeMap);
//            if (!flag) {
//                logger.info(sessionId + "：结束orderAuth method：sign错误");
//                // 通知现金白卡
//                HashMap<String, String> hm = new HashMap<>(16);
//                hm.put("channelId", channelId);
//                hm.put("userPhone", userPhone);
//                hm.put("result", "sign错误");
//                String hmData = JSON.toJSONString(hm);
//                RedisUtils.rpush("tripartite:h5auth:" + channelId, hmData);
//
//                return "redirect:" + url;
//            }
//
//            // 判断用户是否存在
//            BwBorrower borrower = new BwBorrower();
//            borrower.setPhone(userPhone);
//            borrower.setIdCard(userIdcard);
//            borrower.setName(userName);
//            borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
//            if (borrower == null) {
//                logger.info(sessionId + "：结束orderAuth method：用户不存在");
//                // 通知现金白卡
//                HashMap<String, String> hm = new HashMap<>(16);
//                hm.put("channelId", channelId);
//                hm.put("userPhone", userPhone);
//                hm.put("result", "用户不存在");
//                String hmData = JSON.toJSONString(hm);
//                RedisUtils.rpush("tripartite:h5auth:" + channelId, hmData);
//
//                return "redirect:" + url;
//            }
//
//            //检查是否绑卡
//            BwBankCard bwBankCard = bwBankCardService.findBwBankCardByBoorwerId(borrower.getId());
//            if (null == bwBankCard || 4 != bwBankCard.getSignStatus()) {
//                logger.info(sessionId + "：结束orderAuth method：用户未绑卡");
//                // 通知现金白卡
//                HashMap<String, String> hm = new HashMap<>(16);
//                hm.put("channelId", channelId);
//                hm.put("userPhone", userPhone);
//                hm.put("result", "用户未绑卡");
//                String hmData = JSON.toJSONString(hm);
//                RedisUtils.rpush("tripartite:h5auth:" + channelId, hmData);
//
//                return "redirect:" + url;
//            }
//
//            BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(thirdOrderNo);
//            if (CommUtils.isNull(bwOrderRong)) {
//                // 通知现金白卡
//                HashMap<String, String> hm = new HashMap<>(16);
//                hm.put("channelId", channelId);
//                hm.put("userPhone", userPhone);
//                hm.put("result", "第三方订单不存在");
//                String hmData = JSON.toJSONString(hm);
//                RedisUtils.rpush("tripartite:h5auth:" + channelId, hmData);
//
//                logger.info(sessionId + "：结束orderAuth method：第三方订单不存在");
//                return "redirect:" + url;
//            }
//            Long orderId = bwOrderRong.getOrderId();
//
//            BwOrder bwOrder = bwOrderService.findBwOrderById(String.valueOf(orderId));
//            if (CommUtils.isNull(bwOrder)) {
//                // 通知现金白卡
//                HashMap<String, String> hm = new HashMap<>(16);
//                hm.put("channelId", channelId);
//                hm.put("userPhone", userPhone);
//                hm.put("result", "我方订单不存在");
//                String hmData = JSON.toJSONString(hm);
//                RedisUtils.rpush("tripartite:h5auth:" + channelId, hmData);
//
//                logger.info(sessionId + "：结束orderAuth method： 我方订单不存在");
//                return "redirect:" + url;
//            }
//
//            //判断用户是否已认证，防止重复进入
//            BwOrderAuth bwOrderAuth = bwOrderAuthService.findBwOrderAuth(bwOrder.getId(), 11);
//            if (bwOrderAuth != null) {
//                logger.info(sessionId + "：结束orderAuth： 用户已认证，orderId=" + bwOrder.getId());
//                return "redirect:" + url;
//            }
//
//            modelMap.addAttribute("orderId", bwOrder.getId());
//            modelMap.addAttribute("userPhone", userPhone);
//            modelMap.addAttribute("returnUrl", returnUrl);
//            logger.info(sessionId + "：结束orderAuth method：跳转成功");
//            return "order_auth";
//
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行orderAuth method异常:", e);
//            // 通知现金白卡
//            HashMap<String, String> hm = new HashMap<>(16);
//            hm.put("channelId", channelId);
//            hm.put("userPhone", userPhone);
//            hm.put("result", "系统异常");
//            String hmData = JSON.toJSONString(hm);
//            RedisUtils.rpush("tripartite:h5auth:" + channelId, hmData);
//
//            return "redirect:" + url;
//        }
//    }
//
//    @RequestMapping("/sxyDrainage/xjbk/saveOrderAuth.do")
//    public String saveOrderAuth(HttpServletRequest request) {
//        long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + "：开始saveOrderAuth method");
//
//        // 获取参数
//        String orderId = request.getParameter("order_id");
//        String userPhone = request.getParameter("user_phone");
//        String returnUrl = request.getParameter("return_url");
//        logger.info(sessionId + ":order_id=" + orderId + ",user_phone=" + userPhone + ",return_url=" + returnUrl);
//        String url = null;
//        try {
//            url = new String(Base64.decode(returnUrl), "utf-8");
//            url = XianJinBaiKaUtil.getUrl(url);
//            logger.info(sessionId + "：returnUrl==" + url);
//
//            // 插入用款确认记录
//            thirdCommonService.addOrUpdateBwOrderAuth(sessionId, Long.parseLong(orderId), 11, NumberUtils.toInt(channelId));
//            logger.info(sessionId + "：结束saveOrderAuth method：确认成功");
//
//            // 通知现金白卡
//            HashMap<String, String> hm = new HashMap<>(16);
//            hm.put("channelId", channelId);
//            hm.put("userPhone", userPhone);
//            hm.put("result", "确认成功");
//            String hmData = JSON.toJSONString(hm);
//            RedisUtils.rpush("tripartite:h5auth:" + channelId, hmData);
//
//            return "redirect:" + url;
//
//        } catch (Exception e) {
//            logger.error(sessionId + "：执行saveOrderAuth method异常:", e);
//            // 通知现金白卡
//            HashMap<String, String> hm = new HashMap<>(16);
//            hm.put("channelId", channelId);
//            hm.put("userPhone", userPhone);
//            hm.put("result", "系统异常");
//            String hmData = JSON.toJSONString(hm);
//            RedisUtils.rpush("tripartite:h5auth:" + channelId, hmData);
//
//            return "redirect:" + url;
//        }
//    }
//
//}
