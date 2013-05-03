package de.pellepelster.myadmin.dsl.spray.ui.diagram;

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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.pellepelster.myadmin.dsl.spray.ui.Messages;
import de.pellepelster.myadmin.dsl.spray.ui.util.ResourceManager;

public class NewDiagramPage extends WizardPage
{

	private Text nameText;

	private Text locationText;

	private ISelection selection;

	@Inject
	@Named("diagramTypeId")
	private String diagramTypeId;

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
		container.setLayout(new GridLayout(3, false));

		Label locationLabel = new Label(container, SWT.NONE);
		locationLabel.setText(Messages.Location);

		this.locationText = new Text(container, SWT.BORDER);
		GridData locationTextGridData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		locationTextGridData.widthHint = 300;
		this.locationText.setLayoutData(locationTextGridData);
		this.locationText.setEnabled(false);

		Button button = new Button(container, SWT.PUSH);
		button.setText(Messages.Browse);
		button.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				handleBrowse();
			}
		});

		Label labelName = new Label(container, SWT.NONE);
		labelName.setText(Messages.DiagramName);

		createNameText(container);

		initializeLocationText();

		this.nameText.setFocus();
	}

	private void createNameText(Composite container)
	{
		this.nameText = new Text(container, SWT.BORDER);
		GridData gd_nameText = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_nameText.widthHint = 300;
		this.nameText.setLayoutData(gd_nameText);
		this.nameText.addModifyListener(new ModifyListener()
		{
			@Override
			public void modifyText(ModifyEvent e)
			{
				validateName();
			}
		});
		this.nameText.setEnabled(false);
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	public void handleBrowse()
	{
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(getShell(), ResourcesPlugin.getWorkspace().getRoot(), false, "Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK)
		{
			Object[] result = dialog.getResult();
			if (result.length == 1)
			{
				this.locationText.setText(((Path) result[0]).toString());
			}
		}
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
		return true;
		// if (getName() == null || getName().isEmpty())
		// {
		// updateStatus(Messages.ModelFileNameMustBeSpecified);
		// return false;
		// }
		//
		// if (!getName().matches(MyAdminProjectUtil.FQDN_PROJECT_NAME_REGEX))
		// {
		// updateStatus(Messages.ModelFileNameFormat);
		// return false;
		// }
		//
		// return true;
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
