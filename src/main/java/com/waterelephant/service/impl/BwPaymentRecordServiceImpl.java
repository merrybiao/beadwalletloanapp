package com.waterelephant.service.impl;

import com.waterelephant.entity.BwPaymentRecord;
import com.waterelephant.service.BwPaymentRecordService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
public class BwPaymentRecordServiceImpl extends BaseCommonServiceImpl<BwPaymentRecord, Long> implements BwPaymentRecordService {

    @Override
    public void updateByRepayId(Long repayId, BwPaymentRecord updateRecord) {
        if (repayId == null) {
            return;
        }
        Example example = new Example(BwPaymentRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("repayId", repayId);
        updateByExampleSelective(updateRecord, example);
    }
}