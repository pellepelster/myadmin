package de.pellepelster.myadmin.client.web.modules.hierarchical.hooks;

import java.util.HashMap;
import java.util.Map;

import de.pellepelster.myadmin.client.base.util.SimpleCallback;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;

public class HierarchicalHookRegistry
{
	private static HierarchicalHookRegistry instance;

	private Map<String, SimpleCallback<DictionaryHierarchicalNodeVO>> hierarchicalTreeActivationHooks = new HashMap<String, SimpleCallback<DictionaryHierarchicalNodeVO>>();

	private HierarchicalHookRegistry()
	{
	}

	public static HierarchicalHookRegistry getInstance()
	{
		if (instance == null)
		{
			instance = new HierarchicalHookRegistry();
		}

		return instance;
	}

	public void addActivationHook(String treeId, SimpleCallback<DictionaryHierarchicalNodeVO> hierarchicalTreeActivationHook)
	{
		if (this.hierarchicalTreeActivationHooks.containsKey(treeId))
		{
			throw new RuntimeException("hook already registered for tree '" + treeId + "'");
		}

		this.hierarchicalTreeActivationHooks.put(treeId, hierarchicalTreeActivationHook);
	}

	public boolean hasActivationHook(String treeId)
	{
		return this.hierarchicalTreeActivationHooks.get(treeId) != null;
	}

	public SimpleCallback<DictionaryHierarchicalNodeVO> getActivationHook(String treeId)
	{
		return this.hierarchicalTreeActivationHooks.get(treeId);
	}

}
