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
package de.pellepelster.myadmin.client.gwt.modules.dictionary;

import java.util.List;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.view.client.SingleSelectionModel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.BaseTableRowKeyProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public abstract class BaseDataGrid<VOType extends IBaseVO> extends DataGrid<IBaseTable.ITableRow<VOType>>
{
	public static BaseTableRowKeyProvider KEYPROVIDER = new BaseTableRowKeyProvider();

	private final SingleSelectionModel<IBaseTable.ITableRow<VOType>> selectionModel = new SingleSelectionModel<IBaseTable.ITableRow<VOType>>(KEYPROVIDER);

	protected abstract Column<IBaseTable.ITableRow<VOType>, ?> getColumn(BaseControl baseControl);

	private List<BaseControl<?, ?>> baseControls;

	public BaseDataGrid(List<BaseControl<?, ?>> baseControls)
	{
		super(KEYPROVIDER);
		this.baseControls = baseControls;
	}

	protected void createModelColumns()
	{
		for (BaseControl baseControl : baseControls)
		{
			TextHeader textHeader = new TextHeader(baseControl.getModel().getColumnLabel());
			addColumn(getColumn(baseControl), textHeader);
		}

		setSelectionModel(selectionModel);
	}

	public void addVOSelectHandler(final SimpleCallback<IBaseTable.ITableRow<VOType>> voSelectHandler)
	{
		addDomHandler(new DoubleClickHandler()
		{

			/** {@inheritDoc} */
			@Override
			public void onDoubleClick(DoubleClickEvent event)
			{

				if (selectionModel.getSelectedObject() != null)
				{
					voSelectHandler.onCallback(selectionModel.getSelectedObject());
				}
			}
		}, DoubleClickEvent.getType());

	}

	public IBaseTable.ITableRow<VOType> getCurrentSelection()
	{
		return selectionModel.getSelectedObject();
	}

}
