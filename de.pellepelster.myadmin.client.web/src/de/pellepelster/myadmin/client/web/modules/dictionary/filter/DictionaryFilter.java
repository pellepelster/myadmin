package de.pellepelster.myadmin.client.web.modules.dictionary.filter;

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.search.IFilterModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.BaseRootElement;

public class DictionaryFilter<VOType extends IBaseVO> extends BaseRootElement<IFilterModel>
{
	private final Composite rootComposite;

	public DictionaryFilter(IFilterModel filterModel, BaseDictionaryElement<IBaseModel> parent)
	{
		super(filterModel, parent);

		this.rootComposite = new Composite(filterModel.getCompositeModel(), this);
	}

	@Override
	public Composite getRootComposite()
	{
		return this.rootComposite;
	}

	@Override
	public List<? extends BaseDictionaryElement<?>> getAllChildren()
	{
		return this.rootComposite.getAllChildren();
	}

	@Override
	public BaseRootElement<?> getRootElement()
	{
		return this;
	}

}
