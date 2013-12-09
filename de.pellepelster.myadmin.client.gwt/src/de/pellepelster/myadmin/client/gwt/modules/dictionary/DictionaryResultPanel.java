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

import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable.TableUpdateListener;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ISearchModel;
import de.pellepelster.myadmin.client.base.util.SimpleCallback;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.result.DictionaryResult;

public class DictionaryResultPanel<VOType extends IBaseVO> extends VerticalPanel
{

	private final ResultCellTable<VOType> resultCellTable;

	public DictionaryResultPanel(final String dictionaryModelName, final DictionaryResult<VOType> dictionaryResult)
	{

		resultCellTable = new ResultCellTable<VOType>(dictionaryResult);
		resultCellTable.setWidth("100%");
		resultCellTable.addVOActivationHandler(new SimpleCallback<IBaseTable.ITableRow<VOType>>()
		{
			/** {@inheritDoc} */
			@Override
			public void onCallback(IBaseTable.ITableRow<VOType> tableRow)
			{
				DictionaryEditorModuleFactory.openEditorForId(dictionaryModelName, tableRow.getVO().getId());
			}
		});

		dictionaryResult.addTableUpdateListeners(new TableUpdateListener()
		{
			@Override
			public void onUpdate()
			{
				resultCellTable.setRows(dictionaryResult.getRows());
			}
		});

		add(resultCellTable);
	}

	public GenericFilterVO<VOType> getEmptyFilter(ISearchModel searchModel)
	{
		@SuppressWarnings({ "rawtypes", "unchecked" })
		GenericFilterVO<VOType> genericFilter = new GenericFilterVO(searchModel.getVOName());
		return genericFilter;
	}

	public void setFilter(GenericFilterVO<VOType> genericFilterVO)
	{
		resultCellTable.setVisibleRangeAndClearData(resultCellTable.getVisibleRange(), true);
	}

}
