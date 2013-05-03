package de.pellepelster.myadmin.ui.test;

import junit.framework.Assert;

import org.junit.Test;

import de.pellepelster.myadmin.ui.util.MyAdminProjectUtil;

public class MyAdminUiTest
{

	@Test
	public void testFqdnprojectName()
	{
		Assert.assertTrue(MyAdminProjectUtil.isValidFQDNProjectName("a.b.c.d"));
		Assert.assertTrue(MyAdminProjectUtil.isValidFQDNProjectName("a.b.c.D"));
		Assert.assertTrue(MyAdminProjectUtil.isValidFQDNProjectName("a.b"));
		Assert.assertTrue(MyAdminProjectUtil.isValidFQDNProjectName("a.b.c1"));
		Assert.assertFalse(MyAdminProjectUtil.isValidFQDNProjectName("a"));
		Assert.assertFalse(MyAdminProjectUtil.isValidFQDNProjectName(".a.b."));
		Assert.assertFalse(MyAdminProjectUtil.isValidFQDNProjectName("a.b."));
		Assert.assertFalse(MyAdminProjectUtil.isValidFQDNProjectName("a.b.1"));
		Assert.assertFalse(MyAdminProjectUtil.isValidFQDNProjectName(".a.b."));
	}

}
