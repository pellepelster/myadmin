package de.pellepelster.myadmin.client.base.modules.dictionary.controls;

import com.google.gwt.resources.client.ImageResource;

public abstract class BaseButton implements IButton
{
	private final ImageResource image;

	private final String title;

	private final String debugId;

	public BaseButton(ImageResource image, String title, String debugId)
	{
		super();
		this.image = image;
		this.title = title;
		this.debugId = debugId;
	}

	@Override
	public ImageResource getImage()
	{
		return this.image;
	}

	@Override
	public String getTitle()
	{
		return this.title;
	}

	@Override
	public String getDebugId()
	{
		return this.debugId;
	}

}
