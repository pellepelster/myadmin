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
import java.util.Collection;
import java.util.List;

import de.pellepelster.myadmin.client.base.databinding.IObservableValue;
import de.pellepelster.myadmin.client.base.databinding.IUIObservableValue;
import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.databinding.TypeHelper;
import de.pellepelster.myadmin.client.base.databinding.ValueChangeEvent;
import de.pellepelster.myadmin.client.base.db.vos.IValidationMessage;
import de.pellepelster.myadmin.client.base.db.vos.VALIDATION_STATUS;

/**
 * Represents a binding between two {@link IObservableValue}s. The bindings
 * updates the two {@link IObservableValue} and also does validation
 * 
 * @author pelle
 * 
 */
public class Binding
{
	private final DatabindingVOWrapper databindingVOWrapper;

	private final List<IValidationMessage> validationMessages;

	private final List<IValidationListener> validationListeners = new ArrayList<IValidationListener>();

	private final List<IValidator> validators = new ArrayList<IValidator>();

	private final IObservableValue modelObservableValue;

	private final IUIObservableValue uiObservableValue;

	public IObservableValue getModelObservableValue()
	{
		return modelObservableValue;
	}

	public IUIObservableValue getUiObservableValue()
	{
		return uiObservableValue;
	}

	public Binding(IObservableValue modelObservableValue, IUIObservableValue uiObservableValue, DatabindingVOWrapper databindingVOWrapper)
	{

		this.modelObservableValue = modelObservableValue;
		this.uiObservableValue = uiObservableValue;
		this.databindingVOWrapper = databindingVOWrapper;

		this.validationMessages = new ArrayList<IValidationMessage>()
		{
			private static final long serialVersionUID = -1959050093612904687L;

			@Override
			public boolean add(IValidationMessage e)
			{
				boolean result = super.add(e);
				updateUI();
				return result;
			}

			private void updateUI()
			{
				Binding.this.uiObservableValue.setValidationMessages(validationMessages);
			}

			@Override
			public void add(int index, IValidationMessage element)
			{
				super.add(index, element);
				updateUI();
			}

			@Override
			public IValidationMessage remove(int index)
			{
				IValidationMessage result = super.remove(index);
				updateUI();
				return result;
			}

			@Override
			public boolean remove(Object o)
			{
				boolean result = super.remove(o);
				updateUI();
				return result;
			}

			@Override
			public void clear()
			{
				super.clear();
				updateUI();
			}

			@Override
			public boolean addAll(Collection<? extends IValidationMessage> c)
			{
				boolean result = super.addAll(c);
				updateUI();
				return result;
			}

			@Override
			public boolean addAll(int index, Collection<? extends IValidationMessage> c)
			{
				boolean result = super.addAll(index, c);
				updateUI();
				return result;
			}

			@Override
			public boolean removeAll(Collection<?> c)
			{
				boolean result = super.removeAll(c);
				updateUI();
				return result;
			}
		};

		modelObservableValue.addValueChangeListener(new IValueChangeListener()
		{

			/** {@inheritDoc} */
			@Override
			public void handleValueChange(ValueChangeEvent valueChangeEvent)
			{
				Binding.this.uiObservableValue.setContent(valueChangeEvent.getAttributeValue());
			}
		});

		uiObservableValue.addValueChangeListener(new IValueChangeListener()
		{

			/** {@inheritDoc} */
			@Override
			public void handleValueChange(ValueChangeEvent valueChangeEvent)
			{
				validateAndUpdate(valueChangeEvent);
			}
		});

		uiObservableValue.setContent(modelObservableValue.getContent());

	}

	public void addValidationListener(IValidationListener validationListener)
	{
		validationListeners.add(validationListener);
	}

	private void fireValidationListeners(ValueChangeEvent valueChangeEvent)
	{
		for (IValidationListener validationListener : validationListeners)
		{
			validationListener.afterValidate(valueChangeEvent);
		}
	}

	public List<IValidationMessage> getValidationMessages()
	{
		return validationMessages;
	}

	public VALIDATION_STATUS getValidationStatus()
	{
		return ValidationUtils.getValidationStatus(validationMessages);
	}

	public void setValidators(List<IValidator> validators)
	{
		this.validators.clear();
		this.validators.addAll(validators);
	}

	private void validate(Object value)
	{
		getValidationMessages().clear();
		getValidationMessages().addAll(ValidationUtils.validate(validators, value, uiObservableValue.getModel()));
	}

	public void validateAndUpdate()
	{
		validateAndUpdate(modelObservableValue.getModel().getAttributePath(), uiObservableValue.getContent());
	}

	private void validateAndUpdate(ValueChangeEvent valueChangeEvent)
	{
		validateAndUpdate(valueChangeEvent.getAttributePath(), valueChangeEvent.getAttributeValue());

		fireValidationListeners(valueChangeEvent);
	}

	private void validateAndUpdate(String attributePath, Object attributeValue)
	{
		validate(attributeValue);

		if (getValidationStatus() == VALIDATION_STATUS.OK)
		{
			Object value = TypeHelper.convert(uiObservableValue.getContentType(), attributeValue);

			if (Binding.this.modelObservableValue.getModel().getAttributePath().equals(attributePath))
			{
				Binding.this.modelObservableValue.setContent(value);
			}
			else
			{
				databindingVOWrapper.set(attributePath, value);
			}
		}
	}

}
