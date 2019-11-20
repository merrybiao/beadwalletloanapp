/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.service;

import java.util.List;

import com.waterelephant.yixin.dto.param.YixinQueryHistory;

/**
 * @author Administrator
 *
 */
public interface YixinQueryHistoryService {
	
	public int deleteQueryHistory(Long mainid);
	
	public int insertQueryHistory(YixinQueryHistory queryHistory);
	
	public List<YixinQueryHistory> getYixinQueryHistory(Long mainid);

}
