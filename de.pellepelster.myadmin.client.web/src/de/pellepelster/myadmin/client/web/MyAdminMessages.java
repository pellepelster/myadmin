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
package de.pellepelster.myadmin.client.web;

import com.google.gwt.i18n.client.LocalizableResource.Generate;
import com.google.gwt.i18n.client.Messages;

@Generate(format = "com.google.gwt.i18n.rebind.format.PropertiesFormat")
public interface MyAdminMessages extends Messages
{

	static final String MANDATORY_MESSAGE_KEY = "mandatory.message.key";

	@DefaultMessage("Add {0}")
	String dictionaryAdd(String title);

	@DefaultMessage("Add")
	String editableTableAdd();

	@DefaultMessage("Back to search results")
	String editorBack();

	@DefaultMessage("Editor contains unsaved data, do you really want to close?")
	String editorClose();

	@DefaultMessage("Unable to save the data, the editor contains errors")
	String editorContainsErrors();

	@DefaultMessage("*")
	String editorDirtyMarker();

	@DefaultMessage("*")
	String mandatoryMarker();

	@DefaultMessage("New")
	String editorNew();

	@DefaultMessage("None")
	String hierarchicalNone();

	@DefaultMessage("Save")
	String editorSave();

	@DefaultMessage("{0}")
	String editorTitle(String editorTitle);

	@DefaultMessage("''{0}'' is not a valid float")
	String floatValidationError(String value);

	@DefaultMessage("''{0}'' is not a valid boolean")
	String booleanValidationError(String value);


	@DefaultMessage("''{0}'' is not a valid integer")
	String integerValidationError(String value);

	@Key(MANDATORY_MESSAGE_KEY)
	@DefaultMessage("Input is needed for field \"{0}\"")
	String mandatoryMessage(@Optional String fieldLabel);

	@DefaultMessage("Navigation")
	String navigationTitle();

	@DefaultMessage("Create")
	String searchCreate();

	@DefaultMessage("{0} results")
	@AlternateMessage({ "=1", "{0} result" })
	String searchResults(@PluralCount int resoultCount);

	@DefaultMessage("Search")
	String searchSearch();

	@DefaultMessage("Search for {0}")
	String searchTitle(String dictionaryName);

	@DefaultMessage("Refresh")
	String editorRefresh();

	@DefaultMessage("Ok")
	String buttonOk();

	@DefaultMessage("Cancel")
	String buttonCancel();

	@DefaultMessage("Select {0}")
	String voSelectionHeader(String message);

}