package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;

public class ControlFactory
{
	private static ControlFactory instance;

	public static ControlFactory getInstance()
	{
		if (instance == null)
		{
			instance = new ControlFactory();
		}

		return instance;
	}

	public static BaseDictionaryControl createControl(IBaseControlModel baseControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		if (baseControlModel instanceof ITextControlModel)
		{
			return new TextControl((ITextControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IIntegerControlModel)
		{
			return new IntegerControl((IIntegerControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IDateControlModel)
		{
			return new DateControl((IDateControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IBooleanControlModel)
		{
			return new BooleanControl((IBooleanControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IEnumerationControlModel)
		{
			return new EnumerationControl((IEnumerationControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IReferenceControlModel)
		{
			return new ReferenceControl((IReferenceControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IBigDecimalControlModel)
		{
			return new BigDecimalControl((IBigDecimalControlModel) baseControlModel, parent);
		}
		else if (baseControlModel instanceof IHierarchicalControlModel)
		{
			return new HierarchicalControl((IHierarchicalControlModel) baseControlModel, parent);
		}
		else
		{
			throw new RuntimeException("unsupported control model type '" + baseControlModel.getClass().getName() + "'");
		}
	}

}
