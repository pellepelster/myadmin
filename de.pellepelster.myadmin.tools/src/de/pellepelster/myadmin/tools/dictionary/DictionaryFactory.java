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

import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryEditorVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryVO;
import de.pellepelster.myadmin.dsl.generator.Extensions;
import de.pellepelster.myadmin.dsl.myAdminDsl.Dictionary;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEditor;
import de.pellepelster.myadmin.dsl.myAdminDsl.ModelScope;

/**
 * Factory for dictionary creation
 * 
 * @author pelle
 * 
 */
public class DictionaryFactory
{

	public static DictionaryVO createDictionary(Dictionary dictionary, int logIdentiation)
	{

		String fullQualifiedEntityName = Extensions.fullQualifiedEntityName(dictionary.getEntity(), ModelScope.WEB);

		ToolUtils.logInfo(DictionaryImportRunner.LOGGER, String.format("creating dictionary '%s' (%s)", dictionary.getName(), fullQualifiedEntityName),
				logIdentiation);

		DictionaryVO dictionaryVO = new DictionaryVO();
		dictionaryVO.setName(dictionary.getName());
		dictionaryVO.setEntityName(fullQualifiedEntityName);
		dictionaryVO.setTitle(dictionary.getTitle());
		dictionaryVO.setLabel(dictionary.getLabel());
		dictionaryVO.setPluralLabel(dictionary.getPluralLabel());

		ToolUtils.logInfo(DictionaryImportRunner.LOGGER, String.format("creating dictionary labels"), logIdentiation + 1);

		for (DictionaryControl dictionaryControl : dictionary.getLabelcontrols())
		{
			DictionaryControlVO dictionaryControlVO = ControlFactory.getInstance().createDictionaryControlVO(dictionaryControl, logIdentiation + 2);
			dictionaryVO.getLabelControls().add(dictionaryControlVO);
		}

		return dictionaryVO;
	}

	public static DictionaryEditorVO createEditor(DictionaryEditor dictionaryEditor, int logIdentiation)
	{

		DictionaryEditorVO dictionaryEditorVO = new DictionaryEditorVO();
		dictionaryEditorVO.setName(dictionaryEditor.getName());
		dictionaryEditorVO.setTitle(dictionaryEditor.getTitle());

		ToolUtils.logInfo(DictionaryImportRunner.LOGGER, String.format("creating editor '%s'", dictionaryEditorVO.getName()), logIdentiation);

		DictionaryContainerVO editorRootCompositeVO = new DictionaryContainerVO();
		ContainerFactory.getInstance().createContainer(editorRootCompositeVO, dictionaryEditor.getContainercontents(), logIdentiation + 1);

		dictionaryEditorVO.setContainer(editorRootCompositeVO);

		return dictionaryEditorVO;
	}

	private DictionaryFactory()
	{
	}

}
