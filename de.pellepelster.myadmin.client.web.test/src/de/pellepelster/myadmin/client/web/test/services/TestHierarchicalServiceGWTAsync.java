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
package de.pellepelster.myadmin.client.web.test.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.modules.hierarchical.HierarchicalConfigurationVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;
import de.pellepelster.myadmin.client.web.services.IHierachicalServiceGWTAsync;
import de.pellepelster.myadmin.client.web.test.vo.HierarchicalTest1VO;
import de.pellepelster.myadmin.client.web.test.vo.HierarchicalTest2VO;

public class TestHierarchicalServiceGWTAsync implements IHierachicalServiceGWTAsync
{

	public static final String HIERARCHICAL_TREE1 = "HierarchicalTree1";

	private final List<DictionaryHierarchicalNodeVO> tree = new ArrayList<DictionaryHierarchicalNodeVO>();

	private HierarchicalConfigurationVO hierarchicalConfiguration;

	public TestHierarchicalServiceGWTAsync()
	{
		createDictionaryHierarchicalNodeVOs(this.tree, 6, 0, "", 0l);
	}

	private void createDictionaryHierarchicalNodeVOs(List<DictionaryHierarchicalNodeVO> tree, int nodeCount, int currentLevel, String parentLabel, Long parentId)
	{
		if (currentLevel >= 3)
		{
			return;
		}

		for (int i = 1; i < nodeCount; i++)
		{
			String label;
			Long id = Long.parseLong(parentId.toString() + i);

			if (parentLabel == null || parentLabel.isEmpty())
			{
				label = "Node " + i;
			}
			else
			{
				label = parentLabel + "." + i;
			}

			DictionaryHierarchicalNodeVO hierarchicalNodeVO = new DictionaryHierarchicalNodeVO();
			hierarchicalNodeVO.setLabel(label);
			hierarchicalNodeVO.setId(id);
			hierarchicalNodeVO.setVoId(id);
			hierarchicalNodeVO.setVoClassName("de.pellepelster.myadmin.client.web.test.HierarchicalTest" + (currentLevel + 1) + "VO");
			hierarchicalNodeVO.setDictionaryName("HierarchicalDictionary" + (currentLevel + 1));
			tree.add(hierarchicalNodeVO);

			createDictionaryHierarchicalNodeVOs(hierarchicalNodeVO.getChildren(), nodeCount, currentLevel + 1, label, id);
		}

	}

	private DictionaryHierarchicalNodeVO getByParentId(List<DictionaryHierarchicalNodeVO> list, long parentId)
	{
		DictionaryHierarchicalNodeVO result = null;

		for (DictionaryHierarchicalNodeVO dictionaryHierarchicalNodeVO : list)
		{
			if (dictionaryHierarchicalNodeVO.getVoId().equals(parentId))
			{
				return dictionaryHierarchicalNodeVO;
			}

			result = getByParentId(dictionaryHierarchicalNodeVO.getChildren(), parentId);

			if (result != null)
			{
				return result;
			}
		}

		return null;
	}

	private List<DictionaryHierarchicalNodeVO> getByParentId(List<IHierarchicalVO> list, Long voId)
	{
		List<DictionaryHierarchicalNodeVO> result = new ArrayList<DictionaryHierarchicalNodeVO>();

		for (IHierarchicalVO hierarchicalVO : list)
		{
			if (hierarchicalVO.getParent() != null && hierarchicalVO.getParent().getId() == voId)
			{
				DictionaryHierarchicalNodeVO hierarchicalNodeVO = new DictionaryHierarchicalNodeVO();
				hierarchicalNodeVO.setLabel("xxx");
				hierarchicalNodeVO.setId(99);
				hierarchicalNodeVO.setVoId(hierarchicalVO.getId());
				hierarchicalNodeVO.setVoClassName(hierarchicalVO.getClass().getName());
				hierarchicalNodeVO.setDictionaryName("HierarchicalDictionary1");
				hierarchicalNodeVO.setHasChildren(false);
				result.add(hierarchicalNodeVO);
			}
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void getChildNodes(String id, Long voId, String voClassName, AsyncCallback<List<DictionaryHierarchicalNodeVO>> callback)
	{
		if (voId == null && voClassName == null)
		{
			getRootNodes(id, callback);
		}
		else
		{
			List<DictionaryHierarchicalNodeVO> result = new ArrayList<DictionaryHierarchicalNodeVO>();
			result.addAll(flattenList(getByParentId(this.tree, voId).getChildren()));

			if (HierarchicalTest1VO.class.getName().equals(voClassName))
			{
				result.addAll(getByParentId(TestBaseEntityServiceGWTAsync.hierarchicalTest2VOs, voId));
			}

			if (HierarchicalTest2VO.class.getName().equals(voClassName))
			{
				result.addAll(getByParentId(TestBaseEntityServiceGWTAsync.hierarchicalTest3VOs, voId));
			}

			callback.onSuccess(result);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void getConfigurationById(String id, AsyncCallback<HierarchicalConfigurationVO> callback)
	{
		if (this.hierarchicalConfiguration == null)
		{
			this.hierarchicalConfiguration = new HierarchicalConfigurationVO();
			this.hierarchicalConfiguration.getDictionaryHierarchy().put(TestDictionaryServiceGWTAsync.HIERARCHICAL_DICTIONARY1_ID,
					Arrays.asList(new String[] { null }));
			this.hierarchicalConfiguration.getDictionaryHierarchy().put(TestDictionaryServiceGWTAsync.HIERARCHICAL_DICTIONARY2_ID,
					Arrays.asList(new String[] { TestDictionaryServiceGWTAsync.HIERARCHICAL_DICTIONARY1_ID }));
			this.hierarchicalConfiguration.getDictionaryHierarchy().put(TestDictionaryServiceGWTAsync.HIERARCHICAL_DICTIONARY3_ID,
					Arrays.asList(new String[] { TestDictionaryServiceGWTAsync.HIERARCHICAL_DICTIONARY2_ID }));
			this.hierarchicalConfiguration.setTitle(HIERARCHICAL_TREE1);
		}

		callback.onSuccess(this.hierarchicalConfiguration);
	}

	private List<DictionaryHierarchicalNodeVO> flattenList(List<DictionaryHierarchicalNodeVO> list)
	{
		List<DictionaryHierarchicalNodeVO> result = new ArrayList<DictionaryHierarchicalNodeVO>();

		for (DictionaryHierarchicalNodeVO dictionaryHierarchicalNodeVO : list)
		{
			DictionaryHierarchicalNodeVO newNode = dictionaryHierarchicalNodeVO.cloneVO();
			newNode.setHasChildren(!newNode.getChildren().isEmpty());
			newNode.getChildren().clear();

			result.add(newNode);
		}

		return result;
	}

	/** {@inheritDoc} */
	@Override
	public void getRootNodes(String id, AsyncCallback<List<DictionaryHierarchicalNodeVO>> callback)
	{
		callback.onSuccess(flattenList(this.tree));
	}

	@Override
	public void hasChildren(String voClassName, Long voId, AsyncCallback<Boolean> callback)
	{
	}

	@Override
	public void getConfigurations(AsyncCallback<List<HierarchicalConfigurationVO>> callback)
	{
		Arrays.asList(new HierarchicalConfigurationVO[] { this.hierarchicalConfiguration });
	}
}
