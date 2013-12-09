package de.pellepelster.myadmin.client.web.modules.hierarchical.hooks;

import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;
import de.pellepelster.myadmin.client.web.modules.hierarchical.HierarchicalTreeModule;

public class BaseActivationHook
{
	public void onActivate(DictionaryHierarchicalNodeVO hierarchicalNodeVO)
	{
		HierarchicalTreeModule.openModuleForNode(hierarchicalNodeVO);
	}
}
