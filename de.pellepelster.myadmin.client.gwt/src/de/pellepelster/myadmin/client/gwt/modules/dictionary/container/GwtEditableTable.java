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
import java.util.HashMap;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.databinding.ValueChangeEvent;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.util.CollectionUtils;
import de.pellepelster.myadmin.client.gwt.ControlHandler;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseDataGrid;
import de.pellepelster.myadmin.client.gwt.widgets.ImageButton;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.EditableTable;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.IContainer;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class GwtEditableTable<VOType extends IBaseVO> extends BaseDataGrid<IBaseVO> implements IContainer<Panel>
{
	public final static String CONTROL_FIRST_EDIT_DATA_KEY = "CONTROL_FIRST_EDIT_DATA_KEY";

	private final List<IValueChangeListener> valueChangeListeners = new ArrayList<IValueChangeListener>();

	private final SimpleLayoutPanel simpleLayoutPanel = new SimpleLayoutPanel();

	private final VerticalPanel verticalPanel = new VerticalPanel();

	private final ListDataProvider<IBaseVO> dataProvider = new ListDataProvider<IBaseVO>();

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
		Column<IBaseVO, Object> column = new Column<IBaseVO, Object>(new ImageActionCell(MyAdmin.RESOURCES.delete(), new SimpleCallback<IBaseVO>()
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

	private void fireValueChanges()
	{
		ValueChangeEvent valueChangeEvent = new ValueChangeEvent(editableTable.getModel().getAttributePath(), CollectionUtils.copyToArrayList(dataProvider
				.getList()));
		for (IValueChangeListener valueChangeListener : valueChangeListeners)
		{
			valueChangeListener.handleValueChange(valueChangeEvent);
		}
	}

	private void createAddButton()
	{

		ImageButton addButton = new ImageButton(MyAdmin.RESOURCES.add());
		addButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService()
						.getNewVO(editableTable.getModel().getVOName(), new HashMap<String, String>(), new AsyncCallback<IBaseVO>()
						{

							@Override
							public void onSuccess(IBaseVO newVO)
							{
								for (IBaseControlModel baseControlModel : editableTable.getModel().getControls())
								{
									newVO.getData().put(baseControlModel.getName(), CONTROL_FIRST_EDIT_DATA_KEY);
								}

								dataProvider.getList().add(newVO);
								fireValueChanges();
								getSelectionModel().setSelected(newVO, true);
							}

							@Override
							public void onFailure(Throwable caught)
							{
								throw new RuntimeException("error creating new vo '" + editableTable.getModel().getVOName() + "'");
							}
						});
			}
		});
		verticalPanel.add(addButton);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Column<IBaseVO, ?> getColumn(BaseControl baseControl)
	{
		return (Column<IBaseVO, ?>) ControlHandler.getInstance().createColumn(baseControl, true, dataProvider, this);

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
			dataProvider.setList((List<IBaseVO>) content);
		}
		else
		{
			throw new RuntimeException("unsupported content type '" + content.getClass().getName() + "'");
		}
	}

}
