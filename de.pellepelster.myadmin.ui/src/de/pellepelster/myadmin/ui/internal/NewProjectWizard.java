/**
 * Copyright (c) 2013 Christian Pelster.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Christian Pelster - initial API and implementation
 */
package de.pellepelster.myadmin.ui.internal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.ivyde.eclipse.IvyNature;
import org.apache.ivyde.eclipse.cpcontainer.IvyClasspathContainerConfAdapter;
import org.apache.ivyde.eclipse.cpcontainer.IvyClasspathContainerConfiguration;
import org.eclipse.ant.launching.IAntLaunchConstants;
import org.eclipse.core.externaltools.internal.IExternalToolConstants;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.ui.actions.WorkbenchRunnableAdapter;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import de.pellepelster.myadmin.ui.Constants;
import de.pellepelster.myadmin.ui.Messages;

public class NewProjectWizard extends Wizard implements INewWizard
{
	private final Map<Constants.PROJECT_NAME_POSTFIXES, IProject> projects = new HashMap<Constants.PROJECT_NAME_POSTFIXES, IProject>();

	private final NewProjectWizardPage1 newProjectWizardPage1;

	public NewProjectWizard()
	{
		this.newProjectWizardPage1 = new NewProjectWizardPage1();
	}

	@Override
	public void addPages()
	{
		addPage(this.newProjectWizardPage1);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection)
	{
	}

	private void addProjectNature(IProject project, String natureId, IProgressMonitor monitor) throws CoreException
	{
		IProjectDescription description = project.getDescription();
		List<String> natures = new ArrayList<String>();
		natures.addAll(Arrays.asList(description.getNatureIds()));
		natures.add(natureId);

		description.setNatureIds(natures.toArray(new String[0]));
		project.setDescription(description, monitor);

	}

	private IProject getProject(String organization, String projectName, Constants.PROJECT_NAME_POSTFIXES projectNamePostfix)
	{
		String fullProjectName = getProjectName(organization, projectName.toLowerCase(), projectNamePostfix);
		return ResourcesPlugin.getWorkspace().getRoot().getProject(fullProjectName);
	}

