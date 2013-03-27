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
package de.pellepelster.myadmin.client.web;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface MyAdminResources extends ClientBundle
{

	@Source("delete.png")
	ImageResource delete();

	@Source("back.png")
	ImageResource back();

	@Source("add.png")
	ImageResource add();

	@Source("editorSave.png")
	ImageResource editorSave();

	@Source("searchCreate.png")
	ImageResource searchCreate();

	@Source("searchSearch.png")
	ImageResource searchSearch();
}