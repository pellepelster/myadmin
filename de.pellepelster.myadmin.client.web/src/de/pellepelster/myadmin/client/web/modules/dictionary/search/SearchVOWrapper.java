package de.pellepelster.myadmin.client.web.modules.dictionary.search;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.IVOWrapper;

public class SearchVOWrapper<VOType extends IBaseVO> implements IVOWrapper<VOType>
{

	@Override
	public void set(String attribute, Object value)
	{
	}

	@Override
	public Object get(String attribute)
	{
		return null;
	}

}
