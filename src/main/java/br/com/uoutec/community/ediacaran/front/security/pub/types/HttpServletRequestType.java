package br.com.uoutec.community.ediacaran.front.security.pub.types;

import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.MvcResponse;
import org.brandao.brutos.RequestProvider;
import org.brandao.brutos.type.AbstractType;

public class HttpServletRequestType 
	extends AbstractType{

	public Object convert(Object value) {
		return (HttpServletRequest)RequestProvider.getRequest();
	}

	@Override
	public void show(MvcResponse response, Object value) {
		
	}
	
}
