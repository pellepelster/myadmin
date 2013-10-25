package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.math.BigDecimal;

import com.google.gwt.i18n.client.NumberFormat;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;

public class BigDecimalControl extends BaseControl<IBigDecimalControlModel, BigDecimal>
{

	public BigDecimalControl(IBigDecimalControlModel decimalControlModel, BaseModelElement<? extends IBaseModel> parent)
	{
		super(decimalControlModel, parent);
	}

	@Override
	public String format()
	{
		if (getValue() != null && getValue() instanceof BigDecimal)
		{
			return NumberFormat.getDecimalFormat().format(getValue());
		}
		else
		{
			return super.format();
		}
	}
}
