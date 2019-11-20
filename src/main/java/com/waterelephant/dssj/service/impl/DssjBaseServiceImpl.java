package com.waterelephant.dssj.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.beadwallet.utils.CommUtils;
import com.waterelephant.dssj.entity.DssjAuthpulldata;
import com.waterelephant.dssj.service.DssjBaseService;
import com.waterelephant.entity.BwDssjAuthdata;
import com.waterelephant.entity.BwDssjToken;
import com.waterelephant.service.BwDssjAuthdataService;
import com.waterelephant.service.BwDssjTokenService;

@Service
public class DssjBaseServiceImpl extends DssjBusiInfo implements DssjBaseService {
	
	private Logger logger = LoggerFactory.getLogger(DssjBaseServiceImpl.class);
	
	@Autowired
	private BwDssjTokenService bwdssjtokenserviceimpl;
	
	@Autowired
	private BwDssjAuthdataService BwDssjAuthdataServiceimpl;
	

	/**
	 * 获取token
	 */
	@Override
	public Map<String,String> saveToken() throws Exception {
		Map<String,String> map = new HashMap<String, String>();
		JSONObject json = JSONObject.parseObject(getToken());
		map.put("token", json.getString("token"));
		Long id =null;
		BwDssjToken bwdssjtoken = bwdssjtokenserviceimpl.queryBwDssjTokenbyToken(json.getString("token"));
		if(CommUtils.isNull(bwdssjtoken)){
			id = bwdssjtokenserviceimpl.save(json.getString("expire"), json.getString("token"));
			if(id != 0){
				logger.info("插入授权token数据成功，插入的主键值为："+id);
			}
		} else {
			id = bwdssjtoken.getId();
			logger.info("token数据已存在数据库");
		}
		map.put("id", String.valueOf(id));
		return map;
	}

	/**
	 * 获取授权链接和存储用户基本信息
	 */
	@Override
	public Map<String,String> saveAuthData(String name, String idCard, String phone,String notify_url,String redit_url,
			String tiNo) throws Exception {
		Map<String,String> map = saveToken();
		Map<String,String> respmap = new HashMap<String, String>();
		respmap.put("token", map.get("token"));
		BwDssjAuthdata bwdssjauthdata  = BwDssjAuthdataServiceimpl.queryBwDssjAuthdataByTiNo(tiNo);
		String url = null;
		if(CommUtils.isNull(bwdssjauthdata)){
			url = getAuth(name,idCard,phone,map.get("token"),tiNo);
			Long id = BwDssjAuthdataServiceimpl.saveBwDssjAuthdata(map.get("id"),name,idCard, phone,notify_url,redit_url,url, tiNo);
			if(id !=0){
				logger.info("授权基本信息保存成功~~~~~!!");
			}
		} else {
			url = bwdssjauthdata.getUrl();
		}
		respmap.put("url", url);
		return respmap;
	}

	/**
	 * 保存推送过来的数据信息
	 * @throws Exception 
	 */
	@Override
	public boolean savePulldata(DssjAuthpulldata bwdssjauthdata) throws Exception {
		
		return BwDssjAuthdataServiceimpl.updatePullBwDssjAuthdata(bwdssjauthdata);
		
	}

	/**
	 * 保存查询的信用分
	 * @return
	 */
	@Override
	public String saveScoreData(String name,String idCard,String phone,String token,String tiNo)
			throws Exception {
		
		String score = getScoreByQuery(name,idCard,phone,token);
		
		BwDssjAuthdataServiceimpl.updateScore(score,tiNo);
		
		return score;
	}
}
