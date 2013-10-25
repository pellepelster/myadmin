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

public class TableRow<VOType extends IBaseVO, ModelType extends IBaseTableModel> extends BaseDictionaryElement<ModelType> implements IBaseTable.ITableRow<VOType>
{
	private final VOType vo;

	private List<BaseDictionaryControl<?, ?>> columns;
	
	public TableRow(VOType vo, BaseTable<VOType, ModelType> parent)
	{
		super(parent.getModel(), parent);
		
		this.vo = vo;
		columns = Lists.transform(parent.getModel().getControls(), new ControlFunction(parent));
	}

	@Override
	protected VOWrapper<IBaseVO> getVOWrapper() {
		return super.getVOWrapper();
	}

	@Override
	public VOType getVO()
	{
		return this.vo;
	}

	public List<BaseDictionaryControl<?, ?>> getColumns() {
		return columns;
	}

	@Override
	public <ElementType extends IBaseControl<?>> ElementType getElement(DictionaryDescriptor<ElementType> controlDescriptor)
	{
		List<String> modelIds = DictionaryElementUtil.getParentModelIds(this);
		
		List<String> modelIds1 = DictionaryElementUtil.getModelIds(controlDescriptor);
		
		
		return null;
	}

}