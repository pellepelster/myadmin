package de.pellepelster.myadmin.client.web.test;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.layout.IModuleUI;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.modules.dictionary.search.DictionarySearchModuleFactory;

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

	public <T extends IModuleUI> void startModule(final String moduleName, Class<T> moduleType, final String location, final AsyncCallback<T> asyncCallback)
	{
		MyAdminTest.this.junitLayoutFactory.setOneTimeCallback(moduleName, (AsyncCallback<IModuleUI<?, ?>>) asyncCallback);
		MyAdmin.getInstance().startModule(moduleName, location);
	}

	public void openEditor(DictionaryDescriptor<?> dictionaryDescriptor, final AsyncCallback asyncCallback)
	{
		MyAdminTest.this.junitLayoutFactory.setOneTimeCallback(dictionaryDescriptor.getId(), asyncCallback);
		DictionaryEditorModuleFactory.openEditor(dictionaryDescriptor.getId());
	}

	public void openSearch(DictionaryDescriptor<?> dictionaryDescriptor, final AsyncCallback asyncCallback)
	{
		MyAdminTest.this.junitLayoutFactory.setOneTimeCallback(dictionaryDescriptor.getId(), asyncCallback);
		DictionarySearchModuleFactory.openSearch(dictionaryDescriptor.getId());
	}

}
