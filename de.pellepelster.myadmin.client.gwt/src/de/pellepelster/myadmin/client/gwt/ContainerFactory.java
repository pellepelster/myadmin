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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IEditableTableModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.AssignmentTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.Composite;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.EditableTable;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.IContainer;

/**
 * Factory for creation of container UI elements
 * 
 * @author pelle
 * 
 */
public class ContainerFactory
{

	public static IContainer<Panel> createContainer(IBaseContainerModel containerModel)
	{

		IContainer<Panel> container;

		if (containerModel instanceof ICompositeModel)
		{
			container = new Composite((ICompositeModel) containerModel);
		}
		else if (containerModel instanceof IEditableTableModel)
		{
			container = new EditableTable((IEditableTableModel) containerModel);
		}
		else if (containerModel instanceof IAssignmentTableModel)
		{
			container = new AssignmentTable((IAssignmentTableModel) containerModel);
		}
		else
		{
			throw new RuntimeException("unsupported container model type '" + containerModel.getClass().getName() + "'");
		}

		return container;

	}
}
