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

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import de.pellepelster.myadmin.server.test.selenium.SeleniumLogger;

public class BaseSeleniumTests extends BaseRemoteTest
{

	private WebDriver driver;

	private boolean acceptNextAlert = true;

	private StringBuffer verificationErrors = new StringBuffer();

	public WebDriver getDriver()
	{
		return this.driver;
	}

	public String getTestName()
	{
		return getClass().getSimpleName();
	}

	@Before
	@Override
	public void setUp()
	{
		super.setUp();

		if (System.getProperty("selenium.report.dir") == null)
		{
			fail(String.format("property selenium.report.dir needed"));
		}

		this.driver = new SeleniumLogger(System.getProperty("selenium.report.dir"), getClass().getName(), new FirefoxDriver());

		this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		this.driver.manage().window().setSize(new Dimension(1023, 768));

	}

	@After
	public void tearDown() throws Exception
	{
		if (this.driver != null)
		{
			this.driver.quit();

		}
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString))
		{
			fail(verificationErrorString);
		}
	}

	public void waitForElementAndClickById(String id)
	{
		waitForElementById(id);
		this.driver.findElement(By.id(id)).click();
	}

	public void clickById(String id)
	{
		this.driver.findElement(By.id(id)).click();
	}

	public void waitForDivWithText(String text)
	{
		waitForElementByXpath(String.format("//div[text() = \"%s\"]", text));
	}

	public void openNavigationItem(String navigtionItemName)
	{
		this.driver.findElement(By.xpath(String.format("//div[text() = \"%s\"]/preceding::div[1]/img", navigtionItemName))).click();
	}

	public void enterValueById(String id, Object value)
	{
		this.driver.findElement(By.id(id)).clear();
		this.driver.findElement(By.id(id)).sendKeys(value.toString());
	}

	public void clickNavigationItem(String navigtionItemName)
	{
		this.driver.findElement(By.xpath(String.format("//div[text() = \"%s\"]", navigtionItemName))).click();
	}

	public void waitForElementById(String id)
	{
		waitForElement(By.id(id));
	}

	public void waitForElementByXpath(String xpath)
	{
		waitForElement(By.xpath(xpath));
	}

	public void assertElementNotPresentByXpath(String xPath)
	{
		assertElementNotPresent(By.xpath(xPath));
	}

	public void assertElementNotPresent(By by)
	{
		if (isElementPresent(by))
		{
			throw new RuntimeException(String.format("found unexpected element '%s'", by.toString()));
		}
	}

	public void waitForElement(By by)
	{
		waitForElement(by, 10);
	}

	public void waitForElement(By by, int seconds)
	{
		try
		{
			for (int second = 0;; second++)
			{
				if (second >= seconds)
				{

					String errorMessage = String.format("timeout waiting for element '%s'", by.toString());
					fail(errorMessage);
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
			String errorMessage = String.format("error waiting for element '%s'", by.toString());
			fail(errorMessage);
		}
	}

	public void assertSearchResults(int number)
	{
		waitForElementByXpath(String.format("//tr[@__gwt_row='%d']", number - 1));
		assertElementNotPresentByXpath(String.format("//tr[@__gwt_row='%d']", number));
	}

	public boolean isElementPresent(By by)
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

	public String closeAlertAndGetItsText()
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
