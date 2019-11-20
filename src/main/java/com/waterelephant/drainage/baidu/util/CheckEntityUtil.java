package com.waterelephant.drainage.baidu.util;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beadwallet.service.utils.CommUtils;

/**
 * 校验实体参数是否为空 Created by dengyan on 2017/7/20.
 */
public class CheckEntityUtil {

	private static Logger logger = LoggerFactory.getLogger(CheckEntityUtil.class);

	/**
	 * 对实体参数进行校验
	 * 
	 * @param entity
	 * @return
	 */
	public static <T> ThirdCommonResponse validataEntity(T entity) {
		ThirdCommonResponse response = new ThirdCommonResponse();
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<T>> validate = validator.validate(entity);
		if (validate != null && validate.size() > 0) {
			for (ConstraintViolation<T> constraintViolation : validate) {
				String message = constraintViolation.getMessage();
				if (message == null) {
					continue;
				} else {
					if (!CommUtils.isNull(message)) {
						response.setCode(1001);
						response.setMsg(message);
						logger.info("ThirdPushDataController.pushOrder获取信息失败：" + message);
						return response;
					}
				}
			}
		}
		return null;
	}
}
