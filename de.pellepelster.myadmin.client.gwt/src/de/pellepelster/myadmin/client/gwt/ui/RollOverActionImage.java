package de.pellepelster.myadmin.client.gwt.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;

import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.web.util.SimpleCallback;

public class RollOverActionImage extends Image
{
	public RollOverActionImage(ImageResource imageResource, final SimpleCallback<Void> actionCallback)
	{
		super(imageResource);

		addClickHandler(new ClickHandler()
		{

			@Override
			public void onClick(ClickEvent event)
			{
				actionCallback.onCallback(null);
			}
		});

		addMouseOverHandler(new MouseOverHandler()
		{

			@Override
			public void onMouseOver(MouseOverEvent event)
			{
				RollOverActionImage.this.getElement().getStyle().setOpacity(GwtStyles.ENABLED_OPACITY);
			}
		});

		addMouseOutHandler(new MouseOutHandler()
		{

			@Override
			public void onMouseOut(MouseOutEvent event)
			{
				RollOverActionImage.this.getElement().getStyle().setOpacity(GwtStyles.DISABLED_OPACITY);
			}
		});

	}
}
