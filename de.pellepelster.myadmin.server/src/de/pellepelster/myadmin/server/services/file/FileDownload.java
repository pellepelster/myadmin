package de.pellepelster.myadmin.server.services.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.io.ByteStreams;

import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IFileControl;
import de.pellepelster.myadmin.client.web.entities.dictionary.FileVO;
import de.pellepelster.myadmin.db.daos.BaseVODAO;
import de.pellepelster.myadmin.server.Messages;
import de.pellepelster.myadmin.server.core.query.ServerGenericFilterBuilder;

@Controller
@RequestMapping(value = IFileControl.FILE_DOWNLOAD_REQUEST_MAPPING)
public class FileDownload
{
	@Autowired
	private BaseVODAO baseVODAO;

	@RequestMapping(value = IFileControl.REQUEST_MAPPING_GET_FILE + "/{fileUUID}", method = RequestMethod.GET)
	public void getFile(@PathVariable String fileUUID, HttpServletResponse response)
	{
		GenericFilterVO<FileVO> genericFilterVO = ServerGenericFilterBuilder.createGenericFilter(FileVO.class).addCriteria(FileVO.FIELD_FILEUUID, fileUUID)
				.getGenericFilter();
		FileVO file = this.baseVODAO.read(genericFilterVO);

		try
		{
			if (file == null)
			{
				response.setCharacterEncoding("UTF-8");
				response.getWriter().printf(Messages.FILE_NOT_FOUND);
			}
			else
			{
				String contentType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(file.getFileContent()));
				response.setContentType(contentType);
				ByteStreams.copy(new ByteArrayInputStream(file.getFileContent()), response.getOutputStream());
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException("error writing file", e);
		}

	}

	public void setBaseVODAO(BaseVODAO baseVODAO)
	{
		this.baseVODAO = baseVODAO;
	}

}
