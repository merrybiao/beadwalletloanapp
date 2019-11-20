/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.service.BaseService;
import com.waterelephant.yixin.dto.param.YixinRiskResults;
import com.waterelephant.yixin.service.YixinRiskResultService;

/**
 * @author Administrator
 *
 */
@Service
public class YixinRiskResultsServiceImpl extends BaseService<YixinRiskResults, Long> implements YixinRiskResultService {

	/** 
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	public int deleteYixinRiskResult(Long mainid) {
		YixinRiskResults riskResults=new YixinRiskResults();
		riskResults.setMainid(mainid);
		
		return mapper.delete(riskResults);
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	public int insertYixinRiskResult(YixinRiskResults riskResults) {
		
		return mapper.insert(riskResults);
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	public List<YixinRiskResults> getYixinRiskResult(Long mainid) {
		YixinRiskResults riskResults=new YixinRiskResults();
		riskResults.setMainid(mainid);
		return mapper.select(riskResults);
	}

}
