package com.waterelephant.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waterelephant.dto.RongOrderDto;
import com.waterelephant.dto.SystemAuditDto;
import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.entity.BwBorrower;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwOrderRong;
import com.waterelephant.entity.BwPersonInfo;
import com.waterelephant.entity.BwWorkInfo;
import com.waterelephant.service.BwOrderRongService;
import com.waterelephant.service.BwRongOrderAddInfoService;
import com.waterelephant.service.IBwAdjunctService;
import com.waterelephant.service.IBwBorrowerService;
import com.waterelephant.service.IBwOrderService;
import com.waterelephant.service.IBwPersonInfoService;
import com.waterelephant.service.IBwWorkInfoService;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.JsonUtils;
import com.waterelephant.utils.RedisUtils;
import com.waterelephant.utils.SystemConstant;
import com.waterelephant.utils.UploadToCssUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class BwRongOrderAddInfoServiceImpl implements BwRongOrderAddInfoService {
	private Logger logger = Logger.getLogger(BwRongOrderDetailServiceImpl.class);

	@Autowired
	private IBwOrderService bwOrderService;
	@Autowired
	private BwOrderRongService bwOrderRongService;
	@Autowired
	private IBwAdjunctService bwAdjunctService;
	@Autowired
	private IBwBorrowerService bwBorrowerService;
	@Autowired
	private IBwPersonInfoService bwPersonInfoService;
	@Autowired
	private IBwWorkInfoService bwWorkInfoService;

	@Override
	public int save(RongOrderDto rongOrderDto) throws Exception{
		Date now = new Date();
		String biz_data = rongOrderDto.getBiz_data();
		String sign = rongOrderDto.getSign();
		JSONObject bizData = JSONObject.fromObject(biz_data);
		String orderNo = bizData.getString("order_no");
		logger.info("融360推单号为："+orderNo);
		// 根据订单号获取工单号
		BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
		if (CommUtils.isNull(bwOrderRong)) {
			return -1;
		}
		logger.info("根据订单号：" + orderNo + "查询到的工单号为：" + bwOrderRong.getOrderId());
		// 根据工单号获取借款人id
		BwOrder bwOrder = new BwOrder();
		bwOrder.setId(bwOrderRong.getOrderId());
		bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
		Long borrowerId = bwOrder.getBorrowerId();
		logger.info("根据工单单号：" + bwOrderRong.getOrderId() + "查询到的借款人id为：" + borrowerId);
		// 图片信息
		JSONArray ID_Positive = bizData.getJSONArray("ID_Positive");
		JSONArray ID_Negative = bizData.getJSONArray("ID_Negative");
		JSONArray photo_hand_ID = bizData.getJSONArray("photo_hand_ID");
		String sfzzmUrl = ID_Positive.getString(0);
		String sfzfmUrl = ID_Negative.getString(0);
		String czzUrl = photo_hand_ID.getString(0);
		if (CommUtils.isNull(sfzzmUrl)) {
			return -1;
		}
		if (CommUtils.isNull(sfzfmUrl)) {
			return -1;
		}
		if (CommUtils.isNull(czzUrl)) {
			return -1;
		}
		sfzzmUrl = UploadToCssUtils.urlUpload(sfzzmUrl);
		sfzfmUrl = UploadToCssUtils.urlUpload(sfzfmUrl);
		czzUrl = UploadToCssUtils.urlUpload(czzUrl);
		logger.info("当前工单号："+bwOrderRong.getOrderId()+"的身份证正面url："+sfzzmUrl);
		logger.info("当前工单号："+bwOrderRong.getOrderId()+"的身份证反面url："+sfzfmUrl);
		logger.info("当前工单号："+bwOrderRong.getOrderId()+"的身份证持证照url："+czzUrl);
		String comName = bizData.getString("company_name"); // 公司名称
		String addr = bizData.getString("addr_detail"); // 居住地址
		String cityName = "";
		if (addr.contains("省")) {
			 cityName = addr.split(" ")[0]+addr.split(" ")[1]; // 城市
		}else {
			 cityName = addr.split(" ")[0]; // 城市
		}
		String haveCar = bizData.getString("havecar");
		String haveHouse = bizData.getString("havehouse");
		String haveMarry = bizData.getString("user_marriage");
		// 更新公司信息
		BwWorkInfo bwi = new BwWorkInfo();
		bwi.setOrderId(bwOrderRong.getOrderId());
		bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
		bwi.setComName(comName);
		bwi.setUpdateTime(new Date());
		bwWorkInfoService.update(bwi);
		// 持证照
		BwAdjunct czzbaj = new BwAdjunct();
		czzbaj.setAdjunctType(3);
		czzbaj.setOrderId(bwOrderRong.getOrderId());
		czzbaj = bwAdjunctService.findBwAdjunctByAttr(czzbaj);
		if (CommUtils.isNull(czzbaj)) {
			czzbaj = new BwAdjunct();
			czzbaj.setAdjunctType(3);
			czzbaj.setAdjunctPath(czzUrl);
			czzbaj.setOrderId(bwOrderRong.getOrderId());
			czzbaj.setBorrowerId(bwOrder.getBorrowerId());
			czzbaj.setCreateTime(new Date());
			bwAdjunctService.save(czzbaj);
		} else {
			czzbaj.setAdjunctPath(czzUrl);
			czzbaj.setUpdateTime(now);
			bwAdjunctService.update(czzbaj);
		}
		// 身份证正面
		BwAdjunct sfzzmbaj = new BwAdjunct();
		sfzzmbaj.setAdjunctType(1);
		sfzzmbaj.setOrderId(bwOrderRong.getOrderId());
		sfzzmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzzmbaj);
		if (CommUtils.isNull(sfzzmbaj)) {
			sfzzmbaj = new BwAdjunct();
			sfzzmbaj.setAdjunctType(1);
			sfzzmbaj.setAdjunctPath(sfzzmUrl);
			sfzzmbaj.setOrderId(bwOrderRong.getOrderId());
			sfzzmbaj.setBorrowerId(bwOrder.getBorrowerId());
			sfzzmbaj.setCreateTime(now);
			bwAdjunctService.save(sfzzmbaj);
		} else {
			sfzzmbaj.setAdjunctPath(sfzzmUrl);
			sfzzmbaj.setUpdateTime(now);
			bwAdjunctService.update(sfzzmbaj);
		}
		// 身份证反面
		BwAdjunct sfzfmbaj = new BwAdjunct();
		sfzfmbaj.setAdjunctType(2);
		sfzfmbaj.setOrderId(bwOrderRong.getOrderId());
		sfzfmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzfmbaj);
		if (CommUtils.isNull(sfzfmbaj)) {
			sfzfmbaj = new BwAdjunct();
			sfzfmbaj.setAdjunctType(2);
			sfzfmbaj.setAdjunctPath(sfzfmUrl);
			sfzfmbaj.setOrderId(bwOrderRong.getOrderId());
			sfzfmbaj.setBorrowerId(bwOrder.getBorrowerId());
			sfzfmbaj.setCreateTime(now);
			bwAdjunctService.save(sfzfmbaj);
		} else {
			sfzfmbaj.setAdjunctPath(sfzfmUrl);
			sfzfmbaj.setUpdateTime(now);
			bwAdjunctService.update(sfzfmbaj);
		}
		// 亲属联系人
		String relationName = bizData.getString("emergency_contact_personA_name");
		String relationPhone = bizData.getString("emergency_contact_personA_phone");
		String unrelationName = bizData.getString("emergency_contact_personB_name");
		String unrelationPhone = bizData.getString("emergency_contact_personB_phone");
		BwPersonInfo bpi = bwPersonInfoService.findBwPersonInfoByOrderId(bwOrderRong.getOrderId());
		if (CommUtils.isNull(bpi)) {
			// 添加
			bpi = new BwPersonInfo();
			bpi.setOrderId(bwOrderRong.getOrderId());
			if (haveCar.equals("1")) {
				bpi.setCarStatus(1);
			} else {
				bpi.setCarStatus(0);
			}
			if (haveHouse.equals("1")) {
				bpi.setHouseStatus(1);
			} else {
				bpi.setHouseStatus(0);
			}
			if (haveMarry.equals("1")) {
				// 1.表示未婚
				bpi.setMarryStatus(0);
			} else {
				bpi.setMarryStatus(1);
			}
			bpi.setOrderId(bwOrderRong.getOrderId());
			bpi.setRelationName(CommUtils.isNull(relationName)? "":relationName);
			bpi.setRelationPhone(CommUtils.isNull(relationPhone)? "":relationPhone);
			bpi.setUnrelationName(CommUtils.isNull(unrelationName)? "":unrelationName);
			bpi.setUnrelationPhone(CommUtils.isNull(unrelationPhone)? "":unrelationPhone);
			bpi.setUpdateTime(now);
			bpi.setAddress(addr);
			bpi.setCityName(cityName);
			bwPersonInfoService.save(bpi);
		} else {
			// 更新
			bpi.setOrderId(bwOrderRong.getOrderId());
			if (haveCar.equals("1")) {
				bpi.setCarStatus(1);
			} else {
				bpi.setCarStatus(0);
			}
			if (haveHouse.equals("1")) {
				bpi.setHouseStatus(1);
			} else {
				bpi.setHouseStatus(0);
			}
			if (haveMarry.equals("1")) {
				// 1.表示未婚
				bpi.setMarryStatus(0);
			} else {
				bpi.setMarryStatus(1);
			}
			bpi.setAddress(addr);
			bpi.setOrderId(bwOrderRong.getOrderId());
			bpi.setRelationName(relationName);
			bpi.setRelationPhone(relationPhone);
			bpi.setUnrelationName(unrelationName);
			bpi.setUnrelationPhone(unrelationPhone);
			bpi.setUpdateTime(now);
			bpi.setCityName(cityName);
			bwPersonInfoService.update(bpi);
		}

		// 修改认证状态为4
		logger.info("将借款人" + bwOrder.getBorrowerId() + "的认证状态修改为4");
		BwBorrower borrower = new BwBorrower();
		borrower.setId(bwOrder.getBorrowerId());
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		borrower.setAuthStep(4);
		bwBorrowerService.updateBwBorrower(borrower);
		// 修改工单
		logger.info("将工单" + bwOrder.getBorrowerId() + "的认证状态修改为2");
		bwOrder.setStatusId(2l);
		bwOrderService.updateBwOrder(bwOrder);
		// 将待审核的信息放入Redis中
		logger.info("开始存入redis");
		SystemAuditDto systemAuditDto = new SystemAuditDto();
		systemAuditDto.setIncludeAddressBook(1);
		systemAuditDto.setOrderId(bwOrder.getId());
		systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
		systemAuditDto.setCreateTime(now);
		systemAuditDto.setName(borrower.getName());
		systemAuditDto.setPhone(borrower.getPhone());
		systemAuditDto.setIdCard(borrower.getIdCard());
		systemAuditDto.setChannel(11);
		RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
				JsonUtils.toJson(systemAuditDto));
		logger.info("存入redis结束");
		return 1;
	}

	@Override
	public int saveNew(String biz_data) throws Exception {
		Date now = new Date();
		JSONObject bizData = JSONObject.fromObject(biz_data);
		String orderNo = bizData.getString("order_no");
		logger.info("融360推单号为："+orderNo);
		// 根据订单号获取工单号
		BwOrderRong bwOrderRong = bwOrderRongService.getByOrderNo(orderNo);
		if (CommUtils.isNull(bwOrderRong)) {
			return -1;
		}
		logger.info("根据订单号：" + orderNo + "查询到的工单号为：" + bwOrderRong.getOrderId());
		// 根据工单号获取借款人id
		BwOrder bwOrder = new BwOrder();
		bwOrder.setId(bwOrderRong.getOrderId());
		bwOrder = bwOrderService.findBwOrderByAttr(bwOrder);
		Long borrowerId = bwOrder.getBorrowerId();
		logger.info("根据工单单号：" + bwOrderRong.getOrderId() + "查询到的借款人id为：" + borrowerId);
		// 图片信息
		JSONArray ID_Positive = bizData.getJSONArray("ID_Positive");
		JSONArray ID_Negative = bizData.getJSONArray("ID_Negative");
		JSONArray photo_hand_ID = bizData.getJSONArray("photo_hand_ID");
		String sfzzmUrl = ID_Positive.getString(0);
		String sfzfmUrl = ID_Negative.getString(0);
		String czzUrl = photo_hand_ID.getString(0);
		if (CommUtils.isNull(sfzzmUrl)) {
			return -1;
		}
		if (CommUtils.isNull(sfzfmUrl)) {
			return -1;
		}
		if (CommUtils.isNull(czzUrl)) {
			return -1;
		}
		sfzzmUrl = UploadToCssUtils.urlUpload(sfzzmUrl);
		sfzfmUrl = UploadToCssUtils.urlUpload(sfzfmUrl);
		czzUrl = UploadToCssUtils.urlUpload(czzUrl);
		logger.info("当前工单号："+bwOrderRong.getOrderId()+"的身份证正面url："+sfzzmUrl);
		logger.info("当前工单号："+bwOrderRong.getOrderId()+"的身份证反面url："+sfzfmUrl);
		logger.info("当前工单号："+bwOrderRong.getOrderId()+"的身份证持证照url："+czzUrl);
		String comName = bizData.getString("company_name"); // 公司名称
		String addr = bizData.getString("addr_detail"); // 居住地址
		String cityName = "";
		if (addr.contains("省")) {
			 cityName = addr.split(" ")[0]+addr.split(" ")[1]; // 城市
		}else {
			 cityName = addr.split(" ")[0]; // 城市
		}
		String haveCar = bizData.getString("havecar");
		String haveHouse = bizData.getString("havehouse");
		String haveMarry = bizData.getString("user_marriage");
		// 更新公司信息
		BwWorkInfo bwi = new BwWorkInfo();
		bwi.setOrderId(bwOrderRong.getOrderId());
		bwi = bwWorkInfoService.findBwWorkInfoByAttr(bwi);
		bwi.setComName(comName);
		bwi.setUpdateTime(new Date());
		bwWorkInfoService.update(bwi);
		// 持证照
		BwAdjunct czzbaj = new BwAdjunct();
		czzbaj.setAdjunctType(3);
		czzbaj.setOrderId(bwOrderRong.getOrderId());
		czzbaj = bwAdjunctService.findBwAdjunctByAttr(czzbaj);
		if (CommUtils.isNull(czzbaj)) {
			czzbaj = new BwAdjunct();
			czzbaj.setAdjunctType(3);
			czzbaj.setAdjunctPath(czzUrl);
			czzbaj.setOrderId(bwOrderRong.getOrderId());
			czzbaj.setBorrowerId(bwOrder.getBorrowerId());
			czzbaj.setCreateTime(new Date());
			bwAdjunctService.save(czzbaj);
		} else {
			czzbaj.setAdjunctPath(czzUrl);
			czzbaj.setUpdateTime(now);
			bwAdjunctService.update(czzbaj);
		}
		// 身份证正面
		BwAdjunct sfzzmbaj = new BwAdjunct();
		sfzzmbaj.setAdjunctType(1);
		sfzzmbaj.setOrderId(bwOrderRong.getOrderId());
		sfzzmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzzmbaj);
		if (CommUtils.isNull(sfzzmbaj)) {
			sfzzmbaj = new BwAdjunct();
			sfzzmbaj.setAdjunctType(1);
			sfzzmbaj.setAdjunctPath(sfzzmUrl);
			sfzzmbaj.setOrderId(bwOrderRong.getOrderId());
			sfzzmbaj.setBorrowerId(bwOrder.getBorrowerId());
			sfzzmbaj.setCreateTime(now);
			bwAdjunctService.save(sfzzmbaj);
		} else {
			sfzzmbaj.setAdjunctPath(sfzzmUrl);
			sfzzmbaj.setUpdateTime(now);
			bwAdjunctService.update(sfzzmbaj);
		}
		// 身份证反面
		BwAdjunct sfzfmbaj = new BwAdjunct();
		sfzfmbaj.setAdjunctType(2);
		sfzfmbaj.setOrderId(bwOrderRong.getOrderId());
		sfzfmbaj = bwAdjunctService.findBwAdjunctByAttr(sfzfmbaj);
		if (CommUtils.isNull(sfzfmbaj)) {
			sfzfmbaj = new BwAdjunct();
			sfzfmbaj.setAdjunctType(2);
			sfzfmbaj.setAdjunctPath(sfzfmUrl);
			sfzfmbaj.setOrderId(bwOrderRong.getOrderId());
			sfzfmbaj.setBorrowerId(bwOrder.getBorrowerId());
			sfzfmbaj.setCreateTime(now);
			bwAdjunctService.save(sfzfmbaj);
		} else {
			sfzfmbaj.setAdjunctPath(sfzfmUrl);
			sfzfmbaj.setUpdateTime(now);
			bwAdjunctService.update(sfzfmbaj);
		}
		// 亲属联系人
		String relationName = bizData.getString("emergency_contact_personA_name");
		String relationPhone = bizData.getString("emergency_contact_personA_phone");
		String unrelationName = bizData.getString("emergency_contact_personB_name");
		String unrelationPhone = bizData.getString("emergency_contact_personB_phone");
		BwPersonInfo bpi = bwPersonInfoService.findBwPersonInfoByOrderId(bwOrderRong.getOrderId());
		if (CommUtils.isNull(bpi)) {
			// 添加
			bpi = new BwPersonInfo();
			bpi.setOrderId(bwOrderRong.getOrderId());
			if (haveCar.equals("1")) {
				bpi.setCarStatus(1);
			} else {
				bpi.setCarStatus(0);
			}
			if (haveHouse.equals("1")) {
				bpi.setHouseStatus(1);
			} else {
				bpi.setHouseStatus(0);
			}
			if (haveMarry.equals("1")) {
				// 1.表示未婚
				bpi.setMarryStatus(0);
			} else {
				bpi.setMarryStatus(1);
			}
			bpi.setOrderId(bwOrderRong.getOrderId());
			bpi.setRelationName(CommUtils.isNull(relationName)? "":relationName);
			bpi.setRelationPhone(CommUtils.isNull(relationPhone)? "":relationPhone);
			bpi.setUnrelationName(CommUtils.isNull(unrelationName)? "":unrelationName);
			bpi.setUnrelationPhone(CommUtils.isNull(unrelationPhone)? "":unrelationPhone);
			bpi.setUpdateTime(now);
			bpi.setAddress(addr);
			bpi.setCityName(cityName);
			bwPersonInfoService.save(bpi);
		} else {
			// 更新
			bpi.setOrderId(bwOrderRong.getOrderId());
			if (haveCar.equals("1")) {
				bpi.setCarStatus(1);
			} else {
				bpi.setCarStatus(0);
			}
			if (haveHouse.equals("1")) {
				bpi.setHouseStatus(1);
			} else {
				bpi.setHouseStatus(0);
			}
			if (haveMarry.equals("1")) {
				// 1.表示未婚
				bpi.setMarryStatus(0);
			} else {
				bpi.setMarryStatus(1);
			}
			bpi.setAddress(addr);
			bpi.setOrderId(bwOrderRong.getOrderId());
			bpi.setRelationName(relationName);
			bpi.setRelationPhone(relationPhone);
			bpi.setUnrelationName(unrelationName);
			bpi.setUnrelationPhone(unrelationPhone);
			bpi.setUpdateTime(now);
			bpi.setCityName(cityName);
			bwPersonInfoService.update(bpi);
		}

		// 修改认证状态为4
		logger.info("将借款人" + bwOrder.getBorrowerId() + "的认证状态修改为4");
		BwBorrower borrower = new BwBorrower();
		borrower.setId(bwOrder.getBorrowerId());
		borrower = bwBorrowerService.findBwBorrowerByAttr(borrower);
		borrower.setAuthStep(4);
		bwBorrowerService.updateBwBorrower(borrower);
		// 修改工单
		logger.info("将工单" + bwOrder.getBorrowerId() + "的认证状态修改为2");
		bwOrder.setStatusId(2l);
		bwOrderService.updateBwOrder(bwOrder);
		// 将待审核的信息放入Redis中
		logger.info("开始存入redis");
		SystemAuditDto systemAuditDto = new SystemAuditDto();
		systemAuditDto.setIncludeAddressBook(1);
		systemAuditDto.setOrderId(bwOrder.getId());
		systemAuditDto.setBorrowerId(bwOrder.getBorrowerId());
		systemAuditDto.setCreateTime(now);
		systemAuditDto.setName(borrower.getName());
		systemAuditDto.setPhone(borrower.getPhone());
		systemAuditDto.setIdCard(borrower.getIdCard());
		systemAuditDto.setChannel(11);
		RedisUtils.hset(SystemConstant.AUDIT_KEY, systemAuditDto.getOrderId().toString(),
				JsonUtils.toJson(systemAuditDto));
		logger.info("存入redis结束");
		return 1;
	}

}
