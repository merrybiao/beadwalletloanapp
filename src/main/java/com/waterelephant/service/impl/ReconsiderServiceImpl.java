package com.waterelephant.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.waterelephant.entity.BwOrder;
import com.waterelephant.entity.BwReconsider;
import com.waterelephant.exception.BusException;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.ReconsiderService;

import cn.jpush.api.utils.StringUtils;

@Service
public class ReconsiderServiceImpl extends BaseService<BwReconsider,Long> implements ReconsiderService {

	@Override
	public void addReconsider(String orderId, String result, String comment,Long userID) throws BusException {
		BwReconsider reconsider=new BwReconsider();//复议
		if(StringUtils.isEmpty(result)){
			throw new BusException("复审状态为空");
		}
		if(StringUtils.isEmpty(comment)){
			throw new BusException("复审内容注释为空");
		}
		if(StringUtils.isEmpty(orderId)){
			throw new BusException("工单Id为空");
		}
		reconsider.setCreateTime(new Date());
		reconsider.setOrderId(Long.valueOf(orderId));
		reconsider.setReconsider(comment);
		reconsider.setReconsiderType(Long.valueOf(result));
		reconsider.setUserId(userID);
		mapper.insert(reconsider);
		/**
		 * 永久被拒
		 */
		if(result.equals("1")){
			Long type=sqlMapper.selectOne("select count(*) count from bw_reject_record  where order_id='"+orderId+"' and reject_type=0",Long.class);
			if(type>0){
				String sql=" update bw_reject_record set reject_type='0' where order_id='"+orderId+"'";
				sqlMapper.update(sql);
			}else{
				sqlMapper.insert("insert into bw_reject_record(reject_info,order_id,create_time,reject_type)"
						+ "  values('"+comment+"','"+orderId+"',now(),'0')");
			}
			sqlMapper.update("update bw_borrower set auth_step='1' ,update_time=now()  where id=(select borrower_id from bw_order where id='"+orderId+"') ");
			sqlMapper.update("update bw_order set status_id='7', update_time=now() where id='"+orderId+"' ");
			
		}
		/**
		 * 非永久被拒
		 */
		if(result.equals("2")){
			Long type=sqlMapper.selectOne("select count(*) count from bw_reject_record  where order_id='"+orderId+"' and reject_type=1",Long.class);
			if(type>0){
				String str=" update bw_reject_record set reject_type='1' where order_id='"+orderId+"'";
				sqlMapper.update(str);
			}else{
				sqlMapper.insert("insert into bw_reject_record(reject_info,order_id,create_time,reject_type)"
						+ "  values('"+comment+"','"+orderId+"',now(),'1')");
			}
			sqlMapper.update("update bw_borrower set auth_step='1' ,update_time=now()  where id=(select borrower_id from bw_order where id='"+orderId+"') ");
			sqlMapper.update("update bw_order set status_id='7', update_time=now() where id='"+orderId+"' ");
		}
		/**
		 * 初审
		 */
		if(result.equals("3")){
			String orStr=" update bw_order set status_id='2', update_time=now() where id='"+orderId+"'";
			sqlMapper.update(orStr);
			/*sqlMapper.delete("delete from bw_reject_record where order_id='"+orderId+"'");*/
			sqlMapper.update("update bw_borrower set auth_step='4' ,update_time=now()  where id=(select borrower_id from bw_order where id='"+orderId+"') ");
		}
		/**
		 * 终审
		 */
		if(result.equals("4")){
			String orStr=" update bw_order set status_id='3', update_time=now() where id='"+orderId+"'";
			sqlMapper.update(orStr);
			/*sqlMapper.delete("delete from bw_reject_record where order_id='"+orderId+"'");*/
			sqlMapper.update("update bw_borrower set auth_step='4' ,update_time=now()  where id=(select borrower_id from bw_order where id='"+orderId+"') ");
		}
	}

	

	@Override
	public void add(BwReconsider bwReconsider) {
		bwReconsider.setId(null);
		mapper.insert(bwReconsider);
		
	}

}
