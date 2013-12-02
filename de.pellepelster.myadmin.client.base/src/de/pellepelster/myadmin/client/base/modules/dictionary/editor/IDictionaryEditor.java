package de.pellepelster.myadmin.client.base.modules.dictionary.editor;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface IDictionaryEditor<VOType extends IBaseVO>
{
	VOType getVO();
}
