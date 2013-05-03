package de.pellepelster.myadmin.dsl.query.functions;

import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Function;

public class FunctionTypeSelect<T> implements Function<EObject, T>
{
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> FunctionTypeSelect<T> getFunction(Class<T> clazz)
	{
		return new FunctionTypeSelect(clazz);
	}

	private Class<T> clazz;

	private FunctionTypeSelect(Class<T> clazz)
	{
		super();
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T apply(EObject eObject)
	{
		if (clazz.isAssignableFrom(eObject.getClass()))
		{
			return (T) eObject;
		}

		return null;
	}
}
