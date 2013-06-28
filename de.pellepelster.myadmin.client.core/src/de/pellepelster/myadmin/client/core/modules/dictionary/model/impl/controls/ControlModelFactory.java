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
package de.pellepelster.myadmin.client.core.modules.dictionary.model.impl.controls;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.core.utils.DateTimeFormat;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryControlVO;

/**
 * Factory for control model creation
 * 
 * @author Christian Pelster
 * @version $Rev$, $Date$
 * 
 */
public class ControlModelFactory
{

	public static IBaseControlModel getControlModel(IBaseModel parent, DictionaryControlVO controlVO)
	{

		switch (controlVO.getDatatype().getBaseType())
		{
			case TEXT:
				return new TextControlModel(parent, controlVO);
			case INTEGER:
				return new IntegerControlModel(parent, controlVO);
			case BIGDECIMAL:
				return new BigDecimalControlModel(parent, controlVO);
			case BOOLEAN:
				return new BooleanControlModel(parent, controlVO);
			case DATE:
				com.google.gwt.i18n.shared.DateTimeFormat dateTimeFormat = DateTimeFormat
						.getFormat(com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat.DATE_MEDIUM);
				return new DateControlModel(parent, controlVO, dateTimeFormat.getPattern());
			case ENUMERATION:
				return new EnumerationControlModel(parent, controlVO);
			case REFERENCE:
				return new ReferenceControlModel(parent, controlVO);
			case HIERARCHICAL:
				return new HierarchicalControlModel(parent, controlVO);
			default:
				throw new RuntimeException("unsupported basetype '" + controlVO.getDatatype().getBaseType().toString() + "'");
		}

	}

	public static List<IBaseControlModel> getControlModel(IBaseModel parent, List<DictionaryControlVO> controlVOs)
	{

		List<IBaseControlModel> result = new ArrayList<IBaseControlModel>();

		if (controlVOs != null)
		{
			for (DictionaryControlVO controlVO : controlVOs)
			{
				result.add(getControlModel(parent, controlVO));
			}
		}

		return result;

	}

}
