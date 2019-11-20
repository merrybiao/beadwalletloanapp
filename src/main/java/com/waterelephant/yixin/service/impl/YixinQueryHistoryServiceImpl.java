/**
 * @author heqiwen
 * @date 2016年12月23日
 */
package com.waterelephant.yixin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.service.BaseService;
import com.waterelephant.yixin.dto.param.YixinQueryHistory;
import com.waterelephant.yixin.service.YixinQueryHistoryService;

/**
 * @author Administrator
 *
 */
@Service
public class YixinQueryHistoryServiceImpl extends BaseService<YixinQueryHistory, Long> implements YixinQueryHistoryService {

	/** 
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	public int deleteQueryHistory(Long mainid) {
		YixinQueryHistory queryHistory=new YixinQueryHistory();
		queryHistory.setMainid(mainid);
		return mapper.delete(queryHistory);
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	public int insertQueryHistory(YixinQueryHistory queryHistory) {
		
		return mapper.insert(queryHistory);
	}

	/** 
	 * @author heqiwen
	 * @date 2016年12月23日
	 */
	public List<YixinQueryHistory> getYixinQueryHistory(Long mainid) {
		YixinQueryHistory queryHistory=new YixinQueryHistory();
		queryHistory.setMainid(mainid);
		return mapper.select(queryHistory);
	}

}
