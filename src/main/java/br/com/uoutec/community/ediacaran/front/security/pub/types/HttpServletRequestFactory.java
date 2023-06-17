package br.com.uoutec.community.ediacaran.front.security.pub.types;

import javax.servlet.http.HttpServletRequest;

import org.brandao.brutos.annotation.TypeDef;
import org.brandao.brutos.type.DefaultTypeFactory;

@TypeDef
public class HttpServletRequestFactory extends DefaultTypeFactory{

	public HttpServletRequestFactory() {
		super(HttpServletRequestType.class, HttpServletRequest.class);
	}

}
