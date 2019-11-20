package com.waterelephant.zhengxin91.service;

import java.util.List;

import com.waterelephant.zhengxin91.entity.ZxBlack;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 11:10
 */
public interface ZxBlackService {

	public boolean saveZxBlack(ZxBlack zxBlack);

	public boolean updateZxBlackh(ZxBlack zxBlack);

	public boolean deleteZxBlack(ZxBlack zxBlack);

	public List<ZxBlack> queryZxBlack(ZxBlack zxBlack);

	List<ZxBlack> queryZxBlack91(String cardId);

	/**
	 * @author 崔雄健
	 * @date 2017年5月13日
	 * @description
	 * @param
	 * @return
	 */
	ZxBlack queryZxBlack(long borrowerId, long orderId, long source, long sourceItem);

}
