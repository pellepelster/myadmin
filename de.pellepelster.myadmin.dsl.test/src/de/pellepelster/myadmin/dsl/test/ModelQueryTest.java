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
import de.pellepelster.myadmin.dsl.myAdminDsl.Dictionary;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryBigDecimalControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryBooleanControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryDateControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEnumerationControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryIntegerControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryReferenceControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryTextControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.query.ModelQuery;
import de.pellepelster.myadmin.dsl.query.controls.DictionaryControlQuery;
import de.pellepelster.myadmin.tools.util.SpringModelUtils;

public class ModelQueryTest
{

	@BeforeClass
	public static void init()
	{
		MyAdminDslStandaloneSetup.doSetup();
	}

	@Test
	public void testQueryRootPackages()
	{
		Model model = SpringModelUtils.getModel("classpath:model/SingleRootPackageModel.msl");

		Assert.assertTrue(ModelQuery.createQuery(model).getRootPackages().hasExactlyOne());
		Assert.assertEquals("a.b", ModelQuery.createQuery(model).getRootPackages().getSingleResult().getName());
	}

	@Test
	public void testGetPackagesByName()
	{
		Model model = SpringModelUtils.getModel("classpath:model/PackagesModel.msl");

		Assert.assertEquals("c.d", ModelQuery.createQuery(model).getPackageByName("a.b.c.d").getName());
		Assert.assertEquals("a.b", ModelQuery.createQuery(model).getPackageByName("a.b").getName());
	}

	@Test
	public void testGetAndCreatePackageByName()
	{
		Model model = SpringModelUtils.getModel("classpath:model/PackagesModel.msl");

		Assert.assertEquals("e.f", ModelQuery.createQuery(model).getAndCreatePackageByName("a.b.c.d.e.f").getName());
		Assert.assertEquals("e.f", ModelQuery.createQuery(model).getPackageByName("a.b.c.d.e.f").getName());

		Assert.assertEquals("a.b", ModelQuery.createQuery(model).getAndCreatePackageByName("a.b").getName());
		Assert.assertEquals("c.d", ModelQuery.createQuery(model).getAndCreatePackageByName("a.b.c.d").getName());

	}

	@Test
	public void testGetAndCreateSplitPackageByName()
	{
		Model model = SpringModelUtils.getModel("classpath:model/PackagesModel.msl");

		Assert.assertEquals("x", ModelQuery.createQuery(model).getAndCreatePackageByName("a.b.c.x").getName());
		Assert.assertEquals("c", ModelQuery.createQuery(model).getPackageByName("a.b.c").getName());
		Assert.assertEquals("d", ModelQuery.createQuery(model).getPackageByName("a.b.c.d").getName());
	}

	@Test
	public void testGetDictionaryByName()
	{
		Model model = SpringModelUtils.getModel("classpath:model/DictionaryQueryTest.msl");

		Dictionary dictionary = ModelQuery.createQuery(model).getDictionaryByName("TestDictionary1").getDictionary();
		Assert.assertEquals("TestDictionary1", dictionary.getName());
	}

	@Test
	public void testDictionaryControlQuery()
	{
		Model model = SpringModelUtils.getModel("classpath:model/DictionaryQueryTest.msl");

		Dictionary dictionary = ModelQuery.createQuery(model).getDictionaryByName("TestDictionary1").getDictionary();

		DictionaryTextControl textControl = (DictionaryTextControl) dictionary.getDictionaryeditor().getContainercontents().get(0);

		DictionaryControlQuery.create(textControl);
	}

	@Test
	public void testResolveTextControl()
	{
		Model model = SpringModelUtils.getModel("classpath:model/DictionaryQueryTest.msl");

		DictionaryControlQuery<?> dictionaryControlQuery = ModelQuery.createQuery(model).getDictionaryByName("TestDictionary1").getDictionaryEditor()
				.getControlsByType(DictionaryTextControl.class).getSingleDictionaryControlQuery();

		Assert.assertEquals("TextControl1", dictionaryControlQuery.getName(true));
		Assert.assertNull(dictionaryControlQuery.getName(false));

	}

