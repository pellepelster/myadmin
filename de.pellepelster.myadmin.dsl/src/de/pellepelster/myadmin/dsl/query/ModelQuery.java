package de.pellepelster.myadmin.dsl.query;

import java.util.Iterator;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModelRoot;
import de.pellepelster.myadmin.dsl.myAdminDsl.PackageDeclaration;
import de.pellepelster.myadmin.dsl.query.functions.FunctionTypeSelect;

public class ModelQuery
{
	private ModelRoot model;

	private ModelQuery(ModelRoot model)
	{
		this.model = model;
	}

	public static ModelQuery createQuery(ModelRoot model)
	{
		return new ModelQuery(model);
	}

	public PackageQuery getRootPackages()
	{
		return new PackageQuery(Collections2.transform(model.eContents(), FunctionTypeSelect.getFunction(PackageDeclaration.class)));
	}

	public EntitiesQuery getAllEntities()
	{
		Iterator<Entity> entities = Iterators.transform(model.eAllContents(), FunctionTypeSelect.getFunction(Entity.class));
		return new EntitiesQuery(Lists.newArrayList(Iterators.filter(entities, Predicates.notNull())));
	}

	public EntityQuery getEntityByName(String entityName)
	{
		return getAllEntities().getByName(entityName);
	}
}
