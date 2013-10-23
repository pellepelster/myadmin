package de.pellepelster.myadmin.client.web.modules.dictionary;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseContainer;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.BaseRootElement;

public class DictionaryElementUtil
{
	public static <ElementType> ElementType getElement(BaseRootElement baseRootElement,
			DictionaryDescriptor<ElementType> dictionaryDescriptor)
	{
		List<String> modelIds = getModelIds(dictionaryDescriptor);

		String firstModelId = modelIds.get(0);
		modelIds.remove(0);

		if (!baseRootElement.getModel().getName().equals(firstModelId))
		{
			return null;
		}

		return getControl(baseRootElement.getRootComposite().getChildren(), modelIds, 0);

	}

	private static <ControlType extends IBaseControl> ControlType getControl(List<BaseContainer> baseContainers, List<String> modelIds, int level)
	{
		for (BaseContainer<?> baseContainer : baseContainers)
		{
			if (baseContainer.getModel().getName().equals(modelIds.get(level)))
			{
				IBaseControl baseControl = getMatchingControl(baseContainer.getControls(), modelIds, level + 1);

				if (baseControl != null)
				{
					return (ControlType) baseControl;
				}
				else
				{
					ControlType controlType = getControl(baseContainer.getChildren(), modelIds, level + 1);

					if (controlType != null)
					{
						return controlType;
					}
				}
			}
		}

		return null;
	}

	private static BaseControl getMatchingControl(List<BaseControl> baseControls, List<String> modelIds, int level)
	{
		for (BaseControl<?, ?> baseControl : baseControls)
		{
			if (baseControl.getModel().getName().equals(modelIds.get(level)))
			{
				return baseControl;
			}
		}

		return null;
	}

	private static List<String> getModelIds(DictionaryDescriptor<?> dictionaryDescriptor)
	{
		List<String> modelIds = new ArrayList<String>();

		DictionaryDescriptor currentDescriptor = dictionaryDescriptor;

		while (currentDescriptor != null)
		{
			modelIds.add(0, currentDescriptor.getId());
			currentDescriptor = currentDescriptor.getParent();
		}

		return modelIds;
	}
}
