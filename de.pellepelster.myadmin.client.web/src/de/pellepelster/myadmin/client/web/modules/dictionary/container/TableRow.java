package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import java.util.List;

import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.base.modules.dictionary.container.IBaseTable;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseTableModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryElementUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ControlFunction;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class TableRow<VOType extends IBaseVO, ModelType extends IBaseTableModel> extends BaseDictionaryElement<ModelType> implements
		IBaseTable.ITableRow<VOType>
{
	private List<BaseDictionaryControl<?, ?>> columns;

	private final VOWrapper<VOType> voWrapper;

	public TableRow(VOType vo, BaseTable<VOType, ModelType> parent)
	{
		super(parent.getModel(), parent);

		this.columns = Lists.transform(parent.getModel().getControls(), new ControlFunction(this));
		this.voWrapper = new VOWrapper<VOType>(vo);
	}

	@Override
	protected VOWrapper<VOType> getVOWrapper()
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

	@SuppressWarnings("unchecked")
	@Override
	public <ElementType extends IBaseControl<?>> ElementType getElement(DictionaryDescriptor<ElementType> controlDescriptor)
	{
		List<String> parentModelIds = DictionaryElementUtil.getParentModelIds(getParent());
		List<String> controlModelIds = DictionaryElementUtil.getModelIds(controlDescriptor);

		DictionaryElementUtil.removeLeadingModelIds(parentModelIds, controlModelIds);

		return (ElementType) DictionaryElementUtil.getControl(this, controlModelIds);
	}

}