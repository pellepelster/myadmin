/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */

package org.gwtwidgets.server.spring;

import java.lang.reflect.Method;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * Method interceptor that injects the current {@link HttpServletRequest} and
 * the current {@link HttpServletResponse} via reflection to a service. You must
 * specify the setter names via {@link #setRequestSetterName(String)} and
 * {@link #setResponseSetterName(String)} otherwise they will fail silently.
 * These setters can and must have only a single argument namely
 * {@link ServletRequest} and {@link ServletResponse} respectively.
 * 
 * @author George Georgovassilis, g.georgovassilis[at]gmail.com
 */
public class RequestInjection implements MethodInterceptor
{

	protected String requestSetterName;

	protected String responseSetterName;

	private void setRequestOnTarget(HttpServletRequest request, HttpServletResponse response, Object target) throws Exception
	{
		if (requestSetterName != null)
			try
			{
				Method method = target.getClass().getMethod(requestSetterName, new Class[] { HttpServletRequest.class });
				method.invoke(target, new Object[] { request });
			}
			catch (NoSuchMethodException e)
			{
			}
		if (responseSetterName != null)
			try
			{
				Method method = target.getClass().getMethod(responseSetterName, new Class[] { HttpServletResponse.class });
				method.invoke(target, new Object[] { response });
			}
			catch (NoSuchMethodException e)
			{
			}
	}

	public Object invoke(MethodInvocation invocation) throws Throwable
	{
		Object target = invocation.getThis();
		setRequestOnTarget(ServletUtils.getRequest(), ServletUtils.getResponse(), target);
		return invocation.proceed();
	}

	/**
	 * Specify the name of the setter method that can be invoked to set the
	 * current request and response on the service. If the method does not exist
	 * on the service, it is silently discarded.
	 * 
	 * @param setterName
	 */
	public void setRequestSetterName(String setterName)
	{
		this.requestSetterName = setterName;
	}

	/**
	 * Specify the name of the setter method that can be invoked to set the
	 * current response on the service. If the method does not exist on the
	 * service, it is silently discarded.
	 * 
	 * @param setterName
	 */
	public void setResponseSetterName(String setterName)
	{
		this.responseSetterName = setterName;
	}

}
