/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package de.pellepelster.myadmin.client.web.test.modules.dictionary;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBigDecimalControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBooleanControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IDateControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IEnumerationControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IHierarchicalControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IIntegerControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IReferenceControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.ITextControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.IBaseDictionaryModule;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BaseControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BigDecimalControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BooleanControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.DateControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.EnumerationControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.HierarchicalControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.IntegerControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.ReferenceControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.TextControlTest;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public abstract class BaseDictionaryModuleTestUI
{
	private IBaseDictionaryModule baseDictionaryModule;

	public BaseDictionaryModuleTestUI(IBaseDictionaryModule baseDictionaryModule)
	{
		super();
		this.baseDictionaryModule = baseDictionaryModule;
	}

	public <ElementType extends IBaseControl<Value, ?>, Value extends Object> BaseControlTest<ElementType, Value> getBaseControlTestElement(
			DictionaryDescriptor<ElementType> controlDescriptor)
	{
		return new BaseControlTest<ElementType, Value>(this.baseDictionaryModule.getElement(controlDescriptor));
	}

	public TextControlTest getTextControlTest(DictionaryDescriptor<ITextControl> controlDescriptor)
	{
		return new TextControlTest(this.baseDictionaryModule.getElement(controlDescriptor));
	}

	public BigDecimalControlTest getBigDecimalControlTest(DictionaryDescriptor<IBigDecimalControl> controlDescriptor)
	{
		return new BigDecimalControlTest(this.baseDictionaryModule.getElement(controlDescriptor));
	}

	public BooleanControlTest getBooleanControlTest(DictionaryDescriptor<IBooleanControl> controlDescriptor)
	{
		return new BooleanControlTest(this.baseDictionaryModule.getElement(controlDescriptor));
	}

	public DateControlTest getDateControlTest(DictionaryDescriptor<IDateControl> controlDescriptor)
	{
		return new DateControlTest(this.baseDictionaryModule.getElement(controlDescriptor));
	}

	public EnumerationControlTest getEnumerationControlTest(DictionaryDescriptor<IEnumerationControl> controlDescriptor)
	{
		return new EnumerationControlTest(this.baseDictionaryModule.getElement(controlDescriptor));
	}

	public HierarchicalControlTest getHierarchicalControlTest(DictionaryDescriptor<IHierarchicalControl> controlDescriptor)
	{
		return new HierarchicalControlTest(this.baseDictionaryModule.getElement(controlDescriptor));
	}

	public IntegerControlTest getIntegerControlTest(DictionaryDescriptor<IIntegerControl> controlDescriptor)
	{
		return new IntegerControlTest(this.baseDictionaryModule.getElement(controlDescriptor));
	}

	public <ReferenceVOType extends IBaseVO> ReferenceControlTest<ReferenceVOType> getReferenceControlTest(
			DictionaryDescriptor<IReferenceControl<ReferenceVOType>> controlDescriptor)
	{
		return new ReferenceControlTest<ReferenceVOType>(this.baseDictionaryModule.getElement(controlDescriptor));
	}

}
