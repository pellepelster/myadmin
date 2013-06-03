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
package de.pellepelster.myadmin.demo.server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterFactory;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.services.IBaseEntityService;
import de.pellepelster.myadmin.demo.client.web.entities.CityVO;
import de.pellepelster.myadmin.demo.client.web.entities.CountryVO;
import de.pellepelster.myadmin.demo.client.web.entities.Region2CountryVO;
import de.pellepelster.myadmin.demo.client.web.entities.RegionVO;
import de.pellepelster.myadmin.demo.client.web.entities.StateVO;
import de.pellepelster.myadmin.demo.client.web.entities.UserVO;

@SuppressWarnings({ "unchecked", "rawtypes" })
public final class DemoBaseEntityServiceTest extends BaseDemoDBTest
{

	@Autowired
	private IBaseEntityService baseEntityService;

	@Before
	public void initData()
	{
		this.baseEntityService.deleteAll(CityVO.class.getName());
		this.baseEntityService.deleteAll(StateVO.class.getName());
		this.baseEntityService.deleteAll(CountryVO.class.getName());
		this.baseEntityService.deleteAll(RegionVO.class.getName());
		this.baseEntityService.deleteAll(Region2CountryVO.class.getName());

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
	public void testCountry()
	{
		GenericFilterVO genericFilterVO = new GenericFilterVO(CountryVO.class.getName());
		List<IBaseVO> result = this.baseEntityService.filter(genericFilterVO);

		assertEquals(1, result.size());

	}

	@Test
	public void testCountry1()
	{
		GenericFilterVO<?> filter = new GenericFilterVO(CountryVO.class);
		List<CountryVO> result = (List<CountryVO>) this.baseEntityService.filter(filter);
		assertEquals(1, result.size());
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
	public void testMyAdminEntities()
	{
		GenericFilterVO<?> filter = new GenericFilterVO(DictionaryContainerVO.class);
		List<CountryVO> result = (List<CountryVO>) this.baseEntityService.filter(filter);

		assertEquals(0, result.size());
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
	public void testCreateUser()
	{
		UserVO userVO = new UserVO();
		userVO.setUserName("xxx");
		this.baseEntityService.create(userVO);

		List<UserVO> result = this.baseEntityService.filter(GenericFilterFactory.createGenericFilter(UserVO.class, UserVO.FIELD_USERNAME, "xxx"));

		assertEquals(1, result.size());
		assertEquals("xxx", result.get(0).getUserName());
		assertEquals("xxx", result.get(0).get(UserVO.FIELD_USERNAME.getAttributeName()));
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
}
