package de.pellepelster.myadmin.client.web.test.modules.dictionary;

import junit.framework.Assert;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;


public class TestBaseDictionaryControl<ElementType extends IBaseControl<?>> {

	private ElementType baseControl;
	
	public TestBaseDictionaryControl(ElementType baseControl) {
		this.baseControl = baseControl;
	}

	public void assertValue(Object expectedValue)
	{
		Assert.assertEquals(expectedValue, baseControl.getValue());
	}
}
