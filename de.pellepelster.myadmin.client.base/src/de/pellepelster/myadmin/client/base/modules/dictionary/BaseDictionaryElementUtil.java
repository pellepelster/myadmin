package de.pellepelster.myadmin.client.base.modules.dictionary;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;

public class BaseDictionaryElementUtil
{

	public static String getModelId(DictionaryDescriptor<?> dictionaryDescriptor)
	{
		List<String> modelIds = getParentModelIds(dictionaryDescriptor);
		return Joiner.on("/").skipNulls().join(modelIds).toString();
	}

	public static String getModelId(IBaseModel baseModel)
	{
		List<String> modelIds = getParentModelIds(baseModel);
		return Joiner.on("/").skipNulls().join(modelIds).toString();
	}

	public static List<String> getParentModelIds(DictionaryDescriptor<?> dictionaryDescriptor)
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

	public static List<String> getParentModelIds(IBaseModel baseModel)
	{
		List<String> modelIds = new ArrayList<String>();

		IBaseModel currentModel = baseModel;

		while (currentModel != null)
		{
			if (!ICompositeModel.ROOT_COMPOSITE_NAME.equals(currentModel.getName()))
			{
				modelIds.add(0, currentModel.getName());
			}

			currentModel = currentModel.getParent();
		}

		return modelIds;
	}
}