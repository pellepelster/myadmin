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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.core.query.ClientGenericFilterBuilder;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseDictionaryEditorModule;
import de.pellepelster.myadmin.client.web.services.vo.IBaseEntityService;
import de.pellepelster.myadmin.demo.client.web.dictionaries.DemoDictionaryModel;
import de.pellepelster.myadmin.demo.client.web.entities.CityVO;
import de.pellepelster.myadmin.demo.client.web.entities.CompanyVO;
import de.pellepelster.myadmin.demo.client.web.entities.CountryVO;
import de.pellepelster.myadmin.demo.client.web.entities.EmployeeVO;
import de.pellepelster.myadmin.demo.client.web.entities.ManagerVO;
import de.pellepelster.myadmin.demo.client.web.entities.Region2CountryVO;
import de.pellepelster.myadmin.demo.client.web.entities.RegionVO;
import de.pellepelster.myadmin.demo.client.web.entities.StateVO;
import de.pellepelster.myadmin.demo.client.web.entities.UserVO;
import de.pellepelster.myadmin.demo.client.web.inheritance.TestEntity1VO;
import de.pellepelster.myadmin.demo.client.web.test1.Test1VO;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;
import de.pellepelster.myadmin.server.util.TempFileStore;

@SuppressWarnings({ "unchecked", "rawtypes" })
public final class DemoBaseEntityServiceTest extends BaseDemoTest
{

	@Autowired
	private IBaseEntityService baseEntityService;

	@Autowired
	private TempFileStore tempFileStore;

	@Before
	public void initData()
	{

		this.baseEntityService.deleteAll(CityVO.class.getName());
		this.baseEntityService.deleteAll(StateVO.class.getName());
		this.baseEntityService.deleteAll(RegionVO.class.getName());
		this.baseEntityService.deleteAll(Region2CountryVO.class.getName());
		this.baseEntityService.deleteAll(CountryVO.class.getName());

		CountryVO germany = new CountryVO();
		germany.setCountryName("Germany");
		germany = this.baseEntityService.create(germany);

		StateVO niedersachsen = new StateVO();
		niedersachsen.setStateName("Niedersachsen");
		niedersachsen.setStateCountry(germany);
		this.baseEntityService.create(niedersachsen);

		CityVO hannover = new CityVO();
		hannover.setCityName("Hannover");
		niedersachsen.getStateCities().add(hannover);

		Region2CountryVO europe2germany = new Region2CountryVO();
		europe2germany.setRegion2CountryDescription("Test");
		europe2germany.setRegion2CountryCountry(germany);
		europe2germany = this.baseEntityService.create(europe2germany);

		RegionVO europe = new RegionVO();
		europe.getRegion2Countries().add(europe2germany);
		europe = this.baseEntityService.create(europe);

	}

	public void setBaseEntityService(IBaseEntityService baseEntityService)
	{
		this.baseEntityService = baseEntityService;
	}

	@Test
	public void testInheritanceCreate()
	{
		this.baseEntityService.deleteAll(TestEntity1VO.class.getName());

		TestEntity1VO testEntity1VO = new TestEntity1VO();
		testEntity1VO.setString1("aaa");
		testEntity1VO.setString2("bbb");

		Result<TestEntity1VO> createResult = this.baseEntityService.validateAndCreate(testEntity1VO);
		Assert.assertTrue(createResult.getValidationMessages().isEmpty());

		GenericFilterVO genericFilterVO = new GenericFilterVO(TestEntity1VO.class.getName());
		List<TestEntity1VO> result = this.baseEntityService.filter(genericFilterVO);

		assertEquals(1, result.size());

		TestEntity1VO testEntity1VOResult = result.get(0);
		assertEquals("aaa", testEntity1VOResult.getString1());
		assertEquals("bbb", testEntity1VOResult.getString2());
	}

