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
package de.pellepelster.myadmin.client.gwt.modules.dictionary;

import com.google.gwt.user.cellview.client.Column;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IResultModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.ControlHandler;

public class ResultCellTable<VOType extends IBaseVO> extends BaseCellTable<VOType> {

	private final IResultModel resultModel;

	public ResultCellTable(IResultModel resultModel) {
		super(resultModel.getControls());
		this.resultModel = resultModel;

		createModelColumns();
	}

	@Override
	protected Column<VOType, ?> getColumn(IBaseControlModel baseControlModel) {
		return (Column<VOType, ?>) ControlHandler.getInstance().createColumn(baseControlModel, false, null, this);
	}

}
