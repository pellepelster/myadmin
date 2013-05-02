package de.pellepelster.myadmin.dsl.graphiti.ui.wizards;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramsFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.xtext.ui.util.FileOpener;

import com.google.inject.Inject;

import de.pellepelster.myadmin.dsl.graphiti.ui.Messages;
import de.pellepelster.myadmin.dsl.graphiti.ui.ModelDiagramTypeProvider;
import de.pellepelster.myadmin.dsl.graphiti.ui.MyAdminGraphitiConstants;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.DiagramUtil;
import de.pellepelster.myadmin.dsl.graphiti.ui.util.GraphitiProperties;
import de.pellepelster.myadmin.ui.util.MyAdminProjectUtil;

public class NewDiagramWizard extends Wizard implements INewWizard
{
	private String organisationName;

	private String projectName;

	@Inject
	private NewDiagramPage newDiagramPage;

	@Inject
	private IWorkbench workbench;

	@Inject
	private FileOpener fileOpener;

	private static Logger LOG = Logger.getLogger(NewDiagramWizard.class);

	public NewDiagramWizard()
	{
		setWindowTitle(Messages.NewDiagram);
	}

	@Override
	public void addPages()
	{
		addPage(this.newDiagramPage);
	}

	@Override
	public void createPageControls(Composite pageContainer)
	{
		super.createPageControls(pageContainer);
		this.newDiagramPage.setDiagramName(String.format("%s.%s", this.organisationName, this.projectName));
	}

	@Override
	public boolean performFinish()
	{
		return performFinish(this.newDiagramPage);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	public boolean performFinish(NewDiagramPage page)
	{
		final String containerName = page.getContainerName();
		final String fileName = page.getDiagramName();

		final ResourceSet rs = new ResourceSetImpl();
		final URI uri = URI.createPlatformResourceURI(containerName + "/" + fileName + ".diagram", true);
		final Resource resource = rs.createResource(uri);

		final Diagram diagram = PictogramsFactory.eINSTANCE.createDiagram();
		diagram.setDiagramTypeId(ModelDiagramTypeProvider.MODEL_DIAGRAM_TYPE_ID);
		diagram.setName(fileName);

		GraphitiProperties.set(diagram, MyAdminGraphitiConstants.ORGANISATION_NAME_KEY, this.organisationName);
		GraphitiProperties.set(diagram, MyAdminGraphitiConstants.PROJECT_NAME_KEY, this.projectName);

		DiagramUtil.initializeDiagram(diagram);
		resource.getContents().add(diagram);

		IRunnableWithProgress op = new IRunnableWithProgress()
		{
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException
			{
				try
				{
					resource.save(Collections.emptyMap());

					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					IFile file = workspace.getRoot().getFile(new Path(uri.toPlatformString(true)));

					NewDiagramWizard.this.fileOpener.selectAndReveal(file);
					NewDiagramWizard.this.fileOpener.openFileToEdit(getShell(), file);
				}
				catch (IOException e)
				{
					LOG.error(String.format("error  saving diagram '%s'", diagram.getName()), e);
				}
				finally
				{
					monitor.done();
				}
			}
		};
		try
		{
			getContainer().run(true, false, op);
		}
		catch (InterruptedException e)
		{
			return false;
		}
		catch (InvocationTargetException e)
		{
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), Messages.Error, realException.getMessage());

			return false;
		}

		return true;
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
		Object obj = selection.getFirstElement();

		if (!(obj instanceof IAdaptable))
		{
			throw new RuntimeException(String.format("'%s' is not adaptable", obj));
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

		IProject project = container.getProject();

		this.organisationName = MyAdminProjectUtil.getOrganisationName(project);
		this.projectName = MyAdminProjectUtil.getProjectName(project);

		this.newDiagramPage.setSelection(selection);
	}
}
