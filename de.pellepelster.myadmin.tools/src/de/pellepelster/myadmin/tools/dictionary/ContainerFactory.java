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
package de.pellepelster.myadmin.tools.dictionary;

import org.eclipse.emf.common.util.EList;

import de.pellepelster.myadmin.client.base.entities.dictionary.DICTIONARY_CONTAINER_TYPE;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.dsl.generator.Extensions;
import de.pellepelster.myadmin.dsl.myAdminDsl.ColumnLayout;
import de.pellepelster.myadmin.dsl.myAdminDsl.ColumnLayoutData;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryAssignmentTable;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryComposite;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryContainer;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryContainerContent;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEditableTable;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityReferenceType;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityType;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModelScope;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatypeType;

/**
 * Factory for container creation
 * 
 * @author pelle
 * 
 */
public class ContainerFactory
{
	private static ContainerFactory instance;

	public static ContainerFactory getInstance()
	{
		if (instance == null)
		{
			instance = new ContainerFactory();
		}

		return instance;
	}

	private ContainerFactory()
	{
	}

	public void createContainer(DictionaryContainerVO parentDictionaryContainerVO, DictionaryContainer parentDictionaryContainer, int logIdentiation)
	{
		createContainer(parentDictionaryContainerVO, parentDictionaryContainer.getContainercontents(), logIdentiation);
	}
	
	public void createContainer(DictionaryContainerVO parentDictionaryContainerVO,  EList<DictionaryContainerContent>  dictionaryContainerContents, int logIdentiation)
	{

		for (DictionaryContainerContent dictionaryContainerContent : dictionaryContainerContents)
		{
			if (dictionaryContainerContent instanceof DictionaryContainer)
			{
				DictionaryContainer dictionaryContainer = (DictionaryContainer) dictionaryContainerContent;

				DictionaryContainerVO dictionaryContainerVO = createDictionaryContainerVO(dictionaryContainer, logIdentiation);
				parentDictionaryContainerVO.getChildren().add(dictionaryContainerVO);

				createContainer(dictionaryContainerVO, dictionaryContainer, logIdentiation + 1);

			}
			else if (dictionaryContainerContent instanceof DictionaryControl)
			{
				DictionaryControl dictionaryControl = (DictionaryControl) dictionaryContainerContent;

				DictionaryControlVO dictionaryControlVO = ControlFactory.getInstance().createDictionaryControlVO(dictionaryControl, logIdentiation + 1);
				parentDictionaryContainerVO.getControls().add(dictionaryControlVO);
			}
			else
			{
				throw new RuntimeException(String.format("unsupported dictionary container content '%s'", dictionaryContainerContent.getClass()));
			}
		}
	}

	private void createDictionaryAssignmentTable(DictionaryAssignmentTable dictionaryAssignmentTable, DictionaryContainerVO dictionaryContainerVO,
			int logIdentiation)
	{

		ToolUtils.logInfo(DictionaryImportRunner.LOG, String.format("creating assignment table '%s'", dictionaryAssignmentTable.getName()), logIdentiation);

		dictionaryContainerVO.setType(DICTIONARY_CONTAINER_TYPE.ASSIGNMENT_TABLE);
		dictionaryContainerVO.setAttributePath(dictionaryAssignmentTable.getEntityattribute().getName());
		dictionaryContainerVO.setDictionaryName(dictionaryAssignmentTable.getDictionary().getName());

		for (DictionaryControl dictionaryControl : dictionaryAssignmentTable.getColumncontrols())
		{
			DictionaryControlVO dictionaryControlVO = ControlFactory.getInstance().createDictionaryControlVO(dictionaryControl, logIdentiation + 1);
			dictionaryContainerVO.getControls().add(dictionaryControlVO);
		}

	}

