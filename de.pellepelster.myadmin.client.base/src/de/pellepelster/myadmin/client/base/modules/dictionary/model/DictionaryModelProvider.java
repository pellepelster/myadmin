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
package de.pellepelster.myadmin.client.base.modules.dictionary.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public class DictionaryModelProvider
{

	private static Map<String, IDictionaryModel> dictionaries = new HashMap<String, IDictionaryModel>();

	public static IDictionaryModel getDictionary(String dictionaryName)
	{
		if (dictionaries.containsKey(dictionaryName))
		{
			return dictionaries.get(dictionaryName);
		}
		else
		{
			throw new RuntimeException("dictionary '" + dictionaryName + "' not found");
		}
	}

	@Deprecated
	public static IDictionaryModel getDictionaryModelForClass(Class<? extends IBaseVO> voClass)
	{
		for (Map.Entry<String, IDictionaryModel> entry : dictionaries.entrySet())
		{
			if (entry.getValue().getVoName().equals(voClass.getName()))
			{
				return entry.getValue();
			}
		}

		throw new RuntimeException("no dictionary model found for vo class '" + voClass.getName() + "'");
	}

	public static void registerDictionary(IDictionaryModel dictionary)
	{
		dictionaries.put(dictionary.getName(), dictionary);
	}

	public static Collection<IDictionaryModel> getAllDictionaries()
	{
		return dictionaries.values();
	}

}
