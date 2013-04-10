package de.pellepelster.myadmin.client.base.messages;

import de.pellepelster.myadmin.client.base.messages.IMessage.SEVERITY;

public class MyAdminMessages
{
	public static final IMessage MANDATORY = new Message(SEVERITY.ERROR, "MANDATORY", "Attribute '{0}' should not be null for entity '{1}'");
}
