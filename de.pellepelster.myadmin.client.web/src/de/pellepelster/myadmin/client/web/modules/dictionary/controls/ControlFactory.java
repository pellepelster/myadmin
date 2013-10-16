package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumarationControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;

public class ControlFactory
{

	public static BaseControl test(IBaseControlModel baseControlModel)
	{
		if (baseControlModel instanceof ITextControlModel)
		{
			return new TextControl((ITextControlModel) baseControlModel);
		}
		else if (baseControlModel instanceof IIntegerControlModel)
		{
			return new IntegerControl((IIntegerControlModel) baseControlModel);
		}
		else if (baseControlModel instanceof IDateControlModel)
		{
			return new DateControl((IDateControlModel) baseControlModel);
		}
		else if (baseControlModel instanceof IBooleanControlModel)
		{
			return new BooleanControl((IBooleanControlModel) baseControlModel);
		}
		else if (baseControlModel instanceof IEnumarationControlModel)
		{

		}
		else if (baseControlModel instanceof IReferenceControlModel)
		{

		}
		else if (baseControlModel instanceof IBigDecimalControlModel)
		{
			return new BigDecimalControl((IBigDecimalControlModel) baseControlModel);
		}
		else if (baseControlModel instanceof IHierarchicalControlModel)
		{

		}
		else
		{
			throw new RuntimeException("unsupported control model type '" + baseControlModel.getClass().getName() + "'");
		}
	}

}
