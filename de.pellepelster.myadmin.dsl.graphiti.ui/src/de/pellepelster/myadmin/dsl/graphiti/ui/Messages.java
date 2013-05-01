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
package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS
{
	private static final String BUNDLE_NAME = "de.pellepelster.myadmin.dsl.graphiti.ui.messages"; //$NON-NLS-1$

	public static String ModelFileNameMustBeSpecified;

	public static String ModelFileNameFormat;

	public static String NewDiagram;

	public static String Error;

	public static String DeviceTypeMustBeDpecified;

	public static String DeviceTypeNameMustBeValid;

	public static String DeviceTypeNameShouldNotContainDot;

	public static String FileContainerMustBeSpecified;

	public static String FileContainerMustExist;

	public static String ProjectMustBeWritable;

	public static String DiagramName;

	public static String Diagram;

	public static String Browse;

	public static String Location;

	public static String EnterDiagramName;

	static
	{
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages()
	{
	}
}
