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
package de.pellepelster.myadmin.client.web.modules.dictionary.databinding;

import de.pellepelster.myadmin.client.base.VOBeanUtil;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class VOWrapper<VOTYpe extends IBaseVO>
{

	private boolean dirty = false;

	private VOTYpe vo;

	public VOWrapper()
	{
	}

	public VOWrapper(VOTYpe vo)
	{
		super();
		this.vo = vo;
	}

	public Object get(String name)
	{
		return this.vo.get(name);
	}

	public long getId()
	{
		return this.vo.getId();
	}

	public VOTYpe getVO()
	{
		return this.vo;
	}

	public void markClean()
	{
		this.dirty = false;
	}

	public void markDirty()
	{
		this.dirty = true;
	}

	public boolean isDirty()
	{
		return this.dirty;
	}

	public void set(String attributePath, Object value)
	{
		set(attributePath, value, true);
	}

	private void set(String attributePath, Object value, boolean fireValueChangeEvents)
	{
		VOBeanUtil.set(this.vo, attributePath, value);

		markDirty();
	}

	public void setVo(VOTYpe vo)
	{
		this.vo = vo;
		markClean();
	}
}
