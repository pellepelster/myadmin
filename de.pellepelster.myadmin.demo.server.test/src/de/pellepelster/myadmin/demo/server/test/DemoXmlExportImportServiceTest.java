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
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.io.Files;

import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.demo.client.base.test1.Enumeration1;
import de.pellepelster.myadmin.demo.client.web.test1.Test1VO;
import de.pellepelster.myadmin.demo.client.web.test1.Test2VO;
import de.pellepelster.myadmin.demo.server.test.dictionary.BaseDemoDictionaryTest;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;
import de.pellepelster.myadmin.server.services.vo.VOMetaDataService;
import de.pellepelster.myadmin.server.services.xml.XmlVOExportImportService;
import de.pellepelster.myadmin.tools.dictionary.EntityExportRunner;
import de.pellepelster.myadmin.tools.dictionary.EntityImportRunner;

public class DemoXmlExportImportServiceTest extends BaseDemoDictionaryTest
{

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private XmlVOExportImportService exportImportService;

	@Autowired
	private VOMetaDataService metaDataService;

	@Test
	public void exportImportTest1VO() throws ParseException
	{

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		Date dateDatatype1 = dateFormat.parse("2013.11.19");

		BigDecimal bigDecimalDatatype1 = new BigDecimal(123.456);
		Boolean booleanDatatype1 = new Boolean(false);
		Enumeration1 enumerationDatatype1 = Enumeration1.ENUM1;
		Integer integerDatatype1 = new Integer(123);
		String textDatatype1 = "xyz";
		byte[] binaryDatatype1 = new byte[] { 0xa, 0xb, 0xc, 0xd };
		Test2VO referenceDatatype1 = new Test2VO();
		referenceDatatype1.setTextDatatype2("xyz");
		referenceDatatype1 = this.baseEntityService.create(referenceDatatype1);

		Test1VO test1VO = new Test1VO();
		test1VO.setBigDecimalDatatype1(bigDecimalDatatype1);
		test1VO.setBinaryDatatype1(binaryDatatype1);
		test1VO.setBooleanDatatype1(booleanDatatype1);
		test1VO.setDateDatatype1(dateDatatype1);
		test1VO.setEnumerationDatatype1(enumerationDatatype1);
		test1VO.setIntegerDatatype1(integerDatatype1);
		// test1VO.setFileDatatype1(fileDatatype1);

		test1VO.setReferenceDatatype1(referenceDatatype1);
		test1VO.setTextDatatype1(textDatatype1);

		test1VO = this.baseEntityService.create(test1VO);

		File tempDir = Files.createTempDir();

		EntityExportRunner entityExportRunner = new EntityExportRunner(this.exportImportService, this.metaDataService, tempDir);
		entityExportRunner.run();

		this.baseEntityService.deleteAll(Test1VO.class.getName());

		EntityImportRunner entityImportRunner = new EntityImportRunner(this.exportImportService, tempDir);
		entityImportRunner.run();

		List<Test1VO> test1VOs = this.baseEntityService.filter(ServerGenericFilterBuilder.createGenericFilter(Test1VO.class).getGenericFilter());
		Assert.assertEquals(1, test1VOs.size());
		test1VO = test1VOs.get(0);
		Assert.assertEquals(bigDecimalDatatype1, test1VO.getBigDecimalDatatype1());
		Assert.assertEquals(booleanDatatype1, test1VO.getBooleanDatatype1());
		Assert.assertEquals(dateDatatype1, test1VO.getDateDatatype1());
		Assert.assertEquals(enumerationDatatype1, test1VO.getEnumerationDatatype1());
		Assert.assertEquals(textDatatype1, test1VO.getTextDatatype1());
		Assert.assertArrayEquals(binaryDatatype1, test1VO.getBinaryDatatype1());
		Assert.assertEquals(referenceDatatype1.getTextDatatype2(), test1VO.getReferenceDatatype1().getTextDatatype2());
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
