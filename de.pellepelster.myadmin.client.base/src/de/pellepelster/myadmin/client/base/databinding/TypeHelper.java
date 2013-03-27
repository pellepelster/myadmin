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
package de.pellepelster.myadmin.client.base.databinding;

import java.math.BigDecimal;

/**
 * @author pelle
 * 
 */
public final class TypeHelper
{
	public static Object convert(Class<?> targetClass, Object value)
	{
		if (value == null)
		{
			return null;
		}

		// work around the lack of isAssignableFrom in GWT
		if (Integer.class.getName().equals(targetClass.getName()) && value instanceof String)
		{
			String valueString = value.toString();

			if (valueString.isEmpty())
			{
				return null;
			}

			return Integer.parseInt(valueString);
		}

		if (BigDecimal.class.getName().equals(targetClass.getName()) && value instanceof String)
		{
			String valueString = value.toString();

			if (valueString.isEmpty())
			{
				return null;
			}

			return new BigDecimal(valueString);
		}

		return value;
	}

	private void TypeHelper()
	{
	}

}
