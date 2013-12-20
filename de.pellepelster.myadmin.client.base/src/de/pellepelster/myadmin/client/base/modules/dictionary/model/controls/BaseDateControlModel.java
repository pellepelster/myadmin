package de.pellepelster.myadmin.client.base.modules.dictionary.model.controls;

import com.google.gwt.i18n.client.DateTimeFormat;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IDateControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseDateControlModel extends BaseControlModel<IDateControl> implements IDateControlModel
{
	private static final long serialVersionUID = 3316617779660627072L;

	private com.google.gwt.i18n.shared.DateTimeFormat dateTimeFormat = DateTimeFormat
			.getFormat(com.google.gwt.i18n.shared.DateTimeFormat.PredefinedFormat.DATE_MEDIUM);

	public BaseDateControlModel(String name, IBaseModel parent)
	{
		super(name, parent);
	}

	@Override
	public String getFormatPattern()
	{
		return this.dateTimeFormat.getPattern();
	}
}
