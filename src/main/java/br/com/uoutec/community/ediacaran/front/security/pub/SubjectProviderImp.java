package br.com.uoutec.community.ediacaran.front.security.pub;

import javax.inject.Inject;
import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.security.Subject;
import br.com.uoutec.community.ediacaran.security.SubjectProvider;
import br.com.uoutec.ediacaran.core.plugins.PublicBean;

@Singleton
public class SubjectProviderImp 
	implements SubjectProvider, PublicBean{
			
	@Inject
	private SubjectRequestListener subjectRequestListener;
	
	@Override
	public Subject getSubject() {
		return subjectRequestListener.getSubject();
	}
	
}
