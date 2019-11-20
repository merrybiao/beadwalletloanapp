 package com.waterelephant.dssj.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.utils.HttpClientHelper;
import com.waterelephant.dssj.entity.DssjAuthpulldata;
import com.waterelephant.dssj.service.DssjBaseService;
import com.waterelephant.entity.BwDssjAuthdata;
import com.waterelephant.service.BwDssjAuthdataService;
import com.waterelephant.utils.AppResponseResult;


@Controller
@RequestMapping("/dssj")
public class DssjCentersController {
	
	private Logger logger = LoggerFactory.getLogger(DssjCentersController.class);
	
	@Autowired
	private DssjBaseService  dssjbaseserviceimpl;
	
	@Autowired
	private ThreadPoolTaskExecutor dssjTaskExecutor;
	
	@Autowired
	private BwDssjAuthdataService bwdssjauthdataserviceimpl;
	
	/**
	 * 获取信用分请求
	 * @param reqData
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/data/getAuth.do",method = RequestMethod.POST)
	public AppResponseResult getToken(HttpServletRequest request){
		AppResponseResult response = new AppResponseResult();
		try {
			String name = request.getParameter("name");
			String idCard = request.getParameter("idCard");
			String phone = request.getParameter("phone");
			String notify_url = request.getParameter("notify_url");
			String redit_url = request.getParameter("redit_url");
			String tiNo = request.getParameter("tiNo");
			Assert.hasText(name, "缺少参数，[name]字段为空~");
			Assert.hasText(idCard, "缺少参数，[idCard]字段为空~");
			Assert.hasText(phone, "缺少参数，[phone]字段为空~");
			Assert.hasText(notify_url, "缺少参数，[notify_url]字段为空~");
			Assert.hasText(redit_url, "缺少参数，[redit_url]字段为空~");
			Assert.hasText(tiNo, "缺少参数，[tiNo]字段为空~");
			Map<String,String> resp = dssjbaseserviceimpl.saveAuthData(name, idCard, phone, notify_url, redit_url, tiNo);
			response.setCode("0000");
			response.setMsg("调用成功!");
			response.setResult(resp);
		} catch(IllegalArgumentException e){
			response.setCode("800");
			response.setMsg("调用失败!");
			logger.info("数据返回错误"+e.getMessage());
		}catch (Exception e) {
			response.setCode("900");
			response.setMsg("系统异常!");
			logger.error("系统异常"+e);
		}
		return response;
	}
	
	/**
	 * 获取信用分请求
	 * @param reqData
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/data/getScoreByQuery.do",method = RequestMethod.POST)
	public AppResponseResult getAuthData(HttpServletRequest request){
		AppResponseResult response = null;
		try {
			String name = request.getParameter("name");
			String idCard = request.getParameter("idCard");
			String phone = request.getParameter("phone");
			String token = request.getParameter("token");
			String tiNo = request.getParameter("tiNo");
			Assert.hasText(name, "缺少参数，[name]字段为空~");
			Assert.hasText(idCard, "缺少参数，[idCard]字段为空~");
			Assert.hasText(phone, "缺少参数，[phone]字段为空~");
			Assert.hasText(token, "缺少参数，[token]字段为空~");
			Assert.hasText(tiNo, "缺少参数，[tiNo]字段为空~");
			logger.info("name"+name+"idCard"+"phone"+phone+"sequenceno"+tiNo+"token"+token);
			String resp =  dssjbaseserviceimpl.saveScoreData(name, idCard, phone, token, tiNo);
			response = new AppResponseResult();
			response.setCode("0000");
			response.setMsg("请求成功!!!");
			response.setResult(resp);
		}catch(IllegalArgumentException e){
			response = new AppResponseResult();
			response.setCode("800");
			response.setMsg("获取信用分失败!!");
			logger.info(e.getMessage());
		} catch (Exception e) {
			response = new AppResponseResult();
			response.setCode("900");
			response.setMsg("获取用户评分授权失败!!!");
			logger.error("获取用户评分授权异常",e);
		}
		return response;
	}
	

	/**
	 * 获取大圣数据推送的数据
	 * @param request
	 * @param response	
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/data/rollData.do",produces="text/html;charset=UTF-8")
	public String  getRollData(HttpServletRequest request,HttpServletResponse response){
		String resp = "SUCCESS";
		String message = request.getParameter("message");
		String orderno = request.getParameter("orderNo");
		String code = request.getParameter("code");
		String res = request.getParameter("res");
		String timestamp = request.getParameter("timestamp");
		String sign = request.getParameter("sign");
		String tiNo = request.getParameter("tiNo");
		String compressstatus = request.getParameter("compressStatus");
		logger.info("三圣返回[message:"+message+"orderNo:"+orderno+"code:"+code+"res:"+res+"timestamp:"+timestamp+"sign:"+sign+"compressStatus:"+compressstatus+"tiNo:"+tiNo+"]");
		Assert.hasText(code, "无状态码");
		Assert.isTrue(code.equals("200"), "返回状态码错误[code]~");
		DssjAuthpulldata dssjpulldata = new DssjAuthpulldata();
		dssjpulldata.setCompressstatus(compressstatus);
		dssjpulldata.setOrderno(orderno);
		dssjpulldata.setTino(tiNo);
		dssjpulldata.setSign(sign);
		dssjpulldata.setTimestamp(timestamp);
		JSONObject json = JSONObject.parseObject(res);
		dssjpulldata.setStatus(json.getString("status"));
		try {
			boolean flag = dssjbaseserviceimpl.savePulldata(dssjpulldata);
			if(flag){
				logger.info("推送数据保存数据库成功!!!!~~~~~");
			}
			pushData(orderno,dssjpulldata);
		} catch(IllegalArgumentException e){
			resp = "FAIL";
			e.printStackTrace();
		}catch (Exception e) {
			resp = "FAIL";
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 推送数据
	 * @param transId
	 * @param jsonStr
	 */
	private void pushData(String orderno,DssjAuthpulldata jsonStr) {
		//推送数据
		logger.info("开始推送orderno:{}数据",orderno);
		try {
			BwDssjAuthdata bwdssjauthdata = bwdssjauthdataserviceimpl.queryBwDssjAuthdataByorderNo(orderno);
			if(bwdssjauthdata== null || StringUtils.isEmpty(bwdssjauthdata.getNotifyUrl())) {
				logger.info("transId:{}的授权记录不存在或notifiy_url为空");
				return;
			}
			String notifiy_url = bwdssjauthdata.getNotifyUrl();
			AppResponseResult responseResult = new AppResponseResult();
			responseResult.setCode("0000");
			responseResult.setMsg("推送数据~");
			responseResult.setResult(jsonStr);
			logger.info("responseResult"+JSONObject.toJSONString(responseResult));
			String jsonStrData = JSONObject.toJSONString(responseResult);
			Thread task = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i = 0;i<3;i++){
						logger.info("执行orderno:{},notifyUrl:{}推送",orderno,notifiy_url);
						String result = HttpClientHelper.post(notifiy_url, "utf-8", jsonStrData);
						logger.info("执行orderno:{},notifyUrl:{}推送,返回结果：{}",orderno,notifiy_url,result);
						if(!StringUtils.isEmpty(result)) {
							AppResponseResult appResponseResult = JSON.parseObject(result, AppResponseResult.class);
							if(null != appResponseResult) {
								String recode = appResponseResult.getCode();
								if(!StringUtils.isEmpty(recode)) {
									if("0000".equals(recode)) {
										logger.info("执行orderno:{},notifyUrl:{}推送，数据推送成功~",orderno,notifiy_url);
										return;
									}
								}
							}
						}
						continue;
					}
					logger.info("执行orderno:{},notifyUrl:{}数据推送，推送3次后还是失败了~",orderno,notifiy_url);
				}
			});
			dssjTaskExecutor.execute(task);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("执行orderno：{}数据推送异常，异常信息：{}",orderno,e.getMessage());
		}
	}
	
	@RequestMapping(value="/data/rediturl.do")
	public String reditUrl(HttpServletRequest request){
		String tino = request.getParameter("tiNo");
		String status = request.getParameter("status");
		logger.info("同步跳转页面获取到的tiNo:"+tino+"status"+status);
		BwDssjAuthdata bwdssjauthdata = null;
		try {
			bwdssjauthdata = bwdssjauthdataserviceimpl.queryRedirtUrl(tino);
			logger.info("北京提供的接口地址为:"+bwdssjauthdata.getReditUrl());
			return "redirect:"+bwdssjauthdata.getReditUrl()+"?tino="+tino+"&status="+status;
		} catch (Exception e) {
			logger.error("执行tino：{},status：{}数据推送异常，异常信息：{}",tino,status,e);
			return "redirect:"+bwdssjauthdata.getReditUrl()+"?tino="+tino+"&status="+status;
		}
	}
}
