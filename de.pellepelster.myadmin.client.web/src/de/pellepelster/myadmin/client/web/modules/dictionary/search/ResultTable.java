package de.pellepelster.myadmin.client.web.modules.dictionary.search;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IResultModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ControlFactory;

public class ResultTable extends BaseDictionaryElement<IResultModel>
{
	private List<BaseDictionaryControl<?, ?>> controls = Collections.emptyList();

	public ResultTable(IResultModel resultModel, BaseDictionaryElement<IBaseModel> parent)
	{
		super(resultModel, parent);

		this.controls = Lists.transform(resultModel.getControls(), new Function<IBaseControlModel, BaseDictionaryControl<?, ?>>()
		{
			@Override
			@Nullable
			public BaseDictionaryControl<IBaseControlModel, ?> apply(IBaseControlModel baseControlModel)
			{
				return ControlFactory.getInstance().createControl(baseControlModel, getParent());
			}
		});
	}

	public List<BaseDictionaryControl<?, ?>> getControls()
	{
		return this.controls;
	}
}
