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
package de.pellepelster.myadmin.client.gwt.test;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.client.SafeHtmlTemplates.Template;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.google.gwt.text.shared.SimpleSafeHtmlRenderer;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;

public class SuggestCellControl<T> extends AbstractEditableCell<T, SuggestCellControl.ViewData<T>>
{
	public interface IValueFormatter<T>
	{
		String format(T value);
	}

	private class SuggestCellSuggestBox extends SuggestBox
	{
		private final int absoluteLeft;
		private final int absoluteTop;
		private final int offsetHeight;
		private final int offsetWidth;

		public SuggestCellSuggestBox(SuggestOracle oracle, TextBoxBase box)
		{
			super(oracle, box);
			this.absoluteLeft = box.getAbsoluteLeft();
			this.absoluteTop = box.getAbsoluteTop();
			this.offsetWidth = box.getOffsetWidth();
			this.offsetHeight = box.getOffsetHeight();
		}

		@Override
		public int getAbsoluteLeft()
		{
			if (super.getAbsoluteLeft() == 0)
			{
				return absoluteLeft;
			}
			else
			{
				return super.getAbsoluteLeft();
			}
		}

		@Override
		public int getAbsoluteTop()
		{
			if (super.getAbsoluteTop() == 0)
			{
				return absoluteTop;
			}
			else
			{
				return super.getAbsoluteTop();
			}
		}

		@Override
		public int getOffsetHeight()
		{
			if (super.getOffsetHeight() == 0)
			{
				return offsetHeight;
			}
			else
			{
				return super.getOffsetHeight();
			}
		}

		@Override
		public int getOffsetWidth()
		{
			if (super.getOffsetWidth() == 0)
			{
				return offsetWidth;
			}
			else
			{
				return super.getOffsetWidth();
			}
		}

		@Override
		public void onAttach()
		{
			super.onAttach();
		}
	}

	public interface SuggestCellSuggestion<T> extends Suggestion
	{
		public T getValue();
	}

	public class SuggestTextBox extends TextBox
	{
		public SuggestTextBox(Element element)
		{
			super(element);
		}
	}

	interface Template extends SafeHtmlTemplates
	{
		@Template("<input type=\"text\" value=\"{0}\" tabindex=\"-1\"></input>")
		SafeHtml input(String value);
	}

	static class ViewData<T>
	{
		private boolean isEditing;
		private boolean isEditingAgain;
		private T original;
		private T value;

		public ViewData(T value)
		{
			this.value = value;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ViewData other = (ViewData) obj;
			if (isEditing != other.isEditing)
				return false;
			if (isEditingAgain != other.isEditingAgain)
				return false;
			if (original == null)
			{
				if (other.original != null)
					return false;
			}
			else if (!original.equals(other.original))
				return false;
			if (value == null)
			{
				if (other.value != null)
					return false;
			}
			else if (!value.equals(other.value))
				return false;
			return true;
		}

		private boolean equalsOrBothNull(Object o1, Object o2)
		{
			return (o1 == null) ? o2 == null : o1.equals(o2);
		}

		public T getOriginal()
		{
			return original;
		}

		public T getValue()
		{
			return value;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + (isEditing ? 1231 : 1237);
			result = prime * result + (isEditingAgain ? 1231 : 1237);
			result = prime * result + ((original == null) ? 0 : original.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		public boolean isEditing()
		{
			return isEditing;
		}

		public boolean isEditingAgain()
		{
			return isEditingAgain;
		} // public boolean isTest() // { // return test;
			// }

		public void setEditing(boolean isEditing)
		{
			boolean wasEditing = this.isEditing;
			this.isEditing = isEditing;
			// This is a subsequent edit, so start from where we left off.
			if (!wasEditing && isEditing)
			{
				isEditingAgain = true;
				original = value;
			}
		}

		// public void setTest(boolean test) // { // this.test = test; // }
		public void setValue(T value)
		{
			this.value = value;
		}
	}

	private static Template template;
	private final SafeHtmlRenderer<String> renderer;
	private SuggestBox suggestBox;
	private final SuggestOracle suggestOracle;
	private final IValueFormatter<T> valueFormatter;

	public SuggestCellControl(SafeHtmlRenderer<String> renderer, SuggestOracle suggestOracle, IValueFormatter<T> valueFormatter)
	{
		super(ClickEvent.getType().getName(), KeyUpEvent.getType().getName(), KeyDownEvent.getType().getName(), BlurEvent.getType().getName());

		this.suggestOracle = suggestOracle;
		this.renderer = renderer;
		this.valueFormatter = valueFormatter;

		if (template == null)
		{
			template = GWT.create(Template.class);
		}

		if (renderer == null)
		{
			throw new IllegalArgumentException("renderer == null");
		}
	}

	public SuggestCellControl(SuggestOracle suggestOracle, IValueFormatter<T> valueFormatter)
	{
		this(SimpleSafeHtmlRenderer.getInstance(), suggestOracle, valueFormatter);
	}

	private void cancel(Context context, Element parent, T value)
	{
		clearInput(getInputElement(parent));
		setValue(context, parent, value);
	}

	private native void clearInput(Element input) /*-{ if (input.selectionEnd) input.selectionEnd = input.selectionStart; 
													else if ($doc.selection) $doc.selection.clear(); 
													}-*/;

	private void clearSuggestBox()
	{
		if (suggestBox == null)
		{
			throw new RuntimeException("no suggestbox instance");
		}
		suggestBox.hideSuggestionList();
		suggestBox.removeFromParent();
		suggestBox = null;
	}

	private void commit(T value, Context context, Element parent, ValueUpdater<T> valueUpdater)
	{
		ViewData<T> viewData = getAndInitViewData(context);
		viewData.setValue(value);
		viewData.setEditing(false);

		clearSuggestBox();

		setValue(context, parent, viewData.getValue());

		valueUpdater.update(value);

	}

	protected void createSuggestBox(final Context context, final Element parent, T value, final ValueUpdater<T> valueUpdater)
	{
		if (suggestBox != null)
		{
			throw new RuntimeException("SuggestCell already active");
		}

		InputElement input = getInputElement(parent);
		TextBox textBox = new SuggestTextBox(input);
		suggestBox = new SuggestCellSuggestBox(suggestOracle, textBox);
		suggestBox.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>()
		{
			@Override
			public void onSelection(SelectionEvent<Suggestion> event)
			{
				if (event.getSelectedItem() instanceof SuggestCellSuggestion)
				{
					SuggestCellSuggestion<T> suggestCellSuggestions = (SuggestCellSuggestion<T>) event.getSelectedItem();
					commit(suggestCellSuggestions.getValue(), context, parent, valueUpdater);
				}
			}
		});
		parent.replaceChild(suggestBox.getElement(), input);
	}

