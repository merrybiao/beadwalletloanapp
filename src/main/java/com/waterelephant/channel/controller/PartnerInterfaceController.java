package com.waterelephant.channel.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.channel.entity.BwNoticeRecord;
import com.waterelephant.channel.service.CmsContentChannelService;
import com.waterelephant.channel.service.PartnerService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.entity.CmsContent;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.SystemConstant;

@Controller
@RequestMapping(value = "/partnerInterface")
public class PartnerInterfaceController {

	private Logger logger = Logger.getLogger(PartnerInterfaceController.class);

	@Autowired
	private PartnerService partnerService;

	@Autowired
	private CmsContentChannelService cmsContentService;

	/**
	 * 合作伙伴查询接口
	 */
	@RequestMapping(value = "getPartnerList.do")
	@ResponseBody
	public AppResponseResult getPartnerInfo(HttpServletRequest request, HttpServletResponse httpServletResponse) {
		AppResponseResult result = new AppResponseResult();
		try {
			List<Map<String, Object>> partner = new ArrayList<Map<String, Object>>();
			String pageNo = request.getParameter("pageNo");// 页码
			partner = partnerService.getPartnerList(pageNo);
			result.setResult(partner);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("合作伙伴查询接口：getPartnerInfo", e);
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
		}
		return result;
	}

