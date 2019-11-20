package com.waterelephant.mapper;

import com.waterelephant.entity.BwOperateNet;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author lwl
 */
@Repository
public interface BwOperateNetMapper extends Mapper<BwOperateNet> {

    /**
     * (code:dkdh001)
     *
     * @param borrowerId 用户ID
     * @return int
     */
    @Delete(" delete from bw_operate_net where borrower_id = #{borrowerId}")
    int deleteByBorrowerId(Long borrowerId);
}
