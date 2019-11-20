package com.waterelephant.gxboss.comtroller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.gxboss.constant.GxbEnum;
import com.waterelephant.gxboss.constant.OSSUtil;
import com.waterelephant.gxboss.exception.GxbException;
import com.waterelephant.gxboss.service.GxbOssServicce;
import com.waterelephant.utils.GzipUtil;

@RestController
@RequestMapping(value = "gxb")
public class GxbOssController {
	
	private Logger logger = LoggerFactory.getLogger(GxbOssController.class);
	
	@Autowired
	private GxbOssServicce gxbOssServicceImpl;
	
	@RequestMapping("/getAuthData.do")
	public Map<String,Object> getGxbAuthData(HttpServletRequest request){
		Map<String, Object> respmap = new HashMap<String, Object>();
		String searchId = request.getParameter("searchId");
		String channel = request.getParameter("channel");
		String isGzip = request.getParameter("isGzip");
		Assert.hasText(searchId,"~[searchId]参入参数为空~");
		Assert.hasText(channel,"~[channel]参入参数为空~");
		Assert.hasText(isGzip,"~[isGzip]参入参数为空~");
		//授信单
		JSONObject json = null;
		Object result = null;
		String fileUrl = null;
		Integer creditType = GxbEnum.verifyChannel(channel.toUpperCase());
		if(!creditType.equals(-1)) {
			if(searchId.toUpperCase().startsWith("S")) {
				Long id = gxbOssServicceImpl.queryBwBorrowerCreditByCreditNo(searchId);
				if(!CommUtils.isNull(id)) {
					fileUrl = gxbOssServicceImpl.queryBwCreditURLByRelationId(id,1,creditType);
				}
			}else if(searchId.toUpperCase().startsWith("Y")){
			     Long id = gxbOssServicceImpl.queryBwOrderIdByCreditNo(searchId);
			     if(!CommUtils.isNull(id)) {
		    	     fileUrl = gxbOssServicceImpl.queryBwCreditURLByRelationId(id,0,creditType);
			     }
			}
			else {
				throw new GxbException("~工单号不正确，请输入合法的工单号~");
			}
			if(!CommUtils.isNull(fileUrl)) {
				json = OSSUtil.downLoadOssFile(fileUrl.substring(fileUrl.indexOf("/")+1));
			} else {
				respmap.put("ret_code","600");
				respmap.put("ret_msg", "~请求失败，获取的结果为空~");
			}
			if(!CommUtils.isNull(json)) {
				result = Boolean.valueOf(isGzip) ? GzipUtil.gzip(JSON.toJSONString(json)) : json;
				respmap.put("ret_code","0000");
				respmap.put("ret_msg", "请求成功");
				respmap.put("ret_data", result);
	    		logger.info(result.toString());
			} else {
				throw new GxbException("~请求结果为空，请检查文件的完整性~");
			}
		} else {
			throw new GxbException("~产品名称不正确，请输入合法的产品名称~");
		}
		  return respmap;
	}
	
	@ExceptionHandler({Exception.class})
	public Map<String,String> IllegalArgumentException(Exception e){
		Map<String,String> map = new HashMap<String, String>();
		if(e instanceof IllegalArgumentException) {
			map.put("ret_code","900");
			map.put("ret_msg", e.getMessage());
		} else if (e instanceof GxbException) {
			map.put("ret_code","800");
			map.put("ret_msg", e.getMessage());
		} else {
			map.put("ret_code","700");
			map.put("ret_msg", "~系统异常，请稍后再试~");
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static void main1(String[] args) {
		String str = "waterelephant/loan/auth/2019-04-15/1112953066704662639_8.json";
		int i = str.indexOf("/");
		String string = str.substring(i);
		System.out.println(string);
	}
	
	public static <T> T FER( T t) {
		return t;
	}
	
	public static void main(String[] args) {
		String searchId = "s5345646456";
		int index = searchId.indexOf("_");
		String prefix = searchId.substring(0, index+1).trim();
	    String content = searchId.substring(index+1).trim();
		boolean b = searchId.toUpperCase().startsWith("S");
		System.out.println(searchId.toUpperCase());
		System.out.println(prefix);
		System.out.println(content);
		System.out.println(b);
	}
	
	
}
