package com.waterelephant.service;

import com.waterelephant.entity.BwBankCardBindInfo;

public interface BwBankCardBindInfoService extends BaseCommonService<BwBankCardBindInfo, Long> {
    void saveOrUpdateByBorrowerId(BwBankCardBindInfo bankCardBindInfo);

    BwBankCardBindInfo selectByBorrowerId(Long borrowerId);
}
