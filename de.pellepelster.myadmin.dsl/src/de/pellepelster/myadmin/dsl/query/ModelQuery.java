package de.pellepelster.myadmin.dsl.query;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.dsl.myAdminDsl.AbstractElement;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslFactory;
import de.pellepelster.myadmin.dsl.myAdminDsl.PackageDeclaration;
import de.pellepelster.myadmin.dsl.query.functions.FunctionTypeSelect;

public class ModelQuery
{
	private Model model;

	private ModelQuery(Model model)
	{
		this.model = model;
	}

	public static ModelQuery createQuery(Model model)
	{
		return new ModelQuery(model);
	}

	public PackageQuery getRootPackages()
	{
		return new PackageQuery(Collections2.transform(this.model.eContents(), FunctionTypeSelect.getFunction(PackageDeclaration.class)));
	}

	public EntitiesQuery getAllEntities()
	{
		Iterator<Entity> entities = Iterators.transform(this.model.eAllContents(), FunctionTypeSelect.getFunction(Entity.class));
		return new EntitiesQuery(Lists.newArrayList(Iterators.filter(entities, Predicates.notNull())));
	}

	public EntityQuery getEntityByName(String entityName)
	{
		return getAllEntities().getByName(entityName);
	}

	private List<PackageDeclaration> getPackages(List<AbstractElement> abstractElements)
	{
		Iterator<PackageDeclaration> entities = Iterators.transform(abstractElements.iterator(), FunctionTypeSelect.getFunction(PackageDeclaration.class));
		return Lists.newArrayList(Iterators.filter(entities, Predicates.notNull()));
	}

	public void createPackageHiararchy(Collection<PackageDeclaration> packageDeclarations, String parentPackageName,
			SortedMap<String, PackageDeclaration> packageHierarchy)
	{
		String delimiter = "";

		if (parentPackageName != null && !parentPackageName.isEmpty())
		{
			delimiter = ".";
		}

		for (PackageDeclaration packageDeclaration : packageDeclarations)
		{
			String currentPackageName = parentPackageName + delimiter + packageDeclaration.getName();
			packageHierarchy.put(currentPackageName, packageDeclaration);

			createPackageHiararchy(getPackages(packageDeclaration.getElements()), currentPackageName, packageHierarchy);
		}
	}

	public PackageDeclaration getPackageByName(Model model, Collection<PackageDeclaration> packageDeclarations, String packageName, boolean create)
	{
		SortedMap<String, PackageDeclaration> packageHierarchy = new TreeMap<String, PackageDeclaration>();
		createPackageHiararchy(packageDeclarations, "", packageHierarchy);

		if (packageHierarchy.containsKey(packageName))
		{
			return packageHierarchy.get(packageName);
		}

		if (create)
		{
			String tempPackageName = packageName;
			PackageDeclaration parentPackage = null;

			while (tempPackageName.lastIndexOf(".") > -1)
			{
				tempPackageName = tempPackageName.substring(0, tempPackageName.lastIndexOf("."));

				if (packageHierarchy.containsKey(tempPackageName))
				{
					parentPackage = packageHierarchy.get(tempPackageName);
					break;
				}
			}

			if (parentPackage != null)
			{
				String existingPackage = tempPackageName;
				String packageToCreate = packageName.substring(tempPackageName.length() + 1);

				PackageDeclaration packageToSplit = null;

				tempPackageName = packageToCreate;
				while (tempPackageName.lastIndexOf(".") > -1)
				{
					tempPackageName = tempPackageName.substring(0, tempPackageName.lastIndexOf("."));

					for (PackageDeclaration p : getPackages(parentPackage.getElements()))
					{
						if (p.getName().startsWith(tempPackageName))
						{
							packageToSplit = p;
							existingPackage = tempPackageName;
							break;
						}
					}
				}

				if (packageToSplit == null)
				{
					PackageDeclaration newPackage = MyAdminDslFactory.eINSTANCE.createPackageDeclaration();
					newPackage.setName(packageToCreate);

					parentPackage.getElements().add(newPackage);

					return newPackage;
				}
				else
				{
					String oldPackageName = packageToSplit.getName().substring(0, existingPackage.length());
					String oldPackageSplitName = packageToSplit.getName().substring(existingPackage.length() + 1);
					packageToCreate = packageToCreate.substring(existingPackage.length() + 1);

					packageToSplit.setName(oldPackageName);

					PackageDeclaration splitPackage = MyAdminDslFactory.eINSTANCE.createPackageDeclaration();
					splitPackage.setName(oldPackageSplitName);

					splitPackage.getElements().addAll(packageToSplit.getElements());
					splitPackage.getElements().clear();

					packageToSplit.getElements().add(splitPackage);

					PackageDeclaration newPackage = MyAdminDslFactory.eINSTANCE.createPackageDeclaration();
					newPackage.setName(packageToCreate);
					packageToSplit.getElements().add(newPackage);

					return newPackage;
				}
			}

			PackageDeclaration newPackage = MyAdminDslFactory.eINSTANCE.createPackageDeclaration();
			newPackage.setName(packageName);
			model.getElements().add(newPackage);

			return newPackage;

		}

		return null;
	}

	public PackageDeclaration getPackageByName(String packageName)
	{
		return getPackageByName(this.model, getRootPackages().getList(), packageName, false);
	}

	public PackageDeclaration getAndCreatePackageByName(String packageName)
	{
		return getPackageByName(this.model, getRootPackages().getList(), packageName, true);
	}
}
