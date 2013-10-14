package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import java.util.Collections;
import java.util.List;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import de.pellepelster.myadmin.client.base.databinding.IValueChangeListener;
import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IUIControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IUIControlFactory;

public class ReadonlyControl extends Label implements IUIControl<Widget>
{

	private Object content;

	private IBaseControlModel baseControlModel;

	private IUIControlFactory controlFactory;

	public ReadonlyControl(IBaseControlModel baseControlModel, IUIControlFactory controlFactory)
	{
		super();
		this.baseControlModel = baseControlModel;
		this.controlFactory = controlFactory;
	}

	@Override
	public void addValueChangeListener(IValueChangeListener valueChangeListener)
	{
	}

	@Override
	public Class<?> getContentType()
	{
		return Object.class;
	}

	@Override
	public List<IValueChangeListener> getValueChangeListeners()
	{
		return Collections.emptyList();
	}

	@Override
	public void removeValueChangeListener(IValueChangeListener valueChangeListener)
	{
	}

	@Override
	public Object getContent()
	{
		return content;
	}

	@Override
	public IBaseControlModel getModel()
	{
		return baseControlModel;
	}

	@Override
	public Widget getWidget()
	{
		return this;
	}

	@Override
	public void setContent(Object content)
	{
		this.content = content;

		setText(controlFactory.format(baseControlModel, content));
	}

	@Override
	public void setValidationMessages(List<IValidationMessage> validationMessages)
	{
	}

}
