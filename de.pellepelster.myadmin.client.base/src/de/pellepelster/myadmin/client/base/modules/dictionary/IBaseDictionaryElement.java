package de.pellepelster.myadmin.client.base.modules.dictionary;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseRootModel;

public interface IBaseDictionaryElement<ModelType extends IBaseModel>
{
	IBaseDictionaryElement<?> getParent();

	ModelType getModel();

	IVOWrapper<? extends IBaseVO> getVOWrapper();

	IBaseRootElement<? extends IBaseRootModel> getRootElement();

}
