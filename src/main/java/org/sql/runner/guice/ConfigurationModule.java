package org.sql.runner.guice;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import java.util.Properties;

import javax.sql.DataSource;

import org.sql.runner.annotations.PerforamanceLog;
import org.sql.runner.scripts.interceptors.PerformanceInterceptor;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class ConfigurationModule extends AbstractModule{

	private static final String[] confFiles = {"configuration/application.properties","configuration/database.properties"};
	
	private void bindProperties(Properties prop)
	{
		Names.bindProperties(binder(), prop);
	}

	@Override
	protected void configure() {
		for(String file : confFiles)
		{
			bindPropertiesFile(file);
		}
		
		bind(DataSource.class).
		toProvider(DataSourceProvider.class);
		
		bindInterceptor(any(), annotatedWith(PerforamanceLog.class), new PerformanceInterceptor());
	}

	private void bindPropertiesFile(String fileName) {
		Properties prop = new Properties();
		try{
			prop.load(ClassLoader.getSystemResourceAsStream(fileName));
			bindProperties(prop);
		}catch(Exception e){
			throw new Error(e);
		}
	}
}
