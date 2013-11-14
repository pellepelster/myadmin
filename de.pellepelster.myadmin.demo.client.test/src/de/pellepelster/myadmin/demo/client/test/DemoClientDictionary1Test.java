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

import de.pellepelster.myadmin.client.web.test.MyAdminAsyncGwtTestCase;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionaryEditorModuleTestUIAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.container.EditableTableTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BigDecimalControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BooleanControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.DateControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.EnumerationControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.IntegerControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.ReferenceControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.TextControlTestAsyncHelper;
import de.pellepelster.myadmin.demo.client.web.DemoDictionaryIDs;
import de.pellepelster.myadmin.demo.client.web.test1.Test1VO;
import de.pellepelster.myadmin.demo.client.web.test1.Test3VO;

public class DemoClientDictionary1Test extends MyAdminAsyncGwtTestCase<Test1VO>
{

	@Override
	public String getModuleName()
	{
		return "de.pellepelster.myadmin.demo.DemoTest";
	}

	@Test
	public void testEditableTableBase()
	{
		DictionaryEditorModuleTestUIAsyncHelper<Test1VO> editor = openEditor(DemoDictionaryIDs.DICTIONARY1);

		final EditableTableTestAsyncHelper<Test3VO> editableTable = editor
				.getEditableTableTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_EDITABLE_TABLE1);

		editableTable.add();
		editableTable.assertRowCount(1);
		runAsyncTests();
	}

	@Test
	public void testIntegerControl()
	{
		DictionaryEditorModuleTestUIAsyncHelper<Test1VO> editor = openEditor(DemoDictionaryIDs.DICTIONARY1);

		IntegerControlTestAsyncHelper integerControl = editor
				.getIntegerControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.INTEGER_CONTROL1);
		integerControl.assertHasNoErrors();
		runAsyncTests();
	}

	@Test
	public void testReferenceControl()
	{
		DictionaryEditorModuleTestUIAsyncHelper<Test1VO> editor = openEditor(DemoDictionaryIDs.DICTIONARY1);

		ReferenceControlTestAsyncHelper referenceControl = editor
				.getReferenceControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.REFERENCE_CONTROL1);
		referenceControl.assertHasNoErrors();
		runAsyncTests();
	}

	@Test
	public void testEnumerationControl()
	{
		DictionaryEditorModuleTestUIAsyncHelper<Test1VO> editor = openEditor(DemoDictionaryIDs.DICTIONARY1);

		EnumerationControlTestAsyncHelper enumerationControl = editor
				.getEnumerationControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.ENUMERATION_CONTROL1);
		enumerationControl.assertHasNoErrors();
		runAsyncTests();
	}

	@Test
	public void testDateControl()
	{
		DictionaryEditorModuleTestUIAsyncHelper<Test1VO> editor = openEditor(DemoDictionaryIDs.DICTIONARY1);

		DateControlTestAsyncHelper dateControl = editor
				.getDateControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.DATE_CONTROL1);
		dateControl.assertHasNoErrors();

		// parse date
		Date now = new Date();
		dateControl.parse(DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM).format(now));
		dateControl.assertValueWithoutMillies(now);

		// parse incorrect date
		dateControl.parse("xxx");
		dateControl.assertValue(null);

		dateControl.setValue(now);
		dateControl.assertValue(now);
		runAsyncTests();
	}

	@Test
	public void testBooleanControl()
	{
		DictionaryEditorModuleTestUIAsyncHelper<Test1VO> editor = openEditor(DemoDictionaryIDs.DICTIONARY1);

		BooleanControlTestAsyncHelper booleanControl = editor
				.getBooleanControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.BOOLEAN_CONTROL1);
		booleanControl.assertHasNoErrors();
		runAsyncTests();
	}

	@Test
	public void testBigDecimalControl()
	{
		DictionaryEditorModuleTestUIAsyncHelper<Test1VO> editor = openEditor(DemoDictionaryIDs.DICTIONARY1);

		BigDecimalControlTestAsyncHelper bigDecimalControl = editor
				.getBigDecimalControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.BIG_DECIMAL_CONTROL1);
		bigDecimalControl.assertHasNoErrors();
		bigDecimalControl.parse("1");
		bigDecimalControl.assertHasNoErrors();
		bigDecimalControl.assertValue(new BigDecimal(1));

		bigDecimalControl.parse("x");
		bigDecimalControl.assertHasErrorWithText("'x' is not a valid decimal");
		bigDecimalControl.assertValue(null);

		runAsyncTests();
	}

	@Test
	public void testTextControl()
	{
		DictionaryEditorModuleTestUIAsyncHelper<Test1VO> editor = openEditor(DemoDictionaryIDs.DICTIONARY1);

		// text control
		TextControlTestAsyncHelper textControl = editor
				.getTextControlTest(DemoDictionaryIDs.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1);
		textControl.assertMandatory();

		textControl.assertHasNoErrors();
		textControl.setValue("xxx");

		textControl.assertHasNoErrors();
		textControl.setValue(null);
		textControl.assertHasErrorWithText("Input is needed for field \"TextControl1\"");
		editor.assertHasErrors(1);

		textControl.setValue("text1");
		textControl.assertHasNoErrors();
		editor.assertHasErrors(0);

		runAsyncTests();
	}
}
