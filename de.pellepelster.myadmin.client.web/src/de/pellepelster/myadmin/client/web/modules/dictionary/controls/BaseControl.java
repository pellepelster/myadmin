package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public abstract class BaseControl<ModelType extends IBaseControlModel, ValueType> extends BaseModelElement<ModelType> implements IBaseControl<ValueType>
{
	private VOWrapper<IBaseVO> voWrapper;

	public BaseControl(ModelType baseControlModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(baseControlModel);
		this.voWrapper = voWrapper;
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
		return (ValueType) this.voWrapper.get(getModel().getAttributePath());
	}

	@Override
	public void setValue(ValueType value)
	{
		this.voWrapper.set(getModel().getAttributePath(), value);
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
