package de.pellepelster.myadmin.dsl.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.google.common.base.Function;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;

import de.pellepelster.myadmin.dsl.myAdminDsl.Dictionary;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModelRoot;
import de.pellepelster.myadmin.dsl.myAdminDsl.PackageDeclaration;
import de.pellepelster.myadmin.dsl.query.ModelQuery;

public class ModelUtil
{
	private static Logger LOG = Logger.getLogger(ModelUtil.class);

	private static final URIConverter uriConverter = new ExtensibleURIConverterImpl();

	public static Collection<PackageDeclaration> getRootPackages(Model model)
	{
		return ModelQuery.createQuery(model).getRootPackages().getList();
	}

	public static PackageDeclaration getSingleRootPackage(Model model)
	{
		return ModelQuery.createQuery(model).getRootPackages().getSingleResult();
	}

	public static boolean hasSingleRootPackage(Model model)
	{
		return ModelQuery.createQuery(model).getRootPackages().hasExactlyOne();
	}

	// public static void createUpdateOrCreateRootPackage(Model model, String
	// packageName)
	// {
	// PackageDeclaration packageDeclaration;
	// Collection<PackageDeclaration> rootPackages = getRootPackages(model);
	//
	// if (rootPackages.isEmpty())
	// {
	// packageDeclaration =
	// MyAdminDslFactory.eINSTANCE.createPackageDeclaration();
	// model.getElements().add(packageDeclaration);
	// }
	// else if (rootPackages.size() == 1)
	// {
	// packageDeclaration = rootPackages.iterator().next();
	// }
	// else
	// {
	// throw new
	// RuntimeException(String.format("more than one or no root package found for model '%s'",
	// model.getName()));
	// }
	//
	// packageDeclaration.setName(packageName);
	// }

	public static Model getModelFromFile(URI uri)
	{
		return (Model) getModelRootFromFile(uri);
	}

	private static ModelRoot getModelRootFromFile(URI uri)
	{
		ResourceSet resourceSet = new ResourceSetImpl();

		Resource resource = null;
		try
		{
			resource = resourceSet.createResource(uri);
			resource.load(uriConverter.createInputStream(uri), resourceSet.getLoadOptions());
		}
		catch (Exception e)
		{
			LOG.error(String.format("error creating resource for '%s'", resource.getURI().toString()), e);
		}

		if (!resource.getContents().isEmpty())
		{
			if (resource.getContents().get(0) instanceof Model)
			{
				return (Model) resource.getContents().get(0);
			}
			else if (resource.getContents().get(0) instanceof PackageDeclaration)
			{
				return (PackageDeclaration) resource.getContents().get(0);
			}
			else
			{
				throw new RuntimeException(String.format("unknown model root '%s'", resource.getContents().get(0).eClass().toString()));
			}
		}

		return null;
	}

	public static <T extends EObject> T getParentEObject(EObject eObject, Class<T> eObjectClass)
	{
		return getParentEObject(eObject, eObjectClass, true);
	}

	@SuppressWarnings("unchecked")
	public static <T extends EObject> T getParentEObject(EObject eObject, Class<T> eObjectClass, boolean optional)
	{
		EObject currentEObject = eObject;

		int count = 0;
		while (currentEObject != null && !eObjectClass.isAssignableFrom(currentEObject.getClass()))
		{
			// see EcoreUtil.getRootContainer(EObject)
			if (++count > 100000)
			{
				break;
			}
			currentEObject = currentEObject.eContainer();
		}

		if (currentEObject != null)
		{
			return (T) currentEObject;
		}
		else
		{
			if (optional)
			{
				return null;
			}
			else
			{
				throw new RuntimeException(String.format("no parent object of type '%s' found for '%s'", eObjectClass.getName(), eObject.toString()));
			}

		}
	}

	public static Object getControlRef(Object dictionaryControl)
	{
		try
		{
			return PropertyUtils.getProperty(dictionaryControl, "ref");
		}
		catch (Exception e)
		{
			// ignore nonexisting refs
		}

		return null;
	}

	public static String getControlName(DictionaryControl dictionaryControl)
	{
		return getControlAttribute(dictionaryControl, new Function<DictionaryControl, String>()
		{
			@Override
			public String apply(DictionaryControl dictionaryControl)
			{
				return dictionaryControl.getName();
			}
		});
	}

	public static EntityAttribute getEntityAttribute(DictionaryControl dictionaryControl)
	{
		return getControlAttribute(dictionaryControl, new Function<DictionaryControl, EntityAttribute>()
		{
			@Override
			public EntityAttribute apply(DictionaryControl dictionaryControl)
			{
				if (dictionaryControl.getBaseControl() != null)
				{
					return dictionaryControl.getBaseControl().getEntityattribute();
				}
				else
				{
					return null;
				}
			}
		});
	}

	public static <T> T getControlAttribute(DictionaryControl dictionaryControl, Function<DictionaryControl, T> function)
	{
		ArrayList<DictionaryControl> controlHierarchy = getControlHierarchy(dictionaryControl);

		return Iterables.getFirst(Iterables.filter(Iterables.transform(controlHierarchy, function), Predicates.notNull()), null);

		// throw new
		// RuntimeException(String.format("could not find control name for control '%s'",
		// dictionaryControl));
	}

	@SuppressWarnings("unchecked")
	public static <ControlyType extends DictionaryControl> ArrayList<ControlyType> getControlHierarchy(ControlyType dictionaryControl)
	{
		ArrayList<ControlyType> controlHierarchy = new ArrayList<ControlyType>();

		controlHierarchy.add(dictionaryControl);

		ControlyType refControl = (ControlyType) getControlRef(dictionaryControl);

		while (refControl != null)
		{
			controlHierarchy.add(refControl);

			refControl = (ControlyType) getControlRef(refControl);
		}

		return controlHierarchy;
	}

	public static Dictionary getParentDictionary(EObject eObject)
	{
		return getParentEObject(eObject, Dictionary.class, false);
	}

	public static Entity getParentEntity(EObject eObject)
	{
		return getParentEObject(eObject, Entity.class, false);
	}

	public static List<Entity> getEntityHierarchy(Entity entity)
	{
		List<Entity> entityHierarchy = new ArrayList<Entity>();

		entityHierarchy.add(entity);

		Entity entityExtends = entity.getExtends();

		while (entityExtends != null)
		{
			entityHierarchy.add(entityExtends);
			entityExtends = entityExtends.getExtends();
		}

		return entityHierarchy;
	}

	public static String getParentDictionaryName(EObject eObject)
	{
		return getParentDictionary(eObject).getName();
	}
}
