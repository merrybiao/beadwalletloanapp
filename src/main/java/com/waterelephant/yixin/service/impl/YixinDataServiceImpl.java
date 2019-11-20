/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beadwallet.service.serve.BeadWalletYixinService;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;
import com.waterelephant.service.BaseService;
import com.waterelephant.utils.MyDateUtils;
import com.waterelephant.yixin.dto.YixinMainData;
import com.waterelephant.yixin.dto.param.YiXinParamDto;
import com.waterelephant.yixin.dto.param.YixinLoanRecords;
import com.waterelephant.yixin.dto.param.YixinQueryHistory;
import com.waterelephant.yixin.dto.param.YixinQueryStatistics;
import com.waterelephant.yixin.dto.param.YixinRiskResults;
import com.waterelephant.yixin.service.YixinDataService;
import com.waterelephant.yixin.service.YixinLoanRecordService;
import com.waterelephant.yixin.service.YixinQueryHistoryService;
import com.waterelephant.yixin.service.YixinRiskResultService;

/**
 * @author Administrator
 *
 */
@Service
public class YixinDataServiceImpl extends BaseService<YixinMainData, Long> implements YixinDataService{
	
	@Autowired
	private YixinLoanRecordService yixinLoanRecordService;
	@Autowired
	private YixinQueryHistoryService yixinQueryHistoryService;
	@Autowired
	private YixinRiskResultService yixinRiskResultService;
		
	private Logger logger = Logger.getLogger(YixinDataServiceImpl.class);
	/**
	 * 传给身份证，姓名，和查询类型（查询类型可以不用传）。就会返回宜信致诚的借贷，风险数据
	 * 业务：首先要去数据库里查询，如果有数据，就要判断更新时间是否是在一个月以内的，若是一个月以内的，就查数据库的数据返回；若是一个月外的，就要去宜信那边查数据并更新数据和返回。 
	 * 如果在数据库里没查到数据，就去宜信那里查数据插入数据库并返回。
	 * @author heqiwen
	 * @date 2016年12月26日
	 */
	public YixinMainData saveAndQueryYixinDatas(String idNo,String name,String queryReason){
			YixinMainData YixinMainData=new YixinMainData();
			YixinMainData.setIdNo(idNo);
			YixinMainData.setName(name);
			if(StringUtils.isNullOrEmpty(queryReason)){
				queryReason="10";
			}
			Date now = new Date();
			try{
					List<YixinMainData> mainlist=mapper.select(YixinMainData);
					if(mainlist!=null&&mainlist.size()>0){//如果数据库中有它的记录
						YixinMainData=mainlist.get(0);
						Date updateTime = YixinMainData.getUpdateTime();
						int days = MyDateUtils.getDaySpace(updateTime, now);
						if (days < 30) {//如果数据库中的记录是在30天内更新的，就直接查询库时的数据返回即可
							logger.info("数据库中的记录是在30天内更新的，就直接查询库时的数据返回即可");
							YixinMainData=fetchYixinMainData(YixinMainData);
						}else{//如果数据库中的数据已30天没更新了。就重新从接口那边获取。先删除原来的，再去增加新数据。
							logger.info("数据库中的记录是在30天之外的,删除前面的记录,保存新记录");
							//String json=BeadWalletYixinService.queryCreditData(idNo, name, queryReason);
							YixinMainData=saveAndgetYixinMainData(idNo, name, queryReason,YixinMainData.getId(),YixinMainData.getCreateTime());
						}
						
					}else{//如果数据库中没有关于它的记录
						YixinMainData=saveAndgetYixinMainData(idNo, name, queryReason,0l,null);
					}
					
			}catch(Exception e){
				e.printStackTrace();
				logger.error("saveAndQueryYixinDatas调宜信致诚接口出错:"+e);
			}
			return YixinMainData;
	}
	
	
	/**
	 * 通过主表关联查询到它的子表里的数据。
	 * @param mainData
	 * @return
	 * @author heqiwen
	 * @date 2016年12月26日
	 */
	private YixinMainData fetchYixinMainData(YixinMainData mainData){
		List<YixinQueryHistory> historylist=yixinQueryHistoryService.getYixinQueryHistory(mainData.getId());
		mainData.setQueryHistory(historylist);
		
		List<YixinLoanRecords> loanRecordlist=yixinLoanRecordService.getYixinLoanRecord(mainData.getId());
		mainData.setLoanRecords(loanRecordlist);
		
		List<YixinRiskResults> riskResultList=yixinRiskResultService.getYixinRiskResult(mainData.getId());
		mainData.setRiskResults(riskResultList);
		
		return mainData;
	}
	
