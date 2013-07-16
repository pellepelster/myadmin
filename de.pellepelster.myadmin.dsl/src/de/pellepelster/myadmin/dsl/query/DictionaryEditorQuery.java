package de.pellepelster.myadmin.dsl.query;

import java.util.Iterator;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEditor;
import de.pellepelster.myadmin.dsl.query.functions.FunctionEObjectTypeSelect;

public class DictionaryEditorQuery
{
	private DictionaryEditor dictionaryEditor;

	public DictionaryEditorQuery(DictionaryEditor dictionaryEditor)
	{
		this.dictionaryEditor = dictionaryEditor;
	}

	public DictionaryEditor getDictionaryEditor()
	{
		return this.dictionaryEditor;
	}

	public DictionaryControlsQuery<?> getAllControls()
	{
		Iterator<DictionaryControl> dictionaryControls = Iterators.transform(this.dictionaryEditor.eAllContents(),
				FunctionEObjectTypeSelect.getFunction(DictionaryControl.class));

		return new DictionaryControlsQuery(Lists.newArrayList(Iterators.filter(dictionaryControls, Predicates.notNull())));
	}

	public DictionaryControlsQuery<?> getControlsByType(Class<? extends DictionaryControl> controlType)
	{
		Iterator<DictionaryControl> dictionaryControls = Iterators.transform(this.dictionaryEditor.eAllContents(),
				FunctionEObjectTypeSelect.getFunction(controlType));

		return new DictionaryControlsQuery(Lists.newArrayList(Iterators.filter(dictionaryControls, Predicates.notNull())));
	}

}
