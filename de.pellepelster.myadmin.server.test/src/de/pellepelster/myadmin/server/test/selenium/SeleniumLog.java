package de.pellepelster.myadmin.server.test.selenium;

import java.util.ArrayList;
import java.util.List;

public class SeleniumLog {

	private String testName;

	private List<SeleniumLogEntry> seleniumLogEntries = new ArrayList<SeleniumLogEntry>();

	public List<SeleniumLogEntry> getSeleniumLogEntries() {
		return seleniumLogEntries;
	}

	public void setSeleniumLogEntries(List<SeleniumLogEntry> seleniumLogEntries) {
		this.seleniumLogEntries = seleniumLogEntries;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

}
