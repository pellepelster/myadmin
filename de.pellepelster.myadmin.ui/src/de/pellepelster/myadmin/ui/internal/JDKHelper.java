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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.IVMInstallType;
import org.eclipse.jdt.launching.JavaRuntime;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class JDKHelper
{

	public static IVMInstall getJDK()
	{
		return Iterables.find(getAllVmInstalls(), new Predicate<IVMInstall>()
		{
			@Override
			public boolean apply(IVMInstall vmInstall)
			{
				return isJDK(vmInstall);
			}
		});
	}

	private static List<IVMInstall> getAllVmInstalls()
	{
		List<IVMInstall> vmInstalls = new ArrayList<IVMInstall>();

		for (IVMInstallType vmInstallType : JavaRuntime.getVMInstallTypes())
		{
			vmInstalls.addAll(Arrays.asList(vmInstallType.getVMInstalls()));
		}

		return vmInstalls;
	}

	private static boolean isJDK(IVMInstall vmInstall)
	{
		File javaHome = vmInstall.getInstallLocation();
		if (javaHome != null && javaHome.exists())
		{
			File toolsJar = new File(new File(javaHome, "lib"), "tools.jar");
			return toolsJar.exists();
		}

		return false;
	}

}