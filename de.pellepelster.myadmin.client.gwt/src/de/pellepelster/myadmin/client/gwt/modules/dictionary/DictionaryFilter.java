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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.databinding.IUIObservableValue;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.layout.IDictionaryLayoutStrategy;
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IFilterModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.filter.IDictionaryFilterUI;

/**
 * Generic dictionary model based implementation of {@link IDictionaryFilterUI}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 * @param <VOType>
 */
public class DictionaryFilter<VOType extends IBaseVO> implements IDictionaryFilterUI<VOType, Panel>
{

	private final List<IUIObservableValue> uiObservableValues = new ArrayList<IUIObservableValue>();

	private final VerticalPanel verticalPanel = new VerticalPanel();

	/** Filter model represented by this filter */
	private final IFilterModel filterModel;

	@SuppressWarnings("unchecked")
	private final IDictionaryLayoutStrategy<Panel> layoutStrategy = (IDictionaryLayoutStrategy<Panel>) MyAdmin.getInstance().getLayoutFactory()
			.getLayoutStrategy(uiObservableValues, LAYOUT_TYPE.FILTER);

	/**
	 * Constructor for {@link DictionaryFilter}
	 * 
	 * @param filterModel
	 *            Model describing the filter
	 */
	public DictionaryFilter(IFilterModel filterModel)
	{
		this.filterModel = filterModel;
		layoutStrategy.createLayout(verticalPanel, filterModel.getCompositeModel());
	}

	/** {@inheritDoc} */
	@Override
	public void clearFilter()
	{
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return verticalPanel;
	}

	private boolean isCriteriaEmpty(IUIObservableValue uiObservableValue)
	{
		Object criteriaValue = uiObservableValue.getContent();

		if (criteriaValue == null)
		{
			return true;
		}

		if (criteriaValue instanceof String)
		{
			String criteriaValueString = (String) criteriaValue;

			if (criteriaValueString.trim().isEmpty())
			{
				return true;
			}
		}

		return false;
	}

	/** {@inheritDoc} */
	@Override
	public GenericFilterVO<VOType> getFilter()
	{
		@SuppressWarnings({ "rawtypes", "unchecked" })
		GenericFilterVO<VOType> genericFilter = new GenericFilterVO(filterModel.getVOName());

		for (IUIObservableValue uiObservableValue : uiObservableValues)
		{
			if (!isCriteriaEmpty(uiObservableValue))
			{
				genericFilter.addCriteria(uiObservableValue.getModel().getAttributePath(), uiObservableValue.getContent());
			}
		}

		return genericFilter;
	}

}