	/**
	 * 栏目内容查询接口
	 */
	@RequestMapping(value = "getContentList.do")
	@ResponseBody
	public AppResponseResult getContentList(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String channelId = request.getParameter("channelId");// 栏目id
			String pageSize = request.getParameter("pageSize");// 每页条数
			String pageNo = request.getParameter("pageNo");// 页码
			Map<String, Object> resultMap = new HashMap<String, Object>();
			if (StringUtils.isEmpty(channelId)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg(ActivityConstant.ErrorMsg.PARAM_ERROR);
				return result;
			}
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = "10";
			}
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = "1";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("channelId", channelId);
			map.put("pageSize", Integer.parseInt(pageSize));
			map.put("pageNo", Integer.parseInt(pageNo));
			resultMap = partnerService.getContentList(map);
			result.setResult(resultMap);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("栏目内容查询接口：getContentList", e);
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
		}
		return result;
	}

	/**
	 * 首页栏目内容查询接口
	 */
	@RequestMapping(value = "getIndexContentList.do")
	@ResponseBody
	public AppResponseResult getIndexContentList(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {

			// 首页查询未读公告数量--start
			String borrowerId = request.getParameter("borrower_id");// 用户id
			logger.info("【PartnerInterfaceController.getIndexContentList】borrowerId：" + borrowerId);
			// if (StringUtils.isEmpty(borrowerId)) {
			// resultMap.put("unreadCount", 0);
			// } else {
			// int unreadCount = partnerService.getUnreadCount("69", borrowerId);
			// logger.info("【PartnerInterfaceController.getIndexContentList】borrowerId：" + borrowerId
			// + "，首页查询未读公告数量,栏目ID:" + "69，数量：" + unreadCount);
			// resultMap.put("unreadCount", unreadCount);
			// }
			resultMap.put("unreadCount", 0);
			// --end

			// 查询banner
			List<CmsContent> bannerList = partnerService.getIndexContentList(SystemConstant.CMS_BANNARIMG);
			List<Map<String, Object>> resultBannarList = new ArrayList<Map<String, Object>>();
			if (null != bannerList) {
				for (CmsContent cmsContent : bannerList) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("linkUrl", cmsContent.getUrl());
					map.put("imageUrl", cmsContent.getTitleImg());
					map.put("title", cmsContent.getTitle());
					resultBannarList.add(map);
				}
				resultMap.put("resultBannarList", resultBannarList);
			}
			logger.info("【PartnerInterfaceController.getIndexContentList】borrowerId：" + borrowerId + "，查询banner栏目ID："
					+ SystemConstant.CMS_BANNARIMG + "，数量：" + resultBannarList.size());

			// 查询消息
			List<CmsContent> MsgList = partnerService.getIndexContentList(SystemConstant.CMS_MESSAGE);
			List<String> resultMsgList = new ArrayList<String>();
			if (null != MsgList) {
				for (CmsContent cmsContent : MsgList) {
					resultMsgList.add(cmsContent.getUrl());
				}
				resultMap.put("resultMsgList", resultMsgList);
			}
			logger.info("【PartnerInterfaceController.getIndexContentList】borrowerId：" + borrowerId + "，查询消息栏目ID："
					+ SystemConstant.CMS_MESSAGE + "，数量：" + resultMsgList.size());

			// 查询滚动条
			List<String> resultscrollList = getScrollList();
			resultMap.put("resultscrollList", resultscrollList);
			logger.info("【PartnerInterfaceController.getIndexContentList】borrowerId：" + borrowerId + "，查询滚动条栏目ID："
					+ SystemConstant.CMS_SCROLL + "，数量：" + resultscrollList.size());

			// 查询紧急通知
			/*
			 * List<CmsContent> urgentMsgList = partnerService.getIndexContentList(SystemConstant.CMS_URGENTMESSAGE);
			 * List<String> resulturgentMsgList = new ArrayList<String>(); if (null != urgentMsgList) { for (CmsContent
			 * cmsContent : urgentMsgList) { resulturgentMsgList.add(cmsContent.getUrl()); }
			 * resultMap.put("resulturgentMsgList", resulturgentMsgList); }
			 */
			resultMap.put("resulturgentMsgList", new ArrayList<CmsContent>());
			// logger.info("【PartnerInterfaceController.getIndexContentList】borrowerId：" + borrowerId + "，查询紧急通知栏目ID："
			// + SystemConstant.CMS_URGENTMESSAGE + "，数量：" + resulturgentMsgList.size());
			result.setResult(resultMap);
			result.setCode("000");
			result.setMsg("请求成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("【PartnerInterfaceController.getIndexContentList】系统错误", e);
			result.setCode("111");
			result.setMsg("系统错误");
		}
		return result;
	}

	public static List<String> getScrollList() {
		String[] content = { "1000", "1200", "1500", "2000", "2500", "3000" };
		Random random = new Random();
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < 5; i++) {
			StringBuffer sb = new StringBuffer("尾号");
			int cardNo = random.nextInt(9999 - 1000 + 1) + 1000;
			sb.append(cardNo);
			sb.append("，成功借款");
			String amount = content[random.nextInt(content.length)];
			sb.append(amount);
			sb.append("元！");
			set.add(sb.toString());
		}
		for (int i = 0; i < 5; i++) {
			StringBuffer sb = new StringBuffer("尾号");
			int cardNo = random.nextInt(9999 - 1000 + 1) + 1000;
			sb.append(cardNo);
			sb.append("，正常还款，信用等级又提升了!");
			set.add(sb.toString());
		}
		List<String> sourceList = new ArrayList<String>();
		for (String s : set) {
			sourceList.add(s);
		}
		return sourceList;
	}

	/**
	 * 查询公告列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getNoticeList.do")
	@ResponseBody
	public AppResponseResult getNoticeList(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			String borrower_id = request.getParameter("borrower_id");// 用户id
			// String channelId = request.getParameter("channelId");// 栏目id
			String pageSize = request.getParameter("pageSize");// 每页条数
			String pageNo = request.getParameter("pageNo");// 页码
			logger.info("查询公告列表：getNoticeList===borrower_id：" + borrower_id);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			// if (StringUtils.isEmpty(borrower_id)) {
			// result.setCode(ActivityConstant.ErrorCode.FAIL);
			// result.setMsg("用户Id不能为空");
			// return result;
			// }
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = "10";
			}
			if (StringUtils.isEmpty(pageNo)) {
				pageNo = "1";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("borrower_id", borrower_id);
			map.put("channelId", "69");
			map.put("pageSize", Integer.parseInt(pageSize));
			map.put("pageNo", Integer.parseInt(pageNo));
			resultMap = partnerService.getNoticeList(map);

			// 全部标记为已读 --start
			if (!StringUtils.isEmpty(borrower_id)) {
				// 删除用户所有浏览记录
				List<Map<String, Object>> contentList = (List<Map<String, Object>>) resultMap.get("contentList");
				partnerService.deleteRecord(borrower_id);
				// 全部标记为已读
				BwNoticeRecord newBwNoticeRecord = new BwNoticeRecord();
				newBwNoticeRecord.setBorrowerId(Long.parseLong(borrower_id));
				long id = 0;
				for (int i = 0; i < contentList.size(); i++) {
					id = (long) contentList.get(i).get("id");
					newBwNoticeRecord.setNoticeId(id);
					newBwNoticeRecord.setCreateTime(new Date());
					cmsContentService.addNoticeRecord(newBwNoticeRecord);
				}
			}
			// --end

			result.setResult(resultMap);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询公告列表：getNoticeList", e);
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
		}
		return result;
	}

	public static void main(String[] args) {
		for (String s : getScrollList()) {
			System.out.println(s);
		}
	}
}
