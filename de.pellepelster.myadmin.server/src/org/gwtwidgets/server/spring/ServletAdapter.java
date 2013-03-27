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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.pellepelster.gwt.commons.server.ProxyServletAwareRemoteServiceServlet;

/**
 * Base class that abstracts RemoteServiceServlet and introduces spring-related
 * dependencies.
 * 
 * @author George Georgovassilis, g.georgovassilis[at]gmail.com
 * 
 */
public class ServletAdapter extends ProxyServletAwareRemoteServiceServlet implements InitializingBean, ServletContextAware
{

	private static final long serialVersionUID = 3539878787917915268L;

	/**
	 * Return the request which invokes the service. Valid only if used in the
	 * dispatching thread.
	 * 
	 * @return the servlet request
	 */
	public static HttpServletRequest getRequest()
	{
		return ServletUtils.getRequest();
	}

	/**
	 * Return the response which accompanies the request. Valid only if used in
	 * the dispatching thread.
	 * 
	 * @return the servlet response
	 */
	public static HttpServletResponse getResponse()
	{
		return ServletUtils.getResponse();
	}

	protected Logger logger = Logger.getLogger(getClass());

	private boolean compressionEnabled = true;

	private ServletContext servletContext;

	@Override
	public void afterPropertiesSet() throws Exception
	{
	}

	/**
	 * @return
	 */
	@Override
	public ServletContext getServletContext()
	{
		return servletContext;
	}

	public boolean isCompressionEnabled()
	{
		return compressionEnabled;
	}

	/**
	 * Enable or disable compression of RPC response. Note that even when
	 * enabled, the super class ({@link RemoteServiceServlet}) may disable it
	 * for certain responses.
	 * 
	 * @param compressionEnabled
	 */
	public void setCompressionEnabled(boolean compressionEnabled)
	{
		this.compressionEnabled = compressionEnabled;
	}

	/**
	 * @param servletContext
	 */
	@Override
	public void setServletContext(ServletContext servletContext)
	{
		this.servletContext = servletContext;
	}

	@Override
	protected boolean shouldCompressResponse(HttpServletRequest request, HttpServletResponse response, String responsePayload)
	{
		return isCompressionEnabled() && super.shouldCompressResponse(request, response, responsePayload);
	}

}
