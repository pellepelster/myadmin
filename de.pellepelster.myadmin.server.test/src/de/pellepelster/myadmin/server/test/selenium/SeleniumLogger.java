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
package de.pellepelster.myadmin.server.test.selenium;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;

import com.thoughtworks.xstream.XStream;

public class SeleniumLogger implements WebDriver
{

	private static final Logger LOG = Logger.getLogger(SeleniumLogger.class);

	public static final String SCREENSHOT_DIRECTORY = "screenshots";

	private static final String IMAGE_FORMAT = "png";

	private static final int MAX_STACK_SIZE = 16;

	private WebDriver driver;

	private File reportDirectory;

	private File screenshotDirectory;

	private SeleniumLog seleniumLog;

	private String testName;

	public SeleniumLogger(String reportDirectory, String testName, WebDriver driver)
	{

		super();

		this.testName = testName;

		this.seleniumLog = new SeleniumLog();

		this.reportDirectory = new File(reportDirectory, testName);
		if (!this.reportDirectory.exists() && !this.reportDirectory.mkdirs())
		{
			throw new RuntimeException(String.format("could not create report dir '%s'", reportDirectory));
		}
		LOG.info(String.format("log will be written to using '%s'", reportDirectory.toString()));

		this.screenshotDirectory = new File(this.reportDirectory, SCREENSHOT_DIRECTORY);
		if (!this.screenshotDirectory.exists() && !this.screenshotDirectory.mkdirs())
		{
			throw new RuntimeException(String.format("could not create screenshot dir '%s'", this.screenshotDirectory.getName()));
		}

		EventFiringWebDriver eventFiringWebDriver = new EventFiringWebDriver(driver);
		this.driver = eventFiringWebDriver;

		eventFiringWebDriver.register(new WebDriverEventListener()
		{

			@Override
			public void onException(Throwable throwable, WebDriver driver)
			{
				logStep("onException", throwable.getMessage());
			}

			@Override
			public void beforeScript(String script, WebDriver driver)
			{
			}

			@Override
			public void beforeNavigateTo(String url, WebDriver driver)
			{
				logStep("beforeNavigateTo", url.toString());
			}

			@Override
			public void beforeNavigateForward(WebDriver driver)
			{
				logStep("beforeNavigateForward", null);
			}

			@Override
			public void beforeNavigateBack(WebDriver driver)
			{
				logStep("beforeNavigateBack", null);
			}

			@Override
			public void beforeFindBy(By by, WebElement element, WebDriver driver)
			{
				logStep("beforeFindBy", by.toString());
			}

			@Override
			public void beforeClickOn(WebElement element, WebDriver driver)
			{
				logStep("beforeClickOn", element.toString());
			}

			@Override
			public void beforeChangeValueOf(WebElement element, WebDriver driver)
			{
				logStep("beforeChangeValueOf", element.toString());
			}

			@Override
			public void afterScript(String script, WebDriver driver)
			{
			}

			@Override
			public void afterNavigateTo(String url, WebDriver driver)
			{
				logStep("afterNavigateTo", url.toString());
			}

			@Override
			public void afterNavigateForward(WebDriver driver)
			{
				logStep("afterNavigateForward", null);
			}

			@Override
			public void afterNavigateBack(WebDriver driver)
			{
				logStep("afterNavigateBack", null);
			}

			@Override
			public void afterFindBy(By by, WebElement element, WebDriver driver)
			{
				logStep("afterFindBy", by.toString());
			}

			@Override
			public void afterClickOn(WebElement element, WebDriver driver)
			{
				logStep("afterClickOn", element.toString());
			}

			@Override
			public void afterChangeValueOf(WebElement element, WebDriver driver)
			{
				logStep("afterChangeValueOf", element.toString());
			}
		});

	}

	private static byte[] mergeImageAndText(byte[] image, String text) throws IOException
	{

		InputStream in = new ByteArrayInputStream(image);
		BufferedImage im = ImageIO.read(in);
		Graphics2D g2 = im.createGraphics();

		// init font
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Font font = new Font("SansSerif", Font.PLAIN, 12);
		g2.setFont(font);
		FontMetrics fm = g2.getFontMetrics(g2.getFont());

		// sizes
		int imageWidth = im.getWidth();
		int imageHeight = im.getHeight();

		int textWidth = (int) fm.getStringBounds(text, g2).getWidth();
		int textHeight = (int) fm.getStringBounds(text, g2).getHeight();

		int panelWidth = imageWidth;
		int panelHeight = (int) (textHeight * 1.5);
		int panelX = 0;
		int panelY = imageHeight - panelHeight;

		g2.setColor(Color.BLACK);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

		g2.fillRect(panelX, panelY, panelWidth, panelHeight);

		// text position
		int x = Math.max(0, (panelWidth - textWidth) / 2);
		int y = (panelHeight - textHeight) / 2 + fm.getAscent();

		g2.setComposite(AlphaComposite.SrcOver);
		g2.setColor(Color.WHITE);
		g2.drawString(text, panelX + x, panelY + y);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(im, IMAGE_FORMAT, baos);

		return baos.toByteArray();
	}

