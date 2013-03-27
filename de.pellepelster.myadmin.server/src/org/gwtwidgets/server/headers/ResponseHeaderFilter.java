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

package org.gwtwidgets.server.headers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet Filter that applies any HTTP header to a matched URL. Init parameters
 * are HTTP header-value pairs. Will silently fail when the ServletResponse does
 * not cast to a {@link HttpServletResponse}. A special init-parameter is
 * 'ResponseHeaderFilter.UrlPattern': it is not set as an HTTP header but is
 * rather a regular expression which can be used to further refine URLs on which
 * the filter should match. Headers containing an empty value are removed from
 * the response, even if they already exist there.
 * 
 * Inspired by a very <a
 * href="http://www.onjava.com/pub/a/onjava/2004/03/03/filters.html"
 * >worthreading article</a> from Jayson Falkner.
 * 
 * @author George Georgovassilis, g.georgovassilis[at]gmail.com
 * 
 */
public class ResponseHeaderFilter implements Filter
{

	private String[] headers = new String[0];

	private String[] values = new String[0];

	private final Logger logger = Logger.getLogger(getClass());

	private Pattern urlPattern = Pattern.compile(".*?");

	public final static String URL_PATTERN = "ResponseHeaderFilter.UrlPattern";

	private void addHeaders(HttpServletRequest request, HttpServletResponse response)
	{
		response.setBufferSize(0);
		String query = request.getRequestURI();
		Matcher urlMatcher = urlPattern.matcher(query);
		if (!urlMatcher.matches())
		{
			return;
		}
		for (int i = 0; i < headers.length; i++)
		{
			response.setHeader(headers[i], values[i]);
		}
	}

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		if (response instanceof HttpServletResponse)
		{
			addHeaders((HttpServletRequest) request, (HttpServletResponse) response);
		}
		chain.doFilter(request, response);
	}

	@SuppressWarnings("rawtypes")
	private int getSize(Enumeration e)
	{
		int size = 0;
		for (; e.hasMoreElements(); e.nextElement(), size++)
		{
			;
		}
		return size;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public void init(FilterConfig conf) throws ServletException
	{
		int size = getSize(conf.getInitParameterNames());
		if (conf.getInitParameter(URL_PATTERN) != null)
		{
			size--;
			setupMatcher(conf.getInitParameter(URL_PATTERN));
		}
		headers = new String[size];
		values = new String[size];
		if (size == 0)
		{
			logger.warn("Instantiated ResponseHeaderFilter without header mappings");
			return;
		}
		logger.debug("ResponseHeaderFilter header mappings:");
		Enumeration e = conf.getInitParameterNames();
		for (int i = 0; i < size;)
		{
			String header = e.nextElement().toString();
			if (URL_PATTERN.equals(header))
			{
				continue;
			}
			headers[i] = header;
			values[i] = conf.getInitParameter(headers[i]);
			if ("".equals(values[i]))
			{
				values[i] = null;
			}
			logger.debug(headers[i] + " = " + values[i]);
			i++;
		}
	}

	private void setupMatcher(String pattern)
	{
		urlPattern = Pattern.compile(pattern);
		logger.debug("Matching " + pattern);
	}
}
