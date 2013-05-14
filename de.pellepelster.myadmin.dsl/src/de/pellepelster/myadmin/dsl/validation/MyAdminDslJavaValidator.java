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
package de.pellepelster.myadmin.dsl.validation;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.validation.Check;

import de.pellepelster.myadmin.dsl.Messages;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

public class MyAdminDslJavaValidator extends AbstractMyAdminDslJavaValidator
{
	public enum MESSAGE_LEVEL
	{
		NONE, INFO, WARNING, ERROR
	}

	public static class ModelValidationMessage
	{
		private final String message;

		private final MESSAGE_LEVEL messageLevel;

		public static ModelValidationMessage OK_MESSAGE = new ModelValidationMessage(MESSAGE_LEVEL.NONE);

		public ModelValidationMessage(MESSAGE_LEVEL messageLevel)
		{
			this(messageLevel, null);
		}

		public ModelValidationMessage(MESSAGE_LEVEL messageLevel, String message)
		{
			super();
			this.messageLevel = messageLevel;
			this.message = message;
		}

		public String getMessage()
		{
			return this.message;
		}

		public MESSAGE_LEVEL getMessageLevel()
		{
			return this.messageLevel;
		}

	}

	public static ModelValidationMessage validateIdentifier(String identifier)
	{

		char[] identifierChars = identifier.toCharArray();

		if (!Character.isJavaIdentifierStart(identifierChars[0]))
		{
			return new ModelValidationMessage(MESSAGE_LEVEL.ERROR, Messages.NoValidIdentifier);
		}

		for (int i = 1; i < identifierChars.length; i++)
		{
			if (!Character.isJavaIdentifierPart(identifierChars[i]))
			{
				return new ModelValidationMessage(MESSAGE_LEVEL.ERROR, Messages.NoValidIdentifier);
			}
		}

		return ModelValidationMessage.OK_MESSAGE;
	}

	private void convertValidationMessageTo(ModelValidationMessage modelValidationMessage, EStructuralFeature feature)
	{
		switch (modelValidationMessage.getMessageLevel())
		{
			case ERROR:
				error(modelValidationMessage.getMessage(), feature);
				break;
			case WARNING:
				warning(modelValidationMessage.getMessage(), feature);
				break;
			case INFO:
				info(modelValidationMessage.getMessage(), feature);
				break;
			default:
				break;
		}
	}

	@Check
	public void checkGreetingStartsWithCapital(Datatype datatype)
	{
		convertValidationMessageTo(validateIdentifier(datatype.getName()), MyAdminDslPackage.Literals.DATATYPE__NAME);
	}
}
