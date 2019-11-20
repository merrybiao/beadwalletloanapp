package com.waterelephant.zhengxin91.service.impl;

import com.waterelephant.service.BaseService;
import com.waterelephant.zhengxin91.entity.ZxCeshi;
import com.waterelephant.zhengxin91.service.ZxCeshiService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author GuoK
 * @version 1.0
 * @create_date 2017/4/1 17:30
 */
@Service
public class ZxCeshiServiceImpl extends BaseService<ZxCeshi, Long> implements ZxCeshiService {


	@Override
	public List<ZxCeshi> findByZxCeshi(ZxCeshi zxCeshi) {
		return  mapper.select(zxCeshi);
	}

	public List<ZxCeshi> findByBorrowId(String begin , String end){
		String sql ="select * from bw_zx_ceshi t " +
				"where t.borrow_id >="+begin +
				" and t.borrow_id <="+end +"  ";
		return sqlMapper.selectList(sql,ZxCeshi.class );
	}

}
