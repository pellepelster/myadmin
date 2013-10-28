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

import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.GwtAssignmentTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.GwtComposite;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.GwtEditableTable;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.AssignmentTable;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseContainerElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.EditableTable;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.IContainer;

/**
 * Factory for creation of container UI elements
 * 
 * @author pelle
 * 
 */
public class ContainerFactory
{

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static IContainer<Panel> createContainer(BaseContainerElement baseContainer)
	{
		IContainer<Panel> container;

		if (baseContainer instanceof Composite)
		{
			return new GwtComposite((Composite) baseContainer);
		}
		else if (baseContainer instanceof EditableTable)
		{
			container = new GwtEditableTable((EditableTable) baseContainer);
		}
		else if (baseContainer instanceof AssignmentTable)
		{
			container = new GwtAssignmentTable((AssignmentTable) baseContainer);
		}
		else
		{
			throw new RuntimeException("unsupported container type '" + baseContainer.getClass().getName() + "'");
		}

		return container;

	}
}
