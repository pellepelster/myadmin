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
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.SingleUploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

import de.pellepelster.myadmin.client.base.modules.dictionary.controls.IFileControl;
import de.pellepelster.myadmin.client.base.modules.dictionary.model.DictionaryModelUtil;
import de.pellepelster.myadmin.client.base.util.SimpleCallback;
import de.pellepelster.myadmin.client.gwt.ControlHelper;
import de.pellepelster.myadmin.client.gwt.GwtStyles;
import de.pellepelster.myadmin.client.gwt.utils.ActionImage;
import de.pellepelster.myadmin.client.web.MyAdmin;
import de.pellepelster.myadmin.client.web.entities.dictionary.FileVO;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.FileControl;
import de.pellepelster.myadmin.client.web.modules.dictionary.controls.IGwtControl;

public class GwtFileControl extends Composite implements IGwtControl, ClickHandler, SimpleCallback<Void>
{
	private final FileControl fileControl;

	private final ControlHelper gwtControlHelper;

	private SingleUploader singleUploader = new SingleUploader(FileInputType.BUTTON);

	private HorizontalPanel panel = new HorizontalPanel();

	private Anchor fileNameAnchor = new Anchor();

	private ActionImage deleteAction = new ActionImage(MyAdmin.RESOURCES.delete(), this);

	// TODO avoid fetch of byte[] on load
	public GwtFileControl(final FileControl fileControl)
	{
		super();
		panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		panel.setSpacing(GwtStyles.SPACING);

		fileNameAnchor.setTarget("_blank");
		panel.add(fileNameAnchor);

		panel.add(deleteAction);

		panel.add(singleUploader.getForm());
		initWidget(panel);

		singleUploader.setAutoSubmit(true);
		singleUploader.setEnabled(true);

		// TODO get base remote url from service locator
		singleUploader.setServletPath(GWT.getModuleBaseURL() + "../remote/" + IFileControl.GWT_UPLOAD_REQUEST_MAPPING);

		this.fileControl = fileControl;
		gwtControlHelper = new ControlHelper(this, fileControl, this, true);
		singleUploader.ensureDebugId(DictionaryModelUtil.getDebugId(fileControl.getModel()));

		singleUploader.addOnFinishUploadHandler(new OnFinishUploaderHandler()
		{
			@Override
			public void onFinish(IUploader uploader)
			{
				fileControl.parseValue(uploader.getServerInfo().message);
				setFile(uploader.getFileName(), uploader.getServerInfo().message);
			}
		});
	}

	@Override
	public void setContent(Object content)
	{
		if (content != null)
		{
			if (content instanceof FileVO)
			{
				FileVO file = (FileVO) content;
				setFile(file.getFileName(), file.getFileUUID());
			}
			else
			{
				throw new RuntimeException("unsupported value type '" + content.getClass().getName() + "'");
			}
		}
		else
		{
			clearFile();
		}

	}

	@Override
	public void onClick(ClickEvent event)
	{
	}

	private void setFile(String fileName, String fileUUID)
	{
		deleteAction.enable();
		fileNameAnchor.setText(getFileName(fileName));
		fileNameAnchor.setHref(getFileUrl(fileUUID));
	}

	public static String getFileUrl(String fileUUID)
	{
		return GWT.getModuleBaseURL() + "../remote/" + IFileControl.FILE_DOWNLOAD_REQUEST_MAPPING + "/" + IFileControl.REQUEST_MAPPING_GET_FILE + "/"
				+ fileUUID;
	}

	public String getFileName(String filePathName)
	{
		if (filePathName == null)
			return null;

		int dotPos = filePathName.lastIndexOf('.');
		int slashPos = filePathName.lastIndexOf('\\');
		if (slashPos == -1)
		{
			slashPos = filePathName.lastIndexOf('/');
		}

		if (dotPos > slashPos)
		{
			return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0, dotPos);
		}

		return filePathName.substring(slashPos > 0 ? slashPos + 1 : 0);
	}

	private void clearFile()
	{
		deleteAction.disable();
		fileNameAnchor.setText(MyAdmin.MESSAGES.fileNone());
		fileControl.parseValue(null);
	}

	@Override
	public void onCallback(Void t)
	{
		clearFile();
	}
}
