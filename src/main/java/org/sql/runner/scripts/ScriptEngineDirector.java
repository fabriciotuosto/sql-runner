package org.sql.runner.scripts;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.sql.runner.annotations.Java;
import org.sql.runner.annotations.PerforamanceLog;
import org.sql.runner.scripts.out.CompositeWriter;
import org.utils.Utils;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ScriptEngineDirector {

	private final String OUTPUT_FILE_NAME;
	private final ScriptEngine engine;
	private final String script;
	
	@Inject
	public ScriptEngineDirector(@Named("output.file") String outputPath,
			                    @Named("script.name") String path,
			                    @Java ScriptEngine engine)
	{
		OUTPUT_FILE_NAME = outputPath;
		this.script = path;
		this.engine = engine;
	}
	
	@PerforamanceLog
	public ScriptEngineDirector runScript() throws Exception
	{
		Writer writer = null;
		try{
			writer = getWriter();
			engine.runScript(script,writer);
		}finally{
			Utils.closeQuietly(writer);
		}
		return this;
	}

	
	protected Writer getWriter() throws Exception
	{
		OutputStreamWriter file = CompositeWriter.decorateOutputSream(new FileOutputStream(OUTPUT_FILE_NAME));
		return new CompositeWriter(file);
	}
	
}
