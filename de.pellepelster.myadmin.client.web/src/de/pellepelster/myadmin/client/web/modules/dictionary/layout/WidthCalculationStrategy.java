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
package de.pellepelster.myadmin.client.web.modules.dictionary.layout;

import de.pellepelster.gwt.commons.client.GwtCommons;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.controls.EnumerationControlModel;

public final class WidthCalculationStrategy
{
	private static WidthCalculationStrategy instance;

	public int getWidth(int characters, boolean uppercase)
	{
		return getWidth(characters, uppercase, 1f);
	}

	public int getWidth(int characters, boolean uppercase, float factor)
	{
		float width = characters * GwtCommons.getInstance().getAverageCharacterWidth(uppercase);

		return (int) Math.round(Math.ceil(width * factor));
	}

	public String getPxWidth(int characters, boolean uppercase, float factor)
	{
		return getWidth(characters, uppercase, factor) + "px";
	}

	public String getPxWidth(int characters, boolean uppercase)
	{
		return getWidth(characters, uppercase, 1f) + "px";
	}

	public static WidthCalculationStrategy getInstance()
	{
		if (instance == null)
		{
			instance = new WidthCalculationStrategy();
		}

		return instance;
	}

	private WidthCalculationStrategy()
	{
	}

	private boolean isUppercase(IBaseControlModel baseControlModel)
	{
		boolean uppercase = false;

		if (baseControlModel instanceof EnumerationControlModel)
		{
			EnumerationControlModel enumerationControlModel = (EnumerationControlModel) baseControlModel;

			String allEnumValues = "";
			for (String enumValue : enumerationControlModel.getEnumeration().values())
			{
				allEnumValues += enumValue;
			}

			uppercase = allEnumValues.toUpperCase().equals(allEnumValues);

		}

		return uppercase;
	}

	public int getControlWidth(IBaseControlModel baseControlModel)
	{
		if (baseControlModel.getWidthHint() == null)
		{
			return getWidth(IBaseControlModel.DEFAULT_WIDTH_HINT, isUppercase(baseControlModel));
		}
		else
		{
			return getWidth(baseControlModel.getWidthHint(), isUppercase(baseControlModel));
		}
	}

	public int getTableWidth(IBaseTableModel baseTableModel)
	{
		int widht = 0;

		for (IBaseControlModel baseControlModel : baseTableModel.getControls())
		{
			widht += getControlWidth(baseControlModel);
		}

		return widht;
	}

	public String getPxWidth(IBaseControlModel baseControlModel)
	{
		return getPxWidth(baseControlModel.getWidthHint(), isUppercase(baseControlModel));
	}

}
