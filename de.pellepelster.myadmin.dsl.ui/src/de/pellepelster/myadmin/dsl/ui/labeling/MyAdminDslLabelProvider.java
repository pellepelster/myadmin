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
package de.pellepelster.myadmin.dsl.ui.labeling;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.DatatypeType;
import de.pellepelster.myadmin.dsl.myAdminDsl.SimpleType;
import de.pellepelster.myadmin.dsl.myAdminDsl.SimpleTypes;

/**
 * Provides labels for a EObjects.
 * 
 * see
 * http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class MyAdminDslLabelProvider extends DefaultEObjectLabelProvider
{

	@Inject
	public MyAdminDslLabelProvider(AdapterFactoryLabelProvider delegate)
	{
		super(delegate);
	}

	@Override
	protected Object doGetText(Object element)
	{
		if (element instanceof SimpleTypes)
		{
			SimpleTypes simpleType = (SimpleTypes) element;
			return simpleType.getName();
		}

		if (element instanceof SimpleType)
		{
			SimpleType simpleType = (SimpleType) element;
			return doGetText(simpleType.getType());
		}

		if (element instanceof Datatype)
		{
			Datatype datatype = (Datatype) element;
			return datatype.getName();
		}

		if (element instanceof DatatypeType)
		{
			DatatypeType datatypeType = (DatatypeType) element;
			return doGetText(datatypeType.getType());
		}

		return super.doGetText(element);
	}

}
