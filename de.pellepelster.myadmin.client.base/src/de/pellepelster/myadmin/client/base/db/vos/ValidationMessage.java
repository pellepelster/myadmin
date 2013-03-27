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

import java.io.Serializable;

/**
 * Representation for an validation error message
 * 
 * @author pelle
 * 
 */
public class ValidationMessage implements IValidationMessage, Serializable
{

	private static final long serialVersionUID = -8385373403323580526L;

	private VALIDATION_STATUS validationSatus;
	private String messageText;
	private String context;

	public ValidationMessage()
	{
		super();
	}

	public ValidationMessage(VALIDATION_STATUS validationSatus)
	{
		this.validationSatus = validationSatus;
	}

	public ValidationMessage(VALIDATION_STATUS validationSatus, String messageText)
	{
		this(validationSatus, messageText, null);
	}

	public ValidationMessage(VALIDATION_STATUS validationSatus, String messageText, String context)
	{
		super();
		this.validationSatus = validationSatus;
		this.messageText = messageText;
		this.context = context;
	}

	/** {@inheritDoc} */
	@Override
	public String getContext()
	{
		return context;
	}

	/** {@inheritDoc} */
	@Override
	public String getMessage()
	{
		return messageText;
	}

	public String getMessageText()
	{
		return messageText;
	}

	/** {@inheritDoc} */
	@Override
	public VALIDATION_STATUS getStatus()
	{
		return validationSatus;
	}

	public VALIDATION_STATUS getValidationSatus()
	{
		return validationSatus;
	}

	public void setContext(String context)
	{
		this.context = context;
	}

	public void setMessageText(String messageText)
	{
		this.messageText = messageText;
	}

	public void setValidationSatus(VALIDATION_STATUS validationSatus)
	{
		this.validationSatus = validationSatus;
	}

}
