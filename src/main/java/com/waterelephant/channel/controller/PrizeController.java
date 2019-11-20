package com.waterelephant.channel.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.channel.entity.ActivityDrawRecord;
import com.waterelephant.channel.service.PrizeCallService;
import com.waterelephant.service.ActivityInfoService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.RedisUtils;

/**
 * 抽奖活动controller
 * 
 * 
 * Module:
 * 
 * PrizeController.java
 * 
 * @author 毛汇源
 * @since JDK 1.8
 * @version 1.0
 * @description: <描述>
 */

@Controller
@RequestMapping(value = "/prize")
public class PrizeController {

	private Logger logger = Logger.getLogger(PrizeController.class);

	@Autowired
	private PrizeCallService prizeCallService;

	@Autowired
	private ActivityInfoService activityInfoService;

	/**
	 * 分享成功调用接口
	 */
	@RequestMapping(value = "shareSuccess")
	@ResponseBody
	public AppResponseResult shareSuccess(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String type = request.getParameter("type");// 1:朋友圈，2:微博
			String borrower_id = request.getParameter("borrower_id");
			if (CommUtils.isNull(type) || CommUtils.isNull(borrower_id)) {
				result.setCode("501");
				result.setMsg("参数错误");
				return result;
			}
			// 分享次数+1
			int share_count = 0;
			if (RedisUtils.exists("activity:share:count")) {
				share_count = Integer.parseInt(RedisUtils.get("activity:share:count")) + 1;
			}
			RedisUtils.set("activity:share:count", String.valueOf(share_count));
			// ===
			int invite_count = 0;
			if (RedisUtils.hexists("activity:invite:count", borrower_id)) {
				invite_count = Integer.parseInt(RedisUtils.hget("activity:invite:count", borrower_id));
			}
			if (invite_count < 20) {
				if (type.equals("1")) {
					if (!RedisUtils.hexists("activity:weixinInvite:count", borrower_id)) {
						RedisUtils.hset("activity:weixinInvite:count", borrower_id, "1");// 朋友圈一天分享一次
						// 给分享和邀请的总数加一,用于限制每天的20次
						RedisUtils.hset("activity:invite:count", borrower_id, String.valueOf(invite_count + 1));
						// 加抽奖机会
						int chance_count = 0;
						if (RedisUtils.hexists("activity:chance:count", borrower_id)) {
							chance_count = Integer.parseInt(RedisUtils.hget("activity:chance:count", borrower_id));
						}
						RedisUtils.hset("activity:chance:count", borrower_id, String.valueOf(chance_count + 1));
					}
				} else if (type.equals("2")) {
					if (!RedisUtils.hexists("activity:weiboInvite:count", borrower_id)) {
						RedisUtils.hset("activity:weiboInvite:count", borrower_id, "1");// 微博一天分享一次
						// 给分享和邀请的总数加一,用于限制每天的20次
						RedisUtils.hset("activity:invite:count", borrower_id, String.valueOf(invite_count + 1));
						// 加抽奖机会
						int chance_count = 0;
						if (RedisUtils.hexists("activity:chance:count", borrower_id)) {
							chance_count = Integer.parseInt(RedisUtils.hget("activity:chance:count", borrower_id));
						}
						RedisUtils.hset("activity:chance:count", borrower_id, String.valueOf(chance_count + 1));
					}
				}
				result.setCode("000");
				result.setMsg("分享成功");
			} else {
				result.setCode("000");
				result.setMsg("分享成功");// 分享和邀请已达到上限
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分享加机会错误;e=" + e.getMessage());
			result.setCode("111");
			result.setMsg("系统错误");
		}
		return result;
	}

