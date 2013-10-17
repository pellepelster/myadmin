package de.pellepelster.myadmin.client.base.modules.dictionary;

public class DictionaryDescriptor implements IDictionaryDescriptor {

	private String id;

	private DictionaryDescriptor parent;

	public DictionaryDescriptor(String id, DictionaryDescriptor parent) {
		super();
		this.id = id;
		this.parent = parent;
	}

	public DictionaryDescriptor(String id) {
		this(id, null);
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public IDictionaryDescriptor getParent() {
		return parent;
	}

}
