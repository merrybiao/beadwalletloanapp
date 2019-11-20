package com.waterelephant.service;

import java.util.List;

import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.dto.PageResponseVo;
import com.waterelephant.entity.CmsContent;

/**
 * CMS内容
 *
 * @author duxiaoyong
 */
public interface CmsContentService {

	CmsContent findCmsContentByAttr(CmsContent cmsContent);
	
	PageResponseVo<CmsContent> findCmsContentPage(PageRequestVo reqVo);
	
	List<CmsContent> findCmsContentListByAtta(CmsContent cmsContent);
	
}
