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
package de.pellepelster.myadmin.client.gwt.modules.dictionary.controls;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploader;
import gwtupload.client.SingleUploader;

import com.google.gwt.core.client.GWT;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IFileControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.web.entities.dictionary.FileVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.FileControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IGwtControl;

public class GwtFileControl extends SingleUploader implements IGwtControl
{

	private final FileControl fileControl;
	private final ControlHelper gwtControlHelper;

	public GwtFileControl(FileControl fileControl)
	{
		super(FileInputType.BUTTON);

		setAutoSubmit(true);
		setEnabled(true);
		avoidRepeatFiles(true);

		// TODO get base remote url from service locator
		setServletPath(GWT.getModuleBaseURL() + "../remote/fileupload.gupld");

		this.fileControl = fileControl;
		gwtControlHelper = new ControlHelper(this, fileControl, this, true);
		ensureDebugId(DictionaryModelUtil.getDebugId(fileControl.getModel()));

		addOnFinishUploadHandler(new OnFinishUploaderHandler()
		{
			@Override
			public void onFinish(IUploader uploader)
			{
				String fileNameId = uploader.getServerResponse();
				fileNameId.trim();
			}
		});
	}

	@Override
	protected void assignNewNameToFileInput()
	{
		getFileInput().setName(IFileControl.FILE_INPUT_NAME);
	}

	@Override
	public void setContent(Object content)
	{
		if (content != null)
		{
			if (content instanceof FileVO)
			{
			}
			else
			{
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		}

	}
}
