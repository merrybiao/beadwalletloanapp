// package com.waterelephant.service.impl;
//
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.FqgjOperateBasicService;
// import com.waterelephant.sxyDrainage.entity.fqgj.FqgjOperateBasic;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
//
/// **
// * @author wangfei
// * @version 1.0
// * @date 2018/5/29
// * @Description <TODO>
// * @since JDK 1.8
// */
// @Service
// public class FqgjOperateBasicServiceImpl extends BaseService<FqgjOperateBasic, Long> implements
// FqgjOperateBasicService{
//
//
// @Override
// public Integer save(FqgjOperateBasic fqgjOperateBasic) {
// return mapper.insert(fqgjOperateBasic);
// }
//
// @Override
// public Integer updateByOrderId(FqgjOperateBasic fqgjOperateBasic) {
// return mapper.updateByPrimaryKey(fqgjOperateBasic);
// }
//
// @Override
// public FqgjOperateBasic getFqgjOperateBasicByOrderId(Long orderId) {
// String sql = "select * from bw_fqgj_operate_basic where order_id = " + orderId + " limit 1";
// return sqlMapper.selectOne(sql, FqgjOperateBasic.class);
// }
//
// @Override
// public List<FqgjOperateBasic> getFqgjOperateBasicList(Long orderId) {
// String sql = "select * from bw_fqgj_operate_basic p where p.order_id = " + orderId;
// return sqlMapper.selectList(sql, FqgjOperateBasic.class);
// }
//
// @Override
// public int delFqgjOperateBasic(FqgjOperateBasic fqgjOperateBasic) {
// return mapper.delete(fqgjOperateBasic);
// }
//
// @Override
// public int delFqgjOperateBasicByOrderId(Long orderId) {
// String sql = "delete from bw_fqgj_operate_basic where order_id=" + orderId;
// return sqlMapper.delete(sql);
//
// }
// }
