package br.com.uoutec.community.ediacaran.front.security;

import br.com.uoutec.community.ediacaran.front.security.pub.WebSecurityManagerPlugin;
import br.com.uoutec.community.ediacaran.front.security.tomcat.ContextConfigurerListener;
import br.com.uoutec.community.ediacaran.security.SecurityRegistryException;
import br.com.uoutec.ediacaran.core.AbstractPlugin;
import br.com.uoutec.ediacaran.core.EdiacaranListenerManager;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

public class PluginInstaller extends AbstractPlugin{
	
	public void install() throws Throwable{
		applySecurityConfiguration();
		registerListeners();
	}

	private void applySecurityConfiguration() throws SecurityRegistryException {
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
	
	private void registerListeners() {

		EdiacaranListenerManager ediacaranListenerManager =
				EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		
		//ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(SecurityListener.class));
		ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(ContextConfigurerListener.class));
		
	}
	
	@Override
	public void uninstall() throws Throwable {
		removeListeners();
	}
	
	private void removeListeners() {
		
		EdiacaranListenerManager ediacaranListenerManager =
				EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		
		ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(ContextConfigurerListener.class));
		//ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(SecurityListener.class));
	}
	
}
