package de.pellepelster.myadmin.server;

import java.util.List;

import de.pellepelster.myadmin.client.base.LogEntrySimpleVO;
import de.pellepelster.myadmin.client.base.SysteminformationSimpleVO;
import de.pellepelster.myadmin.client.web.ISystemService;

public class SystemServiceImpl implements ISystemService {

	@Override
	public SysteminformationSimpleVO getSystemInformation() {
		return null;
	}

	@Override
	public List<LogEntrySimpleVO> getLog() {
		return null;
	}

}
