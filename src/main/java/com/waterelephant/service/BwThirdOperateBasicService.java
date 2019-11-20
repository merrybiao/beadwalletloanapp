package com.waterelephant.service;

import com.waterelephant.entity.BwThirdOperateBasic;

/**
 * @author 王亚楠
 * @version 1.0
 * @date 2018/6/6
 * @since JDK 1.8
 */
public interface BwThirdOperateBasicService {
    /**
     * 保存
     *
     * @param bwThirdOperateBasic 渠道运营商基础数据
     * @return 影响行数
     */
    int save(BwThirdOperateBasic bwThirdOperateBasic);

    /**
     * 修改
     *
     * @param bwThirdOperateBasic 渠道运营商基础数据
     * @return 影响行数
     */
    int update(BwThirdOperateBasic bwThirdOperateBasic);

    /**
     * 查找
     *
     * @param bwThirdOperateBasic 渠道运营商基础数据
     * @return 查找对象
     */
    BwThirdOperateBasic findByAttr(BwThirdOperateBasic bwThirdOperateBasic);

    /**
     * 删除
     *
     * @param orderId 订单号
     * @return 影响行数
     */
    int deleteAllByOrderId(Long orderId);
}
