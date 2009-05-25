package org.sql.runner.scripts;

import java.io.Writer;

public interface ScriptEngine {

	void runScript(String path, Writer writer) throws Exception;

}
