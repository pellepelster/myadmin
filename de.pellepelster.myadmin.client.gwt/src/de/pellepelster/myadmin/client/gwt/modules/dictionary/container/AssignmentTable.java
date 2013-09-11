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

import java.util.Collection;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.databinding.IUIObservableValue;
import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDatabindingAwareModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.IVOSelectHandler;
import de.pellepelster.myadmin.client.gwt.widgets.ImageButton;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.IContainer;

/**
 * GWT {@link ICompositeModel} implementation
 * 
 * @author pelle
 * 
 */
public class AssignmentTable<VOType extends IBaseVO> extends BaseCellTable<VOType> implements IContainer<Panel>, IUIObservableValue
{

	private final IAssignmentTableModel assignmentTableModel;

	private final VerticalPanel verticalPanel = new VerticalPanel();

	private final ListDataProvider<VOType> dataProvider = new ListDataProvider<VOType>();

	private final SimpleLayoutPanel simpleLayoutPanel = new SimpleLayoutPanel();

	public AssignmentTable(IAssignmentTableModel assignmentTableModel)
	{
		super(assignmentTableModel.getControls());

		this.assignmentTableModel = assignmentTableModel;
		setWidth("100%");

		dataProvider.addDataDisplay(this);

		simpleLayoutPanel.add(this);
		simpleLayoutPanel.setWidth("99%");
		simpleLayoutPanel.setHeight(BaseCellTable.DEFAULT_TABLE_HEIGHT);

		verticalPanel.add(simpleLayoutPanel);
		verticalPanel.setWidth("100%");

		createAddButton();

		createModelColumns();

	}

	@SuppressWarnings("unchecked")
	@Override
	protected Column<VOType, ?> getColumn(IBaseControlModel baseControlModel)
	{
		return (Column<VOType, ?>) MyAdmin.getInstance().getControlHandler().createColumn(baseControlModel, true, dataProvider, this);
	}

	/** {@inheritDoc} */
	@Override
	public void addValueChangeListener(IValueChangeListener valueChangeListener)
	{

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
		return assignmentTableModel;
	}

	/** {@inheritDoc} */
	@Override
	public List<IValueChangeListener> getValueChangeListeners()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void removeValueChangeListener(IValueChangeListener valueChangeListener)
	{
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
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

	/** {@inheritDoc} */
	@Override
	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{

	}

	private void createAddButton()
	{
		ImageButton addButton = new ImageButton(MyAdmin.RESOURCES.add());
		addButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				VOSelectionPopup.create(DictionaryModelProvider.getCachedDictionaryModel(assignmentTableModel.getDictionaryName()).getVOName(),
						assignmentTableModel.getControls(), new IVOSelectHandler<VOType>()
						{

							@Override
							public void onSingleSelect(VOType vo)
							{
								dataProvider.getList().add(vo);
							}
						});
			}
		});
		verticalPanel.add(addButton);
	}

}
