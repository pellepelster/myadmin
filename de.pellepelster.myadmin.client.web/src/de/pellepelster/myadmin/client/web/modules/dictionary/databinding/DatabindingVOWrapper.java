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
		this.contentChangedListeners.add(contentChangedListener);
	}

	public Object get(String name)
	{
		return this.vo.get(name);
	}

	public long getId()
	{
		return this.vo.getId();
	}

	public void addDirtyListener(IDirtyListener dirtyListener)
	{
		this.dirtyListeners.add(dirtyListener);
	}

	public IObservableValue getObservableValue(final IDatabindingAwareModel databindingAwareModel)
	{

		IObservableValue observableValue;

		if (this.observableValues.containsKey(databindingAwareModel))
		{
			observableValue = this.observableValues.get(databindingAwareModel);
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
					this.valueChangeListeners.add(valueChangeListener);
				}

				/** {@inheritDoc} */
				@Override
				public Object getContent()
				{
					return DatabindingVOWrapper.this.vo.get(databindingAwareModel.getAttributePath());
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
					return this.valueChangeListeners;
				}

				/** {@inheritDoc} */
				@Override
				public void removeValueChangeListener(IValueChangeListener valueChangeListener)
				{
					this.valueChangeListeners.remove(valueChangeListener);
				}

				/** {@inheritDoc} */
				@Override
				public void setContent(Object value)
				{
					set(databindingAwareModel.getAttributePath(), value, false);
				}
			};

			this.observableValues.put(databindingAwareModel, observableValue);
		}

		return observableValue;
	}

	public IBaseVO getVO()
	{
		return this.vo;
	}

	private final List<IDirtyListener> dirtyListeners = new ArrayList<IDirtyListener>();

	public void markClean()
	{
		for (IDirtyListener dirtyListener : this.dirtyListeners)
		{
			dirtyListener.markClean();
		}
		this.dirty = false;
	}

	public void markDirty()
	{
		for (IDirtyListener dirtyListener : this.dirtyListeners)
		{
			dirtyListener.markDirty();
		}
		this.dirty = true;
	}

	public boolean isDirty()
	{
		return this.dirty;
	}

	public void set(String attributePath, Object value)
	{
		set(attributePath, value, true);
	}

	private void set(String attributePath, Object value, boolean fireValueChangeEvents)
	{
		VOBeanUtil.set(this.vo, attributePath, value);

		if (fireValueChangeEvents && this.observableValues.containsKey(attributePath))
		{
			for (IValueChangeListener valueChangeListener : this.observableValues.get(attributePath).getValueChangeListeners())
			{
				valueChangeListener.handleValueChange(new ValueChangeEvent(attributePath, valueChangeListener));
			}
		}

		markDirty();
	}

	public void setVo(IBaseVO vo)
	{
		this.vo = vo;
		markClean();

		for (IContentChangedListener contentChangedListener : this.contentChangedListeners)
		{
			contentChangedListener.contentChanged(vo);
		}

		for (Map.Entry<IDatabindingAwareModel, IObservableValue> entry : this.observableValues.entrySet())
		{
			for (IValueChangeListener valueChangeListener : entry.getValue().getValueChangeListeners())
			{
				String attributePath = entry.getKey().getAttributePath();
				valueChangeListener.handleValueChange(new ValueChangeEvent(attributePath, vo.get(attributePath)));
			}
		}
	}
}
