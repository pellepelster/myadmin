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
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import com.google.common.base.Joiner;

import de.pellepelster.myadmin.client.web.MyAdminRemoteServiceLocator;
import de.pellepelster.myadmin.server.services.vo.VOMetaDataService;
import de.pellepelster.myadmin.server.services.xml.XmlVOExportImportService;
import de.pellepelster.myadmin.tools.util.ApplicationContext;
import de.pellepelster.myadmin.tools.util.MyAdminApplicationContextProvider;

public class VOExporter extends BaseToolAntTask
{

	private String applicationContext;

	private String exportDir;

	private Vector<ApplicationContext> applicationContexts = new Vector<ApplicationContext>();

	private static final Logger LOGGER = Logger.getLogger(VOExporter.class);

	/** {@inheritDoc} */
	@Override
	public void execute() throws BuildException
	{
		List<String> applicationContexts = new ArrayList<String>();

		applicationContexts.add("MyAdminToolsApplicationContext.xml");
		applicationContexts.add("MyAdminClientServices-gen.xml");

		if (this.applicationContext != null && !this.applicationContext.isEmpty())
		{
			applicationContexts.add(this.applicationContext);
		}

		LOGGER.info(String.format("initializing spring context (%s)", Joiner.on(", ").join(applicationContexts)));

		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken(getRemoteuser(), getRemotepassword());
		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);

		System.setProperty("remote.server", getRemoteServer());
		System.setProperty("remote.user", getRemoteuser());
		System.setProperty("remote.password", getRemotepassword());
		System.setProperty("remote.port", getRemoteport());
		System.setProperty("remote.path", getRemotepath());

		for (ApplicationContext applicationContext : this.applicationContexts)
		{
			applicationContexts.add(applicationContext.getFile());
		}

		MyAdminApplicationContextProvider.getInstance().init(applicationContexts.toArray(new String[] {}));

		MyAdminRemoteServiceLocator.getInstance().init(MyAdminApplicationContextProvider.getInstance());

		XmlVOExportImportService exportImportService = MyAdminApplicationContextProvider.getInstance().getContext().getBean(XmlVOExportImportService.class);
		VOMetaDataService metaDataService = MyAdminApplicationContextProvider.getInstance().getContext().getBean(VOMetaDataService.class);

		File targetDir = new File(this.exportDir);

		VOExportRunner entityExportRunner = new VOExportRunner(exportImportService, metaDataService, targetDir);
		entityExportRunner.run();
	}

	public String getExportDir()
	{
		return this.exportDir;
	}

	public void setExportDir(String exportDir)
	{
		this.exportDir = exportDir;
	}

	public Vector<ApplicationContext> getApplicationContexts()
	{
		return this.applicationContexts;
	}

	public void setApplicationContexts(Vector<ApplicationContext> applicationContexts)
	{
		this.applicationContexts = applicationContexts;
	}

	public ApplicationContext createApplicationContext()
	{

		ApplicationContext applicationContext = new ApplicationContext();
		this.applicationContexts.add(applicationContext);

		return applicationContext;
	}

	public String getApplicationContext()
	{
		return this.applicationContext;
	}

	public void setApplicationContext(String applicationContext)
	{
		this.applicationContext = applicationContext;
	}

}
