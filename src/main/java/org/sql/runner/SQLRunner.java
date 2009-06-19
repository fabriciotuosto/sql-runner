package org.sql.runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.Validate;
import org.sql.runner.guice.ConfigurationModule;
import org.utils.Timer;
import org.utils.Utils;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Stage;

public class SQLRunner {

	private final DataSource dataSource;
	private QueryRunner runner; 
	private static final String[] ACCEPTED_EXTENSIONS = {"sql"};
	
	public static void main(String[] args) throws Exception 
	{
		Injector injector = Guice.createInjector(Stage.PRODUCTION,new ConfigurationModule());
		SQLRunner sqlRunner = injector.getInstance(SQLRunner.class);
		sqlRunner.processDirs(args);		
	}
	
	
	@Inject
	public SQLRunner(DataSource dataSource) {
		this.dataSource = dataSource;
		runner = new QueryRunner(this.dataSource);
	}
	

	public void processDir(File directory) throws Exception
	{
		@SuppressWarnings("unchecked")// Devuelve una coleccion de files
		Collection<File> files = FileUtils.listFiles(directory,ACCEPTED_EXTENSIONS, false);
		for( File file : files)
		{
			procesFile(file);
		}		
	}
	
	public void processDirs(String... dirsPath) throws Exception
	{
		Timer timer = new Timer();
		try{
			Validate.notEmpty(dirsPath,"Directorios vacios");
			for(String dirPath : dirsPath)
			{
				File directory = new File(dirPath);
				if (!directory.isDirectory())
				{ 
					System.out.println(dirPath + " no es un directorio");
					continue;
				}else{
					processDir(directory);
				}
			}
		}
		finally
		{
			System.out.println(timer.elapsedTimeMessage());
		}
	}
		

	public void procesFile(File file) throws Exception
	{
		System.out.printf("Procesando archivo %s%n",file.getName());
		Scanner scanner = null;
		Writer writer   = null;
		try{
			try{
				writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()+".err"));
				scanner = new Scanner(file).useDelimiter(";");
				while(scanner.hasNext())
				{
					String query = StringEscapeUtils.unescapeJava(scanner.next().trim());
					try{
						runner.update(query);
						System.out.println(query);
					}catch(SQLException e)
					{
						writer.write(query);
						writer.write(";"+SystemUtils.LINE_SEPARATOR);
						writer.flush();
						System.err.println(query);
					}
				}
			}finally
			{
				Utils.close(scanner,writer);				
			}
		}catch (Exception e) {
			throw new Error(e);
		}
	}


}
