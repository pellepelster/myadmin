package de.pellepelster.myadmin.dsl.util;

import java.util.Collection;

import org.apache.log4j.Logger;

import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModelRoot;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;
import de.pellepelster.myadmin.dsl.myAdminDsl.PackageDeclaration;
import de.pellepelster.myadmin.dsl.query.ModelQuery;

public class ModelUtil
{
	private static Logger LOG = Logger.getLogger(ModelUtil.class);

	public static Collection<PackageDeclaration> getRootPackages(ModelRoot model)
	{
		return ModelQuery.createQuery(model).getRootPackages().getPackages();
	}

	public static PackageDeclaration getSingleRootPackage(Model model)
	{
		return ModelQuery.createQuery(model).getRootPackages().getSinglePackage();
	}

	public static boolean hasSingleRootPackage(ModelRoot model)
	{
		return ModelQuery.createQuery(model).getRootPackages().hasOnePackage();
	}

	public static void createUpdateOrCreateRootPackage(Model model, String packageName)
	{
		PackageDeclaration packageDeclaration;
		Collection<PackageDeclaration> rootPackages = getRootPackages(model);

		if (rootPackages.isEmpty())
		{
			packageDeclaration = MyAdminDslFactory.eINSTANCE.createPackageDeclaration();
			model.getElements().add(packageDeclaration);
		}
		else if (rootPackages.size() == 1)
		{
			packageDeclaration = rootPackages.iterator().next();
		}
		else
		{
			throw new RuntimeException(String.format("more than one or no root package found for model '%s'", model.getName()));
		}

		packageDeclaration.setName(packageName);
	}

}
