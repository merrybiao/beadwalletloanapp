package com.waterelephant.mapper;

import com.waterelephant.entity.BwOperateMsg;

import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface BwOperateMsgMapper extends Mapper<BwOperateMsg> {


    /**
     * (code:dkdh001)
     *
     * @param borrowerId 用户ID
     * @return int
     */
    @Delete("delete from bw_operate_msg where borrower_id = #{borrowerId}")
    int deleteByBorrowerId(Long borrowerId);

}
