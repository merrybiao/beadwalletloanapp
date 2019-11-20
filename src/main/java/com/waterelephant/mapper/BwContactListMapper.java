package com.waterelephant.mapper;

import com.waterelephant.entity.BwContactList;

import org.apache.ibatis.annotations.Delete;

import tk.mybatis.mapper.common.Mapper;

/**
 * @since 2018-07-18
 */
public interface BwContactListMapper extends Mapper<BwContactList> {

    /**
     * 根据借款人ID删除通讯录信息
     *
     * @param borrowerId
     * @return
     */
    @Delete("delete from bw_contact_list where borrower_id = #{borrowerId}")
    int deleteByBorrowerId(Long borrowerId);
}
