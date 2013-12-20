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
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseBigDecimalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseBooleanControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseDateControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseEnumerationControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseHierarchicalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseIntegerControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseReferenceControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseTextControlModel;
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
			BaseControlModel<ElementType> baseControlModel)
	{
		return new BaseControlTest<ElementType, Value>(this.baseDictionaryModule.getElement(baseControlModel));
	}

	public TextControlTest getTextControlTest(BaseTextControlModel controlModel)
	{
		return new TextControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public BigDecimalControlTest getBigDecimalControlTest(BaseBigDecimalControlModel controlModel)
	{
		return new BigDecimalControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public BooleanControlTest getBooleanControlTest(BaseBooleanControlModel controlModel)
	{
		return new BooleanControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public DateControlTest getDateControlTest(BaseDateControlModel controlModel)
	{
		return new DateControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public EnumerationControlTest getEnumerationControlTest(BaseEnumerationControlModel controlModel)
	{
		return new EnumerationControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public HierarchicalControlTest getHierarchicalControlTest(BaseHierarchicalControlModel controlModel)
	{
		return new HierarchicalControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public IntegerControlTest getIntegerControlTest(BaseIntegerControlModel controlModel)
	{
		return new IntegerControlTest(this.baseDictionaryModule.getElement(controlModel));
	}

	public <ReferenceVOType extends IBaseVO> ReferenceControlTest<ReferenceVOType> getReferenceControlTest(
			BaseReferenceControlModel<ReferenceVOType> controlModel)
	{
		return new ReferenceControlTest<ReferenceVOType>(this.baseDictionaryModule.getElement(controlModel));
	}

}
