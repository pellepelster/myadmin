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

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import de.pellepelster.myadmin.dsl.MyAdminDslStandaloneSetup;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityType;
import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.query.EntityAttributeQuery;
import de.pellepelster.myadmin.dsl.query.ModelQuery;
import de.pellepelster.myadmin.tools.util.SpringModelUtils;

public class EntityAttributeQueryTest
{
	private Model model = SpringModelUtils.getModel("classpath:model/EntityModel.msl");

	@BeforeClass
	public static void init()
	{
		MyAdminDslStandaloneSetup.doSetup();
	}

	@Test
	public void testGetAttributesByType()
	{
		Assert.assertEquals(1, ModelQuery.createQuery(this.model).getEntityByName("Entity1").getAttributes().getTypes(EntityType.class).getList().size());

		// reference Entity3 entity3
		EntityAttributeQuery entityAttributeQuery = ModelQuery.createQuery(this.model).getEntityByName("Entity1").getAttributes().getAttributeByName("entity3");
		Assert.assertTrue(entityAttributeQuery.hasEntity());
		Assert.assertTrue(Entity.class.isAssignableFrom(entityAttributeQuery.getEntity().getClass()));

		// referencedatatype Entity4Datatype entity4
		entityAttributeQuery = ModelQuery.createQuery(this.model).getEntityByName("Entity1").getAttributes().getAttributeByName("entity4");
		Assert.assertTrue(entityAttributeQuery.hasEntity());
		Assert.assertTrue(Entity.class.isAssignableFrom(entityAttributeQuery.getEntity().getClass()));

		// datatype Entity2Datatype entity2
		entityAttributeQuery = ModelQuery.createQuery(this.model).getEntityByName("Entity1").getAttributes().getAttributeByName("entity2");
		Assert.assertTrue(entityAttributeQuery.hasEntity());
		Assert.assertTrue(Entity.class.isAssignableFrom(entityAttributeQuery.getEntity().getClass()));
	}

	@Test
	public void testGetAttributesType()
	{
		Assert.assertEquals(1, ModelQuery.createQuery(this.model).getEntityByName("Entity1").getAttributes().getTypes(EntityType.class).getList().size());

		// reference Entity3 entity3
		EntityAttributeQuery entityAttributeQuery = ModelQuery.createQuery(this.model).getEntityByName("Entity1").getAttributes().getAttributeByName("entity3");

		Assert.assertTrue(EntityType.class.isAssignableFrom(entityAttributeQuery.getType().getClass()));
	}

}
