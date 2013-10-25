package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import javax.annotation.Nullable;

import com.google.common.base.Function;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class ControlFunction implements Function<IBaseControlModel, BaseDictionaryControl<?, ?>>
{
	private BaseDictionaryElement<? extends IBaseModel> parent;
	
	public ControlFunction(BaseDictionaryElement<? extends IBaseModel> parent) {
		super();
		this.parent = parent;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	@Override
	@Nullable
	public BaseDictionaryControl<IBaseControlModel, Object> apply(IBaseControlModel baseControlModel)
	{
		return ControlFactory.getInstance().createControl(baseControlModel, ControlFunction.this.parent);
	}
}