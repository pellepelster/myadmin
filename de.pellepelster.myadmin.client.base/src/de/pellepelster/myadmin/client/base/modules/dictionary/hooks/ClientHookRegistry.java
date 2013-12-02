package de.pellepelster.myadmin.client.base.modules.dictionary.hooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ClientHookRegistry
{
	private static ClientHookRegistry instance;

	private Map<String, IEditorSaveHook> editorSaveHooks = new HashMap<String, IEditorSaveHook>();

	private Map<String, List<BaseEditorHook>> editorHooks = new HashMap<String, List<BaseEditorHook>>();

	private ClientHookRegistry()
	{
	}

	public static ClientHookRegistry getInstance()
	{
		if (instance == null)
		{
			instance = new ClientHookRegistry();
		}

		return instance;
	}

	// editor save hook
	public void setEditorSaveHook(String dictionaryId, IEditorSaveHook editorSaveHook)
	{
		this.editorSaveHooks.put(dictionaryId, editorSaveHook);
	}

	public boolean hasEditorSaveHook(String dictionaryId)
	{
		return this.editorSaveHooks.containsKey(dictionaryId);
	}

	public IEditorSaveHook getEditorSaveHook(String dictionaryId)
	{
		return this.editorSaveHooks.get(dictionaryId);
	}

	// editor hook
	public void addEditorHook(String dictionaryId, BaseEditorHook editorHook)
	{
		if (this.editorHooks.get(dictionaryId) == null)
		{
			this.editorHooks.put(dictionaryId, new ArrayList<BaseEditorHook>());
		}

		this.editorHooks.get(dictionaryId).add(editorHook);
	}

	public boolean hasEditorHook(String dictionaryId)
	{
		return this.editorHooks.containsKey(dictionaryId) && !this.editorHooks.get(dictionaryId).isEmpty();
	}

	public List<BaseEditorHook> getEditorHook(String dictionaryId)
	{
		return this.editorHooks.get(dictionaryId);
	}

}
