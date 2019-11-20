package com.waterelephant.operatorData.xg.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.waterelephant.entity.BwXgOverall;
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
import com.waterelephant.operatorData.xg.mapper.XgOperatorCallDataMapper;
import com.waterelephant.operatorData.xg.mapper.XgOperatorUserMapper;
import com.waterelephant.rongCarrier.entity.XgOverall;
import com.waterelephant.rongCarrier.mapper.XgOverallMapper;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.mongo.MgCollectionUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
/**
 * 先查询_new的表
 * 再查询mongo的表 broowerId % 1001
 * 在查原表
 * @author dinglinhao
 *
 */
@Service
public class XgOperatorDataServiceImpl extends OperatorsDataAbstractService {
	
	private Logger logger = LoggerFactory.getLogger(XgOperatorDataServiceImpl.class);
	
	@Autowired
	private XgOverallMapper xgOverallMapper;
	
	@Autowired
	private XgOperatorUserMapper xgOperatorUserMapper;
	
	@Autowired
	private XgOperatorCallDataMapper xgOperatorCallDataMapper;
	
//	@Autowired
//	private MongoTemplate mongoTemplate; 
	
	public String getSearchId(Long borrowerId) {
		Example example = new Example(BwXgOverall.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("borrowerId", borrowerId);
		example.setOrderByClause("report_time desc");
		List<XgOverall> list = xgOverallMapper.selectByExample(example);
		if(list == null || list.isEmpty()) return null;
		XgOverall entity = list.get(0);
		//部分情况下report_time字段也有肯能为空
		if(null == entity || StringUtils.isEmpty(entity.getReportTime())) return null;
		
		Date reportTime = DateUtil.stringToDate(entity.getReportTime(), DateUtil.yyyy_MM_dd_HHmmss);
		int days = DateUtil.intervalDay(reportTime, new Date());
		//30天内searchId有效
		return days <= 30 ? entity.getSearchId() : "-1";
	}
	

	@Override
	public OperatorUserDataDto getUserData(Long borrowerId, Long orderId) {
		logger.info("----根据borrowerId:{}获取【西瓜】运营商基本信息----",borrowerId);
		OperatorUserDataDto userData =  xgOperatorUserMapper.queryUserData(borrowerId);
		return userData;
	}
	

	@Override
	public List<OperatorRechargeDataDto> getRechargeDataList(Long borrowerId, Long orderId) {
		return new ArrayList<>();
	}

	@Override
	public List<OperatorMsgDataDto> getMsgDataList(Long borrowerId, Long orderId) {
		return new ArrayList<>();
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<OperatorTelDataDto> getTelDataList(Long borrowerId, Long orderId) {//14951126269258622913
		List<OperatorTelDataDto> list = new ArrayList<>();
		logger.info("----根据borrowerId:{},orderId:{}获取【西瓜】通话记录信息----",borrowerId,orderId);
		try {
			Calendar calendar = Calendar.getInstance();
			int months = 6;//最近6个月的通话记录
			while(months>0) {
				String month = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorTelDataDto dto = null;
				
				if(null == dto) {
					//1、查询bw_xg_area_analysis_detail_new
					dto = xgOperatorCallDataMapper.getCallCountV2(borrowerId,month);
				}
				
				//2、查mongo bw_xg_area_analysis_detail_{borrowerId % 1001} areadetail_
				if(null == dto) { 
					long star = System.currentTimeMillis();
					String collectionName = "areadetail_" + borrowerId % 1001;
					MongoClient mongoClient = null;
					try {
						if(null != MgCollectionUtil.mongoClient) {
							mongoClient = MgCollectionUtil.mongoClient;
						}else {
							mongoClient = MgCollectionUtil.getMongoClient();
						}
						DB db = mongoClient.getDB("areadetail");
						DBCollection dbCollection = db.getCollection(collectionName);

						logger.info("--查询mongoDb---collectionName:{},db:{},borrowerId:{},month:{}--",collectionName,db.getName(),borrowerId,month);
						
						DBObject queryObject = new BasicDBObject();
						queryObject.put("borrower_id", borrowerId);
						queryObject.put("month", month);
						
						DBObject fieldsObject = new BasicDBObject();
						fieldsObject.put("call_cnt", true);
						
						DBObject result = dbCollection.findOne(queryObject, fieldsObject);
//						Query query = new BasicQuery(queryObject, fieldsObject);
//						Map<?,?> data = this.mongoTemplate.findOne(query, Map.class, collectionName);
						if(null != result) {
							Map<?,?> data = result.toMap();
							if(null != data && !data.isEmpty()) {
								Integer callCnt = Integer.valueOf(String.valueOf(data.get("call_cnt")));
								dto = new OperatorTelDataDto();
								dto.setMonth(month);
								dto.setTotalSize(callCnt);
							}
						}
					} catch (Exception e) {
						logger.error("----通过borrower_id:{},month:{}查询mongo：{}失败，异常信息：{}",borrowerId,month,collectionName,e.getMessage());
						e.printStackTrace();
					} finally {
						logger.info("----通过borrower_id:{},month:{}查询mongo耗时:{}ms----",borrowerId,month,(System.currentTimeMillis() - star));
					}
				}
				
				//3、查原表bw_xg_area_analysis_detail
				if(null == dto) {
					dto = xgOperatorCallDataMapper.getCallCount(borrowerId, month);
				}
				
				
//				if(null == dto) {
//					DBCollection dbCollection = null;
//					try {
//						DB db = SystemDBCollectionUtils.getCollectionContactDb();
//
//						dbCollection = db.getCollection("bw_xg_area_analysis_detail_" + borrowerId % 1001);
//
//						BasicDBObject queryObject = new BasicDBObject();
//						queryObject.put("borrower_id", borrowerId);
//						queryObject.put("month", month);
//
//						DBObject result = dbCollection.findOne(queryObject);
//						if(null != result) {
//							dto = new OperatorTelDataDto();
//							dto.setTotalSize((Integer)result.get("call_cnt"));
//							dto.setMonth((String)result.get("month"));
//						}
//						logger.info("borrowerId:" + borrowerId + "mongo查询结果:" + result.toMap().toString());
//					} catch (Exception e) {
//						e.printStackTrace();
//					} finally {
//						dbCollection = null;
//					}
//				}

				if(null !=dto) {
					List<?> items = queryXgCallRecord(borrowerId, month);
					dto.setItems(items == null ? new ArrayList<>() : items);
					list.add(dto);
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----根据borrowerId:{},orderId:{}获取【西瓜】通话记录信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Map<String,String>> queryXgCallRecord(Long borrowerId,String month) {
		List<Map<String,String>> items = null;
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateUtil.stringToDate(month, "yyyy-MM"));
			calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
			String startTime = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 00:00:00";//开始日期为每月1号零点
			calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
			String lastTime  = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 23:59:59";//结束日期为每月最后一天的23点59分59秒
			items =xgOperatorCallDataMapper.getCallRecord(borrowerId,startTime,lastTime);
			logger.info("----根据borrowerId:{},startTime:{},lastTime:{},查询bw_operate_voice表获取【西瓜】通话记录信息,结果为：{}",borrowerId,startTime,lastTime,items == null ?"空":items.size());
		} catch (Exception e) {
			logger.error("----【西瓜】--xg-根据borrower_id：{}，month:{}查询bw_operate_voice表失败，异常信息：{}",borrowerId,month,e.getMessage());
			e.printStackTrace();
		}
		return items;	
	}

	@Override
	public List<OperatorBillDataDto> getBillDataList(Long borrowerId, Long orderId) {
		return new ArrayList<>();
	}

	@Override
	public List<OperatorFamilyDataDto> getFamilyDataList(Long borrowerId, Long orderId) {
		return new ArrayList<>();
	}

	@Override
	public List<OperatorNetLogDataDto> getNetLogDataList(Long borrowerId, Long orderId) {
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
