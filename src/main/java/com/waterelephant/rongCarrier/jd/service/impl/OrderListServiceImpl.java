package com.waterelephant.rongCarrier.jd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.waterelephant.rongCarrier.jd.entity.OrderList;
import com.waterelephant.rongCarrier.jd.service.OrderListService;
import com.waterelephant.service.BaseService;

@Service
public class OrderListServiceImpl extends BaseService<OrderList, Long> implements OrderListService {

	/**
	 * 保存
	 */
	@Override
	public void saveJdOrderList(List<OrderList> list) {
//		StringBuilder sqlStrb = new StringBuilder();
//		sqlStrb.append(
//				"insert into bw_jd_order_list(borrower_id,order_id,receiver,money,buy_way,buy_time,order_status,
// login_name,receiver_addr,receiver_fix_phone,receiver_telephone,product_names,invoice_type,invoice_header,invoice_content,
// create_time,update_time) values");
//		String temp = "";
//		// TODO
//		for (OrderList orderList : list) {
//			temp = "(" + orderList.getBorrowerId() + "," + orderList.getOrderId() + "," + orderList.getReceiver() + ","
//					+ orderList.getMoney() + "," + orderList.getBuyWay() + "," + orderList.getBuyTime() + ","
//					+ orderList.getOrderStatus() + "," + orderList.getLoginName() + "," + orderList.getReceiverAddr()
//					+ "," + orderList.getReceiverFixPhone() + "," + orderList.getReceiverTelephone() + ","
//					+ orderList.getProductNames() + "," + orderList.getInvoiceType() + ","
//					+ orderList.getInvoiceHeader() + "," + orderList.getInvoiceContent() + ","
//					+ orderList.getCreateTime() + "," + orderList.getUpdateTime() + "),";
//			sqlStrb.append(temp);
//		}
//		String sql = sqlStrb.toString();
//		sql = sql.substring(0, sql.length() - 1);
//		// System.out.println(sql);
//		// 保存数据并返回保存结果
		List<String> sqlList = new ArrayList<>();
		StringBuilder sqlSbuilder = new StringBuilder();
		for (int i=0;i<sqlList.size();i++) {
			OrderList orderList = list.get(i);
			sqlSbuilder.append("(");
			sqlSbuilder.append(orderList.getBorrowerId() + ",");
			sqlSbuilder.append(" '" +orderList.getOrderId() + "',");
			sqlSbuilder.append(" '" + orderList.getReceiver() + "',");
			sqlSbuilder.append(orderList.getMoney() + ",");
			sqlSbuilder.append(" '" + orderList.getBuyWay() + "',");
			sqlSbuilder.append(orderList.getBuyTime() + ",");
			sqlSbuilder.append(" '" + orderList.getOrderStatus() + "',");
			sqlSbuilder.append(" '" + orderList.getLoginName() + "',");
			sqlSbuilder.append(" '" + orderList.getReceiverAddr() + "',");
			sqlSbuilder.append(" '" + orderList.getReceiverFixPhone() + "',");
			sqlSbuilder.append(" '" + orderList.getReceiverTelephone() + "',");
			sqlSbuilder.append(" '" + orderList.getProductNames() + "',");
			sqlSbuilder.append(" '" + orderList.getInvoiceType() + "',");
			sqlSbuilder.append(" '" + orderList.getInvoiceHeader() + "',");
			sqlSbuilder.append(" '" + orderList.getInvoiceContent() + "',");
			sqlSbuilder.append(" '" + orderList.getCreateTime() + "',");
			sqlSbuilder.append(" '" + orderList.getUpdateTime() + "'");
			sqlSbuilder.append(")");

			if (i != 100 && (i != (sqlList.size() - 1))) {
				sqlSbuilder.append(","); // 添加逗号
			}

			if (i == 100 || (i == (sqlList.size() - 1))) {
				sqlList.add(sqlSbuilder.toString());
				sqlSbuilder = new StringBuilder();
			}
		}

		for (String sql : sqlList) {
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("insert into bw_jd_order_list");
			strBuilder.append("(");
			strBuilder.append("borrower_id,");
			strBuilder.append("order_id,");
			strBuilder.append("receiver,");
			strBuilder.append("money,");
			strBuilder.append("buy_way,");
			strBuilder.append("buy_time,");
			strBuilder.append("order_status,");
			strBuilder.append("login_name,");
			strBuilder.append("receiver_addr,");
			strBuilder.append("receiver_fix_phone,");
			strBuilder.append("receiver_telephone,");
			strBuilder.append("product_names,");
			strBuilder.append("invoice_type,");
			strBuilder.append("invoice_header,");
			strBuilder.append("invoice_content,");
			strBuilder.append("create_time,");
			strBuilder.append("update_time");
			strBuilder.append(")");
			strBuilder.append("values");
			strBuilder.append(sql);
			// 插入数据
			sqlMapper.insert(sql);
		}
	}

	/**
	 * 更新
	 */
	@Override
	public boolean updateOrderList(OrderList orderList) {
		return mapper.updateByPrimaryKey(orderList) > 0;
	}

	/**
	 * 查询 根据borrowerId查询
	 */
	@Override
	public int queryOrderList(Long borrowerId) {
		String sql = "select count(*) from bw_jd_order_list a where a.borrower_id = " + borrowerId;
		int count = sqlMapper.selectOne(sql, Integer.class);
		return count;
	}

	/**
	 * 根据订单生成时间查询最后的订单
	 */
	@Override
	public OrderList queryBuyTime(Long borrowerId, String orderId) {
		String sql = "select * from bw_jd_order_list a where a.borrower_id = " + borrowerId + " and a.order_id = '"
				+ orderId + "' order by buy_time desc limit 0,1";
		OrderList orderList = sqlMapper.selectOne(sql, OrderList.class);
		return orderList;

	}

	/**
	 * 删除订单信息
	 */
	@Override
	public boolean deleteOrderList(Long borrowerId) {
		String sql = "delete from bw_jd_order_list where borrower_id = " + borrowerId;
		return sqlMapper.delete(sql) > 0;
	}

}
