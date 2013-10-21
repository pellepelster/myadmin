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

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import de.pellepelster.myadmin.client.base.jpql.AssociationVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.demo.DemoDictionaryIDs;

public final class DemoDictionaryTest extends BaseDemoDictionaryTest
{
	@Test
	public void testCountry()
	{
		IDictionaryModel countryDictionaryModel = getDictionaryService().getDictionary(DemoDictionaryIDs.COUNTRY.getId());

		Assert.assertNotNull(countryDictionaryModel);
		Assert.assertNotNull(countryDictionaryModel.getEditorModel());
		Assert.assertNotNull(countryDictionaryModel.getSearchModel());

		Assert.assertEquals(1, countryDictionaryModel.getSearchModel().getFilterModel().size());
	}

	@Test
	public void testModelNames()
	{
		IDictionaryModel countryDictionaryModel = getDictionaryService().getDictionary(DemoDictionaryIDs.COUNTRY.getId());

		ITextControlModel textControlModel = (ITextControlModel) countryDictionaryModel.getEditorModel().getCompositeModel().getChildren().get(0).getChildren()
				.get(0).getControls().get(0);

		Assert.assertEquals("Country-CountryEditor-Composite2-Composite3-CountryName", DictionaryModelUtil.getDebugId(textControlModel));
	}

	@Test
	public void testGetNavigation()
	{
		GenericFilterVO<ModuleNavigationVO> genericFilterVO = new GenericFilterVO<ModuleNavigationVO>(ModuleNavigationVO.class);
		genericFilterVO.addCriteria(ModuleNavigationVO.FIELD_PARENT.getAttributeName(), null);
		genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_CHILDREN.getAttributeName());

		AssociationVO moduleAssociationVO = genericFilterVO.addAssociation(ModuleNavigationVO.FIELD_MODULE);
		moduleAssociationVO.addAssociation(ModuleVO.FIELD_MODULEDEFINITION);
		moduleAssociationVO.addAssociation(ModuleVO.FIELD_PROPERTIES);

		List<ModuleNavigationVO> result = this.baseVODAO.filter(genericFilterVO);
		Assert.assertEquals(2, result.size());

		ModuleNavigationVO countryModuleNavigationVO = result.get(1).getChildren().get(0).getChildren().get(0);
		Assert.assertEquals("Country", countryModuleNavigationVO.getTitle());
		Assert.assertFalse(countryModuleNavigationVO.getModule().getProperties().isEmpty());

	}

	@Test
	public void testDictionary1()
	{
		IDictionaryModel dictionaryModel = getDictionaryService().getDictionary(DemoDictionaryIDs.DICTIONARY1.getId());
		Assert.assertEquals("Dictionary1", dictionaryModel.getName());

		Assert.assertEquals(7, dictionaryModel.getSearchModel().getResultModel().getControls().size());

	}

	@Test
	public void testReferenceControlLabelFallback()
	{
		IDictionaryModel dictionaryModel = getDictionaryService().getDictionary(DemoDictionaryIDs.DICTIONARY1.getId());

		IReferenceControlModel referenceControlModel = (IReferenceControlModel) dictionaryModel.getSearchModel().getResultModel().getControls().get(6);
		Assert.assertEquals("Dictionary2", referenceControlModel.getDictionaryName());
		Assert.assertEquals(1, referenceControlModel.getLabelControls().size());
		Assert.assertEquals("ReferenceControl1", referenceControlModel.getName());
	}

	@Test
	public void testDictionary2()
	{
		IDictionaryModel dictionaryModel = getDictionaryService().getDictionary(DemoDictionaryIDs.DICTIONARY2.getId());
		Assert.assertEquals("Dictionary2", dictionaryModel.getName());

		Assert.assertEquals(1, dictionaryModel.getLabelControls().size());
		Assert.assertEquals("TextControl2", dictionaryModel.getLabelControls().get(0).getName());

	}

}
