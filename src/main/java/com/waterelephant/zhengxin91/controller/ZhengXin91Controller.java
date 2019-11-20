package com.waterelephant.zhengxin91.controller;

import com.beadwallet.service.entity.response.Response;
import com.beadwallet.service.zhengxin91.entity.*;
import com.beadwallet.service.zhengxin91.service.ZhengXin91ServiceSDK;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.IdcardValidator;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.zhengxin91.entity.ZxError;
import com.waterelephant.zhengxin91.service.Zx91Service;
import com.waterelephant.zhengxin91.service.ZxErrorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by song on 2017/3/30.
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/3/30 9:26
 */
@Controller
public class ZhengXin91Controller {
    private Logger logger = Logger.getLogger(ZhengXin91Controller.class);

    @Autowired
    private Zx91Service zx91Service;
    @Autowired
    private ZxErrorService zxErrorService;


    /**
     * 91征信 - 1.借贷信息查询接口 - 1001
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/app/zhengxin91/queryloan.do")
    public AppResponseResult createTask(
            HttpServletRequest request, HttpServletResponse response) {
        AppResponseResult result = new AppResponseResult();
        String realName = request.getParameter("realName");
        String idCard = request.getParameter("idCard");
        String borrowId = request.getParameter("borrowId");
        String orderId = request.getParameter("orderId");
        // 参数非空和身份证判断

        if (CommUtils.isNull(orderId)) {
            result.setCode("1004");
            result.setMsg("订单ID为空");
            return result;
        }
        if (CommUtils.isNull(borrowId)) {
            result.setCode("1005");
            result.setMsg("借款人ID为空");
            return result;
        }
        if (CommUtils.isNull(realName)) {
            result.setCode("1001");
            result.setMsg("姓名为空");
            return result;
        }
        if (CommUtils.isNull(idCard)) {
            result.setCode("1002");
            result.setMsg("身份证号码为空");
            return result;
        }
        if (!IdcardValidator.isValidatedAllIdcard(idCard)) {
            result.setCode("1003");
            result.setMsg("身份证号码不存在");
            return result;
        }
        try {
            Long.parseLong(orderId);
            Long.parseLong(borrowId);
        } catch (Exception e) {
            result.setCode("1000");
            result.setMsg("订单ID或借款人ID不为Long类型");
            return result;
        }
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("realName", realName);
        reqMap.put("idCard", idCard);
        reqMap.put("borrowId", borrowId);
        reqMap.put("orderId", orderId);
        try {
            result = zx91Service.saveTongBuGet(reqMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 91征信 - 2.借贷信息查询接口 - 1002
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/app/zhengxin91/queryInfo.do")
    public AppResponseResult queryInfo(
            HttpServletRequest request, HttpServletResponse response) {
        AppResponseResult result = new AppResponseResult();
        String trxNo = request.getParameter("trxNo");
        // 参数非空和身份证判断
        if (CommUtils.isNull(trxNo)) {
            result.setCode("1001");
            result.setMsg("查询编码为空");
            return result;
        }
        Map<String, String> reqMap = new HashMap<>();
        reqMap.put("trxNo", trxNo);
        Response<Object> s = new Response<>();
        try {
            s = ZhengXin91ServiceSDK.queryInfo(reqMap);
        } catch (Exception e) {
            result.setCode("1000");
            result.setResult("网络繁忙，请稍后再试");
            return result;
        }
        result.setCode(s.getRequestCode());
        result.setMsg(s.getRequestMsg());
        if (s.getObj() != null) {
            Pkg2002SDK pkg2002SDK = (Pkg2002SDK) s.getObj();

            result.setResult(pkg2002SDK);
        }
        return result;
    }

    /**
     * 91征信 - 测试1.1接口
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/app/zhengxin91/testCreateTrxNo.do")
    @ResponseBody
    public AppResponseResult testCreateTrxNo(
            HttpServletRequest request, HttpServletResponse response) {
        AppResponseResult result = new AppResponseResult();
        try {
            String realName = request.getParameter("realName");
            String idCard = request.getParameter("idCard");
            String borrowId = request.getParameter("borrowId");
            String orderId = request.getParameter("orderId");

            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("realName", realName);
            paramMap.put("idCard", idCard);
            paramMap.put("borrowId", borrowId);
            paramMap.put("orderId", orderId);

            result = zx91Service.saveZhenXin11(paramMap); // 91征信1.1接口
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 91征信 - 3. 借贷信息共享接口 & 借贷查询反馈接口 - 3001 & 3002
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/app/zhengxin91/infoShare.do")
    public void infoShare(HttpServletRequest request,
                          HttpServletResponse response) {
        logger.info(" ~~~~~~~~~~~~~~~~~~ 91征信 接受到91征信请求");
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buf = new byte[4096];
            int num = 0;
            while ((num = request.getInputStream().read(buf, 0, 4096)) > 0) {
                swapStream.write(buf, 0, num);
            }
            byte[] reqData = swapStream.toByteArray();
            swapStream.close();
            byte[] rspData = zx91Service.saveYiBu(reqData);
            response.getOutputStream().write(rspData);
        } catch (IOException e) {
            ZxError zxError = new ZxError();
            zxError.setCreateTime(new Date());
            zxError.setMsg(e.toString());
            zxErrorService.saveZxError(zxError);
            e.printStackTrace();
        }
    }

//
//    @Autowired
//    private ZxCeshiService zxCeshiService;
//
//    @RequestMapping("/app/zhengxin91/cesss.do")
//    public void cesss(String begin, String end) {
//        logger.info(" ~~~~~~~~~~~~~~~~~~ 91征信 接受到91征信请求");
//
//        List<ZxCeshi> zxCeshiList = zxCeshiService.findByBorrowId(begin, end);
//        for (ZxCeshi zxCeshi : zxCeshiList) {
//            Map<String, String> reqMap = new HashMap<>();
//            reqMap.put("realName", zxCeshi.getName());
//            reqMap.put("idCard", zxCeshi.getCardId());
//            reqMap.put("borrowId", zxCeshi.getBorrowId() + "");
//            reqMap.put("orderId", zxCeshi.getOrderId() + "");
//            AppResponseResult s = zx91Service.saveTongBu1003(reqMap);
//            zx91Service.saveZhenXin11(reqMap);
//
//        }
//
//    }

    public static void main(String[] args) {

        System.out.println(MyDateUtils.getDaySpace(MyDateUtils.addDays(new Date(), -20), new Date()));

    }

}

