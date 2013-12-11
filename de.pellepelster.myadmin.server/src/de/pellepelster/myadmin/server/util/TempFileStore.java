package de.pellepelster.myadmin.server.util;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.FileVO;
import de.pellepelster.myadmin.db.daos.BaseVODAO;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;

// TODO remove unused temp files
@Component
public class TempFileStore
{
	@Autowired
	private BaseVODAO baseVODAO;

	private final static Logger LOG = Logger.getLogger(TempFileStore.class);

	public String storeTempFile(String fileName, byte[] fileContent)
	{
		String fileUUID = UUID.randomUUID().toString();

		FileVO file = new FileVO();

		file.setFileName(fileName);
		file.setFileUUID(fileUUID);
		file.setFileContent(fileContent);

		this.baseVODAO.create(file);

		return fileUUID;
	}

	public String storeTempFile(byte[] content)
	{
		return storeTempFile(null, content);
	}

	public boolean hasTempFile(String fileUUID)
	{
		return getTempFile(fileUUID) != null;
	}

	public FileVO getTempFile(String fileUUID)
	{
		GenericFilterVO<FileVO> genericFilterVO = ServerGenericFilterBuilder.createGenericFilter(FileVO.class).addCriteria(FileVO.FIELD_FILEUUID, fileUUID)
				.getGenericFilter();
		return this.baseVODAO.read(genericFilterVO);
	}

	public void setBaseVODAO(BaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

}
