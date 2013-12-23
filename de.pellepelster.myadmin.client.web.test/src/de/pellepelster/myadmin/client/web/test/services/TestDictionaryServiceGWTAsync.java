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
package de.pellepelster.myadmin.client.web.test.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.entities.dictionary.CONTROL_TYPE;
import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_BASETYPE;
import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_CONTAINER_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryDatatypeVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryEditorVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryResultVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionarySearchVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;
import de.pellepelster.myadmin.client.web.test.dictionary.TestDictionaryContainerFactory;
import de.pellepelster.myadmin.client.web.test.dictionary.TestDictionaryControlFactory;
import de.pellepelster.myadmin.client.web.test.dictionary.TestDictionaryFactory;
import de.pellepelster.myadmin.client.web.test.vo.HierarchicalTest1VO;
import de.pellepelster.myadmin.client.web.test.vo.HierarchicalTest2VO;
import de.pellepelster.myadmin.client.web.test.vo.HierarchicalTest3VO;
import de.pellepelster.myadmin.client.web.test.vo.Test1VO;
import de.pellepelster.myadmin.client.web.test.vo.Test2VO;
import de.pellepelster.myadmin.client.web.test.vo.Test3VO;

public class TestDictionaryServiceGWTAsync
{

	public static final String DICTIONARY1_ID = "Dictionary1";

	public static final String DICTIONARY2_ID = "Dictionary2";

	public static final String DICTIONARY3_ID = "Dictionary3";

	public static final String HIERARCHICAL_DICTIONARY1_ID = "HierarchicalDictionary1";

	public static final String HIERARCHICAL_DICTIONARY2_ID = "HierarchicalDictionary2";

	public static final String HIERARCHICAL_DICTIONARY3_ID = "HierarchicalDictionary3";

	public static final String DICTIONARY_ASSIGNMENT_TEST_ID = "DictionaryAssignmentTest";

	public static final String DICTIONARY_HIERARCHICAL_TEST_ID = "DictionaryHierarchicalTest";

