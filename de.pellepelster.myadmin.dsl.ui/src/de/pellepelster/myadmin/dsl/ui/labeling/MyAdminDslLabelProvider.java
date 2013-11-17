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

import java.lang.reflect.Method;

import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.xtext.ui.IImageHelper;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;

import com.google.inject.Inject;

import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.DatatypeType;
import de.pellepelster.myadmin.dsl.myAdminDsl.Dictionary;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryAssignmentTable;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryComposite;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEditableTable;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEditor;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryFilter;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryResult;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionarySearch;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.NavigationNode;
import de.pellepelster.myadmin.dsl.myAdminDsl.PackageDeclaration;
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
	private IImageHelper imageHelper;

	@Inject
	public MyAdminDslLabelProvider(AdapterFactoryLabelProvider delegate)
	{
		super(delegate);
	}

	protected String text(SimpleTypes simpleTypes)
	{
		return simpleTypes.getName();
	}

	protected String text(SimpleType simpleType)
	{
		return text(simpleType.getType());
	}

	protected String text(Datatype datatype)
	{
		return datatype.getName();
	}

	protected String text(DatatypeType datatypeType)
	{
		return text(datatypeType.getType());
	}

	private DictionaryControl getRef(DictionaryControl dictionaryControl)
	{
		try
		{
			Method getRefMethod = dictionaryControl.getClass().getMethod("getRef");
			Object result = getRefMethod.invoke(dictionaryControl, new Object[] {});

			return (DictionaryControl) result;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private String getName(DictionaryControl dictionaryControl)
	{
		try
		{
			Method getRefMethod = dictionaryControl.getClass().getMethod("getName");
			Object result = getRefMethod.invoke(dictionaryControl, new Object[] {});

			return (String) result;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	protected String text(DictionaryControl dictionaryControl)
	{
		DictionaryControl ref = getRef(dictionaryControl);

		if (ref == null)
		{
			return getName(dictionaryControl);
		}
		else
		{
			return text(ref);
		}
	}

	protected Object image(PackageDeclaration element)
	{
		return this.imageHelper.getImage("packageDeclaration.png");
	}

	protected Object image(Dictionary element)
	{
		return this.imageHelper.getImage("dictionary.png");
	}

	protected Object image(DictionarySearch element)
	{
		return this.imageHelper.getImage("dictionarySearch.png");
	}

	protected Object image(Entity element)
	{
		return this.imageHelper.getImage("entity.png");
	}

	protected Object image(Datatype element)
	{
		return this.imageHelper.getImage("datatype.png");
	}

	protected Object image(DictionaryComposite element)
	{
		return this.imageHelper.getImage("composite.png");
	}

	protected Object image(DictionaryEditableTable element)
	{
		return this.imageHelper.getImage("dictionaryEditableTable.png");
	}

	protected Object image(DictionaryAssignmentTable element)
	{
		return this.imageHelper.getImage("dictionaryAssignmentTable.png");
	}

	protected Object image(EntityAttribute element)
	{
		return this.imageHelper.getImage("entityAttribute.png");
	}

	protected Object image(DictionaryResult element)
	{
		return this.imageHelper.getImage("dictionaryResult.png");
	}

	protected Object image(DictionaryFilter element)
	{
		return this.imageHelper.getImage("dictionaryFilter.png");
	}

	protected Object image(DictionaryControl dictionaryControl)
	{
		DictionaryControl ref = getRef(dictionaryControl);

		if (ref == null)
		{
			return this.imageHelper.getImage("dictionaryControl.png");
		}
		else
		{
			return this.imageHelper.getImage("dictionaryControlRef.png");
		}

	}

	protected Object image(NavigationNode element)
	{
		return this.imageHelper.getImage("navigationNode.png");
	}

	protected Object image(DictionaryEditor element)
	{
		return this.imageHelper.getImage("dictionaryEditor.png");
	}

}
