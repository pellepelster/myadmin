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
package de.pellepelster.myadmin.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

public class MyAdminNature implements IProjectNature
{

	private IProject project;

	public static String NATURE_ID = "de.pellepelster.myadmin.myadminnature";

	@Override
	public void configure() throws CoreException
	{
	}

	@Override
	public void deconfigure() throws CoreException
	{

	}

	@Override
	public IProject getProject()
	{
		return this.project;
	}

	@Override
	public void setProject(IProject project)
	{
		this.project = project;
	}

}
