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

import junit.framework.Assert;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.Result;
import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IEditableTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBigDecimalControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBooleanControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IDateControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IEnumerationControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IHierarchicalControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IIntegerControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IReferenceControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.ITextControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.IEditorUpdateListener;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BaseControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BigDecimalControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BooleanControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.DateControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.EnumerationControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.HierarchicalControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.IntegerControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.ReferenceControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.TextControlTest;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

/**
 * UI for the navigation module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModuleTestUI<VOType extends IBaseVO> implements IModuleUI<Object, DictionaryEditorModule<VOType>>, IEditorUpdateListener
{
	private DictionaryEditorModule<VOType> module;

	public DictionaryEditorModuleTestUI(DictionaryEditorModule<VOType> module)
	{
		super();
		this.module = module;
		module.addUpdateListener(this);
	}

	@Override
	public boolean close()
	{
		return false;
	}

	@Override
	public boolean contributesToBreadCrumbs()
	{
		return false;
	}

	@Override
	public Object getContainer()
	{
		return null;
	}

	@Override
	public DictionaryEditorModule<VOType> getModule()
	{
		return this.module;
	}

	@Override
	public String getTitle()
	{
		return this.module.getTitle();
	}

	public <TableVOType extends IBaseVO> EditableTableTest<TableVOType> getEditableTableTest(DictionaryDescriptor<IEditableTable<TableVOType>> controlDescriptor)
	{
		return new EditableTableTest<TableVOType>(this.module.getElement(controlDescriptor));
	}

	public <ElementType extends IBaseControl<Value>, Value extends Object> BaseControlTest<ElementType, Value> getBaseControlTestElement(
			DictionaryDescriptor<ElementType> controlDescriptor)
	{
		return new BaseControlTest<ElementType, Value>(this.module.getElement(controlDescriptor));
	}

	public TextControlTest getTextControlTest(DictionaryDescriptor<ITextControl> controlDescriptor)
	{
		return new TextControlTest(this.module.getElement(controlDescriptor));
	}

	public BigDecimalControlTest getBigDecimalControlTest(DictionaryDescriptor<IBigDecimalControl> controlDescriptor)
	{
		return new BigDecimalControlTest(this.module.getElement(controlDescriptor));
	}

	public BooleanControlTest getBooleanControlTest(DictionaryDescriptor<IBooleanControl> controlDescriptor)
	{
		return new BooleanControlTest(this.module.getElement(controlDescriptor));
	}

	public DateControlTest getDateControlTest(DictionaryDescriptor<IDateControl> controlDescriptor)
	{
		return new DateControlTest(this.module.getElement(controlDescriptor));
	}

	public EnumerationControlTest getEnumerationControlTest(DictionaryDescriptor<IEnumerationControl> controlDescriptor)
	{
		return new EnumerationControlTest(this.module.getElement(controlDescriptor));
	}

	public HierarchicalControlTest getHierarchicalControlTest(DictionaryDescriptor<IHierarchicalControl> controlDescriptor)
	{
		return new HierarchicalControlTest(this.module.getElement(controlDescriptor));
	}

	public IntegerControlTest getIntegerControlTest(DictionaryDescriptor<IIntegerControl> controlDescriptor)
	{
		return new IntegerControlTest(this.module.getElement(controlDescriptor));
	}

	public <ReferenceVOType extends IBaseVO> ReferenceControlTest<ReferenceVOType> getReferenceControlTest(
			DictionaryDescriptor<IReferenceControl<ReferenceVOType>> controlDescriptor)
	{
		return new ReferenceControlTest<ReferenceVOType>(this.module.getElement(controlDescriptor));
	}

	public void save(final AsyncCallback<DictionaryEditorModuleTestUI<VOType>> asyncCallback)
	{
		this.module.getDictionaryEditor().save(new BaseErrorAsyncCallback<Result<VOType>>(asyncCallback)
		{
			@Override
			public void onSuccess(Result<VOType> result)
			{
				asyncCallback.onSuccess(DictionaryEditorModuleTestUI.this);
			}
		});
	}

	@Override
	public void onUpdate()
	{
	}

	public void assertHasNoErrors()
	{
		Assert.assertFalse(this.module.getDictionaryEditor().getValidationMessages().hasErrors());
	}

	public void assertHasErrors(int errorCount)
	{
		Assert.assertEquals(errorCount, this.module.getDictionaryEditor().getValidationMessages().count());
	}

	public void assertTitle(String expectedTitle)
	{
		Assert.assertEquals(expectedTitle, this.module.getTitle());
	}
}
