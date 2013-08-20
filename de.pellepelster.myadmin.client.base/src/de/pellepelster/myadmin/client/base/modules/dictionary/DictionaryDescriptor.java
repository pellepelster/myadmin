package de.pellepelster.myadmin.client.base.modules.dictionary;

public class DictionaryDescriptor implements IDictionaryDescriptor
{
	private String id;

	public DictionaryDescriptor(String id)
	{
		super();
		this.id = id;
	}

	@Override
	public String getId()
	{
		return this.id;
	}

}
