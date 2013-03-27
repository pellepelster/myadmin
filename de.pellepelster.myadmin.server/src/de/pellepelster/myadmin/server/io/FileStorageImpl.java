/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package de.pellepelster.myadmin.server.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import de.pellepelster.myadmin.client.web.entities.dictionary.MyAdminUserVO;

/**
 * {@link User} aware implementation of {@link IFileStorage}
 * 
 * @author pelle
 * @version $Rev: 1069 $, $Date: 2011-05-15 20:58:42 +0200 (Sun, 15 May 2011) $
 * 
 */
public class FileStorageImpl implements IFileStorage
{
	private final Logger LOG = Logger.getLogger(FileStorageImpl.class);

	private String tempFilePath;

	/**
	 * Constructor for {@link FileStorageImpl}
	 * 
	 */
	public FileStorageImpl()
	{
		try
		{
			File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
			tempFilePath = tempFile.getParent();
			LOG.debug(String.format("initializing file storage with temp path '%s'", tempFilePath));
			tempFile.delete();

		}
		catch (IOException e)
		{
			LOG.error(String.format("error initializing file storage"), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param fileName
	 * @return
	 */
	private File getTempFile(String fileName)
	{
		Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (user instanceof MyAdminUserVO)
		{
			String tempFileName = String.format("%s_%s", fileName, Long.toString(((MyAdminUserVO) user).getId()));
			LOG.debug(String.format("creating file object (%s) for user '%s'", tempFileName, user));
			return new File(tempFilePath, tempFileName);
		}
		else
		{
			throw new RuntimeException(String.format("access denied for user '%s'", user.toString()));
		}

	}

	/** {@inheritDoc} */
	@Override
	public byte[] loadFile(String fileName)
	{
		try
		{
			File tempFile = getTempFile(fileName);
			LOG.debug(String.format("retrieving %d bytes for '%s'", tempFile.length(), fileName));
			InputStream is = new FileInputStream(tempFile);

			long length = tempFile.length();
			if (length > Integer.MAX_VALUE)
			{
				throw new RuntimeException(String.format("file '%s' too large", tempFile.getAbsoluteFile()));
			}
			byte[] result = new byte[(int) length];

			int offset = 0;
			int numRead = 0;
			while (offset < result.length && (numRead = is.read(result, offset, result.length - offset)) >= 0)
			{
				offset += numRead;
			}

			if (offset < result.length)
			{
				throw new IOException(String.format("could not completely read file '%s'", tempFile.getName()));
			}

			is.close();

			return result;
		}
		catch (Exception e)
		{
			LOG.error(String.format("error reading file file '%s'", fileName), e);
			return new byte[0];
		}
	}

	/** {@inheritDoc} */
	@Override
	public String storeFile(byte[] fileContent, String fileName)
	{
		File tempFile = getTempFile(fileName);
		LOG.debug(String.format("storing %d bytes for '%s'", fileContent.length, fileName));

		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
			fileOutputStream.write(fileContent);
			fileOutputStream.close();
		}
		catch (Exception e)
		{
			LOG.error(String.format("error storing file '%s'", tempFile.getAbsoluteFile()), e);
			throw new RuntimeException(e);
		}

		return fileName;
	}
}
