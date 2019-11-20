package com.waterelephant.gxb.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.entity.BwGxbAuthRecord;
import com.waterelephant.gxb.dto.AuthInfoDto;
import com.waterelephant.gxb.dto.TaxiCommonAddressDto;
import com.waterelephant.gxb.dto.TaxiDataDto;
import com.waterelephant.gxb.dto.TaxiOrderDto;

public interface GxbBusiService {
	//获取token
	String getToken(String name,String phone,String idCard,String authItem,String sequenceNo,String timestamp) throws Exception;
	//授权
	String auth(String token,String returnUrl,String username) throws Exception;
	//快速授权(token+auth)
	Map<String,String> quickAuth(String sequenceNo,String name, String idCard, String phone, String authItem,String returnUrl,String notifyUrl, String timestamp) throws Exception;
	
	TaxiDataDto queryRawdata(String sequenceNo) throws Exception;

	//原始数据拉取接口
	TaxiDataDto checkRawdata(String token,String sequenceNo) throws Exception;
	//数据报告拉取接口
	boolean savereport(List<TaxiOrderDto> listtaxiorderdto,TaxiDataDto taxidatadto,AuthInfoDto authinfodto,List<TaxiCommonAddressDto> taxicommonaddressdto) throws Exception;
	
	Long saveAuthRecord(String name, String phone, String idCard, String authItem,String sequenceNo,String returnUrl,String notifyUrl,String timestamp) throws Exception;
	
	BwGxbAuthRecord queryAuthRecordBySequenceNo(String sequenceNo) throws Exception;
	
	boolean updateAuthRecord(Long id, String redirectUrl,String token) throws Exception;
	
}
