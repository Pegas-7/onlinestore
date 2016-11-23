package com.yauhenikuntsevich.training.onlinestore.web.servlets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LogFilter implements Filter {
	@Override
	public void init(FilterConfig config) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		/*
		 * Called before the Filter instance is removed from service by the web
		 * container
		 */
	}
}