package de.pellepelster.myadmin.client.base.modules.dictionary;

public class DictionaryDescriptor<ElementType> {

	private String id;

	private DictionaryDescriptor<?> parent;

	public DictionaryDescriptor(String id, DictionaryDescriptor<?> parent) {
		super();
		this.id = id;
		this.parent = parent;
	}

	public DictionaryDescriptor(String id) {
		this(id, null);
	}

	public String getId() {
		return this.id;
	}

	public DictionaryDescriptor<?> getParent() {
		return parent;
	}

}
