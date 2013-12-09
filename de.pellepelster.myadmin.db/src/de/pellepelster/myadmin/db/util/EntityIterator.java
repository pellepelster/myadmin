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
package de.pellepelster.myadmin.db.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class EntityIterator implements Iterable<IBaseVO>, Iterator<IBaseVO>
{

	private final Iterator<IBaseVO> iterator;

	public EntityIterator(IBaseVO vo)
	{
		List<IBaseVO> vos = new ArrayList<IBaseVO>();
		createAttributeList(vo, vos);
		this.iterator = vos.iterator();
	}

	@SuppressWarnings("unchecked")
	private void createAttributeList(IBaseVO vo, List<IBaseVO> vos)
	{
		if (vos.contains(vo))
		{
			return;
		}

		for (IAttributeDescriptor<?> attributeDescriptor : BeanUtil.getAttributeDescriptors(vo.getClass()))
		{
			if (IBaseVO.class.isAssignableFrom(attributeDescriptor.getAttributeType()))
			{
				createAttributeList((IBaseVO) vo.get(attributeDescriptor.getAttributeName()), vos);
			}
			else if (List.class.isAssignableFrom(attributeDescriptor.getAttributeType())
					&& IBaseVO.class.isAssignableFrom(attributeDescriptor.getListAttributeType()))
			{
				List<IBaseVO> voList = (List<IBaseVO>) vo.get(attributeDescriptor.getAttributeName());
				vos.addAll(voList);

				for (IBaseVO voListElement : voList)
				{
					createAttributeList(voListElement, vos);
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasNext()
	{
		return this.iterator.hasNext();
	}

	/** {@inheritDoc} */
	@Override
	public Iterator<IBaseVO> iterator()
	{
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public IBaseVO next()
	{
		return this.iterator.next();
	}

	/** {@inheritDoc} */
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
