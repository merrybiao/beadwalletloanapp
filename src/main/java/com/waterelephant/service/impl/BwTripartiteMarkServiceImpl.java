// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwTripartiteMark;
// import com.waterelephant.service.BaseService;
// import com.waterelephant.service.BwTripartiteMarkService;
// import org.springframework.stereotype.Service;
//
/// **
// * @author wangfei
// * @version 1.0
// * @date 2018/5/31
// * @Description <TODO>
// * @since JDK 1.8
// */
// @Service
// public class BwTripartiteMarkServiceImpl extends BaseService<BwTripartiteMark, Long> implements
// BwTripartiteMarkService{
// @Override
// public Integer save(BwTripartiteMark bwTripartiteMark) {
// return mapper.insert(bwTripartiteMark);
// }
//
// @Override
// public Integer delBwTripartiteMark(BwTripartiteMark bwTripartiteMark) {
// return mapper.delete(bwTripartiteMark);
// }
//
// @Override
// public Integer updateByAttr(BwTripartiteMark bwTripartiteMark) {
// return mapper.updateByPrimaryKey(bwTripartiteMark);
// }
//
// @Override
// public BwTripartiteMark getBwTripartiteMarkByIndexKey(String indexkey) {
// String sql = "select * from bw_tripartite_mark where index_key = '" + indexkey + "' limit 1";
// return sqlMapper.selectOne(sql, BwTripartiteMark.class);
// }
// }
