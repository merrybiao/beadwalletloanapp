package com.waterelephant.zhengxin91.service.impl;

import com.waterelephant.service.BaseService;
import com.waterelephant.zhengxin91.entity.JyPunishedDB;
import com.waterelephant.zhengxin91.service.ZxJyPunishedService;

import org.springframework.stereotype.Service;

/**
 * 91征信 - 人法
 * @author liuDaodao
 *
 */
@Service
public class ZxJyPunishedServiceImpl extends BaseService<JyPunishedDB, Long> implements ZxJyPunishedService {

	/**
	 * 保存
	 */
	@Override
	public boolean saveJyPunishedDB(JyPunishedDB jyPunishedDB) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(jyPunishedDB) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	/**
	 * 删除
	 */
	@Override
	public boolean deleteJyPunishedDB(JyPunishedDB jyPunishedDB) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.delete(jyPunishedDB) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

}
