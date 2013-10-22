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
import de.pellepelster.myadmin.client.base.layout.LAYOUT_TYPE;
import de.pellepelster.myadmin.client.gwt.ColumnLayoutStrategy;
import de.pellepelster.myadmin.client.web.modules.dictionary.filter.DictionaryFilter;

/**
 * Generic dictionary model based implementation of {@link IDictionaryFilterUI}
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 * @param <VOType>
 */
public class DictionaryFilterPanel<VOType extends IBaseVO> extends VerticalPanel
{

	private final DictionaryFilter<VOType> dictionaryFilter;

	private final ColumnLayoutStrategy layoutStrategy = new ColumnLayoutStrategy(LAYOUT_TYPE.FILTER);

	/**
	 * Constructor for {@link DictionaryFilterPanel}
	 * 
	 * @param filterModel
	 *            Model describing the filter
	 */
	public DictionaryFilterPanel(DictionaryFilter<VOType> dictionaryFilter)
	{
		this.dictionaryFilter = dictionaryFilter;
		layoutStrategy.createLayout(this, dictionaryFilter.getRootComposite());
	}

	public void clearFilter()
	{
	}

	public GenericFilterVO<VOType> getFilter()
	{
		@SuppressWarnings({ "rawtypes", "unchecked" })
		GenericFilterVO<VOType> genericFilter = new GenericFilterVO(dictionaryFilter.getModel().getVOName());

		// for (IUIObservableValue uiObservableValue : uiObservableValues)
		// {
		// if (!isCriteriaEmpty(uiObservableValue))
		// {
		// genericFilter.addCriteria(uiObservableValue.getModel().getAttributePath(),
		// uiObservableValue.getContent());
		// }
		// }

		return genericFilter;
	}

}
