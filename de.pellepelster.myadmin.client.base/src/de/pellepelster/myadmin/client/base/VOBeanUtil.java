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
package de.pellepelster.myadmin.client.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.pellepelster.myadmin.client.base.db.vos.IAttributeDescriptor;
import de.pellepelster.myadmin.client.base.db.vos.IBaseVO;
import de.pellepelster.myadmin.client.base.db.vos.UnknownAttributeException;

public class VOBeanUtil
{

	private static class DummyVO implements IBaseVO
	{

		private static final long serialVersionUID = 4325945617228729511L;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private List<? extends IBaseVO> list = new ArrayList();

		public DummyVO(List<? extends IBaseVO> list)
		{
			super();
			this.list = list;
		}

		/** {@inheritDoc} */
		@Override
		public Object cloneVO()
		{
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public Object get(String name)
		{
			if ("list".equals(name))
			{
				return list;
			}
			return null;
		}

		@Override
		public IAttributeDescriptor<?> getAttributeDescriptor(String name)
		{
			return null;
		}

		/** {@inheritDoc} */
		@Override
		public long getId()
		{
			return 0;
		}

		/** {@inheritDoc} */
		@Override
		public long getOid()
		{
			return 0;
		}

		@Override
		public boolean isNew()
		{
			return false;
		}

		/** {@inheritDoc} */
		@Override
		public void set(String name, Object value)
		{
		}

		/** {@inheritDoc} */
		@Override
		public void setId(long id)
		{
		}

		/** {@inheritDoc} */
		@Override
		public void setOid(long oid)
		{
		}

		@Override
		public Map<String, Object> getData()
		{
			return null;
		}

	}

	private static class ListAndQualifierResult
	{
		public enum LIST_QUALIFIER_TYPE
		{
			INDEX, ATTRIBUTES, NEW_ITEM
		}

		private final String listAttributePath;
		private final LIST_QUALIFIER_TYPE listQualifierType;
		private Integer index;
		private Map<String, String> attributes;

		public ListAndQualifierResult(String listAttributePath)
		{
			super();
			this.listAttributePath = listAttributePath;
			listQualifierType = LIST_QUALIFIER_TYPE.NEW_ITEM;
		}

		public ListAndQualifierResult(String listAttributePath, Integer index)
		{
			super();
			this.listAttributePath = listAttributePath;
			this.index = index;
			listQualifierType = LIST_QUALIFIER_TYPE.INDEX;
		}

		public ListAndQualifierResult(String listAttributePath, Map<String, String> attributes)
		{
			super();
			this.listAttributePath = listAttributePath;
			this.attributes = attributes;
			listQualifierType = LIST_QUALIFIER_TYPE.ATTRIBUTES;
		}

		public Map<String, String> getAttributes()
		{
			return attributes;
		}

		public Integer getIndex()
		{
			return index;
		}

		public String getListAttributePath()
		{
			return listAttributePath;
		}

		public LIST_QUALIFIER_TYPE getListQualifierType()
		{
			return listQualifierType;
		}

	}

	public static Object get(IBaseVO vo, String attributePath)
	{
		Object result = null;
		String[] attributePaths = attributePath.split("/");

		try
		{
			IBaseVO tempVO = traverseVOPaths(attributePaths, vo);

			String lastAttributePath = attributePaths[attributePaths.length - 1];
			if (pathContainsListQualifier(lastAttributePath))
			{
				result = getSingleFromList(lastAttributePath, tempVO);
			}
			else
			{
				result = tempVO.get(lastAttributePath);
			}

		}
		catch (UnknownAttributeException e)
		{
			throw new UnknownAttributeException("incorrect attribute path '" + attributePath + "' for VO '" + vo.getClass().getName() + "'", e);
		}

		return result;
	}

	private static List<IBaseVO> getFromList(ListAndQualifierResult lqr, List<IBaseVO> voList)
	{
		List<IBaseVO> result = new ArrayList<IBaseVO>();

		switch (lqr.getListQualifierType())
		{
			case ATTRIBUTES:
				for (IBaseVO listVOItem : voList)
				{
					if (voMatchesAttributes(listVOItem, lqr.getAttributes()))
					{
						result.add(listVOItem);
					}
				}
				break;
			case INDEX:

				IBaseVO listVOItem = voList.get(lqr.getIndex());
				result.add(listVOItem);

				break;
			default:
				throw new UnknownAttributeException("unsupported list qualifier type '" + lqr.getListQualifierType() + "'");
		}

		return result;
	}

	private static List<IBaseVO> getList(IBaseVO vo, String listAttributePath)
	{
		Object listObject = vo.get(listAttributePath);
		if (!(listObject instanceof List))
		{
			throw new UnknownAttributeException("attribute path '" + listAttributePath + "' is not an list type");
		}

		@SuppressWarnings("unchecked")
		List<IBaseVO> list = (List<IBaseVO>) listObject;
		return list;
	}

