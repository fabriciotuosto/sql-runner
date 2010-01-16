package org.sql.runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class SQLRunner {

	private final JdbcTemplate jdbctemplate;
	private final FileFilter fileFilter;

	public static void main(String... args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring-context.xml");
		SQLRunner sqlRunner = ctx.getBean(SQLRunner.class);
		for (String path : args) {
			File file = new File(path);
			if (file.exists() && file.isDirectory()) {
				sqlRunner.processDir(file);
			}
		}
	}

	public SQLRunner(JdbcTemplate jdbctemplate, FileFilter fileFilter) {
		this.fileFilter = Preconditions.checkNotNull(fileFilter);
		this.jdbctemplate = Preconditions.checkNotNull(jdbctemplate);
	}

	public void processDir(File directory) throws Exception {
		File[] files = directory.listFiles(fileFilter);
		for (File file : files) {
			procesFile(file);
		}
	}

	public void procesFile(File file) throws Exception {
		System.out.printf("Procesando archivo %s%n", file.getName());
		Scanner scanner = null;
		Writer writer = null;
		String query = null;
		try {
			writer = new BufferedWriter(new FileWriter(file.getAbsolutePath()
					+ ".err"));
			scanner = new Scanner(file).useDelimiter(";");
			while (scanner.hasNext()) {
				query = StringEscapeUtils.unescapeJava(scanner.next()
						.trim());
				jdbctemplate.execute(query);
			}
		} catch (DataAccessException e) {
			System.err.printf("%s;%n",query);
		} finally {
			Closables.closeQuietly(writer,scanner);
		}

	}
}
