package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ControlFunction;

public abstract class BaseContainerElement<ModelType extends IBaseContainerModel> extends BaseDictionaryElement<ModelType>
{
	private List<BaseDictionaryControl<?, ?>> controls = Collections.emptyList();

	private List<BaseContainerElement<?>> children = Collections.emptyList();

	public BaseContainerElement(ModelType baseContainer, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(baseContainer, parent);

		if (!baseContainer.getChildren().isEmpty())
		{
			this.children = Lists.transform(baseContainer.getChildren(), new Function<IBaseContainerModel, BaseContainerElement<?>>()
			{
				@SuppressWarnings({ "static-access", "static-access" })
				@Override
				@Nullable
				public BaseContainerElement<? extends IBaseContainerModel> apply(IBaseContainerModel baseContainerModel)
				{
					return ContainerFactory.getInstance().createContainer(baseContainerModel, BaseContainerElement.this);
				}
			});
		}
		else if (!baseContainer.getControls().isEmpty())
		{
			this.controls = Lists.transform(baseContainer.getControls(), new ControlFunction(BaseContainerElement.this));
		}
	}

	public List<BaseDictionaryControl<?, ?>> getControls()
	{
		return this.controls;
	}

	public List<BaseContainerElement<?>> getChildren()
	{
		return this.children;
	}

}
