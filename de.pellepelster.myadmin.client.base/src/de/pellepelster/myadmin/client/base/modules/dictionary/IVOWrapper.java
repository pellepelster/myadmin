package de.pellepelster.myadmin.client.base.modules.dictionary;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface IVOWrapper<VOType extends IBaseVO>
{
	void set(String attribute, Object value);

	Object get(String attribute);

	VOType getContent();
}
