package de.pellepelster.myadmin.db;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface ISearchIndexService
{
	void createVO(IBaseVO baseVO);

	void deleteVO(IBaseVO baseVO);

	void deleteAllVO(Class<? extends IBaseVO> voClass);

	void update(IBaseVO baseVO);
}
