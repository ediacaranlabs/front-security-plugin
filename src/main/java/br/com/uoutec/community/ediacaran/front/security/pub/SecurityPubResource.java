package br.com.uoutec.community.ediacaran.front.security.pub;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.annotation.Action;
import org.brandao.brutos.annotation.ActionStrategy;
import org.brandao.brutos.annotation.Basic;
import org.brandao.brutos.annotation.Controller;
import org.brandao.brutos.annotation.MappingTypes;
import org.brandao.brutos.annotation.Result;
import org.brandao.brutos.annotation.ScopeType;
import org.brandao.brutos.annotation.Transient;
import org.brandao.brutos.annotation.View;
import org.brandao.brutos.annotation.web.RequestMethod;
import org.brandao.brutos.annotation.web.RequestMethodTypes;
import org.brandao.brutos.annotation.web.ResponseError;
import org.brandao.brutos.annotation.web.ResponseErrors;
import org.brandao.brutos.annotation.web.WebActionStrategyType;
import org.brandao.brutos.web.WebFlowController;

import br.com.uoutec.community.ediacaran.system.i18n.PluginLanguageUtils;
import br.com.uoutec.ediacaran.core.VarParser;
import br.com.uoutec.ediacaran.web.EdiacaranWebInvoker;
import br.com.uoutec.pub.entity.InvalidRequestException;

@Controller
@ActionStrategy(WebActionStrategyType.DETACHED)
@Action(value="/login", view=@View("${plugins.ediacaran.front_security.template}/admin/login/login"))
@ResponseError(rendered=false)
public class SecurityPubResource {

	@Transient
	@Inject
	protected VarParser varParser;
	
	@Action("/login")
	@RequestMethod(RequestMethodTypes.POST)
	@View("${plugins.ediacaran.front_security.template}/admin/login/login_result")
	@ResponseErrors(rendered=false, name="exception")
	@Result("refererResource")
	public String login(
			@Basic(bean="username")
			String username,
			@Basic(bean="password")
			String password,
			@Basic(bean="redirectTo")
			String redirectTo,
			@Basic(mappingType=MappingTypes.VALUE)
			HttpServletRequest request,			
			@Basic(bean=EdiacaranWebInvoker.LOCALE_VAR, scope=ScopeType.REQUEST, mappingType=MappingTypes.VALUE)
			Locale locale) throws InvalidRequestException {
		
		try{
			request.login(username, password);
		}
		catch(ServletException ex){
			if(!"This request has already been authenticated".equals(ex.getMessage())) {
				String error = PluginLanguageUtils
						.getMessageResourceString(
								SecurityPubResourceMessages.RESOURCE_BUNDLE,
								SecurityPubResourceMessages.login.error.invalid_data, 
								locale);
				throw new InvalidRequestException(error, ex);
			}
		}
		catch(Throwable ex){
			String error = PluginLanguageUtils
					.getMessageResourceString(
							SecurityPubResourceMessages.RESOURCE_BUNDLE,
							SecurityPubResourceMessages.login.error.invalid_data, 
							locale);
			throw new InvalidRequestException(error, ex);
		}
		

		if(redirectTo == null || redirectTo.trim().isEmpty() || !redirectTo.startsWith("/")) {
			return varParser.getValue("${plugins.ediacaran.front.landing_page}");	
		}
		
		return redirectTo;
	}
	
	@Action("/logout")
	public void logout(
			@Basic(mappingType=MappingTypes.VALUE)
			HttpServletRequest request) throws ServletException {
		try {
			request.logout();
		}
		finally {
			WebFlowController.redirectTo(varParser.getValue("${plugins.ediacaran.front.login_page}"));
		}
	}
	
}
