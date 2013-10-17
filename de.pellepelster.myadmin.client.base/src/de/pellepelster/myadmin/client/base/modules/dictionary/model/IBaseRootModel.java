package de.pellepelster.myadmin.client.base.modules.dictionary.model;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.containers.ICompositeModel;

public interface IBaseRootModel extends IBaseModel
{
	/**
	 * The composite structure describing the editor UI
	 * 
	 * @return
	 */
	ICompositeModel getCompositeModel();
}
