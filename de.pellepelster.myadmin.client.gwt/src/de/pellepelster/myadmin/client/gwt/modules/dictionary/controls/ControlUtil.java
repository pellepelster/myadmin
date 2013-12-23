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

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.jpql.GenericFilterVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelProvider;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IReferenceControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;

public class ControlUtil
{

	public static void populateListBox(final IReferenceControlModel referenceControlModel, final ListBox listBox)
	{
		IDictionaryModel dictionaryModel = DictionaryModelProvider.getDictionary(referenceControlModel.getDictionaryName());
		GenericFilterVO<IBaseVO> genericFilterVO = new GenericFilterVO(dictionaryModel.getVoName());

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
}
