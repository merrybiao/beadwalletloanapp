package com.waterelephant.zhengxin91.service;

import com.waterelephant.zhengxin91.entity.CrimeEscapingDB;

/**
 * 91征信 - 犯罪在逃
 * @author liuDaodao
 *
 */
public interface ZxCrimeescapingService {

	public boolean saveCrimeEscapingDB(CrimeEscapingDB crimeEscapingDB);

	public boolean deleteCrimeEscapingDB(CrimeEscapingDB crimeEscapingDB);
}