	private void editEvent(Context context, Element parent, T value, ViewData<T> viewData, NativeEvent event, ValueUpdater<T> valueUpdater)
	{
		String type = event.getType();
		boolean keyUp = "keyup".equals(type);
		boolean keyDown = "keydown".equals(type);
		int keyCode = event.getKeyCode();

		if (keyUp || keyDown)
		{
			if (keyUp && keyCode == KeyCodes.KEY_ESCAPE)
			{
				T originalValue = viewData.getOriginal();
				if (viewData.isEditingAgain())
				{
					viewData.setValue(originalValue);
					viewData.setEditing(false);
				}
				cancel(context, parent, value);
			}
			else
			{
				fireEventToSuggesBox(event);
			}
		}
		else if (BlurEvent.getType().getName().equals(type))
		{
			EventTarget eventTarget = event.getEventTarget();
			if (Element.is(eventTarget))
			{
				Element target = Element.as(eventTarget);
				if ("input".equals(target.getTagName().toLowerCase()))
				{
					// commit(viewData.getValue(), context, parent,
					// valueUpdater);
				}
			}
		}

	}

	private boolean enterPressed(NativeEvent event)
	{
		int keyCode = event.getKeyCode();
		return KeyUpEvent.getType().equals(event.getType()) && keyCode == KeyCodes.KEY_ENTER;
	}

	private void fireEventToSuggesBox(NativeEvent event)
	{
		if (suggestBox != null)
		{
			DomEvent.fireNativeEvent(event, suggestBox.getTextBox());
		}
	}

	private ViewData<T> getAndInitViewData(Context context)
	{
		return getAndInitViewData(context, null);
	}

	private ViewData<T> getAndInitViewData(Context context, T value)
	{
		if (getViewData(context.getKey()) == null)
		{
			ViewData<T> viewData = new ViewData<T>(value);
			setViewData(context.getKey(), viewData);
		}
		return getViewData(context.getKey());
	}

	private InputElement getInputElement(Element parent)
	{
		return parent.getFirstChild().<InputElement> cast();
	}

	private boolean hasSuggestBox()
	{
		return suggestBox != null;
	}

	@Override
	public boolean isEditing(Context context, Element parent, T value)
	{
		ViewData<T> viewData = getAndInitViewData(context);
		return viewData == null ? false : viewData.isEditing();
	}

	@Override
	public void onBrowserEvent(final Context context, final Element parent, final T value, NativeEvent event, final ValueUpdater<T> valueUpdater)
	{
		ViewData<T> viewData = getAndInitViewData(context, value);
		if (viewData.isEditing())
		{
			if (!hasSuggestBox())
			{
				createSuggestBox(context, parent, value, valueUpdater);

			}
			editEvent(context, parent, value, viewData, event, valueUpdater);
		}
		else
		{
			if (ClickEvent.getType().getName().equals(event.getType()) || enterPressed(event))
			{
				viewData.setEditing(true);
				setValue(context, parent, value);
				createSuggestBox(context, parent, value, valueUpdater);

				// Scheduler.get().scheduleDeferred(new ScheduledCommand()
				// {
				// @Override
				// public void execute()
				// {
				// getInputElement(parent).focus();
				// getInputElement(parent).select();
				// }
				// });
			}
		}
	}

	@Override
	public void render(Context context, T value, SafeHtmlBuilder sb)
	{
		ViewData<T> viewData = getAndInitViewData(context);
		if (viewData.isEditing())
		{
			if (hasSuggestBox())
			{
				clearSuggestBox();
				sb.append(template.input(valueFormatter.format(value)));
			}
			else
			{
				sb.append(template.input(valueFormatter.format(value)));
			}
		}
		else
		{
			sb.append(renderer.render(valueFormatter.format(value)));
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

	private native void selectAll(Element input) /*-{ input.select(); 
													}-*/;
}