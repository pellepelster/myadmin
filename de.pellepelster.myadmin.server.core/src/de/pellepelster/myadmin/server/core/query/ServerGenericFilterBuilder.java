package de.pellepelster.myadmin.server.core.query;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.core.query.BaseGenericFilterBuilder;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;

public class ServerGenericFilterBuilder<VOType extends IBaseVO> extends BaseGenericFilterBuilder<VOType, ServerGenericFilterBuilder<VOType>>
{

	public ServerGenericFilterBuilder(Class<VOType> voClass)
	{
		super(voClass.getName());
	}

	public static <T extends IBaseVO> ClientGenericFilterBuilder<T> createGenericFilter(Class<T> voClass)
	{
		return new ClientGenericFilterBuilder<T>(voClass.getName());
	}

	public static <T extends IBaseVO> ClientGenericFilterBuilder<T> createGenericFilter(String voClassName)
	{
		return new ClientGenericFilterBuilder<T>(voClassName);
	}

	@Override
	protected ServerGenericFilterBuilder<VOType> getFilterBuilder()
	{
		return this;
	}
}
