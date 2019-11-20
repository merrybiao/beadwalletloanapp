package com.waterelephant.base;


import org.springframework.stereotype.Controller;

/**
 * 基类控制器
 * @author 刘自宾
 *
 */
@Controller
public class BaseController {
	
	/**
	 * 是否输出日志
	 */
	protected boolean isLoged = true;
	
	/**
	 * 默认编码
	 */
	public final static String DEFAULT_CODE = "UTF-8";
	
	/**
	 * 获取网络接口响应对象
	 * @return 接口响应对象
	 */
	public WebRespModel obtainWebResponse() {
		WebRespModel response = new WebRespModel();
		response.setResultCode(WebRespModel.RESULT_CODE_FAIL);
		return response;
	}
	
}
