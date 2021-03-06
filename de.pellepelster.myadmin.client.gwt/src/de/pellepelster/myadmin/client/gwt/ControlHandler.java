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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BigDecimalControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BooleanControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.DateControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.EnumerationControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.FileControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.HierarchicalControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.IGwtControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.IntegerControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.ReferenceControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.TextControlFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;

/**
 * GWT based factory for {@link IBaseControlModel} derived controls
 * 
 * @author pelle
 * 
 */
@SuppressWarnings("unchecked")
public class ControlHandler<ControlModelType extends IBaseControlModel, ControlType extends BaseDictionaryControl<ControlModelType, ?>> implements
		IGwtControlFactory<ControlModelType, ControlType>
{

	private static ControlHandler<IBaseControlModel, BaseDictionaryControl<IBaseControlModel, ?>> instance;

	private static List<IGwtControlFactory<?, ?>> controlFactories = new ArrayList<IGwtControlFactory<?, ?>>();

	private ControlHandler()
	{
		super();
		controlFactories.add(new TextControlFactory());
		controlFactories.add(new IntegerControlFactory());
		controlFactories.add(new DateControlFactory());
		controlFactories.add(new BooleanControlFactory());
		controlFactories.add(new EnumerationControlFactory());
		controlFactories.add(new ReferenceControlFactory());
		controlFactories.add(new BigDecimalControlFactory());
		controlFactories.add(new HierarchicalControlFactory());
		controlFactories.add(new FileControlFactory());
	}

	public static ControlHandler<IBaseControlModel, BaseDictionaryControl<IBaseControlModel, ?>> getInstance()
	{
		if (instance == null)
		{
			instance = new ControlHandler<IBaseControlModel, BaseDictionaryControl<IBaseControlModel, ?>>();
		}

		return instance;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("rawtypes")
	public Column createColumn(ControlType baseControl, boolean editable, ListDataProvider<?> listDataProvider, AbstractCellTable<?> abstractCellTable)
	{
		return getControlFactory(baseControl).createColumn(baseControl, editable, listDataProvider, abstractCellTable);
	}

	/** {@inheritDoc} */
	@Override
	public Widget createControl(ControlType baseControl, LAYOUT_TYPE layoutType)
	{
		return getControlFactory(baseControl).createControl(baseControl, layoutType);
	}

	private IGwtControlFactory<ControlModelType, ControlType> getControlFactory(BaseDictionaryControl<ControlModelType, ?> baseControl)
	{

		for (IGwtControlFactory<?, ?> controlFactory : controlFactories)
		{
			if (controlFactory.supports(baseControl))
			{
				return (IGwtControlFactory<ControlModelType, ControlType>) controlFactory;
			}
		}

		throw new RuntimeException("unsupported control model '" + baseControl.getModel().getClass().getName() + "'");
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseDictionaryControl<?, ?> baseControl)
	{
		return true;
	}

}
