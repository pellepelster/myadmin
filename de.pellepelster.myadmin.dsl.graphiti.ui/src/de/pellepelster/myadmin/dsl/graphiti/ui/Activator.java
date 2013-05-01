package de.pellepelster.myadmin.dsl.graphiti.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.util.Modules2;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.pellepelster.myadmin.dsl.graphiti.ui.internal.GraphitiRuntimeModule;
import de.pellepelster.myadmin.dsl.graphiti.ui.internal.MyAdminModule;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin
{
	private Injector injector;

	// The plug-in ID
	public static final String PLUGIN_ID = "de.pellepelster.myadmin.dsl.graphiti.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception
	{
		super.start(context);
		plugin = this;
		this.injector = createInjector();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(BundleContext context) throws Exception
	{
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault()
	{
		return plugin;
	}

	protected Injector createInjector()
	{
		return Guice.createInjector(Modules2.mixin(new GraphitiRuntimeModule(), new MyAdminModule()));
	}

	public final Injector getInjector()
	{
		return this.injector;
	}

	public static final <T> T get(Class<T> type)
	{
		return getDefault().getInjector().getInstance(type);
	}

}
