package de.pellepelster.myadmin.server.base.xml;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class XmlElementDescriptor
{
	private final Class<? extends IBaseVO> voClass;

	private final boolean isList;

	private final boolean isReference;

	public XmlElementDescriptor(Class<? extends IBaseVO> voClass, boolean isList, boolean isReference)
	{
		super();
		this.voClass = voClass;
		this.isList = isList;
		this.isReference = isReference;
	}

	public Class<? extends IBaseVO> getVoClass()
	{
		return this.voClass;
	}

	public boolean isList()
	{
		return this.isList;
	}

	public boolean isReference()
	{
		return this.isReference;
	}

}
