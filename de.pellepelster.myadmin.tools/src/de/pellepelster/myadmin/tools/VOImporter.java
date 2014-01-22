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
package de.pellepelster.myadmin.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import com.google.common.base.Joiner;

import de.pellepelster.myadmin.client.web.MyAdminRemoteServiceLocator;
import de.pellepelster.myadmin.server.services.xml.XmlVOExportImportService;
import de.pellepelster.myadmin.tools.util.MyAdminApplicationContextProvider;

public class VOImporter extends BaseToolAntTask
{
	private String applicationContext;

	private String importDir;

	private static final Logger LOG = Logger.getLogger(VOImporter.class);

	/** {@inheritDoc} */
	@Override
	public void execute() throws BuildException
	{
		// TODO this should be moved to BaseToolAntTask
		List<String> applicationContexts = new ArrayList<String>();
		applicationContexts.add("MyAdminToolsApplicationContext.xml");
		applicationContexts.add("MyAdminClientServices-gen.xml");

		if (this.applicationContext != null && !this.applicationContext.isEmpty())
		{
			applicationContexts.add(this.applicationContext);
		}

		LOG.info(String.format("initializing spring context (%s)", Joiner.on(", ").join(applicationContexts)));

		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken(getRemoteuser(), getRemotepassword());
		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);

		System.setProperty("remote.server", getRemoteServer());
		System.setProperty("remote.user", getRemoteuser());
		System.setProperty("remote.password", getRemotepassword());
		System.setProperty("remote.port", getRemoteport());
		System.setProperty("remote.path", getRemotepath());

		MyAdminApplicationContextProvider.getInstance().init(applicationContexts.toArray(new String[applicationContexts.size()]));
		MyAdminRemoteServiceLocator.getInstance().init(MyAdminApplicationContextProvider.getInstance());

		XmlVOExportImportService exportImportService = MyAdminApplicationContextProvider.getInstance().getContext().getBean(XmlVOExportImportService.class);

		File sourceDir = new File(this.importDir);

		VOImportRunner entityImportRunner = new VOImportRunner(exportImportService, sourceDir);
		entityImportRunner.run();
	}

	public String getApplicationContext()
	{
		return this.applicationContext;
	}

	public void setApplicationContext(String applicationContext)
	{
		this.applicationContext = applicationContext;
	}

	public String getImportDir()
	{
		return this.importDir;
	}

	public void setImportDir(String importDir)
	{
		this.importDir = importDir;
	}
}
