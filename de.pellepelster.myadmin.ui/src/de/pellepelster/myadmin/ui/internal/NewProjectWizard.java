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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ivyde.eclipse.IvyNature;
import org.apache.ivyde.eclipse.cpcontainer.IvyClasspathContainerConfAdapter;
import org.apache.ivyde.eclipse.cpcontainer.IvyClasspathContainerConfiguration;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.internal.ui.actions.WorkbenchRunnableAdapter;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import de.pellepelster.myadmin.ui.Constants;
import de.pellepelster.myadmin.ui.Constants.PROJECT_NAME_POSTFIXES;
import de.pellepelster.myadmin.ui.Messages;
import de.pellepelster.myadmin.ui.util.MyAdminProjectUtil;

public class NewProjectWizard extends Wizard implements INewWizard
{

	private final Map<Constants.PROJECT_NAME_POSTFIXES, IProject> projects = new HashMap<Constants.PROJECT_NAME_POSTFIXES, IProject>();

	private final Map<Constants.PROJECT_NAME_POSTFIXES, IJavaProject> javaProjects = new HashMap<Constants.PROJECT_NAME_POSTFIXES, IJavaProject>();

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
				this.javaProjects.put(projectNamePostfix, javaProject);

				Properties myadminProjectProperties = new Properties();
				myadminProjectProperties.setProperty(Constants.BUILD_PROJECT_KEY,
						String.format("%s.%s.%s", organization, projectName.toLowerCase(), Constants.PROJECT_NAME_POSTFIXES.BUILD.toString()));

				OutputStream myadminProjectOutputStream = new ByteArrayOutputStream();
				myadminProjectProperties.store(myadminProjectOutputStream, null);

				switch (projectNamePostfix)
				{
					case CLIENT:
						break;
					case GENERATOR:

						MyAdminProjectUtil.writeFileToContainer(project, Constants.MYADMIN_PROJECT_PROPERTIES_FILENAME, new ByteArrayInputStream(
								myadminProjectOutputStream.toString().getBytes()), monitor);

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
			BuildProjectNature.initProject(buildProject, organization, projectName, monitor);
			addProjectNature(buildProject, BuildProjectNature.NATURE_ID, monitor);

			// generator project
			IJavaProject generatorProject = this.javaProjects.get(PROJECT_NAME_POSTFIXES.GENERATOR);

			// client project
			IJavaProject clientProject = this.javaProjects.get(PROJECT_NAME_POSTFIXES.CLIENT);
			setProjectReferences(clientProject, monitor, generatorProject);

			// client test project
			IJavaProject clientTestProject = this.javaProjects.get(PROJECT_NAME_POSTFIXES.CLIENT_TEST);
			setProjectReferences(clientTestProject, monitor, clientProject);
			setProjectReferences(clientTestProject, monitor, generatorProject);

			// server project
			IJavaProject serverProject = this.javaProjects.get(PROJECT_NAME_POSTFIXES.SERVER);
			setProjectReferences(serverProject, monitor, generatorProject);

			// client test project
			IJavaProject serverTestProject = this.javaProjects.get(PROJECT_NAME_POSTFIXES.SERVER_TEST);
			setProjectReferences(serverTestProject, monitor, serverProject);
			setProjectReferences(serverTestProject, monitor, generatorProject);

			final Job bootstrapJob = BuildProjectNature.getBootstrapJob(buildProject);

			Job.getJobManager().addJobChangeListener(new JobChangeAdapter()
			{
				@Override
				public void done(IJobChangeEvent event)
				{
					if (event.getJob() == bootstrapJob)
					{
						IVMInstall jdk = JDKHelper.getJDK();

						if (jdk == null)
						{
							PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable()
							{
								@Override
								public void run()
								{
									MessageDialog.openError(Display.getDefault().getActiveShell(), Messages.InitialBuildNoJdkErrorTitle,
											Messages.InitialBuildNoJdkErrorMessage);
								}
							});
						}
						else
						{
							getInitialBuildJob(organization, projectName, buildProject).schedule();
						}

					}
				}
			});

			bootstrapJob.schedule();
		}
		catch (Exception e)
		{
			Logger.error(e);
			throw new RuntimeException(e);
		}

	}

	protected Job getInitialBuildJob(final String organization, final String projectName, final IProject buildProject)
	{
		Job job = new Job(Messages.InitialBuild)
		{
			@Override
			protected IStatus run(IProgressMonitor monitor)
			{
				try
				{
					refreshProjects(monitor);

					LaunchAntInExternalVM.launchAntInExternalVM(buildProject.getFile("build.xml"), monitor, true, "", JDKHelper.getJDK());

					refreshProjects(monitor);

					for (Constants.PROJECT_NAME_POSTFIXES projectNamePostfix : Constants.PROJECT_NAME_POSTFIXES.values())
					{
						IProject project = getProject(organization, projectName, projectNamePostfix);
						IJavaProject javaProject = JavaCore.create(project);

						// add ivy container & nature
						addProjectNature(project, IvyNature.IVY_NATURE, monitor);

						IvyClasspathContainerConfiguration conf = new IvyClasspathContainerConfiguration(javaProject, "ivy.xml", true);
						switch (projectNamePostfix)
						{
							case CLIENT_TEST:
							case SERVER_TEST:
								conf.setConfs(Collections.singletonList("test"));
								break;

							default:
								conf.setConfs(Collections.singletonList("default"));
								break;
						}

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

	private String getProjectName(String organization, String projectName, Constants.PROJECT_NAME_POSTFIXES projectNamePostfix)
	{
		return String.format("%s.%s.%s", organization, projectName, projectNamePostfix.toString());
	}

	private void refreshProjects(IProgressMonitor monitor) throws CoreException
	{
		for (IProject project : this.projects.values())
		{
			project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		}
	}

	private void setProjectReferences(IJavaProject project, IProgressMonitor monitor, IJavaProject... projectReferences)
	{
		try
		{
			List<IClasspathEntry> classpathEntries = new ArrayList<IClasspathEntry>(Arrays.asList(project.getRawClasspath()));

			for (IJavaProject projectReference : projectReferences)
			{
				IClasspathEntry classpathEntry = JavaCore.newProjectEntry(projectReference.getProject().getFullPath());
				classpathEntries.add(classpathEntry);
			}

			project.setRawClasspath(classpathEntries.toArray(new IClasspathEntry[] {}), monitor);
		}
		catch (CoreException e)
		{
			Logger.error(e);
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
				Logger.error(e);
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
