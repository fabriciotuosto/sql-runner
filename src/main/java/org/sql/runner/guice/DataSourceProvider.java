package org.sql.runner.guice;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.DbUtils;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class DataSourceProvider implements Provider<DataSource>{

	private final DataSource dataSource;
	
	@Inject
	public DataSourceProvider(
			@Named("username")       String user,
			@Named("password")       String pass,
			@Named("url")            String url,
			@Named("driverClassName")String driver) throws SQLException{
		BasicDataSource tempDataSource = new BasicDataSource();
		tempDataSource.setDriverClassName(driver);
		tempDataSource.setUrl(url);
		tempDataSource.setUsername(user);
		tempDataSource.setPassword(pass);
		//Test if connection works
		DbUtils.close(tempDataSource.getConnection());
		// If it works it sets the dataSource
		dataSource = tempDataSource;
	}
	
	public DataSource get() {
		return dataSource;
	}

}
