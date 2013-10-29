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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import de.pellepelster.myadmin.client.base.util.MessageFormat;

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

	private String humanMessage;

	private String code;

	private SEVERITY severity;

	private Map<String, Object> context = new HashMap<String, Object>();

	@SuppressWarnings("unused")
	private ValidationMessage()
	{
	}

	public ValidationMessage(SEVERITY severity, String code, String message, String humanMessage)
	{
		this(severity, code, message, humanMessage, Collections.EMPTY_MAP);
	}

	public ValidationMessage(SEVERITY severity, String code, String message)
	{
		this(severity, code, message, null, Collections.EMPTY_MAP);
	}

	public ValidationMessage(SEVERITY severity, String code, String message, String humanMessage, Map<String, Object> context)
	{
		this.severity = severity;
		this.code = code;
		this.message = message;
		this.context = context;
		this.humanMessage = humanMessage;
	}

	public ValidationMessage(IMessage message, Map<String, Object> context)
	{
		this(message.getSeverity(), message.getCode(), message.getMessage(), message.getHumanMessage(), context);
	}

	@Override
	public String getCode()
	{
		return this.code;
	}

	@Override
	public String getMessage()
	{
		return MessageFormat.format(this.message, this.context);
	}

	@Override
	public String getHumanMessage()
	{
		if (this.humanMessage != null)
		{
			return MessageFormat.format(this.humanMessage, this.context);
		}
		else
		{
			return MessageFormat.format(this.message, this.context);
		}
	}

	@Override
	public SEVERITY getSeverity()
	{
		return this.severity;
	}

	@Override
	public Map<String, Object> getContext()
	{
		return this.context;
	}

}
