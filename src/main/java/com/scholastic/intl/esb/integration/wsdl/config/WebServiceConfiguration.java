package com.scholastic.intl.esb.integration.wsdl.config;

import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfiguration extends WsConfigurerAdapter {
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/customer/ws/*");
	}

	@Bean(name = "nzCustomerInterface")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema priceListSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CustomerInterfacePortType");
		wsdl11Definition.setLocationUri("/customer/ws/nzCustomerInterface");
		wsdl11Definition.setTargetNamespace("http://resource.integration.esb.intl.scholastic.com");
		wsdl11Definition.setSchema(priceListSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema priceListSchema() {
		return new SimpleXsdSchema(new ClassPathResource("customerFuseXsd.xsd"));
	}
	
	@Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(new WebserviceInterceptor());
    }
}
