package br.com.uoutec.community.ediacaran.front.security.pub;

import javax.inject.Singleton;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;

import br.com.uoutec.ediacaran.core.EdiacaranEventListener;
import br.com.uoutec.ediacaran.core.EdiacaranEventObject;
import br.com.uoutec.ediacaran.core.plugins.PublicBean;
import br.com.uoutec.ediacaran.web.ContextInitializer;

@Singleton
public class PrincipalRequestListener implements EdiacaranEventListener, PublicBean{

	ThreadLocal<HttpServletRequest> threadPrincipal;
	
	public PrincipalRequestListener() {
		this.threadPrincipal = new ThreadLocal<>();
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

	private void requestInitialized(ServletRequestEvent arg0) {
		threadPrincipal.set((HttpServletRequest)arg0.getServletRequest());
	}

	private void requestDestroyed(ServletRequestEvent arg0) {
		threadPrincipal.remove();
	}
	
}
