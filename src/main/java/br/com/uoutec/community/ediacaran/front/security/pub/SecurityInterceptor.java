package br.com.uoutec.community.ediacaran.front.security.pub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.ResourceAction;
import org.brandao.brutos.annotation.Intercepts;
import org.brandao.brutos.annotation.InterceptsStack;
import org.brandao.brutos.interceptor.AbstractInterceptor;
import org.brandao.brutos.interceptor.InterceptedException;
import org.brandao.brutos.interceptor.InterceptorHandler;
import org.brandao.brutos.interceptor.InterceptorStack;
import org.brandao.brutos.mapping.Action;
import org.brandao.brutos.mapping.Controller;
import org.brandao.brutos.web.WebMvcRequest;

import br.com.uoutec.community.ediacaran.VarParser;
import br.com.uoutec.community.ediacaran.security.AuthorizationManager;
import br.com.uoutec.community.ediacaran.security.RequiresPermissions;
import br.com.uoutec.community.ediacaran.security.Subject;

//@Singleton
//@Intercepts(isDefault=false)
//@InterceptsStack(name="securityStack", isdefault=true)
@Deprecated
public class SecurityInterceptor 
	/*extends AbstractInterceptor*/ {
/*
	public static final String ADM_CONTEXT = "${plugins.ediacaran.front.admin_context}";
	
	@Inject
	private AuthorizationManager securityManager;
	
	@Inject
	protected VarParser varParser;
	
	public void intercepted(InterceptorStack stack, InterceptorHandler handler)
			throws InterceptedException {
		
		Subject subject = securityManager.getSubject();
		
		if(subject == null || !subject.isAuthenticated()) {
			throw new SecurityException(
					"resource: " + handler.getRequest().getRequestId());
		}
		
		ResourceAction resourceAction         = handler.getRequest().getResourceAction();
		Controller controller                 = resourceAction.getController();
		Action action                         = resourceAction.getMethodForm();
		
		String[] requiresPermissions = getPermissions(controller, action);
		
		if(subject.isPermittedAll(requiresPermissions)) {
			stack.next(handler);
		}
		else {
			throw new SecurityException(
				"permissions: " + Arrays.toString(requiresPermissions) + 
				", resource: " + handler.getRequest().getRequestId());
		}
		
	}

	private String[] getPermissions(Controller controller, Action action) {
		
		RequiresPermissions requiresPermissions = null;
		List<String> permissions = new ArrayList<String>();
		
		if(action != null && action.getMethod() != null){
			requiresPermissions =
				action.getMethod().getAnnotation(RequiresPermissions.class);
			
			if(requiresPermissions != null){
				for(String e: requiresPermissions.value()) {
					permissions.add(e);
				}
			}
		}

		requiresPermissions = controller.getClassType().getAnnotation(RequiresPermissions.class);
		
		if(requiresPermissions != null){
			for(String e: requiresPermissions.value()) {
				permissions.add(e);
			}
		}
		
		return permissions.toArray(new String[] {});
	}
	
	public boolean accept(InterceptorHandler handler) {
		return ((HttpServletRequest)((WebMvcRequest)handler.getRequest()).getServletRequest()).getUserPrincipal() != null;
	}
*/	
}
