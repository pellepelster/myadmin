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
package de.pellepelster.myadmin.server.test;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import de.pellepelster.myadmin.db.IBaseVODAO;
import de.pellepelster.myadmin.mobile.web.entities.dictionary.DictionaryMobileVO;
import de.pellepelster.myadmin.mobile.web.services.BaseEntityServiceCreateMobileParameterWrapper;
import de.pellepelster.myadmin.server.test.restvos.ObjectA;
import de.pellepelster.myadmin.server.test.restvos.ObjectB;
import de.pellepelster.myadmin.server.test.restvos.ObjectC;

public class RestUtilTest extends AbstractMyAdminTest
{

	@Test
	public void baseJacksonTest()
	{
		try
		{

			ObjectMapper objectMapper = new ObjectMapper();

			ObjectA objectA = new ObjectA();
			objectA.setString1("aaa");

			ObjectB objectB = new ObjectB();
			objectB.setString2("bbb");
			objectA.getList1().add(objectB);

			ObjectC objectC = new ObjectC();
			objectC.setString3("ccc");
			objectB.getMap1().put("key1", objectC);

			String json = objectMapper.writeValueAsString(objectA);

			ObjectA result = objectMapper.readValue(json.getBytes(), ObjectA.class);

			Assert.assertEquals("aaa", result.getString1());
			Assert.assertEquals("bbb", result.getList1().get(0).getString2());
			Assert.assertEquals("ccc", result.getList1().get(0).getMap1().get("key1").getString3());

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public IBaseVODAO getBaseVODAO()
	{
		return baseVODAO;
	}

	@Override
	public void setBaseVODAO(IBaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

	@Test
	public void testInvokeServiceMethod()
	{

		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enableDefaultTyping();

			DictionaryMobileVO dictionaryMobileVO = new DictionaryMobileVO();
			dictionaryMobileVO.setEntityName("xxx");

			BaseEntityServiceCreateMobileParameterWrapper c = new BaseEntityServiceCreateMobileParameterWrapper();
			c.vo = dictionaryMobileVO;

			String jsonParameters = objectMapper.writeValueAsString(c);

			// String result = RestUtil.invokeServiceMethod(baseVODAO, "create",
			// jsonParameters,
			// BaseEntityServiceCreateMobileParameterWrapper.class);
			// Assert.assertNotNull(result);

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void testRestTemplate()
	{
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/de.pellepelster.myadmin.demo/remote/rest/BaseEntityService/create";

		try
		{

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enableDefaultTyping();

			DictionaryMobileVO dictionaryMobileVO = new DictionaryMobileVO();
			dictionaryMobileVO.setEntityName("xxx");

			BaseEntityServiceCreateMobileParameterWrapper c = new BaseEntityServiceCreateMobileParameterWrapper();
			c.vo = dictionaryMobileVO;

			String jsonParameters = objectMapper.writeValueAsString(c);

			String result = restTemplate.postForObject(url, jsonParameters, String.class);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}
