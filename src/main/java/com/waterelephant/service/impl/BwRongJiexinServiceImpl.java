/**
 * @author heqiwen
 * @date 2017年1月23日
 */
package com.waterelephant.service.impl;

import com.beadwallet.service.entity.request.RongJiexinReq;
import com.beadwallet.service.serve.BeadWalletRongJiexinService;
import com.google.gson.Gson;
import com.waterelephant.entity.BwRongJiexinData;
import com.waterelephant.entity.BwRongJiexinMain;
import com.waterelephant.service.BwRongJiexinService;
import com.waterelephant.utils.RedisUtils;

/**
 * @author Administrator
 *
 */
public class BwRongJiexinServiceImpl implements BwRongJiexinService {

	
	public String getLoginToken(){
		
		return BeadWalletRongJiexinService.getLoginToken();
	}
	
	public BwRongJiexinData getRongJiexinBlacks(String name,String idCard,String phone){
		RongJiexinReq rongJiexinReq= new RongJiexinReq();
		String token=RedisUtils.get("rongjiexin:token");//这个值是在定时任务项目中设置的,这里获取
		//过年来了后,要检查定时任务项目,和取token值. 及这里没有建表.保存数据.
		try{
			rongJiexinReq.setName(name);
			rongJiexinReq.setIdCard(idCard);
			rongJiexinReq.setPhone(phone);
			rongJiexinReq.setToken(token);
			String result=BeadWalletRongJiexinService.getBackCheckUser(rongJiexinReq);
			BwRongJiexinData jiexinData=new Gson().fromJson(result, BwRongJiexinData.class);
			return jiexinData;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
