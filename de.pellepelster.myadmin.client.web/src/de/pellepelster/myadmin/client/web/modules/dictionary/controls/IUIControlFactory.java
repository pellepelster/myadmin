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
package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.util.List;

import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.IValidator;

/**
 * Factory for control related thingies
 * 
 * @author pelle
 * 
 */
public interface IUIControlFactory<ControlModelType extends IBaseControlModel, WidgetType, ColumType, ContainerType>
{

	ColumType createColumn(BaseControl<ControlModelType> baseControl, boolean editable, ListDataProvider<?> listDataProvider,
			AbstractCellTable<?> abstractCellTable);

	IUIControl<WidgetType> createControl(BaseControl<ControlModelType> baseControl, LAYOUT_TYPE layoutType);

	List<IValidator> createValidators(BaseControl<ControlModelType> baseControl);

	String format(BaseControl<ControlModelType> baseControl, Object value);

	boolean supports(BaseControl baseControl);
}