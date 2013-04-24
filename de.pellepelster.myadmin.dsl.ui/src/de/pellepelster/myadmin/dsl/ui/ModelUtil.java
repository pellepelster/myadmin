package de.pellepelster.myadmin.dsl.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jdt.ui.PreferenceConstants;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import de.pellepelster.myadmin.dsl.myAdminDsl.Model;

public class ModelUtil
{
	private static final String MYADMIN_MODEL_FILE_EXTENSION = "msl";

	private static Logger LOG = Logger.getLogger(ModelUtil.class);

	public static List<Model> getAllModelsForProject(IProject project)
	{
		ResourceSet resourceSet = new ResourceSetImpl();

		List<IFile> modelFiles = ModelUtil.getFilesByExtension(project, MYADMIN_MODEL_FILE_EXTENSION);
		List<Model> models = new ArrayList<Model>();

		for (IFile modelFile : modelFiles)
		{
			models.add(getModelFromFile(modelFile));
		}

		return models;

	}

	public static <T> List<T> selectType(Iterator<EObject> sourceList, Class<T> clazz)
	{
		List<T> filteredList = new ArrayList<T>();

		List<EObject> l = Lists.newArrayList(sourceList);

		filteredList = Lists.newArrayList(Iterators.filter(sourceList, clazz));

		return filteredList;
	}

	public static Model getModelFromFile(IFile modelFile)
	{
		ResourceSet resourceSet = new ResourceSetImpl();

		Resource modelResource = null;
		try
		{
			modelResource = resourceSet.createResource(URI.createURI(modelFile.getRawLocationURI().toString()));
			modelResource.load(modelFile.getContents(), resourceSet.getLoadOptions());
		}
		catch (Exception e)
		{
			LOG.error(String.format("error creating resource for '%s'", modelResource.getURI().toString()), e);
		}

		if (modelResource.getContents().get(0) instanceof Model)
		{
			return (Model) modelResource.getContents().get(0);
		}
		else
		{
			throw new RuntimeException(String.format("file '%s' did not contain an MyAdmin model", modelFile.getLocation().toString()));
		}

	}

	public static boolean isModelFile(IFile file)
	{
		return MYADMIN_MODEL_FILE_EXTENSION.equals(file.getFileExtension());
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
}
