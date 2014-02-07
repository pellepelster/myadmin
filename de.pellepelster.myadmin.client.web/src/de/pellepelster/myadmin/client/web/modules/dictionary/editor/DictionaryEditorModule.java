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
package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.module.IModule;
import de.pellepelster.myadmin.client.base.module.ModuleUtils;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IButton;
import de.pellepelster.myadmin.client.base.modules.dictionary.editor.IEditorUpdateListener;
import de.pellepelster.myadmin.client.base.modules.dictionary.hooks.BaseEditorHook;
import de.pellepelster.myadmin.client.base.modules.dictionary.hooks.DictionaryHookRegistry;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelProvider;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDictionaryModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.BaseDictionaryEditorModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.DictionaryElementUtil;
import de.pellepelster.myadmin.client.web.modules.dictionary.IBaseDictionaryModule;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.DictionaryUtil;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

/**
 * Dictionary editor module
 * 
 * @author pelle
 * 
 */
public class DictionaryEditorModule<VOType extends IBaseVO> extends BaseDictionaryEditorModule implements IBaseDictionaryModule
{
	public final static String MODULE_LOCATOR = ModuleUtils.getBaseModuleUrl(MODULE_ID);

	public final static String UI_MODULE_LOCATOR = MODULE_LOCATOR;

	public static final String getModuleUrlForDictionary(String dictionaryName, long voId)
	{
		return UI_MODULE_LOCATOR + "&" + EDITORDICTIONARYNAME_PARAMETER_ID + "=" + dictionaryName + "&" + ID_PARAMETER_ID + "=" + voId;
	}

	public static final String getModuleUrlForDictionary(String dictionaryName)
	{
		return UI_MODULE_LOCATOR + "&" + EDITORDICTIONARYNAME_PARAMETER_ID + "=" + dictionaryName;
	}

	public enum EditorMode
	{
		INSERT, UPDATE;
	}

	private DictionaryEditor<VOType> dictionaryEditor;

	private IDictionaryModel dictionaryModel;

	public DictionaryEditorModule(String moduleUrl, AsyncCallback<IModule> moduleCallback, Map<String, Object> parameters)
	{
		super(moduleUrl, moduleCallback, parameters);

		init(ModuleUtils.getUrlParameter(moduleUrl, EDITORDICTIONARYNAME_PARAMETER_ID));
	}

	@Override
	public String getTitle()
	{
		return DictionaryUtil.getEditorLabel(this.dictionaryModel, this.dictionaryEditor);
	}

	public IDictionaryModel getDictionaryModel()
	{
		return this.dictionaryModel;
	}

	private void init(String dictionaryName)
	{

		DictionaryEditorModule.this.dictionaryModel = DictionaryModelProvider.getDictionary(dictionaryName);
		DictionaryEditorModule.this.dictionaryEditor = new DictionaryEditor<VOType>(this.dictionaryModel, getParameters());

		AsyncCallback<Void> asyncCallback = new BaseErrorAsyncCallback<Void>()
		{
			@Override
			public void onSuccess(Void result)
			{
				getModuleCallback().onSuccess(DictionaryEditorModule.this);
			}
		};

		if (hasId())
		{
			DictionaryEditorModule.this.dictionaryEditor.load(getId(), asyncCallback);
		}
		else
		{
			DictionaryEditorModule.this.dictionaryEditor.newVO(asyncCallback);
		}

	}

	public DictionaryEditor<VOType> getDictionaryEditor()
	{
		return this.dictionaryEditor;
	}

	@Override
	public <ElementType> ElementType getElement(BaseModel<ElementType> baseModel)
	{
		return DictionaryElementUtil.getElement(this.dictionaryEditor, baseModel);
	}

	public void addUpdateListener(IEditorUpdateListener updateListener)
	{
		this.dictionaryEditor.addUpdateListener(updateListener);
	}

	public List<IButton> getEditorButtons()
	{
		if (DictionaryHookRegistry.getInstance().hasEditorHook(getDictionaryModel().getName()))
		{
			List<IButton> buttons = new ArrayList<IButton>();

			for (BaseEditorHook<VOType> baseEditorHook : DictionaryHookRegistry.getInstance().getEditorHook(getDictionaryModel().getName()))
			{
				buttons.addAll(baseEditorHook.getEditorButtons(this.dictionaryEditor));
			}

			return buttons;
		}
		else
		{
			return Collections.emptyList();
		}
	}

	@Override
	public boolean isInstanceOf(String moduleUrl)
	{
		return MODULE_ID.equals(ModuleUtils.getModuleId(moduleUrl));
	}
}
