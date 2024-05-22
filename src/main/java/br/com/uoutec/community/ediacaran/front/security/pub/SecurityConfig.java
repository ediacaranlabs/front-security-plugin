package br.com.uoutec.community.ediacaran.front.security.pub;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.uoutec.community.ediacaran.security.SecurityConstraint;

public class SecurityConfig {
	
	private Set<SecurityConstraint> constraints;
	
	private String method;
	
	private String realmName;

	private Map<String, Object> options;
	
	public SecurityConfig() {
		this.constraints = new HashSet<SecurityConstraint>();
	}
	
	public Set<SecurityConstraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(Set<SecurityConstraint> constraints) {
		this.constraints = constraints;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRealmName() {
		return realmName;
	}

	public void setRealmName(String realmName) {
		this.realmName = realmName;
	}

	public void setOption(String key, Object value) {
		options.put(key, value);
	}
	
	public Object getOption(String key) {
		return options.get(key);
	}

}
