package de.pellepelster.myadmin.client.web.test;

import java.util.LinkedList;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionaryEditorModuleTestUIAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionarySearchModuleTestUIAsyncHelper;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class MyAdminAsyncGwtTestCase<VOType extends IBaseVO> extends GWTTestCase
{
	public interface AsyncTestItem<T>
	{
		void run(AsyncCallback<Object> asyncCallback, T lastResult);
	};

	private LinkedList<AsyncTestItem> asyncTestItems = new LinkedList<AsyncTestItem>();

	public MyAdminAsyncGwtTestCase()
	{
	}

	public MyAdminAsyncGwtTestCase<VOType> deleteAllVOs(final Class<VOType> voClass)
	{
		this.asyncTestItems.add(new AsyncTestItem<Object>()
		{

			@Override
			public void run(final AsyncCallback<Object> asyncCallback, Object lastResult)
			{
				MyAdmin.getInstance().getRemoteServiceLocator().getBaseEntityService().deleteAll(voClass.getName(), new BaseErrorAsyncCallback()
				{
					@Override
					public void onSuccess(Object result)
					{
						asyncCallback.onSuccess(MyAdminAsyncGwtTestCase.this);
					}
				});
			}
		});

		return this;
	}

	public DictionarySearchModuleTestUIAsyncHelper<VOType> openSearch(final DictionaryDescriptor<?> dictionaryDescriptor)
	{
		this.asyncTestItems.add(new AsyncTestItem<Object>()
		{
			@Override
			public void run(final AsyncCallback<Object> asyncCallback, Object lastResult)
			{
				MyAdminTest.getInstance().openSearch(dictionaryDescriptor, asyncCallback);
			}
		});

		return new DictionarySearchModuleTestUIAsyncHelper<VOType>(this.asyncTestItems);
	}

	public DictionaryEditorModuleTestUIAsyncHelper<VOType> openEditor(final DictionaryDescriptor<?> dictionaryDescriptor)
	{
		this.asyncTestItems.add(new AsyncTestItem<Object>()
		{
			@Override
			public void run(final AsyncCallback<Object> asyncCallback, Object lastResult)
			{
				MyAdminTest.getInstance().setLayoutFactoryOneTimeCallback(dictionaryDescriptor, asyncCallback);
				DictionaryEditorModuleFactory.openEditor(dictionaryDescriptor.getId());
			}
		});

		return new DictionaryEditorModuleTestUIAsyncHelper<VOType>(this.asyncTestItems);
	}

	public void runAsyncTests()
	{
		AsyncTestItem asyncTestItem = this.asyncTestItems.removeFirst();

		asyncTestItem.run(new RecursiveAsyncCallback(this.asyncTestItems, new BaseErrorAsyncCallback<Object>()
		{
			@Override
			public void onSuccess(Object result)
			{
				finishTest();
			}
		}), this);

		delayTestFinish(22000);
	}
}
