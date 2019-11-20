// package com.waterelephant.service.impl;
//
// import com.waterelephant.entity.BwXjbkDeviceInfo;
// import com.waterelephant.service.BwXjbkDeviceInfoService;
// import org.springframework.stereotype.Service;
//
// import java.util.List;
// import java.util.Map;
//
/// **
// * (code:xjbk001)
// *
// * @Author: ZhangChong
// * @Description:
// * @Date: Created in 15:19 2018/6/19
// * @Modified By:
// */
// @Service
// public class BwXjbkDeviceInfoServiceImpl extends BaseCommonServiceImpl<BwXjbkDeviceInfo, Long>
// implements BwXjbkDeviceInfoService {
// @Override
// public BwXjbkDeviceInfo findByAttr(BwXjbkDeviceInfo bwXjbkDeviceInfo) {
// return mapper.selectOne(bwXjbkDeviceInfo);
// }
//
// @Override
// public Integer saveByAttr(BwXjbkDeviceInfo bwXjbkDeviceInfo) {
// return mapper.insert(bwXjbkDeviceInfo);
// }
//
// @Override
// public Integer updateByAttr(BwXjbkDeviceInfo bwXjbkDeviceInfo) {
// return mapper.updateByPrimaryKey(bwXjbkDeviceInfo);
// }
//
// @Override
// public BwXjbkDeviceInfo queryBwXjbkDeviceInfoByOrderId(Long orderId) {
// String sql = "select a.* from bw_xjbk_device_info a where a.order_id =" + orderId;
// return sqlMapper.selectOne(sql, BwXjbkDeviceInfo.class);
// }
//
// @Override
// public int queryAllDevice(String deviceId, String strTime) {
// String sql = "select DISTINCT(c.phone) from bw_xjbk_device_info a"
// + " left join bw_order b on b.id = a.order_id " + " left join bw_borrower c on c.id = b.borrower_Id"
// + " where a.device_id = '" + deviceId + "' and b.submit_time>'" + strTime + "'";
// List<Map<String, Object>> list = sqlMapper.selectList(sql);
// if (list != null) {
// return list.size();
// } else {
// return 0;
// }
// }
//
// }
