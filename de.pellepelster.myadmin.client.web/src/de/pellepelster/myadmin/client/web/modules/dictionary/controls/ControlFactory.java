package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBigDecimalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBooleanControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IDateControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IEnumerationControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IHierarchicalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IIntegerControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.ITextControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

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

	public static BaseControl createControl(IBaseControlModel baseControlModel, VOWrapper<IBaseVO> voWrapper)
	{
		if (baseControlModel instanceof ITextControlModel)
		{
			return new TextControl((ITextControlModel) baseControlModel, voWrapper);
		}
		else if (baseControlModel instanceof IIntegerControlModel)
		{
			return new IntegerControl((IIntegerControlModel) baseControlModel, voWrapper);
		}
		else if (baseControlModel instanceof IDateControlModel)
		{
			return new DateControl((IDateControlModel) baseControlModel, voWrapper);
		}
		else if (baseControlModel instanceof IBooleanControlModel)
		{
			return new BooleanControl((IBooleanControlModel) baseControlModel, voWrapper);
		}
		else if (baseControlModel instanceof IEnumerationControlModel)
		{
			return new EnumerationControl((IEnumerationControlModel) baseControlModel, voWrapper);
		}
		else if (baseControlModel instanceof IReferenceControlModel)
		{
			return new ReferenceControl((IReferenceControlModel) baseControlModel, voWrapper);
		}
		else if (baseControlModel instanceof IBigDecimalControlModel)
		{
			return new BigDecimalControl((IBigDecimalControlModel) baseControlModel, voWrapper);
		}
		else if (baseControlModel instanceof IHierarchicalControlModel)
		{
			return new HierarchicalControl((IHierarchicalControlModel) baseControlModel, voWrapper);
		}
		else
		{
			throw new RuntimeException("unsupported control model type '" + baseControlModel.getClass().getName() + "'");
		}
	}

}
