package com.epam.bench.carrental.server.configurations.child;

import com.epam.bench.carrental.commons.services.*;
import com.epam.bench.carrental.server.configurations.CustomHttpInvokerServiceExporter;
import com.epam.bench.carrental.server.threadlocal.TenantId;
import com.sun.net.httpserver.HttpHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.support.SimpleHttpServerFactoryBean;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChildSpringConfigurations {
    @Value("9090") int port;

    @Autowired
    ServerInformation serverInformationImpl;
    @Autowired
    CustomerService customerServiceImpl;
    @Autowired
    FleetService fleetServiceImpl;
    @Autowired
    CarRentalDetailService carRentalInformationImpl;
    @Autowired
    RentalClassService rentalClassServiceImpl;
    @Autowired
    ReportingService reportingServiceImpl;
    @Autowired
    TenantId tenantId;
    @Autowired
    CarBookingDetailService carBookingDetailImpl;

    @Bean
    public SimpleHttpServerFactoryBean SimpleHttpServerFactoryBean() {
        Map<String, HttpHandler> contextMap = new ConcurrentHashMap<>();
        SimpleHttpServerFactoryBean simpleHttpServerFactoryBean = new SimpleHttpServerFactoryBean();
        simpleHttpServerFactoryBean.setPort(port);
        simpleHttpServerFactoryBean.setExecutor(Executors.newFixedThreadPool(4));
        contextMap.put("/SpringHttpService/greeting.http", customHttpInvokerServiceExporter(serverInformationImpl, ServerInformation.class));
        contextMap.put("/SpringHttpService/customer.http", customHttpInvokerServiceExporter(customerServiceImpl, CustomerService.class));
        contextMap.put("/SpringHttpService/fleet.http", customHttpInvokerServiceExporter(fleetServiceImpl, FleetService.class));
        contextMap.put("/SpringHttpService/rentdetails.http", customHttpInvokerServiceExporter(carRentalInformationImpl, CarRentalDetailService.class));
        contextMap.put("/SpringHttpService/rentalClass.http", customHttpInvokerServiceExporter(rentalClassServiceImpl, RentalClassService.class));
        contextMap.put("/SpringHttpService/report.http", customHttpInvokerServiceExporter(reportingServiceImpl, ReportingService.class));
        contextMap.put("/SpringHttpService/booking.http", customHttpInvokerServiceExporter(carBookingDetailImpl, CarBookingDetailService.class));
        simpleHttpServerFactoryBean.setContexts(contextMap);
        return simpleHttpServerFactoryBean;
    }

    public CustomHttpInvokerServiceExporter customHttpInvokerServiceExporter(Object service, Class<?> serviceInterface) {
        CustomHttpInvokerServiceExporter simpleHttpInvokerServiceExporter = new CustomHttpInvokerServiceExporter(tenantId);
        simpleHttpInvokerServiceExporter.setService(service);
        simpleHttpInvokerServiceExporter.setServiceInterface(serviceInterface);
        simpleHttpInvokerServiceExporter.prepare();
        return simpleHttpInvokerServiceExporter;
    }
}
