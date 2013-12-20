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
package de.pellepelster.myadmin.client.web.test.modules.dictionary;

import java.util.LinkedList;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.UUID;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseBigDecimalControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseBooleanControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseDateControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseEnumerationControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseIntegerControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseReferenceControlModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.BaseTextControlModel;
import de.pellepelster.myadmin.client.web.test.MyAdminAsyncGwtTestCase.AsyncTestItem;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BigDecimalControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.BooleanControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.DateControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.EnumerationControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.IntegerControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.ReferenceControlTestAsyncHelper;
import de.pellepelster.myadmin.client.web.test.modules.dictionary.controls.TextControlTestAsyncHelper;

public class BaseDictionaryModuleTestUIAsyncHelper<T extends BaseDictionaryModuleTestUI, VOType extends IBaseVO> extends BaseAsyncHelper<T>
{
	public BaseDictionaryModuleTestUIAsyncHelper(String asynTestItemResultId, LinkedList<AsyncTestItem> asyncTestItems, Map<String, Object> asyncTestItemResults)
	{
		super(asynTestItemResultId, asyncTestItems, asyncTestItemResults);
	}

	public TextControlTestAsyncHelper getTextControlTest(final BaseTextControlModel controlModel)
	{
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getTextControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});

		return new TextControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public BigDecimalControlTestAsyncHelper getBigDecimalControlTest(final BaseBigDecimalControlModel controlModel)
	{
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getBigDecimalControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});

		return new BigDecimalControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public BooleanControlTestAsyncHelper getBooleanControlTest(final BaseBooleanControlModel controlModel)
	{
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getBooleanControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});

		return new BooleanControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public DateControlTestAsyncHelper getDateControlTest(final BaseDateControlModel controlModel)
	{
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getDateControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});

		return new DateControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public IntegerControlTestAsyncHelper getIntegerControlTest(final BaseIntegerControlModel controlModel)
	{
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getIntegerControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});

		return new IntegerControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public EnumerationControlTestAsyncHelper getEnumerationControlTest(final BaseEnumerationControlModel controlModel)
	{
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults()
						.put(uuid, getAsyncTestItemResult().getEnumerationControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});

		return new EnumerationControlTestAsyncHelper(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}

	public <ReferenceVOType extends IBaseVO> ReferenceControlTestAsyncHelper getReferenceControlTest(
			final BaseReferenceControlModel<ReferenceVOType> controlModel)
	{
		final String uuid = UUID.uuid();

		this.addAsyncTestItem(new AsyncTestItem()
		{
			@Override
			public void run(AsyncCallback<Object> asyncCallback)
			{
				BaseDictionaryModuleTestUIAsyncHelper.this.getAsyncTestItemResults().put(uuid, getAsyncTestItemResult().getReferenceControlTest(controlModel));
				asyncCallback.onSuccess(getAsyncTestItemResult());
			}
		});

		return new ReferenceControlTestAsyncHelper<ReferenceVOType>(uuid, this.getAsyncTestItems(), this.getAsyncTestItemResults());
	}
}
