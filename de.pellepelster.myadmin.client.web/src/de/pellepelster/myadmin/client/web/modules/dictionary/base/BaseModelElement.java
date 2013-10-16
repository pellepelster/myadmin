package de.pellepelster.myadmin.client.web.modules.dictionary.base;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseModelElement<ModelType extends IBaseModel> {

	private ModelType model;

	public BaseModelElement(ModelType model) {
		super();
		this.model = model;
	}

	public ModelType getModel() {
		return model;
	}

}
