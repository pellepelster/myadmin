package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.hooks.ClientHookRegistry;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IEditorModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule.EditorMode;
import de.pellepelster.myadmin.client.web.util.BaseAsyncCallback;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

public class DictionaryEditor<VOType extends IBaseVO> extends BaseRootElement<IEditorModel>
{

	private final EditorVOWrapper<VOType> voWrapper = new EditorVOWrapper<VOType>();

	private List<IEditorUpdateListener> updateListeners = new ArrayList<IEditorUpdateListener>();

	private Map<String, Object> context;

	public DictionaryEditor(IEditorModel editorModel, Map<String, Object> context)
	{
		super(editorModel, null);

		this.context = context;

	}

	@Override
	protected EditorVOWrapper<VOType> getVOWrapper()
	{
		return this.voWrapper;
	}

	@Override
	public BaseRootElement<?> getRootElement()
	{
		return this;
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

	public boolean isDirty()
	{
		return this.voWrapper.isDirty();
	}

	public void newVO(final AsyncCallback<Void> callback)
	{
		AsyncCallback<VOType> newVOCallback = new BaseErrorAsyncCallback<VOType>(callback)
		{

			/** {@inheritDoc} */
			@Override
			public void onSuccess(VOType result)
			{
				setVO(result);

				if (callback != null)
				{
					callback.onSuccess(null);
				}

			}
		};

		MyAdmin.getInstance()
				.getRemoteServiceLocator()
				.getBaseEntityService()
				.getNewVO(this.getModel().getVOName(), de.pellepelster.myadmin.client.base.util.CollectionUtils.copyMap(this.context),
						(AsyncCallback<IBaseVO>) newVOCallback);

	}

	public void load(long id, final AsyncCallback<Void> callback)
	{
		GenericFilterVO<VOType> genericFilterVO = new GenericFilterVO<VOType>(this.getModel().getVOName());
		genericFilterVO.addCriteria(IBaseVO.FIELD_ID, id);

		DictionaryModelUtil.populateAssociations(genericFilterVO, this.getModel().getCompositeModel());

		AsyncCallback<List<VOType>> filterCallback = new BaseErrorAsyncCallback<List<VOType>>()
		{

			/** {@inheritDoc} */
			@Override
			public void onSuccess(List<VOType> result)
			{
				if (result.size() == 1)
				{
					setVO(result.get(0));
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

	public void save()
	{
		save(null);
	}

	private final BaseAsyncCallback<Result<VOType>, Result<VOType>> internalSaveCallback = new BaseAsyncCallback<Result<VOType>, Result<VOType>>()
	{
		/** {@inheritDoc} */
		@Override
		public void onSuccess(Result<VOType> result)
		{
			if (result.getValidationMessages().isEmpty())
			{
				setVO(result.getVO());

				super.callParentCallbacks(result);
			}
			else
			{
				// DictionaryEditorModule.this.dataBindingContext.addValidationMessages(result.getValidationMessages());
			}

		}
	};

	private void setVO(VOType vo)
	{
		DictionaryEditor.this.voWrapper.setVO(vo);
		DictionaryEditor.this.voWrapper.markClean();
		fireUpdateListeners();
	}

	public void save(AsyncCallback<Result<VOType>> asyncCallback)
	{
		this.internalSaveCallback.addParentCallback(asyncCallback);

		// if (!this.dataBindingContext.hasErrors())
		// {
		if (ClientHookRegistry.getInstance().hasEditorSaveHook(getModel().getName()))
		{
			ClientHookRegistry.getInstance().getEditorSaveHook(getModel().getName()).onSave(new BaseErrorAsyncCallback<Boolean>()
			{
				@Override
				public void onSuccess(Boolean doSave)
				{
					if (doSave != null && doSave)
					{
						internalSave();
					}
				}
			}, this.voWrapper.getVO());
		}
		else
		{
			internalSave();
		}
		// }
	}

	private void internalSave()
	{
		switch (getEditorMode())
		{
			case INSERT:
				MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().validateAndCreate(this.voWrapper.getVO(), this.internalSaveCallback);
				break;
			case UPDATE:
				MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().validateAndSave(this.voWrapper.getVO(), this.internalSaveCallback);
				break;
			default:
				throw new RuntimeException("editor mode '" + getEditorMode().toString() + "' not implemented");
		}
	}

	public void refresh()
	{
	}

	public IBaseVO getVO()
	{
		return this.voWrapper.getVO();
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
