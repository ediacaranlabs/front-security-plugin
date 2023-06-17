package br.com.uoutec.community.ediacaran.front.security.tomcat;

import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.apache.tomcat.util.descriptor.web.LoginConfig;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(ContextConfigurer.class);
	
	private Context context;
	
	private Map<String, SecurityCollection> securityCollections;
	
	public ContextConfigurer(Context context) {
		this.securityCollections = new HashMap<String, SecurityCollection>();
		this.context = context;
	}
	
	public void addApplicationListener(String className) {
		//context.addApplicationListener(new ApplicationListener(className, false));
		context.addApplicationListener(className);
	}

	public void addApplicationListener(EventListener listener) {
		context.setApplicationEventListeners(new Object[] {listener});
	}

	public void addFilter(String name, String filterClass, List<String> urls, 
			List<String> dispatches, Map<String, String> params) {
		
    	if(name == null) {
    		throw new NullPointerException("name");
    	}
    	
    	if(filterClass == null) {
    		throw new NullPointerException("filterClass");
    	}
    	
    	if(urls == null) {
    		throw new NullPointerException("urls");
    	}
    	
    	if(dispatches == null) {
    		throw new NullPointerException("dispatches");
    	}

    	if(urls.isEmpty()) {
    		throw new IllegalStateException("urls is emtpy");
    	}
    	
    	if(dispatches.isEmpty()) {
    		throw new IllegalStateException("dispatches is empty");
    	}
    	
		//filterdef - start
    	FilterDef filterDef = new FilterDef();
    	filterDef.setFilterName(name);
    	filterDef.setFilterClass(filterClass);
        
        if(params != null) {
	        for(Entry<String,String> param: params.entrySet()) {
	        	filterDef.addInitParameter(param.getKey(), param.getValue());
	        }
        }
        
        context.addFilterDef(filterDef);
        //filterdef - end
		
		//filtermap - start
		
		FilterMap fm = new FilterMap();
    	fm.setFilterName(name);
		
    	for(String url: urls) {
    		fm.addURLPattern(url);
    	}
    	
        for(String dspt: dispatches) {
        	fm.setDispatcher(dspt);
        }
        
    	context.addFilterMap(fm);
        
		//filtermap - end
		
		logger.trace("added filter " + filterClass ); 		
	}

	public void addFilter(String name, Filter filter, 
			List<String> urls, List<String> dispatches, Map<String, String> params) {
		
    	if(name == null) {
    		throw new NullPointerException("name");
    	}
    	
    	if(filter == null) {
    		throw new NullPointerException("filter");
    	}
    	
    	if(urls == null) {
    		throw new NullPointerException("urls");
    	}
    	
    	if(dispatches == null) {
    		throw new NullPointerException("dispatches");
    	}

    	if(urls.isEmpty()) {
    		throw new IllegalStateException("urls is emtpy");
    	}
    	
    	if(dispatches.isEmpty()) {
    		throw new IllegalStateException("dispatches is empty");
    	}
    	
		//filterdef - start
    	FilterDef filterDef = new FilterDef();
    	filterDef.setFilterName(name);
    	filterDef.setFilter(filter);
        
        if(params != null) {
	        for(Entry<String,String> param: params.entrySet()) {
	        	filterDef.addInitParameter(param.getKey(), param.getValue());
	        }
        }
        
		
        context.addFilterDef(filterDef);
        
        //filterdef - end
		
		//filtermap - start
		FilterMap fm = new FilterMap();
    	fm.setFilterName(name);
		
    	for(String url: urls) {
    		fm.addURLPattern(url);
    	}
    	
        for(String dspt: dispatches) {
        	fm.setDispatcher(dspt);
        }
        
        context.addFilterMap(fm);
        
		//filtermap - end
		
		logger.trace("added filter " + filter.getClass() ); 
		
	}

	public void addSecurityCollection(String name, String description, 
			List<String> patterns, List<String> methods ) {
		
    	if(name == null) {
    		throw new NullPointerException("name");
    	}
    	
    	if(patterns == null) {
    		throw new NullPointerException("pattern");
    	}
    	
    	if(patterns.isEmpty()) {
    		throw new IllegalStateException("pattern is empty");
    	}

        SecurityCollection e = new SecurityCollection();
        e.setName(name);
        e.setDescription(description);
        
        for(String p: patterns) {
        	e.addPattern(p);	
        }
        
        if(methods != null) {
            for(String p: methods) {
            	e.addMethod(p);	
            }
        	
        }
    	
        securityCollections.put(name, e);
		
		logger.trace("added security collection " + name );
		
	}

	public void addSecurityConstraint(String name, String userConstraint, List<String> rules,
			List<String> securityCollections) {
		
    	if(name == null) {
    		throw new NullPointerException("name");
    	}
		
    	if(rules == null) {
    		throw new NullPointerException("rules");
    	}
    	
    	if(rules.isEmpty()) {
    		throw new IllegalStateException("rules is empty");
    	}
    	
    	if(securityCollections == null) {
    		throw new NullPointerException("securityCollections");
    	}
    	
    	if(securityCollections.isEmpty()) {
    		throw new IllegalStateException("securityCollections is empty");
    	}
    	
        SecurityConstraint sc = new SecurityConstraint();
        
        for(String i: securityCollections) {
        	SecurityCollection e =  this.securityCollections.get(i);
        	
        	if(e == null) {
        		throw new NullPointerException(i + " not found");
        	}
        	
        	sc.addCollection(e);
        }

        for(String i: rules) {
        	sc.addAuthRole(i);
        }
        
        sc.setUserConstraint(userConstraint);
        sc.setDisplayName(name);
		
		context.addConstraint(sc);
		
		logger.trace("added security constraint " + name ); 		
	}

	public void addRole(String value) {
		context.addSecurityRole(value);
	}

	public void setLoginConfig(String authMethod, String realmName, String loginPage, String errorPage) {
        context.setLoginConfig(new LoginConfig(authMethod, realmName, loginPage, errorPage));
	}

}
