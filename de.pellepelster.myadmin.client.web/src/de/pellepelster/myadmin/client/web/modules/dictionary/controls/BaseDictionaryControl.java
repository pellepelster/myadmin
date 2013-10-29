package de.pellepelster.myadmin.client.web.modules.dictionary.controls;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
<<<<<<< HEAD
import de.pellepelster.myadmin.client.base.modules.dictionary.IValidationMessages;
=======
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IBaseControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.controls.IBaseControlModel;
import de.pellepelster.myadmin.client.web.MyAdmin;
<<<<<<< HEAD
import de.pellepelster.myadmin.client.web.modules.dictionary.ValidationMessages;
=======
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
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

<<<<<<< HEAD
	private ValidationMessages validationMessages = new ValidationMessages();
=======
	private List<IValidationMessage> validationMessages = new ArrayList<IValidationMessage>();
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86

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
<<<<<<< HEAD
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
=======
		getVOWrapper().set(getModel().getAttributePath(), value);
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
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

<<<<<<< HEAD
=======
	protected void addValidationMessage(IValidationMessage validationMessage)
	{
		this.validationMessages.add(validationMessage);
	}

	protected void clearValidationMessage()
	{
		this.validationMessages.clear();
	}

>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
	@Override
	public void parseValue(String valueString)
	{
		if (Strings.isNullOrEmpty(valueString))
		{
<<<<<<< HEAD
			setValueInternal(null);
=======
			setValue(null);
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
		}
		else
		{
			ParseResult parseResult = parseValueInternal(valueString);

			if (parseResult.getValidationMessage() == null)
			{
				setValue(parseResult.getValue());
<<<<<<< HEAD
=======
				clearValidationMessage();
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
			}
			else
			{
				setValue(null);
<<<<<<< HEAD
				this.validationMessages.addValidationMessage(parseResult.getValidationMessage());
=======
				addValidationMessage(parseResult.getValidationMessage());
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
			}
		}
	}

	@Override
	public boolean isMandatory()
	{
		return getModel().isMandatory();
	}

<<<<<<< HEAD
	@Override
	public IValidationMessages getValidationMessages()
	{
		return this.validationMessages;
=======
	
	@Override
	public List<IValidationMessage> getValidationMessages()
	{
		return validationMessages;
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
	}

	protected abstract ParseResult parseValueInternal(String valueString);

<<<<<<< HEAD
=======
	
>>>>>>> 62ad7c38b04e794970ceaee75309670b4db85f86
}
