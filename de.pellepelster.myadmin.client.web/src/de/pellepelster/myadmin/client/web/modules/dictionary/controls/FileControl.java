package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IFileControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IFileControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class FileControl extends BaseDictionaryControl<IFileControlModel, byte[]> implements IFileControl
{

	public FileControl(IFileControlModel fileControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(fileControlModel, parent);
	}

	public void setTempFileId(String tempFileId)
	{
		getRootElement().g
	}

	@Override
	protected ParseResult parseValueInternal(String valueString)
	{
		return null;
	}

}