	/**
	 * 新增或更新数据库里的数据
	 * @param idNo
	 * @param name
	 * @param queryReason
	 * @param flag
	 * @param createtime
	 * @return
	 * @author heqiwen
	 * @date 2016年12月26日
	 */
	private YixinMainData saveAndgetYixinMainData(String idNo,String name,String queryReason,Long flag,Date createtime){
		YixinMainData YixinMainData=new YixinMainData();
		Date now = new Date();
		String json=BeadWalletYixinService.queryCreditData(idNo, name, queryReason);
		if(json!=null&&!json.equals("")){
			YixinMainData = new Gson().fromJson(json, YixinMainData.class);
			if(YixinMainData!=null){
				YixinQueryStatistics queryStatistics=YixinMainData.getQueryStatistics();
				if(queryStatistics==null){
					logger.info("调宜信接口中数据,查询机构为空.可能今日查询次数到上限");
					if(YixinMainData.getZcCreditScore()==null){
						logger.info("调宜信接口中数据,查询机构为空且致诚分为空.今日查询次数到上限");
						return YixinMainData;
					}
				}else{
					YixinMainData.setTimesByCurrentOrg(queryStatistics.getTimesByCurrentOrg());
					YixinMainData.setTimesByOtherOrg(queryStatistics.getTimesByOtherOrg());
					YixinMainData.setOtherOrgCount(queryStatistics.getOtherOrgCount());
				}
				YixinMainData.setIdNo(idNo);
				YixinMainData.setName(name);
				if(flag<1||0l==flag){
					YixinMainData.setCreateTime(now);
					YixinMainData.setUpdateTime(now);
					mapper.insert(YixinMainData);
				}else{
					if(YixinMainData.getQueryHistory()!=null&&YixinMainData.getQueryHistory().size()>0){
						yixinQueryHistoryService.deleteQueryHistory(flag);
					}
					if(YixinMainData.getLoanRecords()!=null&&YixinMainData.getLoanRecords().size()>0){
						yixinLoanRecordService.deleteYixinLoanRecord(flag);
					}
					if(YixinMainData.getRiskResults()!=null&&YixinMainData.getRiskResults().size()>0){
						yixinRiskResultService.deleteYixinRiskResult(flag);
					}
					YixinMainData.setUpdateTime(now);
					YixinMainData.setId(flag);
					YixinMainData.setCreateTime(createtime);
					mapper.updateByPrimaryKey(YixinMainData);
				}
				long mainid=YixinMainData.getId();
				if(YixinMainData.getQueryHistory()!=null){//遍历增加查询历史
					for(YixinQueryHistory history:YixinMainData.getQueryHistory()){
						history.setCreateTime(now);
						history.setUpdateTime(now);
						history.setMainid(mainid);
						yixinQueryHistoryService.insertQueryHistory(history);
					}
					
				}
				if(YixinMainData.getLoanRecords()!=null){
					for(YixinLoanRecords records:YixinMainData.getLoanRecords()){
						records.setCreateTime(now);
						records.setUpdateTime(now);
						records.setMainid(mainid);
						yixinLoanRecordService.insertYixinLoanRecord(records);
						
					}
				}
				if(YixinMainData.getRiskResults()!=null){
					for(YixinRiskResults results: YixinMainData.getRiskResults()){
						results.setCreateTime(now);
						results.setUpdateTime(now);
						results.setMainid(mainid);
						yixinRiskResultService.insertYixinRiskResult(results);
					}
				}
				
			}
		}
		return YixinMainData;
		
	}
	
}
