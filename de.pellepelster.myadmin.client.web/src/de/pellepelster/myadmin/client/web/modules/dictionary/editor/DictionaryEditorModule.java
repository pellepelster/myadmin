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
package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseDictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryElementUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

/**
 * Dictionary editor module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModule<VOType extends IBaseVO> extends BaseDictionaryEditorModule
{
	private EventBus eventBus = GWT.create(SimpleEventBus.class);

	public enum EditorMode
	{
		INSERT, UPDATE;
	}

	public static final String DICTIONARY_PARAMETER_NAME = "Dictionary";

	private DictionaryEditor<VOType> dictionaryEditor;

	private IDictionaryModel dictionaryModel;

	private List<IEditorUpdateListener> updateListeners = new ArrayList<IEditorUpdateListener>();

	public DictionaryEditorModule(IDictionaryModel dictionaryModel, long voId, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(new ModuleVO(), moduleCallback, parameters);

		init(dictionaryModel.getName());

	}

	public DictionaryEditorModule(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(moduleVO, moduleCallback, parameters);

		init(getEditorDictionaryName());
	}

	public DictionaryEditorModule(String dictionaryName, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(new ModuleVO(), moduleCallback, parameters);

		init(dictionaryName);
	}

	public String getTitle()
	{
		return DictionaryUtil.getEditorTitle(this.dictionaryModel, this.dictionaryEditor);
	}

	private void getAllReferencedDictionaries(final IDictionaryModel dictionaryModel)
	{
		List<String> referencedDictionaryNames = DictionaryModelUtil.getReferencedDictionaryModels(dictionaryModel.getEditorModel().getCompositeModel());

		DictionaryModelProvider.cacheDictionaryModels(referencedDictionaryNames, new BaseErrorAsyncCallback<List<IDictionaryModel>>()
		{
			/** {@inheritDoc} */
			@Override
			public void onSuccess(List<IDictionaryModel> result)
			{
				DictionaryEditorModule.this.dictionaryEditor = new DictionaryEditor<VOType>(dictionaryModel.getEditorModel(), getParameters());
				DictionaryEditorModule.this.dictionaryModel = dictionaryModel;

				AsyncCallback<Void> asyncCallback = new BaseErrorAsyncCallback<Void>()
				{
					@Override
					public void onSuccess(Void result)
					{
						getModuleCallback().onSuccess(DictionaryEditorModule.this);
					}
				};

				if (hasId())
				{
					DictionaryEditorModule.this.dictionaryEditor.load(getId(), asyncCallback);
				}
				else
				{
					DictionaryEditorModule.this.dictionaryEditor.newVO(asyncCallback);
				}
			}
		});
	}

	public IDictionaryModel getDictionaryModel()
	{
		return this.dictionaryModel;
	}

	private void init(String dictionaryName)
	{

		DictionaryModelProvider.getDictionaryModel(dictionaryName, new BaseErrorAsyncCallback<IDictionaryModel>(getModuleCallback())
		{
			/** {@inheritDoc} */
			@Override
			public void onSuccess(IDictionaryModel dictionaryModel)
			{
				getAllReferencedDictionaries(dictionaryModel);
			}
		});

	}

	public EventBus getEventBus()
	{
		return this.eventBus;
	}

	public DictionaryEditor<VOType> getDictionaryEditor()
	{
		return this.dictionaryEditor;
	}

	@Override
	public String getModuleId()
	{
		if (this.dictionaryEditor.getVOWrapper().getVO().isNew())
		{
			return getClass().getName() + '#' + this.dictionaryModel.getName();
		}
		else
		{
			return getClass().getName() + '#' + this.dictionaryModel.getName() + "#" + this.dictionaryEditor.getVOWrapper().getId();
		}
	}

	public <ElementType> ElementType getElement(DictionaryDescriptor<ElementType> controlDescriptor)
	{
		return DictionaryElementUtil.getElement(this.dictionaryEditor, controlDescriptor);
	}

	public void addUpdateListener(IEditorUpdateListener updateListener)
	{
		this.updateListeners.add(updateListener);
	}

	private void fireUpdateListeners()
	{
		for (IEditorUpdateListener updateListener : this.updateListeners)
		{
			updateListener.onUpdate();
		}
	}

}
