package com.waterelephant.operatorData.gnh.service.impl;

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
import com.waterelephant.operatorData.gnh.mapper.GnhOperatorDataMapper;
import com.waterelephant.operatorData.service.impl.OperatorsDataAbstractService;
import com.waterelephant.utils.DateUtil;
@Service
public class GnhOperatorDataServiceImpl extends OperatorsDataAbstractService{
	
	private Logger logger = LoggerFactory.getLogger(GnhOperatorDataServiceImpl.class);
	
	@Autowired
	private GnhOperatorDataMapper gnhoperatordatamapper;

	@Override
	public OperatorUserDataDto getUserData(Long borrowerId, Long orderId) {
		logger.info("----【给你花】根据borrowerId:{},orderId:{}获取基础信息数据----",borrowerId,orderId);
		return gnhoperatordatamapper.queryUserData(borrowerId);
	}

	@Override
	public List<OperatorRechargeDataDto> getRechargeDataList(Long borrowerId,
			Long orderId) {
		return new ArrayList<OperatorRechargeDataDto>();
	}

	@Override
	public List<OperatorMsgDataDto> getMsgDataList(Long borrowerId, Long orderId) {
		List<OperatorMsgDataDto> list = new ArrayList<>();
		logger.info("----【给你花】根据borrowerId:{},orderId:{}获取短信记录信息----",borrowerId,orderId);
		try {
			Calendar calendar = Calendar.getInstance();
			int months = 6;//最近6个月的短信记录数据
			while(months>0) {
				String month = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorMsgDataDto dto = gnhoperatordatamapper.getGnhMsgCount(orderId, month);
				if(null != dto && null != dto.getTotalSize()) {
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					String startTime = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 00:00:00";//开始日期为每月1号零点
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					String lastTime  = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 23:59:59";//结束日期为每月最后一天的23点59分59秒
					List<Map<String,String>> items =gnhoperatordatamapper.getGnhMsgRecord(orderId,startTime,lastTime);
					logger.info("----【给你花】根据orderId:{},startTime:{},lastTime:{},查询bw_jdq_sms表获取短信记录信息,结果为：{}",orderId,startTime,lastTime,items == null ?"空":items.size());
					if(null != items && items.size() >0) {
						dto.setItems(items);
						list.add(dto);
					}	
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----【给你花】根据borrowerId:{},orderId:{}获取短信记录信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<OperatorTelDataDto> getTelDataList(Long borrowerId, Long orderId) {
		logger.info("----【给你花】根据borrowerId:{},orderId:{}获取通话记录信息----",borrowerId,orderId);
		int months = 6;
		Calendar calendar = Calendar.getInstance();
		List<OperatorTelDataDto> list = new ArrayList<OperatorTelDataDto>();
		try {
			while(months>0){
				String dateCount = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorTelDataDto dto = gnhoperatordatamapper.getGnhCallCount(borrowerId, dateCount);
				if(null !=dto&&dto.getTotalSize()>0){
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					String startTime = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd")+ " 00:00:00";
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					String endTime = DateUtil.getDateString(calendar.getTime(), "yyyy-MM-dd")+ " 23:59:59";
					List<Map<String, String>> items = gnhoperatordatamapper.getGnhCallRecord(borrowerId, startTime, endTime);
					logger.info("----【给你花】根据orderId:{},startTime:{},lastTime:{},查询bw_third_operate_voice表获取通话记录信息,结果为：{}",orderId,startTime,endTime,items == null ?"空":items.size());
					if(null !=items&&items.size()>0){
						dto.setItems(items);
						list.add(dto);
					}
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----【给你花】根据borrowerId:{},orderId:{}获取通话记录信息异常：{}----",borrowerId,orderId,e.getMessage());
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
