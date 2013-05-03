/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package de.pellepelster.myadmin.dsl.test;

import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;

import de.pellepelster.myadmin.dsl.myAdminDsl.DatatypeType;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityType;
import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatypeType;
import de.pellepelster.myadmin.dsl.query.ModelQuery;

public class EntityQueryTest extends BaseModelTest
{
	private Model model = getModel("model/EntityModel.msl");

	@Test
	public void testGetAllEntities()
	{
		Assert.assertEquals(4, ModelQuery.createQuery(model).getAllEntities().getEntities().size());
	}

	@Test
	public void testGetEntityByName()
	{
		Entity entity1 = ModelQuery.createQuery(model).getEntityByName("Entity1").getEntity();
		Assert.assertEquals("Entity1", entity1.getName());
	}

	@Test
	public void testGetReferencedEntites()
	{
		Entity entity1 = ModelQuery.createQuery(model).getEntityByName("Entity1").getEntity();
		Assert.assertEquals("Entity1", entity1.getName());
	}

	@Test
	public void testGetAttributesByType()
	{
		Assert.assertEquals(1, ModelQuery.createQuery(model).getEntityByName("Entity1").getAttributes().getTypes(EntityType.class).getList().size());

		// reference Entity3 entity3
		Collection<Entity> entities = ModelQuery.createQuery(model).getEntityByName("Entity1").getAttributes().getTypes(EntityType.class)
				.getFeatures(MyAdminDslPackage.Literals.ENTITY_TYPE__TYPE, Entity.class);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals("Entity3", entities.iterator().next().getName());

		// referencedatatype Entity4Datatype entity4
		entities = ModelQuery.createQuery(model).getEntityByName("Entity1").getAttributes().getTypes(ReferenceDatatypeType.class)
				.getFeaturesQuery(MyAdminDslPackage.Literals.REFERENCE_DATATYPE_TYPE__TYPE, ReferenceDatatype.class)
				.getFeatures(MyAdminDslPackage.Literals.REFERENCE_DATATYPE__ENTITY, Entity.class);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals("Entity4", entities.iterator().next().getName());

		// datatype Entity2Datatype entity2
		entities = ModelQuery.createQuery(model).getEntityByName("Entity1").getAttributes().getTypes(DatatypeType.class)
				.getFeaturesQuery(MyAdminDslPackage.Literals.DATATYPE_TYPE__TYPE, ReferenceDatatype.class)
				.getFeatures(MyAdminDslPackage.Literals.REFERENCE_DATATYPE__ENTITY, Entity.class);
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals("Entity2", entities.iterator().next().getName());
	}
}
