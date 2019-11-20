package com.waterelephant.zhengxin91.service;

import com.waterelephant.zhengxin91.entity.ZxTrxNo;

/**
 * Created by GuoK on 2017/4/1.
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/4/1 17:29
 */
public interface ZxTrxNoService {

	public int save(ZxTrxNo zxTrxNo);

	// public boolean addZxTrxNo(ZxTrxNo zxTrxNo);

	public boolean updateZxTrxNo(ZxTrxNo zxTrxNo);

	public boolean deleteByBorrowerId(String borrowerId);

	public ZxTrxNo findByBorrowId(Long borrowId);

	public ZxTrxNo findByTrxNo(String trxNo);
}
