package com.waterelephant.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.dto.CmsContentVo;
import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.dto.PageResponseVo;
import com.waterelephant.entity.CmsContent;
import com.waterelephant.service.CmsContentService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.PageUtil;

import tk.mybatis.mapper.entity.Example;

/**
 * 内容管理
 * 
 * @author duxiaoyong
 */
@Controller
@RequestMapping("/app/cms/content")
public class AppCmsContentController {

	@Autowired
	private CmsContentService cmsContentService;

	/**
	 * 获得内容分页列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findCmsContentPage.do")
	public AppResponseResult findCmsContentPage(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String channelId = request.getParameter("channelId");
		if(CommUtils.isNull(channelId)){
			result.setCode("701");
			result.setMsg("栏目Id为空");
			return result;
		}
		int[] pageParam = PageUtil.init(request);
		PageRequestVo reqVo = new PageRequestVo(pageParam[0], pageParam[1]);
		Example example = new Example(CmsContent.class);
		example.createCriteria().andEqualTo("channelId", channelId).andEqualTo("status", 3);//3表示审核通过
		reqVo.setExample(example);
		PageResponseVo<CmsContent> rspVoTmp = cmsContentService.findCmsContentPage(reqVo);
		//转换，CmsContent中包含的detail等信息，在分页的时候是不需要的
		//通过转换去除这些信息，节省流量
		List<CmsContentVo> list = new ArrayList<CmsContentVo>();
		if(rspVoTmp.getRows() != null){
			for (CmsContent cmsContent : rspVoTmp.getRows()) {
				list.add(new CmsContentVo(cmsContent));
			}
		}
		PageResponseVo<CmsContentVo> rspVo = new PageResponseVo<>(rspVoTmp.getRows() == null ? 0 : rspVoTmp.getRows().size(), list);
		rspVo.setPageNum(rspVoTmp.getPageNum());
		rspVo.setPageSize(rspVoTmp.getPageSize());

		result.setCode("000");
		result.setMsg("请求处理成功");
		result.setResult(rspVo);
		return result;
	}
	
	/**
	 * 获得内容
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findCmsContent.do")
	public AppResponseResult findCmsContent(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String contentId = request.getParameter("contentId");
		if(CommUtils.isNull(contentId)){
			result.setCode("702");
			result.setMsg("内容Id为空");
			return result;
		}
		CmsContent content = new CmsContent();
		content.setId(Long.parseLong(contentId));
		content.setStatus(3);//3表示审核通过
		content = cmsContentService.findCmsContentByAttr(content);
		result.setCode("000");
		result.setMsg("获取内容信息成功");
		result.setResult(content);
		return result;
	}
	
	/**
	 * 获得所有内容列表
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findCmsContentList.do")
	public AppResponseResult findCmsContentList(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String channelId = request.getParameter("channelId");
		if (CommUtils.isNull(channelId)) {
			result.setCode("701");
			result.setMsg("栏目Id为空");
			return result;
		}
		CmsContent content = new CmsContent();
		content.setChannelId(Long.parseLong(channelId));
		content.setStatus(3);//3表示审核通过
		List<CmsContent> list = cmsContentService.findCmsContentListByAtta(content);
		result.setCode("000");
		result.setMsg("获取内容列表成功");
		result.setResult(list);
		return result;
	}

}
