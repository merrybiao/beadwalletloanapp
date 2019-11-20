package com.waterelephant.mapper;

import com.waterelephant.entity.BwOperateRecharge;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author lwl
 */
@Repository
public interface BwOperateRechargeMapper extends Mapper<BwOperateRecharge> {

    /**
     * (code:dkdh001)
     *
     * @param borrowerId 用户id
     * @return int
     */
    @Delete("delete from bw_operate_recharge where borrower_id = #{borrowerId}")
    int deleteByBorrowerId(Long borrowerId);
}
