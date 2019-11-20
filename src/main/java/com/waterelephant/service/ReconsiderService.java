package com.waterelephant.service;


import com.waterelephant.entity.BwReconsider;
import com.waterelephant.exception.BusException;

/**
 * 复审服务
 * @author DOY
 *
 */
public interface ReconsiderService {

	void addReconsider(String orderId, String result, String comment,Long userId) throws BusException;


	void add(BwReconsider bwReconsider);

}
