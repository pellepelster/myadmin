package de.pellepelster.myadmin.db.daos;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface IVODAOCallback
{
	<VOType extends IBaseVO> void onAdd(VOType vo);
}
