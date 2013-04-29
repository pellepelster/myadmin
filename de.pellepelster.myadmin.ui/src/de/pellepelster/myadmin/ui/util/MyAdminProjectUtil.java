package de.pellepelster.myadmin.ui.util;

import java.io.InputStream;
import java.util.Properties;

import org.eclipse.core.resources.IProject;

import de.pellepelster.myadmin.ui.Constants;

public class MyAdminProjectUtil
{

	public static final String FQDN_PROJECT_NAME_REGEX = "([a-zA-Z_][a-zA-Z\\d_]*\\.)+[A-Z][a-zA-Z\\d_]*";

	public static Properties getMyAdminProjectProperties(IProject project)
	{
		Properties properties = new Properties();

		try
		{
			InputStream inputStream = project.getFile(Constants.MYADMIN_PROJECT_PROPERTIES_FILENAME).getContents();
			properties.load(inputStream);
		}
		catch (Exception e)
		{
			throw new RuntimeException(String.format("error loading '%s' from '%s'", Constants.MYADMIN_PROJECT_PROPERTIES_FILENAME, project.getName()));
		}

		return properties;
	}

	public static Properties getMyAdminProperties(IProject project)
	{
		String buildProjectName = getBuildProjectName(project);

		Properties properties = new Properties();

		try
		{
			InputStream inputStream = project.getFile(String.format("../%s/%s", buildProjectName, Constants.MYADMIN_PROPERTIES_FILENAME)).getContents();
			properties.load(inputStream);
		}
		catch (Exception e)
		{
			throw new RuntimeException(String.format("error loading '%s' from '%s'", Constants.MYADMIN_PROPERTIES_FILENAME, project.getName()));
		}

		return properties;
	}

	private static String getPropertyOrFail(Properties properties, String key)
	{
		if (properties.containsKey(key))
		{
			return properties.getProperty(key);
		}
		else
		{
			throw new RuntimeException(String.format("error property '%s'", key));
		}
	}

	public static String getOrganisationName(IProject project)
	{
		Properties properties = getMyAdminProperties(project);
		return getPropertyOrFail(properties, Constants.ORGANISATION_NAME_KEY);
	}

	public static String getProjectName(IProject project)
	{
		Properties properties = getMyAdminProperties(project);
		return getPropertyOrFail(properties, Constants.PROJECT_NAME_KEY);
	}

	public static String getBuildProjectName(IProject project)
	{
		Properties properties = getMyAdminProjectProperties(project);
		return getPropertyOrFail(properties, Constants.BUILD_PROJECT_KEY);
	}

}
