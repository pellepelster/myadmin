package de.pellepelster.myadmin.dsl.query;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;

import de.pellepelster.myadmin.dsl.query.functions.FunctionEReferenceTypeSelect;
import de.pellepelster.myadmin.dsl.query.functions.FunctionStructuralFeatureTypeSelect;
import de.pellepelster.myadmin.dsl.query.predicates.EObjectStructuralFeatureEqualsPredicate;

public class BaseEObjectQuery<T extends EObject>
{
	private Collection<T> eObjects;

	public BaseEObjectQuery(Collection<T> eObjects)
	{
		this.eObjects = eObjects;
	}

	public Collection<T> getList()
	{
		return this.eObjects;
	}

	public boolean hasExactlyOne()
	{
		return getList().size() == 1;
	}

	public T getSinglePackage()
	{
		if (!hasExactlyOne())
		{
			throw new RuntimeException(String.format("found %d but expected expected one", getList().size()));
		}

		return getList().iterator().next();
	}

	protected <K extends EObject> Collection<K> transform(EStructuralFeature eStructuralFeature, Class<K> typeClass)
	{
		Collection<K> result = Collections2.transform(getList(), FunctionStructuralFeatureTypeSelect.create(eStructuralFeature, typeClass));

		return Collections2.filter(result, Predicates.notNull());
	}

	protected <K extends EObject> Collection<K> transform(EReference eReference, Class<K> typeClass)
	{
		Collection<K> result = Collections2.transform(getList(), FunctionEReferenceTypeSelect.create(eReference, typeClass));

		return Collections2.filter(result, Predicates.notNull());
	}

	protected Collection<T> getByEStructuralFeature(EStructuralFeature eStructuralFeature, Object object)
	{
		return Collections2.filter(getList(), EObjectStructuralFeatureEqualsPredicate.create(eStructuralFeature, "Entity1"));
	}

	protected T getSingleByEStructuralFeature(EStructuralFeature eStructuralFeature, Object object)
	{
		Collection<T> result = getByEStructuralFeature(eStructuralFeature, object);

		if (result.size() != 1)
		{
			throw new RuntimeException(String.format("%d EObjects match '%s' for '%s'", result.size(), eStructuralFeature.getName(), object.toString()));
		}
		else
		{
			return result.iterator().next();
		}
	}

}
