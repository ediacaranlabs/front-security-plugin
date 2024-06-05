package br.com.uoutec.community.ediacaran.front.security;

import java.io.IOException;

import br.com.uoutec.community.ediacaran.front.security.pub.AuthenticationMethodBuilder;
import br.com.uoutec.community.ediacaran.front.security.pub.SubjectRequestListener;
import br.com.uoutec.community.ediacaran.front.security.pub.WebSecurityManagerPlugin;
import br.com.uoutec.community.ediacaran.front.security.tomcat.ContextConfigurerListener;
import br.com.uoutec.community.ediacaran.security.SecurityRegistryException;
import br.com.uoutec.community.ediacaran.system.i18n.Plugini18nManager;
import br.com.uoutec.ediacaran.core.AbstractPlugin;
import br.com.uoutec.ediacaran.core.EdiacaranListenerManager;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

public class PluginInstaller extends AbstractPlugin{
	
	public void install() throws Throwable{
		applySecurityConfiguration();
		registerListeners();
		registeri18n();
	}

	private void registeri18n() throws IOException {
		Plugini18nManager pi18n = EntityContextPlugin.getEntity(Plugini18nManager.class);
		pi18n.registerLanguages();
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
			.form()
				.setOption(AuthenticationMethodBuilder.LOGIN_PAGE, "/login")
				.setOption(AuthenticationMethodBuilder.ERROR_PAGE, "/login?error=true");
		
	}
	
	private void registerListeners() {

		EdiacaranListenerManager ediacaranListenerManager =
				EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		
		ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(ContextConfigurerListener.class));
		ediacaranListenerManager.addListener(EntityContextPlugin.getEntity(SubjectRequestListener.class));
	}
	
	@Override
	public void uninstall() throws Throwable {
		removeListeners();
		unregisteri18n();
	}

	private void unregisteri18n() throws IOException {
		Plugini18nManager pi18n = EntityContextPlugin.getEntity(Plugini18nManager.class);
		pi18n.unregisterLanguages();
	}
	
	private void removeListeners() {
		
		EdiacaranListenerManager ediacaranListenerManager =
				EntityContextPlugin.getEntity(EdiacaranListenerManager.class);
		
		ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(ContextConfigurerListener.class));
		ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(SubjectRequestListener.class));
		
		//ediacaranListenerManager.removeListener(EntityContextPlugin.getEntity(SecurityListener.class));
	}
	
}
