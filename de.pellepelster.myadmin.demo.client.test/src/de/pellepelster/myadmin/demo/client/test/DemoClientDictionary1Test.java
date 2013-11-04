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
package de.pellepelster.myadmin.demo.client.test;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.junit.client.GWTTestCase;

import de.pellepelster.myadmin.client.web.test.MyAdminTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionaryEditorModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.EditableTableTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BigDecimalControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BooleanControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.DateControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.EnumerationControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.IntegerControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.ReferenceControlTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.TextControlTest;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;
import de.pellepelster.myadmin.demo.client.web.DemoDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.test1.Test1VO;
import de.pellepelster.myadmin.demo.client.web.test1.Test2VO;
import de.pellepelster.myadmin.demo.client.web.test1.Test3VO;

public class DemoClientDictionary1Test extends GWTTestCase
{

	@Override
	public String getModuleName()
	{
		return "de.pellepelster.myadmin.demo.DemoTest";
	}

	private class TestDictionary1EditorSaveResult extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<Test1VO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<Test1VO> result)
		{
			TextControlTest textControl = result.getTextControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1);
			textControl.assertValue("text1");

			final EditableTableTest<Test3VO> editableTable = result
					.getEditableTableTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_EDITABLE_TABLE1);

			editableTable.add(new BaseErrorAsyncCallback<EditableTableTest<Test3VO>>()
			{
				@Override
				public void onSuccess(EditableTableTest<Test3VO> result)
				{
					editableTable.assertRowCount(1);

					finishTest();
				}
			});

		}
	}

	private class TestDictionary1EditorSave extends BaseErrorAsyncCallback<DictionaryEditorModuleTestUI<Test1VO>>
	{
		@Override
		public void onSuccess(DictionaryEditorModuleTestUI<Test1VO> result)
		{
			// text control
			TextControlTest textControl = result.getTextControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1);
			textControl.assertMandatory();

			textControl.assertHasNoErrors();
			textControl.setValue("xxx");

			textControl.assertHasNoErrors();
			textControl.setValue(null);
			textControl.assertHasErrorWithText("Input is needed for field \"TextControl1\"");

			textControl.setValue("text1");
			textControl.assertHasNoErrors();

			// big decimal control
			BigDecimalControlTest bigDecimalControl = result
					.getBigDecimalControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.BIG_DECIMAL_CONTROL1);
			bigDecimalControl.assertHasNoErrors();
			bigDecimalControl.parse("1");
			bigDecimalControl.assertHasNoErrors();
			bigDecimalControl.assertValue(new BigDecimal(1));

			bigDecimalControl.parse("x");
			bigDecimalControl.assertHasErrorWithText("'x' is not a valid decimal");
			bigDecimalControl.assertValue(null);

			// boolean control
			BooleanControlTest booleanControl = result
					.getBooleanControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.BOOLEAN_CONTROL1);

			// date control
			DateControlTest dateControl = result.getDateControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.DATE_CONTROL1);

			// parse date
			Date now = new Date();
			dateControl.parse(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM).format(now));
			dateControl.assertValueWithoutMillies(now);

			// parse incorrect date
			dateControl.parse("xxx");
			dateControl.assertValue(null);

			dateControl.setValue(now);
			dateControl.assertValue(now);

			// enumeration control
			EnumerationControlTest enumerationControl = result
					.getEnumerationControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.ENUMERATION_CONTROL1);

			// hierarchical control
			// HierarchicalControlTest hierarchicalControl = result
			// .getHierarchicalControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.);

			// integer control
			IntegerControlTest integerControl = result
					.getIntegerControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.INTEGER_CONTROL1);

			// reference control
			ReferenceControlTest<Test2VO> referenceControl = result
					.getReferenceControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.REFERENCE_CONTROL1);

			result.save(new TestDictionary1EditorSaveResult());
		}
	}

	@Test
	public void testDictionary1()
	{
		MyAdminTest.getInstance().openEditor(DemoDictionaryIDs.DICTIONARY1, new TestDictionary1EditorSave());

		delayTestFinish(2000);
	}

}
