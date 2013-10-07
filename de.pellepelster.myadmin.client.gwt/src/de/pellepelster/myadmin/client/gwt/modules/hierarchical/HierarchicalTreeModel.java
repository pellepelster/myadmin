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
package de.pellepelster.myadmin.client.gwt.modules.hierarchical;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.modules.hierarchical.HierarchicalConfiguration;
import de.pellepelster.myadmin.client.gwt.modules.hierarchical.HierarchicalNodeInfo.HierarchicalNodeCallback;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.events.VOEventHandler;
import de.pellepelster.myadmin.client.web.modules.dictionary.events.VOSavedEvent;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public class HierarchicalTreeModel implements TreeViewModel
{

	private Map<String, HierarchicalDataProvider> dataProviders = new HashMap<String, HierarchicalDataProvider>();

	private final SingleSelectionModel<DictionaryHierarchicalNodeVO> selectionModel = new SingleSelectionModel<DictionaryHierarchicalNodeVO>();

	private HierarchicalConfiguration hierarchicalConfiguration;

	private final Cell<DictionaryHierarchicalNodeVO> hierarchicalCell = new HierarchicalCell();

	private final CellList<DictionaryHierarchicalNodeVO> cellList = new CellList<DictionaryHierarchicalNodeVO>(hierarchicalCell);

	private final static String ROOT_DATAPROVIDER_KEY = "root";

	private final boolean showAddnodes;

	public HierarchicalTreeModel(HierarchicalConfiguration hierarchicalConfiguration, boolean showAddnodes,
			final SimpleCallback<DictionaryHierarchicalNodeVO> nodeActivatedHandler)
	{
		this.hierarchicalConfiguration = hierarchicalConfiguration;
		this.showAddnodes = showAddnodes;

		MyAdmin.EVENT_BUS.addHandler(VOSavedEvent.TYPE, new VOEventHandler()
		{

			@Override
			public void onVOEvent(IBaseVO baseVO)
			{
				if (baseVO instanceof IHierarchicalVO)
				{
					IHierarchicalVO hierarchicalVO = (IHierarchicalVO) baseVO;

					if (dataProviders.containsKey(getKey(hierarchicalVO.getParent())))
					{
						dataProviders.get(getKey(hierarchicalVO.getParent())).onRangeChanged(cellList);
					}

				}

			}
		});

		selectionModel.addSelectionChangeHandler(new Handler()
		{
			/** {@inheritDoc} */
			@Override
			public void onSelectionChange(SelectionChangeEvent event)
			{
				if (nodeActivatedHandler != null && selectionModel.getSelectedObject() != null)
				{
					DictionaryHierarchicalNodeVO dictionaryHierarchicalNodeVO = selectionModel.getSelectedObject();
					nodeActivatedHandler.onCallback(dictionaryHierarchicalNodeVO);
				}
			}
		});

	}

	private String getKey(DictionaryHierarchicalNodeVO dictionaryHierarchicalNodeVO)
	{
		if (dictionaryHierarchicalNodeVO == null)
		{
			return ROOT_DATAPROVIDER_KEY;
		}
		else
		{
			return dictionaryHierarchicalNodeVO.getVoClassName() + "#" + dictionaryHierarchicalNodeVO.getVoId().toString();
		}
	}

	private String getKey(IHierarchicalVO hierarchicalVO)
	{
		if (hierarchicalVO == null)
		{
			return ROOT_DATAPROVIDER_KEY;
		}
		else
		{
			return hierarchicalVO.getClass().getName() + "#" + hierarchicalVO.getId();
		}
	}

	@Override
	public boolean isLeaf(Object value)
	{
		return (value instanceof DictionaryHierarchicalNodeVO && !((DictionaryHierarchicalNodeVO) value).getHasChildren());
	}

	@Override
	public <T> NodeInfo<?> getNodeInfo(T value)
	{
		if (value == null || value instanceof DictionaryHierarchicalNodeVO)
		{
			final DictionaryHierarchicalNodeVO hierarchicalNodeVO = (DictionaryHierarchicalNodeVO) value;
			HierarchicalDataProvider dataProvider = new HierarchicalDataProvider(hierarchicalConfiguration, hierarchicalNodeVO, showAddnodes);
			dataProvider.addDataDisplay(cellList);
			dataProviders.put(getKey(hierarchicalNodeVO), dataProvider);

			return new HierarchicalNodeInfo(dataProvider, hierarchicalCell, selectionModel, new HierarchicalNodeCallback()
			{
				@Override
				public void onUnsetDataDisplay()
				{
					dataProviders.remove(getKey(hierarchicalNodeVO));
				}
			});
		}
		else
		{
			throw new RuntimeException("unsupported tree node type '" + value.getClass().getName() + "'");
		}
	}
}