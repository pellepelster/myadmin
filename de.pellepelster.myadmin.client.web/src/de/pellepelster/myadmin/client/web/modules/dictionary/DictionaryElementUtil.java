package de.pellepelster.myadmin.client.web.modules.dictionary;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.BaseContainer;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.TableRow;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
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

	private static <ElementType> ElementType getElement(List<BaseContainer<?>> baseContainers, List<String> modelIds, int level)
	{
		for (BaseContainer<?> baseContainer : baseContainers)
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

	private static BaseControl<?, ?> getControl(List<BaseControl<?, ?>> baseControls, List<String> modelIds, int level)
	{
		if (level < modelIds.size())
		{
			for (BaseControl<?, ?> baseControl : baseControls)
			{
				if (baseControl.getModel().getName().equals(modelIds.get(level)) && level == modelIds.size() - 1)
				{
					return baseControl;
				}
			}
		}

		return null;
	}

	private static List<String> getModelIds(DictionaryDescriptor<?> dictionaryDescriptor)
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

	public static <VOType extends IBaseVO> List<IBaseTable.ITableRow<VOType>> vos2TableRows(List<VOType> vos)
	{
		List<IBaseTable.ITableRow<VOType>> result = new ArrayList<IBaseTable.ITableRow<VOType>>();

		for (VOType vo : vos)
		{
			result.add(new TableRow<VOType>(vo));
		}

		return result;
	}

}
