package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.BaseDictionary;

public abstract class BaseControl<ModelType extends IBaseControlModel> extends BaseDictionary<ModelType>
{

	public BaseControl(ModelType baseControlModel)
	{
		super(baseControlModel);
	}

}
