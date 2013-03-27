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
public interface IControlFactory<ControlModelType extends IBaseControlModel, WidgetType, ColumType, ContainerType>
{

	/**
	 * @param controlModel
	 * @param editable
	 * @param listDataProvider
	 * @param cellTable
	 * @return
	 */
	ColumType createColumn(ControlModelType controlModel, boolean editable, ListDataProvider<?> listDataProvider, AbstractCellTable<?> abstractCellTable);

	/**
	 * Creates the control UI
	 * 
	 * @param controlModel
	 * @param layoutType
	 * @return
	 */
	IControl<WidgetType> createControl(ControlModelType controlModel, LAYOUT_TYPE layoutType);

	/**
	 * Create validators for this control model
	 * 
	 * @param controlModel
	 * @return
	 */
	List<IValidator> createValidators(ControlModelType controlModel);

	/**
	 * Formats a controls value for display purposes
	 * 
	 * @param value
	 * @return
	 */
	String format(ControlModelType baseControlModel, Object value);

	/**
	 * Indicates whether this factory supports a control model type
	 * 
	 * @param baseControlModel
	 * @return
	 */
	boolean supports(IBaseControlModel baseControlModel);
}