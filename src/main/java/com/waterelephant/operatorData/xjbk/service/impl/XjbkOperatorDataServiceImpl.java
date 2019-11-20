package com.waterelephant.operatorData.xjbk.service.impl;

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
import com.waterelephant.operatorData.xjbk.mapper.XjbkOperatorDataMapper;
import com.waterelephant.utils.DateUtil;

/**
 * 现金白卡运营商数据整合
 * @author dinglinhao
 * @date 2018年8月21日09:50:38
 *
 */
@Service
public class XjbkOperatorDataServiceImpl extends OperatorsDataAbstractService {

	private Logger logger = LoggerFactory.getLogger(XjbkOperatorDataServiceImpl.class);
	
	@Autowired
	private XjbkOperatorDataMapper xjbkOperatorDataMapper;
	
	/**
	 * 运营商基本信息
	 * @param orderId
	 * @return
	 */
	@Override
	public OperatorUserDataDto getUserData(Long borrowerId,Long orderId) {
		logger.info("----根据borrowerId:{}获取【现金白卡】运营商基本信息----",borrowerId);
		OperatorUserDataDto userData = xjbkOperatorDataMapper.queryUserData(borrowerId);
		return userData;
	}
	
	/**
	 * 充值记录
	 * @param borrowerId
	 * @return
	 */
	@Override
	public List<OperatorRechargeDataDto> getRechargeDataList(Long borrowerId,Long orderId){
		//TODO 引流渠道没有存该数据
		return new ArrayList<>();
	}
	
	/**
	 * 短信详单
	 * @param borrowerId
	 * @return
	 */
	@Override
	public List<OperatorMsgDataDto> getMsgDataList(Long borrowerId,Long orderId){
		//TODO 引流渠道没有存该数据
		return new ArrayList<>();
	}
	
