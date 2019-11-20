package com.waterelephant.mapper;

import com.waterelephant.entity.BwOperateBill;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author zhangc
 * @date 2018年7月27日
 */
@Repository
public interface BwOperateBillMapper extends Mapper<BwOperateBill> {

    /**
     * (code:dkdh001)
     *
     * @param borrowerId 用户ID
     * @return int
     */
    @Delete("delete from bw_operate_bill where borrower_id = #{borrowerId}")
    int deleteByBorrowerId(Long borrowerId);
}
