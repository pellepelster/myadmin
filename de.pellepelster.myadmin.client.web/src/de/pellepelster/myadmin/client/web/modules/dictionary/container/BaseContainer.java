package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ControlFactory;

public abstract class BaseContainer<ModelType extends IBaseContainerModel> extends BaseModelElement<ModelType>
{
	private List<BaseControl<?, ?>> controls = Collections.emptyList();

	private List<BaseContainer> children = Collections.emptyList();

	public BaseContainer(ModelType baseContainer, BaseModelElement<IBaseModel> parent)
	{
		super(baseContainer, parent);

		if (!baseContainer.getChildren().isEmpty())
		{
			this.children = Lists.transform(baseContainer.getChildren(), new Function<IBaseContainerModel, BaseContainer>()
			{
				@Override
				@Nullable
				public BaseContainer<IBaseContainerModel> apply(IBaseContainerModel baseContainerModel)
				{
					return ContainerFactory.getInstance().createContainer(baseContainerModel, BaseContainer.this);
				}
			});
		}
		else if (!baseContainer.getControls().isEmpty())
		{
			this.controls = Lists.transform(baseContainer.getControls(), new Function<IBaseControlModel, BaseControl<?, ?>>()
			{
				@Override
				@Nullable
				public BaseControl<IBaseControlModel, Object> apply(IBaseControlModel baseControlModel)
				{
					return ControlFactory.getInstance().createControl(baseControlModel, BaseContainer.this);
				}
			});
		}
	}

	public List<BaseControl<?, ?>> getControls()
	{
		return this.controls;
	}

	public List<BaseContainer> getChildren()
	{
		return this.children;
	}

}
