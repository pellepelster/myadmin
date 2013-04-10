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
package de.pellepelster.myadmin.client.base.messages;

import java.io.Serializable;

/**
 * Representation for an validation error message
 * 
 * @author pelle
 * 
 */
public class ValidationMessage implements IValidationMessage, Serializable
{

	private static final long serialVersionUID = -6931591580968792948L;

	private String message;

	private String code;

	private SEVERITY severity;

	private String context;

	private ValidationMessage()
	{
	}

	public ValidationMessage(SEVERITY severity, String code, String message)
	{
		this(severity, code, message, null);
	}

	public ValidationMessage(SEVERITY severity, String code, String message, String context)
	{
		this.severity = severity;
		this.code = code;
		this.message = message;
		this.context = context;
	}

	// public ValidationMessage(IMessage message, String context)
	// {
	// super();
	// this.severity = message.getSeverity();
	// this.code = message.getCode();
	// this.message = message.getMessage();
	// }

	@Override
	public String getCode()
	{
		return this.code;
	}

	@Override
	public String getMessage()
	{
		return this.message;
	}

	@Override
	public SEVERITY getSeverity()
	{
		return this.severity;
	}

	@Override
	public String getContext()
	{
		return this.context;
	}

}
