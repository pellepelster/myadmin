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

import java.util.Date;

import org.junit.Test;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.junit.client.GWTTestCase;

import de.pellepelster.myadmin.client.web.test.MyAdminTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionaryEditorModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.EditableTableTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.DateControlElementTest;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.TextControlElementTest;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;
import de.pellepelster.myadmin.demo.client.web.DemoDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.test1.Test1VO;
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
			TextControlElementTest textControl = result
					.getTextControlTestElement(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1);
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
			TextControlElementTest textControl = result
					.getTextControlTestElement(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1);
			textControl.setValue("text1");

			// date control
			DateControlElementTest dateControl = result
					.getDateControlTestElement(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.DATE_CONTROL1);
			Date now = new Date();
			dateControl.parse(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM).format(now));
			dateControl.assertValue(now);

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
