package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseRootModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite;

public class BaseRootElement<ModelType extends IBaseRootModel> extends BaseModelElement<ModelType>
{
	private final Composite rootComposite;

	public BaseRootElement(ModelType baseRootModel, BaseModelElement<IBaseModel> parent)
	{
		super(baseRootModel, parent);

		this.rootComposite = new Composite(baseRootModel.getCompositeModel(), parent);
	}

	public Composite getRootComposite()
	{
		return this.rootComposite;
	}
}
