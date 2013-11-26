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

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.entities.dictionary.CONTROL_TYPE;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;
import de.pellepelster.myadmin.dsl.myAdminDsl.BaseDictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryBigDecimalControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryBooleanControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControlGroup;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryDateControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEnumerationControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryIntegerControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryReferenceControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryTextControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.Labels;
import de.pellepelster.myadmin.dsl.util.ModelUtil;

/**
 * Factory for control creation
 * 
 * @author pelle
 * 
 */
public class ControlFactory
{

	private enum LABEL_TYPE
	{
		COLUM, FILTER, TOOLTP, STANDARD, EDITOR
	}

	private static ControlFactory instance;

	public static ControlFactory getInstance()
	{
		if (instance == null)
		{
			instance = new ControlFactory();
		}
		return instance;
	}

	private ControlFactory()
	{
	}

	private void createDictionaryBigDecimalControl(DictionaryBigDecimalControl dictionaryBigDecimalControl, DictionaryControlVO dictionaryControlVO,
			int logIdentiation)
	{
		createDictionaryControlCommon(dictionaryBigDecimalControl, dictionaryControlVO, logIdentiation + 1);

		if (dictionaryBigDecimalControl.getRef() != null)
		{
			createDictionaryBigDecimalControl(dictionaryBigDecimalControl.getRef(), dictionaryControlVO, logIdentiation);
		}
	}

	private void createDictionaryBooleanControl(DictionaryBooleanControl dictionaryBooleanControl, DictionaryControlVO dictionaryControlVO, int logIdentiation)
	{
		createDictionaryControlCommon(dictionaryBooleanControl, dictionaryControlVO, logIdentiation + 1);

		if (dictionaryBooleanControl.getRef() != null)
		{
			createDictionaryBooleanControl(dictionaryBooleanControl.getRef(), dictionaryControlVO, logIdentiation);
		}
	}

	private void createDictionaryControlCommon(DictionaryControl dictionaryControl, DictionaryControlVO dictionaryControlVO, int logIdentiation)
	{
		if (dictionaryControl.getBaseControl() != null && dictionaryControl.getBaseControl().getEntityattribute() != null)
		{
			dictionaryControlVO.setAttributePath(dictionaryControl.getBaseControl().getEntityattribute().getName());

			// ImportUtil.logInfo(String.format("xxx '%s'",
			// dictionaryControlVO.getAttributeName()), logIdentiation);
		}

		// labels
		if (dictionaryControlVO.getLabel() == null && getLabel(dictionaryControl, LABEL_TYPE.STANDARD) != null)
		{
			dictionaryControlVO.setLabel(getLabel(dictionaryControl, LABEL_TYPE.STANDARD));
		}

		if (dictionaryControlVO.getFilterLabel() == null && getLabel(dictionaryControl, LABEL_TYPE.FILTER) != null)
		{
			dictionaryControlVO.setFilterLabel(getLabel(dictionaryControl, LABEL_TYPE.FILTER));
		}
		//
		// if (dictionaryControlVO.getColumnLabel() == null &&
		// getLabel(dictionaryControl, LABEL_TYPE.COLUM) != null)
		// {
		// dictionaryControlVO.setColumnLabel(getLabel(dictionaryControl,
		// LABEL_TYPE.COLUM));
		// }
		//
		// if (dictionaryControlVO.getEditorLabel() == null &&
		// getLabel(dictionaryControl, LABEL_TYPE.EDITOR) != null)
		// {
		// dictionaryControlVO.setEditorLabel(getLabel(dictionaryControl,
		// LABEL_TYPE.EDITOR));
		// }
		//
		// if (dictionaryControlVO.getToolTip() == null &&
		// getLabel(dictionaryControl, LABEL_TYPE.TOOLTP) != null)
		// {
		// dictionaryControlVO.setToolTip(getLabel(dictionaryControl,
		// LABEL_TYPE.TOOLTP));
		// }

		if (dictionaryControl.getBaseControl() != null)
		{
			BaseDictionaryControl baseDictionaryControl = dictionaryControl.getBaseControl();

			if (dictionaryControlVO.getMandatory() == null && baseDictionaryControl.isMandatory())
			{
				dictionaryControlVO.setMandatory(baseDictionaryControl.isMandatory());
			}

			if (dictionaryControlVO.getReadOnly() == null && baseDictionaryControl.isReadonly())
			{
				dictionaryControlVO.setReadOnly(baseDictionaryControl.isReadonly());
			}

		}
	}

