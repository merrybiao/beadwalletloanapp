package com.waterelephant.authentication.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.waterelephant.authentication.request.BodyReaderHttpServletRequestWrapper;

public class HttpServletRequestWrapperFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	    String contentType = request.getContentType();
	    if(null != contentType && contentType.contains("json")) {
	    	if (request instanceof HttpServletRequest) {
	    		ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest)request);
	    		chain.doFilter(requestWrapper, response);
	    	}
	    }else {
	    	chain.doFilter(request, response);
	    }
	}

	@Override
	public void destroy() {}

}
