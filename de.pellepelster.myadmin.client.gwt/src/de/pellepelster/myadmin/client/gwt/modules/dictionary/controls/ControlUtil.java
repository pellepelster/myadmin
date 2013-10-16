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

import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.gwt.modules.dictionary.container.EditableTable;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryModelProvider;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;

public class ControlUtil
{

	public static boolean hasFirstEditMarker(Context context, IBaseControlModel baseControlModel)
	{
		if (context.getKey() == null || !(context.getKey() instanceof IBaseVO))
		{
			throw new RuntimeException("context key is null or not IBaseVO");
		}

		return hasFirstEditMarker((IBaseVO) context.getKey(), baseControlModel);
	}

	public static boolean hasFirstEditMarker(IBaseVO vo, IBaseControlModel baseControlModel)
	{
		return vo.getData().containsKey(baseControlModel.getName())
				&& vo.getData().get(baseControlModel.getName()).equals(EditableTable.CONTROL_FIRST_EDIT_DATA_KEY);
	}

	public static void removeFirstEditMarker(Context context, IBaseControlModel baseControlModel)
	{
		if (context.getKey() == null || !(context.getKey() instanceof IBaseVO))
		{
			throw new RuntimeException("context key is null or not IBaseVO");
		}

		removeFirstEditMarker((IBaseVO) context.getKey(), baseControlModel);
	}

	public static void removeFirstEditMarker(IBaseVO vo, IBaseControlModel baseControlModel)
	{
		vo.getData().remove(baseControlModel.getName());
	}

	public static void populateListBox(final IReferenceControlModel referenceControlModel, final ListBox listBox)
	{
		DictionaryModelProvider.getDictionaryModel(referenceControlModel.getDictionaryName(), new AsyncCallback<IDictionaryModel>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				throw new RuntimeException("error loading cached  dictionary '" + referenceControlModel.getDictionaryName() + "'");
			}

			@SuppressWarnings("rawtypes")
			@Override
			public void onSuccess(final IDictionaryModel dictionaryModel)
			{
				@SuppressWarnings("unchecked")
				GenericFilterVO<IBaseVO> genericFilterVO = new GenericFilterVO(dictionaryModel.getVOName());

				MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().filter(genericFilterVO, new AsyncCallback<List<IBaseVO>>()
				{

					@Override
					public void onFailure(Throwable caught)
					{
						throw new RuntimeException("error loading reference  content for '" + referenceControlModel.getName() + "'");
					}

					@Override
					public void onSuccess(List<IBaseVO> result)
					{
						listBox.addItem("");

						for (IBaseVO vo : result)
						{
							listBox.addItem(DictionaryUtil.getLabel(referenceControlModel, vo));
						}
					}
				});
			}
		});

	}

}
