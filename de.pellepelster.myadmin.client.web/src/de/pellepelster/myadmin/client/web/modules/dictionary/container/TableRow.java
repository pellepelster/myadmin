package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import java.util.List;

import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseTableModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryElementUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ControlFactoryFunction;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.EditorVOWrapper;

public class TableRow<VOType extends IBaseVO, ModelType extends IBaseTableModel> extends BaseDictionaryElement<ModelType> implements
		IBaseTable.ITableRow<VOType>
{
	private List<BaseDictionaryControl<?, ?>> columns;

	private final EditorVOWrapper<VOType> voWrapper;

	public TableRow(VOType vo, BaseTableElement<VOType, ModelType> parent)
	{
		super(parent.getModel(), parent);

		this.columns = Lists.transform(parent.getModel().getControls(), new ControlFactoryFunction(this));
		this.voWrapper = new EditorVOWrapper<VOType>(vo);
	}

	@Override
	public EditorVOWrapper<VOType> getVOWrapper()
	{
		return this.voWrapper;
	}

	@Override
	public VOType getVO()
	{
		return this.getVOWrapper().getVO();
	}

	public List<BaseDictionaryControl<?, ?>> getColumns()
	{
		return this.columns;
	}

	// TODO clean up his mess

	@SuppressWarnings("unchecked")
	@Override
	public <ElementType extends IBaseControl> ElementType getElement(BaseModel<ElementType> baseModel)
	{
		List<String> parentModelIds = DictionaryElementUtil.getParentModelIds(getParent().getModel());
		List<String> controlModelIds = DictionaryElementUtil.getParentModelIds(baseModel);

		DictionaryElementUtil.removeLeadingModelIds(parentModelIds, controlModelIds);

		return (ElementType) DictionaryElementUtil.getControl(this, controlModelIds);
	}

	@Override
	public <ElementType extends IBaseControl> ElementType getElement(IBaseControlModel baseControlModel)
	{
		return (ElementType) DictionaryElementUtil.getControl(this, Lists.newArrayList(baseControlModel.getName()));
	}

	@Override
	public List<? extends BaseDictionaryElement<?>> getAllChildren()
	{
		return this.columns;
	}

}