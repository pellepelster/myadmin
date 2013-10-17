package de.pellepelster.myadmin.client.web.modules.dictionary.search;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IResultModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ControlFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class ResultTable extends BaseModelElement<IResultModel>
{
	private List<BaseControl> controls = Collections.EMPTY_LIST;

	public ResultTable(IResultModel resultModel, final VOWrapper<IBaseVO> voWrapper)
	{
		super(resultModel);

		this.controls = Lists.transform(resultModel.getControls(), new Function<IBaseControlModel, BaseControl>()
		{
			@Override
			@Nullable
			public BaseControl<IBaseControlModel> apply(IBaseControlModel baseControlModel)
			{
				return ControlFactory.getInstance().createControl(baseControlModel, voWrapper);
			}
		});
	}

	public List<BaseControl> getControls()
	{
		return this.controls;
	}
}
