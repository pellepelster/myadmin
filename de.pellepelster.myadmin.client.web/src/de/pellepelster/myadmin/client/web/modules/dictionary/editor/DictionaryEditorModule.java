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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseDictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.Binding;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.DataBindingContext;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.DatabindingVOWrapper;
import de.pellepelster.myadmin.client.web.modules.dictionary.events.VOSavedEvent;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

/**
 * Dictionary editor module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModule<VOType extends IBaseVO> extends BaseDictionaryEditorModule implements IDictionaryEditor<VOType>
{
	public EventBus eventBus = GWT.create(SimpleEventBus.class);

	private SimpleCallback<String> titleChangedCallback;

	private void callTitleChangedCallback()
	{
		if (titleChangedCallback != null)
		{
			titleChangedCallback.onCallback(getTitle());
		}
	}

	public enum EditorMode
	{
		INSERT, UPDATE;
	}

	private final DatabindingVOWrapper voWrapper = new DatabindingVOWrapper();

	private final DataBindingContext dataBindingContext = new DataBindingContext(voWrapper, getEventBus());

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
				voWrapper.setVo(result.getVo());
				voWrapper.markClean();

				MyAdmin.EVENT_BUS.fireEvent(new VOSavedEvent(result.getVo()));

				callTitleChangedCallback();
			}
			else
			{
				dataBindingContext.addValidationMessages(result.getValidationMessages());
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

			final IObservableValue modelObervableValue = voWrapper.getObservableValue(uiObservableValue.getModel());
			final Binding binding = dataBindingContext.createBinding(modelObervableValue, uiObservableValue);

			if (uiObservableValue.getModel() instanceof IBaseControlModel)
			{
				binding.setValidators(MyAdmin.getInstance().getControlHandler().createValidators((IBaseControlModel) uiObservableValue.getModel()));
			}

		}
	}

	private void getAllReferencedDictionaries(final IDictionaryModel dictionaryModel)
	{
		List<String> referencedDictionaryNames = ModelUtil.getReferencedDictionaryModels(dictionaryModel.getEditorModel().getCompositeModel());

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
		return dictionaryModel;
	}

	private EditorMode getEditorMode()
	{
		if (voWrapper.getVo() == null || voWrapper.getVo().getId() == IBaseVO.NEW_VO_ID)
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
		return voWrapper;
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasErrors()
	{
		return dataBindingContext.hasErrors();
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
		return voWrapper.isDirty();
	}

	/** {@inheritDoc} */
	@Override
	public void load()
	{
		load(null);
	}

	public void load(final AsyncCallback<Void> callback)
	{
		if (hasId())
		{
			GenericFilterVO<IBaseVO> genericFilterVO = new GenericFilterVO<IBaseVO>(dictionaryModel.getVOName());
			genericFilterVO.addCriteria(IBaseVO.FIELD_ID, getId());

			ModelUtil.populateAssociations(genericFilterVO, dictionaryModel.getEditorModel().getCompositeModel());

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
						voWrapper.setVo(result.get(0));
						voWrapper.markClean();
						callTitleChangedCallback();
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

			MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService()
					.getNewVO(dictionaryModel.getVOName(), de.pellepelster.myadmin.client.base.util.Collections.copyMap(getParameters()), newVOCallback);
		}
	}

	public EventBus getEventBus()
	{
		return eventBus;
	}

	/** {@inheritDoc} */
	@Override
	public void save()
	{
		dataBindingContext.validateAndUpdate();

		if (!dataBindingContext.hasErrors())
		{
			switch (getEditorMode())
			{
				case INSERT:
					MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().validateAndCreate(voWrapper.getVo(), saveCallback);
					break;
				case UPDATE:
					MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().validateAndSave(voWrapper.getVo(), saveCallback);
					break;
				default:
					throw new RuntimeException("editor mode '" + getEditorMode().toString() + "' not implemented");
			}
		}
	}

	public void setTitleChangedCallback(SimpleCallback<String> titleChangedCallback)
	{
		this.titleChangedCallback = titleChangedCallback;
	}

}