	private void logStep(String eventName, String logText)
	{

		String screenshotFileName = String.format("%s.%s", UUID.randomUUID().toString(), IMAGE_FORMAT);
		takeScreenShot(screenshotFileName, String.format("%s: %s", eventName, logText));

		SeleniumLogEntry seleniumLogEntry = new SeleniumLogEntry();
		seleniumLogEntry.setScreenshot(screenshotFileName);
		seleniumLogEntry.setEventName(eventName);
		seleniumLogEntry.setLogText(logText);

		boolean startingClassClassFound = false;
		int recordedStackSize = 0;

		StringBuilder stack = new StringBuilder();
		StackTraceElement lastStackTraceElement = null;

		for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace())
		{
			if (startingClassClassFound && recordedStackSize < MAX_STACK_SIZE)
			{
				stack.append(String.format("%s.%s#%d%n", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()));
				recordedStackSize++;
			}

			boolean isEventFiringWebDriver = stackTraceElement.getClassName().startsWith(EventFiringWebDriver.class.getName())
					&& (stackTraceElement.getMethodName().equals("click") || stackTraceElement.getMethodName().equals("clear") || stackTraceElement
							.getMethodName().equals("sendKeys"));

			boolean isDelegate = lastStackTraceElement != null && lastStackTraceElement.getClassName().equals(EventFiringWebDriver.class.getName())
					&& stackTraceElement.getClassName().equals(SeleniumLogger.class.getName())
					&& stackTraceElement.getMethodName().equals(lastStackTraceElement.getMethodName());

			if (!startingClassClassFound && (isEventFiringWebDriver || isDelegate))
			{
				startingClassClassFound = true;
			}

			lastStackTraceElement = stackTraceElement;
		}

		if (!startingClassClassFound)
		{
			for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace())
			{
				stack.append(String.format("%s.%s#%d%n", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber()));
				recordedStackSize++;
			}
		}

		stack.append("[...]");
		seleniumLogEntry.setStack(stack.toString());

		stack.append("[...]");
		seleniumLogEntry.setStack(stack.toString());

		this.seleniumLog.getSeleniumLogEntries().add(seleniumLogEntry);
	}

	private void takeScreenShot(String fileName, String logText)
	{

		try
		{

			byte[] screenshot = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.BYTES);
			screenshot = mergeImageAndText(screenshot, logText);

			FileUtils.writeByteArrayToFile(new File(this.screenshotDirectory, fileName), screenshot);

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void get(String url)
	{
		this.driver.get(url);
	}

	@Override
	public String getCurrentUrl()
	{
		return this.driver.getCurrentUrl();
	}

	@Override
	public String getTitle()
	{
		return this.driver.getTitle();
	}

	@Override
	public List<WebElement> findElements(By by)
	{
		return this.driver.findElements(by);
	}

	@Override
	public WebElement findElement(By by)
	{
		return this.driver.findElement(by);
	}

	@Override
	public String getPageSource()
	{
		return this.driver.getPageSource();
	}

	@Override
	public void close()
	{
		this.driver.close();
	}

	public void close(Closeable c)
	{
		if (c == null)
		{
			return;
		}

		try
		{
			c.close();
		}
		catch (IOException e)
		{
			LOG.error("error closing closable", e);
		}
	}

	@Override
	public void quit()
	{

		BufferedWriter writer = null;

		try
		{

			this.seleniumLog.setTestName(this.testName);

			File xmlReportFile = new File(this.reportDirectory, String.format("%s.xml", this.testName));
			File htmlReportFile = new File(this.reportDirectory, String.format("%s.html", this.testName));

			writer = new BufferedWriter(new FileWriter(xmlReportFile));
			writer.write(String.format("<?xml version=\"1.0\"?>%n"));
			writer.write(String.format("<?xml-stylesheet type=\"text/xsl\" href=\"seleniumlogger.xsl\"?>%n"));

			XStream xstream = new XStream();
			xstream.alias("seleniumlogentry", SeleniumLogEntry.class);
			xstream.alias("seleniumlog", SeleniumLog.class);

			xstream.toXML(this.seleniumLog, writer);

			transformReport(xmlReportFile, htmlReportFile, "/seleniumlogger.xsl");

		}
		catch (Exception e)
		{
			throw new RuntimeException("error quitting driver", e);
		}
		finally
		{
			this.driver.quit();
			close(writer);
		}

	}

	private void transformReport(File sourceReport, File targetReport, String xsltPath)
	{

		TransformerFactory factory = TransformerFactory.newInstance();

		try
		{
			Transformer transformer = factory.newTransformer(new StreamSource(getClass().getResourceAsStream(xsltPath)));
			transformer.transform(new StreamSource(sourceReport), new StreamResult(targetReport));

		}
		catch (Exception e)
		{
			throw new RuntimeException("error creating report", e);
		}
	}

	@Override
	public Set<String> getWindowHandles()
	{
		return this.driver.getWindowHandles();
	}

	@Override
	public String getWindowHandle()
	{
		return this.driver.getWindowHandle();
	}

	@Override
	public TargetLocator switchTo()
	{
		return this.driver.switchTo();
	}

	@Override
	public Navigation navigate()
	{
		return this.driver.navigate();
	}

	@Override
	public Options manage()
	{
		return this.driver.manage();
	}

}
