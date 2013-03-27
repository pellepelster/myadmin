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
package de.pellepelster.myadmin.client.base.jpql;

/**
 * Operators that can be used for two {@link IExpressionObject}s
 * 
 * @author pelle
 * @version $Rev$, $Date$
 * 
 */
public enum RelationalOperator
{
	EQUAL
	{
		/** {@inheritDoc} */
		@Override
		public String toString()
		{
			return "=";
		}
	},
	GREATEOREQUAL
	{
		/** {@inheritDoc} */
		@Override
		public String toString()
		{
			return ">=";
		}
	},
	GREATER
	{
		/** {@inheritDoc} */
		@Override
		public String toString()
		{
			return ">";
		}
	},
	IS
	{
		/** {@inheritDoc} */
		@Override
		public String toString()
		{
			return "IS";
		}
	},
	LESS
	{
		/** {@inheritDoc} */
		@Override
		public String toString()
		{
			return "<";
		}
	},
	LESSOREQUAL
	{
		/** {@inheritDoc} */
		@Override
		public String toString()
		{
			return "<=";
		}
	},
	LIKE
	{
		/** {@inheritDoc} */
		@Override
		public String toString()
		{
			return "LIKE";
		}
	},
	NOTEQUAL
	{
		/** {@inheritDoc} */
		@Override
		public String toString()
		{
			return "<>";
		}
	};
}
