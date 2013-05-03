package de.pellepelster.myadmin.dsl.graphiti.ui.util;

import org.eclipse.graphiti.mm.pictograms.Shape;

import com.google.common.base.Function;

public class FunctionGaTypeSelect<T> implements Function<Shape, T>
{
	public static <T> FunctionGaTypeSelect<T> getFunction(Class<T> clazz)
	{
		return new FunctionGaTypeSelect<>(clazz);
	}

	private Class<T> clazz;

	private FunctionGaTypeSelect(Class<T> clazz)
	{
		super();
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T apply(Shape shape)
	{
		if (clazz.isAssignableFrom(shape.getGraphicsAlgorithm().getClass()))
		{
			return (T) shape.getGraphicsAlgorithm();
		}

		return null;
	}

}
