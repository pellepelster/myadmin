package de.pellepelster.myadmin.dsl.util;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ExtensibleURIConverterImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

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
		return ModelQuery.createQuery(model).getRootPackages().getSinglePackage();
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

}