	private static ListAndQualifierResult getListAndQualifier(String attributePath)
	{
		if (attributePath.endsWith("[]"))
		{
			String list = attributePath.substring(0, attributePath.length() - 2);
			return new ListAndQualifierResult(list);
		}
		else
		{
			String list = attributePath.substring(0, attributePath.length() - 1);
			String listSubPaths[] = list.split("\\[");
			list = listSubPaths[0];
			String listQualifier = listSubPaths[1];

			if (listQualifier.contains("="))
			{
				String listQualifierParts[] = listQualifier.split("=");
				Map<String, String> attributes = new HashMap<String, String>();
				attributes.put(listQualifierParts[0], listQualifierParts[1]);

				return new ListAndQualifierResult(list, attributes);
			}
			else
			{
				try
				{
					Integer index = Integer.parseInt(listQualifier);
					return new ListAndQualifierResult(list, index);
				}
				catch (NumberFormatException e)
				{
					// ignore, we try other possibilites later
				}
			}
		}

		throw new UnknownAttributeException("unsupported list qulifier in list attribute path '" + attributePath + "'");
	}

	private static IBaseVO getSingleFromList(ListAndQualifierResult lqr, List<IBaseVO> list)
	{
		List<IBaseVO> result = getFromList(lqr, list);

		if (result.size() == 1)
		{
			return result.get(0);
		}
		else
		{
			if (result.size() == 0)
			{
				throw new UnknownAttributeException("list qualifier did not match any element");
			}
			else
			{
				throw new UnknownAttributeException("list qualifier refers to more than on element");
			}
		}

	}

	private static IBaseVO getSingleFromList(String attributePath, IBaseVO vo)
	{
		ListAndQualifierResult lqr = getListAndQualifier(attributePath);
		List<IBaseVO> list = getList(vo, lqr.getListAttributePath());
		IBaseVO listVOItem = getSingleFromList(lqr, list);

		return listVOItem;
	}

	private static boolean pathContainsListQualifier(String subPath)
	{
		return subPath.length() > 3 && subPath.contains("[") && subPath.endsWith("]");
	}

	public static void set(IBaseVO vo, String attributePath, Object value)
	{
		String[] attributePaths = attributePath.split("/");

		try
		{
			IBaseVO tempVO = traverseVOPaths(attributePaths, vo);
			String lastAttributePath = attributePaths[attributePaths.length - 1];

			if (pathContainsListQualifier(lastAttributePath))
			{
				ListAndQualifierResult lqr = getListAndQualifier(lastAttributePath);
				List<IBaseVO> list = getList(tempVO, lqr.getListAttributePath());

				if (!(value instanceof IBaseVO))
				{
					throw new UnknownAttributeException("value is not a IBaseVo type");
				}

				switch (lqr.getListQualifierType())
				{
					case ATTRIBUTES:

						IBaseVO listVOItem = getSingleFromList(lqr, list);
						list.set(list.indexOf(listVOItem), (IBaseVO) value);

						break;
					case INDEX:

						if (list.size() - 1 < lqr.getIndex())
						{
							throw new UnknownAttributeException("list qualifier is '" + lqr.getIndex() + "' but list has only '" + list.size() + "' elements");
						}

						list.set(lqr.index, (IBaseVO) value);
						break;
					case NEW_ITEM:
						list.add((IBaseVO) value);
						break;
					default:
						throw new UnknownAttributeException("unsupported list qualifier type '" + lqr.getListQualifierType() + "'");
				}

			}
			else
			{
				tempVO.set(lastAttributePath, value);
			}
		}
		catch (UnknownAttributeException e)
		{
			throw new UnknownAttributeException("could not set value for attribute path '" + attributePath + "' on VO '" + vo.getClass().getName() + "'", e);
		}

	}

	public static void set(List<? extends IBaseVO> list, String attributePath, Object value)
	{
		DummyVO dummyVO = new DummyVO(list);
		set(dummyVO, "list" + attributePath, value);
	}

	private static IBaseVO traverseVOPaths(String attributePaths[], IBaseVO vo)
	{
		IBaseVO result = vo;
		int pathIndex = 0;

		for (pathIndex = 0; pathIndex < attributePaths.length - 1; pathIndex++)
		{
			String subPath = attributePaths[pathIndex];

			if (pathContainsListQualifier(subPath))
			{
				result = getSingleFromList(subPath, result);
			}
			else
			{
				try
				{
					result = (IBaseVO) result.get(subPath);
				}
				catch (ClassCastException e)
				{
					throw new UnknownAttributeException("attribute path '" + subPath + "' is not an IBaseVO type");
				}
			}
		}

		return result;
	}

	private static boolean voMatchesAttributes(IBaseVO vo, Map<String, String> attributesToMatch)
	{
		for (Map.Entry<String, String> attributeEntry : attributesToMatch.entrySet())
		{
			Object attributeValue = null;

			try
			{
				attributeValue = vo.get(attributeEntry.getKey());
			}
			catch (UnknownAttributeException e)
			{
				throw new UnknownAttributeException("unknwon attribute '" + attributeEntry.getKey() + "' for VO '" + vo.getClass().getName() + "'");
			}

			if (attributeValue == null || !attributeValue.toString().equals(attributeEntry.getValue()))
			{
				return false;
			}
		}

		return true;
	}

	private VOBeanUtil()
	{
	}

}
