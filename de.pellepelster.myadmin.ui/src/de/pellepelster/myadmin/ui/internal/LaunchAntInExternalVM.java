package de.pellepelster.myadmin.ui.internal;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import org.eclipse.ant.launching.IAntLaunchConstants;
import org.eclipse.core.externaltools.internal.IExternalToolConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;

public class LaunchAntInExternalVM
{
	private static final String MAIN_TYPE_NAME = "org.eclipse.ant.internal.ui.antsupport.InternalAntRunner";
	private static final String REMOTE_ANT_PROCESS_FACTORY_ID = "org.eclipse.ant.ui.remoteAntProcessFactory";

	public static void launchAntInExternalVM(IFile buildFile, IProgressMonitor monitor, boolean captureOutput, String targets) throws CoreException,
			InterruptedException
	{
		ILaunchConfigurationWorkingCopy workingCopy = null;
		final CountDownLatch countDownLatch = new CountDownLatch(1);

		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(new ILaunchesListener2()
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

		workingCopy = LaunchAntInExternalVM.createDefaultLaunchConfiguration(buildFile, captureOutput, targets);
		ILaunch launch = workingCopy.launch(ILaunchManager.RUN_MODE, new SubProgressMonitor(monitor, 1));
		if (!captureOutput)
		{
			ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
			manager.removeLaunch(launch);
		}

		countDownLatch.await();
	}

	private static ILaunchConfigurationWorkingCopy createDefaultLaunchConfiguration(IFile buildFile, boolean captureOutput, String targets)
			throws CoreException
	{
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		ILaunchConfigurationType type = manager.getLaunchConfigurationType(IAntLaunchConstants.ID_ANT_LAUNCH_CONFIGURATION_TYPE);

		ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, buildFile.getProject().getName());

		String antFileName = buildFile.getFullPath().toString();
		File fullAntFile = new File(workspace.getRoot().getLocation().toFile(), antFileName);
		workingCopy.setAttribute(IExternalToolConstants.ATTR_LOCATION, fullAntFile.toString());

		workingCopy.setAttribute("org.eclipse.jdt.launching.WORKING_DIRECTORY", buildFile.getProject().getLocation().toOSString());
		workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, buildFile.getProject().getName());
		// workingCopy.setAttribute(IAntLaunchConstants.ATTR_ANT_TARGETS,
		// targets);

		workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH_PROVIDER, "org.eclipse.ant.ui.AntClasspathProvider"); //$NON-NLS-1$
		IVMInstall defaultInstall = null;
		defaultInstall = JavaRuntime.getDefaultVMInstall();

		if (defaultInstall != null)
		{
			String vmName = defaultInstall.getName();
			String vmTypeID = defaultInstall.getVMInstallType().getId();
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_INSTALL_NAME, vmName);
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_INSTALL_TYPE, vmTypeID);
		}

		workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, MAIN_TYPE_NAME);
		workingCopy.setAttribute(DebugPlugin.ATTR_PROCESS_FACTORY_ID, REMOTE_ANT_PROCESS_FACTORY_ID);
		workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, (String) null);

		workingCopy.setAttribute("org.eclipse.debug.ui.ATTR_LAUNCH_IN_BACKGROUND", false);
		if (captureOutput)
		{
			workingCopy.setAttribute("org.eclipse.ui.externaltools.ATTR_SHOW_CONSOLE", true);
			workingCopy.setAttribute("org.eclipse.ui.externaltools.ATTR_CAPTURE_OUTPUT", true);
		}
		else
		{
			workingCopy.setAttribute("org.eclipse.ui.externaltools.ATTR_SHOW_CONSOLE", false);
			workingCopy.setAttribute("org.eclipse.ui.externaltools.ATTR_CAPTURE_OUTPUT", false);
		}
		workingCopy.setAttribute(ILaunchManager.ATTR_PRIVATE, true);

		return workingCopy;
	}

}