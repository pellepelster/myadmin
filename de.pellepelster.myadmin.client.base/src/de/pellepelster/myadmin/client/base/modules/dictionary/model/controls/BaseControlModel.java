package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import com.google.common.base.Objects;

import de.pellepelster.myadmin.client.base.jpql.RelationalOperator;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.BaseContainerModel;

public abstract class BaseControlModel<ControlElementType> extends BaseModel<ControlElementType> implements IBaseControlModel
{

	private static final long serialVersionUID = 6300062992351577766L;

	private boolean readonly;

	private String filterLabel;

	private String editorLabel;

	private String columnLabel;

	private boolean mandatory;

	private String toolTip;

	private String attributePath;

	public BaseControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	public BaseControlModel(String name, BaseContainerModel<?> parent)
	{
		super(name, parent);

		parent.getControls().add(this);
	}

	@Override
	public String getAttributePath()
	{
		return this.attributePath;
	}

	public void setAttributePath(String attributePath)
	{
		this.attributePath = attributePath;
	}

	@Override
	public String getColumnLabel()
	{
		return this.columnLabel;
	}

	@Override
	public String getEditorLabel()
	{
		return this.editorLabel;
	}

	@Override
	public String getFilterLabel()
	{
		return this.filterLabel;
	}

	@Override
	public RelationalOperator[] getRelationalOperators()
	{
		return new RelationalOperator[0];
	}

	@Override
	public String getToolTip()
	{
		return this.toolTip;
	}

	@Override
	public Integer getWidthHint()
	{
		return IBaseControlModel.DEFAULT_WIDTH_HINT;
	}

	@Override
	public boolean isMandatory()
	{
		return this.mandatory;
	}

	@Override
	public boolean isReadonly()
	{
		return this.readonly;
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this).add("name", getName()).toString();
	}

	public void setReadonly(boolean readonly)
	{
		this.readonly = readonly;
	}

	public void setFilterLabel(String filterLabel)
	{
		this.filterLabel = filterLabel;
	}

	public void setEditorLabel(String editorLabel)
	{
		this.editorLabel = editorLabel;
	}

	public void setColumnLabel(String columnLabel)
	{
		this.columnLabel = columnLabel;
	}

	public void setMandatory(boolean mandatory)
	{
		this.mandatory = mandatory;
	}

	public void setToolTip(String toolTip)
	{
		this.toolTip = toolTip;
	}
}
