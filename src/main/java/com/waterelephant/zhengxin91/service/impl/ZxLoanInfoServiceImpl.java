package com.waterelephant.zhengxin91.service.impl;

import com.waterelephant.service.BaseService;
import com.waterelephant.zhengxin91.entity.ZxLoanInfo;
import com.waterelephant.zhengxin91.service.ZxLoanInfoService;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 11:12
 */
@Service
public class ZxLoanInfoServiceImpl extends BaseService<ZxLoanInfo, Long> implements ZxLoanInfoService {

    private Logger logger = Logger.getLogger(ZxLoanInfoServiceImpl.class);


    @Override
    public boolean saveZxLoanInfo(ZxLoanInfo zxLoanInfo) {
        return mapper.insert(zxLoanInfo) > 0;
    }

    @Override
    public boolean updateBwOrderAuth(ZxLoanInfo zxLoanInfo) {
        return mapper.updateByPrimaryKey(zxLoanInfo) > 0 ;
    }

    @Override
    public boolean deleteZxLoanInfo(ZxLoanInfo zxLoanInfo) {
        return mapper.delete(zxLoanInfo) > 0 ;
    }
}
