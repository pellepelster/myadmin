package de.pellepelster.myadmin.dsl.ui.query;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.google.common.collect.Collections2;

import de.pellepelster.myadmin.dsl.query.predicates.EObjectImplementsPredicate;

public class FilteredEmfTreeContentProvider implements ITreeContentProvider
{
	private EObjectImplementsPredicate allowedEObjectTypes;

	public FilteredEmfTreeContentProvider(Class<?>... classes)
	{
		super();
		this.allowedEObjectTypes = EObjectImplementsPredicate.create(classes);
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
	}

	@Override
	public Object[] getElements(Object inputElement)
	{
		if (inputElement instanceof EObject)
		{
			EObject eObject = (EObject) inputElement;
			return Collections2.filter(eObject.eContents(), this.allowedEObjectTypes).toArray();
		}
		else
		{
			throw new RuntimeException(String.format("unsupported element type ''%s'", inputElement.getClass().getName()));
		}
	}

	@Override
	public Object[] getChildren(Object parentElement)
	{
		return getElements(parentElement);
	}

	@Override
	public Object getParent(Object element)
	{
		if (element instanceof EObject)
		{
			EObject eObject = (EObject) element;
			return eObject.eContainer();
		}
		else
		{
			throw new RuntimeException(String.format("unsupported element type ''%s'", element.getClass().getName()));
		}
	}

	@Override
	public boolean hasChildren(Object element)
	{
		if (element instanceof EObject)
		{
			EObject eObject = (EObject) element;
			return !eObject.eContents().isEmpty();
		}
		else
		{
			throw new RuntimeException(String.format("unsupported element type ''%s'", element.getClass().getName()));
		}
	}

}