	@Test
	public void testResolveIntegerControl()
	{
		Model model = SpringModelUtils.getModel("classpath:model/DictionaryQueryTest.msl");

		DictionaryControlQuery<?> dictionaryControlQuery = ModelQuery.createQuery(model).getDictionaryByName("TestDictionary1").getDictionaryEditor()
				.getControlsByType(DictionaryIntegerControl.class).getSingleDictionaryControlQuery();

		Assert.assertEquals("IntegerControl1", dictionaryControlQuery.getName(true));
		Assert.assertNull(dictionaryControlQuery.getName(false));

	}

	@Test
	public void testResolveBigDecimalControl()
	{
		Model model = SpringModelUtils.getModel("classpath:model/DictionaryQueryTest.msl");

		DictionaryControlQuery<?> dictionaryControlQuery = ModelQuery.createQuery(model).getDictionaryByName("TestDictionary1").getDictionaryEditor()
				.getControlsByType(DictionaryBigDecimalControl.class).getSingleDictionaryControlQuery();

		Assert.assertEquals("BigDecimalControl1", dictionaryControlQuery.getName(true));
		Assert.assertNull(dictionaryControlQuery.getName(false));

	}

	@Test
	public void testResolveDateControl()
	{
		Model model = SpringModelUtils.getModel("classpath:model/DictionaryQueryTest.msl");

		DictionaryControlQuery<?> dictionaryControlQuery = ModelQuery.createQuery(model).getDictionaryByName("TestDictionary1").getDictionaryEditor()
				.getControlsByType(DictionaryDateControl.class).getSingleDictionaryControlQuery();

		Assert.assertEquals("DateControl1", dictionaryControlQuery.getName(true));
		Assert.assertNull(dictionaryControlQuery.getName(false));

	}

	@Test
	public void testResolveBooleanControl()
	{
		Model model = SpringModelUtils.getModel("classpath:model/DictionaryQueryTest.msl");

		DictionaryControlQuery<?> dictionaryControlQuery = ModelQuery.createQuery(model).getDictionaryByName("TestDictionary1").getDictionaryEditor()
				.getControlsByType(DictionaryBooleanControl.class).getSingleDictionaryControlQuery();

		Assert.assertEquals("BooleanControl1", dictionaryControlQuery.getName(true));
		Assert.assertNull(dictionaryControlQuery.getName(false));

	}

	@Test
	public void testResolveEnumerationControl()
	{
		Model model = SpringModelUtils.getModel("classpath:model/DictionaryQueryTest.msl");

		DictionaryControlQuery<?> dictionaryControlQuery = ModelQuery.createQuery(model).getDictionaryByName("TestDictionary1").getDictionaryEditor()
				.getControlsByType(DictionaryEnumerationControl.class).getSingleDictionaryControlQuery();

		Assert.assertEquals("EnumerationControl1", dictionaryControlQuery.getName(true));
		Assert.assertNull(dictionaryControlQuery.getName(false));

	}

	@Test
	public void testResolveReferenceControl()
	{
		Model model = SpringModelUtils.getModel("classpath:model/DictionaryQueryTest.msl");

		DictionaryControlQuery<?> dictionaryControlQuery = ModelQuery.createQuery(model).getDictionaryByName("TestDictionary1").getDictionaryEditor()
				.getControlsByType(DictionaryReferenceControl.class).getSingleDictionaryControlQuery();

		Assert.assertEquals("ReferenceControl1", dictionaryControlQuery.getName(true));
		Assert.assertNull(dictionaryControlQuery.getName(false));

	}

	@Test
	public void testGetAndCreatePackageEmptyModel()
	{
		Model model = SpringModelUtils.getModel("classpath:model/EmptyModel.msl");

		Assert.assertNull(ModelQuery.createQuery(model).getPackageByName("a"));
		Assert.assertEquals("a", ModelQuery.createQuery(model).getAndCreatePackageByName("a").getName());
		Assert.assertNotNull(ModelQuery.createQuery(model).getPackageByName("a"));
	}

}