	/**
	 * 通话详单
	 * 查询最近6个月的通话记录
	 * @param orderId
	 * @return
	 */
	@Override
	public List<OperatorTelDataDto> getTelDataList(Long borrowerId,Long orderId){
		List<OperatorTelDataDto> list = new ArrayList<>();
		logger.info("----根据borrowerId:{},orderId:{}获取【现金白卡】通话记录信息----",borrowerId,orderId);
		try {
			Calendar calendar = Calendar.getInstance();
			int months = 6;//最近6个月的通话记录
			while(months>0) {
				String month = DateUtil.getDateString(calendar.getTime(), "yyyy-MM");
				OperatorTelDataDto dto = xjbkOperatorDataMapper.getCallCount(orderId,month);
				
				if(null != dto && null != dto.getTotalSize()) {
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					String startTime = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 00:00:00";//开始日期为每月1号零点
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					String lastTime  = DateUtil.getDateString(calendar.getTime(), DateUtil.yyyy_MM_dd)+" 23:59:59";//结束日期为每月最后一天的23点59分59秒
					List<Map<String,String>> items =xjbkOperatorDataMapper.getCallRecord(borrowerId,startTime,lastTime);
					logger.info("----根据borrowerId:{},startTime:{},lastTime:{},查询bw_operate_voice表获取【现金白卡】通话记录信息,结果为：{}",borrowerId,startTime,lastTime,items == null ?"空":items.size());
					if(null != items && items.size() >0) {
						dto.setItems(items);
					}else {
						
						long start = System.currentTimeMillis();
						//查询operator_voice_1
						items =xjbkOperatorDataMapper.getCallRecord1(borrowerId,startTime,lastTime);
						logger.info("----根据borrowerId:{},startTime:{},lastTime:{},查询bw_operate_voice_1表获取【现金白卡】通话记录信息,结果为：{}",borrowerId,startTime,lastTime,items == null ?"空":items.size());
						
						if(null == items || items.isEmpty()) {
							//查询operator_voice_2
							items =xjbkOperatorDataMapper.getCallRecord2(borrowerId,startTime,lastTime);
							logger.info("----根据borrowerId:{},startTime:{},lastTime:{},查询bw_operate_voice_2表获取【现金白卡】通话记录信息,结果为：{}",borrowerId,startTime,lastTime,items == null ?"空":items.size());
						}
						
						if(null == items || items.isEmpty()) {
							//查询operator_voice_3
							items =xjbkOperatorDataMapper.getCallRecord3(borrowerId,startTime,lastTime);
							logger.info("----根据borrowerId:{},startTime:{},lastTime:{},查询bw_operate_voice_3表获取【现金白卡】通话记录信息,结果为：{}",borrowerId,startTime,lastTime,items == null ?"空":items.size());
						}
						
						long end = System.currentTimeMillis();
						logger.info("----【现金白卡】borrowerId:{},查询bw_operate_voice三张表耗时：{}ms----",borrowerId,(end-start));
						
//						if(null == items || items.isEmpty())  {
//							//查询ES
//							items = queryCallRecordByESScroll(borrowerId,startTime,lastTime);
//							logger.info("----根据borrowerId:{},startTime:{},lastTime:{},查询ES获取【现金白卡】通话记录信息,结果为：{}",borrowerId,startTime,lastTime,items == null ?"空":items.size());
//						}
						dto.setItems(null == items ? new ArrayList<>() : items);
						//totalSize保证和list的长度相同
						dto.setTotalSize(dto.getItems().size());
					}
					list.add(dto);
				}
				months--;
				calendar.add(Calendar.MONTH, -1);
			}
		} catch (Exception e) {
			logger.error("----根据borrowerId:{},orderId:{}获取【现金白卡】通话记录信息异常：{}----",borrowerId,orderId,e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
//	/**
//	 * 查询通话记录ES
//	 * @param borrowerId
//	 * @param startTime
//	 * @param lastTime
//	 * @return
//	 */
//	private List<Map<String, String>> queryCallRecordByES(Long borrowerId, String startTime, String lastTime) {
//		Long begin = System.currentTimeMillis();
//	    logger.info("====ES查询通话记录begin:" + (begin / 1000) + "====");
//	    Client client = null;
//	    
//	    List<Map<String, String>> result = new ArrayList<>();
//	    try {
//	      client = ElasticSearchUtils.getInstance().getClient();
//	      long stime = DateUtil.stringToDate(startTime, DateUtil.yyyy_MM_dd_HHmmss).getTime();
//	      long ltime = DateUtil.stringToDate(lastTime, DateUtil.yyyy_MM_dd_HHmmss).getTime();
//	      
//	      // 查询数据
//	      SearchRequestBuilder requestBuilder = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
//	    	  .setSearchType(SearchType.QUERY_AND_FETCH)
//	          .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("borrower_id", borrowerId))
//	        		  .must(QueryBuilders.rangeQuery("call_time").from(stime).to(ltime)))
//	          .setScroll(TimeValue.timeValueMinutes(8))
//	          .setFrom(0).setSize(1000).addSort("call_time", SortOrder.ASC);
//	          
//	      SearchResponse response = requestBuilder.get();
//	      	      
//	      long count = response.getHits().getTotalHits();// 总条数
//	      logger.info("====ES 查询BwOperateVoice条件borrower_id = {} AND call_time BETWEEN {} and {},查询结果：{}====",borrowerId,stime,ltime,count);
//	      result.addAll((getCallRecordByES(response.getHits())));
//	      while(response.getHits().getHits().length !=0) {
//	    	  response = client.prepareSearchScroll(response.getScrollId()).setScroll(TimeValue.timeValueMinutes(8)).execute().actionGet();
//	    	  result.addAll(getCallRecordByES(response.getHits()));
//	      }
//	    } catch (Exception e) {
//	      logger.info("------ES查询通话记录出现异常情况" + e.getMessage() + "-------");
//	      e.printStackTrace();
//	    }
//	    Long end = System.currentTimeMillis();
//	    logger.info("====ES查询通话记录end,用时:" + ((end - begin)) + "ms====");
//		return result;
//	}
//	
//	
//	private List<Map<String, String>> queryCallRecordByESScroll(Long borrowerId, String startTime, String lastTime) {
//		Long begin = System.currentTimeMillis();
//	    Client client = null;
//	    
//	    List<Map<String, String>> result = new ArrayList<>();
//	    try {
//	      client = ElasticSearchUtils.getInstance().getClient();
//	      long stime = DateUtil.stringToDate(startTime, DateUtil.yyyy_MM_dd_HHmmss).getTime();
//	      long ltime = DateUtil.stringToDate(lastTime, DateUtil.yyyy_MM_dd_HHmmss).getTime();
//	      
//	      // 查询数据
//	      SearchRequestBuilder requestBuilder = client.prepareSearch(SystemConstant.ES_INDEX).setTypes("BwOperateVoice")
//	    	  .setSearchType(SearchType.QUERY_AND_FETCH)
//	          .setQuery(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("borrower_id", borrowerId))
//	        		  .must(QueryBuilders.rangeQuery("call_time").from(stime).to(ltime)))
//	          .setScroll(TimeValue.timeValueMinutes(8))
//	          .setFrom(0).setSize(2000).addSort("call_time", SortOrder.ASC);
//	          
//	      SearchResponse response = requestBuilder.get();
//	      	      
//	      long count = response.getHits().getTotalHits();// 总条数
//	      logger.info("====ES 查询BwOperateVoice条件borrower_id = {} AND call_time BETWEEN {} and {},查询结果：{}====",borrowerId,stime,ltime,count);
//	      result.addAll((getCallRecordByES(response.getHits())));
//	      while(response.getHits().getHits().length !=0) {
//	    	  response = client.prepareSearchScroll(response.getScrollId()).setScroll(TimeValue.timeValueMinutes(8)).execute().actionGet();
//	    	  result.addAll(getCallRecordByES(response.getHits()));
//	      }
//	    } catch (Exception e) {
//	      logger.info("------ES查询通话记录出现异常情况" + e.getMessage() + "-------");
//	      e.printStackTrace();
//	    }
//	    Long end = System.currentTimeMillis();
//	    logger.info("====ES queryCallRecordByESScroll查询通话记录end,用时:" + ((end - begin)) + "ms====");
//		return result;
//	}

	/**
	 * 账单详情
	 * @param borrowerId
	 * @return
	 */
	@Override
	public List<OperatorBillDataDto> getBillDataList(Long borrowerId,Long orderId){
		//引流渠道没有存该数据
		return new ArrayList<>();
	}
	
	/**
	 * 亲情网记录
	 * @param borrowerId
	 * @return
	 */
	@Override
	public List<OperatorFamilyDataDto> getFamilyDataList(Long borrowerId,Long orderId){
		//引流渠道没有存该数据
		return new ArrayList<>();
	}
	
	/**
	 * 上网记录详单
	 * @param borrowerId
	 * @return
	 */
	@Override
	public List<OperatorNetLogDataDto> getNetLogDataList(Long borrowerId,Long orderId){
		//引流渠道没有存该数据
		return new ArrayList<>();
	}
	
	/**
	 * 月份汇总信息
	 * @param borrowerId
	 * @return
	 */
	@Override
	public OperatorMonthInfoDto getMonthinfo(Long borrowerId,Long orderId) {
		//引流渠道没有存该数据
		return new OperatorMonthInfoDto();
	}
	
	/**
	 * 流量详单
	 * @param orderId
	 * @return
	 */
	@Override
	public List<OperatorNetDataDto> getNetDataList(Long borrowerId,Long orderId){
		//引流渠道没有存该数据
		return new ArrayList<>();
	}
	
//	private List<Map<String,String>> getCallRecordByES(SearchHits hits){
//		List<Map<String,String>> list = new ArrayList<>();
//		for (SearchHit hit : hits) {
//	    	  Map<String,Object> resultMap= hit.getSource();
//	    	  Map<String,String> record = new HashMap<>();
//	    	  Object value = null;
//	    	  for(String key : resultMap.keySet()) {
//	    		  value = resultMap.get(key);
//	    		  switch(key) {
//	    		  case "trade_type":
//	    			  record.put(key, null == value ? "" : String.valueOf(value));
//	    			  break;
//	    		  case "trade_time":
//	    			  record.put(key, null == value ? "" : String.valueOf(value));
//	    			  break;
//	    		  case "call_time":
//	    			  record.put(key, null == value ? "" : DateUtil.getDateString(new Date((long)value), DateUtil.yyyy_MM_dd_HHmmss));
//	    			  break;
//	    		  case "trade_addr":
//	    			  record.put(key, null == value ? "" : String.valueOf(value));
//	    			  break;
//	    		  case "receive_phone":
//	    			  record.put(key, null == value ? "" : String.valueOf(value));
//	    			  break;
//	    		  case "call_type":
//	    			  record.put(key, null == value ? "" : String.valueOf(value));
//	    			  break;
//  			  default:
//  				  break;
//	    		  }
//	    	  }
//	    	  record.put("business_name",""); 
//	    	  record.put("fee","");
//	    	  record.put("special_offer","");
//	    	  list.add(record);
//	      }
//		return list;
//	}

}
