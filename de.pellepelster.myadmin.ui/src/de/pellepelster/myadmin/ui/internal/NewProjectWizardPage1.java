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
package de.pellepelster.myadmin.ui.internal;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.pellepelster.myadmin.ui.Constants.PROJECT_NAME_POSTFIXES;
import de.pellepelster.myadmin.ui.Messages;

public class NewProjectWizardPage1 extends WizardPage
{
	private Text organizationNameText;

	private Text projectNameText;

	private final FocusListener focusListener = new FocusListener()
	{

		@Override
		public void focusLost(FocusEvent e)
		{
			validate(false);
		}

		@Override
		public void focusGained(FocusEvent e)
		{
		}
	};

	public NewProjectWizardPage1()
	{
		super(NewProjectWizardPage1.class.getSimpleName(), Messages.NewProjectPage1Title, null);
		setDescription(Messages.NewProjectPage1Desc);
	}

	@Override
	public void createControl(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		composite.setLayout(gridLayout);
		composite.setLayoutData(GridDataFactory.swtDefaults().grab(true, true).align(SWT.FILL, SWT.FILL).create());
		composite.setFont(parent.getFont());

		// organization
		Label organizationNameLabel = new Label(composite, SWT.NONE);
		organizationNameLabel.setText(Messages.Organization);

		this.organizationNameText = new Text(composite, SWT.BORDER);
		this.organizationNameText.setLayoutData(GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());
		this.organizationNameText.addFocusListener(this.focusListener);

		// module
		Label projectNameLabel = new Label(composite, SWT.NONE);
		projectNameLabel.setText(Messages.ProjectName);

		this.projectNameText = new Text(composite, SWT.BORDER);
		this.projectNameText.setLayoutData(GridDataFactory.swtDefaults().grab(true, false).align(SWT.FILL, SWT.CENTER).create());
		this.projectNameText.addFocusListener(this.focusListener);

		setControl(composite);
	}

	protected boolean validate(boolean isFinalStep)
	{
		if (!getProject().isEmpty() && !getOrganizationName().isEmpty())
		{
			for (PROJECT_NAME_POSTFIXES projectNamePostfix : PROJECT_NAME_POSTFIXES.values())
			{
				String projectName = String.format("%s.%s.%s", getOrganizationName(), getProject(), projectNamePostfix.toString());

				if (ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).exists())
				{
					this.setMessage(Messages.bind(Messages.ProjectExists, new Object[] { projectName }), ERROR);

					return false;
				}
			}
		}
		else
		{
			if (isFinalStep)
			{
				if (getProject().isEmpty())
				{
					this.setMessage(Messages.bind(Messages.FillInField, new Object[] { Messages.ProjectName }), ERROR);
				}

				if (getOrganizationName().isEmpty())
				{
					this.setMessage(Messages.bind(Messages.FillInField, new Object[] { Messages.Organization }), ERROR);
				}

				return false;
			}
		}

		return true;
	}

	public String getOrganizationName()
	{
		return this.organizationNameText.getText();
	}

	public String getProject()
	{
		return this.projectNameText.getText();
	}

}
