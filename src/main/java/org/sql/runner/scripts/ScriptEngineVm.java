package org.sql.runner.scripts;

import java.io.Writer;
import java.util.Properties;

import org.apache.commons.lang.Validate;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.excel4j.ExcelRepository;
import org.utils.Timer;
import org.utils.Utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
class ScriptEngineVm implements ScriptEngine{

	private VelocityContext context;
	public ScriptEngineVm() {}
	
	@Inject
	public ScriptEngineVm(ExcelRepository repository)
	{
		try{
			// Loads velocity properties
			Properties properties = new Properties();
			properties.load(ClassLoader.getSystemResourceAsStream("configuration/velocity.properties"));
			Velocity.init(properties);
			
			// Initialize Velocity context to read the excel
			context = new VelocityContext();
			context.put("repository", repository);
			context.put("timerClass", Timer.class);
			context.put("runner",this);
			context.put("util", new Utils());
		}catch(Exception e){
			throw new Error(e);
		}
	}
	
	public void runScript(String path,Writer writer) throws Exception
	{
		// Validates Parameters
		Validate.notNull(path, "Path al script de velocity");			
		Validate.isTrue(Velocity.resourceExists(path),"El template no existe");
		// Load the template 
        Template template = Velocity.getTemplate(path );
        // Write the generated from the template in the file
        template.merge(context, writer);
	}
	
}
