package de.pellepelster.myadmin.server.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.google.common.io.Files;

@Component
public class TempFileStore implements InitializingBean
{

	public static final String TEMP_FILE_DIR = "myadmin_temp_files";

	private File tempDir;

	public String storeTempFile(byte[] content)
	{
		String tempFileName = UUID.randomUUID().toString();
		File tempFile = new File(this.tempDir, tempFileName);

		try
		{
			Files.write(content, tempFile);
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return tempFileName;
	}

	public byte[] getTempFile(String tempFileName)
	{
		File tempFile = new File(this.tempDir, tempFileName);
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
	}

}
