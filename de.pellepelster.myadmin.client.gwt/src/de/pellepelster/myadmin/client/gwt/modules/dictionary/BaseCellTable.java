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
import com.google.gwt.view.client.SingleSelectionModel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.BaseVOKeyProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.layout.WidthCalculationStrategy;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseCellTable<VOType extends IBaseVO> extends CellTable<VOType>
{
	public static BaseVOKeyProvider KEYPROVIDER = new BaseVOKeyProvider();

	private final SingleSelectionModel<VOType> selectionModel = new SingleSelectionModel<VOType>(KEYPROVIDER);

	protected abstract Column<VOType, ?> getColumn(IBaseControlModel baseControlModel);

	private List<IBaseControlModel> baseControlModels;

	public static final String DEFAULT_TABLE_HEIGHT = "15em";

	public static final String DEFAULT_TABLE_WIDTH = "5em";

	public static final int DEFAULT_MAX_RESULTS = 15;

	public BaseCellTable(List<IBaseControlModel> baseControlModels)
	{
		super(KEYPROVIDER);
		this.baseControlModels = baseControlModels;
	}

	protected void createModelColumns()
	{
		for (IBaseControlModel baseControlModel : baseControlModels)
		{
			TextHeader textHeader = new TextHeader(baseControlModel.getColumnLabel());
			Column column = getColumn(baseControlModel);
			setColumnWidth(column, WidthCalculationStrategy.getInstance().getControlColumnWidth(baseControlModel), Unit.PX);

			addColumn(column, textHeader);
		}

		setSelectionModel(selectionModel);
	}

	protected VOType getSelection()
	{
		return selectionModel.getSelectedObject();
	}

	public void addVOSelectHandler(final IVOSelectHandler<VOType> voDoubleClickHandler)
	{
		addDomHandler(new DoubleClickHandler()
		{

			/** {@inheritDoc} */
			@Override
			public void onDoubleClick(DoubleClickEvent event)
			{

				if (selectionModel.getSelectedObject() != null)
				{
					voDoubleClickHandler.onSingleSelect(selectionModel.getSelectedObject());
				}
			}
		}, DoubleClickEvent.getType());

	}

}
