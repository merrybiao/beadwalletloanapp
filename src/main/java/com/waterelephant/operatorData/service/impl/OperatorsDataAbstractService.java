package com.waterelephant.operatorData.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorFamilyDataDto;
import com.waterelephant.operatorData.dto.OperatorMonthInfoDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.dto.OperatorNetLogDataDto;
import com.waterelephant.operatorData.dto.OperatorRechargeDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;

public abstract class OperatorsDataAbstractService {
	
	private Logger logger = LoggerFactory.getLogger(OperatorsDataAbstractService.class);
	
	/**
	 * 获取认证用户列表
	 * @param orderId
	 * @return
	 */
	public List<Object> getOperatorDataList(Long borrowerId,Long orderId) {
		
		Map<String,Object> data = new HashMap<>();
		try {
			logger.info("----根据borrowerId:{},orderId:{}获取渠道运营商数据----",borrowerId,orderId);
			OperatorUserDataDto userData = getUserData(borrowerId,orderId);
			if(null == userData) return null;
			data.put("userdata", userData);//基本信息
			data.put("billdata", getBillDataList(borrowerId,orderId));//账单信息
			data.put("teldata", getTelDataList(borrowerId,orderId));//通话详单
			data.put("msgdata", getMsgDataList(borrowerId,orderId));//短信详单
			data.put("netdata", getNetDataList(borrowerId,orderId));//流量详单
			data.put("netlogdata", getNetLogDataList(borrowerId,orderId));//上网记录详单
			data.put("familydata", getFamilyDataList(borrowerId,orderId));//亲情网记录
			data.put("monthinfo", getMonthinfo(borrowerId,orderId));//月份汇总信息
			data.put("rechargedata", getRechargeDataList(borrowerId,orderId));//充值记录
		} catch (Exception e) {
			logger.error("----根据borrowerId:{},orderId:{}获取运营商数据失败：{}",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		//认证用户列表
		List<Object> dataList = new ArrayList<>();
		dataList.add(data);
		return dataList;
	}
	
	/**
	 * 运营商基本信息
	 * @param borrowerId
	 * @return
	 */
	public abstract OperatorUserDataDto getUserData(Long borrowerId,Long orderId);
	
	/**
	 * 充值记录
	 * @param borrowerId
	 * @return
	 */
	public abstract List<OperatorRechargeDataDto> getRechargeDataList(Long borrowerId,Long orderId);
	
	/**
	 * 短信详单
	 * @param borrowerId
	 * @return
	 */
	public abstract List<OperatorMsgDataDto> getMsgDataList(Long borrowerId,Long orderId);
	
	/**
	 * 通话详单
	 * @param borrowerId
	 * @return
	 */
	public abstract List<OperatorTelDataDto> getTelDataList(Long borrowerId,Long orderId);
	
	/**
	 * 账单详情
	 * @param borrowerId
	 * @return
	 */
	public abstract List<OperatorBillDataDto> getBillDataList(Long borrowerId,Long orderId);
	
	/**
	 * 亲情网记录
	 * @param borrowerId
	 * @return
	 */
	public abstract List<OperatorFamilyDataDto> getFamilyDataList(Long borrowerId,Long orderId);
	
	/**
	 * 上网记录详单
	 * @param borrowerId
	 * @return
	 */
	public abstract List<OperatorNetLogDataDto> getNetLogDataList(Long borrowerId,Long orderId);
	
	/**
	 * 月份汇总信息
	 * @param borrowerId
	 * @return
	 */
	public abstract OperatorMonthInfoDto getMonthinfo(Long borrowerId,Long orderId);
	
	/**
	 * 流量详单
	 * @param borrowerId
	 * @return
	 */
	public abstract List<OperatorNetDataDto> getNetDataList(Long borrowerId,Long orderId);

}
