package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.messages.IValidationMessage;
import de.pellepelster.myadmin.client.base.modules.dictionary.IValidationMessages;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseModel;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.IBaseRootModel;
import de.pellepelster.myadmin.client.web.modules.dictionary.ValidationMessages;
import de.pellepelster.myadmin.client.web.modules.dictionary.base.BaseDictionaryElement;
import de.pellepelster.myadmin.client.web.modules.dictionary.container.Composite;

public class BaseRootElement<ModelType extends IBaseRootModel> extends BaseDictionaryElement<ModelType>
{
	private final Composite rootComposite;

	private Map<BaseDictionaryElement<?>, ValidationMessages> validationMessagesMap = new HashMap<BaseDictionaryElement<?>, ValidationMessages>();
	
	private RootElementValidationMessages rootElementValidationMessages;
	
	public BaseRootElement(ModelType baseRootModel, BaseDictionaryElement<IBaseModel> parent)
	{
		super(baseRootModel, parent);

		this.rootComposite = new Composite(baseRootModel.getCompositeModel(), this);
		
		rootElementValidationMessages = new RootElementValidationMessages(validationMessagesMap);
	}

	public Composite getRootComposite()
	{
		return this.rootComposite;
	}

	@Override
	public List<? extends BaseDictionaryElement<?>> getAllChildren()
	{
		return this.rootComposite.getAllChildren();
	}

	public ValidationMessages getValidationMessages(BaseDictionaryElement<?> baseDictionaryElement)
	{
		if (!validationMessagesMap.containsKey(baseDictionaryElement))
		{
			validationMessagesMap.put(baseDictionaryElement, new ValidationMessages());
		}
		
		return validationMessagesMap.get(baseDictionaryElement);
	}
	
	public void addValidationMessages(BaseDictionaryElement<?> baseDictionaryElement,  List<IValidationMessage> elementValidationMessages)
	{
		getValidationMessages(baseDictionaryElement).addAll(elementValidationMessages);
	}

	public void clearValidationMessages(BaseDictionaryElement<?> baseDictionaryElement)
	{
		getValidationMessages(baseDictionaryElement).clear();
		validationMessagesMap.remove(baseDictionaryElement);
	}
	
	public IValidationMessages getValidationMessages()
	{
		return rootElementValidationMessages;
	}

}
