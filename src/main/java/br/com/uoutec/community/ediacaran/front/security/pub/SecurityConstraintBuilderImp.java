package br.com.uoutec.community.ediacaran.front.security.pub;

import br.com.uoutec.community.ediacaran.security.AuthorizationManager;
import br.com.uoutec.community.ediacaran.security.SecurityConstraint;
import br.com.uoutec.community.ediacaran.security.SecurityRegistryException;

public class SecurityConstraintBuilderImp 
	extends SecurityBuilderImp 
	implements SecurityConstraintBuilder {

	public static final String GET = "GET";
	
	public static final String HEAD = "HEAD";
	
	public static final String POST = "POST";
	
	public static final String PUT = "PUT";
	
	public static final String DELETE = "DELETE";
	
	public static final String CONNECT = "CONNECT";
	
	public static final String OPTIONS = "OPTIONS";
	
	public static final String TRACE = "TRACE";
	
	public static final String PATCH = "PATCH";

	private AuthorizationManager authorizationManager;
	
	private SecurityConstraint securityConstraint;
	
	public SecurityConstraintBuilderImp(SecurityConstraint sc, 
			AuthorizationManager authorizationManager, SecurityBuilderImp parent) {
		super(parent);
		this.securityConstraint = sc;
		this.authorizationManager = authorizationManager;
	}
	
	public SecurityConstraintBuilderImp addRole(String value) throws SecurityRegistryException {
		if(authorizationManager.getRole(value) == null) {
			throw new SecurityRegistryException("role not found: " + value);
		}
		
		securityConstraint.getRoles().add(value);
		return this;
	}
	
	public SecurityConstraintBuilderImp get() {
		securityConstraint.getMethods().add(GET);
		return this;
	}
	
	public SecurityConstraintBuilderImp head() {
		securityConstraint.getMethods().add(HEAD);
		return this;
	}

	public SecurityConstraintBuilderImp post() {
		securityConstraint.getMethods().add(POST);
		return this;
	}
	
	public SecurityConstraintBuilderImp put() {
		securityConstraint.getMethods().add(PUT);
		return this;
	}
	
	public SecurityConstraintBuilderImp delete() {
		securityConstraint.getMethods().add(DELETE);
		return this;
	}
	
	public SecurityConstraintBuilderImp options() {
		securityConstraint.getMethods().add(OPTIONS);
		return this;
	}
	
	public SecurityConstraintBuilderImp trace() {
		securityConstraint.getMethods().add(TRACE);
		return this;
	}
	
	public SecurityConstraintBuilderImp patch() {
		securityConstraint.getMethods().add(PATCH);
		return this;
	}

	public SecurityConstraintBuilderImp connect() {
		securityConstraint.getMethods().add(CONNECT);
		return this;
	}
	
	public SecurityConstraintBuilderImp method(String value) {
		securityConstraint.getMethods().add(value.toUpperCase());
		return this;
	}
	
	public SecurityConstraintBuilder setUserConstraint(String value) {
		securityConstraint.setUserConstraint(value);
		return this;
	}
	
}
