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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import com.google.gwt.cell.client.Cell.Context;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.EditableTable;
import de.pellepelster.myadmin.client.web.MyAdmin;

public class ControlUtil
{

	public static boolean hasFirstEditMarker(Context context, IBaseControlModel baseControlModel)
	{
		if (context.getKey() == null || !(context.getKey() instanceof IBaseVO))
		{
			throw new RuntimeException("context key is null or not IBaseVO");
		}

		return hasFirstEditMarker((IBaseVO) context.getKey(), baseControlModel);
	}

	public static String getLabel(LAYOUT_TYPE layoutType, IBaseControlModel baseControlModel)
	{
		String label = baseControlModel.getEditorLabel();

		switch (layoutType)
		{
			case EDITOR:
				if (baseControlModel.isMandatory())
				{
					label += MyAdmin.MESSAGES.mandatoryMarker();
				}
				break;
		}

		return label;
	}

	public static boolean hasFirstEditMarker(IBaseVO vo, IBaseControlModel baseControlModel)
	{
		return vo.getData().containsKey(baseControlModel.getName())
				&& vo.getData().get(baseControlModel.getName()).equals(EditableTable.CONTROL_FIRST_EDIT_DATA_KEY);
	}

	public static void removeFirstEditMarker(Context context, IBaseControlModel baseControlModel)
	{
		if (context.getKey() == null || !(context.getKey() instanceof IBaseVO))
		{
			throw new RuntimeException("context key is null or not IBaseVO");
		}

		removeFirstEditMarker((IBaseVO) context.getKey(), baseControlModel);
	}

	public static void removeFirstEditMarker(IBaseVO vo, IBaseControlModel baseControlModel)
	{
		vo.getData().remove(baseControlModel.getName());
	}

}
