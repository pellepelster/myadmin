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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelProvider;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
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
		String title = getLabel(dictionaryModel.getEditorModel().getLabel(), dictionaryModel.getLabel());

		return MyAdmin.MESSAGES.dictionaryAdd(title);

	}

	public static String getEditorLabel(IDictionaryModel dictionaryModel, DictionaryEditor<?> dictionaryEditor)
	{

		IBaseVO vo = dictionaryEditor.getVO();

		String label = getLabel(dictionaryModel.getEditorModel().getLabel(), dictionaryModel.getLabel());

		if (vo == null || vo.isNew())
		{
			label += " (" + MyAdmin.MESSAGES.editorNew() + ")";
		}
		else
		{
			label += " " + getLabel(dictionaryModel.getLabelControls(), vo, Long.toString(vo.getId()));
		}

		if (dictionaryEditor.isDirty())
		{
			label += " " + MyAdmin.MESSAGES.editorDirtyMarker();
		}

		return label;

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
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionaryModelForClass(vo.getClass());

		return getLabel(dictionaryModel.getLabelControls(), vo, defaultLabel);
	}

	public static String getLabel(IReferenceControlModel referenceControlModel, IBaseVO vo, String defaultLabel)
	{
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(referenceControlModel.getDictionaryName());

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
		return getSearchLabel(dictionaryModel, 0);
	}

	public static String getSearchLabel(IDictionaryModel dictionaryModel, int resultCount)
	{

		String label = getLabel(dictionaryModel.getSearchModel().getLabel(), dictionaryModel.getLabel());

		label += " (" + MyAdmin.MESSAGES.searchResults(resultCount) + ")";

		return label;
	}

	public static String getLabel(String label, String defaultLabel)
	{

		if (label != null && !label.trim().isEmpty())
		{
			return label;
		}
		else if (defaultLabel != null && !defaultLabel.trim().isEmpty())
		{
			return defaultLabel;
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
