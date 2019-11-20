package com.waterelephant.zhengxin91.service;

import com.waterelephant.zhengxin91.entity.ZxLoanInfo;

/**
 * @author GuoKun
 * @version 1.0
 * @create_date 2017/4/6 11:10
 */
public interface ZxLoanInfoService {

    public boolean saveZxLoanInfo(ZxLoanInfo zxLoanInfo);

    public boolean updateBwOrderAuth(ZxLoanInfo zxLoanInfo);

    public boolean deleteZxLoanInfo(ZxLoanInfo zxLoanInfo);

}
