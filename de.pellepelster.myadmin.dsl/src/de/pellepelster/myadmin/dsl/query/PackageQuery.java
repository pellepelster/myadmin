package de.pellepelster.myadmin.dsl.query;

import java.util.Collection;

import de.pellepelster.myadmin.dsl.myAdminDsl.PackageDeclaration;

public class PackageQuery
{
	private Collection<PackageDeclaration> packages;

	public PackageQuery(Collection<PackageDeclaration> packages)
	{
		this.packages = packages;
	}

	public Collection<PackageDeclaration> getPackages()
	{
		return packages;
	}

	public boolean hasOnePackage()
	{
		return packages.size() == 1;
	}

	public PackageDeclaration getSinglePackage()
	{
		if (!hasOnePackage())
		{
			throw new RuntimeException(String.format("more than one or package found for model"));
		}

		return packages.iterator().next();
	}

}
