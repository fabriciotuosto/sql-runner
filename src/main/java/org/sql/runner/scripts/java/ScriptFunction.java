package org.sql.runner.scripts.java;

import java.io.Writer;

import org.excel4j.ExcelRow;

import com.google.inject.ImplementedBy;

@ImplementedBy(TwoIterationsTest.class)
public interface ScriptFunction {

	String getSheetName();
	
	void processRow(ExcelRow row , Writer out);
	
}
