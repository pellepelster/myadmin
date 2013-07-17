package de.pellepelster.myadmin.demo.server.test.remote;

import static org.junit.Assert.fail;

public class BaseRemoteTest
{
	private String remoteUrl;

	public void setUp() throws Exception
	{
		if (System.getProperty("remote.url") == null)
		{
			fail("remote.url not set");
		}
		else
		{
			this.remoteUrl = System.getProperty("remote.url");
		}
	}

	public String getRemoteUrl()
	{
		return this.remoteUrl;
	}

}
