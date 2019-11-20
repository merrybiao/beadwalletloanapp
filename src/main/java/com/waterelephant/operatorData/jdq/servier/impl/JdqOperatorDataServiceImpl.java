package com.waterelephant.operatorData.jdq.servier.impl;

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
import com.waterelephant.operatorData.jdq.mapper.JdqOperatorDataMapper;
import com.waterelephant.operatorData.service.impl.OperatorsDataAbstractService;
import com.waterelephant.utils.DateUtil;
@Service
public class JdqOperatorDataServiceImpl extends OperatorsDataAbstractService{
	
	private Logger logger = LoggerFactory.getLogger(JdqOperatorDataServiceImpl.class);
	
	@Autowired
	private JdqOperatorDataMapper jdqoperatordatamapper;


	@Override
	public OperatorUserDataDto getUserData(Long borrowerId, Long orderId) {
		logger.info("---【借点钱】---通过borrowerId:"+borrowerId+"获取基础数据。");
		return jdqoperatordatamapper.queryUserData(orderId);
	}

	@Override
	public List<OperatorRechargeDataDto> getRechargeDataList(Long borrowerId,
			Long orderId) {
		return new ArrayList<>();
	}

	@Override
	public List<OperatorMsgDataDto> getMsgDataList(Long borrowerId, Long orderId) {
		List<OperatorMsgDataDto> list = new ArrayList<>();
		logger.info("----【借点钱】根据borrowerId:{},orderId:{}获取短信记录信息----",borrowerId,orderId);
		try {
			Calendar calendar = Calendar.getInstance();
			int months = 6;//最近6个月的短信记录数据
			while(months>0) {
				String month = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorMsgDataDto dto = jdqoperatordatamapper.getJdqMsgCount(orderId, month);
				if(null != dto && null != dto.getTotalSize()) {
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					String startTime = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 00:00:00";//开始日期为每月1号零点
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					String lastTime  = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 23:59:59";//结束日期为每月最后一天的23点59分59秒
					List<Map<String,String>> items =jdqoperatordatamapper.getJdqMsgRecord(orderId,startTime,lastTime);
					logger.info("----【借点钱】根据orderId:{},startTime:{},lastTime:{},查询bw_jdq_sms表获取短信记录信息,结果为：{}",orderId,startTime,lastTime,items == null ?"空":items.size());
					if(null != items && items.size() >0) {
						dto.setItems(items);
						list.add(dto);
					}	
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----【借点钱】根据borrowerId:{},orderId:{}获取短信记录信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<OperatorTelDataDto> getTelDataList(Long borrowerId, Long orderId) {
		List<OperatorTelDataDto> list = new ArrayList<>();
		logger.info("----根据【借点钱】borrowerId:{},orderId:{}获取通话记录信息----",borrowerId,orderId);
		try {
			Calendar calendar = Calendar.getInstance();
			int months = 6;//最近6个月的通话记录
			while(months>0) {
				String month = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorTelDataDto dto = jdqoperatordatamapper.getJdqCallCount(orderId, month);
				if(null != dto && null != dto.getTotalSize()) {
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					String startTime = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 00:00:00";//开始日期为每月1号零点
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					String lastTime  = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 23:59:59";//结束日期为每月最后一天的23点59分59秒
					List<Map<String,String>> items =jdqoperatordatamapper.getJdqCallRecord(orderId,startTime,lastTime);
					logger.info("----【借点钱】根据orderId:{},startTime:{},lastTime:{},查询bw_jdq_call表获取通话记录信息,结果为：{}",orderId,startTime,lastTime,items == null ?"空":items.size());
					if(null != items && items.size() >0) {
						dto.setItems(items);
						list.add(dto);
					}
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----【借点钱】根据borrowerId:{},orderId:{}获取通话记录信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<OperatorBillDataDto> getBillDataList(Long borrowerId,
			Long orderId) {
		logger.info("----根据【借点钱】borrowerId:{},orderId:{}获取账单记录信息----",borrowerId,orderId);
		List<OperatorBillDataDto> listdata = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
			calendar.add(Calendar.MONTH, -5);
			String startTime = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 00:00:00";//开始日期为每月1号零点
			calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
			String lastTime  = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 23:59:59";//结束日期为每月最后一天的23点59分59秒
			listdata = jdqoperatordatamapper.getJdqBillRecord(orderId, startTime,lastTime);
			logger.info("----【借点钱】根据orderId:{},startTime:{},lastTime:{},查询bw_jdq_call表获取账单记录信息,结果为：{}",orderId,startTime,lastTime,listdata == null ?"空":listdata.size());
			if(null !=listdata&&listdata.size() !=0){
				return listdata;
			}else{
				return new ArrayList<OperatorBillDataDto>();
			}
		} catch (Exception e) {
			logger.error("----【借点钱】根据borrowerId:{},orderId:{}获取账单信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<OperatorFamilyDataDto> getFamilyDataList(Long borrowerId,
			Long orderId) {
		return new ArrayList<>();
	}

	@Override
	public List<OperatorNetLogDataDto> getNetLogDataList(Long borrowerId,
			Long orderId) {
		return new ArrayList<>();
	}

	@Override
	public OperatorMonthInfoDto getMonthinfo(Long borrowerId, Long orderId) {
		return new OperatorMonthInfoDto();
	}

	@Override
	public List<OperatorNetDataDto> getNetDataList(Long borrowerId, Long orderId) {
		List<OperatorNetDataDto> list = new ArrayList<>();
		logger.info("----根据【借点钱】borrowerId:{},orderId:{}获取流量记录信息----",borrowerId,orderId);
		try {
			Calendar calendar = Calendar.getInstance();
			int months = 6;//最近6个月的通话记录
			while(months>0) {
				String month = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorNetDataDto dto = jdqoperatordatamapper.getJdqNetCount(orderId, month);
				if(null != dto && null != dto.getTotalSize()) {
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					String startTime = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 00:00:00";//开始日期为每月1号零点
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					String lastTime  = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 23:59:59";//结束日期为每月最后一天的23点59分59秒
					List<Map<String,String>> items =jdqoperatordatamapper.getJdqNetRecord(orderId,startTime,lastTime);
					logger.info("----【借点钱】根据orderId:{},startTime:{},lastTime:{},查询bw_jdq_net表获取流量记录信息,结果为：{}",orderId,startTime,lastTime,items == null ?"空":items.size());
					if(null != items && items.size() >0) {
						dto.setItems(items);
						list.add(dto);
					}
				}
				months--;
				calendar.add(Calendar.MONTH, -1);	
			}
		} catch (Exception e) {
			logger.error("----【借点钱】根据borrowerId:{},orderId:{}获取流量记录信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
}
