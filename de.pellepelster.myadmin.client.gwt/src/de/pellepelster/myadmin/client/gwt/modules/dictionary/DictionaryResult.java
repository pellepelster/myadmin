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

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IResultModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.ISearchModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.filter.IDictionaryFilterUI;
import de.pellepelster.myadmin.client.web.modules.dictionary.result.IDictionaryResultUI;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

/**
 * Generic dictionary model based implementation of {@link IDictionaryFilterUI}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 * @param <VOType>
 */
public class DictionaryResult<VOType extends IBaseVO> implements IDictionaryResultUI<VOType, Panel>
{

	private final ResultCellTable<VOType> resultCellTable;

	private final VerticalPanel verticalPanel = new VerticalPanel();

	private final MyAdminAsyncDataProvider<VOType> dataProvider;

	/**
	 * Constructor for {@link DictionaryResult}
	 * 
	 * @param filterModel
	 *            Model describing the filter
	 */
	public DictionaryResult(final String dictionaryModelName, IResultModel resultModel)
	{
		resultCellTable = new ResultCellTable<VOType>(resultModel);
		resultCellTable.setWidth("100%");
		resultCellTable.addDoubleClickHandler(new IVODoubleClickHandler<VOType>()
		{

			/** {@inheritDoc} */
			@Override
			public void doubleClick(VOType vo)
			{
				DictionaryEditorModuleFactory.openEditorForId(dictionaryModelName, vo.getId());
			}
		});

		dataProvider = new MyAdminAsyncDataProvider<VOType>();
		dataProvider.addDataDisplay(resultCellTable);
		verticalPanel.add(resultCellTable);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return verticalPanel;
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

	public void setResultsChangedCallback(SimpleCallback<Integer> resultsChangedCallback)
	{
		dataProvider.setResultsChangedCallback(resultsChangedCallback);
	}

}
