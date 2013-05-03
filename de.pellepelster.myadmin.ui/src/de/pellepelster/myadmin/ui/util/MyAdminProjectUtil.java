package de.pellepelster.myadmin.ui.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;

import de.pellepelster.myadmin.ui.Constants;

public class MyAdminProjectUtil
{

	private static final String FQDN_PROJECT_NAME_REGEX = "([a-zA-Z_][a-zA-Z\\d_]*\\.)+([a-zA-Z][A-Za-zA-Z\\d_]*)";

	private static Pattern FQDN_PROJECT_NAME_PATTERN = Pattern.compile(FQDN_PROJECT_NAME_REGEX);
	
	public static boolean isValidFQDNProjectName(String fqdnProjectName)
	{
		return fqdnProjectName != null && FQDN_PROJECT_NAME_PATTERN.matcher(fqdnProjectName).matches();
	}

	public static String[] splitFQDNProjectName(String fqdnProjectName)
	{
		String[] projectNameSegments = new String[2];

		if (isValidFQDNProjectName(fqdnProjectName))
		{
			projectNameSegments[0] = fqdnProjectName.substring(0, fqdnProjectName.lastIndexOf("."));
			projectNameSegments[1] = fqdnProjectName.substring(fqdnProjectName.lastIndexOf(".")+1);
		}
		
		return projectNameSegments;
	}

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