package de.pellepelster.myadmin.client.web.test.modules.dictionary.controls;

<<<<<<< HEAD
import junit.framework.Assert;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
=======

import junit.framework.Assert;
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
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
<<<<<<< HEAD
		Assert.assertEquals(0, getBaseControl().getValidationMessages().count());
	}

	public void assertHasErrorWithText(String text)
	{
		for (IValidationMessage validationMessage : getBaseControl().getValidationMessages())
		{
			if (validationMessage.getMessage().equals(text))
			{
				return;
			}
		}

		Assert.fail("message with containing '" + text + "' not found");
=======
		Assert.assertEquals(0, getBaseControl().getValidationMessages().size());
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
	}

}
