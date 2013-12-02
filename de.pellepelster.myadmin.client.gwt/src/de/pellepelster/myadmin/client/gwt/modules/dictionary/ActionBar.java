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
package de.pellepelster.myadmin.client.gwt.modules.dictionary;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.gwt.widgets.ImageButton;
import de.pellepelster.myadmin.client.gwt.widgets.Spacer;

/**
 * Gmail style button bar
 * 
 * @author pelle
 * 
 */
public class ActionBar extends HorizontalPanel
{

	private final HorizontalPanel actionPanelInternal = new HorizontalPanel();

	public ActionBar()
	{
		setWidth("100%");
		setHorizontalAlignment(HasAlignment.ALIGN_LEFT);
		addStyleName(GwtStyles.SEPARATOR_BORDER_BOTTOM);
		addStyleName(GwtStyles.SEPARATOR_BORDER_TOP);
		addStyleName(GwtStyles.DEBUG_BORDER);
		addStyleName(GwtStyles.VERTICAL_SPACING);
		add(actionPanelInternal);
	}

	private Button addButton(ImageResource imageResource, String title, ClickHandler clickHandler, String styleName, String debugId)
	{
		ImageButton button = new ImageButton();

		if (styleName != null)
		{
			button.addStyleName(styleName);
		}

		button.setResource(imageResource);
		button.setTitle(title);
		actionPanelInternal.add(button);

		if (clickHandler != null)
		{
			button.addClickHandler(clickHandler);
		}
		button.ensureDebugId(debugId);

		return button;
	}

	public Button addButtonBar(ImageResource imageResource, String title, ClickHandler clickHandler, String debugId)
	{
		return addButton(imageResource, title, clickHandler, GwtStyles.BUTTON_BAR_MIDDLE, debugId);
	}

	public Button addButtonBarEnd(ImageResource imageResource, String title, ClickHandler clickHandler, String debugId)
	{
		return addButton(imageResource, title, clickHandler, GwtStyles.BUTTON_BAR_RIGHT, debugId);
	}

	public Button addButtonBarStart(ImageResource imageResource, String title, ClickHandler clickHandler, String debugId)
	{
		return addButton(imageResource, title, clickHandler, GwtStyles.BUTTON_BAR_LEFT, debugId);
	}

	public Button addSingleButton(ImageResource imageResource, String title, ClickHandler clickHandler, String debugId)
	{
		return addButton(imageResource, title, clickHandler, null, debugId);
	}

	public Button addSingleButton(ImageResource imageResource, String title, String debugId)
	{
		return addButton(imageResource, title, null, null, debugId);
	}

	public void addSpacer(double width)
	{
		Spacer spacer = new Spacer(width);
		actionPanelInternal.add(spacer);
	}

}
