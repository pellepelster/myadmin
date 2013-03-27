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

import java.io.IOException;
import java.net.URL;

import org.eclipse.ant.core.IAntPropertyValueProvider;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class MyAdminAntPropertyValueProvider implements IAntPropertyValueProvider
{
	private static final String MYADMIN_ARTEFACT_SUFFIX_ANT_PROPERTY_NAME = "myadmin.artefact.suffix";
	private static final String MYADMIN_HOME_LOCATION_ANT_PROPERTY_NAME = "myadmin.home.location";

	/** {@inheritDoc} */
	@Override
	public String getAntPropertyValue(String antPropertyName)
	{
		if (MYADMIN_ARTEFACT_SUFFIX_ANT_PROPERTY_NAME.equals(antPropertyName))
		{
			return VersionInfoProvider.getString(antPropertyName);
		}
		else if (MYADMIN_HOME_LOCATION_ANT_PROPERTY_NAME.equals(antPropertyName))
		{
			Bundle projectLibBundle = Platform.getBundle("de.pellepelster.myadmin.lib.project"); //$NON-NLS-1$
			Path path = new Path("/");
			URL rootURL = FileLocator.find(projectLibBundle, path, null);

			try
			{
				return FileLocator.resolve(rootURL).getPath();
			}
			catch (IOException e)
			{
				Logger.error(e);
			}

		}

		return "<not found>";
	}
}
