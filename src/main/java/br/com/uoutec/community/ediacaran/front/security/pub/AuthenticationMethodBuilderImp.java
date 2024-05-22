package br.com.uoutec.community.ediacaran.front.security.pub;

public class AuthenticationMethodBuilderImp 
	implements AuthenticationMethodBuilder{

	private SecurityConfig securityConfig;
	
	public AuthenticationMethodBuilderImp(SecurityConfig securityConfig) {
		this.securityConfig = securityConfig;
	}

	@Override
	public AuthenticationMethodBuilder setOption(String name, Object value) {
		securityConfig.setOption(name, value);
		return this;
	}

}