	private DictionaryControlVO getBigDecimalControl(String label, String attributePath)
	{

		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(label);

		dictionaryControlVO.setAttributePath(attributePath);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();
		dictionaryDatatypeVO.setFractionDigits(2);
		dictionaryDatatypeVO.setTotalDigits(2);

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.BIGDECIMAL);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}

	private DictionaryControlVO getBooleanControl(String label, String attributePath)
	{

		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(label);

		dictionaryControlVO.setAttributePath(attributePath);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.BOOLEAN);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}

	private DictionaryControlVO getDateControl(String label, String attributePath)
	{

		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(label);

		dictionaryControlVO.setAttributePath(attributePath);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.DATE);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}

	public void getDictionaries(List<String> dictionaryNames, AsyncCallback<List<IDictionaryModel>> callback)
	{
		List<IDictionaryModel> result = new ArrayList<IDictionaryModel>();

		for (String dictionaryName : dictionaryNames)
		{
			IDictionaryModel dictionaryModel = getDictionary(dictionaryName);

			if (dictionaryModel == null)
			{
				callback.onFailure(new RuntimeException("dictionary '" + dictionaryName + "' not found"));
			}

			result.add(dictionaryModel);
		}

		callback.onSuccess(result);
	}

	private IDictionaryModel getDictionary(String dictionaryName)
	{

		if (DICTIONARY1_ID.equals(dictionaryName))
		{

			List<DictionaryControlVO> dictionaryControl1VOs = new ArrayList<DictionaryControlVO>();
			dictionaryControl1VOs.add(TestDictionaryControlFactory.createTextControl("Textcontrol", "string1", true));
			dictionaryControl1VOs.add(getIntegerControl("IntegerControl", "integer1"));
			dictionaryControl1VOs.add(getBigDecimalControl("BigDecimalControl", "bigDecimal1"));
			dictionaryControl1VOs.add(getDateControl("DateControl", "date1"));
			dictionaryControl1VOs.add(getBooleanControl("BooleanControl", "boolean1"));
			// dictionaryControlVOs.add(getTimestampControl("TimestampControl",
			// "timestamp1"));
			dictionaryControl1VOs.add(getEnumerationControl1("EnumerationControl", "enumeration1"));
			dictionaryControl1VOs.add(getReferenceControl("ReferenceControl", "test3VO", DICTIONARY3_ID));

			List<DictionaryControlVO> dictionaryControl2VOs = new ArrayList<DictionaryControlVO>();
			dictionaryControl2VOs.add(TestDictionaryControlFactory.createTextControl("Textcontrol", "string2"));
			dictionaryControl2VOs.add(getIntegerControl("IntegerControl", "integer2"));
			dictionaryControl2VOs.add(getBigDecimalControl("BigDecimalControl", "bigDecimal2"));
			dictionaryControl2VOs.add(getDateControl("DateControl", "date2"));
			dictionaryControl2VOs.add(getBooleanControl("BooleanControl", "boolean2"));
			// dictionaryControl2VOs.add(getTimestampControl("TimestampControl",
			// "timestamp1"));
			dictionaryControl2VOs.add(getEnumerationControl2("EnumerationControl", "enumeration2"));
			dictionaryControl2VOs.add(getReferenceControl("ReferenceControl", "test3VO", DICTIONARY3_ID));

			// dictionaryControlVOs.add(getIntegerControl("IntegerControl",
			// "integer1"));
			// dictionaryControlVOs.add(getIntegerControl("IntegerControl",
			// "integer1"));
			// REFERENCE, COMBOBOX

			DictionaryContainerVO controlsContainerVO = new DictionaryContainerVO();
			controlsContainerVO.setType(DICTIONARY_CONTAINER_TYPE.COMPOSITE);
			controlsContainerVO.getControls().addAll(dictionaryControl1VOs);

			DictionaryContainerVO editableTable = new DictionaryContainerVO();
			editableTable.setAttributePath("test2VOs");
			editableTable.setType(DICTIONARY_CONTAINER_TYPE.EDITABLE_TABLE);
			editableTable.getControls().addAll(dictionaryControl2VOs);
			editableTable.setEntityName(Test2VO.class.getName());

			DictionaryVO dictionaryVO = new DictionaryVO();
			dictionaryVO.setName(dictionaryName);
			dictionaryVO.setEntityName(Test1VO.class.getName());
			dictionaryVO.getLabelControls().add(TestDictionaryControlFactory.createTextControl("Textcontrol", "string1"));

			// - dictionary editor ---------------------------------------------
			DictionaryEditorVO dictionaryEditorVO = new DictionaryEditorVO();
			DictionaryContainerVO dictionaryContainerVO = new DictionaryContainerVO();
			dictionaryContainerVO.getChildren().add(controlsContainerVO);
			dictionaryContainerVO.getChildren().add(editableTable);
			dictionaryEditorVO.setContainer(dictionaryContainerVO);
			dictionaryVO.setEditor(dictionaryEditorVO);

			// - dictionary search ---------------------------------------------
			DictionarySearchVO dictionarySearchVO = new DictionarySearchVO();
			dictionaryVO.setSearch(dictionarySearchVO);

			// - dictionary filter ---------------------------------------------
			DictionaryFilterVO dictionaryFilterVO = new DictionaryFilterVO();
			dictionarySearchVO.setFilter(Arrays.asList(new DictionaryFilterVO[] { dictionaryFilterVO }));
			dictionaryFilterVO.setContainer(controlsContainerVO);

			// - dictionary result ---------------------------------------------
			DictionaryResultVO dictionaryResultVO = new DictionaryResultVO();
			dictionaryResultVO.getControls().addAll(dictionaryControl1VOs);
			dictionarySearchVO.setResult(dictionaryResultVO);

			DictionaryModel dictionaryModel = new DictionaryModel(dictionaryName, null);

			return dictionaryModel;
		}

		if (DICTIONARY2_ID.equals(dictionaryName))
		{
			DictionaryControlVO text2Control = TestDictionaryControlFactory.createTextControl("Textcontrol", "string2");

			List<DictionaryControlVO> dictionaryControl1VOs = new ArrayList<DictionaryControlVO>();
			dictionaryControl1VOs.add(text2Control);
			dictionaryControl1VOs.add(getIntegerControl("IntegerControl", "integer2"));
			dictionaryControl1VOs.add(getBigDecimalControl("BigDecimalControl", "bigDecimal2"));
			dictionaryControl1VOs.add(getDateControl("DateControl", "date2"));
			dictionaryControl1VOs.add(getBooleanControl("BooleanControl", "boolean2"));
			// dictionaryControlVOs.add(getTimestampControl("TimestampControl",
			// "timestamp1"));
			dictionaryControl1VOs.add(getEnumerationControl1("EnumerationControl", "enumeration2"));

			DictionaryContainerVO controlsContainerVO = new DictionaryContainerVO();
			controlsContainerVO.setType(DICTIONARY_CONTAINER_TYPE.COMPOSITE);
			controlsContainerVO.getControls().addAll(dictionaryControl1VOs);

			DictionaryVO dictionaryVO = new DictionaryVO();
			dictionaryVO.setName(dictionaryName);
			dictionaryVO.setEntityName(Test2VO.class.getName());
			dictionaryVO.getLabelControls().add(text2Control);

			// - dictionary editor ---------------------------------------------
			DictionaryEditorVO dictionaryEditorVO = new DictionaryEditorVO();
			DictionaryContainerVO dictionaryContainerVO = new DictionaryContainerVO();
			dictionaryContainerVO.getChildren().add(controlsContainerVO);
			dictionaryEditorVO.setContainer(dictionaryContainerVO);
			dictionaryVO.setEditor(dictionaryEditorVO);

			// - dictionary search ---------------------------------------------
			DictionarySearchVO dictionarySearchVO = new DictionarySearchVO();
			dictionaryVO.setSearch(dictionarySearchVO);

			// - dictionary filter ---------------------------------------------
			DictionaryFilterVO dictionaryFilterVO = new DictionaryFilterVO();
			dictionarySearchVO.setFilter(Arrays.asList(new DictionaryFilterVO[] { dictionaryFilterVO }));
			dictionaryFilterVO.setContainer(controlsContainerVO);

			// - dictionary result ---------------------------------------------
			DictionaryResultVO dictionaryResultVO = new DictionaryResultVO();
			dictionaryResultVO.getControls().addAll(dictionaryControl1VOs);
			dictionarySearchVO.setResult(dictionaryResultVO);

			DictionaryModel dictionaryModel = new DictionaryModel(dictionaryName, null);

			return dictionaryModel;
		}

		if (DICTIONARY3_ID.equals(dictionaryName))
		{
			TestDictionaryFactory testDictionaryFactory = TestDictionaryFactory.create(dictionaryName, Test3VO.class.getName());
			testDictionaryFactory.addControlToAll(TestDictionaryControlFactory.createTextControl(Test3VO.FIELD_STRING3));

			return testDictionaryFactory.getDictionaryModel();
		}

		if (DICTIONARY_ASSIGNMENT_TEST_ID.equals(dictionaryName))
		{
			TestDictionaryFactory testDictionaryFactory = TestDictionaryFactory.create(dictionaryName, Test1VO.class.getName());

			testDictionaryFactory.createSearch().addControlToAll(TestDictionaryControlFactory.createTextControl(Test1VO.FIELD_STRING1));
			testDictionaryFactory.createEditor().addContainer(
					TestDictionaryContainerFactory.createAssigmentTable(DICTIONARY2_ID, Test1VO.FIELD_TEST2VOS,
							TestDictionaryControlFactory.createTextControl(Test2VO.FIELD_STRING2)));

			return testDictionaryFactory.getDictionaryModel();
		}

		if (DICTIONARY_HIERARCHICAL_TEST_ID.equals(dictionaryName))
		{
			TestDictionaryFactory testDictionaryFactory = TestDictionaryFactory.create(dictionaryName, HierarchicalTest1VO.class.getName());

			DictionaryControlVO controlVO = TestDictionaryControlFactory.createHierarchicalControl(HierarchicalTest1VO.FIELD_PARENT);

			testDictionaryFactory.createEditor().addControl(controlVO);

			testDictionaryFactory.createSearch().addControlToAll(controlVO);

			testDictionaryFactory.addLabelControl(TestDictionaryControlFactory.createTextControl(HierarchicalTest1VO.FIELD_STRING1));

			return testDictionaryFactory.getDictionaryModel();
		}

		if (HIERARCHICAL_DICTIONARY1_ID.equals(dictionaryName) || HIERARCHICAL_DICTIONARY2_ID.equals(dictionaryName)
				|| HIERARCHICAL_DICTIONARY3_ID.equals(dictionaryName))
		{
			DictionaryControlVO hierarchicalControl = getHierarchicalControl("HierarchicalControl", TestHierarchicalServiceGWTAsync.HIERARCHICAL_TREE1);

			DictionaryVO dictionaryVO = new DictionaryVO();
			dictionaryVO.setName(dictionaryName);

			if (HIERARCHICAL_DICTIONARY1_ID.equals(dictionaryName))
			{
				dictionaryVO.setEntityName(HierarchicalTest1VO.class.getName());
			}
			else if (HIERARCHICAL_DICTIONARY2_ID.equals(dictionaryName))
			{
				dictionaryVO.setEntityName(HierarchicalTest2VO.class.getName());
			}
			else if (HIERARCHICAL_DICTIONARY3_ID.equals(dictionaryName))
			{
				dictionaryVO.setEntityName(HierarchicalTest3VO.class.getName());
			}

			dictionaryVO.getLabelControls().add(TestDictionaryControlFactory.createTextControl("String1", "string1"));

			// - dictionary editor ---------------------------------------------
			DictionaryEditorVO dictionaryEditorVO = new DictionaryEditorVO();
			DictionaryContainerVO dictionaryContainerVO = new DictionaryContainerVO();
			dictionaryContainerVO.getControls().add(hierarchicalControl);
			dictionaryEditorVO.setContainer(dictionaryContainerVO);
			dictionaryVO.setEditor(dictionaryEditorVO);

			DictionaryModel dictionaryModel = new DictionaryModel(dictionaryName, null);

			return dictionaryModel;
		}

		return null;
	}

	private DictionaryControlVO getHierarchicalControl(String label, String hierarchicalId)
	{

		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(label);
		dictionaryControlVO.setHierarchicalId(hierarchicalId);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.HIERARCHICAL);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}

	public void getDictionary(String dictionaryName, AsyncCallback<IDictionaryModel> callback)
	{

		IDictionaryModel dictionaryModel = getDictionary(dictionaryName);

		if (dictionaryModel == null)
		{
			callback.onFailure(new RuntimeException("dictionary '" + dictionaryName + "' not found"));
		}
		else
		{
			callback.onSuccess(dictionaryModel);
		}

	}

	private DictionaryControlVO getEnumerationControl1(String label, String attributePath)
	{

		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(label);

		dictionaryControlVO.setAttributePath(attributePath);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.getEnumerationValues().put(Test1VO.ENUM1.ENUM1_VALUE1.toString(), Test1VO.ENUM1.ENUM1_VALUE1.toString());
		dictionaryDatatypeVO.getEnumerationValues().put(Test1VO.ENUM1.ENUM1_VALUE2.toString(), Test1VO.ENUM1.ENUM1_VALUE2.toString());
		dictionaryDatatypeVO.getEnumerationValues().put(Test1VO.ENUM1.ENUM1_VALUE3.toString(), Test1VO.ENUM1.ENUM1_VALUE3.toString());

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.ENUMERATION);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}

	private DictionaryControlVO getEnumerationControl2(String label, String attributePath)
	{

		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(label);

		dictionaryControlVO.setAttributePath(attributePath);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.getEnumerationValues().put(Test2VO.ENUM2.ENUM2_VALUE1.toString(), Test2VO.ENUM2.ENUM2_VALUE1.toString());
		dictionaryDatatypeVO.getEnumerationValues().put(Test2VO.ENUM2.ENUM2_VALUE2.toString(), Test2VO.ENUM2.ENUM2_VALUE2.toString());
		dictionaryDatatypeVO.getEnumerationValues().put(Test2VO.ENUM2.ENUM2_VALUE3.toString(), Test2VO.ENUM2.ENUM2_VALUE3.toString());

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.ENUMERATION);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}

	private DictionaryControlVO getIntegerControl(String label, String attributePath)
	{

		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(label);

		dictionaryControlVO.setAttributePath(attributePath);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.INTEGER);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}

	private DictionaryControlVO getReferenceControl(String label, String attributePath, String dictionaryName)
	{
		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(label);
		dictionaryControlVO.setControlType(CONTROL_TYPE.TEXT);

		dictionaryControlVO.setAttributePath(attributePath);
		dictionaryControlVO.setDictionary(dictionaryName);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.REFERENCE);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}

	private DictionaryControlVO getTimestampControl(String label, String attributePath)
	{

		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();
		dictionaryControlVO.setEditorLabel(label);
		dictionaryControlVO.setColumnLabel(label);
		dictionaryControlVO.setMandatory(false);
		dictionaryControlVO.setName(label);

		dictionaryControlVO.setAttributePath(attributePath);

		DictionaryDatatypeVO dictionaryDatatypeVO = new DictionaryDatatypeVO();

		dictionaryDatatypeVO.setBaseType(DICTIONARY_BASETYPE.TIMESTAMP);
		dictionaryControlVO.setDatatype(dictionaryDatatypeVO);

		return dictionaryControlVO;
	}

}
