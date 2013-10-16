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
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseContainer;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseTable;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.IContainer;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IUIControl;

/**
 * GWT column based implementation for {@link IDictionaryLayoutStrategy}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public class ColumnLayoutStrategy
{
	private final LAYOUT_TYPE layoutType;

	private final List<IUIObservableValue> observableValues;

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

	public void createLayout(Panel parent, BaseContainer<IBaseContainerModel> baseContainer)
	{

		if (!baseContainer.getControls().isEmpty())
		{
			// Create a table to layout the form options
			FlexTable flexTable = new FlexTable();
			flexTable.setCellSpacing(5);
			parent.add(flexTable);
			// FlexCellFormatter cellFormatter =
			// flexTable.getFlexCellFormatter();ControlHandler.getInstance()

			int row = 0;
			for (BaseControl<IBaseControlModel> baseControl : baseContainer.getControls())
			{

				IUIControl<Widget> control = ControlHandler.getInstance().createControl(baseControl, layoutType);
				observableValues.add(control);

				switch (layoutType)
				{
					case EDITOR:
						flexTable.setHTML(row, 0, baseControl.getEditorLabel());
						break;
					case FILTER:
						flexTable.setHTML(row, 0, baseControl.getFilterLabel());
						break;
				}
				flexTable.setWidget(row, 1, control.getWidget());

				row++;
			}
		}

		if (!baseContainer.getChildren().isEmpty())
		{

			for (BaseContainer<IBaseContainerModel> lBaseContainer : baseContainer.getChildren())
			{

				IContainer<Panel> container = ContainerFactory.createContainer(lBaseContainer);
				parent.add(container.getContainer());

				if (container instanceof IUIObservableValue)
				{
					observableValues.add((IUIObservableValue) container);
				}

				if (container.getContainer() instanceof Panel && !(lBaseContainer instanceof BaseTable))
				{
					createLayout(container.getContainer(), lBaseContainer);
				}
			}
		}

	}

}
