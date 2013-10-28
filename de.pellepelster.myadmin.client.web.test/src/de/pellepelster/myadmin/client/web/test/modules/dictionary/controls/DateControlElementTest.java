package de.pellepelster.myadmin.client.web.test.modules.dictionary.controls;

import java.util.Date;

import junit.framework.Assert;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IDateControl;

public class DateControlElementTest extends BaseControlElementTest<IDateControl, Date>
{

	public DateControlElementTest(IDateControl baseControl)
	{
		super(baseControl);
	}

	public void assertValueWithoutMillies(Date expectedValue)
	{
		Assert.assertEquals(expectedValue.getTime() / 1000, getValue().getTime() / 1000);
	}

}
