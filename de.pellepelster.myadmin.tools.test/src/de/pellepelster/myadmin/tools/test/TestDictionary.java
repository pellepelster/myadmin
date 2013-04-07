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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.client.web.services.IDictionaryService;
import de.pellepelster.myadmin.server.test.AbstractMyAdminTest;
import de.pellepelster.myadmin.tools.dictionary.DictionaryImportRunner;

public class TestDictionary extends AbstractMyAdminTest
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

		PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
		Resource modelResource = null;
		List<Resource> modelResources = new ArrayList<Resource>();

		try
		{
			for (Resource resource : pathMatchingResourcePatternResolver.getResources("classpath*:model/*.msl"))
			{
				modelResources.add(resource);
			}

			for (Resource resource : pathMatchingResourcePatternResolver.getResources("classpath:model/TestModel1.msl"))
			{
				modelResource = resource;
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

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
	public void testTestGetDictionary1TexControl1()
	{
		IDictionaryModel dictionaryModel = this.dictionaryService.getDictionary("TestDictionary1");

		IBaseControlModel baseControlModel = dictionaryModel.getLabelControls().get(0);
		Assert.assertTrue(baseControlModel instanceof ITextControlModel);

		ITextControlModel textControlModel = (ITextControlModel) baseControlModel;

		Assert.assertEquals("textDataType1", textControlModel.getAttributePath());
		Assert.assertEquals("TextControl1", textControlModel.getFilterLabel());
		Assert.assertEquals("TextControl1", textControlModel.getColumnLabel());
		Assert.assertEquals("TextControl1", textControlModel.getEditorLabel());
	}
}