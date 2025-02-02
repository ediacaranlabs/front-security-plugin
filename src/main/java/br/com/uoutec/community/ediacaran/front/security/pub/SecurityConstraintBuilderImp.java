package br.com.uoutec.community.ediacaran.front.security.pub;

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

	private SecurityConstraint securityConstraint;
	
	public SecurityConstraintBuilderImp(SecurityConstraint sc, SecurityBuilderImp parent) {
		super(parent);
		this.securityConstraint = sc;
	}
	
	public SecurityConstraintBuilder addRole(String value) throws SecurityRegistryException {
		securityConstraint.getRoles().add(value);
		return this;
	}
	
	public SecurityConstraintBuilder get() {
		securityConstraint.getMethods().add(GET);
		return this;
	}
	
	public SecurityConstraintBuilder head() {
		securityConstraint.getMethods().add(HEAD);
		return this;
	}

	public SecurityConstraintBuilder post() {
		securityConstraint.getMethods().add(POST);
		return this;
	}
	
	public SecurityConstraintBuilder put() {
		securityConstraint.getMethods().add(PUT);
		return this;
	}
	
	public SecurityConstraintBuilder delete() {
		securityConstraint.getMethods().add(DELETE);
		return this;
	}
	
	public SecurityConstraintBuilder options() {
		securityConstraint.getMethods().add(OPTIONS);
		return this;
	}
	
	public SecurityConstraintBuilder trace() {
		securityConstraint.getMethods().add(TRACE);
		return this;
	}
	
	public SecurityConstraintBuilder patch() {
		securityConstraint.getMethods().add(PATCH);
		return this;
	}

	public SecurityConstraintBuilder connect() {
		securityConstraint.getMethods().add(CONNECT);
		return this;
	}
	
	public SecurityConstraintBuilder method(String value) {
		securityConstraint.getMethods().add(value.toUpperCase());
		return this;
	}
	
	public SecurityConstraintBuilder setUserConstraint(String value) {
		securityConstraint.setUserConstraint(value);
		return this;
	}
	
}
