package de.pellepelster.myadmin.client.web.modules.dictionary.container;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.IBaseContainerModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseModelElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.ControlFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.VOWrapper;

public abstract class BaseContainer<ModelType extends IBaseContainerModel> extends BaseModelElement<ModelType>
{
	private List<BaseControl> controls = Collections.EMPTY_LIST;

	private List<BaseContainer> children = Collections.EMPTY_LIST;

	public BaseContainer(ModelType baseContainer, final VOWrapper<IBaseVO> voWrapper)
	{
		super(baseContainer);

		if (!baseContainer.getChildren().isEmpty())
		{
			this.children = Lists.transform(baseContainer.getChildren(), new Function<IBaseContainerModel, BaseContainer>()
			{
				@Override
				@Nullable
				public BaseContainer<IBaseContainerModel> apply(IBaseContainerModel baseContainerModel)
				{
					return ContainerFactory.getInstance().createContainer(baseContainerModel, voWrapper);
				}
			});
		}
		else if (!baseContainer.getControls().isEmpty())
		{
			this.controls = Lists.transform(baseContainer.getControls(), new Function<IBaseControlModel, BaseControl>()
			{
				@Override
				@Nullable
				public BaseControl<IBaseControlModel> apply(IBaseControlModel baseControlModel)
				{
					return ControlFactory.getInstance().createControl(baseControlModel, voWrapper);
				}
			});
		}
	}

	public List<BaseControl> getControls()
	{
		return this.controls;
	}

	public List<BaseContainer> getChildren()
	{
		return this.children;
	}

}
