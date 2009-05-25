package org.sql.runner.scripts.ref;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.apache.commons.proxy.Invoker;
import org.utils.Timer;

public class PerformanceLogInvocationHandler<T> implements Invoker  {
	
	private final T delegate;
	
	private PerformanceLogInvocationHandler(T delegate) {
		this.delegate = delegate;
	}
	
	public static <T> PerformanceLogInvocationHandler<T> newInstance(T delegate)
	{
		return new PerformanceLogInvocationHandler<T>(delegate);
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println(String.format("Invoking method %s with arguments %s on delegate %s",
					method.getName(),Arrays.toString(args),delegate.toString()));
		Timer timer = new Timer();
		Object result = method.invoke(delegate, args);
		System.out.println(String.format("%s() -> %s",method.getName(),timer.elapsedTimeMessage()));
		return result;
	}

}
