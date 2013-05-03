package de.pellepelster.myadmin.dsl.graphiti.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.ResourceManager;
import de.pellepelster.myadmin.ui.util.MyAdminProjectUtil;

public class NewDiagramPage extends WizardPage
{

	private Text nameText;

	private Text locationText;

	private ISelection selection;

	/**
	 * Create the wizard.
	 */
	public NewDiagramPage()
	{
		super("wizardPage");
		setImageDescriptor(ResourceManager.getPluginImageDescriptor("org.eclipse.ui", "/icons/full/wizban/new_wiz.png"));
		setTitle(Messages.Diagram);
		setDescription(Messages.EnterDiagramName);
	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	@Override
	public void createControl(Composite parent)
	{
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		container.setLayout(new GridLayout(2, false));

		Label locationLabel = new Label(container, SWT.NONE);
		locationLabel.setText(Messages.Location);

		this.locationText = new Text(container, SWT.BORDER);
		GridData locationTextGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		locationTextGridData.widthHint = 300;
		this.locationText.setLayoutData(locationTextGridData);
		this.locationText.setEnabled(false);

		Label labelName = new Label(container, SWT.NONE);
		labelName.setText(Messages.DiagramName);

		createNameText(container);

		initializeLocationText();

		this.nameText.setFocus();
	}

	private void createNameText(Composite container)
	{
		this.nameText = new Text(container, SWT.BORDER);
		GridData nameTextGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		nameTextGridData.widthHint = 300;
		this.nameText.setEnabled(false);
		this.nameText.setLayoutData(nameTextGridData);
		this.nameText.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				// validateName();
			}
		});

	}

	protected void updateStatus(String message)
	{
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getDiagramName()
	{
		return this.nameText.getText();
	}

	public void setDiagramName(String name)
	{
		this.nameText.setText(name);
	}

	public String getContainerName()
	{
		return this.locationText.getText();
	}

	protected void initializeLocationText()
	{

		if (this.selection == null || this.selection.isEmpty() || !(this.selection instanceof IStructuredSelection))
		{
			return;
		}

		IStructuredSelection structuredSelection = (IStructuredSelection) this.selection;
		if (structuredSelection.size() > 1)
		{
			return;
		}

		Object obj = structuredSelection.getFirstElement();
		if (!(obj instanceof IAdaptable))
		{
			return;
		}

		IResource resource = null;
		resource = (IResource) ((IAdaptable) obj).getAdapter(IResource.class);
		IContainer container;
		if (resource instanceof IContainer)
		{
			container = (IContainer) resource;
		}
		else
		{
			container = resource.getParent();
		}

		this.locationText.setText(container.getFullPath().toString());
	}

	protected boolean validateName()
	{

		if (getName() == null || getName().isEmpty())
		{
			updateStatus(Messages.ModelFileNameMustBeSpecified);
			return false;
		}

		if (MyAdminProjectUtil.isValidFQDNProjectName(getName()))
		{
			updateStatus(Messages.ModelFileNameFormat);
			return false;
		}

		return true;
	}

	protected boolean locationValid()
	{
		IResource container = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(getContainerName()));

		if (getContainerName().length() == 0)
		{
			updateStatus(Messages.FileContainerMustBeSpecified);
			return false;
		}
		if (container == null || (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0)
		{
			updateStatus(Messages.FileContainerMustExist);
			return false;
		}
		if (!container.isAccessible())
		{
			updateStatus(Messages.ProjectMustBeWritable);
			return false;
		}
		updateStatus(null);
		return true;
	}

	protected void setNameText(String textToSet)
	{
		this.nameText.setText(textToSet);
	}

	/**
	 * Ensures that both text fields are set.
	 */
	protected void dialogChanged()
	{
		String fileName = getName();

		if (!locationValid())
		{
			return;
		}
		if (fileName.length() == 0)
		{
			updateStatus(Messages.DeviceTypeMustBeDpecified);
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0)
		{
			updateStatus(Messages.DeviceTypeNameMustBeValid);
			return;
		}
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1)
		{
			updateStatus(Messages.DeviceTypeNameShouldNotContainDot);
			return;
		}

		if (!validateName())
		{
			return;
		}

		updateStatus(null);
	}

	public void setSelection(ISelection selection)
	{
		this.selection = selection;
	}
}
