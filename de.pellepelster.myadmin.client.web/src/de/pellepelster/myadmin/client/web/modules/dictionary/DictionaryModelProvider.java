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
package de.pellepelster.myadmin.client.web.modules.dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.MyAdmin;

public class DictionaryModelProvider
{

	private static Map<String, IDictionaryModel> dictionaryCache = new HashMap<String, IDictionaryModel>();

	@Deprecated
	public static IDictionaryModel getCachedDictionaryModelForClass(Class<? extends IBaseVO> voClass)
	{
		for (Map.Entry<String, IDictionaryModel> entry : dictionaryCache.entrySet())
		{
			if (entry.getValue().getVOName().equals(voClass.getName()))
			{
				return entry.getValue();
			}
		}

		throw new RuntimeException("no dictionary model found for vo class '" + voClass.getName() + "'");
	}

	public static void cacheDictionaryModels(List<String> dictionaryNames, final AsyncCallback<List<IDictionaryModel>> callback)
	{
		List<String> dictionaryNamesToGet = dictionaryNames;

		for (Iterator<String> dictionaryNamesIterator = dictionaryNamesToGet.iterator(); dictionaryNamesIterator.hasNext();)
		{
			String dictionaryname = dictionaryNamesIterator.next();

			if (dictionaryCache.containsKey(dictionaryname))
			{
				dictionaryNamesIterator.remove();
			}
		}

		if (dictionaryNamesToGet.isEmpty())
		{
			List<IDictionaryModel> dictionaryModels = new ArrayList<IDictionaryModel>();

			for (String dictionnaryName : dictionaryNames)
			{
				dictionaryModels.add(dictionaryCache.get(dictionnaryName));
			}

			callback.onSuccess(dictionaryModels);
			return;
		}

		MyAdmin.getInstance().getRemoteServiceLocator().getDictionaryService().getDictionaries(dictionaryNames, new AsyncCallback<List<IDictionaryModel>>()
		{

			/** {@inheritDoc} */
			@Override
			public void onFailure(Throwable caught)
			{
				if (callback != null)
				{
					callback.onFailure(caught);
				}
			}

			/**
			 * @param result
			 */
			@Override
			public void onSuccess(List<IDictionaryModel> result)
			{

				List<IDictionaryModel> dictionaryModels = new ArrayList<IDictionaryModel>();
				for (IDictionaryModel dictionaryModel : result)
				{
					dictionaryCache.put(dictionaryModel.getName(), dictionaryModel);
					dictionaryModels.add(dictionaryModel);
				}

				if (callback != null)
				{
					callback.onSuccess(dictionaryModels);
				}
			}
		});
	}

	public static List<IDictionaryModel> getCachedDictionaryModels(List<String> dictionaryNames)
	{
		List<IDictionaryModel> dictionaryModels = new ArrayList<IDictionaryModel>();

		for (String dictionaryName : dictionaryNames)
		{
			dictionaryModels.add(getCachedDictionaryModel(dictionaryName));
		}

		return dictionaryModels;
	}

	public static IDictionaryModel getCachedDictionaryModel(String dictionaryName)
	{
		if (dictionaryCache.containsKey(dictionaryName))
		{
			return dictionaryCache.get(dictionaryName);
		}
		else
		{
			throw new RuntimeException("dictionary model '" + dictionaryName + "' not cached");
		}
	}

	public static void getDictionaryModel(String dictionaryName, final AsyncCallback<IDictionaryModel> callback)
	{
		if (dictionaryCache.containsKey(dictionaryName))
		{
			callback.onSuccess(dictionaryCache.get(dictionaryName));
		}
		else
		{
			MyAdmin.getInstance().getRemoteServiceLocator().getDictionaryService().getDictionary(dictionaryName, new AsyncCallback<IDictionaryModel>()
			{

				/** {@inheritDoc} */
				@Override
				public void onFailure(Throwable caught)
				{
					callback.onFailure(caught);
				}

				/**
				 * @param result
				 */
				@Override
				public void onSuccess(IDictionaryModel dictionaryModel)
				{
					dictionaryCache.put(dictionaryModel.getName(), dictionaryModel);
					callback.onSuccess(dictionaryModel);
				}
			});
		}
	}
}
