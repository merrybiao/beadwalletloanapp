package com.waterelephant.channel.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.waterelephant.channel.service.CmsFriendLinkService;
import com.waterelephant.constants.ActivityConstant;
import com.waterelephant.utils.AppResponseResult;

@Controller
@RequestMapping(value = "/linkInterface")
public class LinkInterfaceController {

	private Logger logger = Logger.getLogger(LinkInterfaceController.class);

	@Autowired
	private CmsFriendLinkService cmsFriendLinkService;

	@Autowired
	private CmsContentChannelService cmsContentService;

	/**
	 * 查询接口
	 */
	@RequestMapping(value = "getLinkList.do")
	@ResponseBody
	public AppResponseResult getPartnerInfo(HttpServletRequest request, HttpServletResponse httpServletResponse) {
		AppResponseResult result = new AppResponseResult();
		try {
			List<Map<String, Object>> friendLink = new ArrayList<Map<String, Object>>();
			String pageNo = request.getParameter("pageNo");// 页码
			friendLink = cmsFriendLinkService.getFriendLinkList(pageNo);
			result.setResult(friendLink);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("友情链接查询接口：getLinkList", e);
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
		}
		return result;
	}

	/**
	 * 栏目内容查询接口
	 */
	@RequestMapping(value = "getContent.do")
	@ResponseBody
	public AppResponseResult getContent(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			Map<String, Object> contentDetail = new HashMap<String, Object>();
			String id = request.getParameter("id");// 栏目id
			Long ld = Long.valueOf(id);
			contentDetail = cmsContentService.findContentDetail(ld);
			result.setResult(contentDetail);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
		}
		return result;
	}

	/**
	 * 获取公告详情
	 */
	@RequestMapping(value = "getNoticeDetail.do")
	@ResponseBody
	public AppResponseResult getNoticeDetail(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		try {
			Map<String, Object> contentDetail = new HashMap<String, Object>();
			String id = request.getParameter("id");// 内容id
			String borrower_id = request.getParameter("borrower_id");// 用户id
			if (StringUtils.isEmpty(id)) {
				result.setCode(ActivityConstant.ErrorCode.FAIL);
				result.setMsg(ActivityConstant.ErrorMsg.PARAM_ERROR);
				return result;
			}
			Long ld = Long.valueOf(id);
			contentDetail = cmsContentService.findContentDetail(ld);
			if (!StringUtils.isEmpty(borrower_id)) {
				// 查询是否添加了浏览记录
				BwNoticeRecord bwNoticeRecord = new BwNoticeRecord();
				bwNoticeRecord.setBorrowerId(Long.parseLong(borrower_id));
				bwNoticeRecord.setNoticeId(ld);
				bwNoticeRecord.setCreateTime(new Date());
				int isRead = cmsContentService.isReadNotice(bwNoticeRecord);
				if (isRead == 0) {
					// 添加浏览记录表
					cmsContentService.addNoticeRecord(bwNoticeRecord);
				}
			}

			result.setResult(contentDetail);
			result.setCode(ActivityConstant.ErrorCode.SUCCESS);
			result.setMsg(ActivityConstant.ErrorMsg.SUCCESS);
		} catch (Exception e) {
			result.setCode(ActivityConstant.ErrorCode.FAIL);
			result.setMsg(ActivityConstant.ErrorMsg.SYSTEM_ERROR);
		}
		return result;
	}

}
