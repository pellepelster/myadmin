package de.pellepelster.myadmin.ui.internal;

import java.io.InputStream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;

import de.pellepelster.myadmin.ui.Constants;

public class Utils
{
	public static InputStream openProjectBootstrapAnt()
	{
		return Utils.class.getResourceAsStream(String.format("%s/%s", Constants.TEMPLATES_PATH, Constants.PROJECT_BOOTSTRAP_ANT_FILENAME));
	}

	public static void writeFileToContainer(IContainer container, String fileName, InputStream stream, IProgressMonitor monitor)
	{
		try
		{
			IFile file = container.getFile(new Path(fileName));
			if (file.exists())
			{
				file.setContents(stream, true, true, monitor);
			}
			else
			{
				file.create(stream, true, monitor);
			}
			stream.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}
}
