package com.waterelephant.bairong;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beadwallet.service.bairong.BaiRongSdkSercice;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.bairong.entity.User;
import com.waterelephant.utils.RedisUtils;
/**
 * 
 * BairongZhitouController.java
 * 
 * @author 吴仁彪
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 * @date 2018年4月19日
 */
@Controller
@RequestMapping("/bairong")
public class BaiRongController {
	
	private Logger logger = Logger.getLogger(BaiRongController.class);
	
	/**
	 * 执行token请求
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public void getloginInfo() throws UnsupportedEncodingException {
    	String resp = BaiRongSdkSercice.login();
    	JSONObject respjson = JSON.parseObject(resp);
		logger.info("token为:"+resp);
    	RedisUtils.setex("zhengxin:bairong", String.valueOf(respjson.get("tokenid")), 3600);
	}
	
	@ResponseBody
    @RequestMapping("/ruleSpecialList.do")
	public JSONObject getRuleSpecialList(@RequestBody User user) throws UnsupportedEncodingException{
		logger.info("进入ruleSpecialList");
		/*String rtokenId = null;
		rtokenId = RedisUtils.get("zhengxin:bairong");
		if(CommUtils.isNull(rtokenId)) {
			this.getloginInfo();
		}
		rtokenId = RedisUtils.get("zhengxin:bairong");
		Map<String,String> map = new HashMap<String,String>();
		map.put("tokenId", rtokenId);
		String rid = user.getId();
		String[] cell = user.getCell();
		String rname = user.getName();
		logger.info("获取的rtokenId的值为"+rtokenId);
		Map<String,String> param = new HashMap<String,String>();
		if(CommUtils.isNull(rid) || CommUtils.isNull(cell) || CommUtils.isNull(rname) || !(cell instanceof String[]) || cell.length == 0) {
			param.put("code", "100003");
			param.put("mesg", "必选key值缺失或不合法");
			JSONObject jsonobject = JSONObject.parseObject(JSON.toJSONString(param));
			logger.info("param的值为"+param);
	         return jsonobject;
		}else {
			map.put("id", rid);
			map.put("cell", Arrays.toString(cell));
			map.put("name", rname);
			logger.info("map的值为"+map);
		}
		logger.info("ruleSpecialList传入sercice的值为"+map);
		String resp = BaiRongSdkSercice.getRulespeciallist(map);*/
		JSONObject json = new JSONObject();
		json.put("msg", "此接口已停用");
		return json;
	}
	
	@ResponseBody
    @RequestMapping("/ruleApplyLoan_Online.do")
	public JSONObject  getRuleApplyLoan_Online(@RequestBody User user) throws UnsupportedEncodingException{
		logger.info("进入ruleSpecialList");
		/*String rtokenId = null;
		rtokenId = RedisUtils.get("zhengxin:bairong");
		if(CommUtils.isNull(rtokenId)) {
			this.getloginInfo();
		}
		rtokenId = RedisUtils.get("zhengxin:bairong");
		Map<String,String> map = new HashMap<String,String>();
		map.put("tokenId", rtokenId);
		String rid = user.getId();
		String[] cell = user.getCell();
		String rname = user.getName();
		logger.info("获取的rtokenId的值为"+rtokenId);
		Map<String,String> param = new HashMap<String,String>();
		if(CommUtils.isNull(rid) || CommUtils.isNull(cell) || CommUtils.isNull(rname) || !(cell instanceof String[]) || cell.length == 0) {
			param.put("code", "100003");
			param.put("mesg", "必选key值缺失或不合法");
			JSONObject jsonobject = JSONObject.parseObject(JSON.toJSONString(param));
			logger.info("param的值为"+param);
	         return jsonobject;
		}else {
			map.put("id", rid);
			map.put("cell", Arrays.toString(cell));
			map.put("name", rname);
			logger.info("map的值为"+map);
		}
		logger.info("ruleSpecialList传入sercice的值为"+map);
		String resp = BaiRongSdkSercice.getRulespeciallist(map);
		return JSON.parseObject(resp);*/
		JSONObject json = new JSONObject();
		json.put("msg", "此接口已停用");
		return json;
	}
	
	@ResponseBody
    @RequestMapping("/ruleExecution.do")
	public JSONObject  ruleExecution(@RequestBody User user) throws UnsupportedEncodingException{
		logger.info("进入ruleSpecialList");
		/*String rtokenId = null;
		rtokenId = RedisUtils.get("zhengxin:bairong");
		if(CommUtils.isNull(rtokenId)) {
			this.getloginInfo();
		}
		rtokenId = RedisUtils.get("zhengxin:bairong");
		Map<String,String> map = new HashMap<String,String>();
		map.put("tokenId", rtokenId);
		String rid = user.getId();
		String[] cell = user.getCell();
		String rname = user.getName();
		logger.info("获取的rtokenId的值为"+rtokenId);
		Map<String,String> param = new HashMap<String,String>();
		if(CommUtils.isNull(rid) || CommUtils.isNull(cell) || CommUtils.isNull(rname) || !(cell instanceof String[]) || cell.length == 0) {
			param.put("code", "100003");
			param.put("mesg", "必选key值缺失或不合法");
			JSONObject jsonobject = JSONObject.parseObject(JSON.toJSONString(param));
			logger.info("param的值为"+param);
	         return jsonobject;
		}else {
			map.put("id", rid);
			map.put("cell", Arrays.toString(cell));
			map.put("name", rname);
			logger.info("map的值为"+map);
		}
		logger.info("ruleSpecialList传入sercice的值为"+map);
		String resp = BaiRongSdkSercice.getRulespeciallist(map);
		return JSON.parseObject(resp);*/
		JSONObject json = new JSONObject();
		json.put("msg", "此接口已停用");
		return json;
	}

}
