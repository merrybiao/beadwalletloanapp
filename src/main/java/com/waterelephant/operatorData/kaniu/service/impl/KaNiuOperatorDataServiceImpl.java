package com.waterelephant.operatorData.kaniu.service.impl;

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
import com.waterelephant.operatorData.kaniu.mapper.KaNiuOperatorDataMapper;
import com.waterelephant.operatorData.service.impl.OperatorsDataAbstractService;
import com.waterelephant.utils.DateUtil;
@Service
public class KaNiuOperatorDataServiceImpl extends OperatorsDataAbstractService{
	
	private Logger logger = LoggerFactory.getLogger(KaNiuOperatorDataServiceImpl.class);
	
	@Autowired
	private KaNiuOperatorDataMapper kaniuoperatordatamapper;

	@Override
	public OperatorUserDataDto getUserData(Long borrowerId, Long orderId) {
		logger.info("----【卡牛】依据:"+borrowerId+"查询基础数据执行开始----");
		return kaniuoperatordatamapper.queryUserData(borrowerId);
	}

	@Override
	public List<OperatorRechargeDataDto> getRechargeDataList(Long borrowerId,
			Long orderId) {
		
		return new ArrayList<OperatorRechargeDataDto>();
		
	}

	@Override
	public List<OperatorMsgDataDto> getMsgDataList(Long borrowerId, Long orderId) {
		
		return new ArrayList<OperatorMsgDataDto>();
	}

	@Override
	public List<OperatorTelDataDto> getTelDataList(Long borrowerId, Long orderId) {
		logger.info("----【卡牛】根据borrowerId:{},orderId:{}获取通话记录信息----",borrowerId,orderId);
		int months = 6;
		Calendar calendar = Calendar.getInstance();
		List<OperatorTelDataDto> list = new ArrayList<OperatorTelDataDto>();
		try {
			while(months>0){
				String dateCount = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorTelDataDto dto = kaniuoperatordatamapper.getKaNiuCallCount(borrowerId, dateCount);
				if(null !=dto&&dto.getTotalSize()>0){
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					String startTime = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd")+ " 00:00:00";
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					String endTime = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd")+ " 23:59:59";
					List<Map<String, String>> items = kaniuoperatordatamapper.getKaNiuCallRecord(borrowerId, startTime, endTime);
					logger.info("----【卡牛】根据borrowerId:{},startTime:{},lastTime:{},查询bw_operate_voice表获取通话记录信息,结果为：{}",borrowerId,startTime,endTime,items == null ?"空":items.size());
					if(null !=items&&items.size()>0){
						dto.setItems(items);
						list.add(dto);
					}
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----【卡牛】根据borrowerId:{},orderId:{}获取通话记录信息异常：{}----",borrowerId,orderId,e.getMessage());
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
