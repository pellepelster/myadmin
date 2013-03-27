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
package de.pellepelster.myadmin.server.io;

import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;

public class FileDownloadController implements Controller
{
	private IFileStorage fileStorage;

	private final Logger LOG = Logger.getLogger(FileDownloadController.class);

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String fileName = request.getParameter("file");
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!(user instanceof MyAdminUserVO))
		{
			sendErrorHtml(response, "access denied");
			return null;
		}

		if (fileName == null)
		{
			sendErrorHtml(response, "no filename specified");
			return null;
		}

		LOG.debug(String.format("request for file '%s' by user '%s'", fileName, user.toString()));
		byte[] fileContent = fileStorage.loadFile(fileName);

		if (fileContent.length > 0)
		{
			LOG.debug(String.format("found file '%s' with size %d", fileName, fileContent.length));

			// String mimetype = servletContext.getMimeType(file);
			// response.setContentType(mimetype);

			response.setBufferSize(fileContent.length);
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
			response.setContentLength(fileContent.length);

			response.getOutputStream().write(fileContent);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
		else
		{
			sendErrorHtml(response, String.format("could not get file '%s'", fileName));
		}

		return null;
	}

	private void sendErrorHtml(ServletResponse response, String errorMessage)
	{
		try
		{
			response.setContentType("text/html");
			PrintWriter printwriter = response.getWriter();

			String html = "<html>";
			html += "<body>";
			html += String.format("<h1>%s</h1>", errorMessage);
			html += "</body>";
			html += "</html>";

			printwriter.println(html);
			printwriter.flush();
			printwriter.close();

		}
		catch (Exception e)
		{
			LOG.error(e);
			throw new RuntimeException(e);
		}

	}

	public void setFileStorage(IFileStorage fileStorage)
	{
		this.fileStorage = fileStorage;
	}
}
