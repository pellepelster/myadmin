package de.pellepelster.myadmin.db.index;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.common.base.Optional;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class DictionarySearchElement implements IDictionarySearchElement
{
	private final String type;

	private String id;

	private Map<String, Object> fields = new HashMap<String, Object>();

	public DictionarySearchElement(IBaseVO vo, String label)
	{
		this.type = vo.getClass().getName();
		this.id = Long.toString(vo.getId());

		this.fields.put(DICTIONARY_LABEL_FIELD_NAME, label);
	}

	public DictionarySearchElement(String type, String id)
	{
		super();
		this.type = type;
		this.id = id;
	}

	@Override
	public String getType()
	{
		return this.type;
	}

	@Override
	public String getId()
	{
		return this.id;
	}

	@Override
	public Map<String, Object> getFields()
	{
		return this.fields;
	}

	@Override
	public String getLabel()
	{
		return Optional.fromNullable(this.fields.get(DICTIONARY_LABEL_FIELD_NAME)).or("<no label>").toString();
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this).add("type", this.type).add("id", this.id).add("fields", this.fields).toString();
	}

}
