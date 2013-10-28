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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import java.util.List;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.safecss.shared.SafeStyles;
import com.google.gwt.safecss.shared.SafeStylesBuilder;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.BaseDictionaryControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.ValidationUtils;

public class EditTextCellWithValidation<T> extends BaseCellControl<T>
{

	interface Template extends SafeHtmlTemplates
	{
		@Template("<input style=\"{1}\" type=\"text\" value=\"{0}\" tabindex=\"-1\"></input>")
		SafeHtml input(String value, SafeStyles styles);

		@Template("<span style=\"{1}\">{0}</span>")
		SafeHtml display(String value, SafeStyles styles);
	}

	private static Template template;

	private final BaseDictionaryControl<IBaseControlModel, Object> baseControl;

	private final SafeHtmlRenderer<String> renderer;

	public EditTextCellWithValidation(BaseDictionaryControl<IBaseControlModel, Object> baseControl, IValueHandler<T> valueFormatter)
	{
		super(valueFormatter, ClickEvent.getType().getName(), KeyUpEvent.getType().getName(), KeyDownEvent.getType().getName(), FocusEvent.getType().getName(),
				BlurEvent.getType().getName());

		if (template == null)
		{
			template = GWT.create(Template.class);
		}

		this.baseControl = baseControl;
		this.renderer = SimpleSafeHtmlRenderer.getInstance();
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, T value, NativeEvent event, ValueUpdater<T> valueUpdater)
	{
		ViewData<T> viewData = getAndInitViewData(context);

		String type = event.getType();
		int keyCode = event.getKeyCode();

		boolean enterPressed = KeyUpEvent.getType().getName().equals(type) && keyCode == KeyCodes.KEY_ENTER;
		boolean startEdit = ClickEvent.getType().getName().equals(type) || enterPressed;
		boolean eventTargetIsDiv = false;
		boolean eventTargetIsInput = false;

		if (Element.is(event.getEventTarget()))
		{
			Element target = Element.as(event.getEventTarget());
			GWT.log("target: " + target.getTagName() + "(" + target.getId() + "), eventType: " + type);

			eventTargetIsDiv = "div".equals(target.getTagName().toLowerCase());
			eventTargetIsInput = "input".equals(target.getTagName().toLowerCase());

		}

		if (BlurEvent.getType().getName().equals(type))
		{
			if (eventTargetIsInput)
			{
				commit(context, parent, viewData, valueUpdater);
			}

		}
		else if (FocusEvent.getType().getName().equals(type))
		{
			getInputElement(parent).focus();
		}
		else
		{
			if (viewData.isEditing())
			{
				editEvent(context, parent, value, viewData, event, valueUpdater);

			}
			else
			{
				if (startEdit)
				{
					startEdit(context, parent, value);
				}
			}
		}

	}

	@Override
	public void render(Context context, T value, SafeHtmlBuilder sb)
	{
		ViewData<T> viewData = getAndInitViewData(context);

		SafeStylesBuilder styles = new SafeStylesBuilder();
		List<IValidationMessage> validationMessages = viewData.getValidationMessages();

		if (ValidationUtils.hasError(validationMessages))
		{
			styles.appendTrustedString(GwtStyles.CELL_ERROR_STYLE);
		}

		if (viewData.isEditing())
		{
			sb.append(template.input(getValueHandler().format(viewData.getValue()), styles.toSafeStyles()));
		}
		else
		{
			styles.appendTrustedString(GwtStyles.CELL_ERROR_DISPLAY_PADDING);
			sb.append(template.display(renderer.render(getValueHandler().format(viewData.getValue())).asString(), styles.toSafeStyles()));
		}
	}

	@Override
	public boolean resetFocus(Context context, Element parent, T value)
	{
		if (isEditing(context, parent, value))
		{
			getInputElement(parent).focus();
			return true;
		}
		return false;
	}

	protected void startEdit(Context context, Element parent, T value)
	{
		ViewData<T> viewData = getAndInitViewData(context);
		viewData.setEditing(true);

		setValue(context, parent, value);

		focus(parent);
	}

	private void focus(Element parent)
	{
		InputElement input = getInputElement(parent);

		input.focus();
		input.select();
	}

	private void cancel(Context context, Element parent, T value)
	{
		clearInput(getInputElement(parent));
		setValue(context, parent, value);
	}

	private native void clearInput(Element input) /*-{
													if (input.selectionEnd)
													input.selectionEnd = input.selectionStart;
													else if ($doc.selection)
													$doc.selection.clear();
													}-*/;

	private void commit(Context context, Element parent, ViewData<T> viewData, ValueUpdater<T> valueUpdater)
	{
		T value = updateViewData(parent, viewData, false);

		clearInput(getInputElement(parent));

		setValue(context, parent, viewData.getOriginal());

		if (valueUpdater != null)
		{
			valueUpdater.update(value);
		}
	}

	private void editEvent(Context context, Element parent, T value, ViewData<T> viewData, NativeEvent event, ValueUpdater<T> valueUpdater)
	{
		String type = event.getType();

		boolean keyUp = KeyUpEvent.getType().getName().equals(type);
		boolean keyDown = KeyDownEvent.getType().getName().equals(type);

		if (keyUp || keyDown)
		{
			int keyCode = event.getKeyCode();

			if (keyUp && keyCode == KeyCodes.KEY_ENTER)
			{
				commit(context, parent, viewData, valueUpdater);
			}
			else if (keyUp && keyCode == KeyCodes.KEY_ESCAPE)
			{
				T originalValue = viewData.getOriginal();

				if (viewData.isEditingAgain())
				{
					viewData.setValue(originalValue);
					viewData.setEditing(false);
				}
				else
				{
					setViewData(context.getKey(), null);
				}

				cancel(context, parent, value);
			}
			else
			{
				// Update the text in the view data on each key.
				updateViewData(parent, viewData, true);
			}
		}
	}

	private InputElement getInputElement(Element parent)
	{
		return parent.getFirstChild().<InputElement> cast();
	}

	private T updateViewData(Element parent, ViewData<T> viewData, boolean isEditing)
	{
		InputElement input = (InputElement) parent.getFirstChild();

		String value = input.getValue();
		viewData.setValue(getValueHandler().parse(value));
		viewData.setEditing(isEditing);

		return viewData.getValue();
	}

}
