package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IFileControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IFileControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.FileVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryElementUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class FileControl extends BaseDictionaryControl<IFileControlModel, Object> implements IFileControl
{

	public FileControl(IFileControlModel fileControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(fileControlModel, parent);
	}

	public String getFileName()
	{
		if (getValue() != null)
		{
			FileVO fileVO = (FileVO) getValue();
			return fileVO.getFileName();
		}
		else
		{
			return MyAdmin.MESSAGES.fileNone();
		}
	}

	@Override
	public void parseValue(String valueString)
	{
		if (valueString == null)
		{
			DictionaryElementUtil.getRootEditor(this).getVO().getData().remove(getModel().getAttributePath());
		}
		else
		{
			DictionaryElementUtil.getRootEditor(this).getVO().getData().put(getModel().getAttributePath(), valueString);
		}
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		return null;
	}

}
