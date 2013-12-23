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

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.HttpRequestHandler;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * This component publishes an object (see {@link #setService(Object)} ) as a
 * service to the GWT RPC protocol. Service targets can be:
 * <ul>
 * <li>POJOs which don't have to extend any class or implement any interface.
 * However you should provide a service interface (see
 * {@link #setServiceInterfaces(Class[])} )</li>
 * <li>POJOs which implement {@link RemoteService}</li>
 * <li>You can extend the GWTRPCServiceExporter which assigns the target service
 * to itself.</li>
 * </ul>
 * Please note that exceptions implementing {@link IsSerializable} will be
 * propagated back to the client. Contrary to GWT 1.4's new interpetation of
 * {@link Serializable} and {@link IsSerializable} equality, exceptions
 * implementing {@link Serializable} are <i>not</i> propagated to the client.
 * 
 * @author George Georgovassilis, g.georgovassilis[at]gmail.com
 */
@SuppressWarnings("rawtypes")
public class GWTRPCServiceExporter extends ServletAdapter implements HttpRequestHandler
{

	private static Logger logger = Logger.getLogger(GWTRPCServiceExporter.class);
	private static final long serialVersionUID = 4424267386489335423L;
	protected Object service = this;
	protected Class[] serviceInterfaces;

	@Override
	public void afterPropertiesSet() throws Exception
	{
		if (this.service == null)
		{
			throw new Exception("You must specify a service object.");
		}
		if (this.serviceInterfaces == null)
		{
			logger.debug("Discovering service interfaces");
			this.serviceInterfaces = ReflectionUtils.getExposedInterfaces(this.service.getClass());
			if (this.serviceInterfaces.length == 0)
			{
				logger.warn("The specified service does neither implement RemoteService nor were any service interfaces specified. RPC access to *all* object methods is allowed.");
			}
		}
	}

	public Object getService()
	{
		return this.service;
	}

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			ServletUtils.setRequest(request);
			ServletUtils.setResponse(response);
			doPost(request, response);
		}
		finally
		{
			ServletUtils.setRequest(null);
			ServletUtils.setResponse(null);
		}

	}

	@Override
	protected void onAfterResponseSerialized(String serializedResponse)
	{
		logger.trace("Serialised RPC response: [" + serializedResponse + "]");
	}

	@Override
	protected void onBeforeRequestDeserialized(String serializedRequest)
	{
		logger.trace("Serialised RPC request: [" + serializedRequest + "]");
	}

	/**
	 * Overridden from {@link RemoteServiceServlet} and invoked by the servlet
	 * code.
	 */
	@Override
	public String processCall(String payload) throws SerializationException
	{
		try
		{
			RPCRequest rpcRequest = RPC.decodeRequest(payload, this.service.getClass(), this);
			onAfterRequestDeserialized(rpcRequest);
			return RPC.invokeAndEncodeResponse(this.service, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest.getSerializationPolicy());
		}
		catch (IncompatibleRemoteServiceException ex)
		{
			logger.error("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
			return RPC.encodeResponseForFailure(null, ex);
		}
	}

	/**
	 * Set the service service. RPC requests are decoded and the corresponding
	 * method of the service object is invoked.
	 * 
	 * @param service
	 */
	public void setService(Object service)
	{
		this.service = service;
	}

	/**
	 * Specifies the interfaces which must be implemented by the service bean.
	 * If not specified then any interface extending {@link RemoteService} which
	 * is implemented by the service bean is assumed.
	 * 
	 * @param serviceInterfaces
	 */
	public void setServiceInterfaces(Class[] serviceInterfaces)
	{
		this.serviceInterfaces = serviceInterfaces;
	}

}
