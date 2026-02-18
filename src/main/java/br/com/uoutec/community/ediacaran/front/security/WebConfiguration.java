package br.com.uoutec.community.ediacaran.front.security;

import org.brandao.brutos.annotation.ComponentScan;
import org.brandao.brutos.annotation.Configuration;

import br.com.uoutec.ediacaran.web.EdiacaranWebScanner;

@Configuration
@ComponentScan(basePackage=WebConfiguration.class, scanner = EdiacaranWebScanner.class)
public class WebConfiguration {

}
