package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import java.util.List;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IReferenceControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public class BaseReferenceControlModel<VOType extends IBaseVO> extends BaseControlModel<IReferenceControl<VOType>> implements IReferenceControlModel
{

	private static final long serialVersionUID = -9089131170958607211L;

	public BaseReferenceControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getDictionaryName()
	{
		throw new RuntimeException("no dictionary name set");
	}

	@Override
	public List<IBaseControlModel> getLabelControls()
	{
		throw new RuntimeException("no labels controls set");
	}

	@Override
	public CONTROL_TYPE getControlType()
	{
		return CONTROL_TYPE.TEXT;
	}

}
