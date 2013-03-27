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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumarationControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IControl;

/**
 * control factory for enumeration controls
 * 
 * @author pelle
 * 
 */
public class EnumerationControlFactory extends BaseControlFactory<IEnumarationControlModel>
{

	/** {@inheritDoc} */
	@Override
	public IControl<Widget> createControl(IEnumarationControlModel controlModel, LAYOUT_TYPE layoutType)
	{
		return new EnumerationControl(controlModel);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(IBaseControlModel baseControlModel)
	{
		return baseControlModel instanceof IEnumarationControlModel;
	}

	@Override
	public Column createColumn(final IEnumarationControlModel controlModel, boolean editable, ListDataProvider<?> listDataProvider,
			AbstractCellTable<?> abstractCellTable)
	{

		if (editable)
		{
			List<String> enumList = EnumerationControl.getSortedEnumList(controlModel);
			final SelectionCell selectionCell = new SelectionCell(enumList);

			Column<IBaseVO, String> column = new Column<IBaseVO, String>(selectionCell)
			{

				@Override
				public String getValue(IBaseVO vo)
				{
					Object enumValue = vo.get(controlModel.getAttributePath());
					if (enumValue == null)
					{
						return "";
					}
					else
					{
						return (String) controlModel.getEnumeration().get(vo.get(controlModel.getAttributePath()).toString());
					}
				}
			};

			FieldUpdater<IBaseVO, String> fieldUpdater = new FieldUpdater<IBaseVO, String>()
			{
				@Override
				public void update(int index, IBaseVO vo, String value)
				{
					vo.set(controlModel.getAttributePath(), EnumerationControl.getEnumForText(controlModel, value));
				}
			};
			column.setFieldUpdater(fieldUpdater);

			return column;
		}
		else
		{
			return super.createColumn(controlModel, editable, listDataProvider, abstractCellTable);
		}
	}

}
