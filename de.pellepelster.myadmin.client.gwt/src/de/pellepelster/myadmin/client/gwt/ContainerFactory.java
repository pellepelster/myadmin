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

import com.google.gwt.user.client.ui.Panel;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IAssignmentTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IEditableTableModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.AssignmentTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.EditableTable;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseContainer;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.IContainer;

/**
 * Factory for creation of container UI elements
 * 
 * @author pelle
 * 
 */
public class ContainerFactory
{

	public static IContainer<Panel> createContainer(BaseContainer<IBaseContainerModel> baseContainer)
	{
		IContainer<Panel> container;

		if (baseContainer instanceof de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite)
		{
			return new Composite((de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite) baseContainer);
		}
		else if (baseContainer instanceof IEditableTableModel)
		{
			container = new EditableTable((IEditableTableModel) baseContainer);
		}
		else if (baseContainer instanceof IAssignmentTableModel)
		{
			container = new AssignmentTable((IAssignmentTableModel) baseContainer);
		}
		else
		{
			throw new RuntimeException("unsupported container type '" + baseContainer.getClass().getName() + "'");
		}

		return container;

	}
}
