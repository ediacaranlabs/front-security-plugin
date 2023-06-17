package br.com.uoutec.community.ediacaran.front.security.pub;

import br.com.uoutec.community.ediacaran.system.i18n.PluginLanguageUtils;

public class SecurityPubResourceMessages {

	public static final String RESOURCE_BUNDLE = 
			PluginLanguageUtils
				.getPluginResourceBundle(SecurityPubResource.class);

	public final class login{
		
		public final class error{
			
			public static final String invalid_data      = "login.error.invalid_data";
			
			public static final String invalid_user_pass = "login.error.invalid_user_pass";
			
		}
		
	}
	
}
