package com.waterelephant.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderAuth;
import com.waterelephant.entity.BwOrderAuth2;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.faceID.entity.FaceIDOrderAuthDto;
import com.waterelephant.mapper.BwOrderAuthMapper;
import com.waterelephant.mapper.BwOrderAuthMapper2;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.BwOrderAuthService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.StringUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class BwOrderAuthServiceImpl extends BaseService<BwOrderAuth, Long> implements BwOrderAuthService {

	@Autowired
	private IBwWorkInfoService bwWorkInfoService;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	
	@Autowired
	private BwOrderAuthMapper bwOrderAuthMapper;
	
	@Autowired
	private BwOrderAuthMapper2 bwOrderAuthMapper2;

	@Override
	public void saveBwOrderAuth(BwOrderAuth bwOrderAuth) {
		mapper.insert(bwOrderAuth);
	}

	@Override
	public void updateBwOrderAuth(BwOrderAuth bwOrderAuth) {
		mapper.updateByPrimaryKeySelective(bwOrderAuth);
	}

	@Override
	public void deleteBwOrderAuth(BwOrderAuth bwOrderAuth) {
		mapper.delete(bwOrderAuth);
	}

	@Override
	public List<Integer> findBwOrderAuth(Long orderId) {
		StringBuilder sql = new StringBuilder("select a.auth_type from bw_order_auth a where 1=1 ");
		sql.append(" and a.order_id = ").append(orderId);
		return sqlMapper.selectList(sql.toString(), Integer.class);
	}

	@Override
	public BwOrderAuth findBwOrderAuth(Long orderId, Integer authType) {
		StringBuilder sql = new StringBuilder("select * from bw_order_auth a where 1=1 ");
		sql.append(" and a.order_id = ").append(orderId);
		sql.append(" and a.auth_type = ").append(authType);
		List<BwOrderAuth> list = sqlMapper.selectList(sql.toString(), BwOrderAuth.class);
		return CommUtils.isNull(list) ? null : list.get(0);
	}

	@Override
	public BwAdjunct findBwAdjunct(Long orderId, Integer adjunctType) {
		StringBuilder sql = new StringBuilder("select * from bw_adjunct a where 1=1 ");
		sql.append(" and a.order_id = ").append(orderId);
		sql.append(" and a.adjunct_type = ").append(adjunctType);
		List<BwAdjunct> list = sqlMapper.selectList(sql.toString(), BwAdjunct.class);
		return CommUtils.isNull(list) ? null : list.get(0);
	}

	@Override
	public BwPersonInfo findBwPersonInfo(Long borrowerId, String productType) {
		StringBuilder sql = new StringBuilder(
				"SELECT p.* FROM bw_person_info p LEFT JOIN bw_order o ON p.order_id = o.id WHERE 1=1 ");
		sql.append(" and o.borrower_id = ").append(borrowerId).append(" and o.product_type = ").append(productType)
				.append(" ORDER BY o.create_time DESC LIMIT 1");
		List<BwPersonInfo> list = sqlMapper.selectList(sql.toString(), BwPersonInfo.class);
		return CommUtils.isNull(list) ? null : list.get(0);
	}

	@Override
	public BwWorkInfo findBwWorkInfo(Long borrowerId, String productType) {
		StringBuilder sql = new StringBuilder(
				"SELECT w.* FROM bw_work_info w LEFT JOIN bw_order o ON w.order_id = o.id WHERE 1=1 ");
		sql.append(" and o.borrower_id = ").append(borrowerId).append(" and o.product_type = ").append(productType)
				.append(" ORDER BY o.create_time DESC LIMIT 1");
		List<BwWorkInfo> list = sqlMapper.selectList(sql.toString(), BwWorkInfo.class);
		return CommUtils.isNull(list) ? null : list.get(0);
	}

	@Override
	public void savePensonInfo(String orderId, String cityName, String relationName, String relationPhone,
			String unrelationName, String unrelationPhone, String comName, String industry, String workYears,
			String authChannel, String email, String address) {
		Date now = new Date();
		String city = null;
		if (cityName.length() >= 3) {
			city = cityName.substring(cityName.length() - 3, cityName.length());
		} else {
			city = cityName;
		}

		Map<String, Object> org = bwPersonInfoService.getCityId(city);
		Long orgId = null;
		// 更新工单所属地域
		if (!CommUtils.isNull(org)) {
			orgId = Long.parseLong(org.get("id").toString());
			BwOrder order = bwOrderService.findBwOrderById(orderId);
			order.setOrgId(orgId);
			order.setUpdateTime(now);
			bwOrderService.update(order);
		}
		Long order_id = Long.parseLong(orderId);
		// 个人信息
		BwPersonInfo personInfo = bwPersonInfoService.findBwPersonInfoByOrderId(order_id);
		if (personInfo == null) {
			personInfo = new BwPersonInfo();
			personInfo.setCityName(cityName);
			personInfo.setCreateTime(now);
			personInfo.setUpdateTime(now);
			personInfo.setOrderId(order_id);
			personInfo.setRelationName(relationName);
			personInfo.setRelationPhone(relationPhone);
			personInfo.setUnrelationName(unrelationName);
			personInfo.setUnrelationPhone(unrelationPhone);
			personInfo.setEmail(email);
			personInfo.setAddress(address);
			bwPersonInfoService.addBwPersonInfo(personInfo);
		} else {
			personInfo.setCityName(cityName);
			personInfo.setUpdateTime(now);
			personInfo.setRelationName(relationName);
			personInfo.setRelationPhone(relationPhone);
			personInfo.setUnrelationName(unrelationName);
			personInfo.setUnrelationPhone(unrelationPhone);
			personInfo.setEmail(email);
			personInfo.setAddress(address);
			bwPersonInfoService.update(personInfo);
		}
		// 工作信息
		BwWorkInfo workInfo = bwWorkInfoService.findBwWorkInfoByOrderId(order_id);
		if (workInfo == null) {
			workInfo = new BwWorkInfo();
			workInfo.setOrderId(order_id);
			workInfo.setCreateTime(now);
			workInfo.setUpdateTime(now);
			workInfo.setComName(comName);
			workInfo.setIndustry(industry);
			workInfo.setWorkYears(workYears);
			bwWorkInfoService.addBwWorkInfo(workInfo);
		} else {
			workInfo.setUpdateTime(now);
			workInfo.setComName(comName);
			workInfo.setIndustry(industry);
			workInfo.setWorkYears(workYears);
			bwWorkInfoService.update(workInfo);
		}
		// 工单认证状态
		BwOrderAuth orderAuth = this.findBwOrderAuth(order_id, 2);
		if (orderAuth == null) {
			orderAuth = new BwOrderAuth();
			orderAuth.setAuth_channel(Integer.parseInt(authChannel));
			orderAuth.setAuth_type(2);
			orderAuth.setCreateTime(now);
			orderAuth.setUpdateTime(now);
			orderAuth.setOrderId(order_id);
			this.saveBwOrderAuth(orderAuth);
		} else {
			orderAuth.setAuth_channel(Integer.parseInt(authChannel));
			orderAuth.setUpdateTime(now);
			this.updateBwOrderAuth(orderAuth);
		}
	}

	@Override
	public void savePicInfo(String orderId, String borrowerId, String sfzzmUrl, String sfzfmUrl, String czzUrl,
			String authChannel) {
		Date now = new Date();
		Long order_id = Long.parseLong(orderId);
		Long borrower_id = Long.parseLong(borrowerId);
		BwAdjunct zm = this.findBwAdjunct(order_id, 1);
		if (zm == null) {
			zm = new BwAdjunct();
			zm.setAdjunctPath(sfzzmUrl);
			zm.setAdjunctType(1);
			zm.setCreateTime(now);
			zm.setBorrowerId(borrower_id);
			zm.setOrderId(order_id);
			zm.setUpdateTime(now);
			bwAdjunctService.save(zm);
		} else {
			zm.setAdjunctPath(sfzzmUrl);
			zm.setUpdateTime(now);
			bwAdjunctService.update(zm);
		}
		BwAdjunct fm = this.findBwAdjunct(order_id, 2);
		if (fm == null) {
			fm = new BwAdjunct();
			fm.setAdjunctPath(sfzfmUrl);
			fm.setAdjunctType(2);
			fm.setCreateTime(now);
			fm.setBorrowerId(borrower_id);
			fm.setOrderId(order_id);
			fm.setUpdateTime(now);
			bwAdjunctService.save(fm);
		} else {
			fm.setAdjunctPath(sfzfmUrl);
			fm.setUpdateTime(now);
			bwAdjunctService.update(fm);
		}
		BwAdjunct czz = this.findBwAdjunct(order_id, 3);
		if (czz == null) {
			czz = new BwAdjunct();
			czz.setAdjunctPath(czzUrl);
			czz.setAdjunctType(3);
			czz.setCreateTime(now);
			czz.setBorrowerId(borrower_id);
			czz.setOrderId(order_id);
			czz.setUpdateTime(now);
			bwAdjunctService.save(czz);
		} else {
			czz.setAdjunctPath(czzUrl);
			czz.setUpdateTime(now);
			bwAdjunctService.update(czz);
		}
		// 工单认证状态
		BwOrderAuth orderAuth = this.findBwOrderAuth(order_id, 3);
		if (orderAuth == null) {
			orderAuth = new BwOrderAuth();
			orderAuth.setAuth_type(3);
			orderAuth.setCreateTime(now);
			orderAuth.setUpdateTime(now);
			orderAuth.setOrderId(order_id);
			orderAuth.setAuth_channel(Integer.parseInt(authChannel));
			this.saveBwOrderAuth(orderAuth);
		} else {
			orderAuth.setAuth_channel(Integer.parseInt(authChannel));
			orderAuth.setUpdateTime(now);
			this.updateBwOrderAuth(orderAuth);
		}
	}

	/**
	 * FaceID主要用来保存
	 */
	@Override
	public void savePicInfo(String orderId, String borrowerId, String sfzzmUrl, String sfzfmUrl, String czzUrl,
			String authChannel, String nameAndNumber, String photoState, String verifyFaceResult, String photoStatu) {
		Date now = new Date();
		Long order_id = Long.parseLong(orderId);
		Long borrower_id = Long.parseLong(borrowerId);
		BwAdjunct zm = this.findBwAdjunct(order_id, 1);
		if (zm == null) {
			zm = new BwAdjunct();
			zm.setAdjunctPath(sfzzmUrl);
			zm.setAdjunctType(1);
			zm.setCreateTime(now);
			zm.setBorrowerId(borrower_id);
			zm.setOrderId(order_id);
			zm.setUpdateTime(now);
			zm.setAdjunctDesc(nameAndNumber);
			zm.setPhotoState(Integer.parseInt(photoStatu));
			bwAdjunctService.save(zm);
		} else {
			zm.setAdjunctPath(sfzzmUrl);
			zm.setUpdateTime(now);
			zm.setAdjunctDesc(nameAndNumber);
			zm.setPhotoState(Integer.parseInt(photoStatu));
			bwAdjunctService.update(zm);
		}
		BwAdjunct fm = this.findBwAdjunct(order_id, 2);
		if (fm == null) {
			fm = new BwAdjunct();
			fm.setAdjunctPath(sfzfmUrl);
			fm.setAdjunctType(2);
			fm.setCreateTime(now);
			fm.setBorrowerId(borrower_id);
			fm.setOrderId(order_id);
			fm.setUpdateTime(now);
			fm.setPhotoState(Integer.parseInt(photoStatu));
			bwAdjunctService.save(fm);
		} else {
			fm.setAdjunctPath(sfzfmUrl);
			fm.setUpdateTime(now);
			fm.setPhotoState(Integer.parseInt(photoStatu));
			bwAdjunctService.update(fm);
		}
		BwAdjunct czz = this.findBwAdjunct(order_id, 3);
		if (czz == null) {
			czz = new BwAdjunct();
			czz.setAdjunctPath(czzUrl);
			czz.setAdjunctType(3);
			czz.setCreateTime(now);
			czz.setBorrowerId(borrower_id);
			czz.setOrderId(order_id);
			czz.setUpdateTime(now);
			czz.setAdjunctDesc(verifyFaceResult);
			czz.setPhotoState(Integer.parseInt(photoStatu));
			bwAdjunctService.save(czz);
		} else {
			czz.setAdjunctPath(czzUrl);
			czz.setUpdateTime(now);
			czz.setAdjunctDesc(verifyFaceResult);
			czz.setPhotoState(Integer.parseInt(photoStatu));
			bwAdjunctService.update(czz);
		}
		// 工单认证状态
		BwOrderAuth orderAuth = this.findBwOrderAuth(order_id, 3);
		if (orderAuth == null) {
			orderAuth = new BwOrderAuth();
			orderAuth.setAuth_type(3);
			orderAuth.setCreateTime(now);
			orderAuth.setUpdateTime(now);
			orderAuth.setOrderId(order_id);
			orderAuth.setAuth_channel(Integer.parseInt(authChannel));
			orderAuth.setPhotoState(Integer.parseInt(photoState));
			this.saveBwOrderAuth(orderAuth);
		} else {
			orderAuth.setAuth_channel(Integer.parseInt(authChannel));
			orderAuth.setUpdateTime(now);
			orderAuth.setPhotoState(Integer.parseInt(photoState));
			this.updateBwOrderAuth(orderAuth);
		}
	}

	/**
	 * 身份证正面保存
	 * 
	 * @param orderId
	 * @param borrowerId
	 * @param sfzzmUrl
	 * @param nameAndNumber
	 * @param photoStatu
	 */
	@Override
	public void saveOcrIDFront(String orderId, String borrowerId, String sfzzmUrl, String nameAndNumber,
			String photoStatu) {
			Date now = new Date();
			Long order_id = Long.parseLong(orderId);
			Long borrower_id = Long.parseLong(borrowerId);
			BwAdjunct zm = this.findBwAdjunct(order_id, 1);
			if (zm == null) {
				zm = new BwAdjunct();
				zm.setAdjunctPath(sfzzmUrl);
				zm.setAdjunctType(1);
				zm.setCreateTime(now);
				zm.setBorrowerId(borrower_id);
				zm.setOrderId(order_id);
				zm.setUpdateTime(now);
				zm.setAdjunctDesc(nameAndNumber);
				zm.setPhotoState(Integer.parseInt(photoStatu));
				bwAdjunctService.save(zm);
			} else {
				zm.setAdjunctPath(sfzzmUrl);
				zm.setUpdateTime(now);
				zm.setAdjunctDesc(nameAndNumber);
				zm.setPhotoState(Integer.parseInt(photoStatu));
				bwAdjunctService.update(zm);
			}
	}

	/**
	 * 身份证反面保存
	 * 
	 * @param orderId
	 * @param borrowerId
	 * @param sfzfmUrl
	 * @param nameAndNumber
	 * @param photoStatu
	 */
	@Override
	public void saveOcrIDBack(String orderId, String borrowerId, String sfzfmUrl, String photoStatu) {
		Date now = new Date();
		Long order_id = Long.parseLong(orderId);
		Long borrower_id = Long.parseLong(borrowerId);
		BwAdjunct fm = this.findBwAdjunct(order_id, 2);
		if (fm == null) {
			fm = new BwAdjunct();
			fm.setAdjunctPath(sfzfmUrl);
			fm.setAdjunctType(2);
			fm.setCreateTime(now);
			fm.setBorrowerId(borrower_id);
			fm.setOrderId(order_id);
			fm.setUpdateTime(now);
			fm.setPhotoState(Integer.parseInt(photoStatu));
			bwAdjunctService.save(fm);
		} else {
			fm.setAdjunctPath(sfzfmUrl);
			fm.setUpdateTime(now);
			fm.setPhotoState(Integer.parseInt(photoStatu));
			bwAdjunctService.update(fm);
		}
	}

	/**
	 * 主要用来保存视频图片和视频路径，以及认证信息
	 */
	@Override
	public void savePicInfoH5(String orderId, String borrowerId, String czzUrl, String authChannel, String photoState,
			String photoStatu) {
		Date now = new Date();
		Long order_id = Long.parseLong(orderId);
		Long borrower_id = Long.parseLong(borrowerId);
		BwAdjunct czz = this.findBwAdjunct(order_id, 3);
		if (czz == null) {
			czz = new BwAdjunct();
			czz.setAdjunctPath(czzUrl);
			czz.setAdjunctType(3);
			czz.setCreateTime(now);
			czz.setBorrowerId(borrower_id);
			czz.setOrderId(order_id);
			czz.setUpdateTime(now);
			czz.setAdjunctDesc("手持照片，需要人工审核");
			czz.setPhotoState(Integer.parseInt(photoStatu));
			bwAdjunctService.save(czz);
		} else {
			czz.setAdjunctPath(czzUrl);
			czz.setUpdateTime(now);
			czz.setPhotoState(Integer.parseInt(photoStatu));
			bwAdjunctService.update(czz);
		}
		// 工单认证状态
		BwOrderAuth orderAuth = this.findBwOrderAuth(order_id, 3);
		if (orderAuth == null) {
			orderAuth = new BwOrderAuth();
			orderAuth.setAuth_type(3);
			orderAuth.setCreateTime(now);
			orderAuth.setUpdateTime(now);
			orderAuth.setOrderId(order_id);
			orderAuth.setAuth_channel(Integer.parseInt(authChannel));
			orderAuth.setPhotoState(Integer.parseInt(photoState));
			this.saveBwOrderAuth(orderAuth);
		} else {
			orderAuth.setAuth_channel(Integer.parseInt(authChannel));
			orderAuth.setUpdateTime(now);
			orderAuth.setPhotoState(Integer.parseInt(photoState));
			this.updateBwOrderAuth(orderAuth);
		}
	}

	@Override
	public String checkWorkYears(int age, String workyear) {
		// 借款人工作年限选项
		Map<String, Integer> workYearMap = new HashMap<>();
		workYearMap.put("一年以内", 0);
		workYearMap.put("1-3年", 1);
		workYearMap.put("3-5年", 3);
		workYearMap.put("5-10年", 5);
		workYearMap.put("10年以上", 11);
		// 根据选项获取对应值
		Integer workYear = workYearMap.get(workyear);
		if (workYear == null) {
			return "UNKNOWN";
		}
		// 以借款人18岁工作为标准计算工作年限
		int workyear18 = age - 18;
		// 校验
		if (workyear18 >= workYear.intValue()) {
			// 通过校验
			return workyear;
		} else {
			// 设置一个范围内最大值
			if (workyear18 < 0) {
				// return "ERROR";
				return workyear;
			} else if (workyear18 == 0) {
				return "一年以内";
			} else if (workyear18 >= 1 && workyear18 < 3) {
				return "1-3年";
			} else if (workyear18 >= 3 && workyear18 < 5) {
				return "3-5年";
			} else if (workyear18 >= 5 && workyear18 <= 10) {
				return "5-10年";
			} else {
				return "10年以上";
			}
		}
	}

	@Override
	public BwOrderAuth findLastOrderAuth(Long orderId, Integer auth_type) {
		if (auth_type == null) {
			return null;
		}
		BwOrderAuth bwOrderAuth = sqlMapper.selectOne(
				"select * from bw_order_auth where order_id=" + orderId
						+ " and auth_type=#{auth_type} order by create_time desc limit 1",
				auth_type, BwOrderAuth.class);
		return bwOrderAuth;
	}

	@Override
	public BwOrderAuth findLastOrderAuthByBorrowerId(Long borrowerId, Integer auth_type) {
		if (borrowerId == null || auth_type == null) {
			return null;
		}
		BwOrderAuth bwOrderAuth = sqlMapper.selectOne(
				"select oa.* from bw_order o inner join bw_order_auth oa on o.id=oa.order_id where o.borrower_id="
						+ borrowerId
						+ " and auth_type=#{auth_type}  and product_type = 1  order by oa.update_time desc limit 1",
				auth_type, BwOrderAuth.class);
		return bwOrderAuth;
	}

	/**
	 * 根据工单ID查询所有的工单状态
	 * 
	 * @see com.waterelephant.service.BwOrderAuthService#getListBwOrderAuthByOrderId(java.lang.Long)
	 */
	@Override
	public List<BwOrderAuth> getListBwOrderAuthByOrderId(Long orderId) {
		String sql = "select id,order_id,auth_type,auth_channel,create_time,update_time from bw_order_auth where 1=1  and order_id= "
				+ orderId;
		List<BwOrderAuth> list = sqlMapper.selectList(sql, BwOrderAuth.class);
		if (list.size() > 0) {
			return list;
		}
		list.add(new BwOrderAuth());
		return list;
	}

	@Override
	public void saveCopyPensonInfoAndAuth(Long newOrderId, Long oldOrderId, Integer authChannel) {
		// 获取个人基本信息
		BwPersonInfo personInfo = bwPersonInfoService.findBwPersonInfoByOrderId(oldOrderId);
		BwWorkInfo workInfo = bwWorkInfoService.findBwWorkInfoByOrderId(oldOrderId);
		if (!CommUtils.isNull(personInfo) && !CommUtils.isNull(workInfo)) {
			savePensonInfo(String.valueOf(newOrderId), personInfo.getCityName(), personInfo.getRelationName(),
					personInfo.getRelationPhone(), personInfo.getUnrelationName(), personInfo.getUnrelationPhone(),
					workInfo.getComName(), workInfo.getIndustry(), workInfo.getWorkYears(), String.valueOf(authChannel),
					StringUtil.toString(personInfo.getEmail()), StringUtil.toString(personInfo.getAddress()));
		}
	}

	@Override
	public void saveCopyPicInfoAndAuth(Long newOrderId, Long oldOrderId, Long borrowerId, Integer authChannel) {
		BwAdjunct zm = this.findBwAdjunct(oldOrderId, 1);// 正面照
		BwAdjunct fm = this.findBwAdjunct(oldOrderId, 2);// 反面照
		BwAdjunct czz = this.findBwAdjunct(oldOrderId, 3);// 手持身份证照
		savePicInfo(String.valueOf(newOrderId), String.valueOf(borrowerId), zm.getAdjunctPath(), fm.getAdjunctPath(),
				czz.getAdjunctPath(), String.valueOf(authChannel));
	}

	@Override
	public void saveOrderAuth(Long orderId, Long borrowerId, Integer authType, Integer authChannel, Date createTime,
			Date updateTime) {
		if (orderId != null && borrowerId != null && authType != null) {
			BwOrderAuth paramAuth = new BwOrderAuth();
			paramAuth.setOrderId(orderId);
			paramAuth.setAuth_type(authType);
			BwOrderAuth bwOrderAuth = mapper.selectOne(paramAuth);
			if (bwOrderAuth == null) {
				Date nowDate = new Date();
				paramAuth.setAuth_channel(authChannel);
				paramAuth.setCreateTime(createTime);
				paramAuth.setUpdateTime(updateTime);
				mapper.insertSelective(paramAuth);
			}
		}
	}

	@Override
	public boolean checkOrderAuth(int orderId, String authType) {
		boolean flag = false;
		String sql = "select id from bw_order_auth where order_id = " + orderId + " and auth_type= " + authType + "";
		Integer id = sqlMapper.selectOne(sql, Integer.class);
		if (!StringUtil.isEmpty(id)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * FaceID专用
	 * 
	 * @param orderId
	 * @return
	 */
	@Override
	public List<FaceIDOrderAuthDto> findBwOrderAuthAndPhotoState(Long orderId) {
		StringBuilder sql = new StringBuilder("select a.auth_type,a.photo_state from bw_order_auth a where 1=1 ");
		sql.append(" and a.order_id = ").append(orderId);
		return sqlMapper.selectList(sql.toString(), FaceIDOrderAuthDto.class);
	}
	
	@Override
	public BwOrderAuth selectBwOrderAuth(Long orderId, Integer authType) {
		List<Map<String, Object>> list = bwOrderAuthMapper.selectBwOrderAuth(orderId, authType);
		
		Map<String,Object> resultMap = null == list || list.isEmpty() ? null : list.get(0);
		return null == resultMap ? null : new JSONObject(resultMap).toJavaObject(BwOrderAuth.class);
	}

	@Override
	public boolean updatePhotoStateByOrderId(Long orderId, Integer authType, Integer photoState) {
		BwOrderAuth2 record = new BwOrderAuth2();
		/*record.setOrderId(orderId);
		record.setAuthType(authType);*/
		record.setPhotoState(photoState);
		record.setUpdateTime(new Date());
		Example example = new Example(BwOrderAuth2.class);
		example.createCriteria().andEqualTo("orderId", orderId).andEqualTo("authType", authType);
		return bwOrderAuthMapper2.updateByExampleSelective(record, example) >0;
	}

	@Override
	public BwOrderAuth2 selectBwOrderAuth2(Long orderId, Integer authType) {
		Example example = new Example(BwOrderAuth2.class);
		example.createCriteria().andEqualTo("orderId", orderId).andEqualTo("authType", authType);
		List<BwOrderAuth2> list = bwOrderAuthMapper2.selectByExample(example);
		return null == list || list.isEmpty() ? null : list.get(0);
	}
}