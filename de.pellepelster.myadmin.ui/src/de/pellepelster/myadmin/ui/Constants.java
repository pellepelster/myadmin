package de.pellepelster.myadmin.ui;

public class Constants
{
	public static final String PROJECT_NAME_KEY = "project.name";

	public static final String ORGANISATION_NAME_KEY = "organisation.name";

	public static final String TEMPLATES_PATH = "templates";

	public static final String PROJECT_BOOTSTRAP_ANT_FILENAME = "MyAdminProjectBuildBootstrap.xml";

	public static final String VERSION_PROPERTIES_FILENAME = "version.properties";

	public static final String MYADMIN_PROPERTIES_FILENAME = "myadmin.properties";

	public static final String MYADMIN_PROJECT_PROPERTIES_FILENAME = "myadmin_project.properties";

	public static final String BASE_ANT_PATH = "ant";

	public static final String MYADMIN_ANT_PATH = "myadmin";

	public static final String BUILD_PROJECT_KEY = "build.project";

	public enum PROJECT_NAME_POSTFIXES
	{
		BUILD
		{
			@Override
			public String toString()
			{
				return "build";
			}
		},
		SERVER
		{
			@Override
			public String toString()
			{
				return "server";
			}
		},
		SERVER_TEST
		{
			@Override
			public String toString()
			{
				return "server.test";
			}
		},
		CLIENT
		{
			@Override
			public String toString()
			{
				return "client";
			}
		},
		CLIENT_TEST
		{
			@Override
			public String toString()
			{
				return "client.test";
			}
		},
		DEPLOY
		{
			@Override
			public String toString()
			{
				return "deploy";
			}
		},
		GENERATOR
		{
			@Override
			public String toString()
			{
				return "generator";
			}
		}
	};
}
