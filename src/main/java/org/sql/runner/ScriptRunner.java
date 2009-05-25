package org.sql.runner;

import org.excel4j.ExcelRepository;
import org.sql.runner.annotations.PerforamanceLog;
import org.sql.runner.guice.ConfigurationModule;
import org.sql.runner.scripts.EngineModule;
import org.sql.runner.scripts.ScriptEngineDirector;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.name.Named;

public class ScriptRunner
{
	
	private final ScriptEngineDirector director;
	private final ExcelRepository repository;
	@Inject @Named("script.path") String path;
	
	@Inject
	public ScriptRunner(ExcelRepository repository,ScriptEngineDirector director) {
		this.repository   = repository;
		this.director     = director;
	}
	
	public static void main(String[] args) throws Exception
	{
		Injector injector = Guice.createInjector(Stage.PRODUCTION,new ConfigurationModule(),new EngineModule());
		ScriptRunner script = injector.getInstance(ScriptRunner.class);
		script.processExcles();
	}
	
	@PerforamanceLog
	public void processExcles() throws Exception
	{
		repository.add(path);
		director.runScript();
	}

}
