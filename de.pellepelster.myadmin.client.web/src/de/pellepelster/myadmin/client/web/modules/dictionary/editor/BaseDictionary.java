package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;

public abstract class BaseDictionary<ModelType extends IBaseModel> {

	private ModelType model;

	public BaseDictionary(ModelType model) {
		super();
		this.model = model;
	}

	public ModelType getModel() {
		return model;
	}

}