	@Test
	public void testInheritanceSave()
	{
		this.baseEntityService.deleteAll(TestEntity1VO.class.getName());

		TestEntity1VO testEntity1VO = new TestEntity1VO();
		testEntity1VO.setString1("aaa");
		testEntity1VO.setString2("bbb");

		Result<TestEntity1VO> createResult = this.baseEntityService.validateAndCreate(testEntity1VO);
		Assert.assertTrue(createResult.getValidationMessages().isEmpty());

		createResult.getVO().setString1("xxx");
		createResult.getVO().setString2("yyy");

		Result<TestEntity1VO> saveResult = this.baseEntityService.validateAndSave(createResult.getVO());
		Assert.assertTrue(saveResult.getValidationMessages().isEmpty());

		GenericFilterVO genericFilterVO = new GenericFilterVO(TestEntity1VO.class.getName());
		List<TestEntity1VO> result = this.baseEntityService.filter(genericFilterVO);

		assertEquals(1, result.size());

		TestEntity1VO testEntity1VOResult = result.get(0);
		assertEquals("xxx", testEntity1VOResult.getString1());
		assertEquals("yyy", testEntity1VOResult.getString2());
	}

	@Test
	public void testHierarchicalInheritanceCreateAndSave()
	{
		this.baseEntityService.deleteAll(ManagerVO.class.getName());
		this.baseEntityService.deleteAll(EmployeeVO.class.getName());

		ManagerVO managerVO = new ManagerVO();
		managerVO.setName("aaa");

		EmployeeVO employeeVO = new EmployeeVO();
		employeeVO.setName("uuu");

		Result<EmployeeVO> employeeCreateResult = this.baseEntityService.validateAndCreate(employeeVO);
		managerVO.setParent(employeeCreateResult.getVO());

		Result<ManagerVO> createResult = this.baseEntityService.validateAndCreate(managerVO);

		Assert.assertTrue(createResult.getValidationMessages().isEmpty());
		assertEquals("aaa", createResult.getVO().getName());

		createResult.getVO().setName("bbb");

		Result<ManagerVO> saveResult = this.baseEntityService.validateAndSave(createResult.getVO());
		Assert.assertTrue(saveResult.getValidationMessages().isEmpty());
		assertEquals("bbb", saveResult.getVO().getName());

	}

	@Test
	public void testFilterCountry()
	{
		GenericFilterVO genericFilterVO = new GenericFilterVO(CountryVO.class.getName());
		List<IBaseVO> result = this.baseEntityService.filter(genericFilterVO);

		assertEquals(1, result.size());
	}

	@Test
	public void testBooleanPositive()
	{
		this.baseEntityService.deleteAll(Test1VO.class.getName());
		Test1VO test1VO = new Test1VO();
		test1VO.setBooleanDatatype1(true);
		this.baseEntityService.create(test1VO);

		GenericFilterVO<Test1VO> filter = new GenericFilterVO(Test1VO.class);
		filter.addCriteria(Test1VO.FIELD_BOOLEANDATATYPE1, true);

		List<Test1VO> result = this.baseEntityService.filter(filter);
		assertEquals(1, result.size());
	}

	@Test
	public void testBoolean()
	{

		this.baseEntityService.deleteAll(Test1VO.class.getName());
		Test1VO test1VO = new Test1VO();
		test1VO.setBooleanDatatype1(true);
		this.baseEntityService.create(test1VO);

		GenericFilterVO<Test1VO> filterTrue = new GenericFilterVO(Test1VO.class);
		filterTrue.addCriteria(Test1VO.FIELD_BOOLEANDATATYPE1, true);

		GenericFilterVO<Test1VO> filterFalse = new GenericFilterVO(Test1VO.class);
		filterFalse.addCriteria(Test1VO.FIELD_BOOLEANDATATYPE1, false);

		List<Test1VO> result = this.baseEntityService.filter(filterTrue);
		assertEquals(1, result.size());

		result = this.baseEntityService.filter(filterFalse);
		assertEquals(0, result.size());

	}

