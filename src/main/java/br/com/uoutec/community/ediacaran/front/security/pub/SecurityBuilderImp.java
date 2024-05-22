package br.com.uoutec.community.ediacaran.front.security.pub;

import br.com.uoutec.community.ediacaran.security.SecurityConstraint;

public class SecurityBuilderImp 
	implements SecurityBuilder{

	private SecurityConfig securityConfig;
	
	public SecurityBuilderImp(SecurityBuilderImp builder) {
		this.securityConfig = builder.securityConfig;
	}
	
	public SecurityBuilderImp(SecurityConfig value) {
		this.securityConfig = value;
	}
	
	protected SecurityConfig getSecurityConfig() {
		return securityConfig;
	}
	
	public SecurityConstraintBuilder addConstraint(String value) {
		SecurityConstraint sc = new SecurityConstraint(value);
		securityConfig.getConstraints().add(sc);
		return new SecurityConstraintBuilderImp(sc, this);
	}
	
	public SecurityBuilder setRealmName(String value) {
		securityConfig.setRealmName(value);
		return this;
	}
	
	public void basic() {
		authenticationMethod(BASIC);
	}
	
	public void digest() {
		authenticationMethod(DIGEST);
	}
	
	public void cert() {
		authenticationMethod(CERT);
	}

	public AuthenticationMethodBuilder form() {
		authenticationMethod(FORM);
		return new AuthenticationMethodBuilderImp(securityConfig);
	}
	
	public AuthenticationMethodBuilder authenticationMethod(String value) {
		securityConfig.setMethod(value);
		return new AuthenticationMethodBuilderImp(securityConfig);
	}
	
}
