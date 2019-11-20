package com.waterelephant.authentication.request;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
/**
 * 继承HttpServletRequestWrapper类，在构造方法中缓存body,解决request.getInputStream()或getParameterMap()读取一次后续cotroller获取不到参数的情况
 * <p>1、通过增加filter过滤器将请求转化为BodyReaderHttpServletRequestWrapper
 * <p>2、在BodyReaderHttpServletRequestWrapper中取出request的body(缓存)
 * <p>3、重写getInputStream()方法，将body转化为IO流
 * <p>4、注解@requestBody等可以在controller中取到参数
 * @author dinglinhao
 *
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private Long reqTime;
	private String body;
	private HttpServletRequest request;
	
	public BodyReaderHttpServletRequestWrapper(final HttpServletRequest request) {
		super(request);
		this.request = request;
		this.reqTime = System.currentTimeMillis();
//		String contentType = request.getContentType();
		
		try {
			body =IOUtils.toString(request.getInputStream(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		if(null == body ||"".equals(body)) return null;
		   final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(Charset.forName("UTF-8")));
		   ServletInputStream servletInputStream = new ServletInputStream() {
		   public int read() throws IOException {
		          return byteArrayInputStream.read();
		       }
		   };
		   return servletInputStream;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
    
    @Override
	public String getParameter(String name) {
    	return this.request.getParameter(name);
	}
    
    @Override
	public Map<?, ?> getParameterMap() {
		return this.request.getParameterMap();
	}
	
	@Override
	public Enumeration<?> getParameterNames() {
		return this.request.getParameterNames();
	}

	public String getBody() {
        return this.body;
    }

	public Long getReqTime() {
		return this.reqTime;
	}
}
