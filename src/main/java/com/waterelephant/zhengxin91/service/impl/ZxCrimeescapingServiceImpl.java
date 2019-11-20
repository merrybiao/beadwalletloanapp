package com.waterelephant.zhengxin91.service.impl;

import com.waterelephant.service.BaseService;
import com.waterelephant.zhengxin91.entity.CrimeEscapingDB;
import com.waterelephant.zhengxin91.service.ZxCrimeescapingService;

import org.springframework.stereotype.Service;

/**
 * 91征信 - 犯罪在逃
 * @author liuDaodao
 *
 */
@Service
public class ZxCrimeescapingServiceImpl extends BaseService<CrimeEscapingDB, Long> implements ZxCrimeescapingService {

	/**
	 * 保存
	 */
	@Override
	public boolean saveCrimeEscapingDB(CrimeEscapingDB crimeEscapingDB) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.insert(crimeEscapingDB) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

	/**
	 * 删除
	 */
	@Override
	public boolean deleteCrimeEscapingDB(CrimeEscapingDB crimeEscapingDB) {
		boolean isSuccess = false;
		try {
			isSuccess = mapper.delete(crimeEscapingDB) > 0;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return isSuccess;
	}

}
