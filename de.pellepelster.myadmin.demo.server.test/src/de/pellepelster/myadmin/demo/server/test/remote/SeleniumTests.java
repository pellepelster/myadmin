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

import static org.junit.Assert.fail;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import de.pellepelster.myadmin.demo.client.web.test1.Dictionary1DictionaryDebugIDs;

public class SeleniumTests extends BaseRemoteTest
{

	private WebDriver driver;

	private boolean acceptNextAlert = true;

	private StringBuffer verificationErrors = new StringBuffer();

	@Override
	@Before
	public void setUp() throws Exception
	{
		super.setUp();

		this.driver = new FirefoxDriver();
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testCreateDictionary1Textcontrol1() throws Exception
	{
		this.driver.get(getRemoteUrl() + "/Demo/Demo.html");

		waitForDivWithText("Test");

		openNavigationItem("Test");

		createDictionary1("aaa");
		createDictionary1("bbb");
		createDictionary1("ccc");

		clickById(Dictionary1DictionaryDebugIDs.DICTIONARY_SEARCH_MODULE_SEARCH_BUTTON);

		assertSearchResults(3);

		if (System.getProperty("screenshot.dir") != null)
		{
			File screenshotFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile, new File(System.getProperty("screenshot.dir"), "createCountries.png"));
		}

	}

	private void createDictionary1(Object value) throws Exception
	{
		clickNavigationItem("Dictionary1");

		waitForElementAndClickById(Dictionary1DictionaryDebugIDs.DICTIONARY_SEARCH_MODULE_CREATE_BUTTON);

		waitForElementById(Dictionary1DictionaryDebugIDs.DICTIONARY1_DICTIONARY1EDITOR_DICTIONARY1COMPOSITE3_TEXTCONTROL1);

		enterValueById(Dictionary1DictionaryDebugIDs.DICTIONARY1_DICTIONARY1EDITOR_DICTIONARY1COMPOSITE3_TEXTCONTROL1, value);

		clickById(Dictionary1DictionaryDebugIDs.DICTIONARY_EDITOR_MODULE_SAVE_BUTTON);

		waitForElementByXpath(String.format("//div[text() = \"Dictionary1Editor %s\"]", value.toString()));

		clickById(Dictionary1DictionaryDebugIDs.DICTIONARY_EDITOR_MODULE_BACK_BUTTON);

		waitForElementById(Dictionary1DictionaryDebugIDs.DICTIONARY_SEARCH_MODULE_SEARCH_BUTTON);
	}

	@After
	public void tearDown() throws Exception
	{
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString))
		{
			fail(verificationErrorString);
		}
	}

	private void waitForElementAndClickById(String id)
	{
		waitForElementById(id);
		this.driver.findElement(By.id(id)).click();
	}

	private void clickById(String id)
	{
		this.driver.findElement(By.id(id)).click();
	}

	private void waitForDivWithText(String text)
	{
		waitForElementByXpath(String.format("//div[text() = \"%s\"]", text));
	}

	private void openNavigationItem(String navigtionItemName)
	{
		this.driver.findElement(By.xpath(String.format("//div[text() = \"%s\"]/preceding::div[1]/img", navigtionItemName))).click();
	}

	private void enterValueById(String id, Object value)
	{
		this.driver.findElement(By.id(id)).clear();
		this.driver.findElement(By.id(id)).sendKeys(value.toString());

	}

	private void clickNavigationItem(String navigtionItemName)
	{
		this.driver.findElement(By.xpath(String.format("//div[text() = \"%s\"]", navigtionItemName))).click();
	}

	private void waitForElementById(String id)
	{
		waitForElement(By.id(id));
	}

	private void waitForElementByXpath(String xpath)
	{
		waitForElement(By.xpath(xpath));
	}

	private void assertElementNotPresentByXpath(String xPath)
	{
		assertElementNotPresent(By.xpath(xPath));
	}

	private void assertElementNotPresent(By by)
	{
		if (isElementPresent(by))
		{
			throw new RuntimeException(String.format("found unexpected element '%s'", by.toString()));
		}
	}

	private void waitForElement(By by)
	{
		waitForElement(by, 10);
	}

	private void waitForElement(By by, int seconds)
	{
		try
		{
			for (int second = 0;; second++)
			{
				if (second >= seconds)
				{
					fail(String.format("timeout waiting for element '%s'", by.toString()));

				}

				if (isElementPresent(by))
				{
					return;
				}

				Thread.sleep(1000);
			}
		}
		catch (Exception e)
		{
			fail(String.format("error waiting for element '%s'", by.toString()));
		}
	}

	private void assertSearchResults(int number)
	{
		waitForElementByXpath(String.format("//tr[@__gwt_row='%d']", number - 1));
		assertElementNotPresentByXpath(String.format("//tr[@__gwt_row='%d']", number));
	}

	private boolean isElementPresent(By by)
	{
		try
		{
			this.driver.findElement(by);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	private String closeAlertAndGetItsText()
	{
		try
		{
			Alert alert = this.driver.switchTo().alert();
			if (this.acceptNextAlert)
			{
				alert.accept();
			}
			else
			{
				alert.dismiss();
			}
			return alert.getText();
		}
		finally
		{
			this.acceptNextAlert = true;
		}
	}

}
