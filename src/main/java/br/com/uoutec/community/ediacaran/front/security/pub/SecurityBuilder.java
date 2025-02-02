package br.com.uoutec.community.ediacaran.front.security.pub;

public interface SecurityBuilder {

	public static final String BASIC  = "BASIC";
	
	public static final String DIGEST = "DIGEST";
	
	public static final String CERT   = "CLIENT-CERT";
	
	public static final String FORM   = "FORM";
	
	SecurityConstraintBuilder addConstraint(String value);
	
	AuthenticationMethodBuilder authenticationMethod(String value);
	
	void basic();
	
	void digest();
	
	void cert();
	
	AuthenticationMethodBuilder form();
	
}
