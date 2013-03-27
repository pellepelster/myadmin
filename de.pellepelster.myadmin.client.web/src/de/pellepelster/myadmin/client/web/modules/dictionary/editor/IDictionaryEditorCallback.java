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
package de.pellepelster.myadmin.client.web.modules.dictionary.editor;

import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;

public interface IDictionaryEditorCallback
{

	/**
	 * Gets called whenever a vo is loaded fromthe server
	 * 
	 * @param vo
	 */
	void onLoad(IBaseVO vo);

	/**
	 * Gets called whenever a VO is saved
	 * 
	 * @param vo
	 */
	void onSave(IBaseVO vo);
}
