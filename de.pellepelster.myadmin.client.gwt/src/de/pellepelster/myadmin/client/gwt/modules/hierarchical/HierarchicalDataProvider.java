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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

import de.pellepelster.myadmin.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;

public class HierarchicalDataProvider extends AsyncDataProvider<DictionaryHierarchicalNodeVO>
{
	private HierarchicalConfigurationVO hierarchicalConfiguration;

	private DictionaryHierarchicalNodeVO hierarchicalNode;

	private boolean showAddNodes;

	public HierarchicalDataProvider(HierarchicalConfigurationVO hierarchicalConfiguration, DictionaryHierarchicalNodeVO hierarchicalNode, boolean showAddNodes)
	{
		this.hierarchicalConfiguration = hierarchicalConfiguration;
		this.hierarchicalNode = hierarchicalNode;
		this.showAddNodes = showAddNodes;
	}

	@Override
	protected void onRangeChanged(HasData<DictionaryHierarchicalNodeVO> display)
	{
		Range range = display.getVisibleRange();
		int start = range.getStart();
		int length = range.getLength();

		Long parentId = null;
		String parentClassname = null;

		final List<DictionaryHierarchicalNodeVO> addNodes = new ArrayList<DictionaryHierarchicalNodeVO>();

		if (hierarchicalNode != null)
		{
			parentId = hierarchicalNode.getVoId();
			parentClassname = hierarchicalNode.getVoClassName();

			if (showAddNodes)
			{
				for (Map.Entry<String, List<String>> entry : hierarchicalConfiguration.getDictionaryHierarchy().entrySet())
				{
					if (entry.getValue().contains(hierarchicalNode.getDictionaryName()))
					{
						DictionaryHierarchicalNodeVO addHierarchicalNode = new DictionaryHierarchicalNodeVO();
						addHierarchicalNode.setDictionaryName(entry.getKey());
						addHierarchicalNode.setLabel(DictionaryUtil.getDictionaryAdd(DictionaryModelProvider.getCachedDictionaryModel(entry.getKey())));
						addHierarchicalNode.setHasChildren(false);
						addHierarchicalNode.setParentClassName(parentClassname);
						addHierarchicalNode.setParentVOId(parentId);
						addNodes.add(addHierarchicalNode);
					}
				}
			}
		}
		else
		{
			if (showAddNodes)
			{
				for (Map.Entry<String, List<String>> entry : hierarchicalConfiguration.getDictionaryHierarchy().entrySet())
				{
					if (entry.getValue().size() == 1 && entry.getValue().get(0) == null)
					{
						DictionaryHierarchicalNodeVO addHierarchicalNode = new DictionaryHierarchicalNodeVO();
						addHierarchicalNode.setDictionaryName(entry.getKey());
						addHierarchicalNode.setLabel(DictionaryUtil.getDictionaryAdd(DictionaryModelProvider.getCachedDictionaryModel(entry.getKey())));
						addHierarchicalNode.setHasChildren(false);
						addNodes.add(addHierarchicalNode);
					}
				}
			}
		}

		MyAdmin.getInstance().getRemoteServiceLocator().getHierachicalService()
				.getChildNodes(hierarchicalConfiguration.getId(), parentId, parentClassname, new AsyncCallback<List<DictionaryHierarchicalNodeVO>>()
				{
					@Override
					public void onFailure(Throwable caught)
					{
						throw new RuntimeException(caught);
					}

					@Override
					public void onSuccess(List<DictionaryHierarchicalNodeVO> result)
					{
						result.addAll(addNodes);
						updateRowData(0, result);
					}
				});
	}
}