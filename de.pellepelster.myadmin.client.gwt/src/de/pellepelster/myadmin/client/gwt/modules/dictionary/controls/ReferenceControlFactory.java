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

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.user.cellview.client.AbstractCellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.controls.BaseCellControl.IValueHandler;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ReferenceControl;

/**
 * control factory for reference controls
 * 
 * @author pelle
 * 
 */
public class ReferenceControlFactory extends BaseControlFactory<IReferenceControlModel, ReferenceControl>
{

	/** {@inheritDoc} */
	@Override
	public Widget createControl(ReferenceControl referenceControl, LAYOUT_TYPE layoutType)
	{
		switch (referenceControl.getModel().getControlType())
		{
			case DROPDOWN:
				return new ReferenceDropdownControl(referenceControl);
			default:
				return new ReferenceTextControl(referenceControl);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean supports(BaseControl baseControl)
	{
		return baseControl instanceof ReferenceControl;
	}

	/** {@inheritDoc} */
	@Override
	public Column<IBaseVO, ?> createColumn(final ReferenceControl referenceControl, boolean editable, final ListDataProvider<?> listDataProvider,
			final AbstractCellTable<?> abstractCellTable)
	{

		Column<IBaseVO, IBaseVO> column;

		if (editable)
		{
			final BaseCellControl<IBaseVO> editTextCell;

			switch (referenceControl.getModel().getControlType())
			{
				default:
					editTextCell = new SuggestCellControl<IBaseVO>(referenceControl.getModel(), new VOSuggestOracle(referenceControl.getModel()),
							new IValueHandler<IBaseVO>()
							{

								@Override
								public String format(IBaseVO value)
								{
									return DictionaryUtil.getLabel(referenceControl.getModel(), value, "");
								}

								@Override
								public IBaseVO parse(String value)
								{
									return null;
								}
							});
					break;
			}

			column = new Column<IBaseVO, IBaseVO>(editTextCell)
			{

				@Override
				public IBaseVO getValue(IBaseVO vo)
				{
					return (IBaseVO) vo.get(referenceControl.getModel().getAttributePath());
				}
			};

			FieldUpdater<IBaseVO, IBaseVO> fieldUpdater = new FieldUpdater<IBaseVO, IBaseVO>()
			{
				@Override
				public void update(int index, IBaseVO vo, IBaseVO value)
				{
					vo.set(referenceControl.getModel().getAttributePath(), value);
				}
			};
			column.setFieldUpdater(fieldUpdater);

		}
		else
		{
			column = new Column<IBaseVO, IBaseVO>(new ReferenceCell(referenceControl.getModel()))
			{
				@Override
				public IBaseVO getValue(IBaseVO vo)
				{
					return (IBaseVO) vo.get(referenceControl.getModel().getAttributePath());
				}
			};
		}

		return column;

	}
}
