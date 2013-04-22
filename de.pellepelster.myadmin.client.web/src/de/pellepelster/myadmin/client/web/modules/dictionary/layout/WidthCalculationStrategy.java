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

public final class WidthCalculationStrategy
{
	private static WidthCalculationStrategy instance;

	public int getWidth(int characters)
	{
		return getWidth(characters, 1f);
	}

	public int getWidth(int characters, float factor)
	{
		float width = characters * GwtCommons.getInstance().getAverageCharacterWidth();
		return (int) Math.round(Math.ceil(width * factor));
	}

	public String getPxWidth(int characters, float factor)
	{
		return getWidth(characters, factor) + "px";
	}

	public String getPxWidth(int characters)
	{
		return getWidth(characters, 1f) + "px";
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

	public int getControlWidth(IBaseControlModel baseControlModel)
	{
		if (baseControlModel.getWidthHint() == null)
		{
			return getWidth(IBaseControlModel.DEFAULT_WIDTH_HINT);
		}
		else
		{
			return getWidth(baseControlModel.getWidthHint());
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
}
