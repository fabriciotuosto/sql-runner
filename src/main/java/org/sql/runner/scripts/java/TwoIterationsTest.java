package org.sql.runner.scripts.java;

import java.io.Writer;

import org.excel4j.ExcelRepository;
import org.excel4j.ExcelRow;

import com.google.inject.Inject;

public class TwoIterationsTest implements ScriptFunction
{
	private static final String SHEET_NAME = "Sheet10";
	private final ExcelRepository repo;
	
	@Inject
	public TwoIterationsTest(ExcelRepository repo)
	{
		this.repo = repo;
	}
	
	public String getSheetName() {
		return SHEET_NAME;
	}

	public void processRow(ExcelRow row, Writer out)
	{
		System.out.println(row.toString());
		// Aca podria ser otra hoja excel sobre la cual iterar
		for ( ExcelRow subElement : repo.getSheet(SHEET_NAME))
		{
			System.out.println("In SubElement");
			System.out.println(subElement.toString());
			break;
		}
	}
}
