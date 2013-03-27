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
package de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.containers;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryContainerVO;

/**
 * Factory for container model creation
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class ContainerModelFactory
{

	public static IBaseContainerModel getContainerModel(IBaseModel parent, DictionaryContainerVO containerVO)
	{

		switch (containerVO.getType())
		{
			case COMPOSITE:
				return new CompositeContainerModel(parent, containerVO);
			case EDITABLE_TABLE:
				return new EditableTableModel(parent, containerVO);
			case ASSIGNMENT_TABLE:
				return new AssignmentTableModel(parent, containerVO);
		}

		throw new RuntimeException("unsupported container type '" + containerVO.getType().toString() + "'");
	}

}
