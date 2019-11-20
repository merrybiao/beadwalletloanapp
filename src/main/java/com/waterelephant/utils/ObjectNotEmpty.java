package com.waterelephant.utils;

import java.lang.reflect.Field;

/**
 * ture:有属性为空或传入参数为空，fasle:所有属性不为空
 * 
 * @ClassName: ObjectNotEmpty
 * @Description: 判断对象非空且对象所有属性非空
 * @author He Longyun
 * @date 2017年12月15日 下午12:42:30
 */
public class ObjectNotEmpty {
	public static boolean isAllFieldNull(Object obj) throws Exception {
		boolean flag = false;
		if (!CommUtils.isNull(obj)) {// 判断该对象是否为空
			Class stuCla = (Class) obj.getClass();// 得到类对象
			Field[] fs = stuCla.getDeclaredFields();// 得到属性集合
			for (Field f : fs) {// 遍历属性
				f.setAccessible(true); // 设置属性是可以访问的
				Object val = f.get(obj);// 得到此属性的值
				if (CommUtils.isNull(val)) {// 只要有1个属性为空,那么就有的属性值为空
					flag = true;
					break;
				}
			}
		} else {// 对象为空
			flag = true;
		}
		return flag;
	}
}
