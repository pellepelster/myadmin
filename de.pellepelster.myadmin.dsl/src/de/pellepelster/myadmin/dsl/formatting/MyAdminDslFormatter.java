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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.formatting.impl.AbstractDeclarativeFormatter;
import org.eclipse.xtext.formatting.impl.FormattingConfig;

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
	final int MIN_WRAPS = 1;

	final int DEFAULT_WRAPS = 2;

	final int MAX_WRAPS = 2;

	@Override
	protected void configureFormatting(FormattingConfig c)
	{
		MyAdminDslGrammarAccess m = (MyAdminDslGrammarAccess) getGrammarAccess();
		c.setAutoLinewrap(160);

		// for (Pair<Keyword, Keyword> pair : m.findKeywordPairs("{", "}"))
		// {
		// c.setLinewrap().after(pair.getFirst());
		// c.setLinewrap(2).after(pair.getSecond());
		// c.setIndentation(pair.getFirst(), pair.getSecond());
		// }

		// model/entity/package
		setBlockFormatting(c, m.getModelAccess().getLeftCurlyBracketKeyword_3(), m.getModelAccess().getRightCurlyBracketKeyword_5());
		setBlockFormatting(c, m.getPackageDeclarationAccess().getLeftCurlyBracketKeyword_2(), m.getPackageDeclarationAccess().getRightCurlyBracketKeyword_4());
		setBlockFormatting(c, m.getEntityAccess().getLeftCurlyBracketKeyword_4(), m.getEntityAccess().getRightCurlyBracketKeyword_8());

		/**
		 * TextDatatype IntegerDatatype BigDecimalDatatype BooleanDatatype
		 * DateDatatype EnumerationDatatype ReferenceDatatype;
		 */
		setBlockFormatting(c, m.getTextDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getTextDatatypeAccess().getRightCurlyBracketKeyword_7());
		setBlockFormatting(c, m.getIntegerDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getIntegerDatatypeAccess().getRightCurlyBracketKeyword_6());
		setBlockFormatting(c, m.getBigDecimalDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getBigDecimalDatatypeAccess().getRightCurlyBracketKeyword_10());
		setBlockFormatting(c, m.getBooleanDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getBooleanDatatypeAccess().getRightCurlyBracketKeyword_4());
		setBlockFormatting(c, m.getDateDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getDateDatatypeAccess().getRightCurlyBracketKeyword_4());
		setBlockFormatting(c, m.getEnumerationDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getEnumerationDatatypeAccess().getRightCurlyBracketKeyword_6());
		setBlockFormatting(c, m.getReferenceDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getReferenceDatatypeAccess().getRightCurlyBracketKeyword_6());

		// navigation
		setBlockFormatting(c, m.getNavigationNodeAccess().getLeftCurlyBracketKeyword_2(), m.getNavigationNodeAccess().getRightCurlyBracketKeyword_8());

		// dictionary
		setBlockFormatting(c, m.getDictionaryAccess().getLeftCurlyBracketKeyword_2(), m.getDictionaryAccess().getRightCurlyBracketKeyword_12());

		// dictionary controls
		setBlockFormatting(c, m.getDictionaryAccess().getLeftCurlyBracketKeyword_8_1(), m.getDictionaryAccess().getRightCurlyBracketKeyword_8_3());
		c.setLinewrap(this.MIN_WRAPS, this.DEFAULT_WRAPS, this.MAX_WRAPS).after(
				m.getDictionaryAccess().getDictionarycontrolsDictionaryControlParserRuleCall_8_2_0());

		// dictionary entity
		c.setLinewrap(this.MIN_WRAPS, this.DEFAULT_WRAPS, this.MAX_WRAPS).after(m.getDictionaryAccess().getEntityAssignment_4());

		setBlockFormatting(c, m.getDictionaryEditorAccess().getLeftCurlyBracketKeyword_2(), m.getDictionaryEditorAccess().getRightCurlyBracketKeyword_5());
		setBlockFormatting(c, m.getDictionarySearchAccess().getLeftCurlyBracketKeyword_2(), m.getDictionarySearchAccess().getRightCurlyBracketKeyword_6());
		setBlockFormatting(c, m.getDictionaryResultAccess().getLeftCurlyBracketKeyword_2(), m.getDictionaryResultAccess().getRightCurlyBracketKeyword_4());
		setBlockFormatting(c, m.getDictionaryFilterAccess().getLeftCurlyBracketKeyword_2(), m.getDictionaryFilterAccess().getRightCurlyBracketKeyword_4());
		setBlockFormatting(c, m.getDictionaryCompositeAccess().getLeftCurlyBracketKeyword_2(), m.getDictionaryCompositeAccess().getRightCurlyBracketKeyword_6());

		for (Keyword comma : m.findKeywords(","))
		{
			c.setNoSpace().before(comma);
		}

	}

	private void setBlockFormatting(FormattingConfig c, EObject start, EObject end)
	{
		c.setLinewrap(this.MIN_WRAPS, this.DEFAULT_WRAPS, this.MAX_WRAPS).after(start);
		c.setIndentationIncrement().after(start);
		c.setIndentationDecrement().before(end);
		c.setLinewrap(this.MIN_WRAPS, this.DEFAULT_WRAPS, this.MAX_WRAPS).before(end);
		c.setLinewrap(this.MIN_WRAPS, this.DEFAULT_WRAPS, this.MAX_WRAPS).after(end);
	}
}
