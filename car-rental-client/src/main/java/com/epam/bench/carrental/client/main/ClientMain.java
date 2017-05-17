package com.epam.bench.carrental.client.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epam.bench.carrental.client.configurations.SpringConfigurations;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.env.SimpleCommandLinePropertySource;

public class ClientMain {
    public static void main(String[] args) {
        SimpleCommandLinePropertySource simpleCommandLinePropertySource = new SimpleCommandLinePropertySource(args);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().getPropertySources().addFirst(simpleCommandLinePropertySource);
        applicationContext.register(SpringConfigurations.class);
        applicationContext.refresh();
    }
}