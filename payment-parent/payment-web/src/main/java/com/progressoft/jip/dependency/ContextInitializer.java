package com.progressoft.jip.dependency;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.support.FileSystemXmlApplicationContext;

@WebListener
public class ContextInitializer implements ServletContextListener {

	
	private static final String SPRING_CONTEXT = "spring-context";
	private static final String APP_CONTEXT_LOCATION = "app.context.location";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String appContextLocation = System.getProperty(APP_CONTEXT_LOCATION);
		FileSystemXmlApplicationContext appContext = new FileSystemXmlApplicationContext(appContextLocation);
		ServletContext servletContext = sce.getServletContext();
		servletContext.setAttribute(SPRING_CONTEXT, appContext);
		servletContext.setAttribute(ImplementationProvider.DEPENDENCY_PROVIDER, new SpringImplementationProvider(appContext));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
