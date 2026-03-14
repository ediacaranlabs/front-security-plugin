package br.com.uoutec.community.ediacaran.front.security.pub;

import javax.inject.Singleton;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import br.com.uoutec.community.ediacaran.security.AuthenticatedSubject;
import br.com.uoutec.community.ediacaran.security.Principal;
import br.com.uoutec.community.ediacaran.security.Subject;
import br.com.uoutec.ediacaran.core.EdiacaranEventListener;
import br.com.uoutec.ediacaran.core.EdiacaranEventObject;
import br.com.uoutec.ediacaran.web.ContextInitializer;

@Singleton
public class SubjectRequestListener implements EdiacaranEventListener {

	private final ThreadLocal<ThreadEntry> threadSubject;
	
	public SubjectRequestListener() {
		this.threadSubject = new ThreadLocal<>();
	}
	
	@Override
	public void onEvent(EdiacaranEventObject event) {

		if(event.getSource() instanceof ContextInitializer) {
			
			if("requestInitialized".equals(event.getType())) {
				requestInitialized((ServletRequestEvent)event.getData());
			}
			else
			if("requestDestroyed".equals(event.getType())) {
				requestDestroyed((ServletRequestEvent)event.getData());
			}
		}
	}

	public Subject getSubject() {
		ThreadEntry e = threadSubject.get();
		return e == null? null : e.subject;
	}
	
	private void requestInitialized(ServletRequestEvent arg0) {
		
		ThreadEntry e = threadSubject.get();

		if(e != null) {
			/*
			if(e.owner.equals(arg0.getServletRequest())){
				throw new IllegalStateException();
			}*/
			
			return;
		}
		
		e = new ThreadEntry(
				new AuthenticatedSubject() {
					
					private static final long serialVersionUID = 1L;

					public Principal getPrincipal() {
						HttpServletRequest httpRequest = (HttpServletRequest)arg0.getServletRequest();
						return (Principal) httpRequest.getUserPrincipal();
					}
					
				}, 
				arg0.getServletRequest()
		);
		
		threadSubject.set(e);
	}

	private void requestDestroyed(ServletRequestEvent arg0) {
		
		ThreadEntry e = threadSubject.get();
		
		if(e != null && e.owner.equals(arg0.getServletRequest())) {
			threadSubject.remove();
		}
		
	}
	
	private static class ThreadEntry {
		
		public Subject subject;
		
		public ServletRequest owner;

		public ThreadEntry(Subject subject, ServletRequest owner) {
			this.subject = subject;
			this.owner = owner;
		}
		
	}
	
}
