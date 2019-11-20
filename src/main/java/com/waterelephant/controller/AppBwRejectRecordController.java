package com.waterelephant.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waterelephant.dto.RejectRecordDto;
import com.waterelephant.entity.BwRejectRecord;
import com.waterelephant.service.BwRejectRecordService;
import com.waterelephant.utils.AppResponseResult;
import com.waterelephant.utils.CommUtils;
import com.waterelephant.utils.DateUtil;
import com.waterelephant.utils.MyDateUtils;

/**
 * app认证被拒记录控制器
 * 
 * @author duxiaoyong
 *
 */
@Controller
@RequestMapping("/app/reject/record")
public class AppBwRejectRecordController {
	private Logger logger = Logger.getLogger(AppBwRejectRecordController.class);
	@Autowired
	private BwRejectRecordService bwRejectRecordService;

	@ResponseBody
	@RequestMapping("/findBwRejectRecord.do")
	public AppResponseResult findBwRejectRecord(HttpServletRequest request, HttpServletResponse response) {
		AppResponseResult result = new AppResponseResult();
		String orderId = request.getParameter("orderId");
		if (CommUtils.isNull(orderId)) {
			result.setCode("801");
			result.setMsg("工单Id为空");
			return result;
		}
		// 查询记录并返回
		BwRejectRecord record = new BwRejectRecord();
		logger.info("工单Id：" + orderId);
		record.setOrderId(Long.parseLong(orderId));
		record = bwRejectRecordService.findBwRejectRecordByAtta(record);
		if (!CommUtils.isNull(record)) {
			if (record.getRejectType() == 0) {
				// 永久被拒
				RejectRecordDto rDto = new RejectRecordDto();
				rDto.setId(record.getId());
				rDto.setOrderId(record.getOrderId());
				rDto.setRejectCode(record.getRejectCode());
				rDto.setRejectInfo("系统评分不足");
				rDto.setCreateTime(record.getCreateTime());
				rDto.setRejectType(record.getRejectType());
				result.setResult(rDto);
				result.setCode("000");
				result.setMsg("查询成功");
				return result;
			} else {
				// 非永久被拒
				// 计算时间
				result.setCode("000");
				result.setMsg("获取被拒记录信息成功");
				logger.info("获取被拒记录信息:" + record.getId());
				RejectRecordDto rDto = new RejectRecordDto();
				try {
					Integer num = MyDateUtils.getDaySpace(record.getCreateTime(), new Date());
					logger.info("时间差:" + num);
					rDto.setLimiteTime(num);
					logger.info(rDto.getLimiteTime());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
				}
				logger.info("limiteTime" + MyDateUtils.getDaySpace(record.getCreateTime(), new Date()));
				rDto.setId(record.getId());
				rDto.setOrderId(record.getOrderId());
				rDto.setRejectCode(record.getRejectCode());
				rDto.setRejectInfo("系统评分不足,请于"
						+ DateUtil.getDateString(MyDateUtils.addDays(record.getCreateTime(), 30), DateUtil.YMD)
						+ "之后再次申请");
				rDto.setCreateTime(record.getCreateTime());
				rDto.setRejectType(record.getRejectType());
				result.setResult(rDto);
				result.setCode("000");
				result.setMsg("查询成功");
				return result;
			}
		} else {
			result.setCode("000");
			result.setMsg("查询成功");
			return result;
		}
	}
}
