package br.com.uoutec.community.ediacaran.front.security.pub;

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
