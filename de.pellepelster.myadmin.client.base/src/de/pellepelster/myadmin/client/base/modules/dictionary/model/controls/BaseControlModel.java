package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import com.google.common.base.Objects;

import de.pellepelster.myadmin.client.base.jpql.RelationalOperator;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseControlModel<ControlElementType> extends BaseModel<ControlElementType> implements IBaseControlModel
{

	private static final long serialVersionUID = 6300062992351577766L;

	public BaseControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getAttributePath()
	{
		return null;
	}

	@Override
	public String getColumnLabel()
	{
		return getName();
	}

	@Override
	public String getEditorLabel()
	{
		return getName();
	}

	@Override
	public String getFilterLabel()
	{
		return getName();
	}

	@Override
	public RelationalOperator[] getRelationalOperators()
	{
		return new RelationalOperator[0];
	}

	@Override
	public String getToolTip()
	{
		return getName();
	}

	@Override
	public Integer getWidthHint()
	{
		return IBaseControlModel.DEFAULT_WIDTH_HINT;
	}

	@Override
	public boolean isMandatory()
	{
		return false;
	}

	@Override
	public boolean isReadonly()
	{
		return false;
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this).add("name", getName()).toString();
	}
}
