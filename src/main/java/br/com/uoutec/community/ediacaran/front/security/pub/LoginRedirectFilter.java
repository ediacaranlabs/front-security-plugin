package br.com.uoutec.community.ediacaran.front.security.pub;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginRedirectFilter implements Filter{

	private final Logger logger = LoggerFactory.getLogger(LoginRedirectFilter.class);
	
	private String loginPage;
	
	private String logoutPage;
	
	public LoginRedirectFilter(String loginPage, String logoutPage) {
		this.loginPage = loginPage;
		this.logoutPage = logoutPage;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		
		String uri = httpRequest.getRequestURI();
		uri = uri.replace("\\\\+", "/").replaceAll("/+", "/");
		
		if(logger.isTraceEnabled()) {
			logger.trace("check uri: {}", uri);
		}
		
		if(uri.equals(loginPage) || uri.equals(logoutPage)) {
			chain.doFilter(httpRequest, response);
			return;
		}
		
		String page = uri.endsWith("/login")? loginPage : logoutPage;
		
		String redirect = 
				"<html>\n" +
				"	<head>\n" +
				"		<meta http-equiv=\"refresh\" content=\"0; URL=" + page + "\" />\n" +
				"	</head>\n" +
				"	<body>\n" +
				"	</body>\n" +
				"</html>";
		
		ServletOutputStream sos = response.getOutputStream();
		sos.write(redirect.getBytes());
		sos.flush();
	}

	@Override
	public void destroy() {
	}

}
