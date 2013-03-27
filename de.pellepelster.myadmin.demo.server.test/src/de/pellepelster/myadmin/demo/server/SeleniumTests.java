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

import static org.junit.Assert.fail;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import junit.framework.JUnit4TestAdapter;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class SeleniumTests
{

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception
	{
		driver = new FirefoxDriver();
		
		if (System.getProperty("selenium.base.url") == null)
		{
			fail("selenium.base.url not set");
		}
		else
		{
			baseUrl = System.getProperty("selenium.base.url");
		}

		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	private void createCountry(String countryName) throws Exception
	{
		// open country search
		driver.findElement(By.xpath("//div[text() = \"Country\"]")).click();

		waitForElementById("gwt-debug-DictionarySearch-Country-DictionaryCreate");

		// open country Editor
		driver.findElement(By.id("gwt-debug-DictionarySearch-Country-DictionaryCreate")).click();
		waitForElementById("gwt-debug-Country-null-Composite2-Composite3-CountryName");

		// enter country name
		driver.findElement(By.id("gwt-debug-Country-null-Composite2-Composite3-CountryName")).clear();
		driver.findElement(By.id("gwt-debug-Country-null-Composite2-Composite3-CountryName")).sendKeys(countryName);
		
		// save
		driver.findElement(By.id("gwt-debug-DictionarySearch-Country-DictionarySave")).click();
		waitForElementByXpath("//div[text() = \"CountryEditor "+ countryName + "\"]");
		

		// back to search
		driver.findElement(By.id("gwt-debug-DictionarySearch-Country-DictionaryBack")).click();
		waitForElementById("gwt-debug-DictionarySearch-Country-DictionarySearch");

		// execute search
		driver.findElement(By.id("gwt-debug-DictionarySearch-Country-DictionarySearch")).click();
		waitForElementByXpath("//div[text() = \"" + countryName + "\"]");
		
	}

	
	@Test
	public void testCreateCountry() throws Exception
	{
		driver.get(baseUrl + "/de.pellepelster.myadmin.demo/Demo/Demo.html");
		
		waitForElementByXpath("//div[text() = \"Masterdata\"]");

		driver.findElement(By.xpath("//div[text() = \"Masterdata\"]/preceding::div[1]/img")).click();
		driver.findElement(By.xpath("//div[text() = \"Address\"]/preceding::div[1]/img")).click();

		for (int i = 1; i <= 5; i++)
		{
			createCountry(String.format("Germany %d", i));
		}
		
		if (System.getProperty("screenshot.dir") != null)
		{
			File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screenshotFile, new File(System.getProperty("screenshot.dir"), "createCountries.png"));
		}


	}

	@After
	public void tearDown() throws Exception
	{
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString))
		{
			fail(verificationErrorString);
		}
	}

	private void  waitForElementById(String id)
	{
		waitForElement(By.id(id));
	}

	private void  waitForElementByXpath(String xpath)
	{
		waitForElement(By.xpath(xpath));
	}

	private void  waitForElement(By by)
	{
		try
		{
			for (int second = 0;; second++)
			{
				if (second >= 10)
				{
					fail(String.format("timeout waiting for '%s'", by.toString()));
					
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
			fail(String.format("error waiting element '%s'", e.getMessage()));
		}
	}


	private boolean isElementPresent(By by)
	{
		try
		{
			driver.findElement(by);
			return true;
		}
		catch (NoSuchElementException e)
		{
			return false;
		}
	}

	private String closeAlertAndGetItsText()
	{
		try
		{
			Alert alert = driver.switchTo().alert();
			if (acceptNextAlert)
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
			acceptNextAlert = true;
		}
	}

}
