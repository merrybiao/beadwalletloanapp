package com.waterelephant.capitalDemo.test;

import com.waterelephant.entity.WoNiuOrder;
import com.waterelephant.service.TestWoNiuService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class WoNiuTest {	

	@Autowired
	private TestWoNiuService testWoNiuService;

	/**
	 * 	增加
	 */
	@Test
	public void test01(){
		WoNiuOrder woNiuOrder = new WoNiuOrder();
		woNiuOrder.setUserName("张博");
		woNiuOrder.setMobile("18607190917");
		woNiuOrder.setMoney("1000");
		woNiuOrder.setUpdateTime(new Date());
		boolean flag = testWoNiuService.addWoNiuOrder(woNiuOrder);
		System.out.println(flag);
	}

	/**
	 *  查询
	 */
	@Test
	public void test02(){
		WoNiuOrder woNiuOrder = new WoNiuOrder();
		woNiuOrder.setUserName("张博");
		WoNiuOrder woNiuByAttr = testWoNiuService.findWoNiuByAttr(woNiuOrder);
		System.out.println(woNiuByAttr);
	}

	/**
	 * 	修改
	 */
	@Test
	public void test03(){
		WoNiuOrder woNiuOrder = new WoNiuOrder();
		woNiuOrder.setUserName("张博");
		woNiuOrder = testWoNiuService.findWoNiuByAttr(woNiuOrder);
		woNiuOrder.setUserName("刘道道");
		woNiuOrder.setMoney("9999");
		int update = testWoNiuService.updateWoNiuOrderSelective(woNiuOrder);
		System.out.println(update);
	}

}
