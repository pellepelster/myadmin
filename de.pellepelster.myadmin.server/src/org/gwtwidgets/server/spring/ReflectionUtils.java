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
package org.gwtwidgets.server.spring;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Utilities for internal use
 * 
 * @author Dmitri Shestakov, dvshestakov[at]gmail.com
 * @author George Georgovassilis, g.georgovassilis[at]gmail.com
 * 
 */
@SuppressWarnings("rawtypes")
public class ReflectionUtils
{

	/**
	 * Adds elements of an array to a set. The JRE 1.5 does include a similar
	 * method in the Collections class, but that breaks GWT-SL 1.4
	 * compatibility.
	 * 
	 * @param set
	 * @param elements
	 */
	public static void addAll(Set<Class> set, Class[] elements)
	{
		for (Class element : elements)
		{
			set.add(element);
		}
	}

	/**
	 * Return array of all interfaces that are implemented by clazz and extend
	 * {@link RemoteService}.
	 * 
	 * @param clazz
	 * @return Array of interfaces. May be empty but never null.
	 */
	public static Class[] getExposedInterfaces(Class clazz)
	{
		Set<Class> interfaces = getInterfaces(clazz);
		for (Iterator<Class> ite = interfaces.iterator(); ite.hasNext();)
		{
			Class c = ite.next();
			if (!isExposed(c))
			{
				ite.remove();
			}
		}
		return interfaces.toArray(new Class[interfaces.size()]);
	}

	/**
	 * Return all interfaces that are implemented by this class, traversing
	 * super classes and super interfaces.
	 * 
	 * @param c
	 * @return Set of classes. May be empty but not null.
	 */
	public static Set<Class> getInterfaces(Class c)
	{
		Class interfaces[] = c.getInterfaces();
		Set<Class> classes = new HashSet<Class>();
		if (interfaces == null)
		{
			return classes;
		}
		addAll(classes, interfaces);
		for (Class cl : interfaces)
		{
			classes.addAll(getInterfaces(cl));
		}
		return classes;
	}

	/**
	 * Will try to find method in 'serviceInterfaces' and if found, will attempt
	 * to return a method with the same signature from 'service', otherwise an
	 * exception is thrown. If 'serviceInterfaces' is a zero-sized array, the
	 * interface check is omitted and the method is looked up directly on the
	 * object.
	 * 
	 * @param target
	 *            Object to search method on
	 * @param serviceInterfaces
	 *            The requested method must exist on at least one of the
	 *            interfaces
	 * @param method
	 * @return Method on 'service' or else a {@link NoSuchMethodException} is
	 *         thrown
	 */
	@SuppressWarnings("unchecked")
	public static Method getRPCMethod(Object target, Class[] serviceInterfaces, Method method) throws NoSuchMethodException
	{
		if (serviceInterfaces.length == 0)
		{
			return target.getClass().getMethod(method.getName(), method.getParameterTypes());
		}
		for (Class serviceInterface : serviceInterfaces)
		{
			try
			{
				Method template = serviceInterface.getMethod(method.getName(), method.getParameterTypes());
				return target.getClass().getMethod(template.getName(), template.getParameterTypes());
			}
			catch (NoSuchMethodException e)
			{
			}
		}
		throw new NoSuchMethodException();
	}

	private static boolean isExposed(Class c)
	{
		return RemoteService.class.isAssignableFrom(c);
	}

}
