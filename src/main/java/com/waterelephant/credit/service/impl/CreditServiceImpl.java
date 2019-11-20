package com.waterelephant.credit.service.impl;

import com.waterelephant.credit.service.CreditService;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwRepaymentPlan;
import com.waterelephant.mapper.BwOrderMapper;
import com.waterelephant.mapper.BwRepaymentPlanMapper;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DoubleUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * Module:
 * <p>
 * CreditServiceImpl.java
 *
 * @author 张博
 * @version 1.0
 * @description:
 * @since JDK 1.8
 */
@Service
public class CreditServiceImpl implements CreditService {

    @Autowired
    private IBwOrderService bwOrderService;
    @Autowired
    private BwOrderMapper bwOrderMapper;
    @Autowired
    private BwRepaymentPlanMapper bwRepaymentPlanMapper;


    @Override
    public Double calculateUseCreditMoney(String idCard) {
        Double sum = 0.0d;

        //1-  根据传入身份证查询借款人
        List<BwBorrower> bwBorrowers = bwOrderMapper.selectUserByIdCard(idCard);
        if (CommUtils.isNull(bwBorrowers)) {
            return sum;
        }

        List<Object> bwBorrowerList = new ArrayList<>();
        for (BwBorrower bwBorrower : bwBorrowers) {
            bwBorrowerList.add(bwBorrower.getId());
        }

        //2-  查询所有渠道正在进行中的工单
        Example example = new Example(BwOrder.class);
        List<Object> statusList = new ArrayList<>();
        statusList.add("4");
        statusList.add("9");
        statusList.add("10");
        statusList.add("11");
        statusList.add("12");
        statusList.add("13");
        statusList.add("14");
        example.createCriteria().andIn("borrowerId", bwBorrowerList).andIn("statusId", statusList);
        List<BwOrder> bwOrders = bwOrderService.findBwOrderByExample(example);

        if (CommUtils.isNull(bwOrders)) {
            return sum;
        }

        //3-  查询工单已经使用金额
        for (BwOrder bwOrder : bwOrders) {
            Long orderId = bwOrder.getId();
            Long status = bwOrder.getStatusId();
            Double borrowAmount = bwOrder.getBorrowAmount();

            if (status == 9 || status == 13) {
                //4- 查询所有还款计划
                List<BwRepaymentPlan> repaymentPlans = bwRepaymentPlanMapper.findRepaymentPlans(orderId);
                for (BwRepaymentPlan bwRepaymentPlan : repaymentPlans) {
                    // 获取每一期需要还款的本金
                    Double repayCorpusMoney = bwRepaymentPlan.getRepayCorpusMoney();
                    // 获取每一期已经还款的金额
                    Double alreadyRepayMoney = bwRepaymentPlan.getAlreadyRepayMoney();

                    if (CommUtils.isNull(repayCorpusMoney)) {
                        repayCorpusMoney = 0.0d;
                    }

                    if (CommUtils.isNull(alreadyRepayMoney)) {
                        alreadyRepayMoney = 0.0d;
                    }

                    // 剩余额度
                    Double surplusMoney = DoubleUtil.sub(repayCorpusMoney, alreadyRepayMoney);

                    if (surplusMoney <= 0.0d) {
                        surplusMoney = 0.0d;
                    }

                    sum = DoubleUtil.add(sum, surplusMoney);
                }
            } else {
                sum = DoubleUtil.add(sum, borrowAmount);
            }
        }
        return sum;
    }
}
