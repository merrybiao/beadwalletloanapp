package com.waterelephant.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.waterelephant.entity.BwThirdOperateVoice;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwThirdOperateVoiceService;

/**
 * @author 王亚楠
 * @version 1.0
 * @date 2018/6/6
 * @since JDK 1.8
 */
@Service
public class BwThirdOperateVoiceServiceImpl extends BaseService<BwThirdOperateVoice, Long> implements BwThirdOperateVoiceService {

    /**
     * 保存
     *
     * @param bwThirdOperateVoice 渠道运营商通话记录
     * @return 影响行数
     */
    @Override
    public int save(BwThirdOperateVoice bwThirdOperateVoice) {
        return mapper.insert(bwThirdOperateVoice);
    }

    /**
     * 修改
     *
     * @param bwThirdOperateVoice 渠道运营商通话记录
     * @return 影响行数
     */
    @Override
    public int update(BwThirdOperateVoice bwThirdOperateVoice) {
        return mapper.updateByPrimaryKey(bwThirdOperateVoice);
    }

    /**
     * 查找
     *
     * @param bwThirdOperateVoice 渠道运营商通话记录
     * @return 查找对象
     */
    @Override
    public BwThirdOperateVoice findByAttr(BwThirdOperateVoice bwThirdOperateVoice) {
        return mapper.selectOne(bwThirdOperateVoice);
    }

    /**
     * 删除
     *
     * @param orderId 订单号
     * @return 影响行数
     */
    @Override
    public int deleteAllByOrderId(Long orderId) {
        String sql = "delete from bw_third_operate_voice where order_id = " + orderId;
        return sqlMapper.delete(sql);
    }

	@Override
	public Date getCallTimeByborrowerIdEs(Long orderId) {
		String sql = "select MAX(c.call_time) from bw_third_operate_voice c where c.order_id = " + orderId;
		Date maxDate = sqlMapper.selectOne(sql, Date.class);
		return maxDate;
	}
}
