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
import de.pellepelster.myadmin.demo.client.web.dictionaries.DemoDictionaryModel;
import de.pellepelster.myadmin.demo.client.web.entities.CountryVO;
import de.pellepelster.myadmin.demo.client.web.navigation.DemoNavigationTree;

public final class DemoDictionaryTest extends BaseDemoDictionaryTest
{

	@Test
	public void testGetEditorLabelWithFallback()
	{
		Assert.assertEquals(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1.getName(),
				DictionaryModelUtil.getEditorLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1));

		Assert.assertEquals("IntegerControl",
				DictionaryModelUtil.getEditorLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.INTEGER_CONTROL1));

		Assert.assertEquals("BigDecimalEditorLabel",
				DictionaryModelUtil.getEditorLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.BIG_DECIMAL_CONTROL1));
	}

	@Test
	public void testGetFilterLabelWithFallback()
	{
		Assert.assertEquals(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1.getName(),
				DictionaryModelUtil.getFilterLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1));

		Assert.assertEquals("IntegerControl",
				DictionaryModelUtil.getFilterLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.INTEGER_CONTROL1));

		Assert.assertEquals("BigDecimalFilterLabel",
				DictionaryModelUtil.getFilterLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.BIG_DECIMAL_CONTROL1));
	}

	@Test
	public void testGetColumnLabelWithFallback()
	{
		Assert.assertEquals(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1.getName(),
				DictionaryModelUtil.getColumnLabel(DemoDictionaryModel.DICTIONARY1.DICTIONARY1_EDITOR.DICTIONARY1_COMPOSITE3.TEXT_CONTROL1));

		Assert.assertEquals("IntegerControl",
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
		Assert.assertEquals("Country", DemoNavigationTree.ROOT.MASTERDATA.ADRESS.COUNTRY.getLabel());
		Assert.assertEquals("State", DemoNavigationTree.ROOT.MASTERDATA.ADRESS.STATE.getLabel());
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
