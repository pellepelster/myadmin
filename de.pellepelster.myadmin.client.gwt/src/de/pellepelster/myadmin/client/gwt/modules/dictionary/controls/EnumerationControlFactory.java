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

import java.util.List;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.EnumerationControl;

/**
 * control factory for enumeration controls
 * 
 * @author pelle
 * 
 */
public class EnumerationControlFactory extends BaseControlFactory<IEnumerationControlModel, EnumerationControl>
{

	/** {@inheritDoc} */
	@Override
	public Widget createControl(EnumerationControl enumerationControl, LAYOUT_TYPE layoutType)
	{
		return new GwtEnumerationControl(enumerationControl);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseControl baseControl)
	{
		return baseControl instanceof EnumerationControl;
	}

	@Override
	public Column createColumn(final EnumerationControl enumerationControl, boolean editable, ListDataProvider<?> listDataProvider,
			AbstractCellTable<?> abstractCellTable)
	{

		if (editable)
		{
			List<String> enumList = GwtEnumerationControl.getSortedEnumList(enumerationControl.getModel());
			final SelectionCell selectionCell = new SelectionCell(enumList);

			Column<IBaseVO, String> column = new Column<IBaseVO, String>(selectionCell)
			{

				@Override
				public String getValue(IBaseVO vo)
				{
					Object enumValue = vo.get(enumerationControl.getModel().getAttributePath());
					if (enumValue == null)
					{
						return "";
					}
					else
					{
						return (String) enumerationControl.getModel().getEnumeration().get(vo.get(enumerationControl.getModel().getAttributePath()).toString());
					}
				}
			};

			FieldUpdater<IBaseVO, String> fieldUpdater = new FieldUpdater<IBaseVO, String>()
			{
				@Override
				public void update(int index, IBaseVO vo, String value)
				{
					vo.set(enumerationControl.getModel().getAttributePath(), GwtEnumerationControl.getEnumForText(enumerationControl.getModel(), value));
				}
			};
			column.setFieldUpdater(fieldUpdater);

			return column;
		}
		else
		{
			return super.createColumn(enumerationControl, editable, listDataProvider, abstractCellTable);
		}
	}

}
