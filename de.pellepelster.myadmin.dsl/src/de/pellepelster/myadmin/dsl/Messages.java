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
package de.pellepelster.myadmin.dsl;

import de.pellepelster.myadmin.dsl.util.NLS;

public class Messages extends NLS
{
	private static final String BUNDLE_NAME = "de.pellepelster.myadmin.dsl.messages"; //$NON-NLS-1$

	public static String AssignmentTable;

	public static String NoValidIdentifier;

	public static String ControlEntityAttributeDoesNotMatchParentEntity;

	public static String EditableTable;

	public static String Dictionary;

	static
	{
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages()
	{
	}
}
