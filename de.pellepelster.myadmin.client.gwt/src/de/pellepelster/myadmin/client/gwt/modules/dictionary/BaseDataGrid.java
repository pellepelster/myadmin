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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.BaseVOKeyProvider;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseDataGrid<VOType extends IBaseVO> extends DataGrid<VOType>
{
	public static BaseVOKeyProvider KEYPROVIDER = new BaseVOKeyProvider();

	private final SingleSelectionModel<VOType> selectionModel = new SingleSelectionModel<VOType>(KEYPROVIDER);

	protected abstract Column<VOType, ?> getColumn(IBaseControlModel baseControlModel);

	private List<IBaseControlModel> baseControlModels;

	public BaseDataGrid(List<IBaseControlModel> baseControlModels)
	{
		super(KEYPROVIDER);
		this.baseControlModels = baseControlModels;
	}

	protected void createModelColumns()
	{
		for (IBaseControlModel baseControlModel : baseControlModels)
		{
			TextHeader textHeader = new TextHeader(baseControlModel.getColumnLabel());
			addColumn(getColumn(baseControlModel), textHeader);
		}

		setSelectionModel(selectionModel);
	}

	public void addDoubleClickHandler(final IVODoubleClickHandler<VOType> voDoubleClickHandler)
	{
		addDomHandler(new DoubleClickHandler()
		{

			/** {@inheritDoc} */
			@Override
			public void onDoubleClick(DoubleClickEvent event)
			{

				if (selectionModel.getSelectedObject() != null)
				{
					voDoubleClickHandler.doubleClick(selectionModel.getSelectedObject());
				}
			}
		}, DoubleClickEvent.getType());

	}

}
