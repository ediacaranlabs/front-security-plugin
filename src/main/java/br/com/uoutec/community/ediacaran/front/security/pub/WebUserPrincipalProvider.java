package br.com.uoutec.community.ediacaran.front.security.pub;

import javax.ejb.Singleton;
import javax.inject.Inject;

import br.com.uoutec.ediacaran.core.UserPrincipalProvider;

@Singleton
public class WebUserPrincipalProvider 
	implements UserPrincipalProvider {

	@Inject
	private PrincipalRequestListener principalRequestListener;
	
	public java.security.Principal getUserPrincipal() {
		return principalRequestListener.threadPrincipal.get();
	}
}
