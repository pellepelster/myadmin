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

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;

/**
 * Bundle convenience methods.
 */
public class BundleUtil
{

	private static String NL_TAG = "$nl$/"; //$NON-NLS-1$

	/**
	 * Utility method to validate the state of a bundle. Log invalid bundles to
	 * log file.
	 */
	public static boolean bundleHasValidState(Bundle bundle)
	{
		if (bundle == null || bundle.getState() == Bundle.UNINSTALLED || bundle.getState() == Bundle.INSTALLED)
		{

			if (bundle == null)
			{
				Logger.error("Universal Welcome tried accessing a NULL bundle.", null); //$NON-NLS-1$
			}
			else
			{
				String msg = String.format("tried accessing bundle '%s' with status '%s'", getBundleHeader( //$NON-NLS-1$
						bundle, Constants.BUNDLE_NAME), String.valueOf(bundle.getState()));
				Logger.error(msg, null);
			}
			return false;
		}

		return true;
	}

	public static Bundle getBundleFromConfigurationElement(IConfigurationElement cfg)
	{
		return Platform.getBundle(cfg.getNamespaceIdentifier());
	}

	/**
	 * Retrieves the given key from the bundle header.
	 * 
	 * @param bundle
	 * @param key
	 * @return
	 */
	public static String getBundleHeader(Bundle bundle, String key)
	{
		return (String) bundle.getHeaders().get(key);
	}

	/** ********************* Used by HTML generator ****************** */
	/**
	 * Get the absolute path of the given bundle, in the form
	 * file:/path_to_plugin
	 * 
	 * @param bundle
	 * @return
	 */
	public static String getResolvedBundleLocation(Bundle bundle)
	{
		try
		{
			URL bundleLocation = bundle.getEntry(""); //$NON-NLS-1$
			if (bundleLocation == null)
			{
				return null;
			}
			/*
			 * bundleLocation = FileLocator.toFileURL(bundleLocation); return
			 * bundleLocation.toExternalForm();
			 */
			return toExternalForm(bundleLocation);
		}
		catch (IllegalStateException e)
		{
			Logger.error("Failed to access bundle: " //$NON-NLS-1$
					+ bundle.getSymbolicName(), e);
			return null;
		} /*
		 * catch (IOException e) {
		 * Log.error("Failed to resolve URL path for bundle: " //$NON-NLS-1$ +
		 * bundle.getSymbolicName(), e); return null; }
		 */
	}

	/**
	 * Get the absolute path of the bundle with id <code>bundleId. If
	 * no such bundle is found, return null.
	 * 
	 * @param bundleId
	 * @return
	 */
	public static String getResolvedBundleLocation(String bundleId)
	{
		Bundle bundle = Platform.getBundle(bundleId);
		if (bundle == null)
		{
			return null;
		}
		return getResolvedBundleLocation(bundle);
	}

	/**
	 * Shorthand util method.
	 * 
	 * @param resource
	 * @return
	 */
	public static String getResolvedResourceLocation(String resource, Bundle bundle)
	{
		return getResolvedResourceLocation(resource, bundle, true);
	}

	public static String getResolvedResourceLocation(String resource, Bundle bundle, boolean forceNLResolve)
	{
		// quick exits.
		if (resource == null)
		{
			return null;
		}

		if (bundle == null || !bundleHasValidState(bundle))
		{
			return resource;
		}

		URL localLocation = null;
		try
		{
			// we need to resolve this URL.
			String copyResource = resource;
			if (forceNLResolve && !copyResource.startsWith(NL_TAG))
			{
				if (copyResource.startsWith("/") //$NON-NLS-1$
						|| copyResource.startsWith("\\"))
				{
					copyResource = resource.substring(1);
				}
				copyResource = NL_TAG + copyResource;
			}
			IPath resourcePath = new Path(copyResource);
			localLocation = FileLocator.find(bundle, resourcePath, null);
			if (localLocation == null)
			{
				// localLocation can be null if the passed resource could not
				// be found relative to the plugin. log fact, return resource,
				// as is.
				String msg = String.format("could not find resource '%s' in '%s'", resource, getBundleHeader(bundle, //$NON-NLS-1$
						Constants.BUNDLE_NAME));
				Logger.warn(msg);

				return resource;
			}
			/*
			 * localLocation = FileLocator.toFileURL(localLocation); return
			 * localLocation.toExternalForm();
			 */
			return toExternalForm(localLocation);
		}
		catch (Exception e)
		{
			String msg = String.format("failed to load resource '%s' in '%s'", resource, getBundleHeader(bundle, //$NON-NLS-1$
					Constants.BUNDLE_NAME));
			Logger.error(msg, e);

			return resource;
		}
	}

	/**
	 * Returns the fully qualified location of the passed resource string from
	 * the passed plugin id. If the file could not be loaded from the plugin,
	 * the resource is returned as is.
	 * 
	 * @param resource
	 * @return
	 */
	public static String getResolvedResourceLocation(String resource, String pluginId)
	{
		Bundle bundle = Platform.getBundle(pluginId);
		return getResolvedResourceLocation(resource, bundle, true);
	}

	public static String getResolvedResourceLocation(String base, String resource, Bundle bundle)
	{
		// quick exits.
		if (resource == null)
		{
			return null;
		}

		String fullResource = new Path(base).append(resource).toString();
		String resolvedResource = getResolvedResourceLocation(fullResource, bundle, true);

		if (resolvedResource.equals(fullResource))
		{
			// return resource as is when the resource does not exist.
			return resource;
		}
		return resolvedResource;
	}

	/** *** used by Intro parser ***** */
	/*
	 * Util method to return an URL to a plugin relative resource.
	 */
	public static URL getResourceAsURL(String resource, String pluginId)
	{
		Bundle bundle = Platform.getBundle(pluginId);
		URL localLocation = FileLocator.find(bundle, new Path(resource), null);
		return localLocation;
	}

	/**
	 * Get the resourcelocation, but do not force an $nl$ on it.
	 * 
	 * @param resource
	 * @param element
	 * @return
	 */
	public static String getResourceLocation(String resource, IConfigurationElement element)
	{
		Bundle bundle = getBundleFromConfigurationElement(element);
		return getResolvedResourceLocation(resource, bundle, false);
	}

	private static String toExternalForm(URL localURL)
	{
		try
		{
			localURL = FileLocator.toFileURL(localURL);
			String result = localURL.toExternalForm();
			if (result.startsWith("file:/")) { //$NON-NLS-1$
				if (result.startsWith("file:///") == false) { //$NON-NLS-1$
					result = "file:///" + result.substring(6); //$NON-NLS-1$
				}
			}
			return result;
		}
		catch (IOException e)
		{
			String msg = "Failed to resolve URL: " //$NON-NLS-1$
					+ localURL.toString();
			Logger.error(msg, e);
			return localURL.toString();
		}
	}

}
