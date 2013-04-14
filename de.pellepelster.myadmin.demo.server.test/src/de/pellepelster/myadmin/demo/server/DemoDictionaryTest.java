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
package de.pellepelster.myadmin.demo.server;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import de.pellepelster.myadmin.client.base.jpql.AssociationVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleNavigationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.ModuleVO;
import de.pellepelster.myadmin.demo.client.web.dictionaries.CountryDictionaryIDs;

public final class DemoDictionaryTest extends BaseDemoDictionaryImporterTest
{
	@Test
	public void testCountry()
	{
		IDictionaryModel countryDictionaryModel = getDictionaryService().getDictionary(CountryDictionaryIDs.COUNTRY);

		Assert.assertNotNull(countryDictionaryModel);
		Assert.assertNotNull(countryDictionaryModel.getEditorModel());
		Assert.assertNotNull(countryDictionaryModel.getSearchModel());

		Assert.assertEquals(1, countryDictionaryModel.getSearchModel().getFilterModel().size());
	}

	@Test
	public void testModelNames()
	{
		IDictionaryModel countryDictionaryModel = getDictionaryService().getDictionary(CountryDictionaryIDs.COUNTRY);

		ITextControlModel textControlModel = (ITextControlModel) countryDictionaryModel.getEditorModel().getCompositeModel().getChildren().get(0).getChildren()
				.get(0).getControls().get(0);

		Assert.assertEquals("Country-RootComposite-Composite2-Composite3-CountryName", ModelUtil.getDebugId(textControlModel));
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

}
