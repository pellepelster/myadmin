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

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{
	private static final String BUNDLE_NAME = "de.pellepelster.myadmin.ui.messages"; //$NON-NLS-1$

	public static String InitialBuildNoJdkErrorTitle;

	public static String InitialBuildNoJdkErrorMessage;

	public static String BootstrappingProjects;

	public static String ProjectName;

	public static String Organization;

	public static String FillInField;

	public static String NewProjectErrorTitle;

	public static String NewProjectErrorMessage;

	public static String NewProjectPage1Title;

	public static String NewProjectPage1Desc;

	public static String NewProjectCategoryName;

	public static String ProjectExists;

	public static String InitialBuild;

	static
	{
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages()
	{
	}
}
