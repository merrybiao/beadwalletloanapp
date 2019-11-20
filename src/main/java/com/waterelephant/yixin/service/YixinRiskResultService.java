/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.service;

import java.util.List;

import com.waterelephant.yixin.dto.param.YixinRiskResults;

/**
 * @author Administrator
 *
 */
public interface YixinRiskResultService {


	public int deleteYixinRiskResult(Long mainid);
	
	public int insertYixinRiskResult(YixinRiskResults riskResults);
	
	public List<YixinRiskResults> getYixinRiskResult(Long mainid);
}
