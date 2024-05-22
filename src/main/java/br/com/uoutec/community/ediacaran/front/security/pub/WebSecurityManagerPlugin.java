package br.com.uoutec.community.ediacaran.front.security.pub;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.security.AuthorizationManager;

@Singleton
public class WebSecurityManagerPlugin 
	implements SecurityBuilder {

	private SecurityConfig securityConfig;

	private SecurityBuilder builder;

	@Inject
	public WebSecurityManagerPlugin(AuthorizationManager authorizationManager){
		this.securityConfig = new SecurityConfig();
		this.builder = new SecurityBuilderImp(securityConfig);
	}
	
	public synchronized void setSecurityConfig(SecurityConfig securityConfig) {
		if(this.securityConfig != null) {
			throw new SecurityException();
		}
		
		this.securityConfig = securityConfig;
	}
	
	@Override
	public SecurityConstraintBuilder addConstraint(String value) {
		return builder.addConstraint(value);
	}

	@Override
	public void basic() {
		builder.basic();
	}

	@Override
	public void digest() {
		builder.digest();
	}

	@Override
	public void cert() {
		builder.cert();
	}

	public AuthenticationMethodBuilder form() {
		return builder.form();
	}

	@Override
	public AuthenticationMethodBuilder authenticationMethod(String value) {
		return builder.authenticationMethod(value);
	}
	
}
