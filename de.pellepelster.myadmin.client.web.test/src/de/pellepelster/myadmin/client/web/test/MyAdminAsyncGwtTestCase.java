package de.pellepelster.myadmin.client.web.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.UUID;
import de.pellepelster.myadmin.client.base.modules.dictionary.DictionaryDescriptor;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.DictionaryEditorModuleFactory;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionaryEditorModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionaryEditorModuleTestUIAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionarySearchModuleTestUI;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.DictionarySearchModuleTestUIAsyncHelper;
import de.pellepelster.myadmin.client.web.util.BaseErrorAsyncCallback;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class MyAdminAsyncGwtTestCase<VOType extends IBaseVO> extends GWTTestCase
{
	public interface AsyncTestItem
	{
		void run(AsyncCallback<Object> asyncCallback);
	};

	private LinkedList<AsyncTestItem> asyncTestItems = new LinkedList<AsyncTestItem>();

	private Map<String, Object> asyncTestItemResults = new HashMap<String, Object>();

	public MyAdminAsyncGwtTestCase()
	{
	}

	public MyAdminAsyncGwtTestCase<VOType> deleteAllVOs(final Class<VOType> voClass)
	{
		this.asyncTestItems.add(new AsyncTestItem()
		{

			@Override
			public void run(final AsyncCallback<Object> asyncCallback)
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
		final String uuid = UUID.uuid();

		this.asyncTestItems.add(new AsyncTestItem()
		{
			@Override
			public void run(final AsyncCallback<Object> asyncCallback)
			{
				MyAdminTest.getInstance().openSearch(dictionaryDescriptor, new BaseErrorAsyncCallback<DictionarySearchModuleTestUI>()
				{
					@Override
					public void onSuccess(DictionarySearchModuleTestUI result)
					{
						MyAdminAsyncGwtTestCase.this.asyncTestItemResults.put(uuid, result);
						asyncCallback.onSuccess(result);
					}
				});
			}
		});

		return new DictionarySearchModuleTestUIAsyncHelper<VOType>(uuid, this.asyncTestItems, this.asyncTestItemResults);
	}

	public DictionaryEditorModuleTestUIAsyncHelper<VOType> openEditor(final DictionaryDescriptor<?> dictionaryDescriptor)
	{
		final String uuid = UUID.uuid();

		this.asyncTestItems.add(new AsyncTestItem()
		{
			@Override
			public void run(final AsyncCallback<Object> asyncCallback)
			{
				MyAdminTest.getInstance().setLayoutFactoryOneTimeCallback(dictionaryDescriptor, new BaseErrorAsyncCallback<DictionaryEditorModuleTestUI>()
				{

					@Override
					public void onSuccess(DictionaryEditorModuleTestUI result)
					{
						MyAdminAsyncGwtTestCase.this.asyncTestItemResults.put(uuid, result);
						asyncCallback.onSuccess(result);
					}
				});
				DictionaryEditorModuleFactory.openEditor(dictionaryDescriptor.getId());
			}
		});

		return new DictionaryEditorModuleTestUIAsyncHelper<VOType>(uuid, this.asyncTestItems, this.asyncTestItemResults);
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
		}));

		delayTestFinish(2000);
	}
}
