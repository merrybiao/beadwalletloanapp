package com.waterelephant.zhengxin91.service;

import com.waterelephant.zhengxin91.entity.ZxError;

/**
 * 91征信 - 错误信息
 * @author liuDaodao
 * @version 1.0
 * @create_date 2017/4/1 17:29
 */
public interface ZxErrorService {

    public boolean saveZxError(ZxError zxError);
}
