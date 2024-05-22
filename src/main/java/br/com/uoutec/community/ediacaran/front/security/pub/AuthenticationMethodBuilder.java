package br.com.uoutec.community.ediacaran.front.security.pub;

public interface AuthenticationMethodBuilder {

	public static final String LOGIN_PAGE  = "login_page";
	
	public static final String ERROR_PAGE  = "error_page";
	
	AuthenticationMethodBuilder setOption(String name, Object value);
	
}
