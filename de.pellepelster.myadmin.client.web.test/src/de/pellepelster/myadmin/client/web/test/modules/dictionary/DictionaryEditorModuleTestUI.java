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
package de.pellepelster.myadmin.client.web.test.modules.dictionary;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryControlDescriptor;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.IEditorUpdateListener;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModuleTestUI<VOType extends IBaseVO> implements IModuleUI<Object, DictionaryEditorModule<VOType>>, IEditorUpdateListener
{
	private DictionaryEditorModule<VOType> module;

	private AsyncCallback<DictionaryEditorModuleTestUI<VOType>> asyncCallback;

	public DictionaryEditorModuleTestUI(DictionaryEditorModule<VOType> module)
	{
		super();
		this.module = module;
		module.addUpdateListener(this);
	}

	@Override
	public boolean close()
	{
		return false;
	}

	@Override
	public boolean contributesToBreadCrumbs()
	{
		return false;
	}

	@Override
	public Object getContainer()
	{
		return null;
	}

	@Override
	public DictionaryEditorModule<VOType> getModule()
	{
		return this.module;
	}

	@Override
	public String getTitle()
	{
		return this.module.getTitle();
	}

	public <T> T getControl(DictionaryControlDescriptor<T> controlDescriptor)
	{
		return this.module.getControl(controlDescriptor);
	}

	public void save(AsyncCallback<DictionaryEditorModuleTestUI<VOType>> asyncCallback)
	{
		this.asyncCallback = asyncCallback;
		this.module.save();
	}

	@Override
	public void onUpdate()
	{
		this.asyncCallback.onSuccess(this);
	}

}
