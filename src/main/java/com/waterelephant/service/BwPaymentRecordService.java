package com.waterelephant.service;

import com.waterelephant.entity.BwPaymentRecord;

public interface BwPaymentRecordService extends BaseCommonService<BwPaymentRecord, Long> {
    void updateByRepayId(Long repayId, BwPaymentRecord updateRecord);
}