	@Test
	public void testFilter2Associations()
	{

		GenericFilterVO genericFilterVO = new GenericFilterVO(RegionVO.class.getName());
		genericFilterVO.addAssociation(RegionVO.FIELD_REGION2COUNTRIES.getAttributeName()).addAssociation(
				Region2CountryVO.FIELD_REGION2COUNTRYCOUNTRY.getAttributeName());

		// List<String> associations =
		// DBUtil.filter2Associations(genericFilterVO);

		// assertEquals("region2Countries", associations.get(0));
		// assertEquals("region2Countries/region2CountryCountry",
		// associations.get(1));

	}

	@Test
	public void testFilterRegionWithRegion2Country()
	{

		GenericFilterVO genericFilterVO = new GenericFilterVO(RegionVO.class.getName());
		genericFilterVO.addAssociation(RegionVO.FIELD_REGION2COUNTRIES.getAttributeName());

		RegionVO result = (RegionVO) this.baseEntityService.filter(genericFilterVO).get(0);

		assertEquals(1, result.getRegion2Countries().size());
		assertNull(result.getRegion2Countries().get(0).getRegion2CountryCountry());
	}

	@Test
	public void testFilterRegionWithRegion2CountryAndCountry()
	{

		GenericFilterVO genericFilterVO = new GenericFilterVO(RegionVO.class.getName());
		genericFilterVO.addAssociation(RegionVO.FIELD_REGION2COUNTRIES.getAttributeName()).addAssociation(
				Region2CountryVO.FIELD_REGION2COUNTRYCOUNTRY.getAttributeName());

		RegionVO result = (RegionVO) this.baseEntityService.filter(genericFilterVO).get(0);

		assertEquals(1, result.getRegion2Countries().size());
		assertNotNull(result.getRegion2Countries().get(0).getRegion2CountryCountry());
	}

	@Test
	public void testFilterStateWithCountry()
	{

		GenericFilterVO genericFilterVO = new GenericFilterVO(StateVO.class.getName());
		genericFilterVO.addAssociation(StateVO.FIELD_STATECOUNTRY.getAttributeName());

		List<IBaseVO> result = this.baseEntityService.filter(genericFilterVO);

		StateVO state1 = (StateVO) result.get(0);
		assertNotNull(state1.getStateCountry());
	}

	@Test
	public void testFilterStateWithoutCountry()
	{

		GenericFilterVO genericFilterVO = new GenericFilterVO(StateVO.class.getName());
		List<IBaseVO> result = this.baseEntityService.filter(genericFilterVO);

		StateVO state1 = (StateVO) result.get(0);
		assertNotNull(state1.getStateCountry());
	}

	@Test
	public void testReadState()
	{
		GenericFilterVO genericFilterVO = new GenericFilterVO(StateVO.class.getName());

		List<IBaseVO> result = this.baseEntityService.filter(genericFilterVO);

		StateVO state1 = (StateVO) this.baseEntityService.read(result.get(0).getId(), StateVO.class.getName());

		assertNotNull(state1.getStateCountry());
	}

	@Test
	public void testEntityExtends()
	{

		UserVO userVO = new UserVO();
		userVO.setUserName("xxx");
		this.baseEntityService.create(userVO);

		List<UserVO> result = this.baseEntityService.filter(ServerGenericFilterBuilder.createGenericFilter(UserVO.class)
				.addCriteria(UserVO.FIELD_USERNAME, "xxx").getGenericFilter());

		assertEquals(1, result.size());
		assertEquals("xxx", result.get(0).getUserName());
		assertEquals("xxx", result.get(0).get(UserVO.FIELD_USERNAME.getAttributeName()));
	}

	@Test
	public void testCreateManager()
	{

		// addHierarchy(CompanyDictionaryIDs.COMPANY);
		// addHierarchy(ManagerDictionaryIDs.MANAGER,
		// CompanyDictionaryIDs.COMPANY);
		// addHierarchy(EmployeeDictionaryIDs.EMPLOYEE,
		// CompanyDictionaryIDs.COMPANY, ManagerDictionaryIDs.MANAGER);

		ManagerVO managerVO = new ManagerVO();
		managerVO.setName("xxx");
		managerVO.getData().put(BaseDictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID, DemoDictionaryModel.MANAGER.getName());

		Result<ManagerVO> result = this.baseEntityService.validateAndCreate(managerVO);

		assertEquals(1, result.getValidationMessages().size());
	}

