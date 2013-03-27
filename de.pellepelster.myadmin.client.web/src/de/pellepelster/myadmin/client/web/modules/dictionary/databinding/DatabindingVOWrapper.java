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
package de.pellepelster.myadmin.client.web.modules.dictionary.databinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.VOBeanUtil;
import de.pellepelster.myadmin.client.base.databinding.IObservableValue;
import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.databinding.ValueChangeEvent;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IDatabindingAwareModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.editor.IDirtyListener;

public class DatabindingVOWrapper
{

	private boolean dirty = false;

	private IBaseVO vo;

	private final Map<IDatabindingAwareModel, IObservableValue> observableValues = new HashMap<IDatabindingAwareModel, IObservableValue>();

	private final List<IContentChangedListener> contentChangedListeners = new ArrayList<IContentChangedListener>();

	public DatabindingVOWrapper()
	{
	}

	public DatabindingVOWrapper(IBaseVO vo)
	{
		super();
		this.vo = vo;
	}

	public void addContentChangedListener(IContentChangedListener contentChangedListener)
	{
		contentChangedListeners.add(contentChangedListener);
	}

	public Object get(String name)
	{
		return vo.get(name);
	}

	public long getId()
	{
		return vo.getId();
	}

	public void addDirtyListener(IDirtyListener dirtyListener)
	{
		dirtyListeners.add(dirtyListener);
	}
	
	public IObservableValue getObservableValue(final IDatabindingAwareModel databindingAwareModel)
	{

		IObservableValue observableValue;

		if (observableValues.containsKey(databindingAwareModel))
		{
			observableValue = observableValues.get(databindingAwareModel);
		}
		else
		{
			observableValue = new IObservableValue()
			{

				private final List<IValueChangeListener> valueChangeListeners = new ArrayList<IValueChangeListener>();

				/** {@inheritDoc} */
				@Override
				public void addValueChangeListener(IValueChangeListener valueChangeListener)
				{
					valueChangeListeners.add(valueChangeListener);
				}

				/** {@inheritDoc} */
				@Override
				public Object getContent()
				{
					return vo.get(databindingAwareModel.getAttributePath());
				}

				/** {@inheritDoc} */
				@Override
				public Class<?> getContentType()
				{
					return Object.class;
				}

				/** {@inheritDoc} */
				@Override
				public IDatabindingAwareModel getModel()
				{
					return databindingAwareModel;
				}

				/** {@inheritDoc} */
				@Override
				public List<IValueChangeListener> getValueChangeListeners()
				{
					return valueChangeListeners;
				}

				/** {@inheritDoc} */
				@Override
				public void removeValueChangeListener(IValueChangeListener valueChangeListener)
				{
					valueChangeListeners.remove(valueChangeListener);
				}

				/** {@inheritDoc} */
				@Override
				public void setContent(Object value)
				{
					set(databindingAwareModel.getAttributePath(), value, false);
				}
			};

			observableValues.put(databindingAwareModel, observableValue);
		}

		return observableValue;
	}

	public long getOid()
	{
		return vo.getOid();
	}

	public IBaseVO getVo()
	{
		return vo;
	}

	private final List<IDirtyListener> dirtyListeners = new ArrayList<IDirtyListener>();

	public void markClean()
	{
		for (IDirtyListener dirtyListener : dirtyListeners)
		{
			dirtyListener.markClean();
		}
		dirty = false;
	}

	public void markDirty()
	{
		for (IDirtyListener dirtyListener : dirtyListeners)
		{
			dirtyListener.markDirty();
		}
		dirty = true;
	}

	public boolean isDirty()
	{
		return dirty;
	}

	public void set(String attributePath, Object value)
	{
		set(attributePath, value, true);
	}

	private void set(String attributePath, Object value, boolean fireValueChangeEvents)
	{
		VOBeanUtil.set(vo, attributePath, value);

		if (fireValueChangeEvents && observableValues.containsKey(attributePath))
		{
			for (IValueChangeListener valueChangeListener : observableValues.get(attributePath).getValueChangeListeners())
			{
				valueChangeListener.handleValueChange(new ValueChangeEvent(attributePath, valueChangeListener));
			}
		}

		markDirty();
	}

	public void setId(long id)
	{
		vo.setId(id);
	}

	public void setOid(long oid)
	{
		vo.setOid(oid);
	}

	public void setVo(IBaseVO vo)
	{
		this.vo = vo;
		markClean();

		for (IContentChangedListener contentChangedListener : contentChangedListeners)
		{
			contentChangedListener.contentChanged(vo);
		}

		for (Map.Entry<IDatabindingAwareModel, IObservableValue> entry : observableValues.entrySet())
		{
			for (IValueChangeListener valueChangeListener : entry.getValue().getValueChangeListeners())
			{
				String attributePath = entry.getKey().getAttributePath();
				valueChangeListener.handleValueChange(new ValueChangeEvent(attributePath, vo.get(attributePath)));
			}
		}
	}
}
