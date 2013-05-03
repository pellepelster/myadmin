package de.pellepelster.myadmin.dsl.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.ui.PreferenceConstants;

import de.pellepelster.myadmin.dsl.myAdminDsl.ModelRoot;
import de.pellepelster.myadmin.dsl.util.ModelUtil;

public class ModelUiUtil extends ModelUtil
{
	private static final String MYADMIN_MODEL_FILE_EXTENSION = "msl";

	private static Logger LOG = Logger.getLogger(ModelUiUtil.class);

	public static List<ModelRoot> getAllModelsForProject(IProject project)
	{
		ResourceSet resourceSet = new ResourceSetImpl();

		List<IFile> modelFiles = ModelUiUtil.getFilesByExtension(project, MYADMIN_MODEL_FILE_EXTENSION);
		List<ModelRoot> models = new ArrayList<ModelRoot>();

		for (IFile modelFile : modelFiles)
		{
			ModelRoot model = getModelFromFile(modelFile);

			if (model != null)
			{
				models.add(model);

			}
		}

		return models;

	}

	public static ModelRoot getModelFromFile(IFile file)
	{
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);

		return getModelFromFile(uri);
	}

	public static List<IFile> getFilesByExtension(IProject project, final String extension)
	{
		final List<IFile> files = new ArrayList<IFile>();

		final String binFolderName = PreferenceConstants.getPreferenceStore().getString(PreferenceConstants.SRCBIN_BINNAME);

		try
		{
			project.accept(new IResourceVisitor()
			{
				@Override
				public boolean visit(IResource resource) throws CoreException
				{
					if (IResource.FILE == resource.getType() && extension.equals(resource.getFileExtension()))
					{
						files.add((IFile) resource);
						return false;
					}

					if (IResource.FOLDER == resource.getType())
					{
						return !resource.getName().equals(binFolderName);
					}
					else if (IResource.PROJECT == resource.getType())
					{
						return true;
					}

					return false;
				}
			});

		}
		catch (Exception e)
		{
			LOG.error(String.format("error traversing project '%s'", project.getName()), e);
		}

		return files;
	}

	public static boolean isModelFile(IFile file)
	{
		return MYADMIN_MODEL_FILE_EXTENSION.equals(file.getFileExtension());
	}

}
