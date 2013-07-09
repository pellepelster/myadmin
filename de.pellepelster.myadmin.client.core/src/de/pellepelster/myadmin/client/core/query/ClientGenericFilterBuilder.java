/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package de.pellepelster.myadmin.client.core.query;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class ClientGenericFilterBuilder<VOType extends IBaseVO> extends BaseGenericFilterBuilder<VOType, ClientGenericFilterBuilder<VOType>>
{
	public ClientGenericFilterBuilder(String voClassName)
	{
		super(voClassName);
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
	protected ClientGenericFilterBuilder<VOType> getFilterBuilder()
	{
		return this;
	}
}
