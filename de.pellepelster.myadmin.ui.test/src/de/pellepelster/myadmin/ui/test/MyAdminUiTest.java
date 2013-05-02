package de.pellepelster.myadmin.ui.test;

import junit.framework.Assert;

import org.junit.Test;

import de.pellepelster.myadmin.ui.util.MyAdminProjectUtil;

public class MyAdminUiTest
{

	@Test
	public void testFqdnprojectName()
	{
		String regEx = MyAdminProjectUtil.FQDN_PROJECT_NAME_REGEX;

		Assert.assertTrue("a.b.c.d.Efg".matches(regEx));
		Assert.assertTrue("a.E1".matches(regEx));
		Assert.assertTrue("dsads.dede.Dede".matches(regEx));
		Assert.assertFalse("a.a.e".matches(regEx));
		Assert.assertFalse("a.a".matches(regEx));
		Assert.assertFalse("aaa".matches(regEx));
		Assert.assertFalse("AAA".matches(regEx));

	}

}
