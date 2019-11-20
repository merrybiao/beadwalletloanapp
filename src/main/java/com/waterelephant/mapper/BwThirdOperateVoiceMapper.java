/******************************************************************************
 * Copyright (C) 2016 Wuhan Water Elephant Co.Ltd All Rights Reserved. 
 * 本软件为武汉水象科技有限公司开发研制。 未经本公司正式书面同意，其他任何个人、
 * 团体不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.waterelephant.mapper;

import com.waterelephant.entity.BwThirdOperateVoice;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 王亚楠
 * @version 1.0
 * @date 2018/6/8
 * @since JDK 1.8
 */
@Repository
public interface BwThirdOperateVoiceMapper extends Mapper<BwThirdOperateVoice> {

    /**
     * (code:dkdh001)
     *
     * @param orderId 订单ID
     * @return List<BwThirdOperateVoice>
     */
    @Select("select * from bw_third_operate_voice where order_id = #{orderId}")
    List<BwThirdOperateVoice> findListByOrderId(Long orderId);
}
