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
package de.pellepelster.myadmin.client.web.modules.dictionary.base;

import java.util.List;

import com.google.gwt.core.client.GWT;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ControlContentPresenter;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditor;

/**
 * Utilities for dictionary model handling
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public final class DictionaryUtil
{

	public static String getDictionaryAdd(IDictionaryModel dictionaryModel)
	{
		String title = getTitle(dictionaryModel.getEditorModel().getTitle(), dictionaryModel.getTitle());

		return MyAdmin.MESSAGES.dictionaryAdd(title);

	}

	public static String getEditorTitle(IDictionaryModel dictionaryModel, DictionaryEditor<?> dictionaryEditor)
	{

		IBaseVO vo = dictionaryEditor.getVO();

		String title = getTitle(dictionaryModel.getEditorModel().getTitle(), dictionaryModel.getTitle());

		if (vo == null || vo.isNew())
		{
			title += " (" + MyAdmin.MESSAGES.editorNew() + ")";
		}
		else
		{
			title += " " + getLabel(dictionaryModel.getLabelControls(), vo, Long.toString(vo.getId()));
		}

		if (dictionaryEditor.isDirty())
		{
			title += " " + MyAdmin.MESSAGES.editorDirtyMarker();
		}

		return title;

	}

	public static String getLabel(IReferenceControlModel referenceControlModel, IBaseVO vo)
	{
		String defaultLabel = "<none>";

		if (vo != null)
		{
			defaultLabel = vo.toString();
		}

		return getLabel(referenceControlModel, vo, defaultLabel);
	}

	public static String getLabel(IHierarchicalControlModel hierarchicalControlModel, IHierarchicalVO vo, String defaultLabel)
	{
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getCachedDictionaryModelForClass(vo.getClass());

		return getLabel(dictionaryModel.getLabelControls(), vo, defaultLabel);
	}

	public static String getLabel(IReferenceControlModel referenceControlModel, IBaseVO vo, String defaultLabel)
	{
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getCachedDictionaryModel(referenceControlModel.getDictionaryName());

		List<IBaseControlModel> labelControlModels = DictionaryModelUtil.getLabelControlsWithFallback(referenceControlModel, dictionaryModel);

		return DictionaryUtil.getLabel(labelControlModels, vo, defaultLabel);
	}

	public static String getLabel(List<IBaseControlModel> labelControlModels, IBaseVO vo)
	{
		return getLabel(labelControlModels, vo, null);
	};

	public static String getLabel(List<IBaseControlModel> baseControlModels, IBaseVO vo, String defaultLabel)
	{

		String label = null;
		String delimiter = "";

		if (vo != null)
		{
			for (IBaseControlModel baseControlModel : baseControlModels)
			{
				if (label == null)
				{
					label = "";
				}
				try
				{
					String controlLabel = ControlContentPresenter.getControlContent(baseControlModel, vo.get(baseControlModel.getAttributePath()));
					if (controlLabel != null && !controlLabel.isEmpty())
					{
						label += delimiter + controlLabel;
						delimiter = ", ";
					}
				}
				catch (RuntimeException e)
				{
					GWT.log(e.getMessage(), e);
				}
			}
		}

		return (label == null) ? defaultLabel : label;

	}

	public static String getSearchTitle(IDictionaryModel dictionaryModel)
	{
		return getSearchTitle(dictionaryModel, 0);
	}

	public static String getSearchTitle(IDictionaryModel dictionaryModel, int resultCount)
	{

		String title = getTitle(dictionaryModel.getSearchModel().getTitle(), dictionaryModel.getTitle());

		title += " (" + MyAdmin.MESSAGES.searchResults(resultCount) + ")";

		return title;
	}

	public static String getTitle(String title, String defaultTitle)
	{

		if (title != null && !title.trim().isEmpty())
		{
			return title;
		}
		else if (defaultTitle != null && !defaultTitle.trim().isEmpty())
		{
			return defaultTitle;
		}
		else
		{
			return "<no title>";
		}

	}

	private DictionaryUtil()
	{
		super();
	}

}
