package de.pellepelster.myadmin.db.daos;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface IVODAOCallback
{
	<VOType extends IBaseVO> void onAdd(VOType vo);

	void onDeleteAll(Class<? extends IBaseVO> voClass);

	<T extends IBaseVO> void onDelete(T vo);
}
