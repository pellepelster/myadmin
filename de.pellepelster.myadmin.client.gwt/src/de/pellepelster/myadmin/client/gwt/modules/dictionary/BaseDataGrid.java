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
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.TableUpdateListener;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseTableModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.BaseTableRowKeyProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseTableElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public abstract class BaseDataGrid<VOType extends IBaseVO> extends DataGrid<IBaseTable.ITableRow<VOType>>
{
	private ListDataProvider<IBaseTable.ITableRow<VOType>> dataProvider = new ListDataProvider<IBaseTable.ITableRow<VOType>>();

	private final SingleSelectionModel<IBaseTable.ITableRow<VOType>> selectionModel;

	private List<BaseDictionaryControl<?, ?>> baseControls;

	public BaseDataGrid(final BaseTableElement<VOType, ? extends IBaseTableModel> baseTable)
	{
		this(baseTable.getControls());

		baseTable.addTableUpdateListeners(new TableUpdateListener()
		{
			@Override
			public void onUpdate()
			{
				dataProvider.setList(baseTable.getRows());
			}
		});
	}

	public BaseDataGrid(List<BaseDictionaryControl<?, ?>> baseControls)
	{
		super(new BaseTableRowKeyProvider<VOType>());

		selectionModel = new SingleSelectionModel<IBaseTable.ITableRow<VOType>>(getKeyProvider());
		this.baseControls = baseControls;
		dataProvider.addDataDisplay(this);
	}

	protected void createModelColumns()
	{
		for (BaseDictionaryControl<?, ?> baseControl : baseControls)
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

	public ListDataProvider<IBaseTable.ITableRow<VOType>> getDataProvider()
	{
		return dataProvider;
	}

	protected abstract Column<IBaseTable.ITableRow<VOType>, ?> getColumn(BaseDictionaryControl baseControl);

	public void setRows(List<ITableRow<VOType>> rows)
	{
		dataProvider.setList(rows);
	}

}
