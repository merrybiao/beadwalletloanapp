package com.waterelephant.controller;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beadwallet.utils.CommUtils;
import com.waterelephant.entity.WeixinOauth2Token;
import com.waterelephant.utils.AdvancedUtil;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CdnUploadTools;
import com.waterelephant.utils.JssdkUtil;
import com.waterelephant.utils.SystemConstant;
import com.waterelephant.utils.WeixinUtil;
import com.waterelephant.vo.JssdkSignature;

/**
 * 微信后端处理控制器
 * 
 * @author duxiaoyong
 *
 */
@Controller
@RequestMapping("/weixin/")
public class WeixinController {
	private Logger logger = Logger.getLogger(WeixinController.class);

	/**
	 * 从微信获取当前用户openid并保存（首页）
	 */
	@ResponseBody
	@RequestMapping("getOpenIdIndex.do")
	public AppResponseResult getOpenIdIndex(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String code = request.getParameter("code");
		logger.info("获取微信openid传入的code为：" + code);
		if (CommUtils.isNull(code)) {
			result.setCode("101");
			result.setMsg("code为空");
			return result;
		}
		WeixinOauth2Token token = AdvancedUtil.getOauth2AccessToken(SystemConstant.APPID, SystemConstant.APP_SECRET, code);
		logger.info("获取到的微信openid为：" + (token == null ? null : token.getOpenId()));
		if (CommUtils.isNull(token) || CommUtils.isNull(token.getOpenId())) {
			result.setCode("102");
			result.setMsg("获取openid失败！");
			return result;
		}
		request.getSession().setAttribute(SystemConstant.WEIXIN_OPENID_TOKEN, token.getOpenId());
		result.setCode("000");
		result.setMsg("获取openid成功");
		result.setResult(token.getOpenId());
		return result;
	}
	
	/**
	 * 获取当前用户openid
	 */
	@ResponseBody
	@RequestMapping("getOpenId.do")
	public AppResponseResult getOpenId (HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String openid = (String) request.getSession().getAttribute(SystemConstant.WEIXIN_OPENID_TOKEN);
		logger.info("会话中存储的openid为：" + openid);
		if (CommUtils.isNull(openid)) {
			result.setCode("102");
			result.setMsg("获取openid失败！");
			return result;
		}
		result.setCode("000");
		result.setMsg("获取openid成功");
		result.setResult(openid);
		return result;
	}
	
	/**
	 * 获取微信JS-SDK签名信息
	 */
	@ResponseBody
	@RequestMapping("getJssdkSignature.do")
	public AppResponseResult getJssdkSignature(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String url = request.getParameter("url");
		logger.info("获取微信JS-SDK签名信息传入的url为：" + url);
		if (CommUtils.isNull(url)) {
			result.setCode("101");
			result.setMsg("url为空");
			return result;
		}
		JssdkSignature jssdkSignature = JssdkUtil.getJssdkSignature(url);
		result.setCode("000");
		result.setMsg("获取签名信息成功");
		result.setResult(jssdkSignature);
		return result;
	}
	
	/**
	 * 保存用户位置信息（首页）
	 */
	@ResponseBody
	@RequestMapping("getLocationIndex.do")
	public AppResponseResult getLocationIndex(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String location = request.getParameter("location");
		logger.info("保存用户位置信息传入的location为：" + location);
		if (CommUtils.isNull(location)) {
			result.setCode("101");
			result.setMsg("location为空");
			return result;
		}
		request.getSession().setAttribute(SystemConstant.WEIXIN_LOCATION_TOKEN, location);
		result.setCode("000");
		result.setMsg("保存用户位置信息成功");
		return result;
	}
	
	/**
	 * 获取当前用户位置
	 */
	@ResponseBody
	@RequestMapping("getLocation.do")
	public AppResponseResult getLocation(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String location = (String) request.getSession().getAttribute(SystemConstant.WEIXIN_LOCATION_TOKEN);
		logger.info("会话中存储的location为：" + location);
		if (CommUtils.isNull(location)) {
			result.setCode("102");
			result.setMsg("获取location失败！");
			return result;
		}
		result.setCode("000");
		result.setMsg("获取location成功");
		result.setResult(location);
		return result;
	}
	
	/**
	 * 下载微信服务器图片并上传到阿里云
	 */
	@ResponseBody
	@RequestMapping("appCheckLogin/downloadWeixinToOss.do")
	public AppResponseResult downloadWeixinToOss(HttpServletRequest request) {
		AppResponseResult result = new AppResponseResult();
		String mediaId = request.getParameter("mediaId");
		logger.info("下载微信服务器图片并上传到阿里云传入的mediaId为：" + mediaId);
		if (CommUtils.isNull(mediaId)) {
			result.setCode("101");
			result.setMsg("mediaId为空");
			return result;
		}
		String uploadPath = null;
		try {
			Object[] res = WeixinUtil.downloadMedia(mediaId);
			InputStream in = (InputStream) res[0];
			String filename = (String) res[1];
			logger.info("微信服务器返回的文件名为：" + filename);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			uploadPath = "upload/backend/" + sdf.format(new Date()) + "/" + sdf2.format(new Date()) + CommUtils.getRandomNumber(3)
					+ filename.substring(filename.indexOf("."));
			CdnUploadTools.uploadPic(in, uploadPath);
			in.close();
		} catch (Exception e) {
			logger.error("上传图片发生错误：" + e.getMessage());
			e.printStackTrace();
			result.setCode("102");
			result.setMsg("图片上传错误");
			return result;
		}
		result.setCode("000");
		result.setMsg("图片上传成功");
		result.setResult(uploadPath);
		return result;
	}
	
}
