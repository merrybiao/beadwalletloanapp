package com.waterelephant.service;

import java.util.Map;

import javax.validation.constraints.NotNull;

/**
 * 授信单业务处理
 * @author dinglinhao
 *
 */
public interface CreditBussinessService {
	/**
	 * 根据授信单号查询运营商数据
	 * @param creditNo 授信单号
	 * @param appId 授权应用ID
	 * @param gizp 是否gzip压缩
	 * @return
	 */
	Map<String,Object> queryOperatorData(@NotNull String creditNo,String appId,boolean gizp)  throws Exception;
	/**
	 * 根据授信单号查询京东数据
	 * @param creditNo 授信单号
	 * @param appId 授权应用ID
	 * @param gzip 是否gzip压缩
	 * @return
	 */
	Map<String,Object> queryJdData(@NotNull String creditNo,String appId,boolean gzip)  throws Exception;
	/**
	 * 根据授信单号查询淘宝数据
	 * @param creditNo 授信单号
	 * @param appId 授权应用ID
	 * @param gzip 是否gzip压缩
	 * @return
	 */
	Map<String,Object> queryTaobaoData(@NotNull String creditNo,String appId,boolean gzip)  throws Exception;

}
