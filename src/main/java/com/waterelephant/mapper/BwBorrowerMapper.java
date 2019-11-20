package com.waterelephant.mapper;

import com.waterelephant.entity.BwBorrower;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

/**
 * @author
 * @since 2018-07-23
 */
public interface BwBorrowerMapper extends Mapper<BwBorrower> {

    /**
     * 根据手机号或者身份证查询
     * @param phone
     * @param idCard
     * @return
     */
    @Select("SELECT * FROM bw_borrower WHERE phone = #{phone} and id_card = #{idCard}")
    List<BwBorrower> findByPhoneOrCard(@Param("phone") String phone, @Param("idCard") String idCard);
    
    @Select("<script>SELECT id FROM bw_borrower WHERE phone = #{phone} AND id_card = #{idcard} <if test='name!=null'> AND name = #{name}</if></script>")
    List<Long> queryBorrowerId(@Param("phone") String phone, @Param("idcard") String idcard, @Param("name") String name);
}
