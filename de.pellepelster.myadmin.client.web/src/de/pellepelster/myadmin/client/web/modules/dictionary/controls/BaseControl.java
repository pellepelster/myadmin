package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public abstract class BaseControl<ModelType extends IBaseControlModel> extends BaseModelElement<ModelType>
{

	public BaseControl(ModelType baseControlModel)
	{
		super(baseControlModel);
	}

	public String getEditorLabel()
	{
		String label = getModel().getEditorLabel();

		if (getModel().isMandatory())
		{
			label += MyAdmin.MESSAGES.mandatoryMarker();
		}

		return label;
	}

	public String getFilterLabel()
	{
		return getModel().getFilterLabel();
	}

}