	private void createDictionaryControlGroup(DictionaryControlGroup dictionaryControlGroup, DictionaryControlVO dictionaryControlVO, int logIdentiation)
	{
		createDictionaryControlCommon(dictionaryControlGroup, dictionaryControlVO, logIdentiation + 1);

		if (dictionaryControlGroup.getRef() != null)
		{
			createDictionaryControlGroup(dictionaryControlGroup.getRef(), dictionaryControlVO, logIdentiation);
		}
	}

	public DictionaryControlVO createDictionaryControlVO(DictionaryControl dictionaryControl, int logIdentiation)
	{

		DictionaryControlVO dictionaryControlVO = new DictionaryControlVO();

		String controlName = ModelUtil.getControlName(dictionaryControl);
		ToolUtils.logInfo(DictionaryImportRunner.LOG, String.format("creating control '%s'", controlName), logIdentiation);
		dictionaryControlVO.setName(controlName);

		if (dictionaryControl instanceof DictionaryTextControl)
		{
			createDictionaryTextControl((DictionaryTextControl) dictionaryControl, dictionaryControlVO, logIdentiation);
		}
		else if (dictionaryControl instanceof DictionaryIntegerControl)
		{
			createDictionaryIntegerControl((DictionaryIntegerControl) dictionaryControl, dictionaryControlVO, logIdentiation);
		}
		else if (dictionaryControl instanceof DictionaryBigDecimalControl)
		{
			createDictionaryBigDecimalControl((DictionaryBigDecimalControl) dictionaryControl, dictionaryControlVO, logIdentiation);
		}
		else if (dictionaryControl instanceof DictionaryEnumerationControl)
		{
			createDictionaryEnumerationControl((DictionaryEnumerationControl) dictionaryControl, dictionaryControlVO, logIdentiation);
		}
		else if (dictionaryControl instanceof DictionaryControlGroup)
		{
			createDictionaryControlGroup((DictionaryControlGroup) dictionaryControl, dictionaryControlVO, logIdentiation);
		}
		else if (dictionaryControl instanceof DictionaryDateControl)
		{
			createDictionaryDateControl((DictionaryDateControl) dictionaryControl, dictionaryControlVO, logIdentiation);
		}
		else if (dictionaryControl instanceof DictionaryReferenceControl)
		{
			createDictionaryReferenceControl((DictionaryReferenceControl) dictionaryControl, dictionaryControlVO, logIdentiation);
		}
		else if (dictionaryControl instanceof DictionaryBooleanControl)
		{
			createDictionaryBooleanControl((DictionaryBooleanControl) dictionaryControl, dictionaryControlVO, logIdentiation);
		}
		else
		{
			throw new RuntimeException(String.format("unsupported control type '%s'", dictionaryControl.getClass().getName()));
		}

		// mandatory default and logging
		if (dictionaryControlVO.getMandatory() == null)
		{
			dictionaryControlVO.setMandatory(false);
		}

		ToolUtils.logInfo(DictionaryImportRunner.LOG, String.format("mandatory: %s", dictionaryControlVO.getMandatory().toString()), logIdentiation + 1);

		String labelLogMessage = "";
		labelLogMessage += (dictionaryControlVO.getLabel() != null ? labelLogMessage = dictionaryControlVO.getLabel() : "-");
		labelLogMessage += "/" + (dictionaryControlVO.getFilterLabel() != null ? labelLogMessage = dictionaryControlVO.getFilterLabel() : "-");
		labelLogMessage += "/" + (dictionaryControlVO.getColumnLabel() != null ? labelLogMessage = dictionaryControlVO.getColumnLabel() : "-");
		labelLogMessage += "/" + (dictionaryControlVO.getEditorLabel() != null ? labelLogMessage = dictionaryControlVO.getEditorLabel() : "-");
		labelLogMessage += "/" + (dictionaryControlVO.getToolTip() != null ? labelLogMessage = dictionaryControlVO.getToolTip() : "-");
		ToolUtils.logInfo(DictionaryImportRunner.LOG, String.format("labels: %s", labelLogMessage), logIdentiation + 1);

		// datatype
		dictionaryControlVO.setDatatype(DatatypeFactory.getInstance().createDatatypeVO(dictionaryControl, logIdentiation + 1));

		return dictionaryControlVO;
	}

	private void createDictionaryDateControl(DictionaryDateControl dictionaryDateControl, DictionaryControlVO dictionaryControlVO, int logIdentiation)
	{
		createDictionaryControlCommon(dictionaryDateControl, dictionaryControlVO, logIdentiation + 1);

		if (dictionaryDateControl.getRef() != null)
		{
			createDictionaryDateControl(dictionaryDateControl.getRef(), dictionaryControlVO, logIdentiation);
		}
	}

