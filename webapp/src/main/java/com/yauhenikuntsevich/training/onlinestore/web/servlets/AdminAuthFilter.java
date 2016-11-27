package com.yauhenikuntsevich.training.onlinestore.web.servlets;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yauhenikuntsevich.training.onlinestore.services.AuthenticationService;

public class AdminAuthFilter implements Filter {
	private AuthenticationService authService;

	@Override
	public void init(FilterConfig config) throws ServletException {
		authService = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext())
				.getBean(AuthenticationService.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {
		org.eclipse.jetty.server.Request req = (org.eclipse.jetty.server.Request) request;
		org.eclipse.jetty.server.Response res = (org.eclipse.jetty.server.Response) response;

		String[] credentials = resolveCredentials(req);

		boolean isCredentialsResolved = credentials != null && credentials.length == 2;
		if (!isCredentialsResolved) {
			res.sendError(401);
			return;
		}

		String username = credentials[0];
		String password = credentials[1];
		if (authService.validateUser(username, password, "ADMIN_ROLE")) {
			chain.doFilter(request, response);
		} else {
			res.sendError(401);
		}

	}

	private String[] resolveCredentials(org.eclipse.jetty.server.Request req) {
		try {
			Enumeration<String> headers = req.getHeaders("Authorization");
			String nextElement = headers.nextElement();
			String base64Credentials = nextElement.substring("Basic".length()).trim();
			String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
			return credentials.split(":", 2);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void destroy() {
	}

}