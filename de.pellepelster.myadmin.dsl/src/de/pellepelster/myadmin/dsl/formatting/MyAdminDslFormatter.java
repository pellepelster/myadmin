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
import org.eclipse.xtext.ParserRule;
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
	final int BLOCK_MIN_WRAPS = 1;

	final int BLOCK_DEFAULT_WRAPS = 2;

	final int BLOCK_MAX_WRAPS = 2;

	final int LINE_MIN_WRAPS = 1;

	final int LINE_DEFAULT_WRAPS = 1;

	final int LINE_MAX_WRAPS = 2;

	@Override
	protected void configureFormatting(FormattingConfig c)
	{
		MyAdminDslGrammarAccess m = (MyAdminDslGrammarAccess) getGrammarAccess();
		c.setAutoLinewrap(160);

		// formatting for comments
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).before(m.getSL_COMMENTRule());
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).before(m.getML_COMMENTRule());
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).after(m.getML_COMMENTRule());

		// model/entity/package
		setBlockFormatting(c, m.getModelAccess().getLeftCurlyBracketKeyword_3(), m.getModelAccess().getRightCurlyBracketKeyword_5());
		setBlockFormatting(c, m.getPackageDeclarationRule(), m.getPackageDeclarationAccess().getLeftCurlyBracketKeyword_2(), m.getPackageDeclarationAccess()
				.getRightCurlyBracketKeyword_4());
		setBlockFormatting(c, m.getEntityRule(), m.getEntityAccess().getLeftCurlyBracketKeyword_4(), m.getEntityAccess().getRightCurlyBracketKeyword_8());
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).after(m.getEntityAttributeRule());

		// enum
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).before(m.getEnumerationRule());
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).after(m.getEnumerationRule());

		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).after(m.getEntityAccess().getRightCurlyBracketKeyword_6_3());

		// text
		setBlockFormatting(c, m.getTextDatatypeRule(), m.getTextDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getTextDatatypeAccess()
				.getRightCurlyBracketKeyword_7());
		setBlockFormatting(c, m.getDictionaryTextControlRule(), m.getDictionaryTextControlAccess().getLeftCurlyBracketKeyword_4_0(), m
				.getDictionaryTextControlAccess().getRightCurlyBracketKeyword_4_2());
		c.setLinewrap(this.LINE_MIN_WRAPS, this.LINE_DEFAULT_WRAPS, this.LINE_MAX_WRAPS).after(m.getDictionaryTextControlAccess().getRule());

		// integer
		setBlockFormatting(c, m.getIntegerDatatypeRule(), m.getIntegerDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getIntegerDatatypeAccess()
				.getRightCurlyBracketKeyword_6());
		setBlockFormatting(c, m.getDictionaryIntegerControlRule(), m.getDictionaryIntegerControlAccess().getLeftCurlyBracketKeyword_4_0(), m
				.getDictionaryIntegerControlAccess().getRightCurlyBracketKeyword_4_2());
		c.setLinewrap(this.LINE_MIN_WRAPS, this.LINE_DEFAULT_WRAPS, this.LINE_MAX_WRAPS).after(m.getDictionaryIntegerControlAccess().getRule());

		// bigdecimal
		setBlockFormatting(c, m.getDictionaryIntegerControlRule(), m.getBigDecimalDatatypeAccess().getLeftCurlyBracketKeyword_2(), m
				.getBigDecimalDatatypeAccess().getRightCurlyBracketKeyword_10());
		setBlockFormatting(c, m.getDictionaryBigDecimalControlRule(), m.getDictionaryBigDecimalControlAccess().getLeftCurlyBracketKeyword_4_0(), m
				.getDictionaryBigDecimalControlAccess().getRightCurlyBracketKeyword_4_2());
		c.setLinewrap(this.LINE_MIN_WRAPS, this.LINE_DEFAULT_WRAPS, this.LINE_MAX_WRAPS).after(m.getDictionaryBigDecimalControlAccess().getRule());

		// boolean
		setBlockFormatting(c, m.getBooleanDatatypeRule(), m.getBooleanDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getBooleanDatatypeAccess()
				.getRightCurlyBracketKeyword_4());
		setBlockFormatting(c, m.getDictionaryBooleanControlRule(), m.getDictionaryBooleanControlAccess().getLeftCurlyBracketKeyword_4_0(), m
				.getDictionaryBooleanControlAccess().getRightCurlyBracketKeyword_4_2());
		c.setLinewrap(this.LINE_MIN_WRAPS, this.LINE_DEFAULT_WRAPS, this.LINE_MAX_WRAPS).after(m.getDictionaryBooleanControlAccess().getRule());

		// date
		setBlockFormatting(c, m.getDateDatatypeRule(), m.getDateDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getDateDatatypeAccess()
				.getRightCurlyBracketKeyword_4());
		setBlockFormatting(c, m.getDictionaryDateControlRule(), m.getDictionaryDateControlAccess().getLeftCurlyBracketKeyword_4_0(), m
				.getDictionaryDateControlAccess().getRightCurlyBracketKeyword_4_2());
		c.setLinewrap(this.LINE_MIN_WRAPS, this.LINE_DEFAULT_WRAPS, this.LINE_MAX_WRAPS).after(m.getDictionaryDateControlAccess().getRule());

		// enumeration
		setBlockFormatting(c, m.getEnumerationDatatypeRule(), m.getEnumerationDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getEnumerationDatatypeAccess()
				.getRightCurlyBracketKeyword_6());
		setBlockFormatting(c, m.getDictionaryEnumerationControlRule(), m.getDictionaryEnumerationControlAccess().getLeftCurlyBracketKeyword_4_0(), m
				.getDictionaryEnumerationControlAccess().getRightCurlyBracketKeyword_4_2());
		c.setLinewrap(this.LINE_MIN_WRAPS, this.LINE_DEFAULT_WRAPS, this.LINE_MAX_WRAPS).after(m.getDictionaryEnumerationControlAccess().getRule());

		// reference
		setBlockFormatting(c, m.getReferenceDatatypeRule(), m.getReferenceDatatypeAccess().getLeftCurlyBracketKeyword_2(), m.getReferenceDatatypeAccess()
				.getRightCurlyBracketKeyword_6());
		setBlockFormatting(c, m.getDictionaryReferenceControlRule(), m.getDictionaryReferenceControlAccess().getLeftCurlyBracketKeyword_4_0(), m
				.getDictionaryReferenceControlAccess().getRightCurlyBracketKeyword_4_6());
		c.setLinewrap(this.LINE_MIN_WRAPS, this.LINE_DEFAULT_WRAPS, this.LINE_MAX_WRAPS).after(m.getDictionaryReferenceControlAccess().getRule());

		// navigation
		setBlockFormatting(c, m.getNavigationNodeRule(), m.getNavigationNodeAccess().getLeftCurlyBracketKeyword_2(), m.getNavigationNodeAccess()
				.getRightCurlyBracketKeyword_8());

		// dictionary
		setBlockFormatting(c, m.getDictionaryRule(), m.getDictionaryAccess().getLeftCurlyBracketKeyword_2(), m.getDictionaryAccess()
				.getRightCurlyBracketKeyword_11());

		// dictionary controls
		setBlockFormatting(c, m.getDictionaryRule(), m.getDictionaryAccess().getLeftCurlyBracketKeyword_7_1(), m.getDictionaryAccess()
				.getRightCurlyBracketKeyword_7_3());
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).after(
				m.getDictionaryAccess().getDictionarycontrolsDictionaryControlParserRuleCall_7_2_0());
		setBlockFormatting(c, m.getDictionaryRule(), m.getDictionaryAccess().getLabelcontrolsKeyword_8_0(), m.getDictionaryAccess()
				.getRightCurlyBracketKeyword_8_2());

		// dictionary composite
		setBlockFormatting(c, m.getDictionaryCompositeRule(), m.getDictionaryCompositeAccess().getLeftCurlyBracketKeyword_2(), m.getDictionaryCompositeAccess()
				.getRightCurlyBracketKeyword_6());

		// editable table
		setBlockFormatting(c, m.getDictionaryEditableTableRule(), m.getDictionaryEditableTableAccess().getLeftCurlyBracketKeyword_2(), m
				.getDictionaryEditableTableAccess().getRightCurlyBracketKeyword_11());
		setBlockFormatting(c, m.getDictionaryEditableTableRule(), m.getDictionaryEditableTableAccess().getColumncontrolsKeyword_8(), m
				.getDictionaryEditableTableAccess().getRightCurlyBracketKeyword_10());
		c.setLinewrap(this.LINE_MAX_WRAPS, this.LINE_DEFAULT_WRAPS, this.LINE_MAX_WRAPS).after(
				m.getDictionaryEditableTableAccess().getEntityattributeAssignment_7());

		// dictionary entity
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).after(m.getDictionaryAccess().getEntityAssignment_4());

		setBlockFormatting(c, m.getDictionaryEditorRule(), m.getDictionaryEditorAccess().getLeftCurlyBracketKeyword_2(), m.getDictionaryEditorAccess()
				.getRightCurlyBracketKeyword_5());
		setBlockFormatting(c, m.getDictionarySearchRule(), m.getDictionarySearchAccess().getLeftCurlyBracketKeyword_2(), m.getDictionarySearchAccess()
				.getRightCurlyBracketKeyword_6());
		setBlockFormatting(c, m.getDictionaryResultRule(), m.getDictionaryResultAccess().getLeftCurlyBracketKeyword_2(), m.getDictionaryResultAccess()
				.getRightCurlyBracketKeyword_4());
		setBlockFormatting(c, m.getDictionaryFilterRule(), m.getDictionaryFilterAccess().getLeftCurlyBracketKeyword_2(), m.getDictionaryFilterAccess()
				.getRightCurlyBracketKeyword_4());

		// services
		setBlockFormatting(c, m.getRemoteServiceRule(), m.getRemoteServiceAccess().getLeftCurlyBracketKeyword_2(), m.getRemoteServiceAccess()
				.getRightCurlyBracketKeyword_5());
		setBlockFormatting(c, m.getRemoteMethodRule(), m.getRemoteMethodAccess().getLeftCurlyBracketKeyword_4(), m.getRemoteMethodAccess()
				.getRightCurlyBracketKeyword_7());
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).after(m.getMethodParameterRule());

		for (Keyword comma : m.findKeywords(","))
		{
			c.setNoSpace().before(comma);
		}

	}

	private void setBlockFormatting(FormattingConfig c, EObject start, EObject end)
	{
		setBlockFormatting(c, null, start, end);
	}

	private void setBlockFormatting(FormattingConfig c, ParserRule parserRule, EObject start, EObject end)
	{
		if (parserRule != null)
		{
			c.setLinewrap().before(parserRule);
		}
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).after(start);
		c.setIndentationIncrement().after(start);
		c.setIndentationDecrement().before(end);
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).before(end);
		c.setLinewrap(this.BLOCK_MIN_WRAPS, this.BLOCK_DEFAULT_WRAPS, this.BLOCK_MAX_WRAPS).after(end);
	}
}
