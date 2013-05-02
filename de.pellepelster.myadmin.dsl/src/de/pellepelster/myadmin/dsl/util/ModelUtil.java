package de.pellepelster.myadmin.dsl.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModelRoot;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;
import de.pellepelster.myadmin.dsl.myAdminDsl.PackageDeclaration;

public class ModelUtil
{
	private static Logger LOG = Logger.getLogger(ModelUtil.class);

	public static <T> List<T> selectType(Iterator<EObject> sourceList, Class<T> clazz)
	{
		List<T> filteredList = new ArrayList<T>();

		List<EObject> l = Lists.newArrayList(sourceList);

		filteredList = Lists.newArrayList(Iterators.filter(sourceList, clazz));

		return filteredList;
	}

	public static Collection<PackageDeclaration> getRootPackages(ModelRoot model)
	{
		return Collections2.transform(model.eContents(), new Function<EObject, PackageDeclaration>()
		{
			@Override
			public PackageDeclaration apply(@Nullable EObject eObject)
			{
				if (eObject instanceof PackageDeclaration)
				{
					return (PackageDeclaration) eObject;
				}

				return null;
			}
		});
	}

	public static PackageDeclaration getFirstRootPackage(ModelRoot model)
	{
		return getRootPackages(model).iterator().next();
	}

	public static boolean hasRootPackage(ModelRoot model)
	{
		return !getRootPackages(model).isEmpty();
	}

	public static void createRootPackage(Model model, String packageName)
	{
		PackageDeclaration packageDeclaration = MyAdminDslFactory.eINSTANCE.createPackageDeclaration();
		packageDeclaration.setName(packageName);
		model.getElements().add(packageDeclaration);
	}

}
