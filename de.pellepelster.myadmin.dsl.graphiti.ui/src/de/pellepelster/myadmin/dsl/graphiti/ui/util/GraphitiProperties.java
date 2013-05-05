package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import org.eclipse.graphiti.mm.PropertyContainer;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;

public class GraphitiProperties
{

	static private IPeService peService = Graphiti.getPeService();

	static public void set(PictogramElement shape, String key, String value)
	{
		peService.setPropertyValue(shape, key, value);
	}

	static public void set(PropertyContainer propertyContainer, String key, String value)
	{
		peService.setPropertyValue(propertyContainer, key, value);
	}

	static public void set(PictogramElement shape, String key, int value)
	{
		peService.setPropertyValue(shape, key, Integer.toString(value));
	}

	static public void set(PictogramElement shape, String key, boolean value)
	{
		peService.setPropertyValue(shape, key, Boolean.toString(value));
	}

	static public String get(PropertyContainer propertyContainer, String key)
	{
		return peService.getPropertyValue(propertyContainer, key);
	}

	static public String get(PictogramElement shape, String key)
	{
		return peService.getPropertyValue(shape, key);
	}

	static public int getIntValue(PictogramElement shape, String key)
	{
		String intString = peService.getPropertyValue(shape, key);
		int result;
		try
		{
			result = Integer.parseInt(intString);
		}
		catch (NumberFormatException e)
		{
			result = 0;
		}
		return result;
	}

	static public boolean getBooleanValue(PictogramElement shape, String key)
	{
		String bool = peService.getPropertyValue(shape, key);
		boolean result = false;
		try
		{
			result = Boolean.parseBoolean(bool);
		}
		catch (NumberFormatException e)
		{
			result = false;
		}
		return result;
	}

}
