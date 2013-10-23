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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.ITableRow;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.gwt.ControlHandler;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseDataGrid;
import de.pellepelster.myadmin.client.gwt.widgets.ImageButton;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.EditableTable;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.IContainer;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class GwtEditableTable<VOType extends IBaseVO> extends BaseDataGrid<VOType> implements IContainer<Panel>
{
	public final static String CONTROL_FIRST_EDIT_DATA_KEY = "CONTROL_FIRST_EDIT_DATA_KEY";

	private final SimpleLayoutPanel simpleLayoutPanel = new SimpleLayoutPanel();

	private final VerticalPanel verticalPanel = new VerticalPanel();

	private final ListDataProvider<ITableRow<VOType>> dataProvider = new ListDataProvider<ITableRow<VOType>>();

	private final EditableTable<VOType> editableTable;

	public GwtEditableTable(EditableTable<VOType> editableTable)
	{
		super(editableTable.getControls());

		this.editableTable = editableTable;

		createModelColumns();

		setTableWidth(100d, Unit.PCT);

		dataProvider.addDataDisplay(this);

		simpleLayoutPanel.add(this);
		simpleLayoutPanel.setWidth("99%");
		simpleLayoutPanel.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);

		verticalPanel.setWidth("100%");
		verticalPanel.add(simpleLayoutPanel);

		createAddButton();

		TextHeader textHeader = new TextHeader("");
		Column<IBaseTable.ITableRow<VOType>, Void> column = new Column<IBaseTable.ITableRow<VOType>, Void>(new ImageActionCell(MyAdmin.RESOURCES.delete(),
				new SimpleCallback<IBaseTable.ITableRow<VOType>>()
				{

					@Override
					public void onCallback(IBaseTable.ITableRow<VOType> vo)
					{
						dataProvider.getList().remove(vo);
					}
				}))
		{

			@Override
			public Void getValue(IBaseTable.ITableRow<VOType> vo)
			{
				return null;
			}
		};

		addColumn(column, textHeader);
	}

	private void createAddButton()
	{

		ImageButton addButton = new ImageButton(MyAdmin.RESOURCES.add());
		addButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				editableTable.add(new BaseErrorAsyncCallback<List<IBaseTable.ITableRow<VOType>>>()
				{

					@Override
					public void onSuccess(List<ITableRow<VOType>> result)
					{
						dataProvider.setList(result);
					}
				});
			}
		});
		verticalPanel.add(addButton);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Column<IBaseTable.ITableRow<VOType>, ?> getColumn(BaseControl baseControl)
	{
		return (Column<IBaseTable.ITableRow<VOType>, ?>) ControlHandler.getInstance().createColumn(baseControl, true, dataProvider, this);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return verticalPanel;
	}

}
