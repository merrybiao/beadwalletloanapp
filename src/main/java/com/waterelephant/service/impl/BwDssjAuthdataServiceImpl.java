package com.waterelephant.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.dssj.entity.DssjAuthpulldata;
import com.waterelephant.entity.BwDssjAuthdata;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwDssjAuthdataService;
import com.waterelephant.utils.DateUtil;
@Service
public class BwDssjAuthdataServiceImpl extends BaseService<BwDssjAuthdata, Long> implements BwDssjAuthdataService {

	private Logger logger = LoggerFactory.getLogger(BwDssjAuthdataServiceImpl.class);

	/**
	 * 查询授权记录数据
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BwDssjAuthdata queryBwDssjAuthdataByTiNo(String tino) throws Exception {
		String sql = "SELECT * FROM bw_dssj_updauthdata WHERE tiNo = '"+tino+"'";
		return sqlMapper.selectOne(sql, BwDssjAuthdata.class);
	}
	
	/**
	 * 保存授权记录数据
	 */
	@Override
	public long saveBwDssjAuthdata(String id,String name,String idCard,String phone,String notify_url,String redit_url,String url,String tiNo) throws Exception {
		BwDssjAuthdata BwDssjAuth = new BwDssjAuthdata();
		BwDssjAuth.setUid(Long.parseLong(id));
		BwDssjAuth.setName(name);
		BwDssjAuth.setIdcard(idCard);
		BwDssjAuth.setPhone(phone);
		BwDssjAuth.setNotifyUrl(notify_url);
		BwDssjAuth.setReditUrl(redit_url);
		BwDssjAuth.setUrl(url);
		BwDssjAuth.setTino(tiNo);
		BwDssjAuth.setInsertTime(new Date());
		logger.info("BwDssjAuth"+JSONObject.toJSONString(BwDssjAuth));
		return insert(BwDssjAuth) == 1 ? BwDssjAuth.getId() : 0;
	}
	
	/**
	 * 保存推送过来的数据
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean updatePullBwDssjAuthdata(DssjAuthpulldata dssjauthpulldata) throws Exception {
		String sql = "UPDATE bw_dssj_updauthdata SET "
				+ " status = '"+dssjauthpulldata.getStatus()+"',"
				+ " timestamp = '"+dssjauthpulldata.getTimestamp()+"',"
				+ " compressStatus = '"+dssjauthpulldata.getCompressstatus()+"',"
				+ " sign = '"+dssjauthpulldata.getSign()+"',"
				+ " orderNo = '"+dssjauthpulldata.getOrderno()+"'"
				+ " WHERE tiNo = '"+ dssjauthpulldata.getTino()+"'";
		return sqlMapper.update(sql)>0;
	}


	@SuppressWarnings("deprecation")
	@Override
	public boolean updateScore(String score,String tiNo) throws Exception {
		String sql = "UPDATE bw_dssj_updauthdata SET "
				+ " score = '"+score+"',"
				+ " update_time = '" + DateUtil.getDateString(new Date(),DateUtil.yyyy_MM_dd_HHmmss) + "'"
				+ " WHERE tiNo = '"+ tiNo+"'";
		return sqlMapper.update(sql)>0;
	}

	/**
	 * 查询重定向页面地址
	 */
	@SuppressWarnings("deprecation")
	@Override
	public BwDssjAuthdata queryRedirtUrl(String tino) throws Exception {
		String sql = "SELECT * FROM bw_dssj_updauthdata WHERE tiNo = '"+tino+"'";
		return sqlMapper.selectOne(sql, BwDssjAuthdata.class);
	}

	@SuppressWarnings("deprecation")
	@Override
	public BwDssjAuthdata queryBwDssjAuthdataByorderNo(String orderno)
			throws Exception {
		String sql = "SELECT * FROM bw_dssj_updauthdata WHERE orderNo = '"+orderno+"'";
		return sqlMapper.selectOne(sql, BwDssjAuthdata.class);
	}
}
