package com.waterelephant.zhengxin91.service.impl;

import com.waterelephant.service.BaseService;
import com.waterelephant.zhengxin91.entity.ZxError;
import com.waterelephant.zhengxin91.service.ZxErrorService;
import org.springframework.stereotype.Service;

/**
 * 91征信 - 错误信息
 * @author liuDaodao
 * @version 1.0
 * @create_date 2017/4/1 17:30
 */
@Service
public class ZxErrorServiceImpl extends BaseService<ZxError, Long> implements ZxErrorService {

	@Override
	public boolean saveZxError(ZxError zxError) {
		return mapper.insert(zxError) > 0;
	}

}
