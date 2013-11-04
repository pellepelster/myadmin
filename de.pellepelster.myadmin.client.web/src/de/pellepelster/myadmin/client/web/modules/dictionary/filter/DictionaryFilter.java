package de.pellepelster.myadmin.client.web.modules.dictionary.filter;

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IFilterModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite;

public class DictionaryFilter<VOType extends IBaseVO> extends BaseDictionaryElement<IFilterModel>
{
	private final Composite rootComposite;

	public DictionaryFilter(IFilterModel filterModel, BaseDictionaryElement<IBaseModel> parent)
	{
		super(filterModel, parent);

		this.rootComposite = new Composite(filterModel.getCompositeModel(), parent);
	}

	public Composite getRootComposite()
	{
		return this.rootComposite;
	}

	@Override
	public List<? extends BaseDictionaryElement<?>> getAllChildren()
	{
		return this.rootComposite.getAllChildren();
	}

}
