package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.math.BigDecimal;

import com.google.gwt.i18n.client.NumberFormat;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class BigDecimalControl extends BaseControl<IBigDecimalControlModel>
{

	public BigDecimalControl(IBigDecimalControlModel decimalControlModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(decimalControlModel, voWrapper);
	}

	@Override
	public String format()
	{
		if (getValue() != null && getValue() instanceof BigDecimal)
		{
			return NumberFormat.getDecimalFormat().format((BigDecimal) getValue());
		}
		else
		{
			return super.format();
		}
	}
}
