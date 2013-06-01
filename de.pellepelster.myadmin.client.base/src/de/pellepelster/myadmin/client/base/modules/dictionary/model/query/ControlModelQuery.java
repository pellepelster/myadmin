package de.pellepelster.myadmin.client.base.modules.dictionary.model.query;

import java.util.Collection;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;

public class ControlModelQuery<C extends IBaseControlModel> extends BaseListQuery<C>
{
	public ControlModelQuery(Collection<C> list)
	{
		super(list);
	}

	public <T extends IBaseControlModel> T getControlModelByName(final String controlModelName, final Class<T> controlModelClass)
	{
		Collection<T> controlModels = getControlModelsByName(controlModelName, controlModelClass).getList();

		if (controlModels.size() > 0)
		{
			return controlModels.iterator().next();
		}
		else
		{
			throw new RuntimeException("no control model with name name '" + controlModelName + "' found");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends IBaseControlModel> ControlModelQuery<T> getControlModelsByName(final String controlModelName, final Class<T> controlModelClass)
	{
		return new ControlModelQuery(Collections2.filter(getList(), new Predicate<IBaseControlModel>()
		{
			@Override
			public boolean apply(@Nullable IBaseControlModel baseControlModel)
			{
				return controlModelClass.isAssignableFrom(baseControlModel.getClass()) && controlModelName.equals(baseControlModel.getName());
			}
		}));
	}
}
