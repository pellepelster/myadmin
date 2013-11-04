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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SingleSelectionModel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.BaseTableRowKeyProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.layout.WidthCalculationStrategy;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public abstract class BaseCellTable<VOType extends IBaseVO> extends CellTable<IBaseTable.ITableRow<VOType>>
{
	public static BaseTableRowKeyProvider KEYPROVIDER = new BaseTableRowKeyProvider();

	private ListDataProvider<IBaseTable.ITableRow<VOType>> dataProvider = new ListDataProvider<IBaseTable.ITableRow<VOType>>();

	private final SingleSelectionModel<IBaseTable.ITableRow<VOType>> selectionModel = new SingleSelectionModel<IBaseTable.ITableRow<VOType>>(KEYPROVIDER);

	private List<BaseDictionaryControl<? extends IBaseControlModel, ?>> baseControls;

	public static final String DEFAULT_TABLE_HEIGHT = "15em";

	public static final String DEFAULT_TABLE_WIDTH = "5em";

	public static final int DEFAULT_MAX_RESULTS = 15;

	public BaseCellTable(List<BaseDictionaryControl<? extends IBaseControlModel, ?>> baseControls)
	{
		super(KEYPROVIDER);
		dataProvider.addDataDisplay(this);
		this.baseControls = baseControls;
	}

	protected void createModelColumns()
	{
		for (BaseDictionaryControl<? extends IBaseControlModel, ?> baseControl : baseControls)
		{
			TextHeader textHeader = new TextHeader(baseControl.getModel().getColumnLabel());
			Column column = getColumn(baseControl);
			setColumnWidth(column, WidthCalculationStrategy.getInstance().getControlColumnWidth(baseControl.getModel()), Unit.PX);

			addColumn(column, textHeader);
		}

		setSelectionModel(selectionModel);
	}

	public void setRows(List<IBaseTable.ITableRow<VOType>> rows)
	{
		dataProvider.setList(rows);
	}

	protected IBaseTable.ITableRow<VOType> getSelection()
	{
		return selectionModel.getSelectedObject();
	}

	public void addVOActivationHandler(final SimpleCallback<IBaseTable.ITableRow<VOType>> voActivationHandler)
	{
		addDomHandler(new DoubleClickHandler()
		{

			/** {@inheritDoc} */
			@Override
			public void onDoubleClick(DoubleClickEvent event)
			{

				if (selectionModel.getSelectedObject() != null)
				{
					voActivationHandler.onCallback(selectionModel.getSelectedObject());
				}
			}
		}, DoubleClickEvent.getType());

	}

	protected abstract Column<VOType, ?> getColumn(BaseDictionaryControl<? extends IBaseControlModel, ?> baseControl);

}
