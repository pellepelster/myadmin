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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.SuggestOracle;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.jpql.LogicalOperatorVO;
import de.pellepelster.myadmin.client.base.jpql.RelationalOperator;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;

public class VOSuggestOracle<VOType extends IBaseVO> extends SuggestOracle
{
	private IReferenceControlModel referenceControlModel;

	public VOSuggestOracle(IReferenceControlModel referenceControlModel)
	{
		super();
		this.referenceControlModel = referenceControlModel;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void requestSuggestions(final Request request, final Callback callback)
	{

		DictionaryModelProvider.getDictionaryModel(referenceControlModel.getDictionaryName(), new AsyncCallback<IDictionaryModel>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				throw new RuntimeException("error loadingcached  dictionary '" + referenceControlModel.getDictionaryName() + "'");
			}

			@Override
			public void onSuccess(final IDictionaryModel dictionaryModel)
			{
				@SuppressWarnings("unchecked")
				GenericFilterVO<IBaseVO> genericFilterVO = new GenericFilterVO(dictionaryModel.getVOName());
				genericFilterVO.setLogicalOperator(LogicalOperatorVO.OR);
				genericFilterVO.setMaxResults(request.getLimit() + 1);

				for (IBaseControlModel baseControlModel : dictionaryModel.getLabelControls())
				{
					genericFilterVO.addCriteria(baseControlModel.getAttributePath(), request.getQuery(), RelationalOperator.LIKE);
				}

				MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(genericFilterVO, new AsyncCallback<List<IBaseVO>>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						throw new RuntimeException("error loading suggestions for '" + request.getQuery() + "'");
					}

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(List<IBaseVO> result)
					{
						Response response = new Response();
						Collection<VOSuggestion> voSuggestions = new ArrayList<VOSuggestion>();

						for (IBaseVO vo : result)
						{
							VOSuggestion voSuggestion = new VOSuggestion<VOType>(DictionaryUtil.getLabel(referenceControlModel, vo), (VOType) vo);
							voSuggestions.add(voSuggestion);
						}

						response.setSuggestions(voSuggestions);
						response.setMoreSuggestions(result.size() > request.getLimit());

						callback.onSuggestionsReady(request, response);
					}
				});
			}
		});
	}

}
