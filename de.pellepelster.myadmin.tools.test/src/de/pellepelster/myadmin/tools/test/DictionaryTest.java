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
package de.pellepelster.myadmin.tools.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.query.DictionaryModelQuery;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.client.web.services.IDictionaryService;
import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;
import de.pellepelster.myadmin.tools.SpringModelUtils;
import de.pellepelster.myadmin.tools.dictionary.DictionaryImportRunner;

public class DictionaryTest extends BaseMyAdminJndiContextTest
{

	private static DictionaryImportRunner dictionaryImportRunner;

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private IDictionaryService dictionaryService;

	public IBaseEntityService getBaseEntityService()
	{
		return this.baseEntityService;
	}

	public IDictionaryService getDictionaryService()
	{
		return this.dictionaryService;
	}

	@Before
	public void initData()
	{
		Resource modelResource = SpringModelUtils.getResource("classpath:model/TestModel1.msl");
		List<Resource> modelResources = SpringModelUtils.getResources("classpath*:model/*.msl");

		dictionaryImportRunner = new DictionaryImportRunner(this.baseEntityService, modelResources, modelResource);
		dictionaryImportRunner.run();
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	public void setDictionaryService(IDictionaryService dictionaryService)
	{
		this.dictionaryService = dictionaryService;
	}

	@Test
	public void testTestGetDictionary1()
	{
		IDictionaryModel dictionaryModel = this.dictionaryService.getDictionary("TestDictionary1");
		Assert.assertEquals("TestDictionary1", dictionaryModel.getName());

		Assert.assertEquals("TestDictionary1Title", dictionaryModel.getTitle());
	}

	@Test
	public void testDictionary1TextControl1Defaults()
	{
		IDictionaryModel dictionaryModel = this.dictionaryService.getDictionary("TestDictionary1");

		ITextControlModel textControlModel = DictionaryModelQuery.create(dictionaryModel).getControls()
				.getControlModelByName("TextControl1Defaults", ITextControlModel.class);

		Assert.assertEquals(IBaseControlModel.MAX_LENGTH_DEFAULT, textControlModel.getMaxLength());
		Assert.assertEquals("TextControl1Defaults", textControlModel.getColumnLabel());
		Assert.assertEquals("TextControl1Defaults", textControlModel.getEditorLabel());
		Assert.assertEquals("TextControl1Defaults", textControlModel.getFilterLabel());
	}

	@Test
	public void testDictionaryTextControl1()
	{
		IDictionaryModel dictionaryModel = this.dictionaryService.getDictionary("TestDictionary1");
		IBaseControlModel baseControlModel = dictionaryModel.getLabelControls().get(0);
		Assert.assertTrue(baseControlModel instanceof ITextControlModel);

		ITextControlModel textControlModel = (ITextControlModel) baseControlModel;

		Assert.assertEquals("textDataType1", textControlModel.getAttributePath());
		Assert.assertEquals("TextControl1", textControlModel.getFilterLabel());
		Assert.assertEquals("TextControl1", textControlModel.getColumnLabel());
		Assert.assertEquals("TextControl1", textControlModel.getEditorLabel());

		Assert.assertNotNull(textControlModel.getMaxLength());

		IBaseControlModel baseFilterControlModel = dictionaryModel.getSearchModel().getFilterModel().get(0).getCompositeModel().getControls().get(0);
		Assert.assertTrue(baseFilterControlModel instanceof ITextControlModel);
		ITextControlModel filterTextControlModel = (ITextControlModel) baseFilterControlModel;

		Assert.assertEquals("textDataType1", filterTextControlModel.getAttributePath());
		Assert.assertEquals("TextControl1Filter", filterTextControlModel.getFilterLabel());
		Assert.assertEquals("TextControl1Filter", filterTextControlModel.getColumnLabel());
		Assert.assertEquals("TextControl1Filter", filterTextControlModel.getEditorLabel());
	}

	@Test
	public void testDictionary1()
	{
		IDictionaryModel dictionaryModel = this.dictionaryService.getDictionary("TestDictionary1");

		Assert.assertEquals("Dictionary 1 Label", dictionaryModel.getLabel());
		Assert.assertEquals("Dictionary 1 Label", dictionaryModel.getPluralLabel());
	}

}
