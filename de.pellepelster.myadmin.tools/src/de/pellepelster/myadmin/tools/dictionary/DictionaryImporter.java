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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import de.pellepelster.myadmin.client.web.MyAdminRemoteServiceLocator;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.tools.BaseToolAntTask;
import de.pellepelster.myadmin.tools.MyAdminApplicationContextProvider;

public class DictionaryImporter extends BaseToolAntTask {

	private static final Logger logger = Logger.getLogger(DictionaryImporter.class);

	/** file containing the dictionary model */
	private String modelfile;

	private Path classpath;

	/**
	 * Create the classpath for loading the driver.
	 */
	public Path createClasspath() {
		logger.trace("createClasspath() - start");

		if (this.classpath == null) {
			this.classpath = new Path(getProject());
		}

		return this.classpath.createPath();
	}

	/** {@inheritDoc} */
	@Override
	public void execute() throws BuildException {
		logger.info("initializing spring context");

		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource modelResource = null;
		List<Resource> modelResources = new ArrayList<Resource>();

		if (this.classpath != null) {
			AntClassLoader antClassLoader = new AntClassLoader(getProject(), this.classpath);
			pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver(antClassLoader);
		}

		try {
			for (Resource resource : pathMatchingResourcePatternResolver.getResources("classpath*:model/*.msl")) {
				modelResources.add(resource);
			}

			for (Resource resource : pathMatchingResourcePatternResolver.getResources(this.modelfile)) {
				modelResource = resource;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

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
		IBaseEntityService baseEntityService = MyAdminRemoteServiceLocator.getInstance().getBaseEntityService();

		ApplicationEventMulticaster applicationEventMulticaster = MyAdminApplicationContextProvider.getInstance().getContext().getBean(ApplicationEventMulticaster.class);

		DictionaryImportRunner dictionaryImportRunner = new DictionaryImportRunner(baseEntityService, applicationEventMulticaster, modelResources, modelResource);
		dictionaryImportRunner.run();
	}

	/**
	 * @see DictionaryImporter#dictionaryname
	 */
	public String getModelfile() {
		return this.modelfile;
	}

	/**
	 * Set the classpath for loading the driver.
	 */
	public void setClasspath(Path classpath) {
		if (this.classpath == null) {
			this.classpath = classpath;
		} else {
			this.classpath.append(classpath);
		}
	}

	/**
	 * Set the classpath for loading the driver using the classpath reference.
	 */
	public void setClasspathRef(Reference r) {
		createClasspath().setRefid(r);
	}

	/**
	 * @see DictionaryImporter#dictionaryname
	 */
	public void setModelfile(String modelfile) {
		this.modelfile = modelfile;
	}
}
