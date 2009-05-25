package org.sql.runner.scripts;

import java.io.Writer;

import org.excel4j.ExcelRepository;
import org.excel4j.ExcelRow;
import org.sql.runner.scripts.java.ScriptFunction;

import com.google.inject.Inject;

public class ScriptEngineJava implements ScriptEngine {

	private final ExcelRepository repo;
	private final ScriptFunction function;
	
	@Inject
	public ScriptEngineJava(ExcelRepository repo,ScriptFunction function) {
		this.repo = repo;
		this.function = function;
	}
	
	public void runScript(String path, Writer writer) throws Exception {
		for ( ExcelRow row : repo.getSheet(function.getSheetName()))
		{
			function.processRow(row, writer);
		}
	}

}