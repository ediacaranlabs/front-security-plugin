package br.com.uoutec.community.ediacaran.front.security.pub;

import br.com.uoutec.community.ediacaran.security.SecurityRegistryException;

public interface SecurityConstraintBuilder extends SecurityBuilder{
	
	SecurityConstraintBuilder addRole(String value) throws SecurityRegistryException;
	
	SecurityConstraintBuilder get();
	
	SecurityConstraintBuilder head();

	SecurityConstraintBuilder post();
	
	SecurityConstraintBuilder put();
	
	SecurityConstraintBuilder delete();
	
	SecurityConstraintBuilder options();
	
	SecurityConstraintBuilder trace();
	
	SecurityConstraintBuilder patch();

	SecurityConstraintBuilder connect();
	
	SecurityConstraintBuilder method(String value);
	
	SecurityConstraintBuilder setUserConstraint(String value);
	
}
