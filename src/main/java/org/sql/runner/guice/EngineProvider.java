package org.sql.runner.guice;

import org.sql.runner.annotations.Freemarker;
import org.sql.runner.scripts.ScriptEngine;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class EngineProvider implements Provider<ScriptEngine> {

	public final ScriptEngine engine;
	
	@Inject
	public EngineProvider(@Freemarker ScriptEngine engine) {
	    this.engine = engine; 
	}
	
	public ScriptEngine get() {
		return engine;
	}

}
