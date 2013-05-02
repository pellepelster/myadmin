package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.graphiti.mm.PropertyContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;

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

	public static List<Shape> getShapesByElementId(ContainerShape containerShape)
	{
		List<Shape> shapes = new ArrayList<Shape>();
		getShapesByElementId(containerShape, shapes);

		return shapes;
	}

	public static List<Shape> getShapesByElementId(ContainerShape containerShape, List<Shape> shapes)
	{
		for (Shape shape : containerShape.getChildren())
		{
			shapes.add(shape);

			if (shape instanceof ContainerShape)
			{
				shapes.addAll(getShapesByElementId((ContainerShape) shape));
			}
		}

		return shapes;
	}
	
	public static List<Polyline> getPolylines(ContainerShape containerShape)
	{
		List<Shape> shapes = getShapesByElementId(containerShape);

		List<Polyline> polylines = Lists.transform(shapes, new Function<Shape, Polyline>()
		{
			@Override
			public Polyline apply(Shape shape)
			{
				if (shape.getGraphicsAlgorithm() instanceof Polyline)
				{
					return (Polyline) shape.getGraphicsAlgorithm();
				}
				
				return null;
			}
		});
		
		return polylines;
	}

	public static Collection<Polyline> getPolylinesById(ContainerShape containerShape, final String elementId)
	{
		return Collections2.filter(getPolylines(containerShape), new Predicate<PropertyContainer>()
		{

			@Override
			public boolean apply(PropertyContainer propertyContainer)
			{
				return elementId.equals(get(propertyContainer, MyAdminGraphitiConstants.ELEMENT_ID_KEY));
			}
		});
	}

	public static Polyline getPolylineById(ContainerShape containerShape, final String elementId)
	{
		Collection<Polyline> polylines = getPolylinesById(containerShape, elementId);
		
		if (polylines.size() == 1)
		{
			return polylines.iterator().next();
		}
		else
		{
			throw new RuntimeException(String.format("more than one or no matching element found for element id '%s' on container '%s'", elementId,
					containerShape.toString()));
		}
	}
}
