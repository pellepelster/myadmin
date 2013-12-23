package de.pellepelster.myadmin.client.base.modules.dictionary.model.containers;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IAssignmentTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public class AssignmentTableModel<VOType extends IBaseVO> extends BaseTableModel<IAssignmentTable<VOType>> implements IAssignmentTableModel
{

	private static final long serialVersionUID = 1832725605229414533L;

	private String dictionaryName;

	private String attributePath;

	public AssignmentTableModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getAttributePath()
	{
		return this.attributePath;
	}

	@Override
	public String getDictionaryName()
	{
		return this.dictionaryName;
	}

	public void setDictionaryName(String dictionaryName)
	{
		this.dictionaryName = dictionaryName;
	}

	public void setAttributePath(String attributePath)
	{
		this.attributePath = attributePath;
	}

}
