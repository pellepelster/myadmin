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
			c.setLinewrap().before(pair.getSecond());
			c.setLinewrap(2).after(pair.getSecond());
		}

		for (Keyword comma : f.findKeywords(","))
		{
			c.setNoSpace().before(comma);
		}

		c.setIndentation(f.getPackageDeclarationAccess().getLeftCurlyBracketKeyword_2(), f.getPackageDeclarationAccess().getRightCurlyBracketKeyword_4());
		c.setIndentation(f.getModelAccess().getLeftCurlyBracketKeyword_3(), f.getModelAccess().getRightCurlyBracketKeyword_5());
		c.setIndentation(f.getEntityAccess().getLeftCurlyBracketKeyword_4(), f.getEntityAccess().getRightCurlyBracketKeyword_8());
		c.setIndentation(f.getDictionaryAccess().getLeftCurlyBracketKeyword_2(), f.getDictionaryAccess().getRightCurlyBracketKeyword_12());

		// c.setLinewrap().after(f.getDictionarySearchAccess().getLeftCurlyBracketKeyword_2());

		// It's usually a good idea to activate the following three statements.
		// They will add and preserve newlines around comments
		// c.setLinewrap(0, 1,
		// 2).before(getGrammarAccess().getSL_COMMENTRule());
		// c.setLinewrap(0, 1,
		// 2).before(getGrammarAccess().getML_COMMENTRule());
		// c.setLinewrap(0, 1, 1).after(getGrammarAccess().getML_COMMENTRule());
	}
}
