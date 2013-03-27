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

import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The GWTHandler implements a Spring {@link HandlerMapping} which maps RPC from
 * URLs to {@link RemoteService} implementations. It does so by wrapping service
 * beans with a {@link GWTRPCServiceExporter} dynamically proxying all
 * {@link RemoteService} interfaces implemented by the service and delegating
 * RPC to these interfaces to the service.
 * 
 * Please note that {@link #setInterceptors(Object[])} applies interceptors on
 * the HTTP request level and not on the serialised or deserialised RPC payload.
 * Any interceptors provided here will advise the GWTHandler and not the
 * delegate services, thus exceptions thrown are not propagated to the client.
 * 
 * @author George Georgovassilis, g.georgovassilis[at]gmail.com
 * 
 */
public class GWTHandler extends AbstractUrlHandlerMapping implements HandlerMapping, InitializingBean
{

	// temporary mapping, void after bean initialisation
	private Map<String, Object> mapping;

	private boolean compressionEnabled = true;

	/**
	 * Invoked automatically by Spring after initialisation.
	 */
	public void afterPropertiesSet() throws Exception
	{
		for (Map.Entry<String, Object> entry : mapping.entrySet())
		{
			registerHandler(entry.getKey(), createServiceInstance(entry.getValue(), ReflectionUtils.getExposedInterfaces(entry.getValue().getClass())));
		}
		this.mapping = null;
	}

	@SuppressWarnings("rawtypes")
	private GWTRPCServiceExporter createServiceInstance(Object service, Class[] serviceInterfaces)
	{
		GWTRPCServiceExporter exporter = new GWTRPCServiceExporter();
		exporter.setServletContext(getServletContext());
		exporter.setService(service);
		exporter.setServiceInterfaces(serviceInterfaces);
		exporter.setCompressionEnabled(isCompressionEnabled());
		try
		{
			exporter.afterPropertiesSet();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return exporter;
	}

	public boolean isCompressionEnabled()
	{
		return compressionEnabled;
	}

	/**
	 * Enables (default) compression of RPC response. Follows
	 * {@link RemoteServiceServlet} conventions on compression.
	 * 
	 * @param compressionEnabled
	 */
	public void setCompressionEnabled(boolean compressionEnabled)
	{
		this.compressionEnabled = compressionEnabled;
	}

	/**
	 * Old version of {@link #setMappings(Map)}
	 * 
	 * @param mapping
	 * @deprecated
	 */
	@Deprecated
	public void setMapping(Map<String, Object> mapping)
	{
		setMappings(mapping);
	}

	/**
	 * Set a mapping between URLs and services
	 * 
	 * @param mapping
	 */
	public void setMappings(Map<String, Object> mapping)
	{
		this.mapping = mapping;
	}

}
