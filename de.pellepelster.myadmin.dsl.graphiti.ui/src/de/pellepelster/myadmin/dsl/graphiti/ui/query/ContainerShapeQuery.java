package de.pellepelster.myadmin.dsl.graphiti.ui.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.graphiti.mm.PropertyContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.FunctionGaTypeSelect;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.GraphitiProperties;

public class ContainerShapeQuery
{
	private ContainerShape containerShape;

	public static ContainerShapeQuery create(ContainerShape containerShape)
	{
		return new ContainerShapeQuery(containerShape);
	}

	private ContainerShapeQuery(ContainerShape containerShape)
	{
		super();
		this.containerShape = containerShape;
	}

	private List<Shape> getShapes(ContainerShape containerShape)
	{
		List<Shape> shapes = new ArrayList<Shape>();
		getShapes(containerShape, shapes);
		return shapes;
	}

	private void getShapes(ContainerShape containerShape, List<Shape> shapes)
	{
		for (Shape shape : containerShape.getChildren())
		{
			shapes.add(shape);

			if (shape instanceof ContainerShape)
			{
				shapes.addAll(getShapes((ContainerShape) shape));
			}
		}
	}

	private List<ContainerShape> getContainerShapes(ContainerShape containerShape)
	{
		List<ContainerShape> containerShapes = new ArrayList<ContainerShape>();
		getContainerShapes(containerShape, containerShapes);
		return containerShapes;
	}

	private void getContainerShapes(ContainerShape containerShape, List<ContainerShape> containerShapes)
	{
		for (Shape shape : containerShape.getChildren())
		{
			if (shape instanceof ContainerShape)
			{
				containerShapes.add((ContainerShape) shape);
				getContainerShapes((ContainerShape) shape, containerShapes);
			}
		}
	}

	private <T> Collection<T> getGraphicsAlgorithms(ContainerShape containerShape, Class<T> clazz)
	{
		List<Shape> shapes = getShapes(containerShape);
		List<T> gas = Lists.transform(shapes, FunctionGaTypeSelect.getFunction(clazz));
		return Collections2.filter(gas, Predicates.notNull());
	}

	public <T extends PropertyContainer> Collection<T> getGraphicsAlgorithmsById(final String elementId, Class<T> clazz)
	{
		return Collections2.filter(getGraphicsAlgorithms(this.containerShape, clazz), new Predicate<PropertyContainer>()
		{

			@Override
			public boolean apply(PropertyContainer propertyContainer)
			{
				return elementId.equals(GraphitiProperties.get(propertyContainer, MyAdminGraphitiConstants.ELEMENT_ID_KEY));
			}
		});
	}

	public <T extends PropertyContainer> T getGraphicsAlgorithmById(final String elementId, Class<T> clazz)
	{
		Collection<T> graphicsAlgorithms = getGraphicsAlgorithmsById(elementId, clazz);

		if (graphicsAlgorithms.size() == 1)
		{
			return graphicsAlgorithms.iterator().next();
		}
		else
		{
			throw new RuntimeException(String.format("found %d but expectedt exactly one matching element found for element id '%s' on container '%s'",
					graphicsAlgorithms.size(), elementId, this.containerShape.toString()));
		}
	}

	public Collection<Polyline> getPolylinesById(final String elementId)
	{
		return getGraphicsAlgorithmsById(elementId, Polyline.class);
	}

	public Polyline getPolylineById(final String elementId)
	{
		return getGraphicsAlgorithmById(elementId, Polyline.class);
	}

	public Collection<Text> getTextsById(final String elementId)
	{
		return getGraphicsAlgorithmsById(elementId, Text.class);
	}

	public Text getTextById(final String elementId)
	{
		return getGraphicsAlgorithmById(elementId, Text.class);
	}

	public Collection<ContainerShape> getContainerShapesById(final String elementId)
	{
		return Collections2.filter(getContainerShapes(this.containerShape), new Predicate<PropertyContainer>()
		{
			@Override
			public boolean apply(PropertyContainer propertyContainer)
			{
				return elementId.equals(GraphitiProperties.get(propertyContainer, MyAdminGraphitiConstants.ELEMENT_ID_KEY));
			}
		});
	}

}
