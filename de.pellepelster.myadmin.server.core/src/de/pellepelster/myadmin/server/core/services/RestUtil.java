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
package de.pellepelster.myadmin.server.core.services;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.beanutils.MethodUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import de.pellepelster.myadmin.client.base.rest.ParameterOrder;

/**
 * Rest service handling utils
 * 
 * @author pelle
 * 
 */
public final class RestUtil
{

	public static class ResultWrapper
	{

		public Object result;

		public ResultWrapper(Object result)
		{
			super();
			this.result = result;
		}
	}

	/**
	 * Invoke a remote rest service
	 * 
	 * @param serviceUrl
	 *            service url
	 * @param parameters
	 *            parameter wrapper
	 * @return
	 */
	public static Object invokeRemoteRestService(String serviceUrl, Object parameters)
	{
		RestTemplate restTemplate = new RestTemplate();

		try
		{

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enableDefaultTyping();

			String jsonParameters = objectMapper.writeValueAsString(parameters);
			String result = restTemplate.postForObject(serviceUrl, jsonParameters, String.class);

			return objectMapper.readValue(result.getBytes(), ResultWrapper.class);

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

	/**
	 * Invokes a service method on a service with the given json encoded
	 * parameters
	 * 
	 * @param service
	 *            the service
	 * @param methodName
	 *            name of the method to invoke
	 * @param jsonParameters
	 *            json encoded parameters
	 * @param parametersClass
	 *            class of the parameterwrapper to decode from json
	 * @return the json encoded result of the service call (wrapped in a
	 *         {@link ResultWrapper}
	 */
	public static <T> String invokeServiceMethod(Object service, String methodName, String jsonParameters, Class<T> parametersClass)
	{
		try
		{
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.enableDefaultTyping();
			objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			Field[] fields = parametersClass.getFields();
			Class<?>[] parameterTypes = new Class[fields.length];
			Object[] parameters = new Object[fields.length];

			if (fields.length > 0)
			{

				T parameterWrapper = objectMapper.readValue(jsonParameters.getBytes(), parametersClass);

				// rely on correctly generated ParameterOrder annotations
				// starting at 1
				for (Field field : fields)
				{
					ParameterOrder parameterOrder = field.getAnnotation(ParameterOrder.class);

					Object parameter = field.get(parameterWrapper);
					parameterTypes[parameterOrder.order() - 1] = parameter.getClass();
					parameters[parameterOrder.order() - 1] = parameter;
				}
			}

			Method methodToInvoke = null;

			for (Method method : service.getClass().getMethods())
			{
				if (method.getName().equals(methodName))
				{
					methodToInvoke = method;
				}
			}

			Object result = methodToInvoke.invoke(service, parameters);

			return objectMapper.writeValueAsString(new ResultWrapper(result));

		}
		catch (Exception e)
		{
			throw new RuntimeException(String.format("error invoking method '%s' on service '%s'", methodName, service.getClass()), e);
		}
	}

	public static String invokeServiceMethod(Object service, String methodName, Object[] parameters)
	{
		try
		{
			Object result = MethodUtils.invokeExactMethod(service, methodName, parameters);

			if (result != null)
			{
				return simpleTypeToJson(result);
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(String.format("error invoking method '%s' on service '%s'", methodName, service.getClass()), e);
		}

		return null;

	}

	/**
	 * Private constructor
	 */
	private RestUtil()
	{
	}

	public static String simpleTypeArrayToJson(Object[] objectArray)
	{

		StringBuilder sb = new StringBuilder();

		String delimiter = "";
		sb.append("[");

		for (Object arrayItem : objectArray)
		{

			sb.append(delimiter);
			sb.append("\"");
			if (arrayItem != null)
			{
				sb.append(arrayItem.toString());
			}
			sb.append("\"");
			delimiter = ", ";
		}
		sb.append("]");

		return sb.toString();

	}

	public static String simpleTypeToJson(Object object)
	{

		if (object == null)
		{
			return "";
		}

		if (object instanceof Object[])
		{
			Object[] array = (Object[]) object;
			return simpleTypeArrayToJson(array).toString();
		}

		if (object instanceof List)
		{
			List<?> list = (List) object;
			return simpleTypeArrayToJson(list.toArray()).toString();
		}

		return object.toString();
	}
}
