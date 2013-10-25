package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public abstract class BaseControl<ModelType extends IBaseControlModel, ValueType> extends BaseModelElement<ModelType> implements IBaseControl<ValueType>
{

	public BaseControl(ModelType baseControlModel, BaseModelElement<? extends IBaseModel> parent)
	{
		super(baseControlModel, parent);
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

	@Override
	public ValueType getValue()
	{
		return (ValueType) getVOWrapper().get(getModel().getAttributePath());
	}

	@Override
	public void setValue(ValueType value)
	{
		getVOWrapper().set(getModel().getAttributePath(), value);
	}

	@Override
	public ModelType getModel()
	{
		return super.getModel();
	}

	public String format()
	{
		if (getValue() != null)
		{
			return getValue().toString();
		}
		else
		{
			return "";
		}
	}
}
