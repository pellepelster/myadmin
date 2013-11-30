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

import java.text.MessageFormat;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.validation.Check;

import com.google.common.base.Objects;

import de.pellepelster.myadmin.dsl.Messages;
import de.pellepelster.myadmin.dsl.myAdminDsl.BaseDictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.Dictionary;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryAssignmentTable;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryBigDecimalControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryBooleanControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryDateControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEditableTable;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryEnumerationControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryIntegerControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryReferenceControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryTextControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.Entity;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.MyAdminDslPackage;
import de.pellepelster.myadmin.dsl.util.EntityModelUtil;
import de.pellepelster.myadmin.dsl.util.ModelUtil;

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

	public static ModelValidationMessage validateJavaIdentifier(String identifier)
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
	public void checkDatatype(Datatype datatype)
	{
		convertValidationMessageTo(validateJavaIdentifier(datatype.getName()), MyAdminDslPackage.Literals.DATATYPE__NAME);
	}

	@Check
	public void checkDictionaryControl(BaseDictionaryControl baseDictionaryControl)
	{

		DictionaryControl dictionaryControl = ModelUtil.getParentEObject(baseDictionaryControl, DictionaryControl.class);
		EntityAttribute entityAttribute = ModelUtil.getEntityAttribute(dictionaryControl);

		Dictionary dictionary = ModelUtil.getParentDictionary(dictionaryControl);
		Entity entity = ModelUtil.getParentEObject(entityAttribute, Entity.class);

		if (!Objects.equal(entity, dictionary.getEntity()))
		{
			Object[] messageTokens = new Object[] { entityAttribute.getName(), ModelUtil.getControlName(dictionaryControl), entity.getName(),
					dictionary.getEntity().getName(), Messages.Dictionary };

			if (baseDictionaryControl.eContainer().eContainer() instanceof Dictionary)
			{
				warning(MessageFormat.format(Messages.ControlEntityAttributeDoesNotMatchParent, messageTokens),
						MyAdminDslPackage.Literals.BASE_DICTIONARY_CONTROL__ENTITYATTRIBUTE);
			}
			else
			{
				error(MessageFormat.format(Messages.ControlEntityAttributeDoesNotMatchParent, messageTokens),
						MyAdminDslPackage.Literals.BASE_DICTIONARY_CONTROL__ENTITYATTRIBUTE);
			}

		}
	}

	private void checkDictionaryControl(DictionaryControl dictionaryControl, EStructuralFeature referenceFeature)
	{
		// control has entity own attribute
		if (dictionaryControl.getBaseControl() != null && dictionaryControl.getBaseControl().getEntityattribute() != null)
		{
			// we have an extra check for
			// BaseDictionaryControl.entityattribute
			return;
		}

		EntityAttribute entityAttribute = ModelUtil.getEntityAttribute(dictionaryControl);
		Entity controlEntity = ModelUtil.getParentEObject(entityAttribute, Entity.class);

		Dictionary dictionary = ModelUtil.getParentDictionary(dictionaryControl);
		DictionaryEditableTable dictionaryEditableTable = ModelUtil.getParentEObject(dictionaryControl, DictionaryEditableTable.class);
		DictionaryAssignmentTable dictionaryAssignmentTable = ModelUtil.getParentEObject(dictionaryControl, DictionaryAssignmentTable.class);
		DictionaryReferenceControl dictionaryReferenceControl = ModelUtil.getParentEObject(dictionaryControl, DictionaryReferenceControl.class);

		Entity entity = null;
		String parentElementType = null;

		if (dictionaryEditableTable != null)
		{
			entity = EntityModelUtil.getEntity(dictionaryEditableTable.getEntityattribute());
			parentElementType = Messages.EditableTable;
		}
		else if (dictionaryAssignmentTable != null)
		{
			entity = EntityModelUtil.getEntity(dictionaryAssignmentTable.getEntityattribute());
			parentElementType = Messages.AssignmentTable;
		}
		else if (dictionaryReferenceControl != null)
		{
			dictionary = dictionaryReferenceControl.getDictionary();
			entity = dictionary.getEntity();
			parentElementType = Messages.ReferenceControl;
		}
		else
		{
			entity = dictionary.getEntity();
			parentElementType = Messages.Dictionary;
		}

		Object[] messageTokens = new Object[] { entityAttribute.getName(), ModelUtil.getControlName(dictionaryControl), entity.getName(),
				dictionary.getEntity().getName(), parentElementType };

		if (ModelUtil.getControlRef(dictionaryControl) != null && !Objects.equal(controlEntity, entity))
		{
			error(MessageFormat.format(Messages.ControlEntityAttributeDoesNotMatchParent, messageTokens), referenceFeature);
		}
	}

	// DictionaryControlGroup?
	@Check
	public void checkDictionaryControl(DictionaryTextControl dictionaryTextControl)
	{
		checkDictionaryControl(dictionaryTextControl, MyAdminDslPackage.Literals.DICTIONARY_TEXT_CONTROL__REF);
	}

	@Check
	public void checkDictionaryControl(DictionaryIntegerControl dictionaryIntegerControl)
	{
		checkDictionaryControl(dictionaryIntegerControl, MyAdminDslPackage.Literals.DICTIONARY_INTEGER_CONTROL__REF);
	}

	@Check
	public void checkDictionaryControl(DictionaryBigDecimalControl dictionaryBigDecimalControl)
	{
		checkDictionaryControl(dictionaryBigDecimalControl, MyAdminDslPackage.Literals.DICTIONARY_BIG_DECIMAL_CONTROL__REF);
	}

	@Check
	public void checkDictionaryControl(DictionaryBooleanControl dictionaryBooleanControl)
	{
		checkDictionaryControl(dictionaryBooleanControl, MyAdminDslPackage.Literals.DICTIONARY_BOOLEAN_CONTROL__REF);
	}

	@Check
	public void checkDictionaryControl(DictionaryDateControl dictionaryDateControl)
	{
		checkDictionaryControl(dictionaryDateControl, MyAdminDslPackage.Literals.DICTIONARY_DATE_CONTROL__REF);
	}

	@Check
	public void checkDictionaryControl(DictionaryEnumerationControl dictionaryEnumerationControl)
	{
		checkDictionaryControl(dictionaryEnumerationControl, MyAdminDslPackage.Literals.DICTIONARY_ENUMERATION_CONTROL__REF);
	}

	@Check
	public void checkDictionaryControl(DictionaryReferenceControl dictionaryReferenceControl)
	{
		checkDictionaryControl(dictionaryReferenceControl, MyAdminDslPackage.Literals.DICTIONARY_REFERENCE_CONTROL__REF);
	}

}
