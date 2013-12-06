package de.pellepelster.myadmin.server.services;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.common.base.Joiner;

import de.pellepelster.myadmin.server.util.TempFileStore;

public class FileUploadServlet extends UploadAction
{

	private static final long serialVersionUID = 2749493461541235051L;

	private ApplicationContext applicationContext = null;

	@Override
	public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException
	{
		TempFileStore tempFileStore = getApplicationContext().getBean(TempFileStore.class);

		List<String> fileNameIds = new ArrayList<String>();

		for (FileItem sessionFile : sessionFiles)
		{
			String fileName = Paths.get(sessionFile.getName()).getFileName().toString();
			fileNameIds.add(tempFileStore.storeTempFile(fileName, sessionFile.get()));
		}

		return Joiner.on(",").join(fileNameIds);
	}

	private ApplicationContext getApplicationContext()
	{
		if (this.applicationContext == null)
		{
			this.applicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		}

		return this.applicationContext;
	}

}