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
package de.pellepelster.myadmin.server.validators;

import de.pellepelster.myadmin.client.base.messages.IMessage;
import de.pellepelster.myadmin.client.base.messages.Message;
import de.pellepelster.myadmin.server.Messages;

public class ValidatorMessages
{

	public static final String VALIDATOR_NATURALKEY_MESSAGE = "validator.naturalkey.message";

	public static final String VALIDATOR_MANDATORY_ATTRIBUTE_MESSAGE = "validator.mandatory.attribute.message";

	public static final String VALIDATOR_MANDATORY_LIST_MESSAGE = "validator.mandatory.LIST.message";

	public static final IMessage NATURAL_KEY = new Message(IMessage.SEVERITY.ERROR, "NATURAL_KEY", Messages.getString(VALIDATOR_NATURALKEY_MESSAGE));

	public static final IMessage MANDATORY_ATTRIBUTE = new Message(IMessage.SEVERITY.ERROR, "MANDATORY_ATTRIBUTE", Messages.getString(VALIDATOR_MANDATORY_ATTRIBUTE_MESSAGE));

	public static final IMessage MANDATORY_LIST = new Message(IMessage.SEVERITY.ERROR, "MANDATORY_LIST", Messages.getString(VALIDATOR_MANDATORY_LIST_MESSAGE));

}
