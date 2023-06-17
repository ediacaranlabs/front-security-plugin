package br.com.uoutec.community.ediacaran.front.security.pub;

import br.com.uoutec.community.ediacaran.security.SecurityConstraint;
import br.com.uoutec.community.ediacaran.security.SecurityRegistry;

public class SecurityBuilderImp 
	implements SecurityBuilder{

	private SecurityRegistry securityRegistry;
	
	private SecurityConfig securityConfig;
	
	public SecurityBuilderImp(SecurityBuilderImp builder) {
		this.securityConfig = builder.securityConfig;
		this.securityRegistry = builder.securityRegistry;
	}
	
	public SecurityBuilderImp(SecurityConfig value, SecurityRegistry securityRegistry) {
		this.securityConfig = value;
		this.securityRegistry = securityRegistry;
	}
	
	protected SecurityConfig getSecurityConfig() {
		return securityConfig;
	}
	
	public SecurityConstraintBuilder addConstraint(String value) {
		SecurityConstraint sc = new SecurityConstraint(value);
		securityConfig.getConstraints().add(sc);
		return new SecurityConstraintBuilderImp(sc, securityRegistry, this);
	}
	
	public SecurityBuilder setRealmName(String value) {
		securityConfig.setRealmName(value);
		return this;
	}
	
	public void basic() {
		securityConfig.setMethod(BASIC);
	}
	
	public void digest() {
		securityConfig.setMethod(DIGEST);
	}
	
	public void cert() {
		securityConfig.setMethod(CERT);
	}
	
	public void form(String loginPage) {
		securityConfig.setMethod(FORM);
		securityConfig.setLoginPage(loginPage);
	}
	
	public void form(String loginPage, String errorPage) {
		securityConfig.setMethod(FORM);
		securityConfig.setLoginPage(loginPage);
		securityConfig.setErrorPage(errorPage);
	}
	
}
