package com.waterelephant.service;

import com.waterelephant.entity.BwOperateBasic;

public interface BwOperateBasicService {

	// 保存
	int save(BwOperateBasic bwOperateBasic);

	// 根据借款人查询其运营商基本信息
	BwOperateBasic getOperateBasicById(Long borrowerId);

	// 根据借款人姓名匹配运营商信息
	BwOperateBasic getOperateBasicByName(Long borrowerId, String name);

	// 更新
	int update(BwOperateBasic bwOperateBasic);

	// 根据手机号查询运营商基本信息
	BwOperateBasic getBwOperateBasicByBorrowerId(Long borrowerId);
}
