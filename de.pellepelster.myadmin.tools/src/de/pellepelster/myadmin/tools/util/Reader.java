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
package de.pellepelster.myadmin.tools.util;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.mwe.core.WorkflowContext;
import org.eclipse.emf.mwe.core.WorkflowInterruptedException;
import org.eclipse.emf.mwe.core.issues.Issues;
import org.eclipse.emf.mwe.core.monitor.ProgressMonitor;
import org.eclipse.xtext.mwe.AbstractReader;
import org.eclipse.xtext.mwe.ContainersStateFactory;
import org.eclipse.xtext.mwe.PathTraverser;
import org.eclipse.xtext.mwe.UriFilter;
import org.eclipse.xtext.resource.containers.DelegatingIAllContainerAdapter;
import org.eclipse.xtext.resource.containers.IAllContainersState;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class Reader extends AbstractReader
{

	protected final static Logger LOG = Logger.getLogger(Reader.class.getName());

	protected List<String> pathes = Lists.newArrayList();

	private UriFilter filter;

	private ContainersStateFactory containersStateFactory = new ContainersStateFactory();

	/**
	 * <p>
	 * A path pointing to a folder, jar or zip which contains EMF resources.
	 * </p>
	 * <p>
	 * Example use:
	 * </p>
	 * <code>
	 * &lt;path value="./foo/bar.jar"/&gt;
	 * </code>
	 */
	public void addPath(String path)
	{
		this.pathes.add(path);
	}

	@Override
	protected void checkConfigurationInternal(Issues issues)
	{
		super.checkConfigurationInternal(issues);
		if (this.pathes.isEmpty())
		{
			issues.addWarning("No path set, using java class path entries (useJavaClassPath='true').");
			setUseJavaClassPath(true);
		}
		if (LOG.isDebugEnabled())
		{
			LOG.debug("Resource Pathes : " + this.pathes);
		}
		for (String path : this.pathes)
		{
			if (!new File(path).exists())
				issues.addWarning("Skipping the path '" + path + "', because it does not exist.");
		}
	}

	public ContainersStateFactory getContainersStateFactory()
	{
		return this.containersStateFactory;
	}

	public List<String> getPathes()
	{
		return this.pathes;
	}

	protected PathTraverser getPathTraverser()
	{
		return new PathTraverser();
	}

	public UriFilter getUriFilter()
	{
		return this.filter;
	}

	protected void installAsAdapter(ResourceSet set, IAllContainersState containersState) throws WorkflowInterruptedException
	{
		set.eAdapters().add(new DelegatingIAllContainerAdapter(containersState));
	}

	@Override
	protected void invokeInternal(WorkflowContext ctx, ProgressMonitor monitor, Issues issues)
	{
		ResourceSet resourceSet = getResourceSet();
		Multimap<String, URI> uris = getPathTraverser().resolvePathes(this.pathes, new Predicate<URI>()
		{
			@Override
			public boolean apply(URI input)
			{
				boolean result = true;
				if (getUriFilter() != null)
					result = getUriFilter().matches(input);
				if (result)
					result = getRegistry().getResourceServiceProvider(input) != null;
				return result;
			}
		});
		IAllContainersState containersState = this.containersStateFactory.getContainersState(this.pathes, uris);
		installAsAdapter(resourceSet, containersState);
		populateResourceSet(resourceSet, uris);
		getValidator().validate(resourceSet, getRegistry(), issues);
		addModelElementsToContext(ctx, resourceSet);
	}

	protected void populateResourceSet(ResourceSet set, Multimap<String, URI> uris)
	{
		Collection<URI> values = Sets.newHashSet(uris.values());
		for (URI uri : values)
		{
			set.createResource(uri);
		}
	}

	public void setContainersStateFactory(ContainersStateFactory containersStateFactory)
	{
		this.containersStateFactory = containersStateFactory;
	}

	/**
	 * Optionally set a filter that specifies which URIs are valid to be read. A
	 * common use-case for filters is a file-name based selection of valid URIs.
	 */
	public void setUriFilter(UriFilter filter)
	{
		this.filter = filter;
	}

	/**
	 * <p>
	 * Automatically adds all class path entries of the current process (more
	 * specifically uses 'java.class.path' system property).
	 * </p>
	 * <p>
	 * Example use:
	 * </p>
	 * <code>
	 * &lt;useJavaClassPath value="true"/&gt;
	 * </code>
	 */
	public void setUseJavaClassPath(boolean isUse)
	{
		if (isUse)
		{
			String classPath = System.getProperty("java.class.path");
			String separator = System.getProperty("path.separator");
			String[] strings = classPath.split(separator);
			for (String path : strings)
			{
				addPath(path);
			}
		}
	}

}