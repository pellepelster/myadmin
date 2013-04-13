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

import de.pellepelster.myadmin.client.base.databinding.IUIObservableValue;
import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.databinding.ValueChangeEvent;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDatabindingAwareModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IEditableTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.util.CollectionUtils;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseDataGrid;
import de.pellepelster.myadmin.client.gwt.widgets.ImageButton;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.IContainer;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class EditableTable extends BaseDataGrid<IBaseVO> implements IContainer<Panel>, IUIObservableValue
{
	public final static String CONTROL_FIRST_EDIT_DATA_KEY = "CONTROL_FIRST_EDIT_DATA_KEY";

	private final List<IValueChangeListener> valueChangeListeners = new ArrayList<IValueChangeListener>();

	private final SimpleLayoutPanel simpleLayoutPanel = new SimpleLayoutPanel();

	private final VerticalPanel verticalPanel = new VerticalPanel();

	private final ListDataProvider<IBaseVO> dataProvider = new ListDataProvider<IBaseVO>();

	private final IEditableTableModel editableTableModel;

	public EditableTable(IEditableTableModel editableTableModel)
	{
		super(editableTableModel.getControls());

		this.editableTableModel = editableTableModel;

		createModelColumns();

		setTableWidth(100d, Unit.PCT);

		dataProvider.addDataDisplay(this);

		simpleLayoutPanel.add(this);
		simpleLayoutPanel.setWidth("99%");
		simpleLayoutPanel.setHeight("20em");
		verticalPanel.setWidth("100%");
		verticalPanel.add(simpleLayoutPanel);

		createAddButton();

		TextHeader textHeader = new TextHeader("");

		Column<IBaseVO, Object> column = new Column<IBaseVO, Object>(new EditableTableActionCell(new SimpleCallback<IBaseVO>()
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

		ValueChangeEvent valueChangeEvent = new ValueChangeEvent(editableTableModel.getAttributePath(), CollectionUtils.copyToArrayList(dataProvider.getList()));
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
						.getNewVO(editableTableModel.getVOName(), new HashMap<String, String>(), new AsyncCallback<IBaseVO>()
						{

							@Override
							public void onSuccess(IBaseVO newVO)
							{
								for (IBaseControlModel baseControlModel : editableTableModel.getControls())
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
								throw new RuntimeException("error creating new vo '" + editableTableModel.getVOName() + "'");
							}
						});
			}
		});
		verticalPanel.add(addButton);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Column<IBaseVO, ?> getColumn(IBaseControlModel baseControlModel)
	{
		return (Column<IBaseVO, ?>) MyAdmin.getInstance().getControlHandler().createColumn(baseControlModel, true, dataProvider, this);

	}

	/** {@inheritDoc} */
	@Override
	public void addValueChangeListener(IValueChangeListener valueChangeListener)
	{
		valueChangeListeners.add(valueChangeListener);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return verticalPanel;
	}

	/** {@inheritDoc} */
	@Override
	public Object getContent()
	{
		return dataProvider.getList();
	}

	/** {@inheritDoc} */
	@Override
	public Class<?> getContentType()
	{
		return List.class;
	}

	/** {@inheritDoc} */
	@Override
	public IDatabindingAwareModel getModel()
	{
		return editableTableModel;
	}

	/** {@inheritDoc} */
	@Override
	public List<IValueChangeListener> getValueChangeListeners()
	{
		return valueChangeListeners;
	}

	/** {@inheritDoc} */
	@Override
	public void removeValueChangeListener(IValueChangeListener valueChangeListener)
	{
		valueChangeListeners.remove(valueChangeListener);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
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

	/** {@inheritDoc} */
	@Override
	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{

	}

}
