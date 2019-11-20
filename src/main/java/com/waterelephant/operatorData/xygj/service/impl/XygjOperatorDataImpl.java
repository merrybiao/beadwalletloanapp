package com.waterelephant.operatorData.xygj.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.operatorData.dto.OperatorBillDataDto;
import com.waterelephant.operatorData.dto.OperatorFamilyDataDto;
import com.waterelephant.operatorData.dto.OperatorMonthInfoDto;
import com.waterelephant.operatorData.dto.OperatorMsgDataDto;
import com.waterelephant.operatorData.dto.OperatorNetDataDto;
import com.waterelephant.operatorData.dto.OperatorNetLogDataDto;
import com.waterelephant.operatorData.dto.OperatorRechargeDataDto;
import com.waterelephant.operatorData.dto.OperatorTelDataDto;
import com.waterelephant.operatorData.dto.OperatorUserDataDto;
import com.waterelephant.operatorData.service.impl.OperatorsDataAbstractService;
import com.waterelephant.operatorData.xygj.mapper.XygjOperatorDataMapper;
import com.waterelephant.utils.DateUtil;
@Service
public class XygjOperatorDataImpl extends OperatorsDataAbstractService{
	
	private Logger logger = LoggerFactory.getLogger(XygjOperatorDataImpl.class);
	
	@Autowired
	private XygjOperatorDataMapper mapper;

	@Override
	public OperatorUserDataDto getUserData(Long borrowerId, Long orderId) {
		logger.info("----【信用管家】----依据orderId:{}查询用户基本信息",orderId);
		return mapper.queryUserData(orderId);
	}

	@Override
	public List<OperatorRechargeDataDto> getRechargeDataList(Long borrowerId,
			Long orderId) {
 
		return new ArrayList<OperatorRechargeDataDto>();
	}

	@Override
	public List<OperatorMsgDataDto> getMsgDataList(Long borrowerId, Long orderId) {
		logger.info("----【信用管家】----根据orderId:{}获取短信记录信息<<<<<",orderId);
		int months = 6;
		Calendar calendar = Calendar.getInstance(); 
		List<OperatorMsgDataDto> list = new ArrayList<OperatorMsgDataDto>();
		try {
			while(months > 0){
				String date = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorMsgDataDto dto = this.mapper.queryMsgCount(orderId, date);
				if(null != dto&&dto.getTotalSize() >0 ){
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					//每月开始第一天时间
					String startTime = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd") + " 00:00:00";
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					//每月结束最后一天时间
					String endTime = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd") + " 23:59:59";
					List<Map<String, Object>> msgrecord = this.mapper.queryMsgData(orderId, startTime, endTime);
					logger.info("----【信用管家】----根据orderId:{},startTime:{},endTime:{},查询bw_xygj_sms表获取短信记录信息,结果为：{}",orderId,startTime,endTime,msgrecord == null ?"空":msgrecord.size());
					if(null != msgrecord&&msgrecord.size()>0){
						dto.setItems(msgrecord);
						list.add(dto);
					}
				}
				calendar.add(Calendar.MONTH, -1);
				months--;
			}
		} catch (Exception e) {
			logger.error("----【信用管家】----根据orderId:{}获取短信记录信息异常：{}----",orderId,e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<OperatorTelDataDto> getTelDataList(Long borrowerId, Long orderId) {
		logger.info("----【信用管家】----根据orderId:{}获取通话记录信息----",orderId);
		int months = 6;
		Calendar calendar = Calendar.getInstance();
		List<OperatorTelDataDto> list = new ArrayList<OperatorTelDataDto>();
		try {
			while(months>0){
				String endTime = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorTelDataDto dto = this.mapper.queryCallCount(orderId,endTime);
				if(null !=dto&&dto.getTotalSize()>0){
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					String startTime = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd")+ " 00:00:00";
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					String endTimedData = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd")+ " 23:59:59";
					List<Map<String, Object>> items = this.mapper.queryCallData(orderId, startTime, endTimedData);
					logger.info("----【信用管家】----根据orderId:{},startTime:{},endTime:{},查询bw_xygj_call表获取通话记录信息,结果为：{}",orderId,startTime,endTime,items == null ?"空":items.size());
					if(null !=items&&items.size()>0){
						dto.setItems(items);
						list.add(dto);
					}
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----【信用管家】----根据orderId:{}获取通话记录信息异常：{}----",orderId,e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<OperatorBillDataDto> getBillDataList(Long borrowerId,
			Long orderId) {
  
		return new ArrayList<OperatorBillDataDto>();
	}

	@Override
	public List<OperatorFamilyDataDto> getFamilyDataList(Long borrowerId,
			Long orderId) {
		 
		return new ArrayList<OperatorFamilyDataDto>();
	}

	@Override
	public List<OperatorNetLogDataDto> getNetLogDataList(Long borrowerId,
			Long orderId) {
  
		return new ArrayList<OperatorNetLogDataDto>();
	}

	@Override
	public OperatorMonthInfoDto getMonthinfo(Long borrowerId, Long orderId) {
 
		return new OperatorMonthInfoDto();
	}

	@Override
	public List<OperatorNetDataDto> getNetDataList(Long borrowerId, Long orderId) {
		
		return new ArrayList<OperatorNetDataDto>();
	}
}
