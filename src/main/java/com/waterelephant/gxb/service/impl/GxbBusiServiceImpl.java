package com.waterelephant.gxb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwGxbAuthRecord;
import com.waterelephant.entity.BwGxbTaxiBaseInfo;
import com.waterelephant.gxb.dto.AuthInfoDto;
import com.waterelephant.gxb.dto.TaxiCommonAddressDto;
import com.waterelephant.gxb.dto.TaxiDataDto;
import com.waterelephant.gxb.dto.TaxiOrderDto;
import com.waterelephant.gxb.service.GxbBusiService;
import com.waterelephant.gxb.utils.GxbConstant;
import com.waterelephant.service.BwGxbAuthRecordService;
import com.waterelephant.service.BwGxbTaxiBaseInfoService;
import com.waterelephant.service.BwGxbTaxiCommonAddressDtoService;
import com.waterelephant.service.BwGxbTaxiOrderService;

@Service
public class GxbBusiServiceImpl extends GxbBaseServiceImpl implements GxbBusiService {
	
	private Logger logger = LoggerFactory.getLogger(GxbBusiServiceImpl.class);
	
	@Autowired
	private BwGxbAuthRecordService authRecordService;
	
	@Autowired
	private BwGxbTaxiBaseInfoService taxiBaseInfoService;
	
	@Autowired
	private BwGxbTaxiOrderService taxiOrderService;
	
	@Autowired
	private BwGxbTaxiCommonAddressDtoService bwgxbtaxicommonaddressdtoserviceimpl;

	@Override
	public String getToken(String name, String phone, String idCard, String authItem, String sequenceNo,
			String timestamp) throws Exception {
		if(!GxbConstant.AUTH_ITEMS.contains(authItem)) {
			throw new IllegalAccessException("授权项不正确");
		}
		return token(name, idCard, phone, authItem, timestamp, sequenceNo);
				
	}

	@Override
	public Long saveAuthRecord(String name, String phone, String idCard, String authItem,String sequenceNo,String returnUrl,String notifyUrl,String timestamp)
			throws Exception {
		
		return authRecordService.save(name, idCard, phone, authItem, sequenceNo, returnUrl, notifyUrl, timestamp);
		
	}

	@Override
	public Map<String,String> quickAuth(String sequenceNo,String name, String idCard, String phone, String authItem,String returnUrl,String notifyUrl, String timestamp)
			throws Exception {
		if(!GxbConstant.AUTH_ITEMS.contains(authItem)) {
			throw new IllegalAccessException("授权项不正确");
		}
		Map<String,String> resultMap = new HashMap<>();
		try {
			String token =  token(name, idCard, phone, authItem, timestamp,sequenceNo);
			String redirectUrl = auth(token,returnUrl,phone);
			
			resultMap.put("token", token);
			resultMap.put("redirect_url", redirectUrl);
			resultMap.put("sequence_no", sequenceNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("快速授权失败~异常信息：{}",e.getMessage());
			throw new Exception("授权失败~请稍后再试~");
		}
		return resultMap;
	}
	
	@Override
	public BwGxbAuthRecord queryAuthRecordBySequenceNo(String sequenceNo) throws Exception {
		
		BwGxbAuthRecord authRecord = authRecordService.queryBySequenceNo(sequenceNo);
		
		return authRecord;
	}
	

	@Override
	public TaxiDataDto queryRawdata(String sequenceNo) throws Exception {
		TaxiDataDto taxiDataDto = null;
		
		taxiDataDto = taxiBaseInfoService.queryTaxiDataBySequenceNo(sequenceNo);
		if(null != taxiDataDto) {
			List<TaxiOrderDto> orderList = taxiOrderService.queryTaxiOrderBySequenceNo(sequenceNo);
			taxiDataDto.setOrderList(orderList);
		}
		return taxiDataDto;
	}
	
	@Override
	public TaxiDataDto checkRawdata(String token,String sequenceNo) throws Exception {
		TaxiDataDto taxiDataDto = null;
		String result = getRawdata(token);
		if(result != null) {
			JSONObject data = JSON.parseObject(result);
			
			String authResult = data.getString("authResult");
			if(null != authResult) {
				taxiDataDto = JSON.parseObject(authResult, TaxiDataDto.class);
				BwGxbTaxiBaseInfo baseInfo = taxiBaseInfoService.save(sequenceNo, taxiDataDto);
				taxiOrderService.save(baseInfo.getId(), taxiDataDto.getOrderList());
			}
			String authInfoVo = data.getString("authInfoVo");
			
			if(null != authInfoVo) {
				AuthInfoDto authInfoDto = JSON.parseObject(authInfoVo,AuthInfoDto.class);
				authRecordService.updateStatus(authInfoDto);
			}
		}
		return taxiDataDto;
	}
	
	/**
	 * 数据报告推送
	 */
	@Override
	public boolean savereport(List<TaxiOrderDto> listtaxiorderdto,TaxiDataDto taxidatadto,AuthInfoDto authinfodto,List<TaxiCommonAddressDto> taxicommonaddressdto) throws Exception {
		BwGxbAuthRecord authRecord = authRecordService.queryBySequenceNo(authinfodto.getSequenceNo());
		if(!"1".equals(authRecord.getAuthStatus())){
			//第一步保存乘客基本信息
			BwGxbTaxiBaseInfo bwgxbtaxibaseinfo = taxiBaseInfoService.save(authinfodto.getSequenceNo(),taxidatadto);
			//第二部保存出行信息
			taxiOrderService.save(bwgxbtaxibaseinfo.getId(), listtaxiorderdto);
			//第三部保存滴滴打车用户常用地址信息
			bwgxbtaxicommonaddressdtoserviceimpl.saveTaxiCommonAddressDto(taxicommonaddressdto,bwgxbtaxibaseinfo.getId());
			//第三步修改基本信息表中的数据
			return authRecordService.updateStatus(authinfodto);
		}
		return true;
	}

	@Override
	public boolean updateAuthRecord(Long id, String redirectUrl,String token) throws Exception  {
		Assert.notNull(id, "缺少参数id");
		Assert.hasText(redirectUrl, "缺少参数redirectUrl");
		BwGxbAuthRecord record = new BwGxbAuthRecord();
		record.setId(id);
		record.setToken(token);
		record.setRedirectUrl(redirectUrl);
		return authRecordService.update(record);
	}

}
