package de.pellepelster.myadmin.client.web.test.modules.dictionary.controls;


import junit.framework.Assert;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.ITextControl;

public class TextControlTest extends BaseControlTest<ITextControl, String>
{

	public TextControlTest(ITextControl baseControl)
	{
		super(baseControl);
	}

	public void assertMandatory()
	{
		Assert.assertTrue(getBaseControl().isMandatory());
	}

	public void assertHasNoErrors()
	{
		Assert.assertEquals(0, getBaseControl().getValidationMessages().size());
	}

}
