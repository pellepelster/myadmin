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
package de.pellepelster.myadmin.server.test;

import org.junit.Test;

import de.pellepelster.myadmin.client.web.dictionaries.MyAdminDictionaryModel;

public final class DictionaryTest
{

	@Test
	public void testFilterAll()
	{
		MyAdminDictionaryModel.MY_ADMIN_USER.USER_EDITOR.USER_EDITOR_ROOT_COMPOSITE.USER_NAME.getAttributePath();
	}
}