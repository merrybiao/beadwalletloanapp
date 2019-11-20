package com.waterelephant.service.impl;

import com.waterelephant.entity.BwBankCard;
import com.waterelephant.entity.BwBankCardBindInfo;
import com.waterelephant.mapper.BwBankCardMapper;
import com.waterelephant.service.BwBankCardBindInfoService;
import com.waterelephant.service.IBwBankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class BwBankCardBindInfoServiceImpl extends BaseCommonServiceImpl<BwBankCardBindInfo, Long> implements BwBankCardBindInfoService {

    @Autowired
    private IBwBankCardService bwBankCardService;
    @Autowired
    private BwBankCardMapper bwBankCardMapper;

    @Override
    public void saveOrUpdateByBorrowerId(BwBankCardBindInfo bankCardBindInfo) {
        if (bankCardBindInfo == null || bankCardBindInfo.getBorrowerId() == null) {
            return ;
        }
        Long borrowerId = bankCardBindInfo.getBorrowerId();
        Long bankCardId = bankCardBindInfo.getBankCardId();
        if (bankCardId == null || bankCardId <= 0) {
            BwBankCard queryBankCard = bwBankCardService.selectByBorrowerId(borrowerId);
            if (queryBankCard != null) {
                bankCardId = queryBankCard.getId();
                bankCardBindInfo.setBankCardId(bankCardId);
                Integer signStatus = queryBankCard.getSignStatus();
                Integer bindChannel = bankCardBindInfo.getBindChannel();
                if (signStatus != null && bindChannel != null && !bindChannel.equals(signStatus)) {
                    BwBankCard updateBankCard = new BwBankCard();
                    updateBankCard.setId(bankCardId);
                    updateBankCard.setSignStatus(bindChannel);
                    updateBankCard.setUpdateTime(new Date());
                    bwBankCardMapper.updateByPrimaryKeySelective(updateBankCard);
                }
            }
        }
        Date nowDate = new Date();
        BwBankCardBindInfo queryBankCardBindInfo = selectByBorrowerId(borrowerId);
        bankCardBindInfo.setUpdateTime(nowDate);
        if (queryBankCardBindInfo != null) {
            bankCardBindInfo.setId(queryBankCardBindInfo.getId());
            updateByPrimaryKeySelective(bankCardBindInfo);
        } else {
            bankCardBindInfo.setCreateTime(nowDate);
            insertSelective(bankCardBindInfo);
        }
    }

    @Override
    public BwBankCardBindInfo selectByBorrowerId(Long borrowerId) {
        if (borrowerId == null || borrowerId <= 0L) {
            return null;
        }
        Example example = new Example(BwBankCardBindInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("borrowerId", borrowerId);
        List<BwBankCardBindInfo> list = selectByExample(example);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
}