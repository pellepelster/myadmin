package de.pellepelster.myadmin.client.base.modules.dictionary.model.containers;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IComposite;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.editor.EditorModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.search.FilterModel;

public abstract class CompositeModel<VOType extends IBaseVO> extends BaseContainerModel<IComposite> implements ICompositeModel
{

	private static final long serialVersionUID = 1832725605229414533L;

	public CompositeModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	public CompositeModel(String name, BaseContainerModel<?> parent)
	{
		super(name, parent);
		parent.getChildren().add(this);
	}

	public CompositeModel(String name, EditorModel<?> parent)
	{
		super(name, parent);
		parent.setCompositeModel(this);
	}

	public CompositeModel(String name, FilterModel parent)
	{
		super(name, parent);
		parent.setCompositeModel(this);
	}

}
