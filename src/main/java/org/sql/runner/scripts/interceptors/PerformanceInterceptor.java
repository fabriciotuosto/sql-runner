package org.sql.runner.scripts.interceptors;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.utils.Timer;

public class PerformanceInterceptor implements MethodInterceptor
{

	public Object invoke(MethodInvocation arg0) throws Throwable {
		Timer timer = new Timer();
		Object result = arg0.proceed();
		System.out.println(String.format("%s() -> %s",arg0.getMethod().getName(),timer.elapsedTimeMessage()));
		return result;
	}
	
}
