package de.pellepelster.myadmin.server.test.selenium;

public class SeleniumLogEntry {

	private String screenshot;

	private String logText;

	private String eventName;

	private String stack;

	public String getLogText() {
		return logText;
	}

	public void setLogText(String logText) {
		this.logText = logText;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String event) {
		this.eventName = event;
	}

	public String getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(String screenshot) {
		this.screenshot = screenshot;
	}

	public String getStack() {
		return stack;
	}

	public void setStack(String stack) {
		this.stack = stack;
	}

}
