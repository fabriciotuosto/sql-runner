package org.sql.runner;

import org.apache.commons.beanutils.MethodUtils;


public final class Closables {
	private Closables() {}
	
	/**
	 * Invokes via reflection close method with no parameters
	 * @param <T>
	 * @param closableItmes
	 * @throws Exception if the method close without parameters doesn't exist in the Class of the parameter
	 * or invoking the close method throws an exceptions
	 */
	public static <T> void close(T... closableItmes) throws Exception
	{
		Exception exception = null;
		for (T t : closableItmes)
		{
			if (t != null){
				try{
					invokeMethod("close", t);
				}catch (Exception e)
				{
					exception = e;
				}
			}
		}
		if ( exception != null) throw exception;
	}
 
	/**
	 * 
	 * @param method
	 * @param target
	 * @throws Exception
	 */
	private static void invokeMethod(String method, Object target) throws Exception
	{
		MethodUtils.invokeExactMethod(target, method, null);
	}
 
	/**
	 * Invokes via reflection close method with no parameters
	 * @param <T>
	 * @param closableItmes
	 */
	public static <T> void closeQuietly(T... closableItmes)
	{
		try{
			close(closableItmes);
		}catch (Exception e){} // shhhhh !!
	}

}
