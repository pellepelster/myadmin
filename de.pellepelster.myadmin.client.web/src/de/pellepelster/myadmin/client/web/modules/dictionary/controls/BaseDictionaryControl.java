package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Strings;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.IValidationMessages;
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.modules.dictionary.ValidationMessages;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.IValidator;
import de.pellepelster.myadmin.client.web.modules.dictionary.databinding.validator.MandatoryValidator;

public abstract class BaseDictionaryControl<ModelType extends IBaseControlModel, ValueType> extends BaseDictionaryElement<ModelType> implements
		IBaseControl<ValueType>
{

	protected class ParseResult
	{

		private IValidationMessage validationMessage;

		private ValueType value;

		public ParseResult(ValueType value)
		{
			super();
			this.value = value;
		}

		public ParseResult(IValidationMessage validationMessage)
		{
			super();
			this.validationMessage = validationMessage;
		}

		public IValidationMessage getValidationMessage()
		{
			return this.validationMessage;
		}

		public ValueType getValue()
		{
			return this.value;
		}

	}

	private List<IValidator> validators = new ArrayList<IValidator>();

	private static final MandatoryValidator MANDATORY_VALIDATOR = new MandatoryValidator();

	private ValidationMessages validationMessages = new ValidationMessages();

	public BaseDictionaryControl(ModelType baseControlModel, BaseDictionaryElement<? extends IBaseModel> parent)
	{
		super(baseControlModel, parent);

		if (baseControlModel.isMandatory())
		{
			this.validators.add(MANDATORY_VALIDATOR);
		}

	}

	public String getEditorLabel()
	{
		String label = getModel().getEditorLabel();

		if (getModel().isMandatory())
		{
			label += MyAdmin.MESSAGES.mandatoryMarker();
		}

		return label;
	}

	public String getFilterLabel()
	{
		return getModel().getFilterLabel();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValueType getValue()
	{
		return (ValueType) getVOWrapper().get(getModel().getAttributePath());
	}

	@Override
	public void setValue(ValueType value)
	{
		setValueInternal(value);
	}

	private void setValueInternal(ValueType value)
	{
		this.validationMessages.clear();

		validate(value);

		if (!this.validationMessages.hasError())
		{
			getVOWrapper().set(getModel().getAttributePath(), value);
		}

	}

	private void validate(ValueType value)
	{
		for (IValidator validator : this.validators)
		{
			this.validationMessages.addAll(validator.validate(value, getModel()));
		}
	}

	@Override
	public ModelType getModel()
	{
		return super.getModel();
	}

	@Override
	public String format()
	{
		return getValue() != null ? getValue().toString() : "";
	}

	@Override
	public void parseValue(String valueString)
	{
		if (Strings.isNullOrEmpty(valueString))
		{
			setValueInternal(null);
		}
		else
		{
			ParseResult parseResult = parseValueInternal(valueString);

			if (parseResult.getValidationMessage() == null)
			{
				setValue(parseResult.getValue());
			}
			else
			{
				setValue(null);
				this.validationMessages.addValidationMessage(parseResult.getValidationMessage());
			}
		}
	}

	@Override
	public boolean isMandatory()
	{
		return getModel().isMandatory();
	}

	@Override
	public List<BaseDictionaryElement<?>> getAllChildren()
	{
		return Collections.emptyList();
	}

	@Override
	public IValidationMessages getValidationMessages()
	{
		return this.validationMessages;
	}

	protected abstract ParseResult parseValueInternal(String valueString);
}
