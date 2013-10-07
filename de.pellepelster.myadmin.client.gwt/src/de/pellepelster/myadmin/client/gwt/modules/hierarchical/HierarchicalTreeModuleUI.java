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

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.pellepelster.myadmin.client.base.db.vos.IHierarchicalVO;
import de.pellepelster.myadmin.client.base.util.CollectionUtils;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.BaseModuleUI;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public class HierarchicalTreeModuleUI extends BaseModuleUI<HierarchicalTreeModule>
{
	private final VerticalPanel verticalPanel;

	private SimpleCallback<DictionaryHierarchicalNodeVO> nodeActivatedHandler = new SimpleCallback<DictionaryHierarchicalNodeVO>()
	{

		@Override
		public void onCallback(DictionaryHierarchicalNodeVO dictionaryHierarchicalNodeVO)
		{
			if (dictionaryHierarchicalNodeVO.getVoId() == null)
			{
				HashMap<String, Object> parameters = CollectionUtils.getMap(IHierarchicalVO.FIELD_PARENT_CLASSNAME.getAttributeName(),
						dictionaryHierarchicalNodeVO.getParentClassName(), IHierarchicalVO.FIELD_PARENT_ID.getAttributeName(),
						dictionaryHierarchicalNodeVO.getParentVOId());

				DictionaryEditorModuleFactory.openEditor(dictionaryHierarchicalNodeVO.getDictionaryName(), parameters);
			}
			else
			{
				DictionaryEditorModuleFactory.openEditorForId(dictionaryHierarchicalNodeVO.getDictionaryName(), dictionaryHierarchicalNodeVO.getVoId());
			}
		}
	};

	public HierarchicalTreeModuleUI(HierarchicalTreeModule module)
	{
		super(module);

		verticalPanel = new VerticalPanel();

		HierarchicalTree hierarchicalTree = new HierarchicalTree(module.getHierarchicalConfiguration(), true, nodeActivatedHandler);
		verticalPanel.add(hierarchicalTree);
	}

	/** {@inheritDoc} */
	@Override
	public Panel getContainer()
	{
		return verticalPanel;
	}

	/** {@inheritDoc} */
	@Override
	public String getTitle()
	{
		return getModule().getHierarchicalConfiguration().getTitle();
	}

}