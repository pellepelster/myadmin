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
package de.pellepelster.myadmin.demo.server.test;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.io.Files;

import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.demo.client.web.entities.CityVO;
import de.pellepelster.myadmin.demo.client.web.entities.CountryVO;
import de.pellepelster.myadmin.demo.server.test.dictionary.BaseDemoDictionaryTest;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;
import de.pellepelster.myadmin.server.services.vo.VOMetaDataService;
import de.pellepelster.myadmin.server.services.xml.XmlVOExportImportService;
import de.pellepelster.myadmin.tools.dictionary.EntityExportRunner;
import de.pellepelster.myadmin.tools.dictionary.EntityImportRunner;

public class TestEntityExportImportRunner extends BaseDemoDictionaryTest
{

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private XmlVOExportImportService exportImportService;

	@Autowired
	private VOMetaDataService metaDataService;

	@Test
	public void testFullExport()
	{
		File tempDir = Files.createTempDir();

		createExportData();

		EntityExportRunner entityExportRunner = new EntityExportRunner(this.exportImportService, this.metaDataService, tempDir);
		entityExportRunner.run();

		deleteData();

		EntityImportRunner entityImportRunner = new EntityImportRunner(this.exportImportService, tempDir);
		entityImportRunner.run();

		List<CityVO> cityVOs = this.baseEntityService.filter(ServerGenericFilterBuilder.createGenericFilter(CityVO.class).addAssociation(CityVO.FIELD_COUNTRY)
				.getGenericFilter());
		Assert.assertEquals(1, cityVOs.size());

		CityVO cityVO = cityVOs.get(0);
		Assert.assertEquals("Hamburg", cityVO.getCityName());
		Assert.assertEquals("Germany", cityVO.getCountry().getCountryName());
	}

	private void deleteData()
	{
		this.baseEntityService.deleteAll(CityVO.class.getName());
		this.baseEntityService.deleteAll(CountryVO.class.getName());
	}

	private void createExportData()
	{
		CountryVO countryVO = new CountryVO();
		countryVO.setCountryName("Germany");
		countryVO = this.baseEntityService.create(countryVO);

		CityVO cityVO = new CityVO();
		cityVO.setCityName("Hamburg");
		cityVO.setCountry(countryVO);

		this.baseEntityService.create(cityVO);
	}

	public void setExportImportService(XmlVOExportImportService exportImportService)
	{
		this.exportImportService = exportImportService;
	}

	public void setMetaDataService(VOMetaDataService metaDataService)
	{
		this.metaDataService = metaDataService;
	}

	@Override
	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

}
