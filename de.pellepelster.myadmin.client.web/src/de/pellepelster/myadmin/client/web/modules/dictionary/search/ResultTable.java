package de.pellepelster.myadmin.client.web.modules.dictionary.search;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IResultModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ControlFactory;

public class ResultTable extends BaseModelElement<IResultModel>
{
	private List<BaseControl<?, ?>> controls = Collections.emptyList();

	public ResultTable(IResultModel resultModel, BaseModelElement<IBaseModel> parent)
	{
		super(resultModel, parent);

		this.controls = Lists.transform(resultModel.getControls(), new Function<IBaseControlModel, BaseControl<?, ?>>()
		{
			@Override
			@Nullable
			public BaseControl<IBaseControlModel, ?> apply(IBaseControlModel baseControlModel)
			{
				return ControlFactory.getInstance().createControl(baseControlModel, getParent());
			}
		});
	}

	public List<BaseControl<?, ?>> getControls()
	{
		return this.controls;
	}
}
