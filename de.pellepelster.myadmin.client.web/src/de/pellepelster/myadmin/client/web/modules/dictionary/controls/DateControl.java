package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public class DateControl extends BaseControl<IDateControlModel, Date>
{

	public DateControl(IDateControlModel dateControlModel, VOWrapper<IBaseVO> voWrapper)
	{
		super(dateControlModel, voWrapper);
	}

	@Override
	public String format()
	{

		if (getValue() != null && getValue() instanceof Date)
		{
			DateTimeFormat dateFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
			return dateFormat.format(getValue());
		}
		else
		{
			return super.format();
		}
	}

}
