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
package de.pellepelster.myadmin.demo.server.test.remote;

import org.junit.Test;

import de.pellepelster.myadmin.client.web.MyAdminRemoteServiceLocator;
import de.pellepelster.myadmin.demo.client.web.test1.Dictionary1DictionaryDebugIDs;
import de.pellepelster.myadmin.demo.client.web.test1.Test1VO;

public class Dictionary1Test extends BaseSeleniumTests {

	@Test
	public void testCreateDictionary1Textcontrol1() throws Exception {

		MyAdminRemoteServiceLocator.getInstance().getBaseEntityService().deleteAll(Test1VO.class.getName());

		getDriver().get(getRemoteUrl() + "/Demo/Demo.html");

		waitForDivWithText("Test");

		openNavigationItem("Test");

		createDictionary1("aaa");
		createDictionary1("bbb");
		createDictionary1("ccc");

		clickById(Dictionary1DictionaryDebugIDs.DICTIONARY_SEARCH_MODULE_SEARCH_BUTTON);

		assertSearchResults(3);

	}

	private void createDictionary1(Object value) throws Exception {
		clickNavigationItem("Dictionary1");

		waitForElementAndClickById(Dictionary1DictionaryDebugIDs.DICTIONARY_SEARCH_MODULE_CREATE_BUTTON);
		waitForElementById(Dictionary1DictionaryDebugIDs.DICTIONARY1_DICTIONARY1EDITOR_DICTIONARY1COMPOSITE3_TEXTCONTROL1);

		enterValueById(Dictionary1DictionaryDebugIDs.DICTIONARY1_DICTIONARY1EDITOR_DICTIONARY1COMPOSITE3_TEXTCONTROL1, value);

		clickById(Dictionary1DictionaryDebugIDs.DICTIONARY_EDITOR_MODULE_SAVE_BUTTON);
		waitForElementByXpath(String.format("//div[text() = \"Dictionary1Editor %s\"]", value.toString()));

		clickById(Dictionary1DictionaryDebugIDs.DICTIONARY_EDITOR_MODULE_BACK_BUTTON);
		waitForElementById(Dictionary1DictionaryDebugIDs.DICTIONARY_SEARCH_MODULE_SEARCH_BUTTON);
	}

}
