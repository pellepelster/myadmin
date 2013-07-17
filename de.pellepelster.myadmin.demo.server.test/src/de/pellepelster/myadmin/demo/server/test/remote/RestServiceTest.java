package de.pellepelster.myadmin.demo.server.test.remote;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;


public class RestServiceTest extends BaseRemoteTest
{

	@Override
	@Before
	public void setUp() throws Exception
	{
		super.setUp();
	}

	@Test
	public void testRestUserService()
	{
		RestTemplate restTemplate = new RestTemplate();

		Map<String, String> vars = new HashMap<String, String>();
		vars.put("username", "peter");
		vars.put("mail", "peter@example.org");

		String result = restTemplate.getForObject(getRemoteUrl() + "/remote/rest/userservice/usernameexists/{username}", String.class, vars);
		Assert.assertEquals("false", result);

		result = restTemplate.getForObject(getRemoteUrl() + "/remote/rest/userservice/registeruser/{username}/{mail}", String.class, vars);
		Assert.assertEquals("true", result);

		result = restTemplate.getForObject(getRemoteUrl() + "/remote/rest/userservice/usernameexists/{username}", String.class, vars);
		Assert.assertEquals("true", result);

	}

}