	private void createDictionaryComposite(DictionaryComposite dictionaryComposite, DictionaryContainerVO dictionaryContainerVO, int logIdentiation)
	{
		dictionaryContainerVO.setType(DICTIONARY_CONTAINER_TYPE.COMPOSITE);

		ToolUtils.logInfo(DictionaryImportRunner.LOG, String.format("creating composite '%s'", dictionaryComposite.getName()), logIdentiation);

	}

	private DictionaryContainerVO createDictionaryContainerVO(DictionaryContainer dictionaryContainer, int logIdentiation)
	{

		DictionaryContainerVO dictionaryContainerVO = new DictionaryContainerVO();
		dictionaryContainerVO.setName(dictionaryContainer.getName());

		if (dictionaryContainer.getLayout() != null)
		{
			ColumnLayout columnLayout = dictionaryContainer.getLayout();
			dictionaryContainerVO.setColumns(columnLayout.getColumns());
		}

		if (dictionaryContainer.getLayoutdata() != null)
		{
			ColumnLayoutData columnLayoutData = dictionaryContainer.getLayoutdata();
			dictionaryContainerVO.setColumnspan(columnLayoutData.getColumnspan());
		}

		if (dictionaryContainer instanceof DictionaryComposite)
		{
			createDictionaryComposite((DictionaryComposite) dictionaryContainer, dictionaryContainerVO, logIdentiation + 1);
		}
		else if (dictionaryContainer instanceof DictionaryEditableTable)
		{
			createDictionaryEditableTable((DictionaryEditableTable) dictionaryContainer, dictionaryContainerVO, logIdentiation + 1);
		}
		else if (dictionaryContainer instanceof DictionaryAssignmentTable)
		{
			createDictionaryAssignmentTable((DictionaryAssignmentTable) dictionaryContainer, dictionaryContainerVO, logIdentiation + 1);
		}
		else
		{
			throw new RuntimeException(String.format("unsupported container type '%s'", dictionaryContainer.getClass().getName()));
		}

		return dictionaryContainerVO;
	}

	private void createDictionaryEditableTable(DictionaryEditableTable dictionaryEditableTable, DictionaryContainerVO dictionaryContainerVO, int logIdentiation)
	{

		ToolUtils.logInfo(DictionaryImportRunner.LOG, String.format("creating editable table '%s'", dictionaryEditableTable.getName()), logIdentiation);

		dictionaryContainerVO.setType(DICTIONARY_CONTAINER_TYPE.EDITABLE_TABLE);
		dictionaryContainerVO.setAttributePath(dictionaryEditableTable.getEntityattribute().getName());

		Entity entity = getEntity(dictionaryEditableTable.getEntityattribute());
		String fullQualifiedVoName = Extensions.fullQualifiedEntityName(entity, ModelScope.WEB);
		dictionaryContainerVO.setEntityName(fullQualifiedVoName);

		for (DictionaryControl dictionaryControl : dictionaryEditableTable.getColumncontrols())
		{
			DictionaryControlVO dictionaryControlVO = ControlFactory.getInstance().createDictionaryControlVO(dictionaryControl, logIdentiation + 1);
			dictionaryContainerVO.getControls().add(dictionaryControlVO);
		}
	}

	private Entity getEntity(EntityAttribute entityAttribute)
	{
		if (entityAttribute.getType() instanceof EntityReferenceType)
		{
			EntityReferenceType entityReferenceType = (EntityReferenceType) entityAttribute.getType();

			if (entityReferenceType instanceof EntityType)
			{
				EntityType entityType = (EntityType) entityReferenceType;
				return entityType.getType();
			}
			else if (entityReferenceType instanceof ReferenceDatatypeType)
			{
				ReferenceDatatypeType referenceDatatypeType = (ReferenceDatatypeType) entityReferenceType;

				return referenceDatatypeType.getType().getEntity();
			}
		}

		throw new RuntimeException(String.format("no entity associated with entity attribute '%s' of type '%s'", entityAttribute.getName(), entityAttribute
				.getClass().getName()));
	}

}