	private void createDictionaryEnumerationControl(DictionaryEnumerationControl dictionaryEnumerationControl, DictionaryControlVO dictionaryControlVO,
			int logIdentiation)
	{
		createDictionaryControlCommon(dictionaryEnumerationControl, dictionaryControlVO, logIdentiation + 1);

		if (dictionaryEnumerationControl.getRef() != null)
		{
			createDictionaryEnumerationControl(dictionaryEnumerationControl.getRef(), dictionaryControlVO, logIdentiation);
		}
	}

	private void createDictionaryIntegerControl(DictionaryIntegerControl dictionaryIntegerControl, DictionaryControlVO dictionaryControlVO, int logIdentiation)
	{
		createDictionaryControlCommon(dictionaryIntegerControl, dictionaryControlVO, logIdentiation + 1);

		if (dictionaryIntegerControl.getRef() != null)
		{
			createDictionaryIntegerControl(dictionaryIntegerControl.getRef(), dictionaryControlVO, logIdentiation);
		}
	}

	private void createDictionaryReferenceControl(DictionaryReferenceControl dictionaryReferenceControl, DictionaryControlVO dictionaryControlVO,
			int logIdentiation)
	{

		if (dictionaryReferenceControl.getDictionary() != null)
		{
			dictionaryControlVO.setDictionary(dictionaryReferenceControl.getDictionary().getName());
		}

		switch (dictionaryReferenceControl.getControlType())
		{
			case DROPDOWN:
				dictionaryControlVO.setControlType(CONTROL_TYPE.DROPDOWN);
				break;
			default:
				dictionaryControlVO.setControlType(CONTROL_TYPE.TEXT);
				break;

		}

		createDictionaryControlCommon(dictionaryReferenceControl, dictionaryControlVO, logIdentiation + 1);

		if (dictionaryControlVO.getLabelControls().isEmpty())
		{
			List<DictionaryControl> dictionaryControls = new ArrayList<DictionaryControl>();

			if (dictionaryReferenceControl.getLabelcontrols().isEmpty() && dictionaryReferenceControl.getDictionary() != null)
			{
				dictionaryControls = dictionaryReferenceControl.getDictionary().getLabelcontrols();
			}
			else if (!dictionaryReferenceControl.getLabelcontrols().isEmpty())
			{
				dictionaryControls = dictionaryReferenceControl.getLabelcontrols();
			}

			// control labels
			for (DictionaryControl dictionaryLabelControl : dictionaryControls)
			{
				dictionaryControlVO.getLabelControls().add(createDictionaryControlVO(dictionaryLabelControl, logIdentiation + 1));
			}
		}

		if (dictionaryReferenceControl.getRef() != null)
		{
			createDictionaryReferenceControl(dictionaryReferenceControl.getRef(), dictionaryControlVO, logIdentiation);
		}

	}

	private void createDictionaryTextControl(DictionaryTextControl dictionaryTextControl, DictionaryControlVO dictionaryControlVO, int logIdentiation)
	{

		createDictionaryControlCommon(dictionaryTextControl, dictionaryControlVO, logIdentiation + 1);

		if (dictionaryTextControl.getRef() != null)
		{
			createDictionaryTextControl(dictionaryTextControl.getRef(), dictionaryControlVO, logIdentiation);
		}
	}

	private String getLabel(DictionaryControl dictionaryControl, LABEL_TYPE labelType)
	{
		if (dictionaryControl.getBaseControl() != null && dictionaryControl.getBaseControl().getLabels() != null)
		{
			String label = getLabel(dictionaryControl.getBaseControl().getLabels(), labelType);

			if (label == null || label.isEmpty())
			{
				Datatype datatype = DatatypeFactory.getDatatypeType(dictionaryControl, false);

				if (datatype != null && datatype.getBaseDatatype() != null && datatype.getBaseDatatype().getLabels() != null)
				{
					label = getLabel(datatype.getBaseDatatype().getLabels(), labelType);
				}
			}

			return label;
		}
		else
		{
			return null;
		}

	}

	private String getLabel(Labels labels, LABEL_TYPE labelType)
	{
		switch (labelType)
		{
			case COLUM:
				return labels.getColumnLabel();
			case FILTER:
				return labels.getFilterLabel();
			case TOOLTP:
				return labels.getToolTip();
			case STANDARD:
				return labels.getLabel();
			case EDITOR:
				return labels.getLabel();
			default:
				throw new RuntimeException(String.format("unsupported labeltype '%s'", labelType));
		}

	}

}
