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

import java.util.Arrays;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.pellepelster.myadmin.client.web.entities.dictionary.ClientVO;
import de.pellepelster.myadmin.client.web.services.IUserService;
import de.pellepelster.myadmin.mobile.web.entities.dictionary.DictionaryMobileVO;
import de.pellepelster.myadmin.mobile.web.services.BaseEntityServiceCreateMobileParameterWrapper;
import de.pellepelster.myadmin.server.core.services.RestUtil;
import de.pellepelster.myadmin.server.test.base.BaseMyAdminJndiContextTest;
import de.pellepelster.myadmin.server.test.restvos.ObjectA;
import de.pellepelster.myadmin.server.test.restvos.ObjectB;
import de.pellepelster.myadmin.server.test.restvos.ObjectC;

public class RestUtilTest extends BaseMyAdminJndiContextTest {
	@Autowired
	protected IUserService userService;

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Test
	public void baseJacksonTest() {
		try {
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

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testInvokeServiceMethodSimple() {
		Assert.assertEquals("false", RestUtil.invokeServiceMethod(this.userService, "userNameExists", new Object[] { "peter" }));
	}

	@Test
	public void testSimpleTypeToJson() {
		Assert.assertEquals("false", RestUtil.simpleTypeToJson(false));
		Assert.assertEquals("true", RestUtil.simpleTypeToJson(true));
		Assert.assertEquals("1", RestUtil.simpleTypeToJson(1));
		Assert.assertEquals("1.2", RestUtil.simpleTypeToJson(1.2));
		Assert.assertEquals("[\"a\", \"b\"]", RestUtil.simpleTypeToJson(new String[] { "a", "b" }));
		Assert.assertEquals("[\"d\", \"e\"]", RestUtil.simpleTypeToJson(Arrays.asList(new String[] { "d", "e" })));
	}

	public void testSimpleVOToJson() {
		SysteminformationSimpleVO s = new SysteminformationSimpleVO();
		s.setHostName("abc");
		s.setIpAddress("def");

		Assert.assertEquals("{\"hostName\": \"abc\", \"ipAddress\": \"def\"}", RestUtil.simpleVOToJson(s));
	}

	@Test
	public void testIBaseVOToJson() {
		ClientVO clientVO = new ClientVO();
		clientVO.setName("ccc");
		clientVO.setId(1);

		Assert.assertEquals("{\"id\": 1, \"name\": \"ccc\", \"oid\": 1}", RestUtil.simpleVOToJson(clientVO));
	}

	@Test
	public void testInvokeServiceMethod() {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enableDefaultTyping();

			DictionaryMobileVO dictionaryMobileVO = new DictionaryMobileVO();
			dictionaryMobileVO.setEntityName("xxx");

			BaseEntityServiceCreateMobileParameterWrapper c = new BaseEntityServiceCreateMobileParameterWrapper();
			c.vo = dictionaryMobileVO;

			// String jsonParameters = objectMapper.writeValueAsString(c);
			// String result = RestUtil.invokeServiceMethod(baseVODAO, "create",
			// jsonParameters,
			// BaseEntityServiceCreateMobileParameterWrapper.class);
			// Assert.assertNotNull(result);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
