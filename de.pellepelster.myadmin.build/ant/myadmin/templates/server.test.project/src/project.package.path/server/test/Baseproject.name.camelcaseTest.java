package @project.package@.server.test;

import org.junit.BeforeClass;
import org.springframework.test.context.ContextConfiguration;

import de.pellepelster.myadmin.server.test.base.BaseJndiContextTest;

@ContextConfiguration(locations = { "classpath:/@project.name.camelcase@ServerApplicationContext-gen.xml", "classpath:/@project.name.camelcase@ServerApplicationContextServices-gen.xml",
		"classpath:/@project.name.camelcase@DB-gen.xml" })
public abstract class Base@project.name.camelcase@Test extends BaseJndiContextTest
{

	@BeforeClass
	public static void initJndi() throws Exception
	{
		initJndi("@project.name.camelcase@");
	}

}
