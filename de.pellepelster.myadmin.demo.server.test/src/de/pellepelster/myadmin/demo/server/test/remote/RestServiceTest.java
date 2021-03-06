package de.pellepelster.myadmin.demo.server.test.remote;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

public class RestServiceTest extends BaseRemoteTest {

	@Test
	public void testRestUserService() {
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
