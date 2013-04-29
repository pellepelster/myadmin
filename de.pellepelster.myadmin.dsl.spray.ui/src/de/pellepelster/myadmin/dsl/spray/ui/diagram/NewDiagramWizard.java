package de.pellepelster.myadmin.dsl.spray.ui.diagram;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
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
import org.eclipselabs.spray.runtime.graphiti.wizard.FileOpener;
import org.eclipselabs.spray.runtime.graphiti.wizard.IDiagramInitializer;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.pellepelster.myadmin.dsl.spray.ui.Messages;
import de.pellepelster.myadmin.ui.util.MyAdminProjectUtil;

public class NewDiagramWizard extends Wizard implements INewWizard
{
	private String organisationName;

	private String projectName;

	@Inject
	private NewDiagramPage newDiagramPage;

	@Inject
	@Named("diagramTypeId")
	private String diagramTypeId;

	@Inject
	private IDiagramInitializer diagramInitializer;

	@Inject
	private FileOpener fileOpener;

	@Inject
	private IWorkspace workspace;

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
		final String fileName = page.getName();
		final ResourceSet rs = new ResourceSetImpl();
		final URI uri = URI.createPlatformResourceURI(containerName + "/" + fileName + ".diagram", true);
		final Resource resource = rs.createResource(uri);

		Diagram diagram = PictogramsFactory.eINSTANCE.createDiagram();
		diagram.setDiagramTypeId(this.diagramTypeId);
		diagram.setName(fileName);
		this.diagramInitializer.initialize(diagram);
		resource.getContents().add(diagram);
		IRunnableWithProgress op = new IRunnableWithProgress()
		{
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException
			{
				try
				{
					resource.save(Collections.emptyMap());
					IFile file = NewDiagramWizard.this.workspace.getRoot().getFile(new Path(uri.toPlatformString(true)));
					NewDiagramWizard.this.fileOpener.selectAndReveal(file);
					NewDiagramWizard.this.fileOpener.openFileToEdit(getShell(), file);
				}
				catch (IOException e)
				{
					e.printStackTrace();
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
