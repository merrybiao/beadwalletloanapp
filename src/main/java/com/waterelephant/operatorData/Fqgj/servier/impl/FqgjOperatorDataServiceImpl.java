package com.waterelephant.operatorData.Fqgj.servier.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.operatorData.Fqgj.mapper.FqgjOperatorDataMapper;
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
import com.waterelephant.utils.DateUtil;
@Service          
public class FqgjOperatorDataServiceImpl extends OperatorsDataAbstractService{
	
	private Logger logger = LoggerFactory.getLogger(FqgjOperatorDataServiceImpl.class);

	@Autowired
	private FqgjOperatorDataMapper fqgjoperatordatamapper;

	@Override
	public OperatorUserDataDto getUserData(Long borrowerId, Long orderId) {
		return fqgjoperatordatamapper.queryUserData(borrowerId);
	}

	@Override
	public List<OperatorRechargeDataDto> getRechargeDataList(Long borrowerId,Long orderId) {
		logger.info("----【分期管家】根据borrowerId:{},orderId:{}获取充值记录信息----",borrowerId,orderId);
		List<OperatorRechargeDataDto> listCharge = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
			calendar.add(Calendar.MONTH, -5);
			String startTime = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 00:00:00";//开始日期为每月1号零点
			calendar = Calendar.getInstance();
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
			String lastTime  = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 23:59:59";//结束日期为每月最后一天的23点59分59秒
			listCharge = fqgjoperatordatamapper.getFqgChargeRecord(orderId,startTime,lastTime);
			logger.info("----【分期管家】根据borrowerId:{},startTime:{},lastTime:{},查询bw_fqgj_recharge表获取充值记录信息,结果为：{}",borrowerId,startTime,lastTime,listCharge == null ?"空":listCharge.size());
			if(null !=listCharge&&listCharge.size() !=0){
				return listCharge;
			}else{
				return new ArrayList<OperatorRechargeDataDto>();
			}
		} catch (Exception e) {
			logger.error("----【分期管家】根据borrowerId:{},orderId:{}获取充值记录信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<OperatorMsgDataDto> getMsgDataList(Long borrowerId, Long orderId) {
		List<OperatorMsgDataDto> list = new ArrayList<>();
		logger.info("----【分期管家】根据borrowerId:{},orderId:{}获取短信记录信息----",borrowerId,orderId);
		try {
			Calendar calendar = Calendar.getInstance();
			int months = 6;//最近6个月的短信记录数据
			while(months>0) {
				String month = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorMsgDataDto dto = fqgjoperatordatamapper.getFqgjMsgCount(orderId, month);
				if(null != dto && null != dto.getTotalSize()) {
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					String startTime = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 00:00:00";//开始日期为每月1号零点
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					String lastTime  = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 23:59:59";//结束日期为每月最后一天的23点59分59秒
					List<Map<String,String>> items =fqgjoperatordatamapper.getFqgjMsgRecord(orderId,startTime,lastTime);
					logger.info("----【分期管家】根据orderId:{},startTime:{},lastTime:{},查询bw_fqgj_msg_bill表获取短信记录信息,结果为：{}",orderId,startTime,lastTime,items == null ?"空":items.size());
					if(null != items && items.size() >0) {
						dto.setItems(items);
						list.add(dto);
					}
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----【分期管家】根据borrowerId:{},orderId:{}获取短信记录信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return list;

	}

	@Override
	public List<OperatorTelDataDto> getTelDataList(Long borrowerId, Long orderId) {
		List<OperatorTelDataDto> list = new ArrayList<>();
		logger.info("----根据【分期管家】borrowerId:{},orderId:{}获取通话记录信息----",borrowerId,orderId);
		try {
			Calendar calendar = Calendar.getInstance();
			int months = 6;//最近6个月的通话记录
			while(months>0) {
				String month = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorTelDataDto dto = fqgjoperatordatamapper.getFqgjCallCount(orderId, month);
				if(null != dto && null != dto.getTotalSize()) {
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					String startTime = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 00:00:00";//开始日期为每月1号零点
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					String lastTime  = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 23:59:59";//结束日期为每月最后一天的23点59分59秒
					List<Map<String,String>> items =fqgjoperatordatamapper.getFqgjCallRecord(orderId,startTime,lastTime);
					logger.info("----【分期管家】根据orderId:{},startTime:{},lastTime:{},查询bw_fqgj_call表获取通话记录信息,结果为：{}",orderId,startTime,lastTime,items == null ?"空":items.size());
					if(null != items && items.size() >0) {
						dto.setItems(items);
						list.add(dto);
					}
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----【分期管家】根据borrowerId:{},orderId:{}获取通话记录信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<OperatorBillDataDto> getBillDataList(Long borrowerId,Long orderId) {
		List<OperatorBillDataDto> listdata = new ArrayList<OperatorBillDataDto>();
		try {
			Calendar calendar = Calendar.getInstance();
			int months = 6;//最近6个月的月账单记录
			while(months>0) {
				String month = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorBillDataDto dto = fqgjoperatordatamapper.getFqgjBillRecord(orderId, month);
				if(null !=dto){
					listdata.add(dto);
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----【分期管家】根据borrowerId:{},orderId:{}获取账单信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return listdata;
	}

	@Override
	public List<OperatorFamilyDataDto> getFamilyDataList(Long borrowerId,Long orderId) {
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
		return new ArrayList<>();
	}

}
