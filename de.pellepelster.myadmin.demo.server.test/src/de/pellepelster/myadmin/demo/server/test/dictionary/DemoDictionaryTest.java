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
package de.pellepelster.myadmin.demo.server.test.dictionary;

import org.junit.Assert;
import org.junit.Test;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelProvider;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.base.modules.navigation.NavigationTreeProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.demo.client.web.dictionaries.DemoDictionaryModel;
import de.pellepelster.myadmin.demo.client.web.entities.CountryVO;
import de.pellepelster.myadmin.demo.client.web.navigation.DemoNavigationTree;

public final class DemoDictionaryTest extends BaseDemoDictionaryTest
{

	@Test
	public void testGetEditorLabel()
	{
		Assert.assertEquals(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1.getName(),
				DictionaryModelUtil.getEditorLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1));

		Assert.assertEquals("IntegerControlLabel",
				DictionaryModelUtil.getEditorLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.INTEGER_CONTROL1));

		Assert.assertEquals("BigDecimalEditorLabel",
				DictionaryModelUtil.getEditorLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.BIG_DECIMAL_CONTROL1));
	}

	@Test
	public void testGetFilterLabel()
	{
		Assert.assertEquals(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1.getName(),
				DictionaryModelUtil.getFilterLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1));

		Assert.assertEquals("IntegerControlLabel",
				DictionaryModelUtil.getFilterLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.INTEGER_CONTROL1));

		Assert.assertEquals("BigDecimalFilterLabel",
				DictionaryModelUtil.getFilterLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.BIG_DECIMAL_CONTROL1));
	}

	@Test
	public void testGetColumnLabel()
	{
		Assert.assertEquals(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1.getName(),
				DictionaryModelUtil.getColumnLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1));

		Assert.assertEquals("IntegerControlLabel",
				DictionaryModelUtil.getColumnLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.INTEGER_CONTROL1));

		Assert.assertEquals("BigDecimalColumnLabel",
				DictionaryModelUtil.getColumnLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.BIG_DECIMAL_CONTROL1));
	}

	@Test
	public void testGetVoName()
	{
		Assert.assertEquals(CountryVO.class.getName(), DemoDictionaryModel.COUNTRY.getVoName());
	}

	@Test
	public void testDictionaryLabel()
	{
		Assert.assertEquals("Dictionary1", DictionaryUtil.getLabel(DemoDictionaryModel.DICTIONARY1));
		Assert.assertEquals("Dictionary2Label", DictionaryUtil.getLabel(DemoDictionaryModel.DICTIONARY2));
	}

	@Test
	public void testCountry()
	{
		IDictionaryModel countryDictionaryModel = DictionaryModelProvider.getDictionary(DemoDictionaryModel.COUNTRY.getName());

		Assert.assertNotNull(countryDictionaryModel);
		Assert.assertNotNull(countryDictionaryModel.getEditorModel());
		Assert.assertNotNull(countryDictionaryModel.getSearchModel());

		Assert.assertEquals(1, countryDictionaryModel.getSearchModel().getFilterModels().size());
	}

	@Test
	public void testModelDebugIds()
	{
		IDictionaryModel countryDictionaryModel = DictionaryModelProvider.getDictionary(DemoDictionaryModel.COUNTRY.getName());

		ITextControlModel textControlModel = (ITextControlModel) countryDictionaryModel.getEditorModel().getCompositeModel().getChildren().get(0).getChildren()
				.get(0).getControls().get(0);

		Assert.assertEquals("Country-CountryEditor-Composite2-Composite3-CountryName", DictionaryModelUtil.getDebugId(textControlModel));
	}

	@Test
	public void testNavigationTree()
	{
		Assert.assertEquals(1, NavigationTreeProvider.getRootNavigationElements().size());

		Assert.assertEquals(2, DemoNavigationTree.ROOT.getChildren().size());

		Assert.assertEquals(1, DemoNavigationTree.ROOT.MASTERDATA.getChildren().size());
		Assert.assertEquals(6, DemoNavigationTree.ROOT.MASTERDATA.ADRESS.getChildren().size());

		Assert.assertEquals("City", DemoNavigationTree.ROOT.MASTERDATA.ADRESS.CITY.getLabel());
		Assert.assertEquals("Country", DemoNavigationTree.ROOT.MASTERDATA.ADRESS.COUNTRY.getLabel());
		Assert.assertEquals("Group", DemoNavigationTree.ROOT.MASTERDATA.ADRESS.GROUP.getLabel());
		Assert.assertEquals("Region", DemoNavigationTree.ROOT.MASTERDATA.ADRESS.REGION.getLabel());
		Assert.assertEquals("State", DemoNavigationTree.ROOT.MASTERDATA.ADRESS.STATE.getLabel());
		Assert.assertEquals("User", DemoNavigationTree.ROOT.MASTERDATA.ADRESS.USER.getLabel());

		Assert.assertEquals(4, DemoNavigationTree.ROOT.TEST.getChildren().size());
		Assert.assertEquals("Dictionary1", DemoNavigationTree.ROOT.TEST.DICTIONARY1.getLabel());
		Assert.assertEquals("Dictionary2", DemoNavigationTree.ROOT.TEST.DICTIONARY2.getLabel());
		Assert.assertEquals("Dictionary3", DemoNavigationTree.ROOT.TEST.DICTIONARY3.getLabel());
		Assert.assertEquals("Dictionary4", DemoNavigationTree.ROOT.TEST.DICTIONARY4.getLabel());

	}

	@Test
	public void testDictionary1()
	{
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(DemoDictionaryModel.DICTIONARY1.getName());
		Assert.assertEquals("Dictionary1", dictionaryModel.getName());

		Assert.assertEquals(7, dictionaryModel.getSearchModel().getResultModel().getControls().size());

	}

	@Test
	public void testReferenceControl()
	{
		IReferenceControlModel referenceControlModel = DemoDictionaryModel.DICTIONARY1.DICTIONARY1_SEARCH.DICTIONARY1_RESULT.REFERENCE_CONTROL1;
		Assert.assertEquals("Dictionary2", referenceControlModel.getDictionaryName());
		Assert.assertEquals("ReferenceControl1", referenceControlModel.getName());
	}

	@Test
	public void testDictionary2()
	{
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(DemoDictionaryModel.DICTIONARY2.getName());
		Assert.assertEquals("Dictionary2", dictionaryModel.getName());

		Assert.assertEquals(1, dictionaryModel.getLabelControls().size());
		Assert.assertEquals("TextControl2", dictionaryModel.getLabelControls().get(0).getName());

	}

}
