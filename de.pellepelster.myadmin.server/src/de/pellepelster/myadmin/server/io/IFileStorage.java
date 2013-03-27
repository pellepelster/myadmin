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

/**
 * Interface for storing and retrieving files
 * 
 * @author pelle
 * @version $Rev: 1 $, $Date: 2009-04-14 23:03:38 +0200 (Tue, 14 Apr 2009) $
 * 
 */
public interface IFileStorage
{
	/**
	 * Retrieve a file by name
	 * 
	 * @param fileName
	 *            Name of the file to load
	 * @return Content of the file
	 */
	byte[] loadFile(String fileName);

	/**
	 * Stores a file under a given filename
	 * 
	 * @param fileContent
	 *            Content of the file
	 * @param fileName
	 *            Filename for later retrieval
	 * @return Filename for later retrieval
	 */
	String storeFile(byte[] fileContent, String fileName);
}
