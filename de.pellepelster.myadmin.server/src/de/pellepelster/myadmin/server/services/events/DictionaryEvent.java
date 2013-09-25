package de.pellepelster.myadmin.server.services.events;

import org.springframework.context.ApplicationEvent;

public class DictionaryEvent extends ApplicationEvent {

	public enum DICTIONARY_EVENT_TYPE {
		IMPORT_FINISHED
	}

	private static final long serialVersionUID = 6159612253311699472L;

	private final DICTIONARY_EVENT_TYPE dictionaryEventType;

	public DictionaryEvent(DICTIONARY_EVENT_TYPE dictionaryEventType) {
		super(dictionaryEventType);
		this.dictionaryEventType = dictionaryEventType;
	}

	public DICTIONARY_EVENT_TYPE getDictionaryEventType() {
		return dictionaryEventType;
	}

}
