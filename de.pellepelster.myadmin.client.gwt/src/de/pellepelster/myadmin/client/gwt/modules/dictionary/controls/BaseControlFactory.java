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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.databinding.TypeHelper;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseCellTable;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BaseCellControl.IValueHandler;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BaseCellControl.ViewData;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IControlFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.IValidator;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.ValidationUtils;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.validator.MandatoryValidator;

/**
 * @author pelle
 * 
 */
public abstract class BaseControlFactory<ControlModelType extends IBaseControlModel> implements
		IControlFactory<ControlModelType, Widget, Column<IBaseVO, ?>, Panel>
{

	private static final MandatoryValidator MANDATORY_VALIDATOR = new MandatoryValidator();

	@Override
	public Column<IBaseVO, ?> createColumn(final ControlModelType controlModel, boolean editable, final ListDataProvider<?> listDataProvider,
			final AbstractCellTable<?> abstractCellTable)
	{

		Column<IBaseVO, String> column;

		if (editable)
		{

			final EditTextCellWithValidation editTextCell = new EditTextCellWithValidation(controlModel, new IValueHandler()
			{

				@Override
				public String format(Object value)
				{
					if (value != null)
					{
						return value.toString();
					}
					else
					{
						return "";
					}
				}

				@Override
				public Object parse(String value)
				{
					return value.toString();
				}
			});

			column = new Column<IBaseVO, String>(editTextCell)
			{

				@Override
				public String getValue(IBaseVO vo)
				{
					return format(controlModel, vo.get(controlModel.getAttributePath()));
				}
			};

			FieldUpdater<IBaseVO, String> fieldUpdater = new FieldUpdater<IBaseVO, String>()
			{
				@SuppressWarnings("unchecked")
				@Override
				public void update(int index, IBaseVO vo, String value)
				{

					Object key = BaseCellTable.KEYPROVIDER.getKey(vo);

					List<IValidator> validators = MyAdmin.getInstance().getControlHandler().createValidators(controlModel);
					List<IValidationMessage> validationMessages = ValidationUtils.validate(validators, value, controlModel);

					ViewData<String> viewData = (ViewData<String>) editTextCell.getViewData(key);

					if (validationMessages != null && ValidationUtils.hasError(validationMessages))
					{
						viewData.setValidationMessages(validationMessages);
						// dataGrid.redraw();
					}
					else
					{
						viewData.getValidationMessages().clear();

						vo.set(controlModel.getAttributePath(),
								TypeHelper.convert(vo.getAttributeDescriptor(controlModel.getAttributePath()).getAttributeType(), value));
					}

					listDataProvider.refresh();
				}
			};
			column.setFieldUpdater(fieldUpdater);

		}
		else
		{
			column = new Column<IBaseVO, String>(new TextCell())
			{

				@Override
				public String getValue(IBaseVO vo)
				{
					return format(controlModel, vo.get(controlModel.getAttributePath()));
				}
			};
		}

		return column;

	}

	/** {@inheritDoc} */
	@Override
	public List<IValidator> createValidators(ControlModelType controlModel)
	{
		return createBaseValidators(controlModel);
	}

	public List<IValidator> createBaseValidators(ControlModelType controlModel)
	{
		List<IValidator> validators = new ArrayList<IValidator>();

		if (controlModel.isMandatory())
		{
			validators.add(MANDATORY_VALIDATOR);
		}
		return validators;
	}

	protected List<IValidator> addValidators(ControlModelType controlModel, List<IValidator> validators)
	{
		List<IValidator> validatorsResult = new ArrayList<IValidator>();
		validatorsResult.addAll(validators);
		validatorsResult.addAll(createBaseValidators(controlModel));
		return validatorsResult;
	}

	/** {@inheritDoc} */
	@Override
	public String format(ControlModelType controlModel, Object value)
	{
		if (value != null)
		{
			return value.toString();
		}
		else
		{
			return "";
		}
	}
}
