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
package de.pellepelster.myadmin.dsl.formatting;

import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;
import org.eclipse.xtext.util.Pair;

import de.pellepelster.myadmin.dsl.services.MyAdminDslGrammarAccess;

/**
 * This class contains custom formatting description.
 * 
 * see : http://www.eclipse.org/Xtext/documentation/latest/xtext.html#formatting
 * on how and when to use it
 * 
 * Also see {@link org.eclipse.xtext.xtext.XtextFormattingTokenSerializer} as an
 * example
 */
public class MyAdminDslFormatter extends AbstractDeclarativeFormatter
{

	@Override
	protected void configureFormatting(FormattingConfig c)
	{
		MyAdminDslGrammarAccess f = (MyAdminDslGrammarAccess) getGrammarAccess();
		c.setAutoLinewrap(160);

		for (Pair<Keyword, Keyword> pair : f.findKeywordPairs("{", "}"))
		{
			c.setLinewrap().after(pair.getFirst());
			c.setLinewrap(2).after(pair.getSecond());
			c.setIndentation(pair.getFirst(), pair.getSecond());
		}

		for (Keyword comma : f.findKeywords(","))
		{
			c.setNoSpace().before(comma);
		}

	}
}
