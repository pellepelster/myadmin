package de.pellepelster.myadmin.client.web.modules.dictionary;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryControlDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.IDictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseContainer;

public class DictionaryModelUtil
{
	public static <ControlType extends IBaseControl<?>> ControlType getControl(BaseModelElement<IBaseModel> baseModelElement,
			DictionaryControlDescriptor<ControlType> controlDescriptor)
	{
		int i = 0;
		List<String> modelIds = getModelIds(controlDescriptor);

		BaseModelElement<?> currentModelElement = baseModelElement;

		while (currentModelElement.getModel().getName().equals(modelIds.get(i)))
		{
		}

		throw new RuntimeException(String.format("control for dictionary control descriptor '%s' not found", controlDescriptor.toString()));
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
