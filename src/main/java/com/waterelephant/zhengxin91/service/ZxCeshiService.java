package com.waterelephant.zhengxin91.service;

import com.waterelephant.zhengxin91.entity.ZxCeshi;

import java.util.List;

/**
 * Created by GuoK on 2017/4/1.
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/4/1 17:29
 */
public interface ZxCeshiService {

	List<ZxCeshi> findByZxCeshi(ZxCeshi zxCeshi);

	List<ZxCeshi> findByBorrowId(String begin, String end);
}
