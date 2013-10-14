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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BigDecimalControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BooleanControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.DateControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.EnumerationControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.HierarchicalControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.IntegerControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.ReferenceControlFactory;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.TextControlFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IUIControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IUIControlFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.IValidator;

/**
 * GWT based factory for {@link IBaseControlModel} derived controls
 * 
 * @author pelle
 * 
 */
@SuppressWarnings("unchecked")
public class ControlHandler<ControlModelType extends IBaseControlModel> implements IUIControlFactory<ControlModelType, Widget, Column<IBaseVO, ?>, Panel> {

	private static ControlHandler<IBaseControlModel> instance;

	private static List<IUIControlFactory<?, Widget, Column<IBaseVO, ?>, Panel>> controlFactories = new ArrayList<IUIControlFactory<?, Widget, Column<IBaseVO, ?>, Panel>>();

	private ControlHandler() {
		super();
		controlFactories.add(new TextControlFactory());
		controlFactories.add(new IntegerControlFactory());
		controlFactories.add(new DateControlFactory());
		controlFactories.add(new BooleanControlFactory());
		controlFactories.add(new EnumerationControlFactory());
		controlFactories.add(new ReferenceControlFactory());
		controlFactories.add(new BigDecimalControlFactory());
		controlFactories.add(new HierarchicalControlFactory());
	}

	public static ControlHandler<IBaseControlModel> getInstance() {
		if (instance == null) {
			instance = new ControlHandler<IBaseControlModel>();
		}

		return instance;
	}

	/** {@inheritDoc} */
	@Override
	@SuppressWarnings("rawtypes")
	public Column createColumn(ControlModelType controlModel, boolean editable, ListDataProvider<?> listDataProvider, AbstractCellTable<?> abstractCellTable) {
		return getControlFactory(controlModel).createColumn(controlModel, editable, listDataProvider, abstractCellTable);
	}

	/** {@inheritDoc} */
	@Override
	public IUIControl<Widget> createControl(ControlModelType controlModel, LAYOUT_TYPE layoutType) {
		return getControlFactory(controlModel).createControl(controlModel, layoutType);
	}

	/** {@inheritDoc} */
	@Override
	public List<IValidator> createValidators(ControlModelType controlModel) {
		return getControlFactory(controlModel).createValidators(controlModel);
	}

	private IUIControlFactory<ControlModelType, Widget, Column<IBaseVO, ?>, Panel> getControlFactory(IBaseControlModel baseControlModel) {

		for (IUIControlFactory<?, Widget, Column<IBaseVO, ?>, Panel> controlFactory : controlFactories) {
			if (controlFactory.supports(baseControlModel)) {
				return (IUIControlFactory<ControlModelType, Widget, Column<IBaseVO, ?>, Panel>) controlFactory;
			}
		}

		throw new RuntimeException("unsupported control model '" + baseControlModel.getClass().getName() + "'");
	}

	/** {@inheritDoc} */
	@Override
	public String format(ControlModelType controlModel, Object value) {
		return getControlFactory(controlModel).format(controlModel, value);
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(IBaseControlModel baseControlModel) {
		return true;
	}

}
