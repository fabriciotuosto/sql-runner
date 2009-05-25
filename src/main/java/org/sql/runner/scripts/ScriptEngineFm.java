package org.sql.runner.scripts;

import java.io.File;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.excel4j.ExcelRepository;
import org.utils.Timer;
import org.utils.Utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

@Singleton
class ScriptEngineFm implements ScriptEngine{

	private Configuration cfg = null;
	private Map<String,Object> context = new HashMap<String, Object>();
	
	public ScriptEngineFm() {}
	@Inject
	public ScriptEngineFm(@Named("freemarker.base.dir") String freemakerBaseDir,ExcelRepository respository)
	{
        try {
			/* Create and adjust the configuration */
			cfg = new Configuration();
			ClassTemplateLoader classloader = new ClassTemplateLoader(getClass(),"/");
			FileTemplateLoader  fileloader  = new FileTemplateLoader(new File(freemakerBaseDir));
			TemplateLoader[] loaders = {classloader,fileloader};
			MultiTemplateLoader multiLoader = new MultiTemplateLoader(loaders);
			cfg.setTemplateLoader(multiLoader);
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			// Initialize Velocity context to read the excel
			context.put("repository", respository);
			context.put("timer", new Timer());
			context.put("runner",this);
			context.put("util", new Utils());
			context.put("name","Lorena");
		} catch (Exception e) {
			throw new Error(e);
		}
	}
	
	public void runScript(String path,Writer writer) throws Exception
	{
		// Validates Parameters
		Validate.notNull(path,"El nombre del template no peude ser null");
		Template template = cfg.getTemplate(path);
		Validate.notNull(template,"El template no existe en la configuracion dada");
		template.process(context, writer);
	}
	
}
