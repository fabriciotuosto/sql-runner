package org.sql.runner;

class Preconditions {

	public static <T> T checkNotNull(T parameter){
		if (parameter == null){
			throw new IllegalArgumentException();
		}
		return parameter;
	} 
	
	public static String checkNotEmpty(String parameter){
		if (checkNotNull(parameter).length() < 1){
			throw new IllegalArgumentException();
		}
		return parameter;
	}
}
