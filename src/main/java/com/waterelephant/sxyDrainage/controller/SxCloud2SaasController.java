//package com.waterelephant.sxyDrainage.controller;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.alibaba.fastjson.JSON;
//import com.waterelephant.sxyDrainage.entity.sxy2saas.S2sRequest;
//import com.waterelephant.sxyDrainage.entity.sxy2saas.S2sResponse;
//import com.waterelephant.sxyDrainage.service.Sxy2SaasService;
//import com.waterelephant.utils.CommUtils;
//
///**
// * Module: (code:s2s)
// * <p>
// * SxCloud2SaasController.java
// *
// * @author zhangchong
// * @version 1.0
// * @description: <将水象云的用户的所有数据导入到saas中>
// * @since JDK 1.8
// */
//@Controller
//public class SxCloud2SaasController {
//    private Logger logger = Logger.getLogger(SxCloud2SaasController.class);
//    @Autowired
//    private Sxy2SaasService sxy2SaasServiceImpl;
//
//    @ResponseBody
//    @RequestMapping("/sxyDrainage/getBasicInfo.do")
//    public S2sResponse getBasicInfo(S2sRequest s2sRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + ":开始SxCloud2SaasController getBasicInfo method");
//        S2sResponse s2sResponse = new S2sResponse();
//        try {
//            if (CommUtils.isNull(s2sRequest)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("请求参数为空");
//                logger.info(
//                    sessionId + "结束SxCloud2SaasController getBasicInfo method:" + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//
//            s2sResponse = sxy2SaasServiceImpl.getBasicInfo(sessionId, s2sRequest);
//
//        } catch (Exception e) {
//            s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//            s2sResponse.setMsg("请求失败！");
//            logger.error(sessionId + "执行SxCloud2SaasController getBasicInfo method异常:" + e);
//        }
//        logger.info(sessionId + "结束SxCloud2SaasController getBasicInfo method");
//        return s2sResponse;
//    }
//
//    @ResponseBody
//    @RequestMapping("/sxyDrainage/getOperatorInfo.do")
//    public S2sResponse getOperatorInfo(S2sRequest s2sRequest) {
//        Long sessionId = System.currentTimeMillis();
//        logger.info(sessionId + ":开始SxCloud2SaasController getOperatorInfo method");
//        S2sResponse s2sResponse = new S2sResponse();
//        try {
//            if (CommUtils.isNull(s2sRequest)) {
//                s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//                s2sResponse.setMsg("请求参数为空");
//                logger.info(sessionId + "结束SxCloud2SaasController getOperatorInfo method:"
//                    + JSON.toJSONString(s2sResponse));
//                return s2sResponse;
//            }
//
//            s2sResponse = sxy2SaasServiceImpl.getOperatorInfo(sessionId, s2sRequest);
//
//        } catch (Exception e) {
//            s2sResponse.setCode(S2sResponse.CODE_FAILURE);
//            s2sResponse.setMsg("请求失败！");
//            logger.error(sessionId + "执行SxCloud2SaasController getOperatorInfo method异常:" + e);
//        }
//        logger.info(sessionId + "结束SxCloud2SaasController getOperatorInfo method");
//        return s2sResponse;
//    }
//}
