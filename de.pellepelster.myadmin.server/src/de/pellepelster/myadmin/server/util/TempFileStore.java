package de.pellepelster.myadmin.server.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.google.common.io.Files;

@Component
public class TempFileStore implements InitializingBean
{
	private final static Logger LOG = Logger.getLogger(TempFileStore.class);

	private ConcurrentHashMap<String, String> tempFileNameMappings = new ConcurrentHashMap<String, String>();

	public static final String TEMP_FILE_DIR = "myadmin_temp_files";

	private File tempDir;

	public String storeTempFile(String fileName, byte[] content)
	{
		String tempFileId = UUID.randomUUID().toString();
		File tempFile = new File(this.tempDir, tempFileId);

		try
		{
			Files.write(content, tempFile);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		String mappedTempFileName = fileName;

		if (fileName == null)
		{
			mappedTempFileName = tempFileId;
		}

		this.tempFileNameMappings.put(tempFileId, mappedTempFileName);

		LOG.info(String.format("stored file '%s' as '%s'", mappedTempFileName, tempFileId));

		return tempFileId;
	}

	public String storeTempFile(byte[] content)
	{
		return storeTempFile(null, content);
	}

	public byte[] getTempFile(String tempFileId)
	{
		File tempFile = new File(this.tempDir, tempFileId);
		try
		{
			return Files.toByteArray(tempFile);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception
	{
		this.tempDir = new File(System.getProperty("java.io.tmpdir"), TEMP_FILE_DIR);

		if (this.tempDir.exists() && this.tempDir.isFile())
		{
			throw new RuntimeException(String.format("'%s' already exists and is a file", this.tempDir.toString()));
		}

		if (!this.tempDir.exists())
		{
			this.tempDir.mkdirs();
		}

		LOG.info(String.format("temp file store initialized at '%s'", this.tempDir.toString()));
	}

}
