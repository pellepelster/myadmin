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
package de.pellepelster.myadmin.tools.dictionary;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import de.pellepelster.myadmin.client.web.MyAdminRemoteServiceLocator;
import de.pellepelster.myadmin.tools.BaseToolAntTask;
import de.pellepelster.myadmin.tools.MyAdminApplicationContextProvider;

public class EntityExporter extends BaseToolAntTask
{

	private static final Logger logger = Logger.getLogger(EntityExporter.class);

	/** {@inheritDoc} */
	@Override
	public void execute() throws BuildException
	{
		logger.info("initializing spring context");

		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken(getRemoteuser(), getRemotepassword());
		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);

		System.setProperty("remote.server", getRemoteServer());
		System.setProperty("remote.user", getRemoteuser());
		System.setProperty("remote.password", getRemotepassword());
		System.setProperty("remote.port", getRemoteport());
		System.setProperty("remote.path", getRemotepath());

		MyAdminApplicationContextProvider.getInstance().init(new String[] { "MyAdminToolsApplicationContext.xml", "MyAdminClientServices-gen.xml" });

		MyAdminRemoteServiceLocator.getInstance().init(MyAdminApplicationContextProvider.getInstance());
		// ImportExportService importExportService = null; // =
		// MyAdminRemoteServiceLocator.getInstance().get.getBaseEntityService();

		// File exportDir = new File(System.getProperty("java.io.tempdir"));

		// EntityExportRunner entityExportRunner = new
		// EntityExportRunner(importExportService, exportDir);
		// entityExportRunner.run();
	}
}
