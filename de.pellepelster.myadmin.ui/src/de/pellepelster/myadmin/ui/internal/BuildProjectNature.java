package de.pellepelster.myadmin.ui.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.launching.JavaRuntime;

import de.pellepelster.myadmin.ui.Constants;
import de.pellepelster.myadmin.ui.Messages;
import de.pellepelster.myadmin.ui.util.MyAdminProjectUtil;

public class BuildProjectNature implements IProjectNature
{
	public static String NATURE_ID = "de.pellepelster.myadmin.ui.buildprojectnature";

	private IProject project;

	@Override
	public void configure() throws CoreException
	{
	}

	@Override
	public void deconfigure() throws CoreException
	{
	}

	@Override
	public IProject getProject()
	{
		return this.project;
	}

	@Override
	public void setProject(IProject project)
	{
		this.project = project;
	}

	public static void runBootstrap(IProject buildProject, IProgressMonitor monitor) throws CoreException, InterruptedException
	{
		LaunchAntInExternalVM.launchAntInExternalVM(buildProject.getFolder(Constants.BASE_ANT_PATH).getFile(Constants.PROJECT_BOOTSTRAP_ANT_FILENAME), monitor,
				true, "", JavaRuntime.getDefaultVMInstall());
	}

	public static Job getBootstrapJob(final IProject buildProject)
	{
		Job job = new Job(Messages.BootstrappingProjects)
		{
			@Override
			protected IStatus run(IProgressMonitor monitor)
			{
				try
				{
					BuildProjectNature.runBootstrap(buildProject, monitor);
				}
				catch (Exception e)
				{
					Logger.error(e);
					return new Status(Status.ERROR, Activator.PLUGIN_ID, 1, e.getMessage(), e);
				}

				return Status.OK_STATUS;
			}

		};

		return job;
	}

	public static void initProject(final IProject buildProject, final String organization, final String projectName, final IProgressMonitor monitor)
			throws CoreException, IOException
	{
		// ant bootstrap files
		IFolder baseAntFolder = buildProject.getFolder(Constants.BASE_ANT_PATH);
		baseAntFolder.create(true, true, monitor);
		final IFolder myadminAntFolder = baseAntFolder.getFolder(Constants.MYADMIN_ANT_PATH);
		myadminAntFolder.create(true, true, monitor);

		MyAdminProjectUtil.writeFileToContainer(baseAntFolder, Constants.PROJECT_BOOTSTRAP_ANT_FILENAME, MyAdminProjectUtil.openProjectBootstrapAnt(), monitor);

		// version properties
		Properties versionProperties = new Properties();
		versionProperties.setProperty("module.version.major", "0");
		versionProperties.setProperty("module.version.minor", "0");
		versionProperties.setProperty("module.version.micro", "1");
		OutputStream versionOutputStream = new ByteArrayOutputStream();
		versionProperties.store(versionOutputStream, null);
		MyAdminProjectUtil.writeFileToContainer(buildProject, Constants.VERSION_PROPERTIES_FILENAME, new ByteArrayInputStream(versionOutputStream.toString()
				.getBytes()), monitor);

		// myadmin properties
		Properties myadminProperties = new Properties();
		myadminProperties.setProperty(Constants.ORGANISATION_NAME_KEY, organization);
		myadminProperties.setProperty(Constants.PROJECT_NAME_KEY, projectName.toLowerCase());
		myadminProperties.setProperty(Constants.BUILD_PROJECT_KEY, "${organisation.name}.${project.name}.build");
		myadminProperties.setProperty("client.project", "${organisation.name}.${project.name}.client");
		myadminProperties.setProperty("client.test.project", "${organisation.name}.${project.name}.client.test");
		myadminProperties.setProperty("server.project", "${organisation.name}.${project.name}.server");
		myadminProperties.setProperty("server.test.project", "${organisation.name}.${project.name}.server.test");
		myadminProperties.setProperty("deploy.project", "${organisation.name}.${project.name}.deploy");
		OutputStream myadminOutputStream = new ByteArrayOutputStream();
		myadminProperties.store(myadminOutputStream, null);

		MyAdminProjectUtil.writeFileToContainer(buildProject, Constants.MYADMIN_PROPERTIES_FILENAME, new ByteArrayInputStream(myadminOutputStream.toString()
				.getBytes()), monitor);
	}

}
