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
import java.util.ArrayList;
import java.util.List;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;

public class Result<VOType extends IBaseVO> implements Serializable
{
	private static final long serialVersionUID = 2971295762387189829L;
	private VOType vo;
	private List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();

	public Result()
	{
	}

	public List<IValidationMessage> getValidationMessages()
	{
		return validationMessages;
	}

	public VOType getVo()
	{
		return vo;
	}

	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{
		this.validationMessages = validationMessages;
	}

	public void setVo(VOType vo)
	{
		this.vo = vo;
	}

}
