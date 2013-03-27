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

import org.junit.Test;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IEditorModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ISearchModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumarationControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.MyAdminRemoteServiceLocator;
import de.pellepelster.myadmin.client.web.services.IDictionaryService;

public class DemoDictionaryServiceRemoteTest extends BaseRemoteTest
{

	@Override
	public void setUp()
	{
		initApplicationContext();
	}

	@Test
	public void testCountry()
	{
		IDictionaryService dictionaryService = MyAdminRemoteServiceLocator.getInstance().getDictionaryService();
		IDictionaryModel dictionaryModel = dictionaryService.getDictionary("Country");

		assertEquals("Country", dictionaryModel.getName());
		assertEquals("de.pellepelster.myadmin.demo.client.entities.CountryVO", dictionaryModel.getVOName());

		// label controls
		assertEquals(2, dictionaryModel.getLabelControls().size());
		ITextControlModel label1 = (ITextControlModel) dictionaryModel.getLabelControls().get(0);
		assertEquals("CountryName", label1.getName());

		ITextControlModel label2 = (ITextControlModel) dictionaryModel.getLabelControls().get(1);
		assertEquals("CountryDescription", label2.getName());
		assertEquals("CountrySearch", dictionaryModel.getSearchModel().getName());

		// country editor
		assertEquals("CountrySearch", dictionaryModel.getSearchModel().getName());
		ISearchModel searchModel = dictionaryModel.getSearchModel();
		assertEquals(2, searchModel.getResultModel().getControls().size());

		// country editor
		assertEquals("CountryEditor", dictionaryModel.getEditorModel().getName());
		IEditorModel editorModel = dictionaryModel.getEditorModel();
		assertEquals(2, editorModel.getCompositeModel().getChildren().size());

		// composite3
		ICompositeModel composite3 = (ICompositeModel) editorModel.getCompositeModel().getChildren().get(0);
		assertEquals("Composite3", composite3.getName());
		assertEquals(3, composite3.getControls().size());

		ITextControlModel countryName = (ITextControlModel) composite3.getControls().get(0);
		assertEquals("CountryName", countryName.getName());
		assertEquals("countryName", countryName.getAttributePath());

		ITextControlModel countryDescription = (ITextControlModel) composite3.getControls().get(1);
		assertEquals("CountryDescription", countryDescription.getName());

		IDateControlModel countryFomation = (IDateControlModel) composite3.getControls().get(2);
		assertEquals("CountryFormation", countryFomation.getName());

		// composite4
		ICompositeModel composite4 = (ICompositeModel) editorModel.getCompositeModel().getChildren().get(1);
		assertEquals("Composite4", composite4.getName());
		assertEquals(4, composite4.getControls().size());

		IIntegerControlModel countryPopulation = (IIntegerControlModel) composite4.getControls().get(0);
		assertEquals("CountryPopulation", countryPopulation.getName());

		IBooleanControlModel countryEU = (IBooleanControlModel) composite4.getControls().get(1);
		assertEquals("CountryEU", countryEU.getName());

		IBigDecimalControlModel countryArea = (IBigDecimalControlModel) composite4.getControls().get(2);
		assertEquals("CountryArea", countryArea.getName());

		IEnumarationControlModel countryDrive = (IEnumarationControlModel) composite4.getControls().get(3);
		assertEquals("CountryDrive", countryDrive.getName());

	}

	@Test
	public void testRegion()
	{
		IDictionaryService dictionaryService = MyAdminRemoteServiceLocator.getInstance().getDictionaryService();
		IDictionaryModel dictionaryModel = dictionaryService.getDictionary("Region");

		assertNotNull(dictionaryModel);
	}

	@Test
	public void testUser()
	{
		IDictionaryService dictionaryService = MyAdminRemoteServiceLocator.getInstance().getDictionaryService();
		IDictionaryModel dictionaryModel = dictionaryService.getDictionary("MyAdminUser");

		assertEquals("MyAdminUser", dictionaryModel.getName());
		assertEquals("de.pellepelster.myadmin.client.entities.dictionary.MyAdminUserVO", dictionaryModel.getVOName());

		IEditorModel editorModel = dictionaryModel.getEditorModel();
		assertEquals("UserEditor", editorModel.getName());

		ICompositeModel userEditorComposite1 = (ICompositeModel) editorModel.getCompositeModel().getChildren().get(0);
		assertEquals("UserEditorComposite1", userEditorComposite1.getName());

		IAssignmentTableModel userGroupAssignment = (IAssignmentTableModel) editorModel.getCompositeModel().getChildren().get(1);
		assertEquals("UserGroupAssignment", userGroupAssignment.getName());
		assertEquals("MyAdminGroup", userGroupAssignment.getDictionaryName());

		assertEquals("GroupName", userGroupAssignment.getControls().get(0).getName());

	}
}
