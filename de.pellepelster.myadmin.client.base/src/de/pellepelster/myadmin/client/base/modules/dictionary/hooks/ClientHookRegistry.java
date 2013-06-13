package de.pellepelster.myadmin.client.base.modules.dictionary.hooks;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ClientHookRegistry
{
	private static ClientHookRegistry instance;

	private Map<String, IEditorSaveHook> editorSaveHooks = new HashMap<String, IEditorSaveHook>();

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

}
