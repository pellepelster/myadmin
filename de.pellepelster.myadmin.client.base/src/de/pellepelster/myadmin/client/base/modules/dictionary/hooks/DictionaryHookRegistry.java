package de.pellepelster.myadmin.client.base.modules.dictionary.hooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.modules.dictionary.BaseDictionaryElementUtil;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryControlDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IFileControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IFileControlModel;

@SuppressWarnings("rawtypes")
public class DictionaryHookRegistry
{
	private static DictionaryHookRegistry instance;

	private Map<String, IEditorSaveHook> editorSaveHooks = new HashMap<String, IEditorSaveHook>();

	private Map<String, List<BaseEditorHook>> editorHooks = new HashMap<String, List<BaseEditorHook>>();

	private Map<String, IFileControlHook> fileControlHooks = new HashMap<String, IFileControlHook>();

	private DictionaryHookRegistry()
	{
	}

	public static DictionaryHookRegistry getInstance()
	{
		if (instance == null)
		{
			instance = new DictionaryHookRegistry();
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

	public void setFileControlHook(DictionaryControlDescriptor<IFileControl> fileControlDescriptor, IFileControlHook fileControlHook)
	{
		this.fileControlHooks.put(BaseDictionaryElementUtil.getModelId(fileControlDescriptor), fileControlHook);
	}

	public IFileControlHook getFileControlHook(IFileControlModel fileControlModel)
	{
		return this.fileControlHooks.get(BaseDictionaryElementUtil.getModelId(fileControlModel));
	}

	public boolean hasFileControlHook(IFileControlModel fileControlModel)
	{
		return getFileControlHook(fileControlModel) != null;
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
