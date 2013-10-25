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
import de.pellepelster.myadmin.client.gwt.ControlHandler;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.ResultTable;

public class ResultCellTable<VOType extends IBaseVO> extends BaseCellTable<VOType>
{

	private final ResultTable resultTable;

	public ResultCellTable(ResultTable resultTable)
	{
		super(resultTable.getControls());
		this.resultTable = resultTable;

		createModelColumns();
	}

	@Override
	protected Column<VOType, ?> getColumn(BaseDictionaryControl baseControl)
	{
		return (Column<VOType, ?>) ControlHandler.getInstance().createColumn(baseControl, false, null, this);
	}

}
