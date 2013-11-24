package de.pellepelster.myadmin.ui.internal;

import java.io.File;
import java.util.UUID;
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import de.pellepelster.myadmin.ui.Messages;

public class LaunchAntInExternalVM
{
	private static final String ATTR_WORKING_DIRECTORY = "org.eclipse.jdt.launching.WORKING_DIRECTORY";

	private static final String ATTR_LAUNCH_IN_BACKGROUND = "org.eclipse.debug.ui.ATTR_LAUNCH_IN_BACKGROUND";

	private static final String ATTR_CAPTURE_OUTPUT = "org.eclipse.ui.externaltools.ATTR_CAPTURE_OUTPUT";

	private static final String ATTR_SHOW_CONSOLE = "org.eclipse.ui.externaltools.ATTR_SHOW_CONSOLE";

	private static final String MAIN_TYPE_NAME = "org.eclipse.ant.internal.ui.antsupport.InternalAntRunner";

	private static final String REMOTE_ANT_PROCESS_FACTORY_ID = "org.eclipse.ant.ui.remoteAntProcessFactory";

	public static void launchAntInExternalVM(IFile buildFile, IProgressMonitor monitor, boolean captureOutput, String targets) throws CoreException,
			InterruptedException
	{
		final String launchUUID = UUID.randomUUID().toString();

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
				for (ILaunch launch : launches)
				{
					if (launchUUID.equals(launch.getLaunchConfiguration().getName()))
					{
						countDownLatch.countDown();
					}
				}
			}
		});

		workingCopy = LaunchAntInExternalVM.createDefaultLaunchConfiguration(buildFile, captureOutput, targets, launchUUID);
		ILaunch launch = workingCopy.launch(ILaunchManager.RUN_MODE, new SubProgressMonitor(monitor, 1));
		if (!captureOutput)
		{
			ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
			manager.removeLaunch(launch);
		}

		countDownLatch.await();

	}

	private static ILaunchConfigurationWorkingCopy createDefaultLaunchConfiguration(IFile buildFile, boolean captureOutput, String targets, String launchUUID)
			throws CoreException
	{
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		ILaunchConfigurationType type = manager.getLaunchConfigurationType(IAntLaunchConstants.ID_ANT_LAUNCH_CONFIGURATION_TYPE);

		ILaunchConfigurationWorkingCopy workingCopy = type.newInstance(null, launchUUID);

		String antFileName = buildFile.getFullPath().toString();
		File fullAntFile = new File(workspace.getRoot().getLocation().toFile(), antFileName);
		workingCopy.setAttribute(IExternalToolConstants.ATTR_LOCATION, fullAntFile.toString());

		workingCopy.setAttribute(ATTR_WORKING_DIRECTORY, buildFile.getProject().getLocation().toOSString());
		workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, buildFile.getProject().getName());
		// workingCopy.setAttribute(IAntLaunchConstants.ATTR_ANT_TARGETS,
		// targets);

		workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH_PROVIDER, "org.eclipse.ant.ui.AntClasspathProvider"); //$NON-NLS-1$
		IVMInstall jdk = JavaRuntime.getDefaultVMInstall();

		if (jdk == null)
		{
			MessageDialog.openError(Display.getDefault().getActiveShell(), Messages.NoJdkFoundTitle, Messages.NoJdkFoundMessage);
		}
		else
		{
			String vmName = jdk.getName();
			String vmTypeID = jdk.getVMInstallType().getId();
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_INSTALL_NAME, vmName);
			workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_VM_INSTALL_TYPE, vmTypeID);
		}

		workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, MAIN_TYPE_NAME);
		workingCopy.setAttribute(DebugPlugin.ATTR_PROCESS_FACTORY_ID, REMOTE_ANT_PROCESS_FACTORY_ID);
		workingCopy.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROGRAM_ARGUMENTS, (String) null);

		workingCopy.setAttribute(ATTR_LAUNCH_IN_BACKGROUND, false);
		if (captureOutput)
		{
			workingCopy.setAttribute(ATTR_SHOW_CONSOLE, true);
			workingCopy.setAttribute(ATTR_CAPTURE_OUTPUT, true);
		}
		else
		{
			workingCopy.setAttribute(ATTR_SHOW_CONSOLE, false);
			workingCopy.setAttribute(ATTR_CAPTURE_OUTPUT, false);
		}
		workingCopy.setAttribute(ILaunchManager.ATTR_PRIVATE, true);

		return workingCopy;
	}
}