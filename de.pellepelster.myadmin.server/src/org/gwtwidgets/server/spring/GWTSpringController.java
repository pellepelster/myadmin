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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Simple spring controller that merges GWT's {@link RemoteServiceServlet} , the
 * {@link Controller} and also implements the {@link RemoteService} interface so
 * as to be able to directly delegate RPC calls to extending classes.
 * 
 * @author George Georgovassilis, g.georgovassilis[at]gmail.com
 */
public class GWTSpringController extends ServletAdapter implements Controller, RemoteService
{
	private static final long serialVersionUID = 5399966488983189122L;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
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
		return null;
	}

}
