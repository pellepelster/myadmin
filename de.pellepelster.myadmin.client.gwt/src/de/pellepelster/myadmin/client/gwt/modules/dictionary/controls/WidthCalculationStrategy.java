package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import de.pellepelster.gwt.commons.client.GwtCommons;

public class WidthCalculationStrategy
{
	public static int getWidth(int characters)
	{
		float width = characters * GwtCommons.getInstance().getAverageCharacterWidth();
		return (int) Math.round(Math.ceil(width));
	}

	public static String getPxWidth(int characters)
	{
		return getWidth(characters) + "px";
	}
}
