package de.pellepelster.myadmin.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import de.pellepelster.myadmin.dsl.MyAdminDslStandaloneSetup;
import de.pellepelster.myadmin.dsl.myAdminDsl.Model;
import de.pellepelster.myadmin.tools.dictionary.ToolUtils;

public class SpringModelUtils
{
	public final static Logger LOG = Logger.getLogger(SpringModelUtils.class);

	public static Model getModel(String locationPattern)
	{
		return getModel(SpringModelUtils.getResource(locationPattern), Collections.<Resource> emptyList());
	}

	public static Model getModel(Resource modelResource)
	{
		return getModel(modelResource, Collections.<Resource> emptyList());
	}

	public static Model getModel(Resource modelResource, List<Resource> additionalModelResources)
	{
		try
		{
			MyAdminDslStandaloneSetup.doSetup();

			ResourceSet resourceSet = new ResourceSetImpl();

			for (Resource additionalModelResource : additionalModelResources)
			{
				org.eclipse.emf.ecore.resource.Resource myAdminResource = resourceSet
						.createResource(URI.createURI(additionalModelResource.getURI().toString()));
				myAdminResource.load(additionalModelResource.getInputStream(), resourceSet.getLoadOptions());
			}

			ToolUtils.logInfo(LOG, String.format("loading model file '%s'", modelResource.getURI().toString()), 0);
			org.eclipse.emf.ecore.resource.Resource resource = resourceSet.getResource(URI.createURI(modelResource.getURI().toString()), true);

			return (Model) resource.getContents().get(0);
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	private static PathMatchingResourcePatternResolver PATH_MATCHING_RESOURCE_PATTERN_RESOLVER = new PathMatchingResourcePatternResolver();

	public static List<Resource> getResources(String locationPattern)
	{
		List<Resource> resources = new ArrayList<Resource>();

		try
		{
			for (Resource resource : PATH_MATCHING_RESOURCE_PATTERN_RESOLVER.getResources(locationPattern))
			{
				resources.add(resource);
			}
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return resources;
	}

	public static Resource getResource(String locationPattern)
	{
		List<Resource> resources = getResources(locationPattern);

		if (resources.size() == 1)
		{
			return resources.get(0);
		}
		else
		{
			throw new RuntimeException(String.format("location pattern %d matched %d resources but one was expected ", locationPattern, resources.size()));
		}
	}

}
