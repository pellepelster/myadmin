package de.pellepelster.myadmin.client.web.test.modules.dictionary.controls;

import junit.framework.Assert;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;

public class BaseControlTest<ElementType extends IBaseControl<ValueType>, ValueType>
{

	private ElementType baseControl;

	public BaseControlTest(ElementType baseControl)
	{
		this.baseControl = baseControl;
	}

	public void assertValue(ValueType expectedValue)
	{
		Assert.assertEquals(expectedValue, this.baseControl.getValue());
	}

	public ValueType getValue()
	{
		return this.baseControl.getValue();
	}

	protected ElementType getBaseControl()
	{
		return this.baseControl;
	}

	public void setValue(ValueType value)
	{
		this.baseControl.setValue(value);
	}

	public void parse(String valueString)
	{
		this.baseControl.parseValue(valueString);
	}

	public void assertMandatory()
	{
		Assert.assertTrue(getBaseControl().isMandatory());
	}

	public void assertHasNoErrors()
	{
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
	}

}
