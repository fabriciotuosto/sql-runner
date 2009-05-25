package org.sql.runner.scripts.java;

import java.io.Writer;

import org.excel4j.ExcelRow;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProductoComercialFunction.class)
public interface ScriptFunction {

	String getSheetName();
	
	void processRow(ExcelRow row , Writer out);
	
}
