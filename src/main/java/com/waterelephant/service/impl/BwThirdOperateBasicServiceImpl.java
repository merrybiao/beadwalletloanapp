package com.waterelephant.service.impl;

import com.waterelephant.entity.BwThirdOperateBasic;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwThirdOperateBasicService;
import org.springframework.stereotype.Service;

/**
 * @author 王亚楠
 * @version 1.0
 * @date 2018/6/6
 * @since JDK 1.8
 */
@Service
public class BwThirdOperateBasicServiceImpl extends BaseService<BwThirdOperateBasic, Long> implements BwThirdOperateBasicService {

    /**
     * 保存
     *
     * @param bwThirdOperateBasic 渠道运营商基础数据
     * @return 影响行数
     */
    @Override
    public int save(BwThirdOperateBasic bwThirdOperateBasic) {
        return mapper.insert(bwThirdOperateBasic);
    }

    /**
     * 修改
     *
     * @param bwThirdOperateBasic 渠道运营商基础数据
     * @return 影响行数
     */
    @Override
    public int update(BwThirdOperateBasic bwThirdOperateBasic) {
        return mapper.updateByPrimaryKey(bwThirdOperateBasic);
    }

    /**
     * 查找
     *
     * @param bwThirdOperateBasic 渠道运营商基础数据
     * @return 查找对象
     */
    @Override
    public BwThirdOperateBasic findByAttr(BwThirdOperateBasic bwThirdOperateBasic) {
        return mapper.selectOne(bwThirdOperateBasic);
    }

    /**
     * 删除
     *
     * @param orderId 订单号
     * @return 影响行数
     */
    @Override
    public int deleteAllByOrderId(Long orderId) {
        String sql = "delete from bw_third_operate_basic where order_id = " + orderId;
        return sqlMapper.delete(sql);
    }
}
