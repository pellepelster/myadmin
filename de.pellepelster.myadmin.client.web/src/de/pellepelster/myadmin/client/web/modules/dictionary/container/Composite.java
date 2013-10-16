package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.BaseDictionary;

public class Composite extends BaseDictionary<ICompositeModel>
{

	private List<BaseControl<IBaseControlModel>> controls = new ArrayList<>();

	public Composite(ICompositeModel composite)
	{
		super(composite);

		if (!composite.getChildren().isEmpty())
		{

		}
		else if (!composite.getControls().isEmpty())
		{

		}
	}

}
