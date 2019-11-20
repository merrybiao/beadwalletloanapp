package com.waterelephant.mapper;

import com.waterelephant.entity.BwRepaymentPlan;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

/**
 * 还款计划
 *
 * @author xanthuim
 */
public interface BwRepaymentPlanMapper extends Mapper<BwRepaymentPlan> {

    /**
     * 根据订单ID和期数查询还款计划
     *
     * @param orderId
     * @param number
     * @return
     */
    @Select("select * from bw_repayment_plan where order_id = #{orderId} and number = #{number}")
    BwRepaymentPlan findRepaymentPlan(@Param("orderId") Long orderId, @Param("number") Integer number);

    /**
     * 获取还款计划
     *
     * @param orderId
     * @return
     */
    @Select("select * from bw_repayment_plan where order_id = #{orderId}")
    List<BwRepaymentPlan> findRepaymentPlans(@Param("orderId") Long orderId);
}
