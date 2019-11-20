package com.waterelephant.service;

import java.util.List;
import java.util.Map;

import com.waterelephant.entity.BwAdjunct;
import com.waterelephant.exception.BusException;

public interface IBwAdjunctService {

	// 添加
	Long save(BwAdjunct bwAdjunct);

	// 更新
	int update(BwAdjunct bwAdjunct);

	// 根据主键id和工单id查询附件信息
	BwAdjunct getById(Long id, Long orderId);

	/**
	 * 根据工单ID查找附件信息
	 * 
	 * @param orderId 工单ID
	 * @return
	 */
	List<BwAdjunct> findBwAdjunctByOrderId(Long orderId);

	/**
	 * 根据工单ID查找合同附件（新）
	 * 
	 * @param orderId 工单ID
	 * @return
	 */
	List<BwAdjunct> findBwAdjunctByOrderIdNew(Long orderId);

	/**
	 * 根据工单ID查找合同附件
	 * 
	 * @param orderId 工单ID
	 * @return
	 */
	BwAdjunct findBwAdjunctByAttr(BwAdjunct bwAdjunct);

	/**
	 * 根据工单ID查找附件图片信息
	 * 
	 * @param orderId 工单ID
	 * @return
	 */
	public List<BwAdjunct> findBwAdjunctPhotoByOrderId(Long orderId);

	List<BwAdjunct> adjunctType(Long id) throws BusException;

	void add(BwAdjunct bwAdjunct);

	BwAdjunct findBwAdjunctByOrderId(Long id, int code);

	Long save(String uploadPath, String valueOf, String valueOf2);

	Long queryBorrowerIdByOrderId(Long orderId);

	/**
	 * 
	 * @param orderId
	 * @param adjunctType
	 * @return
	 */
	List<BwAdjunct> findAdjunctByOrderIdAndAdjunctType(Long orderId, Integer adjunctType);

	/**
	 * 根据订单id查询身份证照片类型：0手持，1活体
	 * 
	 * @param orderId
	 * @return
	 */
	int getPhotoStateByOrderId(Long orderId);

	BwAdjunct findBwAdjunct(Long orderId);

	/**
	 * 活体认证 - 连表查询附件和来源 code0088
	 * 
	 * @param orderId
	 * @return
	 */
	List<Map<String, Object>> findBwAdjunctAndVerifySource(Long orderId);
	/**
	 * 查询附件信息，根据Key<类型>,Value<附件>方式返回
	 * @param orderId
	 * @return
	 */
	Map<Integer,BwAdjunct> queryBwAdjunctByOrderId(Long orderId);
}
