package de.pellepelster.myadmin.dsl.query;

import java.util.Collection;

import de.pellepelster.myadmin.dsl.myAdminDsl.PackageDeclaration;

public class PackageQuery extends BaseEObjectQuery<PackageDeclaration>
{
	public PackageQuery(Collection<PackageDeclaration> packages)
	{
		super(packages);
	}

}
