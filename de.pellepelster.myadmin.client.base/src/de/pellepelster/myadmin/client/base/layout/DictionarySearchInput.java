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
package de.pellepelster.myadmin.client.base.layout;

public class DictionarySearchInput implements IContainerInput
{

	private final String dictionaryName;

	public DictionarySearchInput(String dictionaryName)
	{
		this.dictionaryName = dictionaryName;
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		DictionarySearchInput other = (DictionarySearchInput) obj;
		if (dictionaryName == null)
		{
			if (other.dictionaryName != null)
			{
				return false;
			}
		}
		else if (!dictionaryName.equals(other.dictionaryName))
		{
			return false;
		}
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public String getInputId()
	{
		return dictionaryName;
	}

	/** {@inheritDoc} */
	@Override
	public String getLabel()
	{
		return dictionaryName;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dictionaryName == null) ? 0 : dictionaryName.hashCode());
		return result;
	}

}
