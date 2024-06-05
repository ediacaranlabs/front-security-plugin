package br.com.uoutec.community.ediacaran.front.security.pub;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.uoutec.ediacaran.core.UserPrincipalProvider;
import br.com.uoutec.ediacaran.core.plugins.PublicBean;

@Singleton
public class WebUserPrincipalProvider 
	implements UserPrincipalProvider, PublicBean {

	@Inject
	private PrincipalRequestListener principalRequestListener;
	
	public java.security.Principal getUserPrincipal() {
		HttpServletRequest servlet = principalRequestListener.threadPrincipal.get();
		return servlet == null? null : servlet.getUserPrincipal();
	}
}
