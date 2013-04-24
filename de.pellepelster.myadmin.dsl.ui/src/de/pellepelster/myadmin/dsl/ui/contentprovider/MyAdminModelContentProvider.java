package de.pellepelster.myadmin.dsl.ui.contentprovider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.progress.UIJob;

import com.google.inject.Inject;

import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.dsl.ui.ModelUtil;
import de.pellepelster.myadmin.dsl.ui.outline.MyAdminDslOutlineTreeProvider;

public class MyAdminModelContentProvider implements ITreeContentProvider, IResourceChangeListener
{
	private static Logger LOG = Logger.getLogger(MyAdminModelContentProvider.class);

	@Inject
	protected MyAdminDslOutlineTreeProvider dsl;

	private Viewer viewer;

	public MyAdminModelContentProvider()
	{
		super();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	@Override
	public void dispose()
	{

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
	{
		this.viewer = viewer;
	}

	@Override
	public Object[] getElements(Object element)
	{
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement)
	{
		if (parentElement instanceof IProject)
		{

			IProject project = (IProject) parentElement;

			if (project.isAccessible())
			{
				List<Model> models = ModelUtil.getAllModelsForProject(project);
				return models.toArray();
			}
		}
		else if (parentElement instanceof EObject)
		{
			final EObject modelElement = (EObject) parentElement;

			List<EObject> children = new ArrayList<EObject>();
			children.addAll(modelElement.eContents());

			Collections.sort(children, new Comparator<EObject>()
			{
				@Override
				public int compare(EObject o1, EObject o2)
				{
					return Integer.compare(o1.eClass().getClassifierID(), o2.eClass().getClassifierID());
				}
			});

			return children.toArray();
		}

		return null;
	}

	@Override
	public Object getParent(Object element)
	{
		if (element instanceof EObject)
		{
			final EObject modelElement = (EObject) element;
			return modelElement.eContainer();
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean hasChildren(Object element)
	{
		if (element instanceof EObject)
		{
			final EObject modelElement = (EObject) element;
			return !modelElement.eContents().isEmpty();
		}
		else
		{
			return false;
		}
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event)
	{
		IResourceDelta delta = event.getDelta();
		try
		{
			delta.accept(new IResourceDeltaVisitor()
			{
				@Override
				public boolean visit(IResourceDelta delta) throws CoreException
				{
					IResource resource = delta.getResource();
					if (resource == null)
						return false;
					switch (resource.getType())
					{
						case IResource.ROOT:
							return true;
						case IResource.PROJECT:
							return true;
						case IResource.FOLDER:
							return true;
						case IResource.FILE:
							final IFile file = (IFile) resource;

							if (ModelUtil.isModelFile(file))
							{
								UIJob job = new UIJob("Update Viewer") { //$NON-NLS-1$

									@Override
									public IStatus runInUIThread(IProgressMonitor monitor)
									{
										if (MyAdminModelContentProvider.this.viewer != null
												&& !MyAdminModelContentProvider.this.viewer.getControl().isDisposed())
										{
											Model model = ModelUtil.getModelFromFile(file);

											if (MyAdminModelContentProvider.this.viewer instanceof StructuredViewer && model != null)
											{
												((StructuredViewer) MyAdminModelContentProvider.this.viewer).refresh(model, true);
											}
											else
											{
												MyAdminModelContentProvider.this.viewer.refresh();
											}
										}
										return Status.OK_STATUS;
									}
								};
								job.setSystem(true);
								job.schedule();
							}
							return false;
					}
					return false;

				}

			});
		}
		catch (CoreException e)
		{
			LOG.error(String.format("error processing resource change event for '%s'", event.getResource().getFullPath().toString()), e);
			return;
		}

	}
}