	private void createProjects(final String organization, final String projectName, final IProgressMonitor monitor)
	{
		try
		{
			for (Constants.PROJECT_NAME_POSTFIXES projectNamePostfix : Constants.PROJECT_NAME_POSTFIXES.values())
			{
				IProject project = getProject(organization, projectName, projectNamePostfix);

				this.projects.put(projectNamePostfix, project);

				project.create(monitor);
				project.open(monitor);

				List<IClasspathEntry> classpathEntries = new ArrayList<IClasspathEntry>();

				// src folder
				String srcFolderName = PreferenceConstants.getPreferenceStore().getString(PreferenceConstants.SRCBIN_SRCNAME);
				IPath srcPath = new Path(srcFolderName);
				IFolder srcFolder = project.getFolder(srcPath);
				srcFolder.create(true, true, monitor);
				classpathEntries.add(JavaCore.newSourceEntry(project.getFullPath().append(srcPath)));

				// bin folder
				String binFolderName = PreferenceConstants.getPreferenceStore().getString(PreferenceConstants.SRCBIN_BINNAME);
				IPath binPath = new Path(binFolderName);
				IFolder binFolder = project.getFolder(binPath);
				binFolder.create(IResource.FORCE | IResource.DERIVED, true, monitor);
				binFolder.setDerived(true, monitor);

				// project natures
				addProjectNature(project, JavaCore.NATURE_ID, monitor);

				IJavaProject javaProject = JavaCore.create(project);

				Properties myadminProjectProperties = new Properties();
				myadminProjectProperties.setProperty(Constants.BUILD_PROJECT_KEY,
						String.format("%s.%s.%s", organization, projectName.toLowerCase(), Constants.PROJECT_NAME_POSTFIXES.BUILD.toString()));

				OutputStream myadminProjectOutputStream = new ByteArrayOutputStream();
				myadminProjectProperties.store(myadminProjectOutputStream, null);

				switch (projectNamePostfix)
				{
					case GENERATOR:

						write(project, Constants.MYADMIN_PROJECT_PROPERTIES_FILENAME,
								new ByteArrayInputStream(myadminProjectOutputStream.toString().getBytes()), monitor);

						addProjectNature(project, "org.eclipse.xtext.ui.shared.xtextNature", monitor);

						classpathEntries
								.add(JavaCore.newLibraryEntry(project.getFile(String.format("build/%s.%s-mobile-client-gen.jar", organization, projectName))
										.getFullPath(), project.getFile(String.format("build/%s.%s-mobile-client-gen-source.jar", organization, projectName))
										.getFullPath(), null, true));

						classpathEntries.add(JavaCore.newLibraryEntry(
								project.getFile(String.format("build/%s.%s-client-base-gen.jar", organization, projectName)).getFullPath(),
								project.getFile(String.format("build/%s.%s-client-base-gen-source.jar", organization, projectName)).getFullPath(), null, true));

						classpathEntries.add(JavaCore.newLibraryEntry(
								project.getFile(String.format("build/%s.%s-web-client-gen.jar", organization, projectName)).getFullPath(),
								project.getFile(String.format("build/%s.%s-web-client-gen-source.jar", organization, projectName)).getFullPath(), null, true));

						classpathEntries.add(JavaCore.newLibraryEntry(project.getFile(String.format("build/%s.%s-server-gen.jar", organization, projectName))
								.getFullPath(), project.getFile(String.format("build/%s.%s-server-gen-source.jar", organization, projectName)).getFullPath(),
								null, true));

						classpathEntries.add(JavaCore.newLibraryEntry(project.getFile(String.format("build/%s.%s-xml-gen.jar", organization, projectName))
								.getFullPath(), project.getFile(String.format("build/%s.%s-xml-gen-source.jar", organization, projectName)).getFullPath(),
								null, true));

						classpathEntries.add(JavaCore.newLibraryEntry(project.getFile(String.format("build/%s.%s.generator.jar", organization, projectName))
								.getFullPath(), project.getFile(String.format("build/%s.%s.generator-source.jar", organization, projectName)).getFullPath(),
								null, true));

						break;

					default:
						break;
				}

				// add default vm
				classpathEntries.addAll(Arrays.asList(PreferenceConstants.getDefaultJRELibrary()));

				javaProject.setRawClasspath(classpathEntries.toArray(new IClasspathEntry[0]), monitor);
			}

			// build project
			final IProject buildProject = this.projects.get(Constants.PROJECT_NAME_POSTFIXES.BUILD);

			// ant bootstrap files
			IFolder baseAntFolder = buildProject.getFolder(Constants.BASE_ANT_PATH);
			baseAntFolder.create(true, true, monitor);
			final IFolder myadminAntFolder = baseAntFolder.getFolder(Constants.MYADMIN_ANT_PATH);
			myadminAntFolder.create(true, true, monitor);
			write(myadminAntFolder, Constants.PROJECT_BOOTSTRAP_ANT_FILENAME, openProjectBootstrapAnt(), monitor);

			// version properties
			Properties versionProperties = new Properties();
			versionProperties.setProperty("module.version.major", "0");
			versionProperties.setProperty("module.version.minor", "0");
			versionProperties.setProperty("module.version.micro", "1");
			OutputStream versionOutputStream = new ByteArrayOutputStream();
			versionProperties.store(versionOutputStream, null);
			write(buildProject, Constants.VERSION_PROPERTIES_FILENAME, new ByteArrayInputStream(versionOutputStream.toString().getBytes()), monitor);

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

			write(buildProject, Constants.MYADMIN_PROPERTIES_FILENAME, new ByteArrayInputStream(myadminOutputStream.toString().getBytes()), monitor);

			Job job = new Job(Messages.InitializingProjects)
			{
				@Override
				protected IStatus run(IProgressMonitor monitor)
				{
					try
					{
						runAnt(myadminAntFolder.getFile(Constants.PROJECT_BOOTSTRAP_ANT_FILENAME), monitor);
						refreshProjects(monitor);

						runAnt(buildProject.getFile("build.xml"), monitor);
						refreshProjects(monitor);

						for (Constants.PROJECT_NAME_POSTFIXES projectNamePostfix : Constants.PROJECT_NAME_POSTFIXES.values())
						{
							IProject project = getProject(organization, projectName, projectNamePostfix);
							IJavaProject javaProject = JavaCore.create(project);

							// add ivy container & nature
							addProjectNature(project, IvyNature.IVY_NATURE, monitor);

							IvyClasspathContainerConfiguration conf = new IvyClasspathContainerConfiguration(javaProject, "ivy.xml", true);
							conf.setConfs(Collections.singletonList("runtime"));

							IPath path = IvyClasspathContainerConfAdapter.getPath(conf);
							IClasspathAttribute[] atts = conf.getAttributes();

							List<IClasspathEntry> classpathEntries = new ArrayList<IClasspathEntry>();
							classpathEntries.addAll(Arrays.asList(javaProject.getRawClasspath()));

							IClasspathEntry entry = JavaCore.newContainerEntry(path, null, atts, false);
							classpathEntries.add(entry);

							javaProject.setRawClasspath(classpathEntries.toArray(new IClasspathEntry[0]), monitor);

							refreshProjects(monitor);
						}
					}
					catch (Exception coreException)
					{
						return Status.CANCEL_STATUS;
					}

					return Status.OK_STATUS;
				}

			};
			job.schedule();

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	private String getProjectName(String organization, String projectName, Constants.PROJECT_NAME_POSTFIXES projectNamePostfix)
	{
		return String.format("%s.%s.%s", organization, projectName, projectNamePostfix.toString());
	}

	private void runAnt(IFile antFile, IProgressMonitor monitor) throws Exception
	{

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		String antFileName = antFile.getFullPath().toString();
		File fullAntFile = new File(workspace.getRoot().getLocation().toFile(), antFileName);

		ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType lcType = launchManager.getLaunchConfigurationType(IAntLaunchConstants.ID_ANT_LAUNCH_CONFIGURATION_TYPE);
		launchManager.addLaunchListener(new ILaunchesListener2()
		{

			@Override
			public void launchesRemoved(ILaunch[] launches)
			{
			}

			@Override
			public void launchesChanged(ILaunch[] launches)
			{
			}

			@Override
			public void launchesAdded(ILaunch[] launches)
			{
			}

			@Override
			public void launchesTerminated(ILaunch[] launches)
			{
				countDownLatch.countDown();
			}
		});

		String name = launchManager.generateLaunchConfigurationName(Messages.RunAnt);
		ILaunchConfigurationWorkingCopy wc = lcType.newInstance(null, name);
		wc.setAttribute(ILaunchManager.ATTR_PRIVATE, true);
		wc.setAttribute(IExternalToolConstants.ATTR_LOCATION, fullAntFile.toString());
		wc.launch(ILaunchManager.RUN_MODE, monitor);
		wc.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_ARGUMENTS, "-Xmx512M -XX:MaxPermSize=128M");

		countDownLatch.await();
	}

	private void refreshProjects(IProgressMonitor monitor) throws CoreException
	{
		for (IProject project : this.projects.values())
		{
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		}
	}

	private void setProjectReferences(IProject project, IProject... projects)
	{
		try
		{
			IProjectDescription projectDescription = project.getDescription();
			projectDescription.setReferencedProjects(projects);

			project.setDescription(projectDescription, null);
		}
		catch (CoreException e)
		{
			throw new RuntimeException(e);
		}
	}

	private InputStream openProjectBootstrapAnt()
	{
		return getClass().getResourceAsStream(String.format("%s/%s", Constants.TEMPLATES_PATH, Constants.PROJECT_BOOTSTRAP_ANT_FILENAME));
	}

	private void write(IContainer container, String fileName, InputStream stream, IProgressMonitor monitor)
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

	private boolean validate(boolean isFinalStep)
	{
		return this.newProjectWizardPage1.validate(isFinalStep);
	}

	@Override
	public boolean performFinish()
	{
		if (validate(true))
		{
			final String organization = this.newProjectWizardPage1.getOrganizationName();
			final String module = this.newProjectWizardPage1.getProject();

			IWorkspaceRunnable op = new IWorkspaceRunnable()
			{
				@Override
				public void run(IProgressMonitor monitor) throws CoreException, OperationCanceledException
				{
					try
					{
						createProjects(organization, module, monitor);
					}
					catch (RuntimeException e)
					{
						throw new OperationCanceledException(e.getMessage());
					}
				}
			};

			try
			{
				ISchedulingRule rule = null;

				Job job = Job.getJobManager().currentJob();
				if (job != null)
					rule = job.getRule();

				IRunnableWithProgress runnable = null;

				if (rule != null)
					runnable = new WorkbenchRunnableAdapter(op, rule, true);
				else
					runnable = new WorkbenchRunnableAdapter(op, ResourcesPlugin.getWorkspace().getRoot());

				getContainer().run(true, true, runnable);
			}
			catch (Exception e)
			{
				IStatus status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.NewProjectErrorTitle, e);
				ErrorDialog.openError(this.newProjectWizardPage1.getShell(), Messages.NewProjectErrorTitle, Messages.NewProjectErrorMessage, status);

			}

			return true;
		}
		else
		{
			return false;
		}
	}
}
