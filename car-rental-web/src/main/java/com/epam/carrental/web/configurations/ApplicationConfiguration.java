package com.epam.carrental.web.configurations;

import com.epam.bench.carrental.commons.services.CustomerService;
import com.epam.bench.carrental.commons.services.ServerInformation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.epam.carrental.web")
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
    private final String BASE_URL = "http://localhost:9090/SpringHttpService/";

    @Bean
    public HttpInvokerProxyFactoryBean getHttpInvokerProxyFactoryBean() {
        HttpInvokerProxyFactoryBean obj = new HttpInvokerProxyFactoryBean();
        obj.setServiceUrl(BASE_URL + "greeting.http");
        obj.setServiceInterface(ServerInformation.class);
        return obj;
    }

    @Bean
    public HttpInvokerProxyFactoryBean getHttpInvokerProxyCustomerFactoryBean() {
        HttpInvokerProxyFactoryBean obj = new HttpInvokerProxyFactoryBean();
        obj.setServiceUrl(BASE_URL + "customer.http");
        obj.setServiceInterface(CustomerService.class);
        return obj;
    }

    @Bean
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

}
