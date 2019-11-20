package com.waterelephant.zhengxin91.service.impl;

import com.waterelephant.service.BaseService;
import com.waterelephant.zhengxin91.entity.JyPunishBreakDB;
import com.waterelephant.zhengxin91.service.ZxJyPunishBreakService;

import org.springframework.stereotype.Service;

/**
 * 91征信 - 人法
 * @author liuDaodao
 *
 */
@Service
public class ZxJyPunishBreakServiceImpl extends BaseService<JyPunishBreakDB, Long> implements ZxJyPunishBreakService {

	/**
	 * 保存
	 */
	@Override
	public boolean saveJyPunishBreakDB(JyPunishBreakDB jyPunishBreakDB) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(jyPunishBreakDB) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	/**
	 * 删除
	 */
	@Override
	public boolean deleteJyPunishBreakDB(JyPunishBreakDB jyPunishBreakDB) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.delete(jyPunishBreakDB) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

}
