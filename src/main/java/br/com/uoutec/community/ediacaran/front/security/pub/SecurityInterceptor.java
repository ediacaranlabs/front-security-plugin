package br.com.uoutec.community.ediacaran.front.security.pub;

import javax.inject.Singleton;

import org.brandao.brutos.MvcRequest;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.InterceptsStack;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.web.HttpStatus;
import org.brandao.brutos.web.WebStackRequestElement;

import br.com.uoutec.community.ediacaran.plugins.PublicType;
import br.com.uoutec.community.ediacaran.security.AuthorizationException;

@Singleton
@Intercepts(isDefault=false)
@InterceptsStack(name="securityStack", isdefault=true)
public class SecurityInterceptor 
	extends AbstractInterceptor
	implements PublicType {

	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {

		MvcRequest request                         = handler.getRequest();
		WebStackRequestElement stackRequestElement = (WebStackRequestElement) request.getStackRequestElement();
		
		try {
			stack.next(handler);
			Throwable ex = stackRequestElement.getObjectThrow();
			
			if(ex != null && ex instanceof AuthorizationException) {
				stackRequestElement.setResponseStatus(HttpStatus.FORBIDDEN);
				stackRequestElement.setReason("not allowed");
				stackRequestElement.setView("");
			}
		}
		catch(AuthorizationException ex) {
			stackRequestElement.setResponseStatus(HttpStatus.FORBIDDEN);
			stackRequestElement.setReason("not allowed");
			stackRequestElement.setView("");
		}
	}

}
