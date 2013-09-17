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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.container;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.Image;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public class ImageActionCell extends AbstractCell<Object>
{
	private Image image;

	private final ImageResource imageResource;

	private SimpleCallback<IBaseVO> actionCallback;

	@Override
	public Set<String> getConsumedEvents()
	{
		Set<String> consumedEvents = new HashSet<String>();

		consumedEvents.add(MouseOverEvent.getType().getName());
		consumedEvents.add(MouseOutEvent.getType().getName());
		consumedEvents.add(ClickEvent.getType().getName());

		return consumedEvents;
	}

	public ImageActionCell(ImageResource imageResource, SimpleCallback<IBaseVO> actionCallback)
	{
		this.imageResource = imageResource;
		this.actionCallback = actionCallback;
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, Object value, NativeEvent event, ValueUpdater<Object> valueUpdater)
	{
		if (event.getType().equals(MouseOverEvent.getType().getName()))
		{
			parent.getFirstChildElement().getStyle().setOpacity(GwtStyles.ENABLED_OPACITY);
		}

		if (event.getType().equals(MouseOutEvent.getType().getName()))
		{
			parent.getFirstChildElement().getStyle().setOpacity(GwtStyles.DISABLED_OPACITY);
		}

		if (event.getType().equals(ClickEvent.getType().getName()))
		{
			actionCallback.onCallback((IBaseVO) context.getKey());
		}
	}

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, Object value, SafeHtmlBuilder sb)
	{
		if (image == null)
		{
			image = new Image(imageResource);
			image.getElement().setTabIndex(-1);
			image.getElement().getStyle().setOpacity(GwtStyles.DISABLED_OPACITY);
		}

		sb.append(SafeHtmlUtils.fromTrustedString(image.getElement().getString()));
	}
}
