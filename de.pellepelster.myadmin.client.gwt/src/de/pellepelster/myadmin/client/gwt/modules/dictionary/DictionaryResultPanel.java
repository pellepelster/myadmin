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
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ISearchModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public class DictionaryResultPanel<VOType extends IBaseVO> extends VerticalPanel
{

	private final ResultCellTable<VOType> resultCellTable;

	private final MyAdminAsyncDataProvider<VOType> dataProvider;

	public DictionaryResultPanel(final String dictionaryModelName,
			de.pellepelster.myadmin.client.web.modules.dictionary.result.DictionaryResult<VOType> dictionaryResult)
	{

		resultCellTable = new ResultCellTable<VOType>(dictionaryResult);
		resultCellTable.setWidth("100%");
		resultCellTable.addVOSelectHandler(new SimpleCallback<IBaseTable.ITableRow<VOType>>()
		{

			/** {@inheritDoc} */
			@Override
			public void onCallback(IBaseTable.ITableRow<VOType> tableRow)
			{
				DictionaryEditorModuleFactory.openEditorForId(dictionaryModelName, tableRow.getVO().getId());
			}
		});

		dataProvider = new MyAdminAsyncDataProvider<VOType>();
		dataProvider.addDataDisplay(resultCellTable);
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
		dataProvider.setGenericFilterVO(genericFilterVO);
		resultCellTable.setVisibleRangeAndClearData(resultCellTable.getVisibleRange(), true);
	}

}
