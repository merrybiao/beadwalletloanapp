//package com.waterelephant.sxyDrainage.utils.fenling;
//
//import java.io.Serializable;
//
//import org.codehaus.jackson.annotate.JsonIgnore;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.annotation.JsonInclude.Include;
//
///**
// * @author Created by Xanthuim on 2018/3/11.
// */
//@JsonInclude(value = Include.NON_NULL)
//public class ServerResponse<T> implements Serializable {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 4995696757454523524L;
//	private int code;
//	private String message;
//	private T data;
//
//	private ServerResponse(int status) {
//		this.code = status;
//	}
//
//	private ServerResponse(int status, String message) {
//		this.code = status;
//		this.message = message;
//	}
//
//	private ServerResponse(int status, T data) {
//		this.code = status;
//		this.data = data;
//	}
//
//	private ServerResponse(int status, String message, T data) {
//		this.code = status;
//		this.message = message;
//		this.data = data;
//	}
//
//	public int getCode() {
//		return code;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public T getData() {
//		return data;
//	}
//
//	@JsonIgnore
//	public boolean isSuccess() {
//		return this.code == ResponseEnum.SUCCESS.getCode();
//	}
//
//	public static <T> ServerResponse<T> success() {
//		return new ServerResponse<>(ResponseEnum.SUCCESS.getCode());
//	}
//
//	public static <T> ServerResponse<T> success(String message) {
//		return new ServerResponse<>(ResponseEnum.SUCCESS.getCode(), message);
//	}
//
//	public static <T> ServerResponse<T> success(T data) {
//		return new ServerResponse<>(ResponseEnum.SUCCESS.getCode(), data);
//	}
//
//	public static <T> ServerResponse<T> success(String message, T data) {
//		return new ServerResponse<>(ResponseEnum.SUCCESS.getCode(), message, data);
//	}
//
//	public static <T> ServerResponse<T> error() {
//		return new ServerResponse<>(ResponseEnum.ERROR.getCode(), ResponseEnum.ERROR.getDescription());
//	}
//
//	public static <T> ServerResponse<T> error(String error) {
//		return new ServerResponse<>(ResponseEnum.ERROR.getCode(), error);
//	}
//
//	public static <T> ServerResponse<T> error(int code, String error) {
//		return new ServerResponse<>(code, error);
//	}
//}
