package br.com.uoutec.community.ediacaran.front.security.tomcat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.catalina.Context;
import org.apache.catalina.realm.JAASRealm;

import br.com.uoutec.community.ediacaran.front.security.pub.LoginRedirectFilter;
import br.com.uoutec.community.ediacaran.front.security.pub.SecurityConfig;
import br.com.uoutec.community.ediacaran.front.security.pub.WebSecurityManagerPlugin;
import br.com.uoutec.community.ediacaran.security.AbstractSecurityCallback;
import br.com.uoutec.community.ediacaran.security.SecurityConstraint;
import br.com.uoutec.community.ediacaran.security.jaas.RolePrincipal;
import br.com.uoutec.community.ediacaran.security.jaas.UserPrincipal;
import br.com.uoutec.ediacaran.core.ContextManager;
import br.com.uoutec.ediacaran.core.EdiacaranEventListener;
import br.com.uoutec.ediacaran.core.EdiacaranEventObject;
import br.com.uoutec.ediacaran.core.plugins.EntityContextPlugin;

@Singleton
public class ContextConfigurerListener implements EdiacaranEventListener{

	public static final String LOGIN_PAGE = "/plugins/ediacaran/front-security/login";
	
	public static final String LOGOUT_PAGE = "/plugins/ediacaran/front-security/logout";
	
	public ContextConfigurerListener() {
	}

	@Override
	public void onEvent(EdiacaranEventObject event) {
		
		if(event.getSource() instanceof ContextManager && event.getData() instanceof Context) {
			
			final Context ctx = (Context)event.getData();
			final WebSecurityManagerPlugin smp = EntityContextPlugin.getEntity(WebSecurityManagerPlugin.class);
			
			if(smp == null) {
				return;
			}
			
			if("initializing".equals(event.getType())) {
				
				smp.applySecurityConfig(new AbstractSecurityCallback() {
					
					@Override
					public void applySecurityConfig(Object value) {
						ContextConfigurerListener.this.applySecurityConfig((SecurityConfig)value, ctx);
					}
					
				});
				
			}
			else
			if("destroying".equals(event.getType())) {

				smp.applySecurityConfig(new AbstractSecurityCallback() {
					
					@Override
					public void destroySecurityConfig(Object value) {
						ContextConfigurerListener.this.destroySecurityConfig((SecurityConfig)value, ctx);
					}
					
				});
				
			}
		}
	}
	
	public void applySecurityConfig(SecurityConfig value, Context context) {
		
		if(value.getMethod() == null) {
			return;
		}
		
		ContextConfigurer contextConfigurer = new ContextConfigurer(context);
		
		addSecurityConstraint(value.getConstraints(), contextConfigurer);
		
		addRoles(value, contextConfigurer);
		
		if(value.getLoginPage() != null) {
			contextConfigurer.setLoginConfig(value.getMethod(), value.getRealmName() == null? "ediacaranRealm" : value.getRealmName(), "/login", "/login?error");
			
			contextConfigurer.addFilter(
					"LoginRedirectFilter", 
					new LoginRedirectFilter(LOGIN_PAGE, LOGOUT_PAGE), 
					Arrays.asList("/login", "/logout"), 
					Arrays.asList("REQUEST", "FORWARD", "INCLUDE", "ERROR"),
					new HashMap<String,String>());
			
		}
		else {
			contextConfigurer.setLoginConfig(value.getMethod(), value.getRealmName() == null? "ediacaranRealm" : value.getRealmName(), null, null);
		}
		
		registerRealm(context);
	}

	public void destroySecurityConfig(SecurityConfig value, Context context) {
		
	}
	
	private void registerRealm(Context context) {
        JAASRealm realm = new JAASRealm();
        realm.setAppName("default");
        realm.setUserClassNames(UserPrincipal.class.getName());
        realm.setRoleClassNames(RolePrincipal.class.getName());
        context.setRealm(realm);
	}
	
	private void addRoles(SecurityConfig value, ContextConfigurer contextConfigurer) {
		
		Set<String> allRoles = getAllRoles(value);
		for(String r: allRoles) {
			contextConfigurer.addRole(r);
		}
		
	}
	