	/**
	 * 进入抽奖页面
	 */
	@RequestMapping(value = "intoPrizeView")
	@ResponseBody
	public AppResponseResult intoPrizeView(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		Map<String, Object> params = new HashMap<>();
		try {
			String borrower_id = request.getParameter("borrower_id");
			params.put("borrower_id", borrower_id);
			String ip = getIpAddr(request);
			params.put("ip", ip);
			params.put("borrower_id", borrower_id);
			result = prizeCallService.saveIntoPrizeView(params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("进入活动页面错误,e=" + e.getMessage());
			result.setCode("111");
			result.setMsg("系统错误");
		}
		return result;
	}

	/**
	 * 获取访问者IP
	 * 
	 * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
	 * 
	 * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)， 如果还不存在则调用Request .getRemoteAddr()。
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) throws Exception {
		String ip = request.getHeader("X-Real-IP");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			return ip;
		}
		ip = request.getHeader("X-Forwarded-For");
		if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个IP值，第一个为真实IP。
			int index = ip.indexOf(',');
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		} else {
			return request.getRemoteAddr();
		}
	}

	/**
	 * 抽奖
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "luckDraw")
	@ResponseBody
	public AppResponseResult luckDraw(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		// Map<String, Object> resultMap = new HashMap<>();
		Map<String, Object> params = new HashMap<>();
		try {
			String borrower_id = request.getParameter("borrower_id");
			if (CommUtils.isNull(borrower_id)) {
				result.setCode("501");
				result.setMsg("参数错误");
				return result;
			}
			params.put("borrower_id", borrower_id);
			result = prizeCallService.saveLuckDraw(params);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("抽奖错误,e=" + e.getMessage());
			result.setCode("111");
			result.setMsg("系统错误");
		}
		return result;
	}

	/**
	 * 填写联系人和电话
	 */
	@RequestMapping(value = "editContacts")
	@ResponseBody
	public AppResponseResult editContacts(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		Map<String, Object> params = new HashMap<>();
		try {
			String id = request.getParameter("id");// 抽奖记录表id
			String contacts_name = request.getParameter("contacts_name");
			String contacts_phone = request.getParameter("contacts_phone");
			if (CommUtils.isNull(id) || CommUtils.isNull(contacts_name) || CommUtils.isNull(contacts_phone)) {
				result.setCode("501");
				result.setMsg("参数错误");
				return result;
			}
			params.put("id", id);
			params.put("contacts_name", contacts_name);
			params.put("contacts_phone", contacts_phone);
			result = prizeCallService.updateContacts(params);
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统错误");
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
	@RequestMapping(value = "getMyWinningRecord")
	@ResponseBody
	public AppResponseResult getMyWinningRecord(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		Map<String, Object> params = new HashMap<>();
		try {
			String borrower_id = request.getParameter("borrower_id");
			String page = request.getParameter("page");// 页码
			String show_count = request.getParameter("show_count");// 每页显示条数
			if (CommUtils.isNull(borrower_id)) {
				result.setCode("501");
				result.setMsg("参数错误");
				return result;
			}
			if (CommUtils.isNull(page)) {// 默认为1
				page = "1";
			}
			if (CommUtils.isNull(show_count)) {// 默认为10
				show_count = "10";
			}
			params.put("borrower_id", borrower_id);
			params.put("page", page);
			params.put("show_count", show_count);
			result = prizeCallService.getMyWinningRecord(params);
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统错误");
			e.printStackTrace();
			logger.error("查询我的中奖记录,e=" + e.getMessage());
		}
		return result;
	}

	/**
	 * 确认收货
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "confirmReceipt")
	@ResponseBody
	public AppResponseResult confirmReceipt(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String id = request.getParameter("id");
			if (CommUtils.isNull(id)) {
				result.setCode("501");
				result.setMsg("参数错误");
				return result;
			}
			ActivityDrawRecord activityDrawRecord = new ActivityDrawRecord();
			activityDrawRecord.setId(Integer.parseInt(id));
			activityDrawRecord.setGrantStatus(2);// 发放状态 0未发放；1已发放；2确认收货
			activityDrawRecord.setUpdateTime(new Date());
			int count = prizeCallService.updateConfirmReceipt(activityDrawRecord);
			if (count > 0) {
				result.setCode("000");
				result.setMsg("确认收货成功");
			} else {
				result.setCode("502");
				result.setMsg("确认收货失败");
			}
		} catch (Exception e) {
			result.setCode("111");
			result.setMsg("系统错误");
			e.printStackTrace();
			logger.error("确认收货错误;e=" + e.getMessage());
		}
		return result;
	}

}
