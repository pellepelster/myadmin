package de.pellepelster.myadmin.demo.server.test.remote;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import de.pellepelster.myadmin.client.web.MyAdminRemoteServiceLocator;
import de.pellepelster.myadmin.demo.server.test.remote.old.ApplicationContextProvider;

public class BaseRemoteTest {

	private String remoteUrl;

	private String remoteServer;

	private String remotePort;

	private String remotePath;

	@Before
	public void setUp() {

		if (System.getProperty("remote.server") == null) {
			fail(String.format("property remote.server needed"));
		}

		if (System.getProperty("remote.port") == null) {
			fail(String.format("property remote.port needed"));
		}

		if (System.getProperty("remote.path") == null) {
			fail(String.format("property remote.path needed"));
		}

		remotePath = System.getProperty("remote.path");
		remotePort = System.getProperty("remote.port");
		remoteServer = System.getProperty("remote.server");
		remoteUrl = String.format("http://%s:%s/%s", remoteServer, remotePort, remotePath);

		SecurityContextImpl sc = new SecurityContextImpl();
		Authentication auth = new UsernamePasswordAuthenticationToken("system", "system");
		sc.setAuthentication(auth);
		SecurityContextHolder.setContext(sc);

		ApplicationContextProvider.getInstance().init(new String[] { "MyAdminDemoTestApplicationContext.xml", "MyAdminClientServices-gen.xml" });
		MyAdminRemoteServiceLocator.getInstance().init(ApplicationContextProvider.getInstance());

	}

	public String getRemoteUrl() {
		return this.remoteUrl;
	}

}
