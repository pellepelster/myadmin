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
import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.shared.EventBus;

import de.pellepelster.myadmin.client.base.databinding.IObservableValue;
import de.pellepelster.myadmin.client.base.databinding.IUIObservableValue;
import de.pellepelster.myadmin.client.base.databinding.ValueChangeEvent;
import de.pellepelster.myadmin.client.base.db.vos.IValidationMessage;
import de.pellepelster.myadmin.client.base.db.vos.VALIDATION_STATUS;
import de.pellepelster.myadmin.client.web.modules.dictionary.events.DatabindingContextEvent;

/**
 * Context that holds all value bindings for a UI
 * 
 * @author pelle
 * 
 */
public class DataBindingContext
{
	private final DatabindingVOWrapper databindingVOWrapper;

	private final EventBus eventBus;

	private final List<Binding> bindings = new ArrayList<Binding>();

	public DataBindingContext(DatabindingVOWrapper databindingVOWrapper, EventBus eventBus)
	{
		this.databindingVOWrapper = databindingVOWrapper;
		this.eventBus = eventBus;
	}

	public void addValidationMessages(List<IValidationMessage> validationMessagesToAdd)
	{

		for (Binding binding : bindings)
		{
			List<IValidationMessage> validationMessagesToAddForBinding = new ArrayList<IValidationMessage>();

			for (IValidationMessage validationMessageToAdd : validationMessagesToAdd)
			{
				if (validationMessageToAdd.getContext().startsWith(binding.getUiObservableValue().getModel().getAttributePath()))
				{
					validationMessagesToAddForBinding.add(validationMessageToAdd);
				}
			}

			binding.getValidationMessages().addAll(validationMessagesToAddForBinding);
		}
	}

	public Binding createBinding(final IObservableValue modelObservableValue, final IUIObservableValue uiObservableValue)
	{
		final Binding binding = new Binding(modelObservableValue, uiObservableValue, databindingVOWrapper);

		binding.addValidationListener(new IValidationListener()
		{
			/** {@inheritDoc} */
			@Override
			public void afterValidate(ValueChangeEvent valueChangeEvent)
			{
				removeValidationMessagesForAttributePath(valueChangeEvent.getAttributePath());

				eventBus.fireEvent(new DatabindingContextEvent());
			}
		});

		bindings.add(binding);

		return binding;
	}

	public void validateAndUpdate()
	{
		for (Binding binding : bindings)
		{
			binding.validateAndUpdate();
		}
	}

	public boolean hasErrors()
	{
		for (Binding binding : bindings)
		{
			if (binding.getValidationStatus() != VALIDATION_STATUS.OK)
			{
				return true;
			}
		}

		return false;
	}

	public void removeValidationMessagesForAttributePath(String attributePath)
	{
		for (Binding binding : bindings)
		{
			for (Iterator<IValidationMessage> iterator = binding.getValidationMessages().iterator(); iterator.hasNext();)
			{
				IValidationMessage validationMessage = iterator.next();

				if (validationMessage.getContext() != null && validationMessage.getContext().startsWith(attributePath))
				{
					iterator.remove();
				}
			}
		}
	}

}
