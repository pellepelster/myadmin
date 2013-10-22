package de.pellepelster.myadmin.client.web.modules.dictionary;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryControlDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.IDictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseRootModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseContainer;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.BaseRootElement;

public class DictionaryElementUtil
{
	public static <ControlType extends IBaseControl<?>> ControlType getControl(BaseRootElement baseRootElement,
			DictionaryControlDescriptor<ControlType> controlDescriptor)
	{
		List<String> modelIds = getModelIds(controlDescriptor);

		int lastIndex = modelIds.size()-1;
		String lastModelId = modelIds.get(lastIndex);
		modelIds.remove(lastIndex);
		
		if (!baseRootElement.getModel().getName().equals(lastModelId))
		{
			return null;
		}
			
		return getControl(baseRootElement.getRootComposite(), modelIds);
		
	}
	
	public static <ControlType extends IBaseControl<?>> ControlType getControl(BaseContainer<?> baseContainer,
			List<String> modelIds)
	{
		int i = modelIds.size()-1;

		BaseContainer<?> currentModelElement = baseContainer;

		while (currentModelElement.getModel().getName().equals(modelIds.get(i)))
		{
			currentModelElement = getMatchingContainer(modelIds.get(i), currentModelElement);
			i--;
		}

		return null;
	}

	private static BaseContainer<?> getMatchingContainer(String modelId, BaseContainer<?> parentBaseContainer)
	{
		for (BaseContainer<?> baseContainer : parentBaseContainer.getChildren())
		{
			if (baseContainer.getModel().getName().equals(modelId))
			{
				return baseContainer;
			}
		}

		return null;
	}
	
	private static BaseControl<?> getMatchingControl(String modelId, BaseContainer<?> parentBaseContainer)
	{
		for (BaseControl<?> baseControl : parentBaseContainer.getControls())
		{
			if (baseControl.getModel().getName().equals(modelId))
			{
				return baseControl;
			}
		}

		return null;
	}

	private static List<String> getModelIds(DictionaryControlDescriptor<?> controlDescriptor)
	{
		List<String> modelIds = new ArrayList<String>();

		IDictionaryDescriptor currentDescriptor = controlDescriptor;

		while (currentDescriptor != null)
		{
			modelIds.add(currentDescriptor.getId());
			currentDescriptor = currentDescriptor.getParent();
		}

		return modelIds;
	}
}
