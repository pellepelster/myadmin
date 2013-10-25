package de.pellepelster.myadmin.client.web.modules.dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseContainerElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.TableRow;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.BaseRootElement;

public class DictionaryElementUtil
{
	public static <ElementType> ElementType getElement(BaseRootElement<?> baseRootElement, DictionaryDescriptor<ElementType> dictionaryDescriptor)
	{
		List<String> modelIds = getModelIds(dictionaryDescriptor);

		String firstModelId = modelIds.get(0);
		modelIds.remove(0);

		if (!baseRootElement.getModel().getName().equals(firstModelId))
		{
			return null;
		}

		return getElement(baseRootElement.getRootComposite().getChildren(), modelIds, 0);

	}

	private static <ElementType> ElementType getElement(List<BaseContainerElement<?>> baseContainers, List<String> modelIds, int level)
	{
		for (BaseContainerElement<?> baseContainer : baseContainers)
		{
			if (baseContainer.getModel().getName().equals(modelIds.get(level)))
			{

				if (level == modelIds.size() - 1)
				{
					return (ElementType) baseContainer;
				}
				else
				{
					IBaseControl baseControl = getControl(baseContainer.getControls(), modelIds, level + 1);

					if (baseControl != null)
					{
						return (ElementType) baseControl;
					}
					else
					{
						ElementType controlType = getElement(baseContainer.getChildren(), modelIds, level + 1);

						if (controlType != null)
						{
							return controlType;
						}
					}
				}
			}
		}

		return null;
	}

	public static BaseDictionaryControl<?, ?> getControl(TableRow<?, ?> tableRow, List<String> modelIds)
	{
		return getControl(tableRow.getColumns(), modelIds, 0);
	}

	private static BaseDictionaryControl<?, ?> getControl(List<BaseDictionaryControl<?, ?>> baseControls, List<String> modelIds, int level)
	{
		if (level < modelIds.size())
		{
			for (BaseDictionaryControl<?, ?> baseControl : baseControls)
			{
				if (baseControl.getModel().getName().equals(modelIds.get(level)) && level == modelIds.size() - 1)
				{
					return baseControl;
				}
			}
		}

		return null;
	}

	public static List<String> getModelIds(DictionaryDescriptor<?> dictionaryDescriptor)
	{
		List<String> modelIds = new ArrayList<String>();

		DictionaryDescriptor<?> currentDescriptor = dictionaryDescriptor;

		while (currentDescriptor != null)
		{
			modelIds.add(0, currentDescriptor.getId());
			currentDescriptor = currentDescriptor.getParent();
		}

		return modelIds;
	}

	public static List<String> getParentModelIds(BaseDictionaryElement<? extends IBaseModel> baseDictionaryElement)
	{
		List<String> modelIds = new ArrayList<String>();

		BaseDictionaryElement<? extends IBaseModel> currentBaseDictionaryElement = baseDictionaryElement;

		while (currentBaseDictionaryElement != null)
		{
			modelIds.add(0, currentBaseDictionaryElement.getModel().getName());
			currentBaseDictionaryElement = currentBaseDictionaryElement.getParent();
		}

		return modelIds;
	}

	public static void removeLeadingModelIds(List<String> modelIdsToRemove, List<String> modelIds)
	{

		Iterator<String> modelIdsToRemoveIterator = modelIdsToRemove.iterator();
		Iterator<String> modelIdsIterator = modelIds.iterator();

		while (modelIdsToRemoveIterator.hasNext() && modelIdsIterator.hasNext())
		{
			String modelIdToRemove = modelIdsToRemoveIterator.next();
			String modelId = modelIdsIterator.next();

			if (modelId.equals(modelIdToRemove))
			{
				modelIdsIterator.remove();
			}
		}

	}

}
