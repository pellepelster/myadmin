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
package de.pellepelster.myadmin.tools.dictionary;

import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;

import de.pellepelster.myadmin.client.web.entities.dictionary.DICTIONARY_BASETYPEVO;
import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryDatatypeVO;
import de.pellepelster.myadmin.dsl.myAdminDsl.BigDecimalDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.BooleanDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.Datatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.DatatypeType;
import de.pellepelster.myadmin.dsl.myAdminDsl.DateDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.DictionaryControl;
import de.pellepelster.myadmin.dsl.myAdminDsl.EntityAttribute;
import de.pellepelster.myadmin.dsl.myAdminDsl.EnumerationDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.IntegerDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatype;
import de.pellepelster.myadmin.dsl.myAdminDsl.ReferenceDatatypeType;
import de.pellepelster.myadmin.dsl.myAdminDsl.TextDatatype;

/**
 * Factory for control creation
 * 
 * @author pelle
 * 
 */
public class DatatypeFactory
{

	private static DatatypeFactory instance;

	public static Datatype getDatatypeType(DictionaryControl dictionaryControl, boolean recurse)
	{

		if (dictionaryControl.getBaseControl() != null && dictionaryControl.getBaseControl().getEntityattribute() != null)
		{

			EntityAttribute entityAttribute = dictionaryControl.getBaseControl().getEntityattribute();

			if (entityAttribute.getType() instanceof DatatypeType)
			{
				return ((DatatypeType) entityAttribute.getType()).getType();
			}
			else if (entityAttribute.getType() instanceof ReferenceDatatypeType)
			{
				return ((ReferenceDatatypeType) entityAttribute.getType()).getType();
			}
			else
			{
				throw new RuntimeException(String.format("expected 'DatatypeType' but found '%s' for control '%s'", entityAttribute.getType(),
						dictionaryControl.getName()));
			}
		}
		else if (recurse)
		{

			DictionaryControl refControl = null;

			try
			{
				refControl = (DictionaryControl) PropertyUtils.getProperty(dictionaryControl, "ref");
			}
			catch (Exception e)
			{
				// ignore nonexisting refs
			}

			if (refControl != null)
			{
				Datatype result = getDatatypeType(refControl, recurse);

				if (result != null)
				{
					return result;
				}
			}
		}

		return null;
	}

	public static DatatypeFactory getInstance()
	{
		if (instance == null)
		{
			instance = new DatatypeFactory();
		}
		return instance;
	}

	private DatatypeFactory()
	{
	}

	private void createBigDecimalDatatype(BigDecimalDatatype bigDecimalDatatype, DictionaryDatatypeVO datatypeVO, int logIdentiation)
	{

		datatypeVO.setBaseType(DICTIONARY_BASETYPEVO.BIGDECIMAL);
		datatypeVO.setTotalDigits(bigDecimalDatatype.getTotalDigits());
		datatypeVO.setFractionDigits(bigDecimalDatatype.getFractionDigits());
	}

	private void createBooleanDatatype(BooleanDatatype booleanDatatype, DictionaryDatatypeVO datatypeVO, int logIdentiation)
	{
		datatypeVO.setBaseType(DICTIONARY_BASETYPEVO.BOOLEAN);
	}

	public DictionaryDatatypeVO createDatatypeVO(DictionaryControl dictionaryControl, int logIdentiation)
	{
		DictionaryDatatypeVO datatypeVO = new DictionaryDatatypeVO();
		datatypeVO.setName(UUID.randomUUID().toString());

		Datatype datatype = getDatatypeType(dictionaryControl, true);

		if (datatype instanceof EnumerationDatatype)
		{
			createEnumerationDatatype((EnumerationDatatype) datatype, datatypeVO, logIdentiation + 1);
		}
		else if (datatype instanceof BooleanDatatype)
		{
			createBooleanDatatype((BooleanDatatype) datatype, datatypeVO, logIdentiation + 1);
		}
		else if (datatype instanceof IntegerDatatype)
		{
			createIntegerDatatype((IntegerDatatype) datatype, datatypeVO, logIdentiation + 1);
		}
		else if (datatype instanceof BigDecimalDatatype)
		{
			createBigDecimalDatatype((BigDecimalDatatype) datatype, datatypeVO, logIdentiation + 1);
		}
		else if (datatype instanceof ReferenceDatatype)
		{
			createReferenceDatatype((ReferenceDatatype) datatype, datatypeVO, logIdentiation + 1);
		}
		else if (datatype instanceof TextDatatype)
		{
			createTextDatatype((TextDatatype) datatype, datatypeVO, logIdentiation + 1);
		}
		else if (datatype instanceof DateDatatype)
		{
			createDateDatatype((DateDatatype) datatype, datatypeVO, logIdentiation + 1);
		}
		else
		{
			throw new RuntimeException(String.format("unsupported datatype '%s'", datatype.getClass().getName()));
		}

		return datatypeVO;
	}

	private void createDateDatatype(DateDatatype dateDatatype, DictionaryDatatypeVO datatypeVO, int logIdentiation)
	{
		datatypeVO.setBaseType(DICTIONARY_BASETYPEVO.DATE);
	}

	private void createEnumerationDatatype(EnumerationDatatype enumerationDatatype, DictionaryDatatypeVO datatypeVO, int logIdentiation)
	{

		// - enumeration -----------------------------------------------
		datatypeVO.setBaseType(DICTIONARY_BASETYPEVO.ENUMERATION);

		datatypeVO.setEntity(enumerationDatatype.getEnumeration().getName());
		for (String enumerationValue : enumerationDatatype.getEnumeration().getEnumerationValues())
		{
			datatypeVO.getEnumerationValues().put(enumerationValue, enumerationValue);
		}
	}

	private void createIntegerDatatype(IntegerDatatype integerDatatype, DictionaryDatatypeVO datatypeVO, int logIdentiation)
	{
		datatypeVO.setBaseType(DICTIONARY_BASETYPEVO.INTEGER);
	}

	private void createReferenceDatatype(ReferenceDatatype referenceDatatype, DictionaryDatatypeVO datatypeVO, int logIdentiation)
	{
		datatypeVO.setBaseType(DICTIONARY_BASETYPEVO.REFERENCE);
		datatypeVO.setEntity(referenceDatatype.getEntity().getName());
	}

	private void createTextDatatype(TextDatatype textDatatype, DictionaryDatatypeVO datatypeVO, int logIdentiation)
	{
		datatypeVO.setBaseType(DICTIONARY_BASETYPEVO.TEXT);
		datatypeVO.setMaxLength(textDatatype.getMaxLength());
	}
}
