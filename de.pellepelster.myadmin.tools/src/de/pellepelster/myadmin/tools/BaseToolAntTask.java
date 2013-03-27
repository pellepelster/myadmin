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
package de.pellepelster.myadmin.tools;

import org.apache.tools.ant.Task;

public abstract class BaseToolAntTask extends Task
{

	/** Password string for core remote */
	private String remotepassword;

	/** core remote path */
	private String remotepath;

	/** core remote port */
	private String remoteport;

	/** core remote server */
	private String remoteserver;

	/** Username string for core remote */
	private String remoteuser;

	/**
	 * @see BaseImport#remotepassword
	 */
	public String getRemotepassword()
	{
		return this.remotepassword;
	}

	/**
	 * @see BaseImport#remotepath
	 */
	public String getRemotepath()
	{
		return this.remotepath;
	}

	/**
	 * @see BaseImport#remoteport
	 */
	public String getRemoteport()
	{
		return this.remoteport;
	}

	/**
	 * @see BaseImport#remoteserver
	 */
	public String getRemoteServer()
	{
		return this.remoteserver;
	}

	/**
	 * @see BaseImport#remoteuser
	 */
	public String getRemoteuser()
	{
		return this.remoteuser;
	}

	/**
	 * @see BaseImport#remotepassword
	 */
	public void setRemotepassword(String remotepassword)
	{
		this.remotepassword = remotepassword;
	}

	/**
	 * @see BaseImport#remotepath
	 */
	public void setRemotepath(String remotepath)
	{
		this.remotepath = remotepath;
	}

	/**
	 * @see BaseImport#remoteport
	 */
	public void setRemoteport(String remoteport)
	{
		this.remoteport = remoteport;
	}

	/**
	 * @see BaseImport#remoteserver
	 */
	public void setRemoteServer(String remoteserver)
	{
		this.remoteserver = remoteserver;
	}

	/**
	 * @see BaseImport#remoteuser
	 */
	public void setRemoteuser(String remoteuser)
	{
		this.remoteuser = remoteuser;
	}

}
