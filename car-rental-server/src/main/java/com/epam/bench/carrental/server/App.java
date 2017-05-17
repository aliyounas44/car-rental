package com.epam.bench.carrental.server;

import com.epam.bench.carrental.server.configurations.ParentSpringConfigurations;
import com.epam.bench.carrental.server.configurations.child.ChildSpringConfigurations;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	public static void main( String[] args ) {
		AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext(ParentSpringConfigurations.class);

		AnnotationConfigApplicationContext childContext = new AnnotationConfigApplicationContext();
		childContext.register(ChildSpringConfigurations.class);
		childContext.setParent(parentContext);
		childContext.refresh();
	}
}