	@Test
	public void testCreateCompanyVO()
	{

		CompanyVO companyVO = new CompanyVO();
		companyVO.setCompanyName("xxx");
		companyVO.getData().put(BaseDictionaryEditorModule.EDITORDICTIONARYNAME_PARAMETER_ID, DemoDictionaryModel.COMPANY.getName());

		Result<CompanyVO> result = this.baseEntityService.validateAndCreate(companyVO);

		assertEquals(0, result.getValidationMessages().size());
	}

	@Test
	public void testValidateAndSaveMandatoryListItemRegion()
	{
		RegionVO region = new RegionVO();
		region.setRegionName("abc");

		Region2CountryVO region2CountryVO = new Region2CountryVO();
		region.getRegion2Countries().add(region2CountryVO);

		Result<RegionVO> result = this.baseEntityService.validateAndSave(region);

		assertEquals(1, result.getValidationMessages().size());
	}

	@Test
	public void testBinaryDatatype()
	{
		Test1VO test1VO = new Test1VO();

		byte[] bin = new byte[] { 0x1, 0x2, 0x3 };

		test1VO.setBinaryDatatype1(bin);

		test1VO = this.baseEntityService.create(test1VO);

		GenericFilterVO<Test1VO> test1Filter = ClientGenericFilterBuilder.createGenericFilter(Test1VO.class).addCriteria(Test1VO.FIELD_ID, test1VO.getId())
				.getGenericFilter();

		List<Test1VO> result = this.baseEntityService.filter(test1Filter);

		Assert.assertEquals(1, result.size());
		Assert.assertArrayEquals(bin, result.get(0).getBinaryDatatype1());
	}

	@Test
	@Ignore
	public void testFile()
	{
		Test1VO test1VO = new Test1VO();

		byte[] bin1 = new byte[] { 0x1, 0x2, 0x3 };
		byte[] bin2 = new byte[] { 0x3, 0x4, 0x5 };

		String file1TempId = this.tempFileStore.storeTempFile(bin1);
		test1VO.getData().put(Test1VO.FIELD_FILEDATATYPE1.getAttributeName(), file1TempId);

		Result<Test1VO> createResult = this.baseEntityService.validateAndCreate(test1VO);

		Assert.assertNull(createResult.getVO().getFileDatatype1().getFileContent());
		Assert.assertFalse(createResult.getVO().getChangeTracker().hasChanges());
		Assert.assertFalse(createResult.getVO().getFileDatatype1().getChangeTracker().hasChanges());
		Assert.assertEquals(file1TempId, createResult.getVO().getFileDatatype1().getFileUUID());

		GenericFilterVO<Test1VO> test1Filter = ClientGenericFilterBuilder.createGenericFilter(Test1VO.class)
				.addCriteria(Test1VO.FIELD_ID, createResult.getVO().getId()).getGenericFilter();
		List<Test1VO> result = this.baseEntityService.filter(test1Filter);
		Assert.assertEquals(file1TempId, result.get(0).getFileDatatype1().getFileUUID());

		String file2TempId = this.tempFileStore.storeTempFile(bin2);
		createResult.getVO().getData().put(Test1VO.FIELD_FILEDATATYPE1.getAttributeName(), file2TempId);

		Result<Test1VO> saveResult = this.baseEntityService.validateAndSave(createResult.getVO());
		Assert.assertFalse(saveResult.getVO().getChangeTracker().hasChanges());
		Assert.assertFalse(saveResult.getVO().getFileDatatype1().getChangeTracker().hasChanges());

		test1Filter = ClientGenericFilterBuilder.createGenericFilter(Test1VO.class).addCriteria(Test1VO.FIELD_ID, saveResult.getVO().getId())
				.getGenericFilter();
		result = this.baseEntityService.filter(test1Filter);
		Assert.assertEquals(file2TempId, result.get(0).getFileDatatype1().getFileUUID());

	}

	public void setTempFileStore(TempFileStore tempFileStore)
	{
		this.tempFileStore = tempFileStore;
	}

}
