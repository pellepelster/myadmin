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
import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.IEditorUpdateListener;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModuleTestUI<VOType extends IBaseVO> implements IModuleUI<Object, DictionaryEditorModule<VOType>>, IEditorUpdateListener
{
	private DictionaryEditorModule<VOType> module;

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

	public <ElementType> ElementType getElement(DictionaryDescriptor<ElementType> controlDescriptor)
	{
		return this.module.getElement(controlDescriptor);
	}

	public void save(final AsyncCallback<DictionaryEditorModuleTestUI<VOType>> asyncCallback)
	{
		this.module.save(new BaseErrorAsyncCallback<Result<VOType>>(asyncCallback)
		{
			@Override
			public void onSuccess(Result<VOType> result)
			{
				asyncCallback.onSuccess(DictionaryEditorModuleTestUI.this);
			}
		});
	}

	@Override
	public void onUpdate()
	{
	}

}