	private void addSecurityConstraint(Set<SecurityConstraint> value, ContextConfigurer contextConfigurer) {
		
		Map<SecurityConstraintGroup,Map<SecurityCollectionGroup,List<String>>> securityConstraintGroups = 
				createSecurityConstraintGroup(value);
		
		int scgID   = 0;
		int scolgID = 0;
		
		for(Entry<SecurityConstraintGroup,Map<SecurityCollectionGroup,List<String>>> sgcKey: securityConstraintGroups.entrySet()) {
			
			Map<SecurityCollectionGroup,List<String>> scolg = sgcKey.getValue();
			
			List<String> securityCollections = new ArrayList<String>();
			
			for(Entry<SecurityCollectionGroup,List<String>> scolgKey: scolg.entrySet()) {
				
				String scID = scgID + "-" + scolgID;
				
				contextConfigurer.addSecurityCollection(
						scID, 
						null, 
						new ArrayList<String>(scolgKey.getValue()), 
						scolgKey.getKey().methods == null? null : new ArrayList<String>(scolgKey.getKey().methods)
				);
				
				securityCollections.add(scID);
				
				scolgID++;
			}
			
			contextConfigurer.addSecurityConstraint(
					String.valueOf(scgID), 
					sgcKey.getKey().userConstraint, 
					new ArrayList<String>(sgcKey.getKey().roles), 
					securityCollections
			);
			
			scgID++;
		}
		
	}

	private Map<SecurityConstraintGroup,Map<SecurityCollectionGroup,List<String>>> createSecurityConstraintGroup(
			Set<SecurityConstraint> value) {
		
		Map<SecurityConstraintGroup,Map<SecurityCollectionGroup,List<String>>> securityConstraintGroups = 
				new HashMap<SecurityConstraintGroup,Map<SecurityCollectionGroup,List<String>>>();
		
		for(SecurityConstraint sc: value) {
			SecurityConstraintGroup scgKey = new SecurityConstraintGroup(null, sc.getUserConstraint(), sc.getRoles());
			
			Map<SecurityCollectionGroup,List<String>> scg = securityConstraintGroups.get(scgKey);
			
			if(scg == null) {
				scg = new HashMap<SecurityCollectionGroup,List<String>>();
				securityConstraintGroups.put(scgKey, scg);
			}
		
			SecurityCollectionGroup scolgKey = new SecurityCollectionGroup(sc.getMethods());
			
			List<String> uris = scg.get(scolgKey);
			
			if(uris == null) {
				uris = new ArrayList<String>();
				scg.put(scolgKey, uris);
			}
			
			uris.add(sc.getPattern());

		}
		
		return securityConstraintGroups;
		
	}
	
	private Set<String> getAllRoles(SecurityConfig value){
		
		Set<String> r = new HashSet<String>();
		Set<SecurityConstraint> constraints = value.getConstraints();
		
		if(constraints != null) {
			
			for(SecurityConstraint sc: constraints) {
				
				Set<String> roles = sc.getRoles();
				
				if(roles != null) {
					
					for(String role: roles) {
						r.add(role);
					}
					
				}
				
			}
			
		}
		
		return r;
	}
	
	private static class SecurityCollectionGroup{
		
		public Set<String> methods;
		
		public SecurityCollectionGroup(Set<String> methods) {
			this.methods = methods;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((methods == null) ? 0 : methods.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SecurityCollectionGroup other = (SecurityCollectionGroup) obj;
			if (methods == null) {
				if (other.methods != null)
					return false;
			} else if (!methods.equals(other.methods))
				return false;
			return true;
		}
		
	}
	
	private static class SecurityConstraintGroup{
		
		public String userConstraint;
		
		public Set<String> roles;
		
		public SecurityConstraintGroup(String name, String userConstraint, Set<String> roles) {
			this.userConstraint = userConstraint;
			this.roles = null;
			
			if(roles != null) {
				this.roles = new HashSet<String>();
				for(String r: roles) {
					this.roles.add(r);
				}
			}
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((roles == null) ? 0 : roles.hashCode());
			result = prime * result + ((userConstraint == null) ? 0 : userConstraint.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SecurityConstraintGroup other = (SecurityConstraintGroup) obj;
			if (roles == null) {
				if (other.roles != null)
					return false;
			} else if (!roles.equals(other.roles))
				return false;
			if (userConstraint == null) {
				if (other.userConstraint != null)
					return false;
			} else if (!userConstraint.equals(other.userConstraint))
				return false;
			return true;
		}

		
	}
	
}
