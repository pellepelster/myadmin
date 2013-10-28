package de.pellepelster.myadmin.client.web.test.modules.dictionary.controls;

import junit.framework.Assert;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;

public class BaseControlElementTest<ElementType extends IBaseControl<ValueType>, ValueType>
{

	private ElementType baseControl;

	public BaseControlElementTest(ElementType baseControl)
	{
		this.baseControl = baseControl;
	}

	public void assertValue(Object expectedValue)
	{
		Assert.assertEquals(expectedValue, this.baseControl.getValue());
	}

	public void setValue(ValueType value)
	{
		this.baseControl.setValue(value);
	}

	public void parse(String valueString)
	{
		this.baseControl.parseValue(valueString);
	}

}
