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
package de.pellepelster.myadmin.dsl.ui.contentassist;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.ui.editor.contentassist.ContentAssistContext;
import org.eclipse.xtext.ui.editor.contentassist.ICompletionProposalAcceptor;

import de.pellepelster.myadmin.dsl.myAdminDsl.Dictionary;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;

/**
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#contentAssist on
 * how to customize content assistant
 */
public class MyAdminDslProposalProvider extends AbstractMyAdminDslProposalProvider
{

	private String getParentPropopsal(EObject model, Class<? extends EObject> parentClass, EStructuralFeature eStructuralFeature, String proposalPostfix)
	{
		EObject parent = model;

		while (parent.eContainer() != null && !parentClass.isAssignableFrom(parent.getClass()))
		{
			parent = parent.eContainer();
		}

		if (parent == null || !parentClass.isAssignableFrom(parent.getClass()))
		{
			return null;
		}

		Object proposalObject = parent.eGet(eStructuralFeature);

		if (proposalObject == null)
		{
			return null;
		}

		return String.format("%s%s", proposalObject.toString(), proposalPostfix);

	}

	@Override
	public void completeDictionarySearch_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		super.completeDictionarySearch_Name(model, assignment, context, acceptor);
		String proposal = getParentPropopsal(model, Dictionary.class, MyAdminDslPackage.Literals.DICTIONARY__NAME,
				MyAdminDslPackage.Literals.DICTIONARY_SEARCH.getName());
		acceptor.accept(createCompletionProposal(proposal, context));
	}

	@Override
	public void completeDictionaryResult_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		super.completeDictionarySearch_Name(model, assignment, context, acceptor);
		String proposal = getParentPropopsal(model, Dictionary.class, MyAdminDslPackage.Literals.DICTIONARY__NAME,
				MyAdminDslPackage.Literals.DICTIONARY_RESULT.getName());
		acceptor.accept(createCompletionProposal(proposal, context));
	}

	@Override
	public void completeDictionaryEditor_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		super.completeDictionarySearch_Name(model, assignment, context, acceptor);
		String proposal = getParentPropopsal(model, Dictionary.class, MyAdminDslPackage.Literals.DICTIONARY__NAME,
				MyAdminDslPackage.Literals.DICTIONARY_EDITOR.getName());
		acceptor.accept(createCompletionProposal(proposal, context));
	}

	@Override
	public void completeDictionaryFilter_Name(EObject model, Assignment assignment, ContentAssistContext context, ICompletionProposalAcceptor acceptor)
	{
		super.completeDictionarySearch_Name(model, assignment, context, acceptor);
		String proposal = getParentPropopsal(model, Dictionary.class, MyAdminDslPackage.Literals.DICTIONARY__NAME,
				MyAdminDslPackage.Literals.DICTIONARY_FILTER.getName());
		acceptor.accept(createCompletionProposal(proposal, context));
	}

}
