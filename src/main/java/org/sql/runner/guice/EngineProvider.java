package org.sql.runner.guice;

import org.sql.runner.annotations.Freemarker;
import org.sql.runner.scripts.ScriptEngine;
import org.sql.runner.scripts.ref.PerformanceLogInvocationHandler;
import org.utils.proxy.ProxyFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class EngineProvider implements Provider<ScriptEngine> {

	public final ScriptEngine engine;
	
	@Inject
	public EngineProvider(@Freemarker ScriptEngine engine) {
	    this.engine = ProxyFactory.newProxyInstance(engine, PerformanceLogInvocationHandler.newInstance(engine)); 
	}
	
	public ScriptEngine get() {
		return engine;
	}

}
