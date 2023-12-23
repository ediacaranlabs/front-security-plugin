package br.com.uoutec.community.ediacaran.front.security.pub;

import javax.inject.Singleton;

import br.com.uoutec.community.ediacaran.security.SecurityRegistryException;
import br.com.uoutec.ediacaran.core.ContextManager;
import br.com.uoutec.ediacaran.core.EdiacaranEventListener;
import br.com.uoutec.ediacaran.core.EdiacaranEventObject;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

//@Singleton
@Deprecated
public class SecurityListener implements EdiacaranEventListener{

	@Override
	public void onEvent(EdiacaranEventObject event) {
	
		if(event.getSource() instanceof ContextManager) {
			
			if("initializing".equals(event.getType())){
				initializing();
			}
			else
			if("destroying".equals(event.getType())){
				destroying();
			}
				
		}
		
	}

	private void initializing() {
		try {
			install();
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void destroying() {
		try {
			uninstall();
		}
		catch(Throwable ex) {
			throw new RuntimeException(ex);
		}
	}

	protected void install() throws SecurityRegistryException {
		
		WebSecurityManagerPlugin webSecurityManagerPlugin = 
				EntityContextPlugin.getEntity(WebSecurityManagerPlugin.class);
	
		webSecurityManagerPlugin
			.addConstraint("/admin/manager/*")
				.addRole("manager")
				.addRole("user")
			.addConstraint("/admin/*")
				.addRole("user")
			.form("/login", "/login?error=true");
		
	}

	protected void uninstall() {
	}
	
}
