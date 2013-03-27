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
package de.pellepelster.myadmin.db.jpql;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a join in a JPQL query
 * 
 * @author pelle
 * @version $Rev: 797 $, $Date: 2010-12-17 14:39:43 +0100 (Fri, 17 Dec 2010) $
 * 
 */
public class Join implements IEntity
{

	/**
	 * Join types for a JPQL join
	 */
	public enum JOIN_TYPE
	{
		/**
		 * Represents a left join
		 */
		LEFT
		{
			/** {@inheritDoc} */
			@Override
			public String toString()
			{
				return "LEFT JOIN";
			}

		}

		/**
		 * Represents a left fetch join
		 */
		// LEFT_FETCH {
		// /** {@inheritDoc} */
		// @Override
		// public String toString() {
		// return "LEFT JOIN FETCH";
		// }
		//
		// }
	}

	/** Joins for this query */
	private final List<Join> joins = new ArrayList<Join>();

	/** The join field */
	private final String field;

	/** Type of the join */
	private final JOIN_TYPE joinType;

	private String alias;

	private IAliasProvider aliasProvider;;

	/**
	 * Constructor for <code>Join</code>
	 * 
	 * @param aliasProvider
	 * @param joinType
	 * @param field
	 */
	public Join(IAliasProvider aliasProvider, JOIN_TYPE joinType, String field)
	{
		super();
		this.alias = aliasProvider.getNewAlias();
		this.joinType = joinType;
		this.field = field;
		this.aliasProvider = aliasProvider;
	}

	/**
	 * Adds a new fetch join for a field
	 * 
	 * @param field
	 * @return
	 */
	public Join addJoin(JOIN_TYPE joinType, String field)
	{

		Join join = new Join(aliasProvider, joinType, field);
		joins.add(join);

		return join;
	}

	/** {@inheritDoc} */
	@Override
	public String getAlias()
	{
		return alias;
	}

	public String getField()
	{
		return field;
	}

	/**
	 * Returns the join clause for this query
	 * 
	 * @return
	 */
	public String getJoinClause()
	{

		String result = "";
		String delimiter = "";

		for (Join join : joins)
		{
			result += delimiter + String.format("%s %s.%s %s", join.getJoinType(), getAlias(), join.getField(), join.getAlias());
			delimiter = " ";
		}

		return result;
	}

	/**
	 * Returns the type of the join
	 * 
	 * @return
	 */
	public JOIN_TYPE getJoinType()
	{
		return joinType;
	}

}
