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

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.io.Files;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterFactory;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryEditorVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.server.services.ImportExportService;
import de.pellepelster.myadmin.server.services.MetaDataService;
import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;
import de.pellepelster.myadmin.tools.dictionary.EntityExportRunner;
import de.pellepelster.myadmin.tools.dictionary.EntityImportRunner;

public class TestEntityExportImportRunner extends BaseMyAdminJndiContextTest
{

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private ImportExportService importExportService;

	@Autowired
	private MetaDataService metaDataService;

	@Test
	public void testFullExport()
	{
		File tempDir = Files.createTempDir();

		createExportData();

		EntityExportRunner entityExportRunner = new EntityExportRunner(this.importExportService, this.metaDataService, tempDir);
		entityExportRunner.run();

		deleteData();

		EntityImportRunner entityImportRunner = new EntityImportRunner(this.importExportService, this.metaDataService, tempDir);
		entityImportRunner.run();

		List<DictionaryVO> dictionaryVOs = this.baseEntityService.filter(GenericFilterFactory.createGenericFilter(DictionaryVO.class).getGenericFilter());
		Assert.assertEquals(1, dictionaryVOs.size());

		DictionaryVO dictionaryVO = dictionaryVOs.get(0);
		Assert.assertEquals("Dictionary1", dictionaryVO.getName());
		Assert.assertEquals("Editor1", dictionaryVO.getEditor().getName());
	}

	private void deleteData()
	{
		this.baseEntityService.deleteAll(DictionaryVO.class.getName());
		this.baseEntityService.deleteAll(DictionaryEditorVO.class.getName());
	}

	private void createExportData()
	{
		DictionaryEditorVO dictionaryEditorVO = new DictionaryEditorVO();
		dictionaryEditorVO.setName("Editor1");
		dictionaryEditorVO = this.baseEntityService.create(dictionaryEditorVO);

		DictionaryVO dictionaryVO = new DictionaryVO();
		dictionaryVO.setName("Dictionary1");
		dictionaryVO.setEditor(dictionaryEditorVO);
		this.baseEntityService.create(dictionaryVO);
	}

	public void setImportExportService(ImportExportService importExportService)
	{
		this.importExportService = importExportService;
	}

	public void setMetaDataService(MetaDataService metaDataService)
	{
		this.metaDataService = metaDataService;
	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

}
