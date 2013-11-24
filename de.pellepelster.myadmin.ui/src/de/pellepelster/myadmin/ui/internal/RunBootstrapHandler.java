package de.pellepelster.myadmin.ui.internal;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.internal.core.JavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class RunBootstrapHandler extends AbstractHandler
{
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection)
		{
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;

			if (structuredSelection.getFirstElement() instanceof JavaProject)
			{
				JavaProject javaProject = (JavaProject) structuredSelection.getFirstElement();

				try
				{
					if (javaProject.getProject().getNature(BuildProjectNature.NATURE_ID) != null)
					{
						BuildProjectNature.getBootstrapJob(javaProject.getProject()).schedule();
					}
				}
				catch (Exception e)
				{
					throw new RuntimeException(e);
				}

			}
		}

		return null;
	}
}
