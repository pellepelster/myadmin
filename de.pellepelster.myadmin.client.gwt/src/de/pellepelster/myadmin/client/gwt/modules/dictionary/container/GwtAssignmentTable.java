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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.databinding.ValueChangeEvent;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.base.util.CollectionUtils;
import de.pellepelster.myadmin.client.gwt.ControlHandler;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.widgets.ImageButton;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.AssignmentTable;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.IContainer;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.layout.WidthCalculationStrategy;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class GwtAssignmentTable<VOType extends IBaseVO> extends BaseCellTable<VOType> implements IContainer<Panel>
{

	private final List<IValueChangeListener> valueChangeListeners = new ArrayList<IValueChangeListener>();

	private final AssignmentTable<VOType> assignmentTable;

	private final VerticalPanel verticalPanel = new VerticalPanel();

	private final ListDataProvider<VOType> dataProvider = new ListDataProvider<VOType>();

	private final SimpleLayoutPanel simpleLayoutPanel = new SimpleLayoutPanel();

	public GwtAssignmentTable(AssignmentTable<VOType> assignmentTable)
	{
		super(assignmentTable.getControls());

		this.assignmentTable = assignmentTable;
		setWidth("100%");

		dataProvider.addDataDisplay(this);

		simpleLayoutPanel.add(this);
		simpleLayoutPanel.setWidth(WidthCalculationStrategy.getInstance().getTableWidthCss(assignmentTable.getModel()));
		simpleLayoutPanel.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);

		verticalPanel.add(simpleLayoutPanel);
		verticalPanel.setWidth("100%");

		VerticalPanel buttonPanel = new VerticalPanel();
		buttonPanel.setWidth("1px");
		verticalPanel.add(buttonPanel);

		createAddButton(buttonPanel);

		createModelColumns();

		TextHeader textHeader = new TextHeader("");
		Column<VOType, Object> column = new Column<VOType, Object>(new ImageActionCell(MyAdmin.RESOURCES.delete(), new SimpleCallback<IBaseVO>()
		{

			@Override
			public void onCallback(IBaseVO vo)
			{
				dataProvider.getList().remove(vo);
				fireValueChanges();
			}
		}))
		{
			@Override
			public String getValue(IBaseVO vo)
			{
				return null;
			}
		};

		addColumn(column, textHeader);

	}

	@SuppressWarnings("unchecked")
	@Override
	protected Column<VOType, ?> getColumn(BaseControl baseControl)
	{
		return (Column<VOType, ?>) ControlHandler.getInstance().createColumn(baseControl, false, dataProvider, this);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return verticalPanel;
	}

	public void setContent(Object content)
	{
		if (content instanceof List)
		{
			this.dataProvider.getList().clear();
			this.dataProvider.getList().addAll((Collection<VOType>) content);
		}
		else
		{
			throw new RuntimeException("unsupported content type '" + content.getClass().getName() + "'");
		}
	}

	private void fireValueChanges()
	{
		ValueChangeEvent valueChangeEvent = new ValueChangeEvent(assignmentTable.getModel().getAttributePath(), CollectionUtils.copyToArrayList(dataProvider
				.getList()));
		for (IValueChangeListener valueChangeListener : valueChangeListeners)
		{
			valueChangeListener.handleValueChange(valueChangeEvent);
		}
	}

	private void createAddButton(VerticalPanel buttonPanel)
	{
		ImageButton addButton = new ImageButton(MyAdmin.RESOURCES.add());
		addButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				VOSelectionPopup.create(assignmentTable, new SimpleCallback<VOType>()
				{

					@Override
					public void onCallback(VOType vo)
					{
						if (!dataProvider.getList().contains(vo))
						{
							dataProvider.getList().add(vo);
							fireValueChanges();
						}
					}
				}).show();
			}
		});
		buttonPanel.add(addButton);
	}

}
