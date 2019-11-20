package com.waterelephant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwOrderHourStatistics;
import com.waterelephant.mapper.BwOrderHourStatisticsMapper;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderHourStatisticsService;
import com.waterelephant.utils.DateUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BwOrderHourStatisticsServiceImpl extends BaseService<BwOrderHourStatistics, Long> implements BwOrderHourStatisticsService {
	
	//自有渠道（安卓、IOS、微信）
	private static final Integer[] OWN_CHANNELS = new Integer[] {1,2,4};
	/**
	 * 渠道号  渠道名称  备注
	 * 809  融易推  
	 * 820  优借-速秒钱包  分期管家
	 * 833  现金白卡-速秒钱包  
	 * 837  卡牛信用卡管家-速秒钱包  
	 * 840  榕树贷款API-速秒钱包  
	 * 850  闪贷借款-速秒钱包  好贷 
	 * 862  借点钱-速秒钱包  
	 * 876  速贷贷款-速秒钱包  借吧
	 * 881 51卡宝-速秒钱包  
	 * 883  信用管家  
	 * 887  闪电贷API-速秒钱包  
	 * 898  拉卡拉API  未上线
	 * 900  给你花API  
	 * 908 360贷款导航API  该渠道暂停推量
	 * 909  挖财API  
	 * 910  新浪-API  
	 * 965  龙分期  
	 * 966  带上钱API  快牛
	 * 938  菠萝贷API
	 * 1052 借钱快手（77）
	 * 1055 借吧（77）
	 * 5058 现金巴士（乐分期）
	 * 1043 现金白卡（乐分期）
	 * 1049 带上钱（乐分期）
	 */
	//api渠道（新浪、借点钱、借吧等）
	private static final Integer[] API_CHANNELS =new Integer [] {809,820,833,837,840,850,862,876,881,883,887,898,900,908,909,910,965,966,938,1052,1055,5058,1043,1049};
    //其他渠道 not in
	private static final Integer[] OTHER_CHANNELS = new Integer [] {1,2,4,809,820,833,837,840,850,862,876,881,883,887,898,900,908,909,910,965,966,938,1052,1055,5058,1043,1049};
	
	public static final Integer productId = 7;

	@Autowired
	private BwOrderHourStatisticsMapper bwOrderHourStatisticsMapper;
	
	@Override
	public Map<String,Object> currOrderApplyCountStatistics(){
		List<Map<String,Object>> ownData = bwOrderHourStatisticsMapper.queryOrderHourStatistics(1, OWN_CHANNELS);
		
		List<Map<String,Object>> apiData = bwOrderHourStatisticsMapper.queryOrderHourStatistics(1, API_CHANNELS);
		
		List<Map<String,Object>> otherData = bwOrderHourStatisticsMapper.queryOtherChannelsOrderHourStatistics(1, OTHER_CHANNELS);
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("own_channel", ownData);
		resultMap.put("api_channel", apiData);
		resultMap.put("other_channel", otherData);
		return resultMap;
	}
	
	@Override
	public Map<String,Object> currOrderApplyCountStatistics(String startTime,String endTime){
		Map<String,Object> resultMap = new HashMap<>();
		
		//自有渠道
		List<Map<String,Object>> ownData = new ArrayList<>();
		for(Integer channelId : OWN_CHANNELS) {
			Map<String,Object> channelMap = new HashMap<>();
			List<BwOrderHourStatistics> channelData = queryOrderStatisticsRecord(productId, channelId, 1, startTime, endTime);
			Map<String,Object> orderData = new HashMap<>();
			BwOrderHourStatistics record = null;
			if(null != channelData && !channelData.isEmpty()) {
				for(int i=0;i<channelData.size();i++) {
					record = channelData.get(i);
					String key = DateUtil.getDateString(record.getEndTime(), "HH:mm");
					Object value = record.getOrderCount();
					orderData.put(key, value);
				}
			}
			channelMap.put("channel_id", null ==  record ? channelId : record.getChannelId());
			channelMap.put("channel_name", null == record ? "" : record.getChannelName());
			channelMap.put("order_data", orderData);
			ownData.add(channelMap);
		}
		
		//api渠道
		List<Map<String,Object>> apiData = new ArrayList<>();
		for(Integer channelId : API_CHANNELS) {
			Map<String,Object> channelMap = new HashMap<>();
			List<BwOrderHourStatistics> channelData = queryOrderStatisticsRecord(productId, channelId, 1, startTime, endTime);
			Map<String,Object> orderData = new HashMap<>();
			BwOrderHourStatistics record = null;
			if(null != channelData && !channelData.isEmpty()) {
				for(int i=0;i<channelData.size();i++) {
					record = channelData.get(i);
					String key = DateUtil.getDateString(record.getEndTime(), "HH:mm");
					Object value = record.getOrderCount();
					orderData.put(key, value);
				}
			}
			
			channelMap.put("channel_id", null ==  record ? channelId : record.getChannelId());
			channelMap.put("channel_name", null == record ? "" : record.getChannelName());
			channelMap.put("order_data", orderData);
			apiData.add(channelMap);
		}
		
		//其他渠道
		List<Map<String,Object>> otherData = new ArrayList<>();
		if(true) {
			Map<String,Object> channelMap = new HashMap<>();
			List<BwOrderHourStatistics> channelData = bwOrderHourStatisticsMapper.queryOtherChannelsOrderSum(productId, OTHER_CHANNELS,1,startTime, endTime);
			Map<String,Object> orderData = new HashMap<>();
			BwOrderHourStatistics record = null;
			if(null != channelData && !channelData.isEmpty()) {
				for(int i=0;i<channelData.size();i++) {
					record = channelData.get(i);
					String key = DateUtil.getDateString(record.getEndTime(), "HH:mm");
					Object value = record.getOrderCount();
					orderData.put(key, value);
				}
			}
			
			channelMap.put("channel_id", null ==  record ? -1 : record.getChannelId());
			channelMap.put("channel_name", null == record ? "" : record.getChannelName());
			channelMap.put("order_data", orderData);
			otherData.add(channelMap);
		}
		
		resultMap.put("own_channel", ownData);
		resultMap.put("api_channel", apiData);
		resultMap.put("other_channel", otherData);
		return resultMap;
	}
	
	@Override
	public Map<String, Object> currOrderAuditCountStatistics() {
		List<Map<String,Object>> ownData = bwOrderHourStatisticsMapper.queryOrderHourStatistics(2, OWN_CHANNELS);
	
		List<Map<String,Object>> apiData = bwOrderHourStatisticsMapper.queryOrderHourStatistics(2, API_CHANNELS);
		
		List<Map<String,Object>> otherData = bwOrderHourStatisticsMapper.queryOtherChannelsOrderHourStatistics(2, OTHER_CHANNELS);
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("own_channel", ownData);
		resultMap.put("api_channel", apiData);
		resultMap.put("other_channel", otherData);
		return resultMap;
	}
	
	
	@Override
	public Map<String,Object> currOrderAuditCountStatistics(String startTime,String endTime){
		Map<String,Object> resultMap = new HashMap<>();
		
		//自有渠道
		List<Map<String,Object>> ownData = new ArrayList<>();
		for(Integer channelId : OWN_CHANNELS) {
			Map<String,Object> channelMap = new HashMap<>();
			List<BwOrderHourStatistics> channelData = queryOrderStatisticsRecord(productId, channelId, 2, startTime, endTime);
			Map<String,Object> orderData = new HashMap<>();
			BwOrderHourStatistics record = null;
			if(null != channelData && !channelData.isEmpty()) {
				for(int i=0;i<channelData.size();i++) {
					record = channelData.get(i);
					String key = DateUtil.getDateString(record.getEndTime(), "HH:mm");
					Object value = record.getOrderCount();
					orderData.put(key, value);
				}
			}
			channelMap.put("channel_id", null ==  record ? channelId : record.getChannelId());
			channelMap.put("channel_name", null == record ? "" : record.getChannelName());
			channelMap.put("order_data", orderData);
			ownData.add(channelMap);
		}
		
		//api渠道
		List<Map<String,Object>> apiData = new ArrayList<>();
		for(Integer channelId : API_CHANNELS) {
			Map<String,Object> channelMap = new HashMap<>();
			List<BwOrderHourStatistics> channelData = queryOrderStatisticsRecord(productId, channelId, 2, startTime, endTime);
			Map<String,Object> orderData = new HashMap<>();
			BwOrderHourStatistics record = null;
			if(null != channelData && !channelData.isEmpty()) {
				for(int i=0;i<channelData.size();i++) {
					record = channelData.get(i);
					String key = DateUtil.getDateString(record.getEndTime(), "HH:mm");
					Object value = record.getOrderCount();
					orderData.put(key, value);
				}
			}
			
			channelMap.put("channel_id", null ==  record ? channelId : record.getChannelId());
			channelMap.put("channel_name", null == record ? "" : record.getChannelName());
			channelMap.put("order_data", orderData);
			apiData.add(channelMap);
		}
		
		//其他渠道
		List<Map<String,Object>> otherData = new ArrayList<>();
		if(true) {
			Map<String,Object> channelMap = new HashMap<>();
			List<BwOrderHourStatistics> channelData = bwOrderHourStatisticsMapper.queryOtherChannelsOrderSum(productId, OTHER_CHANNELS,2,startTime, endTime);
			Map<String,Object> orderData = new HashMap<>();
			BwOrderHourStatistics record = null;
			if(null != channelData && !channelData.isEmpty()) {
				for(int i=0;i<channelData.size();i++) {
					record = channelData.get(i);
					String key = DateUtil.getDateString(record.getEndTime(), "HH:mm");
					Object value = record.getOrderCount();
					orderData.put(key, value);
				}
			}
			
			channelMap.put("channel_id", null ==  record ? -1 : record.getChannelId());
			channelMap.put("channel_name", null == record ? "" : record.getChannelName());
			channelMap.put("order_data", orderData);
			otherData.add(channelMap);
		}
		
		resultMap.put("own_channel", ownData);
		resultMap.put("api_channel", apiData);
		resultMap.put("other_channel", otherData);
		return resultMap;
	}



	@Override
	public Map<String, Object> currOrderLoansCountStatistics() {
		List<Map<String,Object>> ownData = bwOrderHourStatisticsMapper.queryOrderHourStatistics(3, OWN_CHANNELS);
	
		List<Map<String,Object>> apiData = bwOrderHourStatisticsMapper.queryOrderHourStatistics(3, API_CHANNELS);
		
		List<Map<String,Object>> otherData = bwOrderHourStatisticsMapper.queryOtherChannelsOrderHourStatistics(3, OTHER_CHANNELS);
		
		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("own_channel", ownData);
		resultMap.put("api_channel", apiData);
		resultMap.put("other_channel", otherData);
		return resultMap;
	}
	
	@Override
	public Map<String,Object> currOrderLoansCountStatistics(String startTime,String endTime){
		Map<String,Object> resultMap = new HashMap<>();
		
		//自有渠道
		List<Map<String,Object>> ownData = new ArrayList<>();
		for(Integer channelId : OWN_CHANNELS) {
			Map<String,Object> channelMap = new HashMap<>();
			List<BwOrderHourStatistics> channelData = queryOrderStatisticsRecord(productId, channelId, 3, startTime, endTime);
			Map<String,Object> orderData = new HashMap<>();
			BwOrderHourStatistics record = null;
			if(null != channelData && !channelData.isEmpty()) {
				for(int i=0;i<channelData.size();i++) {
					record = channelData.get(i);
					String key = DateUtil.getDateString(record.getEndTime(), "HH:mm");
					Object value = record.getOrderCount();
					orderData.put(key, value);
				}
			}
			channelMap.put("channel_id", null ==  record ? channelId : record.getChannelId());
			channelMap.put("channel_name", null == record ? "" : record.getChannelName());
			channelMap.put("order_data", orderData);
			ownData.add(channelMap);
		}
		
		//api渠道
		List<Map<String,Object>> apiData = new ArrayList<>();
		for(Integer channelId : API_CHANNELS) {
			Map<String,Object> channelMap = new HashMap<>();
			List<BwOrderHourStatistics> channelData = queryOrderStatisticsRecord(productId, channelId, 3, startTime, endTime);
			Map<String,Object> orderData = new HashMap<>();
			BwOrderHourStatistics record = null;
			if(null != channelData && !channelData.isEmpty()) {
				for(int i=0;i<channelData.size();i++) {
					record = channelData.get(i);
					String key = DateUtil.getDateString(record.getEndTime(), "HH:mm");
					Object value = record.getOrderCount();
					orderData.put(key, value);
				}
			}
			
			channelMap.put("channel_id", null ==  record ? channelId : record.getChannelId());
			channelMap.put("channel_name", null == record ? "" : record.getChannelName());
			channelMap.put("order_data", orderData);
			apiData.add(channelMap);
		}
		
		//其他渠道
		List<Map<String,Object>> otherData = new ArrayList<>();
		if(true) {
			Map<String,Object> channelMap = new HashMap<>();
			List<BwOrderHourStatistics> channelData = bwOrderHourStatisticsMapper.queryOtherChannelsOrderSum(productId, OTHER_CHANNELS,3,startTime, endTime);
			Map<String,Object> orderData = new HashMap<>();
			BwOrderHourStatistics record = null;
			if(null != channelData && !channelData.isEmpty()) {
				for(int i=0;i<channelData.size();i++) {
					record = channelData.get(i);
					String key = DateUtil.getDateString(record.getEndTime(), "HH:mm");
					Object value = record.getOrderCount();
					orderData.put(key, value);
				}
			}
			
			channelMap.put("channel_id", null ==  record ? -1 : record.getChannelId());
			channelMap.put("channel_name", null == record ? "" : record.getChannelName());
			channelMap.put("order_data", orderData);
			otherData.add(channelMap);
		}
		
		resultMap.put("own_channel", ownData);
		resultMap.put("api_channel", apiData);
		resultMap.put("other_channel", otherData);
		return resultMap;
	}


	@Override
	public List<Map<String,Object>> queryCurrOrderApplyCount(Integer[] channels){
		
		return bwOrderHourStatisticsMapper.queryOrderHourStatistics(1, channels);
	}
	
	@Override
	public List<Map<String,Object>> queryCurrOrderAuditCount(Integer[] channels){
		
		return bwOrderHourStatisticsMapper.queryOrderHourStatistics(2, channels);
	}
	
	@Override
	public List<Map<String,Object>> queryCurrOrderLoansCount(Integer[] channels){
		
		return bwOrderHourStatisticsMapper.queryOrderHourStatistics(3, channels);
	}
	
	public List<BwOrderHourStatistics> queryOrderStatisticsRecord(Integer productId,Integer channelId,Integer type,String startTime,String endTime) {
		Example example = new Example(BwOrderHourStatistics.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("productId", productId);
		criteria.andEqualTo("channelId", channelId);
		criteria.andEqualTo("statisticsType", type);
		criteria.andBetween("endTime", startTime, endTime);
		example.setOrderByClause("end_time DESC");
		return selectByExample(example);
	}
	
}
