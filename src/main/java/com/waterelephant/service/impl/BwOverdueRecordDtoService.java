package com.waterelephant.service.impl;

import org.springframework.stereotype.Service;

import com.waterelephant.dto.BwOverdueRecordDto;
import com.waterelephant.service.BaseService;
import com.waterelephant.service.IBwOverdueRecordDtoService;

@Service
public class BwOverdueRecordDtoService extends BaseService<BwOverdueRecordDto, Long>
		implements IBwOverdueRecordDtoService {

	@Override
	public BwOverdueRecordDto findBwOverdueRecordDtoByAttr(BwOverdueRecordDto bwOverdueRecordDto) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select bo.id as orderId,bo.borrow_amount as borrowAmonunt,bor.overdue_day as overdueDay,");
		sb.append(" bor.overdue_accrual_money as overdueAccrualMoney,sum(br.reality_repay_money) as repayMoney  ");
		sb.append(" from  bw_repayment_plan br ");
		sb.append(" left join bw_order bo on bo.id = br.order_id ");
		sb.append(" left join  bw_overdue_record bor  on br.order_id = bor.order_id ");
		sb.append(" where bo.id = #{id} ");
		// sb.append(" AND br.repay_type = 2 and br.repay_status = 1 ");
		return sqlMapper.selectOne(sb.toString(), bwOverdueRecordDto.getOrderId(), BwOverdueRecordDto.class);
	}

}
