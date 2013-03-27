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
package de.pellepelster.myadmin.client.gwt;

import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.databinding.IUIObservableValue;
import de.pellepelster.myadmin.client.base.layout.IDictionaryLayoutStrategy;
import de.pellepelster.myadmin.client.base.layout.ILayoutCallback;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.ControlUtil;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.IContainer;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IControl;

/**
 * GWT column based implementation for {@link IDictionaryLayoutStrategy}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class ColumnLayoutStrategy implements IDictionaryLayoutStrategy<Panel>
{

	private final LAYOUT_TYPE layoutType;

	private final List<IUIObservableValue> observableValues;

	private final ILayoutCallback layoutCallback = new ILayoutCallback()
	{
	};

	/**
	 * Constructor for {@link ColumnLayoutStrategy}
	 * 
	 * @param observableValues
	 * @param layoutType
	 */
	public ColumnLayoutStrategy(List<IUIObservableValue> observableValues, LAYOUT_TYPE layoutType)
	{
		this.observableValues = observableValues;
		this.layoutType = layoutType;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	@Override
	public void createLayout(Panel parent, IBaseContainerModel containerModel)
	{

		if (!containerModel.getControls().isEmpty())
		{

			// Create a table to layout the form options
			FlexTable flexTable = new FlexTable();
			flexTable.setCellSpacing(5);
			parent.add(flexTable);
			// FlexCellFormatter cellFormatter =
			// flexTable.getFlexCellFormatter();ControlHandler.getInstance()

			int row = 0;
			for (IBaseControlModel baseControlModel : containerModel.getControls())
			{

				IControl<Widget> control = MyAdmin.getInstance().getControlHandler().createControl(baseControlModel, layoutType);
				observableValues.add(control);

				flexTable.setHTML(row, 0, ControlUtil.getLabel(layoutType, baseControlModel));
				flexTable.setWidget(row, 1, control.getWidget());

				row++;
			}
		}

		if (!containerModel.getChildren().isEmpty())
		{

			for (IBaseContainerModel baseContainerModel : containerModel.getChildren())
			{

				IContainer<Panel> container = ContainerFactory.createContainer(baseContainerModel);
				parent.add(container.getContainer());

				if (container instanceof IUIObservableValue)
				{
					observableValues.add((IUIObservableValue) container);
				}

				if (container.getContainer() instanceof Panel && !(baseContainerModel instanceof IBaseTableModel))
				{
					createLayout(container.getContainer(), baseContainerModel);
				}
			}
		}

	}

	/** {@inheritDoc} */
	@Override
	public ILayoutCallback getLayoutCallback()
	{
		return layoutCallback;
	}

}
