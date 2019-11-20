package com.waterelephant.service;

import java.util.Date;

import com.waterelephant.entity.BwThirdOperateVoice;

/**
 * @author 王亚楠
 * @version 1.0
 * @date 2018/6/8
 * @since JDK 1.8
 */
public interface BwThirdOperateVoiceService {
    /**
     * 保存
     *
     * @param bwThirdOperateVoice 渠道运营商通话记录
     * @return 影响行数
     */
    int save(BwThirdOperateVoice bwThirdOperateVoice);

    /**
     * 修改
     *
     * @param bwThirdOperateVoice 渠道运营商通话记录
     * @return 影响行数
     */
    int update(BwThirdOperateVoice bwThirdOperateVoice);

    /**
     * 查找
     *
     * @param bwThirdOperateVoice 渠道运营商通话记录
     * @return 查找对象
     */
    BwThirdOperateVoice findByAttr(BwThirdOperateVoice bwThirdOperateVoice);

    /**
     * 删除
     *
     * @param orderId 订单号
     * @return 影响行数
     */
    int deleteAllByOrderId(Long orderId);

    
    
	Date getCallTimeByborrowerIdEs(Long orderId);
}
