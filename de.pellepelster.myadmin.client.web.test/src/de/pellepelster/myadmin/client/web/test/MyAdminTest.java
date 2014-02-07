package de.pellepelster.myadmin.client.web.test;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.BaseModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.module.ModuleHandler;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModuleFactory;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MyAdminTest
{
	private static MyAdminTest instance;

	private JunitLayoutFactory junitLayoutFactory;

	private MyAdminTest()
	{
		this.junitLayoutFactory = new JunitLayoutFactory();
		MyAdmin.getInstance().setLayoutFactory(this.junitLayoutFactory);

	}

	public static MyAdminTest getInstance()
	{
		if (instance == null)
		{
			instance = new MyAdminTest();
		}

		return instance;
	}

	public <T extends IModuleUI> void startModule(final String moduleLocator, Class<T> moduleType, final String location, final AsyncCallback<T> asyncCallback)
	{
		MyAdminTest.this.junitLayoutFactory.setOneTimeCallback(moduleLocator, (AsyncCallback<IModuleUI<?, ?>>) asyncCallback);
		ModuleHandler.getInstance().startUIModule(moduleLocator, location);
	}

	public void openEditor(BaseModel baseModel, final AsyncCallback asyncCallback)
	{
		MyAdminTest.this.junitLayoutFactory.setOneTimeCallback(baseModel.getName(), asyncCallback);
		DictionaryEditorModuleFactory.openEditor(baseModel.getName());
	}

	public void deleteAllVOs(Class<? extends IBaseVO> voClass, final AsyncCallback asyncCallback)
	{
		MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().deleteAll(voClass.getName(), new BaseErrorAsyncCallback()
		{
			@Override
			public void onSuccess(Object result)
			{
				asyncCallback.onSuccess(null);
			}
		});
	}

	public void setLayoutFactoryOneTimeCallback(BaseModel baseModel, final AsyncCallback asyncCallback)
	{
		MyAdminTest.this.junitLayoutFactory.setOneTimeCallback(baseModel.getName(), asyncCallback);
	}

	public void openSearch(BaseModel baseModel, final AsyncCallback asyncCallback)
	{
		setLayoutFactoryOneTimeCallback(baseModel, asyncCallback);
		DictionarySearchModuleFactory.openSearch(baseModel.getName());
	}

}
