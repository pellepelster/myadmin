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
 */package org.gwtwidgets.server.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class that allows access to the invoking servlet request and
 * response, which are stored in a thread local variable of the invoking thread.
 * 
 * @author George Georgovassilis, g.georgovassilis[at]gmail.com
 * 
 */
public class ServletUtils
{
	private static ThreadLocal<HttpServletRequest> servletRequest = new ThreadLocal<HttpServletRequest>();

	private static ThreadLocal<HttpServletResponse> servletResponse = new ThreadLocal<HttpServletResponse>();

	/**
	 * Return the request which invokes the service. Valid only if used in the
	 * dispatching thread.
	 * 
	 * @return the servlet request
	 */
	public static HttpServletRequest getRequest()
	{
		return servletRequest.get();
	}

	/**
	 * Return the response which accompanies the request. Valid only if used in
	 * the dispatching thread.
	 * 
	 * @return the servlet response
	 */
	public static HttpServletResponse getResponse()
	{
		return servletResponse.get();
	}

	/**
	 * Assign the current servlet request to a thread local variable. Valid only
	 * if used inside the invoking thread scope.
	 * 
	 * @param request
	 */
	public static void setRequest(HttpServletRequest request)
	{
		servletRequest.set(request);
	}

	/**
	 * Assign the current servlet response to a thread local variable. Valid
	 * only if used inside the invoking thread scope.
	 * 
	 * @param response
	 */
	public static void setResponse(HttpServletResponse response)
	{
		servletResponse.set(response);
	}

}
