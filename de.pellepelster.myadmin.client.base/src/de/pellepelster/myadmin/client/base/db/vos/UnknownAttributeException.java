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
package de.pellepelster.myadmin.client.base.db.vos;

public class UnknownAttributeException extends RuntimeException
{

	private static final long serialVersionUID = 457364352205335100L;

	public UnknownAttributeException()
	{
		super();
	}

	public UnknownAttributeException(String message)
	{
		super(message);
	}

	public UnknownAttributeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public UnknownAttributeException(Throwable cause)
	{
		super(cause);
	}

}
