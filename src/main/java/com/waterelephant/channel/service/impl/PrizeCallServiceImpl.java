package com.waterelephant.channel.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.waterelephant.channel.entity.ActivityDrawRecord;
import com.waterelephant.channel.service.ActivityDrawRecordService;
import com.waterelephant.channel.service.PrizeCallService;
import com.waterelephant.channel.service.PrizeService;
import com.waterelephant.entity.ActivityDiscountDistribute;
import com.waterelephant.entity.ActivityDiscountInfo;
import com.waterelephant.entity.ActivityInfo;
import com.waterelephant.service.ActivityDiscountDistributeService;
import com.waterelephant.service.ActivityInfoService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;

/**
 * 调用prizeService
 * 
 * 
 * Module:
 * 
 * PrizeCallServiceImpl.java
 * 
 * @author 毛汇源
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */

@Service
public class PrizeCallServiceImpl implements PrizeCallService {

	private Logger logger = Logger.getLogger(PrizeCallServiceImpl.class);

	@Autowired
	private PrizeService prizeService;

	@Autowired
	private ActivityInfoService activityInfoService;

	@Autowired
	private ActivityDiscountDistributeService activityDiscountDistributeService;

	@Autowired
	private ActivityDrawRecordService activityDrawRecordService;

	/**
	 * 进入抽奖页面
	 */
	@Override
	public AppResponseResult saveIntoPrizeView(Map<String, Object> params) {
		AppResponseResult result = new AppResponseResult();
		Map<String, Object> map = new HashMap<>();
		try {
			// 查询活动详情
			ActivityInfo activityInfo = new ActivityInfo();
			activityInfo.setActivityType("3");
			activityInfo = activityInfoService.queryActivityInfo(activityInfo);
			map.put("activityInfo", activityInfo);
			// 记录pv
			if (CommUtils.isNull(activityInfo)) {
				result.setCode("502");
				result.setMsg("该活动已失效");
			}
			int activity_id = activityInfo.getActivityId();
			String borrower_id = (String) params.get("borrower_id");
			String ip = (String) params.get("ip");
			// 插入访问页面记录表
			prizeService.savePvRecord(ip, activity_id);
			// 查询奖品列表
			List<ActivityDiscountInfo> prizeList = prizeService.getPrizeList(activity_id);
			map.put("prizeList", prizeList);
			// 查询中奖记录
			// List<Map<String, Object>> prizeRecord = prizeService.getPrizeRecordList(activity_id);
			// for (int i = 0; i < prizeRecord.size(); i++) {
			// String winning_phone = (String) prizeRecord.get(i).get("phone");
			// winning_phone = winning_phone.substring(0, 3) + "****" + winning_phone.substring(7);
			// prizeRecord.get(i).put("phone", winning_phone);
			// }
			// map.put("prizeRecord", prizeRecord);
			// 查询此人的上次抽中实物填写的联系人和联系电话
			// Map<String, Object> contactInfo = new HashMap<>();//不填写手机号和联系人了
			if (!CommUtils.isNull(borrower_id)) {
				// contactInfo = prizeService.getContactInfo(borrower_id, activity_id);
				int draw_chance = 0;
				if (RedisUtils.hexists("activity:chance:count", borrower_id)) {
					draw_chance = Integer.parseInt(RedisUtils.hget("activity:chance:count", borrower_id));
				}
				map.put("draw_chance", draw_chance);
				// 根据用户id查询用户手机号
				String phone = prizeService.getPhoneByBorrowerId(borrower_id);
				map.put("phone", phone.substring(0, 3) + "****" + phone.substring(7));
			}
			// map.put("contactInfo", contactInfo);
			// 查询剩余的抽奖机会
			result.setCode("000");
			result.setMsg("请求成功");
			result.setResult(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("进入活动页面错误,e=" + e.getMessage());
			result.setCode("111");
			result.setMsg("系统错误");
		}
		return result;
	}

	/**
	 * 抽奖
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public AppResponseResult saveLuckDraw(Map<String, Object> params) {
		AppResponseResult result = new AppResponseResult();
		Map<String, Object> resultMap = new HashMap<>();
		try {
			String borrower_id = (String) params.get("borrower_id");// request.getParameter("borrower_id");
			if (CommUtils.isNull(borrower_id)) {
				result.setCode("501");
				result.setMsg("参数错误");
				return result;
			}
			// 先判断是否在活动时间内
			ActivityInfo activity = new ActivityInfo();
			activity.setActivityType("3");// 抽奖
			activity.setStatus(1);// 是否启用活动（0、否，1、是）
			activity = activityInfoService.queryActivityInfo(activity);
			if (!CommUtils.isNull(activity)) {
				int activityId = activity.getActivityId();
				Long start_time = activity.getStartTime().getTime();
				Long end_time = activity.getEndTime().getTime();
				Long cur_time = new Date().getTime();
				if (start_time <= cur_time && cur_time <= end_time) {// 判断是否在活动时间内
					// 查询剩余的抽奖机会
					int draw_chance = 0;
					if (RedisUtils.hexists("activity:chance:count", borrower_id)) {
						draw_chance = Integer.parseInt(RedisUtils.hget("activity:chance:count", borrower_id));
					}
					if (draw_chance > 0) {
						// 查询抽奖次数,到达20次以后用户再继续翻牌，前端会跳出提示：您今天的翻牌机会已达上限~请明日再来哦
						int draw_count = prizeService.getDrawCount(borrower_id);
						if (draw_count < 20) {
							ActivityDiscountInfo drawResult = new ActivityDiscountInfo();// 抽奖结果
							ActivityDiscountInfo drawResultDefault = new ActivityDiscountInfo();// 谢谢参与
							// 查询是否开启每天至少中一次免息功能 0不开启，1开启。如果开启，那么第一次必中免息券
							ActivityDiscountInfo mxActivityDiscountInfo = prizeService.getMianXiDiscountInfo();
							if (!CommUtils.isNull(mxActivityDiscountInfo) && mxActivityDiscountInfo.getIsOpen() > 0
									&& draw_count == 0) {
								drawResult = mxActivityDiscountInfo;
							} else {
								// 查询该用户之前提交过审核的订单数，实物只有已提交认证资料并完成借款申请的这部分用户才可以抽到
								int apply_count = prizeService.getApplyCount(borrower_id);
								// 抽奖、
								// 查询所有奖品列表
								List<ActivityDiscountInfo> discountInfos = new ArrayList<>();
								if (apply_count > 0) {
									// 查询所有奖品列表
									discountInfos = prizeService.getPrizeListAll(activityId);
								} else {
									// 查询优惠券的奖品列表
									discountInfos = prizeService.getPrizeListCoupon(activityId);
								}
								// 总的概率区间
								double totalPro = 0;
								// 存储每个奖品新的概率区间
								List<Double> proSection = new ArrayList<Double>();
								proSection.add((double) 0);
								// 遍历每个奖品，设置概率区间，总的概率区间为每个概率区间的总和
								for (ActivityDiscountInfo disInfo : discountInfos) {
									// 每个概率区间为奖品概率乘以1000（把三位小数转换为整）
									totalPro += disInfo.getProbability() * 1000;
									proSection.add(totalPro);
									if (disInfo.getType() == 3) {// 奖品类型 1优惠券；2实物；3未中奖
										drawResultDefault = disInfo;
									}
								}
								// 获取总的概率区间中的随机数
								Random random = new Random();
								float randomPro = random.nextInt((int) totalPro);
								// 判断取到的随机数在哪个奖品的概率区间中
								for (int i = 0, size = proSection.size(); i < size; i++) {
									if (randomPro >= proSection.get(i) && randomPro < proSection.get(i + 1)) {
										drawResult = discountInfos.get(i);
									}
								}
								// 抽中实物，如果实物数量为0，返回谢谢参与
								if (!CommUtils.isNull(drawResult.getType()) && drawResult.getType() == 2) {
									if (drawResult.getPrizeSurplus() <= 0) {// 实物券数量为0
										drawResult = drawResultDefault;
									} else {
										// 修改实物数量
										prizeService.updatePrizeCount(drawResult.getDiscountId());
									}
								}
							}
							// 抽奖机会-1
							RedisUtils.hset("activity:chance:count", borrower_id, String.valueOf(draw_chance - 1));
							// 如果是优惠券，加入活动优惠派发表
							if (drawResult.getType() == 1 || drawResult.getType() == 4) {// 现金券和免息券
								ActivityDiscountDistribute activityDiscountDistribute = new ActivityDiscountDistribute();
								activityDiscountDistribute.setBorrowId(Integer.parseInt(borrower_id));
								activityDiscountDistribute.setActivityId(activityId);
								activityDiscountDistribute.setDiscountId(drawResult.getDiscountId());
								if (drawResult.getType() == 1) {
									activityDiscountDistribute.setDistributeType("3");// 抽奖
								} else {
									activityDiscountDistribute.setDistributeType("4");// 免息，派发类型(1、新手券，2.邀请券，3、抽奖，4、免息券，5、现金派发)
								}
								activityDiscountDistribute.setNumber(1);
								activityDiscountDistribute.setAmount(drawResult.getBonusAmount());
								activityDiscountDistribute.setLoanAmount(drawResult.getLoanAmount());
								activityDiscountDistribute.setEffective(1);
								// activityDiscountDistribute.setItemName();
								activityDiscountDistribute.setCreateTime(new Date());
								Calendar c = Calendar.getInstance();
								c.add(Calendar.DAY_OF_MONTH, 60);
								activityDiscountDistribute.setExpiryTime(c.getTime());
								activityDiscountDistribute.setUseNumber(0);
								activityDiscountDistribute.setTotalNumber(1);
								activityDiscountDistributeService
										.addActivityDiscountDistribute(activityDiscountDistribute);
							}
							// 加入抽奖记录
							// Map<String, Object> recordMap = new HashMap<>();
							// recordMap.put("borrower_id", borrower_id);
							// if (drawResult.getType() == 3) {
							// recordMap.put("is_winning", 0);// 是否中奖 0未中奖；1中奖
							// } else {
							// recordMap.put("is_winning", 1);// 是否中奖 0未中奖；1中奖
							// }
							// recordMap.put("activity_id", activity.getActivityId());
							// recordMap.put("prize_id", drawResult.getDiscountId());
							// if (drawResult.getType() == 1) {// 奖品类型 1优惠券；2实物；3未中奖
							// recordMap.put("grant_status", 1);// 发放状态 0未发放；1已发放
							// } else {
							// recordMap.put("grant_status", 0);// 发放状态 0未发放；1已发放
							// }
							// prizeService.saveRrawRecord(recordMap);
							ActivityDrawRecord activityDrawRecord = new ActivityDrawRecord();
							activityDrawRecord.setBorrowerId(Integer.parseInt(borrower_id));
							if (drawResult.getType() == 3) {
								activityDrawRecord.setIsWinning(0);// 是否中奖 0未中奖；1中奖
							} else {
								activityDrawRecord.setIsWinning(1);// 是否中奖 0未中奖；1中奖
							}
							activityDrawRecord.setActivityId(activity.getActivityId());
							activityDrawRecord.setPrizeId(drawResult.getDiscountId());
							if (drawResult.getType() == 1 || drawResult.getType() == 4) {
								// 奖品类型 1优惠券；2实物；3未中奖；4免息券
								activityDrawRecord.setGrantStatus(1);// 发放状态 0未发放；1已发放
							} else {
								activityDrawRecord.setGrantStatus(0);// 发放状态 0未发放；1已发放
							}
							activityDrawRecord.setCreateTime(new Date());
							activityDrawRecordService.saveActivityDrawRecord(activityDrawRecord);
							resultMap.put("prizeName", drawResult.getPrizeName());
							resultMap.put("img", drawResult.getImg());
							resultMap.put("winning_type", drawResult.getType());// 1优惠券；2实物；3未中奖；4免息券；5虚拟实物
							resultMap.put("winning_id", activityDrawRecord.getId());
							result.setCode("000");
							result.setMsg("抽奖成功");
							result.setResult(resultMap);
						} else {
							result.setCode("505");
							result.setMsg("您今天的翻牌机会已达上限~请明日再来哦");
						}
					} else {
						result.setCode("504");
						result.setMsg("您还没有抽奖机会");
					}

				} else {
					result.setCode("503");
					result.setMsg("该活动时间已过");
				}
			} else {
				result.setCode("502");
				result.setMsg("该活动已失效");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("抽奖错误,e=" + e.getMessage());
			result.setCode("111");
			result.setMsg("系统错误");
		}
		Gson gson = new Gson();
		System.out.println("抽奖结果:" + gson.toJson(result));
		return result;
	}

	/**
	 * 填写联系人和电话
	 */
	@Override
	public AppResponseResult updateContacts(Map<String, Object> params) {
		AppResponseResult result = new AppResponseResult();
		try {
			String id = (String) params.get("id");// request.getParameter("id");// 抽奖记录表id
			String contacts_name = (String) params.get("contacts_name");// request.getParameter("contacts_name");
			String contacts_phone = (String) params.get("contacts_phone");// request.getParameter("contacts_phone");
			if (CommUtils.isNull(id) || CommUtils.isNull(contacts_name) || CommUtils.isNull(contacts_phone)) {
				result.setCode("501");
				result.setMsg("参数错误");
				return result;
			}
			ActivityDrawRecord activityDrawRecord = new ActivityDrawRecord();
			activityDrawRecord.setId(Integer.parseInt(id));
			activityDrawRecord.setContactsName(contacts_name);
			activityDrawRecord.setContactsPhone(contacts_phone);
			int count = activityDrawRecordService.updateActivityDrawRecord(activityDrawRecord);
			if (count > 0) {
				result.setCode("000");
				result.setMsg("编辑成功");
			} else {
				result.setCode("502");
				result.setMsg("编辑失败");
			}
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("参数错误");
			e.printStackTrace();
			logger.error("填写联系人和电话,e=" + e.getMessage());
		}
		return result;
	}

	/**
	 * 查询我的中奖记录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public AppResponseResult getMyWinningRecord(Map<String, Object> params) {
		AppResponseResult result = new AppResponseResult();
		try {
			String borrower_id = (String) params.get("borrower_id");// request.getParameter("borrower_id");
			params.put("row", (Integer.parseInt((String) params.get("page")) - 1)
					* Integer.parseInt((String) params.get("show_count")));
			if (CommUtils.isNull(borrower_id)) {
				result.setCode("501");
				result.setMsg("参数错误");
				return result;
			}
			// ActivityDrawRecord activityDrawRecord = new ActivityDrawRecord();
			// activityDrawRecord.setBorrowerId(Integer.parseInt(borrower_id));
			// activityDrawRecord.setIsWinning(1);
			List<Map<String, Object>> list = new ArrayList<>();
			list = activityDrawRecordService.getMyWinningRecord(params);
			result.setCode("000");
			result.setMsg("请求成功");
			result.setResult(list);
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("参数错误");
			e.printStackTrace();
			logger.error("查询我的中奖记录,e=" + e.getMessage());
		}
		return result;
	}

	/**
	 * 确认收货
	 * 
	 * @see com.waterelephant.channel.service.PrizeCallService#updateConfirmReceipt(java.lang.String)
	 */
	@Override
	public int updateConfirmReceipt(ActivityDrawRecord activityDrawRecord) {
		return activityDrawRecordService.updateActivityDrawRecord(activityDrawRecord);
	}

}
