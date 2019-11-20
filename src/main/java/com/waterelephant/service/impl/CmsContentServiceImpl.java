package com.waterelephant.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.dto.PageRequestVo;
import com.waterelephant.dto.PageResponseVo;
import com.waterelephant.entity.CmsContent;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.CmsContentService;


/**
 * CMS内容服务
 *
 * @author duxiaoyong
 */
@Service
public class CmsContentServiceImpl extends BaseService<CmsContent, Long> implements CmsContentService {

	@Override
	public CmsContent findCmsContentByAttr(CmsContent cmsContent) {
		return mapper.selectOne(cmsContent);
	}

	@Override
	public PageResponseVo<CmsContent> findCmsContentPage(PageRequestVo reqVo) {
		return page(reqVo);
	}

	@Override
	public List<CmsContent> findCmsContentListByAtta(CmsContent cmsContent) {
		return mapper.select(cmsContent);
	}

}
