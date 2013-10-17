package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseRootModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class BaseRootElement<ModelType extends IBaseRootModel> extends BaseModelElement<ModelType>
{
	private final Composite rootComposite;

	public BaseRootElement(ModelType baseRootModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(baseRootModel);

		this.rootComposite = new Composite(baseRootModel.getCompositeModel(), voWrapper);
	}

	public Composite getRootComposite()
	{
		return this.rootComposite;
	}
}
