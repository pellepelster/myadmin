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
package de.pellepelster.myadmin.client.gwt.modules.hierarchical;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import de.pellepelster.myadmin.client.web.entities.dictionary.DictionaryHierarchicalNodeVO;

public class HierarchicalCell extends AbstractSafeHtmlCell<DictionaryHierarchicalNodeVO> implements Cell<DictionaryHierarchicalNodeVO>
{

	public HierarchicalCell()
	{
		super(HierarchicalSafeHtmlRenderer.getInstance());
	}

	@Override
	public void render(Context context, SafeHtml value, SafeHtmlBuilder sb)
	{
		if (value != null)
		{
			sb.append(value);
		}
	}

}
