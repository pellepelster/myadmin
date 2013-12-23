package de.pellepelster.myadmin.client.base.modules.dictionary.model.editor;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.hooks.BaseEditorHook;
import de.pellepelster.myadmin.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;

public class EditorModel<VOTYpe extends IBaseVO> extends BaseModel<Object> implements IEditorModel
{

	private static final long serialVersionUID = 7452528927479882166L;

	private String label;

	private ICompositeModel compositeModel;

	public EditorModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public ICompositeModel getCompositeModel()
	{
		return this.compositeModel;
	}

	@Override
	public String getLabel()
	{
		return this.label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public void setCompositeModel(ICompositeModel compositeModel)
	{
		this.compositeModel = compositeModel;
	}

	public void addEditorHook(BaseEditorHook<VOTYpe> editorHook)
	{
		DictionaryHookRegistry.getInstance().addEditorHook(getName(), editorHook);
	}

}
