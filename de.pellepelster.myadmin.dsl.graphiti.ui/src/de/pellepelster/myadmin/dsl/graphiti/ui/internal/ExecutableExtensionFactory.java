package de.pellepelster.myadmin.dsl.graphiti.ui.internal;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import de.pellepelster.myadmin.dsl.graphiti.ui.Activator;

public class ExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory
{

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Bundle getBundle()
	{
		return Activator.getDefault().getBundle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Injector getInjector()
	{
		return Activator.getDefault().getInjector();
	}

}
