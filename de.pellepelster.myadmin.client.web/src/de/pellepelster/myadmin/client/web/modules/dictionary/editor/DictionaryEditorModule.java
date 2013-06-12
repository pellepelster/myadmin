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

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.databinding.IObservableValue;
import de.pellepelster.myadmin.client.base.databinding.IUIObservableValue;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.modules.dictionary.hooks.ClientHookRegistry;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseDictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.Binding;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.DataBindingContext;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.DatabindingVOWrapper;
import de.pellepelster.myadmin.client.web.modules.dictionary.events.VOLoadEvent;
import de.pellepelster.myadmin.client.web.modules.dictionary.events.VOSavedEvent;

/**
 * Dictionary editor module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModule<VOType extends IBaseVO> extends BaseDictionaryEditorModule implements IDictionaryEditor<VOType>
{
	private EventBus eventBus = GWT.create(SimpleEventBus.class);

	public enum EditorMode
	{
		INSERT, UPDATE;
	}

	private final DatabindingVOWrapper voWrapper = new DatabindingVOWrapper();

	private final DataBindingContext dataBindingContext = new DataBindingContext(this.voWrapper, getEventBus());

	public static final String DICTIONARY_PARAMETER_NAME = "Dictionary";

	private IDictionaryModel dictionaryModel;

	private final AsyncCallback<Result<IBaseVO>> saveCallback = new AsyncCallback<Result<IBaseVO>>()
	{

		/** {@inheritDoc} */
		@Override
		public void onFailure(Throwable caught)
		{
			throw new RuntimeException(caught);
		}

		/** {@inheritDoc} */
		@Override
		public void onSuccess(Result<IBaseVO> result)
		{

			if (result.getValidationMessages().isEmpty())
			{
				DictionaryEditorModule.this.voWrapper.setVo(result.getVo());
				DictionaryEditorModule.this.voWrapper.markClean();

				VOSavedEvent voSavedEvent = new VOSavedEvent(result.getVo());
				MyAdmin.EVENT_BUS.fireEvent(voSavedEvent);
				getEventBus().fireEvent(voSavedEvent);
			}
			else
			{
				DictionaryEditorModule.this.dataBindingContext.addValidationMessages(result.getValidationMessages());
			}

		}
	};

	public DictionaryEditorModule(IDictionaryModel dictionaryModel, long voId, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(new ModuleVO(), moduleCallback, parameters);
		this.dictionaryModel = dictionaryModel;

		init(dictionaryModel.getName());

	}

	public DictionaryEditorModule(ModuleVO moduleVO, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(moduleVO, moduleCallback, parameters);

		init(getEditorDictionaryName());
	}

	public DictionaryEditorModule(String editorDictionaryName, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(new ModuleVO(), moduleCallback, parameters);

		init(editorDictionaryName);
	}

	public String getTitle()
	{
		return DictionaryUtil.getEditorTitle(getDictionaryModel(), getVoWrapper());
	}

	@SuppressWarnings("unchecked")
	public void bindControls(List<IUIObservableValue> uiObservableValues)
	{

		for (final IUIObservableValue uiObservableValue : uiObservableValues)
		{

			final IObservableValue modelObervableValue = this.voWrapper.getObservableValue(uiObservableValue.getModel());
			final Binding binding = this.dataBindingContext.createBinding(modelObervableValue, uiObservableValue);

			if (uiObservableValue.getModel() instanceof IBaseControlModel)
			{
				binding.setValidators(MyAdmin.getInstance().getControlHandler().createValidators((IBaseControlModel) uiObservableValue.getModel()));
			}

		}
	}

	private void getAllReferencedDictionaries(final IDictionaryModel dictionaryModel)
	{
		List<String> referencedDictionaryNames = DictionaryModelUtil.getReferencedDictionaryModels(dictionaryModel.getEditorModel().getCompositeModel());

		DictionaryModelProvider.cacheDictionaryModels(referencedDictionaryNames, new AsyncCallback<List<IDictionaryModel>>()
		{

			/** {@inheritDoc} */
			@Override
			public void onFailure(Throwable caught)
			{
				getModuleCallback().onFailure(caught);
			}

			/** {@inheritDoc} */
			@Override
			public void onSuccess(List<IDictionaryModel> result)
			{
				load(new AsyncCallback<Void>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
						getModuleCallback().onFailure(caught);
					}

					@Override
					public void onSuccess(Void result)
					{
						getModuleCallback().onSuccess(DictionaryEditorModule.this);
					}
				});
			}
		});
	}

	public IDictionaryModel getDictionaryModel()
	{
		return this.dictionaryModel;
	}

	private EditorMode getEditorMode()
	{
		if (this.voWrapper.getVO() == null || this.voWrapper.getVO().getId() == IBaseVO.NEW_VO_ID)
		{
			return EditorMode.INSERT;
		}
		else
		{
			return EditorMode.UPDATE;
		}
	}

	public DatabindingVOWrapper getVoWrapper()
	{
		return this.voWrapper;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasErrors()
	{
		return this.dataBindingContext.hasErrors();
	}

	private void init(String editorDictionaryName)
	{

		DictionaryModelProvider.getDictionaryModel(editorDictionaryName, new AsyncCallback<IDictionaryModel>()
		{

			/** {@inheritDoc} */
			@Override
			public void onFailure(Throwable caught)
			{
				getModuleCallback().onFailure(caught);
			}

			/** {@inheritDoc} */
			@Override
			public void onSuccess(IDictionaryModel result)
			{
				DictionaryEditorModule.this.dictionaryModel = result;
				getAllReferencedDictionaries(result);
			}
		});

	}

	public boolean isDirty()
	{
		return this.voWrapper.isDirty();
	}

	/** {@inheritDoc} */
	@Override
	public void load()
	{
		load(null);
	}

	public void load(final AsyncCallback<Void> callback)
	{
		long id = -1;

		if (this.voWrapper.getVO() != null && !this.voWrapper.getVO().isNew())
		{
			id = this.voWrapper.getVO().getId();
		}
		else if (hasId())
		{
			id = getId();
		}

		if (id > 0)
		{
			GenericFilterVO<IBaseVO> genericFilterVO = new GenericFilterVO<IBaseVO>(this.dictionaryModel.getVOName());
			genericFilterVO.addCriteria(IBaseVO.FIELD_ID, id);

			DictionaryModelUtil.populateAssociations(genericFilterVO, this.dictionaryModel.getEditorModel().getCompositeModel());

			AsyncCallback<List<IBaseVO>> filterCallback = new AsyncCallback<List<IBaseVO>>()
			{

				/** {@inheritDoc} */
				@Override
				public void onFailure(Throwable caught)
				{
					if (callback != null)
					{
						callback.onFailure(caught);
					}
				}

				/** {@inheritDoc} */
				@Override
				public void onSuccess(List<IBaseVO> result)
				{
					if (result.size() == 1)
					{
						DictionaryEditorModule.this.eventBus.fireEvent(new VOLoadEvent(result.get(0)));

						DictionaryEditorModule.this.voWrapper.setVo(result.get(0));
						DictionaryEditorModule.this.voWrapper.markClean();
					}
					else
					{
						callback.onFailure(new RuntimeException("error loading vo"));
					}

					if (callback != null)
					{
						callback.onSuccess(null);
					}

				}
			};

			MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(genericFilterVO, filterCallback);

		}
		else
		{
			AsyncCallback<IBaseVO> newVOCallback = new AsyncCallback<IBaseVO>()
			{

				/** {@inheritDoc} */
				@Override
				public void onFailure(Throwable caught)
				{
					if (callback != null)
					{
						callback.onFailure(caught);
					}
				}

				/** {@inheritDoc} */
				@Override
				public void onSuccess(IBaseVO result)
				{
					DictionaryEditorModule.this.voWrapper.setVo(result);

					if (callback != null)
					{
						callback.onSuccess(null);
					}
				}
			};

			MyAdmin.getInstance()
					.getRemoteServiceLocator()
					.getBaseEntityService()
					.getNewVO(this.dictionaryModel.getVOName(), de.pellepelster.myadmin.client.base.util.CollectionUtils.copyMap(getParameters()),
							newVOCallback);
		}
	}

	public EventBus getEventBus()
	{
		return this.eventBus;
	}

	/** {@inheritDoc} */
	@Override
	public void save()
	{
		this.dataBindingContext.validateAndUpdate();

		if (!this.dataBindingContext.hasErrors())
		{
			if (ClientHookRegistry.getInstance().hasEditorSaveHook(getDictionaryModel().getName()))
			{
				ClientHookRegistry.getInstance().getEditorSaveHook(getDictionaryModel().getName()).onSave(new AsyncCallback<Void>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						throw new RuntimeException("error executing editor save hook", caught);
					}

					@Override
					public void onSuccess(Void result)
					{
						internalSave();
					}
				});
			}
			else
			{
				internalSave();
			}
		}
	}

	private void internalSave()
	{
		switch (getEditorMode())
		{
			case INSERT:
				MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().validateAndCreate(this.voWrapper.getVO(), this.saveCallback);
				break;
			case UPDATE:
				MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().validateAndSave(this.voWrapper.getVO(), this.saveCallback);
				break;
			default:
				throw new RuntimeException("editor mode '" + getEditorMode().toString() + "' not implemented");
		}
	}

	public void refresh()
	{
		load();
	}
}
