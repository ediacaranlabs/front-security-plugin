package br.com.uoutec.community.ediacaran.front.security.pub;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.security.AbstractAuthorizationManagerPlugin;
import br.com.uoutec.community.ediacaran.security.AuthorizationManager;

@Singleton
public class WebSecurityManagerPlugin 
	extends AbstractAuthorizationManagerPlugin
	implements SecurityBuilder{

	private SecurityConfig securityConfig;

	private SecurityBuilder builder;

	@Inject
	public WebSecurityManagerPlugin(AuthorizationManager authorizationManager){
		this.securityConfig = new SecurityConfig();
		this.builder = new SecurityBuilderImp(securityConfig, authorizationManager);
	}
	
	@Override
	protected Object getSecurityConfig() {
		return securityConfig;
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

	@Override
	public void form(String loginPage) {
		builder.form(loginPage);
	}

	@Override
	public void form(String loginPage, String errorPage) {
		builder.form(loginPage, errorPage);
	}
	
}
